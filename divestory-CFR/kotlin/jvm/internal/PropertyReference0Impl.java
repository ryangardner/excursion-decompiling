/*
 * Decompiled with CFR <Could not determine version>.
 */
package kotlin.jvm.internal;

import kotlin.jvm.internal.PropertyReference0;
import kotlin.reflect.KDeclarationContainer;
import kotlin.reflect.KProperty;

public class PropertyReference0Impl
extends PropertyReference0 {
    private final String name;
    private final KDeclarationContainer owner;
    private final String signature;

    public PropertyReference0Impl(KDeclarationContainer kDeclarationContainer, String string2, String string3) {
        this.owner = kDeclarationContainer;
        this.name = string2;
        this.signature = string3;
    }

    public Object get() {
        return this.getGetter().call(new Object[0]);
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
