package javax.mail.search;

import javax.mail.Message;

public final class AndTerm extends SearchTerm {
   private static final long serialVersionUID = -3583274505380989582L;
   protected SearchTerm[] terms;

   public AndTerm(SearchTerm var1, SearchTerm var2) {
      SearchTerm[] var3 = new SearchTerm[2];
      this.terms = var3;
      var3[0] = var1;
      var3[1] = var2;
   }

   public AndTerm(SearchTerm[] var1) {
      this.terms = new SearchTerm[var1.length];

      for(int var2 = 0; var2 < var1.length; ++var2) {
         this.terms[var2] = var1[var2];
      }

   }

   public boolean equals(Object var1) {
      if (!(var1 instanceof AndTerm)) {
         return false;
      } else {
         AndTerm var4 = (AndTerm)var1;
         if (var4.terms.length != this.terms.length) {
            return false;
         } else {
            int var2 = 0;

            while(true) {
               SearchTerm[] var3 = this.terms;
               if (var2 >= var3.length) {
                  return true;
               }

               if (!var3[var2].equals(var4.terms[var2])) {
                  return false;
               }

               ++var2;
            }
         }
      }
   }

   public SearchTerm[] getTerms() {
      return (SearchTerm[])this.terms.clone();
   }

   public int hashCode() {
      int var1 = 0;
      int var2 = 0;

      while(true) {
         SearchTerm[] var3 = this.terms;
         if (var1 >= var3.length) {
            return var2;
         }

         var2 += var3[var1].hashCode();
         ++var1;
      }
   }

   public boolean match(Message var1) {
      int var2 = 0;

      while(true) {
         SearchTerm[] var3 = this.terms;
         if (var2 >= var3.length) {
            return true;
         }

         if (!var3[var2].match(var1)) {
            return false;
         }

         ++var2;
      }
   }
}
