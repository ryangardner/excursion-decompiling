/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.base;

import com.google.common.base.AbstractIterator;
import com.google.common.base.CharMatcher;
import com.google.common.base.CommonMatcher;
import com.google.common.base.CommonPattern;
import com.google.common.base.JdkPattern;
import com.google.common.base.Joiner;
import com.google.common.base.Platform;
import com.google.common.base.Preconditions;
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
    private final Strategy strategy;
    private final CharMatcher trimmer;

    private Splitter(Strategy strategy) {
        this(strategy, false, CharMatcher.none(), Integer.MAX_VALUE);
    }

    private Splitter(Strategy strategy, boolean bl, CharMatcher charMatcher, int n) {
        this.strategy = strategy;
        this.omitEmptyStrings = bl;
        this.trimmer = charMatcher;
        this.limit = n;
    }

    public static Splitter fixedLength(final int n) {
        boolean bl = n > 0;
        Preconditions.checkArgument(bl, "The length may not be less than 1");
        return new Splitter(new Strategy(){

            public SplittingIterator iterator(Splitter splitter, CharSequence charSequence) {
                return new SplittingIterator(splitter, charSequence){

                    @Override
                    public int separatorEnd(int n) {
                        return n;
                    }

                    @Override
                    public int separatorStart(int n) {
                        if ((n += n) >= this.toSplit.length()) return -1;
                        return n;
                    }
                };
            }

        });
    }

    public static Splitter on(char c) {
        return Splitter.on(CharMatcher.is(c));
    }

    public static Splitter on(final CharMatcher charMatcher) {
        Preconditions.checkNotNull(charMatcher);
        return new Splitter(new Strategy(){

            public SplittingIterator iterator(Splitter splitter, CharSequence charSequence) {
                return new SplittingIterator(splitter, charSequence){

                    @Override
                    int separatorEnd(int n) {
                        return n + 1;
                    }

                    @Override
                    int separatorStart(int n) {
                        return charMatcher.indexIn(this.toSplit, n);
                    }
                };
            }

        });
    }

    private static Splitter on(final CommonPattern commonPattern) {
        Preconditions.checkArgument(commonPattern.matcher("").matches() ^ true, "The pattern may not match the empty string: %s", (Object)commonPattern);
        return new Splitter(new Strategy(){

            public SplittingIterator iterator(Splitter splitter, CharSequence charSequence) {
                return new SplittingIterator(splitter, charSequence, commonPattern.matcher(charSequence)){
                    final /* synthetic */ CommonMatcher val$matcher;
                    {
                        this.val$matcher = commonMatcher;
                        super(splitter, charSequence);
                    }

                    @Override
                    public int separatorEnd(int n) {
                        return this.val$matcher.end();
                    }

                    @Override
                    public int separatorStart(int n) {
                        if (!this.val$matcher.find(n)) return -1;
                        return this.val$matcher.start();
                    }
                };
            }

        });
    }

    public static Splitter on(final String string2) {
        boolean bl = string2.length() != 0;
        Preconditions.checkArgument(bl, "The separator may not be the empty string.");
        if (string2.length() != 1) return new Splitter(new Strategy(){

            public SplittingIterator iterator(Splitter splitter, CharSequence charSequence) {
                return new SplittingIterator(splitter, charSequence){

                    @Override
                    public int separatorEnd(int n) {
                        return n + string2.length();
                    }

                    @Override
                    public int separatorStart(int n) {
                        int n2 = string2.length();
                        int n3 = this.toSplit.length();
                        block0 : while (n <= n3 - n2) {
                            int n4 = 0;
                            while (n4 < n2) {
                                if (this.toSplit.charAt(n4 + n) != string2.charAt(n4)) {
                                    ++n;
                                    continue block0;
                                }
                                ++n4;
                            }
                            return n;
                            break;
                        }
                        return -1;
                    }
                };
            }

        });
        return Splitter.on(string2.charAt(0));
    }

    public static Splitter on(Pattern pattern) {
        return Splitter.on(new JdkPattern(pattern));
    }

    public static Splitter onPattern(String string2) {
        return Splitter.on(Platform.compilePattern(string2));
    }

    private Iterator<String> splittingIterator(CharSequence charSequence) {
        return this.strategy.iterator(this, charSequence);
    }

    public Splitter limit(int n) {
        boolean bl = n > 0;
        Preconditions.checkArgument(bl, "must be greater than zero: %s", n);
        return new Splitter(this.strategy, this.omitEmptyStrings, this.trimmer, n);
    }

    public Splitter omitEmptyStrings() {
        return new Splitter(this.strategy, true, this.trimmer, this.limit);
    }

    public Iterable<String> split(final CharSequence charSequence) {
        Preconditions.checkNotNull(charSequence);
        return new Iterable<String>(){

            @Override
            public Iterator<String> iterator() {
                return Splitter.this.splittingIterator(charSequence);
            }

            public String toString() {
                Joiner joiner = Joiner.on(", ");
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append('[');
                stringBuilder = joiner.appendTo(stringBuilder, (Iterable<?>)this);
                stringBuilder.append(']');
                return stringBuilder.toString();
            }
        };
    }

    public List<String> splitToList(CharSequence object) {
        Preconditions.checkNotNull(object);
        object = this.splittingIterator((CharSequence)object);
        ArrayList arrayList = new ArrayList();
        while (object.hasNext()) {
            arrayList.add(object.next());
        }
        return Collections.unmodifiableList(arrayList);
    }

    public Splitter trimResults() {
        return this.trimResults(CharMatcher.whitespace());
    }

    public Splitter trimResults(CharMatcher charMatcher) {
        Preconditions.checkNotNull(charMatcher);
        return new Splitter(this.strategy, this.omitEmptyStrings, charMatcher, this.limit);
    }

    public MapSplitter withKeyValueSeparator(char c) {
        return this.withKeyValueSeparator(Splitter.on(c));
    }

    public MapSplitter withKeyValueSeparator(Splitter splitter) {
        return new MapSplitter(this, splitter);
    }

    public MapSplitter withKeyValueSeparator(String string2) {
        return this.withKeyValueSeparator(Splitter.on(string2));
    }

    public static final class MapSplitter {
        private static final String INVALID_ENTRY_MESSAGE = "Chunk [%s] is not a valid entry";
        private final Splitter entrySplitter;
        private final Splitter outerSplitter;

        private MapSplitter(Splitter splitter, Splitter splitter2) {
            this.outerSplitter = splitter;
            this.entrySplitter = Preconditions.checkNotNull(splitter2);
        }

        public Map<String, String> split(CharSequence charSequence) {
            LinkedHashMap<CharSequence, String> linkedHashMap = new LinkedHashMap<CharSequence, String>();
            Iterator<String> iterator2 = this.outerSplitter.split(charSequence).iterator();
            while (iterator2.hasNext()) {
                String string2 = iterator2.next();
                Iterator iterator3 = this.entrySplitter.splittingIterator(string2);
                Preconditions.checkArgument(iterator3.hasNext(), INVALID_ENTRY_MESSAGE, (Object)string2);
                charSequence = (String)iterator3.next();
                Preconditions.checkArgument(linkedHashMap.containsKey(charSequence) ^ true, "Duplicate key [%s] found.", (Object)charSequence);
                Preconditions.checkArgument(iterator3.hasNext(), INVALID_ENTRY_MESSAGE, (Object)string2);
                linkedHashMap.put(charSequence, (String)iterator3.next());
                Preconditions.checkArgument(iterator3.hasNext() ^ true, INVALID_ENTRY_MESSAGE, (Object)string2);
            }
            return Collections.unmodifiableMap(linkedHashMap);
        }
    }

    private static abstract class SplittingIterator
    extends AbstractIterator<String> {
        int limit;
        int offset = 0;
        final boolean omitEmptyStrings;
        final CharSequence toSplit;
        final CharMatcher trimmer;

        protected SplittingIterator(Splitter splitter, CharSequence charSequence) {
            this.trimmer = splitter.trimmer;
            this.omitEmptyStrings = splitter.omitEmptyStrings;
            this.limit = splitter.limit;
            this.toSplit = charSequence;
        }

        @Override
        protected String computeNext() {
            int n;
            int n2;
            int n3;
            block9 : {
                n2 = this.offset;
                while ((n3 = this.offset) != -1) {
                    if ((n3 = this.separatorStart(n3)) == -1) {
                        n3 = this.toSplit.length();
                        this.offset = -1;
                    } else {
                        this.offset = this.separatorEnd(n3);
                    }
                    int n4 = this.offset;
                    n = n2;
                    if (n4 == n2) {
                        this.offset = n3 = n4 + 1;
                        if (n3 <= this.toSplit.length()) continue;
                        this.offset = -1;
                        continue;
                    }
                    do {
                        n2 = n3;
                        if (n >= n3) break;
                        n2 = n3;
                        if (!this.trimmer.matches(this.toSplit.charAt(n))) break;
                        ++n;
                    } while (true);
                    while (n2 > n && this.trimmer.matches(this.toSplit.charAt(n2 - 1))) {
                        --n2;
                    }
                    if (this.omitEmptyStrings && n == n2) {
                        n2 = this.offset;
                        continue;
                    }
                    break block9;
                }
                return (String)this.endOfData();
            }
            n3 = this.limit;
            if (n3 != 1) {
                this.limit = n3 - 1;
                n3 = n2;
                return this.toSplit.subSequence(n, n3).toString();
            }
            n2 = this.toSplit.length();
            this.offset = -1;
            do {
                n3 = n2;
                if (n2 <= n) return this.toSplit.subSequence(n, n3).toString();
                n3 = n2;
                if (!this.trimmer.matches(this.toSplit.charAt(n2 - 1))) return this.toSplit.subSequence(n, n3).toString();
                --n2;
            } while (true);
        }

        abstract int separatorEnd(int var1);

        abstract int separatorStart(int var1);
    }

    private static interface Strategy {
        public Iterator<String> iterator(Splitter var1, CharSequence var2);
    }

}

