import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { NgxPaginationModule } from 'ngx-pagination';
import Swal from 'sweetalert2';
import { CursoModel } from '../../../common/modelos/curso.model';
import { CursosService } from '../../../servicios/cursos/cursos.service';

@Component({
  selector: 'app-get',
  standalone: true,
  imports: [CommonModule, RouterLink, NgxPaginationModule],
  templateUrl: './get.component.html',
  styleUrl: './get.component.css',
})
export class GetComponent implements OnInit {
  
  page: number = 1;
  listaCourse: CursoModel[] = [];
  cursosService = inject(CursosService);

  ngOnInit(): void {
    this.getAllCorsos();
  }

  public getAllCorsos() {
    this.cursosService
    .getAllCursos(this.page - 1, 10, 'id', 'asc')
    .subscribe(data => {
      this.listaCourse = data.content
        .slice()
        .sort((a, b) => a.numberCourse - b.numberCourse);
    });
   }

  public delteCurso(id: number) {
    Swal.fire({
      title: '¿Está seguro de eliminar el registro?',
      confirmButtonText: 'Aceptar',
      confirmButtonColor: '#28a745',
      cancelButtonColor: 'red',
      showCancelButton: true, // Agrega un botón de cancelar
    }).then((result) => {
      if (result.isConfirmed) {
        this.cursosService.deleteCurso(id).subscribe({
          next: () => {
            // Actualizar la lista localmente
            this.listaCourse = this.listaCourse.filter((est) => est.id != id);
            Swal.fire('¡Eliminado correctamente!', '', 'success');
          },
          error: (err) => {
            Swal.fire('Error al eliminar', err.message, 'error');
          },
        });
      }
    });
  }
}
