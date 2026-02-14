import { Injectable } from "@angular/core";
import { ActivatedRouteSnapshot, BaseRouteReuseStrategy } from "@angular/router";

// Custom route reuse strategy
@Injectable()
export class ConversationReuseStrategy extends BaseRouteReuseStrategy {


  public override shouldReuseRoute(future: ActivatedRouteSnapshot, curr: ActivatedRouteSnapshot): boolean {
    // Don't reuse when conversation ID changes
    if (future.paramMap.get('conversationId') !== curr.paramMap.get('conversationId')) {
      return false;
    }
    return super.shouldReuseRoute(future, curr);
  }
}

