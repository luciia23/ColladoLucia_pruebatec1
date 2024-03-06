package com.pruebatec.pt1empleados.logic.operations;

import com.pruebatec.pt1empleados.gui.Constants;
import static com.pruebatec.pt1empleados.gui.Constants.INPUTS.NAME;
import static com.pruebatec.pt1empleados.gui.Constants.INPUTS.POSITION;
import static com.pruebatec.pt1empleados.gui.Constants.INPUTS.SURNAME;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import com.pruebatec.pt1empleados.logic.Employee;
import com.pruebatec.pt1empleados.logic.exceptions.InvalidInputException;
import java.time.format.DateTimeParseException;

public class ValidateEmployee {

    private static final Scanner sc = new Scanner(System.in);

    /*
        Crea el Empleado validando cada input
    */
    public static Employee createEmployee() {
        Employee e = new Employee();
        setValidString(e, Constants.INPUTS.NAME);
        setValidString(e, Constants.INPUTS.SURNAME);
        setValidString(e, Constants.INPUTS.POSITION);
        setValidSalary(e);
        setValidDate(e);
        return (e);
    }

    public static void setValidSalary(Employee e) {
        System.out.println("Ingrese el salario del empleado");
        double salary = getValidSalary();
        e.setSalary(salary);
    }

    public static void setValidDate(Employee e) {
        System.out.println("Ingrese la fecha de inicio:");
        LocalDate joinDate = getValidDate();
        e.setJoinDate(joinDate);
    }

    public static void setValidString(Employee e, Constants.INPUTS input) {
        switch (input) {
            case NAME -> {
                System.out.println("Ingrese el nombre del empleado:");
                e.setName(getValidInput());
            }
            case SURNAME -> {
                System.out.println("Ingrese el apellido del empleado:");
                e.setSurname(getValidInput());
            }
            case POSITION -> {
                System.out.println("Ingrese el cargo del empleado:");
                e.setPosition(getValidInput());
            }
        }

    }

    private static String getValidInput() {
        String input = "";
        while (true) {
            try {
                input = sc.next().trim();
                //Este regex verifica si solo contiene caracteres alfabéticos
                if (!input.matches("[\\p{L}]+")) {
                    throw new InvalidInputException("El input solo debe contener letras. Por favor, inténtelo de nuevo:");
                } else if (input.isEmpty() || input.length() < 3) {
                    throw new InvalidInputException("El input no puede estar vacío, y tiene que tener al menos 3 caracteres."
                            + " Por favor, inténtelo de nuevo:");
                } else {
                    break;
                }
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            }
        }
        return input;
    }

    private static Double getValidSalary() {
        double salary = 0.0;
        boolean validInput = false;

        while (!validInput) {
            String input = sc.next();
            try {
                salary = Double.parseDouble(input);
                if (salary < 0 || salary < 1500) {
                    throw new InvalidInputException("El salario no puede ser negativo, y tiene que ser mayor a 1500€");
                } else  {
                    validInput = true;
                }
            } catch (NumberFormatException ex) {
                System.out.println("Debes ingresar un número válido para el salario");
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            }
        }
        return salary;
    }

    private static LocalDate getValidDate() {
        LocalDate date = null;
        boolean isValid = false;

        while (!isValid) {
            String joinDate = sc.next();
            try {
                date = LocalDate.parse(joinDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                isValid = true;
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha incorrecto. Por favor, inténtelo de nuevo. (dd-MM-yyyy)");
            }
        }
        return date;
    }

}
