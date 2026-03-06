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

import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule, JsonPipe, NgOptimizedImage } from '@angular/common';
import {
  LucideAngularModule,
  Check,
  X,
  Search,
  Send,
  Inbox,
  FileText,
  File,
} from 'lucide-angular';

import {
  FormsModule,
  NG_VALUE_ACCESSOR,
  ReactiveFormsModule,
} from '@angular/forms';
import { Avatar } from './components/avatar/avatar';
import { MatInputModule } from '@angular/material/input';
import { Demo } from './components/demo/demo';
import { Badge } from './components/badge/badge';
import { Button } from './components/button/button';
import { Loading } from './components/loading/loading';
import { Input } from './components/input/input';
import { ErrorCardComponent } from '../core/components/error-card/error-card';
import { SearchBar } from './components/search-bar/search-bar';
import { NoResultsComponent } from './components/no-results/no-results';
import { FilePreviewCard } from './components/file-preview-card/file-preview-card';
import { Modal } from './components/modal/modal';
import { HeaderBar } from './components/header-bar/header-bar';
import { BottomNav } from './components/bottom-nav/bottom-nav';
import { NotificationService } from './services/notification.service';
import { ToastNotification } from './components/toast-notification/toast-notification';
import { ToastContainer } from './components/toast-container/toast-container';
import { RouterModule } from '@angular/router';
import { Sidebar } from './components/sidebar/sidebar';

@NgModule({
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    JsonPipe,
    NgOptimizedImage,
    MatInputModule,
    RouterModule,
    CommonModule,
    LucideAngularModule.pick({ Check, Search, X, Send, Inbox, FileText, File }),
    ErrorCardComponent,
    Modal,
    HeaderBar,
    ToastNotification,
    ToastContainer,
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],

  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: Input,
      multi: true,
    },
    NotificationService,
  ],
  declarations: [
    BottomNav,
    Demo,
    Badge,
    Avatar,
    Button,
    Sidebar,
    Loading,
    Input,
    SearchBar,
    NoResultsComponent,
    FilePreviewCard,
  ],
  exports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    Demo,
    Avatar,
    Badge,
    Button,
    Loading,
    Input,
    ErrorCardComponent,
    SearchBar,
    NoResultsComponent,
    FilePreviewCard,
    Modal,
    HeaderBar,
    BottomNav,
    ToastNotification,
    ToastContainer,
    Sidebar,
  ],
})
export class SharedModule {}
