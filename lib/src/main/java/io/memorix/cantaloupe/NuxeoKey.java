package io.memorix.cantaloupe;

public enum NuxeoKey {
    
    NX_URL("nuxeo.url"),
    NX_USERNAME("nuxeo.username"),
    NX_SECRET("nuxeo.secret"),
    NX_LOAD_PROPERTIES("nuxeo.load_properties"),
    NX_PROXY_ENABLED("nuxeo.proxy.enabled"),
    NX_PROXY_SERVER("nuxeo.proxy.server"),
    NX_PROXY_PORT("nuxeo.proxy.port");

    private final String key;

    NuxeoKey(String key) {
        this.key = key;
    }

    public String key() {
        return key;
    }

    public String toString() {
        return key();
    }
}
