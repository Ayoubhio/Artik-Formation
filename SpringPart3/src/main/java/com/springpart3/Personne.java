package com.springpart3;

//import jakarta.persistence.*;

//import javax.persistence.*;
import java.io.Serializable;

//@Entity
//@Table
public class Personne implements Serializable
{
    private static final long serialVersionUID = 1L;
//    @Id
//    @GeneratedValue(strategy=GenerationType.IDENTITY)
//    @Column(nullable = false , updatable = false)
    private Long id ;
//    @Column(nullable = false , length=45)
    private String nom;
//    @Column(nullable = false , length=45)
    private int age;

    public Personne(Long id, String nom, int age) {
        this.id = id;
        this.nom = nom;
        this.age = age;
    }

    public Personne() {

    }

    public Personne(String name, int age) {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
