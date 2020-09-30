/*
 * Decompiled with CFR <Could not determine version>.
 */
package kotlin.internal;

import java.lang.reflect.Method;
import java.util.regex.MatchResult;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.FallbackThreadLocalRandom;
import kotlin.random.Random;
import kotlin.text.MatchGroup;

@Metadata(bv={1, 0, 3}, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0010\u0018\u00002\u00020\u0001:\u0001\u0010B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0016J\b\u0010\b\u001a\u00020\tH\u0016J\u001a\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016\u00a8\u0006\u0011"}, d2={"Lkotlin/internal/PlatformImplementations;", "", "()V", "addSuppressed", "", "cause", "", "exception", "defaultPlatformRandom", "Lkotlin/random/Random;", "getMatchResultNamedGroup", "Lkotlin/text/MatchGroup;", "matchResult", "Ljava/util/regex/MatchResult;", "name", "", "ReflectAddSuppressedMethod", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
public class PlatformImplementations {
    public void addSuppressed(Throwable throwable, Throwable throwable2) {
        Intrinsics.checkParameterIsNotNull(throwable, "cause");
        Intrinsics.checkParameterIsNotNull(throwable2, "exception");
        Method method = ReflectAddSuppressedMethod.method;
        if (method == null) return;
        method.invoke(throwable, throwable2);
    }

    public Random defaultPlatformRandom() {
        return new FallbackThreadLocalRandom();
    }

    public MatchGroup getMatchResultNamedGroup(MatchResult matchResult, String string2) {
        Intrinsics.checkParameterIsNotNull(matchResult, "matchResult");
        Intrinsics.checkParameterIsNotNull(string2, "name");
        throw (Throwable)new UnsupportedOperationException("Retrieving groups by name is not supported on this platform.");
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u00c2\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u00048\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2={"Lkotlin/internal/PlatformImplementations$ReflectAddSuppressedMethod;", "", "()V", "method", "Ljava/lang/reflect/Method;", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
    private static final class ReflectAddSuppressedMethod {
        public static final ReflectAddSuppressedMethod INSTANCE;
        public static final Method method;

        /*
         * Unable to fully structure code
         */
        static {
            block4 : {
                ReflectAddSuppressedMethod.INSTANCE = new ReflectAddSuppressedMethod();
                var0 = Throwable.class.getMethods();
                Intrinsics.checkExpressionValueIsNotNull(var0, "throwableClass.methods");
                for (Method var3_3 : var0) {
                    Intrinsics.checkExpressionValueIsNotNull(var3_3, "it");
                    if (!Intrinsics.areEqual(var3_3.getName(), "addSuppressed")) ** GOTO lbl-1000
                    var4_4 = var3_3.getParameterTypes();
                    Intrinsics.checkExpressionValueIsNotNull(var4_4, "it.parameterTypes");
                    if (Intrinsics.areEqual(ArraysKt.singleOrNull(var4_4), Throwable.class)) {
                        var5_5 = true;
                    } else lbl-1000: // 2 sources:
                    {
                        var5_5 = false;
                    }
                    if (!var5_5) {
                        continue;
                    }
                    break block4;
                }
                var3_3 = null;
            }
            ReflectAddSuppressedMethod.method = var3_3;
        }

        private ReflectAddSuppressedMethod() {
        }
    }

}

