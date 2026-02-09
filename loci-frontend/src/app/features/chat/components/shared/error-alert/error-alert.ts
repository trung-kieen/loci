import { Component, signal, input, output, computed } from '@angular/core';
import { IChatError } from '../../../models/chat.model';


// export interface IErrorDisplay {
//   message: string;
//   description?: string;
//   type?: 'network' | 'validation' | 'genernal';
// }

@Component({
  selector: 'app-error-alert',
  imports: [],
  templateUrl: './error-alert.html',
  styleUrl: './error-alert.css',
})
export class ErrorAlert {
  error = input.required<IChatError | null>();
  dismiss = output();


  isValidationError = computed(() => {
    const err = this.error();
    if (err == null) return false;
    return err.type === 'validation';
  })


}

