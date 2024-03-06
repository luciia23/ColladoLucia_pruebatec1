package com.pruebatec.pt1empleados.gui;

import com.pruebatec.pt1empleados.logic.Employee;
import java.util.List;

public class Console {

    public static void printError(String message) {
        System.out.println(Constants.RED + "ERROR: " + message + Constants.ANSI_RESET);
    }

    public static void printEmployees(List<Employee> list) {
        for (Employee e : list) {
            System.out.println(e.toString());
        }
    }

}
