import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule, NgOptimizedImage } from '@angular/common';
import { LucideAngularModule, Check, X, Search, Send } from 'lucide-angular';

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

@NgModule({
  imports: [
    CommonModule,
    ReactiveFormsModule,
    NgOptimizedImage,
    MatInputModule,
    BrowserModule,
    LucideAngularModule.pick({ Check, Search, X, Send }),
    ErrorCardComponent
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
    Demo,
    Badge,
    Avatar,
    Button,
    Loading,
    Input
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
    ErrorCardComponent
  ]
})
export class SharedModule { }

