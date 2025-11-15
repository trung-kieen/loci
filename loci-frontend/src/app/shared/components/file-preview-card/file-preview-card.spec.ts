import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FilePreviewCard } from './file-preview-card';

describe('FilePreviewCard', () => {
  let component: FilePreviewCard;
  let fixture: ComponentFixture<FilePreviewCard>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FilePreviewCard]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FilePreviewCard);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
