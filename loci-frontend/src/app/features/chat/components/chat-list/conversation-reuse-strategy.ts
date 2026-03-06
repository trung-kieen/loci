/**
 * Copyright 2026 trung-kieen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

