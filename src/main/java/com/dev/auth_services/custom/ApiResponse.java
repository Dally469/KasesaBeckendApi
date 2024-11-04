package com.dev.auth_services.custom;

 public class ApiResponse {
    private int status;
    private boolean success;
    private String message;
    private String token;
    private Object data;  // Optional field for any additional data

    // Constructor for simple responses
    public ApiResponse(int status, boolean success, String message) {
        this.status = status;
        this.success = success;
        this.message = message;
    }

    // Constructor with data field
    public ApiResponse(int status, boolean success, String message, Object data) {
        this.status = status;
        this.success = success;
        this.message = message;
        this.data = data;
    }
     // Constructor with data field
     public ApiResponse(int status, boolean success, String message, String token, Object data) {
         this.status = status;
         this.success = success;
         this.message = message;
         this.token = token;
         this.data = data;
     }


    // Getters and setters
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public String getToken() {
         return token;
     }
     public void setToken(String token) {
         this.token = token;
     }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}