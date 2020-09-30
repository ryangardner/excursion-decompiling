package com.google.zxing.oned.rss.expanded.decoders;

import com.google.zxing.NotFoundException;

final class FieldParser {
   private static final Object[][] FOUR_DIGIT_DATA_LENGTH;
   private static final Object[][] THREE_DIGIT_DATA_LENGTH;
   private static final Object[][] THREE_DIGIT_PLUS_DIGIT_DATA_LENGTH;
   private static final Object[][] TWO_DIGIT_DATA_LENGTH;
   private static final Object VARIABLE_LENGTH = new Object();

   static {
      Integer var0 = 2;
      Integer var1 = 18;
      Object[] var2 = new Object[]{"00", var1};
      Integer var3 = 14;
      Object[] var4 = new Object[]{"01", var3};
      Integer var5 = 3;
      Object var6 = VARIABLE_LENGTH;
      Integer var7 = 20;
      Object[] var8 = new Object[]{"10", var6, var7};
      Integer var9 = 6;
      Object[] var10 = new Object[]{"11", var9};
      Object[] var11 = new Object[]{"13", var9};
      Object[] var12 = new Object[]{"15", var9};
      Integer var13 = 8;
      Object[] var14 = new Object[]{"17", var9};
      Object var15 = VARIABLE_LENGTH;
      Integer var45 = 10;
      Object[] var16 = new Object[]{"21", var15, var7};
      Object var17 = VARIABLE_LENGTH;
      Object var18 = VARIABLE_LENGTH;
      Object var19 = VARIABLE_LENGTH;
      Integer var20 = 13;
      Object var21 = VARIABLE_LENGTH;
      Integer var52 = 30;
      Object[] var22 = new Object[]{"90", var21, var52};
      Object var23 = VARIABLE_LENGTH;
      Integer var57 = 15;
      Object[] var24 = new Object[]{"91", var23, var52};
      Object[] var25 = new Object[]{"92", VARIABLE_LENGTH, var52};
      Object var26 = VARIABLE_LENGTH;
      Integer var59 = 17;
      Object[] var27 = new Object[]{"93", var26, var52};
      var26 = VARIABLE_LENGTH;
      Object[] var28 = new Object[]{"95", VARIABLE_LENGTH, var52};
      Object[] var29 = new Object[]{"96", VARIABLE_LENGTH, var52};
      Object[] var30 = new Object[]{"97", VARIABLE_LENGTH, var52};
      Object[] var31 = new Object[]{"98", VARIABLE_LENGTH, var52};
      Object[] var32 = new Object[]{"99", VARIABLE_LENGTH, var52};
      TWO_DIGIT_DATA_LENGTH = new Object[][]{var2, var4, {"02", var3}, var8, var10, {"12", var9}, var11, var12, var14, {"20", var0}, var16, {"22", var17, 29}, {"30", var18, var13}, {"37", var19, var13}, var22, var24, var25, var27, {"94", var26, var52}, var28, var29, var30, var31, var32};
      var12 = new Object[]{"240", VARIABLE_LENGTH, var52};
      var14 = new Object[]{"241", VARIABLE_LENGTH, var52};
      Object var41 = VARIABLE_LENGTH;
      Object[] var55 = new Object[]{"250", VARIABLE_LENGTH, var52};
      Object[] var54 = new Object[]{"251", VARIABLE_LENGTH, var52};
      Object[] var53 = new Object[]{"253", VARIABLE_LENGTH, var59};
      Object var49 = VARIABLE_LENGTH;
      Object var48 = VARIABLE_LENGTH;
      Object var43 = VARIABLE_LENGTH;
      Object var50 = VARIABLE_LENGTH;
      var16 = new Object[]{"410", var20};
      var22 = new Object[]{"411", var20};
      var24 = new Object[]{"412", var20};
      var25 = new Object[]{"414", var20};
      Object var46 = VARIABLE_LENGTH;
      Object[] var60 = new Object[]{"421", VARIABLE_LENGTH, var57};
      var27 = new Object[]{"423", VARIABLE_LENGTH, var57};
      THREE_DIGIT_DATA_LENGTH = new Object[][]{var12, var14, {"242", var41, var9}, var55, var54, var53, {"254", var49, var7}, {"400", var48, var52}, {"401", var43, var52}, {"402", var59}, {"403", var50, var52}, var16, var22, var24, {"413", var20}, var25, {"420", var46, var7}, var60, {"422", var5}, var27, {"424", var5}, {"425", var5}, {"426", var5}};
      Object[] var51 = new Object[]{"310", var9};
      var8 = new Object[]{"311", var9};
      var10 = new Object[]{"313", var9};
      var11 = new Object[]{"315", var9};
      var12 = new Object[]{"316", var9};
      var14 = new Object[]{"320", var9};
      var55 = new Object[]{"321", var9};
      var54 = new Object[]{"324", var9};
      var53 = new Object[]{"330", var9};
      var16 = new Object[]{"331", var9};
      var22 = new Object[]{"334", var9};
      var24 = new Object[]{"340", var9};
      var25 = new Object[]{"341", var9};
      var60 = new Object[]{"344", var9};
      var27 = new Object[]{"346", var9};
      var28 = new Object[]{"350", var9};
      var29 = new Object[]{"351", var9};
      var30 = new Object[]{"354", var9};
      var31 = new Object[]{"356", var9};
      var32 = new Object[]{"357", var9};
      Object[] var33 = new Object[]{"361", var9};
      Object[] var34 = new Object[]{"362", var9};
      Object[] var35 = new Object[]{"365", var9};
      Object[] var36 = new Object[]{"366", var9};
      Object[] var37 = new Object[]{"367", var9};
      Object[] var38 = new Object[]{"390", VARIABLE_LENGTH, var57};
      var23 = VARIABLE_LENGTH;
      var43 = VARIABLE_LENGTH;
      Object var44 = VARIABLE_LENGTH;
      var41 = VARIABLE_LENGTH;
      THREE_DIGIT_PLUS_DIGIT_DATA_LENGTH = new Object[][]{var51, var8, {"312", var9}, var10, {"314", var9}, var11, var12, var14, var55, {"322", var9}, {"323", var9}, var54, {"325", var9}, {"326", var9}, {"327", var9}, {"328", var9}, {"329", var9}, var53, var16, {"332", var9}, {"333", var9}, var22, {"335", var9}, {"336", var9}, var24, var25, {"342", var9}, {"343", var9}, var60, {"345", var9}, var27, {"347", var9}, {"348", var9}, {"349", var9}, var28, var29, {"352", var9}, {"353", var9}, var30, {"355", var9}, var31, var32, {"360", var9}, var33, var34, {"363", var9}, {"364", var9}, var35, var36, var37, {"368", var9}, {"369", var9}, var38, {"391", var23, var1}, {"392", var43, var57}, {"393", var44, var1}, {"703", var41, var52}};
      Object[] var58 = new Object[]{"7001", var20};
      Object var56 = VARIABLE_LENGTH;
      var51 = new Object[]{"8001", var3};
      var41 = VARIABLE_LENGTH;
      var44 = VARIABLE_LENGTH;
      var43 = VARIABLE_LENGTH;
      var8 = new Object[]{"8005", var9};
      var10 = new Object[]{"8006", var1};
      Object var42 = VARIABLE_LENGTH;
      var23 = VARIABLE_LENGTH;
      Object[] var40 = new Object[]{"8018", var1};
      var11 = new Object[]{"8020", VARIABLE_LENGTH, 25};
      Object[] var47 = new Object[]{"8100", var9};
      var12 = new Object[]{"8101", var45};
      var14 = new Object[]{"8102", var0};
      Object var39 = VARIABLE_LENGTH;
      var55 = new Object[]{"8200", VARIABLE_LENGTH, 70};
      FOUR_DIGIT_DATA_LENGTH = new Object[][]{var58, {"7002", var56, var52}, {"7003", var45}, var51, {"8002", var41, var7}, {"8003", var44, var52}, {"8004", var43, var52}, var8, var10, {"8007", var42, var52}, {"8008", var23, 12}, var40, var11, var47, var12, var14, {"8110", var39, 70}, var55};
   }

   private FieldParser() {
   }

   static String parseFieldsInGeneralPurpose(String var0) throws NotFoundException {
      if (var0.isEmpty()) {
         return null;
      } else if (var0.length() >= 2) {
         String var1 = var0.substring(0, 2);
         Object[][] var2 = TWO_DIGIT_DATA_LENGTH;
         int var3 = var2.length;

         int var4;
         for(var4 = 0; var4 < var3; ++var4) {
            Object[] var5 = var2[var4];
            if (var5[0].equals(var1)) {
               if (var5[1] == VARIABLE_LENGTH) {
                  return processVariableAI(2, (Integer)var5[2], var0);
               }

               return processFixedAI(2, (Integer)var5[1], var0);
            }
         }

         if (var0.length() >= 3) {
            String var9 = var0.substring(0, 3);
            Object[][] var6 = THREE_DIGIT_DATA_LENGTH;
            var3 = var6.length;

            Object[] var8;
            for(var4 = 0; var4 < var3; ++var4) {
               var8 = var6[var4];
               if (var8[0].equals(var9)) {
                  if (var8[1] == VARIABLE_LENGTH) {
                     return processVariableAI(3, (Integer)var8[2], var0);
                  }

                  return processFixedAI(3, (Integer)var8[1], var0);
               }
            }

            var2 = THREE_DIGIT_PLUS_DIGIT_DATA_LENGTH;
            var3 = var2.length;

            for(var4 = 0; var4 < var3; ++var4) {
               Object[] var7 = var2[var4];
               if (var7[0].equals(var9)) {
                  if (var7[1] == VARIABLE_LENGTH) {
                     return processVariableAI(4, (Integer)var7[2], var0);
                  }

                  return processFixedAI(4, (Integer)var7[1], var0);
               }
            }

            if (var0.length() >= 4) {
               var9 = var0.substring(0, 4);
               var6 = FOUR_DIGIT_DATA_LENGTH;
               var3 = var6.length;

               for(var4 = 0; var4 < var3; ++var4) {
                  var8 = var6[var4];
                  if (var8[0].equals(var9)) {
                     if (var8[1] == VARIABLE_LENGTH) {
                        return processVariableAI(4, (Integer)var8[2], var0);
                     }

                     return processFixedAI(4, (Integer)var8[1], var0);
                  }
               }

               throw NotFoundException.getNotFoundInstance();
            } else {
               throw NotFoundException.getNotFoundInstance();
            }
         } else {
            throw NotFoundException.getNotFoundInstance();
         }
      } else {
         throw NotFoundException.getNotFoundInstance();
      }
   }

   private static String processFixedAI(int var0, int var1, String var2) throws NotFoundException {
      if (var2.length() >= var0) {
         String var3 = var2.substring(0, var0);
         int var4 = var2.length();
         var1 += var0;
         if (var4 >= var1) {
            String var5 = var2.substring(var0, var1);
            String var6 = var2.substring(var1);
            StringBuilder var7 = new StringBuilder();
            var7.append('(');
            var7.append(var3);
            var7.append(')');
            var7.append(var5);
            var2 = var7.toString();
            var5 = parseFieldsInGeneralPurpose(var6);
            if (var5 != null) {
               StringBuilder var8 = new StringBuilder();
               var8.append(var2);
               var8.append(var5);
               var2 = var8.toString();
            }

            return var2;
         } else {
            throw NotFoundException.getNotFoundInstance();
         }
      } else {
         throw NotFoundException.getNotFoundInstance();
      }
   }

   private static String processVariableAI(int var0, int var1, String var2) throws NotFoundException {
      String var3 = var2.substring(0, var0);
      int var4 = var2.length();
      int var5 = var1 + var0;
      var1 = var5;
      if (var4 < var5) {
         var1 = var2.length();
      }

      String var6 = var2.substring(var0, var1);
      String var7 = var2.substring(var1);
      StringBuilder var8 = new StringBuilder();
      var8.append('(');
      var8.append(var3);
      var8.append(')');
      var8.append(var6);
      var2 = var8.toString();
      var3 = parseFieldsInGeneralPurpose(var7);
      if (var3 != null) {
         StringBuilder var9 = new StringBuilder();
         var9.append(var2);
         var9.append(var3);
         var2 = var9.toString();
      }

      return var2;
   }
}
