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

import {
  ChangeDetectionStrategy,
  Component,
  computed,
  input,
  output,
} from '@angular/core';
import { Avatar } from '../avatar/avatar';
import { SharedModule } from '../../shared.module';
// TODO: wire in your real Avatar + Button + Icon modules
// import { AvatarComponent } from 'src/app/shared/ui/avatar/avatar.component';
// import { ButtonComponent } from 'src/app/shared/ui/button/button.component';
// import { LucideAngularModule } from 'lucide-angular';

export interface HeaderAvatar {
  src: string;
  alt: string;
}

export interface HeaderAction {
  icon: string;  // lucide icon name
  label: string; // used for aria-label
  action: string; // identifier emitted on click
}

@Component({
  selector: 'app-header-bar',
  standalone: true,
  imports: [SharedModule],
  // imports: [AvatarComponent, ButtonComponent, LucideAngularModule],
  templateUrl: './header-bar.html',
  styleUrls: ['./header-bar.css'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class HeaderBar{
  // Inputs
  readonly avatar = input<HeaderAvatar | null>(null);
  readonly title = input<string>('');
  readonly subtitle = input<string | null>(null);
  readonly showBackButton = input<boolean>(false);
  readonly actions = input<HeaderAction[]>([]);

  // Outputs
  readonly backClicked = output<void>();
  readonly actionClicked = output<string>();

  // Derived state
  readonly hasAvatar = computed(() => !!this.avatar());
  readonly hasSubtitle = computed(() => !!this.subtitle());

  readonly displayActions = computed(() => {
    const list = this.actions() ?? [];
    if (list.length > 3) {
      // Soft validation: just trim to 3; log in dev
      if (typeof ngDevMode !== 'undefined' && ngDevMode) {
        // eslint-disable-next-line no-console
        console.warn(
          '[HeaderBarComponent] More than 3 actions provided; trimming to 3.',
        );
      }
    }
    return list.slice(0, 3);
  });

  onBackClicked(): void {
    this.backClicked.emit();
  }

  onActionClicked(actionId: string): void {
    this.actionClicked.emit(actionId);
  }
}
