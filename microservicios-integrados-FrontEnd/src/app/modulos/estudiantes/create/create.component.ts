import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { CursoModel } from '../../../common/modelos/curso.model';
import { EstudianteModel } from '../../../common/modelos/estudiante.model';
import { CursosService } from '../../../servicios/cursos/cursos.service';
import { EstudiantesService } from '../../../servicios/estudiantes/estudiantes.service';

@Component({
  selector: 'app-create',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './create.component.html',
  styleUrl: './create.component.css',
})
export class CreateComponent implements OnInit {

  router = inject(Router);
  fb = inject(FormBuilder);
  listaCourse: CursoModel[] = [];
  courseService = inject(CursosService);
  estudianteService = inject(EstudiantesService);
  page: number = 1;

  fgvalidacion = this.fb.group({
    nombreEstudiante: ['', Validators.required],
    apellidosEstudiante: ['', Validators.required],
    emailEstudiante: ['', [Validators.required, Validators.email]],
    cursoIdEstudiante: ['', [Validators.required, Validators.max(999)]],
    nombreAcudiente: ['', Validators.required],
    apellidosAcudiente: ['', Validators.required],
    emailAcudiente: ['', [Validators.required, Validators.email]],
  });

  ngOnInit(): void {
    this.getCursos();
  }

  public create(): void {
    if (!this.fgvalidacion.valid) {
      Object.keys(this.fgvalidacion.controls).forEach(controlName => {
        this.fgvalidacion.get(controlName)?.markAsDirty();
      });
      Swal.fire(
        'Advertencia',
        'Por favor completa todos los campos correctamente',
        'warning'
      );
      return;
    }

    const cursoId = Number(this.fgvalidacion.value.cursoIdEstudiante);
    const cursoSeleccionado = this.listaCourse.find(c => c.id === cursoId);

    if (!cursoSeleccionado) {
      Swal.fire('Error', 'Curso inválido', 'error');
      return;
    }

    const estudianteData: EstudianteModel = {
      id: 0,
      name: this.fgvalidacion.value.nombreEstudiante ?? '',
      lastName: this.fgvalidacion.value.apellidosEstudiante ?? '',
      email: this.fgvalidacion.value.emailEstudiante ?? '',
      courseDTO: {
        id: cursoSeleccionado.id,
        numberCourse: cursoSeleccionado.numberCourse,
        name: cursoSeleccionado.name,
        teacher: cursoSeleccionado.teacher
      },
      attendant: {
        name: this.fgvalidacion.value.nombreAcudiente ?? '',
        lastName: this.fgvalidacion.value.apellidosAcudiente ?? '',
        email: this.fgvalidacion.value.emailAcudiente ?? '',
      }
    };

    // 🔵 MENSAJE DE CARGA
    Swal.fire({
      title: 'Procesando solicitud',
      text: 'Generando comprobante, por favor espere...',
      allowOutsideClick: false,
      allowEscapeKey: false,
      didOpen: () => {
        Swal.showLoading();
      }
    });

    this.estudianteService.saveAndComprobanteEstudiante(estudianteData).subscribe({
      next: (response) => {
        Swal.close();

        const blob = new Blob([response], { type: 'application/pdf' });
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = `comprobantePago_${this.fgvalidacion.value.nombreEstudiante}_${this.fgvalidacion.value.apellidosEstudiante}.pdf`;
        a.click();

        Swal.fire('Éxito', 'Estudiante registrado correctamente', 'success');
        this.router.navigate(['estudiantes/listar']);
      },
      error: () => {
        Swal.close();
        Swal.fire('Error', 'No se logró registrar el estudiante', 'error');
      }
    });
  }

  public getCursos() {
    this.courseService.getAllCursos(this.page - 1, 10, 'id', 'asc').subscribe((data) => {
      this.listaCourse = data.content
        .slice()
        .sort((a, b) => a.numberCourse - b.numberCourse);
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
