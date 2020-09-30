package javax.activation;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Locale;

public class MimeTypeParameterList {
   private static final String TSPECIALS = "()<>@,;:/[]?=\\\"";
   private Hashtable parameters = new Hashtable();

   public MimeTypeParameterList() {
   }

   public MimeTypeParameterList(String var1) throws MimeTypeParseException {
      this.parse(var1);
   }

   private static boolean isTokenChar(char var0) {
      return var0 > ' ' && var0 < 127 && "()<>@,;:/[]?=\\\"".indexOf(var0) < 0;
   }

   private static String quote(String var0) {
      int var1 = var0.length();
      byte var2 = 0;
      int var3 = 0;

      boolean var4;
      for(var4 = false; var3 < var1 && !var4; ++var3) {
         var4 = isTokenChar(var0.charAt(var3)) ^ true;
      }

      if (!var4) {
         return var0;
      } else {
         StringBuffer var5 = new StringBuffer();
         var5.ensureCapacity((int)((double)var1 * 1.5D));
         var5.append('"');

         for(var3 = var2; var3 < var1; ++var3) {
            char var6 = var0.charAt(var3);
            if (var6 == '\\' || var6 == '"') {
               var5.append('\\');
            }

            var5.append(var6);
         }

         var5.append('"');
         return var5.toString();
      }
   }

   private static int skipWhiteSpace(String var0, int var1) {
      for(int var2 = var0.length(); var1 < var2 && Character.isWhitespace(var0.charAt(var1)); ++var1) {
      }

      return var1;
   }

   private static String unquote(String var0) {
      int var1 = var0.length();
      StringBuffer var2 = new StringBuffer();
      var2.ensureCapacity(var1);
      int var3 = 0;

      for(boolean var4 = false; var3 < var1; ++var3) {
         char var5 = var0.charAt(var3);
         if (!var4 && var5 != '\\') {
            var2.append(var5);
         } else if (var4) {
            var2.append(var5);
            var4 = false;
         } else {
            var4 = true;
         }
      }

      return var2.toString();
   }

   public String get(String var1) {
      return (String)this.parameters.get(var1.trim().toLowerCase(Locale.ENGLISH));
   }

   public Enumeration getNames() {
      return this.parameters.keys();
   }

   public boolean isEmpty() {
      return this.parameters.isEmpty();
   }

   protected void parse(String var1) throws MimeTypeParseException {
      if (var1 != null) {
         int var2 = var1.length();
         if (var2 > 0) {
            int var3 = skipWhiteSpace(var1, 0);

            while(true) {
               if (var3 < var2 && var1.charAt(var3) == ';') {
                  int var4 = skipWhiteSpace(var1, var3 + 1);
                  if (var4 >= var2) {
                     return;
                  }

                  for(var3 = var4; var3 < var2 && isTokenChar(var1.charAt(var3)); ++var3) {
                  }

                  String var5 = var1.substring(var4, var3).toLowerCase(Locale.ENGLISH);
                  var3 = skipWhiteSpace(var1, var3);
                  if (var3 < var2 && var1.charAt(var3) == '=') {
                     var4 = skipWhiteSpace(var1, var3 + 1);
                     StringBuilder var10;
                     if (var4 >= var2) {
                        var10 = new StringBuilder("Couldn't find a value for parameter named ");
                        var10.append(var5);
                        throw new MimeTypeParseException(var10.toString());
                     }

                     char var6 = var1.charAt(var4);
                     String var8;
                     if (var6 == '"') {
                        int var7 = var4 + 1;
                        if (var7 >= var2) {
                           throw new MimeTypeParseException("Encountered unterminated quoted parameter value.");
                        }

                        var3 = var7;

                        int var9;
                        char var11;
                        for(var11 = var6; var3 < var2; var3 = var9 + 1) {
                           var11 = var1.charAt(var3);
                           if (var11 == '"') {
                              break;
                           }

                           var9 = var3;
                           if (var11 == '\\') {
                              var9 = var3 + 1;
                           }
                        }

                        if (var11 != '"') {
                           throw new MimeTypeParseException("Encountered unterminated quoted parameter value.");
                        }

                        var8 = unquote(var1.substring(var7, var3));
                        ++var3;
                     } else {
                        if (!isTokenChar(var6)) {
                           var10 = new StringBuilder("Unexpected character encountered at index ");
                           var10.append(var4);
                           throw new MimeTypeParseException(var10.toString());
                        }

                        for(var3 = var4; var3 < var2 && isTokenChar(var1.charAt(var3)); ++var3) {
                        }

                        var8 = var1.substring(var4, var3);
                     }

                     this.parameters.put(var5, var8);
                     var3 = skipWhiteSpace(var1, var3);
                     continue;
                  }

                  throw new MimeTypeParseException("Couldn't find the '=' that separates a parameter name from its value.");
               }

               if (var3 >= var2) {
                  return;
               }

               throw new MimeTypeParseException("More characters encountered in input than expected.");
            }
         }
      }
   }

   public void remove(String var1) {
      this.parameters.remove(var1.trim().toLowerCase(Locale.ENGLISH));
   }

   public void set(String var1, String var2) {
      this.parameters.put(var1.trim().toLowerCase(Locale.ENGLISH), var2);
   }

   public int size() {
      return this.parameters.size();
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      var1.ensureCapacity(this.parameters.size() * 16);
      Enumeration var2 = this.parameters.keys();

      while(var2.hasMoreElements()) {
         String var3 = (String)var2.nextElement();
         var1.append("; ");
         var1.append(var3);
         var1.append('=');
         var1.append(quote((String)this.parameters.get(var3)));
      }

      return var1.toString();
   }
}
