package com.sun.mail.imap.protocol;

import com.sun.mail.iap.ParsingException;
import com.sun.mail.iap.Response;
import java.io.PrintStream;
import java.util.Vector;
import javax.mail.internet.ParameterList;

public class BODYSTRUCTURE implements Item {
   private static int MULTI;
   private static int NESTED;
   private static int SINGLE;
   static final char[] name = new char[]{'B', 'O', 'D', 'Y', 'S', 'T', 'R', 'U', 'C', 'T', 'U', 'R', 'E'};
   private static boolean parseDebug;
   public String attachment;
   public BODYSTRUCTURE[] bodies;
   public ParameterList cParams;
   public ParameterList dParams;
   public String description;
   public String disposition;
   public String encoding;
   public ENVELOPE envelope;
   public String id;
   public String[] language;
   public int lines = -1;
   public String md5;
   public int msgno;
   private int processedType;
   public int size = -1;
   public String subtype;
   public String type;

   static {
      boolean var0 = true;
      SINGLE = 1;
      MULTI = 2;
      NESTED = 3;
      parseDebug = false;

      label40: {
         String var1;
         boolean var10001;
         try {
            var1 = System.getProperty("mail.imap.parse.debug");
         } catch (SecurityException var4) {
            var10001 = false;
            break label40;
         }

         label29: {
            if (var1 != null) {
               try {
                  if (var1.equalsIgnoreCase("true")) {
                     break label29;
                  }
               } catch (SecurityException var3) {
                  var10001 = false;
                  break label40;
               }
            }

            var0 = false;
         }

         try {
            parseDebug = var0;
         } catch (SecurityException var2) {
            var10001 = false;
         }
      }

   }

   public BODYSTRUCTURE(FetchResponse var1) throws ParsingException {
      if (parseDebug) {
         System.out.println("DEBUG IMAP: parsing BODYSTRUCTURE");
      }

      this.msgno = var1.getNumber();
      PrintStream var2;
      StringBuilder var3;
      if (parseDebug) {
         var2 = System.out;
         var3 = new StringBuilder("DEBUG IMAP: msgno ");
         var3.append(this.msgno);
         var2.println(var3.toString());
      }

      var1.skipSpaces();
      if (var1.readByte() != 40) {
         throw new ParsingException("BODYSTRUCTURE parse error: missing ``('' at start");
      } else {
         byte var4;
         StringBuilder var6;
         PrintStream var11;
         if (var1.peekByte() == 40) {
            if (parseDebug) {
               System.out.println("DEBUG IMAP: parsing multipart");
            }

            this.type = "multipart";
            this.processedType = MULTI;
            Vector var8 = new Vector(1);

            do {
               var8.addElement(new BODYSTRUCTURE(var1));
               var1.skipSpaces();
            } while(var1.peekByte() == 40);

            BODYSTRUCTURE[] var7 = new BODYSTRUCTURE[var8.size()];
            this.bodies = var7;
            var8.copyInto(var7);
            this.subtype = var1.readString();
            if (parseDebug) {
               var2 = System.out;
               var3 = new StringBuilder("DEBUG IMAP: subtype ");
               var3.append(this.subtype);
               var2.println(var3.toString());
            }

            if (var1.readByte() == 41) {
               if (parseDebug) {
                  System.out.println("DEBUG IMAP: parse DONE");
               }

               return;
            }

            if (parseDebug) {
               System.out.println("DEBUG IMAP: parsing extension data");
            }

            this.cParams = this.parseParameters(var1);
            if (var1.readByte() == 41) {
               if (parseDebug) {
                  System.out.println("DEBUG IMAP: body parameters DONE");
               }

               return;
            }

            var4 = var1.readByte();
            if (var4 == 40) {
               if (parseDebug) {
                  System.out.println("DEBUG IMAP: parse disposition");
               }

               this.disposition = var1.readString();
               if (parseDebug) {
                  var2 = System.out;
                  var3 = new StringBuilder("DEBUG IMAP: disposition ");
                  var3.append(this.disposition);
                  var2.println(var3.toString());
               }

               this.dParams = this.parseParameters(var1);
               if (var1.readByte() != 41) {
                  throw new ParsingException("BODYSTRUCTURE parse error: missing ``)'' at end of disposition in multipart");
               }

               if (parseDebug) {
                  System.out.println("DEBUG IMAP: disposition DONE");
               }
            } else {
               if (var4 != 78 && var4 != 110) {
                  var6 = new StringBuilder("BODYSTRUCTURE parse error: ");
                  var6.append(this.type);
                  var6.append("/");
                  var6.append(this.subtype);
                  var6.append(": ");
                  var6.append("bad multipart disposition, b ");
                  var6.append(var4);
                  throw new ParsingException(var6.toString());
               }

               if (parseDebug) {
                  System.out.println("DEBUG IMAP: disposition NIL");
               }

               var1.skip(2);
            }

            var4 = var1.readByte();
            if (var4 == 41) {
               if (parseDebug) {
                  System.out.println("DEBUG IMAP: no body-fld-lang");
               }

               return;
            }

            if (var4 != 32) {
               throw new ParsingException("BODYSTRUCTURE parse error: missing space after disposition");
            }

            if (var1.peekByte() == 40) {
               this.language = var1.readStringList();
               if (parseDebug) {
                  var2 = System.out;
                  var3 = new StringBuilder("DEBUG IMAP: language len ");
                  var3.append(this.language.length);
                  var2.println(var3.toString());
               }
            } else {
               String var9 = var1.readString();
               if (var9 != null) {
                  this.language = new String[]{var9};
                  if (parseDebug) {
                     var11 = System.out;
                     StringBuilder var5 = new StringBuilder("DEBUG IMAP: language ");
                     var5.append(var9);
                     var11.println(var5.toString());
                  }
               }
            }

            while(var1.readByte() == 32) {
               this.parseBodyExtension(var1);
            }
         } else {
            if (parseDebug) {
               System.out.println("DEBUG IMAP: single part");
            }

            this.type = var1.readString();
            StringBuilder var10;
            if (parseDebug) {
               var11 = System.out;
               var10 = new StringBuilder("DEBUG IMAP: type ");
               var10.append(this.type);
               var11.println(var10.toString());
            }

            this.processedType = SINGLE;
            this.subtype = var1.readString();
            if (parseDebug) {
               var2 = System.out;
               var3 = new StringBuilder("DEBUG IMAP: subtype ");
               var3.append(this.subtype);
               var2.println(var3.toString());
            }

            if (this.type == null) {
               this.type = "application";
               this.subtype = "octet-stream";
            }

            this.cParams = this.parseParameters(var1);
            if (parseDebug) {
               var11 = System.out;
               var10 = new StringBuilder("DEBUG IMAP: cParams ");
               var10.append(this.cParams);
               var11.println(var10.toString());
            }

            this.id = var1.readString();
            if (parseDebug) {
               var11 = System.out;
               var10 = new StringBuilder("DEBUG IMAP: id ");
               var10.append(this.id);
               var11.println(var10.toString());
            }

            this.description = var1.readString();
            if (parseDebug) {
               var2 = System.out;
               var3 = new StringBuilder("DEBUG IMAP: description ");
               var3.append(this.description);
               var2.println(var3.toString());
            }

            this.encoding = var1.readString();
            if (parseDebug) {
               var11 = System.out;
               var10 = new StringBuilder("DEBUG IMAP: encoding ");
               var10.append(this.encoding);
               var11.println(var10.toString());
            }

            this.size = var1.readNumber();
            if (parseDebug) {
               var2 = System.out;
               var3 = new StringBuilder("DEBUG IMAP: size ");
               var3.append(this.size);
               var2.println(var3.toString());
            }

            if (this.size < 0) {
               throw new ParsingException("BODYSTRUCTURE parse error: bad ``size'' element");
            }

            if (this.type.equalsIgnoreCase("text")) {
               this.lines = var1.readNumber();
               if (parseDebug) {
                  var2 = System.out;
                  var3 = new StringBuilder("DEBUG IMAP: lines ");
                  var3.append(this.lines);
                  var2.println(var3.toString());
               }

               if (this.lines < 0) {
                  throw new ParsingException("BODYSTRUCTURE parse error: bad ``lines'' element");
               }
            } else if (this.type.equalsIgnoreCase("message") && this.subtype.equalsIgnoreCase("rfc822")) {
               this.processedType = NESTED;
               this.envelope = new ENVELOPE(var1);
               this.bodies = new BODYSTRUCTURE[]{new BODYSTRUCTURE(var1)};
               this.lines = var1.readNumber();
               if (parseDebug) {
                  var11 = System.out;
                  var10 = new StringBuilder("DEBUG IMAP: lines ");
                  var10.append(this.lines);
                  var11.println(var10.toString());
               }

               if (this.lines < 0) {
                  throw new ParsingException("BODYSTRUCTURE parse error: bad ``lines'' element");
               }
            } else {
               var1.skipSpaces();
               if (Character.isDigit((char)var1.peekByte())) {
                  var6 = new StringBuilder("BODYSTRUCTURE parse error: server erroneously included ``lines'' element with type ");
                  var6.append(this.type);
                  var6.append("/");
                  var6.append(this.subtype);
                  throw new ParsingException(var6.toString());
               }
            }

            if (var1.peekByte() == 41) {
               var1.readByte();
               if (parseDebug) {
                  System.out.println("DEBUG IMAP: parse DONE");
               }

               return;
            }

            this.md5 = var1.readString();
            if (var1.readByte() == 41) {
               if (parseDebug) {
                  System.out.println("DEBUG IMAP: no MD5 DONE");
               }

               return;
            }

            var4 = var1.readByte();
            if (var4 == 40) {
               this.disposition = var1.readString();
               if (parseDebug) {
                  var2 = System.out;
                  var3 = new StringBuilder("DEBUG IMAP: disposition ");
                  var3.append(this.disposition);
                  var2.println(var3.toString());
               }

               this.dParams = this.parseParameters(var1);
               if (parseDebug) {
                  var11 = System.out;
                  var10 = new StringBuilder("DEBUG IMAP: dParams ");
                  var10.append(this.dParams);
                  var11.println(var10.toString());
               }

               if (var1.readByte() != 41) {
                  throw new ParsingException("BODYSTRUCTURE parse error: missing ``)'' at end of disposition");
               }
            } else {
               if (var4 != 78 && var4 != 110) {
                  var6 = new StringBuilder("BODYSTRUCTURE parse error: ");
                  var6.append(this.type);
                  var6.append("/");
                  var6.append(this.subtype);
                  var6.append(": ");
                  var6.append("bad single part disposition, b ");
                  var6.append(var4);
                  throw new ParsingException(var6.toString());
               }

               if (parseDebug) {
                  System.out.println("DEBUG IMAP: disposition NIL");
               }

               var1.skip(2);
            }

            if (var1.readByte() == 41) {
               if (parseDebug) {
                  System.out.println("DEBUG IMAP: disposition DONE");
               }

               return;
            }

            if (var1.peekByte() == 40) {
               this.language = var1.readStringList();
               if (parseDebug) {
                  var11 = System.out;
                  var10 = new StringBuilder("DEBUG IMAP: language len ");
                  var10.append(this.language.length);
                  var11.println(var10.toString());
               }
            } else {
               String var13 = var1.readString();
               if (var13 != null) {
                  this.language = new String[]{var13};
                  if (parseDebug) {
                     PrintStream var12 = System.out;
                     var10 = new StringBuilder("DEBUG IMAP: language ");
                     var10.append(var13);
                     var12.println(var10.toString());
                  }
               }
            }

            while(var1.readByte() == 32) {
               this.parseBodyExtension(var1);
            }

            if (parseDebug) {
               System.out.println("DEBUG IMAP: all DONE");
            }
         }

      }
   }

   private void parseBodyExtension(Response var1) throws ParsingException {
      var1.skipSpaces();
      byte var2 = var1.peekByte();
      if (var2 == 40) {
         var1.skip(1);

         do {
            this.parseBodyExtension(var1);
         } while(var1.readByte() != 41);
      } else if (Character.isDigit((char)var2)) {
         var1.readNumber();
      } else {
         var1.readString();
      }

   }

   private ParameterList parseParameters(Response var1) throws ParsingException {
      var1.skipSpaces();
      byte var2 = var1.readByte();
      ParameterList var3 = null;
      ParameterList var8;
      if (var2 == 40) {
         var3 = new ParameterList();

         while(true) {
            String var4 = var1.readString();
            PrintStream var5;
            if (parseDebug) {
               var5 = System.out;
               StringBuilder var6 = new StringBuilder("DEBUG IMAP: parameter name ");
               var6.append(var4);
               var5.println(var6.toString());
            }

            if (var4 == null) {
               StringBuilder var9 = new StringBuilder("BODYSTRUCTURE parse error: ");
               var9.append(this.type);
               var9.append("/");
               var9.append(this.subtype);
               var9.append(": ");
               var9.append("null name in parameter list");
               throw new ParsingException(var9.toString());
            }

            String var10 = var1.readString();
            if (parseDebug) {
               var5 = System.out;
               StringBuilder var7 = new StringBuilder("DEBUG IMAP: parameter value ");
               var7.append(var10);
               var5.println(var7.toString());
            }

            var3.set(var4, var10);
            if (var1.readByte() == 41) {
               var3.set((String)null, "DONE");
               var8 = var3;
               break;
            }
         }
      } else {
         if (var2 != 78 && var2 != 110) {
            throw new ParsingException("Parameter list parse error");
         }

         if (parseDebug) {
            System.out.println("DEBUG IMAP: parameter list NIL");
         }

         var1.skip(2);
         var8 = var3;
      }

      return var8;
   }

   public boolean isMulti() {
      return this.processedType == MULTI;
   }

   public boolean isNested() {
      return this.processedType == NESTED;
   }

   public boolean isSingle() {
      return this.processedType == SINGLE;
   }
}
