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

public class CitizensPanel extends JPanel {
    private CityDAO cityDAO;
    private CitizensDAO citizensDAO;
    private JTextField nameTextField;
    private JTextField languageTextField;
    private JFormattedTextField countTextField;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton createButton;
    private JButton editButton;
    private JButton deleteButton;
    private JComboBox<String> citiesComboBox;

    public CitizensPanel() {
        this.cityDAO = new CityDAO();
        this.citizensDAO = new CitizensDAO();
        setSize(800, 600);
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Название");
        tableModel.addColumn("Язык");
        tableModel.addColumn("Город");
        tableModel.addColumn("Количество");
        initializeTable();

        // Создание таблицы
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Панель ввода
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 2));

        JLabel nameLabel = new JLabel("Название:");
        nameTextField = new JTextField(20);

        JLabel languageLabel = new JLabel("Язык:");
        languageTextField = new JTextField(20);

        JLabel countLabel = new JLabel("Количество:");
        NumberFormatter formatterForSquare = new NumberFormatter();
        formatterForSquare.setMinimum(0); // Минимальное значение
        formatterForSquare.setMaximum(1000000); // Максимальное значение
        countTextField = new JFormattedTextField(formatterForSquare);
        countTextField.setColumns(10);

        JLabel citiesLabel = new JLabel("Город:");
        citiesComboBox = new JComboBox<>();
        List<City> cityList = cityDAO.getAll();
        for (City city: cityList) {
            citiesComboBox.addItem(String.valueOf(city.getName()));
        }

        inputPanel.add(nameLabel);
        inputPanel.add(nameTextField);
        inputPanel.add(languageLabel);
        inputPanel.add(languageTextField);
        inputPanel.add(countLabel);
        inputPanel.add(countTextField);
        inputPanel.add(citiesLabel);
        inputPanel.add(citiesComboBox);

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
                String language = languageTextField.getText();

                if (!name.isEmpty() &&citiesComboBox.getItemCount()!=0 && !language.isEmpty() && !countTextField.getText().isEmpty()) {
                    int count = ((Number) countTextField.getValue()).intValue();
                    String city = (String) citiesComboBox.getSelectedItem();
                    if (count > 0 && count < 1000000) {
                        City city1 = cityDAO.getByName(city);
                        Citizens citizens = citizensDAO.create(new Citizens(name, language, count, city1));
                        tableModel.addRow(new Object[]{citizens.getId(), citizens.getName(),citizens.getLanguage(), citizens.getCity().getName(), citizens.getCount()});
                        nameTextField.setText("");
                        languageTextField.setText("");
                        countTextField.setText("");
                    } else {
                        JOptionPane.showMessageDialog(CitizensPanel.this, "Данные некорректны", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(CitizensPanel.this, "Заполните поля", "Ошибка", JOptionPane.ERROR_MESSAGE);

                }

            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                String name = nameTextField.getText();
                String language = languageTextField.getText();
                if (selectedRow >= 0) {
                    if (!name.isEmpty() && !language.isEmpty() && !countTextField.getText().isEmpty()) {
                        int count = ((Number) countTextField.getValue()).intValue();
                        String city = (String) citiesComboBox.getSelectedItem();
                        if (count > 0 && count < 1000000) {
                            City city1 = cityDAO.getByName(city);
                            Citizens citizens = citizensDAO.update(new Citizens((Integer) tableModel.getValueAt(selectedRow, 0), name, language, city1, count));

                            tableModel.setValueAt(name, selectedRow, 1);
                            tableModel.setValueAt(language, selectedRow, 2);
                            tableModel.setValueAt(city1.getName(), selectedRow, 3);
                            tableModel.setValueAt(count, selectedRow, 4);
                            nameTextField.setText("");
                            languageTextField.setText("");
                            countTextField.setText("");
                        } else {
                            JOptionPane.showMessageDialog(CitizensPanel.this, "Данные некорректны", "Ошибка", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(CitizensPanel.this, "Заполните поля", "Ошибка", JOptionPane.ERROR_MESSAGE);

             }
                }
            }

        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    City city =cityDAO.getByName((String) tableModel.getValueAt(selectedRow, 3));
                    citizensDAO.delete(new Citizens((Integer) tableModel.getValueAt(selectedRow, 0), (String) tableModel.getValueAt(selectedRow, 1), (String) tableModel.getValueAt(selectedRow, 2), city, (Integer) tableModel.getValueAt(selectedRow, 4)));
                    tableModel.removeRow(selectedRow);
                    nameTextField.setText("");
                    languageTextField.setText("");
                    countTextField.setText("");
                } else {
                    JOptionPane.showMessageDialog(CitizensPanel.this, "Выберите запись для удаления", "Ошибка", JOptionPane.ERROR_MESSAGE);
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
                    String language = (String) tableModel.getValueAt(selectedRow, 2);
                    int count = (int) tableModel.getValueAt(selectedRow, 4);
                    // Установка данных в текстовые поля
                    nameTextField.setText(name);
                    languageTextField.setText(language);
                    countTextField.setValue(count);
                }
            }
        });
    }

    private void initializeTable() {
        List<Citizens> citizens= citizensDAO.getAll();
        for (Citizens citizens1: citizens)  {
            tableModel.addRow(new Object[]{citizens1.getId(), citizens1.getName(), citizens1.getLanguage(), citizens1.getCity().getName(), citizens1.getCount() });
        }
    }

    public void refreshData() {
        tableModel.setRowCount(0);
        citiesComboBox.removeAllItems();
        List<Citizens> citizens= citizensDAO.getAll();
        for (Citizens citizens1: citizens)  {
            tableModel.addRow(new Object[]{citizens1.getId(), citizens1.getName(), citizens1.getLanguage(), citizens1.getCity().getName(), citizens1.getCount() });
        }
        List<City> cityList = cityDAO.getAll();
        for (City city: cityList) {
            citiesComboBox.addItem(String.valueOf(city.getName()));
        }

    }
}
