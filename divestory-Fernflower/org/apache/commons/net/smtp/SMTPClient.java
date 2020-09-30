package org.apache.commons.net.smtp;

import java.io.IOException;
import java.io.Writer;
import org.apache.commons.net.io.DotTerminatedMessageWriter;

public class SMTPClient extends SMTP {
   public SMTPClient() {
   }

   public SMTPClient(String var1) {
      super(var1);
   }

   public boolean addRecipient(String var1) throws IOException {
      StringBuilder var2 = new StringBuilder();
      var2.append("<");
      var2.append(var1);
      var2.append(">");
      return SMTPReply.isPositiveCompletion(this.rcpt(var2.toString()));
   }

   public boolean addRecipient(RelayPath var1) throws IOException {
      return SMTPReply.isPositiveCompletion(this.rcpt(var1.toString()));
   }

   public boolean completePendingCommand() throws IOException {
      return SMTPReply.isPositiveCompletion(this.getReply());
   }

   public String listHelp() throws IOException {
      return SMTPReply.isPositiveCompletion(this.help()) ? this.getReplyString() : null;
   }

   public String listHelp(String var1) throws IOException {
      return SMTPReply.isPositiveCompletion(this.help(var1)) ? this.getReplyString() : null;
   }

   public boolean login() throws IOException {
      String var1 = this.getLocalAddress().getHostName();
      return var1 == null ? false : SMTPReply.isPositiveCompletion(this.helo(var1));
   }

   public boolean login(String var1) throws IOException {
      return SMTPReply.isPositiveCompletion(this.helo(var1));
   }

   public boolean logout() throws IOException {
      return SMTPReply.isPositiveCompletion(this.quit());
   }

   public boolean reset() throws IOException {
      return SMTPReply.isPositiveCompletion(this.rset());
   }

   public Writer sendMessageData() throws IOException {
      return !SMTPReply.isPositiveIntermediate(this.data()) ? null : new DotTerminatedMessageWriter(this._writer);
   }

   public boolean sendNoOp() throws IOException {
      return SMTPReply.isPositiveCompletion(this.noop());
   }

   public boolean sendShortMessageData(String var1) throws IOException {
      Writer var2 = this.sendMessageData();
      if (var2 == null) {
         return false;
      } else {
         var2.write(var1);
         var2.close();
         return this.completePendingCommand();
      }
   }

   public boolean sendSimpleMessage(String var1, String var2, String var3) throws IOException {
      if (!this.setSender(var1)) {
         return false;
      } else {
         return !this.addRecipient(var2) ? false : this.sendShortMessageData(var3);
      }
   }

   public boolean sendSimpleMessage(String var1, String[] var2, String var3) throws IOException {
      if (!this.setSender(var1)) {
         return false;
      } else {
         int var4 = 0;

         boolean var5;
         for(var5 = false; var4 < var2.length; ++var4) {
            if (this.addRecipient(var2[var4])) {
               var5 = true;
            }
         }

         if (!var5) {
            return false;
         } else {
            return this.sendShortMessageData(var3);
         }
      }
   }

   public boolean setSender(String var1) throws IOException {
      StringBuilder var2 = new StringBuilder();
      var2.append("<");
      var2.append(var1);
      var2.append(">");
      return SMTPReply.isPositiveCompletion(this.mail(var2.toString()));
   }

   public boolean setSender(RelayPath var1) throws IOException {
      return SMTPReply.isPositiveCompletion(this.mail(var1.toString()));
   }

   public boolean verify(String var1) throws IOException {
      int var2 = this.vrfy(var1);
      boolean var3;
      if (var2 != 250 && var2 != 251) {
         var3 = false;
      } else {
         var3 = true;
      }

      return var3;
   }
}
