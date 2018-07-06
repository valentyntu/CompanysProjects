package com.intelink.compproj;

import com.intelink.compproj.entity.AssignmentException;
import com.intelink.compproj.entity.Company;
import com.intelink.compproj.entity.Employee;
import com.intelink.compproj.entity.Project;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class EmployeeTests extends TestEntityBuilder {

    @Test
    public void testHasWorkedOn() throws AssignmentException {
        Company company = buildCompany();

        Project internalProject = buildInternalProject();
        company.addProject(internalProject);

        Employee employee = buildUnskilledEmployee();
        company.employ(employee);

        assertThat(employee.hasWorkedOn(internalProject), equalTo(true));
    }
}
