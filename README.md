# Control de Gastos (Backend)

Control de Gastos es un backend construido con **Spring Boot** y **MongoDB**, diseñado para gestionar un presupuesto y múltiples gastos realizados por el usuario. Este proyecto proporciona una API RESTful que permite crear, leer, actualizar y eliminar gastos, así como realizar estas mismas acciones para un único presupuesto.

## Tecnologías Utilizadas

- **Spring Boot**: Framework para construir aplicaciones Java. Particularmente en este proyecto se utiliza la versión `3.4.0`.
- **Java**: Lenguaje de programación principal. Para este proyecto en específico se utilizó el `JDK 17`.
- **Maven**: Para la gestión de dependencias y construcción del proyecto.
- **Jakarta Validation**: Validación de datos de entrada.
- **MongoDB**: Base de datos NoSQL para almacenar las actividades.
- **Postman**: Para simular ser un cliente que hace peticiones al servidor y probar los endpoints.
- **Eventos de ciclo de vida**: Se crea una clase que extiende de la clase `AbstractMongoEventListener`, la cual permite manejar acciones antes o después de realizar alguna de las operaciones CRUD sobre los objetos de la clase `Expense`.

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

- `controllers/`: Carpeta donde se almacenan las clases que manejan las solicitudes HTTP y definen los endpoints de la API.
- `services/`: Carpeta donde se almacenan las clases que contienen el código relacionado con la lógica de negocio.
- `repositories/`: Carpeta donde se almacenan las interfaces que extienden de una interfaz que permite el manejo de datos.
- `entities/`: Carpeta donde se almacenan las clases que se mapean con sus respectivas colecciones en la base de datos.
- `events/`: Carpeta donde se almacenan las clases que manejan acciones antes o después de realizar alguna de las operaciones CRUD.

## Demo

Puedes ver una demo del proyecto en el siguiente enlace: [Control de Gastos](https://serene-frangollo-9ddb20.netlify.app/).

**Nota:** La demo del proyecto es únicamente demostrativa. Esta no está enlazada con el backend.

---

# Expense Control (Backend)

Expense Control is a backend built with **Spring Boot** and **MongoDB**, designed to manage a budget and multiple expenses made by the user. This project provides a RESTful API that allows creating, reading, updating, and deleting budgets, as well as performing the same actions for individual expenses.

## Technologies Used

- **Spring Boot**: A framework for building Java applications. In this project, version `3.4.0` is used.
- **Java**: The primary programming language. For this project, `JDK 17` was used.
- **Maven**: For dependency management and project build.
- **Jakarta Validation**: For input data validation.
- **MongoDB**: NoSQL database used to store activities.
- **Postman**: Used to simulate a client making server requests and to test the endpoints.
- **Lifecycle Events**: A class extending `AbstractMongoEventListener` is created to handle actions before or after performing CRUD operations on `Expense` objects.

## Features 

- REST API with organized routes to interact with the user's expenses and budget. Supported operations:
  - Get the budget.
  - Save the budget.
  - Delete the budget.
  - List all expenses.
  - Get a specific expense by its ID.
  - Create new expenses by providing the name, amount, category, and date of the expense.
  - Update existing expenses.
  - Delete individual expenses.
  - Delete all expenses.
- Integration with MongoDB for data manipulation.
- The NoSQL database consists of two collections: one manages budget information, and the other manages expense information.
- Input data validation includes:
  - **Name** and **category** attributes of expenses cannot be empty or contain only blank spaces.
  - The **amount** attribute of an expense cannot be less than or equal to zero.
  - The **amount** attribute of the budget must be at least 500 (an arbitrarily chosen value).
  - The **creation date** of the budget cannot be a future date.
- Follows the **MVC** design pattern to separate the project code into different layers.
- Whenever an expense is updated, the `updateAt` field of that record is automatically updated.

## Project Structure

- `controllers/`: Folder containing the classes that handle HTTP requests and define the API endpoints.
- `services/`: Folder containing the classes with business logic.
- `repositories/`: Folder containing the interfaces that extend a base interface for data handling.
- `entities/`: Folder containing the classes mapped to their respective collections in the database.
- `events/`: Folder containing the classes that handle actions before or after performing CRUD operations.

## Demo

You can see a demo of the project at the following link: [Expense Control](https://serene-frangollo-9ddb20.netlify.app/).

**Note:** The project demo is for demonstration purposes only. It is not linked to the backend.

