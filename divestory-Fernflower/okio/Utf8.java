package okio;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000D\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0005\n\u0000\n\u0002\u0010\f\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0012\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\u001a\u0011\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0001H\u0080\b\u001a\u0011\u0010\u000e\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u0007H\u0080\b\u001a1\u0010\u0010\u001a\u00020\u0001*\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00012\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00160\u0015H\u0080\b\u001a1\u0010\u0017\u001a\u00020\u0001*\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00012\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00160\u0015H\u0080\b\u001a1\u0010\u0018\u001a\u00020\u0001*\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00012\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00160\u0015H\u0080\b\u001a1\u0010\u0019\u001a\u00020\u0016*\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00012\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00160\u0015H\u0080\b\u001a1\u0010\u001a\u001a\u00020\u0016*\u00020\u001b2\u0006\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00012\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00160\u0015H\u0080\b\u001a1\u0010\u001c\u001a\u00020\u0016*\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00012\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00160\u0015H\u0080\b\u001a%\u0010\u001d\u001a\u00020\u001e*\u00020\u001b2\b\b\u0002\u0010\u0012\u001a\u00020\u00012\b\b\u0002\u0010\u0013\u001a\u00020\u0001H\u0007¢\u0006\u0002\b\u001f\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0003\u001a\u00020\u0001X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0004\u001a\u00020\u0001X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0005\u001a\u00020\u0001X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0006\u001a\u00020\u0007X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\b\u001a\u00020\tX\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\n\u001a\u00020\u0001X\u0080T¢\u0006\u0002\n\u0000¨\u0006 "},
   d2 = {"HIGH_SURROGATE_HEADER", "", "LOG_SURROGATE_HEADER", "MASK_2BYTES", "MASK_3BYTES", "MASK_4BYTES", "REPLACEMENT_BYTE", "", "REPLACEMENT_CHARACTER", "", "REPLACEMENT_CODE_POINT", "isIsoControl", "", "codePoint", "isUtf8Continuation", "byte", "process2Utf8Bytes", "", "beginIndex", "endIndex", "yield", "Lkotlin/Function1;", "", "process3Utf8Bytes", "process4Utf8Bytes", "processUtf16Chars", "processUtf8Bytes", "", "processUtf8CodePoints", "utf8Size", "", "size", "okio"},
   k = 2,
   mv = {1, 1, 16}
)
public final class Utf8 {
   public static final int HIGH_SURROGATE_HEADER = 55232;
   public static final int LOG_SURROGATE_HEADER = 56320;
   public static final int MASK_2BYTES = 3968;
   public static final int MASK_3BYTES = -123008;
   public static final int MASK_4BYTES = 3678080;
   public static final byte REPLACEMENT_BYTE = 63;
   public static final char REPLACEMENT_CHARACTER = '�';
   public static final int REPLACEMENT_CODE_POINT = 65533;

   public static final boolean isIsoControl(int var0) {
      boolean var1;
      if ((var0 < 0 || 31 < var0) && (127 > var0 || 159 < var0)) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public static final boolean isUtf8Continuation(byte var0) {
      boolean var1;
      if ((var0 & 192) == 128) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public static final int process2Utf8Bytes(byte[] var0, int var1, int var2, Function1<? super Integer, Unit> var3) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$process2Utf8Bytes");
      Intrinsics.checkParameterIsNotNull(var3, "yield");
      int var4 = var1 + 1;
      Integer var5 = 65533;
      if (var2 <= var4) {
         var3.invoke(var5);
         return 1;
      } else {
         byte var7 = var0[var1];
         byte var8 = var0[var4];
         boolean var6;
         if ((var8 & 192) == 128) {
            var6 = true;
         } else {
            var6 = false;
         }

         if (!var6) {
            var3.invoke(var5);
            return 1;
         } else {
            var1 = var8 ^ 3968 ^ var7 << 6;
            if (var1 < 128) {
               var3.invoke(var5);
            } else {
               var3.invoke(var1);
            }

            return 2;
         }
      }
   }

   public static final int process3Utf8Bytes(byte[] var0, int var1, int var2, Function1<? super Integer, Unit> var3) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$process3Utf8Bytes");
      Intrinsics.checkParameterIsNotNull(var3, "yield");
      int var4 = var1 + 2;
      boolean var5 = false;
      boolean var6 = false;
      Integer var7 = 65533;
      boolean var8;
      if (var2 <= var4) {
         var3.invoke(var7);
         int var11 = var1 + 1;
         if (var2 > var11) {
            var8 = var6;
            if ((var0[var11] & 192) == 128) {
               var8 = true;
            }

            if (var8) {
               return 2;
            }
         }

         return 1;
      } else {
         byte var9 = var0[var1];
         byte var12 = var0[var1 + 1];
         if ((var12 & 192) == 128) {
            var8 = true;
         } else {
            var8 = false;
         }

         if (!var8) {
            var3.invoke(var7);
            return 1;
         } else {
            byte var10 = var0[var4];
            var8 = var5;
            if ((var10 & 192) == 128) {
               var8 = true;
            }

            if (!var8) {
               var3.invoke(var7);
               return 2;
            } else {
               var1 = var10 ^ -123008 ^ var12 << 6 ^ var9 << 12;
               if (var1 < 2048) {
                  var3.invoke(var7);
               } else if (55296 <= var1 && 57343 >= var1) {
                  var3.invoke(var7);
               } else {
                  var3.invoke(var1);
               }

               return 3;
            }
         }
      }
   }

   public static final int process4Utf8Bytes(byte[] var0, int var1, int var2, Function1<? super Integer, Unit> var3) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$process4Utf8Bytes");
      Intrinsics.checkParameterIsNotNull(var3, "yield");
      int var4 = var1 + 3;
      boolean var5 = false;
      boolean var6 = false;
      Integer var7 = 65533;
      boolean var9;
      if (var2 <= var4) {
         var3.invoke(var7);
         int var13 = var1 + 1;
         if (var2 > var13) {
            if ((var0[var13] & 192) == 128) {
               var5 = true;
            } else {
               var5 = false;
            }

            if (var5) {
               var13 = var1 + 2;
               if (var2 > var13) {
                  var9 = var6;
                  if ((var0[var13] & 192) == 128) {
                     var9 = true;
                  }

                  if (var9) {
                     return 3;
                  }
               }

               return 2;
            }
         }

         return 1;
      } else {
         byte var8 = var0[var1];
         byte var14 = var0[var1 + 1];
         boolean var10;
         if ((var14 & 192) == 128) {
            var10 = true;
         } else {
            var10 = false;
         }

         if (!var10) {
            var3.invoke(var7);
            return 1;
         } else {
            byte var11 = var0[var1 + 2];
            if ((var11 & 192) == 128) {
               var9 = true;
            } else {
               var9 = false;
            }

            if (!var9) {
               var3.invoke(var7);
               return 2;
            } else {
               byte var12 = var0[var4];
               var9 = var5;
               if ((var12 & 192) == 128) {
                  var9 = true;
               }

               if (!var9) {
                  var3.invoke(var7);
                  return 3;
               } else {
                  var1 = var12 ^ 3678080 ^ var11 << 6 ^ var14 << 12 ^ var8 << 18;
                  if (var1 > 1114111) {
                     var3.invoke(var7);
                  } else if (55296 <= var1 && 57343 >= var1) {
                     var3.invoke(var7);
                  } else if (var1 < 65536) {
                     var3.invoke(var7);
                  } else {
                     var3.invoke(var1);
                  }

                  return 4;
               }
            }
         }
      }
   }

   public static final void processUtf16Chars(byte[] var0, int var1, int var2, Function1<? super Character, Unit> var3) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$processUtf16Chars");
      Intrinsics.checkParameterIsNotNull(var3, "yield");

      while(true) {
         while(var1 < var2) {
            byte var4 = var0[var1];
            int var15;
            if (var4 >= 0) {
               var3.invoke((char)var4);
               var15 = var1 + 1;

               while(true) {
                  var1 = var15;
                  if (var15 >= var2) {
                     break;
                  }

                  var1 = var15;
                  if (var0[var15] < 0) {
                     break;
                  }

                  var3.invoke((char)var0[var15]);
                  ++var15;
               }
            } else {
               byte var21;
               label168: {
                  label192: {
                     boolean var6 = false;
                     boolean var7 = false;
                     boolean var8 = false;
                     boolean var9 = false;
                     boolean var5 = false;
                     char var10;
                     byte var14;
                     char var17;
                     byte var19;
                     if (var4 >> 5 == -2) {
                        int var16 = var1 + 1;
                        if (var2 > var16) {
                           var19 = var0[var1];
                           var14 = var0[var16];
                           if ((var14 & 192) == 128) {
                              var5 = true;
                           }

                           if (var5) {
                              var15 = var14 ^ 3968 ^ var19 << 6;
                              if (var15 < 128) {
                                 var17 = (char)'�';
                                 var10 = var17;
                              } else {
                                 var17 = (char)var15;
                                 var10 = var17;
                              }

                              var3.invoke(var10);
                              break label192;
                           }
                        }

                        var3.invoke((char)'�');
                     } else {
                        label190: {
                           byte var12;
                           byte var18;
                           int var20;
                           if (var4 >> 4 == -2) {
                              int var11 = var1 + 2;
                              if (var2 <= var11) {
                                 var3.invoke((char)'�');
                                 var20 = var1 + 1;
                                 if (var2 > var20) {
                                    var5 = var6;
                                    if ((var0[var20] & 192) == 128) {
                                       var5 = true;
                                    }

                                    if (var5) {
                                       break label192;
                                    }
                                 }
                                 break label190;
                              }

                              var19 = var0[var1];
                              var18 = var0[var1 + 1];
                              if ((var18 & 192) == 128) {
                                 var5 = true;
                              } else {
                                 var5 = false;
                              }

                              if (!var5) {
                                 var3.invoke((char)'�');
                                 break label190;
                              }

                              var12 = var0[var11];
                              var5 = var7;
                              if ((var12 & 192) == 128) {
                                 var5 = true;
                              }

                              if (!var5) {
                                 var3.invoke((char)'�');
                                 break label192;
                              }

                              var15 = var12 ^ -123008 ^ var18 << 6 ^ var19 << 12;
                              if (var15 >= 2048 && (55296 > var15 || 57343 < var15)) {
                                 var17 = (char)var15;
                                 var10 = var17;
                              } else {
                                 var17 = (char)'�';
                                 var10 = var17;
                              }

                              var3.invoke(var10);
                           } else {
                              if (var4 >> 3 != -2) {
                                 var3.invoke('�');
                                 ++var1;
                                 continue;
                              }

                              int var13 = var1 + 3;
                              if (var2 <= var13) {
                                 var3.invoke('�');
                                 var15 = var1 + 1;
                                 if (var2 <= var15) {
                                    break label190;
                                 }

                                 if ((var0[var15] & 192) == 128) {
                                    var5 = true;
                                 } else {
                                    var5 = false;
                                 }

                                 if (!var5) {
                                    break label190;
                                 }

                                 var20 = var1 + 2;
                                 if (var2 <= var20) {
                                    break label192;
                                 }

                                 var5 = var8;
                                 if ((var0[var20] & 192) == 128) {
                                    var5 = true;
                                 }

                                 if (!var5) {
                                    break label192;
                                 }
                              } else {
                                 var14 = var0[var1];
                                 var18 = var0[var1 + 1];
                                 if ((var18 & 192) == 128) {
                                    var5 = true;
                                 } else {
                                    var5 = false;
                                 }

                                 if (!var5) {
                                    var3.invoke('�');
                                    break label190;
                                 }

                                 var4 = var0[var1 + 2];
                                 if ((var4 & 192) == 128) {
                                    var5 = true;
                                 } else {
                                    var5 = false;
                                 }

                                 if (!var5) {
                                    var3.invoke('�');
                                    break label192;
                                 }

                                 var12 = var0[var13];
                                 var5 = var9;
                                 if ((var12 & 192) == 128) {
                                    var5 = true;
                                 }

                                 if (var5) {
                                    var15 = var12 ^ 3678080 ^ var4 << 6 ^ var18 << 12 ^ var14 << 18;
                                    if (var15 <= 1114111 && (55296 > var15 || 57343 < var15) && var15 >= 65536 && var15 != 65533) {
                                       var3.invoke((char)((var15 >>> 10) + 'ퟀ'));
                                       var3.invoke((char)((var15 & 1023) + '\udc00'));
                                    } else {
                                       var3.invoke('�');
                                    }

                                    var21 = 4;
                                    break label168;
                                 }

                                 var3.invoke('�');
                              }
                           }

                           var21 = 3;
                           break label168;
                        }
                     }

                     var21 = 1;
                     break label168;
                  }

                  var21 = 2;
               }

               var1 += var21;
            }
         }

         return;
      }
   }

   public static final void processUtf8Bytes(String var0, int var1, int var2, Function1<? super Byte, Unit> var3) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$processUtf8Bytes");
      Intrinsics.checkParameterIsNotNull(var3, "yield");

      while(true) {
         while(var1 < var2) {
            char var4 = var0.charAt(var1);
            int var5;
            if (var4 < 128) {
               var3.invoke((byte)var4);
               var5 = var1 + 1;

               while(true) {
                  var1 = var5;
                  if (var5 >= var2) {
                     break;
                  }

                  var1 = var5;
                  if (var0.charAt(var5) >= 128) {
                     break;
                  }

                  var3.invoke((byte)var0.charAt(var5));
                  ++var5;
               }
            } else {
               if (var4 < 2048) {
                  var3.invoke((byte)(var4 >> 6 | 192));
                  var3.invoke((byte)(var4 & 63 | 128));
               } else if ('\ud800' <= var4 && '\udfff' >= var4) {
                  if (var4 <= '\udbff') {
                     var5 = var1 + 1;
                     if (var2 > var5) {
                        char var6 = var0.charAt(var5);
                        if ('\udc00' <= var6 && '\udfff' >= var6) {
                           var5 = (var4 << 10) + var0.charAt(var5) - 56613888;
                           var3.invoke((byte)(var5 >> 18 | 240));
                           var3.invoke((byte)(var5 >> 12 & 63 | 128));
                           var3.invoke((byte)(var5 >> 6 & 63 | 128));
                           var3.invoke((byte)(var5 & 63 | 128));
                           var1 += 2;
                           continue;
                        }
                     }
                  }

                  var3.invoke((byte)63);
               } else {
                  var3.invoke((byte)(var4 >> 12 | 224));
                  var3.invoke((byte)(var4 >> 6 & 63 | 128));
                  var3.invoke((byte)(var4 & 63 | 128));
               }

               ++var1;
            }
         }

         return;
      }
   }

   public static final void processUtf8CodePoints(byte[] var0, int var1, int var2, Function1<? super Integer, Unit> var3) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$processUtf8CodePoints");
      Intrinsics.checkParameterIsNotNull(var3, "yield");

      while(true) {
         while(var1 < var2) {
            byte var4 = var0[var1];
            int var15;
            if (var4 >= 0) {
               var3.invoke(Integer.valueOf(var4));
               var15 = var1 + 1;

               while(true) {
                  var1 = var15;
                  if (var15 >= var2) {
                     break;
                  }

                  var1 = var15;
                  if (var0[var15] < 0) {
                     break;
                  }

                  var3.invoke(Integer.valueOf(var0[var15]));
                  ++var15;
               }
            } else {
               byte var20;
               label167: {
                  label191: {
                     boolean var6 = false;
                     boolean var7 = false;
                     boolean var8 = false;
                     boolean var9 = false;
                     boolean var5 = false;
                     Integer var10;
                     byte var14;
                     byte var18;
                     if (var4 >> 5 == -2) {
                        int var16 = var1 + 1;
                        if (var2 > var16) {
                           var18 = var0[var1];
                           var14 = var0[var16];
                           if ((var14 & 192) == 128) {
                              var5 = true;
                           }

                           if (var5) {
                              var15 = var14 ^ 3968 ^ var18 << 6;
                              if (var15 < 128) {
                                 var10 = 65533;
                              } else {
                                 var10 = var15;
                              }

                              var3.invoke(var10);
                              break label191;
                           }
                        }

                        var3.invoke(65533);
                     } else {
                        label189: {
                           byte var12;
                           byte var17;
                           int var19;
                           if (var4 >> 4 == -2) {
                              int var11 = var1 + 2;
                              if (var2 <= var11) {
                                 var3.invoke(65533);
                                 var19 = var1 + 1;
                                 if (var2 > var19) {
                                    var5 = var6;
                                    if ((var0[var19] & 192) == 128) {
                                       var5 = true;
                                    }

                                    if (var5) {
                                       break label191;
                                    }
                                 }
                                 break label189;
                              }

                              var17 = var0[var1];
                              var18 = var0[var1 + 1];
                              if ((var18 & 192) == 128) {
                                 var5 = true;
                              } else {
                                 var5 = false;
                              }

                              if (!var5) {
                                 var3.invoke(65533);
                                 break label189;
                              }

                              var12 = var0[var11];
                              var5 = var7;
                              if ((var12 & 192) == 128) {
                                 var5 = true;
                              }

                              if (!var5) {
                                 var3.invoke(65533);
                                 break label191;
                              }

                              var15 = var12 ^ -123008 ^ var18 << 6 ^ var17 << 12;
                              if (var15 >= 2048 && (55296 > var15 || 57343 < var15)) {
                                 var10 = var15;
                              } else {
                                 var10 = 65533;
                              }

                              var3.invoke(var10);
                           } else {
                              if (var4 >> 3 != -2) {
                                 var3.invoke(65533);
                                 ++var1;
                                 continue;
                              }

                              int var13 = var1 + 3;
                              if (var2 <= var13) {
                                 var3.invoke(65533);
                                 var15 = var1 + 1;
                                 if (var2 <= var15) {
                                    break label189;
                                 }

                                 if ((var0[var15] & 192) == 128) {
                                    var5 = true;
                                 } else {
                                    var5 = false;
                                 }

                                 if (!var5) {
                                    break label189;
                                 }

                                 var19 = var1 + 2;
                                 if (var2 <= var19) {
                                    break label191;
                                 }

                                 var5 = var8;
                                 if ((var0[var19] & 192) == 128) {
                                    var5 = true;
                                 }

                                 if (!var5) {
                                    break label191;
                                 }
                              } else {
                                 var17 = var0[var1];
                                 var14 = var0[var1 + 1];
                                 if ((var14 & 192) == 128) {
                                    var5 = true;
                                 } else {
                                    var5 = false;
                                 }

                                 if (!var5) {
                                    var3.invoke(65533);
                                    break label189;
                                 }

                                 var4 = var0[var1 + 2];
                                 if ((var4 & 192) == 128) {
                                    var5 = true;
                                 } else {
                                    var5 = false;
                                 }

                                 if (!var5) {
                                    var3.invoke(65533);
                                    break label191;
                                 }

                                 var12 = var0[var13];
                                 var5 = var9;
                                 if ((var12 & 192) == 128) {
                                    var5 = true;
                                 }

                                 if (var5) {
                                    var15 = var12 ^ 3678080 ^ var4 << 6 ^ var14 << 12 ^ var17 << 18;
                                    if (var15 <= 1114111 && (55296 > var15 || 57343 < var15) && var15 >= 65536) {
                                       var10 = var15;
                                    } else {
                                       var10 = 65533;
                                    }

                                    var3.invoke(var10);
                                    var20 = 4;
                                    break label167;
                                 }

                                 var3.invoke(65533);
                              }
                           }

                           var20 = 3;
                           break label167;
                        }
                     }

                     var20 = 1;
                     break label167;
                  }

                  var20 = 2;
               }

               var1 += var20;
            }
         }

         return;
      }
   }

   public static final long size(String var0) {
      return size$default(var0, 0, 0, 3, (Object)null);
   }

   public static final long size(String var0, int var1) {
      return size$default(var0, var1, 0, 2, (Object)null);
   }

   public static final long size(String var0, int var1, int var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$utf8Size");
      boolean var3 = true;
      boolean var4;
      if (var1 >= 0) {
         var4 = true;
      } else {
         var4 = false;
      }

      StringBuilder var9;
      if (!var4) {
         var9 = new StringBuilder();
         var9.append("beginIndex < 0: ");
         var9.append(var1);
         throw (Throwable)(new IllegalArgumentException(var9.toString().toString()));
      } else {
         if (var2 >= var1) {
            var4 = true;
         } else {
            var4 = false;
         }

         if (!var4) {
            var9 = new StringBuilder();
            var9.append("endIndex < beginIndex: ");
            var9.append(var2);
            var9.append(" < ");
            var9.append(var1);
            throw (Throwable)(new IllegalArgumentException(var9.toString().toString()));
         } else {
            if (var2 <= var0.length()) {
               var4 = var3;
            } else {
               var4 = false;
            }

            if (!var4) {
               StringBuilder var8 = new StringBuilder();
               var8.append("endIndex > string.length: ");
               var8.append(var2);
               var8.append(" > ");
               var8.append(var0.length());
               throw (Throwable)(new IllegalArgumentException(var8.toString().toString()));
            } else {
               long var5 = 0L;

               while(true) {
                  while(true) {
                     while(var1 < var2) {
                        char var7 = var0.charAt(var1);
                        if (var7 < 128) {
                           ++var5;
                        } else {
                           byte var11;
                           if (var7 < 2048) {
                              var11 = 2;
                           } else {
                              if (var7 >= '\ud800' && var7 <= '\udfff') {
                                 int var10 = var1 + 1;
                                 char var12;
                                 if (var10 < var2) {
                                    var12 = var0.charAt(var10);
                                 } else {
                                    var12 = 0;
                                 }

                                 if (var7 <= '\udbff' && var12 >= '\udc00' && var12 <= '\udfff') {
                                    var5 += (long)4;
                                    var1 += 2;
                                    continue;
                                 }

                                 ++var5;
                                 var1 = var10;
                                 continue;
                              }

                              var11 = 3;
                           }

                           var5 += (long)var11;
                        }

                        ++var1;
                     }

                     return var5;
                  }
               }
            }
         }
      }
   }

   // $FF: synthetic method
   public static long size$default(String var0, int var1, int var2, int var3, Object var4) {
      if ((var3 & 1) != 0) {
         var1 = 0;
      }

      if ((var3 & 2) != 0) {
         var2 = var0.length();
      }

      return size(var0, var1, var2);
   }
}
