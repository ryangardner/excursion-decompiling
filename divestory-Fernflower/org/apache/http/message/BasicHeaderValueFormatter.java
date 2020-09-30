package org.apache.http.message;

import org.apache.http.HeaderElement;
import org.apache.http.NameValuePair;
import org.apache.http.util.CharArrayBuffer;

public class BasicHeaderValueFormatter implements HeaderValueFormatter {
   public static final BasicHeaderValueFormatter DEFAULT = new BasicHeaderValueFormatter();
   public static final String SEPARATORS = " ;,:@()<>\\\"/[]?={}\t";
   public static final String UNSAFE_CHARS = "\"\\";

   public static final String formatElements(HeaderElement[] var0, boolean var1, HeaderValueFormatter var2) {
      Object var3 = var2;
      if (var2 == null) {
         var3 = DEFAULT;
      }

      return ((HeaderValueFormatter)var3).formatElements((CharArrayBuffer)null, var0, var1).toString();
   }

   public static final String formatHeaderElement(HeaderElement var0, boolean var1, HeaderValueFormatter var2) {
      Object var3 = var2;
      if (var2 == null) {
         var3 = DEFAULT;
      }

      return ((HeaderValueFormatter)var3).formatHeaderElement((CharArrayBuffer)null, var0, var1).toString();
   }

   public static final String formatNameValuePair(NameValuePair var0, boolean var1, HeaderValueFormatter var2) {
      Object var3 = var2;
      if (var2 == null) {
         var3 = DEFAULT;
      }

      return ((HeaderValueFormatter)var3).formatNameValuePair((CharArrayBuffer)null, var0, var1).toString();
   }

   public static final String formatParameters(NameValuePair[] var0, boolean var1, HeaderValueFormatter var2) {
      Object var3 = var2;
      if (var2 == null) {
         var3 = DEFAULT;
      }

      return ((HeaderValueFormatter)var3).formatParameters((CharArrayBuffer)null, var0, var1).toString();
   }

   protected void doFormatValue(CharArrayBuffer var1, String var2, boolean var3) {
      byte var4 = 0;
      boolean var5 = var3;
      int var6;
      if (!var3) {
         var6 = 0;

         while(true) {
            var5 = var3;
            if (var6 >= var2.length()) {
               break;
            }

            var5 = var3;
            if (var3) {
               break;
            }

            var3 = this.isSeparator(var2.charAt(var6));
            ++var6;
         }
      }

      var6 = var4;
      if (var5) {
         var1.append('"');
         var6 = var4;
      }

      while(var6 < var2.length()) {
         char var7 = var2.charAt(var6);
         if (this.isUnsafe(var7)) {
            var1.append('\\');
         }

         var1.append(var7);
         ++var6;
      }

      if (var5) {
         var1.append('"');
      }

   }

   protected int estimateElementsLen(HeaderElement[] var1) {
      int var2 = 0;
      if (var1 != null && var1.length >= 1) {
         int var3;
         for(var3 = (var1.length - 1) * 2; var2 < var1.length; ++var2) {
            var3 += this.estimateHeaderElementLen(var1[var2]);
         }

         return var3;
      } else {
         return 0;
      }
   }

   protected int estimateHeaderElementLen(HeaderElement var1) {
      int var2 = 0;
      if (var1 == null) {
         return 0;
      } else {
         int var3 = var1.getName().length();
         String var4 = var1.getValue();
         int var5 = var3;
         if (var4 != null) {
            var5 = var3 + var4.length() + 3;
         }

         int var6 = var1.getParameterCount();
         var3 = var5;
         if (var6 > 0) {
            while(true) {
               var3 = var5;
               if (var2 >= var6) {
                  break;
               }

               var5 += this.estimateNameValuePairLen(var1.getParameter(var2)) + 2;
               ++var2;
            }
         }

         return var3;
      }
   }

   protected int estimateNameValuePairLen(NameValuePair var1) {
      if (var1 == null) {
         return 0;
      } else {
         int var2 = var1.getName().length();
         String var4 = var1.getValue();
         int var3 = var2;
         if (var4 != null) {
            var3 = var2 + var4.length() + 3;
         }

         return var3;
      }
   }

   protected int estimateParametersLen(NameValuePair[] var1) {
      int var2 = 0;
      if (var1 != null && var1.length >= 1) {
         int var3;
         for(var3 = (var1.length - 1) * 2; var2 < var1.length; ++var2) {
            var3 += this.estimateNameValuePairLen(var1[var2]);
         }

         return var3;
      } else {
         return 0;
      }
   }

   public CharArrayBuffer formatElements(CharArrayBuffer var1, HeaderElement[] var2, boolean var3) {
      if (var2 != null) {
         int var4 = this.estimateElementsLen(var2);
         if (var1 == null) {
            var1 = new CharArrayBuffer(var4);
         } else {
            var1.ensureCapacity(var4);
         }

         for(var4 = 0; var4 < var2.length; ++var4) {
            if (var4 > 0) {
               var1.append(", ");
            }

            this.formatHeaderElement(var1, var2[var4], var3);
         }

         return var1;
      } else {
         throw new IllegalArgumentException("Header element array must not be null.");
      }
   }

   public CharArrayBuffer formatHeaderElement(CharArrayBuffer var1, HeaderElement var2, boolean var3) {
      if (var2 != null) {
         int var4 = this.estimateHeaderElementLen(var2);
         if (var1 == null) {
            var1 = new CharArrayBuffer(var4);
         } else {
            var1.ensureCapacity(var4);
         }

         var1.append(var2.getName());
         String var5 = var2.getValue();
         if (var5 != null) {
            var1.append('=');
            this.doFormatValue(var1, var5, var3);
         }

         int var6 = var2.getParameterCount();
         if (var6 > 0) {
            for(var4 = 0; var4 < var6; ++var4) {
               var1.append("; ");
               this.formatNameValuePair(var1, var2.getParameter(var4), var3);
            }
         }

         return var1;
      } else {
         throw new IllegalArgumentException("Header element must not be null.");
      }
   }

   public CharArrayBuffer formatNameValuePair(CharArrayBuffer var1, NameValuePair var2, boolean var3) {
      if (var2 != null) {
         int var4 = this.estimateNameValuePairLen(var2);
         if (var1 == null) {
            var1 = new CharArrayBuffer(var4);
         } else {
            var1.ensureCapacity(var4);
         }

         var1.append(var2.getName());
         String var5 = var2.getValue();
         if (var5 != null) {
            var1.append('=');
            this.doFormatValue(var1, var5, var3);
         }

         return var1;
      } else {
         throw new IllegalArgumentException("NameValuePair must not be null.");
      }
   }

   public CharArrayBuffer formatParameters(CharArrayBuffer var1, NameValuePair[] var2, boolean var3) {
      if (var2 != null) {
         int var4 = this.estimateParametersLen(var2);
         if (var1 == null) {
            var1 = new CharArrayBuffer(var4);
         } else {
            var1.ensureCapacity(var4);
         }

         for(var4 = 0; var4 < var2.length; ++var4) {
            if (var4 > 0) {
               var1.append("; ");
            }

            this.formatNameValuePair(var1, var2[var4], var3);
         }

         return var1;
      } else {
         throw new IllegalArgumentException("Parameters must not be null.");
      }
   }

   protected boolean isSeparator(char var1) {
      boolean var2;
      if (" ;,:@()<>\\\"/[]?={}\t".indexOf(var1) >= 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   protected boolean isUnsafe(char var1) {
      boolean var2;
      if ("\"\\".indexOf(var1) >= 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }
}
