package com.sun.mail.imap.protocol;

import com.sun.mail.iap.ParsingException;
import com.sun.mail.iap.Protocol;
import com.sun.mail.iap.ProtocolException;
import com.sun.mail.iap.Response;
import java.io.IOException;
import java.util.Vector;

public class FetchResponse extends IMAPResponse {
   private static final char[] HEADER = new char[]{'.', 'H', 'E', 'A', 'D', 'E', 'R'};
   private static final char[] TEXT = new char[]{'.', 'T', 'E', 'X', 'T'};
   private Item[] items;

   public FetchResponse(Protocol var1) throws IOException, ProtocolException {
      super(var1);
      this.parse();
   }

   public FetchResponse(IMAPResponse var1) throws IOException, ProtocolException {
      super(var1);
      this.parse();
   }

   public static Item getItem(Response[] var0, int var1, Class var2) {
      if (var0 == null) {
         return null;
      } else {
         for(int var3 = 0; var3 < var0.length; ++var3) {
            if (var0[var3] != null && var0[var3] instanceof FetchResponse && ((FetchResponse)var0[var3]).getNumber() == var1) {
               FetchResponse var4 = (FetchResponse)var0[var3];
               int var5 = 0;

               while(true) {
                  Item[] var6 = var4.items;
                  if (var5 >= var6.length) {
                     break;
                  }

                  if (var2.isInstance(var6[var5])) {
                     return var4.items[var5];
                  }

                  ++var5;
               }
            }
         }

         return null;
      }
   }

   private boolean match(char[] var1) {
      int var2 = var1.length;
      int var3 = this.index;

      for(int var4 = 0; var4 < var2; ++var3) {
         if (Character.toUpperCase((char)this.buffer[var3]) != var1[var4]) {
            return false;
         }

         ++var4;
      }

      return true;
   }

   private void parse() throws ParsingException {
      this.skipSpaces();
      StringBuilder var2;
      if (this.buffer[this.index] == 40) {
         Vector var1 = new Vector();
         Object var4 = null;

         do {
            ++this.index;
            if (this.index >= this.size) {
               var2 = new StringBuilder("error in FETCH parsing, ran off end of buffer, size ");
               var2.append(this.size);
               throw new ParsingException(var2.toString());
            }

            byte var3 = this.buffer[this.index];
            if (var3 != 66) {
               if (var3 != 73) {
                  if (var3 != 82) {
                     if (var3 != 85) {
                        if (var3 != 69) {
                           if (var3 == 70 && this.match(FLAGS.name)) {
                              this.index += FLAGS.name.length;
                              var4 = new FLAGS(this);
                           }
                        } else if (this.match(ENVELOPE.name)) {
                           this.index += ENVELOPE.name.length;
                           var4 = new ENVELOPE(this);
                        }
                     } else if (this.match(UID.name)) {
                        this.index += UID.name.length;
                        var4 = new UID(this);
                     }
                  } else if (this.match(RFC822SIZE.name)) {
                     this.index += RFC822SIZE.name.length;
                     var4 = new RFC822SIZE(this);
                  } else if (this.match(RFC822DATA.name)) {
                     this.index += RFC822DATA.name.length;
                     if (this.match(HEADER)) {
                        this.index += HEADER.length;
                     } else if (this.match(TEXT)) {
                        this.index += TEXT.length;
                     }

                     var4 = new RFC822DATA(this);
                  }
               } else if (this.match(INTERNALDATE.name)) {
                  this.index += INTERNALDATE.name.length;
                  var4 = new INTERNALDATE(this);
               }
            } else if (this.match(BODY.name)) {
               if (this.buffer[this.index + 4] == 91) {
                  this.index += BODY.name.length;
                  var4 = new BODY(this);
               } else {
                  if (this.match(BODYSTRUCTURE.name)) {
                     this.index += BODYSTRUCTURE.name.length;
                  } else {
                     this.index += BODY.name.length;
                  }

                  var4 = new BODYSTRUCTURE(this);
               }
            }

            if (var4 != null) {
               var1.addElement(var4);
            }
         } while(this.buffer[this.index] != 41);

         ++this.index;
         Item[] var5 = new Item[var1.size()];
         this.items = var5;
         var1.copyInto(var5);
      } else {
         var2 = new StringBuilder("error in FETCH parsing, missing '(' at index ");
         var2.append(this.index);
         throw new ParsingException(var2.toString());
      }
   }

   public Item getItem(int var1) {
      return this.items[var1];
   }

   public Item getItem(Class var1) {
      int var2 = 0;

      while(true) {
         Item[] var3 = this.items;
         if (var2 >= var3.length) {
            return null;
         }

         if (var1.isInstance(var3[var2])) {
            return this.items[var2];
         }

         ++var2;
      }
   }

   public int getItemCount() {
      return this.items.length;
   }
}
