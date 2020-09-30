package javax.mail.search;

import javax.mail.Address;
import javax.mail.Message;

public final class RecipientTerm extends AddressTerm {
   private static final long serialVersionUID = 6548700653122680468L;
   protected Message.RecipientType type;

   public RecipientTerm(Message.RecipientType var1, Address var2) {
      super(var2);
      this.type = var1;
   }

   public boolean equals(Object var1) {
      if (!(var1 instanceof RecipientTerm)) {
         return false;
      } else {
         return ((RecipientTerm)var1).type.equals(this.type) && super.equals(var1);
      }
   }

   public Message.RecipientType getRecipientType() {
      return this.type;
   }

   public int hashCode() {
      return this.type.hashCode() + super.hashCode();
   }

   public boolean match(Message var1) {
      Address[] var4;
      try {
         var4 = var1.getRecipients(this.type);
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
