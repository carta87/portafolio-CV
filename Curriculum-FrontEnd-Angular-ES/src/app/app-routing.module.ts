import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { PrincipalComponent } from './module/principal/principal.component'
import { PortafolioComponent } from './core/components/portafolio/portafolio.component';

const routes: Routes = [
  {
    path: 'CV-CarlosTafur',
    component:PrincipalComponent
  },
  {
    path: 'Portafolio',
    component:PortafolioComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
