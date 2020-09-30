package javax.mail.search;

import javax.mail.Address;
import javax.mail.Message;

public final class FromTerm extends AddressTerm {
   private static final long serialVersionUID = 5214730291502658665L;

   public FromTerm(Address var1) {
      super(var1);
   }

   public boolean equals(Object var1) {
      return !(var1 instanceof FromTerm) ? false : super.equals(var1);
   }

   public boolean match(Message var1) {
      Address[] var4;
      try {
         var4 = var1.getFrom();
      } catch (Exception var3) {
         return false;
      }

      if (var4 == null) {
         return false;
      } else {
         for(int var2 = 0; var2 < var4.length; ++var2) {
            if (super.match(var4[var2])) {
               return true;
            }
         }

         return false;
      }
   }
}
