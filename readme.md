README.md: ProyectoFinal (Plataforma Web de Inquilinos)

🌟ProyectoFinal: Plataforma Web Colaborativa para Inquilinos
Autor: [Tu Nombre Completo / Nombres de los Integrantes del Grupo]
Fecha de Inicio del Proyecto: 20 de julio de [Año Actual, EJ: 2025]
Fecha de Presentación/Última Actualización: 31 de julio de 2025

📖Tabla de Contenidos
    - Acerca del Proyecto
    - Características
    - Tecnologías Utilizadas
    - Instalación y Uso
    - Estructura del Proyecto
    - Contribución
    - Licencia
    - Contacto

-️ Acerca del Proyecto
Este repositorio contiene el código fuente de ProyectoFinal, una plataforma web interactiva desarrollada para inquilinos que comparten un mismo propietario. Este proyecto nace de la necesidad de establecer un canal de comunicación directo y eficiente entre inquilinos para la defensa y gestión colectiva de sus derechos e intereses frente a situaciones comunes que puedan surgir con el propietario (ej. mantenimiento, servicios, subidas abusivas del alquiler…).
ProyectoFinal actúa como un foro privado y seguro donde los usuarios registrados podrán intercambiar información, coordinar reuniones, compartir documentos relevantes y organizar acciones conjuntas, fomentando la solidaridad y el empoderamiento de la comunidad de inquilinos.

- Características
CARACTERÍSTICA 1: Registro y Autenticación de Usuarios]: Sistema seguro para que los inquilinos puedan crear cuentas y acceder a la plataforma.
CARACTERÍSTICA 2: Foros de Discusión/Salas de Chat]: Espacios dedicados para la comunicación entre inquilinos, organizados por propietario o arrendador.
CARACTERÍSTICA 3: Gestión de Perfiles]: Los usuarios pueden gestionar su información básica y detalles relacionados con su vivienda.
CARACTERÍSTICA 4 (a futuro): Subida y Compartición de Documentos]: Capacidad para que los inquilinos compartan archivos relevantes (contratos, fotos de desperfectos, etc.).
CARACTERÍSTICA 5 (a futuro) Notificaciones: Alertas sobre nuevas publicaciones o respuestas en los foros, cambio de leyes, nuevas bolsa de alquiler social...
CARACTERÍSTICA 6 (a futuro) Búsqueda de Contenido: Funcionalidad para buscar mensajes o temas específicos dentro de la plataforma.

- Tecnologías Utilizadas
Lenguaje de Programación: Java
IDE: Apache NetBeans IDE 21
Sistema de Construcción (Build Tool): Maven
Tecnologías Web Java:
Spring Boot 3.3.1: El framework principal para el desarrollo del backend, que simplifica la configuración y el despliegue de aplicaciones Spring.
Spring Web (RESTful API): Utilizado para construir la API RESTful que permite la comunicación entre el frontend y el backend, manejando las peticiones HTTP y devolviendo respuestas JSON.
Base de Datos:
MySQL 8.0.36: El sistema de gestión de bases de datos relacionales utilizado para almacenar toda la información del proyecto (usuarios, contratos, ubicaciones, chats, mensajes).
Framework de Persistencia:
JPA (Java Persistence API) / Hibernate 6.5.2.Final: JPA es la especificación para la persistencia de datos en Java, y Hibernate es la implementación de ORM (Object-Relational Mapping) utilizada por Spring Data JPA para mapear objetos Java a tablas de base de datos.
Servidor de Aplicaciones:
Apache Tomcat 10.1.25: El servidor web embebido que Spring Boot incluye por defecto, encargado de ejecutar la aplicación web.
Frontend:
HTML: La estructura base de la interfaz de usuario.
CSS: Para el estilo y diseño de la aplicación.
JavaScript: Para la interactividad y la lógica del lado del cliente, incluyendo las llamadas a la API REST del backend.
Tailwind CSS: Un framework CSS de utilidad-first que se utiliza para aplicar estilos de forma rápida y responsiva directamente en el HTML.
Librerías Clave:
Spring Data JPA: Simplifica el acceso a datos y la implementación de repositorios.
Spring Boot DevTools: Herramientas para mejorar la experiencia de desarrollo, como el reinicio rápido de la aplicación.
Spring Boot Starter Security: Proporciona funcionalidades de autenticación y autorización para asegurar la aplicación.
BCryptPasswordEncoder: Utilizado por Spring Security para la encriptación segura de contraseñas.
MySQL Connector/J: El driver JDBC necesario para que la aplicación Java se conecte a la base de datos MySQL.
Jakarta Persistence API: La API estándar de Java para la persistencia de objetos, fundamental para JPA.
JUnit 5: Utilizado para escribir y ejecutar pruebas unitarias y de integración en el backend.
SLF4J API / Logback Classic: Para el registro de eventos y depuración de la aplicación.
Spring Session JDBC (Opcional): Una dependencia incluida que permite la gestión de sesiones HTTP persistentes en la base de datos, aunque su uso explícito depende de la configuración final.


- Instalación y Uso
Sigue estos pasos para obtener, configurar y ejecutar el ProyectoFinal:
Clonar el Repositorio:
Abre tu terminal y ejecuta en el Bash:
git clone https://github.com/Toniomex/ProyectoFinal.git
cd ProyectoFinal
Abrir con NetBeans:
Inicia Apache NetBeans IDE.
Ve a File > Open Project... y selecciona la carpeta ProyectoFinal. NetBeans detectará y configurará el proyecto.
Configuración de la Base de Datos:
Crea una base de datos llamada ProyectoFinal en tu servidor de BD.
Edita el archivo src/main/resources/database.properties para configurar tus credenciales de base de datos.
Ejecutar el Proyecto:
En NetBeans, asegúrate de que un servidor de aplicaciones (ej. Tomcat) esté configurado para el proyecto.
Haz clic derecho en el proyecto ProyectoFinal en la ventana "Projects".
Selecciona Run > Run Project. NetBeans compilará y desplegará la aplicación en el servidor configurado.
Acceder a la Plataforma:
Abre tu navegador web.
Navega a http://localhost:8080/
Regístrate como nuevo usuario y luego inicia sesión para acceder a las funcionalidades de la plataforma.



📂- Arquitectura Completa de ProyectoFinal
La estructura de tu proyecto ProyectoFinal sigue las convenciones de una aplicación Spring Boot con una arquitectura de capas, ideal para una API RESTful que interactúa con una base de datos MySQL a través de JPA/Hibernate.
ProyectoFinal/
├── .git/                               # Repositorio Git
├── .gitignore                          # Reglas para ignorar archivos (ej. compilados, dependencias, logs)
├── pom.xml                             # Configuración de dependencias y construcción de Maven (Spring Boot Starters, MySQL Connector, JPA, etc.)
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── proyectofinal/      # Paquete principal de la aplicación
│   │   │           ├── ProyectoFinalApplication.java # Clase principal de Spring Boot (punto de entrada)
│   │   │           ├── controller/     # Capa de presentación: Maneja las peticiones HTTP y define los endpoints REST.
│   │   │           │   ├── AuthController.java     # (Ej: Para registro, login, etc.)
│   │   │           │   ├── PersonaController.java  # (Ej: CRUD para la entidad Persona)
│   │   │           │   ├── MensajeController.java  # (Ej: CRUD para la entidad Mensaje)
│   │   │           │   └── UbicacionController.java # (Ej: CRUD para la entidad Ubicacion)
│   │   │           ├── model/          # Capa de dominio/entidades: Define los objetos de negocio que se mapean a la BD.
│   │   │           │   ├── Persona.java
│   │   │           │   ├── Mensaje.java
│   │   │           │   └── Ubicacion.java
│   │   │           ├── repository/     # Capa de acceso a datos (DAO): Interfaces Spring Data JPA para operaciones CRUD.
│   │   │           │   ├── PersonaRepository.java
│   │   │           │   ├── MensajeRepository.java
│   │   │           │   └── UbicacionRepository.java
│   │   │           ├── service/        # Capa de lógica de negocio: Contiene la lógica principal de la aplicación.
│   │   │           │   ├── PersonaService.java
│   │   │           │   ├── MensajeService.java
│   │   │           │   └── UbicacionService.java
│   │   │           └── config/         # Clases de configuración de Spring (ej. Seguridad, Web)
│   │   │               └── SecurityConfig.java # (Ej: Configuración de Spring Security)
│   │   ├── resources/                  # Archivos de configuración, recursos estáticos, etc.
│   │   │   ├── application.properties  # Configuración principal de Spring Boot (BD, puerto, JPA, etc.)
│   │   │   ├── static/                 # Recursos estáticos (si hay un frontend servido por Spring Boot)
│   │   │   │   ├── index.html          # Página principal del frontend
│   │   │   │   ├── css/
│   │   │   │   │   └── style.css       # Estilos CSS (posiblemente usando Tailwind CSS)
│   │   │   │   └── js/
│   │   │   │       └── script.js       # Lógica JavaScript (para interactuar con la API REST)
│   │   │   └── logback-spring.xml      # Configuración de logging (opcional)
│   │   └── webapp/                     # (No aplicable directamente para una API REST de Spring Boot sin JSP/JSF)
│   │       └── WEB-INF/                # (No usado en esta configuración)
│   └── test/                           # Archivos de pruebas unitarias y de integración
│       └── java/
│           └── com/
│               └── proyectofinal/
│                   ├── PersonaServiceTest.java # (Ej: Pruebas para la lógica de negocio de Persona)
│                   └── PersonaRepositoryTest.java # (Ej: Pruebas de integración para el repositorio de Persona)
├── README.md                           # Documentación del proyecto
└── target/                             # Directorio de salida de Maven (JAR/WAR compilado)

- Explicación de las Capas:
Capa de Presentación (controller/):
Contiene las clases RestController que exponen los endpoints REST.
Reciben las peticiones HTTP (GET, POST, PUT, DELETE), validan los datos de entrada y delegan la lógica de negocio a la capa de servicio.
Devuelven respuestas HTTP (JSON, etc.).
Capa de Lógica de Negocio (service/):
Contiene la lógica de negocio central de la aplicación.
Coordina las operaciones entre diferentes entidades, aplica reglas de negocio y transacciones.
Utiliza la capa de repositorio para interactuar con la base de datos.
Capa de Acceso a Datos (repository/):
Define las interfaces que extienden JpaRepository (de Spring Data JPA).
Estas interfaces permiten realizar operaciones CRUD (Crear, Leer, Actualizar, Borrar) básicas y consultas complejas sobre las entidades de la base de datos sin necesidad de escribir implementaciones manuales.
Capa de Dominio/Entidades (model/):
Contiene las clases de entidad que representan las tablas en la base de datos.
Están anotadas con @Entity y @Table (JPA) y definen los atributos y relaciones entre las tablas.
Capa de Configuración (config/):
Clases para configurar aspectos transversales de la aplicación, como la seguridad (Spring Security), la configuración web, etc.



🤝- Contribución
Este proyecto ha sido desarrollado como parte de un curso académico. Para cualquier consulta o sugerencia relacionada con el proyecto, por favor, utiliza la sección de Contacto.

-️ Licencia
Este ProyectoFinal se presenta con fines académicos. Esta en copyleft.

- Contacto
Para cualquier pregunta, comentario o soporte, puedes contactar a:
simonnotantoine@yahoo.fr






