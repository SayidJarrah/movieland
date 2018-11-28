import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/index";

@Injectable({
  providedIn: 'root'
})
export class MovieService {

  constructor(private http: HttpClient) { }

  getAll():Observable<any>{
    return this.http.get(window.location.pathname.split('/')[1] +'/v1/movie/random')
  }
}
