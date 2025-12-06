import { inject, Injectable } from "@angular/core";
import { WebApiService } from "../../../api/web-api.service";
import { Observable } from "rxjs";
import { Page } from "../../../core/model/page";
import { ContactSearchItem } from "../models/contact.model";
import { LoggerService } from "../../../core/services/logger.service";

@Injectable()
export class SearchUserService {
  private apiService = inject(WebApiService);
  private loggerService = inject(LoggerService);
  private logger = this.loggerService.getLogger("SearchUserService")

  search(query: string): Observable<Page<ContactSearchItem>> {

    this.logger.debug("Query user with ", query);
    // const params = new HttpParams().set('q', query.trim());
    // return this.apiService.get<UserSearchItem[]>('/users', params);
    return this.apiService.get<Page<ContactSearchItem>>("/users/search?q=" + query);
  }





}
