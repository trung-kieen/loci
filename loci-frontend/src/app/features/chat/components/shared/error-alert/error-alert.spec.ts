import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ErrorAlert } from './error-alert';

describe('ErrorAlert', () => {
  let component: ErrorAlert;
  let fixture: ComponentFixture<ErrorAlert>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ErrorAlert]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ErrorAlert);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
