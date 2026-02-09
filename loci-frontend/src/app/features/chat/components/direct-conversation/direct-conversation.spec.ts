import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OneToOneConversation } from './one-to-one-conversation';

describe('OneToOneConversation', () => {
  let component: OneToOneConversation;
  let fixture: ComponentFixture<OneToOneConversation>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OneToOneConversation]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OneToOneConversation);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
