# Control de Gastos (Backend)

Control de Gastos es un backend construido con **Spring Boot** y **MongoDB**, diseñado para gestionar un presupuesto y múltiples gastos realizados por el usuario. Este proyecto proporciona una API RESTful que permite crear, leer, actualizar y eliminar gastos, así como realizar estas mismas acciones para un único presupuesto.

## Tecnologías Utilizadas

- **Spring Boot**: Framework para construir aplicaciones Java. Particularmente en este proyecto se utiliza la versión `3.4.0`.
  - **Jakarta Validation**: Validación de datos de entrada.
  - **Eventos de ciclo de vida**: Se crea una clase que extiende de la clase `AbstractMongoEventListener`, la cual permite manejar acciones antes o después de realizar alguna de las operaciones CRUD sobre los objetos de la clase `Expense`.
- **Java**: Lenguaje de programación principal. Para este proyecto en específico se utilizó el `JDK 17`.
- **Maven**: Para la gestión de dependencias y construcción del proyecto.
- **MongoDB**: Base de datos NoSQL para almacenar las actividades.
- **Postman**: Para simular ser un cliente que hace peticiones al servidor y probar los endpoints.
- **JUnit**: Framework de pruebas unitarias utilizado para verificar el correcto funcionamiento de los métodos.
- **Mockito**: Framework de mocking usado para simular dependencias y facilitar las pruebas unitarias en aislamiento.
- **Swagger / OpenAPI**: Herramienta para documentar y probar los endpoints de la API de forma interactiva.
- **Docker**: permite ejecutar esta aplicación en un entorno aislado, sin necesidad de configurar manualmente dependencias o versiones. 

## Características

### EndPoint's

Rutas organizadas para interactuar con los gastos y el presupuesto del usuario. Operaciones soportadas:
- **Budget**:
  - Obtener el presupuesto.
  - Guardar el presupuesto.
  - Eliminar el presupuesto.
- **Expenses**
  - Listar todos los gastos.
  - Obtener un gasto específico por su ID.
  - Crear nuevos gastos. Para ello se debe ingresar el nombre, monto, categoría y fecha del gasto.
  - Actualizar gastos existentes.
  - Eliminar gastos individuales.
  - Eliminar todos los gastos.
 
### Gestor de base de datos

- Integración con MongoDB para la manipulación de datos.
- La base de datos NoSQL cuenta con dos colecciones: una gestiona la información del presupuesto y la otra gestiona la información de los gastos.

### Validaciones

Se emplean las siguientes validaciones:
- No se permite que los atributos **nombre** y **categoría** de los gastos se reciban vacíos o con puros espacios en blanco.
- No se permite que el atributo **monto** del gasto se reciba con un valor menor o igual a cero.
- No se permite que el atributo **monto** del presupuesto se reciba con un valor menor a 500 (valor arbitrariamente seleccionado).
- No se permite que el atributo **fecha de creación** del presupuesto se reciba con una fecha posterior al día de hoy.

### Patrones de diseño

- Se emplea el patrón de diseño arquitectónico conocido como **MVC**, para separar en diferentes capas el código del proyecto.
- El proyecto emplea **DTOs** para separar los datos expuestos en las solicitudes y respuestas del modelo de dominio, mejorando la organización y el control de la información intercambiada.

### Eventos del ciclo de vida para los objetos de las clases entity

- Cada vez que se actualiza la información de algún gasto, se actualizará automáticamente el campo `updateAt` de ese mismo registro.

### Docker

Este proyecto utiliza Docker para crear un entorno de ejecución aislado y reproducible, asegurando que la aplicación funcione igual en cualquier sistema. 

Archivos relevantes:

- `Dockerfile`: define la imagen base y cómo se construye el entorno del proyecto.
- `docker-compose.yml`: orquesta los servicios (API y base de datos) para facilitar la ejecución local.
- `.env`: contiene variables de entorno usadas por Docker (no se incluye en el repositorio por seguridad).

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

- Desarrollar el servicio de catalogos de categoria de gastos.
- Desarrollar las pruebas para este servicio.
- Incorporar este servicio al swagger.
- Crear el perfil dev para conectarse con mongoDb atlas.
- Quizas meterle redis y el patron de diseño `Cache-aside pattern` para que se quede en cache el catalogo.
- Actualizar el readme.
- Despliegue de la aplicacion en AWS.
- Despligue automatico usando jenkins.

---

# Expense Tracker (Backend)

**Expense Tracker** is a backend built with **Spring Boot** and **MongoDB**, designed to manage a budget and multiple user expenses. This project provides a RESTful API that allows creating, reading, updating, and deleting expenses, as well as performing these same actions for a single budget.

## Technologies Used

- **Spring Boot**: Framework for building Java applications. This project specifically uses version `3.4.0`.
  - **Jakarta Validation**: For input data validation.
  - **Lifecycle Events**: A class extending `AbstractMongoEventListener` is implemented to handle actions before or after any CRUD operation performed on `Expense` objects.
- **Java**: Main programming language. This project uses `JDK 17`.
- **Maven**: Build and dependency management tool.
- **MongoDB**: NoSQL database used to store the data.
- **Postman**: Used to simulate API client requests and test endpoints.
- **JUnit**: Unit testing framework to verify correct method behavior.
- **Mockito**: Mocking framework to simulate dependencies and simplify isolated unit tests.
- **Swagger / OpenAPI**: Tool for documenting and interactively testing API endpoints.
- **Docker**: Allows running the application in an isolated environment without manually configuring dependencies or versions.

## Features

### Endpoints

Organized routes to interact with user expenses and budget. Supported operations:

- **Budget**
  - Retrieve the budget.
  - Save the budget.
  - Delete the budget.
  
- **Expenses**
  - List all expenses.
  - Retrieve a specific expense by ID.
  - Create new expenses (requires name, amount, category, and date).
  - Update existing expenses.
  - Delete individual expenses.
  - Delete all expenses.

### Database Handling

- Integration with MongoDB for data manipulation.
- The NoSQL database uses two collections:
  - One for budget information.
  - One for expense information.

### Validations

The following validations are applied:

- Expense **name** and **category** cannot be empty or contain only whitespace.
- Expense **amount** cannot be less than or equal to zero.
- Budget **amount** cannot be less than 500 (arbitrary business rule).
- Budget **creation date** cannot be a future date.

### Design Patterns

- Architectural pattern **MVC** to separate application logic into layers.
- Use of **DTOs** to separate request/response data from domain models, improving structure and data control.

### Entity Lifecycle Events

- Every time an expense is updated, its `updatedAt` field is automatically refreshed.

### Docker

This project uses Docker to create a reproducible, isolated runtime environment, ensuring consistent behavior across systems.

Relevant files:

- `Dockerfile`: Defines the base image and build instructions for the project environment.
- `docker-compose.yml`: Orchestrates services (API and database) for easier local execution.
- `.env`: Contains environment variables used by Docker (not included in the repository for security reasons).

## Project Structure

### Application Source Code

- `controllers/`: Contains classes that handle HTTP requests and define API endpoints.
- `services/`: Contains classes with business logic.
- `repositories/`: Contains interfaces extending Spring Data components for data handling.
- `entities/`: Contains classes mapped to their respective MongoDB collections.
- `events/`: Contains classes that handle pre- and post-CRUD logic.

### Test Code

- `controllers/`: Contains tests validating controller behavior.
- `services/`: Includes tests for service-layer logic.
- `data/`: Contains mock data classes used during testing.
- `integrations/`: Contains integration tests validating full controller behavior.
- `resources/`: Stores JSON files used as test input for integration tests.

## Demo

You can view a live demo of the project here:  
**[Expense Tracker Demo](https://serene-frangollo-9ddb20.netlify.app/)**

**Note:** The demo is for display purposes only. It is **not** connected to the backend.

## Future Improvements

- Develop a category catalog service.
- Add tests for this new service.
- Add this service to Swagger documentation.
- Create the dev profile to connect to MongoDB Atlas.
- Possibly integrate Redis and implement the **Cache-aside pattern** to cache the catalog.
- Update the README.
- Deploy the application on AWS.
- Implement automated deployment using Jenkins.
