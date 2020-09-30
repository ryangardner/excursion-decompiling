package org.apache.commons.net.ntp;

import java.net.DatagramPacket;
import java.util.Arrays;

public class NtpV3Impl implements NtpV3Packet {
   private static final int LI_INDEX = 0;
   private static final int LI_SHIFT = 6;
   private static final int MODE_INDEX = 0;
   private static final int MODE_SHIFT = 0;
   private static final int ORIGINATE_TIMESTAMP_INDEX = 24;
   private static final int POLL_INDEX = 2;
   private static final int PRECISION_INDEX = 3;
   private static final int RECEIVE_TIMESTAMP_INDEX = 32;
   private static final int REFERENCE_ID_INDEX = 12;
   private static final int REFERENCE_TIMESTAMP_INDEX = 16;
   private static final int ROOT_DELAY_INDEX = 4;
   private static final int ROOT_DISPERSION_INDEX = 8;
   private static final int STRATUM_INDEX = 1;
   private static final int TRANSMIT_TIMESTAMP_INDEX = 40;
   private static final int VERSION_INDEX = 0;
   private static final int VERSION_SHIFT = 3;
   private final byte[] buf = new byte[48];
   private volatile DatagramPacket dp;

   private int getInt(int var1) {
      int var2 = ui(this.buf[var1]);
      int var3 = ui(this.buf[var1 + 1]);
      int var4 = ui(this.buf[var1 + 2]);
      return ui(this.buf[var1 + 3]) | var2 << 24 | var3 << 16 | var4 << 8;
   }

   private long getLong(int var1) {
      return ul(this.buf[var1]) << 56 | ul(this.buf[var1 + 1]) << 48 | ul(this.buf[var1 + 2]) << 40 | ul(this.buf[var1 + 3]) << 32 | ul(this.buf[var1 + 4]) << 24 | ul(this.buf[var1 + 5]) << 16 | ul(this.buf[var1 + 6]) << 8 | ul(this.buf[var1 + 7]);
   }

   private TimeStamp getTimestamp(int var1) {
      return new TimeStamp(this.getLong(var1));
   }

   private String idAsHex() {
      return Integer.toHexString(this.getReferenceId());
   }

   private String idAsIPAddress() {
      StringBuilder var1 = new StringBuilder();
      var1.append(ui(this.buf[12]));
      var1.append(".");
      var1.append(ui(this.buf[13]));
      var1.append(".");
      var1.append(ui(this.buf[14]));
      var1.append(".");
      var1.append(ui(this.buf[15]));
      return var1.toString();
   }

   private String idAsString() {
      StringBuilder var1 = new StringBuilder();

      for(int var2 = 0; var2 <= 3; ++var2) {
         char var3 = (char)this.buf[var2 + 12];
         if (var3 == 0) {
            break;
         }

         var1.append(var3);
      }

      return var1.toString();
   }

   private void setInt(int var1, int var2) {
      for(int var3 = 3; var3 >= 0; --var3) {
         this.buf[var1 + var3] = (byte)((byte)(var2 & 255));
         var2 >>>= 8;
      }

   }

   private void setTimestamp(int var1, TimeStamp var2) {
      long var3;
      if (var2 == null) {
         var3 = 0L;
      } else {
         var3 = var2.ntpValue();
      }

      for(int var5 = 7; var5 >= 0; --var5) {
         this.buf[var1 + var5] = (byte)((byte)((int)(255L & var3)));
         var3 >>>= 8;
      }

   }

   protected static final int ui(byte var0) {
      return var0 & 255;
   }

   protected static final long ul(byte var0) {
      return (long)(var0 & 255);
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (var1 != null && this.getClass() == var1.getClass()) {
         NtpV3Impl var2 = (NtpV3Impl)var1;
         return Arrays.equals(this.buf, var2.buf);
      } else {
         return false;
      }
   }

   public DatagramPacket getDatagramPacket() {
      synchronized(this){}

      DatagramPacket var1;
      try {
         if (this.dp == null) {
            var1 = new DatagramPacket(this.buf, this.buf.length);
            this.dp = var1;
            this.dp.setPort(123);
         }

         var1 = this.dp;
      } finally {
         ;
      }

      return var1;
   }

   public int getLeapIndicator() {
      return ui(this.buf[0]) >> 6 & 3;
   }

   public int getMode() {
      return ui(this.buf[0]) >> 0 & 7;
   }

   public String getModeName() {
      return NtpUtils.getModeName(this.getMode());
   }

   public TimeStamp getOriginateTimeStamp() {
      return this.getTimestamp(24);
   }

   public int getPoll() {
      return this.buf[2];
   }

   public int getPrecision() {
      return this.buf[3];
   }

   public TimeStamp getReceiveTimeStamp() {
      return this.getTimestamp(32);
   }

   public int getReferenceId() {
      return this.getInt(12);
   }

   public String getReferenceIdString() {
      int var1 = this.getVersion();
      int var2 = this.getStratum();
      if (var1 == 3 || var1 == 4) {
         if (var2 == 0 || var2 == 1) {
            return this.idAsString();
         }

         if (var1 == 4) {
            return this.idAsHex();
         }
      }

      return var2 >= 2 ? this.idAsIPAddress() : this.idAsHex();
   }

   public TimeStamp getReferenceTimeStamp() {
      return this.getTimestamp(16);
   }

   public int getRootDelay() {
      return this.getInt(4);
   }

   public double getRootDelayInMillisDouble() {
      return (double)this.getRootDelay() / 65.536D;
   }

   public int getRootDispersion() {
      return this.getInt(8);
   }

   public long getRootDispersionInMillis() {
      return (long)this.getRootDispersion() * 1000L / 65536L;
   }

   public double getRootDispersionInMillisDouble() {
      return (double)this.getRootDispersion() / 65.536D;
   }

   public int getStratum() {
      return ui(this.buf[1]);
   }

   public TimeStamp getTransmitTimeStamp() {
      return this.getTimestamp(40);
   }

   public String getType() {
      return "NTP";
   }

   public int getVersion() {
      return ui(this.buf[0]) >> 3 & 7;
   }

   public int hashCode() {
      return Arrays.hashCode(this.buf);
   }

   public void setDatagramPacket(DatagramPacket var1) {
      if (var1 != null && var1.getLength() >= this.buf.length) {
         byte[] var2 = var1.getData();
         int var3 = var1.getLength();
         byte[] var4 = this.buf;
         int var5 = var3;
         if (var3 > var4.length) {
            var5 = var4.length;
         }

         System.arraycopy(var2, 0, this.buf, 0, var5);
         DatagramPacket var6 = this.getDatagramPacket();
         var6.setAddress(var1.getAddress());
         var5 = var1.getPort();
         if (var5 <= 0) {
            var5 = 123;
         }

         var6.setPort(var5);
         var6.setData(this.buf);
      } else {
         throw new IllegalArgumentException();
      }
   }

   public void setLeapIndicator(int var1) {
      byte[] var2 = this.buf;
      var2[0] = (byte)((byte)((var1 & 3) << 6 | var2[0] & 63));
   }

   public void setMode(int var1) {
      byte[] var2 = this.buf;
      var2[0] = (byte)((byte)(var1 & 7 | var2[0] & 248));
   }

   public void setOriginateTimeStamp(TimeStamp var1) {
      this.setTimestamp(24, var1);
   }

   public void setPoll(int var1) {
      this.buf[2] = (byte)((byte)(var1 & 255));
   }

   public void setPrecision(int var1) {
      this.buf[3] = (byte)((byte)(var1 & 255));
   }

   public void setReceiveTimeStamp(TimeStamp var1) {
      this.setTimestamp(32, var1);
   }

   public void setReferenceId(int var1) {
      this.setInt(12, var1);
   }

   public void setReferenceTime(TimeStamp var1) {
      this.setTimestamp(16, var1);
   }

   public void setRootDelay(int var1) {
      this.setInt(4, var1);
   }

   public void setRootDispersion(int var1) {
      this.setInt(8, var1);
   }

   public void setStratum(int var1) {
      this.buf[1] = (byte)((byte)(var1 & 255));
   }

   public void setTransmitTime(TimeStamp var1) {
      this.setTimestamp(40, var1);
   }

   public void setVersion(int var1) {
      byte[] var2 = this.buf;
      var2[0] = (byte)((byte)((var1 & 7) << 3 | var2[0] & 199));
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("[version:");
      var1.append(this.getVersion());
      var1.append(", mode:");
      var1.append(this.getMode());
      var1.append(", poll:");
      var1.append(this.getPoll());
      var1.append(", precision:");
      var1.append(this.getPrecision());
      var1.append(", delay:");
      var1.append(this.getRootDelay());
      var1.append(", dispersion(ms):");
      var1.append(this.getRootDispersionInMillisDouble());
      var1.append(", id:");
      var1.append(this.getReferenceIdString());
      var1.append(", xmitTime:");
      var1.append(this.getTransmitTimeStamp().toDateString());
      var1.append(" ]");
      return var1.toString();
   }
}
