package okio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.RandomAccess;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.AbstractList;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\b\u0018\u0000 \u00152\b\u0012\u0004\u0012\u00020\u00020\u00012\u00060\u0003j\u0002`\u0004:\u0001\u0015B\u001f\b\u0002\u0012\u000e\u0010\u0005\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u0011\u0010\u0013\u001a\u00020\u00022\u0006\u0010\u0014\u001a\u00020\u000eH\u0096\u0002R\u001e\u0010\u0005\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\u0006X\u0080\u0004¢\u0006\n\n\u0002\u0010\f\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\r\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0007\u001a\u00020\bX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u0016"},
   d2 = {"Lokio/Options;", "Lkotlin/collections/AbstractList;", "Lokio/ByteString;", "Ljava/util/RandomAccess;", "Lkotlin/collections/RandomAccess;", "byteStrings", "", "trie", "", "([Lokio/ByteString;[I)V", "getByteStrings$okio", "()[Lokio/ByteString;", "[Lokio/ByteString;", "size", "", "getSize", "()I", "getTrie$okio", "()[I", "get", "index", "Companion", "okio"},
   k = 1,
   mv = {1, 1, 16}
)
public final class Options extends AbstractList<ByteString> implements RandomAccess {
   public static final Options.Companion Companion = new Options.Companion((DefaultConstructorMarker)null);
   private final ByteString[] byteStrings;
   private final int[] trie;

   private Options(ByteString[] var1, int[] var2) {
      this.byteStrings = var1;
      this.trie = var2;
   }

   // $FF: synthetic method
   public Options(ByteString[] var1, int[] var2, DefaultConstructorMarker var3) {
      this(var1, var2);
   }

   @JvmStatic
   public static final Options of(ByteString... var0) {
      return Companion.of(var0);
   }

   public ByteString get(int var1) {
      return this.byteStrings[var1];
   }

   public final ByteString[] getByteStrings$okio() {
      return this.byteStrings;
   }

   public int getSize() {
      return this.byteStrings.length;
   }

   public final int[] getTrie$okio() {
      return this.trie;
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u0011\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002JT\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u00052\b\b\u0002\u0010\f\u001a\u00020\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\b\b\u0002\u0010\u0011\u001a\u00020\r2\b\b\u0002\u0010\u0012\u001a\u00020\r2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\r0\u000fH\u0002J!\u0010\u0014\u001a\u00020\u00152\u0012\u0010\u000e\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00100\u0016\"\u00020\u0010H\u0007¢\u0006\u0002\u0010\u0017R\u0018\u0010\u0003\u001a\u00020\u0004*\u00020\u00058BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0018"},
      d2 = {"Lokio/Options$Companion;", "", "()V", "intCount", "", "Lokio/Buffer;", "getIntCount", "(Lokio/Buffer;)J", "buildTrieRecursive", "", "nodeOffset", "node", "byteStringOffset", "", "byteStrings", "", "Lokio/ByteString;", "fromIndex", "toIndex", "indexes", "of", "Lokio/Options;", "", "([Lokio/ByteString;)Lokio/Options;", "okio"},
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

      private final void buildTrieRecursive(long var1, Buffer var3, int var4, List<? extends ByteString> var5, int var6, int var7, List<Integer> var8) {
         int var9 = var4;
         boolean var15;
         if (var6 < var7) {
            var15 = true;
         } else {
            var15 = false;
         }

         if (!var15) {
            throw (Throwable)(new IllegalArgumentException("Failed requirement.".toString()));
         } else {
            for(var4 = var6; var4 < var7; ++var4) {
               boolean var10;
               if (((ByteString)var5.get(var4)).size() >= var9) {
                  var10 = true;
               } else {
                  var10 = false;
               }

               if (!var10) {
                  throw (Throwable)(new IllegalArgumentException("Failed requirement.".toString()));
               }
            }

            ByteString var11 = (ByteString)var5.get(var6);
            ByteString var12 = (ByteString)var5.get(var7 - 1);
            int var17;
            if (var9 == var11.size()) {
               var17 = ((Number)var8.get(var6)).intValue();
               var4 = var6 + 1;
               var11 = (ByteString)var5.get(var4);
               var6 = var17;
            } else {
               var4 = var6;
               var6 = -1;
            }

            int var13;
            int var14;
            Buffer var18;
            Options.Companion var20;
            if (var11.getByte(var9) != var12.getByte(var9)) {
               var13 = var4 + 1;

               for(var17 = 1; var13 < var7; var17 = var14) {
                  var14 = var17;
                  if (((ByteString)var5.get(var13 - 1)).getByte(var9) != ((ByteString)var5.get(var13)).getByte(var9)) {
                     var14 = var17 + 1;
                  }

                  ++var13;
               }

               var20 = (Options.Companion)this;
               var1 = var1 + var20.getIntCount(var3) + (long)2 + (long)(var17 * 2);
               var3.writeInt(var17);
               var3.writeInt(var6);

               for(var6 = var4; var6 < var7; ++var6) {
                  byte var19 = ((ByteString)var5.get(var6)).getByte(var9);
                  if (var6 == var4 || var19 != ((ByteString)var5.get(var6 - 1)).getByte(var9)) {
                     var3.writeInt(var19 & 255);
                  }
               }

               var18 = new Buffer();

               for(var6 = var4; var6 < var7; var6 = var4) {
                  byte var21 = ((ByteString)var5.get(var6)).getByte(var9);
                  var17 = var6 + 1;
                  var4 = var17;

                  while(true) {
                     if (var4 >= var7) {
                        var4 = var7;
                        break;
                     }

                     if (var21 != ((ByteString)var5.get(var4)).getByte(var9)) {
                        break;
                     }

                     ++var4;
                  }

                  if (var17 == var4 && var9 + 1 == ((ByteString)var5.get(var6)).size()) {
                     var3.writeInt(((Number)var8.get(var6)).intValue());
                  } else {
                     var3.writeInt((int)(var1 + var20.getIntCount(var18)) * -1);
                     var20.buildTrieRecursive(var1, var18, var9 + 1, var5, var6, var4, var8);
                  }
               }

               var3.writeAll((Source)var18);
            } else {
               var14 = Math.min(var11.size(), var12.size());
               var17 = var9;

               for(var13 = 0; var17 < var14 && var11.getByte(var17) == var12.getByte(var17); ++var17) {
                  ++var13;
               }

               var20 = (Options.Companion)this;
               var1 = 1L + var1 + var20.getIntCount(var3) + (long)2 + (long)var13;
               var3.writeInt(-var13);
               var3.writeInt(var6);

               for(var6 = var9 + var13; var9 < var6; ++var9) {
                  var3.writeInt(var11.getByte(var9) & 255);
               }

               if (var4 + 1 == var7) {
                  boolean var16;
                  if (var6 == ((ByteString)var5.get(var4)).size()) {
                     var16 = true;
                  } else {
                     var16 = false;
                  }

                  if (!var16) {
                     throw (Throwable)(new IllegalStateException("Check failed.".toString()));
                  }

                  var3.writeInt(((Number)var8.get(var4)).intValue());
               } else {
                  var18 = new Buffer();
                  var3.writeInt((int)(var20.getIntCount(var18) + var1) * -1);
                  var20.buildTrieRecursive(var1, var18, var6, var5, var4, var7, var8);
                  var3.writeAll((Source)var18);
               }
            }

         }
      }

      // $FF: synthetic method
      static void buildTrieRecursive$default(Options.Companion var0, long var1, Buffer var3, int var4, List var5, int var6, int var7, List var8, int var9, Object var10) {
         if ((var9 & 1) != 0) {
            var1 = 0L;
         }

         if ((var9 & 4) != 0) {
            var4 = 0;
         }

         if ((var9 & 16) != 0) {
            var6 = 0;
         }

         if ((var9 & 32) != 0) {
            var7 = var5.size();
         }

         var0.buildTrieRecursive(var1, var3, var4, var5, var6, var7, var8);
      }

      private final long getIntCount(Buffer var1) {
         return var1.size() / (long)4;
      }

      @JvmStatic
      public final Options of(ByteString... var1) {
         Intrinsics.checkParameterIsNotNull(var1, "byteStrings");
         int var2 = var1.length;
         byte var3 = 0;
         boolean var13;
         if (var2 == 0) {
            var13 = true;
         } else {
            var13 = false;
         }

         if (var13) {
            return new Options(new ByteString[0], new int[]{0, -1}, (DefaultConstructorMarker)null);
         } else {
            List var4 = ArraysKt.toMutableList(var1);
            CollectionsKt.sort(var4);
            Collection var5 = (Collection)(new ArrayList(var1.length));
            int var6 = var1.length;

            for(var2 = 0; var2 < var6; ++var2) {
               ByteString var10000 = var1[var2];
               var5.add(-1);
            }

            Object[] var15 = ((Collection)((List)var5)).toArray(new Integer[0]);
            if (var15 == null) {
               throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
            } else {
               Integer[] var16 = (Integer[])var15;
               List var17 = CollectionsKt.mutableListOf((Integer[])Arrays.copyOf(var16, var16.length));
               int var8 = var1.length;
               var6 = 0;

               for(var2 = 0; var6 < var8; ++var2) {
                  var17.set(CollectionsKt.binarySearch$default(var4, (Comparable)var1[var6], 0, 0, 6, (Object)null), var2);
                  ++var6;
               }

               if (((ByteString)var4.get(0)).size() > 0) {
                  var13 = true;
               } else {
                  var13 = false;
               }

               if (!var13) {
                  throw (Throwable)(new IllegalArgumentException("the empty byte string is not a supported option".toString()));
               } else {
                  for(var6 = 0; var6 < var4.size(); var6 = var2) {
                     ByteString var9 = (ByteString)var4.get(var6);
                     var2 = var6 + 1;
                     var8 = var2;

                     while(var8 < var4.size()) {
                        ByteString var7 = (ByteString)var4.get(var8);
                        if (!var7.startsWith(var9)) {
                           break;
                        }

                        boolean var10;
                        if (var7.size() != var9.size()) {
                           var10 = true;
                        } else {
                           var10 = false;
                        }

                        if (!var10) {
                           StringBuilder var11 = new StringBuilder();
                           var11.append("duplicate option: ");
                           var11.append(var7);
                           throw (Throwable)(new IllegalArgumentException(var11.toString().toString()));
                        }

                        if (((Number)var17.get(var8)).intValue() > ((Number)var17.get(var6)).intValue()) {
                           var4.remove(var8);
                           var17.remove(var8);
                        } else {
                           ++var8;
                        }
                     }
                  }

                  Buffer var18 = new Buffer();
                  Options.Companion var19 = (Options.Companion)this;
                  buildTrieRecursive$default(var19, 0L, var18, 0, var4, 0, 0, var17, 53, (Object)null);
                  int[] var14 = new int[(int)var19.getIntCount(var18)];

                  for(var2 = var3; !var18.exhausted(); ++var2) {
                     var14[var2] = var18.readInt();
                  }

                  Object[] var12 = Arrays.copyOf(var1, var1.length);
                  Intrinsics.checkExpressionValueIsNotNull(var12, "java.util.Arrays.copyOf(this, size)");
                  return new Options((ByteString[])var12, var14, (DefaultConstructorMarker)null);
               }
            }
         }
      }
   }
}
