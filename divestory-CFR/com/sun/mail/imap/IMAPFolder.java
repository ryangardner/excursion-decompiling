/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.imap;

import com.sun.mail.iap.BadCommandException;
import com.sun.mail.iap.CommandFailedException;
import com.sun.mail.iap.ConnectionException;
import com.sun.mail.iap.Literal;
import com.sun.mail.iap.Protocol;
import com.sun.mail.iap.ProtocolException;
import com.sun.mail.iap.Response;
import com.sun.mail.iap.ResponseHandler;
import com.sun.mail.imap.ACL;
import com.sun.mail.imap.AppendUID;
import com.sun.mail.imap.DefaultFolder;
import com.sun.mail.imap.IMAPMessage;
import com.sun.mail.imap.IMAPStore;
import com.sun.mail.imap.MessageLiteral;
import com.sun.mail.imap.Rights;
import com.sun.mail.imap.Utility;
import com.sun.mail.imap.protocol.FetchResponse;
import com.sun.mail.imap.protocol.IMAPProtocol;
import com.sun.mail.imap.protocol.IMAPResponse;
import com.sun.mail.imap.protocol.Item;
import com.sun.mail.imap.protocol.ListInfo;
import com.sun.mail.imap.protocol.MailboxInfo;
import com.sun.mail.imap.protocol.MessageSet;
import com.sun.mail.imap.protocol.Status;
import com.sun.mail.imap.protocol.UID;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.Date;
import java.util.Hashtable;
import java.util.NoSuchElementException;
import java.util.Vector;
import javax.mail.FetchProfile;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.FolderClosedException;
import javax.mail.FolderNotFoundException;
import javax.mail.Message;
import javax.mail.MessageRemovedException;
import javax.mail.MessagingException;
import javax.mail.Quota;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.StoreClosedException;
import javax.mail.UIDFolder;
import javax.mail.internet.MimeMessage;
import javax.mail.search.FlagTerm;
import javax.mail.search.SearchException;
import javax.mail.search.SearchTerm;

public class IMAPFolder
extends Folder
implements UIDFolder,
ResponseHandler {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int ABORTING = 2;
    private static final int IDLE = 1;
    private static final int RUNNING = 0;
    protected static final char UNKNOWN_SEPARATOR = '\uffff';
    protected String[] attributes;
    protected Flags availableFlags;
    private Status cachedStatus = null;
    private long cachedStatusTime = 0L;
    private boolean connectionPoolDebug;
    private boolean debug = false;
    private boolean doExpungeNotification = true;
    protected boolean exists = false;
    protected String fullName;
    private int idleState = 0;
    protected boolean isNamespace = false;
    protected Vector messageCache;
    protected Object messageCacheLock;
    protected String name;
    private boolean opened = false;
    private PrintStream out;
    protected Flags permanentFlags;
    protected IMAPProtocol protocol;
    private int realTotal = -1;
    private boolean reallyClosed = true;
    private int recent = -1;
    protected char separator;
    private int total = -1;
    protected int type;
    protected Hashtable uidTable;
    private long uidnext = -1L;
    private long uidvalidity = -1L;

    protected IMAPFolder(ListInfo listInfo, IMAPStore iMAPStore) {
        this(listInfo.name, listInfo.separator, iMAPStore);
        if (listInfo.hasInferiors) {
            this.type |= 2;
        }
        if (listInfo.canOpen) {
            this.type |= 1;
        }
        this.exists = true;
        this.attributes = listInfo.attrs;
    }

    protected IMAPFolder(String object, char c, IMAPStore iMAPStore) {
        super(iMAPStore);
        if (object == null) throw new NullPointerException("Folder name is null");
        this.fullName = object;
        this.separator = c;
        this.messageCacheLock = new Object();
        this.debug = iMAPStore.getSession().getDebug();
        this.connectionPoolDebug = iMAPStore.getConnectionPoolDebug();
        this.out = object = iMAPStore.getSession().getDebugOut();
        if (object == null) {
            this.out = System.out;
        }
        this.isNamespace = false;
        if (c == '\uffff') return;
        if (c == '\u0000') return;
        if ((c = (char)this.fullName.indexOf(c)) <= '\u0000') return;
        if (c != this.fullName.length() - 1) return;
        this.fullName = this.fullName.substring(0, c);
        this.isNamespace = true;
    }

    protected IMAPFolder(String string2, char c, IMAPStore iMAPStore, boolean bl) {
        this(string2, c, iMAPStore);
        this.isNamespace = bl;
    }

    static /* synthetic */ Status access$0(IMAPFolder iMAPFolder) throws ProtocolException {
        return iMAPFolder.getStatus();
    }

    static /* synthetic */ void access$2(IMAPFolder iMAPFolder, int n) {
        iMAPFolder.idleState = n;
    }

    private void checkClosed() {
        if (this.opened) throw new IllegalStateException("This operation is not allowed on an open folder");
    }

    private void checkExists() throws MessagingException {
        if (this.exists) return;
        if (this.exists()) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(this.fullName));
        stringBuilder.append(" not found");
        throw new FolderNotFoundException(this, stringBuilder.toString());
    }

    private void checkFlags(Flags serializable) throws MessagingException {
        if (this.mode == 2) {
            return;
        }
        serializable = new StringBuilder("Cannot change flags on READ_ONLY folder: ");
        ((StringBuilder)serializable).append(this.fullName);
        throw new IllegalStateException(((StringBuilder)serializable).toString());
    }

    private void checkOpened() throws FolderClosedException {
        if (this.opened) return;
        if (!this.reallyClosed) throw new FolderClosedException(this, "Lost folder connection to server");
        throw new IllegalStateException("This operation is not allowed on a closed folder");
    }

    /*
     * Exception decompiling
     */
    private void checkRange(int var1_1) throws MessagingException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [4[CATCHBLOCK]], but top level block is 1[TRYBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        // the.bytecode.club.bytecodeviewer.decompilers.CFRDecompiler.decompileToZip(CFRDecompiler.java:311)
        // the.bytecode.club.bytecodeviewer.gui.MainViewerGUI$14$1$3.run(MainViewerGUI.java:1211)
        throw new IllegalStateException("Decompilation failed");
    }

    private void cleanup(boolean bl) {
        this.releaseProtocol(bl);
        this.protocol = null;
        this.messageCache = null;
        this.uidTable = null;
        this.exists = false;
        this.attributes = null;
        this.opened = false;
        this.idleState = 0;
        this.notifyConnectionListeners(3);
    }

    /*
     * Exception decompiling
     */
    private void close(boolean var1_1, boolean var2_2) throws MessagingException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [1[TRYBLOCK]], but top level block is 11[CATCHBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        // the.bytecode.club.bytecodeviewer.decompilers.CFRDecompiler.decompileToZip(CFRDecompiler.java:311)
        // the.bytecode.club.bytecodeviewer.gui.MainViewerGUI$14$1$3.run(MainViewerGUI.java:1211)
        throw new IllegalStateException("Decompilation failed");
    }

    private Folder[] doList(String arrlistInfo, final boolean bl) throws MessagingException {
        synchronized (this) {
            int n;
            this.checkExists();
            boolean bl2 = this.isDirectory();
            int n2 = 0;
            if (!bl2) {
                return new Folder[0];
            }
            final char c = this.getSeparator();
            ProtocolCommand protocolCommand = new ProtocolCommand((String)arrlistInfo){
                private final /* synthetic */ String val$pattern;
                {
                    this.val$pattern = string2;
                }

                @Override
                public Object doCommand(IMAPProtocol iMAPProtocol) throws ProtocolException {
                    if (bl) {
                        StringBuilder stringBuilder = new StringBuilder(String.valueOf(IMAPFolder.this.fullName));
                        stringBuilder.append(c);
                        stringBuilder.append(this.val$pattern);
                        return iMAPProtocol.lsub("", stringBuilder.toString());
                    }
                    StringBuilder stringBuilder = new StringBuilder(String.valueOf(IMAPFolder.this.fullName));
                    stringBuilder.append(c);
                    stringBuilder.append(this.val$pattern);
                    return iMAPProtocol.list("", stringBuilder.toString());
                }
            };
            arrlistInfo = (ListInfo[])this.doCommandIgnoreFailure(protocolCommand);
            if (arrlistInfo == null) {
                return new Folder[0];
            }
            int n3 = n2;
            if (arrlistInfo.length > 0) {
                protocolCommand = arrlistInfo[0].name;
                StringBuilder stringBuilder = new StringBuilder(String.valueOf(this.fullName));
                stringBuilder.append(c);
                n3 = n2;
                if (((String)((Object)protocolCommand)).equals(stringBuilder.toString())) {
                    n3 = 1;
                }
            }
            protocolCommand = new IMAPFolder[arrlistInfo.length - n3];
            n2 = n3;
            while (n2 < (n = arrlistInfo.length)) {
                protocolCommand[n2 - n3] = new IMAPFolder(arrlistInfo[n2], (IMAPStore)this.store);
                ++n2;
            }
            return protocolCommand;
        }
    }

    private int findName(ListInfo[] arrlistInfo, String string2) {
        int n;
        int n2 = 0;
        for (n = 0; n < arrlistInfo.length && !arrlistInfo[n].name.equals(string2); ++n) {
        }
        if (n < arrlistInfo.length) return n;
        return n2;
    }

    private IMAPProtocol getProtocol() throws ProtocolException {
        this.waitIfIdle();
        return this.protocol;
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled unnecessary exception pruning
     */
    private Status getStatus() throws ProtocolException {
        IMAPProtocol iMAPProtocol;
        void var4_7;
        block6 : {
            int n = ((IMAPStore)this.store).getStatusCacheTimeout();
            if (n > 0 && this.cachedStatus != null && System.currentTimeMillis() - this.cachedStatusTime < (long)n) {
                return this.cachedStatus;
            }
            Object var2_2 = null;
            iMAPProtocol = this.getStoreProtocol();
            try {
                Status status = iMAPProtocol.status(this.fullName, null);
                if (n > 0) {
                    this.cachedStatus = status;
                    this.cachedStatusTime = System.currentTimeMillis();
                }
                this.releaseStoreProtocol(iMAPProtocol);
                return status;
            }
            catch (Throwable throwable) {}
            break block6;
            catch (Throwable throwable) {
                iMAPProtocol = var2_2;
            }
        }
        this.releaseStoreProtocol(iMAPProtocol);
        throw var4_7;
    }

    private boolean isDirectory() {
        if ((this.type & 2) == 0) return false;
        return true;
    }

    private void keepConnectionAlive(boolean bl) throws ProtocolException {
        if (System.currentTimeMillis() - this.protocol.getTimestamp() > 1000L) {
            this.waitIfIdle();
            this.protocol.noop();
        }
        if (!bl) return;
        if (!((IMAPStore)this.store).hasSeparateStoreConnection()) return;
        IMAPProtocol iMAPProtocol = null;
        try {
            IMAPProtocol iMAPProtocol2;
            iMAPProtocol = iMAPProtocol2 = ((IMAPStore)this.store).getStoreProtocol();
            if (System.currentTimeMillis() - iMAPProtocol2.getTimestamp() > 1000L) {
                iMAPProtocol = iMAPProtocol2;
                iMAPProtocol2.noop();
            }
            ((IMAPStore)this.store).releaseStoreProtocol(iMAPProtocol2);
            return;
        }
        catch (Throwable throwable) {
            ((IMAPStore)this.store).releaseStoreProtocol(iMAPProtocol);
            throw throwable;
        }
    }

    private void releaseProtocol(boolean bl) {
        IMAPProtocol iMAPProtocol = this.protocol;
        if (iMAPProtocol == null) return;
        iMAPProtocol.removeResponseHandler(this);
        if (bl) {
            ((IMAPStore)this.store).releaseProtocol(this, this.protocol);
            return;
        }
        ((IMAPStore)this.store).releaseProtocol(this, null);
    }

    private void setACL(final ACL aCL, final char c) throws MessagingException {
        this.doOptionalCommand("ACL not supported", new ProtocolCommand(){

            @Override
            public Object doCommand(IMAPProtocol iMAPProtocol) throws ProtocolException {
                iMAPProtocol.setACL(IMAPFolder.this.fullName, c, aCL);
                return null;
            }
        });
    }

    private void throwClosedException(ConnectionException connectionException) throws FolderClosedException, StoreClosedException {
        synchronized (this) {
            if (this.protocol != null && connectionException.getProtocol() == this.protocol || this.protocol == null && !this.reallyClosed) {
                FolderClosedException folderClosedException = new FolderClosedException(this, connectionException.getMessage());
                throw folderClosedException;
            }
            StoreClosedException storeClosedException = new StoreClosedException(this.store, connectionException.getMessage());
            throw storeClosedException;
        }
    }

    public void addACL(ACL aCL) throws MessagingException {
        this.setACL(aCL, '\u0000');
    }

    /*
     * Enabled unnecessary exception pruning
     */
    public Message[] addMessages(Message[] arrobject) throws MessagingException {
        synchronized (this) {
            this.checkOpened();
            Message[] arrmessage = new MimeMessage[arrobject.length];
            arrobject = this.appendUIDMessages((Message[])arrobject);
            int n = 0;
            int n2;
            while (n < (n2 = arrobject.length)) {
                long l;
                long l2;
                Object object = arrobject[n];
                if (object != null && (l2 = ((AppendUID)object).uidvalidity) == (l = this.uidvalidity)) {
                    try {
                        arrmessage[n] = this.getMessageByUID(((AppendUID)object).uid);
                    }
                    catch (MessagingException messagingException) {}
                }
                ++n;
            }
            return arrmessage;
        }
    }

    public void addRights(ACL aCL) throws MessagingException {
        this.setACL(aCL, '+');
    }

    /*
     * Enabled unnecessary exception pruning
     */
    @Override
    public void appendMessages(Message[] object) throws MessagingException {
        synchronized (this) {
            this.checkExists();
            int n = ((IMAPStore)this.store).getAppendBufferSize();
            int n2 = 0;
            int n3;
            while (n2 < (n3 = ((Message[])object).length)) {
                ProtocolCommand protocolCommand;
                Date date;
                MessageLiteral messageLiteral;
                Object object2 = object[n2];
                try {
                    n3 = object2.getSize() > n ? 0 : n;
                    messageLiteral = new MessageLiteral((Message)object2, n3);
                    protocolCommand = ((Message)object2).getReceivedDate();
                    date = protocolCommand;
                    if (protocolCommand == null) {
                        date = ((Message)object2).getSentDate();
                    }
                    object2 = ((Message)object2).getFlags();
                }
                catch (IOException iOException) {
                    object = new MessagingException("IOException while appending messages", iOException);
                    throw object;
                }
                catch (MessageRemovedException messageRemovedException) {}
                protocolCommand = new ProtocolCommand((Flags)object2, date, messageLiteral){
                    private final /* synthetic */ Date val$dd;
                    private final /* synthetic */ Flags val$f;
                    private final /* synthetic */ MessageLiteral val$mos;
                    {
                        this.val$f = flags;
                        this.val$dd = date;
                        this.val$mos = messageLiteral;
                    }

                    @Override
                    public Object doCommand(IMAPProtocol iMAPProtocol) throws ProtocolException {
                        iMAPProtocol.append(IMAPFolder.this.fullName, this.val$f, this.val$dd, this.val$mos);
                        return null;
                    }
                };
                this.doCommand(protocolCommand);
                ++n2;
            }
            return;
        }
    }

    /*
     * Enabled unnecessary exception pruning
     */
    public AppendUID[] appendUIDMessages(Message[] object) throws MessagingException {
        synchronized (this) {
            this.checkExists();
            int n = ((IMAPStore)this.store).getAppendBufferSize();
            AppendUID[] arrappendUID = new AppendUID[((Message[])object).length];
            int n2 = 0;
            int n3;
            while (n2 < (n3 = ((Message[])object).length)) {
                Date date;
                Cloneable cloneable;
                MessageLiteral messageLiteral;
                ProtocolCommand protocolCommand = object[n2];
                try {
                    n3 = protocolCommand.getSize() > n ? 0 : n;
                    messageLiteral = new MessageLiteral((Message)((Object)protocolCommand), n3);
                    date = cloneable = ((Message)((Object)protocolCommand)).getReceivedDate();
                    if (cloneable == null) {
                        date = ((Message)((Object)protocolCommand)).getSentDate();
                    }
                    cloneable = ((Message)((Object)protocolCommand)).getFlags();
                }
                catch (IOException iOException) {
                    object = new MessagingException("IOException while appending messages", iOException);
                    throw object;
                }
                catch (MessageRemovedException messageRemovedException) {}
                protocolCommand = new ProtocolCommand((Flags)cloneable, date, messageLiteral){
                    private final /* synthetic */ Date val$dd;
                    private final /* synthetic */ Flags val$f;
                    private final /* synthetic */ MessageLiteral val$mos;
                    {
                        this.val$f = flags;
                        this.val$dd = date;
                        this.val$mos = messageLiteral;
                    }

                    @Override
                    public Object doCommand(IMAPProtocol iMAPProtocol) throws ProtocolException {
                        return iMAPProtocol.appenduid(IMAPFolder.this.fullName, this.val$f, this.val$dd, this.val$mos);
                    }
                };
                arrappendUID[n2] = (AppendUID)this.doCommand(protocolCommand);
                ++n2;
            }
            return arrappendUID;
        }
    }

    @Override
    public void close(boolean bl) throws MessagingException {
        synchronized (this) {
            this.close(bl, false);
            return;
        }
    }

    /*
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     */
    @Override
    public void copyMessages(Message[] object, Folder object2) throws MessagingException {
        MessagingException messagingException;
        // MONITORENTER : this
        this.checkOpened();
        int n = ((Object)object).length;
        if (n == 0) {
            // MONITOREXIT : this
            return;
        }
        if (((Folder)((Object)messagingException)).getStore() != this.store) {
            super.copyMessages((Message[])object, (Folder)((Object)messagingException));
            // MONITOREXIT : this
            return;
        }
        Object object3 = this.messageCacheLock;
        // MONITORENTER : object3
        try {
            IMAPProtocol iMAPProtocol = this.getProtocol();
            MessageSet[] arrmessageSet = Utility.toMessageSet((Message[])object, null);
            if (arrmessageSet != null) {
                iMAPProtocol.copy(arrmessageSet, ((Folder)((Object)messagingException)).getFullName());
                // MONITOREXIT : object3
                return;
            }
            MessageRemovedException messageRemovedException = new MessageRemovedException("Messages have been removed");
            throw messageRemovedException;
        }
        catch (ProtocolException protocolException) {
            messagingException = new MessagingException(protocolException.getMessage(), protocolException);
            throw messagingException;
        }
        catch (ConnectionException connectionException) {
            messagingException = new FolderClosedException(this, connectionException.getMessage());
            throw messagingException;
        }
        catch (CommandFailedException commandFailedException) {
            if (commandFailedException.getMessage().indexOf("TRYCREATE") != -1) {
                StringBuilder stringBuilder = new StringBuilder(String.valueOf(((Folder)((Object)messagingException)).getFullName()));
                stringBuilder.append(" does not exist");
                FolderNotFoundException folderNotFoundException = new FolderNotFoundException((Folder)((Object)messagingException), stringBuilder.toString());
                throw folderNotFoundException;
            }
            messagingException = new MessagingException(commandFailedException.getMessage(), commandFailedException);
            throw messagingException;
        }
    }

    /*
     * Enabled unnecessary exception pruning
     */
    @Override
    public boolean create(final int n) throws MessagingException {
        synchronized (this) {
            char c;
            char c2;
            final char c3 = (n & 1) == 0 ? (c = this.getSeparator()) : (c2 = '\u0000');
            ProtocolCommand protocolCommand = new ProtocolCommand(){

                @Override
                public Object doCommand(IMAPProtocol iMAPProtocol) throws ProtocolException {
                    if ((n & 1) == 0) {
                        StringBuilder stringBuilder = new StringBuilder(String.valueOf(IMAPFolder.this.fullName));
                        stringBuilder.append(c3);
                        iMAPProtocol.create(stringBuilder.toString());
                        return Boolean.TRUE;
                    }
                    iMAPProtocol.create(IMAPFolder.this.fullName);
                    if ((n & 2) == 0) return Boolean.TRUE;
                    ListInfo[] arrlistInfo = iMAPProtocol.list("", IMAPFolder.this.fullName);
                    if (arrlistInfo == null) return Boolean.TRUE;
                    if (arrlistInfo[0].hasInferiors) {
                        return Boolean.TRUE;
                    }
                    iMAPProtocol.delete(IMAPFolder.this.fullName);
                    throw new ProtocolException("Unsupported type");
                }
            };
            protocolCommand = this.doCommandIgnoreFailure(protocolCommand);
            if (protocolCommand == null) {
                return false;
            }
            boolean bl = this.exists();
            if (!bl) return bl;
            this.notifyFolderListeners(1);
            return bl;
        }
    }

    @Override
    public boolean delete(boolean bl) throws MessagingException {
        synchronized (this) {
            Object object;
            this.checkClosed();
            if (bl) {
                object = this.list();
                for (int i = 0; i < ((Folder[])object).length; ++i) {
                    object[i].delete(bl);
                }
            }
            object = new ProtocolCommand(){

                @Override
                public Object doCommand(IMAPProtocol iMAPProtocol) throws ProtocolException {
                    iMAPProtocol.delete(IMAPFolder.this.fullName);
                    return Boolean.TRUE;
                }
            };
            if ((object = this.doCommandIgnoreFailure((ProtocolCommand)object)) == null) {
                return false;
            }
            this.exists = false;
            this.attributes = null;
            this.notifyFolderListeners(2);
            return true;
        }
    }

    public Object doCommand(ProtocolCommand object) throws MessagingException {
        try {
            return this.doProtocolCommand((ProtocolCommand)object);
        }
        catch (ProtocolException protocolException) {
            throw new MessagingException(protocolException.getMessage(), protocolException);
        }
        catch (ConnectionException connectionException) {
            this.throwClosedException(connectionException);
            return null;
        }
    }

    public Object doCommandIgnoreFailure(ProtocolCommand object) throws MessagingException {
        try {
            return this.doProtocolCommand((ProtocolCommand)object);
        }
        catch (ProtocolException protocolException) {
            throw new MessagingException(protocolException.getMessage(), protocolException);
        }
        catch (ConnectionException connectionException) {
            this.throwClosedException(connectionException);
            return null;
        }
        catch (CommandFailedException commandFailedException) {
            return null;
        }
    }

    public Object doOptionalCommand(String string2, ProtocolCommand object) throws MessagingException {
        try {
            return this.doProtocolCommand((ProtocolCommand)object);
        }
        catch (ProtocolException protocolException) {
            throw new MessagingException(protocolException.getMessage(), protocolException);
        }
        catch (ConnectionException connectionException) {
            this.throwClosedException(connectionException);
            return null;
        }
        catch (BadCommandException badCommandException) {
            throw new MessagingException(string2, badCommandException);
        }
    }

    /*
     * Enabled unnecessary exception pruning
     */
    protected Object doProtocolCommand(ProtocolCommand object) throws ProtocolException {
        synchronized (this) {
            if (this.opened && !((IMAPStore)this.store).hasSeparateStoreConnection()) {
                Object object2 = this.messageCacheLock;
                synchronized (object2) {
                    return object.doCommand(this.getProtocol());
                }
            }
        }
        IMAPProtocol iMAPProtocol = null;
        try {
            IMAPProtocol iMAPProtocol2;
            iMAPProtocol = iMAPProtocol2 = this.getStoreProtocol();
            object = object.doCommand(iMAPProtocol2);
            this.releaseStoreProtocol(iMAPProtocol2);
            return object;
        }
        catch (Throwable throwable) {
            this.releaseStoreProtocol(iMAPProtocol);
            throw throwable;
        }
    }

    @Override
    public boolean exists() throws MessagingException {
        synchronized (this) {
            Object object = null;
            if (this.isNamespace && this.separator != '\u0000') {
                object = new StringBuilder(String.valueOf(this.fullName));
                ((StringBuilder)object).append(this.separator);
                object = ((StringBuilder)object).toString();
            } else {
                object = this.fullName;
            }
            ProtocolCommand protocolCommand = new ProtocolCommand((String)object){
                private final /* synthetic */ String val$lname;
                {
                    this.val$lname = string2;
                }

                @Override
                public Object doCommand(IMAPProtocol iMAPProtocol) throws ProtocolException {
                    return iMAPProtocol.list("", this.val$lname);
                }
            };
            protocolCommand = (ListInfo[])this.doCommand(protocolCommand);
            if (protocolCommand != null) {
                int n = this.findName((ListInfo[])protocolCommand, (String)object);
                this.fullName = protocolCommand[n].name;
                this.separator = protocolCommand[n].separator;
                int n2 = this.fullName.length();
                if (this.separator != '\u0000' && n2 > 0 && ((String)(object = this.fullName)).charAt(--n2) == this.separator) {
                    this.fullName = this.fullName.substring(0, n2);
                }
                this.type = 0;
                if (protocolCommand[n].hasInferiors) {
                    this.type |= 2;
                }
                if (protocolCommand[n].canOpen) {
                    this.type |= 1;
                }
                this.exists = true;
                this.attributes = protocolCommand[n].attrs;
                return this.exists;
            } else {
                this.exists = this.opened;
                this.attributes = null;
            }
            return this.exists;
        }
    }

    @Override
    public Message[] expunge() throws MessagingException {
        synchronized (this) {
            return this.expunge(null);
        }
    }

    /*
     * Exception decompiling
     */
    public Message[] expunge(Message[] var1_1) throws MessagingException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [12[CATCHBLOCK]], but top level block is 6[TRYBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        // the.bytecode.club.bytecodeviewer.decompilers.CFRDecompiler.decompileToZip(CFRDecompiler.java:311)
        // the.bytecode.club.bytecodeviewer.gui.MainViewerGUI$14$1$3.run(MainViewerGUI.java:1211)
        throw new IllegalStateException("Decompilation failed");
    }

    @Override
    public void fetch(Message[] arrmessage, FetchProfile fetchProfile) throws MessagingException {
        synchronized (this) {
            this.checkOpened();
            IMAPMessage.fetch(this, arrmessage, fetchProfile);
            return;
        }
    }

    public void forceClose() throws MessagingException {
        synchronized (this) {
            this.close(false, true);
            return;
        }
    }

    public ACL[] getACL() throws MessagingException {
        return (ACL[])this.doOptionalCommand("ACL not supported", new ProtocolCommand(){

            @Override
            public Object doCommand(IMAPProtocol iMAPProtocol) throws ProtocolException {
                return iMAPProtocol.getACL(IMAPFolder.this.fullName);
            }
        });
    }

    public String[] getAttributes() throws MessagingException {
        if (this.attributes != null) return (String[])this.attributes.clone();
        this.exists();
        return (String[])this.attributes.clone();
    }

    /*
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     */
    @Override
    public int getDeletedMessageCount() throws MessagingException {
        // MONITORENTER : this
        if (!this.opened) {
            this.checkExists();
            // MONITOREXIT : this
            return -1;
        }
        Serializable serializable = new Flags();
        serializable.add(Flags.Flag.DELETED);
        try {
            Object object = this.messageCacheLock;
            // MONITORENTER : object
        }
        catch (ProtocolException protocolException) {
            serializable = new MessagingException(protocolException.getMessage(), protocolException);
            throw serializable;
        }
        catch (ConnectionException connectionException) {
            FolderClosedException folderClosedException = new FolderClosedException(this, connectionException.getMessage());
            throw folderClosedException;
        }
        IMAPProtocol iMAPProtocol = this.getProtocol();
        FlagTerm flagTerm = new FlagTerm((Flags)serializable, true);
        int n = iMAPProtocol.search(flagTerm).length;
        // MONITOREXIT : object
        // MONITOREXIT : this
        return n;
    }

    @Override
    public Folder getFolder(String string2) throws MessagingException {
        if (this.attributes != null) {
            if (!this.isDirectory()) throw new MessagingException("Cannot contain subfolders");
        }
        char c = this.getSeparator();
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(this.fullName));
        stringBuilder.append(c);
        stringBuilder.append(string2);
        return new IMAPFolder(stringBuilder.toString(), c, (IMAPStore)this.store);
    }

    @Override
    public String getFullName() {
        synchronized (this) {
            return this.fullName;
        }
    }

    @Override
    public Message getMessage(int n) throws MessagingException {
        synchronized (this) {
            this.checkOpened();
            this.checkRange(n);
            return (Message)this.messageCache.elementAt(n - 1);
        }
    }

    IMAPMessage getMessageBySeqNumber(int n) {
        int n2 = n - 1;
        while (n2 < this.total) {
            IMAPMessage iMAPMessage = (IMAPMessage)this.messageCache.elementAt(n2);
            if (iMAPMessage.getSequenceNumber() == n) {
                return iMAPMessage;
            }
            ++n2;
        }
        return null;
    }

    /*
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     */
    @Override
    public Message getMessageByUID(long l) throws MessagingException {
        Long l2;
        Object object;
        Object object2;
        block13 : {
            block12 : {
                // MONITORENTER : this
                this.checkOpened();
                object2 = null;
                try {
                    Object object3 = this.messageCacheLock;
                    // MONITORENTER : object3
                    l2 = new Long(l);
                    if (this.uidTable == null) break block12;
                }
                catch (ProtocolException protocolException) {
                    object2 = new MessagingException(protocolException.getMessage(), protocolException);
                    throw object2;
                }
                catch (ConnectionException connectionException) {
                    FolderClosedException folderClosedException = new FolderClosedException(this, connectionException.getMessage());
                    throw folderClosedException;
                }
                object2 = object = (IMAPMessage)this.uidTable.get(l2);
                if (object != null) {
                    // MONITOREXIT : object3
                    // MONITOREXIT : this
                    return object;
                }
                break block13;
            }
            this.uidTable = object = new Hashtable();
        }
        UID uID = this.getProtocol().fetchSequenceNumber(l);
        object = object2;
        if (uID != null) {
            object = object2;
            if (uID.seqnum <= this.total) {
                object = this.getMessageBySeqNumber(uID.seqnum);
                ((IMAPMessage)object).setUID(uID.uid);
                this.uidTable.put(l2, object);
            }
        }
        // MONITOREXIT : object3
        // MONITOREXIT : this
        return object;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    @Override
    public int getMessageCount() throws MessagingException {
        synchronized (this) {
            if (this.opened) ** GOTO lbl27
            this.checkExists();
            try {
                return this.getStatus().total;
            }
            catch (ProtocolException var2_4) {
                var3_11 = new MessagingException(var2_4.getMessage(), var2_4);
                throw var3_11;
            }
            catch (ConnectionException var3_12) {
                var2_5 = new StoreClosedException(this.store, var3_12.getMessage());
                throw var2_5;
            }
            catch (BadCommandException var3_13) {
                block19 : {
                    var2_6 = null;
                    var3_14 = null;
                    var3_14 = var4_16 = this.getStoreProtocol();
                    var2_6 = var4_16;
                    var5_20 = var4_16.examine(this.fullName);
                    var3_14 = var4_16;
                    var2_6 = var4_16;
                    var4_16.close();
                    var3_14 = var4_16;
                    var2_6 = var4_16;
                    var1_2 = var5_20.total;
                    this.releaseStoreProtocol(var4_16);
                    return var1_2;
lbl27: // 1 sources:
                    var3_15 = this.messageCacheLock;
                    synchronized (var3_15) {
                        try {
                            this.keepConnectionAlive(true);
                            return this.total;
                        }
                        catch (ProtocolException var4_18) {
                            var2_9 = new MessagingException(var4_18.getMessage(), var4_18);
                            throw var2_9;
                        }
                        catch (ConnectionException var4_19) {
                            var2_10 = new FolderClosedException(this, var4_19.getMessage());
                            throw var2_10;
                        }
                    }
                    {
                        catch (Throwable var2_7) {
                            break block19;
                        }
                        catch (ProtocolException var5_21) {}
                        var3_14 = var2_6;
                        {
                            var3_14 = var2_6;
                            var4_17 = new MessagingException(var5_21.getMessage(), var5_21);
                            var3_14 = var2_6;
                            throw var4_17;
                        }
                    }
                }
                this.releaseStoreProtocol(var3_14);
                throw var2_7;
            }
            {
                catch (Throwable var2_8) {}
                throw var2_8;
            }
        }
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled unnecessary exception pruning
     */
    @Override
    public Message[] getMessagesByUID(long l, long l2) throws MessagingException {
        synchronized (this) {
            Serializable serializable;
            this.checkOpened();
            try {
                Object object = this.messageCacheLock;
                synchronized (object) {
                    if (this.uidTable == null) {
                        this.uidTable = serializable = new Serializable();
                    }
                }
            }
            catch (ProtocolException protocolException) {
                MessagingException messagingException = new MessagingException(protocolException.getMessage(), protocolException);
                throw messagingException;
            }
            catch (ConnectionException connectionException) {
                FolderClosedException folderClosedException = new FolderClosedException(this, connectionException.getMessage());
                throw folderClosedException;
            }
            {
                UID[] arruID = this.getProtocol().fetchSequenceNumbers(l, l2);
                Message[] arrmessage = new Message[arruID.length];
                int n = 0;
                while (n < arruID.length) {
                    IMAPMessage iMAPMessage = this.getMessageBySeqNumber(arruID[n].seqnum);
                    iMAPMessage.setUID(arruID[n].uid);
                    arrmessage[n] = iMAPMessage;
                    Hashtable hashtable = this.uidTable;
                    serializable = new Serializable(arruID[n].uid);
                    hashtable.put(serializable, iMAPMessage);
                    ++n;
                }
                return arrmessage;
            }
        }
    }

    /*
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     */
    @Override
    public Message[] getMessagesByUID(long[] object) throws MessagingException {
        int n;
        Object object2;
        Cloneable cloneable;
        Object object3;
        int n2;
        block23 : {
            int n3;
            block22 : {
                // MONITORENTER : this
                this.checkOpened();
                try {
                    Object object4 = this.messageCacheLock;
                    // MONITORENTER : object4
                    object2 = this.uidTable;
                    n2 = 0;
                    if (object2 != null) {
                        cloneable = new Vector();
                        n = 0;
                        break block22;
                    }
                    object2 = new Hashtable();
                    this.uidTable = object2;
                    object2 = object;
                    break block23;
                }
                catch (ProtocolException protocolException) {
                    MessagingException messagingException = new MessagingException(protocolException.getMessage(), protocolException);
                    throw messagingException;
                }
                catch (ConnectionException connectionException) {
                    object = new FolderClosedException(this, connectionException.getMessage());
                    throw object;
                }
            }
            do {
                if (n >= ((long[])object).length) {
                    n3 = ((Vector)cloneable).size();
                    object2 = new long[n3];
                    break;
                }
                object3 = this.uidTable;
                object2 = new Long(object[n]);
                if (!object3.containsKey(object2)) {
                    ((Vector)cloneable).addElement(object2);
                }
                ++n;
            } while (true);
            for (n = 0; n < n3; ++n) {
                object2[n] = (Long)((Vector)cloneable).elementAt(n);
            }
        }
        if (((long[])object2).length > 0) {
            UID[] arruID = this.getProtocol().fetchSequenceNumbers((long[])object2);
            for (n = 0; n < arruID.length; ++n) {
                object2 = this.getMessageBySeqNumber(arruID[n].seqnum);
                ((IMAPMessage)object2).setUID(arruID[n].uid);
                cloneable = this.uidTable;
                object3 = new Long(arruID[n].uid);
                ((Hashtable)cloneable).put(object3, object2);
            }
        }
        object3 = new Message[((long[])object).length];
        n = n2;
        do {
            if (n >= ((long[])object).length) {
                // MONITOREXIT : object4
                // MONITOREXIT : this
                return object3;
            }
            cloneable = this.uidTable;
            object2 = new Long(object[n]);
            object3[n] = (Message)((Hashtable)cloneable).get(object2);
            ++n;
        } while (true);
    }

    @Override
    public String getName() {
        synchronized (this) {
            String string2 = this.name;
            if (string2 != null) return this.name;
            try {
                this.name = this.fullName.substring(this.fullName.lastIndexOf(this.getSeparator()) + 1);
                return this.name;
            }
            catch (MessagingException messagingException) {}
            return this.name;
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    @Override
    public int getNewMessageCount() throws MessagingException {
        synchronized (this) {
            if (this.opened) ** GOTO lbl27
            this.checkExists();
            try {
                return this.getStatus().recent;
            }
            catch (ProtocolException var2_4) {
                var3_11 = new MessagingException(var2_4.getMessage(), var2_4);
                throw var3_11;
            }
            catch (ConnectionException var2_5) {
                var3_12 = new StoreClosedException(this.store, var2_5.getMessage());
                throw var3_12;
            }
            catch (BadCommandException var3_13) {
                block19 : {
                    var2_6 = null;
                    var3_14 = null;
                    var3_14 = var4_16 = this.getStoreProtocol();
                    var2_6 = var4_16;
                    var5_20 = var4_16.examine(this.fullName);
                    var3_14 = var4_16;
                    var2_6 = var4_16;
                    var4_16.close();
                    var3_14 = var4_16;
                    var2_6 = var4_16;
                    var1_2 = var5_20.recent;
                    this.releaseStoreProtocol(var4_16);
                    return var1_2;
lbl27: // 1 sources:
                    var3_15 = this.messageCacheLock;
                    synchronized (var3_15) {
                        try {
                            this.keepConnectionAlive(true);
                            return this.recent;
                        }
                        catch (ProtocolException var2_9) {
                            var4_18 = new MessagingException(var2_9.getMessage(), var2_9);
                            throw var4_18;
                        }
                        catch (ConnectionException var4_19) {
                            var2_10 = new FolderClosedException(this, var4_19.getMessage());
                            throw var2_10;
                        }
                    }
                    {
                        catch (Throwable var2_7) {
                            break block19;
                        }
                        catch (ProtocolException var4_17) {}
                        var3_14 = var2_6;
                        {
                            var3_14 = var2_6;
                            var5_21 = new MessagingException(var4_17.getMessage(), var4_17);
                            var3_14 = var2_6;
                            throw var5_21;
                        }
                    }
                }
                this.releaseStoreProtocol(var3_14);
                throw var2_7;
            }
            {
                catch (Throwable var2_8) {}
                throw var2_8;
            }
        }
    }

    @Override
    public Folder getParent() throws MessagingException {
        synchronized (this) {
            char c = this.getSeparator();
            int n = this.fullName.lastIndexOf(c);
            if (n == -1) return new DefaultFolder((IMAPStore)this.store);
            return new IMAPFolder(this.fullName.substring(0, n), c, (IMAPStore)this.store);
        }
    }

    @Override
    public Flags getPermanentFlags() {
        synchronized (this) {
            return (Flags)this.permanentFlags.clone();
        }
    }

    public Quota[] getQuota() throws MessagingException {
        return (Quota[])this.doOptionalCommand("QUOTA not supported", new ProtocolCommand(){

            @Override
            public Object doCommand(IMAPProtocol iMAPProtocol) throws ProtocolException {
                return iMAPProtocol.getQuotaRoot(IMAPFolder.this.fullName);
            }
        });
    }

    @Override
    public char getSeparator() throws MessagingException {
        synchronized (this) {
            if (this.separator != '\uffff') return this.separator;
            Object object = null;
            object = new ProtocolCommand(){

                @Override
                public Object doCommand(IMAPProtocol iMAPProtocol) throws ProtocolException {
                    if (!iMAPProtocol.isREV1()) return iMAPProtocol.list("", IMAPFolder.this.fullName);
                    return iMAPProtocol.list(IMAPFolder.this.fullName, "");
                }
            };
            this.separator = (object = (ListInfo[])this.doCommand((ProtocolCommand)object)) != null ? (char)object[0].separator : (char)47;
            return this.separator;
        }
    }

    protected IMAPProtocol getStoreProtocol() throws ProtocolException {
        synchronized (this) {
            if (!this.connectionPoolDebug) return ((IMAPStore)this.store).getStoreProtocol();
            this.out.println("DEBUG: getStoreProtocol() - borrowing a connection");
            return ((IMAPStore)this.store).getStoreProtocol();
        }
    }

    @Override
    public int getType() throws MessagingException {
        synchronized (this) {
            if (this.opened) {
                if (this.attributes != null) return this.type;
                this.exists();
                return this.type;
            } else {
                this.checkExists();
            }
            return this.type;
        }
    }

    /*
     * Enabled unnecessary exception pruning
     */
    @Override
    public long getUID(Message object) throws MessagingException {
        synchronized (this) {
            if (((Message)object).getFolder() != this) {
                object = new NoSuchElementException("Message does not belong to this folder");
                throw object;
            }
            this.checkOpened();
            Object object2 = (IMAPMessage)object;
            long l = ((IMAPMessage)object2).getUID();
            if (l != -1L) {
                return l;
            }
            object = this.messageCacheLock;
            synchronized (object) {
                try {
                    try {
                        IMAPProtocol iMAPProtocol = this.getProtocol();
                        ((IMAPMessage)object2).checkExpunged();
                        UID uID = iMAPProtocol.fetchUID(((IMAPMessage)object2).getSequenceNumber());
                        if (uID == null) return l;
                        l = uID.uid;
                        ((IMAPMessage)object2).setUID(l);
                        if (this.uidTable == null) {
                            Hashtable hashtable;
                            this.uidTable = hashtable = new Hashtable();
                        }
                        Hashtable hashtable = this.uidTable;
                        Long l2 = new Long(l);
                        hashtable.put(l2, object2);
                        return l;
                    }
                    catch (ProtocolException protocolException) {
                        object2 = new MessagingException(protocolException.getMessage(), protocolException);
                        throw object2;
                    }
                    catch (ConnectionException connectionException) {
                        object2 = new FolderClosedException(this, connectionException.getMessage());
                        throw object2;
                    }
                }
                catch (Throwable throwable) {}
                throw throwable;
            }
        }
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     */
    public long getUIDNext() throws MessagingException {
        block21 : {
            block20 : {
                block18 : {
                    block19 : {
                        // MONITORENTER : this
                        if (this.opened) {
                            var1_1 = this.uidnext;
                            // MONITOREXIT : this
                            return var1_1;
                        }
                        var3_3 = null;
                        var4_4 = this.getStoreProtocol();
                        var5_8 = var4_4;
                        try {
                            var6_9 = var4_4.status(this.fullName, new String[]{"UIDNEXT"});
                            var5_8 = var6_9;
                            break block18;
                        }
                        catch (ProtocolException var6_10) {
                            break block19;
                        }
                        catch (ConnectionException var6_11) {
                            ** GOTO lbl34
                        }
                        catch (BadCommandException var6_12) {
                            break block20;
                        }
                        catch (Throwable var4_5) {
                            var5_8 = null;
                            break block21;
                        }
                        catch (ProtocolException var6_13) {
                            var4_4 = null;
                        }
                    }
                    var5_8 = var4_4;
                    var5_8 = var4_4;
                    var3_3 = new MessagingException(var6_14.getMessage(), (Exception)var6_14);
                    var5_8 = var4_4;
                    throw var3_3;
                    catch (ConnectionException var6_15) {
                        var4_4 = null;
                    }
lbl34: // 2 sources:
                    var5_8 = var4_4;
                    this.throwClosedException((ConnectionException)var6_16);
                    var5_8 = var3_3;
                }
                this.releaseStoreProtocol(var4_4);
                var1_2 = var5_8.uidnext;
                // MONITOREXIT : this
                return var1_2;
                catch (BadCommandException var6_17) {
                    var4_4 = null;
                }
            }
            var5_8 = var4_4;
            try {
                var5_8 = var4_4;
                var3_3 = new MessagingException("Cannot obtain UIDNext", (Exception)var6_18);
                var5_8 = var4_4;
                throw var3_3;
            }
            catch (Throwable var4_6) {
                // empty catch block
            }
        }
        this.releaseStoreProtocol((IMAPProtocol)var5_8);
        throw var4_7;
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     */
    @Override
    public long getUIDValidity() throws MessagingException {
        block21 : {
            block20 : {
                block18 : {
                    block19 : {
                        // MONITORENTER : this
                        if (this.opened) {
                            var1_1 = this.uidvalidity;
                            // MONITOREXIT : this
                            return var1_1;
                        }
                        var3_3 = null;
                        var4_4 = this.getStoreProtocol();
                        var5_8 = var4_4;
                        try {
                            var6_9 = var4_4.status(this.fullName, new String[]{"UIDVALIDITY"});
                            var5_8 = var6_9;
                            break block18;
                        }
                        catch (ProtocolException var6_10) {
                            break block19;
                        }
                        catch (ConnectionException var6_11) {
                            ** GOTO lbl34
                        }
                        catch (BadCommandException var6_12) {
                            break block20;
                        }
                        catch (Throwable var4_5) {
                            var5_8 = null;
                            break block21;
                        }
                        catch (ProtocolException var6_13) {
                            var4_4 = null;
                        }
                    }
                    var5_8 = var4_4;
                    var5_8 = var4_4;
                    var3_3 = new MessagingException(var6_14.getMessage(), (Exception)var6_14);
                    var5_8 = var4_4;
                    throw var3_3;
                    catch (ConnectionException var6_15) {
                        var4_4 = null;
                    }
lbl34: // 2 sources:
                    var5_8 = var4_4;
                    this.throwClosedException((ConnectionException)var6_16);
                    var5_8 = var3_3;
                }
                this.releaseStoreProtocol(var4_4);
                var1_2 = var5_8.uidvalidity;
                // MONITOREXIT : this
                return var1_2;
                catch (BadCommandException var6_17) {
                    var4_4 = null;
                }
            }
            var5_8 = var4_4;
            try {
                var5_8 = var4_4;
                var3_3 = new MessagingException("Cannot obtain UIDValidity", (Exception)var6_18);
                var5_8 = var4_4;
                throw var3_3;
            }
            catch (Throwable var4_6) {
                // empty catch block
            }
        }
        this.releaseStoreProtocol((IMAPProtocol)var5_8);
        throw var4_7;
    }

    /*
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     */
    @Override
    public int getUnreadMessageCount() throws MessagingException {
        // MONITORENTER : this
        if (!this.opened) {
            this.checkExists();
            try {
                int n = this.getStatus().unseen;
                // MONITOREXIT : this
                return n;
            }
            catch (ProtocolException protocolException) {
                MessagingException messagingException = new MessagingException(protocolException.getMessage(), protocolException);
                throw messagingException;
            }
            catch (ConnectionException connectionException) {
                StoreClosedException storeClosedException = new StoreClosedException(this.store, connectionException.getMessage());
                throw storeClosedException;
            }
            catch (BadCommandException badCommandException) {
                // MONITOREXIT : this
                return -1;
            }
        }
        Flags flags = new Flags();
        flags.add(Flags.Flag.SEEN);
        try {
            Object object = this.messageCacheLock;
            // MONITORENTER : object
        }
        catch (ProtocolException protocolException) {
            MessagingException messagingException = new MessagingException(protocolException.getMessage(), protocolException);
            throw messagingException;
        }
        catch (ConnectionException connectionException) {
            FolderClosedException folderClosedException = new FolderClosedException(this, connectionException.getMessage());
            throw folderClosedException;
        }
        IMAPProtocol iMAPProtocol = this.getProtocol();
        FlagTerm flagTerm = new FlagTerm(flags, false);
        int n = iMAPProtocol.search(flagTerm).length;
        // MONITOREXIT : object
        // MONITOREXIT : this
        return n;
    }

    @Override
    public void handleResponse(Response object) {
        if (((Response)object).isOK() || ((Response)object).isNO() || ((Response)object).isBAD() || ((Response)object).isBYE()) {
            ((IMAPStore)this.store).handleResponseCode((Response)object);
        }
        boolean bl = ((Response)object).isBYE();
        int n = 0;
        if (bl) {
            if (!this.opened) return;
            this.cleanup(false);
            return;
        }
        if (((Response)object).isOK()) {
            return;
        }
        if (!((Response)object).isUnTagged()) {
            return;
        }
        if (!(object instanceof IMAPResponse)) {
            PrintStream printStream = this.out;
            StringBuilder stringBuilder = new StringBuilder("UNEXPECTED RESPONSE : ");
            stringBuilder.append(((Response)object).toString());
            printStream.println(stringBuilder.toString());
            this.out.println("CONTACT javamail@sun.com");
            return;
        }
        if (!((IMAPResponse)(object = (IMAPResponse)object)).keyEquals("EXISTS")) {
            if (!((IMAPResponse)object).keyEquals("EXPUNGE")) {
                if (((IMAPResponse)object).keyEquals("FETCH")) {
                    Object object2 = (FetchResponse)object;
                    object = (Flags)((Object)((FetchResponse)object2).getItem(Flags.class));
                    if (object == null) return;
                    if ((object2 = this.getMessageBySeqNumber(((IMAPResponse)object2).getNumber())) == null) return;
                    ((IMAPMessage)object2)._setFlags((Flags)object);
                    this.notifyMessageChangedListeners(1, (Message)object2);
                    return;
                }
                if (!((IMAPResponse)object).keyEquals("RECENT")) return;
                this.recent = ((IMAPResponse)object).getNumber();
                return;
            }
        } else {
            int n2;
            int n3 = ((IMAPResponse)object).getNumber();
            if (n3 <= (n2 = this.realTotal)) {
                return;
            }
            n2 = n3 - n2;
            object = new Message[n2];
            do {
                int n4;
                if (n >= n2) {
                    this.notifyMessageAddedListeners((Message[])object);
                    return;
                }
                this.total = n3 = this.total + 1;
                this.realTotal = n4 = this.realTotal + 1;
                IMAPMessage iMAPMessage = new IMAPMessage(this, n3, n4);
                object[n] = iMAPMessage;
                this.messageCache.addElement(iMAPMessage);
                ++n;
            } while (true);
        }
        IMAPMessage iMAPMessage = this.getMessageBySeqNumber(((IMAPResponse)object).getNumber());
        iMAPMessage.setExpunged(true);
        n = iMAPMessage.getMessageNumber();
        do {
            if (n >= this.total) {
                --this.realTotal;
                if (!this.doExpungeNotification) return;
                this.notifyMessageRemovedListeners(false, new Message[]{iMAPMessage});
                return;
            }
            object = (IMAPMessage)this.messageCache.elementAt(n);
            if (!((Message)object).isExpunged()) {
                ((IMAPMessage)object).setSequenceNumber(((IMAPMessage)object).getSequenceNumber() - 1);
            }
            ++n;
        } while (true);
    }

    void handleResponses(Response[] arrresponse) {
        int n = 0;
        while (n < arrresponse.length) {
            if (arrresponse[n] != null) {
                this.handleResponse(arrresponse[n]);
            }
            ++n;
        }
        return;
    }

    /*
     * Loose catch block
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     */
    @Override
    public boolean hasNewMessages() throws MessagingException {
        // MONITORENTER : this
        boolean bl = this.opened;
        boolean bl2 = false;
        if (bl) {
            block12 : {
                Object object = this.messageCacheLock;
                // MONITORENTER : object
                try {
                    this.keepConnectionAlive(true);
                    if (this.recent <= 0) break block12;
                    bl2 = true;
                }
                catch (ProtocolException protocolException) {
                    MessagingException messagingException = new MessagingException(protocolException.getMessage(), protocolException);
                    throw messagingException;
                }
                catch (ConnectionException connectionException) {
                    FolderClosedException folderClosedException = new FolderClosedException(this, connectionException.getMessage());
                    throw folderClosedException;
                }
            }
            // MONITOREXIT : object
            // MONITOREXIT : this
            return bl2;
        }
        this.checkExists();
        ProtocolCommand protocolCommand = new ProtocolCommand(){

            @Override
            public Object doCommand(IMAPProtocol arrlistInfo) throws ProtocolException {
                if ((arrlistInfo = arrlistInfo.list("", IMAPFolder.this.fullName)) != null) {
                    if (arrlistInfo[0].changeState == 1) {
                        return Boolean.TRUE;
                    }
                    if (arrlistInfo[0].changeState == 2) {
                        return Boolean.FALSE;
                    }
                }
                if (IMAPFolder.access$0((IMAPFolder)IMAPFolder.this).recent <= 0) return Boolean.FALSE;
                return Boolean.TRUE;
            }
        };
        protocolCommand = (Boolean)this.doCommandIgnoreFailure(protocolCommand);
        if (protocolCommand == null) {
            // MONITOREXIT : this
            return false;
        }
        bl2 = (Boolean)((Object)protocolCommand);
        // MONITOREXIT : this
        return bl2;
        catch (Throwable throwable) {
            throw throwable;
        }
    }

    /*
     * Loose catch block
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     */
    public void idle() throws MessagingException {
        // MONITORENTER : this
        this.checkOpened();
        ProtocolCommand protocolCommand = new ProtocolCommand(){

            @Override
            public Object doCommand(IMAPProtocol iMAPProtocol) throws ProtocolException {
                if (IMAPFolder.this.idleState == 0) {
                    iMAPProtocol.idleStart();
                    IMAPFolder.access$2(IMAPFolder.this, 1);
                    return Boolean.TRUE;
                }
                try {
                    IMAPFolder.this.messageCacheLock.wait();
                    return Boolean.FALSE;
                }
                catch (InterruptedException interruptedException) {
                    return Boolean.FALSE;
                }
            }
        };
        if (!((Boolean)this.doOptionalCommand("IDLE not supported", protocolCommand)).booleanValue()) {
            // MONITOREXIT : this
            return;
        }
        // MONITOREXIT : this
        do {
            Response response = this.protocol.readIdleResponse();
            protocolCommand = this.messageCacheLock;
            // MONITORENTER : protocolCommand
            if (response != null && this.protocol != null && this.protocol.processIdleResponse(response)) {
                // MONITOREXIT : protocolCommand
                continue;
            }
            this.idleState = 0;
            this.messageCacheLock.notifyAll();
            // MONITOREXIT : protocolCommand
            int n = ((IMAPStore)this.store).getMinIdleTime();
            if (n <= 0) return;
            long l = n;
            Thread.sleep(l);
            return;
            break;
        } while (true);
        catch (InterruptedException interruptedException) {
            return;
        }
        {
            catch (ProtocolException protocolException) {
                throw new MessagingException(protocolException.getMessage(), protocolException);
            }
            catch (ConnectionException connectionException) {
                this.throwClosedException(connectionException);
                continue;
            }
        }
    }

    /*
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     */
    @Override
    public boolean isOpen() {
        // MONITORENTER : this
        Object object = this.messageCacheLock;
        // MONITORENTER : object
        boolean bl = this.opened;
        if (bl) {
            try {
                this.keepConnectionAlive(false);
                // MONITOREXIT : object
            }
            catch (ProtocolException protocolException) {}
        }
        bl = this.opened;
        // MONITOREXIT : this
        return bl;
    }

    @Override
    public boolean isSubscribed() {
        synchronized (this) {
            CharSequence charSequence;
            ListInfo[] arrlistInfo = null;
            if (this.isNamespace && this.separator != '\u0000') {
                charSequence = new StringBuilder(String.valueOf(this.fullName));
                ((StringBuilder)charSequence).append(this.separator);
                charSequence = ((StringBuilder)charSequence).toString();
            } else {
                charSequence = this.fullName;
            }
            try {
                ProtocolCommand protocolCommand = new ProtocolCommand((String)charSequence){
                    private final /* synthetic */ String val$lname;
                    {
                        this.val$lname = string2;
                    }

                    @Override
                    public Object doCommand(IMAPProtocol iMAPProtocol) throws ProtocolException {
                        return iMAPProtocol.lsub("", this.val$lname);
                    }
                };
                protocolCommand = (ProtocolCommand)this.doProtocolCommand(protocolCommand);
                arrlistInfo = protocolCommand;
            }
            catch (ProtocolException protocolException) {
                // empty catch block
            }
            if (arrlistInfo == null) return false;
            boolean bl = arrlistInfo[this.findName((ListInfo[])arrlistInfo, (String)charSequence)].canOpen;
            return bl;
        }
    }

    @Override
    public Folder[] list(String string2) throws MessagingException {
        return this.doList(string2, false);
    }

    public Rights[] listRights(final String string2) throws MessagingException {
        return (Rights[])this.doOptionalCommand("ACL not supported", new ProtocolCommand(){

            @Override
            public Object doCommand(IMAPProtocol iMAPProtocol) throws ProtocolException {
                return iMAPProtocol.listRights(IMAPFolder.this.fullName, string2);
            }
        });
    }

    @Override
    public Folder[] listSubscribed(String string2) throws MessagingException {
        return this.doList(string2, true);
    }

    public Rights myRights() throws MessagingException {
        return (Rights)this.doOptionalCommand("ACL not supported", new ProtocolCommand(){

            @Override
            public Object doCommand(IMAPProtocol iMAPProtocol) throws ProtocolException {
                return iMAPProtocol.myRights(IMAPFolder.this.fullName);
            }
        });
    }

    /*
     * Exception decompiling
     */
    @Override
    public void open(int var1_1) throws MessagingException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [5[TRYBLOCK]], but top level block is 8[TRYBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        // the.bytecode.club.bytecodeviewer.decompilers.CFRDecompiler.decompileToZip(CFRDecompiler.java:311)
        // the.bytecode.club.bytecodeviewer.gui.MainViewerGUI$14$1$3.run(MainViewerGUI.java:1211)
        throw new IllegalStateException("Decompilation failed");
    }

    protected void releaseStoreProtocol(IMAPProtocol iMAPProtocol) {
        synchronized (this) {
            if (iMAPProtocol == this.protocol) return;
            ((IMAPStore)this.store).releaseStoreProtocol(iMAPProtocol);
            return;
        }
    }

    public void removeACL(final String string2) throws MessagingException {
        this.doOptionalCommand("ACL not supported", new ProtocolCommand(){

            @Override
            public Object doCommand(IMAPProtocol iMAPProtocol) throws ProtocolException {
                iMAPProtocol.deleteACL(IMAPFolder.this.fullName, string2);
                return null;
            }
        });
    }

    public void removeRights(ACL aCL) throws MessagingException {
        this.setACL(aCL, '-');
    }

    @Override
    public boolean renameTo(Folder object) throws MessagingException {
        synchronized (this) {
            this.checkClosed();
            this.checkExists();
            if (((Folder)object).getStore() == this.store) {
                ProtocolCommand protocolCommand = new ProtocolCommand((Folder)object){
                    private final /* synthetic */ Folder val$f;
                    {
                        this.val$f = folder;
                    }

                    @Override
                    public Object doCommand(IMAPProtocol iMAPProtocol) throws ProtocolException {
                        iMAPProtocol.rename(IMAPFolder.this.fullName, this.val$f.getFullName());
                        return Boolean.TRUE;
                    }
                };
                if ((protocolCommand = this.doCommandIgnoreFailure(protocolCommand)) == null) {
                    return false;
                }
                this.exists = false;
                this.attributes = null;
                this.notifyFolderRenamedListeners((Folder)object);
                return true;
            }
            object = new MessagingException("Can't rename across Stores");
            throw object;
        }
    }

    /*
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     */
    @Override
    public Message[] search(SearchTerm arrmessage) throws MessagingException {
        Message[] arrmessage2;
        // MONITORENTER : this
        this.checkOpened();
        try {
            arrmessage2 = null;
            Object object = this.messageCacheLock;
            // MONITORENTER : object
        }
        catch (ProtocolException protocolException) {
            arrmessage = new MessagingException(protocolException.getMessage(), protocolException);
            throw arrmessage;
        }
        catch (ConnectionException connectionException) {
            arrmessage = new FolderClosedException(this, connectionException.getMessage());
            throw arrmessage;
        }
        catch (SearchException searchException) {
            arrmessage = super.search((SearchTerm)arrmessage);
            // MONITOREXIT : this
            return arrmessage;
        }
        catch (CommandFailedException commandFailedException) {
            arrmessage = super.search((SearchTerm)arrmessage);
            // MONITOREXIT : this
            return arrmessage;
        }
        int[] arrn = this.getProtocol().search((SearchTerm)arrmessage);
        if (arrn == null) {
            // MONITOREXIT : object
            // MONITOREXIT : this
            return arrmessage2;
        }
        arrmessage2 = new IMAPMessage[arrn.length];
        int n = 0;
        while (n < arrn.length) {
            arrmessage2[n] = this.getMessageBySeqNumber(arrn[n]);
            ++n;
        }
        return arrmessage2;
    }

    /*
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     */
    @Override
    public Message[] search(SearchTerm arrmessage, Message[] object3) throws MessagingException {
        Message[] arrmessage2;
        FolderClosedException folderClosedException;
        // MONITORENTER : this
        this.checkOpened();
        int n = ((void)folderClosedException).length;
        if (n == 0) {
            // MONITOREXIT : this
            return folderClosedException;
        }
        try {
            arrmessage2 = null;
            Object object2 = this.messageCacheLock;
            // MONITORENTER : object2
        }
        catch (ProtocolException object3) {
            arrmessage = new MessagingException(((Throwable)object3).getMessage(), (Exception)object3);
            throw arrmessage;
        }
        catch (ConnectionException connectionException) {
            folderClosedException = new FolderClosedException(this, connectionException.getMessage());
            throw folderClosedException;
        }
        catch (SearchException searchException) {
            arrmessage = super.search((SearchTerm)arrmessage, (Message[])folderClosedException);
            // MONITOREXIT : this
            return arrmessage;
        }
        catch (CommandFailedException commandFailedException) {
            arrmessage = super.search((SearchTerm)arrmessage, (Message[])folderClosedException);
            // MONITOREXIT : this
            return arrmessage;
        }
        int[] arrn = this.getProtocol();
        MessageSet[] arrmessageSet = Utility.toMessageSet((Message[])folderClosedException, null);
        if (arrmessageSet == null) {
            arrmessage2 = new MessageRemovedException("Messages have been removed");
            throw arrmessage2;
        }
        if ((arrn = arrn.search(arrmessageSet, (SearchTerm)arrmessage)) == null) {
            // MONITOREXIT : object2
            // MONITOREXIT : this
            return arrmessage2;
        }
        arrmessage2 = new IMAPMessage[arrn.length];
        n = 0;
        while (n < arrn.length) {
            arrmessage2[n] = this.getMessageBySeqNumber(arrn[n]);
            ++n;
        }
        return arrmessage2;
    }

    /*
     * WARNING - void declaration
     * Enabled unnecessary exception pruning
     */
    @Override
    public void setFlags(Message[] arrmessage, Flags flags, boolean bl) throws MessagingException {
        synchronized (this) {
            void var2_7;
            this.checkOpened();
            this.checkFlags((Flags)var2_7);
            int n = arrmessage.length;
            if (n == 0) {
                return;
            }
            Object object = this.messageCacheLock;
            synchronized (object) {
                try {
                    try {
                        IMAPProtocol iMAPProtocol = this.getProtocol();
                        MessageSet[] arrmessageSet = Utility.toMessageSet(arrmessage, null);
                        if (arrmessageSet != null) {
                            void var3_10;
                            iMAPProtocol.storeFlags(arrmessageSet, (Flags)var2_7, (boolean)var3_10);
                            return;
                        }
                        MessageRemovedException messageRemovedException = new MessageRemovedException("Messages have been removed");
                        throw messageRemovedException;
                    }
                    catch (ProtocolException protocolException) {
                        MessagingException messagingException = new MessagingException(protocolException.getMessage(), protocolException);
                        throw messagingException;
                    }
                    catch (ConnectionException connectionException) {
                        FolderClosedException folderClosedException = new FolderClosedException(this, connectionException.getMessage());
                        throw folderClosedException;
                    }
                }
                catch (Throwable throwable) {}
                throw throwable;
            }
        }
    }

    public void setQuota(final Quota quota) throws MessagingException {
        this.doOptionalCommand("QUOTA not supported", new ProtocolCommand(){

            @Override
            public Object doCommand(IMAPProtocol iMAPProtocol) throws ProtocolException {
                iMAPProtocol.setQuota(quota);
                return null;
            }
        });
    }

    @Override
    public void setSubscribed(final boolean bl) throws MessagingException {
        synchronized (this) {
            ProtocolCommand protocolCommand = new ProtocolCommand(){

                @Override
                public Object doCommand(IMAPProtocol iMAPProtocol) throws ProtocolException {
                    if (bl) {
                        iMAPProtocol.subscribe(IMAPFolder.this.fullName);
                        return null;
                    }
                    iMAPProtocol.unsubscribe(IMAPFolder.this.fullName);
                    return null;
                }
            };
            this.doCommandIgnoreFailure(protocolCommand);
            return;
        }
    }

    void waitIfIdle() throws ProtocolException {
        int n;
        while ((n = this.idleState) != 0) {
            if (n == 1) {
                this.protocol.idleAbort();
                this.idleState = 2;
            }
            try {
                this.messageCacheLock.wait();
            }
            catch (InterruptedException interruptedException) {
            }
        }
        return;
    }

    public static class FetchProfileItem
    extends FetchProfile.Item {
        public static final FetchProfileItem HEADERS = new FetchProfileItem("HEADERS");
        public static final FetchProfileItem SIZE = new FetchProfileItem("SIZE");

        protected FetchProfileItem(String string2) {
            super(string2);
        }
    }

    public static interface ProtocolCommand {
        public Object doCommand(IMAPProtocol var1) throws ProtocolException;
    }

}

