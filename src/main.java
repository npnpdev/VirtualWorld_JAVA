// Autor: Igor Tomkowicz, s194103

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Konfiguracja Gry");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(300, 200);
            frame.setLayout(new GridLayout(4, 2));

            JLabel labelWiersze = new JLabel("Podaj liczbę wierszy kraty:");
            JTextField textWiersze = new JTextField();
            JLabel labelKolumny = new JLabel("Podaj liczbę kolumn kraty:");
            JTextField textKolumny = new JTextField();

            JRadioButton krataButton = new JRadioButton("Kraty", true);
            JRadioButton hexButton = new JRadioButton("Hex");

            ButtonGroup group = new ButtonGroup();
            group.add(krataButton);
            group.add(hexButton);

            JButton startButton = new JButton("Start");
            startButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        int wiersze = Integer.parseInt(textWiersze.getText());
                        int kolumny = Integer.parseInt(textKolumny.getText());

                        if (wiersze <= 0 || kolumny <= 0) {
                            JOptionPane.showMessageDialog(frame, "Podane wymiary muszą być dodatnie.");
                        } else {
                            String swiat;
                            if (krataButton.isSelected()) {
                                swiat = "krata";
                            } else {
                            	swiat = "hex";
                            }
                            Gra gra = new Gra(wiersze, kolumny, swiat);
                            frame.dispose();  // Usunięcie okna po wprowadzeniu prawidłowych wymiarów
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame, "Podaj prawidłowe liczby.");
                    }
                }
            });

            frame.add(labelWiersze);
            frame.add(textWiersze);
            frame.add(labelKolumny);
            frame.add(textKolumny);
            frame.add(krataButton);
            frame.add(hexButton);
            frame.add(startButton);

            frame.setVisible(true);
        });
    }
}
