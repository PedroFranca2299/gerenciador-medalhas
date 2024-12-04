package com.medalmanager.model.entity;

public class Country {
    private Long id;
    private String name;
    private boolean participating;

    public Country(Long id, String name, boolean participating) {
        this.id = id;
        this.name = name;
        this.participating = participating;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public boolean isParticipating() { return participating; }
    public void setParticipating(boolean participating) { this.participating = participating; }
}