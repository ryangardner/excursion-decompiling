package okhttp3;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.internal.Util;
import okio.Buffer;
import okio.BufferedSink;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u0000 \u001c2\u00020\u0001:\u0002\u001b\u001cB#\b\u0000\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0006J\b\u0010\n\u001a\u00020\u000bH\u0016J\b\u0010\f\u001a\u00020\rH\u0016J\u000e\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\bJ\u000e\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\bJ\u000e\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\bJ\r\u0010\u0007\u001a\u00020\bH\u0007¢\u0006\u0002\b\u0012J\u000e\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\bJ\u001a\u0010\u0014\u001a\u00020\u000b2\b\u0010\u0015\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0015\u001a\u00020\u0016H\u0016R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0007\u001a\u00020\b8G¢\u0006\u0006\u001a\u0004\b\u0007\u0010\t¨\u0006\u001d"},
   d2 = {"Lokhttp3/FormBody;", "Lokhttp3/RequestBody;", "encodedNames", "", "", "encodedValues", "(Ljava/util/List;Ljava/util/List;)V", "size", "", "()I", "contentLength", "", "contentType", "Lokhttp3/MediaType;", "encodedName", "index", "encodedValue", "name", "-deprecated_size", "value", "writeOrCountBytes", "sink", "Lokio/BufferedSink;", "countBytes", "", "writeTo", "", "Builder", "Companion", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public final class FormBody extends RequestBody {
   private static final MediaType CONTENT_TYPE;
   public static final FormBody.Companion Companion = new FormBody.Companion((DefaultConstructorMarker)null);
   private final List<String> encodedNames;
   private final List<String> encodedValues;

   static {
      CONTENT_TYPE = MediaType.Companion.get("application/x-www-form-urlencoded");
   }

   public FormBody(List<String> var1, List<String> var2) {
      Intrinsics.checkParameterIsNotNull(var1, "encodedNames");
      Intrinsics.checkParameterIsNotNull(var2, "encodedValues");
      super();
      this.encodedNames = Util.toImmutableList(var1);
      this.encodedValues = Util.toImmutableList(var2);
   }

   private final long writeOrCountBytes(BufferedSink var1, boolean var2) {
      Buffer var7;
      if (var2) {
         var7 = new Buffer();
      } else {
         if (var1 == null) {
            Intrinsics.throwNpe();
         }

         var7 = var1.getBuffer();
      }

      int var3 = 0;

      for(int var4 = this.encodedNames.size(); var3 < var4; ++var3) {
         if (var3 > 0) {
            var7.writeByte(38);
         }

         var7.writeUtf8((String)this.encodedNames.get(var3));
         var7.writeByte(61);
         var7.writeUtf8((String)this.encodedValues.get(var3));
      }

      long var5;
      if (var2) {
         var5 = var7.size();
         var7.clear();
      } else {
         var5 = 0L;
      }

      return var5;
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

   public long contentLength() {
      return this.writeOrCountBytes((BufferedSink)null, true);
   }

   public MediaType contentType() {
      return CONTENT_TYPE;
   }

   public final String encodedName(int var1) {
      return (String)this.encodedNames.get(var1);
   }

   public final String encodedValue(int var1) {
      return (String)this.encodedValues.get(var1);
   }

   public final String name(int var1) {
      return HttpUrl.Companion.percentDecode$okhttp$default(HttpUrl.Companion, this.encodedName(var1), 0, 0, true, 3, (Object)null);
   }

   public final int size() {
      return this.encodedNames.size();
   }

   public final String value(int var1) {
      return HttpUrl.Companion.percentDecode$okhttp$default(HttpUrl.Companion, this.encodedValue(var1), 0, 0, true, 3, (Object)null);
   }

   public void writeTo(BufferedSink var1) throws IOException {
      Intrinsics.checkParameterIsNotNull(var1, "sink");
      this.writeOrCountBytes(var1, false);
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0013\b\u0007\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J\u0016\u0010\t\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\u0007J\u0016\u0010\f\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\u0007J\u0006\u0010\r\u001a\u00020\u000eR\u0010\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"},
      d2 = {"Lokhttp3/FormBody$Builder;", "", "charset", "Ljava/nio/charset/Charset;", "(Ljava/nio/charset/Charset;)V", "names", "", "", "values", "add", "name", "value", "addEncoded", "build", "Lokhttp3/FormBody;", "okhttp"},
      k = 1,
      mv = {1, 1, 16}
   )
   public static final class Builder {
      private final Charset charset;
      private final List<String> names;
      private final List<String> values;

      public Builder() {
         this((Charset)null, 1, (DefaultConstructorMarker)null);
      }

      public Builder(Charset var1) {
         this.charset = var1;
         this.names = (List)(new ArrayList());
         this.values = (List)(new ArrayList());
      }

      // $FF: synthetic method
      public Builder(Charset var1, int var2, DefaultConstructorMarker var3) {
         if ((var2 & 1) != 0) {
            var1 = (Charset)null;
         }

         this(var1);
      }

      public final FormBody.Builder add(String var1, String var2) {
         Intrinsics.checkParameterIsNotNull(var1, "name");
         Intrinsics.checkParameterIsNotNull(var2, "value");
         FormBody.Builder var3 = (FormBody.Builder)this;
         ((Collection)var3.names).add(HttpUrl.Companion.canonicalize$okhttp$default(HttpUrl.Companion, var1, 0, 0, " \"':;<=>@[]^`{}|/\\?#&!$(),~", false, false, true, false, var3.charset, 91, (Object)null));
         ((Collection)var3.values).add(HttpUrl.Companion.canonicalize$okhttp$default(HttpUrl.Companion, var2, 0, 0, " \"':;<=>@[]^`{}|/\\?#&!$(),~", false, false, true, false, var3.charset, 91, (Object)null));
         return var3;
      }

      public final FormBody.Builder addEncoded(String var1, String var2) {
         Intrinsics.checkParameterIsNotNull(var1, "name");
         Intrinsics.checkParameterIsNotNull(var2, "value");
         FormBody.Builder var3 = (FormBody.Builder)this;
         ((Collection)var3.names).add(HttpUrl.Companion.canonicalize$okhttp$default(HttpUrl.Companion, var1, 0, 0, " \"':;<=>@[]^`{}|/\\?#&!$(),~", true, false, true, false, var3.charset, 83, (Object)null));
         ((Collection)var3.values).add(HttpUrl.Companion.canonicalize$okhttp$default(HttpUrl.Companion, var2, 0, 0, " \"':;<=>@[]^`{}|/\\?#&!$(),~", true, false, true, false, var3.charset, 83, (Object)null));
         return var3;
      }

      public final FormBody build() {
         return new FormBody(this.names, this.values);
      }
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0005"},
      d2 = {"Lokhttp3/FormBody$Companion;", "", "()V", "CONTENT_TYPE", "Lokhttp3/MediaType;", "okhttp"},
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
   }
}
