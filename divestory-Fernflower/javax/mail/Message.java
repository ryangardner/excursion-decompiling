package javax.mail;

import java.io.InvalidObjectException;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.Date;
import javax.mail.search.SearchTerm;

public abstract class Message implements Part {
   protected boolean expunged = false;
   protected Folder folder = null;
   protected int msgnum = 0;
   protected Session session = null;

   protected Message() {
   }

   protected Message(Folder var1, int var2) {
      this.folder = var1;
      this.msgnum = var2;
      this.session = var1.store.session;
   }

   protected Message(Session var1) {
      this.session = var1;
   }

   public abstract void addFrom(Address[] var1) throws MessagingException;

   public void addRecipient(Message.RecipientType var1, Address var2) throws MessagingException {
      this.addRecipients(var1, new Address[]{var2});
   }

   public abstract void addRecipients(Message.RecipientType var1, Address[] var2) throws MessagingException;

   public Address[] getAllRecipients() throws MessagingException {
      Address[] var1 = this.getRecipients(Message.RecipientType.TO);
      Address[] var2 = this.getRecipients(Message.RecipientType.CC);
      Address[] var3 = this.getRecipients(Message.RecipientType.BCC);
      if (var2 == null && var3 == null) {
         return var1;
      } else {
         int var4;
         if (var1 != null) {
            var4 = var1.length;
         } else {
            var4 = 0;
         }

         int var5;
         if (var2 != null) {
            var5 = var2.length;
         } else {
            var5 = 0;
         }

         int var6;
         if (var3 != null) {
            var6 = var3.length;
         } else {
            var6 = 0;
         }

         Address[] var7 = new Address[var4 + var5 + var6];
         if (var1 != null) {
            System.arraycopy(var1, 0, var7, 0, var1.length);
            var4 = var1.length + 0;
         } else {
            var4 = 0;
         }

         var5 = var4;
         if (var2 != null) {
            System.arraycopy(var2, 0, var7, var4, var2.length);
            var5 = var4 + var2.length;
         }

         if (var3 != null) {
            System.arraycopy(var3, 0, var7, var5, var3.length);
            var4 = var3.length;
         }

         return var7;
      }
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

   public abstract Address[] getRecipients(Message.RecipientType var1) throws MessagingException;

   public Address[] getReplyTo() throws MessagingException {
      return this.getFrom();
   }

   public abstract Date getSentDate() throws MessagingException;

   public abstract String getSubject() throws MessagingException;

   public boolean isExpunged() {
      return this.expunged;
   }

   public boolean isSet(Flags.Flag var1) throws MessagingException {
      return this.getFlags().contains(var1);
   }

   public boolean match(SearchTerm var1) throws MessagingException {
      return var1.match(this);
   }

   public abstract Message reply(boolean var1) throws MessagingException;

   public abstract void saveChanges() throws MessagingException;

   protected void setExpunged(boolean var1) {
      this.expunged = var1;
   }

   public void setFlag(Flags.Flag var1, boolean var2) throws MessagingException {
      this.setFlags(new Flags(var1), var2);
   }

   public abstract void setFlags(Flags var1, boolean var2) throws MessagingException;

   public abstract void setFrom() throws MessagingException;

   public abstract void setFrom(Address var1) throws MessagingException;

   protected void setMessageNumber(int var1) {
      this.msgnum = var1;
   }

   public void setRecipient(Message.RecipientType var1, Address var2) throws MessagingException {
      this.setRecipients(var1, new Address[]{var2});
   }

   public abstract void setRecipients(Message.RecipientType var1, Address[] var2) throws MessagingException;

   public void setReplyTo(Address[] var1) throws MessagingException {
      throw new MethodNotSupportedException("setReplyTo not supported");
   }

   public abstract void setSentDate(Date var1) throws MessagingException;

   public abstract void setSubject(String var1) throws MessagingException;

   public static class RecipientType implements Serializable {
      public static final Message.RecipientType BCC = new Message.RecipientType("Bcc");
      public static final Message.RecipientType CC = new Message.RecipientType("Cc");
      public static final Message.RecipientType TO = new Message.RecipientType("To");
      private static final long serialVersionUID = -7479791750606340008L;
      protected String type;

      protected RecipientType(String var1) {
         this.type = var1;
      }

      protected Object readResolve() throws ObjectStreamException {
         if (this.type.equals("To")) {
            return TO;
         } else if (this.type.equals("Cc")) {
            return CC;
         } else if (this.type.equals("Bcc")) {
            return BCC;
         } else {
            StringBuilder var1 = new StringBuilder("Attempt to resolve unknown RecipientType: ");
            var1.append(this.type);
            throw new InvalidObjectException(var1.toString());
         }
      }

      public String toString() {
         return this.type;
      }
   }
}
