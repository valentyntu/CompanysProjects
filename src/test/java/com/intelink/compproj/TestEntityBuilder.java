package com.intelink.compproj;

import com.intelink.compproj.entity.Company;
import com.intelink.compproj.entity.Employee;
import com.intelink.compproj.entity.Phase;
import com.intelink.compproj.entity.Project;
import com.intelink.compproj.entity.Technology;

public class TestEntityBuilder {

    public static Company buildCompany() {
        Company company = new Company("The-Best-IT-Company");
        return company;
    }

    private static Project buildProject(int phases) {
        Project project = new Project(phases);
        return project;
    }

    public static Project buildInternalProject(int phases) {
        Project project = buildProject(phases);
        project.setCommercial(false);
        return project;
    }

    public static Project buildCommercialProject(int phases) {
        Project project = buildProject(phases);
        project.setCommercial(true);
        return project;
    }

    public static Employee buildUnskilledEmployee() {
        Employee employee = new Employee("John D.");
        return employee;
    }

    public static Employee buildJuniorFrontEndDeveloper() {
        Employee employee = buildUnskilledEmployee();
        employee.know(Technology.HTML);
        employee.know(Technology.CSS);
        employee.know(Technology.JS);
        return employee;
    }

    public static Employee buildFrontEndDeveloper() {
        Employee employee = buildJuniorFrontEndDeveloper();
        employee.know(Technology.ANGULAR);
        employee.know(Technology.REACT);
        return employee;
    }

    public static Employee buildJuniorBackEndDeveloper() {
        Employee employee = buildUnskilledEmployee();
        employee.know(Technology.JAVA);
        employee.know(Technology.REST);
        return employee;
    }
}
