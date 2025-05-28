Proyecto SIENEP – Grupo TTT | Team Ten Tech – UTEC

## 📘 Descripción

Este proyecto consiste en la implementación de clases en Java a partir de un conjunto de requerimientos funcionales, no funcionales y de dominio del sistema **SIENEP** (Seguimiento Integral de Estudiantes con Necesidades Educativas Personalizadas). El objetivo es representar adecuadamente las entidades del sistema y sus relaciones, sin utilizar diagramas de clases.

---

## 🗂️ Estructura del proyecto

- Cada clase está implementada en su propio archivo `.java`.
- Se incluyen relaciones entre clases, como asociación y composición.
- Se definen enumeraciones para representar estados relevantes del dominio.
- Todas las clases sobrescriben el método `toString()` para facilitar la lectura de su estado.
- Se implementan múltiples constructores donde corresponde.
- No se incluye un método `main()` ya que no se requiere una ejecución funcional.

---

## 📐 Supuestos de diseño

- Se asume que un usuario no puede cambiar su `id` ni su `username` una vez creado.
- Se considera que un estudiante puede tener una lista vacía de informes médicos al ser creado.
- Un `InformeFinal` siempre está asociado a un `Seguimiento` válido.
- Los archivos adjuntos están asociados a un único `Usuario` y contienen metadatos como ruta y tipo.
- Los valores de estado (`EstadoEstudiante`, `EstadoSeguimiento`) se modelan con enumeraciones para garantizar consistencia.

---

## 👥 Participantes

| Nombre completo     | Usuario GitLab       | Aporte principal           	       |
|-----------------------------|--------------------------|-----------------------------------------------|
| Ignacio Arévalo     	| @ignacio.arevalo   | Clase Usuario, Clase Estudiantes |
| Estefany Moreira    	| @estefany.moreira | Clase , ArchivoAdjunto 	       |
| Paulo Vaccaro   	| @paulo.vaccaro     | 10anios 			       |
| Cleris Mill    		| @cleris.mill             | Clase , ArchivoAdjunto                  |
| Wilmar Hellbusch     | @wilmar.hellbusch  | Clase , ArchivoAdjunto,                 |


> La participación puede verificarse en el historial de commits del repositorio.

---

## ⚙️ Requisitos técnicos

- Proyecto desarrollado en Java 21
- Código organizado y con estructura coherente.
- Uso adecuado de relaciones entre clases.
- Comentarios incluidos donde se considere necesario para entender la lógica.
- Convenciones de nombres y buenas prácticas de estilo respetadas.

---

## 📁 Archivos principales

- `Usuario.java`  
- `Estudiante.java`  
- `Rol.java`  
- `ArchivoAdjunto.java`  
- `InformeFinal.java`  
- `EstadoEstudiante.java` (enum)  
- `EstadoSeguimiento.java` (enum)

---
