package com.syntak.library;

import android.content.Context;
import java.util.UUID;

public class DeviceUuidFactory {
   protected static final String PREFS_DEVICE_ID = "device_id";
   protected static final String PREFS_FILE = "device_id.xml";
   protected static UUID uuid;

   public DeviceUuidFactory(Context param1) {
      // $FF: Couldn't be decompiled
   }

   public UUID getDeviceUuid() {
      return uuid;
   }
}
