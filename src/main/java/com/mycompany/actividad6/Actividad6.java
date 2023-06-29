package com.mycompany.actividad6;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Actividad6 extends JFrame implements ActionListener {
    private JTextField nombreField, numeroField;
    private JButton agregarButton, eliminarButton;

    public Actividad6() {
        // Configuración de la ventana
        setTitle("Interfaz de Nombres");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 2));

        // Campos de texto para el nombre y el número
        JLabel nombreLabel = new JLabel("Nombre:");
        nombreField = new JTextField();
        JLabel numeroLabel = new JLabel("Número:");
        numeroField = new JTextField();

        // Botones de agregar y eliminar
        agregarButton = new JButton("Agregar");
        agregarButton.addActionListener(this);
        eliminarButton = new JButton("Eliminar");
        eliminarButton.addActionListener(this);

        // Agregar componentes a la ventana
        add(nombreLabel);
        add(nombreField);
        add(numeroLabel);
        add(numeroField);
        add(agregarButton);
        add(eliminarButton);

        // Mostrar la ventana
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == agregarButton) {
            agregarNombre();
        } else if (e.getSource() == eliminarButton) {
            eliminarNombre();
        }
    }

    private void agregarNombre() {
        String nombre = nombreField.getText();
        String numero = numeroField.getText();

        try (PrintWriter writer = new PrintWriter(new FileWriter("nombres.txt", true))) {
            writer.println(nombre + "," + numero);
            JOptionPane.showMessageDialog(this, "Nombre agregado correctamente.");
            nombreField.setText("");
            numeroField.setText("");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al agregar el nombre.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarNombre() {
    String nombre = nombreField.getText();

    try {
        File inputFile = new File("nombres.txt");
        File tempFile = new File("temp.txt");

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        PrintWriter writer = new PrintWriter(new FileWriter(tempFile));

        String lineToRemove = nombre + ",";
        String currentLine;

        while ((currentLine = reader.readLine()) != null) {
            if (currentLine.startsWith(lineToRemove)) {
                continue;
            }
            writer.println(currentLine);
        }

        writer.close();
        reader.close();

        boolean deleteSuccessful = inputFile.delete();
        if (!deleteSuccessful) {
            JOptionPane.showMessageDialog(this, "Error al eliminar el nombre.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean renameSuccessful = tempFile.renameTo(inputFile);
        if (renameSuccessful) {
            JOptionPane.showMessageDialog(this, "Nombre eliminado correctamente.");
            nombreField.setText("");
            numeroField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Error al eliminar el nombre.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (IOException ex) {
        JOptionPane.showMessageDialog(this, "Error al eliminar el nombre.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Actividad6 interfaz = new Actividad6();
            interfaz.setVisible(true);
        });
    }
}
