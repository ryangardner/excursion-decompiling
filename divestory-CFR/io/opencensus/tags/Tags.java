/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.tags;

import io.opencensus.internal.Provider;
import io.opencensus.tags.NoopTags;
import io.opencensus.tags.Tagger;
import io.opencensus.tags.TaggingState;
import io.opencensus.tags.TagsComponent;
import io.opencensus.tags.propagation.TagPropagationComponent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;

public final class Tags {
    private static final Logger logger = Logger.getLogger(Tags.class.getName());
    private static final TagsComponent tagsComponent = Tags.loadTagsComponent(TagsComponent.class.getClassLoader());

    private Tags() {
    }

    public static TaggingState getState() {
        return tagsComponent.getState();
    }

    public static TagPropagationComponent getTagPropagationComponent() {
        return tagsComponent.getTagPropagationComponent();
    }

    public static Tagger getTagger() {
        return tagsComponent.getTagger();
    }

    static TagsComponent loadTagsComponent(@Nullable ClassLoader object) {
        try {
            return Provider.createInstance(Class.forName("io.opencensus.impl.tags.TagsComponentImpl", true, (ClassLoader)object), TagsComponent.class);
        }
        catch (ClassNotFoundException classNotFoundException) {
            logger.log(Level.FINE, "Couldn't load full implementation for TagsComponent, now trying to load lite implementation.", classNotFoundException);
            try {
                return Provider.createInstance(Class.forName("io.opencensus.impllite.tags.TagsComponentImplLite", true, (ClassLoader)object), TagsComponent.class);
            }
            catch (ClassNotFoundException classNotFoundException2) {
                logger.log(Level.FINE, "Couldn't load lite implementation for TagsComponent, now using default implementation for TagsComponent.", classNotFoundException2);
                return NoopTags.newNoopTagsComponent();
            }
        }
    }

    @Deprecated
    public static void setState(TaggingState taggingState) {
        tagsComponent.setState(taggingState);
    }
}

