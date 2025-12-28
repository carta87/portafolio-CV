import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CertificacionesPlatziComponent } from './certificaciones-platzi.component';

describe('CertificacionesPlatziComponent', () => {
  let component: CertificacionesPlatziComponent;
  let fixture: ComponentFixture<CertificacionesPlatziComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CertificacionesPlatziComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CertificacionesPlatziComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
