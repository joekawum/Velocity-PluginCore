package de.joekawum.pluginCore.mysql;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MySQLManager {
    private final MySQL mySQL;

    public MySQLManager(MySQL mySQL) {
        this.mySQL = mySQL;
    }

    public void createTable(String tableName, String value) {
        this.mySQL.update("CREATE TABLE IF NOT EXISTS " + tableName + "(" + value + ");");
    }

    public boolean playerExists(String tableName, UUID uuid) {
        try {
            ResultSet rs = this.mySQL.query("SELECT * FROM " + tableName + " WHERE uuid= '" + uuid.toString() + "'");
            boolean b = (rs != null && rs.next() && rs.getString("uuid") != null);
            if (rs != null)
                rs.close();
            return b;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean valueExists(String tableName, String where, Object whereValue) {
        try {
            ResultSet rs = this.mySQL.query("SELECT * FROM " + tableName + " WHERE " + where + "= '" + whereValue.toString() + "'");
            boolean b = (rs != null && rs.next() && rs.getString(where) != null);
            if (rs != null)
                rs.close();
            return b;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean insertValue(String tableName, String tableObjects, Object[] values) throws SQLException {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < values.length; i++)
            list.add("?");
        String x = String.join(",", (Iterable)list);
        PreparedStatement ps = this.mySQL.getStatement("INSERT INTO " + tableName + "(" + tableObjects + ") VALUES (" + x + ")");
        for (int j = 0; j < values.length; j++)
            ps.setObject(j + 1, values[j]);
        ps.executeUpdate();
        ps.close();
        return true;
    }

    public boolean setValue(String tableName, String where, Object whereValue, String object, Object value) throws SQLException {
        if (!valueExists(tableName, where, whereValue))
            return false;
        PreparedStatement ps = this.mySQL.getStatement("UPDATE " + tableName + " SET " + object + "= ? WHERE " + where + "= ?;");
        ps.setObject(1, value);
        ps.setObject(2, whereValue);
        ps.executeUpdate();
        ps.close();
        return true;
    }

    public Object getValue(String tableName, String where, Object whereValue, String object) throws SQLException {
        if (!valueExists(tableName, where, whereValue))
            return null;
        ResultSet rs = this.mySQL.query("SELECT * FROM " + tableName + " WHERE " + where + "= '" + whereValue.toString() + "'");
        if (rs != null && rs.next()) {
            Object returnValue = rs.getObject(object);
            rs.close();
            return returnValue;
        }
        return Integer.valueOf(0);
    }

    public boolean deleteValue(String tableName, String where, Object whereValue) throws SQLException {
        if (!valueExists(tableName, where, whereValue))
            return false;
        PreparedStatement ps = this.mySQL.getStatement("DELETE FROM " + tableName + " WHERE " + where + "= ?;");
        ps.setObject(1, whereValue);
        ps.executeUpdate();
        ps.close();
        return true;
    }

    public int getTableSize(String tableName) throws SQLException {
        ResultSet rs = this.mySQL.query("SELECT * FROM " + tableName);
        int count = 0;
        for (; rs != null && rs.next(); count++);
        rs.close();
        return count;
    }

    public List<Object[]> filterTable(String tableName, String where, Object whereValue, String[] objects) throws SQLException {
        ResultSet rs = this.mySQL.query("SELECT * FROM " + tableName + " WHERE " + where + "= '" + String.valueOf(whereValue) + "'");
        List<Object[]> list = new ArrayList();
        while (rs != null && rs.next()) {
            Object[] obj = new Object[objects.length];
            for (int i = 0; i < objects.length; i++)
                obj[i] = rs.getObject(objects[i]);
            list.add(obj);
        }
        return list;
    }
}