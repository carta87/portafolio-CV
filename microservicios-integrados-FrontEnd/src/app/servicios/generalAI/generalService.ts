import { HttpClient, HttpHeaders } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { catchError, Observable, of, timeout } from 'rxjs';
import { environment } from '../../../environments/environment.prod';

@Injectable({
  providedIn: 'root'
})
export class GeneralService {

  private http = inject(HttpClient);

  private headers = new HttpHeaders({
    Accept: 'text/html'
  });

  public getInfoInicial(): Observable<string> {
    return this.http.get(
      `${environment.apiUrlGeneral}/welcomeCoursePlatform`,
      {
        headers: this.headers,
        responseType: 'text'   // 👈 clave
      }
    ).pipe(
      timeout(3000),
      catchError(() =>
        of(`
          <p><i>
            Somos un equipo de profesionales emprendedores y soñadores,
            apasionados por la educación e intrépidos en el servicio de gestión
            de cursos académicos.
          </i></p>

          <p><i>
            Aspiramos a mejorar la vida de las personas aportando compromiso,
            eficiencia y calidad a cada uno de nuestros clientes.
          </i></p>

          <p><i>Creado Frontend</i></p>
        `)
      )
    );
  }
}
