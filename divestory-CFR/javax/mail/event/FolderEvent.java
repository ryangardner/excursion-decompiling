/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail.event;

import javax.mail.Folder;
import javax.mail.event.FolderListener;
import javax.mail.event.MailEvent;

public class FolderEvent
extends MailEvent {
    public static final int CREATED = 1;
    public static final int DELETED = 2;
    public static final int RENAMED = 3;
    private static final long serialVersionUID = 5278131310563694307L;
    protected transient Folder folder;
    protected transient Folder newFolder;
    protected int type;

    public FolderEvent(Object object, Folder folder, int n) {
        this(object, folder, folder, n);
    }

    public FolderEvent(Object object, Folder folder, Folder folder2, int n) {
        super(object);
        this.folder = folder;
        this.newFolder = folder2;
        this.type = n;
    }

    @Override
    public void dispatch(Object object) {
        int n = this.type;
        if (n == 1) {
            ((FolderListener)object).folderCreated(this);
            return;
        }
        if (n == 2) {
            ((FolderListener)object).folderDeleted(this);
            return;
        }
        if (n != 3) return;
        ((FolderListener)object).folderRenamed(this);
    }

    public Folder getFolder() {
        return this.folder;
    }

    public Folder getNewFolder() {
        return this.newFolder;
    }

    public int getType() {
        return this.type;
    }
}

