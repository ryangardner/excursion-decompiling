/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail.event;

import javax.mail.Store;
import javax.mail.event.MailEvent;
import javax.mail.event.StoreListener;

public class StoreEvent
extends MailEvent {
    public static final int ALERT = 1;
    public static final int NOTICE = 2;
    private static final long serialVersionUID = 1938704919992515330L;
    protected String message;
    protected int type;

    public StoreEvent(Store store, int n, String string2) {
        super(store);
        this.type = n;
        this.message = string2;
    }

    @Override
    public void dispatch(Object object) {
        ((StoreListener)object).notification(this);
    }

    public String getMessage() {
        return this.message;
    }

    public int getMessageType() {
        return this.type;
    }
}

