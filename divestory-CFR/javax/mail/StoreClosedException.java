/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail;

import javax.mail.MessagingException;
import javax.mail.Store;

public class StoreClosedException
extends MessagingException {
    private static final long serialVersionUID = -3145392336120082655L;
    private transient Store store;

    public StoreClosedException(Store store) {
        this(store, null);
    }

    public StoreClosedException(Store store, String string2) {
        super(string2);
        this.store = store;
    }

    public Store getStore() {
        return this.store;
    }
}

