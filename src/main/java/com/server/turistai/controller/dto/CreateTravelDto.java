package com.server.turistai.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class CreateTravelDto {

    private String title;
    private String description;

    private Date date;


    public CreateTravelDto() {
    }

    public CreateTravelDto(String title, String description, Date date, String image) {
        this.title = title;
        this.description = description;
        this.date = date;
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
