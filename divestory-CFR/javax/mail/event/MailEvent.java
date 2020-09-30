/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail.event;

import java.util.EventObject;

public abstract class MailEvent
extends EventObject {
    private static final long serialVersionUID = 1846275636325456631L;

    public MailEvent(Object object) {
        super(object);
    }

    public abstract void dispatch(Object var1);
}

