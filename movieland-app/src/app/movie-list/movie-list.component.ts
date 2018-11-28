import { Component, OnInit } from '@angular/core';
import {MovieService} from "../movie.service";

@Component({
  selector: 'app-movie-list',
  templateUrl: './movie-list.component.html',
  styleUrls: ['./movie-list.component.css']
})
export class MovieListComponent implements OnInit {
  movies: Array<any>

  constructor(private movieService: MovieService) { }

  ngOnInit() {
    this.movieService.getAll().subscribe(data=>{
      this.movies = data;
    });
  }

}
