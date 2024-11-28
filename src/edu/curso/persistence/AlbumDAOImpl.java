package edu.curso.persistence;

import edu.curso.control.MusicaException;
import edu.curso.model.Album;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlbumDAOImpl implements AlbumDAO{

    private final static String DB_CLASS = "org.mariadb.jdbc.Driver";
    private final static String DB_URL = "jdbc:mariadb://localhost:3306/musicadb?allowPublicKeyRetrieval=true";
    private final static String DB_USER = "root";
    private final static String DB_PASS = "1504";
    private Connection con = null;

    public AlbumDAOImpl() throws MusicaException {
        try {
            Class.forName(DB_CLASS);
            con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        } catch (ClassNotFoundException | SQLException e) {
            throw new MusicaException(e);
        }
    }

    @Override
    public void inserir(Album a) throws MusicaException {
        try {
            String sql = "" +
                    "INSERT INTO album (" +
                    "titulo, artista, genero, data_lancamento, numero_faixas) " +
                    "VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, a.getTitulo());
            stm.setString(2, a.getArtista());
            stm.setString(3, a.getGenero());
            Date dt = Date.valueOf(a.getData_lancamento());
            stm.setDate(4, dt);
            stm.setInt(5, a.getNumero_faixas());
            int i = stm.executeUpdate();
        } catch(SQLException e){
            throw new MusicaException(e);
        }
    }

    @Override
    public void atualizar(Album a) throws MusicaException {
        try {
            String sql = "" +
                    "UPDATE album SET " +
                    "titulo = ?, artista = ?, genero = ?, data_lancamento = ?, numero_faixas = ? " +
                    "WHERE id = ?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, a.getTitulo());
            stm.setString(2, a.getArtista());
            stm.setString(3, a.getGenero());
            Date dt = Date.valueOf(a.getData_lancamento());
            stm.setDate(4, dt);
            stm.setInt(5, a.getNumero_faixas());
            stm.setLong(6, a.getId());
            int i = stm.executeUpdate();
        } catch(SQLException e){
            throw new MusicaException(e);
        }
    }

    @Override
    public void remover(Album a) throws MusicaException {
        try {
            String SQL = """
                    DELETE FROM album WHERE id = ?
                    """;
            PreparedStatement stm = con.prepareStatement(SQL);
            stm.setLong( 1, a.getId() );
            int i = stm.executeUpdate();
        } catch (SQLException e) {
            throw new MusicaException( e );
        }

    }

    @Override
    public List<Album> pesquisarPorNome(String nome) throws MusicaException {
        List<Album> lista = new ArrayList<>();
        try {
            String SQL = """
                    SELECT * FROM album WHERE titulo LIKE ?
                    """;
            PreparedStatement stm = con.prepareStatement(SQL);
            stm.setString(1, "%" + nome + "%");
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Album a = new Album();
                a.setId( rs.getLong("id") );
                a.setTitulo( rs.getString("titulo") );
                a.setArtista( rs.getString("artista") );
                a.setGenero( rs.getString("genero") );
                a.setData_lancamento( rs.getDate("data_lancamento").toLocalDate() );
                a.setNumero_faixas(rs.getInt("numero_faixas"));
                lista.add( a );
            }
        } catch (SQLException e) {
            throw new MusicaException( e );
        }
        return lista;
    }
}
