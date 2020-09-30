package com.google.common.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public final class Splitter {
   private final int limit;
   private final boolean omitEmptyStrings;
   private final Splitter.Strategy strategy;
   private final CharMatcher trimmer;

   private Splitter(Splitter.Strategy var1) {
      this(var1, false, CharMatcher.none(), Integer.MAX_VALUE);
   }

   private Splitter(Splitter.Strategy var1, boolean var2, CharMatcher var3, int var4) {
      this.strategy = var1;
      this.omitEmptyStrings = var2;
      this.trimmer = var3;
      this.limit = var4;
   }

   public static Splitter fixedLength(final int var0) {
      boolean var1;
      if (var0 > 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      Preconditions.checkArgument(var1, "The length may not be less than 1");
      return new Splitter(new Splitter.Strategy() {
         public Splitter.SplittingIterator iterator(Splitter var1, CharSequence var2) {
            return new Splitter.SplittingIterator(var1, var2) {
               public int separatorEnd(int var1) {
                  return var1;
               }

               public int separatorStart(int var1) {
                  var1 += var0;
                  if (var1 >= this.toSplit.length()) {
                     var1 = -1;
                  }

                  return var1;
               }
            };
         }
      });
   }

   public static Splitter on(char var0) {
      return on(CharMatcher.is(var0));
   }

   public static Splitter on(final CharMatcher var0) {
      Preconditions.checkNotNull(var0);
      return new Splitter(new Splitter.Strategy() {
         public Splitter.SplittingIterator iterator(Splitter var1, CharSequence var2) {
            return new Splitter.SplittingIterator(var1, var2) {
               int separatorEnd(int var1) {
                  return var1 + 1;
               }

               int separatorStart(int var1) {
                  return var0.indexIn(this.toSplit, var1);
               }
            };
         }
      });
   }

   private static Splitter on(final CommonPattern var0) {
      Preconditions.checkArgument(var0.matcher("").matches() ^ true, "The pattern may not match the empty string: %s", (Object)var0);
      return new Splitter(new Splitter.Strategy() {
         public Splitter.SplittingIterator iterator(Splitter var1, CharSequence var2) {
            return new Splitter.SplittingIterator(var1, var2, var0.matcher(var2)) {
               // $FF: synthetic field
               final CommonMatcher val$matcher;

               {
                  this.val$matcher = var4;
               }

               public int separatorEnd(int var1) {
                  return this.val$matcher.end();
               }

               public int separatorStart(int var1) {
                  if (this.val$matcher.find(var1)) {
                     var1 = this.val$matcher.start();
                  } else {
                     var1 = -1;
                  }

                  return var1;
               }
            };
         }
      });
   }

   public static Splitter on(final String var0) {
      boolean var1;
      if (var0.length() != 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      Preconditions.checkArgument(var1, "The separator may not be the empty string.");
      return var0.length() == 1 ? on(var0.charAt(0)) : new Splitter(new Splitter.Strategy() {
         public Splitter.SplittingIterator iterator(Splitter var1, CharSequence var2) {
            return new Splitter.SplittingIterator(var1, var2) {
               public int separatorEnd(int var1) {
                  return var1 + var0.length();
               }

               public int separatorStart(int var1) {
                  int var2 = var0.length();

                  label23:
                  for(int var3 = this.toSplit.length(); var1 <= var3 - var2; ++var1) {
                     for(int var4 = 0; var4 < var2; ++var4) {
                        if (this.toSplit.charAt(var4 + var1) != var0.charAt(var4)) {
                           continue label23;
                        }
                     }

                     return var1;
                  }

                  return -1;
               }
            };
         }
      });
   }

   public static Splitter on(Pattern var0) {
      return on((CommonPattern)(new JdkPattern(var0)));
   }

   public static Splitter onPattern(String var0) {
      return on(Platform.compilePattern(var0));
   }

   private Iterator<String> splittingIterator(CharSequence var1) {
      return this.strategy.iterator(this, var1);
   }

   public Splitter limit(int var1) {
      boolean var2;
      if (var1 > 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      Preconditions.checkArgument(var2, "must be greater than zero: %s", var1);
      return new Splitter(this.strategy, this.omitEmptyStrings, this.trimmer, var1);
   }

   public Splitter omitEmptyStrings() {
      return new Splitter(this.strategy, true, this.trimmer, this.limit);
   }

   public Iterable<String> split(final CharSequence var1) {
      Preconditions.checkNotNull(var1);
      return new Iterable<String>() {
         public Iterator<String> iterator() {
            return Splitter.this.splittingIterator(var1);
         }

         public String toString() {
            Joiner var1x = Joiner.on(", ");
            StringBuilder var2 = new StringBuilder();
            var2.append('[');
            var2 = var1x.appendTo((StringBuilder)var2, (Iterable)this);
            var2.append(']');
            return var2.toString();
         }
      };
   }

   public List<String> splitToList(CharSequence var1) {
      Preconditions.checkNotNull(var1);
      Iterator var3 = this.splittingIterator(var1);
      ArrayList var2 = new ArrayList();

      while(var3.hasNext()) {
         var2.add(var3.next());
      }

      return Collections.unmodifiableList(var2);
   }

   public Splitter trimResults() {
      return this.trimResults(CharMatcher.whitespace());
   }

   public Splitter trimResults(CharMatcher var1) {
      Preconditions.checkNotNull(var1);
      return new Splitter(this.strategy, this.omitEmptyStrings, var1, this.limit);
   }

   public Splitter.MapSplitter withKeyValueSeparator(char var1) {
      return this.withKeyValueSeparator(on(var1));
   }

   public Splitter.MapSplitter withKeyValueSeparator(Splitter var1) {
      return new Splitter.MapSplitter(this, var1);
   }

   public Splitter.MapSplitter withKeyValueSeparator(String var1) {
      return this.withKeyValueSeparator(on(var1));
   }

   public static final class MapSplitter {
      private static final String INVALID_ENTRY_MESSAGE = "Chunk [%s] is not a valid entry";
      private final Splitter entrySplitter;
      private final Splitter outerSplitter;

      private MapSplitter(Splitter var1, Splitter var2) {
         this.outerSplitter = var1;
         this.entrySplitter = (Splitter)Preconditions.checkNotNull(var2);
      }

      // $FF: synthetic method
      MapSplitter(Splitter var1, Splitter var2, Object var3) {
         this(var1, var2);
      }

      public Map<String, String> split(CharSequence var1) {
         LinkedHashMap var2 = new LinkedHashMap();
         Iterator var3 = this.outerSplitter.split(var1).iterator();

         while(var3.hasNext()) {
            String var4 = (String)var3.next();
            Iterator var5 = this.entrySplitter.splittingIterator(var4);
            Preconditions.checkArgument(var5.hasNext(), "Chunk [%s] is not a valid entry", (Object)var4);
            String var6 = (String)var5.next();
            Preconditions.checkArgument(var2.containsKey(var6) ^ true, "Duplicate key [%s] found.", (Object)var6);
            Preconditions.checkArgument(var5.hasNext(), "Chunk [%s] is not a valid entry", (Object)var4);
            var2.put(var6, (String)var5.next());
            Preconditions.checkArgument(var5.hasNext() ^ true, "Chunk [%s] is not a valid entry", (Object)var4);
         }

         return Collections.unmodifiableMap(var2);
      }
   }

   private abstract static class SplittingIterator extends AbstractIterator<String> {
      int limit;
      int offset = 0;
      final boolean omitEmptyStrings;
      final CharSequence toSplit;
      final CharMatcher trimmer;

      protected SplittingIterator(Splitter var1, CharSequence var2) {
         this.trimmer = var1.trimmer;
         this.omitEmptyStrings = var1.omitEmptyStrings;
         this.limit = var1.limit;
         this.toSplit = var2;
      }

      protected String computeNext() {
         int var1 = this.offset;

         while(true) {
            while(true) {
               int var2 = this.offset;
               if (var2 == -1) {
                  return (String)this.endOfData();
               }

               var2 = this.separatorStart(var2);
               if (var2 == -1) {
                  var2 = this.toSplit.length();
                  this.offset = -1;
               } else {
                  this.offset = this.separatorEnd(var2);
               }

               int var3 = this.offset;
               int var4 = var1;
               if (var3 != var1) {
                  while(true) {
                     var1 = var2;
                     if (var4 >= var2) {
                        break;
                     }

                     var1 = var2;
                     if (!this.trimmer.matches(this.toSplit.charAt(var4))) {
                        break;
                     }

                     ++var4;
                  }

                  while(var1 > var4 && this.trimmer.matches(this.toSplit.charAt(var1 - 1))) {
                     --var1;
                  }

                  if (!this.omitEmptyStrings || var4 != var1) {
                     var2 = this.limit;
                     if (var2 == 1) {
                        var1 = this.toSplit.length();
                        this.offset = -1;

                        while(true) {
                           var2 = var1;
                           if (var1 <= var4) {
                              break;
                           }

                           var2 = var1;
                           if (!this.trimmer.matches(this.toSplit.charAt(var1 - 1))) {
                              break;
                           }

                           --var1;
                        }
                     } else {
                        this.limit = var2 - 1;
                        var2 = var1;
                     }

                     return this.toSplit.subSequence(var4, var2).toString();
                  }

                  var1 = this.offset;
               } else {
                  var2 = var3 + 1;
                  this.offset = var2;
                  if (var2 > this.toSplit.length()) {
                     this.offset = -1;
                  }
               }
            }
         }
      }

      abstract int separatorEnd(int var1);

      abstract int separatorStart(int var1);
   }

   private interface Strategy {
      Iterator<String> iterator(Splitter var1, CharSequence var2);
   }
}
