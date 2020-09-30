package javax.mail.internet;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.mail.Address;
import javax.mail.Session;

public class InternetAddress extends Address implements Cloneable {
   private static final String rfc822phrase = "()<>@,;:\\\"\t .[]".replace(' ', '\u0000').replace('\t', '\u0000');
   private static final long serialVersionUID = -7507595530758302903L;
   private static final String specialsNoDot = "()<>,;:\\\"[]@";
   private static final String specialsNoDotNoAt = "()<>,;:\\\"[]";
   protected String address;
   protected String encodedPersonal;
   protected String personal;

   public InternetAddress() {
   }

   public InternetAddress(String var1) throws AddressException {
      InternetAddress[] var2 = parse(var1, true);
      if (var2.length == 1) {
         this.address = var2[0].address;
         this.personal = var2[0].personal;
         this.encodedPersonal = var2[0].encodedPersonal;
      } else {
         throw new AddressException("Illegal address", var1);
      }
   }

   public InternetAddress(String var1, String var2) throws UnsupportedEncodingException {
      this(var1, var2, (String)null);
   }

   public InternetAddress(String var1, String var2, String var3) throws UnsupportedEncodingException {
      this.address = var1;
      this.setPersonal(var2, var3);
   }

   public InternetAddress(String var1, boolean var2) throws AddressException {
      this(var1);
      if (var2) {
         checkAddress(this.address, true, true);
      }

   }

   private static void checkAddress(String var0, boolean var1, boolean var2) throws AddressException {
      if (var0.indexOf(34) < 0) {
         int var3 = 0;
         byte var4 = 0;
         int var7;
         if (var1) {
            var3 = var4;

            while(true) {
               var7 = indexOfAny(var0, ",:", var3);
               if (var7 < 0) {
                  break;
               }

               if (var0.charAt(var3) != '@') {
                  throw new AddressException("Illegal route-addr", var0);
               }

               if (var0.charAt(var7) == ':') {
                  var3 = var7 + 1;
                  break;
               }

               var3 = var7 + 1;
            }
         }

         var7 = var0.indexOf(64, var3);
         String var5;
         String var6;
         if (var7 >= 0) {
            if (var7 == var3) {
               throw new AddressException("Missing local name", var0);
            }

            if (var7 == var0.length() - 1) {
               throw new AddressException("Missing domain", var0);
            }

            var5 = var0.substring(var3, var7);
            var6 = var0.substring(var7 + 1);
         } else {
            if (var2) {
               throw new AddressException("Missing final '@domain'", var0);
            }

            var6 = null;
            var5 = var0;
         }

         if (indexOfAny(var0, " \t\n\r") < 0) {
            if (indexOfAny(var5, "()<>,;:\\\"[]@") < 0) {
               if (var6 != null && var6.indexOf(91) < 0 && indexOfAny(var6, "()<>,;:\\\"[]@") >= 0) {
                  throw new AddressException("Illegal character in domain", var0);
               }
            } else {
               throw new AddressException("Illegal character in local name", var0);
            }
         } else {
            throw new AddressException("Illegal whitespace in address", var0);
         }
      }
   }

   public static InternetAddress getLocalAddress(Session var0) {
      String var1;
      boolean var10001;
      String var2;
      String var19;
      String var3;
      if (var0 == null) {
         try {
            var1 = System.getProperty("user.name");
            var19 = InetAddress.getLocalHost().getHostName();
         } catch (AddressException | UnknownHostException | SecurityException var15) {
            var10001 = false;
            return null;
         }

         var2 = null;
      } else {
         try {
            var2 = var0.getProperty("mail.from");
         } catch (AddressException | UnknownHostException | SecurityException var14) {
            var10001 = false;
            return null;
         }

         if (var2 == null) {
            label138: {
               try {
                  var3 = var0.getProperty("mail.user");
               } catch (AddressException | UnknownHostException | SecurityException var13) {
                  var10001 = false;
                  return null;
               }

               label125: {
                  if (var3 != null) {
                     var1 = var3;

                     try {
                        if (var3.length() != 0) {
                           break label125;
                        }
                     } catch (AddressException | UnknownHostException | SecurityException var18) {
                        var10001 = false;
                        return null;
                     }
                  }

                  try {
                     var1 = var0.getProperty("user.name");
                  } catch (AddressException | UnknownHostException | SecurityException var12) {
                     var10001 = false;
                     return null;
                  }
               }

               label144: {
                  if (var1 != null) {
                     label114:
                     try {
                        if (var1.length() == 0) {
                           break label114;
                        }
                        break label144;
                     } catch (AddressException | UnknownHostException | SecurityException var17) {
                        var10001 = false;
                        return null;
                     }
                  }

                  try {
                     var1 = System.getProperty("user.name");
                  } catch (AddressException | UnknownHostException | SecurityException var11) {
                     var10001 = false;
                     return null;
                  }
               }

               try {
                  var3 = var0.getProperty("mail.host");
               } catch (AddressException | UnknownHostException | SecurityException var10) {
                  var10001 = false;
                  return null;
               }

               if (var3 != null) {
                  var19 = var3;

                  try {
                     if (var3.length() != 0) {
                        break label138;
                     }
                  } catch (AddressException | UnknownHostException | SecurityException var16) {
                     var10001 = false;
                     return null;
                  }
               }

               InetAddress var4;
               try {
                  var4 = InetAddress.getLocalHost();
               } catch (AddressException | UnknownHostException | SecurityException var9) {
                  var10001 = false;
                  return null;
               }

               var19 = var3;
               if (var4 != null) {
                  try {
                     var19 = var4.getHostName();
                  } catch (AddressException | UnknownHostException | SecurityException var8) {
                     var10001 = false;
                     return null;
                  }
               }
            }
         } else {
            var1 = null;
            var19 = var1;
         }
      }

      var3 = var2;
      if (var2 == null) {
         var3 = var2;
         if (var1 != null) {
            label140: {
               var3 = var2;

               try {
                  if (var1.length() == 0) {
                     break label140;
                  }
               } catch (AddressException | UnknownHostException | SecurityException var7) {
                  var10001 = false;
                  return null;
               }

               var3 = var2;
               if (var19 != null) {
                  var3 = var2;

                  try {
                     if (var19.length() != 0) {
                        StringBuilder var21 = new StringBuilder(String.valueOf(var1));
                        var21.append("@");
                        var21.append(var19);
                        var3 = var21.toString();
                     }
                  } catch (AddressException | UnknownHostException | SecurityException var6) {
                     var10001 = false;
                     return null;
                  }
               }
            }
         }
      }

      if (var3 != null) {
         try {
            InternetAddress var20 = new InternetAddress(var3);
            return var20;
         } catch (AddressException | UnknownHostException | SecurityException var5) {
            var10001 = false;
         }
      }

      return null;
   }

   private static int indexOfAny(String var0, String var1) {
      return indexOfAny(var0, var1, 0);
   }

   private static int indexOfAny(String param0, String param1, int param2) {
      // $FF: Couldn't be decompiled
   }

   private boolean isSimple() {
      String var1 = this.address;
      return var1 == null || indexOfAny(var1, "()<>,;:\\\"[]") < 0;
   }

   private static int lengthOfFirstSegment(String var0) {
      int var1 = var0.indexOf("\r\n");
      return var1 != -1 ? var1 : var0.length();
   }

   private static int lengthOfLastSegment(String var0, int var1) {
      int var2 = var0.lastIndexOf("\r\n");
      return var2 != -1 ? var0.length() - var2 - 2 : var0.length() + var1;
   }

   public static InternetAddress[] parse(String var0) throws AddressException {
      return parse(var0, true);
   }

   public static InternetAddress[] parse(String var0, boolean var1) throws AddressException {
      return parse(var0, var1, false);
   }

   private static InternetAddress[] parse(String var0, boolean var1, boolean var2) throws AddressException {
      int var3 = var0.length();
      Vector var4 = new Vector();
      int var5 = 0;
      int var6 = -1;
      boolean var7 = false;
      boolean var8 = false;
      int var9 = -1;
      boolean var10 = false;
      int var11 = -1;

      int var12;
      InternetAddress var13;
      String var22;
      InternetAddress var24;
      for(var12 = -1; var5 < var3; ++var5) {
         char var15 = var0.charAt(var5);
         if (var15 != '\t' && var15 != '\n' && var15 != '\r' && var15 != ' ') {
            char var20;
            int var21;
            if (var15 != '"') {
               int var27;
               if (var15 != ',') {
                  if (var15 == '>') {
                     throw new AddressException("Missing '<'", var0, var5);
                  }

                  label260: {
                     int var17;
                     if (var15 != '[') {
                        if (var15 != '(') {
                           if (var15 == ')') {
                              throw new AddressException("Missing '('", var0, var5);
                           }

                           switch(var15) {
                           case ':':
                              if (var7) {
                                 throw new AddressException("Nested group", var0, var5);
                              }

                              var21 = var6;
                              if (var6 == -1) {
                                 var21 = var5;
                              }

                              var7 = true;
                              var6 = var21;
                              break label260;
                           case ';':
                              var9 = var6;
                              if (var6 == -1) {
                                 var9 = var5;
                              }

                              if (!var7) {
                                 throw new AddressException("Illegal semicolon, not in group", var0, var5);
                              }

                              var6 = var9;
                              if (var9 == -1) {
                                 var6 = var5;
                              }

                              var13 = new InternetAddress();
                              var13.setAddress(var0.substring(var6, var5 + 1).trim());
                              var4.addElement(var13);
                              var6 = -1;
                              var7 = false;
                              var8 = false;
                              var9 = -1;
                              continue;
                           case '<':
                              if (var8) {
                                 throw new AddressException("Extra route-addr", var0, var5);
                              }

                              var9 = var6;
                              var21 = var12;
                              if (!var7) {
                                 if (var6 >= 0) {
                                    var12 = var5;
                                 }

                                 var11 = var6;
                                 var9 = var5 + 1;
                                 var21 = var12;
                              }

                              ++var5;

                              boolean var23;
                              for(var23 = false; var5 < var3; ++var5) {
                                 var20 = var0.charAt(var5);
                                 if (var20 != '"') {
                                    if (var20 != '>') {
                                       if (var20 == '\\') {
                                          ++var5;
                                       }
                                    } else if (!var23) {
                                       break;
                                    }
                                 } else {
                                    var23 ^= true;
                                 }
                              }

                              if (var5 >= var3) {
                                 if (var23) {
                                    throw new AddressException("Missing '\"'", var0, var5);
                                 }

                                 throw new AddressException("Missing '>'", var0, var5);
                              }

                              var8 = true;
                              var6 = var9;
                              var9 = var5;
                              var12 = var21;
                              break label260;
                           default:
                              if (var6 == -1) {
                                 var6 = var5;
                              }
                              continue;
                           }
                        }

                        var21 = var9;
                        if (var6 >= 0) {
                           var21 = var9;
                           if (var9 == -1) {
                              var21 = var5;
                           }
                        }

                        var9 = var11;
                        if (var11 == -1) {
                           var9 = var5 + 1;
                        }

                        ++var5;

                        for(var11 = 1; var5 < var3 && var11 > 0; ++var5) {
                           var15 = var0.charAt(var5);
                           if (var15 != '(') {
                              if (var15 != ')') {
                                 if (var15 == '\\') {
                                    ++var5;
                                 }
                              } else {
                                 --var11;
                              }
                           } else {
                              ++var11;
                           }
                        }

                        if (var11 > 0) {
                           throw new AddressException("Missing ')'", var0, var5);
                        }

                        int var16 = var5 - 1;
                        var5 = var16;
                        var17 = var21;
                        var11 = var9;
                        var27 = var12;
                        if (var12 == -1) {
                           var27 = var16;
                           var5 = var16;
                           var17 = var21;
                           var11 = var9;
                        }
                     } else {
                        ++var5;

                        for(; var5 < var3; ++var5) {
                           char var28 = var0.charAt(var5);
                           if (var28 != '\\') {
                              if (var28 == ']') {
                                 break;
                              }
                           } else {
                              ++var5;
                           }
                        }

                        if (var5 >= var3) {
                           throw new AddressException("Missing ']'", var0, var5);
                        }

                        var27 = var12;
                        var17 = var9;
                     }

                     var9 = var17;
                     var12 = var27;
                  }

                  var10 = true;
               } else if (var6 == -1) {
                  var6 = -1;
                  var8 = false;
                  var9 = -1;
                  var10 = false;
               } else if (var7) {
                  var8 = false;
               } else {
                  var27 = var9;
                  if (var9 == -1) {
                     var27 = var5;
                  }

                  String var14 = var0.substring(var6, var27).trim();
                  if (!var10 && !var1 && !var2) {
                     StringTokenizer var18 = new StringTokenizer(var14);

                     while(var18.hasMoreTokens()) {
                        var22 = var18.nextToken();
                        checkAddress(var22, false, false);
                        var24 = new InternetAddress();
                        var24.setAddress(var22);
                        var4.addElement(var24);
                     }
                  } else {
                     if (var1 || !var2) {
                        checkAddress(var14, var8, false);
                     }

                     var13 = new InternetAddress();
                     var13.setAddress(var14);
                     var9 = var11;
                     var6 = var12;
                     if (var11 >= 0) {
                        var13.encodedPersonal = unquote(var0.substring(var11, var12).trim());
                        var9 = -1;
                        var6 = -1;
                     }

                     var4.addElement(var13);
                     var12 = var6;
                     var11 = var9;
                  }

                  var6 = -1;
                  var8 = false;
                  var9 = -1;
                  var10 = false;
               }
            } else {
               var21 = var6;
               if (var6 == -1) {
                  var21 = var5;
               }

               ++var5;

               for(; var5 < var3; ++var5) {
                  var20 = var0.charAt(var5);
                  if (var20 == '"') {
                     break;
                  }

                  if (var20 == '\\') {
                     ++var5;
                  }
               }

               if (var5 >= var3) {
                  throw new AddressException("Missing '\"'", var0, var5);
               }

               boolean var26 = true;
               var6 = var21;
               var10 = var26;
            }
         }
      }

      if (var6 >= 0) {
         if (var9 != -1) {
            var5 = var9;
         }

         var22 = var0.substring(var6, var5).trim();
         if (!var10 && !var1 && !var2) {
            StringTokenizer var25 = new StringTokenizer(var22);

            while(var25.hasMoreTokens()) {
               var0 = var25.nextToken();
               checkAddress(var0, false, false);
               var13 = new InternetAddress();
               var13.setAddress(var0);
               var4.addElement(var13);
            }
         } else {
            if (var1 || !var2) {
               checkAddress(var22, var8, false);
            }

            var24 = new InternetAddress();
            var24.setAddress(var22);
            if (var11 >= 0) {
               var24.encodedPersonal = unquote(var0.substring(var11, var12).trim());
            }

            var4.addElement(var24);
         }
      }

      InternetAddress[] var19 = new InternetAddress[var4.size()];
      var4.copyInto(var19);
      return var19;
   }

   public static InternetAddress[] parseHeader(String var0, boolean var1) throws AddressException {
      return parse(var0, var1, true);
   }

   private static String quotePhrase(String var0) {
      int var1 = var0.length();
      byte var2 = 0;
      int var3 = 0;

      boolean var4;
      StringBuffer var5;
      for(var4 = false; var3 < var1; ++var3) {
         char var6 = var0.charAt(var3);
         if (var6 == '"' || var6 == '\\') {
            var5 = new StringBuffer(var1 + 3);
            var5.append('"');

            for(var3 = var2; var3 < var1; ++var3) {
               char var7 = var0.charAt(var3);
               if (var7 == '"' || var7 == '\\') {
                  var5.append('\\');
               }

               var5.append(var7);
            }

            var5.append('"');
            return var5.toString();
         }

         if (var6 < ' ' && var6 != '\r' && var6 != '\n' && var6 != '\t' || var6 >= 127 || rfc822phrase.indexOf(var6) >= 0) {
            var4 = true;
         }
      }

      String var8 = var0;
      if (var4) {
         var5 = new StringBuffer(var1 + 2);
         var5.append('"');
         var5.append(var0);
         var5.append('"');
         var8 = var5.toString();
      }

      return var8;
   }

   public static String toString(Address[] var0) {
      return toString(var0, 0);
   }

   public static String toString(Address[] var0, int var1) {
      if (var0 != null && var0.length != 0) {
         StringBuffer var2 = new StringBuffer();

         for(int var3 = 0; var3 < var0.length; ++var3) {
            int var4 = var1;
            if (var3 != 0) {
               var2.append(", ");
               var4 = var1 + 2;
            }

            String var5 = var0[var3].toString();
            var1 = var4;
            if (lengthOfFirstSegment(var5) + var4 > 76) {
               var2.append("\r\n\t");
               var1 = 8;
            }

            var2.append(var5);
            var1 = lengthOfLastSegment(var5, var1);
         }

         return var2.toString();
      } else {
         return null;
      }
   }

   private static String unquote(String var0) {
      String var1 = var0;
      if (var0.startsWith("\"")) {
         var1 = var0;
         if (var0.endsWith("\"")) {
            var0 = var0.substring(1, var0.length() - 1);
            var1 = var0;
            if (var0.indexOf(92) >= 0) {
               StringBuffer var6 = new StringBuffer(var0.length());

               int var4;
               for(int var2 = 0; var2 < var0.length(); var2 = var4 + 1) {
                  char var3 = var0.charAt(var2);
                  var4 = var2;
                  char var5 = var3;
                  if (var3 == '\\') {
                     var4 = var2;
                     var5 = var3;
                     if (var2 < var0.length() - 1) {
                        var4 = var2 + 1;
                        char var7 = var0.charAt(var4);
                        var5 = var7;
                     }
                  }

                  var6.append(var5);
               }

               var1 = var6.toString();
            }
         }
      }

      return var1;
   }

   public Object clone() {
      InternetAddress var1;
      try {
         var1 = (InternetAddress)super.clone();
      } catch (CloneNotSupportedException var2) {
         var1 = null;
      }

      return var1;
   }

   public boolean equals(Object var1) {
      if (!(var1 instanceof InternetAddress)) {
         return false;
      } else {
         String var2 = ((InternetAddress)var1).getAddress();
         String var3 = this.address;
         if (var2 == var3) {
            return true;
         } else {
            return var3 != null && var3.equalsIgnoreCase(var2);
         }
      }
   }

   public String getAddress() {
      return this.address;
   }

   public InternetAddress[] getGroup(boolean var1) throws AddressException {
      String var2 = this.getAddress();
      if (!var2.endsWith(";")) {
         return null;
      } else {
         int var3 = var2.indexOf(58);
         return var3 < 0 ? null : parseHeader(var2.substring(var3 + 1, var2.length() - 1), var1);
      }
   }

   public String getPersonal() {
      String var1 = this.personal;
      if (var1 != null) {
         return var1;
      } else {
         var1 = this.encodedPersonal;
         if (var1 != null) {
            try {
               var1 = MimeUtility.decodeText(var1);
               this.personal = var1;
               return var1;
            } catch (Exception var2) {
               return this.encodedPersonal;
            }
         } else {
            return null;
         }
      }
   }

   public String getType() {
      return "rfc822";
   }

   public int hashCode() {
      String var1 = this.address;
      return var1 == null ? 0 : var1.toLowerCase(Locale.ENGLISH).hashCode();
   }

   public boolean isGroup() {
      String var1 = this.address;
      return var1 != null && var1.endsWith(";") && this.address.indexOf(58) > 0;
   }

   public void setAddress(String var1) {
      this.address = var1;
   }

   public void setPersonal(String var1) throws UnsupportedEncodingException {
      this.personal = var1;
      if (var1 != null) {
         this.encodedPersonal = MimeUtility.encodeWord(var1);
      } else {
         this.encodedPersonal = null;
      }

   }

   public void setPersonal(String var1, String var2) throws UnsupportedEncodingException {
      this.personal = var1;
      if (var1 != null) {
         this.encodedPersonal = MimeUtility.encodeWord(var1, var2, (String)null);
      } else {
         this.encodedPersonal = null;
      }

   }

   public String toString() {
      if (this.encodedPersonal == null) {
         String var1 = this.personal;
         if (var1 != null) {
            try {
               this.encodedPersonal = MimeUtility.encodeWord(var1);
            } catch (UnsupportedEncodingException var2) {
            }
         }
      }

      StringBuilder var3;
      if (this.encodedPersonal != null) {
         var3 = new StringBuilder(String.valueOf(quotePhrase(this.encodedPersonal)));
         var3.append(" <");
         var3.append(this.address);
         var3.append(">");
         return var3.toString();
      } else if (!this.isGroup() && !this.isSimple()) {
         var3 = new StringBuilder("<");
         var3.append(this.address);
         var3.append(">");
         return var3.toString();
      } else {
         return this.address;
      }
   }

   public String toUnicodeString() {
      String var1 = this.getPersonal();
      StringBuilder var2;
      if (var1 != null) {
         var2 = new StringBuilder(String.valueOf(quotePhrase(var1)));
         var2.append(" <");
         var2.append(this.address);
         var2.append(">");
         return var2.toString();
      } else if (!this.isGroup() && !this.isSimple()) {
         var2 = new StringBuilder("<");
         var2.append(this.address);
         var2.append(">");
         return var2.toString();
      } else {
         return this.address;
      }
   }

   public void validate() throws AddressException {
      checkAddress(this.getAddress(), true, true);
   }
}
