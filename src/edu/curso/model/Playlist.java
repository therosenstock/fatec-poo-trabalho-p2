package edu.curso.model;

import java.time.LocalDate;

public class Playlist {
    private long id = 0;
    private String nome = "";
    private String descricao = "";
    private LocalDate dataCriacao = LocalDate.now();
    private int quantidade_musica = 0;
    private String criador_playlist = "";

    public Playlist(){

    }
    public Playlist(long id, String nome, String descricao, LocalDate dataCriacao, int quantidade_musica, String criador_playlist) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.dataCriacao = dataCriacao;
        this.quantidade_musica = quantidade_musica;
        this.criador_playlist = criador_playlist;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public int getQuantidade_musica() {
        return quantidade_musica;
    }

    public void setQuantidade_musica(int quantidade_musica) {
        this.quantidade_musica = quantidade_musica;
    }

    public String getCriador_playlist() {
        return criador_playlist;
    }

    public void setCriador_playlist(String criador_playlist) {
        this.criador_playlist = criador_playlist;
    }
}
