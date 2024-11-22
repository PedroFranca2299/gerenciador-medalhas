package com.medalmanager.util;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class StyleConstants {
    // Cores
    public static final Color PRIMARY_COLOR = new Color(41, 128, 185);    // Azul
    public static final Color SECONDARY_COLOR = new Color(44, 62, 80);    // Azul escuro
    public static final Color BACKGROUND_COLOR = new Color(236, 240, 241); // Cinza claro
    public static final Color SUCCESS_COLOR = new Color(46, 204, 113);    // Verde
    public static final Color WARNING_COLOR = new Color(241, 196, 15);    // Amarelo
    public static final Color ERROR_COLOR = new Color(231, 76, 60);       // Vermelho

    // Fontes
    public static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 24);
    public static final Font HEADER_FONT = new Font("Segoe UI", Font.BOLD, 18);
    public static final Font BUTTON_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font REGULAR_FONT = new Font("Segoe UI", Font.PLAIN, 14);

    // Bordas
    public static final Border PANEL_BORDER = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PRIMARY_COLOR, 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
    );

    // Métodos de estilização
    public static void styleButton(JButton button) {
        button.setFont(BUTTON_FONT);
        button.setBackground(PRIMARY_COLOR);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        button.setOpaque(true);

        // Efeito hover
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(button.getBackground().darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(PRIMARY_COLOR);
            }
        });
    }

    public static void styleSecondaryButton(JButton button) {
        styleButton(button);
        button.setBackground(SECONDARY_COLOR);
    }

    public static void styleTable(JTable table) {
        table.setFont(REGULAR_FONT);
        table.setRowHeight(30);
        table.setShowGrid(true);
        table.setGridColor(new Color(189, 195, 199));
        table.getTableHeader().setFont(HEADER_FONT);
        table.getTableHeader().setBackground(PRIMARY_COLOR);
        table.getTableHeader().setForeground(Color.WHITE);
    }

    public static void styleComboBox(JComboBox<?> comboBox) {
        comboBox.setFont(REGULAR_FONT);
        comboBox.setBackground(Color.WHITE);
        comboBox.setBorder(BorderFactory.createLineBorder(PRIMARY_COLOR));
    }

    public static void stylePanel(JPanel panel) {
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(PANEL_BORDER);
    }
}