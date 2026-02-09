import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MessageInput } from './message-input';

describe('MessageInput', () => {
  let component: MessageInput;
  let fixture: ComponentFixture<MessageInput>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MessageInput]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MessageInput);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
