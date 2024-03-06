package com.pruebatec.pt1empleados.persistence;

import com.pruebatec.pt1empleados.logic.Employee;
import com.pruebatec.pt1empleados.persistence.exceptions.NonexistentEntityException;
import com.pruebatec.pt1empleados.persistence.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class EmployeeJpaController implements Serializable {

    public EmployeeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public EmployeeJpaController() {
        emf = Persistence.createEntityManagerFactory("jpaPU");
    }

   /*
    *Comprueba si existe el empleado pasado por parámetro
    *Lanza PreexistingEntityException si ya existe el empleado
    */
    public boolean isDuplicateEmployee(String name, String surname) throws PreexistingEntityException {
        EntityManager em = null;
        Long count;
        try {
            em = getEntityManager();
            Query q = em.createNamedQuery("Employee.isDuplicated");
            q.setParameter("name", name);
            q.setParameter("surname", surname);
            count = (Long) q.getSingleResult();
            if (count > 0){
                throw new PreexistingEntityException("Ya existe ese empleado en la bbdd");
            }
            return count > 0;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    /*
    *Añade el empleado a la bbdd
    *Lanza PreexistingEntityException csi ya existe el empleado
    */
    public void create(Employee employee) throws PreexistingEntityException {
        EntityManager em = null;
        if (isDuplicateEmployee(employee.getName(), employee.getSurname())) {
            return;
        }
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(employee);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Employee employee) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            employee = em.merge(employee);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = employee.getId();
                if (findEmployee(id) == null) {
                    throw new NonexistentEntityException("The employee with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Employee employee;
            try {
                employee = em.getReference(Employee.class, id);
                employee.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The employee with id " + id + " no longer exists.", enfe);
            }
            em.remove(employee);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Employee> findEmployeeEntities() {
        return findEmployeeEntities(true, -1, -1);
    }

    public List<Employee> findEmployeeEntities(int maxResults, int firstResult) {
        return findEmployeeEntities(false, maxResults, firstResult);
    }

    private List<Employee> findEmployeeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Employee.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    /*
    *Busca los empleados activos en la bbdd
    *Lanza NoResultException cuando no hay registros
    */
    public List<Employee> findAllActiveEmployees() throws NoResultException {
        EntityManager em = getEntityManager();
        List<Employee> list;
        try {
            Query q = em.createNamedQuery("Employee.findAllActive");
            list = q.getResultList();
            if (list.isEmpty()) {
                throw new NoResultException("No hay registros de empleados activos en la bbdd");
            }
            return list;
        } finally {
            em.close();
        }
    }

    /*
    *Busca los empleados que haya con ese cargo en la bbdd
    *Lanza NoResultException cuando no hay registros del cargo
    */
    public List<Employee> findAllEmployeesByPosition(String position) throws NoResultException {
        EntityManager em = getEntityManager();
        List<Employee> list;
        try {
            Query q = em.createNamedQuery("Employee.findEmployeePosition");
            q.setParameter("position", position);
            list = q.getResultList();
            if (list.isEmpty()) {
                throw new NoResultException("No hay registros con el cargo de " + position);
            }
            return list;
        } finally {
            em.close();
        }
    }

    /*
    * Busca el empleado con el id pasado por parámetro
    * Lanza NonexistentEntityException si no lo encuentra
    */
    public Employee findEmployee(int id) throws NonexistentEntityException {
        EntityManager em = getEntityManager();
        Employee e;
        try {
            e = em.find(Employee.class, id);
            if (e == null || !e.getActive()) {
                throw new NonexistentEntityException("El empleado no existe");
            }
            return e;
        } finally {
            em.close();
        }
    }

    /*
    * Busca todos los cargos que haya en la bbdd
    * Lanza NoResultException si no encuentra ninguno
    */
    public List<String> findAllPositions() throws NoResultException {
        EntityManager em = getEntityManager();

        try {
            Query q = em.createNamedQuery("Employee.findPositions");
            List<String> list = q.getResultList();
            if (list.isEmpty()) {
                throw new NoResultException("No se encontraron cargos en la bbdd");
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public int getEmployeeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Employee> rt = cq.from(Employee.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
