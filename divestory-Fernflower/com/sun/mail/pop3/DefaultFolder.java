package com.sun.mail.pop3;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.MethodNotSupportedException;

public class DefaultFolder extends Folder {
   DefaultFolder(POP3Store var1) {
      super(var1);
   }

   public void appendMessages(Message[] var1) throws MessagingException {
      throw new MethodNotSupportedException("Append not supported");
   }

   public void close(boolean var1) throws MessagingException {
      throw new MethodNotSupportedException("close");
   }

   public boolean create(int var1) throws MessagingException {
      return false;
   }

   public boolean delete(boolean var1) throws MessagingException {
      throw new MethodNotSupportedException("delete");
   }

   public boolean exists() {
      return true;
   }

   public Message[] expunge() throws MessagingException {
      throw new MethodNotSupportedException("expunge");
   }

   public Folder getFolder(String var1) throws MessagingException {
      if (var1.equalsIgnoreCase("INBOX")) {
         return this.getInbox();
      } else {
         throw new MessagingException("only INBOX supported");
      }
   }

   public String getFullName() {
      return "";
   }

   protected Folder getInbox() throws MessagingException {
      return this.getStore().getFolder("INBOX");
   }

   public Message getMessage(int var1) throws MessagingException {
      throw new MethodNotSupportedException("getMessage");
   }

   public int getMessageCount() throws MessagingException {
      return 0;
   }

   public String getName() {
      return "";
   }

   public Folder getParent() {
      return null;
   }

   public Flags getPermanentFlags() {
      return new Flags();
   }

   public char getSeparator() {
      return '/';
   }

   public int getType() {
      return 2;
   }

   public boolean hasNewMessages() throws MessagingException {
      return false;
   }

   public boolean isOpen() {
      return false;
   }

   public Folder[] list(String var1) throws MessagingException {
      return new Folder[]{this.getInbox()};
   }

   public void open(int var1) throws MessagingException {
      throw new MethodNotSupportedException("open");
   }

   public boolean renameTo(Folder var1) throws MessagingException {
      throw new MethodNotSupportedException("renameTo");
   }
}
