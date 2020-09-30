package javax.mail;

import java.util.Vector;

public class FetchProfile {
   private Vector headers = null;
   private Vector specials = null;

   public void add(String var1) {
      if (this.headers == null) {
         this.headers = new Vector();
      }

      this.headers.addElement(var1);
   }

   public void add(FetchProfile.Item var1) {
      if (this.specials == null) {
         this.specials = new Vector();
      }

      this.specials.addElement(var1);
   }

   public boolean contains(String var1) {
      Vector var2 = this.headers;
      return var2 != null && var2.contains(var1);
   }

   public boolean contains(FetchProfile.Item var1) {
      Vector var2 = this.specials;
      return var2 != null && var2.contains(var1);
   }

   public String[] getHeaderNames() {
      Vector var1 = this.headers;
      if (var1 == null) {
         return new String[0];
      } else {
         String[] var2 = new String[var1.size()];
         this.headers.copyInto(var2);
         return var2;
      }
   }

   public FetchProfile.Item[] getItems() {
      Vector var1 = this.specials;
      if (var1 == null) {
         return new FetchProfile.Item[0];
      } else {
         FetchProfile.Item[] var2 = new FetchProfile.Item[var1.size()];
         this.specials.copyInto(var2);
         return var2;
      }
   }

   public static class Item {
      public static final FetchProfile.Item CONTENT_INFO = new FetchProfile.Item("CONTENT_INFO");
      public static final FetchProfile.Item ENVELOPE = new FetchProfile.Item("ENVELOPE");
      public static final FetchProfile.Item FLAGS = new FetchProfile.Item("FLAGS");
      private String name;

      protected Item(String var1) {
         this.name = var1;
      }
   }
}
