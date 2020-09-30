package org.apache.commons.net.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SubnetUtils {
   private static final String IP_ADDRESS = "(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})";
   private static final int NBITS = 32;
   private static final String SLASH_FORMAT = "(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})/(\\d{1,3})";
   private static final Pattern addressPattern = Pattern.compile("(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})");
   private static final Pattern cidrPattern = Pattern.compile("(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})/(\\d{1,3})");
   private int address = 0;
   private int broadcast = 0;
   private boolean inclusiveHostCount = false;
   private int netmask = 0;
   private int network = 0;

   public SubnetUtils(String var1) {
      this.calculate(var1);
   }

   public SubnetUtils(String var1, String var2) {
      this.calculate(this.toCidrNotation(var1, var2));
   }

   private void calculate(String var1) {
      Matcher var2 = cidrPattern.matcher(var1);
      if (!var2.matches()) {
         StringBuilder var5 = new StringBuilder();
         var5.append("Could not parse [");
         var5.append(var1);
         var5.append("]");
         throw new IllegalArgumentException(var5.toString());
      } else {
         this.address = this.matchAddress(var2);
         int var3 = Integer.parseInt(var2.group(5));
         int var4 = 0;

         for(var3 = this.rangeCheck(var3, 0, 32); var4 < var3; ++var4) {
            this.netmask |= 1 << 31 - var4;
         }

         var3 = this.address;
         var4 = this.netmask;
         var3 &= var4;
         this.network = var3;
         this.broadcast = var3 | var4;
      }
   }

   private String format(int[] var1) {
      StringBuilder var2 = new StringBuilder();

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2.append(var1[var3]);
         if (var3 != var1.length - 1) {
            var2.append(".");
         }
      }

      return var2.toString();
   }

   private int matchAddress(Matcher var1) {
      int var2 = 1;

      int var3;
      for(var3 = 0; var2 <= 4; ++var2) {
         var3 |= (this.rangeCheck(Integer.parseInt(var1.group(var2)), 0, 255) & 255) << (4 - var2) * 8;
      }

      return var3;
   }

   private int rangeCheck(int var1, int var2, int var3) {
      if (var1 >= var2 && var1 <= var3) {
         return var1;
      } else {
         StringBuilder var4 = new StringBuilder();
         var4.append("Value [");
         var4.append(var1);
         var4.append("] not in range [");
         var4.append(var2);
         var4.append(",");
         var4.append(var3);
         var4.append("]");
         throw new IllegalArgumentException(var4.toString());
      }
   }

   private int[] toArray(int var1) {
      int[] var2 = new int[4];

      for(int var3 = 3; var3 >= 0; --var3) {
         var2[var3] |= var1 >>> (3 - var3) * 8 & 255;
      }

      return var2;
   }

   private String toCidrNotation(String var1, String var2) {
      StringBuilder var3 = new StringBuilder();
      var3.append(var1);
      var3.append("/");
      var3.append(this.pop(this.toInteger(var2)));
      return var3.toString();
   }

   private int toInteger(String var1) {
      Matcher var2 = addressPattern.matcher(var1);
      if (var2.matches()) {
         return this.matchAddress(var2);
      } else {
         StringBuilder var3 = new StringBuilder();
         var3.append("Could not parse [");
         var3.append(var1);
         var3.append("]");
         throw new IllegalArgumentException(var3.toString());
      }
   }

   public final SubnetUtils.SubnetInfo getInfo() {
      return new SubnetUtils.SubnetInfo();
   }

   public boolean isInclusiveHostCount() {
      return this.inclusiveHostCount;
   }

   int pop(int var1) {
      var1 -= var1 >>> 1 & 1431655765;
      var1 = (var1 & 858993459) + (var1 >>> 2 & 858993459);
      var1 = 252645135 & var1 + (var1 >>> 4);
      var1 += var1 >>> 8;
      return var1 + (var1 >>> 16) & 63;
   }

   public void setInclusiveHostCount(boolean var1) {
      this.inclusiveHostCount = var1;
   }

   public final class SubnetInfo {
      private static final long UNSIGNED_INT_MASK = 4294967295L;

      private SubnetInfo() {
      }

      // $FF: synthetic method
      SubnetInfo(Object var2) {
         this();
      }

      private int address() {
         return SubnetUtils.this.address;
      }

      private int broadcast() {
         return SubnetUtils.this.broadcast;
      }

      private long broadcastLong() {
         return (long)SubnetUtils.this.broadcast & 4294967295L;
      }

      private int high() {
         int var1;
         if (SubnetUtils.this.isInclusiveHostCount()) {
            var1 = this.broadcast();
         } else if (this.broadcastLong() - this.networkLong() > 1L) {
            var1 = this.broadcast() - 1;
         } else {
            var1 = 0;
         }

         return var1;
      }

      private int low() {
         int var1;
         if (SubnetUtils.this.isInclusiveHostCount()) {
            var1 = this.network();
         } else if (this.broadcastLong() - this.networkLong() > 1L) {
            var1 = this.network() + 1;
         } else {
            var1 = 0;
         }

         return var1;
      }

      private int netmask() {
         return SubnetUtils.this.netmask;
      }

      private int network() {
         return SubnetUtils.this.network;
      }

      private long networkLong() {
         return (long)SubnetUtils.this.network & 4294967295L;
      }

      public int asInteger(String var1) {
         return SubnetUtils.this.toInteger(var1);
      }

      public String getAddress() {
         SubnetUtils var1 = SubnetUtils.this;
         return var1.format(var1.toArray(this.address()));
      }

      @Deprecated
      public int getAddressCount() {
         long var1 = this.getAddressCountLong();
         if (var1 <= 2147483647L) {
            return (int)var1;
         } else {
            StringBuilder var3 = new StringBuilder();
            var3.append("Count is larger than an integer: ");
            var3.append(var1);
            throw new RuntimeException(var3.toString());
         }
      }

      public long getAddressCountLong() {
         long var1 = this.broadcastLong();
         long var3 = this.networkLong();
         byte var5;
         if (SubnetUtils.this.isInclusiveHostCount()) {
            var5 = 1;
         } else {
            var5 = -1;
         }

         var1 = var1 - var3 + (long)var5;
         var3 = var1;
         if (var1 < 0L) {
            var3 = 0L;
         }

         return var3;
      }

      public String[] getAllAddresses() {
         int var1 = this.getAddressCount();
         String[] var2 = new String[var1];
         if (var1 == 0) {
            return var2;
         } else {
            int var3 = this.low();

            for(var1 = 0; var3 <= this.high(); ++var1) {
               SubnetUtils var4 = SubnetUtils.this;
               var2[var1] = var4.format(var4.toArray(var3));
               ++var3;
            }

            return var2;
         }
      }

      public String getBroadcastAddress() {
         SubnetUtils var1 = SubnetUtils.this;
         return var1.format(var1.toArray(this.broadcast()));
      }

      public String getCidrSignature() {
         SubnetUtils var1 = SubnetUtils.this;
         String var2 = var1.format(var1.toArray(this.address()));
         SubnetUtils var3 = SubnetUtils.this;
         return var1.toCidrNotation(var2, var3.format(var3.toArray(this.netmask())));
      }

      public String getHighAddress() {
         SubnetUtils var1 = SubnetUtils.this;
         return var1.format(var1.toArray(this.high()));
      }

      public String getLowAddress() {
         SubnetUtils var1 = SubnetUtils.this;
         return var1.format(var1.toArray(this.low()));
      }

      public String getNetmask() {
         SubnetUtils var1 = SubnetUtils.this;
         return var1.format(var1.toArray(this.netmask()));
      }

      public String getNetworkAddress() {
         SubnetUtils var1 = SubnetUtils.this;
         return var1.format(var1.toArray(this.network()));
      }

      public boolean isInRange(int var1) {
         long var2 = (long)var1 & 4294967295L;
         long var4 = (long)this.low();
         long var6 = (long)this.high();
         boolean var8;
         if (var2 >= (var4 & 4294967295L) && var2 <= (4294967295L & var6)) {
            var8 = true;
         } else {
            var8 = false;
         }

         return var8;
      }

      public boolean isInRange(String var1) {
         return this.isInRange(SubnetUtils.this.toInteger(var1));
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append("CIDR Signature:\t[");
         var1.append(this.getCidrSignature());
         var1.append("]");
         var1.append(" Netmask: [");
         var1.append(this.getNetmask());
         var1.append("]\n");
         var1.append("Network:\t[");
         var1.append(this.getNetworkAddress());
         var1.append("]\n");
         var1.append("Broadcast:\t[");
         var1.append(this.getBroadcastAddress());
         var1.append("]\n");
         var1.append("First Address:\t[");
         var1.append(this.getLowAddress());
         var1.append("]\n");
         var1.append("Last Address:\t[");
         var1.append(this.getHighAddress());
         var1.append("]\n");
         var1.append("# Addresses:\t[");
         var1.append(this.getAddressCount());
         var1.append("]\n");
         return var1.toString();
      }
   }
}
