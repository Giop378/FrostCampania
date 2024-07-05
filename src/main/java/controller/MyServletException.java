package controller;

import jakarta.servlet.ServletException;

public class MyServletException extends ServletException {

    public MyServletException() {
        super();
    }

    public MyServletException(String message) {
        super(message);
    }
}
