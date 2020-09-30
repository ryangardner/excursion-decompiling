/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.pop3;

import com.sun.mail.pop3.POP3Store;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.MethodNotSupportedException;
import javax.mail.Store;

public class DefaultFolder
extends Folder {
    DefaultFolder(POP3Store pOP3Store) {
        super(pOP3Store);
    }

    @Override
    public void appendMessages(Message[] arrmessage) throws MessagingException {
        throw new MethodNotSupportedException("Append not supported");
    }

    @Override
    public void close(boolean bl) throws MessagingException {
        throw new MethodNotSupportedException("close");
    }

    @Override
    public boolean create(int n) throws MessagingException {
        return false;
    }

    @Override
    public boolean delete(boolean bl) throws MessagingException {
        throw new MethodNotSupportedException("delete");
    }

    @Override
    public boolean exists() {
        return true;
    }

    @Override
    public Message[] expunge() throws MessagingException {
        throw new MethodNotSupportedException("expunge");
    }

    @Override
    public Folder getFolder(String string2) throws MessagingException {
        if (!string2.equalsIgnoreCase("INBOX")) throw new MessagingException("only INBOX supported");
        return this.getInbox();
    }

    @Override
    public String getFullName() {
        return "";
    }

    protected Folder getInbox() throws MessagingException {
        return this.getStore().getFolder("INBOX");
    }

    @Override
    public Message getMessage(int n) throws MessagingException {
        throw new MethodNotSupportedException("getMessage");
    }

    @Override
    public int getMessageCount() throws MessagingException {
        return 0;
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public Folder getParent() {
        return null;
    }

    @Override
    public Flags getPermanentFlags() {
        return new Flags();
    }

    @Override
    public char getSeparator() {
        return '/';
    }

    @Override
    public int getType() {
        return 2;
    }

    @Override
    public boolean hasNewMessages() throws MessagingException {
        return false;
    }

    @Override
    public boolean isOpen() {
        return false;
    }

    @Override
    public Folder[] list(String string2) throws MessagingException {
        return new Folder[]{this.getInbox()};
    }

    @Override
    public void open(int n) throws MessagingException {
        throw new MethodNotSupportedException("open");
    }

    @Override
    public boolean renameTo(Folder folder) throws MessagingException {
        throw new MethodNotSupportedException("renameTo");
    }
}

