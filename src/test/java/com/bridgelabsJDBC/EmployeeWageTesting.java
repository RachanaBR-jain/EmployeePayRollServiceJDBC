package com.bridgelabsJDBC;

import com.bridgelabsJDBC.EmployeePayrollData;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class EmployeeWageTesting {
    EmployeeWageMain employeeWage;
    List<EmployeePayrollData> employeePayrollDataList;

    @BeforeEach
    public void setup() {
        employeeWage = new EmployeeWageMain();

    }

    @Test
    public void givenEmployeePayrollDB_WhenRetrieved_ShouldMatchEmployeeCount() {
        employeePayrollDataList = employeeWage.retrieveAllData();
        System.out.println(employeePayrollDataList.get(0));
        System.out.println(employeePayrollDataList.get(1));
        System.out.println(employeePayrollDataList.get(2));
        Assertions.assertEquals(3, employeePayrollDataList.size());
    }

    @Test
    public void givenEmployeePayrollDB_WhenUpdated_ShouldMatchEmployeeCount() {
        employeePayrollDataList = employeeWage.retrieveAllData();
        int res = employeeWage.updateEmployeeDataUsingStatement();
        System.out.println("After update " + employeePayrollDataList.get(1));
        Assertions.assertEquals(1, res);
    }
}


