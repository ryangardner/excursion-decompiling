/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail;

import javax.mail.Folder;
import javax.mail.MessagingException;

public class FolderClosedException
extends MessagingException {
    private static final long serialVersionUID = 1687879213433302315L;
    private transient Folder folder;

    public FolderClosedException(Folder folder) {
        this(folder, null);
    }

    public FolderClosedException(Folder folder, String string2) {
        super(string2);
        this.folder = folder;
    }

    public Folder getFolder() {
        return this.folder;
    }
}

