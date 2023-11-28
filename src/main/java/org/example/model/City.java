package org.example.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "city")
public class City {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="name")
    private String name;

    @Column(name = "year")
    private int year;

    @Column(name="square")
    private int square;

    @OneToMany(mappedBy = "city")
    private List<Citizens> citizens;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public City(String name, int year, int square) {
        this.name = name;
        this.year = year;
        this.square = square;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getSquare() {
        return square;
    }

    public void setSquare(int square) {
        this.square = square;
    }

    public List<Citizens> getCitizens() {
        return citizens;
    }

    public void setCitizens(List<Citizens> citizens) {
        this.citizens = citizens;
    }

    public City() {
    }



    public City(int year, int square, List<Citizens> citizens) {
        this.year = year;
        this.square = square;
        this.citizens = citizens;
    }

    public City(int id, String name, int year, int square) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.square = square;
    }

    public City(int id, int year, int square, List<Citizens> citizens) {
        this.id = id;
        this.year = year;
        this.square = square;
        this.citizens = citizens;
    }
}
