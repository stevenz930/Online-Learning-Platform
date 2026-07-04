package com.example.itp4229m.retrofit;

public class ApiResponse<T> {
    private String status;
    private T data;
    private String message;
    private int has_enrolled;
    private int is_in_cart;


    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public T getData() { return data; }
    public void setData(T data) {
        this.data = data;
    }

    public int getHasEnrolled() {
        return has_enrolled;
    }

    public int getIsInCart() {
        return is_in_cart;
    }

}
