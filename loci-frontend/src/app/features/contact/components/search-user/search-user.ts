import { Component, inject, signal } from '@angular/core';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { debounceTime, distinctUntilChanged, finalize, switchMap, tap } from 'rxjs';
import { UserSearchItem } from '../../models/contact.model';
import { SearchUserService } from '../../services/search-user.service';

@Component({
  selector: 'app-search-user',
  imports: [ReactiveFormsModule],
  templateUrl: './search-user.html',
  styleUrl: './search-user.css',
})
export class SearchUser {

  private searchService = inject(SearchUserService);

  searchControl = new FormControl('', { nonNullable: true });
  users = signal<UserSearchItem[]>([]);
  loading = signal(false);

  /* expose a signal that reacts to search text */
  private search$ = this.searchControl.valueChanges.pipe(
    debounceTime(300),
    distinctUntilChanged(),
    tap(() => this.loading.set(true)),
    switchMap(q => this.searchService.search(q).pipe(
      finalize(() => this.loading.set(false))
    ))
  );

  constructor() {

    /* single allowed subscribe (no takeUntil needed) – feeds the signal */

    this.search$.subscribe(res => this.users.set(res.content));

    this.searchService.search(this.searchControl.getRawValue())
      .subscribe({
        next: (u) => this.users.set(u.content),
        complete: () => this.loading.set(false)
      })
  }

  trackById(_: number, u: UserSearchItem): string { return u.id; }

  onAddFriend(user: UserSearchItem): void {
    this.searchService.addFriend(user.id).subscribe(() => {
      user.friendshipStatus = 'pending';
      /* re-trigger signal so button flips instantly */
      this.users.set([...this.users()]);
    });
  }

}
