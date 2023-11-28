package org.example;

import org.example.ui.CitiesPanel;
import org.example.ui.CitizensPanel;
import org.example.ui.Filter1Panel;
import org.example.ui.Filter2Panel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {

    private CardLayout cardLayout;
    private JPanel cardPanel;

    public Main() {
        setTitle("Учет городов и жителей");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        CitiesPanel citiesPanel = new CitiesPanel();
        CitizensPanel citizensPanel = new CitizensPanel();
        Filter1Panel filter1Panel = new Filter1Panel();
        Filter2Panel filter2Panel = new Filter2Panel();

        cardPanel.add(citiesPanel, "screen1");
        cardPanel.add(citizensPanel, "screen2");
        cardPanel.add(filter1Panel, "screen3");
        cardPanel.add(filter2Panel, "screen4");

        getContentPane().add(cardPanel, BorderLayout.CENTER);

        JButton button1 = new JButton("Города");
        JButton button2 = new JButton("Жители");
        JButton button3 = new JButton("Фильтрация 1");
        JButton button4 = new JButton("Фильтрация 2");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(button1);
        buttonPanel.add(button2);
        buttonPanel.add(button3);
        buttonPanel.add(button4);

        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "screen1");
                CitiesPanel citiesPanel = (CitiesPanel) cardPanel.getComponent(0);
                citiesPanel.refreshData(); // Вызываем метод обновления данных при переходе на панель "Города"
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "screen2");
                CitizensPanel citizensPanel1 = (CitizensPanel) cardPanel.getComponent(1);
                citizensPanel1.refreshData(); // Вызываем метод обновления данных при переходе на панель "Города"

            }
        });

        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "screen3");
            }
        });

        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "screen4");

            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }
}