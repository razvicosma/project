package com.jfxbase.oopjfxbase.utils;

import java.util.List;

public class Apartment {
    private Integer id;
    private String name;
    private String description;
    private Integer price;
    private Integer nrOfGuests;
    private Integer nrOfBedrooms;
    private Integer nrOfBathrooms;
    private Location location;
    private List<String> amenities;
    private List<String> photos;
    private List<Booking> bookings;
    private List<Review> reviews;
    private List<String> tags;

    public Apartment(Integer id, String name, String description, Integer price, Integer nrOfGuests, Integer nrOfBedrooms, Integer nrOfBathrooms, Location location, List<String> amenities, List<String> photos, List<Booking> bookings, List<Review> reviews, List<String> tags) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.nrOfGuests = nrOfGuests;
        this.nrOfBedrooms = nrOfBedrooms;
        this.nrOfBathrooms = nrOfBathrooms;
        this.location = location;
        this.amenities = amenities;
        this.photos = photos;
        this.bookings = bookings;
        this.reviews = reviews;
        this.tags = tags;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getNrOfGuests() {
        return nrOfGuests;
    }

    public void setNrOfGuests(Integer nrOfGuests) {
        this.nrOfGuests = nrOfGuests;
    }

    public Integer getNrOfBedrooms() {
        return nrOfBedrooms;
    }

    public void setNrOfBedrooms(Integer nrOfBedrooms) {
        this.nrOfBedrooms = nrOfBedrooms;
    }

    public Integer getNrOfBathrooms() {
        return nrOfBathrooms;
    }

    public void setNrOfBathrooms(Integer nrOfBathrooms) {
        this.nrOfBathrooms = nrOfBathrooms;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<String> getAmenities() {
        return amenities;
    }

    public void setAmenities(List<String> amenities) {
        this.amenities = amenities;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
    public void addBooking(Booking booking) { this.bookings.add(booking); }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
