import { NgModule } from "@angular/core";
import { ChatRoutingModule } from "./chat.routes";
import { RouteReuseStrategy } from "@angular/router";
import { ConversationReuseStrategy } from "./components/chat-list/conversation-reuse-strategy";

@NgModule({
  imports: [ChatRoutingModule],
  providers: [
    {
      provide: RouteReuseStrategy,
      useClass: ConversationReuseStrategy
    }

  ]
})
export class ChatModule {


}
