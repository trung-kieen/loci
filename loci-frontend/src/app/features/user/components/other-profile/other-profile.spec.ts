import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OtherProfile } from './other-profile';

describe('OtherProfile', () => {
  let component: OtherProfile;
  let fixture: ComponentFixture<OtherProfile>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OtherProfile]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OtherProfile);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
