package com.google.android.gms.internal.drive;

final class zznj extends IllegalArgumentException {
   zznj(int var1, int var2) {
      StringBuilder var3 = new StringBuilder(54);
      var3.append("Unpaired surrogate at index ");
      var3.append(var1);
      var3.append(" of ");
      var3.append(var2);
      super(var3.toString());
   }
}
