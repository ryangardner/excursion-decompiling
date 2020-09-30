package org.apache.commons.net.ntp;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class TimeInfo {
   private List<String> _comments;
   private Long _delay;
   private boolean _detailsComputed;
   private final NtpV3Packet _message;
   private Long _offset;
   private final long _returnTime;

   public TimeInfo(NtpV3Packet var1, long var2) {
      this(var1, var2, (List)null, true);
   }

   public TimeInfo(NtpV3Packet var1, long var2, List<String> var4) {
      this(var1, var2, var4, true);
   }

   public TimeInfo(NtpV3Packet var1, long var2, List<String> var4, boolean var5) {
      if (var1 != null) {
         this._returnTime = var2;
         this._message = var1;
         this._comments = var4;
         if (var5) {
            this.computeDetails();
         }

      } else {
         throw new IllegalArgumentException("message cannot be null");
      }
   }

   public TimeInfo(NtpV3Packet var1, long var2, boolean var4) {
      this(var1, var2, (List)null, var4);
   }

   public void addComment(String var1) {
      if (this._comments == null) {
         this._comments = new ArrayList();
      }

      this._comments.add(var1);
   }

   public void computeDetails() {
      if (!this._detailsComputed) {
         this._detailsComputed = true;
         if (this._comments == null) {
            this._comments = new ArrayList();
         }

         TimeStamp var1 = this._message.getOriginateTimeStamp();
         long var2 = var1.getTime();
         TimeStamp var4 = this._message.getReceiveTimeStamp();
         long var5 = var4.getTime();
         TimeStamp var7 = this._message.getTransmitTimeStamp();
         long var8 = var7.getTime();
         long var10 = var1.ntpValue();
         long var12 = 0L;
         if (var10 == 0L) {
            if (var7.ntpValue() != 0L) {
               this._offset = var8 - this._returnTime;
               this._comments.add("Error: zero orig time -- cannot compute delay");
            } else {
               this._comments.add("Error: zero orig time -- cannot compute delay/offset");
            }
         } else if (var4.ntpValue() != 0L && var7.ntpValue() != 0L) {
            label49: {
               var10 = this._returnTime - var2;
               if (var8 < var5) {
                  this._comments.add("Error: xmitTime < rcvTime");
               } else {
                  long var14 = var8 - var5;
                  if (var14 <= var10) {
                     var12 = var10 - var14;
                     break label49;
                  }

                  if (var14 - var10 == 1L) {
                     if (var10 != 0L) {
                        this._comments.add("Info: processing time > total network time by 1 ms -> assume zero delay");
                        break label49;
                     }
                  } else {
                     this._comments.add("Warning: processing time > total network time");
                  }
               }

               var12 = var10;
            }

            this._delay = var12;
            if (var2 > this._returnTime) {
               this._comments.add("Error: OrigTime > DestRcvTime");
            }

            this._offset = (var5 - var2 + (var8 - this._returnTime)) / 2L;
         } else {
            this._comments.add("Warning: zero rcvNtpTime or xmitNtpTime");
            var12 = this._returnTime;
            if (var2 > var12) {
               this._comments.add("Error: OrigTime > DestRcvTime");
            } else {
               this._delay = var12 - var2;
            }

            if (var4.ntpValue() != 0L) {
               this._offset = var5 - var2;
            } else if (var7.ntpValue() != 0L) {
               this._offset = var8 - this._returnTime;
            }
         }

      }
   }

   public boolean equals(Object var1) {
      boolean var2 = true;
      if (this == var1) {
         return true;
      } else if (var1 != null && this.getClass() == var1.getClass()) {
         TimeInfo var3 = (TimeInfo)var1;
         if (this._returnTime != var3._returnTime || !this._message.equals(var3._message)) {
            var2 = false;
         }

         return var2;
      } else {
         return false;
      }
   }

   public InetAddress getAddress() {
      DatagramPacket var1 = this._message.getDatagramPacket();
      InetAddress var2;
      if (var1 == null) {
         var2 = null;
      } else {
         var2 = var1.getAddress();
      }

      return var2;
   }

   public List<String> getComments() {
      return this._comments;
   }

   public Long getDelay() {
      return this._delay;
   }

   public NtpV3Packet getMessage() {
      return this._message;
   }

   public Long getOffset() {
      return this._offset;
   }

   public long getReturnTime() {
      return this._returnTime;
   }

   public int hashCode() {
      return (int)this._returnTime * 31 + this._message.hashCode();
   }
}
