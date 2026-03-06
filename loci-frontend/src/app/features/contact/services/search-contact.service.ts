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

import { inject, Injectable } from '@angular/core';
import { WebApiService } from '../../../core/api/web-api.service';
import { Observable } from 'rxjs';
import {
  IFriendSuggestionList,
  IContactProfileList,
} from '../models/contact.model';
import { LoggerService } from '../../../core/services/logger.service';

@Injectable()
export class DiscoveryContactService {
  private apiService = inject(WebApiService);
  private loggerService = inject(LoggerService);
  private logger = this.loggerService.getLogger('SearchUserService');

  getSuggestions(): Observable<IFriendSuggestionList> {
    return this.apiService.get<IFriendSuggestionList>('/users/suggests');
  }

  search(query: string): Observable<IContactProfileList> {
    this.logger.debug('Query user with ', query);
    // const params = new HttpParams().set('q', query.trim());
    // return this.apiService.get<UserSearchItem[]>('/users', params);
    return this.apiService.get<IContactProfileList>('/users/search?q=' + query);
  }
}
