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

import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

// features/chat/chat-routing.module.ts
const routes: Routes = [
  {
    path: '',
    loadComponent: () =>
      import('./components/chat-layout/chat-layout').then((m) => m.ChatLayout),
    children: [
      {
        path: '', // /chat - empty state (no conversation selected)
        pathMatch: 'full',
        loadComponent: () =>
          import('./components/empty-chat/empty-chat').then((m) => m.EmptyChat),
      },
      {
        // one to one need to use conversationId to identify
        path: 'one/:conversationId', // /chat/one/123
        loadComponent: () =>
          import(
            './components/direct-conversation/direct-conversation'
          ).then((m) => m.DirectConversation),
            runGuardsAndResolvers: 'paramsChange'
      },
      {
        // group conversation can u the
        path: 'group/:conversationId', // /chat/group/456
        loadComponent: () =>
          import('./components/group-conversation/group-conversation').then(
            (m) => m.GroupConversation,
          ),
            runGuardsAndResolvers: 'paramsChange'
      },
      {
        path: 'group/:conversationId/profile', // /chat/group/456/profile
        loadComponent: () =>
          import('./components/group-profile/group-profile').then(
            (m) => m.GroupProfile,
          ),
      },
      {
        path: 'media/:type/:id', // /chat/media/image/789
        loadComponent: () =>
          import('./components/media-viewer/media-viewer').then(
            (m) => m.MediaViewer,
          ),
      },
      {
        path: 'create-group', // /chat/create-group
        loadComponent: () =>
          import('./components/create-group/create-group').then(
            (m) => m.CreateGroup,
          ),
      },
    ],
  },
];
@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ChatRoutingModule {}
