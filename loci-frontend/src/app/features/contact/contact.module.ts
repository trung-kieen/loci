import { NgModule } from "@angular/core";
import { ContactRoutingModule } from "./contact.routes";
import { SearchUserService } from "./services/search-user.service";

@NgModule({
  imports: [ContactRoutingModule],
  providers: [SearchUserService],
})
export class ContactModule {

}
