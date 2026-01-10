import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink, RouterOutlet } from '@angular/router';
import { LogingRequest } from '../../../common/modelos/loginRequest.model';
import { LoginService } from '../../../servicios/auth/login.service';
import Swal from 'sweetalert2';
@Component({
  selector: 'app-login',
  standalone: true,
  imports: [RouterOutlet, ReactiveFormsModule, CommonModule, RouterLink],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent {

  loginError: string = "";
  loginForm: FormGroup;
  router = inject(Router);
  formBuilder = inject(FormBuilder);
  loginService = inject(LoginService);

  constructor() {
    // Inicializa el formulario dentro del constructor
    this.loginForm = this.formBuilder.group({
      username: ['', Validators.required ],
      password: ['', Validators.required]
      //email: ['', [Validators.required, Validators.email]],
    });
  }

  get email(){
    return this.loginForm.controls['email'];
  }

  get password(){
    return this.loginForm.controls['password'];
  }

  get username(){
    return this.loginForm.controls['username'];
  }

  public login(): void {

  if (!this.loginForm.valid) {
    Swal.fire(
      'Advertencia',
      'Ingrese usuario y contraseña correctamente',
      'warning'
    );
    return;
  }

  let loadingShown = false;

  // ⏳ Temporizador de 5 segundos
  const loadingTimer = setTimeout(() => {
    loadingShown = true;
    Swal.fire({
      title: 'Procesando solicitud',
      text: 'Conectando al backend, por favor espere...',
      allowOutsideClick: false,
      allowEscapeKey: false,
      didOpen: () => {
        Swal.showLoading();
      }
    });
  }, 5000);

  this.loginService.login(this.loginForm.value as LogingRequest)
    .subscribe({
      next: (data) => {
        clearTimeout(loadingTimer);

        if (loadingShown) {
          Swal.close();
        }

        localStorage.setItem('token', data.token);

        Swal.fire(
          'Bienvenido',
          'Inicio de sesión exitoso',
          'success'
        );

        this.router.navigateByUrl('/inicio');
        this.loginForm.reset();
      },
      error: () => {
        clearTimeout(loadingTimer);

        if (loadingShown) {
          Swal.close();
        }

        Swal.fire(
          'Error',
          'Usuario o contraseña incorrectos',
          'error'
        );
      }
    });
  }

  public logout(){
    this.loginService.logout();
  }
}
