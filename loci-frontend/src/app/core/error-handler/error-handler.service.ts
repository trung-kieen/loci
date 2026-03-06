/**
 * Copyright 2026 trung-kieen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import { HttpErrorResponse } from '@angular/common/http';
import { Injectable, ErrorHandler, inject } from '@angular/core';
import { Router } from '@angular/router';
import { throwError } from 'rxjs';
import { LoggerService } from '../services/logger.service';

@Injectable({
  providedIn: 'root'
})
export class ErrorHandlerService implements ErrorHandler {
  private router = inject(Router);
  private loggerService = inject(LoggerService);
  private logger = this.loggerService.getLogger("ErrorHandlerService");
  handleError(error: unknown) {

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

  private handleGenericError(error: HttpErrorResponse | Error | unknown) {
    let message: string;
    if (error instanceof HttpErrorResponse) {
      message = error.error?.message || error.message || `${error.status} ${error.statusText}`;
    } else if (error instanceof Error) {
      message = error.message;
    } else {                                           // last resort
      message = 'An unexpected error occurred';
    }
    this.showError(message);
  }

  private showError(message: string) {
    this.logger.error("Handle error response: ", message)
    // Could be a toast notification, alert, or modal
    // Example: this.notificationService.showError(message);
  }

}
