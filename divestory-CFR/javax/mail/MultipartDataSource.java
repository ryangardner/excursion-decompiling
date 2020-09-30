/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail;

import javax.activation.DataSource;
import javax.mail.BodyPart;
import javax.mail.MessagingException;

public interface MultipartDataSource
extends DataSource {
    public BodyPart getBodyPart(int var1) throws MessagingException;

    public int getCount();
}

