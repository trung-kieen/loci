import { HttpParams } from "@angular/common/http";

/* eslint-disable @typescript-eslint/no-explicit-any */
export interface Pageable {
  pageNumber: number;
  pageSize: number;
  sort: Sort;
}

export interface Sort {
  empty: boolean;
  sorted: boolean;
  unsorted: boolean;
}

/**
 * Exact mirror of Spring Data REST / Spring HATEOAS page JSON.
 * Generic <T> is domain DTO coming from the backend.
 */
export interface Page<T> {
  content: T[];
  pageable: Pageable;
  totalElements: number;
  totalPages: number;
  last: boolean;
  number: number;
  size: number;
  sort: Sort;
  numberOfElements: number;
  first: boolean;
  empty: boolean;
}

/* ---------------------------------------------------------- */
/* Helper to build HttpParams in a type-safe way              */
/* ---------------------------------------------------------- */
export interface PageRequest {
  page?: number;   // 0-based
  size?: number;
  sort?: string;   // e.g. "name,desc"
}

export function pageParams(req: PageRequest): HttpParams {
  let p = new HttpParams();
  if (req.page  !== undefined) p = p.set('page',  req.page.toString());
  if (req.size  !== undefined) p = p.set('size',  req.size.toString());
  if (req.sort  !== undefined) p = p.set('sort',  req.sort);
  return p;
}
