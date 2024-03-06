package com.pruebatec.pt1empleados.logic;

import com.pruebatec.pt1empleados.gui.Console;
import com.pruebatec.pt1empleados.gui.Constants;
import com.pruebatec.pt1empleados.logic.operations.ValidateEmployee;
import com.pruebatec.pt1empleados.persistence.PersistenceController;
import com.pruebatec.pt1empleados.persistence.exceptions.NonexistentEntityException;
import com.pruebatec.pt1empleados.persistence.exceptions.PreexistingEntityException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import javax.persistence.NoResultException;

public class Controller {

    private Scanner sc = new Scanner(System.in);
    PersistenceController pController = new PersistenceController();

    /*
     * Agrega uno o más empleados a la base de datos. Muestra un menú para
     * agregar empleados y solicita la entrada del usuario. Verifica la entrada
     * y agrega el empleado a la base de datos.
     */
    public void addEmployee() {
        boolean flag = true;
        String input;
        while (flag) {
            try {
                System.out.println("Creación empleado...\n");
                pController.createEmployee(ValidateEmployee.createEmployee());
                System.out.println("Empleado agregado a la bbdd!");
            } catch (PreexistingEntityException ex) {
                System.out.println(ex.getMessage());
            }
            System.out.println("¿Desea agregar otro empleado? (s/n)");
            input = sc.next();
            if (input.equalsIgnoreCase("n")) {
                flag = false;
            }
        }

    }

    private void executeUpdate(Employee e, int op) {

        switch (op) {
            case 1 ->
                ValidateEmployee.setValidString(e, Constants.INPUTS.NAME);
            case 2 ->
                ValidateEmployee.setValidString(e, Constants.INPUTS.SURNAME);
            case 3 ->
                ValidateEmployee.setValidString(e, Constants.INPUTS.POSITION);
            case 4 ->
                ValidateEmployee.setValidSalary(e);
            case 5 ->
                ValidateEmployee.setValidDate(e);
            default -> {
                Console.printError("Opción no válida");
                return;
            }
        }
        pController.modifyEmployee(e);
        System.out.println("Se ha modificado el empleado: " + e.toString());
    }

    public void modifyEmployees() {
        //Facilita ver a todos los empleados que hay en la bbdd
        System.out.println("¿Quieres ver todos los empleados que hay en la bbdd?(s/n)");
        String resp = sc.next();
        boolean validOption = false;
        try {
            if (resp.equalsIgnoreCase("s")) {
                Console.printEmployees(pController.selectEmployees());
            }
            do {
                try {
                    System.out.println(Constants.UPDATE_MENU);
                    int op = sc.nextInt();
                    if (op >= 1 && op <= 5) {
                        System.out.println("Introduce el id del empleado que desea modificar");
                        int id = sc.nextInt();
                        Employee e = pController.findEmployeeById(id);
                        executeUpdate(e, op);
                        validOption = true;
                    } else if (op == 6) {
                        System.out.println("Saliendo del menú modificar....");
                        validOption = true;
                    } else {
                        Console.printError("Error: opción no válida. Introduce un número entre 1 y 6.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Error: Debes ingresar un número entero.");
                    sc.nextLine();
                } catch (NonexistentEntityException ex) {
                    System.out.println(ex.getMessage());
                }
            } while (!validOption);
        } catch (NoResultException exception) {
            System.out.println(exception.getMessage());
        }

    }

    /*
    * Muestra todos los empleados activos que haya en la bbdd
    */
    public void showEmployees() {
        List<Employee> listEmployee;
        try {
            listEmployee = pController.selectEmployees();
            System.out.println("---------- E M P L E A D O S  A C T I V O S ----------");
            Console.printEmployees(listEmployee);
        } catch (NoResultException ex) {
            System.out.println(ex.getMessage());
        }
    }

   /*
    *   Elimina el empleado deseado
    */
    public void deleteEmployee() {
        Employee e;
        boolean flag = true;

        while (flag) {
            System.out.println("Ingrese el id:");
            int id = sc.nextInt();
            try {
                e = pController.findEmployeeById(id);

                System.out.println("¿Seguro que quieres eliminarlo?(s/n)");
                String input = sc.next();

                if (input.equalsIgnoreCase("s")) {
                    pController.deleteEmployee(e);
                    System.out.println("Empleado con id " + id + " ha sido eliminado");
                } else {
                    System.out.println("La eliminación ha sido cancelada");;
                }
                System.out.println("¿Quieres eliminar otro empleado? (s/n)");
                String resp = sc.next();
                if (resp.equalsIgnoreCase("n")) {
                    flag = false;
                }
            } catch (NonexistentEntityException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    
    /*
    *   Muestra los empleados que haya con el cargo indicado
    */
    public void findEmployeePosition() {
        System.out.println("¿Quieres ver todos los cargos que hay presentes en la bbdd?(s/n)");
        String resp = sc.next();
        try {
            //Facilita ver a todos los cargos que hay en la bbdd al usuario
            List<String> listPositions = pController.findAllPositions();
            if (resp.equalsIgnoreCase("s")) {
                for (String s : listPositions) {
                    System.out.println("-> " + s);
                }
            }
            System.out.println("Introduzca el cargo que desea buscar:");
            String input = sc.next();
            System.out.println("---------- E M P L E A D O S  C A R G O ----------");
            System.out.println("\n->CARGO: " + input + "\n");
            Console.printEmployees(pController.findEmployeePosition(input));
        } catch (NoResultException e) {
            System.out.println(e.getMessage());
        }
    }

}
