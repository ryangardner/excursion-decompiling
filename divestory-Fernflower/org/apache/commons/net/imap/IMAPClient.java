package org.apache.commons.net.imap;

import java.io.IOException;

public class IMAPClient extends IMAP {
   private static final char DQUOTE = '"';
   private static final String DQUOTE_S = "\"";

   @Deprecated
   public boolean append(String var1) throws IOException {
      return this.append(var1, (String)null, (String)null);
   }

   @Deprecated
   public boolean append(String var1, String var2, String var3) throws IOException {
      String var4 = var1;
      if (var2 != null) {
         StringBuilder var6 = new StringBuilder();
         var6.append(var1);
         var6.append(" ");
         var6.append(var2);
         var4 = var6.toString();
      }

      var1 = var4;
      if (var3 != null) {
         StringBuilder var5;
         if (var3.charAt(0) == '{') {
            var5 = new StringBuilder();
            var5.append(var4);
            var5.append(" ");
            var5.append(var3);
            var1 = var5.toString();
         } else {
            var5 = new StringBuilder();
            var5.append(var4);
            var5.append(" {");
            var5.append(var3);
            var5.append("}");
            var1 = var5.toString();
         }
      }

      return this.doCommand(IMAPCommand.APPEND, var1);
   }

   public boolean append(String var1, String var2, String var3, String var4) throws IOException {
      StringBuilder var7 = new StringBuilder(var1);
      if (var2 != null) {
         var7.append(" ");
         var7.append(var2);
      }

      boolean var5 = false;
      if (var3 != null) {
         var7.append(" ");
         if (var3.charAt(0) == '"') {
            var7.append(var3);
         } else {
            var7.append('"');
            var7.append(var3);
            var7.append('"');
         }
      }

      var7.append(" ");
      if (var4.startsWith("\"") && var4.endsWith("\"")) {
         var7.append(var4);
         return this.doCommand(IMAPCommand.APPEND, var7.toString());
      } else {
         var7.append('{');
         var7.append(var4.length());
         var7.append('}');
         boolean var6 = var5;
         if (IMAPReply.isContinuation(this.sendCommand(IMAPCommand.APPEND, var7.toString()))) {
            var6 = var5;
            if (IMAPReply.isSuccess(this.sendData(var4))) {
               var6 = true;
            }
         }

         return var6;
      }
   }

   public boolean capability() throws IOException {
      return this.doCommand(IMAPCommand.CAPABILITY);
   }

   public boolean check() throws IOException {
      return this.doCommand(IMAPCommand.CHECK);
   }

   public boolean close() throws IOException {
      return this.doCommand(IMAPCommand.CLOSE);
   }

   public boolean copy(String var1, String var2) throws IOException {
      IMAPCommand var3 = IMAPCommand.COPY;
      StringBuilder var4 = new StringBuilder();
      var4.append(var1);
      var4.append(" ");
      var4.append(var2);
      return this.doCommand(var3, var4.toString());
   }

   public boolean create(String var1) throws IOException {
      return this.doCommand(IMAPCommand.CREATE, var1);
   }

   public boolean delete(String var1) throws IOException {
      return this.doCommand(IMAPCommand.DELETE, var1);
   }

   public boolean examine(String var1) throws IOException {
      return this.doCommand(IMAPCommand.EXAMINE, var1);
   }

   public boolean expunge() throws IOException {
      return this.doCommand(IMAPCommand.EXPUNGE);
   }

   public boolean fetch(String var1, String var2) throws IOException {
      IMAPCommand var3 = IMAPCommand.FETCH;
      StringBuilder var4 = new StringBuilder();
      var4.append(var1);
      var4.append(" ");
      var4.append(var2);
      return this.doCommand(var3, var4.toString());
   }

   public boolean list(String var1, String var2) throws IOException {
      IMAPCommand var3 = IMAPCommand.LIST;
      StringBuilder var4 = new StringBuilder();
      var4.append(var1);
      var4.append(" ");
      var4.append(var2);
      return this.doCommand(var3, var4.toString());
   }

   public boolean login(String var1, String var2) throws IOException {
      if (this.getState() != IMAP.IMAPState.NOT_AUTH_STATE) {
         return false;
      } else {
         IMAPCommand var3 = IMAPCommand.LOGIN;
         StringBuilder var4 = new StringBuilder();
         var4.append(var1);
         var4.append(" ");
         var4.append(var2);
         if (!this.doCommand(var3, var4.toString())) {
            return false;
         } else {
            this.setState(IMAP.IMAPState.AUTH_STATE);
            return true;
         }
      }
   }

   public boolean logout() throws IOException {
      return this.doCommand(IMAPCommand.LOGOUT);
   }

   public boolean lsub(String var1, String var2) throws IOException {
      IMAPCommand var3 = IMAPCommand.LSUB;
      StringBuilder var4 = new StringBuilder();
      var4.append(var1);
      var4.append(" ");
      var4.append(var2);
      return this.doCommand(var3, var4.toString());
   }

   public boolean noop() throws IOException {
      return this.doCommand(IMAPCommand.NOOP);
   }

   public boolean rename(String var1, String var2) throws IOException {
      IMAPCommand var3 = IMAPCommand.RENAME;
      StringBuilder var4 = new StringBuilder();
      var4.append(var1);
      var4.append(" ");
      var4.append(var2);
      return this.doCommand(var3, var4.toString());
   }

   public boolean search(String var1) throws IOException {
      return this.search((String)null, var1);
   }

   public boolean search(String var1, String var2) throws IOException {
      String var3 = "";
      if (var1 != null) {
         StringBuilder var5 = new StringBuilder();
         var5.append("");
         var5.append("CHARSET ");
         var5.append(var1);
         var3 = var5.toString();
      }

      StringBuilder var4 = new StringBuilder();
      var4.append(var3);
      var4.append(var2);
      var1 = var4.toString();
      return this.doCommand(IMAPCommand.SEARCH, var1);
   }

   public boolean select(String var1) throws IOException {
      return this.doCommand(IMAPCommand.SELECT, var1);
   }

   public boolean status(String var1, String[] var2) throws IOException {
      if (var2 != null && var2.length >= 1) {
         StringBuilder var3 = new StringBuilder();
         var3.append(var1);
         var3.append(" (");

         for(int var4 = 0; var4 < var2.length; ++var4) {
            if (var4 > 0) {
               var3.append(" ");
            }

            var3.append(var2[var4]);
         }

         var3.append(")");
         return this.doCommand(IMAPCommand.STATUS, var3.toString());
      } else {
         throw new IllegalArgumentException("STATUS command requires at least one data item name");
      }
   }

   public boolean store(String var1, String var2, String var3) throws IOException {
      IMAPCommand var4 = IMAPCommand.STORE;
      StringBuilder var5 = new StringBuilder();
      var5.append(var1);
      var5.append(" ");
      var5.append(var2);
      var5.append(" ");
      var5.append(var3);
      return this.doCommand(var4, var5.toString());
   }

   public boolean subscribe(String var1) throws IOException {
      return this.doCommand(IMAPCommand.SUBSCRIBE, var1);
   }

   public boolean uid(String var1, String var2) throws IOException {
      IMAPCommand var3 = IMAPCommand.UID;
      StringBuilder var4 = new StringBuilder();
      var4.append(var1);
      var4.append(" ");
      var4.append(var2);
      return this.doCommand(var3, var4.toString());
   }

   public boolean unsubscribe(String var1) throws IOException {
      return this.doCommand(IMAPCommand.UNSUBSCRIBE, var1);
   }

   public static enum FETCH_ITEM_NAMES {
      ALL,
      BODY,
      BODYSTRUCTURE,
      ENVELOPE,
      FAST,
      FLAGS,
      FULL,
      INTERNALDATE,
      RFC822,
      UID;

      static {
         IMAPClient.FETCH_ITEM_NAMES var0 = new IMAPClient.FETCH_ITEM_NAMES("UID", 9);
         UID = var0;
      }
   }

   public static enum SEARCH_CRITERIA {
      ALL,
      ANSWERED,
      BCC,
      BEFORE,
      BODY,
      CC,
      DELETED,
      DRAFT,
      FLAGGED,
      FROM,
      HEADER,
      KEYWORD,
      LARGER,
      NEW,
      NOT,
      OLD,
      ON,
      OR,
      RECENT,
      SEEN,
      SENTBEFORE,
      SENTON,
      SENTSINCE,
      SINCE,
      SMALLER,
      SUBJECT,
      TEXT,
      TO,
      UID,
      UNANSWERED,
      UNDELETED,
      UNDRAFT,
      UNFLAGGED,
      UNKEYWORD,
      UNSEEN;

      static {
         IMAPClient.SEARCH_CRITERIA var0 = new IMAPClient.SEARCH_CRITERIA("UNSEEN", 34);
         UNSEEN = var0;
      }
   }

   public static enum STATUS_DATA_ITEMS {
      MESSAGES,
      RECENT,
      UIDNEXT,
      UIDVALIDITY,
      UNSEEN;

      static {
         IMAPClient.STATUS_DATA_ITEMS var0 = new IMAPClient.STATUS_DATA_ITEMS("UNSEEN", 4);
         UNSEEN = var0;
      }
   }
}
