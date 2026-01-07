import { isPlatformBrowser } from '@angular/common';
import {
  HttpClient,
  HttpErrorResponse,
  HttpHeaders,
} from '@angular/common/http';
import { inject, Inject, Injectable, PLATFORM_ID } from '@angular/core';
import { BehaviorSubject, catchError, Observable, tap, throwError } from 'rxjs';
import { environment } from '../../../environments/environment.prod';
import { LogingRequest } from '../../common/modelos/loginRequest.model';
import { LoginResponse } from '../../common/modelos/loginResponse.model';

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  //npm install jwt-decode
  dirty: boolean = false;
  http = inject(HttpClient);
  //se mejora almecenando la informacion en sesion
  currentUserLoginOn: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
  currentUserData: BehaviorSubject<LoginResponse> = new BehaviorSubject<LoginResponse>({
      username: '',
      token: '',
      message: '',
      status: '',
  });

  constructor(@Inject(PLATFORM_ID) private platformId: Object) {
    if (isPlatformBrowser(this.platformId)) {
      const storedUserData = sessionStorage.getItem('userData');
      this.currentUserLoginOn.next(sessionStorage.getItem('token') != null);
      this.currentUserData.next(
        storedUserData ? JSON.parse(storedUserData) : { username: '', token: '', message: '', status: '' }
      );
    }
  }

  public login(credentials: LogingRequest): Observable<LoginResponse> {
    const _headers = new HttpHeaders({
      accept: 'application/json',
      'Content-Type': 'application/json',
    });
    return this.http.post<LoginResponse>(`${environment.apiUrlAuth}/login`, credentials, { headers: _headers })
      .pipe(
        tap((userData: LoginResponse) => {
          if (isPlatformBrowser(this.platformId)) {
            sessionStorage.setItem('userData', JSON.stringify(userData));
            sessionStorage.setItem('token', userData.token);
          }
          this.currentUserData.next(userData);
          this.currentUserLoginOn.next(true);
        }),
        catchError(this.handleError)
      );
  }

  public logout(): void {
    if (isPlatformBrowser(this.platformId)) {
      sessionStorage.removeItem('token');
    }
    this.currentUserLoginOn.next(false);
  }

  private handleError(error: HttpErrorResponse) {
    if (error.status === 0) {
      console.error('Se ha producido un error', error.error);
      return throwError(() => new Error('Se ha producido un error de conexión', error.error));
    } else if (error.status != undefined && error.error != undefined) {
      console.error('El Backend retornó código de estado ', error.status, error.error);
    }
    return throwError(() => new Error(
      error.error != undefined && error.error.message.length <= 40
        ? error.error.message : 'Algo falló. Intente nuevamente'
    ));
  }

  get userData(): Observable<LoginResponse> {
    return this.currentUserData.asObservable();
  }

  get userLoginOn(): Observable<boolean> {
    return this.currentUserLoginOn.asObservable();
  }

  get userToken(): string {
    return this.currentUserData.value.token;
  }
}
