package org.apache.commons.net.pop3;

import java.io.IOException;
import java.io.Reader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ListIterator;
import java.util.StringTokenizer;
import org.apache.commons.net.io.DotTerminatedMessageReader;

public class POP3Client extends POP3 {
   private static POP3MessageInfo __parseStatus(String var0) {
      StringTokenizer var1 = new StringTokenizer(var0);
      boolean var2 = var1.hasMoreElements();
      POP3MessageInfo var6 = null;
      if (!var2) {
         return null;
      } else {
         int var3;
         int var4;
         try {
            var3 = Integer.parseInt(var1.nextToken());
            if (!var1.hasMoreElements()) {
               return null;
            }

            var4 = Integer.parseInt(var1.nextToken());
         } catch (NumberFormatException var5) {
            return var6;
         }

         var6 = new POP3MessageInfo(var3, var4);
         return var6;
      }
   }

   private static POP3MessageInfo __parseUID(String var0) {
      StringTokenizer var1 = new StringTokenizer(var0);
      boolean var2 = var1.hasMoreElements();
      POP3MessageInfo var5 = null;
      if (!var2) {
         return null;
      } else {
         int var3;
         String var6;
         try {
            var3 = Integer.parseInt(var1.nextToken());
            if (!var1.hasMoreElements()) {
               return null;
            }

            var6 = var1.nextToken();
         } catch (NumberFormatException var4) {
            return var5;
         }

         var5 = new POP3MessageInfo(var3, var6);
         return var5;
      }
   }

   public boolean capa() throws IOException {
      if (this.sendCommand(12) == 0) {
         this.getAdditionalReply();
         return true;
      } else {
         return false;
      }
   }

   public boolean deleteMessage(int var1) throws IOException {
      int var2 = this.getState();
      boolean var3 = false;
      boolean var4 = var3;
      if (var2 == 1) {
         var4 = var3;
         if (this.sendCommand(6, Integer.toString(var1)) == 0) {
            var4 = true;
         }
      }

      return var4;
   }

   public POP3MessageInfo listMessage(int var1) throws IOException {
      if (this.getState() != 1) {
         return null;
      } else {
         return this.sendCommand(4, Integer.toString(var1)) != 0 ? null : __parseStatus(this._lastReplyLine.substring(3));
      }
   }

   public POP3MessageInfo[] listMessages() throws IOException {
      if (this.getState() != 1) {
         return null;
      } else if (this.sendCommand(4) != 0) {
         return null;
      } else {
         this.getAdditionalReply();
         int var1 = this._replyLines.size() - 2;
         POP3MessageInfo[] var2 = new POP3MessageInfo[var1];
         ListIterator var3 = this._replyLines.listIterator(1);

         for(int var4 = 0; var4 < var1; ++var4) {
            var2[var4] = __parseStatus((String)var3.next());
         }

         return var2;
      }
   }

   public POP3MessageInfo listUniqueIdentifier(int var1) throws IOException {
      if (this.getState() != 1) {
         return null;
      } else {
         return this.sendCommand(11, Integer.toString(var1)) != 0 ? null : __parseUID(this._lastReplyLine.substring(3));
      }
   }

   public POP3MessageInfo[] listUniqueIdentifiers() throws IOException {
      if (this.getState() != 1) {
         return null;
      } else if (this.sendCommand(11) != 0) {
         return null;
      } else {
         this.getAdditionalReply();
         int var1 = this._replyLines.size() - 2;
         POP3MessageInfo[] var2 = new POP3MessageInfo[var1];
         ListIterator var3 = this._replyLines.listIterator(1);

         for(int var4 = 0; var4 < var1; ++var4) {
            var2[var4] = __parseUID((String)var3.next());
         }

         return var2;
      }
   }

   public boolean login(String var1, String var2) throws IOException {
      if (this.getState() != 0) {
         return false;
      } else if (this.sendCommand(0, var1) != 0) {
         return false;
      } else if (this.sendCommand(1, var2) != 0) {
         return false;
      } else {
         this.setState(1);
         return true;
      }
   }

   public boolean login(String var1, String var2, String var3) throws IOException, NoSuchAlgorithmException {
      if (this.getState() != 0) {
         return false;
      } else {
         MessageDigest var4 = MessageDigest.getInstance("MD5");
         StringBuilder var5 = new StringBuilder();
         var5.append(var2);
         var5.append(var3);
         byte[] var9 = var4.digest(var5.toString().getBytes(this.getCharsetName()));
         StringBuilder var8 = new StringBuilder(128);

         for(int var6 = 0; var6 < var9.length; ++var6) {
            int var7 = var9[var6] & 255;
            if (var7 <= 15) {
               var8.append("0");
            }

            var8.append(Integer.toHexString(var7));
         }

         StringBuilder var10 = new StringBuilder(256);
         var10.append(var1);
         var10.append(' ');
         var10.append(var8.toString());
         if (this.sendCommand(9, var10.toString()) != 0) {
            return false;
         } else {
            this.setState(1);
            return true;
         }
      }
   }

   public boolean logout() throws IOException {
      int var1 = this.getState();
      boolean var2 = true;
      if (var1 == 1) {
         this.setState(2);
      }

      this.sendCommand(2);
      if (this._replyCode != 0) {
         var2 = false;
      }

      return var2;
   }

   public boolean noop() throws IOException {
      int var1 = this.getState();
      boolean var2 = false;
      boolean var3 = var2;
      if (var1 == 1) {
         var3 = var2;
         if (this.sendCommand(7) == 0) {
            var3 = true;
         }
      }

      return var3;
   }

   public boolean reset() throws IOException {
      int var1 = this.getState();
      boolean var2 = false;
      boolean var3 = var2;
      if (var1 == 1) {
         var3 = var2;
         if (this.sendCommand(8) == 0) {
            var3 = true;
         }
      }

      return var3;
   }

   public Reader retrieveMessage(int var1) throws IOException {
      if (this.getState() != 1) {
         return null;
      } else {
         return this.sendCommand(5, Integer.toString(var1)) != 0 ? null : new DotTerminatedMessageReader(this._reader);
      }
   }

   public Reader retrieveMessageTop(int var1, int var2) throws IOException {
      if (var2 >= 0 && this.getState() == 1) {
         StringBuilder var3 = new StringBuilder();
         var3.append(Integer.toString(var1));
         var3.append(" ");
         var3.append(Integer.toString(var2));
         return this.sendCommand(10, var3.toString()) != 0 ? null : new DotTerminatedMessageReader(this._reader);
      } else {
         return null;
      }
   }

   public POP3MessageInfo status() throws IOException {
      if (this.getState() != 1) {
         return null;
      } else {
         return this.sendCommand(3) != 0 ? null : __parseStatus(this._lastReplyLine.substring(3));
      }
   }
}
