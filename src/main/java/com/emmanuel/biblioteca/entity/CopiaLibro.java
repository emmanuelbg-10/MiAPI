package com.emmanuel.biblioteca.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class CopiaLibro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "libro_id", nullable = false)
    @JsonBackReference("libro-copiaLibro")
    private Libro libro;

    @OneToMany(mappedBy = "copiaLibro", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("copiaLibro-prestamos")
    private List<Prestamo> prestamos;

    @Column(nullable = false)
    private boolean prestado = false; // 🔹 Indica si la copia está prestada

    public CopiaLibro() {}

    public CopiaLibro(Libro libro) {
        this.libro = libro;
    }

    public boolean isPrestado() {
        return prestado;
    }

    public void setPrestado(boolean prestado) {
        this.prestado = prestado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public List<Prestamo> getPrestamos() {
        return prestamos;
    }

    public void setPrestamos(List<Prestamo> prestamos) {
        this.prestamos = prestamos;
    }

    @Override
    public String toString() {
        return "CopiaLibro{" +
                "id=" + id +
                ", libro=" + libro +
                ", prestamos=" + prestamos +
                '}';
    }
}
