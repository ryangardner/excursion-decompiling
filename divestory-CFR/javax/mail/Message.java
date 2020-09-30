/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail;

import java.io.InvalidObjectException;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.Date;
import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.MethodNotSupportedException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.SearchTerm;

public abstract class Message
implements Part {
    protected boolean expunged = false;
    protected Folder folder = null;
    protected int msgnum = 0;
    protected Session session = null;

    protected Message() {
    }

    protected Message(Folder folder, int n) {
        this.folder = folder;
        this.msgnum = n;
        this.session = folder.store.session;
    }

    protected Message(Session session) {
        this.session = session;
    }

    public abstract void addFrom(Address[] var1) throws MessagingException;

    public void addRecipient(RecipientType recipientType, Address address) throws MessagingException {
        this.addRecipients(recipientType, new Address[]{address});
    }

    public abstract void addRecipients(RecipientType var1, Address[] var2) throws MessagingException;

    public Address[] getAllRecipients() throws MessagingException {
        Address[] arraddress = this.getRecipients(RecipientType.TO);
        Address[] arraddress2 = this.getRecipients(RecipientType.CC);
        Address[] arraddress3 = this.getRecipients(RecipientType.BCC);
        if (arraddress2 == null && arraddress3 == null) {
            return arraddress;
        }
        int n = arraddress != null ? arraddress.length : 0;
        int n2 = arraddress2 != null ? arraddress2.length : 0;
        int n3 = arraddress3 != null ? arraddress3.length : 0;
        Address[] arraddress4 = new Address[n + n2 + n3];
        if (arraddress != null) {
            System.arraycopy(arraddress, 0, arraddress4, 0, arraddress.length);
            n = arraddress.length + 0;
        } else {
            n = 0;
        }
        n2 = n;
        if (arraddress2 != null) {
            System.arraycopy(arraddress2, 0, arraddress4, n, arraddress2.length);
            n2 = n + arraddress2.length;
        }
        if (arraddress3 == null) return arraddress4;
        System.arraycopy(arraddress3, 0, arraddress4, n2, arraddress3.length);
        n = arraddress3.length;
        return arraddress4;
    }

    public abstract Flags getFlags() throws MessagingException;

    public Folder getFolder() {
        return this.folder;
    }

    public abstract Address[] getFrom() throws MessagingException;

    public int getMessageNumber() {
        return this.msgnum;
    }

    public abstract Date getReceivedDate() throws MessagingException;

    public abstract Address[] getRecipients(RecipientType var1) throws MessagingException;

    public Address[] getReplyTo() throws MessagingException {
        return this.getFrom();
    }

    public abstract Date getSentDate() throws MessagingException;

    public abstract String getSubject() throws MessagingException;

    public boolean isExpunged() {
        return this.expunged;
    }

    public boolean isSet(Flags.Flag flag) throws MessagingException {
        return this.getFlags().contains(flag);
    }

    public boolean match(SearchTerm searchTerm) throws MessagingException {
        return searchTerm.match(this);
    }

    public abstract Message reply(boolean var1) throws MessagingException;

    public abstract void saveChanges() throws MessagingException;

    protected void setExpunged(boolean bl) {
        this.expunged = bl;
    }

    public void setFlag(Flags.Flag flag, boolean bl) throws MessagingException {
        this.setFlags(new Flags(flag), bl);
    }

    public abstract void setFlags(Flags var1, boolean var2) throws MessagingException;

    public abstract void setFrom() throws MessagingException;

    public abstract void setFrom(Address var1) throws MessagingException;

    protected void setMessageNumber(int n) {
        this.msgnum = n;
    }

    public void setRecipient(RecipientType recipientType, Address address) throws MessagingException {
        this.setRecipients(recipientType, new Address[]{address});
    }

    public abstract void setRecipients(RecipientType var1, Address[] var2) throws MessagingException;

    public void setReplyTo(Address[] arraddress) throws MessagingException {
        throw new MethodNotSupportedException("setReplyTo not supported");
    }

    public abstract void setSentDate(Date var1) throws MessagingException;

    public abstract void setSubject(String var1) throws MessagingException;

    public static class RecipientType
    implements Serializable {
        public static final RecipientType BCC;
        public static final RecipientType CC;
        public static final RecipientType TO;
        private static final long serialVersionUID = -7479791750606340008L;
        protected String type;

        static {
            TO = new RecipientType("To");
            CC = new RecipientType("Cc");
            BCC = new RecipientType("Bcc");
        }

        protected RecipientType(String string2) {
            this.type = string2;
        }

        protected Object readResolve() throws ObjectStreamException {
            if (this.type.equals("To")) {
                return TO;
            }
            if (this.type.equals("Cc")) {
                return CC;
            }
            if (this.type.equals("Bcc")) {
                return BCC;
            }
            StringBuilder stringBuilder = new StringBuilder("Attempt to resolve unknown RecipientType: ");
            stringBuilder.append(this.type);
            throw new InvalidObjectException(stringBuilder.toString());
        }

        public String toString() {
            return this.type;
        }
    }

}

