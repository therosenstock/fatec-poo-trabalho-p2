package edu.curso.model;

import java.time.LocalDate;

public class Album {
    private long id = 0;
    private String titulo = "";
    private String artista = "";
    private String genero = "";
    private LocalDate data_lancamento = LocalDate.now();
    private int numero_faixas = 0;

    public Album(){

    }
    public Album(long id, String titulo, String artista, String genero, LocalDate data_lancamento, int numero_faixas) {
        this.id = id;
        this.titulo = titulo;
        this.artista = artista;
        this.genero = genero;
        this.data_lancamento = data_lancamento;
        this.numero_faixas = numero_faixas;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public LocalDate getData_lancamento() {
        return data_lancamento;
    }

    public void setData_lancamento(LocalDate data_lancamento) {
        this.data_lancamento = data_lancamento;
    }

    public int getNumero_faixas() {
        return numero_faixas;
    }

    public void setNumero_faixas(int numero_faixas) {
        this.numero_faixas = numero_faixas;
    }
}
