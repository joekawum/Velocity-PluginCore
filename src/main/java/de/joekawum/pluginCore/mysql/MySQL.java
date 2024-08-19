package de.joekawum.pluginCore.mysql;

import java.sql.*;

public class MySQL {
    private final String HOST;

    private final String DATABASE;

    private final String USER;

    private final String PASSWORD;

    private final int PORT;

    private Connection con;

    public MySQL(String host, String database, String user, String password, int port) {
        this.HOST = host;
        this.DATABASE = database;
        this.USER = user;
        this.PASSWORD = password;
        this.PORT = port;
        connect();
    }

    public void connect() {
        try {
            this.con = DriverManager.getConnection("jdbc:mysql://" + this.HOST + ":" + this.PORT + "/" + this.DATABASE + "?autoReconnect=true", this.USER, this.PASSWORD);
            System.out.println("[PluginCore] MySQL Verbindung erfolgreich");
        } catch (SQLException e) {
            System.out.println("[PluginCore] MySQL Verbindung fehlgeschlagen!");
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            if (this.con != null) {
                this.con.close();
                System.out.println("[PluginCore] MySQL Verbindung beendet");
            }
        } catch (SQLException e) {
            System.out.println("[PluginCore] Fehler beim Trennen der MySQL Verbindung!");
        }
    }

    public void update(String qry) {
        try {
            Statement st = this.con.createStatement();
            st.executeUpdate(qry);
            st.close();
        } catch (SQLException e) {
            connect();
            System.out.println(e);
        }
    }

    public ResultSet query(String qry) {
        ResultSet rs = null;
        try {
            Statement st = this.con.createStatement();
            rs = st.executeQuery(qry);
        } catch (SQLException e) {
            connect();
            System.out.println(e);
        }
        return rs;
    }

    public PreparedStatement getStatement(String statement) throws SQLException {
        return this.con.prepareStatement(statement);
    }
}