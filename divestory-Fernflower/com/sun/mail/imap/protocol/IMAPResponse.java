package com.sun.mail.imap.protocol;

import com.sun.mail.iap.Protocol;
import com.sun.mail.iap.ProtocolException;
import com.sun.mail.iap.Response;
import com.sun.mail.util.ASCIIUtility;
import java.io.IOException;
import java.util.Vector;

public class IMAPResponse extends Response {
   private String key;
   private int number;

   public IMAPResponse(Protocol var1) throws IOException, ProtocolException {
      super(var1);
      if (this.isUnTagged() && !this.isOK() && !this.isNO() && !this.isBAD() && !this.isBYE()) {
         String var3 = this.readAtom();
         this.key = var3;

         try {
            this.number = Integer.parseInt(var3);
            this.key = this.readAtom();
         } catch (NumberFormatException var2) {
         }
      }

   }

   public IMAPResponse(IMAPResponse var1) {
      super((Response)var1);
      this.key = var1.key;
      this.number = var1.number;
   }

   public static IMAPResponse readResponse(Protocol var0) throws IOException, ProtocolException {
      IMAPResponse var1 = new IMAPResponse(var0);
      Object var2 = var1;
      if (var1.keyEquals("FETCH")) {
         var2 = new FetchResponse(var1);
      }

      return (IMAPResponse)var2;
   }

   public String getKey() {
      return this.key;
   }

   public int getNumber() {
      return this.number;
   }

   public boolean keyEquals(String var1) {
      String var2 = this.key;
      return var2 != null && var2.equalsIgnoreCase(var1);
   }

   public String[] readSimpleList() {
      this.skipSpaces();
      byte var1 = this.buffer[this.index];
      String[] var2 = null;
      if (var1 != 40) {
         return null;
      } else {
         ++this.index;
         Vector var3 = new Vector();

         int var4;
         int var5;
         for(var4 = this.index; this.buffer[this.index] != 41; var4 = var5) {
            var5 = var4;
            if (this.buffer[this.index] == 32) {
               var3.addElement(ASCIIUtility.toString(this.buffer, var4, this.index));
               var5 = this.index + 1;
            }

            ++this.index;
         }

         if (this.index > var4) {
            var3.addElement(ASCIIUtility.toString(this.buffer, var4, this.index));
         }

         ++this.index;
         var5 = var3.size();
         if (var5 > 0) {
            var2 = new String[var5];
            var3.copyInto(var2);
         }

         return var2;
      }
   }
}
