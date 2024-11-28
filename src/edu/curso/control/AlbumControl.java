package edu.curso.control;

import edu.curso.model.Album;
import edu.curso.persistence.AlbumDAO;
import edu.curso.persistence.AlbumDAOImpl;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.List;

public class AlbumControl {

    private ObservableList<Album> lista = FXCollections.observableArrayList();
    private long contador = 0;

    private LongProperty id = new SimpleLongProperty(0);
    private StringProperty titulo = new SimpleStringProperty("");
    private StringProperty artista = new SimpleStringProperty("");
    private StringProperty genero = new SimpleStringProperty("");
    private ObjectProperty<LocalDate> dataLancamento = new SimpleObjectProperty<>(LocalDate.now());
    private IntegerProperty numeroFaixas = new SimpleIntegerProperty(0);
    private AlbumDAO albumDAO = null;

    public AlbumControl() throws MusicaException{
        albumDAO = new AlbumDAOImpl();
    }

    public Album paraEntidade(){
        Album a = new Album();
        a.setId(id.get());
        a.setTitulo(titulo.get());
        a.setArtista(artista.get());
        a.setGenero(genero.get());
        a.setData_lancamento(dataLancamento.get());
        a.setNumero_faixas(numeroFaixas.get());
        return a;
    }

    public void paraTela (Album a){
        if (a!= null){
            id.set(a.getId());
            titulo.set(a.getTitulo());
            artista.set(a.getArtista());
            genero.set(a.getGenero());
            dataLancamento.set(a.getData_lancamento());
            numeroFaixas.set(a.getNumero_faixas());
        }
    }

    public void excluir(Album a) throws MusicaException{
        albumDAO.remover(a);
        pesquisarTodos();
    }

    public void limparTudo(){
        id.set(0);
        titulo.set("");
        artista.set("");
        genero.set("");
        dataLancamento.set(LocalDate.now());
        numeroFaixas.set(0);
    }

    public void gravar() throws MusicaException{
        Album a = paraEntidade();
        if(a.getId() == 0){
            this.contador += 1;
            a.setId(this.contador);
            albumDAO.inserir(a);
        } else{
            albumDAO.atualizar(a);
        }
        limparTudo();
        pesquisarTodos();
    }

    public void pesquisar() throws MusicaException{
        List<Album> temp = albumDAO.pesquisarPorNome(titulo.get());
        lista.clear();
        lista.addAll(temp);
    }

    public void pesquisarTodos() throws MusicaException{
        List<Album> temp = albumDAO.pesquisarPorNome("");
        lista.clear();
        lista.addAll(temp);
    }

    public LongProperty idProperty() {
        return id;
    }

    public StringProperty tituloProperty() {
        return titulo;
    }

    public StringProperty artistaProperty() {
        return artista;
    }

    public StringProperty generoProperty() {
        return genero;
    }

    public ObjectProperty<LocalDate> dataLancamentoProperty() {
        return dataLancamento;
    }

    public IntegerProperty numeroFaixasProperty() {
        return numeroFaixas;
    }

    public ObservableList<Album> getLista() {
        return lista;
    }
}
