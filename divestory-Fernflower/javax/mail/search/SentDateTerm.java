package javax.mail.search;

import java.util.Date;
import javax.mail.Message;

public final class SentDateTerm extends DateTerm {
   private static final long serialVersionUID = 5647755030530907263L;

   public SentDateTerm(int var1, Date var2) {
      super(var1, var2);
   }

   public boolean equals(Object var1) {
      return !(var1 instanceof SentDateTerm) ? false : super.equals(var1);
   }

   public boolean match(Message var1) {
      Date var3;
      try {
         var3 = var1.getSentDate();
      } catch (Exception var2) {
         return false;
      }

      return var3 == null ? false : super.match(var3);
   }
}
