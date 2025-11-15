import { Injectable } from "@angular/core";

/**
 * Simple focus management service:
 * - Captures the previously focused element when the modal opens.
 * - Restores focus back to that element when the modal closes.
 */
@Injectable({ providedIn: 'root' })
export default class FocusManagementService {
  private previousFocusedElement: HTMLElement | null = null;


  capture(): void {
    this.previousFocusedElement = document.activeElement as HTMLElement | null;
  }

  restore(): void {
    if (this.previousFocusedElement) {
      this.previousFocusedElement.focus();
      this.previousFocusedElement = null;
    }
  }
}
