/*
 * Decompiled with CFR <Could not determine version>.
 */
package kotlin.jvm.internal;

import kotlin.jvm.internal.CallableReference;
import kotlin.jvm.internal.FunctionBase;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KCallable;
import kotlin.reflect.KDeclarationContainer;
import kotlin.reflect.KFunction;

public class FunctionReference
extends CallableReference
implements FunctionBase,
KFunction {
    private final int arity;

    public FunctionReference(int n) {
        this.arity = n;
    }

    public FunctionReference(int n, Object object) {
        super(object);
        this.arity = n;
    }

    @Override
    protected KCallable computeReflected() {
        return Reflection.function(this);
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof FunctionReference)) {
            if (!(object instanceof KFunction)) return false;
            return object.equals(this.compute());
        }
        object = (FunctionReference)object;
        if (this.getOwner() == null) {
            if (((CallableReference)object).getOwner() != null) return false;
        } else if (!this.getOwner().equals(((CallableReference)object).getOwner())) return false;
        if (!this.getName().equals(((CallableReference)object).getName())) return false;
        if (!this.getSignature().equals(((CallableReference)object).getSignature())) return false;
        if (!Intrinsics.areEqual(this.getBoundReceiver(), ((CallableReference)object).getBoundReceiver())) return false;
        return bl;
    }

    @Override
    public int getArity() {
        return this.arity;
    }

    @Override
    protected KFunction getReflected() {
        return (KFunction)super.getReflected();
    }

    public int hashCode() {
        int n;
        if (this.getOwner() == null) {
            n = 0;
            return (n + this.getName().hashCode()) * 31 + this.getSignature().hashCode();
        }
        n = this.getOwner().hashCode() * 31;
        return (n + this.getName().hashCode()) * 31 + this.getSignature().hashCode();
    }

    @Override
    public boolean isExternal() {
        return this.getReflected().isExternal();
    }

    @Override
    public boolean isInfix() {
        return this.getReflected().isInfix();
    }

    @Override
    public boolean isInline() {
        return this.getReflected().isInline();
    }

    @Override
    public boolean isOperator() {
        return this.getReflected().isOperator();
    }

    @Override
    public boolean isSuspend() {
        return this.getReflected().isSuspend();
    }

    public String toString() {
        Object object = this.compute();
        if (object != this) {
            return object.toString();
        }
        if ("<init>".equals(this.getName())) {
            return "constructor (Kotlin reflection is not available)";
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("function ");
        ((StringBuilder)object).append(this.getName());
        ((StringBuilder)object).append(" (Kotlin reflection is not available)");
        return ((StringBuilder)object).toString();
    }
}

