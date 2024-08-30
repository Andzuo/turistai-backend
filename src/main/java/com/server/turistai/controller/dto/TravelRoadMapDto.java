package com.server.turistai.controller.dto;

public class TravelRoadMapDto {

    private String title;
    private String comment;
    private String addres;
    private Boolean visited;

    public TravelRoadMapDto() {
    }

    public TravelRoadMapDto(String title, String comment, String addres, Boolean visited) {
        this.title = title;
        this.comment = comment;
        this.addres = addres;
        this.visited = visited;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAddres() {
        return addres;
    }

    public void setAddres(String addres) {
        this.addres = addres;
    }

    public Boolean getVisited() {
        return visited;
    }

    public void setVisited(Boolean visited) {
        this.visited = visited;
    }
}
