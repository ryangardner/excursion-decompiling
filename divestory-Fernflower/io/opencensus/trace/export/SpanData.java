package io.opencensus.trace.export;

import io.opencensus.common.Timestamp;
import io.opencensus.internal.Utils;
import io.opencensus.trace.Annotation;
import io.opencensus.trace.AttributeValue;
import io.opencensus.trace.BaseMessageEvent;
import io.opencensus.trace.Link;
import io.opencensus.trace.MessageEvent;
import io.opencensus.trace.NetworkEvent;
import io.opencensus.trace.Span;
import io.opencensus.trace.SpanContext;
import io.opencensus.trace.SpanId;
import io.opencensus.trace.Status;
import io.opencensus.trace.internal.BaseMessageEventUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

public abstract class SpanData {
   SpanData() {
   }

   @Deprecated
   public static SpanData create(SpanContext var0, @Nullable SpanId var1, @Nullable Boolean var2, String var3, Timestamp var4, SpanData.Attributes var5, SpanData.TimedEvents<Annotation> var6, SpanData.TimedEvents<? extends BaseMessageEvent> var7, SpanData.Links var8, @Nullable Integer var9, @Nullable Status var10, @Nullable Timestamp var11) {
      return create(var0, var1, var2, var3, (Span.Kind)null, var4, var5, var6, var7, var8, var9, var10, var11);
   }

   public static SpanData create(SpanContext var0, @Nullable SpanId var1, @Nullable Boolean var2, String var3, @Nullable Span.Kind var4, Timestamp var5, SpanData.Attributes var6, SpanData.TimedEvents<Annotation> var7, SpanData.TimedEvents<? extends BaseMessageEvent> var8, SpanData.Links var9, @Nullable Integer var10, @Nullable Status var11, @Nullable Timestamp var12) {
      Utils.checkNotNull(var8, "messageOrNetworkEvents");
      ArrayList var13 = new ArrayList();
      Iterator var14 = var8.getEvents().iterator();

      while(var14.hasNext()) {
         SpanData.TimedEvent var15 = (SpanData.TimedEvent)var14.next();
         BaseMessageEvent var16 = (BaseMessageEvent)var15.getEvent();
         if (var16 instanceof MessageEvent) {
            var13.add(var15);
         } else {
            var13.add(SpanData.TimedEvent.create(var15.getTimestamp(), BaseMessageEventUtils.asMessageEvent(var16)));
         }
      }

      return new AutoValue_SpanData(var0, var1, var2, var3, var4, var5, var6, var7, SpanData.TimedEvents.create(var13, var8.getDroppedEventsCount()), var9, var10, var11, var12);
   }

   public abstract SpanData.TimedEvents<Annotation> getAnnotations();

   public abstract SpanData.Attributes getAttributes();

   @Nullable
   public abstract Integer getChildSpanCount();

   public abstract SpanContext getContext();

   @Nullable
   public abstract Timestamp getEndTimestamp();

   @Nullable
   public abstract Boolean getHasRemoteParent();

   @Nullable
   public abstract Span.Kind getKind();

   public abstract SpanData.Links getLinks();

   public abstract SpanData.TimedEvents<MessageEvent> getMessageEvents();

   public abstract String getName();

   @Deprecated
   public SpanData.TimedEvents<NetworkEvent> getNetworkEvents() {
      SpanData.TimedEvents var1 = this.getMessageEvents();
      ArrayList var2 = new ArrayList();
      Iterator var3 = var1.getEvents().iterator();

      while(var3.hasNext()) {
         SpanData.TimedEvent var4 = (SpanData.TimedEvent)var3.next();
         var2.add(SpanData.TimedEvent.create(var4.getTimestamp(), BaseMessageEventUtils.asNetworkEvent((BaseMessageEvent)var4.getEvent())));
      }

      return SpanData.TimedEvents.create(var2, var1.getDroppedEventsCount());
   }

   @Nullable
   public abstract SpanId getParentSpanId();

   public abstract Timestamp getStartTimestamp();

   @Nullable
   public abstract Status getStatus();

   public abstract static class Attributes {
      Attributes() {
      }

      public static SpanData.Attributes create(Map<String, AttributeValue> var0, int var1) {
         return new AutoValue_SpanData_Attributes(Collections.unmodifiableMap(new HashMap((Map)Utils.checkNotNull(var0, "attributeMap"))), var1);
      }

      public abstract Map<String, AttributeValue> getAttributeMap();

      public abstract int getDroppedAttributesCount();
   }

   public abstract static class Links {
      Links() {
      }

      public static SpanData.Links create(List<Link> var0, int var1) {
         return new AutoValue_SpanData_Links(Collections.unmodifiableList(new ArrayList((Collection)Utils.checkNotNull(var0, "links"))), var1);
      }

      public abstract int getDroppedLinksCount();

      public abstract List<Link> getLinks();
   }

   public abstract static class TimedEvent<T> {
      TimedEvent() {
      }

      public static <T> SpanData.TimedEvent<T> create(Timestamp var0, T var1) {
         return new AutoValue_SpanData_TimedEvent(var0, var1);
      }

      public abstract T getEvent();

      public abstract Timestamp getTimestamp();
   }

   public abstract static class TimedEvents<T> {
      TimedEvents() {
      }

      public static <T> SpanData.TimedEvents<T> create(List<SpanData.TimedEvent<T>> var0, int var1) {
         return new AutoValue_SpanData_TimedEvents(Collections.unmodifiableList(new ArrayList((Collection)Utils.checkNotNull(var0, "events"))), var1);
      }

      public abstract int getDroppedEventsCount();

      public abstract List<SpanData.TimedEvent<T>> getEvents();
   }
}
