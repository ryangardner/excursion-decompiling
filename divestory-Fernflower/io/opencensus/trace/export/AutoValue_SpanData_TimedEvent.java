package io.opencensus.trace.export;

import io.opencensus.common.Timestamp;

final class AutoValue_SpanData_TimedEvent<T> extends SpanData.TimedEvent<T> {
   private final T event;
   private final Timestamp timestamp;

   AutoValue_SpanData_TimedEvent(Timestamp var1, T var2) {
      if (var1 != null) {
         this.timestamp = var1;
         if (var2 != null) {
            this.event = var2;
         } else {
            throw new NullPointerException("Null event");
         }
      } else {
         throw new NullPointerException("Null timestamp");
      }
   }

   public boolean equals(Object var1) {
      boolean var2 = true;
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof SpanData.TimedEvent)) {
         return false;
      } else {
         SpanData.TimedEvent var3 = (SpanData.TimedEvent)var1;
         if (!this.timestamp.equals(var3.getTimestamp()) || !this.event.equals(var3.getEvent())) {
            var2 = false;
         }

         return var2;
      }
   }

   public T getEvent() {
      return this.event;
   }

   public Timestamp getTimestamp() {
      return this.timestamp;
   }

   public int hashCode() {
      return (this.timestamp.hashCode() ^ 1000003) * 1000003 ^ this.event.hashCode();
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("TimedEvent{timestamp=");
      var1.append(this.timestamp);
      var1.append(", event=");
      var1.append(this.event);
      var1.append("}");
      return var1.toString();
   }
}
