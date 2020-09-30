package com.google.zxing.client.result;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import java.util.HashMap;

public final class ExpandedProductResultParser extends ResultParser {
   private static String findAIvalue(int var0, String var1) {
      if (var1.charAt(var0) != '(') {
         return null;
      } else {
         String var2 = var1.substring(var0 + 1);
         StringBuilder var4 = new StringBuilder();

         for(var0 = 0; var0 < var2.length(); ++var0) {
            char var3 = var2.charAt(var0);
            if (var3 == ')') {
               return var4.toString();
            }

            if (var3 < '0' || var3 > '9') {
               return null;
            }

            var4.append(var3);
         }

         return var4.toString();
      }
   }

   private static String findValue(int var0, String var1) {
      StringBuilder var2 = new StringBuilder();
      var1 = var1.substring(var0);

      for(var0 = 0; var0 < var1.length(); ++var0) {
         char var3 = var1.charAt(var0);
         if (var3 == '(') {
            if (findAIvalue(var0, var1) != null) {
               break;
            }

            var2.append('(');
         } else {
            var2.append(var3);
         }
      }

      return var2.toString();
   }

   public ExpandedProductParsedResult parse(Result var1) {
      if (var1.getBarcodeFormat() != BarcodeFormat.RSS_EXPANDED) {
         return null;
      } else {
         String var2 = getMassagedText(var1);
         HashMap var3 = new HashMap();
         String var4 = null;
         String var5 = var4;
         String var6 = var4;
         String var7 = var4;
         String var8 = var4;
         String var9 = var4;
         String var10 = var4;
         String var12 = var4;
         String var13 = var4;
         String var14 = var4;
         String var15 = var4;
         int var16 = 0;
         String var17 = var4;
         String var18 = var4;

         String var11;
         String var22;
         for(var11 = var4; var16 < var2.length(); var13 = var22) {
            var4 = findAIvalue(var16, var2);
            if (var4 == null) {
               return null;
            }

            int var19;
            int var21;
            byte var23;
            label169: {
               var19 = var4.length();
               byte var20 = 2;
               var19 = var16 + var19 + 2;
               var22 = findValue(var19, var2);
               var21 = var22.length();
               var16 = var4.hashCode();
               if (var16 != 1536) {
                  if (var16 != 1537) {
                     if (var16 != 1567) {
                        if (var16 != 1568) {
                           if (var16 != 1570) {
                              if (var16 != 1572) {
                                 if (var16 != 1574) {
                                    switch(var16) {
                                    case 1567966:
                                       if (var4.equals("3100")) {
                                          var23 = 7;
                                          break label169;
                                       }
                                       break;
                                    case 1567967:
                                       if (var4.equals("3101")) {
                                          var23 = 8;
                                          break label169;
                                       }
                                       break;
                                    case 1567968:
                                       if (var4.equals("3102")) {
                                          var23 = 9;
                                          break label169;
                                       }
                                       break;
                                    case 1567969:
                                       if (var4.equals("3103")) {
                                          var23 = 10;
                                          break label169;
                                       }
                                       break;
                                    case 1567970:
                                       if (var4.equals("3104")) {
                                          var23 = 11;
                                          break label169;
                                       }
                                       break;
                                    case 1567971:
                                       if (var4.equals("3105")) {
                                          var23 = 12;
                                          break label169;
                                       }
                                       break;
                                    case 1567972:
                                       if (var4.equals("3106")) {
                                          var23 = 13;
                                          break label169;
                                       }
                                       break;
                                    case 1567973:
                                       if (var4.equals("3107")) {
                                          var23 = 14;
                                          break label169;
                                       }
                                       break;
                                    case 1567974:
                                       if (var4.equals("3108")) {
                                          var23 = 15;
                                          break label169;
                                       }
                                       break;
                                    case 1567975:
                                       if (var4.equals("3109")) {
                                          var23 = 16;
                                          break label169;
                                       }
                                       break;
                                    default:
                                       switch(var16) {
                                       case 1568927:
                                          if (var4.equals("3200")) {
                                             var23 = 17;
                                             break label169;
                                          }
                                          break;
                                       case 1568928:
                                          if (var4.equals("3201")) {
                                             var23 = 18;
                                             break label169;
                                          }
                                          break;
                                       case 1568929:
                                          if (var4.equals("3202")) {
                                             var23 = 19;
                                             break label169;
                                          }
                                          break;
                                       case 1568930:
                                          if (var4.equals("3203")) {
                                             var23 = 20;
                                             break label169;
                                          }
                                          break;
                                       case 1568931:
                                          if (var4.equals("3204")) {
                                             var23 = 21;
                                             break label169;
                                          }
                                          break;
                                       case 1568932:
                                          if (var4.equals("3205")) {
                                             var23 = 22;
                                             break label169;
                                          }
                                          break;
                                       case 1568933:
                                          if (var4.equals("3206")) {
                                             var23 = 23;
                                             break label169;
                                          }
                                          break;
                                       case 1568934:
                                          if (var4.equals("3207")) {
                                             var23 = 24;
                                             break label169;
                                          }
                                          break;
                                       case 1568935:
                                          if (var4.equals("3208")) {
                                             var23 = 25;
                                             break label169;
                                          }
                                          break;
                                       case 1568936:
                                          if (var4.equals("3209")) {
                                             var23 = 26;
                                             break label169;
                                          }
                                          break;
                                       default:
                                          switch(var16) {
                                          case 1575716:
                                             if (var4.equals("3920")) {
                                                var23 = 27;
                                                break label169;
                                             }
                                             break;
                                          case 1575717:
                                             if (var4.equals("3921")) {
                                                var23 = 28;
                                                break label169;
                                             }
                                             break;
                                          case 1575718:
                                             if (var4.equals("3922")) {
                                                var23 = 29;
                                                break label169;
                                             }
                                             break;
                                          case 1575719:
                                             if (var4.equals("3923")) {
                                                var23 = 30;
                                                break label169;
                                             }
                                             break;
                                          default:
                                             switch(var16) {
                                             case 1575747:
                                                if (var4.equals("3930")) {
                                                   var23 = 31;
                                                   break label169;
                                                }
                                                break;
                                             case 1575748:
                                                if (var4.equals("3931")) {
                                                   var23 = 32;
                                                   break label169;
                                                }
                                                break;
                                             case 1575749:
                                                if (var4.equals("3932")) {
                                                   var23 = 33;
                                                   break label169;
                                                }
                                                break;
                                             case 1575750:
                                                if (var4.equals("3933")) {
                                                   var23 = 34;
                                                   break label169;
                                                }
                                             }
                                          }
                                       }
                                    }
                                 } else if (var4.equals("17")) {
                                    var23 = 6;
                                    break label169;
                                 }
                              } else if (var4.equals("15")) {
                                 var23 = 5;
                                 break label169;
                              }
                           } else if (var4.equals("13")) {
                              var23 = 4;
                              break label169;
                           }
                        } else if (var4.equals("11")) {
                           var23 = 3;
                           break label169;
                        }
                     } else if (var4.equals("10")) {
                        var23 = var20;
                        break label169;
                     }
                  } else if (var4.equals("01")) {
                     var23 = 1;
                     break label169;
                  }
               } else if (var4.equals("00")) {
                  var23 = 0;
                  break label169;
               }

               var23 = -1;
            }

            label178: {
               label177: {
                  label176: {
                     switch(var23) {
                     case 0:
                        var4 = var10;
                        var5 = var22;
                        break;
                     case 1:
                        var11 = var22;
                        var4 = var10;
                        break;
                     case 2:
                        var6 = var22;
                        var4 = var10;
                        break;
                     case 3:
                        var7 = var22;
                        var4 = var10;
                        break;
                     case 4:
                        var8 = var22;
                        var4 = var10;
                        break;
                     case 5:
                        var9 = var22;
                        var4 = var10;
                        break;
                     case 6:
                        var4 = var22;
                        break;
                     case 7:
                     case 8:
                     case 9:
                     case 10:
                     case 11:
                     case 12:
                     case 13:
                     case 14:
                     case 15:
                     case 16:
                        var12 = var4.substring(3);
                        var4 = "KG";
                        break label177;
                     case 17:
                     case 18:
                     case 19:
                     case 20:
                     case 21:
                     case 22:
                     case 23:
                     case 24:
                     case 25:
                     case 26:
                        var12 = var4.substring(3);
                        var4 = "LB";
                        break label177;
                     case 27:
                     case 28:
                     case 29:
                     case 30:
                        var14 = var4.substring(3);
                        var13 = var10;
                        var10 = var14;
                        break label176;
                     case 31:
                     case 32:
                     case 33:
                     case 34:
                        if (var22.length() < 4) {
                           return null;
                        }

                        var14 = var22.substring(3);
                        var15 = var22.substring(0, 3);
                        var4 = var4.substring(3);
                        var13 = var10;
                        var22 = var14;
                        var10 = var4;
                        break label176;
                     default:
                        var3.put(var4, var22);
                        var4 = var10;
                     }

                     var22 = var13;
                     var10 = var14;
                     var13 = var4;
                  }

                  var14 = var10;
                  var4 = var17;
                  var10 = var13;
                  break label178;
               }

               var18 = var22;
               var22 = var13;
            }

            var16 = var19 + var21;
            var17 = var4;
         }

         return new ExpandedProductParsedResult(var2, var11, var5, var6, var7, var8, var9, var10, var18, var17, var12, var13, var14, var15, var3);
      }
   }
}
