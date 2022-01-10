package io.memorix.cantaloupe;

import org.junit.jupiter.api.BeforeEach;

import edu.illinois.library.cantaloupe.config.Configuration;
import edu.illinois.library.cantaloupe.config.ConfigurationFactory;

public abstract class BaseTest {

    public static String NX_URL = "http://example.org/nuxeo";
    public static String NX_USERNAME = "Username";
    public static String NX_SECRET = "Secret";

    static {
        // Suppress a Dock icon and annoying Space transition in full-screen
        // mode in macOS.
        System.setProperty("java.awt.headless", "true");
        // Suppress an exception thrown by the JAI framework.
        System.setProperty("com.sun.media.jai.disableMediaLib", "true");
    }

    static public void setUp() throws Exception {
        ConfigurationFactory.clearInstance();
        System.setProperty(ConfigurationFactory.CONFIG_VM_ARGUMENT, "memory");
        Configuration.getInstance().setProperty(NuxeoKey.NX_URL.key(), "http://example.org/nuxeo");
        Configuration.getInstance().setProperty(NuxeoKey.NX_USERNAME.key(), "Username");
        Configuration.getInstance().setProperty(NuxeoKey.NX_SECRET.key(), "Secret");
    }

}
