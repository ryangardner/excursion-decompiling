/*
 * Decompiled with CFR <Could not determine version>.
 */
package kotlin.jvm.internal;

import kotlin.jvm.internal.FunctionReference;
import kotlin.reflect.KDeclarationContainer;

public class FunctionReferenceImpl
extends FunctionReference {
    private final String name;
    private final KDeclarationContainer owner;
    private final String signature;

    public FunctionReferenceImpl(int n, KDeclarationContainer kDeclarationContainer, String string2, String string3) {
        super(n);
        this.owner = kDeclarationContainer;
        this.name = string2;
        this.signature = string3;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public KDeclarationContainer getOwner() {
        return this.owner;
    }

    @Override
    public String getSignature() {
        return this.signature;
    }
}

