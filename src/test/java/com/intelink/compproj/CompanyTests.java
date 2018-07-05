package com.intelink.compproj;

import com.intelink.compproj.entity.Company;
import com.intelink.compproj.entity.Employee;
import com.intelink.compproj.entity.Project;
import com.intelink.compproj.entity.Technology;
import org.junit.Assert;
import org.junit.Test;


public class CompanyTests extends TestEntityBuilder {

    @Test
    public void testHireEmployee() {
        Company company = buildCompany();

        Project project = buildInternalProject(4);
        project.require(Technology.HTML);
        project.require(Technology.CSS);
        project.require(Technology.JS);
        company.addProject(project);

        Employee employee = buildJuniorFrontEndDeveloper();
        company.employ(employee);

        Assert.assertTrue(company.getEmployees().contains(employee));
//        Assert.assertTrue(company.getInternalProjects().iterator().next()
//                .getCurrentPhase().getEmployees().contains(employee));
//        Assert.assertTrue(employee.getProjects().contains(project));
    }

    @Test
    public void testPortfolio() {
        Company company = buildCompany();

        int projectPhases = 4;
        Project project = buildInternalProject(projectPhases);
        project.require(Technology.HTML);
        project.require(Technology.CSS);
        project.require(Technology.JS);
        company.addProject(project);

        Employee employee = buildJuniorFrontEndDeveloper();
        company.employ(employee);

        Assert.assertEquals(0, employee.getPortfolio().getCompletedTasksCount());

        for (int i = 0; i < projectPhases; i++) {
            project.getCurrentPhase().complete();
        }

        Assert.assertEquals(projectPhases, employee.getPortfolio().getCompletedTasksCount());
    }

}
