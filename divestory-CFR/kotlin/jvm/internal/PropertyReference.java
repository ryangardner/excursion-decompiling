/*
 * Decompiled with CFR <Could not determine version>.
 */
package kotlin.jvm.internal;

import kotlin.jvm.internal.CallableReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KCallable;
import kotlin.reflect.KDeclarationContainer;
import kotlin.reflect.KProperty;

public abstract class PropertyReference
extends CallableReference
implements KProperty {
    public PropertyReference() {
    }

    public PropertyReference(Object object) {
        super(object);
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof PropertyReference)) {
            if (!(object instanceof KProperty)) return false;
            return object.equals(this.compute());
        }
        object = (PropertyReference)object;
        if (!this.getOwner().equals(((CallableReference)object).getOwner())) return false;
        if (!this.getName().equals(((CallableReference)object).getName())) return false;
        if (!this.getSignature().equals(((CallableReference)object).getSignature())) return false;
        if (!Intrinsics.areEqual(this.getBoundReceiver(), ((CallableReference)object).getBoundReceiver())) return false;
        return bl;
    }

    @Override
    protected KProperty getReflected() {
        return (KProperty)super.getReflected();
    }

    public int hashCode() {
        return (this.getOwner().hashCode() * 31 + this.getName().hashCode()) * 31 + this.getSignature().hashCode();
    }

    @Override
    public boolean isConst() {
        return this.getReflected().isConst();
    }

    @Override
    public boolean isLateinit() {
        return this.getReflected().isLateinit();
    }

    public String toString() {
        Object object = this.compute();
        if (object != this) {
            return object.toString();
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("property ");
        ((StringBuilder)object).append(this.getName());
        ((StringBuilder)object).append(" (Kotlin reflection is not available)");
        return ((StringBuilder)object).toString();
    }
}

