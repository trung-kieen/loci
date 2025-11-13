import { ChangeDetectionStrategy, Component, ElementRef, ViewChild, effect, input, output, signal } from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';

@Component({
  selector: 'app-input',
  standalone: false,
  // standalone: true,
  // imports: [CommonModule, LucideAngularModule.pick({ Check, Search, X })],
  templateUrl: './input.html',
  styleUrl: './input.css',
  // schemas: [CUSTOM_ELEMENTS_SCHEMA],


  changeDetection: ChangeDetectionStrategy.OnPush,
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: Input,
      multi: true,
    },
  ],
})
export class Input implements ControlValueAccessor {
  type = input<'text' | 'email' | 'password' | 'textarea' | 'search'>('text');
  placeholder = input<string>('');
  disabled = input<boolean>(false);
  maxLength = input<number>(200);
  rows = input<number>(1);
  error = input<boolean>(false); // For validation error state (passed from form)

  valueChanged = output<string>();
  enterPressed = output<void>();

  value = signal<string>('');
  isDisabled = signal<boolean>(false);
  uniqueId = `input-${Date.now()}-${Math.floor(Math.random() * 1000)}`;
  public counterId = `${this.uniqueId}-counter`;

  // onChange: (v : string) => void = (v: string) => { this.value.set(v) };
  onChange: (v : string) => void = (v: string) => { this.value.set(v) };
  onTouched: () => void = () => { console.log("touched")};
  private isTouched = false;

  @ViewChild('inputElement') inputElement?: ElementRef<HTMLInputElement>;
  @ViewChild('textareaElement') textareaElement?: ElementRef<HTMLTextAreaElement>;

  constructor() {
    effect(() => {
      if (this.type() === 'textarea' && this.textareaElement) {
        this.autoExpand();
      }
    });
  }

  writeValue(value: unknown): void {
    this.value.set((value as string) ?? '');
  }

  registerOnChange(fn: (value: string) => void): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: () => void): void {
    this.onTouched = fn;
  }

  setDisabledState(isDisabled: boolean): void {
    this.isDisabled.set(isDisabled);
    // Handled via input binding
  }

  onInput(event: Event): void {
    const target = event.target as HTMLInputElement | HTMLTextAreaElement;
    const newValue = target.value;
    this.value.set(newValue);
    this.onChange(newValue);
    this.valueChanged.emit(newValue);
    if (this.type() === 'textarea') {
      this.autoExpand();
    }
  }

  onKeydown(event: KeyboardEvent): void {
    if (event.key === 'Enter' && !event.shiftKey && this.type() !== 'textarea') {
      event.preventDefault();
      this.enterPressed.emit();
    }
  }

  onBlur(): void {
    if (!this.isTouched) {
      this.isTouched = true;
      this.onTouched();
    }
  }

  clear(): void {
    this.value.set('');
    this.onChange('');
    this.valueChanged.emit('');
    if (this.inputElement) {
      this.inputElement.nativeElement.focus();
    }
  }

  private autoExpand(): void {
    if (!this.textareaElement) return;
    const el = this.textareaElement.nativeElement;
    const styles = getComputedStyle(el);
    const lineHeight = parseFloat(styles.lineHeight);
    const paddingTop = parseFloat(styles.paddingTop);
    const paddingBottom = parseFloat(styles.paddingBottom);
    const borderTop = parseFloat(styles.borderTopWidth);
    const borderBottom = parseFloat(styles.borderBottomWidth);
    const minRows = this.rows();
    const maxRows = 5;

    const minHeight = lineHeight * minRows + paddingTop + paddingBottom + borderTop + borderBottom;
    const maxHeight = lineHeight * maxRows + paddingTop + paddingBottom + borderTop + borderBottom;

    el.style.height = 'auto';
    const contentHeight = el.scrollHeight;
    let newHeight = Math.max(minHeight, contentHeight);
    newHeight = Math.min(newHeight, maxHeight);
    el.style.height = `${newHeight}px`;

    if (contentHeight > maxHeight) {
      el.style.overflowY = 'scroll';
    } else {
      el.style.overflowY = 'hidden';
    }
  }
}
