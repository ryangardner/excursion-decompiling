package javax.mail.search;

import javax.mail.Message;

public final class SizeTerm extends IntegerComparisonTerm {
   private static final long serialVersionUID = -2556219451005103709L;

   public SizeTerm(int var1, int var2) {
      super(var1, var2);
   }

   public boolean equals(Object var1) {
      return !(var1 instanceof SizeTerm) ? false : super.equals(var1);
   }

   public boolean match(Message var1) {
      int var2;
      try {
         var2 = var1.getSize();
      } catch (Exception var3) {
         return false;
      }

      return var2 == -1 ? false : super.match(var2);
   }
}
