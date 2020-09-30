package com.sun.mail.imap;

import com.sun.mail.iap.ByteArray;
import com.sun.mail.iap.ConnectionException;
import com.sun.mail.iap.ProtocolException;
import com.sun.mail.util.FolderClosedIOException;
import com.sun.mail.util.MessageRemovedIOException;
import java.io.IOException;
import java.io.InputStream;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.FolderClosedException;
import javax.mail.MessagingException;

public class IMAPInputStream extends InputStream {
   private static final int slop = 64;
   private int blksize;
   private byte[] buf;
   private int bufcount;
   private int bufpos;
   private int max;
   private IMAPMessage msg;
   private boolean peek;
   private int pos;
   private ByteArray readbuf;
   private String section;

   public IMAPInputStream(IMAPMessage var1, String var2, int var3, boolean var4) {
      this.msg = var1;
      this.section = var2;
      this.max = var3;
      this.peek = var4;
      this.pos = 0;
      this.blksize = var1.getFetchBlockSize();
   }

   private void checkSeen() {
      if (!this.peek) {
         Folder var1;
         boolean var10001;
         try {
            var1 = this.msg.getFolder();
         } catch (MessagingException var3) {
            var10001 = false;
            return;
         }

         if (var1 != null) {
            try {
               if (var1.getMode() != 1 && !this.msg.isSet(Flags.Flag.SEEN)) {
                  this.msg.setFlag(Flags.Flag.SEEN, true);
               }
            } catch (MessagingException var2) {
               var10001 = false;
            }
         }

      }
   }

   private void fill() throws IOException {
      // $FF: Couldn't be decompiled
   }

   private void forceCheckExpunged() throws MessageRemovedIOException, FolderClosedIOException {
      Object var1 = this.msg.getMessageCacheLock();
      synchronized(var1){}

      label327: {
         Throwable var10000;
         boolean var10001;
         label328: {
            label320: {
               ConnectionException var52;
               label319: {
                  FolderClosedException var3;
                  try {
                     try {
                        this.msg.getProtocol().noop();
                        break label320;
                     } catch (ConnectionException var48) {
                        var52 = var48;
                        break label319;
                     } catch (FolderClosedException var49) {
                        var3 = var49;
                     } catch (ProtocolException var50) {
                        break label320;
                     }
                  } catch (Throwable var51) {
                     var10000 = var51;
                     var10001 = false;
                     break label328;
                  }

                  try {
                     FolderClosedIOException var2 = new FolderClosedIOException(var3.getFolder(), var3.getMessage());
                     throw var2;
                  } catch (Throwable var46) {
                     var10000 = var46;
                     var10001 = false;
                     break label328;
                  }
               }

               try {
                  FolderClosedIOException var54 = new FolderClosedIOException(this.msg.getFolder(), var52.getMessage());
                  throw var54;
               } catch (Throwable var45) {
                  var10000 = var45;
                  var10001 = false;
                  break label328;
               }
            }

            label311:
            try {
               break label327;
            } catch (Throwable var47) {
               var10000 = var47;
               var10001 = false;
               break label311;
            }
         }

         while(true) {
            Throwable var53 = var10000;

            try {
               throw var53;
            } catch (Throwable var44) {
               var10000 = var44;
               var10001 = false;
               continue;
            }
         }
      }

      if (this.msg.isExpunged()) {
         throw new MessageRemovedIOException();
      }
   }

   public int available() throws IOException {
      synchronized(this){}

      int var1;
      int var2;
      try {
         var1 = this.bufcount;
         var2 = this.bufpos;
      } finally {
         ;
      }

      return var1 - var2;
   }

   public int read() throws IOException {
      synchronized(this){}

      int var1;
      byte[] var11;
      label84: {
         Throwable var10000;
         label83: {
            boolean var10001;
            label82: {
               int var2;
               try {
                  if (this.bufpos < this.bufcount) {
                     break label82;
                  }

                  this.fill();
                  var1 = this.bufpos;
                  var2 = this.bufcount;
               } catch (Throwable var9) {
                  var10000 = var9;
                  var10001 = false;
                  break label83;
               }

               if (var1 >= var2) {
                  return -1;
               }
            }

            label76:
            try {
               var11 = this.buf;
               var1 = this.bufpos++;
               break label84;
            } catch (Throwable var8) {
               var10000 = var8;
               var10001 = false;
               break label76;
            }
         }

         Throwable var3 = var10000;
         throw var3;
      }

      byte var10 = var11[var1];
      return var10 & 255;
   }

   public int read(byte[] var1) throws IOException {
      return this.read(var1, 0, var1.length);
   }

   public int read(byte[] var1, int var2, int var3) throws IOException {
      synchronized(this){}

      Throwable var10000;
      label157: {
         boolean var10001;
         int var4;
         try {
            var4 = this.bufcount - this.bufpos;
         } catch (Throwable var17) {
            var10000 = var17;
            var10001 = false;
            break label157;
         }

         int var5 = var4;
         if (var4 <= 0) {
            try {
               this.fill();
               var4 = this.bufcount;
               var5 = this.bufpos;
            } catch (Throwable var16) {
               var10000 = var16;
               var10001 = false;
               break label157;
            }

            var4 -= var5;
            var5 = var4;
            if (var4 <= 0) {
               return -1;
            }
         }

         var4 = var3;
         if (var5 < var3) {
            var4 = var5;
         }

         label142:
         try {
            System.arraycopy(this.buf, this.bufpos, var1, var2, var4);
            this.bufpos += var4;
            return var4;
         } catch (Throwable var15) {
            var10000 = var15;
            var10001 = false;
            break label142;
         }
      }

      Throwable var18 = var10000;
      throw var18;
   }
}
