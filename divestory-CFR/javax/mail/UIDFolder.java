/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail;

import javax.mail.FetchProfile;
import javax.mail.Message;
import javax.mail.MessagingException;

public interface UIDFolder {
    public static final long LASTUID = -1L;

    public Message getMessageByUID(long var1) throws MessagingException;

    public Message[] getMessagesByUID(long var1, long var3) throws MessagingException;

    public Message[] getMessagesByUID(long[] var1) throws MessagingException;

    public long getUID(Message var1) throws MessagingException;

    public long getUIDValidity() throws MessagingException;

    public static class FetchProfileItem
    extends FetchProfile.Item {
        public static final FetchProfileItem UID = new FetchProfileItem("UID");

        protected FetchProfileItem(String string2) {
            super(string2);
        }
    }

}

