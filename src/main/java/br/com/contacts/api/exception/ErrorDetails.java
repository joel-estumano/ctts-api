package br.com.contacts.api.exception;

import java.time.LocalDateTime;

public class ErrorDetails {
    private int statusCode;
    private String message;
    private String details;
    private String path;
    private LocalDateTime timestamp; 

    public ErrorDetails(int statusCode, String message, String details, String path) {
        this.statusCode = statusCode;
        this.message = message;
        this.details = details;
        this.path = path;
        this.timestamp = LocalDateTime.now(); 
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
