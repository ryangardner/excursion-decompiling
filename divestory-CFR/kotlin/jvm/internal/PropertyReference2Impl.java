/*
 * Decompiled with CFR <Could not determine version>.
 */
package kotlin.jvm.internal;

import kotlin.jvm.internal.PropertyReference2;
import kotlin.reflect.KDeclarationContainer;
import kotlin.reflect.KProperty;

public class PropertyReference2Impl
extends PropertyReference2 {
    private final String name;
    private final KDeclarationContainer owner;
    private final String signature;

    public PropertyReference2Impl(KDeclarationContainer kDeclarationContainer, String string2, String string3) {
        this.owner = kDeclarationContainer;
        this.name = string2;
        this.signature = string3;
    }

    public Object get(Object object, Object object2) {
        return this.getGetter().call(object, object2);
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

