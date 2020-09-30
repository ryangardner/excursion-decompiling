package androidx.core.text.util;

import java.util.Locale;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class FindAddress {
   private static final String HOUSE_COMPONENT = "(?:one|[0-9]+([a-z](?=[^a-z]|$)|st|nd|rd|th)?)";
   private static final String HOUSE_END = "(?=[,\"'\t                　\n\u000b\f\r\u0085\u2028\u2029]|$)";
   private static final String HOUSE_POST_DELIM = ",\"'\t                　\n\u000b\f\r\u0085\u2028\u2029";
   private static final String HOUSE_PRE_DELIM = ":,\"'\t                　\n\u000b\f\r\u0085\u2028\u2029";
   private static final int MAX_ADDRESS_LINES = 5;
   private static final int MAX_ADDRESS_WORDS = 14;
   private static final int MAX_LOCATION_NAME_DISTANCE = 5;
   private static final int MIN_ADDRESS_WORDS = 4;
   private static final String NL = "\n\u000b\f\r\u0085\u2028\u2029";
   private static final String SP = "\t                　";
   private static final String WORD_DELIM = ",*•\t                　\n\u000b\f\r\u0085\u2028\u2029";
   private static final String WORD_END = "(?=[,*•\t                　\n\u000b\f\r\u0085\u2028\u2029]|$)";
   private static final String WS = "\t                　\n\u000b\f\r\u0085\u2028\u2029";
   private static final int kMaxAddressNameWordLength = 25;
   private static final Pattern sHouseNumberRe = Pattern.compile("(?:one|[0-9]+([a-z](?=[^a-z]|$)|st|nd|rd|th)?)(?:-(?:one|[0-9]+([a-z](?=[^a-z]|$)|st|nd|rd|th)?))*(?=[,\"'\t                　\n\u000b\f\r\u0085\u2028\u2029]|$)", 2);
   private static final Pattern sLocationNameRe = Pattern.compile("(?:alley|annex|arcade|ave[.]?|avenue|alameda|bayou|beach|bend|bluffs?|bottom|boulevard|branch|bridge|brooks?|burgs?|bypass|broadway|camino|camp|canyon|cape|causeway|centers?|circles?|cliffs?|club|common|corners?|course|courts?|coves?|creek|crescent|crest|crossing|crossroad|curve|circulo|dale|dam|divide|drives?|estates?|expressway|extensions?|falls?|ferry|fields?|flats?|fords?|forest|forges?|forks?|fort|freeway|gardens?|gateway|glens?|greens?|groves?|harbors?|haven|heights|highway|hills?|hollow|inlet|islands?|isle|junctions?|keys?|knolls?|lakes?|land|landing|lane|lights?|loaf|locks?|lodge|loop|mall|manors?|meadows?|mews|mills?|mission|motorway|mount|mountains?|neck|orchard|oval|overpass|parks?|parkways?|pass|passage|path|pike|pines?|plains?|plaza|points?|ports?|prairie|privada|radial|ramp|ranch|rapids?|rd[.]?|rest|ridges?|river|roads?|route|row|rue|run|shoals?|shores?|skyway|springs?|spurs?|squares?|station|stravenue|stream|st[.]?|streets?|summit|speedway|terrace|throughway|trace|track|trafficway|trail|tunnel|turnpike|underpass|unions?|valleys?|viaduct|views?|villages?|ville|vista|walks?|wall|ways?|wells?|xing|xrd)(?=[,*•\t                　\n\u000b\f\r\u0085\u2028\u2029]|$)", 2);
   private static final Pattern sStateRe = Pattern.compile("(?:(ak|alaska)|(al|alabama)|(ar|arkansas)|(as|american[\t                　]+samoa)|(az|arizona)|(ca|california)|(co|colorado)|(ct|connecticut)|(dc|district[\t                　]+of[\t                　]+columbia)|(de|delaware)|(fl|florida)|(fm|federated[\t                　]+states[\t                　]+of[\t                　]+micronesia)|(ga|georgia)|(gu|guam)|(hi|hawaii)|(ia|iowa)|(id|idaho)|(il|illinois)|(in|indiana)|(ks|kansas)|(ky|kentucky)|(la|louisiana)|(ma|massachusetts)|(md|maryland)|(me|maine)|(mh|marshall[\t                　]+islands)|(mi|michigan)|(mn|minnesota)|(mo|missouri)|(mp|northern[\t                　]+mariana[\t                　]+islands)|(ms|mississippi)|(mt|montana)|(nc|north[\t                　]+carolina)|(nd|north[\t                　]+dakota)|(ne|nebraska)|(nh|new[\t                　]+hampshire)|(nj|new[\t                　]+jersey)|(nm|new[\t                　]+mexico)|(nv|nevada)|(ny|new[\t                　]+york)|(oh|ohio)|(ok|oklahoma)|(or|oregon)|(pa|pennsylvania)|(pr|puerto[\t                　]+rico)|(pw|palau)|(ri|rhode[\t                　]+island)|(sc|south[\t                　]+carolina)|(sd|south[\t                　]+dakota)|(tn|tennessee)|(tx|texas)|(ut|utah)|(va|virginia)|(vi|virgin[\t                　]+islands)|(vt|vermont)|(wa|washington)|(wi|wisconsin)|(wv|west[\t                　]+virginia)|(wy|wyoming))(?=[,*•\t                　\n\u000b\f\r\u0085\u2028\u2029]|$)", 2);
   private static final FindAddress.ZipRange[] sStateZipCodeRanges = new FindAddress.ZipRange[]{new FindAddress.ZipRange(99, 99, -1, -1), new FindAddress.ZipRange(35, 36, -1, -1), new FindAddress.ZipRange(71, 72, -1, -1), new FindAddress.ZipRange(96, 96, -1, -1), new FindAddress.ZipRange(85, 86, -1, -1), new FindAddress.ZipRange(90, 96, -1, -1), new FindAddress.ZipRange(80, 81, -1, -1), new FindAddress.ZipRange(6, 6, -1, -1), new FindAddress.ZipRange(20, 20, -1, -1), new FindAddress.ZipRange(19, 19, -1, -1), new FindAddress.ZipRange(32, 34, -1, -1), new FindAddress.ZipRange(96, 96, -1, -1), new FindAddress.ZipRange(30, 31, -1, -1), new FindAddress.ZipRange(96, 96, -1, -1), new FindAddress.ZipRange(96, 96, -1, -1), new FindAddress.ZipRange(50, 52, -1, -1), new FindAddress.ZipRange(83, 83, -1, -1), new FindAddress.ZipRange(60, 62, -1, -1), new FindAddress.ZipRange(46, 47, -1, -1), new FindAddress.ZipRange(66, 67, 73, -1), new FindAddress.ZipRange(40, 42, -1, -1), new FindAddress.ZipRange(70, 71, -1, -1), new FindAddress.ZipRange(1, 2, -1, -1), new FindAddress.ZipRange(20, 21, -1, -1), new FindAddress.ZipRange(3, 4, -1, -1), new FindAddress.ZipRange(96, 96, -1, -1), new FindAddress.ZipRange(48, 49, -1, -1), new FindAddress.ZipRange(55, 56, -1, -1), new FindAddress.ZipRange(63, 65, -1, -1), new FindAddress.ZipRange(96, 96, -1, -1), new FindAddress.ZipRange(38, 39, -1, -1), new FindAddress.ZipRange(55, 56, -1, -1), new FindAddress.ZipRange(27, 28, -1, -1), new FindAddress.ZipRange(58, 58, -1, -1), new FindAddress.ZipRange(68, 69, -1, -1), new FindAddress.ZipRange(3, 4, -1, -1), new FindAddress.ZipRange(7, 8, -1, -1), new FindAddress.ZipRange(87, 88, 86, -1), new FindAddress.ZipRange(88, 89, 96, -1), new FindAddress.ZipRange(10, 14, 0, 6), new FindAddress.ZipRange(43, 45, -1, -1), new FindAddress.ZipRange(73, 74, -1, -1), new FindAddress.ZipRange(97, 97, -1, -1), new FindAddress.ZipRange(15, 19, -1, -1), new FindAddress.ZipRange(6, 6, 0, 9), new FindAddress.ZipRange(96, 96, -1, -1), new FindAddress.ZipRange(2, 2, -1, -1), new FindAddress.ZipRange(29, 29, -1, -1), new FindAddress.ZipRange(57, 57, -1, -1), new FindAddress.ZipRange(37, 38, -1, -1), new FindAddress.ZipRange(75, 79, 87, 88), new FindAddress.ZipRange(84, 84, -1, -1), new FindAddress.ZipRange(22, 24, 20, -1), new FindAddress.ZipRange(6, 9, -1, -1), new FindAddress.ZipRange(5, 5, -1, -1), new FindAddress.ZipRange(98, 99, -1, -1), new FindAddress.ZipRange(53, 54, -1, -1), new FindAddress.ZipRange(24, 26, -1, -1), new FindAddress.ZipRange(82, 83, -1, -1)};
   private static final Pattern sSuffixedNumberRe = Pattern.compile("([0-9]+)(st|nd|rd|th)", 2);
   private static final Pattern sWordRe = Pattern.compile("[^,*•\t                　\n\u000b\f\r\u0085\u2028\u2029]+(?=[,*•\t                　\n\u000b\f\r\u0085\u2028\u2029]|$)", 2);
   private static final Pattern sZipCodeRe = Pattern.compile("(?:[0-9]{5}(?:-[0-9]{4})?)(?=[,*•\t                　\n\u000b\f\r\u0085\u2028\u2029]|$)", 2);

   private FindAddress() {
   }

   private static int attemptMatch(String var0, MatchResult var1) {
      int var2 = var1.end();
      Matcher var3 = sWordRe.matcher(var0);
      String var15 = "";
      int var4 = 1;
      int var5 = 1;
      boolean var6 = true;
      boolean var7 = false;
      int var8 = -1;
      int var9 = -1;

      int var10;
      while(true) {
         var10 = var2;
         if (var2 >= var0.length()) {
            break;
         }

         if (!var3.find(var2)) {
            var10 = var0.length();
         } else {
            var10 = var2;
            if (var3.end() - var3.start() <= 25) {
               while(var10 < var3.start()) {
                  var2 = var4;
                  if ("\n\u000b\f\r\u0085\u2028\u2029".indexOf(var0.charAt(var10)) != -1) {
                     var2 = var4 + 1;
                  }

                  ++var10;
                  var4 = var2;
               }

               if (var4 > 5) {
                  break;
               }

               ++var5;
               if (var5 > 14) {
                  break;
               }

               boolean var11;
               int var12;
               int var13;
               boolean var17;
               if (matchHouseNumber(var0, var10) != null) {
                  if (var6 && var4 > 1) {
                     return -var10;
                  }

                  var17 = var6;
                  var11 = var7;
                  var12 = var8;
                  var13 = var9;
                  if (var8 == -1) {
                     var17 = var6;
                     var11 = var7;
                     var12 = var10;
                     var13 = var9;
                  }
               } else if (isValidLocationName(var3.group(0))) {
                  var17 = false;
                  var11 = true;
                  var12 = var8;
                  var13 = var9;
               } else {
                  if (var5 == 5 && !var7) {
                     var10 = var3.end();
                     break;
                  }

                  var2 = var9;
                  if (var7) {
                     var2 = var9;
                     if (var5 > 4) {
                        MatchResult var14 = matchState(var0, var10);
                        var2 = var9;
                        if (var14 != null) {
                           if (var15.equals("et") && var14.group(0).equals("al")) {
                              var10 = var14.end();
                              break;
                           }

                           Matcher var16 = sWordRe.matcher(var0);
                           if (var16.find(var14.end())) {
                              var2 = var9;
                              if (isValidZipCode(var16.group(0), var14)) {
                                 return var16.end();
                              }
                           } else {
                              var2 = var14.end();
                           }
                        }
                     }
                  }

                  boolean var18 = false;
                  var13 = var2;
                  var12 = var8;
                  var11 = var7;
                  var17 = var18;
               }

               var15 = var3.group(0);
               var10 = var3.end();
               var6 = var17;
               var7 = var11;
               var8 = var12;
               var9 = var13;
               var2 = var10;
               continue;
            }

            var10 = var3.end();
         }

         return -var10;
      }

      if (var9 > 0) {
         return var9;
      } else {
         if (var8 > 0) {
            var10 = var8;
         }

         return -var10;
      }
   }

   private static boolean checkHouseNumber(String var0) {
      int var1 = 0;

      int var2;
      int var3;
      for(var2 = 0; var1 < var0.length(); var2 = var3) {
         var3 = var2;
         if (Character.isDigit(var0.charAt(var1))) {
            var3 = var2 + 1;
         }

         ++var1;
      }

      if (var2 > 5) {
         return false;
      } else {
         Matcher var5 = sSuffixedNumberRe.matcher(var0);
         if (var5.find()) {
            var1 = Integer.parseInt(var5.group(1));
            if (var1 == 0) {
               return false;
            } else {
               String var4 = var5.group(2).toLowerCase(Locale.getDefault());
               var3 = var1 % 10;
               var0 = "th";
               if (var3 != 1) {
                  if (var3 != 2) {
                     if (var3 != 3) {
                        return var4.equals("th");
                     } else {
                        if (var1 % 100 != 13) {
                           var0 = "rd";
                        }

                        return var4.equals(var0);
                     }
                  } else {
                     if (var1 % 100 != 12) {
                        var0 = "nd";
                     }

                     return var4.equals(var0);
                  }
               } else {
                  if (var1 % 100 != 11) {
                     var0 = "st";
                  }

                  return var4.equals(var0);
               }
            }
         } else {
            return true;
         }
      }
   }

   static String findAddress(String var0) {
      Matcher var1 = sHouseNumberRe.matcher(var0);
      int var2 = 0;

      while(var1.find(var2)) {
         if (checkHouseNumber(var1.group(0))) {
            var2 = var1.start();
            int var3 = attemptMatch(var0, var1);
            if (var3 > 0) {
               return var0.substring(var2, var3);
            }

            var2 = -var3;
         } else {
            var2 = var1.end();
         }
      }

      return null;
   }

   public static boolean isValidLocationName(String var0) {
      return sLocationNameRe.matcher(var0).matches();
   }

   public static boolean isValidZipCode(String var0) {
      return sZipCodeRe.matcher(var0).matches();
   }

   public static boolean isValidZipCode(String var0, String var1) {
      return isValidZipCode(var0, matchState(var1, 0));
   }

   private static boolean isValidZipCode(String var0, MatchResult var1) {
      boolean var2 = false;
      if (var1 == null) {
         return false;
      } else {
         int var3 = var1.groupCount();

         int var4;
         while(true) {
            var4 = var3;
            if (var3 <= 0) {
               break;
            }

            var4 = var3 - 1;
            if (var1.group(var3) != null) {
               break;
            }

            var3 = var4;
         }

         boolean var5 = var2;
         if (sZipCodeRe.matcher(var0).matches()) {
            var5 = var2;
            if (sStateZipCodeRanges[var4].matches(var0)) {
               var5 = true;
            }
         }

         return var5;
      }
   }

   public static MatchResult matchHouseNumber(String var0, int var1) {
      if (var1 > 0 && ":,\"'\t                　\n\u000b\f\r\u0085\u2028\u2029".indexOf(var0.charAt(var1 - 1)) == -1) {
         return null;
      } else {
         Matcher var2 = sHouseNumberRe.matcher(var0).region(var1, var0.length());
         if (var2.lookingAt()) {
            MatchResult var3 = var2.toMatchResult();
            if (checkHouseNumber(var3.group(0))) {
               return var3;
            }
         }

         return null;
      }
   }

   public static MatchResult matchState(String var0, int var1) {
      Object var2 = null;
      if (var1 > 0 && ",*•\t                　\n\u000b\f\r\u0085\u2028\u2029".indexOf(var0.charAt(var1 - 1)) == -1) {
         return null;
      } else {
         Matcher var3 = sStateRe.matcher(var0).region(var1, var0.length());
         MatchResult var4 = (MatchResult)var2;
         if (var3.lookingAt()) {
            var4 = var3.toMatchResult();
         }

         return var4;
      }
   }

   private static class ZipRange {
      int mException1;
      int mException2;
      int mHigh;
      int mLow;

      ZipRange(int var1, int var2, int var3, int var4) {
         this.mLow = var1;
         this.mHigh = var2;
         this.mException1 = var3;
         this.mException2 = var4;
      }

      boolean matches(String var1) {
         boolean var2 = false;
         int var3 = Integer.parseInt(var1.substring(0, 2));
         if (this.mLow <= var3 && var3 <= this.mHigh || var3 == this.mException1 || var3 == this.mException2) {
            var2 = true;
         }

         return var2;
      }
   }
}
