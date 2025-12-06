import { inject, Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { LoggerService } from "../../../core/services/logger.service";
import { WebApiService } from "../../../api/web-api.service";

@Injectable({
  providedIn: "root"
})
export class FriendManagerService {

  private apiService = inject(WebApiService);
  private loggerService = inject(LoggerService);
  private logger = this.loggerService.getLogger("FriendManagerService")
  addFriend(userId: string): Observable<void> {
    this.logger.debug("Perform send request to userId ", userId);
    return this.apiService.post<void>(`/contact-requests/${userId}`, {});
  }



  getListRequestConnectContact(): Observable<any> {
    this.logger.debug("Request to view list of request connect contact");
    const result = this.apiService.get<any>(`/contact-requests`);
    this.logger.debug("List request connect contact result {}", result);
    return result;
  }
}
