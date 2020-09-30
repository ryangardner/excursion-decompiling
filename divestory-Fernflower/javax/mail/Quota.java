package javax.mail;

public class Quota {
   public String quotaRoot;
   public Quota.Resource[] resources;

   public Quota(String var1) {
      this.quotaRoot = var1;
   }

   public void setResourceLimit(String var1, long var2) {
      Quota.Resource[] var4;
      if (this.resources == null) {
         var4 = new Quota.Resource[1];
         this.resources = var4;
         var4[0] = new Quota.Resource(var1, 0L, var2);
      } else {
         int var5 = 0;

         while(true) {
            Quota.Resource[] var6 = this.resources;
            if (var5 >= var6.length) {
               var5 = var6.length + 1;
               var4 = new Quota.Resource[var5];
               System.arraycopy(var6, 0, var4, 0, var6.length);
               var4[var5 - 1] = new Quota.Resource(var1, 0L, var2);
               this.resources = var4;
               return;
            }

            if (var6[var5].name.equalsIgnoreCase(var1)) {
               this.resources[var5].limit = var2;
               return;
            }

            ++var5;
         }
      }
   }

   public static class Resource {
      public long limit;
      public String name;
      public long usage;

      public Resource(String var1, long var2, long var4) {
         this.name = var1;
         this.usage = var2;
         this.limit = var4;
      }
   }
}
