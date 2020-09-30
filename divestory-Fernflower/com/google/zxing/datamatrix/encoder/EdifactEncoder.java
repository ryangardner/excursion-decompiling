package com.google.zxing.datamatrix.encoder;

final class EdifactEncoder implements Encoder {
   private static void encodeChar(char var0, StringBuilder var1) {
      if (var0 >= ' ' && var0 <= '?') {
         var1.append(var0);
      } else if (var0 >= '@' && var0 <= '^') {
         var1.append((char)(var0 - 64));
      } else {
         HighLevelEncoder.illegalCharacter(var0);
      }

   }

   private static String encodeToCodewords(CharSequence var0, int var1) {
      int var2 = var0.length() - var1;
      if (var2 != 0) {
         char var3 = var0.charAt(var1);
         char var4 = 0;
         char var5;
         if (var2 >= 2) {
            var5 = var0.charAt(var1 + 1);
         } else {
            var5 = 0;
         }

         char var6;
         if (var2 >= 3) {
            var6 = var0.charAt(var1 + 2);
         } else {
            var6 = 0;
         }

         if (var2 >= 4) {
            var4 = var0.charAt(var1 + 3);
         }

         var1 = (var3 << 18) + (var5 << 12) + (var6 << 6) + var4;
         char var7 = (char)(var1 >> 16 & 255);
         char var8 = (char)(var1 >> 8 & 255);
         char var9 = (char)(var1 & 255);
         StringBuilder var10 = new StringBuilder(3);
         var10.append(var7);
         if (var2 >= 2) {
            var10.append(var8);
         }

         if (var2 >= 3) {
            var10.append(var9);
         }

         return var10.toString();
      } else {
         throw new IllegalStateException("StringBuilder must not be empty");
      }
   }

   private static void handleEOD(EncoderContext var0, CharSequence var1) {
      Throwable var10000;
      label738: {
         int var2;
         boolean var10001;
         try {
            var2 = var1.length();
         } catch (Throwable var78) {
            var10000 = var78;
            var10001 = false;
            break label738;
         }

         if (var2 == 0) {
            var0.signalEncoderChange(0);
            return;
         }

         boolean var3 = true;
         int var6;
         if (var2 == 1) {
            int var4;
            int var5;
            try {
               var0.updateSymbolInfo();
               var4 = var0.getSymbolInfo().getDataCapacity();
               var5 = var0.getCodewordCount();
               var6 = var0.getRemainingCharacters();
            } catch (Throwable var77) {
               var10000 = var77;
               var10001 = false;
               break label738;
            }

            if (var6 == 0 && var4 - var5 <= 2) {
               var0.signalEncoderChange(0);
               return;
            }
         }

         if (var2 <= 4) {
            label739: {
               var6 = var2 - 1;

               String var79;
               label722: {
                  label721: {
                     try {
                        var79 = encodeToCodewords(var1, 0);
                        if (!(var0.hasMoreCharacters() ^ true)) {
                           break label721;
                        }
                     } catch (Throwable var75) {
                        var10000 = var75;
                        var10001 = false;
                        break label739;
                     }

                     if (var6 <= 2) {
                        break label722;
                     }
                  }

                  var3 = false;
               }

               boolean var82 = var3;
               if (var6 <= 2) {
                  label740: {
                     try {
                        var0.updateSymbolInfo(var0.getCodewordCount() + var6);
                     } catch (Throwable var73) {
                        var10000 = var73;
                        var10001 = false;
                        break label739;
                     }

                     var82 = var3;

                     try {
                        if (var0.getSymbolInfo().getDataCapacity() - var0.getCodewordCount() < 3) {
                           break label740;
                        }

                        var0.updateSymbolInfo(var0.getCodewordCount() + var79.length());
                     } catch (Throwable var74) {
                        var10000 = var74;
                        var10001 = false;
                        break label739;
                     }

                     var82 = false;
                  }
               }

               if (var82) {
                  try {
                     var0.resetSymbolInfo();
                     var0.pos -= var6;
                  } catch (Throwable var72) {
                     var10000 = var72;
                     var10001 = false;
                     break label739;
                  }
               } else {
                  try {
                     var0.writeCodewords(var79);
                  } catch (Throwable var71) {
                     var10000 = var71;
                     var10001 = false;
                     break label739;
                  }
               }

               var0.signalEncoderChange(0);
               return;
            }
         } else {
            label726:
            try {
               IllegalStateException var81 = new IllegalStateException("Count must not exceed 4");
               throw var81;
            } catch (Throwable var76) {
               var10000 = var76;
               var10001 = false;
               break label726;
            }
         }
      }

      Throwable var80 = var10000;
      var0.signalEncoderChange(0);
      throw var80;
   }

   public void encode(EncoderContext var1) {
      StringBuilder var2 = new StringBuilder();

      while(var1.hasMoreCharacters()) {
         encodeChar(var1.getCurrentChar(), var2);
         ++var1.pos;
         if (var2.length() >= 4) {
            var1.writeCodewords(encodeToCodewords(var2, 0));
            var2.delete(0, 4);
            if (HighLevelEncoder.lookAheadTest(var1.getMessage(), var1.pos, this.getEncodingMode()) != this.getEncodingMode()) {
               var1.signalEncoderChange(0);
               break;
            }
         }
      }

      var2.append('\u001f');
      handleEOD(var1, var2);
   }

   public int getEncodingMode() {
      return 4;
   }
}
