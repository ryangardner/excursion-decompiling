package com.fasterxml.jackson.core.io;

import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import com.fasterxml.jackson.core.util.TextBuffer;
import java.util.Arrays;

public final class JsonStringEncoder {
   private static final byte[] HB = CharTypes.copyHexBytes();
   private static final char[] HC = CharTypes.copyHexChars();
   private static final int INITIAL_BYTE_BUFFER_SIZE = 200;
   private static final int INITIAL_CHAR_BUFFER_SIZE = 120;
   private static final int SURR1_FIRST = 55296;
   private static final int SURR1_LAST = 56319;
   private static final int SURR2_FIRST = 56320;
   private static final int SURR2_LAST = 57343;
   private static final JsonStringEncoder instance = new JsonStringEncoder();

   private int _appendByte(int var1, int var2, ByteArrayBuilder var3, int var4) {
      var3.setCurrentSegmentLength(var4);
      var3.append(92);
      if (var2 < 0) {
         var3.append(117);
         if (var1 > 255) {
            var2 = var1 >> 8;
            var3.append(HB[var2 >> 4]);
            var3.append(HB[var2 & 15]);
            var1 &= 255;
         } else {
            var3.append(48);
            var3.append(48);
         }

         var3.append(HB[var1 >> 4]);
         var3.append(HB[var1 & 15]);
      } else {
         var3.append((byte)var2);
      }

      return var3.getCurrentSegmentLength();
   }

   private int _appendNamed(int var1, char[] var2) {
      var2[1] = (char)((char)var1);
      return 2;
   }

   private int _appendNumeric(int var1, char[] var2) {
      var2[1] = (char)117;
      char[] var3 = HC;
      var2[4] = (char)var3[var1 >> 4];
      var2[5] = (char)var3[var1 & 15];
      return 6;
   }

   private static int _convert(int var0, int var1) {
      if (var1 >= 56320 && var1 <= 57343) {
         return (var0 - '\ud800' << 10) + 65536 + (var1 - '\udc00');
      } else {
         StringBuilder var2 = new StringBuilder();
         var2.append("Broken surrogate pair: first char 0x");
         var2.append(Integer.toHexString(var0));
         var2.append(", second 0x");
         var2.append(Integer.toHexString(var1));
         var2.append("; illegal combination");
         throw new IllegalArgumentException(var2.toString());
      }
   }

   private static void _illegal(int var0) {
      throw new IllegalArgumentException(UTF8Writer.illegalSurrogateDesc(var0));
   }

   private char[] _qbuf() {
      char[] var1 = new char[]{(char)92, '\u0000', (char)48, (char)48, '\u0000', '\u0000'};
      return var1;
   }

   public static JsonStringEncoder getInstance() {
      return instance;
   }

   public byte[] encodeAsUTF8(String var1) {
      int var2 = var1.length();
      int var3 = 200;
      byte[] var4 = new byte[200];
      ByteArrayBuilder var5 = null;
      int var6 = 0;
      int var7 = 0;

      while(true) {
         byte[] var8 = var4;
         ByteArrayBuilder var9 = var5;
         int var10 = var7;
         if (var6 >= var2) {
            return var9 == null ? Arrays.copyOfRange(var8, 0, var10) : var9.completeAndCoalesce(var10);
         }

         var10 = var6 + 1;

         int var11;
         int var12;
         for(var11 = var1.charAt(var6); var11 <= 127; var5 = var9) {
            var6 = var3;
            var8 = var4;
            var9 = var5;
            var12 = var7;
            if (var7 >= var3) {
               var9 = var5;
               if (var5 == null) {
                  var9 = ByteArrayBuilder.fromInitial(var4, var7);
               }

               var8 = var9.finishCurrentSegment();
               var6 = var8.length;
               var12 = 0;
            }

            var7 = var12 + 1;
            var8[var12] = (byte)((byte)var11);
            if (var10 >= var2) {
               var10 = var7;
               return var9 == null ? Arrays.copyOfRange(var8, 0, var10) : var9.completeAndCoalesce(var10);
            }

            var11 = var1.charAt(var10);
            ++var10;
            var3 = var6;
            var4 = var8;
         }

         var9 = var5;
         if (var5 == null) {
            var9 = ByteArrayBuilder.fromInitial(var4, var7);
         }

         var6 = var3;
         byte[] var14 = var4;
         var12 = var7;
         if (var7 >= var3) {
            var14 = var9.finishCurrentSegment();
            var6 = var14.length;
            var12 = 0;
         }

         label76: {
            if (var11 < 2048) {
               var3 = var12 + 1;
               var14[var12] = (byte)((byte)(var11 >> 6 | 192));
            } else {
               int var13;
               if (var11 >= 55296 && var11 <= 57343) {
                  if (var11 > 56319) {
                     _illegal(var11);
                  }

                  if (var10 >= var2) {
                     _illegal(var11);
                  }

                  var3 = _convert(var11, var1.charAt(var10));
                  if (var3 > 1114111) {
                     _illegal(var3);
                  }

                  var13 = var12 + 1;
                  var14[var12] = (byte)((byte)(var3 >> 18 | 240));
                  var11 = var6;
                  var7 = var13;
                  if (var13 >= var6) {
                     var14 = var9.finishCurrentSegment();
                     var11 = var14.length;
                     var7 = 0;
                  }

                  var12 = var7 + 1;
                  var14[var7] = (byte)((byte)(var3 >> 12 & 63 | 128));
                  var7 = var11;
                  var6 = var12;
                  if (var12 >= var11) {
                     var14 = var9.finishCurrentSegment();
                     var7 = var14.length;
                     var6 = 0;
                  }

                  var14[var6] = (byte)((byte)(var3 >> 6 & 63 | 128));
                  var11 = var3;
                  var3 = var10 + 1;
                  var10 = var6 + 1;
                  var6 = var3;
                  break label76;
               }

               var13 = var12 + 1;
               var14[var12] = (byte)((byte)(var11 >> 12 | 224));
               var7 = var6;
               var3 = var13;
               if (var13 >= var6) {
                  var14 = var9.finishCurrentSegment();
                  var7 = var14.length;
                  var3 = 0;
               }

               var14[var3] = (byte)((byte)(var11 >> 6 & 63 | 128));
               ++var3;
               var6 = var7;
            }

            var7 = var6;
            var6 = var10;
            var10 = var3;
         }

         var3 = var7;
         var12 = var10;
         if (var10 >= var7) {
            var14 = var9.finishCurrentSegment();
            var3 = var14.length;
            var12 = 0;
         }

         var14[var12] = (byte)((byte)(var11 & 63 | 128));
         var7 = var12 + 1;
         var4 = var14;
         var5 = var9;
      }
   }

   public void quoteAsString(CharSequence var1, StringBuilder var2) {
      int[] var3 = CharTypes.get7BitOutputEscapes();
      int var4 = var3.length;
      int var5 = var1.length();
      char[] var6 = null;
      int var7 = 0;

      while(var7 < var5) {
         while(true) {
            char var8 = var1.charAt(var7);
            int var10;
            if (var8 < var4 && var3[var8] != 0) {
               char[] var9 = var6;
               if (var6 == null) {
                  var9 = this._qbuf();
               }

               char var12 = var1.charAt(var7);
               int var11 = var3[var12];
               if (var11 < 0) {
                  var10 = this._appendNumeric(var12, var9);
               } else {
                  var10 = this._appendNamed(var11, var9);
               }

               var2.append(var9, 0, var10);
               ++var7;
               var6 = var9;
            } else {
               var2.append(var8);
               var10 = var7 + 1;
               var7 = var10;
               if (var10 >= var5) {
                  return;
               }
            }
         }
      }

   }

   public char[] quoteAsString(CharSequence var1) {
      if (var1 instanceof String) {
         return this.quoteAsString((String)var1);
      } else {
         char[] var2 = new char[120];
         int[] var3 = CharTypes.get7BitOutputEscapes();
         int var4 = var3.length;
         int var5 = var1.length();
         TextBuffer var6 = null;
         char[] var7 = null;
         int var8 = 0;
         int var9 = 0;

         char[] var10;
         TextBuffer var11;
         int var12;
         label62:
         while(true) {
            var10 = var2;
            var11 = var6;
            var12 = var9;
            if (var8 >= var5) {
               break;
            }

            while(true) {
               char var13 = var1.charAt(var8);
               if (var13 < var4 && var3[var13] != 0) {
                  var10 = var7;
                  if (var7 == null) {
                     var10 = this._qbuf();
                  }

                  char var15 = var1.charAt(var8);
                  int var14 = var3[var15];
                  if (var14 < 0) {
                     var12 = this._appendNumeric(var15, var10);
                  } else {
                     var12 = this._appendNamed(var14, var10);
                  }

                  var14 = var9 + var12;
                  if (var14 > var2.length) {
                     var14 = var2.length - var9;
                     if (var14 > 0) {
                        System.arraycopy(var10, 0, var2, var9, var14);
                     }

                     var11 = var6;
                     if (var6 == null) {
                        var11 = TextBuffer.fromInitial(var2);
                     }

                     var2 = var11.finishCurrentSegment();
                     var9 = var12 - var14;
                     System.arraycopy(var10, var14, var2, 0, var9);
                     var6 = var11;
                  } else {
                     System.arraycopy(var10, 0, var2, var9, var12);
                     var9 = var14;
                  }

                  ++var8;
                  var7 = var10;
                  break;
               }

               var10 = var2;
               var11 = var6;
               var12 = var9;
               if (var9 >= var2.length) {
                  var11 = var6;
                  if (var6 == null) {
                     var11 = TextBuffer.fromInitial(var2);
                  }

                  var10 = var11.finishCurrentSegment();
                  var12 = 0;
               }

               var9 = var12 + 1;
               var10[var12] = (char)var13;
               ++var8;
               if (var8 >= var5) {
                  var12 = var9;
                  break label62;
               }

               var2 = var10;
               var6 = var11;
            }
         }

         if (var11 == null) {
            return Arrays.copyOfRange(var10, 0, var12);
         } else {
            var11.setCurrentLength(var12);
            return var11.contentsAsArray();
         }
      }
   }

   public char[] quoteAsString(String var1) {
      char[] var2 = new char[120];
      int[] var3 = CharTypes.get7BitOutputEscapes();
      int var4 = var3.length;
      int var5 = var1.length();
      TextBuffer var6 = null;
      char[] var7 = null;
      int var8 = 0;
      int var9 = 0;

      char[] var10;
      TextBuffer var11;
      int var12;
      label58:
      while(true) {
         var10 = var2;
         var11 = var6;
         var12 = var9;
         if (var8 >= var5) {
            break;
         }

         while(true) {
            char var13 = var1.charAt(var8);
            if (var13 < var4 && var3[var13] != 0) {
               var10 = var7;
               if (var7 == null) {
                  var10 = this._qbuf();
               }

               char var15 = var1.charAt(var8);
               int var14 = var3[var15];
               if (var14 < 0) {
                  var12 = this._appendNumeric(var15, var10);
               } else {
                  var12 = this._appendNamed(var14, var10);
               }

               var14 = var9 + var12;
               if (var14 > var2.length) {
                  var14 = var2.length - var9;
                  if (var14 > 0) {
                     System.arraycopy(var10, 0, var2, var9, var14);
                  }

                  var11 = var6;
                  if (var6 == null) {
                     var11 = TextBuffer.fromInitial(var2);
                  }

                  var2 = var11.finishCurrentSegment();
                  var9 = var12 - var14;
                  System.arraycopy(var10, var14, var2, 0, var9);
                  var6 = var11;
               } else {
                  System.arraycopy(var10, 0, var2, var9, var12);
                  var9 = var14;
               }

               ++var8;
               var7 = var10;
               break;
            }

            var10 = var2;
            var11 = var6;
            var12 = var9;
            if (var9 >= var2.length) {
               var11 = var6;
               if (var6 == null) {
                  var11 = TextBuffer.fromInitial(var2);
               }

               var10 = var11.finishCurrentSegment();
               var12 = 0;
            }

            var9 = var12 + 1;
            var10[var12] = (char)var13;
            ++var8;
            if (var8 >= var5) {
               var12 = var9;
               break label58;
            }

            var2 = var10;
            var6 = var11;
         }
      }

      if (var11 == null) {
         return Arrays.copyOfRange(var10, 0, var12);
      } else {
         var11.setCurrentLength(var12);
         return var11.contentsAsArray();
      }
   }

   public byte[] quoteAsUTF8(String var1) {
      int var2 = var1.length();
      byte[] var3 = new byte[200];
      ByteArrayBuilder var4 = null;
      int var5 = 0;
      int var6 = 0;

      while(true) {
         byte[] var7 = var3;
         ByteArrayBuilder var8 = var4;
         int var9 = var6;
         if (var5 >= var2) {
            return var8 == null ? Arrays.copyOfRange(var7, 0, var9) : var8.completeAndCoalesce(var9);
         }

         int[] var10 = CharTypes.get7BitOutputEscapes();
         byte[] var15 = var3;

         while(true) {
            char var11 = var1.charAt(var5);
            if (var11 > 127 || var10[var11] != 0) {
               ByteArrayBuilder var16 = var4;
               if (var4 == null) {
                  var16 = ByteArrayBuilder.fromInitial(var15, var6);
               }

               var3 = var15;
               int var17 = var6;
               if (var6 >= var15.length) {
                  var3 = var16.finishCurrentSegment();
                  var17 = 0;
               }

               var9 = var5 + 1;
               char var12 = var1.charAt(var5);
               if (var12 <= 127) {
                  var6 = this._appendByte(var12, var10[var12], var16, var17);
                  var3 = var16.getCurrentSegment();
                  var5 = var9;
                  var4 = var16;
               } else {
                  byte[] var14;
                  label82: {
                     if (var12 <= 2047) {
                        var6 = var17 + 1;
                        var3[var17] = (byte)((byte)(var12 >> 6 | 192));
                        var5 = var12 & 63 | 128;
                     } else {
                        if (var12 >= '\ud800' && var12 <= '\udfff') {
                           if (var12 > '\udbff') {
                              _illegal(var12);
                           }

                           if (var9 >= var2) {
                              _illegal(var12);
                           }

                           int var18 = _convert(var12, var1.charAt(var9));
                           if (var18 > 1114111) {
                              _illegal(var18);
                           }

                           var5 = var17 + 1;
                           var3[var17] = (byte)((byte)(var18 >> 18 | 240));
                           var14 = var3;
                           var6 = var5;
                           if (var5 >= var3.length) {
                              var14 = var16.finishCurrentSegment();
                              var6 = 0;
                           }

                           var5 = var6 + 1;
                           var14[var6] = (byte)((byte)(var18 >> 12 & 63 | 128));
                           var3 = var14;
                           var6 = var5;
                           if (var5 >= var14.length) {
                              var3 = var16.finishCurrentSegment();
                              var6 = 0;
                           }

                           var3[var6] = (byte)((byte)(var18 >> 6 & 63 | 128));
                           var17 = var18 & 63 | 128;
                           var5 = var9 + 1;
                           ++var6;
                           var14 = var3;
                           var9 = var17;
                           break label82;
                        }

                        var5 = var17 + 1;
                        var3[var17] = (byte)((byte)(var12 >> 12 | 224));
                        var14 = var3;
                        var6 = var5;
                        if (var5 >= var3.length) {
                           var14 = var16.finishCurrentSegment();
                           var6 = 0;
                        }

                        var14[var6] = (byte)((byte)(var12 >> 6 & 63 | 128));
                        var5 = var12 & 63 | 128;
                        ++var6;
                        var3 = var14;
                     }

                     var17 = var5;
                     var5 = var9;
                     var14 = var3;
                     var9 = var17;
                  }

                  var3 = var14;
                  var17 = var6;
                  if (var6 >= var14.length) {
                     var3 = var16.finishCurrentSegment();
                     var17 = 0;
                  }

                  var3[var17] = (byte)((byte)var9);
                  var6 = var17 + 1;
                  var4 = var16;
               }
               break;
            }

            var7 = var15;
            ByteArrayBuilder var13 = var4;
            var9 = var6;
            if (var6 >= var15.length) {
               var13 = var4;
               if (var4 == null) {
                  var13 = ByteArrayBuilder.fromInitial(var15, var6);
               }

               var7 = var13.finishCurrentSegment();
               var9 = 0;
            }

            var6 = var9 + 1;
            var7[var9] = (byte)((byte)var11);
            ++var5;
            if (var5 >= var2) {
               var8 = var13;
               var9 = var6;
               return var8 == null ? Arrays.copyOfRange(var7, 0, var9) : var8.completeAndCoalesce(var9);
            }

            var15 = var7;
            var4 = var13;
         }
      }
   }
}
