package okhttp3.internal.http2;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okio.ByteString;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0005\b\u0086\b\u0018\u0000 \u00132\u00020\u0001:\u0001\u0013B\u0017\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005B\u0017\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0006\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0007B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0006\u0012\u0006\u0010\u0004\u001a\u00020\u0006¢\u0006\u0002\u0010\bJ\t\u0010\u000b\u001a\u00020\u0006HÆ\u0003J\t\u0010\f\u001a\u00020\u0006HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00062\b\b\u0002\u0010\u0004\u001a\u00020\u0006HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\nHÖ\u0001J\b\u0010\u0012\u001a\u00020\u0003H\u0016R\u0010\u0010\t\u001a\u00020\n8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0002\u001a\u00020\u00068\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0004\u001a\u00020\u00068\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"},
   d2 = {"Lokhttp3/internal/http2/Header;", "", "name", "", "value", "(Ljava/lang/String;Ljava/lang/String;)V", "Lokio/ByteString;", "(Lokio/ByteString;Ljava/lang/String;)V", "(Lokio/ByteString;Lokio/ByteString;)V", "hpackSize", "", "component1", "component2", "copy", "equals", "", "other", "hashCode", "toString", "Companion", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public final class Header {
   public static final Header.Companion Companion = new Header.Companion((DefaultConstructorMarker)null);
   public static final ByteString PSEUDO_PREFIX;
   public static final ByteString RESPONSE_STATUS;
   public static final String RESPONSE_STATUS_UTF8 = ":status";
   public static final ByteString TARGET_AUTHORITY;
   public static final String TARGET_AUTHORITY_UTF8 = ":authority";
   public static final ByteString TARGET_METHOD;
   public static final String TARGET_METHOD_UTF8 = ":method";
   public static final ByteString TARGET_PATH;
   public static final String TARGET_PATH_UTF8 = ":path";
   public static final ByteString TARGET_SCHEME;
   public static final String TARGET_SCHEME_UTF8 = ":scheme";
   public final int hpackSize;
   public final ByteString name;
   public final ByteString value;

   static {
      PSEUDO_PREFIX = ByteString.Companion.encodeUtf8(":");
      RESPONSE_STATUS = ByteString.Companion.encodeUtf8(":status");
      TARGET_METHOD = ByteString.Companion.encodeUtf8(":method");
      TARGET_PATH = ByteString.Companion.encodeUtf8(":path");
      TARGET_SCHEME = ByteString.Companion.encodeUtf8(":scheme");
      TARGET_AUTHORITY = ByteString.Companion.encodeUtf8(":authority");
   }

   public Header(String var1, String var2) {
      Intrinsics.checkParameterIsNotNull(var1, "name");
      Intrinsics.checkParameterIsNotNull(var2, "value");
      this(ByteString.Companion.encodeUtf8(var1), ByteString.Companion.encodeUtf8(var2));
   }

   public Header(ByteString var1, String var2) {
      Intrinsics.checkParameterIsNotNull(var1, "name");
      Intrinsics.checkParameterIsNotNull(var2, "value");
      this(var1, ByteString.Companion.encodeUtf8(var2));
   }

   public Header(ByteString var1, ByteString var2) {
      Intrinsics.checkParameterIsNotNull(var1, "name");
      Intrinsics.checkParameterIsNotNull(var2, "value");
      super();
      this.name = var1;
      this.value = var2;
      this.hpackSize = var1.size() + 32 + this.value.size();
   }

   // $FF: synthetic method
   public static Header copy$default(Header var0, ByteString var1, ByteString var2, int var3, Object var4) {
      if ((var3 & 1) != 0) {
         var1 = var0.name;
      }

      if ((var3 & 2) != 0) {
         var2 = var0.value;
      }

      return var0.copy(var1, var2);
   }

   public final ByteString component1() {
      return this.name;
   }

   public final ByteString component2() {
      return this.value;
   }

   public final Header copy(ByteString var1, ByteString var2) {
      Intrinsics.checkParameterIsNotNull(var1, "name");
      Intrinsics.checkParameterIsNotNull(var2, "value");
      return new Header(var1, var2);
   }

   public boolean equals(Object var1) {
      if (this != var1) {
         if (!(var1 instanceof Header)) {
            return false;
         }

         Header var2 = (Header)var1;
         if (!Intrinsics.areEqual((Object)this.name, (Object)var2.name) || !Intrinsics.areEqual((Object)this.value, (Object)var2.value)) {
            return false;
         }
      }

      return true;
   }

   public int hashCode() {
      ByteString var1 = this.name;
      int var2 = 0;
      int var3;
      if (var1 != null) {
         var3 = var1.hashCode();
      } else {
         var3 = 0;
      }

      var1 = this.value;
      if (var1 != null) {
         var2 = var1.hashCode();
      }

      return var3 * 31 + var2;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append(this.name.utf8());
      var1.append(": ");
      var1.append(this.value.utf8());
      return var1.toString();
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\t\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0086T¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0007X\u0086T¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0007X\u0086T¢\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0007X\u0086T¢\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0007X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0010"},
      d2 = {"Lokhttp3/internal/http2/Header$Companion;", "", "()V", "PSEUDO_PREFIX", "Lokio/ByteString;", "RESPONSE_STATUS", "RESPONSE_STATUS_UTF8", "", "TARGET_AUTHORITY", "TARGET_AUTHORITY_UTF8", "TARGET_METHOD", "TARGET_METHOD_UTF8", "TARGET_PATH", "TARGET_PATH_UTF8", "TARGET_SCHEME", "TARGET_SCHEME_UTF8", "okhttp"},
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
