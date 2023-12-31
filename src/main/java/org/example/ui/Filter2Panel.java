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

public class Filter2Panel extends JPanel {
    private CityDAO cityDAO;
    private JTextField citizensTextField;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton filterButton;

    public Filter2Panel() {
        this.cityDAO = new CityDAO();
        setSize(800, 600);
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Название");
        tableModel.addColumn("Год основания");
        tableModel.addColumn("Площадь");

        // Создание таблицы
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Панель ввода
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(1, 2));

        JLabel citizensLabel = new JLabel("Название жителей:");
        citizensTextField = new JTextField(20);



        inputPanel.add(citizensLabel);
        inputPanel.add(citizensTextField);

        filterButton = new JButton("Поиск");
        // Добавление компонентов на основное окно
        add(inputPanel, BorderLayout.NORTH);
        add(filterButton, BorderLayout.SOUTH);
        // Обработка событий кнопок
        filterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String citizens = citizensTextField.getText();
                if (!citizens.isEmpty()) {
                    List<City> city = cityDAO.searchCitiesByCitizenName(citizens);
                    initializeTable(city);
                } else {
                    JOptionPane.showMessageDialog(Filter2Panel.this, "Заполните поля", "Ошибка", JOptionPane.ERROR_MESSAGE);

                }
            }
        });


    }

    private void initializeTable(List<City> cities) {
        tableModel.setRowCount(0);
        for (City city : cities) {
            tableModel.addRow(new Object[]{city.getId(), city.getName(), city.getYear(), city.getSquare()});
        }
    }
}