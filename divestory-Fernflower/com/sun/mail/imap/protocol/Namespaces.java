package com.sun.mail.imap.protocol;

import com.sun.mail.iap.ProtocolException;
import com.sun.mail.iap.Response;
import java.util.Vector;

public class Namespaces {
   public Namespaces.Namespace[] otherUsers;
   public Namespaces.Namespace[] personal;
   public Namespaces.Namespace[] shared;

   public Namespaces(Response var1) throws ProtocolException {
      this.personal = this.getNamespaces(var1);
      this.otherUsers = this.getNamespaces(var1);
      this.shared = this.getNamespaces(var1);
   }

   private Namespaces.Namespace[] getNamespaces(Response var1) throws ProtocolException {
      var1.skipSpaces();
      if (var1.peekByte() != 40) {
         String var4 = var1.readAtom();
         if (var4 != null) {
            if (var4.equalsIgnoreCase("NIL")) {
               return null;
            } else {
               StringBuilder var5 = new StringBuilder("Expected NIL, got ");
               var5.append(var4);
               throw new ProtocolException(var5.toString());
            }
         } else {
            throw new ProtocolException("Expected NIL, got null");
         }
      } else {
         Vector var2 = new Vector();
         var1.readByte();

         do {
            var2.addElement(new Namespaces.Namespace(var1));
         } while(var1.peekByte() != 41);

         var1.readByte();
         Namespaces.Namespace[] var3 = new Namespaces.Namespace[var2.size()];
         var2.copyInto(var3);
         return var3;
      }
   }

   public static class Namespace {
      public char delimiter;
      public String prefix;

      public Namespace(Response var1) throws ProtocolException {
         if (var1.readByte() == 40) {
            this.prefix = BASE64MailboxDecoder.decode(var1.readString());
            var1.skipSpaces();
            if (var1.peekByte() == 34) {
               var1.readByte();
               char var2 = (char)var1.readByte();
               this.delimiter = (char)var2;
               if (var2 == '\\') {
                  this.delimiter = (char)((char)var1.readByte());
               }

               if (var1.readByte() != 34) {
                  throw new ProtocolException("Missing '\"' at end of QUOTED_CHAR");
               }
            } else {
               String var3 = var1.readAtom();
               if (var3 == null) {
                  throw new ProtocolException("Expected NIL, got null");
               }

               if (!var3.equalsIgnoreCase("NIL")) {
                  StringBuilder var4 = new StringBuilder("Expected NIL, got ");
                  var4.append(var3);
                  throw new ProtocolException(var4.toString());
               }

               this.delimiter = (char)0;
            }

            if (var1.peekByte() != 41) {
               var1.skipSpaces();
               var1.readString();
               var1.skipSpaces();
               var1.readStringList();
            }

            if (var1.readByte() != 41) {
               throw new ProtocolException("Missing ')' at end of Namespace");
            }
         } else {
            throw new ProtocolException("Missing '(' at start of Namespace");
         }
      }
   }
}
