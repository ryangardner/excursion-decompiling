package com.google.zxing.pdf417.decoder;

import com.google.zxing.FormatException;
import com.google.zxing.common.CharacterSetECI;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.pdf417.PDF417ResultMetadata;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

final class DecodedBitStreamParser {
   private static final int AL = 28;
   private static final int AS = 27;
   private static final int BEGIN_MACRO_PDF417_CONTROL_BLOCK = 928;
   private static final int BEGIN_MACRO_PDF417_OPTIONAL_FIELD = 923;
   private static final int BYTE_COMPACTION_MODE_LATCH = 901;
   private static final int BYTE_COMPACTION_MODE_LATCH_6 = 924;
   private static final Charset DEFAULT_ENCODING = Charset.forName("ISO-8859-1");
   private static final int ECI_CHARSET = 927;
   private static final int ECI_GENERAL_PURPOSE = 926;
   private static final int ECI_USER_DEFINED = 925;
   private static final BigInteger[] EXP900;
   private static final int LL = 27;
   private static final int MACRO_PDF417_TERMINATOR = 922;
   private static final int MAX_NUMERIC_CODEWORDS = 15;
   private static final char[] MIXED_CHARS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '&', '\r', '\t', ',', ':', '#', '-', '.', '$', '/', '+', '%', '*', '=', '^'};
   private static final int ML = 28;
   private static final int MODE_SHIFT_TO_BYTE_COMPACTION_MODE = 913;
   private static final int NUMBER_OF_SEQUENCE_CODEWORDS = 2;
   private static final int NUMERIC_COMPACTION_MODE_LATCH = 902;
   private static final int PAL = 29;
   private static final int PL = 25;
   private static final int PS = 29;
   private static final char[] PUNCT_CHARS = new char[]{';', '<', '>', '@', '[', '\\', ']', '_', '`', '~', '!', '\r', '\t', ',', ':', '\n', '-', '.', '$', '/', '"', '|', '*', '(', ')', '?', '{', '}', '\''};
   private static final int TEXT_COMPACTION_MODE_LATCH = 900;

   static {
      BigInteger[] var0 = new BigInteger[16];
      EXP900 = var0;
      var0[0] = BigInteger.ONE;
      BigInteger var3 = BigInteger.valueOf(900L);
      EXP900[1] = var3;
      int var1 = 2;

      while(true) {
         BigInteger[] var2 = EXP900;
         if (var1 >= var2.length) {
            return;
         }

         var2[var1] = var2[var1 - 1].multiply(var3);
         ++var1;
      }
   }

   private DecodedBitStreamParser() {
   }

   private static int byteCompaction(int var0, int[] var1, Charset var2, int var3, StringBuilder var4) {
      ByteArrayOutputStream var5 = new ByteArrayOutputStream();
      short var6 = 922;
      short var7 = 923;
      byte var8 = 0;
      long var12;
      int var19;
      if (var0 == 901) {
         int[] var9 = new int[6];
         int var10 = var1[var3];
         var0 = var3 + 1;
         boolean var11 = false;
         short var17 = var6;

         label129:
         while(true) {
            int var18 = 0;
            var12 = 0L;

            while(true) {
               while(var0 < var1[0] && !var11) {
                  int var14 = var18 + 1;
                  var9[var18] = var10;
                  var12 = var12 * 900L + (long)var10;
                  var18 = var0 + 1;
                  var10 = var1[var0];
                  if (var10 != 900 && var10 != 901 && var10 != 902 && var10 != 924 && var10 != 928 && var10 != var7 && var10 != var17) {
                     if (var14 % 5 == 0 && var14 > 0) {
                        for(var0 = 0; var0 < 6; var7 = 923) {
                           var5.write((byte)((int)(var12 >> (5 - var0) * 8)));
                           ++var0;
                           var17 = 922;
                        }

                        var0 = var18;
                        continue label129;
                     }

                     var0 = var18;
                     var18 = var14;
                     var17 = 922;
                     var7 = 923;
                  } else {
                     var0 = var18 - 1;
                     var18 = var14;
                     var17 = 922;
                     var7 = 923;
                     var11 = true;
                  }
               }

               if (var0 == var1[0] && var10 < 900) {
                  var19 = var18 + 1;
                  var9[var18] = var10;
                  var18 = var8;
               } else {
                  var19 = var18;
                  var18 = var8;
               }

               while(true) {
                  var3 = var0;
                  if (var18 >= var19) {
                     break label129;
                  }

                  var5.write((byte)var9[var18]);
                  ++var18;
               }
            }
         }
      } else if (var0 == 924) {
         var0 = var3;
         boolean var20 = false;
         var3 = 0;
         var12 = 0L;

         label87:
         while(true) {
            long var15;
            do {
               do {
                  if (var0 >= var1[0] || var20) {
                     var3 = var0;
                     break label87;
                  }

                  var19 = var0 + 1;
                  var0 = var1[var0];
                  if (var0 < 900) {
                     ++var3;
                     var15 = var12 * 900L + (long)var0;
                     var0 = var19;
                     var19 = var3;
                  } else if (var0 != 900 && var0 != 901 && var0 != 902 && var0 != 924 && var0 != 928 && var0 != 923 && var0 != 922) {
                     var0 = var19;
                     var19 = var3;
                     var15 = var12;
                  } else {
                     var0 = var19 - 1;
                     var20 = true;
                     var15 = var12;
                     var19 = var3;
                  }

                  var3 = var19;
                  var12 = var15;
               } while(var19 % 5 != 0);

               var3 = var19;
               var12 = var15;
            } while(var19 <= 0);

            for(var3 = 0; var3 < 6; ++var3) {
               var5.write((byte)((int)(var15 >> (5 - var3) * 8)));
            }

            var3 = 0;
            var12 = 0L;
         }
      }

      var4.append(new String(var5.toByteArray(), var2));
      return var3;
   }

   static DecoderResult decode(int[] var0, String var1) throws FormatException {
      int var2 = var0.length;
      int var3 = 2;
      StringBuilder var4 = new StringBuilder(var2 * 2);
      Charset var5 = DEFAULT_ENCODING;
      var2 = var0[1];

      PDF417ResultMetadata var6;
      for(var6 = new PDF417ResultMetadata(); var3 < var0[0]; ++var3) {
         if (var2 != 913) {
            label37:
            switch(var2) {
            case 900:
               var3 = textCompaction(var0, var3, var4);
               break;
            case 902:
               var3 = numericCompaction(var0, var3, var4);
               break;
            default:
               switch(var2) {
               case 922:
               case 923:
                  throw FormatException.getFormatInstance();
               case 924:
                  break;
               case 925:
                  ++var3;
                  break label37;
               case 926:
                  var3 += 2;
                  break label37;
               case 927:
                  var5 = Charset.forName(CharacterSetECI.getCharacterSetECIByValue(var0[var3]).name());
                  ++var3;
                  break label37;
               case 928:
                  var3 = decodeMacroBlock(var0, var3, var6);
                  break label37;
               default:
                  var3 = textCompaction(var0, var3 - 1, var4);
                  break label37;
               }
            case 901:
               var3 = byteCompaction(var2, var0, var5, var3, var4);
            }
         } else {
            var4.append((char)var0[var3]);
            ++var3;
         }

         if (var3 >= var0.length) {
            throw FormatException.getFormatInstance();
         }

         var2 = var0[var3];
      }

      if (var4.length() != 0) {
         DecoderResult var7 = new DecoderResult((byte[])null, var4.toString(), (List)null, var1);
         var7.setOther(var6);
         return var7;
      } else {
         throw FormatException.getFormatInstance();
      }
   }

   private static String decodeBase900toBase10(int[] var0, int var1) throws FormatException {
      BigInteger var2 = BigInteger.ZERO;

      for(int var3 = 0; var3 < var1; ++var3) {
         var2 = var2.add(EXP900[var1 - var3 - 1].multiply(BigInteger.valueOf((long)var0[var3])));
      }

      String var4 = var2.toString();
      if (var4.charAt(0) == '1') {
         return var4.substring(1);
      } else {
         throw FormatException.getFormatInstance();
      }
   }

   private static int decodeMacroBlock(int[] var0, int var1, PDF417ResultMetadata var2) throws FormatException {
      if (var1 + 2 > var0[0]) {
         throw FormatException.getFormatInstance();
      } else {
         int[] var3 = new int[2];

         int var4;
         for(var4 = 0; var4 < 2; ++var1) {
            var3[var4] = var0[var1];
            ++var4;
         }

         var2.setSegmentIndex(Integer.parseInt(decodeBase900toBase10(var3, 2)));
         StringBuilder var7 = new StringBuilder();
         var4 = textCompaction(var0, var1, var7);
         var2.setFileId(var7.toString());
         if (var0[var4] == 923) {
            var1 = var4 + 1;
            var3 = new int[var0[0] - var1];
            boolean var5 = false;
            var4 = 0;

            while(var1 < var0[0] && !var5) {
               int var6 = var1 + 1;
               var1 = var0[var1];
               if (var1 < 900) {
                  var3[var4] = var1;
                  var1 = var6;
                  ++var4;
               } else {
                  if (var1 != 922) {
                     throw FormatException.getFormatInstance();
                  }

                  var2.setLastSegment(true);
                  var1 = var6 + 1;
                  var5 = true;
               }
            }

            var2.setOptionalData(Arrays.copyOf(var3, var4));
         } else {
            var1 = var4;
            if (var0[var4] == 922) {
               var2.setLastSegment(true);
               var1 = var4 + 1;
            }
         }

         return var1;
      }
   }

   private static void decodeTextCompaction(int[] var0, int[] var1, int var2, StringBuilder var3) {
      DecodedBitStreamParser.Mode var4 = DecodedBitStreamParser.Mode.ALPHA;
      DecodedBitStreamParser.Mode var5 = DecodedBitStreamParser.Mode.ALPHA;

      DecodedBitStreamParser.Mode var10;
      for(int var6 = 0; var6 < var2; var4 = var10) {
         char var11;
         label145: {
            byte var13;
            label144: {
               label143: {
                  char var9;
                  int var12;
                  label142: {
                     label141: {
                        int var7 = var0[var6];
                        int var8 = null.$SwitchMap$com$google$zxing$pdf417$decoder$DecodedBitStreamParser$Mode[var4.ordinal()];
                        var9 = ' ';
                        switch(var8) {
                        case 1:
                           if (var7 >= 26) {
                              if (var7 == 26) {
                                 var10 = var4;
                                 var11 = var9;
                                 break label145;
                              }

                              if (var7 == 27) {
                                 var10 = DecodedBitStreamParser.Mode.LOWER;
                              } else if (var7 == 28) {
                                 var10 = DecodedBitStreamParser.Mode.MIXED;
                              } else {
                                 if (var7 == 29) {
                                    var10 = DecodedBitStreamParser.Mode.PUNCT_SHIFT;
                                    break label144;
                                 }

                                 if (var7 == 913) {
                                    var3.append((char)var1[var6]);
                                    var10 = var4;
                                 } else {
                                    var10 = var4;
                                    if (var7 == 900) {
                                       var10 = DecodedBitStreamParser.Mode.ALPHA;
                                    }
                                 }
                              }
                              break label143;
                           }

                           var12 = var7 + 65;
                           break label142;
                        case 2:
                           if (var7 >= 26) {
                              if (var7 == 26) {
                                 var10 = var4;
                                 var11 = var9;
                                 break label145;
                              }

                              if (var7 == 27) {
                                 var10 = DecodedBitStreamParser.Mode.ALPHA_SHIFT;
                                 break label144;
                              }

                              if (var7 == 28) {
                                 var10 = DecodedBitStreamParser.Mode.MIXED;
                              } else {
                                 if (var7 == 29) {
                                    var10 = DecodedBitStreamParser.Mode.PUNCT_SHIFT;
                                    break label144;
                                 }

                                 if (var7 == 913) {
                                    var3.append((char)var1[var6]);
                                    var10 = var4;
                                 } else {
                                    var10 = var4;
                                    if (var7 == 900) {
                                       var10 = DecodedBitStreamParser.Mode.ALPHA;
                                    }
                                 }
                              }
                              break label143;
                           }

                           var12 = var7 + 97;
                           break label142;
                        case 3:
                           if (var7 < 25) {
                              var9 = MIXED_CHARS[var7];
                              var10 = var4;
                              var11 = var9;
                              break label145;
                           }

                           if (var7 == 25) {
                              var10 = DecodedBitStreamParser.Mode.PUNCT;
                           } else {
                              if (var7 == 26) {
                                 var10 = var4;
                                 var11 = var9;
                                 break label145;
                              }

                              if (var7 == 27) {
                                 var10 = DecodedBitStreamParser.Mode.LOWER;
                              } else if (var7 == 28) {
                                 var10 = DecodedBitStreamParser.Mode.ALPHA;
                              } else {
                                 if (var7 == 29) {
                                    var10 = DecodedBitStreamParser.Mode.PUNCT_SHIFT;
                                    break label144;
                                 }

                                 if (var7 == 913) {
                                    var3.append((char)var1[var6]);
                                    var10 = var4;
                                 } else {
                                    var10 = var4;
                                    if (var7 == 900) {
                                       var10 = DecodedBitStreamParser.Mode.ALPHA;
                                    }
                                 }
                              }
                           }
                           break label143;
                        case 4:
                           if (var7 < 29) {
                              var9 = PUNCT_CHARS[var7];
                              var10 = var4;
                              var11 = var9;
                              break label145;
                           }

                           if (var7 == 29) {
                              var10 = DecodedBitStreamParser.Mode.ALPHA;
                           } else if (var7 == 913) {
                              var3.append((char)var1[var6]);
                              var10 = var4;
                           } else {
                              var10 = var4;
                              if (var7 == 900) {
                                 var10 = DecodedBitStreamParser.Mode.ALPHA;
                              }
                           }
                           break label143;
                        case 5:
                           if (var7 < 26) {
                              var9 = (char)(var7 + 65);
                              break label141;
                           }

                           if (var7 == 26) {
                              break label141;
                           }

                           if (var7 == 900) {
                              var10 = DecodedBitStreamParser.Mode.ALPHA;
                              break label143;
                           }
                           break;
                        case 6:
                           if (var7 < 29) {
                              var9 = PUNCT_CHARS[var7];
                              break label141;
                           }

                           if (var7 == 29) {
                              var10 = DecodedBitStreamParser.Mode.ALPHA;
                              break label143;
                           }

                           if (var7 == 913) {
                              var3.append((char)var1[var6]);
                           } else if (var7 == 900) {
                              var10 = DecodedBitStreamParser.Mode.ALPHA;
                              break label143;
                           }
                           break;
                        default:
                           var10 = var4;
                           break label143;
                        }

                        var10 = var5;
                        break label143;
                     }

                     var10 = var5;
                     var11 = var9;
                     break label145;
                  }

                  var9 = (char)var12;
                  var10 = var4;
                  var11 = var9;
                  break label145;
               }

               var13 = 0;
               var11 = (char)var13;
               break label145;
            }

            var13 = 0;
            var5 = var4;
            var11 = (char)var13;
         }

         if (var11 != 0) {
            var3.append(var11);
         }

         ++var6;
      }

   }

   private static int numericCompaction(int[] var0, int var1, StringBuilder var2) throws FormatException {
      int[] var3 = new int[15];
      boolean var4 = false;
      byte var5 = 0;
      int var6 = var1;
      var1 = var5;

      while(var6 < var0[0] && !var4) {
         int var7 = var6 + 1;
         int var8 = var0[var6];
         if (var7 == var0[0]) {
            var4 = true;
         }

         int var9;
         if (var8 < 900) {
            var3[var1] = var8;
            var9 = var1 + 1;
            var6 = var7;
         } else {
            label43: {
               if (var8 != 900 && var8 != 901 && var8 != 924 && var8 != 928 && var8 != 923) {
                  var9 = var1;
                  var6 = var7;
                  if (var8 != 922) {
                     break label43;
                  }
               }

               var6 = var7 - 1;
               var4 = true;
               var9 = var1;
            }
         }

         if (var9 % 15 != 0 && var8 != 902) {
            var1 = var9;
            if (!var4) {
               continue;
            }
         }

         var1 = var9;
         if (var9 > 0) {
            var2.append(decodeBase900toBase10(var3, var9));
            var1 = 0;
         }
      }

      return var6;
   }

   private static int textCompaction(int[] var0, int var1, StringBuilder var2) {
      int[] var3 = new int[(var0[0] - var1) * 2];
      int[] var4 = new int[(var0[0] - var1) * 2];
      boolean var5 = false;
      byte var6 = 0;
      int var7 = var1;
      var1 = var6;

      while(var7 < var0[0] && !var5) {
         int var8 = var7 + 1;
         var7 = var0[var7];
         if (var7 < 900) {
            var3[var1] = var7 / 30;
            var3[var1 + 1] = var7 % 30;
            var1 += 2;
            var7 = var8;
         } else if (var7 != 913) {
            if (var7 != 928) {
               switch(var7) {
               case 900:
                  var3[var1] = 900;
                  ++var1;
                  var7 = var8;
                  continue;
               case 901:
               case 902:
                  break;
               default:
                  switch(var7) {
                  case 922:
                  case 923:
                  case 924:
                     break;
                  default:
                     var7 = var8;
                     continue;
                  }
               }
            }

            var7 = var8 - 1;
            var5 = true;
         } else {
            var3[var1] = 913;
            var7 = var8 + 1;
            var4[var1] = var0[var8];
            ++var1;
         }
      }

      decodeTextCompaction(var3, var4, var1, var2);
      return var7;
   }

   private static enum Mode {
      ALPHA,
      ALPHA_SHIFT,
      LOWER,
      MIXED,
      PUNCT,
      PUNCT_SHIFT;

      static {
         DecodedBitStreamParser.Mode var0 = new DecodedBitStreamParser.Mode("PUNCT_SHIFT", 5);
         PUNCT_SHIFT = var0;
      }
   }
}
