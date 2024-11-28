package edu.curso.control;
import edu.curso.model.Playlist;
import edu.curso.persistence.PlaylistDAO;
import edu.curso.persistence.PlaylistDAOImpl;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;

public class PlaylistControl {
    private ObservableList<Playlist> lista = FXCollections.observableArrayList();
    private long contador = 0;

    private LongProperty id = new SimpleLongProperty(0);
    private StringProperty nome = new SimpleStringProperty("");
    private StringProperty descricao = new SimpleStringProperty("");
    private ObjectProperty<LocalDate> dataCriacao = new SimpleObjectProperty<>(LocalDate.now());
    private IntegerProperty quantidadeMusica = new SimpleIntegerProperty(0);
    private StringProperty criadorPlaylist = new SimpleStringProperty("");

    private PlaylistDAO playlistDAO = null;

    public PlaylistControl() throws MusicaException {
        playlistDAO = new PlaylistDAOImpl();
    }

    public Playlist paraEntidade() {
        Playlist p = new Playlist();
        p.setId(id.get());
        p.setNome(nome.get());
        p.setDescricao(descricao.get());
        p.setDataCriacao(dataCriacao.get());
        p.setQuantidade_musica(quantidadeMusica.get());
        p.setCriador_playlist(criadorPlaylist.get());
        return p;
    }

    public void paraTela(Playlist p) {
        if (p != null) {
            id.set(p.getId());
            nome.set(p.getNome());
            descricao.set(p.getDescricao());
            dataCriacao.set(p.getDataCriacao());
            quantidadeMusica.set(p.getQuantidade_musica());
            criadorPlaylist.set(p.getCriador_playlist());
        }
    }

    public void excluir(Playlist p) throws MusicaException {
        playlistDAO.remover(p);
        pesquisarTodos();
    }

    public void limparTudo() {
        id.set(0);
        nome.set("");
        descricao.set("");
        dataCriacao.set(LocalDate.now());
        quantidadeMusica.set(0);
        criadorPlaylist.set("");
    }

    public void gravar() throws MusicaException {
        Playlist p = paraEntidade();
        if (p.getId() == 0) {
            this.contador += 1;
            p.setId(this.contador);
            playlistDAO.inserir(p);
        } else {
            playlistDAO.atualizar(p);
        }
        pesquisarTodos();
        limparTudo();
    }

    public void pesquisar() throws MusicaException {
        lista.clear();
        lista.addAll(playlistDAO.pesquisarPorNome(nome.get()));
    }

    public void pesquisarTodos() throws MusicaException {
        lista.clear();
        lista.addAll(playlistDAO.pesquisarPorNome(""));
    }

    public LongProperty idProperty() {
        return id;
    }

    public StringProperty nomeProperty() {
        return nome;
    }

    public StringProperty descricaoProperty() {
        return descricao;
    }

    public ObjectProperty<LocalDate> dataCriacaoProperty() {
        return dataCriacao;
    }

    public IntegerProperty quantidadeMusicaProperty() {
        return quantidadeMusica;
    }

    public StringProperty criadorPlaylistProperty() {
        return criadorPlaylist;
    }

    public ObservableList<Playlist> getLista() {
        return lista;
    }
}

