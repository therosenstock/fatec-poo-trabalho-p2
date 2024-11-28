package edu.curso.boundary;

import edu.curso.control.AlbumControl;
import edu.curso.control.MusicaException;
import edu.curso.model.Album;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Font;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.NumberStringConverter;

import java.time.LocalDate;

public class AlbumBoundary implements Tela{
    private Label lblId = new Label("");
    private TextField txtTitulo = new TextField();
    private TextField txtArtista = new TextField();
    private TextField txtGenero = new TextField();
    private DatePicker txtLancamento = new DatePicker();
    private TextField txtNumeroFaixas = new TextField();

    private TableView<Album> tableView = new TableView<>();

    private AlbumControl control = null;

    @Override
    public Pane render() {
        try{
            control = new AlbumControl();
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
                new Alert(Alert.AlertType.ERROR, "Erro ao gravar o álbum", ButtonType.OK).showAndWait();
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

        Label titleLabel = new Label("CRUD de Álbuns");
        titleLabel.setFont(new Font(20));


        grid.add(titleLabel, 1, 0);
        grid.add(new Label("Id: "), 1, 1);
        grid.add(lblId, 2, 1);
        grid.add(new Label("Título: "), 1, 2);
        grid.add(txtTitulo, 2, 2);
        grid.add(btnNovo, 3, 2);
        grid.add(new Label("Artista/Banda: "), 1, 3);
        grid.add(txtArtista, 2, 3);
        grid.add(new Label("Gênero: "), 1, 4);
        grid.add(txtGenero, 2, 4);
        grid.add(new Label("Data de Lançamento: "), 1, 5);
        grid.add(txtLancamento, 2, 5);
        grid.add(new Label("Número de faixas: "), 1, 6);
        grid.add(txtNumeroFaixas, 2, 6);

        grid.add(btnGravar, 1, 7);
        grid.add(btnPesquisar, 2, 7);

        grid.setVgap(10);
        grid.setHgap(10);

        comunicacaoControle();
        gerarTabela();
        pane.setTop(grid);
        pane.setCenter(tableView);
        return pane;
    }

    public void gerarTabela(){
        TableColumn<Album, Long> col1 = new TableColumn<>("Id");
        col1.setCellValueFactory(new PropertyValueFactory<Album, Long>("id"));

        TableColumn<Album, String> col2 = new TableColumn<>("Título");
        col2.setCellValueFactory(new PropertyValueFactory<Album, String>("titulo"));

        TableColumn<Album, String> col3 = new TableColumn<>("Artista/Banda");
        col3.setCellValueFactory(new PropertyValueFactory<Album, String>("artista"));

        TableColumn<Album, String> col4 = new TableColumn<>("Gênero");
        col4.setCellValueFactory(new PropertyValueFactory<Album, String>("genero"));

        TableColumn<Album, LocalDate> col5 = new TableColumn<>("Data de Lançamento");
        col5.setCellValueFactory(new PropertyValueFactory<Album, LocalDate>("data_lancamento"));

        TableColumn<Album, Integer> col6 = new TableColumn<>("Número de Faixas");
        col6.setCellValueFactory(new PropertyValueFactory<Album, Integer>("numero_faixas"));

        tableView.getSelectionModel().selectedItemProperty().addListener(
                (obs, antigo, novo) -> {
                    if(novo != null){
                        control.paraTela(novo);
                    }
                }
        );
        Callback<TableColumn<Album, Void>, TableCell<Album, Void>> cb =
                new Callback<>() {
                    @Override
                    public TableCell<Album, Void> call(TableColumn<Album, Void> param) {
                        TableCell<Album, Void> celula = new TableCell<>() {
                            final Button btnApagar = new Button("Apagar");
                            {
                                btnApagar.setOnAction(e -> {
                                    Album album = tableView.getItems().get(getIndex());
                                    try {
                                        control.excluir(album);
                                    } catch (MusicaException err) {
                                        new Alert(Alert.AlertType.ERROR, "Erro ao excluir o álbum selecionado", ButtonType.OK).showAndWait();
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

        TableColumn<Album, Void> col7 = new TableColumn<>("Ação");
        col7.setCellFactory(cb);

        tableView.getColumns().addAll(col1, col2, col3, col4, col5, col6, col7);
        tableView.setItems(control.getLista());

    }
    public static ObservableList<Album> getAlbumList() {
                return FXCollections.observableArrayList(
                new Album(1, "Abbey Road", "The Beatles", "Rock", LocalDate.of(1969, 9, 26), 17),
                new Album(2, "Thriller", "Michael Jackson", "Pop", LocalDate.of(1982, 11, 30), 9)
        );
    }

    public void comunicacaoControle(){
        control.idProperty().addListener((obs, antigo, novo) -> {
            lblId.setText(String.valueOf(novo));
        });

        IntegerStringConverter integerConverter = new IntegerStringConverter();
        Bindings.bindBidirectional(control.tituloProperty(), txtTitulo.textProperty());
        Bindings.bindBidirectional(control.artistaProperty(), txtArtista.textProperty());
        Bindings.bindBidirectional(control.generoProperty(), txtGenero.textProperty());
        Bindings.bindBidirectional(control.dataLancamentoProperty(), txtLancamento.valueProperty());
        Bindings.bindBidirectional(txtNumeroFaixas.textProperty(), control.numeroFaixasProperty(), (StringConverter) integerConverter);
    }

}
