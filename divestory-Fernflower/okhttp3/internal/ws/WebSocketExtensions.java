package okhttp3.internal.ws;

import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import okhttp3.Headers;
import okhttp3.internal.Util;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0015\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\b\u0018\u0000 \u001c2\u00020\u0001:\u0001\u001cBE\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\t\u001a\u00020\u0003¢\u0006\u0002\u0010\nJ\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\u0010\u0010\r\u001a\u0004\u0018\u00010\u0005HÆ\u0003¢\u0006\u0002\u0010\u000eJ\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u0005HÆ\u0003¢\u0006\u0002\u0010\u000eJ\t\u0010\u0011\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0012\u001a\u00020\u0003HÆ\u0003JN\u0010\u0013\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00032\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\u0003HÆ\u0001¢\u0006\u0002\u0010\u0014J\u0013\u0010\u0015\u001a\u00020\u00032\b\u0010\u0016\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0017\u001a\u00020\u0005HÖ\u0001J\u000e\u0010\u0018\u001a\u00020\u00032\u0006\u0010\u0019\u001a\u00020\u0003J\t\u0010\u001a\u001a\u00020\u001bHÖ\u0001R\u0014\u0010\u0004\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004¢\u0006\u0004\n\u0002\u0010\u000bR\u0010\u0010\u0006\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004¢\u0006\u0004\n\u0002\u0010\u000bR\u0010\u0010\b\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\u001d"},
   d2 = {"Lokhttp3/internal/ws/WebSocketExtensions;", "", "perMessageDeflate", "", "clientMaxWindowBits", "", "clientNoContextTakeover", "serverMaxWindowBits", "serverNoContextTakeover", "unknownValues", "(ZLjava/lang/Integer;ZLjava/lang/Integer;ZZ)V", "Ljava/lang/Integer;", "component1", "component2", "()Ljava/lang/Integer;", "component3", "component4", "component5", "component6", "copy", "(ZLjava/lang/Integer;ZLjava/lang/Integer;ZZ)Lokhttp3/internal/ws/WebSocketExtensions;", "equals", "other", "hashCode", "noContextTakeover", "clientOriginated", "toString", "", "Companion", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public final class WebSocketExtensions {
   public static final WebSocketExtensions.Companion Companion = new WebSocketExtensions.Companion((DefaultConstructorMarker)null);
   private static final String HEADER_WEB_SOCKET_EXTENSION = "Sec-WebSocket-Extensions";
   public final Integer clientMaxWindowBits;
   public final boolean clientNoContextTakeover;
   public final boolean perMessageDeflate;
   public final Integer serverMaxWindowBits;
   public final boolean serverNoContextTakeover;
   public final boolean unknownValues;

   public WebSocketExtensions() {
      this(false, (Integer)null, false, (Integer)null, false, false, 63, (DefaultConstructorMarker)null);
   }

   public WebSocketExtensions(boolean var1, Integer var2, boolean var3, Integer var4, boolean var5, boolean var6) {
      this.perMessageDeflate = var1;
      this.clientMaxWindowBits = var2;
      this.clientNoContextTakeover = var3;
      this.serverMaxWindowBits = var4;
      this.serverNoContextTakeover = var5;
      this.unknownValues = var6;
   }

   // $FF: synthetic method
   public WebSocketExtensions(boolean var1, Integer var2, boolean var3, Integer var4, boolean var5, boolean var6, int var7, DefaultConstructorMarker var8) {
      if ((var7 & 1) != 0) {
         var1 = false;
      }

      if ((var7 & 2) != 0) {
         var2 = (Integer)null;
      }

      if ((var7 & 4) != 0) {
         var3 = false;
      }

      if ((var7 & 8) != 0) {
         var4 = (Integer)null;
      }

      if ((var7 & 16) != 0) {
         var5 = false;
      }

      if ((var7 & 32) != 0) {
         var6 = false;
      }

      this(var1, var2, var3, var4, var5, var6);
   }

   // $FF: synthetic method
   public static WebSocketExtensions copy$default(WebSocketExtensions var0, boolean var1, Integer var2, boolean var3, Integer var4, boolean var5, boolean var6, int var7, Object var8) {
      if ((var7 & 1) != 0) {
         var1 = var0.perMessageDeflate;
      }

      if ((var7 & 2) != 0) {
         var2 = var0.clientMaxWindowBits;
      }

      if ((var7 & 4) != 0) {
         var3 = var0.clientNoContextTakeover;
      }

      if ((var7 & 8) != 0) {
         var4 = var0.serverMaxWindowBits;
      }

      if ((var7 & 16) != 0) {
         var5 = var0.serverNoContextTakeover;
      }

      if ((var7 & 32) != 0) {
         var6 = var0.unknownValues;
      }

      return var0.copy(var1, var2, var3, var4, var5, var6);
   }

   public final boolean component1() {
      return this.perMessageDeflate;
   }

   public final Integer component2() {
      return this.clientMaxWindowBits;
   }

   public final boolean component3() {
      return this.clientNoContextTakeover;
   }

   public final Integer component4() {
      return this.serverMaxWindowBits;
   }

   public final boolean component5() {
      return this.serverNoContextTakeover;
   }

   public final boolean component6() {
      return this.unknownValues;
   }

   public final WebSocketExtensions copy(boolean var1, Integer var2, boolean var3, Integer var4, boolean var5, boolean var6) {
      return new WebSocketExtensions(var1, var2, var3, var4, var5, var6);
   }

   public boolean equals(Object var1) {
      if (this != var1) {
         if (!(var1 instanceof WebSocketExtensions)) {
            return false;
         }

         WebSocketExtensions var2 = (WebSocketExtensions)var1;
         if (this.perMessageDeflate != var2.perMessageDeflate || !Intrinsics.areEqual((Object)this.clientMaxWindowBits, (Object)var2.clientMaxWindowBits) || this.clientNoContextTakeover != var2.clientNoContextTakeover || !Intrinsics.areEqual((Object)this.serverMaxWindowBits, (Object)var2.serverMaxWindowBits) || this.serverNoContextTakeover != var2.serverNoContextTakeover || this.unknownValues != var2.unknownValues) {
            return false;
         }
      }

      return true;
   }

   public int hashCode() {
      byte var1 = this.perMessageDeflate;
      byte var2 = 1;
      byte var3 = var1;
      if (var1 != 0) {
         var3 = 1;
      }

      Integer var4 = this.clientMaxWindowBits;
      int var5 = 0;
      int var9;
      if (var4 != null) {
         var9 = var4.hashCode();
      } else {
         var9 = 0;
      }

      byte var6 = this.clientNoContextTakeover;
      byte var7 = var6;
      if (var6 != 0) {
         var7 = 1;
      }

      var4 = this.serverMaxWindowBits;
      if (var4 != null) {
         var5 = var4.hashCode();
      }

      byte var8 = this.serverNoContextTakeover;
      var6 = var8;
      if (var8 != 0) {
         var6 = 1;
      }

      var8 = this.unknownValues;
      if (var8 == 0) {
         var2 = var8;
      }

      return ((((var3 * 31 + var9) * 31 + var7) * 31 + var5) * 31 + var6) * 31 + var2;
   }

   public final boolean noContextTakeover(boolean var1) {
      return var1 ? this.clientNoContextTakeover : this.serverNoContextTakeover;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("WebSocketExtensions(perMessageDeflate=");
      var1.append(this.perMessageDeflate);
      var1.append(", clientMaxWindowBits=");
      var1.append(this.clientMaxWindowBits);
      var1.append(", clientNoContextTakeover=");
      var1.append(this.clientNoContextTakeover);
      var1.append(", serverMaxWindowBits=");
      var1.append(this.serverMaxWindowBits);
      var1.append(", serverNoContextTakeover=");
      var1.append(this.serverNoContextTakeover);
      var1.append(", unknownValues=");
      var1.append(this.unknownValues);
      var1.append(")");
      return var1.toString();
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\t"},
      d2 = {"Lokhttp3/internal/ws/WebSocketExtensions$Companion;", "", "()V", "HEADER_WEB_SOCKET_EXTENSION", "", "parse", "Lokhttp3/internal/ws/WebSocketExtensions;", "responseHeaders", "Lokhttp3/Headers;", "okhttp"},
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

      public final WebSocketExtensions parse(Headers var1) throws IOException {
         Intrinsics.checkParameterIsNotNull(var1, "responseHeaders");
         Integer var2 = (Integer)null;
         int var3 = var1.size();
         Integer var4 = var2;
         int var5 = 0;
         boolean var6 = false;
         boolean var7 = false;
         boolean var8 = false;

         boolean var9;
         boolean var15;
         for(var9 = false; var5 < var3; var9 = var15) {
            boolean var10;
            Integer var11;
            boolean var12;
            Integer var13;
            boolean var14;
            if (!StringsKt.equals(var1.name(var5), "Sec-WebSocket-Extensions", true)) {
               var10 = var6;
               var11 = var2;
               var12 = var7;
               var13 = var4;
               var14 = var8;
               var15 = var9;
            } else {
               String var16 = var1.value(var5);
               int var17 = 0;

               while(true) {
                  var10 = var6;
                  var11 = var2;
                  var12 = var7;
                  var13 = var4;
                  var14 = var8;
                  var15 = var9;
                  if (var17 >= var16.length()) {
                     break;
                  }

                  int var18 = Util.delimiterOffset$default(var16, ',', var17, 0, 4, (Object)null);
                  int var19 = Util.delimiterOffset(var16, ';', var17, var18);
                  String var23 = Util.trimSubstring(var16, var17, var19);
                  ++var19;
                  if (!StringsKt.equals(var23, "permessage-deflate", true)) {
                     var17 = var19;
                     var9 = true;
                     var11 = var4;
                     var13 = var2;
                  } else {
                     var17 = var19;
                     var13 = var2;
                     var10 = var7;
                     var11 = var4;
                     var12 = var8;
                     if (var6) {
                        var9 = true;
                        var12 = var8;
                        var11 = var4;
                        var10 = var7;
                        var13 = var2;
                        var17 = var19;
                     }

                     while(var17 < var18) {
                        var19 = Util.delimiterOffset(var16, ';', var17, var18);
                        int var20 = Util.delimiterOffset(var16, '=', var17, var19);
                        String var21 = Util.trimSubstring(var16, var17, var20);
                        String var22;
                        if (var20 < var19) {
                           var22 = StringsKt.removeSurrounding(Util.trimSubstring(var16, var20 + 1, var19), (CharSequence)"\"");
                        } else {
                           var22 = null;
                        }

                        label93: {
                           if (StringsKt.equals(var21, "client_max_window_bits", true)) {
                              if (var13 != null) {
                                 var9 = true;
                              }

                              if (var22 != null) {
                                 var13 = StringsKt.toIntOrNull(var22);
                              } else {
                                 var13 = null;
                              }

                              var4 = var13;
                              var7 = var10;
                              var2 = var11;
                              var8 = var12;
                              if (var13 != null) {
                                 break label93;
                              }

                              var4 = var13;
                              var2 = var11;
                           } else {
                              if (StringsKt.equals(var21, "client_no_context_takeover", true)) {
                                 if (var10) {
                                    var9 = true;
                                 }

                                 if (var22 != null) {
                                    var9 = true;
                                 }

                                 var7 = true;
                                 var4 = var13;
                                 var2 = var11;
                                 var8 = var12;
                                 break label93;
                              }

                              if (StringsKt.equals(var21, "server_max_window_bits", true)) {
                                 if (var11 != null) {
                                    var9 = true;
                                 }

                                 Integer var24;
                                 if (var22 != null) {
                                    var24 = StringsKt.toIntOrNull(var22);
                                 } else {
                                    var24 = null;
                                 }

                                 var4 = var13;
                                 var7 = var10;
                                 var2 = var24;
                                 var8 = var12;
                                 if (var24 != null) {
                                    break label93;
                                 }

                                 var4 = var13;
                                 var2 = var24;
                              } else {
                                 var4 = var13;
                                 var2 = var11;
                                 if (StringsKt.equals(var21, "server_no_context_takeover", true)) {
                                    if (var12) {
                                       var9 = true;
                                    }

                                    if (var22 != null) {
                                       var9 = true;
                                    }

                                    var8 = true;
                                    var4 = var13;
                                    var7 = var10;
                                    var2 = var11;
                                    break label93;
                                 }
                              }
                           }

                           var9 = true;
                           var8 = var12;
                           var7 = var10;
                        }

                        var17 = var19 + 1;
                        var13 = var4;
                        var10 = var7;
                        var11 = var2;
                        var12 = var8;
                     }

                     var6 = true;
                     var7 = var10;
                     var8 = var12;
                  }

                  var2 = var13;
                  var4 = var11;
               }
            }

            ++var5;
            var6 = var10;
            var2 = var11;
            var7 = var12;
            var4 = var13;
            var8 = var14;
         }

         return new WebSocketExtensions(var6, var2, var7, var4, var8, var9);
      }
   }
}
