import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LoggerService } from '../../../core/services/logger.service';
import { WebApiService } from '../../../core/api/web-api.service';
import { UpdatedStatus } from '../../user/models/public-profile.model';
import { FriendRequestList } from '../models/contact.model';
import { ConversationPreview } from '../../chat/models/chat.model';

@Injectable({
  providedIn: 'root',
})
export class FriendManagerService {
  unsendFriendRequest(profileId: string): Observable<UpdatedStatus> {
    return this.apiService.delete<UpdatedStatus>(
      `/contact-requests/${profileId}`,
      {},
    );
  }
  denyFriendRequest(profileId: string): Observable<UpdatedStatus> {
    return this.apiService.post<UpdatedStatus>(
      `/contact-requests/user/${profileId}/reject`,
      {},
    );
  }
  unfriendUser(profileId: string) {
    return this.apiService.delete<UpdatedStatus>(`/friends/${profileId}`, {});
  }

  private apiService = inject(WebApiService);
  private loggerService = inject(LoggerService);
  private logger = this.loggerService.getLogger('FriendManagerService');
  sendAddFriend(userId: string): Observable<UpdatedStatus> {
    return this.apiService.post<UpdatedStatus>(
      `/contact-requests/${userId}`,
      {},
    );
  }

  acceptFriendRequestFromUser(publicId: string): Observable<UpdatedStatus> {
    return this.apiService.post<UpdatedStatus>(
      `/contact-requests/user/${publicId}/accept`,
      {},
    );
  }
  blockUser(targetUserId: string): Observable<UpdatedStatus> {
    return this.apiService.post<UpdatedStatus>(`/blocks/${targetUserId}`, {});
  }

  unblockUser(targetUserId: string): Observable<UpdatedStatus> {
    return this.apiService.delete<UpdatedStatus>(`/blocks/${targetUserId}`, {});
  }


  getConversationByUser(targetUserId: string): Observable<ConversationPreview> {
    return this.apiService.get<ConversationPreview>(`/conversations/user/${targetUserId}`, {});
  }

  createConversationWithUser(targetUserId: string): Observable<ConversationPreview> {
    return this.apiService.post<ConversationPreview>(`/conversations?userId=${targetUserId}`, {});
  }

  getPendingRequests(): Observable<FriendRequestList> {
    return this.apiService.get<FriendRequestList>(
      `/contact-requests`,
    );
  }
}
