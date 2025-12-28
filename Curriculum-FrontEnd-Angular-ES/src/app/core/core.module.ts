import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

import { HeaderComponent } from './components/header/header.component';
import { AsideComponent } from './components/aside/aside.component';
import { ContentComponent } from './components/content/content.component';
import { PortafolioComponent } from './components/portafolio/portafolio.component';
import { CertificacionesMisionTicComponent } from './components/portafolio/certificaciones-mision-tic/certificaciones-mision-tic.component';
import { CertificacionesPlatziComponent } from './components/portafolio/certificaciones-platzi/certificaciones-platzi.component';
import { CertificacionesOtrasComponent } from './components/portafolio/certificaciones-otras/certificaciones-otras.component';

@NgModule({
    declarations:[
    HeaderComponent,
    AsideComponent,
    ContentComponent,
    PortafolioComponent,
    CertificacionesMisionTicComponent,
    CertificacionesPlatziComponent,
    CertificacionesOtrasComponent
  ],
  exports:[
    HeaderComponent,
    AsideComponent,
    ContentComponent,
    CommonModule
  ],
    imports: [
        CommonModule,
        RouterModule,
    ]
})

export class CoreModule { }