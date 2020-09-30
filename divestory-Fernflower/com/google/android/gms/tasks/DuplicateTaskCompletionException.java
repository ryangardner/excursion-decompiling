package com.google.android.gms.tasks;

public final class DuplicateTaskCompletionException extends IllegalStateException {
   private DuplicateTaskCompletionException(String var1, Throwable var2) {
      super(var1, var2);
   }

   public static IllegalStateException of(Task<?> var0) {
      if (!var0.isComplete()) {
         return new IllegalStateException("DuplicateTaskCompletionException can only be created from completed Task.");
      } else {
         Exception var1 = var0.getException();
         String var3;
         if (var1 != null) {
            var3 = "failure";
         } else if (var0.isSuccessful()) {
            var3 = String.valueOf(var0.getResult());
            StringBuilder var2 = new StringBuilder(String.valueOf(var3).length() + 7);
            var2.append("result ");
            var2.append(var3);
            var3 = var2.toString();
         } else if (var0.isCanceled()) {
            var3 = "cancellation";
         } else {
            var3 = "unknown issue";
         }

         var3 = String.valueOf(var3);
         if (var3.length() != 0) {
            var3 = "Complete with: ".concat(var3);
         } else {
            var3 = new String("Complete with: ");
         }

         return new DuplicateTaskCompletionException(var3, var1);
      }
   }
}
