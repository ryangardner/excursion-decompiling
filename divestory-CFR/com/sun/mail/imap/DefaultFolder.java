/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.imap;

import com.sun.mail.iap.ProtocolException;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;
import com.sun.mail.imap.protocol.IMAPProtocol;
import com.sun.mail.imap.protocol.ListInfo;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.MethodNotSupportedException;
import javax.mail.Store;

public class DefaultFolder
extends IMAPFolder {
    protected DefaultFolder(IMAPStore iMAPStore) {
        super("", '\uffff', iMAPStore);
        this.exists = true;
        this.type = 2;
    }

    @Override
    public void appendMessages(Message[] arrmessage) throws MessagingException {
        throw new MethodNotSupportedException("Cannot append to Default Folder");
    }

    @Override
    public boolean delete(boolean bl) throws MessagingException {
        throw new MethodNotSupportedException("Cannot delete Default Folder");
    }

    @Override
    public Message[] expunge() throws MessagingException {
        throw new MethodNotSupportedException("Cannot expunge Default Folder");
    }

    @Override
    public Folder getFolder(String string2) throws MessagingException {
        return new IMAPFolder(string2, '\uffff', (IMAPStore)this.store);
    }

    @Override
    public String getName() {
        return this.fullName;
    }

    @Override
    public Folder getParent() {
        return null;
    }

    @Override
    public boolean hasNewMessages() throws MessagingException {
        return false;
    }

    @Override
    public Folder[] list(String arrfolder) throws MessagingException {
        ListInfo[] arrlistInfo = null;
        arrlistInfo = (ListInfo[])this.doCommand(new IMAPFolder.ProtocolCommand((String)arrfolder){
            private final /* synthetic */ String val$pattern;
            {
                this.val$pattern = string2;
            }

            @Override
            public Object doCommand(IMAPProtocol iMAPProtocol) throws ProtocolException {
                return iMAPProtocol.list("", this.val$pattern);
            }
        });
        int n = 0;
        if (arrlistInfo == null) {
            return new Folder[0];
        }
        int n2 = arrlistInfo.length;
        arrfolder = new IMAPFolder[n2];
        while (n < n2) {
            arrfolder[n] = new IMAPFolder(arrlistInfo[n], (IMAPStore)this.store);
            ++n;
        }
        return arrfolder;
    }

    @Override
    public Folder[] listSubscribed(String arrfolder) throws MessagingException {
        ListInfo[] arrlistInfo = null;
        arrlistInfo = (ListInfo[])this.doCommand(new IMAPFolder.ProtocolCommand((String)arrfolder){
            private final /* synthetic */ String val$pattern;
            {
                this.val$pattern = string2;
            }

            @Override
            public Object doCommand(IMAPProtocol iMAPProtocol) throws ProtocolException {
                return iMAPProtocol.lsub("", this.val$pattern);
            }
        });
        int n = 0;
        if (arrlistInfo == null) {
            return new Folder[0];
        }
        int n2 = arrlistInfo.length;
        arrfolder = new IMAPFolder[n2];
        while (n < n2) {
            arrfolder[n] = new IMAPFolder(arrlistInfo[n], (IMAPStore)this.store);
            ++n;
        }
        return arrfolder;
    }

    @Override
    public boolean renameTo(Folder folder) throws MessagingException {
        throw new MethodNotSupportedException("Cannot rename Default Folder");
    }

}

