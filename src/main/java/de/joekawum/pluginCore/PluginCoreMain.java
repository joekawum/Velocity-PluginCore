package de.joekawum.pluginCore;

import com.google.inject.Inject;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import org.slf4j.Logger;

@Plugin(id = "plugincore-velocity", name = "PluginCore", version = "1.0-SNAPSHOT", authors = {"joekawumYT"})
public final class PluginCoreMain {

    private final ProxyServer server;
    private final Logger logger;

    @Inject
    public PluginCoreMain(ProxyServer server, Logger logger) {
        this.server = server;
        this.logger = logger;

        logger.info("booting up...");
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
    }
}
