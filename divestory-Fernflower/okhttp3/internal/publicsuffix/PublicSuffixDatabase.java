package okhttp3.internal.publicsuffix;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.net.IDN;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import okhttp3.internal.Util;
import okio.BufferedSource;
import okio.GzipSource;
import okio.Okio;
import okio.Source;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0005\u0018\u0000 \u00152\u00020\u0001:\u0001\u0015B\u0005¢\u0006\u0002\u0010\u0002J\u001c\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\f0\u000bH\u0002J\u0010\u0010\u000e\u001a\u0004\u0018\u00010\f2\u0006\u0010\u000f\u001a\u00020\fJ\b\u0010\u0010\u001a\u00020\u0011H\u0002J\b\u0010\u0012\u001a\u00020\u0011H\u0002J\u0016\u0010\u0013\u001a\u00020\u00112\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u0006J\u0016\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\u0006\u0010\u000f\u001a\u00020\fH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"},
   d2 = {"Lokhttp3/internal/publicsuffix/PublicSuffixDatabase;", "", "()V", "listRead", "Ljava/util/concurrent/atomic/AtomicBoolean;", "publicSuffixExceptionListBytes", "", "publicSuffixListBytes", "readCompleteLatch", "Ljava/util/concurrent/CountDownLatch;", "findMatchingRule", "", "", "domainLabels", "getEffectiveTldPlusOne", "domain", "readTheList", "", "readTheListUninterruptibly", "setListBytes", "splitDomain", "Companion", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public final class PublicSuffixDatabase {
   public static final PublicSuffixDatabase.Companion Companion = new PublicSuffixDatabase.Companion((DefaultConstructorMarker)null);
   private static final char EXCEPTION_MARKER = '!';
   private static final List<String> PREVAILING_RULE = CollectionsKt.listOf("*");
   public static final String PUBLIC_SUFFIX_RESOURCE = "publicsuffixes.gz";
   private static final byte[] WILDCARD_LABEL = new byte[]{(byte)42};
   private static final PublicSuffixDatabase instance = new PublicSuffixDatabase();
   private final AtomicBoolean listRead = new AtomicBoolean(false);
   private byte[] publicSuffixExceptionListBytes;
   private byte[] publicSuffixListBytes;
   private final CountDownLatch readCompleteLatch = new CountDownLatch(1);

   // $FF: synthetic method
   public static final byte[] access$getPublicSuffixListBytes$p(PublicSuffixDatabase var0) {
      byte[] var1 = var0.publicSuffixListBytes;
      if (var1 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("publicSuffixListBytes");
      }

      return var1;
   }

   // $FF: synthetic method
   public static final void access$setPublicSuffixListBytes$p(PublicSuffixDatabase var0, byte[] var1) {
      var0.publicSuffixListBytes = var1;
   }

   private final List<String> findMatchingRule(List<String> var1) {
      if (!this.listRead.get() && this.listRead.compareAndSet(false, true)) {
         this.readTheListUninterruptibly();
      } else {
         try {
            this.readCompleteLatch.await();
         } catch (InterruptedException var10) {
            Thread.currentThread().interrupt();
         }
      }

      boolean var3;
      if (((PublicSuffixDatabase)this).publicSuffixListBytes != null) {
         var3 = true;
      } else {
         var3 = false;
      }

      if (!var3) {
         throw (Throwable)(new IllegalStateException("Unable to load publicsuffixes.gz resource from the classpath.".toString()));
      } else {
         int var4 = var1.size();
         byte[][] var2 = new byte[var4][];

         String var5;
         int var16;
         for(var16 = 0; var16 < var4; ++var16) {
            var5 = (String)var1.get(var16);
            Charset var6 = StandardCharsets.UTF_8;
            Intrinsics.checkExpressionValueIsNotNull(var6, "UTF_8");
            if (var5 == null) {
               throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }

            byte[] var17 = var5.getBytes(var6);
            Intrinsics.checkExpressionValueIsNotNull(var17, "(this as java.lang.String).getBytes(charset)");
            var2[var16] = var17;
         }

         byte[][] var7 = (byte[][])var2;
         String var11 = (String)null;
         var4 = var7.length;
         var16 = 0;

         String var13;
         PublicSuffixDatabase.Companion var18;
         while(true) {
            if (var16 >= var4) {
               var13 = var11;
               break;
            }

            var18 = Companion;
            byte[] var12 = this.publicSuffixListBytes;
            if (var12 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("publicSuffixListBytes");
            }

            var13 = var18.binarySearch(var12, var7, var16);
            if (var13 != null) {
               break;
            }

            ++var16;
         }

         Object[] var8;
         label111: {
            var8 = (Object[])var7;
            if (var8.length > 1) {
               byte[][] var19 = (byte[][])var8.clone();
               var4 = ((Object[])var19).length;

               for(var16 = 0; var16 < var4 - 1; ++var16) {
                  var19[var16] = WILDCARD_LABEL;
                  var18 = Companion;
                  byte[] var9 = this.publicSuffixListBytes;
                  if (var9 == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("publicSuffixListBytes");
                  }

                  var5 = var18.binarySearch(var9, var19, var16);
                  if (var5 != null) {
                     break label111;
                  }
               }
            }

            var5 = var11;
         }

         String var20 = var11;
         if (var5 != null) {
            var4 = var8.length;
            var16 = 0;

            while(true) {
               var20 = var11;
               if (var16 >= var4 - 1) {
                  break;
               }

               PublicSuffixDatabase.Companion var22 = Companion;
               byte[] var21 = this.publicSuffixExceptionListBytes;
               if (var21 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("publicSuffixExceptionListBytes");
               }

               var20 = var22.binarySearch(var21, var7, var16);
               if (var20 != null) {
                  break;
               }

               ++var16;
            }
         }

         if (var20 != null) {
            StringBuilder var14 = new StringBuilder();
            var14.append('!');
            var14.append(var20);
            return StringsKt.split$default((CharSequence)var14.toString(), new char[]{'.'}, false, 0, 6, (Object)null);
         } else if (var13 == null && var5 == null) {
            return PREVAILING_RULE;
         } else {
            label86: {
               if (var13 != null) {
                  var1 = StringsKt.split$default((CharSequence)var13, new char[]{'.'}, false, 0, 6, (Object)null);
                  if (var1 != null) {
                     break label86;
                  }
               }

               var1 = CollectionsKt.emptyList();
            }

            List var15;
            label81: {
               if (var5 != null) {
                  var15 = StringsKt.split$default((CharSequence)var5, new char[]{'.'}, false, 0, 6, (Object)null);
                  if (var15 != null) {
                     break label81;
                  }
               }

               var15 = CollectionsKt.emptyList();
            }

            if (var1.size() <= var15.size()) {
               var1 = var15;
            }

            return var1;
         }
      }
   }

   private final void readTheList() throws IOException {
      byte[] var1 = (byte[])null;
      InputStream var48 = PublicSuffixDatabase.class.getResourceAsStream("publicsuffixes.gz");
      if (var48 != null) {
         Closeable var49 = (Closeable)Okio.buffer((Source)(new GzipSource(Okio.source(var48))));
         Throwable var2 = (Throwable)null;

         byte[] var52;
         byte[] var53;
         try {
            BufferedSource var3 = (BufferedSource)var49;
            var53 = var3.readByteArray((long)var3.readInt());
            var52 = var3.readByteArray((long)var3.readInt());
            Unit var5 = Unit.INSTANCE;
         } catch (Throwable var43) {
            Throwable var4 = var43;

            try {
               throw var4;
            } finally {
               CloseableKt.closeFinally(var49, var43);
            }
         }

         label330: {
            Throwable var10000;
            label336: {
               CloseableKt.closeFinally(var49, var2);
               synchronized(this){}
               boolean var10001;
               if (var53 == null) {
                  try {
                     Intrinsics.throwNpe();
                  } catch (Throwable var47) {
                     var10000 = var47;
                     var10001 = false;
                     break label336;
                  }
               }

               try {
                  this.publicSuffixListBytes = var53;
               } catch (Throwable var46) {
                  var10000 = var46;
                  var10001 = false;
                  break label336;
               }

               if (var52 == null) {
                  try {
                     Intrinsics.throwNpe();
                  } catch (Throwable var45) {
                     var10000 = var45;
                     var10001 = false;
                     break label336;
                  }
               }

               label318:
               try {
                  this.publicSuffixExceptionListBytes = var52;
                  Unit var51 = Unit.INSTANCE;
                  break label330;
               } catch (Throwable var44) {
                  var10000 = var44;
                  var10001 = false;
                  break label318;
               }
            }

            Throwable var50 = var10000;
            throw var50;
         }

         this.readCompleteLatch.countDown();
      }
   }

   private final void readTheListUninterruptibly() {
      // $FF: Couldn't be decompiled
   }

   private final List<String> splitDomain(String var1) {
      List var2 = StringsKt.split$default((CharSequence)var1, new char[]{'.'}, false, 0, 6, (Object)null);
      return Intrinsics.areEqual((Object)((String)CollectionsKt.last(var2)), (Object)"") ? CollectionsKt.dropLast(var2, 1) : var2;
   }

   public final String getEffectiveTldPlusOne(String var1) {
      Intrinsics.checkParameterIsNotNull(var1, "domain");
      String var2 = IDN.toUnicode(var1);
      Intrinsics.checkExpressionValueIsNotNull(var2, "unicodeDomain");
      List var3 = this.splitDomain(var2);
      List var6 = this.findMatchingRule(var3);
      if (var3.size() == var6.size() && ((String)var6.get(0)).charAt(0) != '!') {
         return null;
      } else {
         int var4;
         int var5;
         if (((String)var6.get(0)).charAt(0) == '!') {
            var4 = var3.size();
            var5 = var6.size();
         } else {
            var4 = var3.size();
            var5 = var6.size() + 1;
         }

         return SequencesKt.joinToString$default(SequencesKt.drop(CollectionsKt.asSequence((Iterable)this.splitDomain(var1)), var4 - var5), (CharSequence)".", (CharSequence)null, (CharSequence)null, 0, (CharSequence)null, (Function1)null, 62, (Object)null);
      }
   }

   public final void setListBytes(byte[] var1, byte[] var2) {
      Intrinsics.checkParameterIsNotNull(var1, "publicSuffixListBytes");
      Intrinsics.checkParameterIsNotNull(var2, "publicSuffixExceptionListBytes");
      this.publicSuffixListBytes = var1;
      this.publicSuffixExceptionListBytes = var2;
      this.listRead.set(true);
      this.readCompleteLatch.countDown();
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\f\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\r\u001a\u00020\fJ)\u0010\u000e\u001a\u0004\u0018\u00010\u0007*\u00020\n2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\n0\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0002¢\u0006\u0002\u0010\u0013R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"},
      d2 = {"Lokhttp3/internal/publicsuffix/PublicSuffixDatabase$Companion;", "", "()V", "EXCEPTION_MARKER", "", "PREVAILING_RULE", "", "", "PUBLIC_SUFFIX_RESOURCE", "WILDCARD_LABEL", "", "instance", "Lokhttp3/internal/publicsuffix/PublicSuffixDatabase;", "get", "binarySearch", "labels", "", "labelIndex", "", "([B[[BI)Ljava/lang/String;", "okhttp"},
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

      private final String binarySearch(byte[] var1, byte[][] var2, int var3) {
         int var4 = var1.length;
         String var5 = (String)null;
         int var6 = 0;

         while(true) {
            String var7 = var5;
            if (var6 >= var4) {
               return var7;
            }

            int var8;
            for(var8 = (var6 + var4) / 2; var8 > -1 && var1[var8] != (byte)10; --var8) {
            }

            int var9 = var8 + 1;
            var8 = 1;

            while(true) {
               int var10 = var9 + var8;
               if (var1[var10] == (byte)10) {
                  int var11 = var10 - var9;
                  int var12 = var3;
                  boolean var13 = false;
                  var8 = 0;
                  int var14 = 0;

                  int var15;
                  while(true) {
                     if (var13) {
                        var15 = 46;
                        var13 = false;
                     } else {
                        var15 = Util.and((byte)var2[var12][var8], 255);
                     }

                     var15 -= Util.and((byte)var1[var9 + var14], 255);
                     if (var15 != 0) {
                        break;
                     }

                     ++var14;
                     ++var8;
                     if (var14 == var11) {
                        break;
                     }

                     if (var2[var12].length == var8) {
                        if (var12 == ((Object[])var2).length - 1) {
                           break;
                        }

                        ++var12;
                        var13 = true;
                        var8 = -1;
                     }
                  }

                  if (var15 >= 0) {
                     label95: {
                        if (var15 <= 0) {
                           var14 = var11 - var14;
                           int var17 = var2[var12].length - var8;
                           var8 = var12 + 1;

                           for(var12 = ((Object[])var2).length; var8 < var12; ++var8) {
                              var17 += var2[var8].length;
                           }

                           if (var17 < var14) {
                              break label95;
                           }

                           if (var17 <= var14) {
                              Charset var16 = StandardCharsets.UTF_8;
                              Intrinsics.checkExpressionValueIsNotNull(var16, "UTF_8");
                              var7 = new String(var1, var9, var11, var16);
                              return var7;
                           }
                        }

                        var6 = var10 + 1;
                        break;
                     }
                  }

                  var4 = var9 - 1;
                  break;
               }

               ++var8;
            }
         }
      }

      public final PublicSuffixDatabase get() {
         return PublicSuffixDatabase.instance;
      }
   }
}
