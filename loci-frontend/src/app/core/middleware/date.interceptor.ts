import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

const ISO_DATE_REGEX = /^\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}(\.\d+)?Z$/;

function convertDates(obj: unknown): unknown {
  if (typeof obj === 'string' && ISO_DATE_REGEX.test(obj)) {
    return new Date(obj);
  }
  if (Array.isArray(obj)) return obj.map(convertDates);
  if (obj !== null && typeof obj === 'object') {
    return Object.fromEntries(
      Object.entries(obj as object).map(([k, v]) => [k, convertDates(v)])
    );
  }
  return obj;
}

@Injectable()
export class DateInterceptor implements HttpInterceptor {
  intercept(req: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    return next.handle(req).pipe(
      map(event => {
        if (event instanceof HttpResponse && event.body) {
          return event.clone({ body: convertDates(event.body) });
        }
        return event;
      })
    );
  }
}
