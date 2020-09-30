package javax.mail.search;

import javax.mail.Message;

public final class NotTerm extends SearchTerm {
   private static final long serialVersionUID = 7152293214217310216L;
   protected SearchTerm term;

   public NotTerm(SearchTerm var1) {
      this.term = var1;
   }

   public boolean equals(Object var1) {
      return !(var1 instanceof NotTerm) ? false : ((NotTerm)var1).term.equals(this.term);
   }

   public SearchTerm getTerm() {
      return this.term;
   }

   public int hashCode() {
      return this.term.hashCode() << 1;
   }

   public boolean match(Message var1) {
      return this.term.match(var1) ^ true;
   }
}
