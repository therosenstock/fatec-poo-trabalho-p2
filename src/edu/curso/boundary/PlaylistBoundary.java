package edu.curso.boundary;

import edu.curso.control.AlbumControl;
import edu.curso.control.MusicaException;
import edu.curso.control.PlaylistControl;
import edu.curso.model.Playlist;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.util.Callback;

import java.time.LocalDate;

public class PlaylistBoundary implements Tela{
    private Label lblId = new Label("");
    private TextField txtNome = new TextField();
    private TextField txtDescricao = new TextField();
    private TextField txtQuantidadeMusica = new TextField();
    private DatePicker dataCriacao = new DatePicker();
    private TextField txtCriador = new TextField();

    private TableView<Playlist> tableView = new TableView<>();

    private PlaylistControl control = null;

    @Override
    public Pane render() {
        try{
            control = new PlaylistControl();
            control.pesquisarTodos();
        } catch(MusicaException e){
            new Alert(Alert.AlertType.ERROR, "Erro ao iniciar o banco de dados", ButtonType.OK).showAndWait();
        }

        BorderPane pane = new BorderPane();
        GridPane grid = new GridPane();

        Button btnGravar = new Button("Gravar");
        btnGravar.setOnAction(e -> {
            try{
                control.gravar();
            } catch(MusicaException err){
                new Alert(Alert.AlertType.ERROR, "Erro ao gravar a playlist", ButtonType.OK).showAndWait();
            }
            tableView.refresh();
        });
        Button btnPesquisar = new Button("Pesquisar");
        btnPesquisar.setOnAction(e -> {
            try{
                control.pesquisar();
            } catch(MusicaException err){
                new Alert(Alert.AlertType.ERROR, "Erro ao pesquisar por nome", ButtonType.OK).showAndWait();
            }
        });
        Button btnNovo = new Button("*");
        btnNovo.setOnAction( e -> control.limparTudo() );

        Label titleLabel = new Label("CRUD de Playlists");
        titleLabel.setFont(new Font(20));


        grid.add(titleLabel, 1, 0);

        grid.add(new Label("Id: "), 1, 1);
        grid.add(lblId, 2, 1);
        grid.add(new Label("Nome da Playlist: "), 1, 2);
        grid.add(txtNome, 2, 2);
        grid.add(btnNovo, 3, 2);
        grid.add(new Label("Descrição: "), 1, 3);
        grid.add(txtDescricao, 2, 3);
        grid.add(new Label("Data de criação: "), 1, 4);
        grid.add(dataCriacao, 2, 4);
        grid.add(new Label("Quantidade de músicas: "), 1, 5);
        grid.add(txtQuantidadeMusica, 2, 5);
        grid.add(new Label("Criador da Playlist: "), 1, 6);
        grid.add(txtCriador, 2, 6);

        grid.add(btnGravar, 1, 7);
        grid.add(btnPesquisar, 2, 7);

        grid.setVgap(10);
        grid.setHgap(10);
        gerarTabela();
        pane.setTop(grid);
        pane.setCenter(tableView);
        return pane;
    }

    public void gerarTabela(){
        TableColumn<Playlist, Long> col1 = new TableColumn<>("Id");
        col1.setCellValueFactory(new PropertyValueFactory<Playlist, Long>("id"));

        TableColumn<Playlist, String> col2 = new TableColumn<>("Nome da Playlist");
        col2.setCellValueFactory(new PropertyValueFactory<Playlist, String>("nome"));

        TableColumn<Playlist, String> col3 = new TableColumn<>("Descrição");
        col3.setCellValueFactory(new PropertyValueFactory<Playlist, String>("descricao"));

        TableColumn<Playlist, LocalDate> col4 = new TableColumn<>("Data de criação");
        col4.setCellValueFactory(new PropertyValueFactory<Playlist, LocalDate>("dataCriacao"));

        TableColumn<Playlist, Integer> col5 = new TableColumn<>("Qtde de músicas");
        col5.setCellValueFactory(new PropertyValueFactory<Playlist, Integer>("quantidade_musica"));

        TableColumn<Playlist, String> col6 = new TableColumn<>("Criador da Playlist");
        col6.setCellValueFactory(new PropertyValueFactory<Playlist, String>("criador_playlist"));

        tableView.getSelectionModel().selectedItemProperty().addListener(
                (obs, antigo, novo) -> {
                    if(novo != null){
                        control.paraTela(novo);
                    }
                }
        );
        Callback<TableColumn<Playlist, Void>, TableCell<Playlist, Void>> cb =
                new Callback<>() {
                    @Override
                    public TableCell<Playlist, Void> call(TableColumn<Playlist, Void> param) {
                        TableCell<Playlist, Void> celula = new TableCell<>() {
                            final Button btnApagar = new Button("Apagar");
                            {
                                btnApagar.setOnAction(e -> {
                                    Playlist play = tableView.getItems().get(getIndex());
                                    try{
                                        control.excluir(play);
                                    } catch(MusicaException err){
                                        new Alert(Alert.AlertType.ERROR, "Erro ao excluir a playlist selecionada");
                                    }
                                });
                            }

                            @Override
                            protected void updateItem(Void item, boolean empty) {
                                if(!empty){
                                    setGraphic(btnApagar);
                                } else{
                                    setGraphic(null);
                                }
                            }
                        };
                        return celula;
                    }
                };

        TableColumn<Playlist, Void> col7 = new TableColumn<>("Ação");
        col7.setCellFactory(cb);

        tableView.getColumns().addAll(col1, col2, col3, col4, col5, col6, col7);
        tableView.setItems(control.getLista());

    }

    public void comunicacaoControle(){
        control.idProperty().add
    }

    public static ObservableList<Playlist> getPlaylists() {
        ObservableList<Playlist> playlists = FXCollections.observableArrayList();

        Playlist playlist1 = new Playlist(1, "Top Hits", "Uma seleção das músicas mais populares.", LocalDate.of(2023, 5, 12), 20, "João Silva");
        playlists.add(playlist1);

        Playlist playlist2 = new Playlist(2, "Chill Vibes", "Playlist para relaxar com músicas suaves.", LocalDate.of(2022, 8, 25), 15, "Maria Oliveira");
        playlists.add(playlist2);

        return playlists;
    }
}
