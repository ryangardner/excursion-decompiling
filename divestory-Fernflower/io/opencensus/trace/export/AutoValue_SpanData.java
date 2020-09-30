package io.opencensus.trace.export;

import io.opencensus.common.Timestamp;
import io.opencensus.trace.Annotation;
import io.opencensus.trace.MessageEvent;
import io.opencensus.trace.Span;
import io.opencensus.trace.SpanContext;
import io.opencensus.trace.SpanId;
import io.opencensus.trace.Status;
import javax.annotation.Nullable;

final class AutoValue_SpanData extends SpanData {
   private final SpanData.TimedEvents<Annotation> annotations;
   private final SpanData.Attributes attributes;
   private final Integer childSpanCount;
   private final SpanContext context;
   private final Timestamp endTimestamp;
   private final Boolean hasRemoteParent;
   private final Span.Kind kind;
   private final SpanData.Links links;
   private final SpanData.TimedEvents<MessageEvent> messageEvents;
   private final String name;
   private final SpanId parentSpanId;
   private final Timestamp startTimestamp;
   private final Status status;

   AutoValue_SpanData(SpanContext var1, @Nullable SpanId var2, @Nullable Boolean var3, String var4, @Nullable Span.Kind var5, Timestamp var6, SpanData.Attributes var7, SpanData.TimedEvents<Annotation> var8, SpanData.TimedEvents<MessageEvent> var9, SpanData.Links var10, @Nullable Integer var11, @Nullable Status var12, @Nullable Timestamp var13) {
      if (var1 != null) {
         this.context = var1;
         this.parentSpanId = var2;
         this.hasRemoteParent = var3;
         if (var4 != null) {
            this.name = var4;
            this.kind = var5;
            if (var6 != null) {
               this.startTimestamp = var6;
               if (var7 != null) {
                  this.attributes = var7;
                  if (var8 != null) {
                     this.annotations = var8;
                     if (var9 != null) {
                        this.messageEvents = var9;
                        if (var10 != null) {
                           this.links = var10;
                           this.childSpanCount = var11;
                           this.status = var12;
                           this.endTimestamp = var13;
                        } else {
                           throw new NullPointerException("Null links");
                        }
                     } else {
                        throw new NullPointerException("Null messageEvents");
                     }
                  } else {
                     throw new NullPointerException("Null annotations");
                  }
               } else {
                  throw new NullPointerException("Null attributes");
               }
            } else {
               throw new NullPointerException("Null startTimestamp");
            }
         } else {
            throw new NullPointerException("Null name");
         }
      } else {
         throw new NullPointerException("Null context");
      }
   }

   public boolean equals(Object var1) {
      boolean var2 = true;
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof SpanData)) {
         return false;
      } else {
         SpanData var4 = (SpanData)var1;
         if (this.context.equals(var4.getContext())) {
            label85: {
               SpanId var3 = this.parentSpanId;
               if (var3 == null) {
                  if (var4.getParentSpanId() != null) {
                     break label85;
                  }
               } else if (!var3.equals(var4.getParentSpanId())) {
                  break label85;
               }

               Boolean var5 = this.hasRemoteParent;
               if (var5 == null) {
                  if (var4.getHasRemoteParent() != null) {
                     break label85;
                  }
               } else if (!var5.equals(var4.getHasRemoteParent())) {
                  break label85;
               }

               if (this.name.equals(var4.getName())) {
                  label66: {
                     Span.Kind var6 = this.kind;
                     if (var6 == null) {
                        if (var4.getKind() != null) {
                           break label66;
                        }
                     } else if (!var6.equals(var4.getKind())) {
                        break label66;
                     }

                     if (this.startTimestamp.equals(var4.getStartTimestamp()) && this.attributes.equals(var4.getAttributes()) && this.annotations.equals(var4.getAnnotations()) && this.messageEvents.equals(var4.getMessageEvents()) && this.links.equals(var4.getLinks())) {
                        label86: {
                           Integer var7 = this.childSpanCount;
                           if (var7 == null) {
                              if (var4.getChildSpanCount() != null) {
                                 break label86;
                              }
                           } else if (!var7.equals(var4.getChildSpanCount())) {
                              break label86;
                           }

                           Status var8 = this.status;
                           if (var8 == null) {
                              if (var4.getStatus() != null) {
                                 break label86;
                              }
                           } else if (!var8.equals(var4.getStatus())) {
                              break label86;
                           }

                           Timestamp var9 = this.endTimestamp;
                           if (var9 == null) {
                              if (var4.getEndTimestamp() == null) {
                                 return var2;
                              }
                           } else if (var9.equals(var4.getEndTimestamp())) {
                              return var2;
                           }
                        }
                     }
                  }
               }
            }
         }

         var2 = false;
         return var2;
      }
   }

   public SpanData.TimedEvents<Annotation> getAnnotations() {
      return this.annotations;
   }

   public SpanData.Attributes getAttributes() {
      return this.attributes;
   }

   @Nullable
   public Integer getChildSpanCount() {
      return this.childSpanCount;
   }

   public SpanContext getContext() {
      return this.context;
   }

   @Nullable
   public Timestamp getEndTimestamp() {
      return this.endTimestamp;
   }

   @Nullable
   public Boolean getHasRemoteParent() {
      return this.hasRemoteParent;
   }

   @Nullable
   public Span.Kind getKind() {
      return this.kind;
   }

   public SpanData.Links getLinks() {
      return this.links;
   }

   public SpanData.TimedEvents<MessageEvent> getMessageEvents() {
      return this.messageEvents;
   }

   public String getName() {
      return this.name;
   }

   @Nullable
   public SpanId getParentSpanId() {
      return this.parentSpanId;
   }

   public Timestamp getStartTimestamp() {
      return this.startTimestamp;
   }

   @Nullable
   public Status getStatus() {
      return this.status;
   }

   public int hashCode() {
      int var1 = this.context.hashCode();
      SpanId var2 = this.parentSpanId;
      int var3 = 0;
      int var4;
      if (var2 == null) {
         var4 = 0;
      } else {
         var4 = var2.hashCode();
      }

      Boolean var15 = this.hasRemoteParent;
      int var5;
      if (var15 == null) {
         var5 = 0;
      } else {
         var5 = var15.hashCode();
      }

      int var6 = this.name.hashCode();
      Span.Kind var16 = this.kind;
      int var7;
      if (var16 == null) {
         var7 = 0;
      } else {
         var7 = var16.hashCode();
      }

      int var8 = this.startTimestamp.hashCode();
      int var9 = this.attributes.hashCode();
      int var10 = this.annotations.hashCode();
      int var11 = this.messageEvents.hashCode();
      int var12 = this.links.hashCode();
      Integer var17 = this.childSpanCount;
      int var13;
      if (var17 == null) {
         var13 = 0;
      } else {
         var13 = var17.hashCode();
      }

      Status var18 = this.status;
      int var14;
      if (var18 == null) {
         var14 = 0;
      } else {
         var14 = var18.hashCode();
      }

      Timestamp var19 = this.endTimestamp;
      if (var19 != null) {
         var3 = var19.hashCode();
      }

      return ((((((((((((var1 ^ 1000003) * 1000003 ^ var4) * 1000003 ^ var5) * 1000003 ^ var6) * 1000003 ^ var7) * 1000003 ^ var8) * 1000003 ^ var9) * 1000003 ^ var10) * 1000003 ^ var11) * 1000003 ^ var12) * 1000003 ^ var13) * 1000003 ^ var14) * 1000003 ^ var3;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("SpanData{context=");
      var1.append(this.context);
      var1.append(", parentSpanId=");
      var1.append(this.parentSpanId);
      var1.append(", hasRemoteParent=");
      var1.append(this.hasRemoteParent);
      var1.append(", name=");
      var1.append(this.name);
      var1.append(", kind=");
      var1.append(this.kind);
      var1.append(", startTimestamp=");
      var1.append(this.startTimestamp);
      var1.append(", attributes=");
      var1.append(this.attributes);
      var1.append(", annotations=");
      var1.append(this.annotations);
      var1.append(", messageEvents=");
      var1.append(this.messageEvents);
      var1.append(", links=");
      var1.append(this.links);
      var1.append(", childSpanCount=");
      var1.append(this.childSpanCount);
      var1.append(", status=");
      var1.append(this.status);
      var1.append(", endTimestamp=");
      var1.append(this.endTimestamp);
      var1.append("}");
      return var1.toString();
   }
}
