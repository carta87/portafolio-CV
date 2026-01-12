import { Component, inject } from '@angular/core';
import { CursosService } from '../../../servicios/cursos/cursos.service';
import { EstudianteModel } from '../../../common/modelos/estudiante.model';
import { FormBuilder, Validators } from '@angular/forms';
import { CursoModel } from '../../../common/modelos/curso.model';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-info',
  standalone: true,
  imports: [],
  templateUrl: './info.component.html',
  styleUrl: './info.component.css'
})
export class InfoComponent {

   fb = inject(FormBuilder);
    mostrarFormulario = false;
    cursoService = inject(CursosService);
    listaEstudiantes: EstudianteModel[] = [];
  
    fgvalidacion = this.fb.group({
      numeroCurso: ['', [Validators.required, Validators.max(999)]],
      nombreCurso: ['', Validators.required],
      nombreProfesor: ['', Validators.required],
    });
  
    public buscar() {
      let id = Number(this.fgvalidacion.value.numeroCurso);
      if (isNaN(id) || id <= 0) {
        Swal.fire('Error', 'Ingrese un valor numerico correcto.', 'error');
        return;
      }
      this.cursoService.getCursoAllEstudiantes(id).subscribe({
        next: (data: CursoModel) => {
          debugger
          if (data.teacher == '') {
            Swal.fire('Error',
              'No encuentra informacion, valide numero de curso.', 'error');
          } else {
            this.mostrarFormulario = true;
            this.fgvalidacion.patchValue({
              nombreCurso: data.name,
              nombreProfesor: data.teacher,
            });
            this.listaEstudiantes = data.studentDTOList || [];
          }
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
              'El numero curso no existe, valida nuevamente el numero de curso.',
              'error'
            );
          } else {
            Swal.fire(
              'Error',
              'No se pudieron cargar los cursos.',
              'error'
            );
          }
        }
      });
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
