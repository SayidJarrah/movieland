import {Component, OnInit} from '@angular/core';
import {GenreService} from "../genre.service";

@Component({
  selector: 'app-genre-list',
  templateUrl: './genre-list.component.html',
  styleUrls: ['./genre-list.component.css']
})
export class GenreListComponent implements OnInit {
  genres: Array<any>

  constructor(private genreService: GenreService) {
  }

  ngOnInit() {
    this.genreService.getAll().subscribe(data => {
      this.genres = data
    });
  }

}
