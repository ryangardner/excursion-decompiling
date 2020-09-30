package io.opencensus.trace;

import io.opencensus.internal.Utils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;

public abstract class Tracestate {
   private static final int KEY_MAX_SIZE = 256;
   private static final int MAX_KEY_VALUE_PAIRS = 32;
   private static final int VALUE_MAX_SIZE = 256;

   Tracestate() {
   }

   public static Tracestate.Builder builder() {
      return new Tracestate.Builder(Tracestate.Builder.EMPTY);
   }

   private static Tracestate create(List<Tracestate.Entry> var0) {
      boolean var1;
      if (var0.size() <= 32) {
         var1 = true;
      } else {
         var1 = false;
      }

      Utils.checkState(var1, "Invalid size");
      return new AutoValue_Tracestate(Collections.unmodifiableList(var0));
   }

   private static boolean validateKey(String var0) {
      if (var0.length() <= 256 && !var0.isEmpty() && var0.charAt(0) >= 'a' && var0.charAt(0) <= 'z') {
         for(int var1 = 1; var1 < var0.length(); ++var1) {
            char var2 = var0.charAt(var1);
            if ((var2 < 'a' || var2 > 'z') && (var2 < '0' || var2 > '9') && var2 != '_' && var2 != '-' && var2 != '*' && var2 != '/') {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   private static boolean validateValue(String var0) {
      if (var0.length() <= 256 && var0.charAt(var0.length() - 1) != ' ') {
         for(int var1 = 0; var1 < var0.length(); ++var1) {
            char var2 = var0.charAt(var1);
            if (var2 == ',' || var2 == '=' || var2 < ' ' || var2 > '~') {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   @Nullable
   public String get(String var1) {
      Iterator var2 = this.getEntries().iterator();

      Tracestate.Entry var3;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         var3 = (Tracestate.Entry)var2.next();
      } while(!var3.getKey().equals(var1));

      return var3.getValue();
   }

   public abstract List<Tracestate.Entry> getEntries();

   public Tracestate.Builder toBuilder() {
      return new Tracestate.Builder(this);
   }

   public static final class Builder {
      private static final Tracestate EMPTY = Tracestate.create(Collections.emptyList());
      @Nullable
      private ArrayList<Tracestate.Entry> entries;
      private final Tracestate parent;

      private Builder(Tracestate var1) {
         Utils.checkNotNull(var1, "parent");
         this.parent = var1;
         this.entries = null;
      }

      // $FF: synthetic method
      Builder(Tracestate var1, Object var2) {
         this(var1);
      }

      public Tracestate build() {
         ArrayList var1 = this.entries;
         return var1 == null ? this.parent : Tracestate.create(var1);
      }

      public Tracestate.Builder remove(String var1) {
         Utils.checkNotNull(var1, "key");
         if (this.entries == null) {
            this.entries = new ArrayList(this.parent.getEntries());
         }

         for(int var2 = 0; var2 < this.entries.size(); ++var2) {
            if (((Tracestate.Entry)this.entries.get(var2)).getKey().equals(var1)) {
               this.entries.remove(var2);
               break;
            }
         }

         return this;
      }

      public Tracestate.Builder set(String var1, String var2) {
         Tracestate.Entry var4 = Tracestate.Entry.create(var1, var2);
         if (this.entries == null) {
            this.entries = new ArrayList(this.parent.getEntries());
         }

         for(int var3 = 0; var3 < this.entries.size(); ++var3) {
            if (((Tracestate.Entry)this.entries.get(var3)).getKey().equals(var4.getKey())) {
               this.entries.remove(var3);
               break;
            }
         }

         this.entries.add(0, var4);
         return this;
      }
   }

   public abstract static class Entry {
      Entry() {
      }

      public static Tracestate.Entry create(String var0, String var1) {
         Utils.checkNotNull(var0, "key");
         Utils.checkNotNull(var1, "value");
         Utils.checkArgument(Tracestate.validateKey(var0), "Invalid key %s", var0);
         Utils.checkArgument(Tracestate.validateValue(var1), "Invalid value %s", var1);
         return new AutoValue_Tracestate_Entry(var0, var1);
      }

      public abstract String getKey();

      public abstract String getValue();
   }
}
