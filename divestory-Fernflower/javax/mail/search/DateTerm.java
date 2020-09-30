package javax.mail.search;

import java.util.Date;

public abstract class DateTerm extends ComparisonTerm {
   private static final long serialVersionUID = 4818873430063720043L;
   protected Date date;

   protected DateTerm(int var1, Date var2) {
      this.comparison = var1;
      this.date = var2;
   }

   public boolean equals(Object var1) {
      if (!(var1 instanceof DateTerm)) {
         return false;
      } else {
         return ((DateTerm)var1).date.equals(this.date) && super.equals(var1);
      }
   }

   public int getComparison() {
      return this.comparison;
   }

   public Date getDate() {
      return new Date(this.date.getTime());
   }

   public int hashCode() {
      return this.date.hashCode() + super.hashCode();
   }

   protected boolean match(Date var1) {
      switch(this.comparison) {
      case 1:
         if (!var1.before(this.date) && !var1.equals(this.date)) {
            return false;
         }

         return true;
      case 2:
         return var1.before(this.date);
      case 3:
         return var1.equals(this.date);
      case 4:
         return var1.equals(this.date) ^ true;
      case 5:
         return var1.after(this.date);
      case 6:
         if (!var1.after(this.date) && !var1.equals(this.date)) {
            return false;
         }

         return true;
      default:
         return false;
      }
   }
}
