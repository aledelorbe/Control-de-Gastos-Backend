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

## Características

- API REST con rutas organizadas para interactuar con los gastos y el presupuesto del usuario. Operaciones soportadas:
  - Obtener el presupuesto.
  - Guardar el presupuesto.
  - Eliminar el presupuesto.
  - Listar todos los gastos.
  - Obtener un gasto específico por su ID.
  - Crear nuevos gastos. Para ello se debe ingresar el nombre, monto, categoría y fecha del gasto.
  - Actualizar gastos existentes.
  - Eliminar gastos individuales.
  - Eliminar todos los gastos.
- Integración con MongoDB para la manipulación de datos.
- La base de datos NoSQL cuenta con dos colecciones: una gestiona la información del presupuesto y la otra gestiona la información de los gastos.
- Validación de datos de entrada. Se emplean las siguientes validaciones:
  - No se permite que los atributos **nombre** y **categoría** de los gastos se reciban vacíos o con puros espacios en blanco.
  - No se permite que el atributo **monto** del gasto se reciba con un valor menor o igual a cero.
  - No se permite que el atributo **monto** del presupuesto se reciba con un valor menor a 500 (valor arbitrariamente seleccionado).
  - No se permite que el atributo **fecha de creación** del presupuesto se reciba con una fecha posterior al día de hoy.
- Se emplea el patrón de diseño arquitectónico conocido como **MVC**, para separar en diferentes capas el código del proyecto.
- Cada vez que se actualiza la información de algún gasto, se actualizará automáticamente el campo `updateAt` de ese mismo registro.

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

---

# Expense Tracker (Backend)

Expense Tracker is a backend built with **Spring Boot** and **MongoDB**, designed to manage a budget and multiple expenses made by the user. This project provides a RESTful API that allows creating, reading, updating, and deleting expenses, as well as performing these same actions for a single budget.

## Technologies Used

- **Spring Boot**: Framework for building Java applications. In this project, version `3.4.0` is used.
  - **Jakarta Validation**: For validating input data.
  - **Lifecycle Events**: A class extends `AbstractMongoEventListener` to handle actions before or after performing any CRUD operations on `Expense` objects.
- **Java**: Main programming language. This project specifically uses `JDK 17`.
- **Maven**: For dependency management and project build.
- **MongoDB**: NoSQL database for storing activities.
- **Postman**: To simulate a client making requests to the server and test the endpoints.
- **JUnit**: Unit testing framework used to verify the correct behavior of methods.
- **Mockito**: Mocking framework used to simulate dependencies and facilitate isolated unit tests.

## Features

- REST API with organized routes to interact with the user's expenses and budget. Supported operations:
  - Get the budget.
  - Save the budget.
  - Delete the budget.
  - List all expenses.
  - Get a specific expense by its ID.
  - Create new expenses. You must provide the name, amount, category, and date of the expense.
  - Update existing expenses.
  - Delete individual expenses.
  - Delete all expenses.
- Integration with MongoDB for data manipulation.
- The NoSQL database has two collections: one manages the budget information, and the other manages the expense information.
- Input data validation. The following validations are applied:
  - The **name** and **category** fields of expenses must not be empty or contain only whitespace.
  - The **amount** field of an expense must not be less than or equal to zero.
  - The **amount** field of the budget must not be less than 500 (an arbitrarily chosen value).
  - The **creation date** of the budget must not be a future date.
- Uses the **MVC** architectural design pattern to separate the project's code into different layers.
- Whenever an expense is updated, the `updateAt` field of that record is automatically updated.

## Project Structure

### Application source code

- `controllers/`: Folder containing the classes that handle HTTP requests and define the API endpoints.
- `services/`: Folder containing the classes with the business logic.
- `repositories/`: Folder containing interfaces that extend a base interface to manage data access.
- `entities/`: Folder containing the classes mapped to their respective collections in the database.
- `events/`: Folder containing the classes that handle actions before or after performing any CRUD operations.

### Test code

- `controllers/`: Contains the test classes that validate the behavior of the controller methods in the source code.
- `services/`: Includes the test classes dedicated to verifying the correct behavior of the methods within the application's services.
- `data/`: Stores classes with mock data used during test execution.
- `integrations/`: Contains the test classes that validate the complete behavior of the controllers (integration tests).
- `resources/`: Stores JSON data used as input for integration tests.

## Demo

You can see a demo of the project at the following link: [Expense Tracker](https://serene-frangollo-9ddb20.netlify.app/).

**Note:** The project demo is for demonstration purposes only. It is not connected to the backend.
