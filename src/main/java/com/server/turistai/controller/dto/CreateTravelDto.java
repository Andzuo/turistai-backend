package com.server.turistai.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class CreateTravelDto {

    private String title;
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date date;

    private String image;

    // Construtor padrão
    public CreateTravelDto() {
    }

    // Construtor com parâmetros
    public CreateTravelDto(String title, String description, Date date, String image) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.image = image;
    }

    // Getters e Setters
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "CreateTravelDto{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", image='" + image + '\'' +
                '}';
    }
}
