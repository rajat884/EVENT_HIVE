package com.eventhive.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.math.BigDecimal;

public class Event implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String title;
    private String description;
    private Date date;
    private Time time;
    private String venue;
    private BigDecimal price;
    private String category;
    private String coverImagePath; // Path to the stored image file
    private int organizerId; // Foreign key to link to the User who created it

    // Default constructor
    public Event() {
    }

    // Full constructor
    public Event(int id, String title, String description, Date date, Time time, String venue,
                 BigDecimal price, String category, String coverImagePath, int organizerId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.time = time;
        this.venue = venue;
        this.price = price;
        this.category = category;
        this.coverImagePath = coverImagePath;
        this.organizerId = organizerId;
    }

    // --- Getters and Setters for all fields ---

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCoverImagePath() {
        return coverImagePath;
    }

    public void setCoverImagePath(String coverImagePath) {
        this.coverImagePath = coverImagePath;
    }

    public int getOrganizerId() {
        return organizerId;
    }
    private BigDecimal revenue;


    public BigDecimal getRevenue() {
        return revenue;
    }

    public void setRevenue(BigDecimal revenue) {
        this.revenue = revenue;
    }
    public void setOrganizerId(int organizerId) {
        this.organizerId = organizerId;
    }
}