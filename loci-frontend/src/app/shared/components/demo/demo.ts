import { Component, inject, input, model, OnDestroy, OnInit, output, signal } from '@angular/core';
import { Message } from '@stomp/stompjs';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { FilePreview } from '../file-preview-card/file-preview-card';
import { RxStompService } from '../../../core/socket/rx-stomp.service';
import { environment } from '../../../../environments/environments';
import { AuthService } from '../../../core/auth/auth.service';

interface ChatMessage {
  content: string
}
@Component({
  standalone: false,
  selector: 'app-demo',
  templateUrl: './demo.html',
  styleUrl: './demo.css',
})
export class Demo implements OnInit {

  private stomp = inject(RxStompService)
  greetings: ChatMessage[] = [];


  send() {
    this.stomp.publish({
      destination: '/app/chat.send',
      body: JSON.stringify({ content: "Hello" })
    });
  }




  receivesMessage = signal<ChatMessage[]>([]);
  private rxStompService = inject(RxStompService);
  private authService = inject(AuthService);
  async ngOnInit(): Promise<void> {
    console.log(this.rxStompService);
    this.rxStompService.watch("/topic/messages").subscribe((message: Message) => {
      console.log("receive the message ", message.body);
      const chatMessage = JSON.parse(message.body) as ChatMessage;
      console.log("content", chatMessage.content);
      // const newMessage = message.body as ChatMessage;
      // this.receivesMessage.update(m => [...m,  newMessage]);
    })
  }


  public onSendMessage() {
    const bodyMessage = `Message created at ${new Date()}`;
    console.log(`the env config ${environment.socketEndpoint}`);
    console.log("send message to server", bodyMessage);
    this.rxStompService.publish({ destination: "/app/messages", body: bodyMessage });
  }

  isSaving = false;
  isDeleting = false;
  isLoading = signal(true);
  inputValue = model('');
  searchValue = model('');

  saveProfile() {
    console.log("save")
    // Handle save logic
  }

  cancel() {
    // Handle cancel logic
    console.log("cancel")
  }

  deleteAccount() {
    // Handle delete logic
    console.log("delete")
  }


  private fb = inject(FormBuilder);

  chatId = input<string>(); // Example input from parent (e.g., Conversation screen)
  messageSent = output<string>(); // Output to emit sent message

  form = this.fb.group({
    message: ['', [Validators.required, Validators.maxLength(500)]],
    search: [''],
    groupName: ['', Validators.required],
  });

  get messageControl() {
    return this.form.get('message') as FormControl<string>;
  }

  get searchControl() {
    return this.form.get('search') as FormControl<string>;
  }

  get groupNameControl() {
    return this.form.get('groupName') as FormControl<string>;
  }

  onSend() {
    if (this.form.invalid) return;
    const message = this.messageControl.value;
    this.messageSent.emit(message);
    this.messageControl.reset();
  }

  onEnterPressed() {
    this.onSend(); // Handle Enter key for sending
  }

  onValueChanged(value: string) {
    console.log('Value changed:', value); // Example: Could trigger typing indicator via WebSocket
  }


  readonly attachments = signal<FilePreview[]>([
    {
      filename: 'project-proposal.pdf',
      size: '245 KB',
      type: 'application/pdf',
      url: '/assets/mocks/project-proposal.pdf',
    },
    {
      filename: 'screenshot-long-name-preview-example-01.png',
      size: '1.2 MB',
      type: 'image/png',
      url: 'avatar6.svg',
    },
  ]);

  removeAttachment(index: number): void {
    const next = [...this.attachments()];
    next.splice(index, 1);
    this.attachments.set(next);
  }


  // Modal
  readonly modalOpen = signal(false);

  openModal(): void {
    this.modalOpen.set(true);
  }

  closeModal(): void {
    this.modalOpen.set(false);
  }

  onConfirm(): void {
    // TODO: Hook delete logic (API call, service)
    console.log('Conversation deleted');

    this.modalOpen.set(false);
  }

  onCancel(): void {
    this.modalOpen.set(false);
  }
  onBack(): void {
    console.log("Go backed")
  }

  onHeaderAction(event: string) {
    console.log("Header action called", event);
  }

  // onHeaderAction(event: Event): void {
  //   console.log("Header action")
  // }

}
