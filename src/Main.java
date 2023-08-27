import javax.swing.*;
import java.awt.*;
import java.io.*;

public class Main {
    // TODO: Refactor in separate files
    static String nombreArchivo = "estudiantes.txt";
    static String[] labels = {"Cedula", "Nombres", "Apellidos", "Fecha de Nacimiento", "Matricula", "Carrera"};

    public static void main(String[] args) {
        // 1. Ingresar estudiantes
        // 2. Consulta especifica de estudiantes
        // 3. Mostrar estudiantes en archivo de texto (.txt)
        // 4. Salir

        // Crea el archivo de texto en caso de que no exista, para evitar errores
        crearArchivoDeTexto(nombreArchivo);

        // Crear la interfaz grafica (UI)
        crearInterfaz();

    }

    static void crearInterfaz() {
        JFrame frame = new JFrame("Sistema estudiantil");
        frame.setSize(1366, 768);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Inicializar con la ventana maximizada
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel panel = new JPanel();

        // Fijar un color de fondo para el panel (Dark)
        panel.setBackground(Color.decode("#2a2e34"));

        // Header
        JLabel header = new JLabel("Sistema Estudiantil");
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 24));

        // Botones para realizar las acciones requeridas
        JButton ingresarEstudiantesBtn = new JButton("Ingresar estudiante");
        JButton consultarEstudianteBtn = new JButton("Consultar estudiante");
        JButton mostrarTodosEstudiantesBtn = new JButton("Mostrar todos los estudiantes");
        JButton salirBtn = new JButton("Salir");

        // Fijar un color diferente para cada boton con una diferente accion
        ingresarEstudiantesBtn.setBackground(Color.CYAN);
        consultarEstudianteBtn.setBackground(Color.GREEN);
        mostrarTodosEstudiantesBtn.setBackground(Color.ORANGE);
        salirBtn.setBackground(Color.RED);
        salirBtn.setForeground(Color.WHITE); // Color del texto del boton

        ingresarEstudiantesBtn.addActionListener(e -> ingresarNuevoEstudiante(frame));
        consultarEstudianteBtn.addActionListener(e -> obtenerEstudiantePorCedula(frame));
        mostrarTodosEstudiantesBtn.addActionListener(e -> mostrarTodosEstudiantes(frame));
        // Cerrar la ventana cuando clickea el boton de salir
        salirBtn.addActionListener(e -> frame.dispose());

        // Fijar el layout a `GridBagLayout` para poder posicionar los botones en el centro
        panel.setLayout(new GridBagLayout());

        // Fijar las posiciones para los botones
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.insets = new Insets(4, 8, 4, 8); // Agregar espacio entre los botones

        // Añadir todos los elemetos al panel
        panel.add(header, gridBagConstraints);
        panel.add(ingresarEstudiantesBtn, gridBagConstraints);
        panel.add(consultarEstudianteBtn, gridBagConstraints);
        panel.add(mostrarTodosEstudiantesBtn, gridBagConstraints);
        panel.add(salirBtn, gridBagConstraints);

        frame.add(panel);
        frame.setVisible(true);
    }

    static void crearArchivoDeTexto(String nombreArchivo) {
        File archivoTxt = new File(nombreArchivo);

        // Si ya existe el archivo, no hacer nada
        if (archivoTxt.exists()) {
            return;
        }

        // Pero si no existe, crear el archivo
        try {
            archivoTxt.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void ingresarNuevoEstudiante(JFrame frame) {
        // Crear la interfaz para mostrar los `textFields` para insertar los datos del estudiante
        JPanel textFieldsPanel = new JPanel();

        // Fijar el layout con GridBagLayout para que este todo en el centro y de manera vertical
        textFieldsPanel.setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.insets = new Insets(4, 0, 4, 0);

        JTextField[] textFields = new JTextField[labels.length];

        // Crear un `JLabel` y `JTextField` por cada item en el array de labels
        for (int i = 0; i < labels.length; i++) {
            textFieldsPanel.add(new JLabel(labels[i]), gridBagConstraints);
            textFieldsPanel.add(textFields[i] = new JTextField(10), gridBagConstraints);
        }

        int opcion = JOptionPane.showConfirmDialog(frame,
                textFieldsPanel,
                "Ingresar estudiante",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (opcion == JOptionPane.OK_OPTION) {
            try {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(nombreArchivo, true));

                for (int i = 0; i < labels.length; i++) {
                    bufferedWriter.write(labels[i] + ": " + textFields[i].getText() + "\n");
                }

                bufferedWriter.newLine(); // Agregar una nueva linea despues de cada datos de estudiante
                bufferedWriter.close();

                JOptionPane.showMessageDialog(frame,
                        "El estudiante ha sido ingresado exitosamente",
                        "Exito",
                        JOptionPane.INFORMATION_MESSAGE);

            } catch (IOException err) {
                JOptionPane.showMessageDialog(frame,
                        "Hubo un error al ingresar el estudiante, revisar la consola",
                        "Error!",
                        JOptionPane.ERROR_MESSAGE);

                throw new RuntimeException(err);
            }
        }
    }

    static void obtenerEstudiantePorCedula(JFrame frame) {
        JPanel consultarEstudiantePanel = new JPanel();
        JTextArea areaTexto = new JTextArea(15, 30);
        areaTexto.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(areaTexto);

        // Pedir cedula del estudiante a consultar
        String numeroCedula = JOptionPane.showInputDialog(frame,
                "Ingrese cedula del estudiante",
                "Consultar estudiante",
                JOptionPane.QUESTION_MESSAGE);

        // Si no es insertado ningun numero de cedula, es decir, vacio, no hacer nada.
        if (numeroCedula == null || numeroCedula.isEmpty()) return;

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(nombreArchivo));

            String linea;
            boolean estudianteEncontrado = false;

            // Leer las lineas del archivo de texto hasta que encontremos aquella que es igual a `Cedula: [numeroCedula]`
            while ((linea = bufferedReader.readLine()) != null) {

                // Esto es igual a `Cedula: [numeroCedula]`
                // TODO Replace with regex
                if (linea.equals(labels[0] + ": " + numeroCedula.trim())) {

                    // Si encontramos la linea con el numero de cedula ingresado, la añadimos al area de texto
                    areaTexto.append(linea + "\n");

                    // Avanzamos `labels.length - 1` lineas para obtener el resto de las lineas con los datos del estudiante
                    // y agregar las lineas al area de texto
                    for (int i = 0; i < labels.length - 1; i++) {
                        areaTexto.append(bufferedReader.readLine() + "\n");
                    }

                    estudianteEncontrado = true;

                    break;
                }
            }

            bufferedReader.close();

            // Si despues del `while` `estudianteEncontrado` es aun `false`, significa que no encontro ninguno con el
            // numero de cedula ingresado.
            // Por lo tanto, mostrar un mensaje informandole al usuario que no se encontro y `return` para finalizar la ejecucion
            if (!estudianteEncontrado) {
                JOptionPane.showMessageDialog(frame,
                        "No existe ningun estudiante registrado con el numero de cedula que ingresastes.",
                        "No resultados",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            consultarEstudiantePanel.add(scrollPane);

            // TODO : Improve displaying of the student cause I don't like the text area with the scroll pane
            JOptionPane.showMessageDialog(frame,
                    consultarEstudiantePanel,
                    "Estudiante encontrado",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException err) {
            JOptionPane.showMessageDialog(frame,
                    "Hubo un error al obtener el estudiante. Revisar la consola de desarrollo",
                    "Error!",
                    JOptionPane.ERROR_MESSAGE);

            throw new RuntimeException(err);
        }

    }

    static void mostrarTodosEstudiantes(JFrame frame) {
        // Crear la interfaz para mostrar todos los estudiantes
        JPanel todosEstudiantesPanel = new JPanel();
        JTextArea textArea = new JTextArea(15, 30);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        // Obtener todos los estudiantes del archivo de texto y agregarlos al `textArea`
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(nombreArchivo));

            bufferedReader.lines().forEach(line -> textArea.append(line + "\n"));

            bufferedReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        todosEstudiantesPanel.add(scrollPane);

        // Mostrar un caja de dialogo mostrando todos los estudiantes
        // Todo Why does it scroll to the bottom at the beginning?
        JOptionPane.showMessageDialog(frame,
                todosEstudiantesPanel,
                "Todos los estudiantes encontrados",
                JOptionPane.INFORMATION_MESSAGE);
    }
}