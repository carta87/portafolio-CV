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
    this.getAllCursos();
  }

  public getAllCursos(): void {

  // 1️⃣ Validar página
  if (isNaN(this.page) || this.page <= 0) {
    Swal.fire('Error', 'Ingrese un número de página válido.', 'error');
    return;
  }

  // 2️⃣ Consumir servicio
  this.cursosService
    .getAllCursos(this.page - 1, 10, 'id', 'asc')
    .subscribe({
      next: (data) => {
        // 3️⃣ Validar respuesta
        if (!data || !data.content || data.content.length === 0) {
          Swal.fire('Información', 'No existen cursos registrados.', 'info');
          this.listaCourse = [];
          return;
        }

        // 4️⃣ Ordenar cursos
        this.listaCourse = data.content
          .slice()
          .sort((a, b) => a.numberCourse - b.numberCourse);
      },

      error: (error) => {
        console.error('Error al obtener cursos:', error);

        // 5️⃣ Manejo por código HTTP
        if (error.status === 429) {
          Swal.fire(
            'Demasiadas solicitudes',
            'Espere unos segundos y vuelva a intentar.',
            'warning'
          );
        } else if (error.status === 404) {
          Swal.fire(
            'No encontrado',
            'El recurso no existe.',
            'error'
          );
        } else {
          Swal.fire(
            'Error',
            'No se pudieron cargar los cursos.',
            'error'
          );
        }
        this.listaCourse = [];
      }
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
          error: () => {
            Swal.fire('Error', 'No se logró Eliminar el curso, valida si tiene estudiantes registrados.', 'error');
          },
        });
      }
    });
  }
}
