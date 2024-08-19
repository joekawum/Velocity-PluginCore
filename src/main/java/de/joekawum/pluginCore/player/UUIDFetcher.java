package de.joekawum.pluginCore.player;

import de.joekawum.pluginCore.PluginCore;
import de.joekawum.pluginCore.mysql.MySQL;
import de.joekawum.pluginCore.mysql.MySQLManager;
import java.sql.SQLException;
import java.util.UUID;

public class UUIDFetcher {
    private final MySQL mySQL;

    private final MySQLManager mySQLManager;

    public UUIDFetcher(PluginCore pluginCore, MySQL mySQL) {
        this.mySQL = mySQL;
        this.mySQLManager = pluginCore.mysql();
        this.mySQLManager.createTable("PlayerData", "uuid VARCHAR(36), username VARCHAR(16), ip VARCHAR(32), joinDate LONG");
    }

    public void fetch(UUID uuid, String name, String ip) throws SQLException {
        if (this.mySQLManager.valueExists("PlayerData", "uuid", uuid.toString())) {
            this.mySQLManager.setValue("PlayerData", "uuid", uuid.toString(), "username", name);
            this.mySQLManager.setValue("PlayerData", "uuid", uuid.toString(), "ip", ip);
        } else {
            this.mySQLManager.insertValue("PlayerData", "uuid, username, ip, joinDate", new Object[] {
                    uuid.toString(),
                    name,
                    ip,
                    Long.valueOf(System.currentTimeMillis()) });
        }
    }

    public String getUsername(UUID uuid) throws SQLException {
        if (this.mySQLManager.valueExists("PlayerData", "uuid", uuid.toString()))
            return (String)this.mySQLManager.getValue("PlayerData", "uuid", uuid.toString(), "username");
        return null;
    }

    public UUID getUniqueId(String username) throws SQLException {
        if (this.mySQLManager.valueExists("PlayerData", "username", username))
            return UUID.fromString((String)this.mySQLManager.getValue("PlayerData", "username", username, "uuid"));
        return null;
    }

    public String getIP(UUID uuid) throws SQLException {
        if (this.mySQLManager.valueExists("PlayerData", "uuid", uuid.toString()))
            return (String)this.mySQLManager.getValue("PlayerData", "uuid", uuid.toString(), "ip");
        return null;
    }
}
