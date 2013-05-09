/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.util.Date;

/**
 * Diese Bean enthält alle Daten eines Films
 * @author neumeister
 */
public class FilmBean {
    private String name;
    private int borrowerID;
    private String url;
    private Date returnDate;
    private int renewals;
    private String imageURL;
    private int releaseYear;
    private String director;
    private String writer;
    private int runtime;
    private String country;
    private String trailerURL;
    private int id;

    public int getBorrowerID() {
        return borrowerID;
    }

    public void setBorrowerID(int borrowerID) {
        this.borrowerID = borrowerID;
    }

    public String getCountry() {
        return country;
    }

    public String getDirector() {
        return director;
    }

    public int getId() {
        return id;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getName() {
        return name;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public int getRenewals() {
        return renewals;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public int getRuntime() {
        return runtime;
    }

    public String getTrailerURL() {
        return trailerURL;
    }

    public String getUrl() {
        return url;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWriter() {
        return writer;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public void setRenewals(int renewals) {
        this.renewals = renewals;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public void setTrailerURL(String trailerURL) {
        this.trailerURL = trailerURL;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    

}
