/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.Rect
 *  android.view.View
 *  android.view.ViewGroup
 */
package androidx.fragment.app;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.os.CancellationSignal;
import androidx.core.view.OneShotPreDrawListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewGroupCompat;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class FragmentTransitionImpl {
    protected static void bfsAddViewChildren(List<View> list, View view) {
        int n = list.size();
        if (FragmentTransitionImpl.containedBeforeIndex(list, view, n)) {
            return;
        }
        list.add(view);
        int n2 = n;
        while (n2 < list.size()) {
            view = list.get(n2);
            if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup)view;
                int n3 = viewGroup.getChildCount();
                for (int i = 0; i < n3; ++i) {
                    view = viewGroup.getChildAt(i);
                    if (FragmentTransitionImpl.containedBeforeIndex(list, view, n)) continue;
                    list.add(view);
                }
            }
            ++n2;
        }
    }

    private static boolean containedBeforeIndex(List<View> list, View view, int n) {
        int n2 = 0;
        while (n2 < n) {
            if (list.get(n2) == view) {
                return true;
            }
            ++n2;
        }
        return false;
    }

    static String findKeyForValue(Map<String, String> object, String string2) {
        Iterator<Map.Entry<String, String>> iterator2 = object.entrySet().iterator();
        do {
            if (!iterator2.hasNext()) return null;
        } while (!string2.equals((object = iterator2.next()).getValue()));
        return (String)object.getKey();
    }

    protected static boolean isNullOrEmpty(List list) {
        if (list == null) return true;
        if (list.isEmpty()) return true;
        return false;
    }

    public abstract void addTarget(Object var1, View var2);

    public abstract void addTargets(Object var1, ArrayList<View> var2);

    public abstract void beginDelayedTransition(ViewGroup var1, Object var2);

    public abstract boolean canHandle(Object var1);

    void captureTransitioningViews(ArrayList<View> arrayList, View view) {
        if (view.getVisibility() != 0) return;
        if (!(view instanceof ViewGroup)) {
            arrayList.add(view);
            return;
        }
        if (ViewGroupCompat.isTransitionGroup((ViewGroup)(view = (ViewGroup)view))) {
            arrayList.add(view);
            return;
        }
        int n = view.getChildCount();
        int n2 = 0;
        while (n2 < n) {
            this.captureTransitioningViews(arrayList, view.getChildAt(n2));
            ++n2;
        }
    }

    public abstract Object cloneTransition(Object var1);

    void findNamedViews(Map<String, View> map, View view) {
        if (view.getVisibility() != 0) return;
        String string2 = ViewCompat.getTransitionName(view);
        if (string2 != null) {
            map.put(string2, view);
        }
        if (!(view instanceof ViewGroup)) return;
        view = (ViewGroup)view;
        int n = view.getChildCount();
        int n2 = 0;
        while (n2 < n) {
            this.findNamedViews(map, view.getChildAt(n2));
            ++n2;
        }
    }

    protected void getBoundsOnScreen(View view, Rect rect) {
        int[] arrn = new int[2];
        view.getLocationOnScreen(arrn);
        rect.set(arrn[0], arrn[1], arrn[0] + view.getWidth(), arrn[1] + view.getHeight());
    }

    public abstract Object mergeTransitionsInSequence(Object var1, Object var2, Object var3);

    public abstract Object mergeTransitionsTogether(Object var1, Object var2, Object var3);

    ArrayList<String> prepareSetNameOverridesReordered(ArrayList<View> arrayList) {
        ArrayList<String> arrayList2 = new ArrayList<String>();
        int n = arrayList.size();
        int n2 = 0;
        while (n2 < n) {
            View view = arrayList.get(n2);
            arrayList2.add(ViewCompat.getTransitionName(view));
            ViewCompat.setTransitionName(view, null);
            ++n2;
        }
        return arrayList2;
    }

    public abstract void removeTarget(Object var1, View var2);

    public abstract void replaceTargets(Object var1, ArrayList<View> var2, ArrayList<View> var3);

    public abstract void scheduleHideFragmentView(Object var1, View var2, ArrayList<View> var3);

    void scheduleNameReset(ViewGroup viewGroup, final ArrayList<View> arrayList, final Map<String, String> map) {
        OneShotPreDrawListener.add((View)viewGroup, new Runnable(){

            @Override
            public void run() {
                int n = arrayList.size();
                int n2 = 0;
                while (n2 < n) {
                    View view = (View)arrayList.get(n2);
                    String string2 = ViewCompat.getTransitionName(view);
                    ViewCompat.setTransitionName(view, (String)map.get(string2));
                    ++n2;
                }
            }
        });
    }

    public abstract void scheduleRemoveTargets(Object var1, Object var2, ArrayList<View> var3, Object var4, ArrayList<View> var5, Object var6, ArrayList<View> var7);

    public abstract void setEpicenter(Object var1, Rect var2);

    public abstract void setEpicenter(Object var1, View var2);

    public void setListenerForTransitionEnd(Fragment fragment, Object object, CancellationSignal cancellationSignal, Runnable runnable2) {
        runnable2.run();
    }

    void setNameOverridesOrdered(View view, final ArrayList<View> arrayList, final Map<String, String> map) {
        OneShotPreDrawListener.add(view, new Runnable(){

            @Override
            public void run() {
                int n = arrayList.size();
                int n2 = 0;
                while (n2 < n) {
                    View view = (View)arrayList.get(n2);
                    String string2 = ViewCompat.getTransitionName(view);
                    if (string2 != null) {
                        ViewCompat.setTransitionName(view, FragmentTransitionImpl.findKeyForValue(map, string2));
                    }
                    ++n2;
                }
            }
        });
    }

    void setNameOverridesReordered(View view, final ArrayList<View> arrayList, final ArrayList<View> arrayList2, final ArrayList<String> arrayList3, Map<String, String> map) {
        final int n = arrayList2.size();
        final ArrayList<String> arrayList4 = new ArrayList<String>();
        int n2 = 0;
        do {
            if (n2 >= n) {
                OneShotPreDrawListener.add(view, new Runnable(){

                    @Override
                    public void run() {
                        int n2 = 0;
                        while (n2 < n) {
                            ViewCompat.setTransitionName((View)arrayList2.get(n2), (String)arrayList3.get(n2));
                            ViewCompat.setTransitionName((View)arrayList.get(n2), (String)arrayList4.get(n2));
                            ++n2;
                        }
                    }
                });
                return;
            }
            Object object = arrayList.get(n2);
            String string2 = ViewCompat.getTransitionName((View)object);
            arrayList4.add(string2);
            if (string2 != null) {
                ViewCompat.setTransitionName((View)object, null);
                object = map.get(string2);
                for (int i = 0; i < n; ++i) {
                    if (!((String)object).equals(arrayList3.get(i))) continue;
                    ViewCompat.setTransitionName(arrayList2.get(i), string2);
                    break;
                }
            }
            ++n2;
        } while (true);
    }

    public abstract void setSharedElementTargets(Object var1, View var2, ArrayList<View> var3);

    public abstract void swapSharedElementTargets(Object var1, ArrayList<View> var2, ArrayList<View> var3);

    public abstract Object wrapTransitionInSet(Object var1);

}

