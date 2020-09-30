/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.text.Spannable
 *  android.text.SpannableString
 *  android.text.method.LinkMovementMethod
 *  android.text.method.MovementMethod
 *  android.text.style.URLSpan
 *  android.text.util.Linkify
 *  android.text.util.Linkify$MatchFilter
 *  android.text.util.Linkify$TransformFilter
 *  android.webkit.WebView
 *  android.widget.TextView
 */
package androidx.core.text.util;

import android.os.Build;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.text.style.URLSpan;
import android.text.util.Linkify;
import android.webkit.WebView;
import android.widget.TextView;
import androidx.core.text.util.FindAddress;
import androidx.core.util.PatternsCompat;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class LinkifyCompat {
    private static final Comparator<LinkSpec> COMPARATOR;
    private static final String[] EMPTY_STRING;

    static {
        EMPTY_STRING = new String[0];
        COMPARATOR = new Comparator<LinkSpec>(){

            @Override
            public int compare(LinkSpec linkSpec, LinkSpec linkSpec2) {
                if (linkSpec.start < linkSpec2.start) {
                    return -1;
                }
                if (linkSpec.start > linkSpec2.start) {
                    return 1;
                }
                if (linkSpec.end < linkSpec2.end) {
                    return 1;
                }
                if (linkSpec.end <= linkSpec2.end) return 0;
                return -1;
            }
        };
    }

    private LinkifyCompat() {
    }

    private static void addLinkMovementMethod(TextView textView) {
        if (textView.getMovementMethod() instanceof LinkMovementMethod) return;
        if (!textView.getLinksClickable()) return;
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public static void addLinks(TextView textView, Pattern pattern, String string2) {
        if (LinkifyCompat.shouldAddLinksFallbackToFramework()) {
            Linkify.addLinks((TextView)textView, (Pattern)pattern, (String)string2);
            return;
        }
        LinkifyCompat.addLinks(textView, pattern, string2, null, null, null);
    }

    public static void addLinks(TextView textView, Pattern pattern, String string2, Linkify.MatchFilter matchFilter, Linkify.TransformFilter transformFilter) {
        if (LinkifyCompat.shouldAddLinksFallbackToFramework()) {
            Linkify.addLinks((TextView)textView, (Pattern)pattern, (String)string2, (Linkify.MatchFilter)matchFilter, (Linkify.TransformFilter)transformFilter);
            return;
        }
        LinkifyCompat.addLinks(textView, pattern, string2, null, matchFilter, transformFilter);
    }

    public static void addLinks(TextView textView, Pattern pattern, String string2, String[] arrstring, Linkify.MatchFilter matchFilter, Linkify.TransformFilter transformFilter) {
        if (LinkifyCompat.shouldAddLinksFallbackToFramework()) {
            Linkify.addLinks((TextView)textView, (Pattern)pattern, (String)string2, (String[])arrstring, (Linkify.MatchFilter)matchFilter, (Linkify.TransformFilter)transformFilter);
            return;
        }
        SpannableString spannableString = SpannableString.valueOf((CharSequence)textView.getText());
        if (!LinkifyCompat.addLinks((Spannable)spannableString, pattern, string2, arrstring, matchFilter, transformFilter)) return;
        textView.setText((CharSequence)spannableString);
        LinkifyCompat.addLinkMovementMethod(textView);
    }

    public static boolean addLinks(Spannable spannable, int n) {
        Object object;
        if (LinkifyCompat.shouldAddLinksFallbackToFramework()) {
            return Linkify.addLinks((Spannable)spannable, (int)n);
        }
        if (n == 0) {
            return false;
        }
        Object object2 = (URLSpan[])spannable.getSpans(0, spannable.length(), URLSpan.class);
        for (int i = ((URLSpan[])object2).length - 1; i >= 0; --i) {
            spannable.removeSpan((Object)object2[i]);
        }
        if ((n & 4) != 0) {
            Linkify.addLinks((Spannable)spannable, (int)4);
        }
        object2 = new ArrayList();
        if ((n & 1) != 0) {
            object = PatternsCompat.AUTOLINK_WEB_URL;
            Linkify.MatchFilter matchFilter = Linkify.sUrlMatchFilter;
            LinkifyCompat.gatherLinks((ArrayList<LinkSpec>)object2, spannable, (Pattern)object, new String[]{"http://", "https://", "rtsp://"}, matchFilter, null);
        }
        if ((n & 2) != 0) {
            LinkifyCompat.gatherLinks((ArrayList<LinkSpec>)object2, spannable, PatternsCompat.AUTOLINK_EMAIL_ADDRESS, new String[]{"mailto:"}, null, null);
        }
        if ((n & 8) != 0) {
            LinkifyCompat.gatherMapLinks((ArrayList<LinkSpec>)object2, spannable);
        }
        LinkifyCompat.pruneOverlaps((ArrayList<LinkSpec>)object2, spannable);
        if (((ArrayList)object2).size() == 0) {
            return false;
        }
        object = ((ArrayList)object2).iterator();
        while (object.hasNext()) {
            object2 = (LinkSpec)object.next();
            if (((LinkSpec)object2).frameworkAddedSpan != null) continue;
            LinkifyCompat.applyLink(((LinkSpec)object2).url, ((LinkSpec)object2).start, ((LinkSpec)object2).end, spannable);
        }
        return true;
    }

    public static boolean addLinks(Spannable spannable, Pattern pattern, String string2) {
        if (!LinkifyCompat.shouldAddLinksFallbackToFramework()) return LinkifyCompat.addLinks(spannable, pattern, string2, null, null, null);
        return Linkify.addLinks((Spannable)spannable, (Pattern)pattern, (String)string2);
    }

    public static boolean addLinks(Spannable spannable, Pattern pattern, String string2, Linkify.MatchFilter matchFilter, Linkify.TransformFilter transformFilter) {
        if (!LinkifyCompat.shouldAddLinksFallbackToFramework()) return LinkifyCompat.addLinks(spannable, pattern, string2, null, matchFilter, transformFilter);
        return Linkify.addLinks((Spannable)spannable, (Pattern)pattern, (String)string2, (Linkify.MatchFilter)matchFilter, (Linkify.TransformFilter)transformFilter);
    }

    public static boolean addLinks(Spannable spannable, Pattern object, String arrstring, String[] object2, Linkify.MatchFilter matchFilter, Linkify.TransformFilter transformFilter) {
        String[] arrstring2;
        block8 : {
            block7 : {
                if (LinkifyCompat.shouldAddLinksFallbackToFramework()) {
                    return Linkify.addLinks((Spannable)spannable, (Pattern)object, (String)arrstring, (String[])object2, (Linkify.MatchFilter)matchFilter, (Linkify.TransformFilter)transformFilter);
                }
                arrstring2 = arrstring;
                if (arrstring == null) {
                    arrstring2 = "";
                }
                if (object2 == null) break block7;
                arrstring = object2;
                if (((String[])object2).length >= 1) break block8;
            }
            arrstring = EMPTY_STRING;
        }
        String[] arrstring3 = new String[arrstring.length + 1];
        arrstring3[0] = arrstring2.toLowerCase(Locale.ROOT);
        int n = 0;
        while (n < arrstring.length) {
            object2 = arrstring[n];
            object2 = object2 == null ? "" : object2.toLowerCase(Locale.ROOT);
            arrstring3[++n] = object2;
        }
        object = ((Pattern)object).matcher((CharSequence)spannable);
        boolean bl = false;
        while (((Matcher)object).find()) {
            n = ((Matcher)object).start();
            int n2 = ((Matcher)object).end();
            boolean bl2 = matchFilter != null ? matchFilter.acceptMatch((CharSequence)spannable, n, n2) : true;
            if (!bl2) continue;
            LinkifyCompat.applyLink(LinkifyCompat.makeUrl(((Matcher)object).group(0), arrstring3, (Matcher)object, transformFilter), n, n2, spannable);
            bl = true;
        }
        return bl;
    }

    public static boolean addLinks(TextView textView, int n) {
        if (LinkifyCompat.shouldAddLinksFallbackToFramework()) {
            return Linkify.addLinks((TextView)textView, (int)n);
        }
        if (n == 0) {
            return false;
        }
        CharSequence charSequence = textView.getText();
        if (charSequence instanceof Spannable) {
            if (!LinkifyCompat.addLinks((Spannable)charSequence, n)) return false;
            LinkifyCompat.addLinkMovementMethod(textView);
            return true;
        }
        if (!LinkifyCompat.addLinks((Spannable)(charSequence = SpannableString.valueOf((CharSequence)charSequence)), n)) return false;
        LinkifyCompat.addLinkMovementMethod(textView);
        textView.setText(charSequence);
        return true;
    }

    private static void applyLink(String string2, int n, int n2, Spannable spannable) {
        spannable.setSpan((Object)new URLSpan(string2), n, n2, 33);
    }

    private static String findAddress(String string2) {
        if (Build.VERSION.SDK_INT < 28) return FindAddress.findAddress(string2);
        return WebView.findAddress((String)string2);
    }

    private static void gatherLinks(ArrayList<LinkSpec> arrayList, Spannable spannable, Pattern object, String[] arrstring, Linkify.MatchFilter matchFilter, Linkify.TransformFilter transformFilter) {
        object = ((Pattern)object).matcher((CharSequence)spannable);
        while (((Matcher)object).find()) {
            int n = ((Matcher)object).start();
            int n2 = ((Matcher)object).end();
            if (matchFilter != null && !matchFilter.acceptMatch((CharSequence)spannable, n, n2)) continue;
            LinkSpec linkSpec = new LinkSpec();
            linkSpec.url = LinkifyCompat.makeUrl(((Matcher)object).group(0), arrstring, (Matcher)object, transformFilter);
            linkSpec.start = n;
            linkSpec.end = n2;
            arrayList.add(linkSpec);
        }
    }

    private static void gatherMapLinks(ArrayList<LinkSpec> arrayList, Spannable object) {
        object = object.toString();
        int n = 0;
        try {
            CharSequence charSequence;
            while ((charSequence = LinkifyCompat.findAddress((String)object)) != null) {
                String string2;
                int n2 = ((String)object).indexOf((String)charSequence);
                if (n2 < 0) {
                    return;
                }
                LinkSpec linkSpec = new LinkSpec();
                int n3 = ((String)charSequence).length() + n2;
                linkSpec.start = n2 + n;
                linkSpec.end = n += n3;
                object = ((String)object).substring(n3);
                try {
                    string2 = URLEncoder.encode((String)charSequence, "UTF-8");
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("geo:0,0?q=");
                }
                catch (UnsupportedEncodingException unsupportedEncodingException) {
                    continue;
                }
                ((StringBuilder)charSequence).append(string2);
                linkSpec.url = ((StringBuilder)charSequence).toString();
                arrayList.add(linkSpec);
            }
            return;
        }
        catch (UnsupportedOperationException unsupportedOperationException) {
            return;
        }
    }

    private static String makeUrl(String charSequence, String[] arrstring, Matcher object, Linkify.TransformFilter transformFilter) {
        int n;
        block4 : {
            String string2 = charSequence;
            if (transformFilter != null) {
                string2 = transformFilter.transformUrl((Matcher)object, (String)charSequence);
            }
            int n2 = 0;
            do {
                n = arrstring.length;
                int n3 = 1;
                if (n2 >= n) break;
                if (string2.regionMatches(true, 0, arrstring[n2], 0, arrstring[n2].length())) {
                    n = n3;
                    charSequence = string2;
                    if (!string2.regionMatches(false, 0, arrstring[n2], 0, arrstring[n2].length())) {
                        charSequence = new StringBuilder();
                        ((StringBuilder)charSequence).append(arrstring[n2]);
                        ((StringBuilder)charSequence).append(string2.substring(arrstring[n2].length()));
                        charSequence = ((StringBuilder)charSequence).toString();
                        n = n3;
                    }
                    break block4;
                }
                ++n2;
            } while (true);
            n = 0;
            charSequence = string2;
        }
        object = charSequence;
        if (n != 0) return object;
        object = charSequence;
        if (arrstring.length <= 0) return object;
        object = new StringBuilder();
        ((StringBuilder)object).append(arrstring[0]);
        ((StringBuilder)object).append((String)charSequence);
        return ((StringBuilder)object).toString();
    }

    private static void pruneOverlaps(ArrayList<LinkSpec> arrayList, Spannable spannable) {
        LinkSpec linkSpec;
        int n = spannable.length();
        int n2 = 0;
        Object object = (URLSpan[])spannable.getSpans(0, n, URLSpan.class);
        for (n = 0; n < ((URLSpan[])object).length; ++n) {
            linkSpec = new LinkSpec();
            linkSpec.frameworkAddedSpan = object[n];
            linkSpec.start = spannable.getSpanStart((Object)object[n]);
            linkSpec.end = spannable.getSpanEnd((Object)object[n]);
            arrayList.add(linkSpec);
        }
        Collections.sort(arrayList, COMPARATOR);
        int n3 = arrayList.size();
        n = n2;
        while (n < n3 - 1) {
            object = arrayList.get(n);
            int n4 = n + 1;
            linkSpec = arrayList.get(n4);
            if (object.start <= linkSpec.start && object.end > linkSpec.start && (n2 = linkSpec.end <= object.end || object.end - object.start > linkSpec.end - linkSpec.start ? n4 : (object.end - object.start < linkSpec.end - linkSpec.start ? n : -1)) != -1) {
                linkSpec = arrayList.get((int)n2).frameworkAddedSpan;
                if (linkSpec != null) {
                    spannable.removeSpan((Object)linkSpec);
                }
                arrayList.remove(n2);
                --n3;
                continue;
            }
            n = n4;
        }
    }

    private static boolean shouldAddLinksFallbackToFramework() {
        if (Build.VERSION.SDK_INT < 28) return false;
        return true;
    }

    private static class LinkSpec {
        int end;
        URLSpan frameworkAddedSpan;
        int start;
        String url;

        LinkSpec() {
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface LinkifyMask {
    }

}

