package edu.curso.persistence;

import edu.curso.control.MusicaException;

import edu.curso.model.Playlist;

import java.util.List;

public interface PlaylistDAO {
    void inserir(Playlist p) throws MusicaException;
    void atualizar(Playlist p) throws MusicaException;
    void remover(Playlist p) throws MusicaException;
    List<Playlist> pesquisarPorTitulo(String titulo) throws MusicaException;
}
