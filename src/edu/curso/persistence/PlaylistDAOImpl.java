package edu.curso.persistence;

import edu.curso.control.MusicaException;
import edu.curso.model.Playlist;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDAOImpl implements PlaylistDAO{

    private final static String DB_CLASS = "org.mariadb.jdbc.Driver";
    private final static String DB_URL = "jdbc:mariadb://localhost:3306/musicadb?allowPublicKeyRetrieval=true";
    private final static String DB_USER = "root";
    private final static String DB_PASS = "1504";
    private Connection con = null;

    public PlaylistDAOImpl() throws MusicaException {
        try {
            Class.forName(DB_CLASS);
            con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        } catch (ClassNotFoundException | SQLException e) {
            throw new MusicaException(e);
        }
    }

    @Override
    public void inserir(Playlist p) throws MusicaException {
        try {
            String sql = "INSERT INTO playlist (" +
                    "nome, descricao, data_criacao, quantidade_musica, criador_playlist) " +
                    "VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, p.getNome());
            stm.setString(2, p.getDescricao());
            Date dt = Date.valueOf(p.getDataCriacao());
            stm.setDate(3, dt);
            stm.setInt(4, p.getQuantidade_musica());
            stm.setString(5, p.getCriador_playlist());
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new MusicaException(e);
        }
    }

    @Override
    public void atualizar(Playlist p) throws MusicaException {
        try {
            String sql = "UPDATE playlist SET " +
                    "nome = ?, descricao = ?, data_criacao = ?, quantidade_musica = ?, criador_playlist = ? " +
                    "WHERE id = ?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, p.getNome());
            stm.setString(2, p.getDescricao());
            Date dt = Date.valueOf(p.getDataCriacao());
            stm.setDate(3, dt);
            stm.setInt(4, p.getQuantidade_musica());
            stm.setString(5, p.getCriador_playlist());
            stm.setLong(6, p.getId());
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new MusicaException(e);
        }
    }

    @Override
    public void remover(Playlist p) throws MusicaException {
        try {
            String sql = "DELETE FROM playlist WHERE id = ?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setLong(1, p.getId());
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new MusicaException(e);
        }
    }

    @Override
    public List<Playlist> pesquisarPorTitulo(String titulo) throws MusicaException {
        List<Playlist> lista = new ArrayList<>();
        try {
            String sql = "SELECT * FROM playlist WHERE nome LIKE ?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, "%" + titulo + "%");
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Playlist p = new Playlist();
                p.setId(rs.getLong("id"));
                p.setNome(rs.getString("nome"));
                p.setDescricao(rs.getString("descricao"));
                p.setDataCriacao(rs.getDate("data_criacao").toLocalDate());
                p.setQuantidade_musica(rs.getInt("quantidade_musica"));
                p.setCriador_playlist(rs.getString("criador_playlist"));
                lista.add(p);
            }
        } catch (SQLException e) {
            throw new MusicaException(e);
        }
        return lista;
    }
}
