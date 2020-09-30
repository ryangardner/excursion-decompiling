package javax.mail.search;

import javax.mail.Address;

public abstract class AddressTerm extends SearchTerm {
   private static final long serialVersionUID = 2005405551929769980L;
   protected Address address;

   protected AddressTerm(Address var1) {
      this.address = var1;
   }

   public boolean equals(Object var1) {
      return !(var1 instanceof AddressTerm) ? false : ((AddressTerm)var1).address.equals(this.address);
   }

   public Address getAddress() {
      return this.address;
   }

   public int hashCode() {
      return this.address.hashCode();
   }

   protected boolean match(Address var1) {
      return var1.equals(this.address);
   }
}
