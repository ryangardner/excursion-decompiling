package androidx.lifecycle;

import java.util.HashMap;
import java.util.Map;

public class MethodCallsLogger {
   private Map<String, Integer> mCalledMethods = new HashMap();

   public boolean approveCall(String var1, int var2) {
      Integer var3 = (Integer)this.mCalledMethods.get(var1);
      boolean var4 = false;
      int var5;
      if (var3 != null) {
         var5 = var3;
      } else {
         var5 = 0;
      }

      if ((var5 & var2) != 0) {
         var4 = true;
      }

      this.mCalledMethods.put(var1, var2 | var5);
      return var4 ^ true;
   }
}
