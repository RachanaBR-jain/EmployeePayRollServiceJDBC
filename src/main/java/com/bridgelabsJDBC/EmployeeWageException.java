package com.bridgelabsJDBC;
public class EmployeeWageException extends Exception {
    public enum ExceptionType {
        EMPLOYEEPAYROLL_DB_PROBLEM,UNABLE_TO_UPDATE
    }
    ExceptionType type;

    public  EmployeeWageException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }
}
