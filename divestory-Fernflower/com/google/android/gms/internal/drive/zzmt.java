package com.google.android.gms.internal.drive;

final class zzmt {
   static String zzc(zzjc var0) {
      zzmu var4 = new zzmu(var0);
      StringBuilder var1 = new StringBuilder(var4.size());

      for(int var2 = 0; var2 < var4.size(); ++var2) {
         byte var3 = var4.zzs(var2);
         if (var3 != 34) {
            if (var3 != 39) {
               if (var3 != 92) {
                  switch(var3) {
                  case 7:
                     var1.append("\\a");
                     break;
                  case 8:
                     var1.append("\\b");
                     break;
                  case 9:
                     var1.append("\\t");
                     break;
                  case 10:
                     var1.append("\\n");
                     break;
                  case 11:
                     var1.append("\\v");
                     break;
                  case 12:
                     var1.append("\\f");
                     break;
                  case 13:
                     var1.append("\\r");
                     break;
                  default:
                     if (var3 >= 32 && var3 <= 126) {
                        var1.append((char)var3);
                     } else {
                        var1.append('\\');
                        var1.append((char)((var3 >>> 6 & 3) + 48));
                        var1.append((char)((var3 >>> 3 & 7) + 48));
                        var1.append((char)((var3 & 7) + 48));
                     }
                  }
               } else {
                  var1.append("\\\\");
               }
            } else {
               var1.append("\\'");
            }
         } else {
            var1.append("\\\"");
         }
      }

      return var1.toString();
   }
}
