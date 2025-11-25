import { inject, Injectable } from "@angular/core";
import { WebApiService } from "../../../api/web-api.service";
import { delay, Observable, of } from "rxjs";
import { Page } from "../../../core/model/page";
import { UserSearchItem } from "../models/contact.model";

// const FAKE_DB: UserSearchItem[] = [
//   { id: '1', name: 'Jessica Brown', username: 'jessicabrown', email: 'jessica.brown@email.com', avatarSeed: '3456', friendshipStatus: 'none' },
//   { id: '2', name: 'Ryan Miller', username: 'ryanmiller', email: 'ryan.miller@company.com', avatarSeed: '6789', friendshipStatus: 'none' },
//   { id: '3', name: 'Samantha Lee', username: 'samanthalee', email: 'samantha.lee@startup.io', avatarSeed: '9012', friendshipStatus: 'none' },
//   { id: '4', name: 'Kevin Wong', username: 'kevinwong', email: 'kevin.wong@techcorp.com', avatarSeed: '3457', friendshipStatus: 'pending' },
//   { id: '5', name: 'Nina Patel', username: 'ninapatel', email: 'nina.patel@design.studio', avatarSeed: '7890', friendshipStatus: 'none' },
// ];
@Injectable()
export class SearchUserService {
  private apiService = inject(WebApiService);

  search(query: string): Observable<Page<UserSearchItem>> {

    // const params = new HttpParams().set('q', query.trim());
    // return this.apiService.get<UserSearchItem[]>('/users', params);
    return this.apiService.get<Page<UserSearchItem>>("/users/search?q=" + query);
  }


  addFriend(userId: string): Observable<void> {
    return this.apiService.post<void>(`/users/${userId}/friend`, {});
  }


  // Mock
  // search(query: string): Observable<UserSearchItem[]> {
  //   const q = query.trim().toLowerCase();
  //   const filtered = FAKE_DB.filter(u =>
  //     u.name.toLowerCase().includes(q) ||
  //     u.username.toLowerCase().includes(q) ||
  //     u.email.toLowerCase().includes(q)
  //   );
  //   return of(filtered).pipe(delay(400)); // tiny delay to feel real
  // }

  // addFriend(userId: string): Observable<void> {
  //   const target = FAKE_DB.find(u => u.id === userId);
  //   if (target) target.friendshipStatus = 'pending';
  //   return of(undefined).pipe(delay(300));
  // }

}
