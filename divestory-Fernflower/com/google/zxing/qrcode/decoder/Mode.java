package com.google.zxing.qrcode.decoder;

public enum Mode {
   ALPHANUMERIC(new int[]{9, 11, 13}, 2),
   BYTE(new int[]{8, 16, 16}, 4),
   ECI(new int[]{0, 0, 0}, 7),
   FNC1_FIRST_POSITION(new int[]{0, 0, 0}, 5),
   FNC1_SECOND_POSITION(new int[]{0, 0, 0}, 9),
   HANZI,
   KANJI(new int[]{8, 10, 12}, 8),
   NUMERIC(new int[]{10, 12, 14}, 1),
   STRUCTURED_APPEND(new int[]{0, 0, 0}, 3),
   TERMINATOR(new int[]{0, 0, 0}, 0);

   private final int bits;
   private final int[] characterCountBitsForVersions;

   static {
      Mode var0 = new Mode("HANZI", 9, new int[]{8, 10, 12}, 13);
      HANZI = var0;
   }

   private Mode(int[] var3, int var4) {
      this.characterCountBitsForVersions = var3;
      this.bits = var4;
   }

   public static Mode forBits(int var0) {
      if (var0 != 0) {
         if (var0 != 1) {
            if (var0 != 2) {
               if (var0 != 3) {
                  if (var0 != 4) {
                     if (var0 != 5) {
                        if (var0 != 7) {
                           if (var0 != 8) {
                              if (var0 != 9) {
                                 if (var0 == 13) {
                                    return HANZI;
                                 } else {
                                    throw new IllegalArgumentException();
                                 }
                              } else {
                                 return FNC1_SECOND_POSITION;
                              }
                           } else {
                              return KANJI;
                           }
                        } else {
                           return ECI;
                        }
                     } else {
                        return FNC1_FIRST_POSITION;
                     }
                  } else {
                     return BYTE;
                  }
               } else {
                  return STRUCTURED_APPEND;
               }
            } else {
               return ALPHANUMERIC;
            }
         } else {
            return NUMERIC;
         }
      } else {
         return TERMINATOR;
      }
   }

   public int getBits() {
      return this.bits;
   }

   public int getCharacterCountBits(Version var1) {
      int var2 = var1.getVersionNumber();
      byte var3;
      if (var2 <= 9) {
         var3 = 0;
      } else if (var2 <= 26) {
         var3 = 1;
      } else {
         var3 = 2;
      }

      return this.characterCountBitsForVersions[var3];
   }
}
