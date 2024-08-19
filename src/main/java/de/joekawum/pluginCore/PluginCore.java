package de.joekawum.pluginCore;

import de.joekawum.pluginCore.mysql.MySQL;
import de.joekawum.pluginCore.mysql.MySQLManager;
import de.joekawum.pluginCore.player.UUIDFetcher;

public final class PluginCore {
    private static PluginCore instance;

    private final MySQL mySQL;

    private final MySQLManager mySQLManager;

    private final UUIDFetcher uuidFetcher;

    public PluginCore() {
        instance = this;
        this.mySQL = new MySQL("127.0.0.1", "velocityCore", "root", "", 3306);
        this.mySQLManager = new MySQLManager(this.mySQL);
        this.uuidFetcher = new UUIDFetcher(this, this.mySQL);
    }

    public MySQLManager mysql() {
        return this.mySQLManager;
    }

    public UUIDFetcher uuidFetcher() {
        return this.uuidFetcher;
    }

    public void disable() {
        this.mySQL.close();
    }

    public static PluginCore instance() {
        if (instance == null)
            return new PluginCore();
        return instance;
    }
}
