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
