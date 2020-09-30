package com.google.zxing;

public enum BarcodeFormat {
   AZTEC,
   CODABAR,
   CODE_128,
   CODE_39,
   CODE_93,
   DATA_MATRIX,
   EAN_13,
   EAN_8,
   ITF,
   MAXICODE,
   PDF_417,
   QR_CODE,
   RSS_14,
   RSS_EXPANDED,
   UPC_A,
   UPC_E,
   UPC_EAN_EXTENSION;

   static {
      BarcodeFormat var0 = new BarcodeFormat("UPC_EAN_EXTENSION", 16);
      UPC_EAN_EXTENSION = var0;
   }
}
