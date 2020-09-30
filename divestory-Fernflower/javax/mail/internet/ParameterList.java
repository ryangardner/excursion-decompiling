package javax.mail.internet;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class ParameterList {
   private static boolean applehack;
   private static boolean decodeParameters;
   private static boolean decodeParametersStrict;
   private static boolean encodeParameters;
   private static final char[] hex;
   private String lastName;
   private Map list;
   private Set multisegmentNames;
   private Map slist;

   static {
      label99: {
         String var0;
         boolean var10001;
         try {
            var0 = System.getProperty("mail.mime.encodeparameters");
         } catch (SecurityException var11) {
            var10001 = false;
            break label99;
         }

         boolean var1;
         boolean var2;
         label92: {
            label91: {
               var1 = true;
               if (var0 != null) {
                  try {
                     if (var0.equalsIgnoreCase("true")) {
                        break label91;
                     }
                  } catch (SecurityException var10) {
                     var10001 = false;
                     break label99;
                  }
               }

               var2 = false;
               break label92;
            }

            var2 = true;
         }

         try {
            encodeParameters = var2;
            var0 = System.getProperty("mail.mime.decodeparameters");
         } catch (SecurityException var9) {
            var10001 = false;
            break label99;
         }

         label80: {
            label79: {
               if (var0 != null) {
                  try {
                     if (var0.equalsIgnoreCase("true")) {
                        break label79;
                     }
                  } catch (SecurityException var8) {
                     var10001 = false;
                     break label99;
                  }
               }

               var2 = false;
               break label80;
            }

            var2 = true;
         }

         try {
            decodeParameters = var2;
            var0 = System.getProperty("mail.mime.decodeparameters.strict");
         } catch (SecurityException var7) {
            var10001 = false;
            break label99;
         }

         label68: {
            label67: {
               if (var0 != null) {
                  try {
                     if (var0.equalsIgnoreCase("true")) {
                        break label67;
                     }
                  } catch (SecurityException var6) {
                     var10001 = false;
                     break label99;
                  }
               }

               var2 = false;
               break label68;
            }

            var2 = true;
         }

         try {
            decodeParametersStrict = var2;
            var0 = System.getProperty("mail.mime.applefilenames");
         } catch (SecurityException var5) {
            var10001 = false;
            break label99;
         }

         label56: {
            label55: {
               if (var0 != null) {
                  try {
                     if (var0.equalsIgnoreCase("true")) {
                        break label55;
                     }
                  } catch (SecurityException var4) {
                     var10001 = false;
                     break label99;
                  }
               }

               var2 = false;
               break label56;
            }

            var2 = var1;
         }

         try {
            applehack = var2;
         } catch (SecurityException var3) {
            var10001 = false;
         }
      }

      hex = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
   }

   public ParameterList() {
      this.list = new LinkedHashMap();
      this.lastName = null;
      if (decodeParameters) {
         this.multisegmentNames = new HashSet();
         this.slist = new HashMap();
      }

   }

   public ParameterList(String var1) throws ParseException {
      this();
      HeaderTokenizer var5 = new HeaderTokenizer(var1, "()<>@,;:\\\"\t []/?=");

      while(true) {
         HeaderTokenizer.Token var2 = var5.next();
         int var3 = var2.getType();
         if (var3 != -4) {
            StringBuilder var6;
            String var7;
            String var8;
            if ((char)var3 != ';') {
               if (applehack && var3 == -1) {
                  var8 = this.lastName;
                  if (var8 != null && (var8.equals("name") || this.lastName.equals("filename"))) {
                     StringBuilder var9 = new StringBuilder(String.valueOf((String)this.list.get(this.lastName)));
                     var9.append(" ");
                     var9.append(var2.getValue());
                     var7 = var9.toString();
                     this.list.put(this.lastName, var7);
                     continue;
                  }
               }

               var6 = new StringBuilder("Expected ';', got \"");
               var6.append(var2.getValue());
               var6.append("\"");
               throw new ParseException(var6.toString());
            }

            var2 = var5.next();
            if (var2.getType() != -4) {
               if (var2.getType() != -1) {
                  var6 = new StringBuilder("Expected parameter name, got \"");
                  var6.append(var2.getValue());
                  var6.append("\"");
                  throw new ParseException(var6.toString());
               }

               var7 = var2.getValue().toLowerCase(Locale.ENGLISH);
               HeaderTokenizer.Token var4 = var5.next();
               if ((char)var4.getType() != '=') {
                  var6 = new StringBuilder("Expected '=', got \"");
                  var6.append(var4.getValue());
                  var6.append("\"");
                  throw new ParseException(var6.toString());
               }

               var4 = var5.next();
               var3 = var4.getType();
               if (var3 != -1 && var3 != -2) {
                  var6 = new StringBuilder("Expected parameter value, got \"");
                  var6.append(var4.getValue());
                  var6.append("\"");
                  throw new ParseException(var6.toString());
               }

               var8 = var4.getValue();
               this.lastName = var7;
               if (decodeParameters) {
                  this.putEncodedName(var7, var8);
               } else {
                  this.list.put(var7, var8);
               }
               continue;
            }
         }

         if (decodeParameters) {
            this.combineMultisegmentNames(false);
         }

         return;
      }
   }

   private void combineMultisegmentNames(boolean param1) throws ParseException {
      // $FF: Couldn't be decompiled
   }

   private static String decodeBytes(String var0, String var1) throws UnsupportedEncodingException {
      byte[] var2 = new byte[var0.length()];
      int var3 = 0;

      int var4;
      for(var4 = 0; var3 < var0.length(); ++var4) {
         char var5 = var0.charAt(var3);
         int var6 = var3;
         char var7 = var5;
         if (var5 == '%') {
            var7 = (char)Integer.parseInt(var0.substring(var3 + 1, var3 + 3), 16);
            var6 = var3 + 2;
         }

         var2[var4] = (byte)((byte)var7);
         var3 = var6 + 1;
      }

      return new String(var2, 0, var4, MimeUtility.javaCharset(var1));
   }

   private static ParameterList.Value decodeValue(String var0) throws ParseException {
      ParameterList.Value var1 = new ParameterList.Value((ParameterList.Value)null);
      var1.encodedValue = var0;
      var1.value = var0;

      StringIndexOutOfBoundsException var10000;
      label131: {
         UnsupportedEncodingException var34;
         label132: {
            NumberFormatException var35;
            label113: {
               int var2;
               boolean var10001;
               try {
                  var2 = var0.indexOf(39);
               } catch (NumberFormatException var27) {
                  var35 = var27;
                  var10001 = false;
                  break label113;
               } catch (UnsupportedEncodingException var28) {
                  var34 = var28;
                  var10001 = false;
                  break label132;
               } catch (StringIndexOutOfBoundsException var29) {
                  var10000 = var29;
                  var10001 = false;
                  break label131;
               }

               ParseException var3;
               StringBuilder var4;
               if (var2 <= 0) {
                  label94: {
                     try {
                        if (!decodeParametersStrict) {
                           return var1;
                        }
                     } catch (NumberFormatException var9) {
                        var35 = var9;
                        var10001 = false;
                        break label94;
                     } catch (UnsupportedEncodingException var10) {
                        var34 = var10;
                        var10001 = false;
                        break label132;
                     } catch (StringIndexOutOfBoundsException var11) {
                        var10000 = var11;
                        var10001 = false;
                        break label131;
                     }

                     try {
                        var4 = new StringBuilder("Missing charset in encoded value: ");
                        var4.append(var0);
                        var3 = new ParseException(var4.toString());
                        throw var3;
                     } catch (NumberFormatException var6) {
                        var35 = var6;
                        var10001 = false;
                     } catch (UnsupportedEncodingException var7) {
                        var34 = var7;
                        var10001 = false;
                        break label132;
                     } catch (StringIndexOutOfBoundsException var8) {
                        var10000 = var8;
                        var10001 = false;
                        break label131;
                     }
                  }
               } else {
                  label120: {
                     String var33;
                     try {
                        var33 = var0.substring(0, var2);
                     } catch (NumberFormatException var24) {
                        var35 = var24;
                        var10001 = false;
                        break label120;
                     } catch (UnsupportedEncodingException var25) {
                        var34 = var25;
                        var10001 = false;
                        break label132;
                     } catch (StringIndexOutOfBoundsException var26) {
                        var10000 = var26;
                        var10001 = false;
                        break label131;
                     }

                     int var5 = var2 + 1;

                     try {
                        var2 = var0.indexOf(39, var5);
                     } catch (NumberFormatException var21) {
                        var35 = var21;
                        var10001 = false;
                        break label120;
                     } catch (UnsupportedEncodingException var22) {
                        var34 = var22;
                        var10001 = false;
                        break label132;
                     } catch (StringIndexOutOfBoundsException var23) {
                        var10000 = var23;
                        var10001 = false;
                        break label131;
                     }

                     if (var2 < 0) {
                        label99: {
                           try {
                              if (!decodeParametersStrict) {
                                 return var1;
                              }
                           } catch (NumberFormatException var15) {
                              var35 = var15;
                              var10001 = false;
                              break label99;
                           } catch (UnsupportedEncodingException var16) {
                              var34 = var16;
                              var10001 = false;
                              break label132;
                           } catch (StringIndexOutOfBoundsException var17) {
                              var10000 = var17;
                              var10001 = false;
                              break label131;
                           }

                           try {
                              var4 = new StringBuilder("Missing language in encoded value: ");
                              var4.append(var0);
                              var3 = new ParseException(var4.toString());
                              throw var3;
                           } catch (NumberFormatException var12) {
                              var35 = var12;
                              var10001 = false;
                           } catch (UnsupportedEncodingException var13) {
                              var34 = var13;
                              var10001 = false;
                              break label132;
                           } catch (StringIndexOutOfBoundsException var14) {
                              var10000 = var14;
                              var10001 = false;
                              break label131;
                           }
                        }
                     } else {
                        try {
                           var0.substring(var5, var2);
                           var0 = var0.substring(var2 + 1);
                           var1.charset = var33;
                           var1.value = decodeBytes(var0, var33);
                           return var1;
                        } catch (NumberFormatException var18) {
                           var35 = var18;
                           var10001 = false;
                        } catch (UnsupportedEncodingException var19) {
                           var34 = var19;
                           var10001 = false;
                           break label132;
                        } catch (StringIndexOutOfBoundsException var20) {
                           var10000 = var20;
                           var10001 = false;
                           break label131;
                        }
                     }
                  }
               }
            }

            NumberFormatException var32 = var35;
            if (decodeParametersStrict) {
               throw new ParseException(var32.toString());
            }

            return var1;
         }

         UnsupportedEncodingException var31 = var34;
         if (decodeParametersStrict) {
            throw new ParseException(var31.toString());
         }

         return var1;
      }

      StringIndexOutOfBoundsException var30 = var10000;
      if (decodeParametersStrict) {
         throw new ParseException(var30.toString());
      } else {
         return var1;
      }
   }

   private static ParameterList.Value encodeValue(String var0, String var1) {
      if (MimeUtility.checkAscii(var0) == 1) {
         return null;
      } else {
         byte[] var2;
         try {
            var2 = var0.getBytes(MimeUtility.javaCharset(var1));
         } catch (UnsupportedEncodingException var6) {
            return null;
         }

         StringBuffer var3 = new StringBuffer(var2.length + var1.length() + 2);
         var3.append(var1);
         var3.append("''");

         for(int var4 = 0; var4 < var2.length; ++var4) {
            char var5 = (char)(var2[var4] & 255);
            if (var5 > ' ' && var5 < 127 && var5 != '*' && var5 != '\'' && var5 != '%' && "()<>@,;:\\\"\t []/?=".indexOf(var5) < 0) {
               var3.append(var5);
            } else {
               var3.append('%');
               var3.append(hex[var5 >> 4]);
               var3.append(hex[var5 & 15]);
            }
         }

         ParameterList.Value var7 = new ParameterList.Value((ParameterList.Value)null);
         var7.charset = var1;
         var7.value = var0;
         var7.encodedValue = var3.toString();
         return var7;
      }
   }

   private void putEncodedName(String var1, String var2) throws ParseException {
      int var3 = var1.indexOf(42);
      if (var3 < 0) {
         this.list.put(var1, var2);
      } else if (var3 == var1.length() - 1) {
         var1 = var1.substring(0, var3);
         this.list.put(var1, decodeValue(var2));
      } else {
         String var4 = var1.substring(0, var3);
         this.multisegmentNames.add(var4);
         this.list.put(var4, "");
         String var5 = var1;
         Object var6 = var2;
         if (var1.endsWith("*")) {
            var6 = new ParameterList.Value((ParameterList.Value)null);
            ParameterList.Value var7 = (ParameterList.Value)var6;
            var7.encodedValue = var2;
            var7.value = var2;
            var5 = var1.substring(0, var1.length() - 1);
         }

         this.slist.put(var5, var6);
      }

   }

   private static String quote(String var0) {
      return MimeUtility.quote(var0, "()<>@,;:\\\"\t []/?=");
   }

   public String get(String var1) {
      Object var2 = this.list.get(var1.trim().toLowerCase(Locale.ENGLISH));
      if (var2 instanceof ParameterList.MultiValue) {
         var1 = ((ParameterList.MultiValue)var2).value;
      } else if (var2 instanceof ParameterList.Value) {
         var1 = ((ParameterList.Value)var2).value;
      } else {
         var1 = (String)var2;
      }

      return var1;
   }

   public Enumeration getNames() {
      return new ParameterList.ParamEnum(this.list.keySet().iterator());
   }

   public void remove(String var1) {
      this.list.remove(var1.trim().toLowerCase(Locale.ENGLISH));
   }

   public void set(String var1, String var2) {
      if (var1 == null && var2 != null && var2.equals("DONE")) {
         if (decodeParameters && this.multisegmentNames.size() > 0) {
            try {
               this.combineMultisegmentNames(true);
            } catch (ParseException var4) {
            }
         }

      } else {
         var1 = var1.trim().toLowerCase(Locale.ENGLISH);
         if (decodeParameters) {
            try {
               this.putEncodedName(var1, var2);
            } catch (ParseException var5) {
               this.list.put(var1, var2);
            }
         } else {
            this.list.put(var1, var2);
         }

      }
   }

   public void set(String var1, String var2, String var3) {
      if (encodeParameters) {
         ParameterList.Value var4 = encodeValue(var2, var3);
         if (var4 != null) {
            this.list.put(var1.trim().toLowerCase(Locale.ENGLISH), var4);
         } else {
            this.set(var1, var2);
         }
      } else {
         this.set(var1, var2);
      }

   }

   public int size() {
      return this.list.size();
   }

   public String toString() {
      return this.toString(0);
   }

   public String toString(int var1) {
      ParameterList.ToStringBuffer var2 = new ParameterList.ToStringBuffer(var1);
      Iterator var3 = this.list.keySet().iterator();

      while(true) {
         while(var3.hasNext()) {
            String var4 = (String)var3.next();
            Object var5 = this.list.get(var4);
            StringBuilder var8;
            if (var5 instanceof ParameterList.MultiValue) {
               ParameterList.MultiValue var9 = (ParameterList.MultiValue)var5;
               var8 = new StringBuilder(String.valueOf(var4));
               var8.append("*");
               var4 = var8.toString();

               for(var1 = 0; var1 < var9.size(); ++var1) {
                  Object var6 = var9.get(var1);
                  StringBuilder var7;
                  if (var6 instanceof ParameterList.Value) {
                     var7 = new StringBuilder(String.valueOf(var4));
                     var7.append(var1);
                     var7.append("*");
                     var2.addNV(var7.toString(), ((ParameterList.Value)var6).encodedValue);
                  } else {
                     var7 = new StringBuilder(String.valueOf(var4));
                     var7.append(var1);
                     var2.addNV(var7.toString(), (String)var6);
                  }
               }
            } else if (var5 instanceof ParameterList.Value) {
               var8 = new StringBuilder(String.valueOf(var4));
               var8.append("*");
               var2.addNV(var8.toString(), ((ParameterList.Value)var5).encodedValue);
            } else {
               var2.addNV(var4, (String)var5);
            }
         }

         return var2.toString();
      }
   }

   private static class MultiValue extends ArrayList {
      String value;

      private MultiValue() {
      }

      // $FF: synthetic method
      MultiValue(ParameterList.MultiValue var1) {
         this();
      }
   }

   private static class ParamEnum implements Enumeration {
      private Iterator it;

      ParamEnum(Iterator var1) {
         this.it = var1;
      }

      public boolean hasMoreElements() {
         return this.it.hasNext();
      }

      public Object nextElement() {
         return this.it.next();
      }
   }

   private static class ToStringBuffer {
      private StringBuffer sb = new StringBuffer();
      private int used;

      public ToStringBuffer(int var1) {
         this.used = var1;
      }

      public void addNV(String var1, String var2) {
         String var3 = ParameterList.quote(var2);
         this.sb.append("; ");
         this.used += 2;
         int var4 = var1.length();
         int var5 = var3.length();
         if (this.used + var4 + var5 + 1 > 76) {
            this.sb.append("\r\n\t");
            this.used = 8;
         }

         StringBuffer var6 = this.sb;
         var6.append(var1);
         var6.append('=');
         var4 = this.used + var1.length() + 1;
         this.used = var4;
         if (var4 + var3.length() > 76) {
            var1 = MimeUtility.fold(this.used, var3);
            this.sb.append(var1);
            var4 = var1.lastIndexOf(10);
            if (var4 >= 0) {
               this.used += var1.length() - var4 - 1;
            } else {
               this.used += var1.length();
            }
         } else {
            this.sb.append(var3);
            this.used += var3.length();
         }

      }

      public String toString() {
         return this.sb.toString();
      }
   }

   private static class Value {
      String charset;
      String encodedValue;
      String value;

      private Value() {
      }

      // $FF: synthetic method
      Value(ParameterList.Value var1) {
         this();
      }
   }
}
