package com.google.zxing.datamatrix.decoder;

import com.google.zxing.FormatException;
import com.google.zxing.common.BitSource;
import com.google.zxing.common.DecoderResult;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;

final class DecodedBitStreamParser {
   private static final char[] C40_BASIC_SET_CHARS = new char[]{'*', '*', '*', ' ', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
   private static final char[] C40_SHIFT2_SET_CHARS;
   private static final char[] TEXT_BASIC_SET_CHARS;
   private static final char[] TEXT_SHIFT2_SET_CHARS;
   private static final char[] TEXT_SHIFT3_SET_CHARS;

   static {
      char[] var0 = new char[]{'!', '"', '#', '$', '%', '&', '\'', '(', ')', '*', '+', ',', '-', '.', '/', ':', ';', '<', '=', '>', '?', '@', '[', '\\', ']', '^', '_'};
      C40_SHIFT2_SET_CHARS = var0;
      TEXT_BASIC_SET_CHARS = new char[]{'*', '*', '*', ' ', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
      TEXT_SHIFT2_SET_CHARS = var0;
      TEXT_SHIFT3_SET_CHARS = new char[]{'`', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '{', '|', '}', '~', '\u007f'};
   }

   private DecodedBitStreamParser() {
   }

   static DecoderResult decode(byte[] var0) throws FormatException {
      BitSource var1 = new BitSource(var0);
      StringBuilder var2 = new StringBuilder(100);
      StringBuilder var3 = new StringBuilder(0);
      ArrayList var4 = new ArrayList(1);
      DecodedBitStreamParser.Mode var5 = DecodedBitStreamParser.Mode.ASCII_ENCODE;

      do {
         if (var5 == DecodedBitStreamParser.Mode.ASCII_ENCODE) {
            var5 = decodeAsciiSegment(var1, var2, var3);
         } else {
            int var6 = null.$SwitchMap$com$google$zxing$datamatrix$decoder$DecodedBitStreamParser$Mode[var5.ordinal()];
            if (var6 != 1) {
               if (var6 != 2) {
                  if (var6 != 3) {
                     if (var6 != 4) {
                        if (var6 != 5) {
                           throw FormatException.getFormatInstance();
                        }

                        decodeBase256Segment(var1, var2, var4);
                     } else {
                        decodeEdifactSegment(var1, var2);
                     }
                  } else {
                     decodeAnsiX12Segment(var1, var2);
                  }
               } else {
                  decodeTextSegment(var1, var2);
               }
            } else {
               decodeC40Segment(var1, var2);
            }

            var5 = DecodedBitStreamParser.Mode.ASCII_ENCODE;
         }
      } while(var5 != DecodedBitStreamParser.Mode.PAD_ENCODE && var1.available() > 0);

      if (var3.length() > 0) {
         var2.append(var3);
      }

      String var7 = var2.toString();
      ArrayList var8 = var4;
      if (var4.isEmpty()) {
         var8 = null;
      }

      return new DecoderResult(var0, var7, var8, (String)null);
   }

   private static void decodeAnsiX12Segment(BitSource var0, StringBuilder var1) throws FormatException {
      int[] var2 = new int[3];

      do {
         if (var0.available() == 8) {
            return;
         }

         int var3 = var0.readBits(8);
         if (var3 == 254) {
            return;
         }

         parseTwoBytes(var3, var0.readBits(8), var2);

         for(var3 = 0; var3 < 3; ++var3) {
            int var4 = var2[var3];
            if (var4 == 0) {
               var1.append('\r');
            } else if (var4 == 1) {
               var1.append('*');
            } else if (var4 == 2) {
               var1.append('>');
            } else if (var4 == 3) {
               var1.append(' ');
            } else if (var4 < 14) {
               var1.append((char)(var4 + 44));
            } else {
               if (var4 >= 40) {
                  throw FormatException.getFormatInstance();
               }

               var1.append((char)(var4 + 51));
            }
         }
      } while(var0.available() > 0);

   }

   private static DecodedBitStreamParser.Mode decodeAsciiSegment(BitSource var0, StringBuilder var1, StringBuilder var2) throws FormatException {
      boolean var3 = false;

      do {
         int var4 = var0.readBits(8);
         if (var4 == 0) {
            throw FormatException.getFormatInstance();
         }

         int var6;
         if (var4 <= 128) {
            var6 = var4;
            if (var3) {
               var6 = var4 + 128;
            }

            var1.append((char)(var6 - 1));
            return DecodedBitStreamParser.Mode.ASCII_ENCODE;
         }

         if (var4 == 129) {
            return DecodedBitStreamParser.Mode.PAD_ENCODE;
         }

         boolean var5;
         if (var4 <= 229) {
            var6 = var4 - 130;
            if (var6 < 10) {
               var1.append('0');
            }

            var1.append(var6);
            var5 = var3;
         } else {
            if (var4 == 230) {
               return DecodedBitStreamParser.Mode.C40_ENCODE;
            }

            if (var4 == 231) {
               return DecodedBitStreamParser.Mode.BASE256_ENCODE;
            }

            if (var4 == 232) {
               var1.append('\u001d');
               var5 = var3;
            } else {
               var5 = var3;
               if (var4 != 233) {
                  if (var4 == 234) {
                     var5 = var3;
                  } else if (var4 == 235) {
                     var5 = true;
                  } else if (var4 == 236) {
                     var1.append("[)>\u001e05\u001d");
                     var2.insert(0, "\u001e\u0004");
                     var5 = var3;
                  } else if (var4 == 237) {
                     var1.append("[)>\u001e06\u001d");
                     var2.insert(0, "\u001e\u0004");
                     var5 = var3;
                  } else {
                     if (var4 == 238) {
                        return DecodedBitStreamParser.Mode.ANSIX12_ENCODE;
                     }

                     if (var4 == 239) {
                        return DecodedBitStreamParser.Mode.TEXT_ENCODE;
                     }

                     if (var4 == 240) {
                        return DecodedBitStreamParser.Mode.EDIFACT_ENCODE;
                     }

                     if (var4 == 241) {
                        var5 = var3;
                     } else {
                        var5 = var3;
                        if (var4 >= 242) {
                           if (var4 != 254 || var0.available() != 0) {
                              throw FormatException.getFormatInstance();
                           }

                           var5 = var3;
                        }
                     }
                  }
               }
            }
         }

         var3 = var5;
      } while(var0.available() > 0);

      return DecodedBitStreamParser.Mode.ASCII_ENCODE;
   }

   private static void decodeBase256Segment(BitSource var0, StringBuilder var1, Collection<byte[]> var2) throws FormatException {
      int var3 = var0.getByteOffset() + 1;
      int var4 = var0.readBits(8);
      int var5 = var3 + 1;
      var3 = unrandomize255State(var4, var3);
      if (var3 == 0) {
         var3 = var0.available() / 8;
      } else if (var3 >= 250) {
         var3 = (var3 - 249) * 250 + unrandomize255State(var0.readBits(8), var5);
         ++var5;
      }

      if (var3 >= 0) {
         byte[] var6 = new byte[var3];

         for(var4 = 0; var4 < var3; ++var5) {
            if (var0.available() < 8) {
               throw FormatException.getFormatInstance();
            }

            var6[var4] = (byte)((byte)unrandomize255State(var0.readBits(8), var5));
            ++var4;
         }

         var2.add(var6);

         try {
            String var8 = new String(var6, "ISO8859_1");
            var1.append(var8);
         } catch (UnsupportedEncodingException var7) {
            var1 = new StringBuilder();
            var1.append("Platform does not support required encoding: ");
            var1.append(var7);
            throw new IllegalStateException(var1.toString());
         }
      } else {
         throw FormatException.getFormatInstance();
      }
   }

   private static void decodeC40Segment(BitSource var0, StringBuilder var1) throws FormatException {
      int[] var2 = new int[3];
      boolean var3 = false;
      int var4 = 0;

      do {
         if (var0.available() == 8) {
            return;
         }

         int var5 = var0.readBits(8);
         if (var5 == 254) {
            return;
         }

         parseTwoBytes(var5, var0.readBits(8), var2);

         for(var5 = 0; var5 < 3; ++var5) {
            int var6 = var2[var5];
            char[] var7;
            char var8;
            if (var4 == 0) {
               if (var6 < 3) {
                  var4 = var6 + 1;
               } else {
                  var7 = C40_BASIC_SET_CHARS;
                  if (var6 >= var7.length) {
                     throw FormatException.getFormatInstance();
                  }

                  var8 = var7[var6];
                  if (var3) {
                     var1.append((char)(var8 + 128));
                     var3 = false;
                  } else {
                     var1.append(var8);
                  }
               }
            } else {
               label73: {
                  if (var4 != 1) {
                     if (var4 != 2) {
                        if (var4 != 3) {
                           throw FormatException.getFormatInstance();
                        }

                        if (!var3) {
                           var1.append((char)(var6 + 96));
                           break label73;
                        }

                        var1.append((char)(var6 + 224));
                     } else {
                        var7 = C40_SHIFT2_SET_CHARS;
                        if (var6 >= var7.length) {
                           if (var6 == 27) {
                              var1.append('\u001d');
                           } else {
                              if (var6 != 30) {
                                 throw FormatException.getFormatInstance();
                              }

                              var3 = true;
                           }
                           break label73;
                        }

                        var8 = var7[var6];
                        if (!var3) {
                           var1.append(var8);
                           break label73;
                        }

                        var1.append((char)(var8 + 128));
                     }
                  } else {
                     if (!var3) {
                        var1.append((char)var6);
                        break label73;
                     }

                     var1.append((char)(var6 + 128));
                  }

                  var3 = false;
               }

               var4 = 0;
            }
         }
      } while(var0.available() > 0);

   }

   private static void decodeEdifactSegment(BitSource var0, StringBuilder var1) {
      while(var0.available() > 16) {
         for(int var2 = 0; var2 < 4; ++var2) {
            int var3 = var0.readBits(6);
            if (var3 == 31) {
               var2 = 8 - var0.getBitOffset();
               if (var2 != 8) {
                  var0.readBits(var2);
               }

               return;
            }

            int var4 = var3;
            if ((var3 & 32) == 0) {
               var4 = var3 | 64;
            }

            var1.append((char)var4);
         }

         if (var0.available() <= 0) {
            return;
         }
      }

   }

   private static void decodeTextSegment(BitSource var0, StringBuilder var1) throws FormatException {
      int[] var2 = new int[3];
      boolean var3 = false;
      int var4 = 0;

      do {
         if (var0.available() == 8) {
            return;
         }

         int var5 = var0.readBits(8);
         if (var5 == 254) {
            return;
         }

         parseTwoBytes(var5, var0.readBits(8), var2);

         for(var5 = 0; var5 < 3; ++var5) {
            int var6 = var2[var5];
            char[] var7;
            char var8;
            if (var4 == 0) {
               if (var6 < 3) {
                  var4 = var6 + 1;
               } else {
                  var7 = TEXT_BASIC_SET_CHARS;
                  if (var6 >= var7.length) {
                     throw FormatException.getFormatInstance();
                  }

                  var8 = var7[var6];
                  if (var3) {
                     var1.append((char)(var8 + 128));
                     var3 = false;
                  } else {
                     var1.append(var8);
                  }
               }
            } else {
               label77: {
                  if (var4 != 1) {
                     if (var4 != 2) {
                        if (var4 != 3) {
                           throw FormatException.getFormatInstance();
                        }

                        var7 = TEXT_SHIFT3_SET_CHARS;
                        if (var6 >= var7.length) {
                           throw FormatException.getFormatInstance();
                        }

                        var8 = var7[var6];
                        if (!var3) {
                           var1.append(var8);
                           break label77;
                        }

                        var1.append((char)(var8 + 128));
                     } else {
                        var7 = TEXT_SHIFT2_SET_CHARS;
                        if (var6 >= var7.length) {
                           if (var6 == 27) {
                              var1.append('\u001d');
                           } else {
                              if (var6 != 30) {
                                 throw FormatException.getFormatInstance();
                              }

                              var3 = true;
                           }
                           break label77;
                        }

                        var8 = var7[var6];
                        if (!var3) {
                           var1.append(var8);
                           break label77;
                        }

                        var1.append((char)(var8 + 128));
                     }
                  } else {
                     if (!var3) {
                        var1.append((char)var6);
                        break label77;
                     }

                     var1.append((char)(var6 + 128));
                  }

                  var3 = false;
               }

               var4 = 0;
            }
         }
      } while(var0.available() > 0);

   }

   private static void parseTwoBytes(int var0, int var1, int[] var2) {
      var0 = (var0 << 8) + var1 - 1;
      var1 = var0 / 1600;
      var2[0] = var1;
      var1 = var0 - var1 * 1600;
      var0 = var1 / 40;
      var2[1] = var0;
      var2[2] = var1 - var0 * 40;
   }

   private static int unrandomize255State(int var0, int var1) {
      var0 -= var1 * 149 % 255 + 1;
      if (var0 < 0) {
         var0 += 256;
      }

      return var0;
   }

   private static enum Mode {
      ANSIX12_ENCODE,
      ASCII_ENCODE,
      BASE256_ENCODE,
      C40_ENCODE,
      EDIFACT_ENCODE,
      PAD_ENCODE,
      TEXT_ENCODE;

      static {
         DecodedBitStreamParser.Mode var0 = new DecodedBitStreamParser.Mode("BASE256_ENCODE", 6);
         BASE256_ENCODE = var0;
      }
   }
}
