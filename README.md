# Sistema de Gestión de Eventos y Participantes
_A una API RESTful en Spring Boot para administrar eventos, inscripciones, asistencia, notificaciones y reportes en PDF._

## Características
- **Gestión de Eventos:** Creación, edición, eliminación y listado de eventos.
- **Gestión de Participantes:** Registro, edición, eliminación y listado de participantes.
- **Inscripción y Asistencia:** Inscripción automática con registro de asistencia en estado "Sin Confirmar", que luego se actualiza a "Confirmada" o "Ausente".
- **Notificaciones:** Envío de correos electrónicos a los participantes para confirmar inscripciones y cambios en el estado de asistencia.
- **Reportes:** Generación de reportes en PDF de participantes y asistencia.
- **Contenerización:** Despliegue del proyecto con Docker.

## Tecnologías Utilizadas
- **Java 17**  
- **Spring Boot 3**  
- **Spring Data JPA**  
- **MySQL**  
- **iText** (para la generación de PDFs)  
- **Docker**  
- **Postman** (para pruebas de la API)

Uso de la API
La API expone los siguientes endpoints para gestionar eventos, participantes y asistencia:

Eventos
Crear Evento

Método: POST
URL: /evento/crear
Cuerpo (JSON):
json
Copiar
Editar
{
  "nombreE": "Charla de ChatGPT",
  "descripcion": "Evento sobre tecnología",
  "fechaHoraE": "2025-02-04T20:42:49",
  "cupoMax": 42
}
Listar Eventos

Método: GET
URL: /evento/traer
Inscribir Participante a un Evento

Método: PUT
URL: /evento/inscripcion/{idEvento}/{idParticipante}
Ejemplo:
swift
Copiar
Editar
PUT /evento/inscripcion/202/2
Cancelar Inscripción de un Participante

Método: DELETE
URL: /evento/cancelar/{idEvento}/{idParticipante}
Ejemplo:
swift
Copiar
Editar
DELETE /evento/cancelar/202/2
Generar Reporte de Evento (PDF)

Método: GET
URL: /evento/reporte/{idEvento}
Ejemplo:
bash
Copiar
Editar
GET /evento/reporte/1
Participantes
Crear Participante

Método: POST
URL: /participante/crear
Cuerpo (JSON):
json
Copiar
Editar
{
  "nombreP": "Juan",
  "apellidoP": "Pérez",
  "correo": "juan.perez@email.com"
}
Listar Participantes

Método: GET
URL: /participante/traer
Editar Participante

Método: PUT
URL: /participante/editar
Cuerpo (JSON):
json
Copiar
Editar
{
  "id": 102,
  "nombreP": "Juan",
  "apellidoP": "Martinez",
  "correo": "juan@gmail.com"
}
Borrar Participante

Método: DELETE
URL: /participante/borrar/{idParticipante}
Ejemplo:
bash
Copiar
Editar
DELETE /participante/borrar/53
Asistencia
Registrar/Actualizar Asistencia

Método: PUT
URL: /asistencia/registrar/{idEvento}/{idParticipante}/{estadoAsistencia}
Parámetro estadoAsistencia:
0 para "Ausente"
1 para "Confirmada"
Ejemplo:
swift
Copiar
Editar
PUT /asistencia/registrar/202/2/0
Obtener Reporte de Asistencia por Evento (PDF)

Método: GET
URL: /asistencia/evento/{idEvento}
Ejemplo:
bash
Copiar
Editar
GET /asistencia/evento/152
Obtener Reporte de Asistencia por Participante

Método: GET
URL: /asistencia/participante/{idParticipante}
Ejemplo:
bash
Copiar
Editar
GET /asistencia/participante/102

