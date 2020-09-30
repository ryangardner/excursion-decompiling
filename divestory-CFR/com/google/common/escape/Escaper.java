/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.escape;

import com.google.common.base.Function;
import com.google.errorprone.annotations.DoNotMock;

@DoNotMock(value="Use Escapers.nullEscaper() or another methods from the *Escapers classes")
public abstract class Escaper {
    private final Function<String, String> asFunction = new Function<String, String>(){

        @Override
        public String apply(String string2) {
            return Escaper.this.escape(string2);
        }
    };

    protected Escaper() {
    }

    public final Function<String, String> asFunction() {
        return this.asFunction;
    }

    public abstract String escape(String var1);

}

