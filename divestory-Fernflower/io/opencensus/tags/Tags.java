package io.opencensus.tags;

import io.opencensus.internal.Provider;
import io.opencensus.tags.propagation.TagPropagationComponent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;

public final class Tags {
   private static final Logger logger = Logger.getLogger(Tags.class.getName());
   private static final TagsComponent tagsComponent = loadTagsComponent(TagsComponent.class.getClassLoader());

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

   static TagsComponent loadTagsComponent(@Nullable ClassLoader var0) {
      try {
         TagsComponent var1 = (TagsComponent)Provider.createInstance(Class.forName("io.opencensus.impl.tags.TagsComponentImpl", true, var0), TagsComponent.class);
         return var1;
      } catch (ClassNotFoundException var3) {
         logger.log(Level.FINE, "Couldn't load full implementation for TagsComponent, now trying to load lite implementation.", var3);

         try {
            TagsComponent var4 = (TagsComponent)Provider.createInstance(Class.forName("io.opencensus.impllite.tags.TagsComponentImplLite", true, var0), TagsComponent.class);
            return var4;
         } catch (ClassNotFoundException var2) {
            logger.log(Level.FINE, "Couldn't load lite implementation for TagsComponent, now using default implementation for TagsComponent.", var2);
            return NoopTags.newNoopTagsComponent();
         }
      }
   }

   @Deprecated
   public static void setState(TaggingState var0) {
      tagsComponent.setState(var0);
   }
}
