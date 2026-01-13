import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { AuthGuard } from "../../common/guards/AuthGuard";


const routes: Routes = [
  {
    path: 'crear',
    canActivate: [AuthGuard],
    loadComponent: () => import('./create/create.component').then(c => c.CreateComponent)
  },
  {
    path: 'listar',
    canActivate: [AuthGuard],
    loadComponent: () => import('./get/get.component').then(c => c.GetComponent)
  },
  {
    path: 'editar/:id',
    canActivate: [AuthGuard],
    loadComponent:()=> import('./edit/edit.component').then(c => c.EditComponent)
  },
  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'listar'
  }
]

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class EstudianteRoutingModule{}
