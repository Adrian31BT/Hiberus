# Proyecto Hiberus

Sistema de gestión de empleados con métricas y administración departamental.

## Descripción General

Este proyecto implementa una aplicación web para la gestión de empleados y departamentos. Incluye funcionalidades para visualizar métricas clave, administrar información de empleados y gestionar departamentos.

## Componentes Principales

### Backend (Spring Boot)

**Módulos:**

- **Controllers**: Maneja las solicitudes HTTP y define los endpoints de la API
- **Models**: Define las entidades de la base de datos como Department y Employee
- **Services**: Contiene la lógica de negocio para la manipulación de datos
- **Repositories**: Interfaces para el acceso a datos y operaciones CRUD
- **DTOs**: Objetos para transferencia de datos optimizada entre capas
- **Exceptions**: Implementa el manejo centralizado de errores y excepciones personalizadas
- **Config**: Contiene configuraciones como WebConfig para CORS (solo para desarrollo)

**Principales APIs:**

| Endpoint | Método | Descripción |
|----------|--------|-------------|
| `/api/employee/all` | GET | Obtiene todos los empleados |
| `/api/department/create` | POST | Crea un nuevo registro para departamentos |
| `/api/department/delete/{deparmentId}` | POST | Elimina de manera lógica un registro de departamento |
| `/api/employee/create/{departmentId}` | POST | Crea un nuevo registro de empleado que pertenece a un departamento dentro de la compañia |
| `/api/employee/delete/{employeeId}` | POST | Elimina de manera lógica un registro de empleado que pertenece a un departamento dentro de la compañia |
| `/api/employee/highestSalary` | GET | Devuelve el nombre del empleado y su salario correspondiente al más alto (en caso de valores repetidos, solo devuelve uno) |
| `/api/employee/lowerAge` | GET | Devuelve el nombre del empleado y su edad correspondiente al más joven (en caso de valores repetidos, solo devuelve uno) |
| `/api/employee/countLastMonth` | GET | Devuelve el número de empleados que ingresaron en el último mes, tomando como la fecha actual hacia 1 mes atrás |

### Frontend (Angular 19)

**Módulos Principales:**

- **Pages**: Contiene las páginas principales (employees, metrics)
- **Services**: Servicios para comunicación con el backend
- **Components**: Componentes reutilizables como formularios y tablas
- **Interfaces**: Definiciones de tipos para modelado de datos
- **Templates**: Plantillas compartidas como menú de navegación

**Características de la Interfaz:**

- Dashboard con métricas clave
- Gestión de empleados con tabla interactiva
- Formularios con validación para crear empleados

## Tecnologías

### Backend

- Java 21
- Spring Boot 3.4.5
- JPA/Hibernate
- REST API

### Frontend

- Angular 19.2.3
- Bootstrap
- TypeScript

### Infraestructura

- Docker para contenerización
- Docker Compose para orquestación

## Instalación y Ejecución

### Con Docker

```bash
# Clonar el repositorio
git clone https://github.com/Adrian31BT/Hiberus.git
cd Hiberus

# Iniciar los servicios
docker-compose up -d
```

### La aplicación estará disponible en:

- Frontend: http://localhost:4200
- Backend: http://localhost:8080

### Desarrollo local

## Backend:

```bash
cd backend
mvn clean install
mvn spring-boot:run
```

## Frontend:

```bash
cd frontend
npm install
ng serve
```

## Funcionalidades Clave

1. **Dashboard de Métricas**
   Visualización del empleado con mayor salario
   Empleado más joven
   Conteo de empleados del último mes
2. **Gestión de Empleados**
   Listado con filtrado y ordenación
   Creación y edición mediante formularios modales
   Validación de datos en tiempo real
3. **Administración de Departamentos**
   Visualización de departamentos disponibles
   Asociación de empleados con departamentos

## Modelo de Datos

### Empleado
- ID
- Nombre
- Apellido
- Edad
- Salario
- Fecha de inicio
- Fecha de fin (opcional)
- Estado (Activo/Inactivo)
- Departamento (relación)

### Departamento

- ID
- Nombre
- Estado

## Requisitos

- Node.js v22.12.0
- Angular CLI: 19.2.3
- JDK 21
- Docker y Docker Compose
- Maven

## Estructura del Proyecto

El proyecto está organizado conceptualmente en:

- **API REST**: Capa de controladores que expone funcionalidades
- **Lógica de negocio**: Implementada en servicios
- **Acceso a datos**: Mediante repositorios JPA
- **Interfaz de usuario**: Componentes Angular

## Contacto

Ing. Adrian Bacilio Tumbaco - adrian31baciliot@gmail.com
