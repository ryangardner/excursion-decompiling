package org.apache.commons.net.ntp;

import java.net.DatagramPacket;

public interface NtpV3Packet {
   int LI_ALARM_CONDITION = 3;
   int LI_LAST_MINUTE_HAS_59_SECONDS = 2;
   int LI_LAST_MINUTE_HAS_61_SECONDS = 1;
   int LI_NO_WARNING = 0;
   int MODE_BROADCAST = 5;
   int MODE_CLIENT = 3;
   int MODE_CONTROL_MESSAGE = 6;
   int MODE_PRIVATE = 7;
   int MODE_RESERVED = 0;
   int MODE_SERVER = 4;
   int MODE_SYMMETRIC_ACTIVE = 1;
   int MODE_SYMMETRIC_PASSIVE = 2;
   int NTP_MAXCLOCK = 10;
   int NTP_MAXPOLL = 14;
   int NTP_MINCLOCK = 1;
   int NTP_MINPOLL = 4;
   int NTP_PORT = 123;
   String TYPE_DAYTIME = "DAYTIME";
   String TYPE_ICMP = "ICMP";
   String TYPE_NTP = "NTP";
   String TYPE_TIME = "TIME";
   int VERSION_3 = 3;
   int VERSION_4 = 4;

   DatagramPacket getDatagramPacket();

   int getLeapIndicator();

   int getMode();

   String getModeName();

   TimeStamp getOriginateTimeStamp();

   int getPoll();

   int getPrecision();

   TimeStamp getReceiveTimeStamp();

   int getReferenceId();

   String getReferenceIdString();

   TimeStamp getReferenceTimeStamp();

   int getRootDelay();

   double getRootDelayInMillisDouble();

   int getRootDispersion();

   long getRootDispersionInMillis();

   double getRootDispersionInMillisDouble();

   int getStratum();

   TimeStamp getTransmitTimeStamp();

   String getType();

   int getVersion();

   void setDatagramPacket(DatagramPacket var1);

   void setLeapIndicator(int var1);

   void setMode(int var1);

   void setOriginateTimeStamp(TimeStamp var1);

   void setPoll(int var1);

   void setPrecision(int var1);

   void setReceiveTimeStamp(TimeStamp var1);

   void setReferenceId(int var1);

   void setReferenceTime(TimeStamp var1);

   void setRootDelay(int var1);

   void setRootDispersion(int var1);

   void setStratum(int var1);

   void setTransmitTime(TimeStamp var1);

   void setVersion(int var1);
}
