package org.apache.commons.net.imap;

public enum IMAPCommand {
   APPEND(2, 4),
   AUTHENTICATE(1),
   CAPABILITY(0),
   CHECK(0),
   CLOSE(0),
   COPY(2),
   CREATE(1),
   DELETE(1),
   EXAMINE(1),
   EXPUNGE(0),
   FETCH(2),
   LIST(2),
   LOGIN(2),
   LOGOUT(0),
   LSUB(2),
   NOOP(0),
   RENAME(2),
   SEARCH(1, Integer.MAX_VALUE),
   SELECT(1),
   STARTTLS(0),
   STATUS(2),
   STORE(3),
   SUBSCRIBE(1),
   UID,
   UNSUBSCRIBE(1),
   XOAUTH(1);

   private final String imapCommand;
   private final int maxParamCount;
   private final int minParamCount;

   static {
      IMAPCommand var0 = new IMAPCommand("UID", 25, 2, Integer.MAX_VALUE);
      UID = var0;
   }

   private IMAPCommand() {
      this((String)null);
   }

   private IMAPCommand(int var3) {
      this((String)null, var3, var3);
   }

   private IMAPCommand(int var3, int var4) {
      this((String)null, var3, var4);
   }

   private IMAPCommand(String var3) {
      this(var3, 0);
   }

   private IMAPCommand(String var3, int var4) {
      this(var3, var4, var4);
   }

   private IMAPCommand(String var3, int var4, int var5) {
      this.imapCommand = var3;
      this.minParamCount = var4;
      this.maxParamCount = var5;
   }

   public static final String getCommand(IMAPCommand var0) {
      return var0.getIMAPCommand();
   }

   public String getIMAPCommand() {
      String var1 = this.imapCommand;
      if (var1 == null) {
         var1 = this.name();
      }

      return var1;
   }
}
