import { inject, Injectable } from "@angular/core";
import { WebSocketService } from "../../../core/socket/websocket.service";
import { IUserPresence } from "../../chat/service/chat-api.service";

@Injectable({
  providedIn: 'root'
})
export class UserPresenceObservableService {
  private wsService = inject(WebSocketService);

  status(userId: string) {
    return this.wsService.subscribe<IUserPresence>("/topic/presence.change" + userId);
  }

}
