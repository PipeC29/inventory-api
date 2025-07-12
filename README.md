# 📦 Inventory API

API RESTful completa para gestión de inventario de productos con autenticación JWT, desarrollada con Spring Boot 3, Java 17 y Maven.

## 🚀 Características

- **API RESTful** con operaciones CRUD completas para productos
- **Autenticación JWT** con tokens seguros
- **Documentación Swagger/OpenAPI 3.0** interactiva
- **Base de datos H2** en memoria con datos de prueba
- **Manejo global de excepciones** con respuestas estructuradas
- **Validaciones** con Bean Validation
- **Arquitectura limpia** con capas bien separadas
- **Seguridad** con Spring Security

## 🏗️ Arquitectura

```
├── Controller (REST endpoints)
├── Service (Lógica de negocio)
├── Repository (Acceso a datos)
└── Model (Entidades JPA)
```

## 🛠️ Tecnologías

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Security** (JWT)
- **Spring Data JPA**
- **H2 Database** (en memoria)
- **Maven** (gestión de dependencias)
- **Swagger/OpenAPI 3.0** (documentación)
- **Bean Validation** (validaciones)

## 📋 Requisitos

- Java 17 o superior
- Maven 3.6 o superior
- IDE compatible (IntelliJ IDEA, Eclipse, VS Code)

## 🚀 Instalación y Ejecución

### 1. Clonar el repositorio
```bash
git clone https://github.com/tu-usuario/inventory-api.git
cd inventory-api
```

### 2. Compilar el proyecto
```bash
mvn clean compile
```

### 3. Ejecutar la aplicación

#### Opción A: Con Maven
```bash
mvn spring-boot:run
```

#### Opción B: Con IntelliJ IDEA
1. Abrir el proyecto en IntelliJ IDEA
2. Localizar la clase `InventoryApiApplication.java`
3. Hacer clic derecho → "Run 'InventoryApiApplication'"

#### Opción C: Con JAR ejecutable
```bash
mvn clean package
java -jar target/inventory-api-1.0.0.jar
```

### 4. Verificar la instalación
La aplicación estará disponible en: `http://localhost:8080/api`

## 📚 Documentación de la API

### Swagger UI
Accede a la documentación interactiva en:
```
http://localhost:8080/api/swagger-ui/index.html
```

### Endpoints principales

#### 🔐 Autenticación
```http
POST /api/auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "password"
}
```

#### 📦 Productos
```http
# Obtener todos los productos
GET /api/products
Authorization: Bearer {token}

# Obtener producto por ID
GET /api/products/{id}
Authorization: Bearer {token}

# Crear nuevo producto
POST /api/products
Authorization: Bearer {token}
Content-Type: application/json

{
  "name": "Laptop Dell XPS 13",
  "description": "Laptop ultrabook con procesador Intel i7",
  "price": 999.99,
  "quantity": 25
}

# Actualizar producto
PUT /api/products/{id}
Authorization: Bearer {token}
Content-Type: application/json

{
  "name": "Laptop Dell XPS 13 - Actualizada",
  "description": "Laptop ultrabook con procesador Intel i7 de 11va generación",
  "price": 1099.99,
  "quantity": 20
}

# Eliminar producto
DELETE /api/products/{id}
Authorization: Bearer {token}
```

#### 🔍 Búsquedas especializadas
```http
# Buscar productos por nombre
GET /api/products/search?name=laptop
Authorization: Bearer {token}

# Productos por rango de precios
GET /api/products/price-range?minPrice=100&maxPrice=500
Authorization: Bearer {token}

# Productos con stock bajo
GET /api/products/low-stock?threshold=10
Authorization: Bearer {token}

# Productos en stock
GET /api/products/in-stock
Authorization: Bearer {token}

# Estadísticas del inventario
GET /api/products/stats
Authorization: Bearer {token}
```

## 🔑 Credenciales por Defecto

| Usuario | Contraseña | Roles |
|---------|------------|-------|
| `admin` | `password` | ADMIN, USER |
| `user`  | `password` | USER |

## 🗄️ Base de Datos

### Consola H2
Accede a la consola de base de datos en:
```
http://localhost:8080/api/h2-console
```

**Configuración de conexión:**
- **JDBC URL:** `jdbc:h2:mem:inventory`
- **Username:** `sa`
- **Password:** `password`

### Datos de Prueba
La aplicación incluye 15 productos precargados para testing, incluyendo:
- Laptops, smartphones, monitores
- Periféricos (teclados, mouse, auriculares)
- Accesorios tecnológicos

## 📝 Ejemplos de Uso

### 1. Autenticación
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "admin", "password": "password"}'
```

**Respuesta:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "username": "admin",
  "expiresIn": 86400000
}
```

### 2. Obtener productos
```bash
curl -X GET http://localhost:8080/api/products \
  -H "Authorization: Bearer {tu-token-jwt}"
```

### 3. Crear producto
```bash
curl -X POST http://localhost:8080/api/products \
  -H "Authorization: Bearer {tu-token-jwt}" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Nuevo Producto",
    "description": "Descripción del producto",
    "price": 299.99,
    "quantity": 50
  }'
```

### 4. Buscar productos
```bash
curl -X GET "http://localhost:8080/api/products/search?name=laptop" \
  -H "Authorization: Bearer {tu-token-jwt}"
```

## 🧪 Testing

### Ejecutar tests
```bash
mvn test
```

### Cobertura de código
```bash
mvn jacoco:report
```

## 📊 Estructura del Proyecto

```
inventory-api/
├── src/
│   ├── main/
│   │   ├── java/com/example/inventoryapi/
│   │   │   ├── config/          # Configuraciones
│   │   │   ├── controller/      # Controladores REST
│   │   │   ├── dto/             # Data Transfer Objects
│   │   │   ├── exception/       # Manejo de excepciones
│   │   │   ├── model/           # Entidades JPA
│   │   │   ├── repository/      # Repositorios
│   │   │   ├── security/        # Configuración JWT
│   │   │   └── service/         # Lógica de negocio
│   │   └── resources/
│   │       ├── application.yml  # Configuración Spring Boot
│   │       └── data.sql         # Datos de prueba
│   └── test/                    # Tests unitarios
├── pom.xml                      # Dependencias Maven
└── README.md                    # Documentación
```

## 🔧 Configuración

### Variables de entorno
```properties
# JWT
JWT_SECRET=mySecretKey12345678901234567890123456789012345678901234567890
JWT_EXPIRATION=86400000

# Base de datos
SPRING_DATASOURCE_URL=jdbc:h2:mem:inventory
SPRING_DATASOURCE_USERNAME=sa
SPRING_DATASOURCE_PASSWORD=password

# Servidor
SERVER_PORT=8080
```

### Profiles
```bash
# Desarrollo
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# Producción
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

## 📈 Monitoreo

### Actuator endpoints
```http
GET /api/actuator/health
GET /api/actuator/info
GET /api/actuator/metrics
```

## 🚀 Despliegue

### Docker
```dockerfile
FROM openjdk:17-jdk-slim
COPY target/inventory-api-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

```bash
docker build -t inventory-api .
docker run -p 8080:8080 inventory-api
```

## 🤝 Contribución

1. Fork el proyecto
2. Crea una rama feature (`git checkout -b feature/nueva-funcionalidad`)
3. Commit tus cambios (`git commit -m 'Agregar nueva funcionalidad'`)
4. Push a la rama (`git push origin feature/nueva-funcionalidad`)
5. Abre un Pull Request

## 📄 Licencia

Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para más detalles.

## 👥 Autor

- **PipeC29** - [GitHub](https://github.com/PipeC29/)


---

⭐ **¡Si este proyecto te fue útil, considera darle una estrella en GitHub!**
