package com.infosupport.ap.exercise.services.responses;

public class GetPerson {
    private String name;
    private String userdata;

    public GetPerson() {
    }

    public GetPerson(String name, String userdata) {
        this.name = name;
        this.userdata = userdata;
    }

    public String getName() {
        return name;
    }

    public String getUserdata() {
        return userdata;
    }

    @Override
    public String toString() {
        return "GetPerson{" +
                "name='" + name + '\'' +
                ", userdata='" + userdata + '\'' +
                '}';
    }
}
