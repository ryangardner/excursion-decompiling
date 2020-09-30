package com.google.api.client.testing.http;

import com.google.api.client.util.Clock;
import java.util.concurrent.atomic.AtomicLong;

public class FixedClock implements Clock {
   private AtomicLong currentTime;

   public FixedClock() {
      this(0L);
   }

   public FixedClock(long var1) {
      this.currentTime = new AtomicLong(var1);
   }

   public long currentTimeMillis() {
      return this.currentTime.get();
   }

   public FixedClock setTime(long var1) {
      this.currentTime.set(var1);
      return this;
   }
}
