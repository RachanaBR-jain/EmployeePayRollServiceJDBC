package com.bridgelabsJDBC;
public class EmployeeWageException extends Exception {
    public enum ExceptionType {
        WRONG_CREDENTIALS, CANNOT_LOAD_DRIVER
    }
    ExceptionType type;

    public  EmployeeWageException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }
}
