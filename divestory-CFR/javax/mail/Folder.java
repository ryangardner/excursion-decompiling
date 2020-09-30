/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail;

import java.util.Vector;
import javax.mail.EventQueue;
import javax.mail.FetchProfile;
import javax.mail.Flags;
import javax.mail.FolderNotFoundException;
import javax.mail.Message;
import javax.mail.MessageRemovedException;
import javax.mail.MessagingException;
import javax.mail.MethodNotSupportedException;
import javax.mail.Store;
import javax.mail.URLName;
import javax.mail.event.ConnectionEvent;
import javax.mail.event.ConnectionListener;
import javax.mail.event.FolderEvent;
import javax.mail.event.FolderListener;
import javax.mail.event.MailEvent;
import javax.mail.event.MessageChangedEvent;
import javax.mail.event.MessageChangedListener;
import javax.mail.event.MessageCountEvent;
import javax.mail.event.MessageCountListener;
import javax.mail.search.SearchTerm;

public abstract class Folder {
    public static final int HOLDS_FOLDERS = 2;
    public static final int HOLDS_MESSAGES = 1;
    public static final int READ_ONLY = 1;
    public static final int READ_WRITE = 2;
    private volatile Vector connectionListeners = null;
    private volatile Vector folderListeners = null;
    private volatile Vector messageChangedListeners = null;
    private volatile Vector messageCountListeners = null;
    protected int mode = -1;
    private EventQueue q;
    private Object qLock = new Object();
    protected Store store;

    protected Folder(Store store) {
        this.store = store;
    }

    /*
     * Enabled unnecessary exception pruning
     */
    private void queueEvent(MailEvent mailEvent, Vector vector) {
        Object object = this.qLock;
        synchronized (object) {
            if (this.q == null) {
                EventQueue eventQueue;
                this.q = eventQueue = new EventQueue();
            }
        }
        vector = (Vector)vector.clone();
        this.q.enqueue(mailEvent, vector);
    }

    private void terminateQueue() {
        Object object = this.qLock;
        synchronized (object) {
            if (this.q == null) return;
            Vector vector = new Vector();
            vector.setSize(1);
            EventQueue eventQueue = this.q;
            TerminatorEvent terminatorEvent = new TerminatorEvent();
            eventQueue.enqueue(terminatorEvent, vector);
            this.q = null;
            return;
        }
    }

    public void addConnectionListener(ConnectionListener connectionListener) {
        synchronized (this) {
            if (this.connectionListeners == null) {
                Vector vector;
                this.connectionListeners = vector = new Vector();
            }
            this.connectionListeners.addElement(connectionListener);
            return;
        }
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

    public void addMessageChangedListener(MessageChangedListener messageChangedListener) {
        synchronized (this) {
            if (this.messageChangedListeners == null) {
                Vector vector;
                this.messageChangedListeners = vector = new Vector();
            }
            this.messageChangedListeners.addElement(messageChangedListener);
            return;
        }
    }

    public void addMessageCountListener(MessageCountListener messageCountListener) {
        synchronized (this) {
            if (this.messageCountListeners == null) {
                Vector vector;
                this.messageCountListeners = vector = new Vector();
            }
            this.messageCountListeners.addElement(messageCountListener);
            return;
        }
    }

    public abstract void appendMessages(Message[] var1) throws MessagingException;

    public abstract void close(boolean var1) throws MessagingException;

    public void copyMessages(Message[] object, Folder folder) throws MessagingException {
        if (folder.exists()) {
            folder.appendMessages((Message[])object);
            return;
        }
        object = new StringBuilder(String.valueOf(folder.getFullName()));
        ((StringBuilder)object).append(" does not exist");
        throw new FolderNotFoundException(((StringBuilder)object).toString(), folder);
    }

    public abstract boolean create(int var1) throws MessagingException;

    public abstract boolean delete(boolean var1) throws MessagingException;

    public abstract boolean exists() throws MessagingException;

    public abstract Message[] expunge() throws MessagingException;

    public void fetch(Message[] arrmessage, FetchProfile fetchProfile) throws MessagingException {
    }

    protected void finalize() throws Throwable {
        super.finalize();
        this.terminateQueue();
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     */
    public int getDeletedMessageCount() throws MessagingException {
        // MONITORENTER : this
        var1_1 = this.isOpen();
        if (!var1_1) {
            // MONITOREXIT : this
            return -1;
        }
        var2_2 = 0;
        var3_3 = this.getMessageCount();
        var4_4 = 1;
        block5 : do {
            if (var4_4 > var3_3) {
                // MONITOREXIT : this
                return var2_2;
            }
            try {
                var1_1 = this.getMessage(var4_4).isSet(Flags.Flag.DELETED);
                var5_5 = var2_2;
                if (var1_1) {
                    var5_5 = var2_2 + 1;
                }
lbl18: // 4 sources:
                do {
                    ++var4_4;
                    var2_2 = var5_5;
                    continue block5;
                    break;
                } while (true);
            }
            catch (MessageRemovedException var6_6) {
                var5_5 = var2_2;
                ** continue;
            }
        } while (true);
    }

    public abstract Folder getFolder(String var1) throws MessagingException;

    public abstract String getFullName();

    public abstract Message getMessage(int var1) throws MessagingException;

    public abstract int getMessageCount() throws MessagingException;

    public Message[] getMessages() throws MessagingException {
        synchronized (this) {
            int n;
            Message[] arrmessage;
            int n2;
            block8 : {
                if (this.isOpen()) {
                    n = this.getMessageCount();
                    arrmessage = new Message[n];
                    n2 = 1;
                    break block8;
                }
                IllegalStateException illegalStateException = new IllegalStateException("Folder not open");
                throw illegalStateException;
            }
            do {
                if (n2 > n) {
                    return arrmessage;
                }
                arrmessage[n2 - 1] = this.getMessage(n2);
                ++n2;
                continue;
                break;
            } while (true);
        }
    }

    public Message[] getMessages(int n, int n2) throws MessagingException {
        synchronized (this) {
            Message[] arrmessage = new Message[n2 - n + 1];
            int n3 = n;
            do {
                if (n3 > n2) {
                    return arrmessage;
                }
                arrmessage[n3 - n] = this.getMessage(n3);
                ++n3;
                continue;
                break;
            } while (true);
        }
    }

    public Message[] getMessages(int[] arrn) throws MessagingException {
        synchronized (this) {
            int n = arrn.length;
            Message[] arrmessage = new Message[n];
            int n2 = 0;
            do {
                if (n2 >= n) {
                    return arrmessage;
                }
                arrmessage[n2] = this.getMessage(arrn[n2]);
                ++n2;
                continue;
                break;
            } while (true);
        }
    }

    public int getMode() {
        if (!this.isOpen()) throw new IllegalStateException("Folder not open");
        return this.mode;
    }

    public abstract String getName();

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     */
    public int getNewMessageCount() throws MessagingException {
        // MONITORENTER : this
        var1_1 = this.isOpen();
        if (!var1_1) {
            // MONITOREXIT : this
            return -1;
        }
        var2_2 = 0;
        var3_3 = this.getMessageCount();
        var4_4 = 1;
        block5 : do {
            if (var4_4 > var3_3) {
                // MONITOREXIT : this
                return var2_2;
            }
            try {
                var1_1 = this.getMessage(var4_4).isSet(Flags.Flag.RECENT);
                var5_5 = var2_2;
                if (var1_1) {
                    var5_5 = var2_2 + 1;
                }
lbl18: // 4 sources:
                do {
                    ++var4_4;
                    var2_2 = var5_5;
                    continue block5;
                    break;
                } while (true);
            }
            catch (MessageRemovedException var6_6) {
                var5_5 = var2_2;
                ** continue;
            }
        } while (true);
    }

    public abstract Folder getParent() throws MessagingException;

    public abstract Flags getPermanentFlags();

    public abstract char getSeparator() throws MessagingException;

    public Store getStore() {
        return this.store;
    }

    public abstract int getType() throws MessagingException;

    public URLName getURLName() throws MessagingException {
        URLName uRLName = this.getStore().getURLName();
        String string2 = this.getFullName();
        StringBuffer stringBuffer = new StringBuffer();
        this.getSeparator();
        if (string2 == null) return new URLName(uRLName.getProtocol(), uRLName.getHost(), uRLName.getPort(), stringBuffer.toString(), uRLName.getUsername(), null);
        stringBuffer.append(string2);
        return new URLName(uRLName.getProtocol(), uRLName.getHost(), uRLName.getPort(), stringBuffer.toString(), uRLName.getUsername(), null);
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     */
    public int getUnreadMessageCount() throws MessagingException {
        // MONITORENTER : this
        var1_1 = this.isOpen();
        if (!var1_1) {
            // MONITOREXIT : this
            return -1;
        }
        var2_2 = 0;
        var3_3 = this.getMessageCount();
        var4_4 = 1;
        block5 : do {
            if (var4_4 > var3_3) {
                // MONITOREXIT : this
                return var2_2;
            }
            try {
                var1_1 = this.getMessage(var4_4).isSet(Flags.Flag.SEEN);
                var5_5 = var2_2;
                if (!var1_1) {
                    var5_5 = var2_2 + 1;
                }
lbl18: // 4 sources:
                do {
                    ++var4_4;
                    var2_2 = var5_5;
                    continue block5;
                    break;
                } while (true);
            }
            catch (MessageRemovedException var6_6) {
                var5_5 = var2_2;
                ** continue;
            }
        } while (true);
    }

    public abstract boolean hasNewMessages() throws MessagingException;

    public abstract boolean isOpen();

    public boolean isSubscribed() {
        return true;
    }

    public Folder[] list() throws MessagingException {
        return this.list("%");
    }

    public abstract Folder[] list(String var1) throws MessagingException;

    public Folder[] listSubscribed() throws MessagingException {
        return this.listSubscribed("%");
    }

    public Folder[] listSubscribed(String string2) throws MessagingException {
        return this.list(string2);
    }

    protected void notifyConnectionListeners(int n) {
        if (this.connectionListeners != null) {
            this.queueEvent(new ConnectionEvent(this, n), this.connectionListeners);
        }
        if (n != 3) return;
        this.terminateQueue();
    }

    protected void notifyFolderListeners(int n) {
        if (this.folderListeners != null) {
            this.queueEvent(new FolderEvent(this, this, n), this.folderListeners);
        }
        this.store.notifyFolderListeners(n, this);
    }

    protected void notifyFolderRenamedListeners(Folder folder) {
        if (this.folderListeners != null) {
            this.queueEvent(new FolderEvent(this, this, folder, 3), this.folderListeners);
        }
        this.store.notifyFolderRenamedListeners(this, folder);
    }

    protected void notifyMessageAddedListeners(Message[] arrmessage) {
        if (this.messageCountListeners == null) {
            return;
        }
        this.queueEvent(new MessageCountEvent(this, 1, false, arrmessage), this.messageCountListeners);
    }

    protected void notifyMessageChangedListeners(int n, Message message) {
        if (this.messageChangedListeners == null) {
            return;
        }
        this.queueEvent(new MessageChangedEvent(this, n, message), this.messageChangedListeners);
    }

    protected void notifyMessageRemovedListeners(boolean bl, Message[] arrmessage) {
        if (this.messageCountListeners == null) {
            return;
        }
        this.queueEvent(new MessageCountEvent(this, 2, bl, arrmessage), this.messageCountListeners);
    }

    public abstract void open(int var1) throws MessagingException;

    public void removeConnectionListener(ConnectionListener connectionListener) {
        synchronized (this) {
            if (this.connectionListeners == null) return;
            this.connectionListeners.removeElement(connectionListener);
            return;
        }
    }

    public void removeFolderListener(FolderListener folderListener) {
        synchronized (this) {
            if (this.folderListeners == null) return;
            this.folderListeners.removeElement(folderListener);
            return;
        }
    }

    public void removeMessageChangedListener(MessageChangedListener messageChangedListener) {
        synchronized (this) {
            if (this.messageChangedListeners == null) return;
            this.messageChangedListeners.removeElement(messageChangedListener);
            return;
        }
    }

    public void removeMessageCountListener(MessageCountListener messageCountListener) {
        synchronized (this) {
            if (this.messageCountListeners == null) return;
            this.messageCountListeners.removeElement(messageCountListener);
            return;
        }
    }

    public abstract boolean renameTo(Folder var1) throws MessagingException;

    public Message[] search(SearchTerm searchTerm) throws MessagingException {
        return this.search(searchTerm, this.getMessages());
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    public Message[] search(SearchTerm var1_1, Message[] var2_2) throws MessagingException {
        var3_3 = new Vector<Message>();
        var4_4 = 0;
        block2 : do {
            if (var4_4 >= var2_2.length) {
                var1_1 = new Message[var3_3.size()];
                var3_3.copyInto(var1_1);
                return var1_1;
            }
            try {
                if (var2_2[var4_4].match((SearchTerm)var1_1)) {
                    var3_3.addElement(var2_2[var4_4]);
                }
lbl11: // 4 sources:
                do {
                    ++var4_4;
                    continue block2;
                    break;
                } while (true);
            }
            catch (MessageRemovedException var5_5) {
                ** continue;
            }
        } while (true);
    }

    public void setFlags(int n, int n2, Flags flags, boolean bl) throws MessagingException {
        synchronized (this) {
            while (n <= n2) {
                try {
                    this.getMessage(n).setFlags(flags, bl);
                }
                catch (Throwable throwable) {
                    throw throwable;
                }
                catch (MessageRemovedException messageRemovedException) {}
                ++n;
            }
            return;
        }
    }

    public void setFlags(int[] arrn, Flags flags, boolean bl) throws MessagingException {
        synchronized (this) {
            int n = 0;
            do {
                block6 : {
                    int n2 = arrn.length;
                    if (n < n2) break block6;
                    return;
                }
                try {
                    this.getMessage(arrn[n]).setFlags(flags, bl);
                }
                catch (MessageRemovedException messageRemovedException) {}
                ++n;
            } while (true);
        }
    }

    public void setFlags(Message[] arrmessage, Flags flags, boolean bl) throws MessagingException {
        synchronized (this) {
            int n = 0;
            do {
                block6 : {
                    int n2 = arrmessage.length;
                    if (n < n2) break block6;
                    return;
                }
                try {
                    arrmessage[n].setFlags(flags, bl);
                }
                catch (MessageRemovedException messageRemovedException) {}
                ++n;
            } while (true);
        }
    }

    public void setSubscribed(boolean bl) throws MessagingException {
        throw new MethodNotSupportedException();
    }

    public String toString() {
        String string2 = this.getFullName();
        if (string2 == null) return super.toString();
        return string2;
    }

    static class TerminatorEvent
    extends MailEvent {
        private static final long serialVersionUID = 3765761925441296565L;

        TerminatorEvent() {
            super(new Object());
        }

        @Override
        public void dispatch(Object object) {
            Thread.currentThread().interrupt();
        }
    }

}

