import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/index";

@Injectable({
  providedIn: 'root'
})
export class MovieService {
  public URL_PREFIX = window.location.pathname.split('/')[1];

  constructor(private http: HttpClient) {
  }

  getAll(): Observable<any> {
    return this.http.get(this.URL_PREFIX + '/v1/movie')
  }

  get(id: string) {
    return this.http.get(this.URL_PREFIX + '/v1/movie/' + id);
  }
}
