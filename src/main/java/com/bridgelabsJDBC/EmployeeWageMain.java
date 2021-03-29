package com.bridgelabsJDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeeWageMain {
    private static EmployeeWageMain employeePayrollDBService;
    private PreparedStatement employeePayrollDataStatement;

    public EmployeeWageMain() {

    }

    public static EmployeeWageMain getInstance() {
        if (employeePayrollDBService == null)
            employeePayrollDBService = new EmployeeWageMain();
        return employeePayrollDBService;
    }

    private Connection getConnection() throws SQLException {
        String jdbcURL = "jdbc:mysql://localhost:3306/employeewagejdbc?useSSL=false";
        String user = "root";
        String password = "beerleke";

        Connection connection;
        System.out.println("Connection to database: " + jdbcURL);
        connection = DriverManager.getConnection(jdbcURL, user, password);
        System.out.println("Connection is successful!!!!" + connection);

        return connection;
    }

    public List<EmployeePayrollData> retrieveAllData() throws EmployeeWageException {
        String sql = "SELECT * FROM employee_payroll;";
        List<EmployeePayrollData> employeePayrollList = new ArrayList<>();
        try (Connection connection = this.getConnection()) {

            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            employeePayrollList = this.getEmployeePayrollData(result);

        } catch (SQLException e) {
            throw new EmployeeWageException(e.getMessage(), EmployeeWageException.ExceptionType.EMPLOYEEPAYROLL_DB_PROBLEM);
        }
        return employeePayrollList;
    }

    public int updateEmployeeDataUsingPreparedStatement(String name, double salary) throws EmployeeWageException {
        return this.updateEmployeeDataUsingStatement(name, salary);
    }

    int updateEmployeeDataUsingStatement(String name, double salary) throws EmployeeWageException {
        String sql = String.format("UPDATE employee_payroll SET salary=%.2f WHERE name='%s';", salary, name);
        try (Connection connection = this.getConnection()) {
            Statement statement = connection.createStatement();
            return statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new EmployeeWageException(e.getMessage(), EmployeeWageException.ExceptionType.UNABLE_TO_UPDATE);
        }
    }

    private List<EmployeePayrollData> getEmployeePayrollData(ResultSet result) throws EmployeeWageException {
        List<EmployeePayrollData> employeePayrollList = new ArrayList<>();
        try {
            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                Double salary = result.getDouble("salary");
                LocalDate start = result.getDate("start").toLocalDate();
                employeePayrollList.add(new EmployeePayrollData(id, name, salary, start));
            }
        } catch (SQLException e) {
            throw new EmployeeWageException(e.getMessage(), EmployeeWageException.ExceptionType.EMPLOYEEPAYROLL_DB_PROBLEM);
        }

        return employeePayrollList;
    }

    private void prepareStatementForEmployeeData() throws EmployeeWageException {
        try {
            Connection connection = this.getConnection();
            String sql = "SELECT * FROM employee_payroll WHERE name=? ";
            employeePayrollDataStatement = connection.prepareStatement(sql);
        } catch (SQLException e) {
            throw new EmployeeWageException(e.getMessage(), EmployeeWageException.ExceptionType.EMPLOYEEPAYROLL_DB_PROBLEM);
        }
    }

    public List<EmployeePayrollData> getEmployeePayrollData(String name) throws EmployeeWageException {
        List<EmployeePayrollData> employeePayrollList = null;
        if (this.employeePayrollDataStatement == null)
            this.prepareStatementForEmployeeData();
        try {
            employeePayrollDataStatement.setString(1, name);
            ResultSet result = employeePayrollDataStatement.executeQuery();
            employeePayrollList = this.getEmployeePayrollData(result);
        } catch (SQLException e) {
            throw new EmployeeWageException(e.getMessage(), EmployeeWageException.ExceptionType.UNABLE_TO_UPDATE);
        }
        return employeePayrollList;
    }

    public List<EmployeePayrollData> readDataByDate(String startDate) throws EmployeeWageException {
        String sql = "select * from employee_payroll where start between cast(? as date) and date(now());";
        List<EmployeePayrollData> employeePayrollList = new ArrayList<>();
        try (Connection connection = this.getConnection()) {
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setString(1, startDate);
            ResultSet result = prepareStatement.executeQuery();
            employeePayrollList = this.getEmployeePayrollData(result);
        } catch (SQLException e) {
            throw new EmployeeWageException(e.getMessage(), EmployeeWageException.ExceptionType.EMPLOYEEPAYROLL_DB_PROBLEM);
        }
        return employeePayrollList;
    }

    public long readTotalSalary(String gender) throws EmployeeWageException {
        String sql = "select gender,sum(salary) from employee_payroll group by gender;";
        int totalSalary = 0;
        try (Connection connection = this.getConnection()) {
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            ResultSet result = prepareStatement.executeQuery();
            while (result.next()) {
                if (result.getString(1).equalsIgnoreCase(gender))
                    totalSalary = result.getInt(2);
            }
        } catch (SQLException e) {
            throw new EmployeeWageException(e.getMessage(),
                    EmployeeWageException.ExceptionType.EMPLOYEEPAYROLL_DB_PROBLEM);
        }
        return totalSalary;
    }


}