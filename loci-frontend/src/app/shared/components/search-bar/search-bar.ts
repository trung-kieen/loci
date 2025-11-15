import { JsonPipe } from '@angular/common';
import {
  Component,
  ChangeDetectionStrategy,
  OnInit,
  OnDestroy,
  input,
  output,
  signal,
  computed
} from '@angular/core';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { Subscription } from 'rxjs';
import { debounceTime, distinctUntilChanged } from 'rxjs/operators';

@Component({
  selector: 'app-search-bar',
  standalone: false,
  templateUrl: './search-bar.html',
  styleUrls: ['./search-bar.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SearchBar implements OnInit, OnDestroy {
  // Inputs
  placeholder = input<string>('Search...');
  showCancel = input<boolean>(false);
  autocompleteItems = input<any[]>([]);

  // Outputs
  searchQuery = output<string>();   // debounced 300ms via FormControl
  itemSelected = output<any>();
  canceled = output<void>();

  // Reactive FormControl
  readonly searchControl = new FormControl<string>('', { nonNullable: true });

  private readonly query = signal<string>('');
  readonly showClearButton = computed(() => this.query().trim().length > 0);

  public readonly isFocused = signal<boolean>(false);
  private readonly highlightedIndex = signal<number | null>(null);

  readonly autocompleteOpen = computed(
    () =>
      this.isFocused() &&
      this.query().trim().length > 0 &&
      (this.autocompleteItems()?.length ?? 0) > 0
  );

  readonly inputId = `search-input-${Math.random().toString(36).slice(2)}`;
  readonly autocompleteListId = `search-autocomplete-${Math.random()
    .toString(36)
    .slice(2)}`;

  private valueChangesSub?: Subscription;

  // Lifecycle
  ngOnInit(): void {
    this.valueChangesSub = this.searchControl.valueChanges
      .pipe(debounceTime(300), distinctUntilChanged())
      .subscribe(value => {
        const v = value ?? '';
        this.query.set(v);
        this.searchQuery.emit(v);
        this.highlightedIndex.set(null);
      });
  }

  ngOnDestroy(): void {
    this.valueChangesSub?.unsubscribe();
  }

  // UI Handlers
  onFocus(): void {
    this.isFocused.set(true);
  }

  onBlur(): void {
    // Timeout allows click/mousedown on dropdown items before closing
    setTimeout(() => {
      this.isFocused.set(false);
      this.highlightedIndex.set(null);
    }, 100);
  }

  onClearClicked(): void {
    this.searchControl.setValue('');
    this.query.set('');
    this.highlightedIndex.set(null);
  }

  onCancelClicked(): void {
    this.onClearClicked();
    this.canceled.emit();
  }

  onKeydown(event: KeyboardEvent): void {
    const items = this.autocompleteItems() ?? [];
    const length = items.length;

    if (!length) {
      if (event.key === 'Escape') {
        this.highlightedIndex.set(null);
      }
      return;
    }

    const current = this.highlightedIndex();

    if (event.key === 'ArrowDown') {
      event.preventDefault();
      const next = current === null ? 0 : (current + 1) % length;
      this.highlightedIndex.set(next);
    } else if (event.key === 'ArrowUp') {
      event.preventDefault();
      const prev =
        current === null ? length - 1 : (current - 1 + length) % length;
      this.highlightedIndex.set(prev);
    } else if (event.key === 'Enter') {
      if (current !== null && items[current] !== undefined) {
        event.preventDefault();
        this.selectItem(items[current], current);
      }
    } else if (event.key === 'Escape') {
      this.highlightedIndex.set(null);
    }
  }

  onItemMouseEnter(index: number): void {
    this.highlightedIndex.set(index);
  }

  onItemMouseDown(event: MouseEvent, item: any, index: number): void {
    // Prevent input blur before selection
    event.preventDefault();
    this.selectItem(item, index);
  }

  // ========== Helpers ==========
  private selectItem(item: any, index: number): void {
    const label = this.getItemLabel(item);
    this.searchControl.setValue(label);
    this.query.set(label);
    this.itemSelected.emit(item);
    this.highlightedIndex.set(index);
    this.isFocused.set(false);
  }

  trackByItem = (index: number, item: any) => item?.id ?? item;

  getItemLabel(item: any): string {
    if (!item) {
      return '';
    }
    if (typeof item === 'string') {
      return item;
    }
    return item.label ?? item.name ?? item.title ?? '';
  }

  // Exposed for template binding (read-only)
  highlightedIndexSignal = this.highlightedIndex;
  autocompleteOpenSignal = this.autocompleteOpen;
}
