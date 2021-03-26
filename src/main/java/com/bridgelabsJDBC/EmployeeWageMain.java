package com.bridgelabsJDBC;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
public class EmployeeWageMain {

        private static final String URL = "jdbc:mysql://localhost:3306/employeepayroll";
        private static final String user = "root";
        private static final String password = "beerleke";

        private void establishConnection() throws EmployeeWageException {
            Connection connection;
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                System.out.println("Driver found!");
            } catch (ClassNotFoundException e) {
                throw new EmployeeWageException("Cannot find the JDBC Driver!!", EmployeeWageException.ExceptionType.CANNOT_LOAD_DRIVER);
            }
            listDrivers();
            try {
                System.out.println("Connecting to database: " + URL);
                connection = DriverManager.getConnection(URL, user, password);
                System.out.println("Connection established with: " + connection);
            } catch (SQLException e) {
                throw new EmployeeWageException("Cannot connect to the JDBC Driver!!",
                        EmployeeWageException.ExceptionType.WRONG_CREDENTIALS);
            }
        }

        private void listDrivers() {
            Enumeration<Driver> driverList = DriverManager.getDrivers();
            while (driverList.hasMoreElements()) {
                Driver driverClass = driverList.nextElement();
                System.out.println("Driver: " + driverClass.getClass().getName());
            }
        }

        public static void main(String[] args) throws EmployeeWageException {
            EmployeeWageMain employeePayroll = new EmployeeWageMain();
            employeePayroll.establishConnection();
        }
    }


