import { HttpErrorResponse } from '@angular/common/http';
import { Injectable, ErrorHandler, inject } from '@angular/core';
import { Router } from '@angular/router';
import { throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ErrorHandlerService implements ErrorHandler {
  private router = inject(Router);
  handleError(error: any) {
    // TODO: log error


    if (error instanceof HttpErrorResponse) {
      this.handleHttpError(error);
    } else if (error instanceof TypeError) {
      this.handleTypeError(error);
    } else {
      this.handleGenericError(error);
    }


    return throwError(() => error)
  }


  private handleHttpError(error: HttpErrorResponse) {
    let errorMessage = '';

    if (error.error instanceof ErrorEvent) {
      // Client-side error
      errorMessage = `Client Error: ${error.error.message}`;
    } else {
      // Server-side error
      switch (error.status) {
        case 400:
          errorMessage = 'Bad Request: Please check your input';
          break;
        case 401:
          errorMessage = 'Unauthorized: Please log in';
          // Redirect to login page
          this.router.navigate(['/login']);
          break;
        case 403:
          errorMessage = 'Forbidden: You don\'t have permission';
          break;
        case 404:
          errorMessage = 'Resource not found';
          break;
        case 500:
          errorMessage = 'Internal Server Error';
          break;
        default:
          errorMessage = `Server Error: ${error.message}`;
      }
    }

    // Display error to user (you can implement a notification service)
    this.showError(errorMessage);
  }

  private handleTypeError(error: TypeError) {
    const errorMessage = `Type Error: ${error.message}`;
    this.showError(errorMessage);
  }

  private handleGenericError(error: any) {
    const errorMessage = `An unexpected error occurred: ${error.message || error}`;
    this.showError(errorMessage);
  }

  private showError(message: string) {
    // TODO: Change handler for the error default behavior
    // Could be a toast notification, alert, or modal
    console.error('Error notification:', message);
    // Example: this.notificationService.showError(message);
  }

}
