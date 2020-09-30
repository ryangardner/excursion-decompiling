/*
 * Decompiled with CFR <Could not determine version>.
 */
package kotlin.internal;

import kotlin.KotlinVersion;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.internal.PlatformImplementations;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

@Metadata(bv={1, 0, 3}, d1={"\u0000\u001e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\b\u0004\u001a \u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u0005H\u0001\u001a\"\u0010\b\u001a\u0002H\t\"\n\b\u0000\u0010\t\u0018\u0001*\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\u0083\b\u00a2\u0006\u0002\u0010\f\u001a\b\u0010\r\u001a\u00020\u0005H\u0002\"\u0010\u0010\u0000\u001a\u00020\u00018\u0000X\u0081\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000e"}, d2={"IMPLEMENTATIONS", "Lkotlin/internal/PlatformImplementations;", "apiVersionIsAtLeast", "", "major", "", "minor", "patch", "castToBaseType", "T", "", "instance", "(Ljava/lang/Object;)Ljava/lang/Object;", "getJavaVersion", "kotlin-stdlib"}, k=2, mv={1, 1, 16})
public final class PlatformImplementationsKt {
    public static final PlatformImplementations IMPLEMENTATIONS;

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    static {
        block16 : {
            block20 : {
                block17 : {
                    var0 = PlatformImplementationsKt.getJavaVersion();
                    if (var0 < 65544) break block17;
                    try {
                        var1_1 = Class.forName("kotlin.internal.jdk8.JDK8PlatformImplementations").newInstance();
                        Intrinsics.checkExpressionValueIsNotNull(var1_1, "Class.forName(\"kotlin.in\u2026entations\").newInstance()");
                        if (var1_1 == null) ** GOTO lbl9
                        try {
                            block18 : {
                                break block18;
lbl9: // 1 sources:
                                var2_11 = new TypeCastException("null cannot be cast to non-null type kotlin.internal.PlatformImplementations");
                                throw var2_11;
                            }
                            var2_9 = (PlatformImplementations)var1_1;
                            break block16;
                        }
                        catch (ClassCastException var2_10) {}
                        var3_26 = var1_1.getClass().getClassLoader();
                        var1_2 = PlatformImplementations.class.getClassLoader();
                        var5_34 = new StringBuilder();
                        var5_34.append("Instance classloader: ");
                        var5_34.append(var3_26);
                        var5_34.append(", base type classloader: ");
                        var5_34.append(var1_2);
                        var4_30 = new ClassCastException(var5_34.toString());
                        var2_12 = var4_30.initCause(var2_10);
                        Intrinsics.checkExpressionValueIsNotNull(var2_12, "ClassCastException(\"Inst\u2026baseTypeCL\").initCause(e)");
                        throw var2_12;
                    }
                    catch (ClassNotFoundException var2_13) {
                        try {
                            var1_3 = Class.forName("kotlin.internal.JRE8PlatformImplementations").newInstance();
                            Intrinsics.checkExpressionValueIsNotNull(var1_3, "Class.forName(\"kotlin.in\u2026entations\").newInstance()");
                            if (var1_3 == null) ** GOTO lbl37
                            try {
                                block19 : {
                                    break block19;
lbl37: // 1 sources:
                                    var2_15 = new TypeCastException("null cannot be cast to non-null type kotlin.internal.PlatformImplementations");
                                    throw var2_15;
                                }
                                var2_9 = (PlatformImplementations)var1_3;
                                break block16;
                            }
                            catch (ClassCastException var2_14) {}
                            var1_4 = var1_3.getClass().getClassLoader();
                            var5_35 = PlatformImplementations.class.getClassLoader();
                            var4_31 = new StringBuilder();
                            var4_31.append("Instance classloader: ");
                            var4_31.append(var1_4);
                            var4_31.append(", base type classloader: ");
                            var4_31.append(var5_35);
                            var3_27 = new ClassCastException(var4_31.toString());
                            var2_16 = var3_27.initCause(var2_14);
                            Intrinsics.checkExpressionValueIsNotNull(var2_16, "ClassCastException(\"Inst\u2026baseTypeCL\").initCause(e)");
                            throw var2_16;
                        }
                        catch (ClassNotFoundException var2_17) {
                            // empty catch block
                        }
                    }
                }
                if (var0 < 65543) break block20;
                try {
                    var1_5 = Class.forName("kotlin.internal.jdk7.JDK7PlatformImplementations").newInstance();
                    Intrinsics.checkExpressionValueIsNotNull(var1_5, "Class.forName(\"kotlin.in\u2026entations\").newInstance()");
                    if (var1_5 == null) ** GOTO lbl68
                    try {
                        block21 : {
                            break block21;
lbl68: // 1 sources:
                            var2_19 = new TypeCastException("null cannot be cast to non-null type kotlin.internal.PlatformImplementations");
                            throw var2_19;
                        }
                        var2_9 = (PlatformImplementations)var1_5;
                        break block16;
                    }
                    catch (ClassCastException var2_18) {}
                    var3_28 = var1_5.getClass().getClassLoader();
                    var5_36 = PlatformImplementations.class.getClassLoader();
                    var1_6 = new StringBuilder();
                    var1_6.append("Instance classloader: ");
                    var1_6.append(var3_28);
                    var1_6.append(", base type classloader: ");
                    var1_6.append(var5_36);
                    var4_32 = new ClassCastException(var1_6.toString());
                    var2_20 = var4_32.initCause(var2_18);
                    Intrinsics.checkExpressionValueIsNotNull(var2_20, "ClassCastException(\"Inst\u2026baseTypeCL\").initCause(e)");
                    throw var2_20;
                }
                catch (ClassNotFoundException var2_21) {
                    try {
                        var1_7 = Class.forName("kotlin.internal.JRE7PlatformImplementations").newInstance();
                        Intrinsics.checkExpressionValueIsNotNull(var1_7, "Class.forName(\"kotlin.in\u2026entations\").newInstance()");
                        if (var1_7 == null) ** GOTO lbl96
                        try {
                            block22 : {
                                break block22;
lbl96: // 1 sources:
                                var2_23 = new TypeCastException("null cannot be cast to non-null type kotlin.internal.PlatformImplementations");
                                throw var2_23;
                            }
                            var2_9 = (PlatformImplementations)var1_7;
                            break block16;
                        }
                        catch (ClassCastException var2_22) {}
                        var3_29 = var1_7.getClass().getClassLoader();
                        var4_33 = PlatformImplementations.class.getClassLoader();
                        var5_37 = new StringBuilder();
                        var5_37.append("Instance classloader: ");
                        var5_37.append(var3_29);
                        var5_37.append(", base type classloader: ");
                        var5_37.append(var4_33);
                        var1_8 = new ClassCastException(var5_37.toString());
                        var2_24 = var1_8.initCause(var2_22);
                        Intrinsics.checkExpressionValueIsNotNull(var2_24, "ClassCastException(\"Inst\u2026baseTypeCL\").initCause(e)");
                        throw var2_24;
                    }
                    catch (ClassNotFoundException var2_25) {}
                }
            }
            var2_9 = new PlatformImplementations();
        }
        PlatformImplementationsKt.IMPLEMENTATIONS = var2_9;
    }

    public static final boolean apiVersionIsAtLeast(int n, int n2, int n3) {
        return KotlinVersion.CURRENT.isAtLeast(n, n2, n3);
    }

    private static final /* synthetic */ <T> T castToBaseType(Object object) {
        Object object2;
        try {
            Intrinsics.reifiedOperationMarker(1, "T");
            object2 = object;
        }
        catch (ClassCastException classCastException) {
            object = object.getClass().getClassLoader();
            Intrinsics.reifiedOperationMarker(4, "T");
            ClassLoader classLoader = Object.class.getClassLoader();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Instance classloader: ");
            stringBuilder.append(object);
            stringBuilder.append(", base type classloader: ");
            stringBuilder.append(classLoader);
            object = new ClassCastException(stringBuilder.toString()).initCause(classCastException);
            Intrinsics.checkExpressionValueIsNotNull(object, "ClassCastException(\"Inst\u2026baseTypeCL\").initCause(e)");
            throw object;
        }
        return (T)object2;
    }

    /*
     * Loose catch block
     * Enabled unnecessary exception pruning
     */
    private static final int getJavaVersion() {
        int n;
        String string2 = System.getProperty("java.specification.version");
        int n2 = 65542;
        if (string2 == null) return 65542;
        CharSequence charSequence = string2;
        int n3 = StringsKt.indexOf$default(charSequence, '.', 0, false, 6, null);
        if (n3 < 0) {
            int n4 = Integer.parseInt(string2);
            return n4 * 65536;
        }
        int n5 = n3 + 1;
        int n6 = n = StringsKt.indexOf$default(charSequence, '.', n5, false, 4, null);
        if (n < 0) {
            n6 = string2.length();
        }
        if (string2 == null) throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        charSequence = string2.substring(0, n3);
        Intrinsics.checkExpressionValueIsNotNull(charSequence, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
        if (string2 == null) throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        string2 = string2.substring(n5, n6);
        Intrinsics.checkExpressionValueIsNotNull(string2, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
        try {
            n = Integer.parseInt((String)charSequence);
            n6 = Integer.parseInt(string2);
            return n * 65536 + n6;
        }
        catch (NumberFormatException numberFormatException) {
            return n2;
        }
        catch (NumberFormatException numberFormatException) {
            return n2;
        }
    }
}

