import { HttpClient, HttpHeaders } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment.prod';
import { CursoModel } from '../../common/modelos/curso.model';
import { PageResponse } from '../../common/modelos/pageResponse';

@Injectable({
  providedIn: 'root'
})
export class CursosService {

  htpp = inject(HttpClient);
  headers = new HttpHeaders({
    'accept': 'application/json',
    'Content-Type': 'application/json'
  });

  public getAllCursos(
    page: number,
    size: number,
    sortBy: string,
    sortDirection: string
  ): Observable<PageResponse<CursoModel>>{
    return this.htpp.get<PageResponse<CursoModel>>(
      `${environment.apiUrlCourse}?page=${page}&size=${size}&sort=${sortBy},${sortDirection}`);
  }

  public getCursoId(id: number): Observable<CursoModel>{
    return this.htpp.get<CursoModel>(environment.apiUrlCourse + '/' + id, {headers: this.headers});
  }

  public saveCursos(curso: CursoModel){
    return this.htpp.post<CursoModel>(environment.apiUrlCourse, JSON.stringify(curso), {headers: this.headers});
  }

  public updateCursos(curso: CursoModel){
    return this.htpp.put<CursoModel>(environment.apiUrlCourse, JSON.stringify(curso), {headers: this.headers});
  }

  public deleteCurso(id: number): Observable<CursoModel>{
    return this.htpp.delete<CursoModel>(environment.apiUrlCourse + '/'+ id, {headers: this.headers});
  }

  public getCursoAllEstudiantes(id: number): Observable<CursoModel>{
    return this.htpp.get<CursoModel>(environment.apiUrlCourse + '/search-student/' + id, {headers: this.headers});
  }

  public getInfoCurso(id: number): Observable<string>{
    return this.htpp.get<string>(environment.apiUrlCourse + '/explainContent/' + id, {headers: this.headers});
  }

}
