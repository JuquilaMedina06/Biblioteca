/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package biblioteca;

/**
 *
 * @author juqui
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;

public class BibliotecaGUI extends JFrame {
    private ArrayList<Libro> biblioteca;
    private DefaultListModel<Libro> modeloLista;
    private JList<Libro> listaLibros;
    private JLabel etiquetaInventario;

    public BibliotecaGUI() {
        biblioteca = new ArrayList<>();
        modeloLista = new DefaultListModel<>();
        listaLibros = new JList<>(modeloLista);

        // Configuración de la ventana principal
        setTitle("📚 Biblioteca Mejorada 📚");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(40, 44, 52)); // Fondo gris oscuro
        setLayout(new BorderLayout());

        // Encabezado
        JLabel encabezado = new JLabel("Gestión de Biblioteca", SwingConstants.CENTER);
        encabezado.setFont(new Font("SansSerif", Font.BOLD, 28));
        encabezado.setForeground(new Color(255, 165, 0)); // Naranja vibrante
        encabezado.setOpaque(true);
        encabezado.setBackground(new Color(25, 29, 38)); // Fondo del encabezado
        add(encabezado, BorderLayout.NORTH);

        // Panel de entrada
        JPanel panelEntrada = new JPanel(new GridLayout(3, 2, 10, 10));
        panelEntrada.setBackground(new Color(55, 60, 70)); // Fondo gris intermedio
        panelEntrada.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JTextField campoTitulo = new JTextField();
        JTextField campoAutor = new JTextField();
        JTextField campoBuscar = new JTextField();

        campoTitulo.setBackground(new Color(75, 80, 90));
        campoTitulo.setForeground(Color.WHITE);
        campoAutor.setBackground(new Color(75, 80, 90));
        campoAutor.setForeground(Color.WHITE);
        campoBuscar.setBackground(new Color(75, 80, 90));
        campoBuscar.setForeground(Color.WHITE);

        panelEntrada.add(crearEtiqueta("Título:"));
        panelEntrada.add(campoTitulo);
        panelEntrada.add(crearEtiqueta("Autor:"));
        panelEntrada.add(campoAutor);
        panelEntrada.add(crearEtiqueta("Buscar (por título o autor):"));
        panelEntrada.add(campoBuscar);
        add(panelEntrada, BorderLayout.WEST);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBotones.setBackground(new Color(55, 60, 70)); // Fondo gris intermedio
        JButton botonAgregar = crearBoton("Agregar", new Color(50, 205, 50)); // Verde brillante
        JButton botonEliminar = crearBoton("Eliminar", new Color(255, 69, 0)); // Rojo vibrante
        JButton botonBuscar = crearBoton("Buscar", new Color(30, 144, 255)); // Azul brillante
        JButton botonInventario = crearBoton("Inventario", new Color(255, 140, 0)); // Naranja fuerte

        panelBotones.add(botonAgregar);
        panelBotones.add(botonEliminar);
        panelBotones.add(botonBuscar);
        panelBotones.add(botonInventario);
        add(panelBotones, BorderLayout.SOUTH);

        // Lista de libros e inventario
        JPanel panelLista = new JPanel(new BorderLayout());
        panelLista.setBackground(new Color(40, 44, 52)); // Fondo gris oscuro
        panelLista.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        etiquetaInventario = crearEtiqueta("Total de libros: 0");
        etiquetaInventario.setHorizontalAlignment(SwingConstants.CENTER);
        etiquetaInventario.setFont(new Font("SansSerif", Font.BOLD, 16));
        panelLista.add(etiquetaInventario, BorderLayout.NORTH);

        listaLibros.setFont(new Font("SansSerif", Font.PLAIN, 14));
        listaLibros.setBackground(new Color(55, 60, 70)); // Fondo de la lista
        listaLibros.setForeground(Color.WHITE);
        listaLibros.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.ORANGE), "Lista de Libros", 0, 0, new Font("SansSerif", Font.BOLD, 16), Color.ORANGE));
        panelLista.add(new JScrollPane(listaLibros), BorderLayout.CENTER);

        add(panelLista, BorderLayout.CENTER);

        // Funcionalidad de los botones
        botonAgregar.addActionListener(e -> {
            String titulo = campoTitulo.getText();
            String autor = campoAutor.getText();

            if (!titulo.isEmpty() && !autor.isEmpty()) {
                Libro libro = new Libro(titulo, autor, "");
                biblioteca.add(libro);
                modeloLista.addElement(libro);
                campoTitulo.setText("");
                campoAutor.setText("");
                actualizarInventario();
                JOptionPane.showMessageDialog(this, "📕 Libro agregado exitosamente.");
            } else {
                JOptionPane.showMessageDialog(this, "❗ Por favor, completa los campos de título y autor.");
            }
        });

        botonEliminar.addActionListener(e -> {
            int indiceSeleccionado = listaLibros.getSelectedIndex();
            if (indiceSeleccionado != -1) {
                biblioteca.remove(indiceSeleccionado);
                modeloLista.remove(indiceSeleccionado);
                actualizarInventario();
                JOptionPane.showMessageDialog(this, "📕 Libro eliminado.");
            } else {
                JOptionPane.showMessageDialog(this, "❗ Selecciona un libro para eliminar.");
            }
        });

        botonBuscar.addActionListener(e -> {
            String textoBusqueda = campoBuscar.getText().toLowerCase();
            modeloLista.clear();

            if (!textoBusqueda.isEmpty()) {
                for (Libro libro : biblioteca) {
                    if (libro.getTitulo().toLowerCase().contains(textoBusqueda) || libro.getAutor().toLowerCase().contains(textoBusqueda)) {
                        modeloLista.addElement(libro);
                    }
                }
                if (modeloLista.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "🔍 No se encontraron libros que coincidan con la búsqueda.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "❗ Por favor, escribe algo para buscar.");
            }
        });

        botonInventario.addActionListener(e -> {
            if (biblioteca.isEmpty()) {
                JOptionPane.showMessageDialog(this, "📖 No hay libros en el inventario.");
            } else {
                HashMap<String, ArrayList<String>> librosPorAutor = new HashMap<>();
                for (Libro libro : biblioteca) {
                    librosPorAutor.putIfAbsent(libro.getAutor(), new ArrayList<>());
                    librosPorAutor.get(libro.getAutor()).add(libro.getTitulo());
                }

                StringBuilder inventario = new StringBuilder("📚 Inventario de libros:\n\n");
                inventario.append("Total de libros: ").append(biblioteca.size()).append("\n\n");
                for (String autor : librosPorAutor.keySet()) {
                    inventario.append("Autor: ").append(autor).append("\n");
                    for (String titulo : librosPorAutor.get(autor)) {
                        inventario.append("   • ").append(titulo).append("\n");
                    }
                    inventario.append("\n");
                }

                JOptionPane.showMessageDialog(this, inventario.toString());
            }
        });
    }

    private JLabel crearEtiqueta(String texto) {
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setForeground(Color.WHITE);
        return etiqueta;
    }

    private JButton crearBoton(String texto, Color colorFondo) {
        JButton boton = new JButton(texto);
        boton.setBackground(colorFondo);
        boton.setForeground(Color.BLACK);
        boton.setFont(new Font("SansSerif", Font.BOLD, 14));
        boton.setFocusPainted(false);
        return boton;
    }

    private void actualizarInventario() {
        etiquetaInventario.setText("Total de libros: " + biblioteca.size());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new BibliotecaGUI().setVisible(true);
        });
    }
}






