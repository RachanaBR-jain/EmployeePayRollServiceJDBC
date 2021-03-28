package com.bridgelabsJDBC;

import com.bridgelabsJDBC.EmployeePayrollData;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;

import java.util.List;

public class EmployeeWageTesting {

    @Test
    public void givenEmployeePayrollDB_WhenRetrieved_ShouldMatchEmployeeCount() {
        EmployeeWageMain employeeWage = new EmployeeWageMain();
        List<EmployeePayrollData> employeePayrollDataList = employeeWage.retrieveAllData();
        System.out.println(employeePayrollDataList.get(0));
        System.out.println(employeePayrollDataList.get(1));
        System.out.println(employeePayrollDataList.get(2));
        Assertions.assertEquals(3, employeePayrollDataList.size());
    }
}
