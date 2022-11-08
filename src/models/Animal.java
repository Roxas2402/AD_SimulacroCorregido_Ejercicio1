package models;

import java.io.Serializable;

public class Animal implements Serializable {
    private String especie;
    private String raza;
    private int edad;
    private String color;

    @Override
    public String toString() {
        return "Animal{" +
                "especie='" + especie + '\'' +
                ", raza='" + raza + '\'' +
                ", edad=" + edad +
                ", color='" + color + '\'' +
                '}';
    }

    public Animal() {
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Animal(String especie, String raza, int edad, String color) {
        this.especie = especie;
        this.raza = raza;
        this.edad = edad;
        this.color = color;
    }
}
