import {
  ChangeDetectionStrategy,
  Component,
  ElementRef,
  HostListener,
  ViewChild,
  computed,
  effect,
  inject,
  input,
  output,
  signal,
  Directive,
  Input as NgInput,
  OnChanges,
  OnDestroy,
  SimpleChanges,
} from '@angular/core';
import { CommonModule, DOCUMENT } from '@angular/common';
import FocusManagementService from '../../services/focus-manager.service';


/**
 * Body scroll lock directive:
 * - Adds `overflow-hidden` to the <body> when enabled.
 * - Removes it when disabled or destroyed.
 */
@Directive({
  selector: '[appBodyScrollLock]',
  standalone: true,
})
export class BodyScrollLockDirective implements OnChanges, OnDestroy {
  @NgInput() appBodyScrollLock = false;

  private readonly document = inject(DOCUMENT);

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['appBodyScrollLock']) {
      if (this.appBodyScrollLock) {
        this.document.body.classList.add('overflow-hidden');
      } else {
        this.document.body.classList.remove('overflow-hidden');
      }
    }
  }

  ngOnDestroy(): void {
    this.document.body.classList.remove('overflow-hidden');
  }
}

export type ModalType = 'default' | 'danger';

let nextId = 0;

@Component({
  selector: 'app-modal',
  standalone: true,
  imports: [CommonModule, BodyScrollLockDirective],
  templateUrl: './modal.html',
  styleUrls: ['./modal.css'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class Modal{
  // Inputs
  readonly isOpen = input<boolean>(false);
  readonly title = input<string>('');
  readonly confirmText = input<string>('Confirm');
  readonly cancelText = input<string>('Cancel');
  readonly type = input<ModalType>('default');

  // Outputs
  readonly confirmed = output<void>();
  readonly canceled = output<void>();

  // Template refs
  @ViewChild('dialogCard', { static: false })
  dialogCard?: ElementRef<HTMLElement>;

  private readonly focusService = inject(FocusManagementService);

  readonly modalId = `app-modal-${nextId++}`;
  readonly titleId = `${this.modalId}-title`;

  // Button styling based on modal type
  readonly confirmButtonClasses = computed(() => {
    const base =
      'inline-flex items-center justify-center px-4 py-2 rounded-md text-sm font-medium focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-offset-2';
    if (this.type() === 'danger') {
      return (
        base +
        ' bg-red-600 text-white hover:bg-red-700 focus-visible:ring-red-600'
      );
    }
    return (
      base +
      ' bg-[var(--color-primary)] text-white hover:bg-[var(--color-primary-600)] focus-visible:ring-[var(--color-primary)]'
    );
  });

  readonly cancelButtonClasses = signal(
    'inline-flex items-center justify-center px-4 py-2 rounded-md text-sm font-medium border border-neutral-300 text-neutral-700 bg-white hover:bg-neutral-50 focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-offset-2 focus-visible:ring-neutral-400',
  );

  constructor() {
    // React to open/close changes for focus management & initial focus
    effect(() => {
      const open = this.isOpen();

      if (open) {
        this.onOpened();
      } else {
        this.onClosed();
      }
    });
  }

  // ESC key closes the modal as "cancel"
  @HostListener('document:keydown.escape', ['$event'])
  onEscapeKey(event: Event): void { // original is KeyboardEvent
    if (!this.isOpen()) {
      return;
    }
    event.preventDefault();
    this.handleCancel();
  }

  // Backdrop click closes as "cancel"
  onBackdropClick(): void {
    this.handleCancel();
  }

  // Prevent click from bubbling to backdrop
  stopClickPropagation(event: MouseEvent): void {
    event.stopPropagation();
  }

  // Keyboard handling for focus trap
  onKeydown(event: KeyboardEvent): void {
    if (event.key !== 'Tab') {
      return;
    }

    const dialogEl = this.dialogCard?.nativeElement;
    if (!dialogEl) {
      return;
    }

    const focusableSelectors = [
      'button',
      '[href]',
      'input',
      'select',
      'textarea',
      '[tabindex]:not([tabindex="-1"])',
    ].join(',');

    const focusable = Array.from(
      dialogEl.querySelectorAll<HTMLElement>(focusableSelectors),
    ).filter(
      (el) =>
        !el.hasAttribute('disabled') &&
        el.getAttribute('aria-hidden') !== 'true',
    );

    if (!focusable.length) {
      return;
    }

    const first = focusable[0];
    const last = focusable[focusable.length - 1];
    const current = document.activeElement as HTMLElement;

    if (event.shiftKey) {
      // Shift + Tab
      if (!dialogEl.contains(current) || current === first) {
        last.focus();
        event.preventDefault();
      }
    } else {
      // Tab
      if (!dialogEl.contains(current) || current === last) {
        first.focus();
        event.preventDefault();
      }
    }
  }

  private onOpened(): void {
    // Capture currently focused element to restore later
    this.focusService.capture();

    // Wait for view to render then focus first element
    queueMicrotask(() => this.focusFirstInteractiveElement());
  }

  private onClosed(): void {
    // Restore focus back to whatever was focused before
    this.focusService.restore();
  }

  private focusFirstInteractiveElement(): void {
    const dialogEl = this.dialogCard?.nativeElement;
    if (!dialogEl) {
      return;
    }

    const focusableSelectors = [
      'button',
      '[href]',
      'input',
      'select',
      'textarea',
      '[tabindex]:not([tabindex="-1"])',
    ].join(',');

    const focusable = Array.from(
      dialogEl.querySelectorAll<HTMLElement>(focusableSelectors),
    ).filter(
      (el) =>
        !el.hasAttribute('disabled') &&
        el.getAttribute('aria-hidden') !== 'true',
    );

    if (focusable.length) {
      focusable[0].focus();
      return;
    }

    // Fallback: focus the dialog container itself
    dialogEl.setAttribute('tabindex', '-1');
    dialogEl.focus();
  }

  handleConfirm(): void {
    this.confirmed.emit();
  }

  handleCancel(): void {
    this.canceled.emit();
  }
}
