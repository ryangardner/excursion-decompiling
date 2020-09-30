package com.google.common.eventbus;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

public class DeadEvent {
   private final Object event;
   private final Object source;

   public DeadEvent(Object var1, Object var2) {
      this.source = Preconditions.checkNotNull(var1);
      this.event = Preconditions.checkNotNull(var2);
   }

   public Object getEvent() {
      return this.event;
   }

   public Object getSource() {
      return this.source;
   }

   public String toString() {
      return MoreObjects.toStringHelper((Object)this).add("source", this.source).add("event", this.event).toString();
   }
}
