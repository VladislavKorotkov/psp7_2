package org.example.ui;

import org.example.DAO.CityDAO;
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

public class CitiesPanel extends JPanel {
    private CityDAO cityDAO;
    private JTextField nameTextField;
    private JFormattedTextField yearTextField;
    private JFormattedTextField squareTextField;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton createButton;
    private JButton editButton;
    private JButton deleteButton;

    public CitiesPanel() {
        this.cityDAO = new CityDAO();
        setSize(800, 600);
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Название");
        tableModel.addColumn("Год основания");
        tableModel.addColumn("Площадь");
        initializeTable();

        // Создание таблицы
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Панель ввода
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2));

        JLabel nameLabel = new JLabel("Название:");
        nameTextField = new JTextField(20);

        JLabel yearLabel = new JLabel("Год основания:");
        NumberFormatter formatterForYear = new NumberFormatter();
        formatterForYear.setMinimum(0); // Минимальное значение
        formatterForYear.setMaximum(2023); // Максимальное значение
        yearTextField = new JFormattedTextField(formatterForYear);
        yearTextField.setColumns(10);

        JLabel squareLabel = new JLabel("Площадь км2:");
        NumberFormatter formatterForSquare = new NumberFormatter();
        formatterForSquare.setMinimum(0); // Минимальное значение
        formatterForSquare.setMaximum(10000); // Максимальное значение
        squareTextField = new JFormattedTextField(formatterForSquare);
        squareTextField.setColumns(10);

        inputPanel.add(nameLabel);
        inputPanel.add(nameTextField);
        inputPanel.add(yearLabel);
        inputPanel.add(yearTextField);
        inputPanel.add(squareLabel);
        inputPanel.add(squareTextField);

        // Панель кнопок
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        createButton = new JButton("Создать");
        editButton = new JButton("Редактировать");
        deleteButton = new JButton("Удалить");

        buttonPanel.add(createButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        // Добавление компонентов на основное окно
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);

        // Обработка событий кнопок
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameTextField.getText();
                if (!name.isEmpty() && !yearTextField.getText().isEmpty() && !squareTextField.getText().isEmpty()) {
                    int year = ((Number) yearTextField.getValue()).intValue();
                    int square = ((Number) squareTextField.getValue()).intValue();

                    if (year >= 0 && year <= 2023 && square > 0 && square < 10000) {
                        City city = cityDAO.create(new City(name, year, square));
                        tableModel.addRow(new Object[]{city.getId(), city.getName(), city.getYear(), city.getSquare()});
                        nameTextField.setText("");
                        yearTextField.setText("");
                        squareTextField.setText("");
                    } else {
                        JOptionPane.showMessageDialog(CitiesPanel.this, "Данные некорректны", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(CitiesPanel.this, "Заполните поля", "Ошибка", JOptionPane.ERROR_MESSAGE);

                }

            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                String name = nameTextField.getText();
                if (selectedRow >= 0) {
                    if (!name.isEmpty() && !yearTextField.getText().isEmpty() && !squareTextField.getText().isEmpty()) {
                        int year = ((Number) yearTextField.getValue()).intValue();
                        int square = ((Number) squareTextField.getValue()).intValue();
                        if (year >= 0 && year <= 2023 && square > 0 && square < 10000) {
                            City city = cityDAO.update(new City((Integer) tableModel.getValueAt(selectedRow, 0), name, year, square));

                            tableModel.setValueAt(name, selectedRow, 1);
                            tableModel.setValueAt(year, selectedRow, 2);
                            tableModel.setValueAt(square, selectedRow, 3);

                            nameTextField.setText("");
                            yearTextField.setText("");
                            squareTextField.setText("");
                        } else {
                            JOptionPane.showMessageDialog(CitiesPanel.this, "Данные некорректны", "Ошибка", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(CitiesPanel.this, "Заполните поля", "Ошибка", JOptionPane.ERROR_MESSAGE);

                    }
                }
            }

        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    cityDAO.delete(new City((Integer) tableModel.getValueAt(selectedRow, 0), (String) tableModel.getValueAt(selectedRow, 1), (Integer) tableModel.getValueAt(selectedRow, 2), (Integer) tableModel.getValueAt(selectedRow, 3)));
                    tableModel.removeRow(selectedRow);

                    nameTextField.setText("");
                    yearTextField.setText("");
                    squareTextField.setText("");
                } else {
                    JOptionPane.showMessageDialog(CitiesPanel.this, "Выберите запись для удаления", "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    // Получение данных выбранной строки
                    int id = (int) tableModel.getValueAt(selectedRow, 0);
                    String name = (String) tableModel.getValueAt(selectedRow, 1);
                    int yaer = (int) tableModel.getValueAt(selectedRow, 2);
                    int square = (int) tableModel.getValueAt(selectedRow, 3);
                    // Установка данных в текстовые поля
                    nameTextField.setText(name);
                    yearTextField.setValue(yaer);
                    squareTextField.setValue(square);
                }
            }
        });
    }

    private void initializeTable() {
        List<City> cityList = cityDAO.getAll();
        for (City city : cityList) {
            tableModel.addRow(new Object[]{city.getId(), city.getName(), city.getYear(), city.getSquare()});
        }
    }

    public void refreshData() {
        // Обновление данных в таблице
        tableModel.setRowCount(0);
        List<City> cities = cityDAO.getAll();
        tableModel.setRowCount(0);
        for (City city : cities) {
            tableModel.addRow(new Object[]{city.getId(), city.getName(), city.getYear(), city.getSquare()});
        }
    }
}
