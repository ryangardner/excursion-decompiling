package com.sun.activation.registries;

public class MailcapTokenizer {
   public static final int EOI_TOKEN = 5;
   public static final int EQUALS_TOKEN = 61;
   public static final int SEMICOLON_TOKEN = 59;
   public static final int SLASH_TOKEN = 47;
   public static final int START_TOKEN = 1;
   public static final int STRING_TOKEN = 2;
   public static final int UNKNOWN_TOKEN = 0;
   private char autoquoteChar;
   private int currentToken;
   private String currentTokenValue;
   private String data;
   private int dataIndex;
   private int dataLength;
   private boolean isAutoquoting;

   public MailcapTokenizer(String var1) {
      this.data = var1;
      this.dataIndex = 0;
      this.dataLength = var1.length();
      this.currentToken = 1;
      this.currentTokenValue = "";
      this.isAutoquoting = false;
      this.autoquoteChar = (char)59;
   }

   private static String fixEscapeSequences(String var0) {
      int var1 = var0.length();
      StringBuffer var2 = new StringBuffer();
      var2.ensureCapacity(var1);

      for(int var3 = 0; var3 < var1; ++var3) {
         char var4 = var0.charAt(var3);
         if (var4 != '\\') {
            var2.append(var4);
         } else if (var3 < var1 - 1) {
            ++var3;
            var2.append(var0.charAt(var3));
         } else {
            var2.append(var4);
         }
      }

      return var2.toString();
   }

   private static boolean isControlChar(char var0) {
      return Character.isISOControl(var0);
   }

   private static boolean isSpecialChar(char var0) {
      boolean var1;
      if (var0 != '"' && var0 != ',' && var0 != '/' && var0 != '(' && var0 != ')') {
         switch(var0) {
         case ':':
         case ';':
         case '<':
         case '=':
         case '>':
         case '?':
         case '@':
            break;
         default:
            switch(var0) {
            case '[':
            case '\\':
            case ']':
               break;
            default:
               var1 = false;
               return var1;
            }
         }
      }

      var1 = true;
      return var1;
   }

   private static boolean isStringTokenChar(char var0) {
      return !isSpecialChar(var0) && !isControlChar(var0) && !isWhiteSpaceChar(var0);
   }

   private static boolean isWhiteSpaceChar(char var0) {
      return Character.isWhitespace(var0);
   }

   public static String nameForToken(int var0) {
      String var1;
      if (var0 != 0) {
         if (var0 != 1) {
            if (var0 != 2) {
               if (var0 != 5) {
                  if (var0 != 47) {
                     if (var0 != 59) {
                        if (var0 != 61) {
                           var1 = "really unknown";
                        } else {
                           var1 = "'='";
                        }
                     } else {
                        var1 = "';'";
                     }
                  } else {
                     var1 = "'/'";
                  }
               } else {
                  var1 = "EOI";
               }
            } else {
               var1 = "string";
            }
         } else {
            var1 = "start";
         }
      } else {
         var1 = "unknown";
      }

      return var1;
   }

   private void processAutoquoteToken() {
      int var1 = this.dataIndex;
      boolean var2 = false;

      while(true) {
         int var3 = this.dataIndex;
         if (var3 >= this.dataLength || var2) {
            this.currentToken = 2;
            this.currentTokenValue = fixEscapeSequences(this.data.substring(var1, this.dataIndex));
            return;
         }

         if (this.data.charAt(var3) != this.autoquoteChar) {
            ++this.dataIndex;
         } else {
            var2 = true;
         }
      }
   }

   private void processStringToken() {
      int var1 = this.dataIndex;

      while(true) {
         int var2 = this.dataIndex;
         if (var2 >= this.dataLength || !isStringTokenChar(this.data.charAt(var2))) {
            this.currentToken = 2;
            this.currentTokenValue = this.data.substring(var1, this.dataIndex);
            return;
         }

         ++this.dataIndex;
      }
   }

   public int getCurrentToken() {
      return this.currentToken;
   }

   public String getCurrentTokenValue() {
      return this.currentTokenValue;
   }

   public int nextToken() {
      if (this.dataIndex >= this.dataLength) {
         this.currentToken = 5;
         this.currentTokenValue = null;
      } else {
         while(true) {
            int var1 = this.dataIndex;
            if (var1 >= this.dataLength || !isWhiteSpaceChar(this.data.charAt(var1))) {
               var1 = this.dataIndex;
               if (var1 < this.dataLength) {
                  char var2 = this.data.charAt(var1);
                  if (this.isAutoquoting) {
                     if (var2 != ';' && var2 != '=') {
                        this.processAutoquoteToken();
                     } else {
                        this.currentToken = var2;
                        this.currentTokenValue = (new Character(var2)).toString();
                        ++this.dataIndex;
                     }
                  } else if (isStringTokenChar(var2)) {
                     this.processStringToken();
                  } else if (var2 != '/' && var2 != ';' && var2 != '=') {
                     this.currentToken = 0;
                     this.currentTokenValue = (new Character(var2)).toString();
                     ++this.dataIndex;
                  } else {
                     this.currentToken = var2;
                     this.currentTokenValue = (new Character(var2)).toString();
                     ++this.dataIndex;
                  }
               } else {
                  this.currentToken = 5;
                  this.currentTokenValue = null;
               }
               break;
            }

            ++this.dataIndex;
         }
      }

      return this.currentToken;
   }

   public void setIsAutoquoting(boolean var1) {
      this.isAutoquoting = var1;
   }
}
