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

/* eslint-disable @typescript-eslint/no-explicit-any */
import {
  HttpClient,
  HttpContext,
  HttpHeaders,
  HttpParams,
} from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable, retry } from 'rxjs';
import { environment } from '../../../environments/environments';

export interface RequestOptions {
  headers?: HttpHeaders | Record<string, string | string[]>;
  context?: HttpContext;
  observe?: 'body';
  params?: HttpParams | Record<string, string | number | boolean>;
  reportProgress?: boolean;
  responseType?: 'json';
  withCredentials?: boolean;
  // credentials?: RequestCredentials;
  keepalive?: boolean;
  // priority?: RequestPriority;
  // cache?: RequestCache;
  // mode?: RequestMode;
  // redirect?: RequestRedirect;
  // referrer?: string;
  // integrity?: string;
  // transferCache?: {
  //     includeHeaders?: string[];
  // } | boolean;
  timeout?: number;
}

/**
 * Proxy service provide httpclient wrapper and websocket wrapper
 * Can be replaced by Interceptor
 * Get and delete in the httpclient will not have the body
 */
@Injectable()
export class WebApiService {
  private http = inject(HttpClient);
  private apiBaseUrl = environment.apiUrl;

  get<T>(endpoint: string, options?: RequestOptions): Observable<T> {
    const url = this.getFullUrl(endpoint);
    return this.http.get<T>(url, options).pipe(
      retry(2), // Retry on failure
      // catchError(this.handleError)
    );
  }

  post<T>(
    endpoint: string,
    body: any,
    options?: RequestOptions,
  ): Observable<T> {
    const url = this.getFullUrl(endpoint);
    return this.http
      .post<T>(url, body, options)
      .pipe
      // catchError(this.handleError)
      ();
  }

  put<T>(endpoint: string, body: any, options?: RequestOptions): Observable<T> {
    const url = this.getFullUrl(endpoint);
    return this.http
      .put<T>(url, body, options)
      .pipe
      // catchError(this.handleError)
      ();
  }
  patch<T>(
    endpoint: string,
    body: any,
    options?: RequestOptions,
  ): Observable<T> {
    const url = this.getFullUrl(endpoint);
    return this.http
      .patch<T>(url, body, options)
      .pipe
      // catchError(this.handleError)
      ();
  }

  // Generic DELETE
  delete<T>(endpoint: string, options?: RequestOptions): Observable<T> {
    const url = this.getFullUrl(endpoint);
    return this.http
      .delete<T>(url, options)
      .pipe
      // catchError(this.handleError)
      ();
  }

  // Utility for full URL
  private getFullUrl(endpoint: string): string {
    return `${this.apiBaseUrl}/${endpoint.replace(/^\/+/, '')}`; // Normalize path
  }

  /**
   * Mostly use for patch update for image file
   */
  patchForm<T>(
    endpoint: string,
    formData: FormData,
    options?: RequestOptions,
  ): Observable<T> {
    return this.patch<T>(endpoint, formData, {
      ...options,
      // Form data omit cotnent type of request
      // headers: { ...options?.headers, 'Content-Type': 'multipart/form-data', }
    });
  }

  postForm<T>(
    endpoint: string,
    formData: FormData,
    options?: RequestOptions,
  ): Observable<T> {
    return this.post<T>(endpoint, formData, {
      ...options,
      // Form data omit cotnent type of request
      // headers: { ...options?.headers, 'Content-Type': 'multipart/form-data', }
    });
  }
}
