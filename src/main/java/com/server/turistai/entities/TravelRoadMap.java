package com.server.turistai.entities;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_travelRoadMap")
public class TravelRoadMap {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(nullable = false)
    private String title;
    private String addres;
    @ElementCollection
    private List<String> comments;
    private boolean visited;
    @Column(name = "tb_travelRoadMap_images")
    private String image;

    @ManyToOne
    @JoinColumn(name = "travel_id", nullable = false)
    private Travel travel;

    public Travel getTravel() {
        return travel;
    }

    public void setTravel(Travel travel) {
        this.travel = travel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    public String getAddres() {
        return addres;
    }

    public void setAddres(String addres) {
        this.addres = addres;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String images) {
        this.image = images;
    }
}
