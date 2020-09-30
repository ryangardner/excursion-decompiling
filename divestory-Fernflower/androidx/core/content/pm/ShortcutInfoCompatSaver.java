package androidx.core.content.pm;

import java.util.ArrayList;
import java.util.List;

public abstract class ShortcutInfoCompatSaver<T> {
   public abstract T addShortcuts(List<ShortcutInfoCompat> var1);

   public List<ShortcutInfoCompat> getShortcuts() throws Exception {
      return new ArrayList();
   }

   public abstract T removeAllShortcuts();

   public abstract T removeShortcuts(List<String> var1);

   public static class NoopImpl extends ShortcutInfoCompatSaver<Void> {
      public Void addShortcuts(List<ShortcutInfoCompat> var1) {
         return null;
      }

      public Void removeAllShortcuts() {
         return null;
      }

      public Void removeShortcuts(List<String> var1) {
         return null;
      }
   }
}
