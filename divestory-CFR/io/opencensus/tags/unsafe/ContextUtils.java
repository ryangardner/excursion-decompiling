/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.tags.unsafe;

import io.grpc.Context;
import io.opencensus.internal.Utils;
import io.opencensus.tags.Tag;
import io.opencensus.tags.TagContext;
import java.util.Collections;
import java.util.Iterator;
import javax.annotation.Nullable;

public final class ContextUtils {
    private static final TagContext EMPTY_TAG_CONTEXT;
    private static final Context.Key<TagContext> TAG_CONTEXT_KEY;

    static {
        EmptyTagContext emptyTagContext = new EmptyTagContext();
        EMPTY_TAG_CONTEXT = emptyTagContext;
        TAG_CONTEXT_KEY = Context.keyWithDefault("opencensus-tag-context-key", emptyTagContext);
    }

    private ContextUtils() {
    }

    public static TagContext getValue(Context object) {
        TagContext tagContext = TAG_CONTEXT_KEY.get((Context)object);
        object = tagContext;
        if (tagContext != null) return object;
        return EMPTY_TAG_CONTEXT;
    }

    public static Context withValue(Context context, @Nullable TagContext tagContext) {
        return Utils.checkNotNull(context, "context").withValue(TAG_CONTEXT_KEY, tagContext);
    }

    private static final class EmptyTagContext
    extends TagContext {
        private EmptyTagContext() {
        }

        @Override
        protected Iterator<Tag> getIterator() {
            return Collections.emptySet().iterator();
        }
    }

}

