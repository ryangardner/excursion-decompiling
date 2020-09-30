package javax.mail.internet;

public class HeaderTokenizer {
   private static final HeaderTokenizer.Token EOFToken = new HeaderTokenizer.Token(-4, (String)null);
   public static final String MIME = "()<>@,;:\\\"\t []/?=";
   public static final String RFC822 = "()<>@,;:\\\"\t .[]";
   private int currentPos;
   private String delimiters;
   private int maxPos;
   private int nextPos;
   private int peekPos;
   private boolean skipComments;
   private String string;

   public HeaderTokenizer(String var1) {
      this(var1, "()<>@,;:\\\"\t .[]");
   }

   public HeaderTokenizer(String var1, String var2) {
      this(var1, var2, true);
   }

   public HeaderTokenizer(String var1, String var2, boolean var3) {
      String var4 = var1;
      if (var1 == null) {
         var4 = "";
      }

      this.string = var4;
      this.skipComments = var3;
      this.delimiters = var2;
      this.peekPos = 0;
      this.nextPos = 0;
      this.currentPos = 0;
      this.maxPos = var4.length();
   }

   private static String filterToken(String var0, int var1, int var2) {
      StringBuffer var3 = new StringBuffer();
      boolean var4 = false;
      boolean var5 = false;
      int var6 = var1;

      for(boolean var8 = var4; var6 < var2; ++var6) {
         char var7 = var0.charAt(var6);
         if (var7 != '\n' || !var8) {
            if (var5) {
               var3.append(var7);
               var8 = false;
               var5 = false;
               continue;
            }

            if (var7 == '\\') {
               var8 = false;
               var5 = true;
               continue;
            }

            if (var7 == '\r') {
               var8 = true;
               continue;
            }

            var3.append(var7);
         }

         var8 = false;
      }

      return var3.toString();
   }

   private HeaderTokenizer.Token getNext() throws ParseException {
      if (this.currentPos >= this.maxPos) {
         return EOFToken;
      } else if (this.skipWhiteSpace() == -4) {
         return EOFToken;
      } else {
         char var1 = this.string.charAt(this.currentPos);
         boolean var2 = false;

         char var3;
         int var4;
         String var6;
         boolean var9;
         int var11;
         for(var3 = var1; var3 == '('; var2 = var9) {
            int var7 = this.currentPos + 1;
            this.currentPos = var7;
            var4 = 1;

            for(var9 = var2; var4 > 0; var4 = var11) {
               var11 = this.currentPos;
               if (var11 >= this.maxPos) {
                  break;
               }

               boolean var5;
               label125: {
                  char var8 = this.string.charAt(var11);
                  if (var8 == '\\') {
                     ++this.currentPos;
                  } else if (var8 != '\r') {
                     if (var8 == '(') {
                        var11 = var4 + 1;
                        var5 = var9;
                     } else {
                        var5 = var9;
                        var11 = var4;
                        if (var8 == ')') {
                           var11 = var4 - 1;
                           var5 = var9;
                        }
                     }
                     break label125;
                  }

                  var5 = true;
                  var11 = var4;
               }

               ++this.currentPos;
               var9 = var5;
            }

            if (var4 != 0) {
               throw new ParseException("Unbalanced comments");
            }

            if (!this.skipComments) {
               if (var9) {
                  var6 = filterToken(this.string, var7, this.currentPos - 1);
               } else {
                  var6 = this.string.substring(var7, this.currentPos - 1);
               }

               return new HeaderTokenizer.Token(-3, var6);
            }

            if (this.skipWhiteSpace() == -4) {
               return EOFToken;
            }

            char var12 = this.string.charAt(this.currentPos);
            var3 = var12;
         }

         int var10;
         if (var3 == '"') {
            var4 = this.currentPos + 1;
            this.currentPos = var4;

            while(true) {
               var10 = this.currentPos;
               if (var10 >= this.maxPos) {
                  throw new ParseException("Unbalanced quoted string");
               }

               label87: {
                  char var13 = this.string.charAt(var10);
                  if (var13 == '\\') {
                     ++this.currentPos;
                  } else if (var13 != '\r') {
                     var9 = var2;
                     if (var13 == '"') {
                        var10 = this.currentPos + 1;
                        this.currentPos = var10;
                        if (var2) {
                           var6 = filterToken(this.string, var4, var10 - 1);
                        } else {
                           var6 = this.string.substring(var4, var10 - 1);
                        }

                        return new HeaderTokenizer.Token(-2, var6);
                     }
                     break label87;
                  }

                  var9 = true;
               }

               ++this.currentPos;
               var2 = var9;
            }
         } else if (var3 >= ' ' && var3 < 127 && this.delimiters.indexOf(var3) < 0) {
            var11 = this.currentPos;

            while(true) {
               var10 = this.currentPos;
               if (var10 >= this.maxPos) {
                  break;
               }

               var1 = this.string.charAt(var10);
               if (var1 < ' ' || var1 >= 127 || var1 == '(' || var1 == ' ' || var1 == '"' || this.delimiters.indexOf(var1) >= 0) {
                  break;
               }

               ++this.currentPos;
            }

            return new HeaderTokenizer.Token(-1, this.string.substring(var11, this.currentPos));
         } else {
            ++this.currentPos;
            return new HeaderTokenizer.Token(var3, new String(new char[]{var3}));
         }
      }
   }

   private int skipWhiteSpace() {
      while(true) {
         int var1 = this.currentPos;
         if (var1 >= this.maxPos) {
            return -4;
         }

         char var2 = this.string.charAt(var1);
         if (var2 != ' ' && var2 != '\t' && var2 != '\r' && var2 != '\n') {
            return this.currentPos;
         }

         ++this.currentPos;
      }
   }

   public String getRemainder() {
      return this.string.substring(this.nextPos);
   }

   public HeaderTokenizer.Token next() throws ParseException {
      this.currentPos = this.nextPos;
      HeaderTokenizer.Token var1 = this.getNext();
      int var2 = this.currentPos;
      this.peekPos = var2;
      this.nextPos = var2;
      return var1;
   }

   public HeaderTokenizer.Token peek() throws ParseException {
      this.currentPos = this.peekPos;
      HeaderTokenizer.Token var1 = this.getNext();
      this.peekPos = this.currentPos;
      return var1;
   }

   public static class Token {
      public static final int ATOM = -1;
      public static final int COMMENT = -3;
      public static final int EOF = -4;
      public static final int QUOTEDSTRING = -2;
      private int type;
      private String value;

      public Token(int var1, String var2) {
         this.type = var1;
         this.value = var2;
      }

      public int getType() {
         return this.type;
      }

      public String getValue() {
         return this.value;
      }
   }
}
