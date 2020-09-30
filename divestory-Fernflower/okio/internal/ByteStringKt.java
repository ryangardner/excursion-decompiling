package okio.internal;

import java.util.Arrays;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import okio.Buffer;
import okio.ByteString;
import okio._Base64;
import okio._Platform;
import okio._Util;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000P\n\u0000\n\u0002\u0010\u0019\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\f\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0005\n\u0002\b\u0018\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0018\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0005H\u0002\u001a\u0011\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0007H\u0080\b\u001a\u0010\u0010\f\u001a\u00020\u00052\u0006\u0010\r\u001a\u00020\u000eH\u0002\u001a\r\u0010\u000f\u001a\u00020\u0010*\u00020\nH\u0080\b\u001a\r\u0010\u0011\u001a\u00020\u0010*\u00020\nH\u0080\b\u001a\u0015\u0010\u0012\u001a\u00020\u0005*\u00020\n2\u0006\u0010\u0013\u001a\u00020\nH\u0080\b\u001a\u000f\u0010\u0014\u001a\u0004\u0018\u00010\n*\u00020\u0010H\u0080\b\u001a\r\u0010\u0015\u001a\u00020\n*\u00020\u0010H\u0080\b\u001a\r\u0010\u0016\u001a\u00020\n*\u00020\u0010H\u0080\b\u001a\u0015\u0010\u0017\u001a\u00020\u0018*\u00020\n2\u0006\u0010\u0019\u001a\u00020\u0007H\u0080\b\u001a\u0015\u0010\u0017\u001a\u00020\u0018*\u00020\n2\u0006\u0010\u0019\u001a\u00020\nH\u0080\b\u001a\u0017\u0010\u001a\u001a\u00020\u0018*\u00020\n2\b\u0010\u0013\u001a\u0004\u0018\u00010\u001bH\u0080\b\u001a\u0015\u0010\u001c\u001a\u00020\u001d*\u00020\n2\u0006\u0010\u001e\u001a\u00020\u0005H\u0080\b\u001a\r\u0010\u001f\u001a\u00020\u0005*\u00020\nH\u0080\b\u001a\r\u0010 \u001a\u00020\u0005*\u00020\nH\u0080\b\u001a\r\u0010!\u001a\u00020\u0010*\u00020\nH\u0080\b\u001a\u001d\u0010\"\u001a\u00020\u0005*\u00020\n2\u0006\u0010\u0013\u001a\u00020\u00072\u0006\u0010#\u001a\u00020\u0005H\u0080\b\u001a\r\u0010$\u001a\u00020\u0007*\u00020\nH\u0080\b\u001a\u001d\u0010%\u001a\u00020\u0005*\u00020\n2\u0006\u0010\u0013\u001a\u00020\u00072\u0006\u0010#\u001a\u00020\u0005H\u0080\b\u001a\u001d\u0010%\u001a\u00020\u0005*\u00020\n2\u0006\u0010\u0013\u001a\u00020\n2\u0006\u0010#\u001a\u00020\u0005H\u0080\b\u001a-\u0010&\u001a\u00020\u0018*\u00020\n2\u0006\u0010'\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00020\u00072\u0006\u0010(\u001a\u00020\u00052\u0006\u0010)\u001a\u00020\u0005H\u0080\b\u001a-\u0010&\u001a\u00020\u0018*\u00020\n2\u0006\u0010'\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00020\n2\u0006\u0010(\u001a\u00020\u00052\u0006\u0010)\u001a\u00020\u0005H\u0080\b\u001a\u0015\u0010*\u001a\u00020\u0018*\u00020\n2\u0006\u0010+\u001a\u00020\u0007H\u0080\b\u001a\u0015\u0010*\u001a\u00020\u0018*\u00020\n2\u0006\u0010+\u001a\u00020\nH\u0080\b\u001a\u001d\u0010,\u001a\u00020\n*\u00020\n2\u0006\u0010-\u001a\u00020\u00052\u0006\u0010.\u001a\u00020\u0005H\u0080\b\u001a\r\u0010/\u001a\u00020\n*\u00020\nH\u0080\b\u001a\r\u00100\u001a\u00020\n*\u00020\nH\u0080\b\u001a\r\u00101\u001a\u00020\u0007*\u00020\nH\u0080\b\u001a\u001d\u00102\u001a\u00020\n*\u00020\u00072\u0006\u0010'\u001a\u00020\u00052\u0006\u0010)\u001a\u00020\u0005H\u0080\b\u001a\r\u00103\u001a\u00020\u0010*\u00020\nH\u0080\b\u001a\r\u00104\u001a\u00020\u0010*\u00020\nH\u0080\b\u001a$\u00105\u001a\u000206*\u00020\n2\u0006\u00107\u001a\u0002082\u0006\u0010'\u001a\u00020\u00052\u0006\u0010)\u001a\u00020\u0005H\u0000\"\u0014\u0010\u0000\u001a\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003¨\u00069"},
   d2 = {"HEX_DIGIT_CHARS", "", "getHEX_DIGIT_CHARS", "()[C", "codePointIndexToCharIndex", "", "s", "", "codePointCount", "commonOf", "Lokio/ByteString;", "data", "decodeHexDigit", "c", "", "commonBase64", "", "commonBase64Url", "commonCompareTo", "other", "commonDecodeBase64", "commonDecodeHex", "commonEncodeUtf8", "commonEndsWith", "", "suffix", "commonEquals", "", "commonGetByte", "", "pos", "commonGetSize", "commonHashCode", "commonHex", "commonIndexOf", "fromIndex", "commonInternalArray", "commonLastIndexOf", "commonRangeEquals", "offset", "otherOffset", "byteCount", "commonStartsWith", "prefix", "commonSubstring", "beginIndex", "endIndex", "commonToAsciiLowercase", "commonToAsciiUppercase", "commonToByteArray", "commonToByteString", "commonToString", "commonUtf8", "commonWrite", "", "buffer", "Lokio/Buffer;", "okio"},
   k = 2,
   mv = {1, 1, 16}
)
public final class ByteStringKt {
   private static final char[] HEX_DIGIT_CHARS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

   // $FF: synthetic method
   public static final int access$codePointIndexToCharIndex(byte[] var0, int var1) {
      return codePointIndexToCharIndex(var0, var1);
   }

   // $FF: synthetic method
   public static final int access$decodeHexDigit(char var0) {
      return decodeHexDigit(var0);
   }

   private static final int codePointIndexToCharIndex(byte[] var0, int var1) {
      int var2 = var0.length;
      int var3 = 0;
      int var4 = 0;
      int var5 = 0;

      label388:
      while(var3 < var2) {
         byte var6 = var0[var3];
         boolean var12;
         byte var14;
         int var15;
         int var18;
         if (var6 >= 0) {
            var18 = var5 + 1;
            if (var5 == var1) {
               return var4;
            }

            if (var6 != 10 && var6 != 13) {
               if ((var6 < 0 || 31 < var6) && (127 > var6 || 159 < var6)) {
                  var12 = false;
               } else {
                  var12 = true;
               }

               if (var12) {
                  return -1;
               }
            }

            if (var6 != '�') {
               if (var6 < 65536) {
                  var14 = 1;
               } else {
                  var14 = 2;
               }

               var4 += var14;
               var15 = var3 + 1;
               var3 = var18;
               var18 = var4;

               while(true) {
                  int var16 = var3;
                  var3 = var15;
                  var4 = var18;
                  var5 = var16;
                  if (var15 >= var2) {
                     continue label388;
                  }

                  var3 = var15;
                  var4 = var18;
                  var5 = var16;
                  if (var0[var15] < 0) {
                     continue label388;
                  }

                  byte var20 = var0[var15];
                  var4 = var16 + 1;
                  if (var16 == var1) {
                     return var18;
                  }

                  if (var20 != 10 && var20 != 13) {
                     boolean var11;
                     if ((var20 < 0 || 31 < var20) && (127 > var20 || 159 < var20)) {
                        var11 = false;
                     } else {
                        var11 = true;
                     }

                     if (var11) {
                        break;
                     }
                  }

                  if (var20 == '�') {
                     break;
                  }

                  byte var13;
                  if (var20 < 65536) {
                     var13 = 1;
                  } else {
                     var13 = 2;
                  }

                  var18 += var13;
                  ++var15;
                  var3 = var4;
               }

               return -1;
            }

            return -1;
         } else {
            boolean var7;
            byte var8;
            if (var6 >> 5 == -2) {
               var18 = var3 + 1;
               if (var2 <= var18) {
                  if (var5 == var1) {
                     return var4;
                  }

                  return -1;
               }

               var6 = var0[var3];
               var8 = var0[var18];
               if ((var8 & 192) == 128) {
                  var7 = true;
               } else {
                  var7 = false;
               }

               if (!var7) {
                  if (var5 == var1) {
                     return var4;
                  }

                  return -1;
               }

               var15 = var8 ^ 3968 ^ var6 << 6;
               if (var15 < 128) {
                  if (var5 == var1) {
                     return var4;
                  }

                  return -1;
               }

               var18 = var5 + 1;
               if (var5 == var1) {
                  return var4;
               }

               if (var15 != 10 && var15 != 13) {
                  if ((var15 < 0 || 31 < var15) && (127 > var15 || 159 < var15)) {
                     var12 = false;
                  } else {
                     var12 = true;
                  }

                  if (var12) {
                     return -1;
                  }
               }

               if (var15 == 65533) {
                  return -1;
               }

               if (var15 < 65536) {
                  var14 = 1;
               } else {
                  var14 = 2;
               }

               var4 += var14;
               var3 += 2;
               var5 = var18;
            } else {
               byte var9;
               if (var6 >> 4 == -2) {
                  int var17 = var3 + 2;
                  if (var2 <= var17) {
                     if (var5 == var1) {
                        return var4;
                     }

                     return -1;
                  }

                  var8 = var0[var3];
                  var6 = var0[var3 + 1];
                  if ((var6 & 192) == 128) {
                     var7 = true;
                  } else {
                     var7 = false;
                  }

                  if (!var7) {
                     if (var5 == var1) {
                        return var4;
                     }

                     return -1;
                  }

                  var9 = var0[var17];
                  if ((var9 & 192) == 128) {
                     var7 = true;
                  } else {
                     var7 = false;
                  }

                  if (!var7) {
                     if (var5 == var1) {
                        return var4;
                     }

                     return -1;
                  }

                  var15 = var9 ^ -123008 ^ var6 << 6 ^ var8 << 12;
                  if (var15 < 2048) {
                     if (var5 == var1) {
                        return var4;
                     }

                     return -1;
                  }

                  if (55296 <= var15 && 57343 >= var15) {
                     if (var5 == var1) {
                        return var4;
                     }

                     return -1;
                  }

                  var18 = var5 + 1;
                  if (var5 == var1) {
                     return var4;
                  }

                  if (var15 != 10 && var15 != 13) {
                     if ((var15 < 0 || 31 < var15) && (127 > var15 || 159 < var15)) {
                        var12 = false;
                     } else {
                        var12 = true;
                     }

                     if (var12) {
                        return -1;
                     }
                  }

                  if (var15 == 65533) {
                     return -1;
                  }

                  if (var15 < 65536) {
                     var14 = 1;
                  } else {
                     var14 = 2;
                  }

                  var4 += var14;
                  var3 += 3;
                  var5 = var18;
               } else {
                  if (var6 >> 3 != -2) {
                     if (var5 == var1) {
                        return var4;
                     }

                     return -1;
                  }

                  int var10 = var3 + 3;
                  if (var2 <= var10) {
                     if (var5 == var1) {
                        return var4;
                     }

                     return -1;
                  }

                  var8 = var0[var3];
                  var6 = var0[var3 + 1];
                  if ((var6 & 192) == 128) {
                     var7 = true;
                  } else {
                     var7 = false;
                  }

                  if (!var7) {
                     if (var5 == var1) {
                        return var4;
                     }

                     return -1;
                  }

                  var9 = var0[var3 + 2];
                  if ((var9 & 192) == 128) {
                     var7 = true;
                  } else {
                     var7 = false;
                  }

                  if (!var7) {
                     if (var5 == var1) {
                        return var4;
                     }

                     return -1;
                  }

                  byte var19 = var0[var10];
                  if ((var19 & 192) == 128) {
                     var7 = true;
                  } else {
                     var7 = false;
                  }

                  if (!var7) {
                     if (var5 == var1) {
                        return var4;
                     }

                     return -1;
                  }

                  var15 = var19 ^ 3678080 ^ var9 << 6 ^ var6 << 12 ^ var8 << 18;
                  if (var15 > 1114111) {
                     if (var5 == var1) {
                        return var4;
                     }

                     return -1;
                  }

                  if (55296 <= var15 && 57343 >= var15) {
                     if (var5 == var1) {
                        return var4;
                     }

                     return -1;
                  }

                  if (var15 < 65536) {
                     if (var5 == var1) {
                        return var4;
                     }

                     return -1;
                  }

                  var18 = var5 + 1;
                  if (var5 == var1) {
                     return var4;
                  }

                  if (var15 != 10 && var15 != 13) {
                     if ((var15 < 0 || 31 < var15) && (127 > var15 || 159 < var15)) {
                        var12 = false;
                     } else {
                        var12 = true;
                     }

                     if (var12) {
                        return -1;
                     }
                  }

                  if (var15 == 65533) {
                     return -1;
                  }

                  if (var15 < 65536) {
                     var14 = 1;
                  } else {
                     var14 = 2;
                  }

                  var4 += var14;
                  var3 += 4;
                  var5 = var18;
               }
            }
         }
      }

      return var4;
   }

   public static final String commonBase64(ByteString var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonBase64");
      return _Base64.encodeBase64$default(var0.getData$okio(), (byte[])null, 1, (Object)null);
   }

   public static final String commonBase64Url(ByteString var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonBase64Url");
      return _Base64.encodeBase64(var0.getData$okio(), _Base64.getBASE64_URL_SAFE());
   }

   public static final int commonCompareTo(ByteString var0, ByteString var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonCompareTo");
      Intrinsics.checkParameterIsNotNull(var1, "other");
      int var2 = var0.size();
      int var3 = var1.size();
      int var4 = Math.min(var2, var3);
      int var5 = 0;

      while(true) {
         byte var6 = -1;
         if (var5 >= var4) {
            if (var2 == var3) {
               return 0;
            }

            if (var2 >= var3) {
               var6 = 1;
            }

            return var6;
         }

         int var7 = var0.getByte(var5) & 255;
         int var8 = var1.getByte(var5) & 255;
         if (var7 != var8) {
            if (var7 >= var8) {
               var6 = 1;
            }

            return var6;
         }

         ++var5;
      }
   }

   public static final ByteString commonDecodeBase64(String var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonDecodeBase64");
      byte[] var1 = _Base64.decodeBase64ToArray(var0);
      ByteString var2;
      if (var1 != null) {
         var2 = new ByteString(var1);
      } else {
         var2 = null;
      }

      return var2;
   }

   public static final ByteString commonDecodeHex(String var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonDecodeHex");
      int var1 = var0.length();
      byte var2 = 0;
      boolean var5;
      if (var1 % 2 == 0) {
         var5 = true;
      } else {
         var5 = false;
      }

      if (!var5) {
         StringBuilder var7 = new StringBuilder();
         var7.append("Unexpected hex string: ");
         var7.append(var0);
         throw (Throwable)(new IllegalArgumentException(var7.toString().toString()));
      } else {
         int var3 = var0.length() / 2;
         byte[] var4 = new byte[var3];

         for(var1 = var2; var1 < var3; ++var1) {
            int var6 = var1 * 2;
            var4[var1] = (byte)((byte)((access$decodeHexDigit(var0.charAt(var6)) << 4) + access$decodeHexDigit(var0.charAt(var6 + 1))));
         }

         return new ByteString(var4);
      }
   }

   public static final ByteString commonEncodeUtf8(String var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonEncodeUtf8");
      ByteString var1 = new ByteString(_Platform.asUtf8ToByteArray(var0));
      var1.setUtf8$okio(var0);
      return var1;
   }

   public static final boolean commonEndsWith(ByteString var0, ByteString var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonEndsWith");
      Intrinsics.checkParameterIsNotNull(var1, "suffix");
      return var0.rangeEquals(var0.size() - var1.size(), (ByteString)var1, 0, var1.size());
   }

   public static final boolean commonEndsWith(ByteString var0, byte[] var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonEndsWith");
      Intrinsics.checkParameterIsNotNull(var1, "suffix");
      return var0.rangeEquals(var0.size() - var1.length, (byte[])var1, 0, var1.length);
   }

   public static final boolean commonEquals(ByteString var0, Object var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonEquals");
      boolean var2 = true;
      if (var1 != var0) {
         if (var1 instanceof ByteString) {
            ByteString var3 = (ByteString)var1;
            if (var3.size() == var0.getData$okio().length && var3.rangeEquals(0, (byte[])var0.getData$okio(), 0, var0.getData$okio().length)) {
               return var2;
            }
         }

         var2 = false;
      }

      return var2;
   }

   public static final byte commonGetByte(ByteString var0, int var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonGetByte");
      return var0.getData$okio()[var1];
   }

   public static final int commonGetSize(ByteString var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonGetSize");
      return var0.getData$okio().length;
   }

   public static final int commonHashCode(ByteString var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonHashCode");
      int var1 = var0.getHashCode$okio();
      if (var1 != 0) {
         return var1;
      } else {
         var1 = Arrays.hashCode(var0.getData$okio());
         var0.setHashCode$okio(var1);
         return var1;
      }
   }

   public static final String commonHex(ByteString var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonHex");
      char[] var1 = new char[var0.getData$okio().length * 2];
      byte[] var7 = var0.getData$okio();
      int var2 = var7.length;
      int var3 = 0;

      for(int var4 = 0; var3 < var2; ++var3) {
         byte var5 = var7[var3];
         int var6 = var4 + 1;
         var1[var4] = (char)getHEX_DIGIT_CHARS()[var5 >> 4 & 15];
         var4 = var6 + 1;
         var1[var6] = (char)getHEX_DIGIT_CHARS()[var5 & 15];
      }

      return new String(var1);
   }

   public static final int commonIndexOf(ByteString var0, byte[] var1, int var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonIndexOf");
      Intrinsics.checkParameterIsNotNull(var1, "other");
      int var3 = var0.getData$okio().length - var1.length;
      var2 = Math.max(var2, 0);
      if (var2 <= var3) {
         while(true) {
            if (_Util.arrayRangeEquals(var0.getData$okio(), var2, var1, 0, var1.length)) {
               return var2;
            }

            if (var2 == var3) {
               break;
            }

            ++var2;
         }
      }

      return -1;
   }

   public static final byte[] commonInternalArray(ByteString var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonInternalArray");
      return var0.getData$okio();
   }

   public static final int commonLastIndexOf(ByteString var0, ByteString var1, int var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonLastIndexOf");
      Intrinsics.checkParameterIsNotNull(var1, "other");
      return var0.lastIndexOf(var1.internalArray$okio(), var2);
   }

   public static final int commonLastIndexOf(ByteString var0, byte[] var1, int var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonLastIndexOf");
      Intrinsics.checkParameterIsNotNull(var1, "other");

      for(var2 = Math.min(var2, var0.getData$okio().length - var1.length); var2 >= 0; --var2) {
         if (_Util.arrayRangeEquals(var0.getData$okio(), var2, var1, 0, var1.length)) {
            return var2;
         }
      }

      return -1;
   }

   public static final ByteString commonOf(byte[] var0) {
      Intrinsics.checkParameterIsNotNull(var0, "data");
      var0 = Arrays.copyOf(var0, var0.length);
      Intrinsics.checkExpressionValueIsNotNull(var0, "java.util.Arrays.copyOf(this, size)");
      return new ByteString(var0);
   }

   public static final boolean commonRangeEquals(ByteString var0, int var1, ByteString var2, int var3, int var4) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonRangeEquals");
      Intrinsics.checkParameterIsNotNull(var2, "other");
      return var2.rangeEquals(var3, var0.getData$okio(), var1, var4);
   }

   public static final boolean commonRangeEquals(ByteString var0, int var1, byte[] var2, int var3, int var4) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonRangeEquals");
      Intrinsics.checkParameterIsNotNull(var2, "other");
      boolean var5;
      if (var1 >= 0 && var1 <= var0.getData$okio().length - var4 && var3 >= 0 && var3 <= var2.length - var4 && _Util.arrayRangeEquals(var0.getData$okio(), var1, var2, var3, var4)) {
         var5 = true;
      } else {
         var5 = false;
      }

      return var5;
   }

   public static final boolean commonStartsWith(ByteString var0, ByteString var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonStartsWith");
      Intrinsics.checkParameterIsNotNull(var1, "prefix");
      return var0.rangeEquals(0, (ByteString)var1, 0, var1.size());
   }

   public static final boolean commonStartsWith(ByteString var0, byte[] var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonStartsWith");
      Intrinsics.checkParameterIsNotNull(var1, "prefix");
      return var0.rangeEquals(0, (byte[])var1, 0, var1.length);
   }

   public static final ByteString commonSubstring(ByteString var0, int var1, int var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonSubstring");
      boolean var3 = true;
      boolean var4;
      if (var1 >= 0) {
         var4 = true;
      } else {
         var4 = false;
      }

      if (var4) {
         if (var2 <= var0.getData$okio().length) {
            var4 = true;
         } else {
            var4 = false;
         }

         if (var4) {
            if (var2 - var1 >= 0) {
               var4 = var3;
            } else {
               var4 = false;
            }

            if (var4) {
               return var1 == 0 && var2 == var0.getData$okio().length ? var0 : new ByteString(ArraysKt.copyOfRange(var0.getData$okio(), var1, var2));
            } else {
               throw (Throwable)(new IllegalArgumentException("endIndex < beginIndex".toString()));
            }
         } else {
            StringBuilder var5 = new StringBuilder();
            var5.append("endIndex > length(");
            var5.append(var0.getData$okio().length);
            var5.append(')');
            throw (Throwable)(new IllegalArgumentException(var5.toString().toString()));
         }
      } else {
         throw (Throwable)(new IllegalArgumentException("beginIndex < 0".toString()));
      }
   }

   public static final ByteString commonToAsciiLowercase(ByteString var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonToAsciiLowercase");

      for(int var1 = 0; var1 < var0.getData$okio().length; ++var1) {
         byte var2 = var0.getData$okio()[var1];
         byte var3 = (byte)65;
         if (var2 >= var3) {
            byte var4 = (byte)90;
            if (var2 <= var4) {
               byte[] var6 = var0.getData$okio();
               var6 = Arrays.copyOf(var6, var6.length);
               Intrinsics.checkExpressionValueIsNotNull(var6, "java.util.Arrays.copyOf(this, size)");
               int var5 = var1 + 1;
               var6[var1] = (byte)((byte)(var2 + 32));

               for(var1 = var5; var1 < var6.length; ++var1) {
                  byte var7 = var6[var1];
                  if (var7 >= var3 && var7 <= var4) {
                     var6[var1] = (byte)((byte)(var7 + 32));
                  }
               }

               return new ByteString(var6);
            }
         }
      }

      return var0;
   }

   public static final ByteString commonToAsciiUppercase(ByteString var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonToAsciiUppercase");

      for(int var1 = 0; var1 < var0.getData$okio().length; ++var1) {
         byte var2 = var0.getData$okio()[var1];
         byte var3 = (byte)97;
         if (var2 >= var3) {
            byte var4 = (byte)122;
            if (var2 <= var4) {
               byte[] var6 = var0.getData$okio();
               var6 = Arrays.copyOf(var6, var6.length);
               Intrinsics.checkExpressionValueIsNotNull(var6, "java.util.Arrays.copyOf(this, size)");
               int var5 = var1 + 1;
               var6[var1] = (byte)((byte)(var2 - 32));

               for(var1 = var5; var1 < var6.length; ++var1) {
                  byte var7 = var6[var1];
                  if (var7 >= var3 && var7 <= var4) {
                     var6[var1] = (byte)((byte)(var7 - 32));
                  }
               }

               return new ByteString(var6);
            }
         }
      }

      return var0;
   }

   public static final byte[] commonToByteArray(ByteString var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonToByteArray");
      byte[] var1 = var0.getData$okio();
      var1 = Arrays.copyOf(var1, var1.length);
      Intrinsics.checkExpressionValueIsNotNull(var1, "java.util.Arrays.copyOf(this, size)");
      return var1;
   }

   public static final ByteString commonToByteString(byte[] var0, int var1, int var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonToByteString");
      _Util.checkOffsetAndCount((long)var0.length, (long)var1, (long)var2);
      return new ByteString(ArraysKt.copyOfRange(var0, var1, var2 + var1));
   }

   public static final String commonToString(ByteString var0) {
      ByteString var1 = var0;
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonToString");
      int var2 = var0.getData$okio().length;
      boolean var3 = true;
      boolean var9;
      if (var2 == 0) {
         var9 = true;
      } else {
         var9 = false;
      }

      if (var9) {
         return "[size=0]";
      } else {
         var2 = access$codePointIndexToCharIndex(var0.getData$okio(), 64);
         String var5;
         StringBuilder var10;
         if (var2 == -1) {
            StringBuilder var8;
            if (var0.getData$okio().length <= 64) {
               var8 = new StringBuilder();
               var8.append("[hex=");
               var8.append(var0.hex());
               var8.append(']');
               var5 = var8.toString();
            } else {
               var10 = new StringBuilder();
               var10.append("[size=");
               var10.append(var0.getData$okio().length);
               var10.append(" hex=");
               if (64 <= var0.getData$okio().length) {
                  var9 = var3;
               } else {
                  var9 = false;
               }

               if (!var9) {
                  var8 = new StringBuilder();
                  var8.append("endIndex > length(");
                  var8.append(var0.getData$okio().length);
                  var8.append(')');
                  throw (Throwable)(new IllegalArgumentException(var8.toString().toString()));
               }

               if (64 != var0.getData$okio().length) {
                  var1 = new ByteString(ArraysKt.copyOfRange(var0.getData$okio(), 0, 64));
               }

               var10.append(var1.hex());
               var10.append("…]");
               var5 = var10.toString();
            }

            return var5;
         } else {
            String var4 = var0.utf8();
            if (var4 != null) {
               String var7 = var4.substring(0, var2);
               Intrinsics.checkExpressionValueIsNotNull(var7, "(this as java.lang.Strin…ing(startIndex, endIndex)");
               var7 = StringsKt.replace$default(StringsKt.replace$default(StringsKt.replace$default(var7, "\\", "\\\\", false, 4, (Object)null), "\n", "\\n", false, 4, (Object)null), "\r", "\\r", false, 4, (Object)null);
               if (var2 < var4.length()) {
                  var10 = new StringBuilder();
                  var10.append("[size=");
                  var10.append(var0.getData$okio().length);
                  var10.append(" text=");
                  var10.append(var7);
                  var10.append("…]");
                  var5 = var10.toString();
               } else {
                  StringBuilder var6 = new StringBuilder();
                  var6.append("[text=");
                  var6.append(var7);
                  var6.append(']');
                  var5 = var6.toString();
               }

               return var5;
            } else {
               throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }
         }
      }
   }

   public static final String commonUtf8(ByteString var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonUtf8");
      String var1 = var0.getUtf8$okio();
      String var2 = var1;
      if (var1 == null) {
         var2 = _Platform.toUtf8String(var0.internalArray$okio());
         var0.setUtf8$okio(var2);
      }

      return var2;
   }

   public static final void commonWrite(ByteString var0, Buffer var1, int var2, int var3) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonWrite");
      Intrinsics.checkParameterIsNotNull(var1, "buffer");
      var1.write(var0.getData$okio(), var2, var3);
   }

   private static final int decodeHexDigit(char var0) {
      int var3;
      if ('0' <= var0 && '9' >= var0) {
         var3 = var0 - 48;
      } else {
         byte var1 = 97;
         if ('a' > var0 || 'f' < var0) {
            var1 = 65;
            if ('A' > var0 || 'F' < var0) {
               StringBuilder var2 = new StringBuilder();
               var2.append("Unexpected hex digit: ");
               var2.append(var0);
               throw (Throwable)(new IllegalArgumentException(var2.toString()));
            }
         }

         var3 = var0 - var1 + 10;
      }

      return var3;
   }

   public static final char[] getHEX_DIGIT_CHARS() {
      return HEX_DIGIT_CHARS;
   }
}
