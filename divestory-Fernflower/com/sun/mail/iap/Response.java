package com.sun.mail.iap;

import com.sun.mail.util.ASCIIUtility;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Vector;

public class Response {
   public static final int BAD = 12;
   public static final int BYE = 16;
   public static final int CONTINUATION = 1;
   public static final int NO = 8;
   public static final int OK = 4;
   public static final int SYNTHETIC = 32;
   public static final int TAGGED = 2;
   public static final int TAG_MASK = 3;
   public static final int TYPE_MASK = 28;
   public static final int UNTAGGED = 3;
   private static final int increment = 100;
   protected byte[] buffer = null;
   protected int index;
   protected int pindex;
   protected int size;
   protected String tag = null;
   protected int type = 0;

   public Response(Protocol var1) throws IOException, ProtocolException {
      ByteArray var2 = var1.getResponseBuffer();
      ByteArray var3 = var1.getInputStream().readResponse(var2);
      this.buffer = var3.getBytes();
      this.size = var3.getCount() - 2;
      this.parse();
   }

   public Response(Response var1) {
      this.index = var1.index;
      this.size = var1.size;
      this.buffer = var1.buffer;
      this.type = var1.type;
      this.tag = var1.tag;
   }

   public Response(String var1) {
      byte[] var2 = ASCIIUtility.getBytes(var1);
      this.buffer = var2;
      this.size = var2.length;
      this.parse();
   }

   public static Response byeResponse(Exception var0) {
      StringBuilder var1 = new StringBuilder("* BYE JavaMail Exception: ");
      var1.append(var0.toString());
      Response var2 = new Response(var1.toString().replace('\r', ' ').replace('\n', ' '));
      var2.type |= 32;
      return var2;
   }

   private void parse() {
      this.index = 0;
      byte[] var1 = this.buffer;
      if (var1[0] == 43) {
         this.type |= 1;
         this.index = 0 + 1;
      } else {
         if (var1[0] == 42) {
            this.type |= 3;
            this.index = 0 + 1;
         } else {
            this.type |= 2;
            this.tag = this.readAtom();
         }

         int var2 = this.index;
         String var3 = this.readAtom();
         String var4 = var3;
         if (var3 == null) {
            var4 = "";
         }

         if (var4.equalsIgnoreCase("OK")) {
            this.type |= 4;
         } else if (var4.equalsIgnoreCase("NO")) {
            this.type |= 8;
         } else if (var4.equalsIgnoreCase("BAD")) {
            this.type |= 12;
         } else if (var4.equalsIgnoreCase("BYE")) {
            this.type |= 16;
         } else {
            this.index = var2;
         }

         this.pindex = this.index;
      }
   }

   private Object parseString(boolean var1, boolean var2) {
      this.skipSpaces();
      byte[] var3 = this.buffer;
      int var4 = this.index;
      byte var5 = var3[var4];
      int var6;
      int var10;
      if (var5 == 34) {
         var10 = var4 + 1;
         this.index = var10;
         var4 = var10;

         while(true) {
            var3 = this.buffer;
            var6 = this.index;
            byte var7 = var3[var6];
            if (var7 == 34) {
               this.index = var6 + 1;
               if (var2) {
                  return ASCIIUtility.toString(var3, var10, var4);
               }

               return new ByteArray(this.buffer, var10, var4 - var10);
            }

            if (var7 == 92) {
               this.index = var6 + 1;
            }

            var6 = this.index;
            if (var6 != var4) {
               var3 = this.buffer;
               var3[var4] = (byte)var3[var6];
            }

            ++var4;
            ++this.index;
         }
      } else if (var5 != 123) {
         if (var1) {
            String var9 = this.readAtom();
            return var2 ? var9 : new ByteArray(this.buffer, var4, this.index);
         } else if (var5 != 78 && var5 != 110) {
            return null;
         } else {
            this.index += 3;
            return null;
         }
      } else {
         ++var4;
         this.index = var4;

         while(true) {
            var3 = this.buffer;
            var10 = this.index;
            if (var3[var10] == 125) {
               try {
                  var4 = ASCIIUtility.parseInt(var3, var4, var10);
               } catch (NumberFormatException var8) {
                  return null;
               }

               var6 = this.index + 3;
               var10 = var6 + var4;
               this.index = var10;
               return var2 ? ASCIIUtility.toString(this.buffer, var6, var10) : new ByteArray(this.buffer, var6, var4);
            }

            this.index = var10 + 1;
         }
      }
   }

   public String getRest() {
      this.skipSpaces();
      return ASCIIUtility.toString(this.buffer, this.index, this.size);
   }

   public String getTag() {
      return this.tag;
   }

   public int getType() {
      return this.type;
   }

   public boolean isBAD() {
      return (this.type & 28) == 12;
   }

   public boolean isBYE() {
      return (this.type & 28) == 16;
   }

   public boolean isContinuation() {
      return (this.type & 3) == 1;
   }

   public boolean isNO() {
      return (this.type & 28) == 8;
   }

   public boolean isOK() {
      return (this.type & 28) == 4;
   }

   public boolean isSynthetic() {
      return (this.type & 32) == 32;
   }

   public boolean isTagged() {
      return (this.type & 3) == 2;
   }

   public boolean isUnTagged() {
      return (this.type & 3) == 3;
   }

   public byte peekByte() {
      int var1 = this.index;
      return var1 < this.size ? this.buffer[var1] : 0;
   }

   public String readAtom() {
      return this.readAtom('\u0000');
   }

   public String readAtom(char var1) {
      this.skipSpaces();
      int var2 = this.index;
      if (var2 >= this.size) {
         return null;
      } else {
         while(true) {
            int var3 = this.index;
            if (var3 >= this.size) {
               break;
            }

            byte var4 = this.buffer[var3];
            if (var4 <= 32 || var4 == 40 || var4 == 41 || var4 == 37 || var4 == 42 || var4 == 34 || var4 == 92 || var4 == 127 || var1 != 0 && var4 == var1) {
               break;
            }

            ++this.index;
         }

         return ASCIIUtility.toString(this.buffer, var2, this.index);
      }
   }

   public String readAtomString() {
      return (String)this.parseString(true, true);
   }

   public byte readByte() {
      int var1 = this.index;
      if (var1 < this.size) {
         byte[] var2 = this.buffer;
         this.index = var1 + 1;
         return var2[var1];
      } else {
         return 0;
      }
   }

   public ByteArray readByteArray() {
      if (this.isContinuation()) {
         this.skipSpaces();
         byte[] var1 = this.buffer;
         int var2 = this.index;
         return new ByteArray(var1, var2, this.size - var2);
      } else {
         return (ByteArray)this.parseString(false, false);
      }
   }

   public ByteArrayInputStream readBytes() {
      ByteArray var1 = this.readByteArray();
      return var1 != null ? var1.toByteArrayInputStream() : null;
   }

   public long readLong() {
      this.skipSpaces();
      int var1 = this.index;

      while(true) {
         int var2 = this.index;
         if (var2 >= this.size || !Character.isDigit((char)this.buffer[var2])) {
            var2 = this.index;
            if (var2 > var1) {
               try {
                  long var3 = ASCIIUtility.parseLong(this.buffer, var1, var2);
                  return var3;
               } catch (NumberFormatException var6) {
               }
            }

            return -1L;
         }

         ++this.index;
      }
   }

   public int readNumber() {
      this.skipSpaces();
      int var1 = this.index;

      while(true) {
         int var2 = this.index;
         if (var2 >= this.size || !Character.isDigit((char)this.buffer[var2])) {
            var2 = this.index;
            if (var2 > var1) {
               try {
                  var1 = ASCIIUtility.parseInt(this.buffer, var1, var2);
                  return var1;
               } catch (NumberFormatException var4) {
               }
            }

            return -1;
         }

         ++this.index;
      }
   }

   public String readString() {
      return (String)this.parseString(false, true);
   }

   public String readString(char var1) {
      this.skipSpaces();
      int var2 = this.index;
      if (var2 >= this.size) {
         return null;
      } else {
         while(true) {
            int var3 = this.index;
            if (var3 >= this.size || this.buffer[var3] == var1) {
               return ASCIIUtility.toString(this.buffer, var2, this.index);
            }

            this.index = var3 + 1;
         }
      }
   }

   public String[] readStringList() {
      this.skipSpaces();
      byte[] var1 = this.buffer;
      int var2 = this.index;
      if (var1[var2] != 40) {
         return null;
      } else {
         this.index = var2 + 1;
         Vector var4 = new Vector();

         byte[] var3;
         do {
            var4.addElement(this.readString());
            var3 = this.buffer;
            var2 = this.index++;
         } while(var3[var2] != 41);

         var2 = var4.size();
         if (var2 > 0) {
            String[] var5 = new String[var2];
            var4.copyInto(var5);
            return var5;
         } else {
            return null;
         }
      }
   }

   public void reset() {
      this.index = this.pindex;
   }

   public void skip(int var1) {
      this.index += var1;
   }

   public void skipSpaces() {
      while(true) {
         int var1 = this.index;
         if (var1 >= this.size || this.buffer[var1] != 32) {
            return;
         }

         this.index = var1 + 1;
      }
   }

   public void skipToken() {
      while(true) {
         int var1 = this.index;
         if (var1 >= this.size || this.buffer[var1] == 32) {
            return;
         }

         this.index = var1 + 1;
      }
   }

   public String toString() {
      return ASCIIUtility.toString(this.buffer, 0, this.size);
   }
}
