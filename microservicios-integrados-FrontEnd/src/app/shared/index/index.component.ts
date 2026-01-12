import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { RouterLink, RouterOutlet } from '@angular/router';
import { LoginResponse } from '../../common/modelos/loginResponse.model';
import { LoginService } from '../../servicios/auth/login.service';
import { GeneralService } from '../../servicios/generalAI/generalService';

@Component({
  selector: 'app-index',
  standalone: true,
  imports: [RouterOutlet, CommonModule, RouterLink],
  templateUrl: './index.component.html',
  styleUrl: './index.component.css',
})
export class IndexComponent implements OnInit {
  
  informacionInicial: string = "";
  userLoginOn: boolean = false;
  userData?: LoginResponse;
  loginService = inject(LoginService);
  generalService = inject(GeneralService);
  

  ngOnInit(): void {
    this.generalService.getInfoInicial().subscribe({
      next: (informacion) => {
        this.informacionInicial = informacion;
      },
    });
    this.loginService.currentUserLoginOn.subscribe({
      next: (loginStatus) => {
        this.userLoginOn = loginStatus;
      },
    });

    this.loginService.currentUserData.subscribe({
      next: (userData) => {
        this.userData = userData;
      },
    });
  }
}
