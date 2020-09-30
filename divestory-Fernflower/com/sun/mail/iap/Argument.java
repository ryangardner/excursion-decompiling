package com.sun.mail.iap;

import com.sun.mail.util.ASCIIUtility;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Vector;

public class Argument {
   protected Vector items = new Vector(1);

   private void astring(byte[] var1, Protocol var2) throws IOException, ProtocolException {
      DataOutputStream var3 = (DataOutputStream)var2.getOutputStream();
      int var4 = var1.length;
      if (var4 > 1024) {
         this.literal(var1, var2);
      } else {
         byte var5 = 0;
         boolean var6;
         if (var4 == 0) {
            var6 = true;
         } else {
            var6 = false;
         }

         int var7 = 0;
         boolean var8 = false;

         while(true) {
            if (var7 >= var4) {
               if (var6) {
                  var3.write(34);
               }

               if (var8) {
                  for(var7 = var5; var7 < var4; ++var7) {
                     byte var12 = var1[var7];
                     if (var12 == 34 || var12 == 92) {
                        var3.write(92);
                     }

                     var3.write(var12);
                  }
               } else {
                  var3.write(var1);
               }

               if (var6) {
                  var3.write(34);
               }

               return;
            }

            byte var9 = var1[var7];
            if (var9 == 0 || var9 == 13 || var9 == 10) {
               break;
            }

            int var10 = var9 & 255;
            if (var10 > 127) {
               break;
            }

            boolean var11;
            label98: {
               if (var9 != 42 && var9 != 37 && var9 != 40 && var9 != 41 && var9 != 123 && var9 != 34 && var9 != 92) {
                  var11 = var8;
                  if (var10 > 32) {
                     break label98;
                  }
               }

               if (var9 != 34 && var9 != 92) {
                  var6 = true;
                  var11 = var8;
               } else {
                  var6 = true;
                  var11 = true;
               }
            }

            ++var7;
            var8 = var11;
         }

         this.literal(var1, var2);
      }
   }

   private void literal(Literal var1, Protocol var2) throws IOException, ProtocolException {
      var1.writeTo(this.startLiteral(var2, var1.size()));
   }

   private void literal(ByteArrayOutputStream var1, Protocol var2) throws IOException, ProtocolException {
      var1.writeTo(this.startLiteral(var2, var1.size()));
   }

   private void literal(byte[] var1, Protocol var2) throws IOException, ProtocolException {
      this.startLiteral(var2, var1.length).write(var1);
   }

   private OutputStream startLiteral(Protocol var1, int var2) throws IOException, ProtocolException {
      DataOutputStream var3 = (DataOutputStream)var1.getOutputStream();
      boolean var4 = var1.supportsNonSyncLiterals();
      var3.write(123);
      var3.writeBytes(Integer.toString(var2));
      if (var4) {
         var3.writeBytes("+}\r\n");
      } else {
         var3.writeBytes("}\r\n");
      }

      var3.flush();
      if (!var4) {
         while(true) {
            Response var5 = var1.readResponse();
            if (var5.isContinuation()) {
               break;
            }

            if (var5.isTagged()) {
               throw new LiteralException(var5);
            }
         }
      }

      return var3;
   }

   public void append(Argument var1) {
      Vector var2 = this.items;
      var2.ensureCapacity(var2.size() + var1.items.size());

      for(int var3 = 0; var3 < var1.items.size(); ++var3) {
         this.items.addElement(var1.items.elementAt(var3));
      }

   }

   public void write(Protocol var1) throws IOException, ProtocolException {
      Vector var2 = this.items;
      int var3 = 0;
      int var4;
      if (var2 != null) {
         var4 = var2.size();
      } else {
         var4 = 0;
      }

      for(DataOutputStream var6 = (DataOutputStream)var1.getOutputStream(); var3 < var4; ++var3) {
         if (var3 > 0) {
            var6.write(32);
         }

         Object var5 = this.items.elementAt(var3);
         if (var5 instanceof Atom) {
            var6.writeBytes(((Atom)var5).string);
         } else if (var5 instanceof Number) {
            var6.writeBytes(((Number)var5).toString());
         } else if (var5 instanceof AString) {
            this.astring(((AString)var5).bytes, var1);
         } else if (var5 instanceof byte[]) {
            this.literal((byte[])var5, var1);
         } else if (var5 instanceof ByteArrayOutputStream) {
            this.literal((ByteArrayOutputStream)var5, var1);
         } else if (var5 instanceof Literal) {
            this.literal((Literal)var5, var1);
         } else if (var5 instanceof Argument) {
            var6.write(40);
            ((Argument)var5).write(var1);
            var6.write(41);
         }
      }

   }

   public void writeArgument(Argument var1) {
      this.items.addElement(var1);
   }

   public void writeAtom(String var1) {
      this.items.addElement(new Atom(var1));
   }

   public void writeBytes(Literal var1) {
      this.items.addElement(var1);
   }

   public void writeBytes(ByteArrayOutputStream var1) {
      this.items.addElement(var1);
   }

   public void writeBytes(byte[] var1) {
      this.items.addElement(var1);
   }

   public void writeNumber(int var1) {
      this.items.addElement(new Integer(var1));
   }

   public void writeNumber(long var1) {
      this.items.addElement(new Long(var1));
   }

   public void writeString(String var1) {
      this.items.addElement(new AString(ASCIIUtility.getBytes(var1)));
   }

   public void writeString(String var1, String var2) throws UnsupportedEncodingException {
      if (var2 == null) {
         this.writeString(var1);
      } else {
         this.items.addElement(new AString(var1.getBytes(var2)));
      }

   }
}
