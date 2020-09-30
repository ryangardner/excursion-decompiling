package com.sun.mail.imap.protocol;

import com.sun.mail.iap.Argument;
import com.sun.mail.iap.BadCommandException;
import com.sun.mail.iap.ByteArray;
import com.sun.mail.iap.CommandFailedException;
import com.sun.mail.iap.ConnectionException;
import com.sun.mail.iap.Literal;
import com.sun.mail.iap.ParsingException;
import com.sun.mail.iap.Protocol;
import com.sun.mail.iap.ProtocolException;
import com.sun.mail.iap.Response;
import com.sun.mail.imap.ACL;
import com.sun.mail.imap.AppendUID;
import com.sun.mail.imap.Rights;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;
import javax.mail.Flags;
import javax.mail.Quota;
import javax.mail.internet.MimeUtility;
import javax.mail.search.SearchException;
import javax.mail.search.SearchTerm;

public class IMAPProtocol extends Protocol {
   private static final byte[] CRLF = new byte[]{13, 10};
   private static final byte[] DONE = new byte[]{68, 79, 78, 69, 13, 10};
   private boolean authenticated;
   private List authmechs;
   private ByteArray ba;
   private Map capabilities;
   private boolean connected;
   private String idleTag;
   private String name;
   private boolean rev1;
   private SaslAuthenticator saslAuthenticator;
   private String[] searchCharsets;

   public IMAPProtocol(String var1, String var2, int var3, boolean var4, PrintStream var5, Properties var6, boolean var7) throws IOException, ProtocolException {
      StringBuilder var8 = new StringBuilder("mail.");
      var8.append(var1);
      super(var2, var3, var4, var5, var6, var8.toString(), var7);
      this.connected = false;
      this.rev1 = false;
      this.capabilities = null;
      this.authmechs = null;

      label323: {
         Throwable var10000;
         label324: {
            boolean var10001;
            try {
               this.name = var1;
            } catch (Throwable var38) {
               var10000 = var38;
               var10001 = false;
               break label324;
            }

            if (true) {
               try {
                  this.capability();
               } catch (Throwable var37) {
                  var10000 = var37;
                  var10001 = false;
                  break label324;
               }
            }

            try {
               if (this.hasCapability("IMAP4rev1")) {
                  this.rev1 = true;
               }
            } catch (Throwable var36) {
               var10000 = var36;
               var10001 = false;
               break label324;
            }

            String[] var39;
            try {
               var39 = new String[2];
               this.searchCharsets = var39;
            } catch (Throwable var35) {
               var10000 = var35;
               var10001 = false;
               break label324;
            }

            var39[0] = "UTF-8";

            label304:
            try {
               var39[1] = MimeUtility.mimeCharset(MimeUtility.getDefaultJavaCharset());
               this.connected = true;
               break label323;
            } catch (Throwable var34) {
               var10000 = var34;
               var10001 = false;
               break label304;
            }
         }

         Throwable var40 = var10000;
         if (!this.connected) {
            this.disconnect();
         }

         throw var40;
      }

      if (false) {
         this.disconnect();
      }

   }

   private void copy(String var1, String var2) throws ProtocolException {
      var2 = BASE64MailboxEncoder.encode(var2);
      Argument var3 = new Argument();
      var3.writeAtom(var1);
      var3.writeString(var2);
      this.simpleCommand("COPY", var3);
   }

   private String createFlagList(Flags var1) {
      StringBuffer var2 = new StringBuffer();
      var2.append("(");
      Flags.Flag[] var3 = var1.getSystemFlags();
      boolean var4 = true;

      int var5;
      boolean var7;
      for(var5 = 0; var5 < var3.length; var4 = var7) {
         label54: {
            Flags.Flag var6 = var3[var5];
            String var9;
            if (var6 == Flags.Flag.ANSWERED) {
               var9 = "\\Answered";
            } else if (var6 == Flags.Flag.DELETED) {
               var9 = "\\Deleted";
            } else if (var6 == Flags.Flag.DRAFT) {
               var9 = "\\Draft";
            } else if (var6 == Flags.Flag.FLAGGED) {
               var9 = "\\Flagged";
            } else if (var6 == Flags.Flag.RECENT) {
               var9 = "\\Recent";
            } else {
               var7 = var4;
               if (var6 != Flags.Flag.SEEN) {
                  break label54;
               }

               var9 = "\\Seen";
            }

            if (var4) {
               var4 = false;
            } else {
               var2.append(' ');
            }

            var2.append(var9);
            var7 = var4;
         }

         ++var5;
      }

      String[] var8 = var1.getUserFlags();

      for(var5 = 0; var5 < var8.length; ++var5) {
         if (var4) {
            var4 = false;
         } else {
            var2.append(' ');
         }

         var2.append(var8[var5]);
      }

      var2.append(")");
      return var2.toString();
   }

   private ListInfo[] doList(String var1, String var2, String var3) throws ProtocolException {
      var2 = BASE64MailboxEncoder.encode(var2);
      var3 = BASE64MailboxEncoder.encode(var3);
      Argument var4 = new Argument();
      var4.writeString(var2);
      var4.writeString(var3);
      Response[] var5 = this.command(var1, var4);
      ListInfo[] var11 = (ListInfo[])null;
      Response var6 = var5[var5.length - 1];
      ListInfo[] var9 = var11;
      if (var6.isOK()) {
         Vector var12 = new Vector(1);
         int var7 = 0;

         for(int var8 = var5.length; var7 < var8; ++var7) {
            if (var5[var7] instanceof IMAPResponse) {
               IMAPResponse var10 = (IMAPResponse)var5[var7];
               if (var10.keyEquals(var1)) {
                  var12.addElement(new ListInfo(var10));
                  var5[var7] = null;
               }
            }
         }

         var9 = var11;
         if (var12.size() > 0) {
            var9 = new ListInfo[var12.size()];
            var12.copyInto(var9);
         }
      }

      this.notifyResponseHandlers(var5);
      this.handleResult(var6);
      return var9;
   }

   private Response[] fetch(String var1, String var2, boolean var3) throws ProtocolException {
      StringBuilder var4;
      if (var3) {
         var4 = new StringBuilder("UID FETCH ");
         var4.append(var1);
         var4.append(" (");
         var4.append(var2);
         var4.append(")");
         return this.command(var4.toString(), (Argument)null);
      } else {
         var4 = new StringBuilder("FETCH ");
         var4.append(var1);
         var4.append(" (");
         var4.append(var2);
         var4.append(")");
         return this.command(var4.toString(), (Argument)null);
      }
   }

   private AppendUID getAppendUID(Response var1) {
      if (!var1.isOK()) {
         return null;
      } else {
         byte var2;
         do {
            var2 = var1.readByte();
         } while(var2 > 0 && var2 != 91);

         if (var2 == 0) {
            return null;
         } else {
            return !var1.readAtom().equalsIgnoreCase("APPENDUID") ? null : new AppendUID(var1.readLong(), var1.readLong());
         }
      }
   }

   private int[] issueSearch(String var1, SearchTerm var2, String var3) throws ProtocolException, SearchException, IOException {
      String var4;
      if (var3 == null) {
         var4 = null;
      } else {
         var4 = MimeUtility.javaCharset(var3);
      }

      Argument var11 = SearchSequence.generateSequence(var2, var4);
      var11.writeAtom(var1);
      Response[] var9;
      if (var3 == null) {
         var9 = this.command("SEARCH", var11);
      } else {
         StringBuilder var10 = new StringBuilder("SEARCH CHARSET ");
         var10.append(var3);
         var9 = this.command(var10.toString(), var11);
      }

      Response var14 = var9[var9.length - 1];
      int[] var12 = (int[])null;
      if (var14.isOK()) {
         Vector var15 = new Vector();
         int var5 = var9.length;
         byte var6 = 0;

         int var7;
         for(var7 = 0; var7 < var5; ++var7) {
            if (var9[var7] instanceof IMAPResponse) {
               IMAPResponse var13 = (IMAPResponse)var9[var7];
               if (var13.keyEquals("SEARCH")) {
                  while(true) {
                     int var8 = var13.readNumber();
                     if (var8 == -1) {
                        var9[var7] = null;
                        break;
                     }

                     var15.addElement(new Integer(var8));
                  }
               }
            }
         }

         var5 = var15.size();
         var12 = new int[var5];

         for(var7 = var6; var7 < var5; ++var7) {
            var12[var7] = (Integer)var15.elementAt(var7);
         }
      }

      this.notifyResponseHandlers(var9);
      this.handleResult(var14);
      return var12;
   }

   private Quota parseQuota(Response var1) throws ParsingException {
      Quota var2 = new Quota(var1.readAtomString());
      var1.skipSpaces();
      if (var1.readByte() == 40) {
         Vector var3 = new Vector();

         while(var1.peekByte() != 41) {
            String var4 = var1.readAtom();
            if (var4 != null) {
               var3.addElement(new Quota.Resource(var4, var1.readLong(), var1.readLong()));
            }
         }

         var1.readByte();
         var2.resources = new Quota.Resource[var3.size()];
         var3.copyInto(var2.resources);
         return var2;
      } else {
         throw new ParsingException("parse error in QUOTA");
      }
   }

   private int[] search(String var1, SearchTerm var2) throws ProtocolException, SearchException {
      int[] var10;
      if (SearchSequence.isAscii(var2)) {
         try {
            var10 = this.issueSearch(var1, var2, (String)null);
            return var10;
         } catch (IOException var9) {
         }
      }

      int var4 = 0;

      while(true) {
         String[] var3 = this.searchCharsets;
         if (var4 >= var3.length) {
            throw new SearchException("Search failed");
         }

         if (var3[var4] != null) {
            try {
               var10 = this.issueSearch(var1, var2, var3[var4]);
               return var10;
            } catch (CommandFailedException var5) {
               this.searchCharsets[var4] = null;
            } catch (IOException var6) {
            } catch (ProtocolException var7) {
               throw var7;
            } catch (SearchException var8) {
               throw var8;
            }
         }

         ++var4;
      }
   }

   private void storeFlags(String var1, Flags var2, boolean var3) throws ProtocolException {
      StringBuilder var4;
      Response[] var5;
      if (var3) {
         var4 = new StringBuilder("STORE ");
         var4.append(var1);
         var4.append(" +FLAGS ");
         var4.append(this.createFlagList(var2));
         var5 = this.command(var4.toString(), (Argument)null);
      } else {
         var4 = new StringBuilder("STORE ");
         var4.append(var1);
         var4.append(" -FLAGS ");
         var4.append(this.createFlagList(var2));
         var5 = this.command(var4.toString(), (Argument)null);
      }

      this.notifyResponseHandlers(var5);
      this.handleResult(var5[var5.length - 1]);
   }

   public void append(String var1, Flags var2, Date var3, Literal var4) throws ProtocolException {
      this.appenduid(var1, var2, var3, var4, false);
   }

   public AppendUID appenduid(String var1, Flags var2, Date var3, Literal var4) throws ProtocolException {
      return this.appenduid(var1, var2, var3, var4, true);
   }

   public AppendUID appenduid(String var1, Flags var2, Date var3, Literal var4, boolean var5) throws ProtocolException {
      var1 = BASE64MailboxEncoder.encode(var1);
      Argument var6 = new Argument();
      var6.writeString(var1);
      if (var2 != null) {
         Flags var7 = var2;
         if (var2.contains(Flags.Flag.RECENT)) {
            var7 = new Flags(var2);
            var7.remove(Flags.Flag.RECENT);
         }

         var6.writeAtom(this.createFlagList(var7));
      }

      if (var3 != null) {
         var6.writeString(INTERNALDATE.format(var3));
      }

      var6.writeBytes(var4);
      Response[] var8 = this.command("APPEND", var6);
      this.notifyResponseHandlers(var8);
      this.handleResult(var8[var8.length - 1]);
      return var5 ? this.getAppendUID(var8[var8.length - 1]) : null;
   }

   public void authlogin(String param1, String param2) throws ProtocolException {
      // $FF: Couldn't be decompiled
   }

   public void authplain(String param1, String param2, String param3) throws ProtocolException {
      // $FF: Couldn't be decompiled
   }

   public void capability() throws ProtocolException {
      Response[] var1 = this.command("CAPABILITY", (Argument)null);
      if (var1[var1.length - 1].isOK()) {
         this.capabilities = new HashMap(10);
         this.authmechs = new ArrayList(5);
         int var2 = 0;

         for(int var3 = var1.length; var2 < var3; ++var2) {
            if (var1[var2] instanceof IMAPResponse) {
               IMAPResponse var4 = (IMAPResponse)var1[var2];
               if (var4.keyEquals("CAPABILITY")) {
                  this.parseCapabilities(var4);
               }
            }
         }

      } else {
         throw new ProtocolException(var1[var1.length - 1].toString());
      }
   }

   public void check() throws ProtocolException {
      this.simpleCommand("CHECK", (Argument)null);
   }

   public void close() throws ProtocolException {
      this.simpleCommand("CLOSE", (Argument)null);
   }

   public void copy(int var1, int var2, String var3) throws ProtocolException {
      StringBuilder var4 = new StringBuilder(String.valueOf(String.valueOf(var1)));
      var4.append(":");
      var4.append(String.valueOf(var2));
      this.copy(var4.toString(), var3);
   }

   public void copy(MessageSet[] var1, String var2) throws ProtocolException {
      this.copy(MessageSet.toString(var1), var2);
   }

   public void create(String var1) throws ProtocolException {
      String var2 = BASE64MailboxEncoder.encode(var1);
      Argument var3 = new Argument();
      var3.writeString(var2);
      this.simpleCommand("CREATE", var3);
   }

   public void delete(String var1) throws ProtocolException {
      String var2 = BASE64MailboxEncoder.encode(var1);
      Argument var3 = new Argument();
      var3.writeString(var2);
      this.simpleCommand("DELETE", var3);
   }

   public void deleteACL(String var1, String var2) throws ProtocolException {
      if (this.hasCapability("ACL")) {
         String var3 = BASE64MailboxEncoder.encode(var1);
         Argument var4 = new Argument();
         var4.writeString(var3);
         var4.writeString(var2);
         Response[] var6 = this.command("DELETEACL", var4);
         Response var5 = var6[var6.length - 1];
         this.notifyResponseHandlers(var6);
         this.handleResult(var5);
      } else {
         throw new BadCommandException("ACL not supported");
      }
   }

   public void disconnect() {
      super.disconnect();
      this.authenticated = false;
   }

   public MailboxInfo examine(String var1) throws ProtocolException {
      String var2 = BASE64MailboxEncoder.encode(var1);
      Argument var3 = new Argument();
      var3.writeString(var2);
      Response[] var4 = this.command("EXAMINE", var3);
      MailboxInfo var5 = new MailboxInfo(var4);
      var5.mode = 1;
      this.notifyResponseHandlers(var4);
      this.handleResult(var4[var4.length - 1]);
      return var5;
   }

   public void expunge() throws ProtocolException {
      this.simpleCommand("EXPUNGE", (Argument)null);
   }

   public Response[] fetch(int var1, int var2, String var3) throws ProtocolException {
      StringBuilder var4 = new StringBuilder(String.valueOf(String.valueOf(var1)));
      var4.append(":");
      var4.append(String.valueOf(var2));
      return this.fetch(var4.toString(), var3, false);
   }

   public Response[] fetch(int var1, String var2) throws ProtocolException {
      return this.fetch(String.valueOf(var1), var2, false);
   }

   public Response[] fetch(MessageSet[] var1, String var2) throws ProtocolException {
      return this.fetch(MessageSet.toString(var1), var2, false);
   }

   public BODY fetchBody(int var1, String var2) throws ProtocolException {
      return this.fetchBody(var1, var2, false);
   }

   public BODY fetchBody(int var1, String var2, int var3, int var4) throws ProtocolException {
      return this.fetchBody(var1, var2, var3, var4, false, (ByteArray)null);
   }

   public BODY fetchBody(int var1, String var2, int var3, int var4, ByteArray var5) throws ProtocolException {
      return this.fetchBody(var1, var2, var3, var4, false, var5);
   }

   protected BODY fetchBody(int var1, String var2, int var3, int var4, boolean var5, ByteArray var6) throws ProtocolException {
      this.ba = var6;
      String var10;
      if (var5) {
         var10 = "BODY.PEEK[";
      } else {
         var10 = "BODY[";
      }

      StringBuilder var7 = new StringBuilder(var10);
      var10 = "]<";
      if (var2 == null) {
         var2 = var10;
      } else {
         StringBuilder var8 = new StringBuilder(String.valueOf(var2));
         var8.append("]<");
         var2 = var8.toString();
      }

      var7.append(var2);
      var7.append(String.valueOf(var3));
      var7.append(".");
      var7.append(String.valueOf(var4));
      var7.append(">");
      Response[] var9 = this.fetch(var1, var7.toString());
      this.notifyResponseHandlers(var9);
      Response var11 = var9[var9.length - 1];
      if (var11.isOK()) {
         return (BODY)FetchResponse.getItem(var9, var1, BODY.class);
      } else if (var11.isNO()) {
         return null;
      } else {
         this.handleResult(var11);
         return null;
      }
   }

   protected BODY fetchBody(int var1, String var2, boolean var3) throws ProtocolException {
      String var4 = "]";
      StringBuilder var5;
      StringBuilder var6;
      Response[] var7;
      if (var3) {
         var5 = new StringBuilder("BODY.PEEK[");
         if (var2 != null) {
            var6 = new StringBuilder(String.valueOf(var2));
            var6.append("]");
            var4 = var6.toString();
         }

         var5.append(var4);
         var7 = this.fetch(var1, var5.toString());
      } else {
         var5 = new StringBuilder("BODY[");
         if (var2 != null) {
            var6 = new StringBuilder(String.valueOf(var2));
            var6.append("]");
            var4 = var6.toString();
         }

         var5.append(var4);
         var7 = this.fetch(var1, var5.toString());
      }

      this.notifyResponseHandlers(var7);
      Response var8 = var7[var7.length - 1];
      if (var8.isOK()) {
         return (BODY)FetchResponse.getItem(var7, var1, BODY.class);
      } else if (var8.isNO()) {
         return null;
      } else {
         this.handleResult(var8);
         return null;
      }
   }

   public BODYSTRUCTURE fetchBodyStructure(int var1) throws ProtocolException {
      Response[] var2 = this.fetch(var1, "BODYSTRUCTURE");
      this.notifyResponseHandlers(var2);
      Response var3 = var2[var2.length - 1];
      if (var3.isOK()) {
         return (BODYSTRUCTURE)FetchResponse.getItem(var2, var1, BODYSTRUCTURE.class);
      } else if (var3.isNO()) {
         return null;
      } else {
         this.handleResult(var3);
         return null;
      }
   }

   public Flags fetchFlags(int var1) throws ProtocolException {
      Response[] var2 = this.fetch(var1, "FLAGS");
      int var3 = var2.length;
      int var4 = 0;

      Flags var5;
      Flags var6;
      for(var5 = null; var4 < var3; var5 = var6) {
         var6 = var5;
         if (var2[var4] != null) {
            var6 = var5;
            if (var2[var4] instanceof FetchResponse) {
               if (((FetchResponse)var2[var4]).getNumber() != var1) {
                  var6 = var5;
               } else {
                  var5 = (Flags)((FetchResponse)var2[var4]).getItem(Flags.class);
                  var6 = var5;
                  if (var5 != null) {
                     var2[var4] = null;
                     break;
                  }
               }
            }
         }

         ++var4;
      }

      this.notifyResponseHandlers(var2);
      this.handleResult(var2[var2.length - 1]);
      return var5;
   }

   public RFC822DATA fetchRFC822(int var1, String var2) throws ProtocolException {
      if (var2 == null) {
         var2 = "RFC822";
      } else {
         StringBuilder var3 = new StringBuilder("RFC822.");
         var3.append(var2);
         var2 = var3.toString();
      }

      Response[] var4 = this.fetch(var1, var2);
      this.notifyResponseHandlers(var4);
      Response var5 = var4[var4.length - 1];
      if (var5.isOK()) {
         return (RFC822DATA)FetchResponse.getItem(var4, var1, RFC822DATA.class);
      } else if (var5.isNO()) {
         return null;
      } else {
         this.handleResult(var5);
         return null;
      }
   }

   public UID fetchSequenceNumber(long var1) throws ProtocolException {
      Response[] var3 = this.fetch(String.valueOf(var1), "UID", true);
      int var4 = var3.length;
      int var5 = 0;

      UID var6;
      UID var7;
      for(var6 = null; var5 < var4; var6 = var7) {
         var7 = var6;
         if (var3[var5] != null) {
            if (!(var3[var5] instanceof FetchResponse)) {
               var7 = var6;
            } else {
               var6 = (UID)((FetchResponse)var3[var5]).getItem(UID.class);
               var7 = var6;
               if (var6 != null) {
                  if (var6.uid == var1) {
                     break;
                  }

                  var7 = null;
               }
            }
         }

         ++var5;
      }

      this.notifyResponseHandlers(var3);
      this.handleResult(var3[var3.length - 1]);
      return var6;
   }

   public UID[] fetchSequenceNumbers(long var1, long var3) throws ProtocolException {
      StringBuilder var5 = new StringBuilder(String.valueOf(String.valueOf(var1)));
      var5.append(":");
      String var6;
      if (var3 == -1L) {
         var6 = "*";
      } else {
         var6 = String.valueOf(var3);
      }

      var5.append(var6);
      Response[] var10 = this.fetch(var5.toString(), "UID", true);
      Vector var12 = new Vector();
      int var7 = 0;

      for(int var8 = var10.length; var7 < var8; ++var7) {
         if (var10[var7] != null && var10[var7] instanceof FetchResponse) {
            UID var9 = (UID)((FetchResponse)var10[var7]).getItem(UID.class);
            if (var9 != null) {
               var12.addElement(var9);
            }
         }
      }

      this.notifyResponseHandlers(var10);
      this.handleResult(var10[var10.length - 1]);
      UID[] var11 = new UID[var12.size()];
      var12.copyInto(var11);
      return var11;
   }

   public UID[] fetchSequenceNumbers(long[] var1) throws ProtocolException {
      StringBuffer var2 = new StringBuffer();
      byte var3 = 0;

      int var4;
      for(var4 = 0; var4 < var1.length; ++var4) {
         if (var4 > 0) {
            var2.append(",");
         }

         var2.append(String.valueOf(var1[var4]));
      }

      Response[] var5 = this.fetch(var2.toString(), "UID", true);
      Vector var7 = new Vector();
      int var6 = var5.length;

      for(var4 = var3; var4 < var6; ++var4) {
         if (var5[var4] != null && var5[var4] instanceof FetchResponse) {
            UID var8 = (UID)((FetchResponse)var5[var4]).getItem(UID.class);
            if (var8 != null) {
               var7.addElement(var8);
            }
         }
      }

      this.notifyResponseHandlers(var5);
      this.handleResult(var5[var5.length - 1]);
      UID[] var9 = new UID[var7.size()];
      var7.copyInto(var9);
      return var9;
   }

   public UID fetchUID(int var1) throws ProtocolException {
      Response[] var2 = this.fetch(var1, "UID");
      this.notifyResponseHandlers(var2);
      Response var3 = var2[var2.length - 1];
      if (var3.isOK()) {
         return (UID)FetchResponse.getItem(var2, var1, UID.class);
      } else if (var3.isNO()) {
         return null;
      } else {
         this.handleResult(var3);
         return null;
      }
   }

   public ACL[] getACL(String var1) throws ProtocolException {
      if (!this.hasCapability("ACL")) {
         throw new BadCommandException("ACL not supported");
      } else {
         var1 = BASE64MailboxEncoder.encode(var1);
         Argument var2 = new Argument();
         var2.writeString(var1);
         Response[] var10 = this.command("GETACL", var2);
         Response var3 = var10[var10.length - 1];
         Vector var9 = new Vector();
         if (var3.isOK()) {
            int var4 = 0;

            for(int var5 = var10.length; var4 < var5; ++var4) {
               if (var10[var4] instanceof IMAPResponse) {
                  IMAPResponse var6 = (IMAPResponse)var10[var4];
                  if (var6.keyEquals("ACL")) {
                     var6.readAtomString();

                     while(true) {
                        String var7 = var6.readAtomString();
                        if (var7 == null) {
                           break;
                        }

                        String var8 = var6.readAtomString();
                        if (var8 == null) {
                           break;
                        }

                        var9.addElement(new ACL(var7, new Rights(var8)));
                     }

                     var10[var4] = null;
                  }
               }
            }
         }

         this.notifyResponseHandlers(var10);
         this.handleResult(var3);
         ACL[] var11 = new ACL[var9.size()];
         var9.copyInto(var11);
         return var11;
      }
   }

   public Map getCapabilities() {
      return this.capabilities;
   }

   OutputStream getIMAPOutputStream() {
      return this.getOutputStream();
   }

   public Quota[] getQuota(String var1) throws ProtocolException {
      if (!this.hasCapability("QUOTA")) {
         throw new BadCommandException("QUOTA not supported");
      } else {
         Argument var2 = new Argument();
         var2.writeString(var1);
         Response[] var3 = this.command("GETQUOTA", var2);
         Vector var7 = new Vector();
         Response var4 = var3[var3.length - 1];
         if (var4.isOK()) {
            int var5 = 0;

            for(int var6 = var3.length; var5 < var6; ++var5) {
               if (var3[var5] instanceof IMAPResponse) {
                  IMAPResponse var8 = (IMAPResponse)var3[var5];
                  if (var8.keyEquals("QUOTA")) {
                     var7.addElement(this.parseQuota(var8));
                     var3[var5] = null;
                  }
               }
            }
         }

         this.notifyResponseHandlers(var3);
         this.handleResult(var4);
         Quota[] var9 = new Quota[var7.size()];
         var7.copyInto(var9);
         return var9;
      }
   }

   public Quota[] getQuotaRoot(String var1) throws ProtocolException {
      if (!this.hasCapability("QUOTA")) {
         throw new BadCommandException("GETQUOTAROOT not supported");
      } else {
         var1 = BASE64MailboxEncoder.encode(var1);
         Argument var2 = new Argument();
         var2.writeString(var1);
         Response[] var12 = this.command("GETQUOTAROOT", var2);
         Response var3 = var12[var12.length - 1];
         Hashtable var10 = new Hashtable();
         boolean var4 = var3.isOK();
         byte var5 = 0;
         int var7;
         if (var4) {
            int var6 = var12.length;

            for(var7 = 0; var7 < var6; ++var7) {
               if (var12[var7] instanceof IMAPResponse) {
                  IMAPResponse var8 = (IMAPResponse)var12[var7];
                  if (!var8.keyEquals("QUOTAROOT")) {
                     if (var8.keyEquals("QUOTA")) {
                        Quota var16 = this.parseQuota(var8);
                        Quota var14 = (Quota)var10.get(var16.quotaRoot);
                        if (var14 != null) {
                           Quota.Resource[] var15 = var14.resources;
                        }

                        var10.put(var16.quotaRoot, var16);
                        var12[var7] = null;
                     }
                  } else {
                     var8.readAtomString();

                     while(true) {
                        String var9 = var8.readAtomString();
                        if (var9 == null) {
                           var12[var7] = null;
                           break;
                        }

                        var10.put(var9, new Quota(var9));
                     }
                  }
               }
            }
         }

         this.notifyResponseHandlers(var12);
         this.handleResult(var3);
         Quota[] var13 = new Quota[var10.size()];
         Enumeration var11 = var10.elements();

         for(var7 = var5; var11.hasMoreElements(); ++var7) {
            var13[var7] = (Quota)var11.nextElement();
         }

         return var13;
      }
   }

   protected ByteArray getResponseBuffer() {
      ByteArray var1 = this.ba;
      this.ba = null;
      return var1;
   }

   public boolean hasCapability(String var1) {
      return this.capabilities.containsKey(var1.toUpperCase(Locale.ENGLISH));
   }

   public void idleAbort() throws ProtocolException {
      OutputStream var1 = this.getOutputStream();

      try {
         var1.write(DONE);
         var1.flush();
      } catch (IOException var2) {
      }

   }

   public void idleStart() throws ProtocolException {
      // $FF: Couldn't be decompiled
   }

   public boolean isAuthenticated() {
      return this.authenticated;
   }

   public boolean isREV1() {
      return this.rev1;
   }

   public ListInfo[] list(String var1, String var2) throws ProtocolException {
      return this.doList("LIST", var1, var2);
   }

   public Rights[] listRights(String var1, String var2) throws ProtocolException {
      if (!this.hasCapability("ACL")) {
         throw new BadCommandException("ACL not supported");
      } else {
         var1 = BASE64MailboxEncoder.encode(var1);
         Argument var3 = new Argument();
         var3.writeString(var1);
         var3.writeString(var2);
         Response[] var4 = this.command("LISTRIGHTS", var3);
         Response var11 = var4[var4.length - 1];
         Vector var8 = new Vector();
         if (var11.isOK()) {
            int var5 = 0;

            for(int var6 = var4.length; var5 < var6; ++var5) {
               if (var4[var5] instanceof IMAPResponse) {
                  IMAPResponse var9 = (IMAPResponse)var4[var5];
                  if (var9.keyEquals("LISTRIGHTS")) {
                     var9.readAtomString();
                     var9.readAtomString();

                     while(true) {
                        String var7 = var9.readAtomString();
                        if (var7 == null) {
                           var4[var5] = null;
                           break;
                        }

                        var8.addElement(new Rights(var7));
                     }
                  }
               }
            }
         }

         this.notifyResponseHandlers(var4);
         this.handleResult(var11);
         Rights[] var10 = new Rights[var8.size()];
         var8.copyInto(var10);
         return var10;
      }
   }

   public void login(String var1, String var2) throws ProtocolException {
      Argument var3 = new Argument();
      var3.writeString(var1);
      var3.writeString(var2);
      Response[] var4 = this.command("LOGIN", var3);
      this.notifyResponseHandlers(var4);
      this.handleResult(var4[var4.length - 1]);
      this.setCapabilities(var4[var4.length - 1]);
      this.authenticated = true;
   }

   public void logout() throws ProtocolException {
      Response[] var1 = this.command("LOGOUT", (Argument)null);
      this.authenticated = false;
      this.notifyResponseHandlers(var1);
      this.disconnect();
   }

   public ListInfo[] lsub(String var1, String var2) throws ProtocolException {
      return this.doList("LSUB", var1, var2);
   }

   public Rights myRights(String var1) throws ProtocolException {
      if (!this.hasCapability("ACL")) {
         throw new BadCommandException("ACL not supported");
      } else {
         var1 = BASE64MailboxEncoder.encode(var1);
         Argument var2 = new Argument();
         var2.writeString(var1);
         Response[] var3 = this.command("MYRIGHTS", var2);
         Response var4 = var3[var3.length - 1];
         boolean var5 = var4.isOK();
         Rights var9 = null;
         if (var5) {
            int var6 = 0;
            int var7 = var3.length;

            Rights var10;
            for(var9 = null; var6 < var7; var9 = var10) {
               if (!(var3[var6] instanceof IMAPResponse)) {
                  var10 = var9;
               } else {
                  IMAPResponse var8 = (IMAPResponse)var3[var6];
                  var10 = var9;
                  if (var8.keyEquals("MYRIGHTS")) {
                     var8.readAtomString();
                     String var11 = var8.readAtomString();
                     var10 = var9;
                     if (var9 == null) {
                        var10 = new Rights(var11);
                     }

                     var3[var6] = null;
                  }
               }

               ++var6;
            }
         }

         this.notifyResponseHandlers(var3);
         this.handleResult(var4);
         return var9;
      }
   }

   public Namespaces namespace() throws ProtocolException {
      if (!this.hasCapability("NAMESPACE")) {
         throw new BadCommandException("NAMESPACE not supported");
      } else {
         Namespaces var1 = null;
         Response[] var2 = this.command("NAMESPACE", (Argument)null);
         Response var3 = var2[var2.length - 1];
         if (var3.isOK()) {
            int var4 = 0;
            int var5 = var2.length;

            Namespaces var6;
            for(var1 = null; var4 < var5; var1 = var6) {
               if (!(var2[var4] instanceof IMAPResponse)) {
                  var6 = var1;
               } else {
                  IMAPResponse var7 = (IMAPResponse)var2[var4];
                  var6 = var1;
                  if (var7.keyEquals("NAMESPACE")) {
                     var6 = var1;
                     if (var1 == null) {
                        var6 = new Namespaces(var7);
                     }

                     var2[var4] = null;
                  }
               }

               ++var4;
            }
         }

         this.notifyResponseHandlers(var2);
         this.handleResult(var3);
         return var1;
      }
   }

   public void noop() throws ProtocolException {
      if (this.debug) {
         this.out.println("IMAP DEBUG: IMAPProtocol noop");
      }

      this.simpleCommand("NOOP", (Argument)null);
   }

   protected void parseCapabilities(Response var1) {
      while(true) {
         String var2 = var1.readAtom(']');
         if (var2 != null) {
            if (var2.length() != 0) {
               this.capabilities.put(var2.toUpperCase(Locale.ENGLISH), var2);
               if (!var2.regionMatches(true, 0, "AUTH=", 0, 5)) {
                  continue;
               }

               this.authmechs.add(var2.substring(5));
               if (this.debug) {
                  PrintStream var3 = this.out;
                  StringBuilder var4 = new StringBuilder("IMAP DEBUG: AUTH: ");
                  var4.append(var2.substring(5));
                  var3.println(var4.toString());
               }
               continue;
            }

            if (var1.peekByte() != 93) {
               var1.skipToken();
               continue;
            }
         }

         return;
      }
   }

   public BODY peekBody(int var1, String var2) throws ProtocolException {
      return this.fetchBody(var1, var2, true);
   }

   public BODY peekBody(int var1, String var2, int var3, int var4) throws ProtocolException {
      return this.fetchBody(var1, var2, var3, var4, true, (ByteArray)null);
   }

   public BODY peekBody(int var1, String var2, int var3, int var4, ByteArray var5) throws ProtocolException {
      return this.fetchBody(var1, var2, var3, var4, true, var5);
   }

   protected void processGreeting(Response var1) throws ProtocolException {
      super.processGreeting(var1);
      if (var1.isOK()) {
         this.setCapabilities(var1);
      } else if (((IMAPResponse)var1).keyEquals("PREAUTH")) {
         this.authenticated = true;
         this.setCapabilities(var1);
      } else {
         throw new ConnectionException(this, var1);
      }
   }

   public boolean processIdleResponse(Response var1) throws ProtocolException {
      this.notifyResponseHandlers(new Response[]{var1});
      boolean var2 = var1.isBYE();
      boolean var3 = var2;
      if (var1.isTagged()) {
         var3 = var2;
         if (var1.getTag().equals(this.idleTag)) {
            var3 = true;
         }
      }

      if (var3) {
         this.idleTag = null;
      }

      this.handleResult(var1);
      return var3 ^ true;
   }

   public void proxyauth(String var1) throws ProtocolException {
      Argument var2 = new Argument();
      var2.writeString(var1);
      this.simpleCommand("PROXYAUTH", var2);
   }

   public Response readIdleResponse() {
      // $FF: Couldn't be decompiled
   }

   public Response readResponse() throws IOException, ProtocolException {
      return IMAPResponse.readResponse(this);
   }

   public void rename(String var1, String var2) throws ProtocolException {
      var1 = BASE64MailboxEncoder.encode(var1);
      var2 = BASE64MailboxEncoder.encode(var2);
      Argument var3 = new Argument();
      var3.writeString(var1);
      var3.writeString(var2);
      this.simpleCommand("RENAME", var3);
   }

   public void sasllogin(String[] var1, String var2, String var3, String var4, String var5) throws ProtocolException {
      SaslAuthenticator var6 = this.saslAuthenticator;
      int var7 = 0;
      if (var6 == null) {
         label74: {
            Exception var10000;
            label73: {
               Constructor var8;
               String var9;
               Properties var10;
               boolean var10001;
               Boolean var18;
               label63: {
                  try {
                     var8 = Class.forName("com.sun.mail.imap.protocol.IMAPSaslAuthenticator").getConstructor(IMAPProtocol.class, String.class, Properties.class, Boolean.TYPE, PrintStream.class, String.class);
                     var9 = this.name;
                     var10 = this.props;
                     if (this.debug) {
                        var18 = Boolean.TRUE;
                        break label63;
                     }
                  } catch (Exception var13) {
                     var10000 = var13;
                     var10001 = false;
                     break label73;
                  }

                  try {
                     var18 = Boolean.FALSE;
                  } catch (Exception var12) {
                     var10000 = var12;
                     var10001 = false;
                     break label73;
                  }
               }

               try {
                  this.saslAuthenticator = (SaslAuthenticator)var8.newInstance(this, var9, var10, var18, this.out, this.host);
                  break label74;
               } catch (Exception var11) {
                  var10000 = var11;
                  var10001 = false;
               }
            }

            Exception var14 = var10000;
            if (this.debug) {
               PrintStream var17 = this.out;
               StringBuilder var16 = new StringBuilder("IMAP DEBUG: Can't load SASL authenticator: ");
               var16.append(var14);
               var17.println(var16.toString());
            }

            return;
         }
      }

      Object var15;
      if (var1 != null && var1.length > 0) {
         ArrayList var19;
         for(var19 = new ArrayList(var1.length); var7 < var1.length; ++var7) {
            if (this.authmechs.contains(var1[var7])) {
               var19.add(var1[var7]);
            }
         }

         var15 = var19;
      } else {
         var15 = this.authmechs;
      }

      var1 = (String[])((List)var15).toArray(new String[((List)var15).size()]);
      if (this.saslAuthenticator.authenticate(var1, var2, var3, var4, var5)) {
         this.authenticated = true;
      }

   }

   public int[] search(SearchTerm var1) throws ProtocolException, SearchException {
      return this.search("ALL", var1);
   }

   public int[] search(MessageSet[] var1, SearchTerm var2) throws ProtocolException, SearchException {
      return this.search(MessageSet.toString(var1), var2);
   }

   public MailboxInfo select(String var1) throws ProtocolException {
      var1 = BASE64MailboxEncoder.encode(var1);
      Argument var2 = new Argument();
      var2.writeString(var1);
      Response[] var4 = this.command("SELECT", var2);
      MailboxInfo var3 = new MailboxInfo(var4);
      this.notifyResponseHandlers(var4);
      Response var5 = var4[var4.length - 1];
      if (var5.isOK()) {
         if (var5.toString().indexOf("READ-ONLY") != -1) {
            var3.mode = 1;
         } else {
            var3.mode = 2;
         }
      }

      this.handleResult(var5);
      return var3;
   }

   public void setACL(String var1, char var2, ACL var3) throws ProtocolException {
      if (!this.hasCapability("ACL")) {
         throw new BadCommandException("ACL not supported");
      } else {
         Argument var4;
         label14: {
            var1 = BASE64MailboxEncoder.encode(var1);
            var4 = new Argument();
            var4.writeString(var1);
            var4.writeString(var3.getName());
            String var7 = var3.getRights().toString();
            if (var2 != '+') {
               var1 = var7;
               if (var2 != '-') {
                  break label14;
               }
            }

            StringBuilder var5 = new StringBuilder(String.valueOf(var2));
            var5.append(var7);
            var1 = var5.toString();
         }

         var4.writeString(var1);
         Response[] var8 = this.command("SETACL", var4);
         Response var6 = var8[var8.length - 1];
         this.notifyResponseHandlers(var8);
         this.handleResult(var6);
      }
   }

   protected void setCapabilities(Response var1) {
      byte var2;
      do {
         var2 = var1.readByte();
      } while(var2 > 0 && var2 != 91);

      if (var2 != 0) {
         if (var1.readAtom().equalsIgnoreCase("CAPABILITY")) {
            this.capabilities = new HashMap(10);
            this.authmechs = new ArrayList(5);
            this.parseCapabilities(var1);
         }
      }
   }

   public void setQuota(Quota var1) throws ProtocolException {
      if (!this.hasCapability("QUOTA")) {
         throw new BadCommandException("QUOTA not supported");
      } else {
         Argument var2 = new Argument();
         var2.writeString(var1.quotaRoot);
         Argument var3 = new Argument();
         if (var1.resources != null) {
            for(int var4 = 0; var4 < var1.resources.length; ++var4) {
               var3.writeAtom(var1.resources[var4].name);
               var3.writeNumber(var1.resources[var4].limit);
            }
         }

         var2.writeArgument(var3);
         Response[] var5 = this.command("SETQUOTA", var2);
         Response var6 = var5[var5.length - 1];
         this.notifyResponseHandlers(var5);
         this.handleResult(var6);
      }
   }

   public void startTLS() throws ProtocolException {
      try {
         super.startTLS("STARTTLS");
      } catch (ProtocolException var2) {
         throw var2;
      } catch (Exception var3) {
         this.notifyResponseHandlers(new Response[]{Response.byeResponse(var3)});
         this.disconnect();
      }

   }

   public Status status(String var1, String[] var2) throws ProtocolException {
      if (!this.isREV1() && !this.hasCapability("IMAP4SUNVERSION")) {
         throw new BadCommandException("STATUS not supported");
      } else {
         var1 = BASE64MailboxEncoder.encode(var1);
         Argument var3 = new Argument();
         var3.writeString(var1);
         Argument var4 = new Argument();
         String[] var10 = var2;
         if (var2 == null) {
            var10 = Status.standardItems;
         }

         int var5 = var10.length;
         byte var6 = 0;

         int var7;
         for(var7 = 0; var7 < var5; ++var7) {
            var4.writeAtom(var10[var7]);
         }

         var3.writeArgument(var4);
         Response[] var13 = this.command("STATUS", var3);
         Response var14 = var13[var13.length - 1];
         boolean var8 = var14.isOK();
         Status var12 = null;
         if (var8) {
            var5 = var13.length;
            var12 = null;

            Status var11;
            for(var7 = var6; var7 < var5; var12 = var11) {
               if (!(var13[var7] instanceof IMAPResponse)) {
                  var11 = var12;
               } else {
                  IMAPResponse var9 = (IMAPResponse)var13[var7];
                  var11 = var12;
                  if (var9.keyEquals("STATUS")) {
                     if (var12 == null) {
                        var12 = new Status(var9);
                     } else {
                        Status.add(var12, new Status(var9));
                     }

                     var13[var7] = null;
                     var11 = var12;
                  }
               }

               ++var7;
            }
         }

         this.notifyResponseHandlers(var13);
         this.handleResult(var14);
         return var12;
      }
   }

   public void storeFlags(int var1, int var2, Flags var3, boolean var4) throws ProtocolException {
      StringBuilder var5 = new StringBuilder(String.valueOf(String.valueOf(var1)));
      var5.append(":");
      var5.append(String.valueOf(var2));
      this.storeFlags(var5.toString(), var3, var4);
   }

   public void storeFlags(int var1, Flags var2, boolean var3) throws ProtocolException {
      this.storeFlags(String.valueOf(var1), var2, var3);
   }

   public void storeFlags(MessageSet[] var1, Flags var2, boolean var3) throws ProtocolException {
      this.storeFlags(MessageSet.toString(var1), var2, var3);
   }

   public void subscribe(String var1) throws ProtocolException {
      Argument var2 = new Argument();
      var2.writeString(BASE64MailboxEncoder.encode(var1));
      this.simpleCommand("SUBSCRIBE", var2);
   }

   protected boolean supportsNonSyncLiterals() {
      return this.hasCapability("LITERAL+");
   }

   public void uidexpunge(UIDSet[] var1) throws ProtocolException {
      if (this.hasCapability("UIDPLUS")) {
         StringBuilder var2 = new StringBuilder("UID EXPUNGE ");
         var2.append(UIDSet.toString(var1));
         this.simpleCommand(var2.toString(), (Argument)null);
      } else {
         throw new BadCommandException("UID EXPUNGE not supported");
      }
   }

   public void unsubscribe(String var1) throws ProtocolException {
      Argument var2 = new Argument();
      var2.writeString(BASE64MailboxEncoder.encode(var1));
      this.simpleCommand("UNSUBSCRIBE", var2);
   }
}
