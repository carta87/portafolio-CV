import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CertificacionesOtrasComponent } from './certificaciones-otras.component';

describe('CertificacionesOtrasComponent', () => {
  let component: CertificacionesOtrasComponent;
  let fixture: ComponentFixture<CertificacionesOtrasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CertificacionesOtrasComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CertificacionesOtrasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
