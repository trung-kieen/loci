import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable, retry } from 'rxjs';
import { environment } from '../../environments/environments';




@Injectable()
export class WebApiService {
  private http = inject(HttpClient);
  private apiBaseUrl = environment.apiUrl;

  // Private method for common headers (customize as needed, e.g., add auth token)
  private getHeaders(options?: Record<string, string>): HttpHeaders {
    let headers = new HttpHeaders({
      'Content-Type': 'application/json',
      // 'Authorization': `Bearer ${this.getToken()}` // Uncomment if using auth
    });
    if (options) {
      Object.keys(options).forEach(key => headers = headers.set(key, options[key]));
    }
    return headers;
  }

  // Generic GET
  get<T>(endpoint: string, params?: HttpParams, headers?: Record<string, string>): Observable<T> {
    const url = this.getFullUrl(endpoint);
    const httpOptions = { headers: this.getHeaders(headers), params };
    return this.http.get<T>(url, httpOptions).pipe(
      retry(2), // Retry on failure
      // catchError(this.handleError)
    );
  }

  // Generic POST
  post<T>(endpoint: string, body: unknown, headers?: Record<string, string>): Observable<T> {
    const url = this.getFullUrl(endpoint);
    const httpOptions = { headers: this.getHeaders(headers) };
    return this.http.post<T>(url, body, httpOptions).pipe(
      // catchError(this.handleError)
    );
  }

  // Generic PUT
  put<T>(endpoint: string, body: unknown, headers?: Record<string, string>): Observable<T> {
    const url = this.getFullUrl(endpoint);
    const httpOptions = { headers: this.getHeaders(headers) };
    return this.http.put<T>(url, body, httpOptions).pipe(
      // catchError(this.handleError)
    );
  }
  patch<T>(endpoint: string, body: unknown, headers?: Record<string, string>): Observable<T> {
    const url = this.getFullUrl(endpoint);
    const httpOptions = { headers: this.getHeaders(headers) };
    return this.http.patch<T>(url, body, httpOptions).pipe(
      // catchError(this.handleError)
    );
  }

  // Generic DELETE
  delete<T>(endpoint: string, headers?: Record<string, string>): Observable<T> {
    const url = this.getFullUrl(endpoint);
    const httpOptions = { headers: this.getHeaders(headers) };
    return this.http.delete<T>(url, httpOptions).pipe(
      // catchError(this.handleError)
    );
  }

  // Utility for full URL
  private getFullUrl(endpoint: string): string {
    return `${this.apiBaseUrl}/${endpoint.replace(/^\/+/, '')}`; // Normalize path
  }

  // Centralized error handler
  // private handleError(error: HttpErrorResponse) {
  //   let errorMessage = 'An unknown error occurred!';
  //   if (error.error instanceof ErrorEvent) {
  //     errorMessage = `Client-side error: ${error.error.message}`;
  //   } else {
  //     errorMessage = `Server error: ${error.status} - ${error.message}`;
  //     // Optionally, handle specific status codes (e.g., 401 for logout)
  //   }
  //   console.error(errorMessage);
  //   return throwError(() => new Error(errorMessage));
  // }
}
