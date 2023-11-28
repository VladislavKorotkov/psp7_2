package org.example.ui;

import org.example.DAO.CitizensDAO;
import org.example.DAO.CityDAO;
import org.example.model.Citizens;
import org.example.model.City;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Filter1Panel extends JPanel {
    private CityDAO cityDAO;
    private CitizensDAO citizensDAO;
    private JTextField languageTextField;
    private JTextField cityTextField;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton filterButton;

    public Filter1Panel() {
        this.citizensDAO = new CitizensDAO();
        setSize(800, 600);
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Название");
        tableModel.addColumn("Язык");
        tableModel.addColumn("Город");
        tableModel.addColumn("Количество");

        // Создание таблицы
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Панель ввода
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 2));

        JLabel languageLabel = new JLabel("Язык:");
        languageTextField = new JTextField(20);

        JLabel cityLabel = new JLabel("Город:");
        cityTextField = new JTextField(20);


        inputPanel.add(languageLabel);
        inputPanel.add(languageTextField);
        inputPanel.add(cityLabel);
        inputPanel.add(cityTextField);

        filterButton = new JButton("Поиск");
        // Добавление компонентов на основное окно
        add(inputPanel, BorderLayout.NORTH);
        add(filterButton, BorderLayout.SOUTH);
        // Обработка событий кнопок
        filterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String language = languageTextField.getText();
                String city = cityTextField.getText();
                if (!language.isEmpty() && !city.isEmpty()) {
                    List<Citizens> citizens = citizensDAO.searchCitizensByLanguageAndCity(language, city);
                    initializeTable(citizens);
                } else {
                    JOptionPane.showMessageDialog(Filter1Panel.this, "Заполните поля", "Ошибка", JOptionPane.ERROR_MESSAGE);

                }
            }
        });


    }

    private void initializeTable(List<Citizens> citizens) {
        tableModel.setRowCount(0);
        for (Citizens citizens1 : citizens) {
            tableModel.addRow(new Object[]{citizens1.getId(), citizens1.getName(), citizens1.getLanguage(), citizens1.getCity().getName(), citizens1.getCount()});
        }
    }
}