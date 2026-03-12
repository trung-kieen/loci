import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MessageStateIndicator } from './message-state-indicator';

describe('MessageStateIndicator', () => {
  let component: MessageStateIndicator;
  let fixture: ComponentFixture<MessageStateIndicator>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MessageStateIndicator]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MessageStateIndicator);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
