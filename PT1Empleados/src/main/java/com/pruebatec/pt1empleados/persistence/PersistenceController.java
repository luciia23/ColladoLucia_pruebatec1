package com.pruebatec.pt1empleados.persistence;

import com.pruebatec.pt1empleados.logic.Employee;
import com.pruebatec.pt1empleados.persistence.exceptions.NonexistentEntityException;
import com.pruebatec.pt1empleados.persistence.exceptions.PreexistingEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.NoResultException;

public class PersistenceController {

    EmployeeJpaController eController = new EmployeeJpaController();

    public void createEmployee(Employee e) throws PreexistingEntityException {
        eController.create(e);
    }

    public List<Employee> selectEmployees() throws NoResultException {
        return eController.findAllActiveEmployees();
    }

    public Employee findEmployeeById(int id) throws NonexistentEntityException {
        return eController.findEmployee(id);
    }

    public void deleteEmployee(Employee e) {
        try {
            e.setActive(false);
            eController.edit(e);
        } catch (Exception ex) {
            Logger.getLogger(PersistenceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Employee> findEmployeePosition(String position) throws NoResultException{
        return eController.findAllEmployeesByPosition(position);
    }

    public List<String> findAllPositions() throws NoResultException{
        return eController.findAllPositions();
    }

    public void modifyEmployee(Employee e) {
        try {
            eController.edit(e);
        } catch (Exception ex) {
            Logger.getLogger(PersistenceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
