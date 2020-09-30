/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail;

import javax.mail.Folder;
import javax.mail.MessagingException;

public class FolderNotFoundException
extends MessagingException {
    private static final long serialVersionUID = 472612108891249403L;
    private transient Folder folder;

    public FolderNotFoundException() {
    }

    public FolderNotFoundException(String string2, Folder folder) {
        super(string2);
        this.folder = folder;
    }

    public FolderNotFoundException(Folder folder) {
        this.folder = folder;
    }

    public FolderNotFoundException(Folder folder, String string2) {
        super(string2);
        this.folder = folder;
    }

    public Folder getFolder() {
        return this.folder;
    }
}

