package com.google.zxing.datamatrix.encoder;

final class TextEncoder extends C40Encoder {
   int encodeChar(char var1, StringBuilder var2) {
      if (var1 == ' ') {
         var2.append('\u0003');
         return 1;
      } else if (var1 >= '0' && var1 <= '9') {
         var2.append((char)(var1 - 48 + 4));
         return 1;
      } else if (var1 >= 'a' && var1 <= 'z') {
         var2.append((char)(var1 - 97 + 14));
         return 1;
      } else if (var1 >= 0 && var1 <= 31) {
         var2.append('\u0000');
         var2.append(var1);
         return 2;
      } else if (var1 >= '!' && var1 <= '/') {
         var2.append('\u0001');
         var2.append((char)(var1 - 33));
         return 2;
      } else if (var1 >= ':' && var1 <= '@') {
         var2.append('\u0001');
         var2.append((char)(var1 - 58 + 15));
         return 2;
      } else if (var1 >= '[' && var1 <= '_') {
         var2.append('\u0001');
         var2.append((char)(var1 - 91 + 22));
         return 2;
      } else if (var1 == '`') {
         var2.append('\u0002');
         var2.append((char)(var1 - 96));
         return 2;
      } else if (var1 >= 'A' && var1 <= 'Z') {
         var2.append('\u0002');
         var2.append((char)(var1 - 65 + 1));
         return 2;
      } else if (var1 >= '{' && var1 <= 127) {
         var2.append('\u0002');
         var2.append((char)(var1 - 123 + 27));
         return 2;
      } else if (var1 >= 128) {
         var2.append("\u0001\u001e");
         return this.encodeChar((char)(var1 - 128), var2) + 2;
      } else {
         HighLevelEncoder.illegalCharacter(var1);
         return -1;
      }
   }

   public int getEncodingMode() {
      return 2;
   }
}
