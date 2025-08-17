package com.eventhive.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Registration implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private int eventId;
    private int attendeeId;
    private Timestamp registrationDate;

    // Default constructor
    public Registration() {
    }

    // Full constructor
    public Registration(int id, int eventId, int attendeeId, Timestamp registrationDate) {
        this.id = id;
        this.eventId = eventId;
        this.attendeeId = attendeeId;
        this.registrationDate = registrationDate;
    }

    // --- Getters and Setters ---

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getAttendeeId() {
        return attendeeId;
    }

    public void setAttendeeId(int attendeeId) {
        this.attendeeId = attendeeId;
    }

    public Timestamp getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Timestamp registrationDate) {
        this.registrationDate = registrationDate;
    }
}