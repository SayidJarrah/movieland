package com.dkorniichuk.movieland.entity;

import com.dkorniichuk.movieland.service.util.deserializer.MovieDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Set;

@JsonDeserialize(using = MovieDeserializer.class)
public class Movie {
    private int id;
    private String name;
    private int yearOfRelease;
    private String description;
    private double rating;
    private double price;
    private String picturePath;
    private Set<Country> countries;
    private Set<Genre> genres;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Set<Review> reviews;
    //   private String nameNative = name.split("/")[1];
    //   private String nameRussian = name.split("/")[0];

    public Movie() {
    }

    ;

    public Movie(String name, int yearOfRelease, String description, double rating, double price, String picturePath, Set<Genre> genres, Set<Country> countries) {
        this.name = name;
        this.yearOfRelease = yearOfRelease;
        this.description = description;
        this.rating = rating;
        this.price = price;
        this.picturePath = picturePath;
        this.genres = genres;
        this.countries = countries;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYearOfRelease() {
        return yearOfRelease;
    }

    public void setYearOfRelease(int yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public Set<Country> getCountries() {
        return countries;
    }

    public void setCountries(Set<Country> countries) {
        this.countries = countries;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

   /* public String getNameRussian() {
        return nameRussian;
    }

    public String getNameNative() {
        return nameNative;
    }

    public void setNameNative(String nameNative) {
        this.nameNative = nameNative;
    }

    public void setNameRussian(String nameRussian) {
        this.nameRussian = nameRussian;
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        if (id != movie.id) return false;
        if (yearOfRelease != movie.yearOfRelease) return false;
        if (Double.compare(movie.rating, rating) != 0) return false;
        if (Double.compare(movie.price, price) != 0) return false;
        if (name != null ? !name.equals(movie.name) : movie.name != null) return false;
        if (description != null ? !description.equals(movie.description) : movie.description != null) return false;
        if (picturePath != null ? !picturePath.equals(movie.picturePath) : movie.picturePath != null) return false;
        if (countries != null ? !countries.equals(movie.countries) : movie.countries != null) return false;
        return genres != null ? genres.equals(movie.genres) : movie.genres == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + yearOfRelease;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        temp = Double.doubleToLongBits(rating);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (picturePath != null ? picturePath.hashCode() : 0);
        result = 31 * result + (countries != null ? countries.hashCode() : 0);
        result = 31 * result + (genres != null ? genres.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", yearOfRelease=" + yearOfRelease +
                ", description='" + description + '\'' +
                ", rating=" + rating +
                ", price=" + price +
                ", picturePath='" + picturePath + '\'' +
                ", countries=" + countries +
                ", genres=" + genres +
                '}';
    }
}
