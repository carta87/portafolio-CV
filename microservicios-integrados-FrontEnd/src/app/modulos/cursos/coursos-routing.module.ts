import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';
import { CreateComponent } from './create/create.component';
import { SidebarComponent } from './sidebar/sidebar.component';
import { GetComponent } from './get/get.component';
import { EditComponent } from './edit/edit.component';
import { FiltroComponent } from './filtro/filtro.component';
import { InfoComponent } from './info/info.component';
import { AuthGuard } from '../../common/guards/AuthGuard';

const routes: Routes = [
  {
    path: '',
    component: SidebarComponent,
    canActivate: [AuthGuard],
    children: [
      { path: 'crear', component: CreateComponent },
      { path: 'editar', component: EditComponent },
      { path: 'editar/:id', component: EditComponent },
      { path: 'listar', component: GetComponent },
      { path: 'filtro', component: FiltroComponent },
      { path: 'info', component: InfoComponent }
    ],
  },
  {
    path: '',
    redirectTo: '/listar',
    pathMatch: 'full',
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class CoursosRoutingModule {}
