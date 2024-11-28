package edu.curso;

import edu.curso.boundary.AlbumBoundary;
import edu.curso.boundary.PlaylistBoundary;
import edu.curso.boundary.Tela;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Main extends Application {
    private Map<String, Tela> telas = new HashMap<>();

    @Override
    public void start(Stage stage) throws Exception {
        telas.put("album", new AlbumBoundary());
        telas.put("playlist", new PlaylistBoundary());

        BorderPane pane = new BorderPane();
        MenuBar menuBar = new MenuBar();
        Menu menuMusica = new Menu("Música");
        MenuItem menuItemAlbum = new MenuItem("Álbum");
        menuItemAlbum.setOnAction(e ->
                pane.setCenter(telas.get("album").render())
        );
        MenuItem menuItemPlaylist = new MenuItem("Playlist");
        menuItemPlaylist.setOnAction( e->
                pane.setCenter( telas.get("playlist").render() )
        );
        menuMusica.getItems().addAll(menuItemAlbum);
        menuMusica.getItems().addAll(menuItemPlaylist);
        menuBar.getMenus().addAll(menuMusica);
        pane.setTop(menuBar);
        Scene scn = new Scene (pane, 800, 600);
        stage.setScene(scn);
        stage.setTitle("Suas músicas");
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }
}