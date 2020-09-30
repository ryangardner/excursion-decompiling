/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.tags.propagation;

import io.opencensus.tags.TagContext;
import io.opencensus.tags.propagation.TagContextDeserializationException;
import io.opencensus.tags.propagation.TagContextSerializationException;
import java.util.List;
import javax.annotation.Nullable;

public abstract class TagContextTextFormat {
    public abstract <C> TagContext extract(C var1, Getter<C> var2) throws TagContextDeserializationException;

    public abstract List<String> fields();

    public abstract <C> void inject(TagContext var1, C var2, Setter<C> var3) throws TagContextSerializationException;

    public static abstract class Getter<C> {
        @Nullable
        public abstract String get(C var1, String var2);
    }

    public static abstract class Setter<C> {
        public abstract void put(C var1, String var2, String var3);
    }

}

