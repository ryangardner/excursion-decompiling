/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail;

import javax.mail.Folder;
import javax.mail.MessagingException;

public class ReadOnlyFolderException
extends MessagingException {
    private static final long serialVersionUID = 5711829372799039325L;
    private transient Folder folder;

    public ReadOnlyFolderException(Folder folder) {
        this(folder, null);
    }

    public ReadOnlyFolderException(Folder folder, String string2) {
        super(string2);
        this.folder = folder;
    }

    public Folder getFolder() {
        return this.folder;
    }
}

