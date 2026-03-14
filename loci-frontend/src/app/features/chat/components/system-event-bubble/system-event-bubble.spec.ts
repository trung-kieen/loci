import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SystemEventBubble } from './system-event-bubble';

describe('SystemEventBubble', () => {
  let component: SystemEventBubble;
  let fixture: ComponentFixture<SystemEventBubble>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SystemEventBubble]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SystemEventBubble);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
