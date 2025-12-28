import {
  computed,
  DestroyRef,
  inject,
  Injectable,
  signal,
} from '@angular/core';
import { PublicProfile, UpdatedStatus } from '../models/public-profile.model';
import { WebApiService } from '../../../core/api/web-api.service';
import { catchError, EMPTY, finalize, Observable, tap, throwError } from 'rxjs';
import { FriendshipStatus } from '../../contact/models/contact.model';
import { FriendManagerService } from '../../contact/services/friend-manager.service';
import { HttpErrorResponse, HttpStatusCode } from '@angular/common/http';
import { ProblemDetail } from '../../../core/error-handler/problem-detail';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { ConversationPreview } from '../../chat/models/chat.model';
import { LoggerService } from '../../../core/services/logger.service';
@Injectable()
export class PublicProfileService {

  private loggerService = inject(LoggerService);
  private logger = this.loggerService.getLogger("PublicProfileService ");

  private friendManager = inject(FriendManagerService);
  private destroyRef = inject(DestroyRef);
  private apiService = inject(WebApiService);

  private _profile = signal<PublicProfile | null>(null);
  private _isLoading = signal<boolean>(true);
  private _error = signal<string | null>(null);

  private _profileId = signal<string | null>(null);

  public readonly profile = this._profile.asReadonly();
  public readonly isLoading = this._isLoading.asReadonly();
  public readonly error = this._error.asReadonly();
  readonly profileId = this._profileId.asReadonly();

  readonly connectionStatusText = computed(() => {
    const status = this.profile()?.connectionStatus;
    const statusMap: Record<FriendshipStatus, string> = {
      not_connected: 'Add Friend',
      friend_request_sent: 'Request Sent',
      friend_request_received: 'Accept Request',
      friends: 'Friends',
      blocked: 'Blocked',
      blocked_by: 'Unavailable',
      not_determined: 'Add Friend',
    };

    return status ? statusMap[status] : 'Add friend';
  });

  readonly connectionStatusIcon = computed(() => {
    const status = this.profile()?.connectionStatus;
    const iconMap: Record<FriendshipStatus, string> = {
      not_connected: 'fa-user-plus',
      friend_request_sent: 'fa-clock',
      friend_request_received: 'fa-user-check',
      friends: 'fa-user-check',
      blocked: 'fa-ban',
      blocked_by: 'fa-ban',
      not_determined: 'fa-user-plus',
    };
    return status ? iconMap[status] : 'fa-user-plus';
  });

  readonly canAddFriend = computed(() => {
    const status = this.profile()?.connectionStatus;
    if (!status) return
    return FriendManagerService.canAddFriend(status);
  });


  readonly isFriends = computed(() => {
    const status = this.profile()?.connectionStatus;
    if (!status) return
    return FriendManagerService.isFriends(status);
  });


  readonly canAcceptRequest = computed(() => {
    const status = this._profile()?.connectionStatus;
    if (!status) return
    return FriendManagerService.canAcceptRequest(status);
  });

  readonly canMessage = computed(() => {
    const status = this._profile()?.connectionStatus;
    if (!status) return
    return FriendManagerService.canMessage(status);
  });
  readonly canUnsendRequest = computed(() => {
    const status = this._profile()?.connectionStatus;
    if (!status) return
    return FriendManagerService.canUnsendRequest(status);
  });

  readonly canBlock = computed(() => {
    const status = this._profile()?.connectionStatus;
    if (!status) return
    return FriendManagerService.canBlock(status);
  });

  readonly isBlocked = computed(() => {
    const status = this._profile()?.connectionStatus;
    if (!status) return
    return FriendManagerService.isBlocked(status);
  });

  // isActiveRecently remains unchanged
  readonly isActiveRecently = computed(() => {
    const lastActive = this._profile()?.lastActive;

    if (!lastActive) return false;
    const fiveMinutesAgo = new Date(Date.now() - 5 * 60 * 1000);
    return new Date(lastActive) > fiveMinutesAgo;
  });

  getProfile(userId: string): Observable<PublicProfile> {
    return this.apiService.get<PublicProfile>('/users/' + userId);
  }




  addFriend(): Observable<UpdatedStatus> {
    const profileId = this._profileId();
    if (!profileId) {
      return throwError(() => new Error('Profile ID not set'));
    }

    this._isLoading.set(true);
    this._error.set(null);

    return this.friendManager.sendAddFriend(profileId).pipe(
      tap({
        next: (updatedStatus) => {
          this._profile.update((profile) =>
            profile
              ? { ...profile, connectionStatus: updatedStatus.status }
              : null,
          );
        },
      }),
      catchError((err: HttpErrorResponse) => {
        const problem = err.error as ProblemDetail;
        console.error('Failed to send friend request:', problem);
        this._error.set(problem.detail);
        // Refresh profile to ensure UI consistency
        this.getProfile(profileId).subscribe();
        return throwError(() => err);
      }),
      finalize(() => {
        this._isLoading.set(false);
      }),
      takeUntilDestroyed(this.destroyRef),
    );
  }


  public requestMessage(): Observable<ConversationPreview> {
    const profileId = this._profileId();
    if (profileId == null) {
      return EMPTY;
    }
    this.logger.info("Request for conversation with user {}", profileId);
    return this.friendManager.getConversationByUser(profileId).pipe(
      catchError((error) => {
        if (error instanceof HttpErrorResponse) {
          if (error.status === HttpStatusCode.NotFound) {
            this.logger.info("Not found conversation request to create converstaion with user {}", profileId);
            return this.friendManager.createConversationWithUser(profileId);
          }
        }

        this.logger.error("Unknow error to get conversation {}", error);
        return EMPTY;
      })
    );


  }

  public setProfileId(profileId: string) {
    this._profileId.set(profileId);
  }
  public loadProfile(): void {
    const profileId = this._profileId();
    if (profileId == null) {
      return;
    }
    this._isLoading.set(true);
    this._error.set(null);

    this.getProfile(profileId).subscribe({
      next: (p) => {
        this._profile.set(p);
        this._isLoading.set(false);
      },
      error: (err: HttpErrorResponse) => {
        const problem = err.error as ProblemDetail;
        this._error.set(problem.detail);
        this._isLoading.set(false);
      },
      complete: () => this._isLoading.set(false),
    });
  }

  public acceptRequest(): Observable<UpdatedStatus> {
    const profileId = this._profileId();
    if (!profileId) {
      return throwError(() => new Error('Profile ID not set'));
    }

    return this.friendManager.acceptFriendRequestFromUser(profileId).pipe(
      tap({
        next: (updatedStatus) => {
          this._profile.update((p) => {
            return p ? { ...p, connectionStatus: updatedStatus.status } : null;
          });
        },
      }),
      catchError((err: HttpErrorResponse) => {
        const problem = err.error as ProblemDetail;
        console.error('Failed to accept friend request:', problem);
        this._error.set(problem.detail);
        this.getProfile(profileId).subscribe();
        return throwError(() => err);
      }),
      finalize(() => {
        this._isLoading.set(false);
      }),
      takeUntilDestroyed(this.destroyRef),
    );
  }

  blockUser(): Observable<UpdatedStatus> {
    const profileId = this._profileId();
    if (!profileId) {
      return throwError(() => new Error('Profile ID not set'));
    }

    this._isLoading.set(true);
    this._error.set(null);

    return this.friendManager.blockUser(profileId).pipe(
      tap({
        next: (updatedStatus) => {
          this._profile.update((profile) =>
            profile
              ? { ...profile, connectionStatus: updatedStatus.status }
              : null,
          );
        },
      }),
      catchError((err: HttpErrorResponse) => {
        const problem = err.error as ProblemDetail;
        console.error('Failed to block user:', problem);
        this._error.set(problem.detail);
        this.getProfile(profileId).subscribe();
        return throwError(() => err);
      }),
      finalize(() => {
        this._isLoading.set(false);
      }),
      takeUntilDestroyed(this.destroyRef),
    );
  }

  unblockUser(): Observable<UpdatedStatus> {
    const profileId = this._profileId();
    if (!profileId) {
      return throwError(() => new Error('Profile ID not set'));
    }

    this._isLoading.set(true);
    this._error.set(null);

    return this.friendManager.unblockUser(profileId).pipe(
      tap({
        next: (updatedStatus) => {
          this._profile.update((profile) =>
            profile
              ? { ...profile, connectionStatus: updatedStatus.status }
              : null,
          );
        },
      }),
      catchError((err: HttpErrorResponse) => {
        const problem = err.error as ProblemDetail;
        console.error('Failed to unblock user:', problem);
        this._error.set(problem.detail);
        this.getProfile(profileId).subscribe();
        return throwError(() => err);
      }),
      finalize(() => {
        this._isLoading.set(false);
      }),
      takeUntilDestroyed(this.destroyRef),
    );
  }
  unfriend(): Observable<UpdatedStatus> {
    const profileId = this._profileId();
    if (!profileId) {
      return throwError(() => new Error('Profile ID not set'));
    }

    this._isLoading.set(true);
    this._error.set(null);

    return this.friendManager.unfriendUser(profileId).pipe(
      tap({
        next: (updatedStatus) => {
          this._profile.update((profile) =>
            profile
              ? { ...profile, connectionStatus: updatedStatus.status }
              : null,
          );
        },
      }),
      catchError((err: HttpErrorResponse) => {
        const problem = err.error as ProblemDetail;
        console.error('Failed to unfriend user:', problem);
        this._error.set(problem.detail);
        this.getProfile(profileId).subscribe();
        return throwError(() => err);
      }),
      finalize(() => {
        this._isLoading.set(false);
      }),
      takeUntilDestroyed(this.destroyRef),
    );
  }

  denyRequest(): Observable<UpdatedStatus> {
    const profileId = this._profileId();
    if (!profileId) return throwError(() => new Error('Profile ID not set'));

    this._isLoading.set(true);
    this._error.set(null);

    return this.friendManager.denyFriendRequest(profileId).pipe(
      tap({
        next: (updatedStatus) => {
          this._profile.update((profile) =>
            profile
              ? { ...profile, connectionStatus: updatedStatus.status }
              : null,
          );
        },
      }),
      catchError((err: HttpErrorResponse) => {
        const problem = err.error as ProblemDetail;
        console.error('Failed to deny friend request:', problem);
        this._error.set(problem.detail);
        this.getProfile(profileId).subscribe();
        return throwError(() => err);
      }),
      finalize(() => this._isLoading.set(false)),
      takeUntilDestroyed(this.destroyRef),
    );
  }

  unsendFriendRequest(): Observable<UpdatedStatus> {
    const profileId = this._profileId();
    if (!profileId) return throwError(() => new Error('Profile ID not set'));

    this._isLoading.set(true);
    this._error.set(null);

    return this.friendManager.unsendFriendRequest(profileId).pipe(
      tap({
        next: (updatedStatus) => {
          this._profile.update((profile) =>
            profile
              ? { ...profile, connectionStatus: updatedStatus.status }
              : null,
          );
        },
      }),
      catchError((err: HttpErrorResponse) => {
        const problem = err.error as ProblemDetail;
        console.error('Failed to unsend friend request:', problem);
        this._error.set(problem.detail);
        this.getProfile(profileId).subscribe();
        return throwError(() => err);
      }),
      finalize(() => this._isLoading.set(false)),
      takeUntilDestroyed(this.destroyRef),
    );
  }
}
