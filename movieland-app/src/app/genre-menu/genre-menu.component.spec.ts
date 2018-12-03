import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GenreMenuComponent } from './genre-menu.component';

describe('GenreMenuComponent', () => {
  let component: GenreMenuComponent;
  let fixture: ComponentFixture<GenreMenuComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GenreMenuComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GenreMenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
