package com.server.turistai.entities;

import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 *
 */
@Entity
@Table(name = "tb_travel")
public class Travel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "travels_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name= "user_id")
    private User user;

    @Column(nullable = false)
    private String title;

    private String description;

    @Temporal(TemporalType.DATE)
    private Date date;

    private String location;

    private String state;

    private String image;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
}
