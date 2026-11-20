Este repositorio es un programa que permite convertir un archivo json con preguntas y respuestas a un test de hasta 20 preguntas elegidas al azar entre todas las del json.

El formato que debe tener el json es el siguiente:



    "preguntas": [
        {
            "enunciado": "Al diseñar una aplicación, ¿Qué principios de diseño se están aplicando principalmente?",
            "solucion": "B",
            "opciones": [
              "A. Un diseño orientado a la tecnología, centrado en optimizar el rendimiento del código por encima de la comodidad del usuario.",
              "B. Un diseño centrado en el usuario (User-Centered Design), que adapta la interfaz a las necesidades reales de las personas.",
              "C. Un diseño basado exclusivamente en el lenguaje de presentación para transmitir datos de forma unidireccional.",
              "D. Una interfaz de preguntas y respuestas donde el ordenador guía de forma rígida cada paso del proceso."
            ]
        },
        ···
    ]


Para lanzar la aplicación es necesario java 25 o superior.
- Descarga o clona el repositorio.
- Situate en el directorio raiz del proyecto.
- Ejecuta `./gradlew.bat runfx`
Otra opción sería:
- Ejecuta `./gradlew.bat fatjar`
- Ejecuta `java -jar ./app/build/libs/[nombre del .jar]`
