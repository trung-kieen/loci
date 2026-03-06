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
import { Observable } from 'rxjs';
import { HttpParams } from '@angular/common/http';
import { LoggerService } from '../../../core/services/logger.service';
import { WebApiService } from '../../../core/api/web-api.service';
import { IFriendRequestList, FriendshipStatus, BlockedUserList } from '../models/contact.model';
import { IChatReference, IFriendList } from '../../chat/models/chat.model';
import { IUpdatedStatus } from '../../user/models/user.model';

@Injectable({
  providedIn: 'root',
})
export class FriendManagerService {
  private apiService = inject(WebApiService);
  private loggerService = inject(LoggerService);
  private logger = this.loggerService.getLogger('FriendManagerService');

  searchFriend(query: string): Observable<IFriendList> {
    return this.apiService.get<IFriendList>(`/friends?q=${query}`);
  }

  unsendFriendRequest(profileId: string): Observable<IUpdatedStatus> {
    return this.apiService.delete<IUpdatedStatus>(
      `/contact-requests/${profileId}`,
      {},
    );
  }




  denyFriendRequest(profileId: string): Observable<IUpdatedStatus> {
    return this.apiService.post<IUpdatedStatus>(
      `/contact-requests/user/${profileId}/reject`,
      {},
    );
  }
  unfriendUser(profileId: string) {
    return this.apiService.delete<IUpdatedStatus>(`/friends/${profileId}`, {});
  }

  sendAddFriend(userId: string): Observable<IUpdatedStatus> {
    return this.apiService.post<IUpdatedStatus>(
      `/contact-requests/${userId}`,
      {},
    );
  }

  acceptFriendRequestFromUser(publicId: string): Observable<IUpdatedStatus> {
    return this.apiService.post<IUpdatedStatus>(
      `/contact-requests/user/${publicId}/accept`,
      {},
    );
  }
  blockUser(targetUserId: string): Observable<IUpdatedStatus> {
    return this.apiService.post<IUpdatedStatus>(`/blocks/${targetUserId}`, {});
  }

  unblockUser(targetUserId: string): Observable<IUpdatedStatus> {
    return this.apiService.delete<IUpdatedStatus>(
      `/blocks/${targetUserId}`,
      {},
    );
  }


  getBlockedUser(page = 0, size= 50): Observable<BlockedUserList> {
    const params = new HttpParams().set("page", page.toString()).set("size", size.toString());

    return this.apiService.get<BlockedUserList>("/blocks", { params });

    // const params = new HttpParams()
    //   .set('page', page.toString())
    //   .set('limit', limit.toString());

    // return this.apiService.get<BlockedUsersResponseDto>("/blocked", { params }).pipe(
    //   catchError(error => {
    //     console.error('Failed to fetch blocked users:', error);
    //     return throwError(() => new Error('Unable to load blocked users. Please try again.'));
    //   })
    // );

    // return null;
  }


  reportUser(userId: string, reason: string): Observable<void> {
    throw new Error('Method not implemented.');
  }


  getConversationByUser(targetUserId: string): Observable<IChatReference> {
    return this.apiService.get<IChatReference>(
      `/conversations/user/${targetUserId}`,
      {},
    );
  }

  createConversationWithUser(targetUserId: string): Observable<IChatReference> {
    return this.apiService.post<IChatReference>(
      `/conversations?userId=${targetUserId}`,
      {},
    );
  }

  getPendingRequests(): Observable<IFriendRequestList> {
    return this.apiService.get<IFriendRequestList>(`/contact-requests`);
  }

  public static isFriends = (status: FriendshipStatus) => {
    return status === FriendshipStatus.FRIENDS;
  };

  public static canDenyRequest = (status: FriendshipStatus) => {
    return status === FriendshipStatus.FRIEND_REQUEST_RECEIVED;
  };

  public static canUnsendRequest = (status: FriendshipStatus) => {
    return status === FriendshipStatus.FRIEND_REQUEST_SENT;
  };

  public static canAddFriend = (status: FriendshipStatus) => {
    return (
      status === FriendshipStatus.NOT_CONNECTED ||
      status === FriendshipStatus.NOT_DETERMINED
    );
  };
  public static canAcceptRequest = (status: FriendshipStatus) => {
    return status === FriendshipStatus.FRIEND_REQUEST_RECEIVED;
  };
  public static canMessage = (status: FriendshipStatus) => {
    return (
      status === FriendshipStatus.FRIENDS ||
      status === FriendshipStatus.FRIEND_REQUEST_RECEIVED
    );
  };
  public static canBlock = (status: FriendshipStatus) => {
    return (
      status !== FriendshipStatus.BLOCKED &&
      status !== FriendshipStatus.BLOCKED_BY
    );
  };
  public static isBlocked = (status: FriendshipStatus) => {
    return status === FriendshipStatus.BLOCKED;
  };

  public static isBlockedBy = (status: FriendshipStatus) => {
    return status === FriendshipStatus.BLOCKED_BY;
  };
}
