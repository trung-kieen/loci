import { NgModule } from "@angular/core";
import { UserRoutingModule } from "./user.routes";
import { ProfileService } from "./services/profile.service";
import { SharedModule } from "../../shared/shared.module";
import { MyProfile } from "./components/my-profile/my-profile";

@NgModule({
  // declarations: [MyProfile],
  imports: [UserRoutingModule],
  providers: [ProfileService],
})
export class UserModule {

}
