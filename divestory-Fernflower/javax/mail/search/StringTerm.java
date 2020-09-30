package javax.mail.search;

public abstract class StringTerm extends SearchTerm {
   private static final long serialVersionUID = 1274042129007696269L;
   protected boolean ignoreCase;
   protected String pattern;

   protected StringTerm(String var1) {
      this.pattern = var1;
      this.ignoreCase = true;
   }

   protected StringTerm(String var1, boolean var2) {
      this.pattern = var1;
      this.ignoreCase = var2;
   }

   public boolean equals(Object var1) {
      if (!(var1 instanceof StringTerm)) {
         return false;
      } else {
         StringTerm var2 = (StringTerm)var1;
         if (this.ignoreCase) {
            return var2.pattern.equalsIgnoreCase(this.pattern) && var2.ignoreCase == this.ignoreCase;
         } else {
            return var2.pattern.equals(this.pattern) && var2.ignoreCase == this.ignoreCase;
         }
      }
   }

   public boolean getIgnoreCase() {
      return this.ignoreCase;
   }

   public String getPattern() {
      return this.pattern;
   }

   public int hashCode() {
      int var1;
      if (this.ignoreCase) {
         var1 = this.pattern.hashCode();
      } else {
         var1 = this.pattern.hashCode();
      }

      return var1;
   }

   protected boolean match(String var1) {
      int var2 = var1.length();
      int var3 = this.pattern.length();

      for(int var4 = 0; var4 <= var2 - var3; ++var4) {
         boolean var5 = this.ignoreCase;
         String var6 = this.pattern;
         if (var1.regionMatches(var5, var4, var6, 0, var6.length())) {
            return true;
         }
      }

      return false;
   }
}
