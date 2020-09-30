package com.google.zxing.client.result;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import java.util.regex.Pattern;

public final class VINResultParser extends ResultParser {
   private static final Pattern AZ09 = Pattern.compile("[A-Z0-9]{17}");
   private static final Pattern IOQ = Pattern.compile("[IOQ]");

   private static char checkChar(int var0) {
      if (var0 < 10) {
         return (char)(var0 + 48);
      } else if (var0 == 10) {
         return 'X';
      } else {
         throw new IllegalArgumentException();
      }
   }

   private static boolean checkChecksum(CharSequence var0) {
      boolean var1 = false;
      int var2 = 0;

      int var3;
      int var4;
      for(var3 = 0; var2 < var0.length(); var2 = var4) {
         var4 = var2 + 1;
         var3 += vinPositionWeight(var4) * vinCharValue(var0.charAt(var2));
      }

      if (var0.charAt(8) == checkChar(var3 % 11)) {
         var1 = true;
      }

      return var1;
   }

   private static String countryCode(CharSequence var0) {
      char var1 = var0.charAt(0);
      char var2 = var0.charAt(1);
      if (var1 != '9') {
         if (var1 != 'S') {
            if (var1 != 'Z') {
               switch(var1) {
               case '1':
               case '4':
               case '5':
                  return "US";
               case '2':
                  return "CA";
               case '3':
                  if (var2 >= 'A' && var2 <= 'W') {
                     return "MX";
                  }
                  break;
               default:
                  switch(var1) {
                  case 'J':
                     if (var2 >= 'A' && var2 <= 'T') {
                        return "JP";
                     }
                     break;
                  case 'K':
                     if (var2 >= 'L' && var2 <= 'R') {
                        return "KO";
                     }
                     break;
                  case 'L':
                     return "CN";
                  case 'M':
                     if (var2 >= 'A' && var2 <= 'E') {
                        return "IN";
                     }
                     break;
                  default:
                     switch(var1) {
                     case 'V':
                        if (var2 >= 'F' && var2 <= 'R') {
                           return "FR";
                        }

                        if (var2 >= 'S' && var2 <= 'W') {
                           return "ES";
                        }
                        break;
                     case 'W':
                        return "DE";
                     case 'X':
                        if (var2 == '0' || var2 >= '3' && var2 <= '9') {
                           return "RU";
                        }
                     }
                  }
               }
            } else if (var2 >= 'A' && var2 <= 'R') {
               return "IT";
            }
         } else {
            if (var2 >= 'A' && var2 <= 'M') {
               return "UK";
            }

            if (var2 >= 'N' && var2 <= 'T') {
               return "DE";
            }
         }
      } else if (var2 >= 'A' && var2 <= 'E' || var2 >= '3' && var2 <= '9') {
         return "BR";
      }

      return null;
   }

   private static int modelYear(char var0) {
      if (var0 >= 'E' && var0 <= 'H') {
         return var0 - 69 + 1984;
      } else if (var0 >= 'J' && var0 <= 'N') {
         return var0 - 74 + 1988;
      } else if (var0 == 'P') {
         return 1993;
      } else if (var0 >= 'R' && var0 <= 'T') {
         return var0 - 82 + 1994;
      } else if (var0 >= 'V' && var0 <= 'Y') {
         return var0 - 86 + 1997;
      } else if (var0 >= '1' && var0 <= '9') {
         return var0 - 49 + 2001;
      } else if (var0 >= 'A' && var0 <= 'D') {
         return var0 - 65 + 2010;
      } else {
         throw new IllegalArgumentException();
      }
   }

   private static int vinCharValue(char var0) {
      if (var0 >= 'A' && var0 <= 'I') {
         return var0 - 65 + 1;
      } else if (var0 >= 'J' && var0 <= 'R') {
         return var0 - 74 + 1;
      } else if (var0 >= 'S' && var0 <= 'Z') {
         return var0 - 83 + 2;
      } else if (var0 >= '0' && var0 <= '9') {
         return var0 - 48;
      } else {
         throw new IllegalArgumentException();
      }
   }

   private static int vinPositionWeight(int var0) {
      if (var0 >= 1 && var0 <= 7) {
         return 9 - var0;
      } else if (var0 == 8) {
         return 10;
      } else if (var0 == 9) {
         return 0;
      } else if (var0 >= 10 && var0 <= 17) {
         return 19 - var0;
      } else {
         throw new IllegalArgumentException();
      }
   }

   public VINParsedResult parse(Result var1) {
      if (var1.getBarcodeFormat() != BarcodeFormat.CODE_39) {
         return null;
      } else {
         String var4 = var1.getText();
         String var2 = IOQ.matcher(var4).replaceAll("").trim();
         if (!AZ09.matcher(var2).matches()) {
            return null;
         } else {
            try {
               if (!checkChecksum(var2)) {
                  return null;
               } else {
                  var4 = var2.substring(0, 3);
                  VINParsedResult var5 = new VINParsedResult(var2, var4, var2.substring(3, 9), var2.substring(9, 17), countryCode(var4), var2.substring(3, 8), modelYear(var2.charAt(9)), var2.charAt(10), var2.substring(11));
                  return var5;
               }
            } catch (IllegalArgumentException var3) {
               return null;
            }
         }
      }
   }
}
