package kotlin.text;

import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0002\b\u0019\b\u0086\u0001\u0018\u0000 \u001b2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u001bB\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012j\u0002\b\u0013j\u0002\b\u0014j\u0002\b\u0015j\u0002\b\u0016j\u0002\b\u0017j\u0002\b\u0018j\u0002\b\u0019j\u0002\b\u001a¨\u0006\u001c"},
   d2 = {"Lkotlin/text/CharDirectionality;", "", "value", "", "(Ljava/lang/String;II)V", "getValue", "()I", "UNDEFINED", "LEFT_TO_RIGHT", "RIGHT_TO_LEFT", "RIGHT_TO_LEFT_ARABIC", "EUROPEAN_NUMBER", "EUROPEAN_NUMBER_SEPARATOR", "EUROPEAN_NUMBER_TERMINATOR", "ARABIC_NUMBER", "COMMON_NUMBER_SEPARATOR", "NONSPACING_MARK", "BOUNDARY_NEUTRAL", "PARAGRAPH_SEPARATOR", "SEGMENT_SEPARATOR", "WHITESPACE", "OTHER_NEUTRALS", "LEFT_TO_RIGHT_EMBEDDING", "LEFT_TO_RIGHT_OVERRIDE", "RIGHT_TO_LEFT_EMBEDDING", "RIGHT_TO_LEFT_OVERRIDE", "POP_DIRECTIONAL_FORMAT", "Companion", "kotlin-stdlib"},
   k = 1,
   mv = {1, 1, 16}
)
public enum CharDirectionality {
   ARABIC_NUMBER,
   BOUNDARY_NEUTRAL,
   COMMON_NUMBER_SEPARATOR;

   public static final CharDirectionality.Companion Companion;
   EUROPEAN_NUMBER,
   EUROPEAN_NUMBER_SEPARATOR,
   EUROPEAN_NUMBER_TERMINATOR,
   LEFT_TO_RIGHT,
   LEFT_TO_RIGHT_EMBEDDING,
   LEFT_TO_RIGHT_OVERRIDE,
   NONSPACING_MARK,
   OTHER_NEUTRALS,
   PARAGRAPH_SEPARATOR,
   POP_DIRECTIONAL_FORMAT,
   RIGHT_TO_LEFT,
   RIGHT_TO_LEFT_ARABIC,
   RIGHT_TO_LEFT_EMBEDDING,
   RIGHT_TO_LEFT_OVERRIDE,
   SEGMENT_SEPARATOR,
   UNDEFINED,
   WHITESPACE;

   private static final Lazy directionalityMap$delegate;
   private final int value;

   static {
      CharDirectionality var0 = new CharDirectionality("UNDEFINED", 0, -1);
      UNDEFINED = var0;
      CharDirectionality var1 = new CharDirectionality("LEFT_TO_RIGHT", 1, 0);
      LEFT_TO_RIGHT = var1;
      CharDirectionality var2 = new CharDirectionality("RIGHT_TO_LEFT", 2, 1);
      RIGHT_TO_LEFT = var2;
      CharDirectionality var3 = new CharDirectionality("RIGHT_TO_LEFT_ARABIC", 3, 2);
      RIGHT_TO_LEFT_ARABIC = var3;
      CharDirectionality var4 = new CharDirectionality("EUROPEAN_NUMBER", 4, 3);
      EUROPEAN_NUMBER = var4;
      CharDirectionality var5 = new CharDirectionality("EUROPEAN_NUMBER_SEPARATOR", 5, 4);
      EUROPEAN_NUMBER_SEPARATOR = var5;
      CharDirectionality var6 = new CharDirectionality("EUROPEAN_NUMBER_TERMINATOR", 6, 5);
      EUROPEAN_NUMBER_TERMINATOR = var6;
      CharDirectionality var7 = new CharDirectionality("ARABIC_NUMBER", 7, 6);
      ARABIC_NUMBER = var7;
      CharDirectionality var8 = new CharDirectionality("COMMON_NUMBER_SEPARATOR", 8, 7);
      COMMON_NUMBER_SEPARATOR = var8;
      CharDirectionality var9 = new CharDirectionality("NONSPACING_MARK", 9, 8);
      NONSPACING_MARK = var9;
      CharDirectionality var10 = new CharDirectionality("BOUNDARY_NEUTRAL", 10, 9);
      BOUNDARY_NEUTRAL = var10;
      CharDirectionality var11 = new CharDirectionality("PARAGRAPH_SEPARATOR", 11, 10);
      PARAGRAPH_SEPARATOR = var11;
      CharDirectionality var12 = new CharDirectionality("SEGMENT_SEPARATOR", 12, 11);
      SEGMENT_SEPARATOR = var12;
      CharDirectionality var13 = new CharDirectionality("WHITESPACE", 13, 12);
      WHITESPACE = var13;
      CharDirectionality var14 = new CharDirectionality("OTHER_NEUTRALS", 14, 13);
      OTHER_NEUTRALS = var14;
      CharDirectionality var15 = new CharDirectionality("LEFT_TO_RIGHT_EMBEDDING", 15, 14);
      LEFT_TO_RIGHT_EMBEDDING = var15;
      CharDirectionality var16 = new CharDirectionality("LEFT_TO_RIGHT_OVERRIDE", 16, 15);
      LEFT_TO_RIGHT_OVERRIDE = var16;
      CharDirectionality var17 = new CharDirectionality("RIGHT_TO_LEFT_EMBEDDING", 17, 16);
      RIGHT_TO_LEFT_EMBEDDING = var17;
      CharDirectionality var18 = new CharDirectionality("RIGHT_TO_LEFT_OVERRIDE", 18, 17);
      RIGHT_TO_LEFT_OVERRIDE = var18;
      CharDirectionality var19 = new CharDirectionality("POP_DIRECTIONAL_FORMAT", 19, 18);
      POP_DIRECTIONAL_FORMAT = var19;
      Companion = new CharDirectionality.Companion((DefaultConstructorMarker)null);
      directionalityMap$delegate = LazyKt.lazy((Function0)null.INSTANCE);
   }

   private CharDirectionality(int var3) {
      this.value = var3;
   }

   public final int getValue() {
      return this.value;
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u000b\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\u0005R'\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u00048BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\b¨\u0006\r"},
      d2 = {"Lkotlin/text/CharDirectionality$Companion;", "", "()V", "directionalityMap", "", "", "Lkotlin/text/CharDirectionality;", "getDirectionalityMap", "()Ljava/util/Map;", "directionalityMap$delegate", "Lkotlin/Lazy;", "valueOf", "directionality", "kotlin-stdlib"},
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

      private final Map<Integer, CharDirectionality> getDirectionalityMap() {
         Lazy var1 = CharDirectionality.directionalityMap$delegate;
         CharDirectionality.Companion var2 = CharDirectionality.Companion;
         return (Map)var1.getValue();
      }

      public final CharDirectionality valueOf(int var1) {
         CharDirectionality var2 = (CharDirectionality)((CharDirectionality.Companion)this).getDirectionalityMap().get(var1);
         if (var2 != null) {
            return var2;
         } else {
            StringBuilder var3 = new StringBuilder();
            var3.append("Directionality #");
            var3.append(var1);
            var3.append(" is not defined.");
            throw (Throwable)(new IllegalArgumentException(var3.toString()));
         }
      }
   }
}
