package javax.mail.search;

import javax.mail.Message;

public final class MessageNumberTerm extends IntegerComparisonTerm {
   private static final long serialVersionUID = -5379625829658623812L;

   public MessageNumberTerm(int var1) {
      super(3, var1);
   }

   public boolean equals(Object var1) {
      return !(var1 instanceof MessageNumberTerm) ? false : super.equals(var1);
   }

   public boolean match(Message var1) {
      int var2;
      try {
         var2 = var1.getMessageNumber();
      } catch (Exception var3) {
         return false;
      }

      return super.match(var2);
   }
}
