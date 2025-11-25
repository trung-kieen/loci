import { NgModule } from "@angular/core";
import { UserRoutingModule } from "./user.routes";
import { MyProfileService } from "./services/my-profile.service";
import { OtherProfileService } from "./services/other-profile.service";

@NgModule({
  // declarations: [MyProfile],
  imports: [UserRoutingModule],
  providers: [MyProfileService, OtherProfileService],
})
export class UserModule {

}
