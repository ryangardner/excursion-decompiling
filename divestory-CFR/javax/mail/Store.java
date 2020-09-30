/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail;

import java.util.Vector;
import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Service;
import javax.mail.Session;
import javax.mail.URLName;
import javax.mail.event.FolderEvent;
import javax.mail.event.FolderListener;
import javax.mail.event.MailEvent;
import javax.mail.event.StoreEvent;
import javax.mail.event.StoreListener;

public abstract class Store
extends Service {
    private volatile Vector folderListeners = null;
    private volatile Vector storeListeners = null;

    protected Store(Session session, URLName uRLName) {
        super(session, uRLName);
    }

    public void addFolderListener(FolderListener folderListener) {
        synchronized (this) {
            if (this.folderListeners == null) {
                Vector vector;
                this.folderListeners = vector = new Vector();
            }
            this.folderListeners.addElement(folderListener);
            return;
        }
    }

    public void addStoreListener(StoreListener storeListener) {
        synchronized (this) {
            if (this.storeListeners == null) {
                Vector vector;
                this.storeListeners = vector = new Vector();
            }
            this.storeListeners.addElement(storeListener);
            return;
        }
    }

    public abstract Folder getDefaultFolder() throws MessagingException;

    public abstract Folder getFolder(String var1) throws MessagingException;

    public abstract Folder getFolder(URLName var1) throws MessagingException;

    public Folder[] getPersonalNamespaces() throws MessagingException {
        return new Folder[]{this.getDefaultFolder()};
    }

    public Folder[] getSharedNamespaces() throws MessagingException {
        return new Folder[0];
    }

    public Folder[] getUserNamespaces(String string2) throws MessagingException {
        return new Folder[0];
    }

    protected void notifyFolderListeners(int n, Folder folder) {
        if (this.folderListeners == null) {
            return;
        }
        this.queueEvent(new FolderEvent(this, folder, n), this.folderListeners);
    }

    protected void notifyFolderRenamedListeners(Folder folder, Folder folder2) {
        if (this.folderListeners == null) {
            return;
        }
        this.queueEvent(new FolderEvent(this, folder, folder2, 3), this.folderListeners);
    }

    protected void notifyStoreListeners(int n, String string2) {
        if (this.storeListeners == null) {
            return;
        }
        this.queueEvent(new StoreEvent(this, n, string2), this.storeListeners);
    }

    public void removeFolderListener(FolderListener folderListener) {
        synchronized (this) {
            if (this.folderListeners == null) return;
            this.folderListeners.removeElement(folderListener);
            return;
        }
    }

    public void removeStoreListener(StoreListener storeListener) {
        synchronized (this) {
            if (this.storeListeners == null) return;
            this.storeListeners.removeElement(storeListener);
            return;
        }
    }
}

