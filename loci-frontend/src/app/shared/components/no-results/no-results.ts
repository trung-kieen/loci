import {
  ChangeDetectionStrategy,
  Component,
  computed,
  input,
  output,
} from '@angular/core';

@Component({
  selector: 'app-no-results',
  templateUrl: './no-results.html',
  styleUrls: ['./no-results.css'],
  standalone: false,
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class NoResultsComponent {
  readonly message = input<string>('No results found');
  readonly icon = input<string>('Inbox');
  readonly actionText = input<string | null>(null);

  readonly actionClicked = output<void>();

  // Computed icon name (fallback + normalization)
  readonly iconName = computed(() => this.icon()?.trim() || 'Inbox');

  onActionClick(): void {
    this.actionClicked.emit();
  }
}
