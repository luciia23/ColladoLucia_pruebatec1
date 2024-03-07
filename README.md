# Proyecto JPA Empleados

Este proyecto JPA se centra en el desarrollo del backend lógico para una aplicación de gestión de empleados de una empresa.

## FUNCIONALIDADES PRINCIPALES

- **Alta Empleado (CREATE):**
    - Permite agregar un nuevo empleado a la base de datos.

- **Listado Empleados (SELECT):**
    - Muestra un listado de todos los empleados activos en la empresa.
    - Permite filtrar la lista de empleados por su cargo en la empresa.

- **Modificación Empleado (UPDATE):**
    - Permite modificar los datos de los empleados registrados que se encuentran activos en la empresa.

- **Borrado Empleado (DELETE):**
    - Se realiza un borrado lógico de los empleados, manteniéndolos en la base de datos como desactivados.

Las funcionalidades descritas estarán disponibles para el usuario a través de un menú principal que permanecerá activo hasta que el usuario decida finalizarlo. Este enfoque garantiza la integridad de los datos y permite al usuario realizar operaciones de manera segura y eficiente.

## REQUISITOS

Para ejecutar este proyecto, necesitarás:

- **JDK:** Es necesario tener instalado Java Development Kit versión 17 o superior.
- **BBDD:**
    - Se proporciona un archivo `empleados.sql` que contiene la estructura de la base de datos.
    - Se necesita un gestor de MySQL (phpMyAdmin, Workbench, etc.).
- **IDE:**
    - Utilizar un entorno de desarrollo integrado compatible (Netbeans / IntelliJ).


## SUPUESTOS
- **Validación de datos**
    - Se ha implementado una gestión sólida de la validación de los datos introducidos por el usuario.
    Los datos ingresados tendrán que cumplir los requisitos específicos antes de ser procesados por el sistema
- **Consultas**
    - Para optimizar el rendimiento y la eficacia del proyecto, se han realizado queries a la base de datos. Tanto para consultas, como filtrado de datos y verificación de entidades duplicadas.
- **Manejo de Excepciones**
    - Se han manejado las excepciones en las operaciones CRUD. Utilizando bloques try/catch para capturar excepciones como **entidades que no existen o entidades duplicadas**.
    - Además, se ha creado una excepción propia `InvalidInputException` para lanzar en el caso de la introducción de datos inválidos por parte del usuario.
