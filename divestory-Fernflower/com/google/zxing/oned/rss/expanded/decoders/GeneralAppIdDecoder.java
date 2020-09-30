package com.google.zxing.oned.rss.expanded.decoders;

import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.common.BitArray;

final class GeneralAppIdDecoder {
   private final StringBuilder buffer = new StringBuilder();
   private final CurrentParsingState current = new CurrentParsingState();
   private final BitArray information;

   GeneralAppIdDecoder(BitArray var1) {
      this.information = var1;
   }

   private DecodedChar decodeAlphanumeric(int var1) {
      int var2 = this.extractNumericValueFromBitArray(var1, 5);
      if (var2 == 15) {
         return new DecodedChar(var1 + 5, '$');
      } else if (var2 >= 5 && var2 < 15) {
         return new DecodedChar(var1 + 5, (char)(var2 + 48 - 5));
      } else {
         var2 = this.extractNumericValueFromBitArray(var1, 6);
         if (var2 >= 32 && var2 < 58) {
            return new DecodedChar(var1 + 6, (char)(var2 + 33));
         } else {
            char var4;
            byte var5;
            switch(var2) {
            case 58:
               var5 = 42;
               var4 = (char)var5;
               break;
            case 59:
               var5 = 44;
               var4 = (char)var5;
               break;
            case 60:
               var5 = 45;
               var4 = (char)var5;
               break;
            case 61:
               var5 = 46;
               var4 = (char)var5;
               break;
            case 62:
               var5 = 47;
               var4 = (char)var5;
               break;
            default:
               StringBuilder var3 = new StringBuilder();
               var3.append("Decoding invalid alphanumeric value: ");
               var3.append(var2);
               throw new IllegalStateException(var3.toString());
            }

            return new DecodedChar(var1 + 6, var4);
         }
      }
   }

   private DecodedChar decodeIsoIec646(int var1) throws FormatException {
      int var2 = this.extractNumericValueFromBitArray(var1, 5);
      if (var2 == 15) {
         return new DecodedChar(var1 + 5, '$');
      } else if (var2 >= 5 && var2 < 15) {
         return new DecodedChar(var1 + 5, (char)(var2 + 48 - 5));
      } else {
         var2 = this.extractNumericValueFromBitArray(var1, 7);
         if (var2 >= 64 && var2 < 90) {
            return new DecodedChar(var1 + 7, (char)(var2 + 1));
         } else if (var2 >= 90 && var2 < 116) {
            return new DecodedChar(var1 + 7, (char)(var2 + 7));
         } else {
            char var3;
            byte var4;
            switch(this.extractNumericValueFromBitArray(var1, 8)) {
            case 232:
               var4 = 33;
               var3 = (char)var4;
               break;
            case 233:
               var4 = 34;
               var3 = (char)var4;
               break;
            case 234:
               var4 = 37;
               var3 = (char)var4;
               break;
            case 235:
               var4 = 38;
               var3 = (char)var4;
               break;
            case 236:
               var4 = 39;
               var3 = (char)var4;
               break;
            case 237:
               var4 = 40;
               var3 = (char)var4;
               break;
            case 238:
               var4 = 41;
               var3 = (char)var4;
               break;
            case 239:
               var4 = 42;
               var3 = (char)var4;
               break;
            case 240:
               var4 = 43;
               var3 = (char)var4;
               break;
            case 241:
               var4 = 44;
               var3 = (char)var4;
               break;
            case 242:
               var4 = 45;
               var3 = (char)var4;
               break;
            case 243:
               var4 = 46;
               var3 = (char)var4;
               break;
            case 244:
               var4 = 47;
               var3 = (char)var4;
               break;
            case 245:
               var4 = 58;
               var3 = (char)var4;
               break;
            case 246:
               var4 = 59;
               var3 = (char)var4;
               break;
            case 247:
               var4 = 60;
               var3 = (char)var4;
               break;
            case 248:
               var4 = 61;
               var3 = (char)var4;
               break;
            case 249:
               var4 = 62;
               var3 = (char)var4;
               break;
            case 250:
               var4 = 63;
               var3 = (char)var4;
               break;
            case 251:
               var4 = 95;
               var3 = (char)var4;
               break;
            case 252:
               var4 = 32;
               var3 = (char)var4;
               break;
            default:
               throw FormatException.getFormatInstance();
            }

            return new DecodedChar(var1 + 8, var3);
         }
      }
   }

   private DecodedNumeric decodeNumeric(int var1) throws FormatException {
      int var2 = var1 + 7;
      if (var2 > this.information.getSize()) {
         var1 = this.extractNumericValueFromBitArray(var1, 4);
         return var1 == 0 ? new DecodedNumeric(this.information.getSize(), 10, 10) : new DecodedNumeric(this.information.getSize(), var1 - 1, 10);
      } else {
         var1 = this.extractNumericValueFromBitArray(var1, 7) - 8;
         return new DecodedNumeric(var2, var1 / 11, var1 % 11);
      }
   }

   static int extractNumericValueFromBitArray(BitArray var0, int var1, int var2) {
      int var3 = 0;

      int var4;
      int var5;
      for(var4 = 0; var3 < var2; var4 = var5) {
         var5 = var4;
         if (var0.get(var1 + var3)) {
            var5 = var4 | 1 << var2 - var3 - 1;
         }

         ++var3;
      }

      return var4;
   }

   private boolean isAlphaOr646ToNumericLatch(int var1) {
      int var2 = var1 + 3;
      if (var2 > this.information.getSize()) {
         return false;
      } else {
         while(var1 < var2) {
            if (this.information.get(var1)) {
               return false;
            }

            ++var1;
         }

         return true;
      }
   }

   private boolean isAlphaTo646ToAlphaLatch(int var1) {
      if (var1 + 1 > this.information.getSize()) {
         return false;
      } else {
         for(int var2 = 0; var2 < 5; ++var2) {
            int var3 = var2 + var1;
            if (var3 >= this.information.getSize()) {
               break;
            }

            if (var2 == 2) {
               if (!this.information.get(var1 + 2)) {
                  return false;
               }
            } else if (this.information.get(var3)) {
               return false;
            }
         }

         return true;
      }
   }

   private boolean isNumericToAlphaNumericLatch(int var1) {
      if (var1 + 1 > this.information.getSize()) {
         return false;
      } else {
         for(int var2 = 0; var2 < 4; ++var2) {
            int var3 = var2 + var1;
            if (var3 >= this.information.getSize()) {
               break;
            }

            if (this.information.get(var3)) {
               return false;
            }
         }

         return true;
      }
   }

   private boolean isStillAlpha(int var1) {
      int var2 = this.information.getSize();
      boolean var3 = false;
      if (var1 + 5 > var2) {
         return false;
      } else {
         var2 = this.extractNumericValueFromBitArray(var1, 5);
         if (var2 >= 5 && var2 < 16) {
            return true;
         } else if (var1 + 6 > this.information.getSize()) {
            return false;
         } else {
            var1 = this.extractNumericValueFromBitArray(var1, 6);
            boolean var4 = var3;
            if (var1 >= 16) {
               var4 = var3;
               if (var1 < 63) {
                  var4 = true;
               }
            }

            return var4;
         }
      }
   }

   private boolean isStillIsoIec646(int var1) {
      int var2 = this.information.getSize();
      boolean var3 = false;
      if (var1 + 5 > var2) {
         return false;
      } else {
         var2 = this.extractNumericValueFromBitArray(var1, 5);
         if (var2 >= 5 && var2 < 16) {
            return true;
         } else if (var1 + 7 > this.information.getSize()) {
            return false;
         } else {
            var2 = this.extractNumericValueFromBitArray(var1, 7);
            if (var2 >= 64 && var2 < 116) {
               return true;
            } else if (var1 + 8 > this.information.getSize()) {
               return false;
            } else {
               var1 = this.extractNumericValueFromBitArray(var1, 8);
               boolean var4 = var3;
               if (var1 >= 232) {
                  var4 = var3;
                  if (var1 < 253) {
                     var4 = true;
                  }
               }

               return var4;
            }
         }
      }
   }

   private boolean isStillNumeric(int var1) {
      int var2 = this.information.getSize();
      boolean var3 = true;
      if (var1 + 7 > var2) {
         if (var1 + 4 > this.information.getSize()) {
            var3 = false;
         }

         return var3;
      } else {
         var2 = var1;

         while(true) {
            int var4 = var1 + 3;
            if (var2 >= var4) {
               return this.information.get(var4);
            }

            if (this.information.get(var2)) {
               return true;
            }

            ++var2;
         }
      }
   }

   private BlockParsedResult parseAlphaBlock() {
      while(this.isStillAlpha(this.current.getPosition())) {
         DecodedChar var1 = this.decodeAlphanumeric(this.current.getPosition());
         this.current.setPosition(var1.getNewPosition());
         if (var1.isFNC1()) {
            return new BlockParsedResult(new DecodedInformation(this.current.getPosition(), this.buffer.toString()), true);
         }

         this.buffer.append(var1.getValue());
      }

      if (this.isAlphaOr646ToNumericLatch(this.current.getPosition())) {
         this.current.incrementPosition(3);
         this.current.setNumeric();
      } else if (this.isAlphaTo646ToAlphaLatch(this.current.getPosition())) {
         if (this.current.getPosition() + 5 < this.information.getSize()) {
            this.current.incrementPosition(5);
         } else {
            this.current.setPosition(this.information.getSize());
         }

         this.current.setIsoIec646();
      }

      return new BlockParsedResult(false);
   }

   private DecodedInformation parseBlocks() throws FormatException {
      BlockParsedResult var2;
      boolean var3;
      boolean var4;
      do {
         int var1 = this.current.getPosition();
         if (this.current.isAlpha()) {
            var2 = this.parseAlphaBlock();
            var3 = var2.isFinished();
         } else if (this.current.isIsoIec646()) {
            var2 = this.parseIsoIec646Block();
            var3 = var2.isFinished();
         } else {
            var2 = this.parseNumericBlock();
            var3 = var2.isFinished();
         }

         if (var1 != this.current.getPosition()) {
            var4 = true;
         } else {
            var4 = false;
         }
      } while((var4 || var3) && !var3);

      return var2.getDecodedInformation();
   }

   private BlockParsedResult parseIsoIec646Block() throws FormatException {
      while(this.isStillIsoIec646(this.current.getPosition())) {
         DecodedChar var1 = this.decodeIsoIec646(this.current.getPosition());
         this.current.setPosition(var1.getNewPosition());
         if (var1.isFNC1()) {
            return new BlockParsedResult(new DecodedInformation(this.current.getPosition(), this.buffer.toString()), true);
         }

         this.buffer.append(var1.getValue());
      }

      if (this.isAlphaOr646ToNumericLatch(this.current.getPosition())) {
         this.current.incrementPosition(3);
         this.current.setNumeric();
      } else if (this.isAlphaTo646ToAlphaLatch(this.current.getPosition())) {
         if (this.current.getPosition() + 5 < this.information.getSize()) {
            this.current.incrementPosition(5);
         } else {
            this.current.setPosition(this.information.getSize());
         }

         this.current.setAlpha();
      }

      return new BlockParsedResult(false);
   }

   private BlockParsedResult parseNumericBlock() throws FormatException {
      while(this.isStillNumeric(this.current.getPosition())) {
         DecodedNumeric var1 = this.decodeNumeric(this.current.getPosition());
         this.current.setPosition(var1.getNewPosition());
         if (var1.isFirstDigitFNC1()) {
            DecodedInformation var2;
            if (var1.isSecondDigitFNC1()) {
               var2 = new DecodedInformation(this.current.getPosition(), this.buffer.toString());
            } else {
               var2 = new DecodedInformation(this.current.getPosition(), this.buffer.toString(), var1.getSecondDigit());
            }

            return new BlockParsedResult(var2, true);
         }

         this.buffer.append(var1.getFirstDigit());
         if (var1.isSecondDigitFNC1()) {
            return new BlockParsedResult(new DecodedInformation(this.current.getPosition(), this.buffer.toString()), true);
         }

         this.buffer.append(var1.getSecondDigit());
      }

      if (this.isNumericToAlphaNumericLatch(this.current.getPosition())) {
         this.current.setAlpha();
         this.current.incrementPosition(4);
      }

      return new BlockParsedResult(false);
   }

   String decodeAllCodes(StringBuilder var1, int var2) throws NotFoundException, FormatException {
      String var3 = null;

      while(true) {
         DecodedInformation var4 = this.decodeGeneralPurposeField(var2, var3);
         var3 = FieldParser.parseFieldsInGeneralPurpose(var4.getNewString());
         if (var3 != null) {
            var1.append(var3);
         }

         if (var4.isRemaining()) {
            var3 = String.valueOf(var4.getRemainingValue());
         } else {
            var3 = null;
         }

         if (var2 == var4.getNewPosition()) {
            return var1.toString();
         }

         var2 = var4.getNewPosition();
      }
   }

   DecodedInformation decodeGeneralPurposeField(int var1, String var2) throws FormatException {
      this.buffer.setLength(0);
      if (var2 != null) {
         this.buffer.append(var2);
      }

      this.current.setPosition(var1);
      DecodedInformation var3 = this.parseBlocks();
      return var3 != null && var3.isRemaining() ? new DecodedInformation(this.current.getPosition(), this.buffer.toString(), var3.getRemainingValue()) : new DecodedInformation(this.current.getPosition(), this.buffer.toString());
   }

   int extractNumericValueFromBitArray(int var1, int var2) {
      return extractNumericValueFromBitArray(this.information, var1, var2);
   }
}
