import { HttpClient, HttpHeaders } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment.prod';

@Injectable({
  providedIn: 'root'
})
export class GeneralService {

  htpp = inject(HttpClient);
  headers = new HttpHeaders({
    'accept': 'application/json',
    'Content-Type': 'application/json'
  });

  public getInfoInicial(): Observable<string>{
    return this.htpp.get<string>(environment.apiUrlGeneral + '/welcomeCoursePlatform', {headers: this.headers});
  }

}
