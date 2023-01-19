package com.petyes.domain;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static org.junit.jupiter.api.extension.ExtensionContext.Namespace.GLOBAL;

public abstract class BaseSetupExtension
        implements BeforeAllCallback, ExtensionContext.Store.CloseableResource {
    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        String uniqueKey = this.getClass().getName();
        Object value = context.getRoot().getStore(GLOBAL).get(uniqueKey);
        if (value == null) {
            context.getRoot().getStore(GLOBAL).put(uniqueKey, this);
            setup();
        }
    }

    abstract void setup();

    public abstract void close() throws Throwable;

}