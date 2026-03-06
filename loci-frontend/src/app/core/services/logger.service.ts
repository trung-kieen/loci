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
import { Injectable, isDevMode } from '@angular/core';
import { environment } from '../../../environments/environments';
import { HttpErrorResponse } from '@angular/common/http';

export enum LogLevel {
  Debug = 0,
  Info = 1,
  Warn = 2,
  Error = 3,
}

@Injectable({ providedIn: 'root' })
export class LoggerService {
  private minLevel: LogLevel = environment.production
    ? LogLevel.Warn
    : LogLevel.Debug;

  // Sensitive keys that will be redacted
  private readonly sensitiveKeys = [
    'password',
    'token',
    'authorization',
    'auth',
    'secret',
    'key',
    'apikey',
    'apisecret',
    'credentials',
  ];

  private shouldLog(level: LogLevel): boolean {
    return level >= this.minLevel;
  }

  /**
   * Enhanced serialization that handles circular refs, depth limiting,
   * sensitive data, and special types (Errors, DOM nodes, HttpErrorResponse)
   */
  private serializeParam(param: any, depth = 0, maxDepth = 3): string {
    // Primitives & null/undefined
    if (param === null || param === undefined) return String(param);
    if (
      typeof param === 'string' ||
      typeof param === 'number' ||
      typeof param === 'boolean'
    ) {
      return String(param);
    }

    // Symbol/BigInt
    if (typeof param === 'symbol') return param.toString();
    if (typeof param === 'bigint') return `${param}n`;

    // Error objects
    if (param instanceof Error) {
      return JSON.stringify({
        name: param.name,
        message: param.message,
        stack: param.stack?.split('\n').slice(0, 5).join('\n'),
      });
    }

    // DOM elements
    if (param instanceof HTMLElement) {
      const id = param.id ? ` id="${param.id}"` : '';
      const classes = param.className ? ` class="${param.className}"` : '';
      return `[DOM: <${param.tagName.toLowerCase()}${id}${classes}>]`;
    }

    // Angular HttpErrorResponse
    if (param instanceof HttpErrorResponse) {
      return JSON.stringify({
        status: param.status,
        statusText: param.statusText,
        url: param.url,
        message: param.message,
      });
    }

    // Functions & callables
    if (typeof param === 'function') {
      return `[Function: ${param.name || 'anonymous'}]`;
    }
    if (
      typeof param.then === 'function' ||
      typeof param.subscribe === 'function'
    ) {
      return `[${param.constructor?.name || 'Promise/Observable'}]`;
    }

    // Collections
    if (param instanceof Map) return `[Map: ${param.size} entries]`;
    if (param instanceof Set) return `[Set: ${param.size} values]`;

    // Depth limit
    if (depth > maxDepth) return `[${param.constructor?.name || 'Object'}]`;

    // Main serialization with sanitization & circular detection
    try {
      const seen = new WeakSet();

      return JSON.stringify(
        param,
        (key, value) => {
          // Redact sensitive data
          if (
            typeof key === 'string' &&
            this.sensitiveKeys.some((k) => key.toLowerCase().includes(k))
          ) {
            return '[REDACTED]';
          }

          // Handle circular references
          if (typeof value === 'object' && value !== null) {
            if (seen.has(value)) return '[Circular]';
            seen.add(value);
          }

          // Truncate long strings
          if (typeof value === 'string' && value.length > 500) {
            return `${value.substring(0, 500)}... (${value.length - 500} chars)`;
          }

          return value;
        },
        environment.production ? undefined : 2, // Pretty print in dev only
      );
    } catch {
      return `[Unserializable: ${String(param).substring(0, 100)}]`;
    }
  }

  private formatMessage(
    level: string,
    context: string,
    message: string,
  ): string {
    const timestamp = new Date().toISOString().slice(11, 23);
    return `${timestamp} ${level.padEnd(5)} [${context}] ${message}`;
  }

  getLogger(context = 'App') {
    return {
      debug: (message: string, ...optionalParams: any[]) => {
        if (this.shouldLog(LogLevel.Debug)) {
          const cssStyle = 'color: #8a8a8a; font-weight: bold;';
          const formatted = this.formatMessage('DEBUG', context, message);

          const params = environment.production
            ? optionalParams.map((p) => this.serializeParam(p))
            : optionalParams;

          console.debug(`%c${formatted}`, cssStyle, ...params);
        }
      },

      info: (message: string, ...optionalParams: any[]) => {
        if (this.shouldLog(LogLevel.Info)) {
          const cssStyle = 'color: #33ab33; font-weight: bold;';
          const formatted = this.formatMessage('INFO', context, message);

          const params = environment.production
            ? optionalParams.map((p) => this.serializeParam(p))
            : optionalParams;

          console.info(`%c${formatted}`, cssStyle, ...params);
        }
      },

      warn: (message: string, ...optionalParams: any[]) => {
        if (this.shouldLog(LogLevel.Warn)) {
          const cssStyle = 'color: #ff8c00; font-weight: bold;';
          const formatted = this.formatMessage('WARN', context, message);

          const params = environment.production
            ? optionalParams.map((p) => this.serializeParam(p))
            : optionalParams;

          console.warn(`%c${formatted}`, cssStyle, ...params);
        }
      },

      error: (message: string, ...optionalParams: any[]) => {
        if (this.shouldLog(LogLevel.Error)) {
          const cssStyle = 'color: #ff3333; font-weight: bold;';
          const formatted = this.formatMessage('ERROR', context, message);

          // Always serialize Errors for stack traces
          const params = optionalParams.map((p) =>
            p instanceof Error
              ? this.serializeParam(p)
              : environment.production
                ? this.serializeParam(p)
                : p,
          );

          console.error(`%c${formatted}`, cssStyle, ...params);
        }
      },
    };
  }
}
