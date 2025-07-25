
# 🚀 Event Ingestor Service

[![Java](https://img.shields.io/badge/Java-21+-red?style=flat&logo=openjdk)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?style=flat&logo=springboot)](https://spring.io/projects/spring-boot)
[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](LICENSE)

`event-ingestor-service` es el primer microservicio de nuestra **Plataforma Distribuida de Procesamiento y Análisis de Eventos**.  
Su función principal es **recibir eventos de log** a través de una **API REST**, aplicar **cifrado/hashing** y publicarlos en un **broker de mensajería** para su posterior procesamiento asíncrono.

---

## 📑 Tabla de Contenidos
- [🌟 Características](#-características)
- [🛠 Tecnologías](#-tecnologías)
- [⚙️ Configuración](#️-configuración)
- [🧪 Uso de la API](#-uso-de-la-api)
- [📜 Licencia](#-licencia)

---

## 🌟 Características
- **Ingesta de Eventos REST:** Endpoint HTTP `POST` para logs en JSON.
- **Procesamiento de Seguridad:** Hashing y cifrado de campos sensibles.
- **Publicación Asíncrona:** Envía eventos a Kafka (o RabbitMQ).
- **Desarrollo Rápido:** Basado en Spring Boot 3.x.
- **Contenerización:** Compatible con Docker.

---

## 🛠 Tecnologías
- **Lenguaje:** Java 21+
- **Framework:** Spring Boot 3.x
- **Dependencias:** Maven, Lombok
- **Servicios Web:** Spring Web (REST API)
- **Herramientas:** Docker, DevTools

---

## ⚙️ Configuración
<details>
<summary><b>1. Prerequisitos</b></summary>

- JDK **21+**
- Maven **3.x+**
- Git
- IDE (IntelliJ, VS Code, Eclipse)
- Postman o Insomnia
</details>

<details>
<summary><b>2. Clonar el Repositorio</b></summary>

```bash
git clone https://github.com/HarolRiosDev/event-ingestor-service.git
cd event-ingestor-service
```
</details>

<details>
<summary><b>3. Compilar y Ejecutar</b></summary>

```bash
mvn clean package
java -jar target/event-ingestor-service-0.0.1-SNAPSHOT.jar
```

O ejecuta directamente:

```bash
mvn spring-boot:run
```
</details>

---

## 🧪 Uso de la API
- **Base URL:** `http://localhost:8080/api`
- **Swagger UI:** `http://localhost:8080/swagger-ui.html`

### ➕ Ejemplo de Petición
```bash
curl -X POST "http://localhost:8080/api/logs" -H "Content-Type: application/json" -d '{
  "level": "INFO",
  "message": "User login successful",
  "sourceIp": "192.168.1.5"
}'
```

---

## 📜 Licencia
Este proyecto está bajo la [Licencia Apache 2.0](LICENSE).
