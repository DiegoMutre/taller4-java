# Taller 4 - Fundamentos de Programacion - Java

## Features
- Ingresa estudiantes con:
  - Cedula.
  - Nombres.
  - Apellidos.
  - Matricula.
  - Carrera.
- Consulta estudiantes por el numero de cedula.
- Muestra todos los estudiantes ingresados.
- Guarda todos los datos de los estudiantes ingresados en un archivo de texto.

## Known issues
- No esta validando los text fields, por lo tanto se puede insertar estudiantes con ningun dato.
- Si existen dos estudiantes con el mismo numero de cedula, solo retornara el primero que encuentre.
- Si se elimina el archivo `estudiantes.txt` mientras el codigo esta en ejecucion, puede haber errores al consultar estudiantes.
- Todo esta en un solo archivo, el `Main.java`, por lo que hace un poco complicado leer todo ese codigo.
- **Si existe algun error, notificar por favor :)**
