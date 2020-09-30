package org.apache.http.params;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class BasicHttpParams extends AbstractHttpParams implements Serializable, Cloneable {
   private static final long serialVersionUID = -7086398485908701455L;
   private final HashMap parameters = new HashMap();

   public void clear() {
      this.parameters.clear();
   }

   public Object clone() throws CloneNotSupportedException {
      BasicHttpParams var1 = (BasicHttpParams)super.clone();
      this.copyParams(var1);
      return var1;
   }

   public HttpParams copy() {
      try {
         HttpParams var1 = (HttpParams)this.clone();
         return var1;
      } catch (CloneNotSupportedException var2) {
         throw new UnsupportedOperationException("Cloning not supported");
      }
   }

   protected void copyParams(HttpParams var1) {
      Iterator var2 = this.parameters.entrySet().iterator();

      while(var2.hasNext()) {
         Entry var3 = (Entry)var2.next();
         if (var3.getKey() instanceof String) {
            var1.setParameter((String)var3.getKey(), var3.getValue());
         }
      }

   }

   public Object getParameter(String var1) {
      return this.parameters.get(var1);
   }

   public boolean isParameterSet(String var1) {
      boolean var2;
      if (this.getParameter(var1) != null) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public boolean isParameterSetLocally(String var1) {
      boolean var2;
      if (this.parameters.get(var1) != null) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public boolean removeParameter(String var1) {
      if (this.parameters.containsKey(var1)) {
         this.parameters.remove(var1);
         return true;
      } else {
         return false;
      }
   }

   public HttpParams setParameter(String var1, Object var2) {
      this.parameters.put(var1, var2);
      return this;
   }

   public void setParameters(String[] var1, Object var2) {
      for(int var3 = 0; var3 < var1.length; ++var3) {
         this.setParameter(var1[var3], var2);
      }

   }
}
