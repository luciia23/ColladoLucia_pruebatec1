package com.pruebatec.pt1empleados.logic;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity(name = "Empleado")
@NamedQueries({
    @NamedQuery(name = "Employee.findAllActive", query = "SELECT e FROM Empleado e WHERE e.active = true"),
    @NamedQuery(name = "Employee.findPositions", query = "SELECT DISTINCT e.position FROM Empleado e WHERE e.active = true"),
    @NamedQuery(name = "Employee.isDuplicated", query = "SELECT COUNT(e) FROM Empleado e WHERE e.name = :name AND e.surname = :surname"),
    @NamedQuery(name = "Employee.findEmployeePosition", query = "SELECT e FROM Empleado e WHERE e.active = true AND e.position = :position")
})
public class Employee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_empleado")
    private int id;
    
    @Column(name = "nombre")
    private String name;
    
    @Column(name = "apellido")
    private String surname;
    
    @Column(name = "cargo")
    private String position;
    
    @Column(name = "salario")
    private Double salary;
    
    @Column(name = "fecha_inicio")
    private LocalDate joinDate;
    
    @Column(name = "activo")
    private Boolean active;

    public Employee() {
        this.active = true;
    }

    public Employee(String name, String surname, String position, Double salary, LocalDate joinDate) {
        this.name = name;
        this.surname = surname;
        this.position = position;
        this.salary = salary;
        this.joinDate = joinDate;
        this.active = true;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPosition() {
        return position;
    }

    public Double getSalary() {
        return salary;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }

    public Boolean getActive() {
        return active;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public void setJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return """
           Empleado {
             ID: """ + id + ",\n"
                + "  Nombre: " + name + ",\n"
                + "  Apellido: " + surname + ",\n"
                + "  Cargo: " + position + ",\n"
                + "  Salario: " + salary + ",\n"
                + "  Fecha de Inicio: " + joinDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + "\n"
                + "}\n";
    }

}
