package org.example.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "citizens")
public class Citizens {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "language")
    private String language;

    @Column(name="name")
    private String name;

    @Column(name="count")
    private int count;

    @ManyToOne
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    private City city;

    public int getId() {
        return id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Citizens(int id, String name, String language, City city, int count) {
        this.id = id;
        this.language = language;
        this.name = name;
        this.count = count;
        this.city = city;
    }

    public Citizens( String name, String language, int count, City city) {
        this.language = language;
        this.name = name;
        this.count = count;
        this.city = city;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Citizens(String language, String name, City city) {
        this.language = language;
        this.name = name;
        this.city = city;
    }

    public Citizens() {
    }

    public Citizens(int id, String language, String name, City city) {
        this.id = id;
        this.language = language;
        this.name = name;
        this.city = city;
    }
}
