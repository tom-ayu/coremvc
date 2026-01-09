# **Sistema CORE - Gestor de Eficiencia Operativa de Proyectos**

🌐 **Deploy público:** https://coremvc-lute.onrender.com/login

---

## **Tabla de contenidos**

- [Descripción](#descripción)
- [Estado del proyecto](#estado-del-proyecto)
- [Arquitectura y Principios de Diseño](#arquitectura-y-principios-de-diseño)
- [Características principales](#características-principales)
- [Tecnologías usadas](#tecnologías-usadas)
- [Instalación y ejecución](#instalación-y-ejecución)
- [Autores](#autores)

---

## **Descripción**

> App web desarrollada con Java 21 y Spring Boot para la gestión integral de proyectos de software, recursos y tareas, permitiendo calcular métricas clave como eficiencia de proyectos, carga de recursos y riesgos mediante parámetros como % de QA, reaperturas, horas estimadas vs reales, tiempo ajustado y desviación.


---

## **Estado del proyecto**

Estructura principal y Administrador completo y desplegado en producción.

Incluye:
- Autenticación con Spring Security (uso de Roles)
- Hasheo de contraseñas con Spring Security (BCrypt)
- Gestión de **Usuarios**, **Proyectos**, **Recursos** y **Tareas**
- Deploy en Render (con Docker)
- MS SQL Server local, H2 en producción

---

## **Arquitectura y Principios de Diseño**

### **Principios SOLID Implementados**

#### 1. **Single Responsibility Principle (SRP)**

Cada clase tiene una única responsabilidad:

- **Proyecto.java**: Solo entidad JPA (atributos + getters/setters)
- **EficienciaStrategy**: Solo calcula la eficiencia
- **CostoRealStrategy**: Solo calcula el costo real
- **DesviacionStrategy**: Solo calcula la desviación presupuestaria
- **AnalizadorRiesgos**: Solo analiza los riesgos del proyecto
- **ValidadorProyecto**: Solo valida las reglas de negocio

**Beneficios:**
- Código más mantenible y comprensible
- Facilita testing unitario
- Reduce acoplamiento entre componentes

#### 2. **Dependency Inversion Principle (DIP)**

Los módulos de alto nivel dependen de abstracciones, no de implementaciones concretas:
```java
// Controller depende de interfaz, no de implementación concreta
@Controller
public class ProyectoController {
    private final IProyectoService proyectoService; // ← Abstracción
}
```

**Beneficios:**
- Facilita testing con mocks
- Permite cambiar implementaciones sin afectar clientes
- Reduce acoplamiento entre capas

---

### **Patrones de Diseño Implementados**

#### 1. **Strategy Pattern**

Encapsula diferentes algoritmos de cálculo de métricas en estrategias intercambiables:
```
servicio/estrategias/
├── IMetricaStrategy.java          (Interfaz común)
├── EficienciaStrategy.java        (Calcula eficiencia)
├── CostoRealStrategy.java         (Calcula costo real)
└── DesviacionStrategy.java        (Calcula desviación)
```

**Ventajas:**
- Fácil agregar nuevas métricas sin modificar código existente
- Separación clara de algoritmos
- Mayor testabilidad

#### 2. **Builder Pattern**

Construcción flexible de objetos complejos con API fluida:
```java
// Construcción de reportes paso a paso
ReporteProyecto reporte = new ReporteProyecto.Builder(id, nombre)
    .conMetricas(metricas)
    .conRiesgo(true)
    .conNivelRiesgo("ALTO")
    .conDescripcion("Proyecto con desviación presupuestaria")
    .build();
```

**Ventajas:**
- Objetos inmutables (thread-safe)
- Código más legible
- Construcción flexible

**Ejemplo de uso:**
```bash
# Endpoint para generar reporte
GET http://localhost:8080/proyectos/reporte/1

# Respuesta JSON:
{
  "proyectoId": 1,
  "nombreProyecto": "Sistema de Ventas",
  "metricas": {
    "eficiencia": 0.81,
    "costoReal": 7895.0,
    "desviacion": -42105.0
  },
  "enRiesgo": true,
  "nivelRiesgo": "MEDIO"
}
```

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
