Descripción del proyecto

Desarrollo del backend lógico para una aplicación de gestión de empleados de una empresa

- FUNCIONALIDADES PRINCIPALES

    - Dar de alta a un nuevo empleado
    - Listado
        - empleados activos
        - filtrar por cargo
    - Modificación de los datos de los empleados registrados activos
    - Borrado
        - se realizará un borrado lógico
            que permaneceran en la bbdd como desactivados

- REQUISITOS
    -JDK (17)
    - bbdd (viene empleados.sql y un gestor de MYSQL)
    - IDE

- SUPUESTOS
    - validacion de datos
    - se realizan queries
    - lanzamiento de excepciones tan
    - para evitar duplicados, no permito que introduzcan un empleado con un nombre y apellido que haya ya registrado en la bbdd
