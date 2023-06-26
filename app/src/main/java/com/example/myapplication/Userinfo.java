package com.example.myapplication;

import org.jetbrains.annotations.NotNull;

import kotlin.jvm.JvmField;

public class Userinfo {
    private  String name;
    private String email;
    private int id;
    private int nivel;
    private double experiencia;
    @JvmField
    private String rango;

    public Userinfo(String name, String email, int id) {
        this.name = name;
        this.email = email;
        this.id = id;
    }
    public Userinfo(int id, String name, String email, int nivel, double experiencia, String rango) {
        this.name = name;
        this.email = email;
        this.id = id;
        this.nivel = nivel;
        this.experiencia = experiencia;
        this.rango = rango;
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

    public void setRango(@NotNull String s) {
        this.rango = s;
    }
}
