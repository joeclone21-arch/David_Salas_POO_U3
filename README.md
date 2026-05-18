# David_Salas_POO_U3
# Sistema Aleatorio de Sorteos para Liga Profesional

Este proyecto es una aplicación de consola desarrollada en **Java** enfocada en la gestión y automatización de emparejamientos deportivos para torneos eliminatorios (octavos de final, cuartos de final y semifinales). El sistema garantiza la aleatoriedad pura de los partidos y aplica un control de reglas de negocio para asegurar la transparencia del torneo.

## Objetivo de la Actividad
El propósito principal es resolver el problema de emparejamiento aleatorio de $N$ equipos según la etapa seleccionada, asegurando mediante restricciones lógicas que:
1. Ningún equipo repita un partido en la misma etapa.
2. Un equipo no pueda ser competidor y oponente de sí mismo en un mismo encuentro (evitar emparejamientos fantasma o duplicados).

---

## Conceptos Avanzados de POO Implementados

El desarrollo de este software fue diseñado bajo el paradigma de **Programación Orientada a Objetos (POO)** y abarca los siguientes temas clave del plan de estudios:

### 1. Algoritmo y Uso de Recursividad
Para cumplir con los requerimientos estrictos del problema, el núcleo del sorteo se diseñó utilizando un **enfoque recursivo** en lugar de bucles iterativos tradicionales (`for`/`while`). 
* **Subproblema:** En cada ciclo recursivo, el algoritmo extrae un par de equipos al azar de la lista y genera un objeto `Partido`.
* **Caso Base:** La recursión se detiene de forma segura cuando quedan menos de 2 equipos por emparejar en la estructura de datos.

### 2. Manejo de Excepciones Robustas y Propias (Clases 11 y 12)
Para evitar fallos catastróficos en tiempo de ejecución debido a entradas erróneas del usuario, se implementó una arquitectura defensiva mediante bloques `try-catch-finally` y la creación de **excepciones personalizadas**:
* `EtapaInvalidaException`: Controla que el usuario solo elija etapas existentes.
* `CantidadEquiposException`: Valida que el número de equipos ingresados coincida con la etapa matemática del torneo.
* `NombreEquipoInvalidoException`: Rechaza mediante Expresiones Regulares (Regex) entradas vacías o caracteres inválidos (como puntos aislados).
* `EquiposDuplicadosException`: Bloquea el ingreso de equipos con nombres idénticos, blindando la integridad del sorteo.

### 3. Persistencia de Datos y Archivos (Clases 13, 14 y 15)
El programa no solo procesa los datos en memoria volátil, sino que garantiza el almacenamiento del sorteo a través de dos formatos físicos en el disco duro:
* **Archivos de Texto (`.txt`):** Exportación de los partidos en un formato plano y legible por humanos mediante flujos de caracteres controlados (`BufferedWriter`).
* **Archivos Binarios y Serialización de Objetos (`.dat` / `.bin`):** Implementación de la interfaz `Serializable` en la entidad `Partido`. Se utiliza `ObjectOutputStream` para empaquetar y persistir el estado real de los objetos directamente en formato binario para futuras lecturas del sistema.

---

## Estructura del Proyecto

El código fuente está modularizado en tres componentes clave dentro del paquete `com.torneo`:
* **`Main.java`:** Interfaz de consola, control de flujos y ejecución del algoritmo recursivo de sorteo.
* **`Partido.java`:** Clase de entidad serializable que modela el emparejamiento de los clubes deportivos.
* **`SorteoException.java`:** Repositorio centralizado de todas las excepciones personalizadas del negocio (incluyendo el estándar de control `serialVersionUID`).
