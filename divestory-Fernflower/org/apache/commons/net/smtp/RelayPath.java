package org.apache.commons.net.smtp;

import java.util.Enumeration;
import java.util.Vector;

public final class RelayPath {
   String _emailAddress;
   Vector<String> _path = new Vector();

   public RelayPath(String var1) {
      this._emailAddress = var1;
   }

   public void addRelay(String var1) {
      this._path.addElement(var1);
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append('<');
      Enumeration var2 = this._path.elements();
      if (var2.hasMoreElements()) {
         var1.append('@');
         var1.append((String)var2.nextElement());

         while(var2.hasMoreElements()) {
            var1.append(",@");
            var1.append((String)var2.nextElement());
         }

         var1.append(':');
      }

      var1.append(this._emailAddress);
      var1.append('>');
      return var1.toString();
   }
}
