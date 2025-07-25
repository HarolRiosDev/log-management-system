# 🚀 Event Ingestor Service

Este repositorio contiene el microservicio `event-ingestor-service`, el primer componente de nuestra **Plataforma Distribuida de Procesamiento y Análisis de Eventos**. Su función principal es recibir eventos de log entrantes a través de una API REST, aplicar un procesamiento inicial de seguridad (cifrado/hashing) y publicar estos eventos en un sistema de mensajería para su posterior procesamiento asíncrono.

---

## 🌟 Características Principales

* **Ingesta de Eventos REST:** Proporciona un endpoint HTTP POST para la recepción de eventos de log en formato JSON.
* **Procesamiento de Seguridad:** Implementa la lógica de **criptografía** para el cifrado y/o hashing de campos sensibles dentro de los eventos recibidos.
* **Publicación de Eventos:** Envía los eventos procesados (y seguros) a un broker de mensajería (Kafka/RabbitMQ) para desacoplar el proceso de ingesta del procesamiento posterior.
* **Desarrollo en Spring Boot:** Construido con el framework Spring Boot para un desarrollo rápido y eficiente.
* **Contenerización:** Preparado para ser empaquetado y desplegado como un contenedor Docker.

---

## 🛠️ Tecnologías Utilizadas

* **Lenguaje:** Java 21+
* **Framework:** Spring Boot 3.x
* **Gestor de Dependencias:** Maven
* **Servicios Web:** Spring Web (RESTful API)
* **Utilidades:** Project Lombok (para reducir boilerplate)
* **Herramientas de Desarrollo:** Spring Boot DevTools

---

## 🚀 Cómo Empezar

Sigue estos pasos para levantar y probar el servicio localmente.

### Prerequisitos

Asegúrate de tener instalado en tu sistema:

* JDK 21+
* Maven 3.x+
* Git
* Un IDE (IntelliJ IDEA, VS Code, Eclipse)
* Postman o Insomnia (para probar la API)

### 1. Clonar el Repositorio

```bash
git clone [https://github.com/HarolRiosDev/event-ingestor-service.git](https://github.com/HarolRiosDev/event-ingestor-service.git)
cd event-ingestor-service
