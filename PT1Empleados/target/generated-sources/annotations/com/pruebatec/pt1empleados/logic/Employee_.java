package com.pruebatec.pt1empleados.logic;

import java.time.LocalDate;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2024-03-06T22:19:03", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Employee.class)
public class Employee_ { 

    public static volatile SingularAttribute<Employee, LocalDate> joinDate;
    public static volatile SingularAttribute<Employee, String> surname;
    public static volatile SingularAttribute<Employee, String> name;
    public static volatile SingularAttribute<Employee, Boolean> active;
    public static volatile SingularAttribute<Employee, Integer> id;
    public static volatile SingularAttribute<Employee, String> position;
    public static volatile SingularAttribute<Employee, Double> salary;

}