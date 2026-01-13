import { Component, inject } from '@angular/core';
import { CursosService } from '../../../servicios/cursos/cursos.service';
import { EstudianteModel } from '../../../common/modelos/estudiante.model';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import Swal from 'sweetalert2';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-info',
  standalone: true,
   imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './info.component.html',
  styleUrl: './info.component.css'
})
export class InfoComponent {

  fb = inject(FormBuilder);
  mostrarFormulario = false;
  cursoService = inject(CursosService);
  listaEstudiantes: EstudianteModel[] = [];
  infoCurso: string = "";
  cargando = true;

  fgvalidacion = this.fb.group({
    numeroCurso: ['', [Validators.required, Validators.max(999)]]
  });

   buscar(): void {
    if (this.fgvalidacion.invalid) {
      Swal.fire('Error', 'Ingrese un número de curso válido.', 'error');
      return;
    }

    const id = Number(this.fgvalidacion.value.numeroCurso);

    if (id <= 0) {
      Swal.fire('Error', 'El número de curso debe ser mayor a cero.', 'error');
      return;
    }

    this.cargando = true;
    this.infoCurso = '';

    this.cursoService.getInfoCurso(id).subscribe({
      next: (data: string) => {
        this.infoCurso = data;
        this.cargando = false;
      },
      error: (error) => {
        this.cargando = false;
        this.manejarError(error);
      }
    });
  }

  private manejarError(error: any): void {
    console.error('Error al obtener curso:', error);

    switch (error.status) {
      case 404:
        Swal.fire(
          'No encontrado',
          'El número de curso no existe.',
          'error'
        );
        break;

      case 429:
        Swal.fire(
          'Demasiadas solicitudes',
          'Espere unos segundos y vuelva a intentar.',
          'warning'
        );
        break;

      default:
        Swal.fire(
          'Error',
          error.error.message,
          'error'
        );
        break;
    }
  }

  public getErrorMessage(controlName: string): string {
    const control = this.fgvalidacion.get(controlName);
    if (control?.hasError('required')) {
      return 'Este campo es obligatorio';
    } else if (control?.hasError('email')) {
      return 'El formato del correo electrónico no es válido';
    } else if (control?.hasError('max')) {
      return 'la cantidad permitida es maximo 3 digito';
    }
    return '';
  }

}
