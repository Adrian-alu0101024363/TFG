package com.example.myapplication;

import org.jetbrains.annotations.NotNull;

import kotlin.jvm.JvmField;

public class Userinfo {
    private  String name;
    private String email;
    private int id;
    private int nivel;
    private double experiencia;
    private String mother;
    private String target;
    private int active;
    @JvmField
    private String rango;

    public Userinfo(String name, String email, int id) {
        this.name = name;
        this.email = email;
        this.id = id;
    }
    public Userinfo(int id, String name, String email, int nivel, double experiencia, String rango, String mother, String target, int active) {
        this.name = name;
        this.email = email;
        this.id = id;
        this.nivel = nivel;
        this.experiencia = experiencia;
        this.rango = rango;
        this.mother = mother;
        this.target = target;
        this.active = active;
    }
    public String getName() {
        return name;
    }
    public  String getEmail() {
        return email;
    }
    public int getId() {
        return id;
    }
    public int getNivel() { return nivel;}
    public double getExperiencia() {return experiencia;}
    public String getRango() {return rango;}
    public String getMother() {return mother;}
    public String getTarget() {return  target;}
    public int getActive() {return active;}
    public void setName(String name) {
        this.name = name;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setNivel(int level) {this.nivel = level;}
    public void setExperiencia(double experience) { this.experiencia = experience;}
    public void setMother(String mother) {this.mother = mother;}
    public void setTarget(String target) {this.target = target;}
    public void setActive(int active) {this.active = active;}

    public void setRango(@NotNull String s) {
        this.rango = s;
    }
}
