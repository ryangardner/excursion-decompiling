package org.apache.http.message;

import org.apache.http.Header;
import org.apache.http.HttpVersion;
import org.apache.http.ParseException;
import org.apache.http.ProtocolVersion;
import org.apache.http.RequestLine;
import org.apache.http.StatusLine;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.CharArrayBuffer;

public class BasicLineParser implements LineParser {
   public static final BasicLineParser DEFAULT = new BasicLineParser();
   protected final ProtocolVersion protocol;

   public BasicLineParser() {
      this((ProtocolVersion)null);
   }

   public BasicLineParser(ProtocolVersion var1) {
      Object var2 = var1;
      if (var1 == null) {
         var2 = HttpVersion.HTTP_1_1;
      }

      this.protocol = (ProtocolVersion)var2;
   }

   public static final Header parseHeader(String var0, LineParser var1) throws ParseException {
      if (var0 != null) {
         Object var2 = var1;
         if (var1 == null) {
            var2 = DEFAULT;
         }

         CharArrayBuffer var3 = new CharArrayBuffer(var0.length());
         var3.append(var0);
         return ((LineParser)var2).parseHeader(var3);
      } else {
         throw new IllegalArgumentException("Value to parse may not be null");
      }
   }

   public static final ProtocolVersion parseProtocolVersion(String var0, LineParser var1) throws ParseException {
      if (var0 != null) {
         Object var2 = var1;
         if (var1 == null) {
            var2 = DEFAULT;
         }

         CharArrayBuffer var3 = new CharArrayBuffer(var0.length());
         var3.append(var0);
         return ((LineParser)var2).parseProtocolVersion(var3, new ParserCursor(0, var0.length()));
      } else {
         throw new IllegalArgumentException("Value to parse may not be null.");
      }
   }

   public static final RequestLine parseRequestLine(String var0, LineParser var1) throws ParseException {
      if (var0 != null) {
         Object var2 = var1;
         if (var1 == null) {
            var2 = DEFAULT;
         }

         CharArrayBuffer var3 = new CharArrayBuffer(var0.length());
         var3.append(var0);
         return ((LineParser)var2).parseRequestLine(var3, new ParserCursor(0, var0.length()));
      } else {
         throw new IllegalArgumentException("Value to parse may not be null.");
      }
   }

   public static final StatusLine parseStatusLine(String var0, LineParser var1) throws ParseException {
      if (var0 != null) {
         Object var2 = var1;
         if (var1 == null) {
            var2 = DEFAULT;
         }

         CharArrayBuffer var3 = new CharArrayBuffer(var0.length());
         var3.append(var0);
         return ((LineParser)var2).parseStatusLine(var3, new ParserCursor(0, var0.length()));
      } else {
         throw new IllegalArgumentException("Value to parse may not be null.");
      }
   }

   protected ProtocolVersion createProtocolVersion(int var1, int var2) {
      return this.protocol.forVersion(var1, var2);
   }

   protected RequestLine createRequestLine(String var1, String var2, ProtocolVersion var3) {
      return new BasicRequestLine(var1, var2, var3);
   }

   protected StatusLine createStatusLine(ProtocolVersion var1, int var2, String var3) {
      return new BasicStatusLine(var1, var2, var3);
   }

   public boolean hasProtocolVersion(CharArrayBuffer var1, ParserCursor var2) {
      if (var1 == null) {
         throw new IllegalArgumentException("Char array buffer may not be null");
      } else if (var2 != null) {
         int var3 = var2.getPos();
         String var10 = this.protocol.getProtocol();
         int var4 = var10.length();
         int var5 = var1.length();
         boolean var6 = false;
         if (var5 < var4 + 4) {
            return false;
         } else {
            if (var3 < 0) {
               var5 = var1.length() - 4 - var4;
            } else {
               var5 = var3;
               if (var3 == 0) {
                  while(true) {
                     var5 = var3;
                     if (var3 >= var1.length()) {
                        break;
                     }

                     var5 = var3;
                     if (!HTTP.isWhitespace(var1.charAt(var3))) {
                        break;
                     }

                     ++var3;
                  }
               }
            }

            int var7 = var5 + var4;
            if (var7 + 4 > var1.length()) {
               return false;
            } else {
               boolean var8 = true;

               for(var3 = 0; var8 && var3 < var4; ++var3) {
                  if (var1.charAt(var5 + var3) == var10.charAt(var3)) {
                     var8 = true;
                  } else {
                     var8 = false;
                  }
               }

               boolean var9 = var8;
               if (var8) {
                  var8 = var6;
                  if (var1.charAt(var7) == '/') {
                     var8 = true;
                  }

                  var9 = var8;
               }

               return var9;
            }
         }
      } else {
         throw new IllegalArgumentException("Parser cursor may not be null");
      }
   }

   public Header parseHeader(CharArrayBuffer var1) throws ParseException {
      return new BufferedHeader(var1);
   }

   public ProtocolVersion parseProtocolVersion(CharArrayBuffer var1, ParserCursor var2) throws ParseException {
      if (var1 == null) {
         throw new IllegalArgumentException("Char array buffer may not be null");
      } else if (var2 == null) {
         throw new IllegalArgumentException("Parser cursor may not be null");
      } else {
         String var3 = this.protocol.getProtocol();
         int var4 = var3.length();
         int var5 = var2.getPos();
         int var6 = var2.getUpperBound();
         this.skipWhitespace(var1, var2);
         int var7 = var2.getPos();
         int var8 = var7 + var4;
         StringBuffer var14;
         if (var8 + 4 > var6) {
            var14 = new StringBuffer();
            var14.append("Not a valid protocol version: ");
            var14.append(var1.substring(var5, var6));
            throw new ParseException(var14.toString());
         } else {
            boolean var9 = false;
            boolean var10 = true;

            int var11;
            for(var11 = 0; var10 && var11 < var4; ++var11) {
               if (var1.charAt(var7 + var11) == var3.charAt(var11)) {
                  var10 = true;
               } else {
                  var10 = false;
               }
            }

            boolean var16 = var10;
            if (var10) {
               var10 = var9;
               if (var1.charAt(var8) == '/') {
                  var10 = true;
               }

               var16 = var10;
            }

            if (var16) {
               var11 = var7 + var4 + 1;
               int var17 = var1.indexOf(46, var11, var6);
               if (var17 != -1) {
                  int var15;
                  try {
                     var15 = Integer.parseInt(var1.substringTrimmed(var11, var17));
                  } catch (NumberFormatException var13) {
                     var14 = new StringBuffer();
                     var14.append("Invalid protocol major version number: ");
                     var14.append(var1.substring(var5, var6));
                     throw new ParseException(var14.toString());
                  }

                  var7 = var17 + 1;
                  var11 = var1.indexOf(32, var7, var6);
                  var17 = var11;
                  if (var11 == -1) {
                     var17 = var6;
                  }

                  try {
                     var11 = Integer.parseInt(var1.substringTrimmed(var7, var17));
                  } catch (NumberFormatException var12) {
                     var14 = new StringBuffer();
                     var14.append("Invalid protocol minor version number: ");
                     var14.append(var1.substring(var5, var6));
                     throw new ParseException(var14.toString());
                  }

                  var2.updatePos(var17);
                  return this.createProtocolVersion(var15, var11);
               } else {
                  var14 = new StringBuffer();
                  var14.append("Invalid protocol version number: ");
                  var14.append(var1.substring(var5, var6));
                  throw new ParseException(var14.toString());
               }
            } else {
               var14 = new StringBuffer();
               var14.append("Not a valid protocol version: ");
               var14.append(var1.substring(var5, var6));
               throw new ParseException(var14.toString());
            }
         }
      }
   }

   public RequestLine parseRequestLine(CharArrayBuffer var1, ParserCursor var2) throws ParseException {
      if (var1 == null) {
         throw new IllegalArgumentException("Char array buffer may not be null");
      } else if (var2 != null) {
         int var3 = var2.getPos();
         int var4 = var2.getUpperBound();

         label62: {
            int var5;
            int var6;
            boolean var10001;
            try {
               this.skipWhitespace(var1, var2);
               var5 = var2.getPos();
               var6 = var1.indexOf(32, var5, var4);
            } catch (IndexOutOfBoundsException var14) {
               var10001 = false;
               break label62;
            }

            StringBuffer var9;
            ParseException var16;
            if (var6 >= 0) {
               label63: {
                  String var7;
                  try {
                     var7 = var1.substringTrimmed(var5, var6);
                     var2.updatePos(var6);
                     this.skipWhitespace(var1, var2);
                     var6 = var2.getPos();
                     var5 = var1.indexOf(32, var6, var4);
                  } catch (IndexOutOfBoundsException var13) {
                     var10001 = false;
                     break label63;
                  }

                  if (var5 < 0) {
                     try {
                        var9 = new StringBuffer();
                        var9.append("Invalid request line: ");
                        var9.append(var1.substring(var3, var4));
                        var16 = new ParseException(var9.toString());
                        throw var16;
                     } catch (IndexOutOfBoundsException var11) {
                        var10001 = false;
                     }
                  } else {
                     label65: {
                        try {
                           String var8 = var1.substringTrimmed(var6, var5);
                           var2.updatePos(var5);
                           ProtocolVersion var18 = this.parseProtocolVersion(var1, var2);
                           this.skipWhitespace(var1, var2);
                           if (var2.atEnd()) {
                              return this.createRequestLine(var7, var8, var18);
                           }
                        } catch (IndexOutOfBoundsException var15) {
                           var10001 = false;
                           break label65;
                        }

                        try {
                           var9 = new StringBuffer();
                           var9.append("Invalid request line: ");
                           var9.append(var1.substring(var3, var4));
                           var16 = new ParseException(var9.toString());
                           throw var16;
                        } catch (IndexOutOfBoundsException var10) {
                           var10001 = false;
                        }
                     }
                  }
               }
            } else {
               try {
                  var9 = new StringBuffer();
                  var9.append("Invalid request line: ");
                  var9.append(var1.substring(var3, var4));
                  var16 = new ParseException(var9.toString());
                  throw var16;
               } catch (IndexOutOfBoundsException var12) {
                  var10001 = false;
               }
            }
         }

         StringBuffer var17 = new StringBuffer();
         var17.append("Invalid request line: ");
         var17.append(var1.substring(var3, var4));
         throw new ParseException(var17.toString());
      } else {
         throw new IllegalArgumentException("Parser cursor may not be null");
      }
   }

   public StatusLine parseStatusLine(CharArrayBuffer param1, ParserCursor param2) throws ParseException {
      // $FF: Couldn't be decompiled
   }

   protected void skipWhitespace(CharArrayBuffer var1, ParserCursor var2) {
      int var3 = var2.getPos();

      for(int var4 = var2.getUpperBound(); var3 < var4 && HTTP.isWhitespace(var1.charAt(var3)); ++var3) {
      }

      var2.updatePos(var3);
   }
}
