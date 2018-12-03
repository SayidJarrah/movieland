import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HttpClientModule} from "@angular/common/http";
import {MovieService} from "./movie.service";
import {MovieListComponent} from './movie-list/movie-list.component';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {
  MatButtonModule,
  MatCardModule,
  MatGridListModule,
  MatInputModule,
  MatListModule,
  MatSidenavModule,
  MatToolbarModule
} from '@angular/material';
import {RouterModule, Routes} from "@angular/router";
import { MovieDetailComponent } from './movie-detail/movie-detail.component';
import {FormsModule} from "@angular/forms";
import {MatDividerModule} from "@angular/material/divider";
import { GenreListComponent } from './genre-list/genre-list.component';
import {GenreService} from "./genre.service";
import { GenreMenuComponent } from './genre-menu/genre-menu.component';

const appRoutes: Routes = [
  {path: '', component:MovieListComponent},
  {path: 'movie-list', component: MovieListComponent},
  {path: 'genre-list', component: GenreListComponent},
  {path: 'movie-detail/:id', component: MovieDetailComponent}

]

@NgModule({
  declarations: [
    AppComponent,
    MovieListComponent,
    MovieDetailComponent,
    GenreListComponent,
    GenreMenuComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatCardModule,
    MatInputModule,
    MatListModule,
    MatGridListModule,
    MatToolbarModule,
    MatDividerModule,
    FormsModule,
    MatSidenavModule,
    RouterModule.forRoot(appRoutes)

  ],
  providers: [MovieService,GenreService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
