# **Proyecto CRUD + Login MVC**

> Aplicación web desarrollada con **Java 21 y Spring Boot** para gestionar usuarios con autenticación y autorización simple (rol único).

---

## **Tabla de contenidos**

* [Descripción](#descripción)
* [Estado del proyecto](#estado-del-proyecto)
* [Características principales](#características-principales)
* [Requisitos](#requisitos)
* [Instalación y ejecución](#instalación-y-ejecución)
* [Configuración de la base de datos (SQL Server)](#configuración-de-la-base-de-datos-sql-server)
* [Recomendaciones a futuro](#recomendaciones-a-futuro)
* [Autores](#autores)

---

## **Descripción**

Proyecto CRUD + Login MVC es una aplicación base con arquitectura **Modelo–Vista–Controlador (MVC)** desarrollada con **Spring Boot** y **Thymeleaf**, que permite realizar autenticación de usuarios y operaciones CRUD sobre la entidad *Usuarios*.

**Propósito:** servir como núcleo para el futuro sistema CORE, ofreciendo una estructura estable y fácilmente extensible.  
**Motivación:** disponer de una base sólida en Java para gestionar usuarios y entidades, con conexión a SQL Server y buenas prácticas mínimas de seguridad y persistencia.

---

## **Estado del proyecto**

**En desarrollo**

Actualmente cuenta con:
- Login funcional (Spring Security).
- CRUD completo de usuarios.
- Persistencia con SQL Server.

---

## **Características principales**

* Autenticación y autorización mediante Spring Security.  
* Gestión de usuarios con rol único.  
* CRUD para **Usuarios**.  
* Integración con **SQL Server** mediante Spring Data JPA.  
* Plantillas dinámicas con **Thymeleaf**.  
* Configuración automática de tablas (`ddl-auto=update`).  

---

## **Requisitos**

* **Java 21 (JDK 21)**
* **SQL Server** (local o remoto con TCP/IP habilitado, y con conexión activa en el puerto 1433)
* **IntelliJ IDEA** (IDE que permite ejecutar la aplicación)

---

## **Instalación y ejecución**

### Pasos para ejecutar el proyecto

1. Clona el repositorio:  
   ```bash
   git clone https://github.com/tom-ayu/proyectofin-web-mvc.git
   ```
2. Abre el proyecto en **IntelliJ IDEA**.  
3. Abre el archivo `application.properties` y asegurarse de que contenga las siguientes configuraciones:  
   ```properties
   spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=ProyectoWeb;encrypt=false;trustServerCertificate=true
   spring.datasource.username=adminsql
   spring.datasource.password=AdminSQL123!
   spring.datasource.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver

   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   spring.jpa.properties.hibernate.format_sql=true
   spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.SQLServerDialect

   logging.level.org.springframework.security=TRACE

   spring.thymeleaf.cache=false
   ```
4. Asegúrate de haber creado la base de datos siguiendo el script del siguiente apartado.  
5. Ejecuta la aplicación desde IntelliJ:
   - Abre la clase principal (`ProyectofinWebMvcApplication.java`).
   - Haz clic derecho → **Run 'ProyectofinWeb...main()'**.  
6. Accede a la aplicación en tu navegador en  
   `http://localhost:8080`

---

## **Configuración de la base de datos (SQL Server)**

Asegúrate de tener habilitado el protocolo **TCP/IP** en tu SQL Server Configuration Manager.  
Luego, ejecuta el siguiente script en **SQL Server Management Studio**:

```sql
-- Crear base de datos
CREATE DATABASE ProyectoWeb;
GO

-- Crear login
CREATE LOGIN adminsql WITH PASSWORD = 'AdminSQL123!', CHECK_POLICY = OFF, CHECK_EXPIRATION = OFF;
GO

-- Crear usuario para la base de datos
USE ProyectoWeb;
GO
CREATE USER adminsql FOR LOGIN adminsql;
ALTER ROLE db_owner ADD MEMBER adminsql;
GO
```

> Este script crea la base de datos `ProyectoWeb` y un usuario con permisos de administración.  
> Asegúrate de que tu conexión en `application.properties` coincida con estos valores.

---

## **Recomendaciones a futuro**

* Adaptar este **CRUD + Login** a la estructura y lógica del sistema **CORE**.  
* Implementar gestión de múltiples roles y permisos.  
* Agregar validaciones avanzadas y mensajes de error personalizados.  
* Externalizar credenciales mediante variables de entorno.  
* Optimizar vistas Thymeleaf con componentes reutilizables.  

---

## **Autores**

| Nombre  | GitHub |
|--------:|:------:|
| Jessica Olalla | [@Jess15](https://github.com/Wikiniki15) |
| Tomás Ontaneda | [@TomOnt](https://github.com/tom-ayu) |
