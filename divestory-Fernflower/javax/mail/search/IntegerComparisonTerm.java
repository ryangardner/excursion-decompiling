package javax.mail.search;

public abstract class IntegerComparisonTerm extends ComparisonTerm {
   private static final long serialVersionUID = -6963571240154302484L;
   protected int number;

   protected IntegerComparisonTerm(int var1, int var2) {
      this.comparison = var1;
      this.number = var2;
   }

   public boolean equals(Object var1) {
      if (!(var1 instanceof IntegerComparisonTerm)) {
         return false;
      } else {
         return ((IntegerComparisonTerm)var1).number == this.number && super.equals(var1);
      }
   }

   public int getComparison() {
      return this.comparison;
   }

   public int getNumber() {
      return this.number;
   }

   public int hashCode() {
      return this.number + super.hashCode();
   }

   protected boolean match(int var1) {
      switch(this.comparison) {
      case 1:
         if (var1 <= this.number) {
            return true;
         }

         return false;
      case 2:
         if (var1 < this.number) {
            return true;
         }

         return false;
      case 3:
         if (var1 == this.number) {
            return true;
         }

         return false;
      case 4:
         if (var1 != this.number) {
            return true;
         }

         return false;
      case 5:
         if (var1 > this.number) {
            return true;
         }

         return false;
      case 6:
         if (var1 >= this.number) {
            return true;
         }

         return false;
      default:
         return false;
      }
   }
}
