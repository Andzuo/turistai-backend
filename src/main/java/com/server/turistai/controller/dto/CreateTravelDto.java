package com.server.turistai.controller.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class CreateTravelDto {

    private String title;
    private String description;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date date;

    private String location;
    private String state;

    public CreateTravelDto() {
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public CreateTravelDto(String title, String description, Date date, String image, String location, String state) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.location = location;
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "CreateTravelDto{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                '}';
    }
}
