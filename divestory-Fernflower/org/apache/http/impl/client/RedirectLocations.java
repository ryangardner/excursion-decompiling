package org.apache.http.impl.client;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class RedirectLocations {
   private final List<URI> all = new ArrayList();
   private final Set<URI> unique = new HashSet();

   public void add(URI var1) {
      this.unique.add(var1);
      this.all.add(var1);
   }

   public boolean contains(URI var1) {
      return this.unique.contains(var1);
   }

   public List<URI> getAll() {
      return new ArrayList(this.all);
   }

   public boolean remove(URI var1) {
      boolean var2 = this.unique.remove(var1);
      if (var2) {
         Iterator var3 = this.all.iterator();

         while(var3.hasNext()) {
            if (((URI)var3.next()).equals(var1)) {
               var3.remove();
            }
         }
      }

      return var2;
   }
}
