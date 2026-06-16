# Control de Gastos (Backend)

Control de Gastos es un backend construido con **Java**, **Spring Boot**, **MongoDB** y **Redis**, diseñado para gestionar un presupuesto y múltiples gastos realizados por el usuario. Este proyecto proporciona una API RESTful que permite crear, leer, actualizar y eliminar gastos, así como realizar estas mismas acciones para un único presupuesto.

## Tecnologías Utilizadas

- **Spring Boot**: Framework para construir aplicaciones Java. Particularmente en este proyecto se utiliza la versión `3.4.0`.
  - **Jakarta Validation**: Validación de datos de entrada.
  - **Eventos de ciclo de vida**: Se crea una clase que extiende de la clase `AbstractMongoEventListener`, la cual permite manejar acciones antes o después de realizar alguna de las operaciones CRUD sobre los objetos de la clase `Expense`.
- **Java**: Lenguaje de programación principal. Para este proyecto en específico se utilizó el `JDK 17`.
- **Maven**: Para la gestión de dependencias y construcción del proyecto.
- **MongoDB**: Base de datos NoSQL para almacenar las actividades.
- **Redis**: Base de datos NoSQL usada como cache.
- **JUnit**: Framework de pruebas unitarias utilizado para verificar el correcto funcionamiento de los métodos.
- **Mockito**: Framework de mocking usado para simular dependencias y facilitar las pruebas unitarias en aislamiento.
- **JaCoCo**: Herramienta de análisis de cobertura de código utilizada para medir el porcentaje de código ejecutado por las pruebas y generar reportes de cobertura.
- **Swagger / OpenAPI**: Herramienta para documentar y probar los endpoints de la API de forma interactiva.
- **Docker**: permite ejecutar esta aplicación en un entorno aislado, sin necesidad de configurar manualmente dependencias o versiones.
- **Postman**: Para simular ser un cliente que hace peticiones al servidor y probar los endpoints.

## Características

### EndPoints

Rutas organizadas para interactuar con los gastos y el presupuesto del usuario. Operaciones soportadas:

- **Budget**:
  - Obtener el presupuesto.
  - Guardar el presupuesto.
  - Actualizar el presupuesto.
  - Eliminar el presupuesto.
- **Expenses**
  - Listar todos los gastos.
  - Obtener un gasto específico por su ID.
  - Crear nuevos gastos. Para ello se debe ingresar el nombre, monto, categoría y fecha del gasto.
  - Actualizar gastos existentes.
  - Eliminar gastos individuales.
  - Eliminar todos los gastos.
- **ExpenseCategories**
  - Listar todas las categorías de gastos disponibles, entre estas se incluyen: ahorro, comida, casa, diversión, salud, suscripciones, mascotas y otros.

### Gestor de base de datos

- Integración con MongoDB para la manipulación de datos.
- La base de datos NoSQL cuenta con dos colecciones: una gestiona la información del presupuesto y la otra gestiona la información de los gastos.

### Validaciones

Se emplean las siguientes validaciones:

- No se permite que los atributos **nombre** y **categoría** de los gastos se reciban vacíos o con puros espacios en blanco.
- No se permite que el atributo **monto** del gasto se reciba con un valor menor o igual a cero.
- No se permite que el atributo **monto** del presupuesto se reciba con un valor menor a 500 (valor arbitrariamente seleccionado).
- No se permite que el atributo **fecha de creación** del presupuesto se reciba con una fecha posterior al día de hoy.

### Eventos de ciclo de vida de objetos de las clases entity

- Cada vez que se actualiza la información de algún gasto, se actualizará automáticamente el campo `updateAt` de ese mismo registro.

### Patrones

#### Patrón arquitectónico

- **MVC**: Usado para separar en diferentes capas el código del proyecto.
- **DTO**: Usado para transferir datos entre las diferentes capas de la aplicación sin exponer directamente las entidades del dominio.

#### Patrón de caché

- **Cache-Aside**: Usado para mejorar los tiempos de respuesta y reducir la carga sobre la base de datos.

#### Patrón creacional

- **Singleton Container**: Usado para levantar una unica instancia de los contenedores de MongoDB y Redis, los cuales son contenedores que son usados por todas las pruebas de integración, evitando levantar nuevos contenedores para cada clase de prueba.

#### Patrón de resilencia

- **Circuit breaker**: Usado para proteger las llamadas realizadas a Redis. Su objetivo es evitar que fallos repetidos en Redis afecten el rendimiento o la disponibilidad de la aplicación.

### Docker

Este proyecto utiliza Docker para crear un entorno de ejecución aislado y reproducible, asegurando que la aplicación funcione igual en cualquier sistema.

Archivos relevantes:

- `Dockerfile`: Define la imagen base y cómo se construye el entorno del proyecto.
- `docker-compose.yml`: Orquesta los servicios (aplicación, base de datos y Redis) para facilitar la ejecución local.
- `.env`: Contiene variables de entorno usadas por Docker (no se incluyen en el repositorio por seguridad).

## Estructura del Proyecto

### Código fuente de la aplicación

- `controllers/`: Carpeta donde se almacenan las clases que manejan las solicitudes HTTP y definen los endpoints de la API.
- `services/`: Carpeta donde se almacenan las clases que contienen el código relacionado con la lógica de negocio.
- `repositories/`: Carpeta donde se almacenan las interfaces que extienden de una interfaz que permite el manejo de datos.
- `entities/`: Carpeta donde se almacenan las clases que se mapean con sus respectivas colecciones en la base de datos.
- `events/`: Carpeta donde se almacenan las clases que manejan acciones antes o después de realizar alguna de las operaciones CRUD.

### Código de pruebas

- `controllers/`: Contiene las clases de prueba que validan el comportamiento de los métodos en los controladores del código fuente.
- `services/`: Incluye las clases de prueba dedicadas a verificar el correcto funcionamiento de los métodos dentro de los servicios de la aplicación.
- `data/`: Almacena clases con datos simulados (mock data) utilizados durante la ejecución de las pruebas.
- `integrations/`: Contiene las clases de prueba que validan el comportamiento completo de los controladores (tests de integración).
- `resources/`: Almacena los datos en formato JSON utilizados como insumos para las pruebas de integración.

## Demo

Puedes ver una demo del proyecto en el siguiente enlace: [Control de Gastos](https://serene-frangollo-9ddb20.netlify.app/).

**Nota:** La demo del proyecto es únicamente demostrativa. Esta no está enlazada con el backend.

## Futuras mejoras

- Cambiar de gestor de base de datos por oracle.
- Modificar la tabla de budgets
  - Se agregaran los siguietnes atributos: fecha_inicio (sera el valor del gasto mas antiguo de la lista), fecha_final (sera el valor del gasto mas reciente de la lista) y total_gastado.
  - Tendra la relacion de 1 a muchos con la tabla gastos.
  - Crear el servicio que guarde una lista de gastos, esto hara la insercion en la tabla budget junto con la insercion de la lista de gastos.
  - Crear el servicio que liste todos los gastos con base a un id_presupuesto.
- Agregar spring security.
  - Usara jwt
  - Tendra los roles de usuario y admin.
- Crear la tabla de users.
  - Tendra la relacion de uno a muchos con la tabla presupuesto.
- Actualizar el readme.
- Despliegue de la aplicacion en AWS.
- Despligue automatico usando jenkins.

---

# Expense Tracker (Backend)

Expense Tracker is a backend application built with **Java**, **Spring Boot**, **MongoDB**, and **Redis**, designed to manage a budget and multiple user expenses. This project provides a RESTful API that allows users to create, read, update, and delete expenses, as well as perform the same operations for a single budget.

## Technologies Used

- **Spring Boot**: Framework for building Java applications. This project uses version `3.4.0`.

  - **Jakarta Validation**: Used for input data validation.
  - **Lifecycle Events**: A class extending `AbstractMongoEventListener` is implemented to handle actions before or after CRUD operations on `Expense` objects.
- **Java**: Main programming language. This project uses **JDK 17**.
- **Maven**: Dependency management and project build tool.
- **MongoDB**: NoSQL database used to store application data.
- **Redis**: NoSQL database used as a cache layer.
- **JUnit**: Unit testing framework used to verify the correct behavior of methods.
- **Mockito**: Mocking framework used to simulate dependencies and facilitate isolated unit testing.
- **JaCoCo**: Code coverage analysis tool used to measure the percentage of code executed by tests and generate coverage reports.
- **Swagger / OpenAPI**: Tool used to document and test API endpoints interactively.
- **Docker**: Allows the application to run in an isolated environment without manually configuring dependencies or versions.
- **Postman**: Used to simulate client requests and test API endpoints.

## Features

### Endpoints

Organized routes for interacting with user expenses and budgets. Supported operations include:

- **Budget**
  - Retrieve the budget.
  - Save a budget.
  - Update a budget.
  - Delete a budget.

- **Expenses**

  - Retrieve all expenses.
  - Retrieve a specific expense by its ID.
  - Create new expenses. The expense name, amount, category, and date must be provided.
  - Update existing expenses.
  - Delete individual expenses.
  - Delete all expenses.

- **Expense Categories**

  - Retrieve all available expense categories, including: savings, food, housing, entertainment, health, subscriptions, pets, and others.

### Database Management

- Integration with MongoDB for data persistence and manipulation.
- The NoSQL database contains two collections:

  - One collection manages budget information.
  - One collection manages expense information.

### Validations

The following validations are implemented:

- Expense **name** and **category** cannot be null, empty, or contain only blank spaces.
- The **amount** attribute of an expense cannot be less than or equal to zero.
- The **amount** attribute of a budget cannot be less than 500 (an arbitrarily selected value).
- The **budget creation date** cannot be later than the current date.

### Entity Lifecycle Events

- Whenever an expense is updated, the `updatedAt` field is automatically updated for that record.

### Design Patterns

#### Architectural Patterns

- **MVC (Model-View-Controller)**: Used to separate the project into different layers.
- **DTO (Data Transfer Object)**: Used to transfer data between application layers without exposing domain entities directly.

#### Caching Pattern

- **Cache-Aside**: Used to improve response times and reduce database load.

#### Creational Pattern

- **Singleton Container**: Used to create a single instance of MongoDB and Redis containers, which are shared across all integration tests, avoiding the overhead of creating new containers for each test class.

#### Resilience Pattern

- **Circuit Breaker**: Used to protect Redis calls. Its purpose is to prevent repeated Redis failures from affecting application performance or availability.

### Docker

This project uses Docker to create an isolated and reproducible execution environment, ensuring the application behaves consistently across different systems.

Relevant files:

- `Dockerfile`: Defines the base image and how the project environment is built.
- `docker-compose.yml`: Orchestrates services (application, database, and Redis) to simplify local execution.
- `.env`: Contains environment variables used by Docker (not included in the repository for security reasons).

## Project Structure

### Application Source Code

- `controllers/`: Contains classes responsible for handling HTTP requests and defining API endpoints.
- `services/`: Contains classes responsible for business logic.
- `repositories/`: Contains interfaces that provide data access functionality.
- `entities/`: Contains classes mapped to their corresponding database collections.
- `events/`: Contains classes that handle actions before or after CRUD operations.

### Test Code

- `controllers/`: Contains test classes that validate controller behavior.
- `services/`: Contains test classes that verify service-layer functionality.
- `data/`: Stores mock data used during test execution.
- `integrations/`: Contains integration tests that validate complete controller behavior.
- `resources/`: Stores JSON files used as input data for integration tests.

## Demo

You can view a demo of the project at the following link:

- Expense Tracker: https://serene-frangollo-9ddb20.netlify.app/

**Note:** The demo is for demonstration purposes only and is not connected to the backend.

## Future Improvements

- Replace the current database management system with Oracle.
- Modify the budgets table.

  - Add the following attributes:

    - `start_date` (the oldest expense date in the expense list).
    - `end_date` (the most recent expense date in the expense list).
    - `total_spent`.
  - Establish a one-to-many relationship with the expenses table.
  - Create a service that saves a list of expenses. This service will insert a budget record along with the associated expense records.
  - Create a service that retrieves all expenses based on a `budget_id`.
- Add Spring Security.

  - Use JWT authentication.
  - Support `USER` and `ADMIN` roles.
- Create a users table.

  - Establish a one-to-many relationship with the budgets table.
- Update the README.
- Deploy the application to AWS.
- Implement automated deployment using Jenkins.
