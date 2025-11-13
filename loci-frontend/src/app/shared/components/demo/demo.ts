import { Component, inject, input, model, output, signal } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';

@Component({
  standalone: false,
  selector: 'app-demo',
  templateUrl: './demo.html',
  styleUrl: './demo.css',
})
export class Demo {
  isSaving = false;
  isDeleting = false;
  isLoading = signal(true);
  inputValue = model('');
  searchValue= model('');

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

}
