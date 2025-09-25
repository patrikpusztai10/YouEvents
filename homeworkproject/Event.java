package com.example.homeworkproject;

public class Event {
    private String event_name;
    private String event_location;
    private String event_date;
    private String event_description;
    private String image;
    private int interest;


    public int getInterest() {
        return interest;
    }

    public void setInterest(int interest) {
        this.interest = interest;
    }

    public Event(String name, String image, int interest)
    {
        this.event_name=name;
        this.image=image;
        this.interest=interest;
    }

    public Event(String event_name, String event_location, String event_date, String event_description, String image) {
        this.event_name = event_name;
        this.event_location = event_location;
        this.event_date = event_date;
        this.event_description = event_description;
        this.image = image;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getEvent_description() {
        return event_description;
    }

    public void setEvent_description(String event_description) {
        this.event_description = event_description;
    }

    public String getEvent_location() {
        return event_location;
    }

    public void setEvent_location(String event_location) {
        this.event_location = event_location;
    }

    public String getEvent_date() {
        return event_date;
    }

    public void setEvent_date(String event_date) {
        this.event_date = event_date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }




}
