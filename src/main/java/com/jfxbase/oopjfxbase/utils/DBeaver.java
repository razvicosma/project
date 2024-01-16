package com.jfxbase.oopjfxbase.utils;

import com.jfxbase.oopjfxbase.utils.enums.SCENE_IDENTIFIER;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;

import java.sql.*;
import java.util.*;


public class DBeaver{
    public static void signUpUser(ActionEvent event, String first_name, String last_name, String username, String password, String confirmPassword) {

        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUser = null;
        ResultSet resultSet = null;

        String url = "jdbc:postgresql://localhost:5432/project";
        String user = "postgres";
        String dbPassword = "DBeaver";
        String insertUserQuery = "INSERT INTO users(first_name, last_name, username, password) VALUES(?, ?, ?, ?)";
        String checkUserQuery = "SELECT * FROM users WHERE username = ?";
        try  {
            connection = DriverManager.getConnection(url,user,dbPassword);
            psCheckUser = connection.prepareStatement(checkUserQuery);
            psCheckUser.setString(1,username);
            resultSet = psCheckUser.executeQuery();

            if (resultSet.isBeforeFirst()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Username already exists.");
                alert.show();
                throw new SQLException("Username already exists. ");
            } else if (first_name.isEmpty() || last_name.isEmpty() || username.isEmpty() || password.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please fill all fields. ");
                alert.show();
                throw new SQLException("Please fill all fields. ");
            }else if(!password.equals(confirmPassword)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("The password fields do not match. ");
                alert.show();
                throw new SQLException("The password fields do not match. ");
            }else {
                psInsert = connection.prepareStatement(insertUserQuery);
                psInsert.setString(1, first_name);
                psInsert.setString(2, last_name);
                psInsert.setString(3, username);
                psInsert.setString(4, password);
                psInsert.executeUpdate();
                resultSet = psCheckUser.executeQuery();
                resultSet.next();
                User usery = new User(resultSet.getInt("id"),first_name,last_name,username,password,false,
                        resultSet.getString("profile_picture"));
                Platform.runLater(() -> {
                    System.out.println("Changing scene...");
                    ApplicationHandler.getInstance().changeScene(SCENE_IDENTIFIER.LoggedIn, usery, null);
                    System.out.println("Scene changed successfully.");
                });


            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psCheckUser != null) {
                try {
                    psCheckUser.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psInsert != null) {
                try {
                    psInsert.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void signInUser(ActionEvent event, String username, String password) {

        Connection connection = null;
        PreparedStatement psCheckUser = null;
        PreparedStatement psGetBookings = null;
        PreparedStatement psGetReviews = null;
        ResultSet reviewsResultSet = null;
        ResultSet bookingsResultSet = null;
        ResultSet resultSet = null;

        String url = "jdbc:postgresql://localhost:5432/project";
        String user = "postgres";
        String dbPassword = "DBeaver";
        String checkUserQuery = "SELECT * FROM users WHERE username = ?";
        String showBookingsQuery = "SELECT * FROM bookings WHERE user_id = ?";
        String showReviewsQuery = "SELECT * FROM reviews WHERE booking_id = ?";
        try  {
            connection = DriverManager.getConnection(url,user,dbPassword);
            psCheckUser = connection.prepareStatement(checkUserQuery);
            psGetBookings = connection.prepareStatement(showBookingsQuery);
            psGetReviews = connection.prepareStatement(showReviewsQuery);
            psCheckUser.setString(1,username);
            resultSet = psCheckUser.executeQuery();

            if (resultSet.isBeforeFirst()) {
                resultSet.next();
                String passwordFromDatabase = resultSet.getString("password");
                if (passwordFromDatabase.equals(password)){
                    psGetBookings.setInt(1,resultSet.getInt("id"));
                    bookingsResultSet = psGetBookings.executeQuery();
                    String first_name = resultSet.getString("first_name");
                    String last_name = resultSet.getString("last_name");
                    Boolean admin_rights = resultSet.getBoolean("admin_rights");
                    User usery = new User(resultSet.getInt("id"),first_name,last_name,username,password,admin_rights,
                            resultSet.getString("profile_picture"));
                    while(bookingsResultSet.next()) {
                        psGetReviews.setInt(1,bookingsResultSet.getInt("id"));
                        reviewsResultSet = psGetReviews.executeQuery();
                        Review review = null;
                        while(reviewsResultSet.next()) {
                            review = new Review(reviewsResultSet.getInt("id"), reviewsResultSet.getInt("rating"),
                                    reviewsResultSet.getString("comment"), reviewsResultSet.getDate("date").toLocalDate(),
                                    username);
                        }
                        Booking booking = new Booking(bookingsResultSet.getInt("id"),bookingsResultSet.getInt("user_id"),
                                bookingsResultSet.getInt("apartment_id"), bookingsResultSet.getDate("check_in").toLocalDate(),
                                bookingsResultSet.getDate("check_out").toLocalDate(),bookingsResultSet.getInt("total_cost"),
                                review);
                        usery.addBooking(booking);
                    }
                Platform.runLater(() -> {
                        System.out.println("Changing scene...");
                        if(!admin_rights) {
                            ApplicationHandler.getInstance().changeScene(SCENE_IDENTIFIER.LoggedIn, usery, null);
                        } else {
                            ApplicationHandler.getInstance().changeScene(SCENE_IDENTIFIER.Admin, usery, null);
                        }
                        System.out.println("Scene changed successfully.");
                    });
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Invalid credentials. ");
                    alert.show();
                    throw new SQLException("Invalid credentials. ");
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Invalid credentials. ");
                alert.show();
                throw new SQLException("Invalid credentials. ");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (bookingsResultSet != null) {
                try {
                    bookingsResultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (reviewsResultSet != null) {
                try {
                    reviewsResultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psCheckUser != null) {
                try {
                    psCheckUser.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psGetBookings != null) {
                try {
                    psGetBookings.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psGetReviews != null) {
                try {
                    psGetReviews.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static ListView<User> adminData(ActionEvent event) {

        Connection connection = null;
        PreparedStatement psAdminData = null;
        ResultSet resultSet = null;
        ListView<User> list = new ListView<User>();

        String url = "jdbc:postgresql://localhost:5432/project";
        String user = "postgres";
        String dbPassword = "DBeaver";
        String adminDataQuery = "SELECT * FROM users";
        try {
            connection = DriverManager.getConnection(url, user, dbPassword);
            psAdminData = connection.prepareStatement(adminDataQuery);
            resultSet = psAdminData.executeQuery();

            while(resultSet.next()){
                User usery = new User(resultSet.getInt("id"),resultSet.getString("first_name"),resultSet.getString("last_name"),resultSet.getString("username"),resultSet.getString("password"),resultSet.getBoolean("admin_rights"), resultSet.getString("profile_picture"));
                list.getItems().add(usery);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psAdminData != null) {
                try {
                    psAdminData.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }
    public static void deleteUser(ActionEvent event, String username) {

        Connection connection = null;
        PreparedStatement psDeleteUser = null;
        ResultSet resultSet = null;

        String url = "jdbc:postgresql://localhost:5432/project";
        String user = "postgres";
        String dbPassword = "DBeaver";
        String deleteUserQuery = "DELETE FROM users WHERE username = ?";
        try {
            connection = DriverManager.getConnection(url, user, dbPassword);
            psDeleteUser = connection.prepareStatement(deleteUserQuery);
            psDeleteUser.setString(1,username);
            int rowsAffected = psDeleteUser.executeUpdate();
            if(rowsAffected > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("User deleted successfully. ");
                alert.show();
            }else {
                System.out.println(username + "User not found or not deleted");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("User " + username + " not found. ");
                alert.show();
                throw new SQLException("User " + username + " not found. ");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psDeleteUser != null) {
                try {
                    psDeleteUser.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static ListView<Apartment> showApartments(ActionEvent event) {

        Connection connection = null;
        ResultSet resultSet = null;
        ResultSet imageResultSet = null;
        ResultSet bookingsResultSet = null;
        ResultSet reviewsResultSet = null;
        ResultSet tagsResultSet = null;
        ResultSet amenitiesResultSet = null;
        ResultSet userResultSet = null;
        PreparedStatement psGetUser = null;
        PreparedStatement psGetAmenities = null;
        PreparedStatement psGetTags = null;
        PreparedStatement psGetReviews = null;
        PreparedStatement psGetBookings = null;
        PreparedStatement psGetImages = null;
        PreparedStatement psShowApartments = null;
        ListView<Apartment> list = new ListView<Apartment>();

        String url = "jdbc:postgresql://localhost:5432/project";
        String user = "postgres";
        String dbPassword = "DBeaver";
        String showApartmentsQuery = "   SELECT A.id AS id, A.name AS name, A.description AS description, A.price AS price, " +
                "A.nr_of_guests AS nr_of_guests, A.nr_of_bedrooms AS nr_of_bedrooms, A.nr_of_bathrooms AS nr_of_bathrooms, " +
                "L.address AS address, C.name AS city, C.country AS country FROM apartments A JOIN locations L ON " +
                "A.location_id = L.id JOIN cities C ON L.city_id = C.id";
        String showImagesQuery = "SELECT * FROM photos WHERE apartment_id = ?";
        String showBookingsQuery = "SELECT * FROM bookings WHERE apartment_id = ?";
        String showReviewsQuery = "SELECT * FROM reviews WHERE booking_id = ?";
        String showTagsQuery = "SELECT A.apartment_id AS apartment_id, B.name AS tag FROM tags2apartments A JOIN tags B ON " +
                "A.tag_id = B.id WHERE apartment_id = ?";
        String showAmenitiesQuery = "SELECT A.apartment_id AS apartment_id, B.name AS amenity FROM apartment2amenities A JOIN amenities B ON " +
                "A.amenity_id = B.id WHERE apartment_id = ?";
        String showUserQuery = "   select U.username as username,R.id as review_id from users U join bookings B on U.id = B.user_id join " +
                "reviews R on B.id = R.booking_id where r.id = ?";
        try {
            connection = DriverManager.getConnection(url, user, dbPassword);
            psShowApartments = connection.prepareStatement(showApartmentsQuery);
            psGetImages = connection.prepareStatement(showImagesQuery);
            psGetBookings = connection.prepareStatement(showBookingsQuery);
            psGetReviews = connection.prepareStatement(showReviewsQuery);
            psGetTags = connection.prepareStatement(showTagsQuery);
            psGetAmenities = connection.prepareStatement(showAmenitiesQuery);
            psGetUser = connection.prepareStatement(showUserQuery);
            resultSet = psShowApartments.executeQuery();

            while(resultSet.next()){
                psGetImages.setInt(1,resultSet.getInt("id"));
                imageResultSet = psGetImages.executeQuery();
                List<String> images = new ArrayList<>();
                while(imageResultSet.next()) {
                    images.add(imageResultSet.getString("url"));
                }
                psGetTags.setInt(1,resultSet.getInt("id"));
                tagsResultSet = psGetTags.executeQuery();
                List<String> tags = new ArrayList<>();
                while(tagsResultSet.next()) {
                    tags.add(tagsResultSet.getString("tag"));
                }
                psGetAmenities.setInt(1,resultSet.getInt("id"));
                amenitiesResultSet = psGetAmenities.executeQuery();
                List<String> amenities = new ArrayList<>();
                while(amenitiesResultSet.next()) {
                    amenities.add(amenitiesResultSet.getString("amenity"));
                }
                psGetBookings.setInt(1,resultSet.getInt("id"));
                bookingsResultSet = psGetBookings.executeQuery();
                List<Booking> bookings = new ArrayList<>();
                List<Review> reviews = new ArrayList<>();
                while(bookingsResultSet.next()) {
                    psGetReviews.setInt(1,bookingsResultSet.getInt("id"));
                    reviewsResultSet = psGetReviews.executeQuery();
                    Review review = null;
                    while(reviewsResultSet.next()) {
                        psGetUser.setInt(1,reviewsResultSet.getInt("id"));
                        userResultSet = psGetUser.executeQuery();
                        userResultSet.next();
                        review = new Review(reviewsResultSet.getInt("id"), reviewsResultSet.getInt("rating"),
                                reviewsResultSet.getString("comment"), reviewsResultSet.getDate("date").toLocalDate(),
                                userResultSet.getString("username"));
                        reviews.add(review);
                    }
                    Booking booking = new Booking(bookingsResultSet.getInt("id"),bookingsResultSet.getInt("user_id"),
                            bookingsResultSet.getInt("apartment_id"), bookingsResultSet.getDate("check_in").toLocalDate(),
                            bookingsResultSet.getDate("check_out").toLocalDate(),bookingsResultSet.getInt("total_cost"),
                            review);
                    bookings.add(booking);
                }
                Location location = new Location(resultSet.getString("address"),resultSet.getString("city"),
                        resultSet.getString("country"));
                Apartment apartment = new Apartment(resultSet.getInt("id"),resultSet.getString("name"),
                        resultSet.getString("description"), resultSet.getInt("price"), resultSet.getInt("nr_of_guests"),
                        resultSet.getInt("nr_of_bedrooms"), resultSet.getInt("nr_of_bathrooms"),location,amenities,images,
                        bookings,reviews,tags);
                list.getItems().add(apartment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (imageResultSet != null) {
                try {
                    imageResultSet.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (bookingsResultSet != null) {
                try {
                    bookingsResultSet.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (reviewsResultSet != null) {
                try {
                    reviewsResultSet.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (tagsResultSet != null) {
                try {
                    tagsResultSet.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (amenitiesResultSet != null) {
                try {
                    amenitiesResultSet.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (userResultSet != null) {
                try {
                    userResultSet.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (psShowApartments != null) {
                try {
                    psShowApartments.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psGetImages != null) {
                try {
                    psGetImages.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psGetBookings != null) {
                try {
                    psGetBookings.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psGetReviews != null) {
                try {
                    psGetReviews.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psGetTags != null) {
                try {
                    psGetTags.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psGetAmenities != null) {
                try {
                    psGetAmenities.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psGetUser != null) {
                try {
                    psGetUser.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    public static void makeBooking(ActionEvent event, Booking booking) {

        Connection connection = null;
        PreparedStatement psInsert = null;
        ResultSet resultSet = null;

        String url = "jdbc:postgresql://localhost:5432/project";
        String user = "postgres";
        String dbPassword = "DBeaver";
        String insertBookingQuery = "INSERT INTO bookings(user_id, apartment_id, check_in, check_out, total_cost) VALUES(?, ?, ?, ?, ?)";
        try  {
            connection = DriverManager.getConnection(url,user,dbPassword);
                psInsert = connection.prepareStatement(insertBookingQuery);
                psInsert.setInt(1, booking.getUser_id());
                psInsert.setInt(2, booking.getApartment_id());
                psInsert.setDate(3, java.sql.Date.valueOf(booking.getCheckIn()));
                psInsert.setDate(4, java.sql.Date.valueOf(booking.getCheckOut()));
                psInsert.setInt(5,booking.getTotalCost());
                psInsert.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psInsert != null) {
                try {
                    psInsert.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void makeReview(ActionEvent event, Review review, Integer bookingId) {

        Connection connection = null;
        PreparedStatement psInsert = null;
        ResultSet resultSet = null;

        String url = "jdbc:postgresql://localhost:5432/project";
        String user = "postgres";
        String dbPassword = "DBeaver";
        String insertBookingQuery = "INSERT INTO reviews(booking_id, rating, comment, date) VALUES(?, ?, ?, ?)";
        try  {
            connection = DriverManager.getConnection(url,user,dbPassword);
            psInsert = connection.prepareStatement(insertBookingQuery);
            psInsert.setInt(1, bookingId);
            psInsert.setInt(2, review.getRating());
            psInsert.setString(3, review.getComment());
            psInsert.setDate(4, java.sql.Date.valueOf(review.getDate()));
            psInsert.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psInsert != null) {
                try {
                    psInsert.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static String getApartmentName(ActionEvent event, Integer apartmentId) {

        Connection connection = null;
        PreparedStatement psApartment = null;
        ResultSet resultSet = null;
        String apartment = null;

        String url = "jdbc:postgresql://localhost:5432/project";
        String user = "postgres";
        String dbPassword = "DBeaver";
        String adminDataQuery = "SELECT * FROM apartments WHERE id = ?";
        try {
            connection = DriverManager.getConnection(url, user, dbPassword);
            psApartment = connection.prepareStatement(adminDataQuery);
            psApartment.setInt(1,apartmentId);
            resultSet = psApartment.executeQuery();
            if(resultSet.next()){
                apartment = resultSet.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psApartment != null) {
                try {
                    psApartment.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return apartment;
    }
}
