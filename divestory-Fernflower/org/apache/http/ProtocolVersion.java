package org.apache.http;

import java.io.Serializable;
import org.apache.http.util.CharArrayBuffer;

public class ProtocolVersion implements Serializable, Cloneable {
   private static final long serialVersionUID = 8950662842175091068L;
   protected final int major;
   protected final int minor;
   protected final String protocol;

   public ProtocolVersion(String var1, int var2, int var3) {
      if (var1 != null) {
         if (var2 >= 0) {
            if (var3 >= 0) {
               this.protocol = var1;
               this.major = var2;
               this.minor = var3;
            } else {
               throw new IllegalArgumentException("Protocol minor version number may not be negative");
            }
         } else {
            throw new IllegalArgumentException("Protocol major version number must not be negative.");
         }
      } else {
         throw new IllegalArgumentException("Protocol name must not be null.");
      }
   }

   public Object clone() throws CloneNotSupportedException {
      return super.clone();
   }

   public int compareToVersion(ProtocolVersion var1) {
      if (var1 != null) {
         if (this.protocol.equals(var1.protocol)) {
            int var2 = this.getMajor() - var1.getMajor();
            int var3 = var2;
            if (var2 == 0) {
               var3 = this.getMinor() - var1.getMinor();
            }

            return var3;
         } else {
            StringBuffer var4 = new StringBuffer();
            var4.append("Versions for different protocols cannot be compared. ");
            var4.append(this);
            var4.append(" ");
            var4.append(var1);
            throw new IllegalArgumentException(var4.toString());
         }
      } else {
         throw new IllegalArgumentException("Protocol version must not be null.");
      }
   }

   public final boolean equals(Object var1) {
      boolean var2 = true;
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof ProtocolVersion)) {
         return false;
      } else {
         ProtocolVersion var3 = (ProtocolVersion)var1;
         if (!this.protocol.equals(var3.protocol) || this.major != var3.major || this.minor != var3.minor) {
            var2 = false;
         }

         return var2;
      }
   }

   public ProtocolVersion forVersion(int var1, int var2) {
      return var1 == this.major && var2 == this.minor ? this : new ProtocolVersion(this.protocol, var1, var2);
   }

   public final int getMajor() {
      return this.major;
   }

   public final int getMinor() {
      return this.minor;
   }

   public final String getProtocol() {
      return this.protocol;
   }

   public final boolean greaterEquals(ProtocolVersion var1) {
      boolean var2;
      if (this.isComparable(var1) && this.compareToVersion(var1) >= 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public final int hashCode() {
      return this.protocol.hashCode() ^ this.major * 100000 ^ this.minor;
   }

   public boolean isComparable(ProtocolVersion var1) {
      boolean var2;
      if (var1 != null && this.protocol.equals(var1.protocol)) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public final boolean lessEquals(ProtocolVersion var1) {
      boolean var2;
      if (this.isComparable(var1) && this.compareToVersion(var1) <= 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public String toString() {
      CharArrayBuffer var1 = new CharArrayBuffer(16);
      var1.append(this.protocol);
      var1.append('/');
      var1.append(Integer.toString(this.major));
      var1.append('.');
      var1.append(Integer.toString(this.minor));
      return var1.toString();
   }
}
