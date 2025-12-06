import { Component, inject, OnInit } from '@angular/core';
import { FriendManagerService } from '../../services/friend-manager.service';

@Component({
  selector: 'app-friend-request',
  imports: [],
  templateUrl: './friend-request.html',
  styleUrl: './friend-request.css',
})
export class FriendRequest implements OnInit {
  private friendManager = inject(FriendManagerService);
  ngOnInit(): void {
    this.friendManager.getListRequestConnectContact();
  }


}
