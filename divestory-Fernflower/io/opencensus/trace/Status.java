package io.opencensus.trace;

import io.opencensus.internal.Utils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;
import javax.annotation.Nullable;

public final class Status {
   public static final Status ABORTED;
   public static final Status ALREADY_EXISTS;
   public static final Status CANCELLED;
   public static final Status DATA_LOSS;
   public static final Status DEADLINE_EXCEEDED;
   public static final Status FAILED_PRECONDITION;
   public static final Status INTERNAL;
   public static final Status INVALID_ARGUMENT;
   public static final Status NOT_FOUND;
   public static final Status OK;
   public static final Status OUT_OF_RANGE;
   public static final Status PERMISSION_DENIED;
   public static final Status RESOURCE_EXHAUSTED;
   private static final List<Status> STATUS_LIST = buildStatusList();
   public static final Status UNAUTHENTICATED;
   public static final Status UNAVAILABLE;
   public static final Status UNIMPLEMENTED;
   public static final Status UNKNOWN;
   private final Status.CanonicalCode canonicalCode;
   @Nullable
   private final String description;

   static {
      OK = Status.CanonicalCode.OK.toStatus();
      CANCELLED = Status.CanonicalCode.CANCELLED.toStatus();
      UNKNOWN = Status.CanonicalCode.UNKNOWN.toStatus();
      INVALID_ARGUMENT = Status.CanonicalCode.INVALID_ARGUMENT.toStatus();
      DEADLINE_EXCEEDED = Status.CanonicalCode.DEADLINE_EXCEEDED.toStatus();
      NOT_FOUND = Status.CanonicalCode.NOT_FOUND.toStatus();
      ALREADY_EXISTS = Status.CanonicalCode.ALREADY_EXISTS.toStatus();
      PERMISSION_DENIED = Status.CanonicalCode.PERMISSION_DENIED.toStatus();
      UNAUTHENTICATED = Status.CanonicalCode.UNAUTHENTICATED.toStatus();
      RESOURCE_EXHAUSTED = Status.CanonicalCode.RESOURCE_EXHAUSTED.toStatus();
      FAILED_PRECONDITION = Status.CanonicalCode.FAILED_PRECONDITION.toStatus();
      ABORTED = Status.CanonicalCode.ABORTED.toStatus();
      OUT_OF_RANGE = Status.CanonicalCode.OUT_OF_RANGE.toStatus();
      UNIMPLEMENTED = Status.CanonicalCode.UNIMPLEMENTED.toStatus();
      INTERNAL = Status.CanonicalCode.INTERNAL.toStatus();
      UNAVAILABLE = Status.CanonicalCode.UNAVAILABLE.toStatus();
      DATA_LOSS = Status.CanonicalCode.DATA_LOSS.toStatus();
   }

   private Status(Status.CanonicalCode var1, @Nullable String var2) {
      this.canonicalCode = (Status.CanonicalCode)Utils.checkNotNull(var1, "canonicalCode");
      this.description = var2;
   }

   private static List<Status> buildStatusList() {
      TreeMap var0 = new TreeMap();
      Status.CanonicalCode[] var1 = Status.CanonicalCode.values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Status.CanonicalCode var4 = var1[var3];
         Status var5 = (Status)var0.put(var4.value(), new Status(var4, (String)null));
         if (var5 != null) {
            StringBuilder var6 = new StringBuilder();
            var6.append("Code value duplication between ");
            var6.append(var5.getCanonicalCode().name());
            var6.append(" & ");
            var6.append(var4.name());
            throw new IllegalStateException(var6.toString());
         }
      }

      return Collections.unmodifiableList(new ArrayList(var0.values()));
   }

   public boolean equals(@Nullable Object var1) {
      boolean var2 = true;
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof Status)) {
         return false;
      } else {
         Status var3 = (Status)var1;
         if (this.canonicalCode != var3.canonicalCode || !Utils.equalsObjects(this.description, var3.description)) {
            var2 = false;
         }

         return var2;
      }
   }

   public Status.CanonicalCode getCanonicalCode() {
      return this.canonicalCode;
   }

   @Nullable
   public String getDescription() {
      return this.description;
   }

   public int hashCode() {
      return Arrays.hashCode(new Object[]{this.canonicalCode, this.description});
   }

   public boolean isOk() {
      boolean var1;
      if (Status.CanonicalCode.OK == this.canonicalCode) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("Status{canonicalCode=");
      var1.append(this.canonicalCode);
      var1.append(", description=");
      var1.append(this.description);
      var1.append("}");
      return var1.toString();
   }

   public Status withDescription(@Nullable String var1) {
      return Utils.equalsObjects(this.description, var1) ? this : new Status(this.canonicalCode, var1);
   }

   public static enum CanonicalCode {
      ABORTED(10),
      ALREADY_EXISTS(6),
      CANCELLED(1),
      DATA_LOSS(15),
      DEADLINE_EXCEEDED(4),
      FAILED_PRECONDITION(9),
      INTERNAL(13),
      INVALID_ARGUMENT(3),
      NOT_FOUND(5),
      OK(0),
      OUT_OF_RANGE(11),
      PERMISSION_DENIED(7),
      RESOURCE_EXHAUSTED(8),
      UNAUTHENTICATED,
      UNAVAILABLE(14),
      UNIMPLEMENTED(12),
      UNKNOWN(2);

      private final int value;

      static {
         Status.CanonicalCode var0 = new Status.CanonicalCode("UNAUTHENTICATED", 16, 16);
         UNAUTHENTICATED = var0;
      }

      private CanonicalCode(int var3) {
         this.value = var3;
      }

      public Status toStatus() {
         return (Status)Status.STATUS_LIST.get(this.value);
      }

      public int value() {
         return this.value;
      }
   }
}
