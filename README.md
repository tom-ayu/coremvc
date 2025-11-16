# **Sistema CORE - Gestor de Eficiencia Operativa de Proyectos**

🌐 **Demo en vivo:** https://coremvc-lute.onrender.com/login

🎥 **Video explicativo:** https://youtu.be/RBRhDk59vPY

---

## **Tabla de contenidos**

* [Descripción](#descripción)
* [Estado del proyecto](#estado-del-proyecto)
* [Características principales](#características-principales)
* [Tecnologías usadas](#tecnologías-usadas)
* [Requisitos](#requisitos)
* [Instalación y ejecución](#instalación-y-ejecución)
* [Configuración de la base de datos](#configuración-de-la-base-de-datos)
* [Arquitectura del sistema](#arquitectura-del-sistema)
* [Credenciales de acceso](#credenciales-de-acceso)
* [Deploy en producción](#deploy-en-producción)
* [Autores](#autores)

---

## **Descripción**

> Aplicación web desarrollada con **Java 21 y Spring Boot** para la gestión integral de proyectos, recursos humanos y tareas con seguimiento de tiempos y estados.

**Propósito:** Facilitar la gestión operativa mediante seguimiento de presupuestos, horas estimadas/reales y asignación de recursos.

**Motivación:** Crear un sistema robusto con validaciones en back-end, relaciones entre entidades y seguridad basada en roles, listo para producción.

---

## **Estado del proyecto**

Estructura principal y Administrador completo y desplegado en producción.

Incluye:
- Autenticación/Autorización con Spring Security
- Roles ADMIN/USER
- Gestión de **Usuarios**, **Proyectos**, **Recursos** y **Tareas**
- Deploy en Render (con Docker)
- MS SQL Server local, H2 en producción

---

## **Características principales**

### Autenticación y Seguridad
* Spring Security + BCrypt
* Control por roles
* Home dinámico por rol
* Errores personalizados

### Gestión de Usuarios
* CRUD
* Roles ADMIN/USER
* Activación/desactivación

### Gestión de Proyectos
* Presupuesto total
* Horas estimadas
* % QA configurable
* Validaciones backend

### Gestión de Recursos
* Roles técnicos
* Horas disponibles
* Costo por hora

### Gestión de Tareas
* Relación a proyectos/recursos
* Estados y prioridades
* Estimación vs tiempo real
* Reaperturas
* Dropdowns dinámicos

---

## **Tecnologías usadas**

### Desarrollo
* Java 21
* Spring Boot 3.5.6
* Maven 3
* MS SQL Server
* IntelliJ IDEA

### Producción
* Docker
* Render

---

## Instalación y ejecución

### 1. Clonar el repositorio

```bash
git clone https://github.com/tom-ayu/coremvc.git
cd coremvc
```

### 2. Configurar SQL Server (BD local)

* Asegúrate de tener habilitado el protocolo **TCP/IP** en *SQL Server Configuration Manager*.
* Ejecuta el siguiente script en *SQL Server Management Studio*:

```sql
-- Crear base de datos
CREATE DATABASE ProyectoWeb;
GO

-- Crear login
CREATE LOGIN [USUARIO] WITH PASSWORD = '[CONTRASEÑA]', CHECK_POLICY = OFF, CHECK_EXPIRATION = OFF;
GO

-- Crear usuario para la base de datos
USE ProyectoWeb;
GO
CREATE USER [USUARIO] FOR LOGIN [USUARIO];
ALTER ROLE db_owner ADD MEMBER [USUARIO];
GO
```
###### NOTA: Cambiar [USUARIO] y [CONTRASEÑA] por elección personal.


### 3. Configurar la conexión con la base de datos (local)

Ejecuta el script SQL proporcionado en la sección **Configuración de la base de datos**.

Verifica que el archivo `src/main/resources/application.properties` contenga lo siguiente:

```properties
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=ProyectoWeb;encrypt=false;trustServerCertificate=true
spring.datasource.username=[USUARIO]
spring.datasource.password=[CONTRASEÑA]
spring.datasource.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.SQLServerDialect

logging.level.org.springframework.security=TRACE

spring.thymeleaf.cache=false

server.error.whitelabel.enabled=false
server.error.include-message=always
server.error.include-stacktrace=on_param
server.error.include-exception=true
```

### 3. Ejecutar la aplicación

1. Abre el proyecto en IntelliJ.
2. Localiza la clase principal: `ProyectofinWebMvcApplication.java`.
3. Clic derecho → **Run 'ProyectofinWebMvcApplication'**.

### 4. Acceder a la aplicación

Accede a **[http://localhost:8080](http://localhost:8080)** en tu navegador.

---

## **Autores**

| Nombre  | GitHub |
|--------:|:------:|
| Jessica Olalla | [@Jess15](https://github.com/Wikiniki15) |
| Tomás Ontaneda | [@TomOnt](https://github.com/tom-ayu) |
