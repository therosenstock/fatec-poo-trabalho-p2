package edu.curso.persistence;

import edu.curso.control.MusicaException;
import edu.curso.model.Album;

import java.util.List;

public interface AlbumDAO {
    void inserir(Album a) throws MusicaException;
    void atualizar(Album a) throws MusicaException;
    void remover(Album a) throws MusicaException;
    List<Album> pesquisarPorNome(String nome) throws MusicaException;
}
