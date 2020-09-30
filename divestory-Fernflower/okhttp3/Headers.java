package okhttp3;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map.Entry;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.ReplaceWith;
import kotlin.TuplesKt;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.ArrayIteratorKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.jvm.internal.markers.KMappedMarker;
import kotlin.ranges.IntProgression;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import okhttp3.internal.Util;
import okhttp3.internal.http.DatesKt;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010(\n\u0002\b\u0002\n\u0002\u0010\"\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0010 \n\u0002\b\u0006\u0018\u0000 '2\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u00020\u0001:\u0002&'B\u0015\b\u0002\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\u0002\u0010\u0006J\u0006\u0010\u000b\u001a\u00020\fJ\u0013\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0096\u0002J\u0013\u0010\u0011\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0012\u001a\u00020\u0003H\u0086\u0002J\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u00142\u0006\u0010\u0012\u001a\u00020\u0003J\u0012\u0010\u0015\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u0012\u001a\u00020\u0003H\u0007J\b\u0010\u0017\u001a\u00020\tH\u0016J\u001b\u0010\u0018\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u00020\u0019H\u0096\u0002J\u000e\u0010\u0012\u001a\u00020\u00032\u0006\u0010\u001a\u001a\u00020\tJ\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00030\u001cJ\u0006\u0010\u001d\u001a\u00020\u001eJ\r\u0010\b\u001a\u00020\tH\u0007¢\u0006\u0002\b\u001fJ\u0018\u0010 \u001a\u0014\u0012\u0004\u0012\u00020\u0003\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\"0!J\b\u0010#\u001a\u00020\u0003H\u0016J\u000e\u0010$\u001a\u00020\u00032\u0006\u0010\u001a\u001a\u00020\tJ\u0014\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00030\"2\u0006\u0010\u0012\u001a\u00020\u0003R\u0016\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0007R\u0011\u0010\b\u001a\u00020\t8G¢\u0006\u0006\u001a\u0004\b\b\u0010\n¨\u0006("},
   d2 = {"Lokhttp3/Headers;", "", "Lkotlin/Pair;", "", "namesAndValues", "", "([Ljava/lang/String;)V", "[Ljava/lang/String;", "size", "", "()I", "byteCount", "", "equals", "", "other", "", "get", "name", "getDate", "Ljava/util/Date;", "getInstant", "Ljava/time/Instant;", "hashCode", "iterator", "", "index", "names", "", "newBuilder", "Lokhttp3/Headers$Builder;", "-deprecated_size", "toMultimap", "", "", "toString", "value", "values", "Builder", "Companion", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public final class Headers implements Iterable<Pair<? extends String, ? extends String>>, KMappedMarker {
   public static final Headers.Companion Companion = new Headers.Companion((DefaultConstructorMarker)null);
   private final String[] namesAndValues;

   private Headers(String[] var1) {
      this.namesAndValues = var1;
   }

   // $FF: synthetic method
   public Headers(String[] var1, DefaultConstructorMarker var2) {
      this(var1);
   }

   @JvmStatic
   public static final Headers of(Map<String, String> var0) {
      return Companion.of(var0);
   }

   @JvmStatic
   public static final Headers of(String... var0) {
      return Companion.of(var0);
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "size",
   imports = {}
)
   )
   public final int _deprecated_size/* $FF was: -deprecated_size*/() {
      return this.size();
   }

   public final long byteCount() {
      String[] var1 = this.namesAndValues;
      long var2 = (long)(var1.length * 2);
      int var4 = var1.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         var2 += (long)this.namesAndValues[var5].length();
      }

      return var2;
   }

   public boolean equals(Object var1) {
      boolean var2;
      if (var1 instanceof Headers && Arrays.equals(this.namesAndValues, ((Headers)var1).namesAndValues)) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public final String get(String var1) {
      Intrinsics.checkParameterIsNotNull(var1, "name");
      return Companion.get(this.namesAndValues, var1);
   }

   public final Date getDate(String var1) {
      Intrinsics.checkParameterIsNotNull(var1, "name");
      var1 = this.get(var1);
      Date var2;
      if (var1 != null) {
         var2 = DatesKt.toHttpDateOrNull(var1);
      } else {
         var2 = null;
      }

      return var2;
   }

   public final Instant getInstant(String var1) {
      Intrinsics.checkParameterIsNotNull(var1, "name");
      Date var2 = this.getDate(var1);
      Instant var3;
      if (var2 != null) {
         var3 = var2.toInstant();
      } else {
         var3 = null;
      }

      return var3;
   }

   public int hashCode() {
      return Arrays.hashCode(this.namesAndValues);
   }

   public Iterator<Pair<String, String>> iterator() {
      int var1 = this.size();
      Pair[] var2 = new Pair[var1];

      for(int var3 = 0; var3 < var1; ++var3) {
         var2[var3] = TuplesKt.to(this.name(var3), this.value(var3));
      }

      return ArrayIteratorKt.iterator(var2);
   }

   public final String name(int var1) {
      return this.namesAndValues[var1 * 2];
   }

   public final Set<String> names() {
      TreeSet var1 = new TreeSet(StringsKt.getCASE_INSENSITIVE_ORDER(StringCompanionObject.INSTANCE));
      int var2 = this.size();

      for(int var3 = 0; var3 < var2; ++var3) {
         var1.add(this.name(var3));
      }

      Set var4 = Collections.unmodifiableSet((Set)var1);
      Intrinsics.checkExpressionValueIsNotNull(var4, "Collections.unmodifiableSet(result)");
      return var4;
   }

   public final Headers.Builder newBuilder() {
      Headers.Builder var1 = new Headers.Builder();
      CollectionsKt.addAll((Collection)var1.getNamesAndValues$okhttp(), this.namesAndValues);
      return var1;
   }

   public final int size() {
      return this.namesAndValues.length / 2;
   }

   public final Map<String, List<String>> toMultimap() {
      TreeMap var1 = new TreeMap(StringsKt.getCASE_INSENSITIVE_ORDER(StringCompanionObject.INSTANCE));
      int var2 = this.size();

      for(int var3 = 0; var3 < var2; ++var3) {
         String var4 = this.name(var3);
         Locale var5 = Locale.US;
         Intrinsics.checkExpressionValueIsNotNull(var5, "Locale.US");
         if (var4 == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
         }

         String var6 = var4.toLowerCase(var5);
         Intrinsics.checkExpressionValueIsNotNull(var6, "(this as java.lang.String).toLowerCase(locale)");
         List var7 = (List)var1.get(var6);
         List var8 = var7;
         if (var7 == null) {
            var8 = (List)(new ArrayList(2));
            ((Map)var1).put(var6, var8);
         }

         var8.add(this.value(var3));
      }

      return (Map)var1;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      int var2 = this.size();

      for(int var3 = 0; var3 < var2; ++var3) {
         var1.append(this.name(var3));
         var1.append(": ");
         var1.append(this.value(var3));
         var1.append("\n");
      }

      String var4 = var1.toString();
      Intrinsics.checkExpressionValueIsNotNull(var4, "StringBuilder().apply(builderAction).toString()");
      return var4;
   }

   public final String value(int var1) {
      return this.namesAndValues[var1 * 2 + 1];
   }

   public final List<String> values(String var1) {
      Intrinsics.checkParameterIsNotNull(var1, "name");
      List var2 = (List)null;
      int var3 = this.size();

      List var5;
      for(int var4 = 0; var4 < var3; var2 = var5) {
         var5 = var2;
         if (StringsKt.equals(var1, this.name(var4), true)) {
            var5 = var2;
            if (var2 == null) {
               var5 = (List)(new ArrayList(2));
            }

            var5.add(this.value(var4));
         }

         ++var4;
      }

      List var6;
      if (var2 != null) {
         var6 = Collections.unmodifiableList(var2);
         Intrinsics.checkExpressionValueIsNotNull(var6, "Collections.unmodifiableList(result)");
      } else {
         var6 = CollectionsKt.emptyList();
      }

      return var6;
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\b\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0005J\u0018\u0010\b\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\fH\u0007J\u0016\u0010\b\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\rJ\u0016\u0010\b\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\u0005J\u000e\u0010\u000e\u001a\u00020\u00002\u0006\u0010\u000f\u001a\u00020\u0010J\u0015\u0010\u0011\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0005H\u0000¢\u0006\u0002\b\u0012J\u001d\u0010\u0011\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\u0005H\u0000¢\u0006\u0002\b\u0012J\u0016\u0010\u0013\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\u0005J\u0006\u0010\u0014\u001a\u00020\u0010J\u0013\u0010\u0015\u001a\u0004\u0018\u00010\u00052\u0006\u0010\n\u001a\u00020\u0005H\u0086\u0002J\u000e\u0010\u0016\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u0005J\u0019\u0010\u0017\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\fH\u0087\u0002J\u0019\u0010\u0017\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\rH\u0086\u0002J\u0019\u0010\u0017\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\u0005H\u0086\u0002R\u001a\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0018"},
      d2 = {"Lokhttp3/Headers$Builder;", "", "()V", "namesAndValues", "", "", "getNamesAndValues$okhttp", "()Ljava/util/List;", "add", "line", "name", "value", "Ljava/time/Instant;", "Ljava/util/Date;", "addAll", "headers", "Lokhttp3/Headers;", "addLenient", "addLenient$okhttp", "addUnsafeNonAscii", "build", "get", "removeAll", "set", "okhttp"},
      k = 1,
      mv = {1, 1, 16}
   )
   public static final class Builder {
      private final List<String> namesAndValues = (List)(new ArrayList(20));

      public final Headers.Builder add(String var1) {
         Intrinsics.checkParameterIsNotNull(var1, "line");
         Headers.Builder var2 = (Headers.Builder)this;
         int var3 = StringsKt.indexOf$default((CharSequence)var1, ':', 0, false, 6, (Object)null);
         boolean var4;
         if (var3 != -1) {
            var4 = true;
         } else {
            var4 = false;
         }

         if (var4) {
            String var5 = var1.substring(0, var3);
            Intrinsics.checkExpressionValueIsNotNull(var5, "(this as java.lang.Strin…ing(startIndex, endIndex)");
            if (var5 != null) {
               var5 = StringsKt.trim((CharSequence)var5).toString();
               var1 = var1.substring(var3 + 1);
               Intrinsics.checkExpressionValueIsNotNull(var1, "(this as java.lang.String).substring(startIndex)");
               var2.add(var5, var1);
               return var2;
            } else {
               throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
            }
         } else {
            StringBuilder var6 = new StringBuilder();
            var6.append("Unexpected header: ");
            var6.append(var1);
            throw (Throwable)(new IllegalArgumentException(var6.toString().toString()));
         }
      }

      public final Headers.Builder add(String var1, String var2) {
         Intrinsics.checkParameterIsNotNull(var1, "name");
         Intrinsics.checkParameterIsNotNull(var2, "value");
         Headers.Builder var3 = (Headers.Builder)this;
         Headers.Companion.checkName(var1);
         Headers.Companion.checkValue(var2, var1);
         var3.addLenient$okhttp(var1, var2);
         return var3;
      }

      public final Headers.Builder add(String var1, Instant var2) {
         Intrinsics.checkParameterIsNotNull(var1, "name");
         Intrinsics.checkParameterIsNotNull(var2, "value");
         Headers.Builder var3 = (Headers.Builder)this;
         var3.add(var1, new Date(var2.toEpochMilli()));
         return var3;
      }

      public final Headers.Builder add(String var1, Date var2) {
         Intrinsics.checkParameterIsNotNull(var1, "name");
         Intrinsics.checkParameterIsNotNull(var2, "value");
         Headers.Builder var3 = (Headers.Builder)this;
         var3.add(var1, DatesKt.toHttpDateString(var2));
         return var3;
      }

      public final Headers.Builder addAll(Headers var1) {
         Intrinsics.checkParameterIsNotNull(var1, "headers");
         Headers.Builder var2 = (Headers.Builder)this;
         int var3 = var1.size();

         for(int var4 = 0; var4 < var3; ++var4) {
            var2.addLenient$okhttp(var1.name(var4), var1.value(var4));
         }

         return var2;
      }

      public final Headers.Builder addLenient$okhttp(String var1) {
         Intrinsics.checkParameterIsNotNull(var1, "line");
         Headers.Builder var2 = (Headers.Builder)this;
         int var3 = StringsKt.indexOf$default((CharSequence)var1, ':', 1, false, 4, (Object)null);
         if (var3 != -1) {
            String var4 = var1.substring(0, var3);
            Intrinsics.checkExpressionValueIsNotNull(var4, "(this as java.lang.Strin…ing(startIndex, endIndex)");
            var1 = var1.substring(var3 + 1);
            Intrinsics.checkExpressionValueIsNotNull(var1, "(this as java.lang.String).substring(startIndex)");
            var2.addLenient$okhttp(var4, var1);
         } else if (var1.charAt(0) == ':') {
            var1 = var1.substring(1);
            Intrinsics.checkExpressionValueIsNotNull(var1, "(this as java.lang.String).substring(startIndex)");
            var2.addLenient$okhttp("", var1);
         } else {
            var2.addLenient$okhttp("", var1);
         }

         return var2;
      }

      public final Headers.Builder addLenient$okhttp(String var1, String var2) {
         Intrinsics.checkParameterIsNotNull(var1, "name");
         Intrinsics.checkParameterIsNotNull(var2, "value");
         Headers.Builder var3 = (Headers.Builder)this;
         var3.namesAndValues.add(var1);
         var3.namesAndValues.add(StringsKt.trim((CharSequence)var2).toString());
         return var3;
      }

      public final Headers.Builder addUnsafeNonAscii(String var1, String var2) {
         Intrinsics.checkParameterIsNotNull(var1, "name");
         Intrinsics.checkParameterIsNotNull(var2, "value");
         Headers.Builder var3 = (Headers.Builder)this;
         Headers.Companion.checkName(var1);
         var3.addLenient$okhttp(var1, var2);
         return var3;
      }

      public final Headers build() {
         Object[] var1 = ((Collection)this.namesAndValues).toArray(new String[0]);
         if (var1 != null) {
            return new Headers((String[])var1, (DefaultConstructorMarker)null);
         } else {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
         }
      }

      public final String get(String var1) {
         Intrinsics.checkParameterIsNotNull(var1, "name");
         IntProgression var2 = RangesKt.step(RangesKt.downTo(this.namesAndValues.size() - 2, 0), 2);
         int var3 = var2.getFirst();
         int var4 = var2.getLast();
         int var5 = var2.getStep();
         if (var5 >= 0) {
            if (var3 > var4) {
               return null;
            }
         } else if (var3 < var4) {
            return null;
         }

         while(!StringsKt.equals(var1, (String)this.namesAndValues.get(var3), true)) {
            if (var3 == var4) {
               return null;
            }

            var3 += var5;
         }

         return (String)this.namesAndValues.get(var3 + 1);
      }

      public final List<String> getNamesAndValues$okhttp() {
         return this.namesAndValues;
      }

      public final Headers.Builder removeAll(String var1) {
         Intrinsics.checkParameterIsNotNull(var1, "name");
         Headers.Builder var2 = (Headers.Builder)this;

         int var4;
         for(int var3 = 0; var3 < var2.namesAndValues.size(); var3 = var4 + 2) {
            var4 = var3;
            if (StringsKt.equals(var1, (String)var2.namesAndValues.get(var3), true)) {
               var2.namesAndValues.remove(var3);
               var2.namesAndValues.remove(var3);
               var4 = var3 - 2;
            }
         }

         return var2;
      }

      public final Headers.Builder set(String var1, String var2) {
         Intrinsics.checkParameterIsNotNull(var1, "name");
         Intrinsics.checkParameterIsNotNull(var2, "value");
         Headers.Builder var3 = (Headers.Builder)this;
         Headers.Companion.checkName(var1);
         Headers.Companion.checkValue(var2, var1);
         var3.removeAll(var1);
         var3.addLenient$okhttp(var1, var2);
         return var3;
      }

      public final Headers.Builder set(String var1, Instant var2) {
         Intrinsics.checkParameterIsNotNull(var1, "name");
         Intrinsics.checkParameterIsNotNull(var2, "value");
         return ((Headers.Builder)this).set(var1, new Date(var2.toEpochMilli()));
      }

      public final Headers.Builder set(String var1, Date var2) {
         Intrinsics.checkParameterIsNotNull(var1, "name");
         Intrinsics.checkParameterIsNotNull(var2, "value");
         Headers.Builder var3 = (Headers.Builder)this;
         var3.set(var1, DatesKt.toHttpDateString(var2));
         return var3;
      }
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010$\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0018\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J%\u0010\t\u001a\u0004\u0018\u00010\u00062\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00060\u000b2\u0006\u0010\u0005\u001a\u00020\u0006H\u0002¢\u0006\u0002\u0010\fJ#\u0010\r\u001a\u00020\u000e2\u0012\u0010\n\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00060\u000b\"\u00020\u0006H\u0007¢\u0006\u0004\b\u000f\u0010\u0010J#\u0010\u000f\u001a\u00020\u000e2\u0012\u0010\n\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00060\u000b\"\u00020\u0006H\u0007¢\u0006\u0004\b\u0011\u0010\u0010J!\u0010\u000f\u001a\u00020\u000e2\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0013H\u0007¢\u0006\u0002\b\u0011J\u001d\u0010\u0014\u001a\u00020\u000e*\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0013H\u0007¢\u0006\u0002\b\u000f¨\u0006\u0015"},
      d2 = {"Lokhttp3/Headers$Companion;", "", "()V", "checkName", "", "name", "", "checkValue", "value", "get", "namesAndValues", "", "([Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;", "headersOf", "Lokhttp3/Headers;", "of", "([Ljava/lang/String;)Lokhttp3/Headers;", "-deprecated_of", "headers", "", "toHeaders", "okhttp"},
      k = 1,
      mv = {1, 1, 16}
   )
   public static final class Companion {
      private Companion() {
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker var1) {
         this();
      }

      private final void checkName(String var1) {
         boolean var2;
         if (((CharSequence)var1).length() > 0) {
            var2 = true;
         } else {
            var2 = false;
         }

         if (!var2) {
            throw (Throwable)(new IllegalArgumentException("name is empty".toString()));
         } else {
            int var3 = var1.length();

            for(int var6 = 0; var6 < var3; ++var6) {
               char var4 = var1.charAt(var6);
               boolean var5;
               if ('!' <= var4 && '~' >= var4) {
                  var5 = true;
               } else {
                  var5 = false;
               }

               if (!var5) {
                  throw (Throwable)(new IllegalArgumentException(Util.format("Unexpected char %#04x at %d in header name: %s", Integer.valueOf(var4), var6, var1).toString()));
               }
            }

         }
      }

      private final void checkValue(String var1, String var2) {
         int var3 = var1.length();

         for(int var4 = 0; var4 < var3; ++var4) {
            char var5 = var1.charAt(var4);
            boolean var6;
            if (var5 == '\t' || ' ' <= var5 && '~' >= var5) {
               var6 = true;
            } else {
               var6 = false;
            }

            if (!var6) {
               throw (Throwable)(new IllegalArgumentException(Util.format("Unexpected char %#04x at %d in %s value: %s", Integer.valueOf(var5), var4, var2, var1).toString()));
            }
         }

      }

      private final String get(String[] var1, String var2) {
         IntProgression var3 = RangesKt.step(RangesKt.downTo(var1.length - 2, 0), 2);
         int var4 = var3.getFirst();
         int var5 = var3.getLast();
         int var6 = var3.getStep();
         if (var6 >= 0) {
            if (var4 > var5) {
               return null;
            }
         } else if (var4 < var5) {
            return null;
         }

         while(!StringsKt.equals(var2, var1[var4], true)) {
            if (var4 == var5) {
               return null;
            }

            var4 += var6;
         }

         return var1[var4 + 1];
      }

      @Deprecated(
         level = DeprecationLevel.ERROR,
         message = "function moved to extension",
         replaceWith = @ReplaceWith(
   expression = "headers.toHeaders()",
   imports = {}
)
      )
      public final Headers _deprecated_of/* $FF was: -deprecated_of*/(Map<String, String> var1) {
         Intrinsics.checkParameterIsNotNull(var1, "headers");
         return ((Headers.Companion)this).of(var1);
      }

      @Deprecated(
         level = DeprecationLevel.ERROR,
         message = "function name changed",
         replaceWith = @ReplaceWith(
   expression = "headersOf(*namesAndValues)",
   imports = {}
)
      )
      public final Headers _deprecated_of/* $FF was: -deprecated_of*/(String... var1) {
         Intrinsics.checkParameterIsNotNull(var1, "namesAndValues");
         return ((Headers.Companion)this).of((String[])Arrays.copyOf(var1, var1.length));
      }

      @JvmStatic
      public final Headers of(Map<String, String> var1) {
         Intrinsics.checkParameterIsNotNull(var1, "$this$toHeaders");
         String[] var2 = new String[var1.size() * 2];
         Iterator var7 = var1.entrySet().iterator();

         for(int var3 = 0; var7.hasNext(); var3 += 2) {
            Entry var4 = (Entry)var7.next();
            String var5 = (String)var4.getKey();
            String var8 = (String)var4.getValue();
            if (var5 == null) {
               throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
            }

            var5 = StringsKt.trim((CharSequence)var5).toString();
            if (var8 == null) {
               throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
            }

            String var6 = StringsKt.trim((CharSequence)var8).toString();
            Headers.Companion var9 = (Headers.Companion)this;
            var9.checkName(var5);
            var9.checkValue(var6, var5);
            var2[var3] = var5;
            var2[var3 + 1] = var6;
         }

         return new Headers(var2, (DefaultConstructorMarker)null);
      }

      @JvmStatic
      public final Headers of(String... var1) {
         Intrinsics.checkParameterIsNotNull(var1, "namesAndValues");
         boolean var2;
         if (var1.length % 2 == 0) {
            var2 = true;
         } else {
            var2 = false;
         }

         if (!var2) {
            throw (Throwable)(new IllegalArgumentException("Expected alternating header names and values".toString()));
         } else {
            Object var8 = var1.clone();
            if (var8 == null) {
               throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<kotlin.String>");
            } else {
               var1 = (String[])var8;
               int var3 = var1.length;

               int var9;
               for(var9 = 0; var9 < var3; ++var9) {
                  boolean var4;
                  if (var1[var9] != null) {
                     var4 = true;
                  } else {
                     var4 = false;
                  }

                  if (!var4) {
                     throw (Throwable)(new IllegalArgumentException("Headers cannot be null".toString()));
                  }

                  String var5 = var1[var9];
                  if (var5 == null) {
                     throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
                  }

                  var1[var9] = StringsKt.trim((CharSequence)var5).toString();
               }

               IntProgression var11 = RangesKt.step((IntProgression)RangesKt.until(0, var1.length), 2);
               var9 = var11.getFirst();
               var3 = var11.getLast();
               int var10 = var11.getStep();
               if (var10 >= 0) {
                  if (var9 > var3) {
                     return new Headers(var1, (DefaultConstructorMarker)null);
                  }
               } else if (var9 < var3) {
                  return new Headers(var1, (DefaultConstructorMarker)null);
               }

               while(true) {
                  String var6 = var1[var9];
                  String var7 = var1[var9 + 1];
                  Headers.Companion var12 = (Headers.Companion)this;
                  var12.checkName(var6);
                  var12.checkValue(var7, var6);
                  if (var9 == var3) {
                     return new Headers(var1, (DefaultConstructorMarker)null);
                  }

                  var9 += var10;
               }
            }
         }
      }
   }
}
