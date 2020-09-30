package okio.internal;

import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010\u0012\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002\u001a\u001e\u0010\u0003\u001a\u00020\u0002*\u00020\u00012\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0005¨\u0006\u0007"},
   d2 = {"commonAsUtf8ToByteArray", "", "", "commonToUtf8String", "beginIndex", "", "endIndex", "okio"},
   k = 2,
   mv = {1, 1, 16}
)
public final class _Utf8Kt {
   public static final byte[] commonAsUtf8ToByteArray(String var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonAsUtf8ToByteArray");
      byte[] var1 = new byte[var0.length() * 4];
      int var2 = var0.length();

      byte[] var8;
      for(int var3 = 0; var3 < var2; ++var3) {
         char var4 = var0.charAt(var3);
         if (var4 >= 128) {
            int var5 = var0.length();
            int var10 = var3;
            var2 = var3;

            while(true) {
               while(var2 < var5) {
                  char var9 = var0.charAt(var2);
                  byte var12;
                  if (var9 < 128) {
                     var12 = (byte)var9;
                     var3 = var10 + 1;
                     var1[var10] = (byte)var12;
                     ++var2;

                     while(var2 < var5 && var0.charAt(var2) < 128) {
                        var1[var3] = (byte)((byte)var0.charAt(var2));
                        ++var2;
                        ++var3;
                     }

                     var10 = var3;
                  } else {
                     label49: {
                        int var6;
                        byte var7;
                        byte var11;
                        if (var9 < 2048) {
                           var7 = (byte)(var9 >> 6 | 192);
                           var6 = var10 + 1;
                           var1[var10] = (byte)var7;
                           var11 = (byte)(var9 & 63 | 128);
                           var3 = var6 + 1;
                           var1[var6] = (byte)var11;
                        } else if ('\ud800' <= var9 && '\udfff' >= var9) {
                           if (var9 <= '\udbff') {
                              int var14 = var2 + 1;
                              if (var5 > var14) {
                                 char var13 = var0.charAt(var14);
                                 if ('\udc00' <= var13 && '\udfff' >= var13) {
                                    var3 = (var9 << 10) + var0.charAt(var14) - 56613888;
                                    var7 = (byte)(var3 >> 18 | 240);
                                    var6 = var10 + 1;
                                    var1[var10] = (byte)var7;
                                    var7 = (byte)(var3 >> 12 & 63 | 128);
                                    var10 = var6 + 1;
                                    var1[var6] = (byte)var7;
                                    var7 = (byte)(var3 >> 6 & 63 | 128);
                                    var6 = var10 + 1;
                                    var1[var10] = (byte)var7;
                                    var11 = (byte)(var3 & 63 | 128);
                                    var3 = var6 + 1;
                                    var1[var6] = (byte)var11;
                                    var2 += 2;
                                    break label49;
                                 }
                              }
                           }

                           var3 = var10 + 1;
                           var1[var10] = (byte)63;
                        } else {
                           var7 = (byte)(var9 >> 12 | 224);
                           var6 = var10 + 1;
                           var1[var10] = (byte)var7;
                           var7 = (byte)(var9 >> 6 & 63 | 128);
                           var10 = var6 + 1;
                           var1[var6] = (byte)var7;
                           var12 = (byte)(var9 & 63 | 128);
                           var3 = var10 + 1;
                           var1[var10] = (byte)var12;
                        }

                        ++var2;
                     }

                     var10 = var3;
                  }
               }

               var8 = Arrays.copyOf(var1, var10);
               Intrinsics.checkExpressionValueIsNotNull(var8, "java.util.Arrays.copyOf(this, newSize)");
               return var8;
            }
         }

         var1[var3] = (byte)((byte)var4);
      }

      var8 = Arrays.copyOf(var1, var0.length());
      Intrinsics.checkExpressionValueIsNotNull(var8, "java.util.Arrays.copyOf(this, newSize)");
      return var8;
   }

   public static final String commonToUtf8String(byte[] var0, int var1, int var2) {
      int var3 = var1;
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonToUtf8String");
      if (var1 >= 0 && var2 <= var0.length && var1 <= var2) {
         char[] var10 = new char[var2 - var1];
         var1 = 0;

         while(true) {
            int var15;
            label191:
            while(true) {
               if (var3 >= var2) {
                  return new String(var10, 0, var1);
               }

               byte var5 = var0[var3];
               char var6;
               int var11;
               if (var5 >= 0) {
                  var6 = (char)var5;
                  var11 = var1 + 1;
                  var10[var1] = (char)var6;
                  ++var3;
                  var1 = var11;
                  var11 = var3;

                  while(true) {
                     var3 = var11;
                     var15 = var1;
                     if (var11 >= var2) {
                        break label191;
                     }

                     var3 = var11;
                     var15 = var1;
                     if (var0[var11] < 0) {
                        break label191;
                     }

                     var10[var1] = (char)((char)var0[var11]);
                     ++var11;
                     ++var1;
                  }
               }

               byte var20;
               label188: {
                  label187: {
                     byte var7;
                     boolean var12;
                     byte var13;
                     if (var5 >> 5 == -2) {
                        var11 = var3 + 1;
                        if (var2 <= var11) {
                           var6 = (char)'�';
                           var11 = var1 + 1;
                           var10[var1] = (char)var6;
                           var1 = var11;
                        } else {
                           var13 = var0[var3];
                           var7 = var0[var11];
                           if ((var7 & 192) == 128) {
                              var12 = true;
                           } else {
                              var12 = false;
                           }

                           if (var12) {
                              var11 = var7 ^ 3968 ^ var13 << 6;
                              if (var11 < 128) {
                                 var6 = (char)'�';
                                 var11 = var1 + 1;
                                 var10[var1] = (char)var6;
                                 var1 = var11;
                              } else {
                                 var6 = (char)var11;
                                 var11 = var1 + 1;
                                 var10[var1] = (char)var6;
                                 var1 = var11;
                              }
                              break label187;
                           }

                           var6 = (char)'�';
                           var11 = var1 + 1;
                           var10[var1] = (char)var6;
                           var1 = var11;
                        }
                     } else {
                        boolean var17;
                        byte var18;
                        if (var5 >> 4 != -2) {
                           if (var5 >> 3 != -2) {
                              var11 = var1 + 1;
                              var10[var1] = (char)'�';
                              ++var3;
                              var1 = var11;
                              continue;
                           }

                           label164: {
                              label163: {
                                 label162: {
                                    int var9 = var3 + 3;
                                    if (var2 <= var9) {
                                       var11 = var1 + 1;
                                       var10[var1] = (char)'�';
                                       var15 = var3 + 1;
                                       var1 = var11;
                                       if (var2 > var15) {
                                          boolean var14;
                                          if ((var0[var15] & 192) == 128) {
                                             var14 = true;
                                          } else {
                                             var14 = false;
                                          }

                                          if (var14) {
                                             var15 = var3 + 2;
                                             var1 = var11;
                                             if (var2 > var15) {
                                                if ((var0[var15] & 192) == 128) {
                                                   var17 = true;
                                                } else {
                                                   var17 = false;
                                                }

                                                var1 = var11;
                                                if (var17) {
                                                   break label163;
                                                }

                                                var1 = var11;
                                             }
                                             break label162;
                                          }

                                          var1 = var11;
                                       }
                                    } else {
                                       var13 = var0[var3];
                                       var7 = var0[var3 + 1];
                                       if ((var7 & 192) == 128) {
                                          var12 = true;
                                       } else {
                                          var12 = false;
                                       }

                                       if (var12) {
                                          var18 = var0[var3 + 2];
                                          if ((var18 & 192) == 128) {
                                             var12 = true;
                                          } else {
                                             var12 = false;
                                          }

                                          if (var12) {
                                             byte var19 = var0[var9];
                                             if ((var19 & 192) == 128) {
                                                var12 = true;
                                             } else {
                                                var12 = false;
                                             }

                                             if (var12) {
                                                var15 = var19 ^ 3678080 ^ var18 << 6 ^ var7 << 12 ^ var13 << 18;
                                                if (var15 > 1114111) {
                                                   var11 = var1 + 1;
                                                   var10[var1] = (char)'�';
                                                   var1 = var11;
                                                } else if (55296 <= var15 && 57343 >= var15) {
                                                   var11 = var1 + 1;
                                                   var10[var1] = (char)'�';
                                                   var1 = var11;
                                                } else if (var15 < 65536) {
                                                   var11 = var1 + 1;
                                                   var10[var1] = (char)'�';
                                                   var1 = var11;
                                                } else if (var15 != 65533) {
                                                   char var16 = (char)((var15 >>> 10) + 'ퟀ');
                                                   var11 = var1 + 1;
                                                   var10[var1] = (char)var16;
                                                   var6 = (char)((var15 & 1023) + '\udc00');
                                                   var1 = var11 + 1;
                                                   var10[var11] = (char)var6;
                                                } else {
                                                   var11 = var1 + 1;
                                                   var10[var1] = (char)'�';
                                                   var1 = var11;
                                                }

                                                var20 = 4;
                                                break label164;
                                             }

                                             var11 = var1 + 1;
                                             var10[var1] = (char)'�';
                                             var1 = var11;
                                             break label163;
                                          }

                                          var11 = var1 + 1;
                                          var10[var1] = (char)'�';
                                          var1 = var11;
                                          break label162;
                                       }

                                       var11 = var1 + 1;
                                       var10[var1] = (char)'�';
                                       var1 = var11;
                                    }

                                    var20 = 1;
                                    break label164;
                                 }

                                 var20 = 2;
                                 break label164;
                              }

                              var20 = 3;
                           }

                           var3 += var20;
                           continue;
                        }

                        int var8 = var3 + 2;
                        if (var2 <= var8) {
                           var6 = (char)'�';
                           var11 = var1 + 1;
                           var10[var1] = (char)var6;
                           var15 = var3 + 1;
                           var1 = var11;
                           if (var2 > var15) {
                              if ((var0[var15] & 192) == 128) {
                                 var17 = true;
                              } else {
                                 var17 = false;
                              }

                              var1 = var11;
                              if (var17) {
                                 break label187;
                              }

                              var1 = var11;
                           }
                        } else {
                           var7 = var0[var3];
                           var13 = var0[var3 + 1];
                           if ((var13 & 192) == 128) {
                              var12 = true;
                           } else {
                              var12 = false;
                           }

                           if (var12) {
                              var18 = var0[var8];
                              if ((var18 & 192) == 128) {
                                 var12 = true;
                              } else {
                                 var12 = false;
                              }

                              if (var12) {
                                 var11 = var18 ^ -123008 ^ var13 << 6 ^ var7 << 12;
                                 if (var11 < 2048) {
                                    var6 = (char)'�';
                                    var11 = var1 + 1;
                                    var10[var1] = (char)var6;
                                    var1 = var11;
                                 } else if (55296 <= var11 && 57343 >= var11) {
                                    var6 = (char)'�';
                                    var11 = var1 + 1;
                                    var10[var1] = (char)var6;
                                    var1 = var11;
                                 } else {
                                    var6 = (char)var11;
                                    var11 = var1 + 1;
                                    var10[var1] = (char)var6;
                                    var1 = var11;
                                 }

                                 var20 = 3;
                                 break label188;
                              }

                              var6 = (char)'�';
                              var11 = var1 + 1;
                              var10[var1] = (char)var6;
                              var1 = var11;
                              break label187;
                           }

                           var6 = (char)'�';
                           var11 = var1 + 1;
                           var10[var1] = (char)var6;
                           var1 = var11;
                        }
                     }

                     var20 = 1;
                     break label188;
                  }

                  var20 = 2;
               }

               var3 += var20;
               var15 = var1;
               break;
            }

            var1 = var15;
         }
      } else {
         StringBuilder var4 = new StringBuilder();
         var4.append("size=");
         var4.append(var0.length);
         var4.append(" beginIndex=");
         var4.append(var1);
         var4.append(" endIndex=");
         var4.append(var2);
         throw (Throwable)(new ArrayIndexOutOfBoundsException(var4.toString()));
      }
   }

   // $FF: synthetic method
   public static String commonToUtf8String$default(byte[] var0, int var1, int var2, int var3, Object var4) {
      if ((var3 & 1) != 0) {
         var1 = 0;
      }

      if ((var3 & 2) != 0) {
         var2 = var0.length;
      }

      return commonToUtf8String(var0, var1, var2);
   }
}
