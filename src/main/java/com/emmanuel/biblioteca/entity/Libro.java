package com.emmanuel.biblioteca.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String genero;

    @Column(name = "codigo_unico", unique = true, nullable = false)
    private String codigoUnico;

    @ManyToOne
    @JoinColumn(name = "autor_id", nullable = false)
    @JsonBackReference // Evita la recursión infinita al serializar
    private Autor autor;

    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("libro-copiaLibro")
    List<CopiaLibro> copiaLibros;

    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("libro-resena")
    private List<Resena> resenas;

    public Libro() {}

    public Libro(String titulo, String genero, String codigoUnico, Autor autor) {
        this.titulo = titulo;
        this.genero = genero;
        this.codigoUnico = codigoUnico;
        this.autor = autor;
    }

    public List<Resena> getResenas() {
        return resenas;
    }

    public void setResenas(List<Resena> resenas) {
        this.resenas = resenas;
    }

    public List<CopiaLibro> getCopiaLibros() {
        return copiaLibros;
    }

    public void setCopiaLibros(List<CopiaLibro> copiaLibros) {
        this.copiaLibros = copiaLibros;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getCodigoUnico() {
        return codigoUnico;
    }

    public void setCodigoUnico(String codigoUnico) {
        this.codigoUnico = codigoUnico;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Libro{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", genero='" + genero + '\'' +
                ", codigoUnico='" + codigoUnico + '\'' +
                ", autor=" + autor +
                ", copiaLibros=" + copiaLibros +
                ", resenas=" + resenas +
                '}';
    }
}
