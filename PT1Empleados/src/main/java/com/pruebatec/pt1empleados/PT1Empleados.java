package com.pruebatec.pt1empleados;

import com.pruebatec.pt1empleados.gui.Console;
import com.pruebatec.pt1empleados.gui.Constants;
import com.pruebatec.pt1empleados.logic.Controller;
import java.util.Scanner;

public class PT1Empleados {

    private static Scanner sc = new Scanner(System.in);
    private static final Controller control = new Controller();

    private static boolean executeOption() {
        try{
                 String input = sc.next();
        int op = Integer.parseInt(input);
        switch (op) {
            case 1 ->
                control.addEmployee();
            case 2 ->
                control.showEmployees();
            case 3 ->
                control.modifyEmployees();
            case 4 ->
                control.deleteEmployee();
            case 5 ->
                control.findEmployeePosition();
            case 6 -> {
                System.out.println("Saliendo del menú principal...");
                System.out.println(Constants.BYE_MESSAGE);
                return true;
            }
            default ->
                Console.printError("Opción no válida");
        }   
        }catch(NumberFormatException e){
            Console.printError("Debes ingresar un número válido");
        }

        return false;
    }

    public static void main(String[] args) {
        System.out.println(Constants.WELCOME_MESSAGE);

        boolean flag = false;

        while (!flag) {
            System.out.println(Constants.MAIN_MENU);
            flag = executeOption();
        }
        sc.close();
    }
}
