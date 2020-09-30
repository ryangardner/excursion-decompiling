/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail;

import javax.mail.Multipart;
import javax.mail.Part;

public abstract class BodyPart
implements Part {
    protected Multipart parent;

    public Multipart getParent() {
        return this.parent;
    }

    void setParent(Multipart multipart) {
        this.parent = multipart;
    }
}

