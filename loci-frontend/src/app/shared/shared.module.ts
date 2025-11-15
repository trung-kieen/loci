import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule, JsonPipe, NgOptimizedImage } from '@angular/common';
import { LucideAngularModule, Check, X, Search, Send, Inbox, FileText, File } from 'lucide-angular';

import { NG_VALUE_ACCESSOR, ReactiveFormsModule } from '@angular/forms';
import { Avatar } from './components/avatar/avatar';
import { MatInputModule } from '@angular/material/input';
import { Demo } from './components/demo/demo';
import { Badge } from './components/badge/badge';
import { BrowserModule } from '@angular/platform-browser';
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

@NgModule({
  imports: [
    CommonModule,
    ReactiveFormsModule,
    JsonPipe,
    NgOptimizedImage,
    MatInputModule,
    BrowserModule,
    LucideAngularModule.pick({ Check, Search, X, Send, Inbox, FileText, File }),
    ErrorCardComponent,
    Modal,
    HeaderBar
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],

  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: Input,
      multi: true,
    },
  ],
  declarations: [
    BottomNav,
    Demo,
    Badge,
    Avatar,
    Button,
    Loading,
    Input,
    SearchBar,
    NoResultsComponent,
    FilePreviewCard,
  ],
  exports: [
    CommonModule,
    ReactiveFormsModule,
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
    BottomNav
  ]
})
export class SharedModule { }

