/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.pop3;

import com.sun.mail.pop3.DefaultFolder;
import com.sun.mail.pop3.POP3Message;
import com.sun.mail.pop3.POP3Store;
import com.sun.mail.pop3.Protocol;
import com.sun.mail.pop3.Status;
import com.sun.mail.util.LineInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.mail.FetchProfile;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.FolderClosedException;
import javax.mail.FolderNotFoundException;
import javax.mail.Message;
import javax.mail.MessageRemovedException;
import javax.mail.MessagingException;
import javax.mail.MethodNotSupportedException;
import javax.mail.Store;
import javax.mail.UIDFolder;

public class POP3Folder
extends Folder {
    private boolean doneUidl = false;
    private boolean exists = false;
    private Vector message_cache;
    private String name;
    private boolean opened = false;
    private Protocol port;
    private int size;
    private int total;

    POP3Folder(POP3Store pOP3Store, String string2) {
        super(pOP3Store);
        this.name = string2;
        if (!string2.equalsIgnoreCase("INBOX")) return;
        this.exists = true;
    }

    @Override
    public void appendMessages(Message[] arrmessage) throws MessagingException {
        throw new MethodNotSupportedException("Append not supported");
    }

    void checkClosed() throws IllegalStateException {
        if (this.opened) throw new IllegalStateException("Folder is Open");
    }

    void checkOpen() throws IllegalStateException {
        if (!this.opened) throw new IllegalStateException("Folder is not Open");
    }

    void checkReadable() throws IllegalStateException {
        if (!this.opened) throw new IllegalStateException("Folder is not Readable");
        if (this.mode == 1) return;
        if (this.mode != 2) throw new IllegalStateException("Folder is not Readable");
    }

    void checkWritable() throws IllegalStateException {
        if (!this.opened) throw new IllegalStateException("Folder is not Writable");
        if (this.mode != 2) throw new IllegalStateException("Folder is not Writable");
    }

    /*
     * Exception decompiling
     */
    @Override
    public void close(boolean var1_1) throws MessagingException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [1[TRYBLOCK]], but top level block is 8[CATCHBLOCK]
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
    public boolean create(int n) throws MessagingException {
        return false;
    }

    protected POP3Message createMessage(Folder object, int n) throws MessagingException {
        Object object2;
        block3 : {
            object = ((POP3Store)this.store).messageConstructor;
            if (object != null) {
                try {
                    object2 = new Integer(n);
                    object = (POP3Message)((Constructor)object).newInstance(this, object2);
                    break block3;
                }
                catch (Exception exception) {}
            }
            object = null;
        }
        object2 = object;
        if (object != null) return object2;
        return new POP3Message(this, n);
    }

    @Override
    public boolean delete(boolean bl) throws MessagingException {
        throw new MethodNotSupportedException("delete");
    }

    @Override
    public boolean exists() {
        return this.exists;
    }

    @Override
    public Message[] expunge() throws MessagingException {
        throw new MethodNotSupportedException("Expunge not supported");
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     */
    @Override
    public void fetch(Message[] var1_1, FetchProfile var2_4) throws MessagingException {
        // MONITORENTER : this
        this.checkReadable();
        var3_6 = this.doneUidl;
        var4_7 = 0;
        if (!var3_6 && var2_3.contains(UIDFolder.FetchProfileItem.UID)) {
            var5_8 = this.message_cache.size();
            var6_9 = new String[var5_8];
            try {
                var3_6 = this.port.uidl(var6_9);
                ** if (var3_6) goto lbl-1000
            }
            catch (IOException var1_2) {
                var2_3 = new MessagingException("error getting UIDL", var1_2);
                throw var2_3;
            }
            catch (EOFException var2_4) {
                this.close(false);
                var1_1 /* !! */  = new FolderClosedException(this, var2_4.toString());
                throw var1_1 /* !! */ ;
            }
lbl-1000: // 1 sources:
            {
                // MONITOREXIT : this
                return;
            }
lbl-1000: // 1 sources:
            {
            }
            var7_10 = 0;
            do {
                if (var7_10 >= var5_8) {
                    this.doneUidl = true;
                    break;
                }
                if (var6_9[var7_10] != null) {
                    ((POP3Message)this.getMessage((int)(var7_10 + 1))).uid = var6_9[var7_10];
                }
                ++var7_10;
            } while (true);
        }
        if (!var2_3.contains(FetchProfile.Item.ENVELOPE)) {
            // MONITOREXIT : this
            return;
        }
        var7_10 = var4_7;
        block10 : do {
            if (var7_10 >= (var4_7 = var1_1 /* !! */ .length)) {
                return;
            }
            try {
                var2_3 = (POP3Message)var1_1 /* !! */ [var7_10];
                var2_3.getHeader("");
                var2_3.getSize();
lbl43: // 2 sources:
                do {
                    ++var7_10;
                    continue block10;
                    break;
                } while (true);
            }
            catch (MessageRemovedException var2_5) {
                ** continue;
            }
        } while (true);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        this.close(false);
    }

    @Override
    public Folder getFolder(String string2) throws MessagingException {
        throw new MessagingException("not a directory");
    }

    @Override
    public String getFullName() {
        return this.name;
    }

    @Override
    public Message getMessage(int n) throws MessagingException {
        synchronized (this) {
            this.checkOpen();
            Object object = this.message_cache;
            int n2 = n - 1;
            POP3Message pOP3Message = (POP3Message)((Vector)object).elementAt(n2);
            object = pOP3Message;
            if (pOP3Message != null) return object;
            object = this.createMessage(this, n);
            this.message_cache.setElementAt(object, n2);
            return object;
        }
    }

    @Override
    public int getMessageCount() throws MessagingException {
        synchronized (this) {
            boolean bl = this.opened;
            if (!bl) {
                return -1;
            }
            this.checkReadable();
            int n = this.total;
            return n;
        }
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Folder getParent() {
        return new DefaultFolder((POP3Store)this.store);
    }

    @Override
    public Flags getPermanentFlags() {
        return new Flags();
    }

    Protocol getProtocol() throws MessagingException {
        this.checkOpen();
        return this.port;
    }

    @Override
    public char getSeparator() {
        return '\u0000';
    }

    public int getSize() throws MessagingException {
        synchronized (this) {
            this.checkOpen();
            return this.size;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     */
    public int[] getSizes() throws MessagingException {
        // MONITORENTER : this
        this.checkOpen();
        var1_1 = new int[this.total];
        var2_2 = null;
        var3_3 = null;
        var4_8 = this.port.list();
        try {
            var5_12 = new LineInputStream(var4_8);
        }
        catch (Throwable var5_14) {
            ** GOTO lbl49
        }
        catch (Throwable var5_15) {
            var4_8 = null;
            ** GOTO lbl49
        }
        catch (IOException var4_9) {
            var4_8 = null;
            var5_12 = var2_2;
            ** GOTO lbl62
        }
        catch (IOException var5_17) {
            var5_12 = var2_2;
            ** GOTO lbl62
        }
        do {
            block31 : {
                var2_2 = var5_12.readLine();
                if (var2_2 != null) break block31;
                try {
                    var5_12.close();
                }
                catch (IOException var5_13) {
                    // empty catch block
                }
                if (var4_8 == null) break block34;
                break block35;
            }
            try {
                var3_3 = new StringTokenizer((String)var2_2);
                var6_18 = Integer.parseInt(var3_3.nextToken());
                var7_19 = Integer.parseInt(var3_3.nextToken());
                if (var6_18 <= 0 || var6_18 > this.total) continue;
                var1_1[var6_18 - 1] = var7_19;
                continue;
            }
            catch (Throwable var3_4) {
                var2_2 = var5_12;
                var5_12 = var3_4;
                var3_3 = var2_2;
            }
            catch (IOException var3_5) {
                break block33;
            }
            if (var3_3 != null) {
                try {
                    var3_3.close();
                }
                catch (IOException var3_6) {
                    // empty catch block
                }
            }
            if (var4_8 == null) throw var5_12;
            var4_8.close();
            break;
        } while (true);
        catch (IOException var4_11) {
            throw var5_12;
        }
        {
            block34 : {
                block35 : {
                    block33 : {
                        throw var5_12;
                    }
                    if (var5_12 != null) {
                        try {
                            var5_12.close();
                        }
                        catch (IOException var5_16) {
                            // empty catch block
                        }
                    }
                    if (var4_8 == null) break block34;
                }
                try {
                    var4_8.close();
                    return var1_1;
                }
                catch (IOException var4_10) {
                    return var1_1;
                }
            }
            // MONITOREXIT : this
            return var1_1;
            catch (Exception var3_7) {
                continue;
            }
        }
    }

    @Override
    public int getType() {
        return 1;
    }

    public String getUID(Message object) throws MessagingException {
        synchronized (this) {
            this.checkOpen();
            object = (POP3Message)object;
            try {
                if (((POP3Message)object).uid != "UNKNOWN") return ((POP3Message)object).uid;
                ((POP3Message)object).uid = this.port.uidl(((Message)object).getMessageNumber());
                return ((POP3Message)object).uid;
            }
            catch (IOException iOException) {
                object = new MessagingException("error getting UIDL", iOException);
                throw object;
            }
            catch (EOFException eOFException) {
                this.close(false);
                FolderClosedException folderClosedException = new FolderClosedException(this, eOFException.toString());
                throw folderClosedException;
            }
        }
    }

    @Override
    public boolean hasNewMessages() throws MessagingException {
        return false;
    }

    @Override
    public boolean isOpen() {
        if (!this.opened) {
            return false;
        }
        if (this.store.isConnected()) {
            return true;
        }
        try {
            this.close(false);
            return false;
        }
        catch (MessagingException messagingException) {
            return false;
        }
    }

    @Override
    public Folder[] list(String string2) throws MessagingException {
        throw new MessagingException("not a directory");
    }

    public InputStream listCommand() throws MessagingException, IOException {
        synchronized (this) {
            this.checkOpen();
            return this.port.list();
        }
    }

    @Override
    protected void notifyMessageChangedListeners(int n, Message message) {
        super.notifyMessageChangedListeners(n, message);
    }

    /*
     * Enabled unnecessary exception pruning
     */
    @Override
    public void open(int n) throws MessagingException {
        synchronized (this) {
            this.checkClosed();
            boolean bl = this.exists;
            if (!bl) {
                FolderNotFoundException folderNotFoundException = new FolderNotFoundException(this, "folder is not INBOX");
                throw folderNotFoundException;
            }
            try {
                Vector vector = ((POP3Store)this.store).getPort(this);
                this.port = vector;
                vector = ((Protocol)((Object)vector)).stat();
                this.total = ((Status)vector).total;
                this.size = ((Status)vector).size;
                this.mode = n;
                this.opened = true;
                this.message_cache = vector = new Vector(this.total);
                vector.setSize(this.total);
                this.doneUidl = false;
            }
            catch (IOException iOException) {
                Object object;
                try {
                    if (this.port != null) {
                        this.port.quit();
                    }
                    this.port = null;
                }
                catch (Throwable throwable) {
                    this.port = null;
                    ((POP3Store)this.store).closePort(this);
                    throw throwable;
                }
                catch (IOException iOException2) {
                    this.port = null;
                    object = (POP3Store)this.store;
                }
                object = (POP3Store)this.store;
                ((POP3Store)object).closePort(this);
                object = new MessagingException("Open failed", iOException);
                throw object;
            }
            this.notifyConnectionListeners(1);
            return;
        }
    }

    @Override
    public boolean renameTo(Folder folder) throws MessagingException {
        throw new MethodNotSupportedException("renameTo");
    }
}

