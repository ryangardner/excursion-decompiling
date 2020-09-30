package io.opencensus.trace.export;

import java.util.List;

final class AutoValue_SpanData_TimedEvents<T> extends SpanData.TimedEvents<T> {
   private final int droppedEventsCount;
   private final List<SpanData.TimedEvent<T>> events;

   AutoValue_SpanData_TimedEvents(List<SpanData.TimedEvent<T>> var1, int var2) {
      if (var1 != null) {
         this.events = var1;
         this.droppedEventsCount = var2;
      } else {
         throw new NullPointerException("Null events");
      }
   }

   public boolean equals(Object var1) {
      boolean var2 = true;
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof SpanData.TimedEvents)) {
         return false;
      } else {
         SpanData.TimedEvents var3 = (SpanData.TimedEvents)var1;
         if (!this.events.equals(var3.getEvents()) || this.droppedEventsCount != var3.getDroppedEventsCount()) {
            var2 = false;
         }

         return var2;
      }
   }

   public int getDroppedEventsCount() {
      return this.droppedEventsCount;
   }

   public List<SpanData.TimedEvent<T>> getEvents() {
      return this.events;
   }

   public int hashCode() {
      return (this.events.hashCode() ^ 1000003) * 1000003 ^ this.droppedEventsCount;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("TimedEvents{events=");
      var1.append(this.events);
      var1.append(", droppedEventsCount=");
      var1.append(this.droppedEventsCount);
      var1.append("}");
      return var1.toString();
   }
}
