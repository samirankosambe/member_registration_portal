import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GetClaimsComponent } from './get-claims.component';

describe('GetClaimsComponent', () => {
  let component: GetClaimsComponent;
  let fixture: ComponentFixture<GetClaimsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GetClaimsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GetClaimsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
