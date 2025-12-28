import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CertificacionesMisionTicComponent } from './certificaciones-mision-tic.component';

describe('CertificacionesMisionTicComponent', () => {
  let component: CertificacionesMisionTicComponent;
  let fixture: ComponentFixture<CertificacionesMisionTicComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CertificacionesMisionTicComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CertificacionesMisionTicComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
