import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from "rxjs/index";
import {ActivatedRoute, Router} from "@angular/router";
import {MovieService} from "../movie.service";

@Component({
  selector: 'app-movie-detail',
  templateUrl: './movie-detail.component.html',
  styleUrls: ['./movie-detail.component.css']
})
export class MovieDetailComponent implements OnInit,OnDestroy  {
  movie: any = {};
  sub: Subscription;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private movieService: MovieService) {
  }

  ngOnInit() {
    this.sub = this.route.params.subscribe(params => {
      const id = params['id'];
      if (id) {
        this.movieService.get(id).subscribe((movie: any) => {
          if (movie) {
            this.movie = movie;
        //    this.movie.href = movie._links.self.href;
          } else {
            console.log(`Movie with id '${id}' not found`);
            this.gotoList();
          }
        })
      }
    })
  }

  ngOnDestroy() {
    this.sub.unsubscribe();
  }

  gotoList(){
    this.router.navigate(['/movie-list']);
  }


}



