package okhttp3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.internal.Util;
import okio.Buffer;
import okio.BufferedSink;
import okio.ByteString;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\u0018\u0000 #2\u00020\u0001:\u0003\"#$B%\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\u0002\u0010\tJ\r\u0010\n\u001a\u00020\u000bH\u0007¢\u0006\u0002\b\u0015J\b\u0010\r\u001a\u00020\u000eH\u0016J\b\u0010\u000f\u001a\u00020\u0005H\u0016J\u000e\u0010\u0016\u001a\u00020\b2\u0006\u0010\u0017\u001a\u00020\u0012J\u0013\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u0007¢\u0006\u0002\b\u0018J\r\u0010\u0011\u001a\u00020\u0012H\u0007¢\u0006\u0002\b\u0019J\r\u0010\u0004\u001a\u00020\u0005H\u0007¢\u0006\u0002\b\u001aJ\u001a\u0010\u001b\u001a\u00020\u000e2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001d2\u0006\u0010\u001e\u001a\u00020\u001fH\u0002J\u0010\u0010 \u001a\u00020!2\u0006\u0010\u001c\u001a\u00020\u001dH\u0016R\u0011\u0010\n\u001a\u00020\u000b8G¢\u0006\u0006\u001a\u0004\b\n\u0010\fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0019\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00078\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0010R\u0011\u0010\u0011\u001a\u00020\u00128G¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0013R\u0013\u0010\u0004\u001a\u00020\u00058\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0014¨\u0006%"},
   d2 = {"Lokhttp3/MultipartBody;", "Lokhttp3/RequestBody;", "boundaryByteString", "Lokio/ByteString;", "type", "Lokhttp3/MediaType;", "parts", "", "Lokhttp3/MultipartBody$Part;", "(Lokio/ByteString;Lokhttp3/MediaType;Ljava/util/List;)V", "boundary", "", "()Ljava/lang/String;", "contentLength", "", "contentType", "()Ljava/util/List;", "size", "", "()I", "()Lokhttp3/MediaType;", "-deprecated_boundary", "part", "index", "-deprecated_parts", "-deprecated_size", "-deprecated_type", "writeOrCountBytes", "sink", "Lokio/BufferedSink;", "countBytes", "", "writeTo", "", "Builder", "Companion", "Part", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public final class MultipartBody extends RequestBody {
   public static final MediaType ALTERNATIVE;
   private static final byte[] COLONSPACE;
   private static final byte[] CRLF;
   public static final MultipartBody.Companion Companion = new MultipartBody.Companion((DefaultConstructorMarker)null);
   private static final byte[] DASHDASH;
   public static final MediaType DIGEST;
   public static final MediaType FORM;
   public static final MediaType MIXED;
   public static final MediaType PARALLEL;
   private final ByteString boundaryByteString;
   private long contentLength;
   private final MediaType contentType;
   private final List<MultipartBody.Part> parts;
   private final MediaType type;

   static {
      MIXED = MediaType.Companion.get("multipart/mixed");
      ALTERNATIVE = MediaType.Companion.get("multipart/alternative");
      DIGEST = MediaType.Companion.get("multipart/digest");
      PARALLEL = MediaType.Companion.get("multipart/parallel");
      FORM = MediaType.Companion.get("multipart/form-data");
      COLONSPACE = new byte[]{(byte)58, (byte)32};
      CRLF = new byte[]{(byte)13, (byte)10};
      byte var0 = (byte)45;
      DASHDASH = new byte[]{var0, var0};
   }

   public MultipartBody(ByteString var1, MediaType var2, List<MultipartBody.Part> var3) {
      Intrinsics.checkParameterIsNotNull(var1, "boundaryByteString");
      Intrinsics.checkParameterIsNotNull(var2, "type");
      Intrinsics.checkParameterIsNotNull(var3, "parts");
      super();
      this.boundaryByteString = var1;
      this.type = var2;
      this.parts = var3;
      MediaType.Companion var5 = MediaType.Companion;
      StringBuilder var4 = new StringBuilder();
      var4.append(this.type);
      var4.append("; boundary=");
      var4.append(this.boundary());
      this.contentType = var5.get(var4.toString());
      this.contentLength = -1L;
   }

   private final long writeOrCountBytes(BufferedSink var1, boolean var2) throws IOException {
      Buffer var3 = (Buffer)null;
      if (var2) {
         var3 = new Buffer();
         var1 = (BufferedSink)var3;
      }

      int var4 = this.parts.size();
      long var5 = 0L;

      long var12;
      for(int var7 = 0; var7 < var4; ++var7) {
         MultipartBody.Part var8 = (MultipartBody.Part)this.parts.get(var7);
         Headers var9 = var8.headers();
         RequestBody var14 = var8.body();
         if (var1 == null) {
            Intrinsics.throwNpe();
         }

         var1.write(DASHDASH);
         var1.write(this.boundaryByteString);
         var1.write(CRLF);
         if (var9 != null) {
            int var10 = var9.size();

            for(int var11 = 0; var11 < var10; ++var11) {
               var1.writeUtf8(var9.name(var11)).write(COLONSPACE).writeUtf8(var9.value(var11)).write(CRLF);
            }
         }

         MediaType var15 = var14.contentType();
         if (var15 != null) {
            var1.writeUtf8("Content-Type: ").writeUtf8(var15.toString()).write(CRLF);
         }

         var12 = var14.contentLength();
         if (var12 != -1L) {
            var1.writeUtf8("Content-Length: ").writeDecimalLong(var12).write(CRLF);
         } else if (var2) {
            if (var3 == null) {
               Intrinsics.throwNpe();
            }

            var3.clear();
            return -1L;
         }

         var1.write(CRLF);
         if (var2) {
            var5 += var12;
         } else {
            var14.writeTo(var1);
         }

         var1.write(CRLF);
      }

      if (var1 == null) {
         Intrinsics.throwNpe();
      }

      var1.write(DASHDASH);
      var1.write(this.boundaryByteString);
      var1.write(DASHDASH);
      var1.write(CRLF);
      var12 = var5;
      if (var2) {
         if (var3 == null) {
            Intrinsics.throwNpe();
         }

         var12 = var5 + var3.size();
         var3.clear();
      }

      return var12;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "boundary",
   imports = {}
)
   )
   public final String _deprecated_boundary/* $FF was: -deprecated_boundary*/() {
      return this.boundary();
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "parts",
   imports = {}
)
   )
   public final List<MultipartBody.Part> _deprecated_parts/* $FF was: -deprecated_parts*/() {
      return this.parts;
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

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "type",
   imports = {}
)
   )
   public final MediaType _deprecated_type/* $FF was: -deprecated_type*/() {
      return this.type;
   }

   public final String boundary() {
      return this.boundaryByteString.utf8();
   }

   public long contentLength() throws IOException {
      long var1 = this.contentLength;
      long var3 = var1;
      if (var1 == -1L) {
         var3 = this.writeOrCountBytes((BufferedSink)null, true);
         this.contentLength = var3;
      }

      return var3;
   }

   public MediaType contentType() {
      return this.contentType;
   }

   public final MultipartBody.Part part(int var1) {
      return (MultipartBody.Part)this.parts.get(var1);
   }

   public final List<MultipartBody.Part> parts() {
      return this.parts;
   }

   public final int size() {
      return this.parts.size();
   }

   public final MediaType type() {
      return this.type;
   }

   public void writeTo(BufferedSink var1) throws IOException {
      Intrinsics.checkParameterIsNotNull(var1, "sink");
      this.writeOrCountBytes(var1, false);
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0011\b\u0007\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0016\u0010\u000b\u001a\u00020\u00002\u0006\u0010\f\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u0003J \u0010\u000b\u001a\u00020\u00002\u0006\u0010\f\u001a\u00020\u00032\b\u0010\u000e\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u000f\u001a\u00020\u0010J\u0018\u0010\u0011\u001a\u00020\u00002\b\u0010\u0012\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u000f\u001a\u00020\u0010J\u000e\u0010\u0011\u001a\u00020\u00002\u0006\u0010\u0014\u001a\u00020\bJ\u000e\u0010\u0011\u001a\u00020\u00002\u0006\u0010\u000f\u001a\u00020\u0010J\u0006\u0010\u0015\u001a\u00020\u0016J\u000e\u0010\u0017\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\nR\u000e\u0010\u0002\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0018"},
      d2 = {"Lokhttp3/MultipartBody$Builder;", "", "boundary", "", "(Ljava/lang/String;)V", "Lokio/ByteString;", "parts", "", "Lokhttp3/MultipartBody$Part;", "type", "Lokhttp3/MediaType;", "addFormDataPart", "name", "value", "filename", "body", "Lokhttp3/RequestBody;", "addPart", "headers", "Lokhttp3/Headers;", "part", "build", "Lokhttp3/MultipartBody;", "setType", "okhttp"},
      k = 1,
      mv = {1, 1, 16}
   )
   public static final class Builder {
      private final ByteString boundary;
      private final List<MultipartBody.Part> parts;
      private MediaType type;

      public Builder() {
         this((String)null, 1, (DefaultConstructorMarker)null);
      }

      public Builder(String var1) {
         Intrinsics.checkParameterIsNotNull(var1, "boundary");
         super();
         this.boundary = ByteString.Companion.encodeUtf8(var1);
         this.type = MultipartBody.MIXED;
         this.parts = (List)(new ArrayList());
      }

      // $FF: synthetic method
      public Builder(String var1, int var2, DefaultConstructorMarker var3) {
         if ((var2 & 1) != 0) {
            var1 = UUID.randomUUID().toString();
            Intrinsics.checkExpressionValueIsNotNull(var1, "UUID.randomUUID().toString()");
         }

         this(var1);
      }

      public final MultipartBody.Builder addFormDataPart(String var1, String var2) {
         Intrinsics.checkParameterIsNotNull(var1, "name");
         Intrinsics.checkParameterIsNotNull(var2, "value");
         MultipartBody.Builder var3 = (MultipartBody.Builder)this;
         var3.addPart(MultipartBody.Part.Companion.createFormData(var1, var2));
         return var3;
      }

      public final MultipartBody.Builder addFormDataPart(String var1, String var2, RequestBody var3) {
         Intrinsics.checkParameterIsNotNull(var1, "name");
         Intrinsics.checkParameterIsNotNull(var3, "body");
         MultipartBody.Builder var4 = (MultipartBody.Builder)this;
         var4.addPart(MultipartBody.Part.Companion.createFormData(var1, var2, var3));
         return var4;
      }

      public final MultipartBody.Builder addPart(Headers var1, RequestBody var2) {
         Intrinsics.checkParameterIsNotNull(var2, "body");
         MultipartBody.Builder var3 = (MultipartBody.Builder)this;
         var3.addPart(MultipartBody.Part.Companion.create(var1, var2));
         return var3;
      }

      public final MultipartBody.Builder addPart(MultipartBody.Part var1) {
         Intrinsics.checkParameterIsNotNull(var1, "part");
         MultipartBody.Builder var2 = (MultipartBody.Builder)this;
         ((Collection)var2.parts).add(var1);
         return var2;
      }

      public final MultipartBody.Builder addPart(RequestBody var1) {
         Intrinsics.checkParameterIsNotNull(var1, "body");
         MultipartBody.Builder var2 = (MultipartBody.Builder)this;
         var2.addPart(MultipartBody.Part.Companion.create(var1));
         return var2;
      }

      public final MultipartBody build() {
         if (((Collection)this.parts).isEmpty() ^ true) {
            return new MultipartBody(this.boundary, this.type, Util.toImmutableList(this.parts));
         } else {
            throw (Throwable)(new IllegalStateException("Multipart body must have at least one part.".toString()));
         }
      }

      public final MultipartBody.Builder setType(MediaType var1) {
         Intrinsics.checkParameterIsNotNull(var1, "type");
         MultipartBody.Builder var2 = (MultipartBody.Builder)this;
         if (Intrinsics.areEqual((Object)var1.type(), (Object)"multipart")) {
            var2.type = var1;
            return var2;
         } else {
            StringBuilder var3 = new StringBuilder();
            var3.append("multipart != ");
            var3.append(var1);
            throw (Throwable)(new IllegalArgumentException(var3.toString().toString()));
         }
      }
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001d\u0010\r\u001a\u00020\u000e*\u00060\u000fj\u0002`\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0000¢\u0006\u0002\b\u0013R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"},
      d2 = {"Lokhttp3/MultipartBody$Companion;", "", "()V", "ALTERNATIVE", "Lokhttp3/MediaType;", "COLONSPACE", "", "CRLF", "DASHDASH", "DIGEST", "FORM", "MIXED", "PARALLEL", "appendQuotedString", "", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "key", "", "appendQuotedString$okhttp", "okhttp"},
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

      public final void appendQuotedString$okhttp(StringBuilder var1, String var2) {
         Intrinsics.checkParameterIsNotNull(var1, "$this$appendQuotedString");
         Intrinsics.checkParameterIsNotNull(var2, "key");
         var1.append('"');
         int var3 = var2.length();

         for(int var4 = 0; var4 < var3; ++var4) {
            char var5 = var2.charAt(var4);
            if (var5 == '\n') {
               var1.append("%0A");
            } else if (var5 == '\r') {
               var1.append("%0D");
            } else if (var5 == '"') {
               var1.append("%22");
            } else {
               var1.append(var5);
            }
         }

         var1.append('"');
      }
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000 \u000b2\u00020\u0001:\u0001\u000bB\u0019\b\u0002\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\r\u0010\u0004\u001a\u00020\u0005H\u0007¢\u0006\u0002\b\tJ\u000f\u0010\u0002\u001a\u0004\u0018\u00010\u0003H\u0007¢\u0006\u0002\b\nR\u0013\u0010\u0004\u001a\u00020\u00058\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0007R\u0015\u0010\u0002\u001a\u0004\u0018\u00010\u00038\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\b¨\u0006\f"},
      d2 = {"Lokhttp3/MultipartBody$Part;", "", "headers", "Lokhttp3/Headers;", "body", "Lokhttp3/RequestBody;", "(Lokhttp3/Headers;Lokhttp3/RequestBody;)V", "()Lokhttp3/RequestBody;", "()Lokhttp3/Headers;", "-deprecated_body", "-deprecated_headers", "Companion", "okhttp"},
      k = 1,
      mv = {1, 1, 16}
   )
   public static final class Part {
      public static final MultipartBody.Part.Companion Companion = new MultipartBody.Part.Companion((DefaultConstructorMarker)null);
      private final RequestBody body;
      private final Headers headers;

      private Part(Headers var1, RequestBody var2) {
         this.headers = var1;
         this.body = var2;
      }

      // $FF: synthetic method
      public Part(Headers var1, RequestBody var2, DefaultConstructorMarker var3) {
         this(var1, var2);
      }

      @JvmStatic
      public static final MultipartBody.Part create(Headers var0, RequestBody var1) {
         return Companion.create(var0, var1);
      }

      @JvmStatic
      public static final MultipartBody.Part create(RequestBody var0) {
         return Companion.create(var0);
      }

      @JvmStatic
      public static final MultipartBody.Part createFormData(String var0, String var1) {
         return Companion.createFormData(var0, var1);
      }

      @JvmStatic
      public static final MultipartBody.Part createFormData(String var0, String var1, RequestBody var2) {
         return Companion.createFormData(var0, var1, var2);
      }

      @Deprecated(
         level = DeprecationLevel.ERROR,
         message = "moved to val",
         replaceWith = @ReplaceWith(
   expression = "body",
   imports = {}
)
      )
      public final RequestBody _deprecated_body/* $FF was: -deprecated_body*/() {
         return this.body;
      }

      @Deprecated(
         level = DeprecationLevel.ERROR,
         message = "moved to val",
         replaceWith = @ReplaceWith(
   expression = "headers",
   imports = {}
)
      )
      public final Headers _deprecated_headers/* $FF was: -deprecated_headers*/() {
         return this.headers;
      }

      public final RequestBody body() {
         return this.body;
      }

      public final Headers headers() {
         return this.headers;
      }

      @Metadata(
         bv = {1, 0, 3},
         d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\bH\u0007J\u0018\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000bH\u0007J\"\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u000b2\b\u0010\r\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u0007\u001a\u00020\bH\u0007¨\u0006\u000e"},
         d2 = {"Lokhttp3/MultipartBody$Part$Companion;", "", "()V", "create", "Lokhttp3/MultipartBody$Part;", "headers", "Lokhttp3/Headers;", "body", "Lokhttp3/RequestBody;", "createFormData", "name", "", "value", "filename", "okhttp"},
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

         @JvmStatic
         public final MultipartBody.Part create(Headers var1, RequestBody var2) {
            Intrinsics.checkParameterIsNotNull(var2, "body");
            String var3;
            if (var1 != null) {
               var3 = var1.get("Content-Type");
            } else {
               var3 = null;
            }

            boolean var4 = true;
            boolean var5;
            if (var3 == null) {
               var5 = true;
            } else {
               var5 = false;
            }

            if (var5) {
               if (var1 != null) {
                  var3 = var1.get("Content-Length");
               } else {
                  var3 = null;
               }

               if (var3 == null) {
                  var5 = var4;
               } else {
                  var5 = false;
               }

               if (var5) {
                  return new MultipartBody.Part(var1, var2, (DefaultConstructorMarker)null);
               } else {
                  throw (Throwable)(new IllegalArgumentException("Unexpected header: Content-Length".toString()));
               }
            } else {
               throw (Throwable)(new IllegalArgumentException("Unexpected header: Content-Type".toString()));
            }
         }

         @JvmStatic
         public final MultipartBody.Part create(RequestBody var1) {
            Intrinsics.checkParameterIsNotNull(var1, "body");
            return ((MultipartBody.Part.Companion)this).create((Headers)null, var1);
         }

         @JvmStatic
         public final MultipartBody.Part createFormData(String var1, String var2) {
            Intrinsics.checkParameterIsNotNull(var1, "name");
            Intrinsics.checkParameterIsNotNull(var2, "value");
            return ((MultipartBody.Part.Companion)this).createFormData(var1, (String)null, RequestBody.Companion.create$default(RequestBody.Companion, (String)var2, (MediaType)null, 1, (Object)null));
         }

         @JvmStatic
         public final MultipartBody.Part createFormData(String var1, String var2, RequestBody var3) {
            Intrinsics.checkParameterIsNotNull(var1, "name");
            Intrinsics.checkParameterIsNotNull(var3, "body");
            StringBuilder var4 = new StringBuilder();
            var4.append("form-data; name=");
            MultipartBody.Companion.appendQuotedString$okhttp(var4, var1);
            if (var2 != null) {
               var4.append("; filename=");
               MultipartBody.Companion.appendQuotedString$okhttp(var4, var2);
            }

            var1 = var4.toString();
            Intrinsics.checkExpressionValueIsNotNull(var1, "StringBuilder().apply(builderAction).toString()");
            Headers var5 = (new Headers.Builder()).addUnsafeNonAscii("Content-Disposition", var1).build();
            return ((MultipartBody.Part.Companion)this).create(var5, var3);
         }
      }
   }
}
