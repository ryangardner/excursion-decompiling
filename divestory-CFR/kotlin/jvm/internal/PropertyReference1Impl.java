/*
 * Decompiled with CFR <Could not determine version>.
 */
package kotlin.jvm.internal;

import kotlin.jvm.internal.PropertyReference1;
import kotlin.reflect.KDeclarationContainer;
import kotlin.reflect.KProperty;

public class PropertyReference1Impl
extends PropertyReference1 {
    private final String name;
    private final KDeclarationContainer owner;
    private final String signature;

    public PropertyReference1Impl(KDeclarationContainer kDeclarationContainer, String string2, String string3) {
        this.owner = kDeclarationContainer;
        this.name = string2;
        this.signature = string3;
    }

    public Object get(Object object) {
        return this.getGetter().call(object);
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

