/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.Rect
 *  android.view.View
 *  android.view.ViewGroup
 */
package androidx.transition;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentTransitionImpl;
import androidx.transition.Transition;
import androidx.transition.TransitionListenerAdapter;
import androidx.transition.TransitionManager;
import androidx.transition.TransitionSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FragmentTransitionSupport
extends FragmentTransitionImpl {
    private static boolean hasSimpleTarget(Transition transition) {
        if (!FragmentTransitionSupport.isNullOrEmpty(transition.getTargetIds())) return true;
        if (!FragmentTransitionSupport.isNullOrEmpty(transition.getTargetNames())) return true;
        if (!FragmentTransitionSupport.isNullOrEmpty(transition.getTargetTypes())) return true;
        return false;
    }

    @Override
    public void addTarget(Object object, View view) {
        if (object == null) return;
        ((Transition)object).addTarget(view);
    }

    @Override
    public void addTargets(Object object, ArrayList<View> arrayList) {
        if ((object = (Transition)object) == null) {
            return;
        }
        boolean bl = object instanceof TransitionSet;
        int n = 0;
        int n2 = 0;
        if (bl) {
            object = (TransitionSet)object;
            n = ((TransitionSet)object).getTransitionCount();
            while (n2 < n) {
                this.addTargets(((TransitionSet)object).getTransitionAt(n2), arrayList);
                ++n2;
            }
            return;
        }
        if (FragmentTransitionSupport.hasSimpleTarget((Transition)object)) return;
        if (!FragmentTransitionSupport.isNullOrEmpty(((Transition)object).getTargets())) return;
        int n3 = arrayList.size();
        n2 = n;
        while (n2 < n3) {
            ((Transition)object).addTarget(arrayList.get(n2));
            ++n2;
        }
    }

    @Override
    public void beginDelayedTransition(ViewGroup viewGroup, Object object) {
        TransitionManager.beginDelayedTransition(viewGroup, (Transition)object);
    }

    @Override
    public boolean canHandle(Object object) {
        return object instanceof Transition;
    }

    @Override
    public Object cloneTransition(Object object) {
        if (object == null) return null;
        return ((Transition)object).clone();
    }

    @Override
    public Object mergeTransitionsInSequence(Object object, Object object2, Object object3) {
        object = (Transition)object;
        object2 = (Transition)object2;
        object3 = (Transition)object3;
        if (object != null && object2 != null) {
            object = new TransitionSet().addTransition((Transition)object).addTransition((Transition)object2).setOrdering(1);
        } else if (object == null) {
            object = object2 != null ? object2 : null;
        }
        if (object3 == null) return object;
        object2 = new TransitionSet();
        if (object != null) {
            ((TransitionSet)object2).addTransition((Transition)object);
        }
        ((TransitionSet)object2).addTransition((Transition)object3);
        return object2;
    }

    @Override
    public Object mergeTransitionsTogether(Object object, Object object2, Object object3) {
        TransitionSet transitionSet = new TransitionSet();
        if (object != null) {
            transitionSet.addTransition((Transition)object);
        }
        if (object2 != null) {
            transitionSet.addTransition((Transition)object2);
        }
        if (object3 == null) return transitionSet;
        transitionSet.addTransition((Transition)object3);
        return transitionSet;
    }

    @Override
    public void removeTarget(Object object, View view) {
        if (object == null) return;
        ((Transition)object).removeTarget(view);
    }

    @Override
    public void replaceTargets(Object object, ArrayList<View> arrayList, ArrayList<View> arrayList2) {
        int n;
        object = (Transition)object;
        boolean bl = object instanceof TransitionSet;
        int n2 = 0;
        if (bl) {
            object = (TransitionSet)object;
            n = ((TransitionSet)object).getTransitionCount();
            while (n2 < n) {
                this.replaceTargets(((TransitionSet)object).getTransitionAt(n2), arrayList, arrayList2);
                ++n2;
            }
            return;
        }
        if (FragmentTransitionSupport.hasSimpleTarget((Transition)object)) return;
        List<View> list = ((Transition)object).getTargets();
        if (list.size() != arrayList.size()) return;
        if (!list.containsAll(arrayList)) return;
        n2 = arrayList2 == null ? 0 : arrayList2.size();
        for (n = 0; n < n2; ++n) {
            ((Transition)object).addTarget(arrayList2.get(n));
        }
        n2 = arrayList.size() - 1;
        while (n2 >= 0) {
            ((Transition)object).removeTarget(arrayList.get(n2));
            --n2;
        }
    }

    @Override
    public void scheduleHideFragmentView(Object object, final View view, final ArrayList<View> arrayList) {
        ((Transition)object).addListener(new Transition.TransitionListener(){

            @Override
            public void onTransitionCancel(Transition transition) {
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                view.setVisibility(8);
                int n = arrayList.size();
                int n2 = 0;
                while (n2 < n) {
                    ((View)arrayList.get(n2)).setVisibility(0);
                    ++n2;
                }
            }

            @Override
            public void onTransitionPause(Transition transition) {
            }

            @Override
            public void onTransitionResume(Transition transition) {
            }

            @Override
            public void onTransitionStart(Transition transition) {
            }
        });
    }

    @Override
    public void scheduleRemoveTargets(Object object, final Object object2, final ArrayList<View> arrayList, final Object object3, final ArrayList<View> arrayList2, final Object object4, final ArrayList<View> arrayList3) {
        ((Transition)object).addListener(new TransitionListenerAdapter(){

            @Override
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
            }

            @Override
            public void onTransitionStart(Transition object) {
                object = object2;
                if (object != null) {
                    FragmentTransitionSupport.this.replaceTargets(object, arrayList, null);
                }
                if ((object = object3) != null) {
                    FragmentTransitionSupport.this.replaceTargets(object, arrayList2, null);
                }
                if ((object = object4) == null) return;
                FragmentTransitionSupport.this.replaceTargets(object, arrayList3, null);
            }
        });
    }

    @Override
    public void setEpicenter(Object object, final Rect rect) {
        if (object == null) return;
        ((Transition)object).setEpicenterCallback(new Transition.EpicenterCallback(){

            @Override
            public Rect onGetEpicenter(Transition transition) {
                transition = rect;
                if (transition == null) return null;
                if (!transition.isEmpty()) return rect;
                return null;
            }
        });
    }

    @Override
    public void setEpicenter(Object object, View view) {
        if (view == null) return;
        object = (Transition)object;
        final Rect rect = new Rect();
        this.getBoundsOnScreen(view, rect);
        ((Transition)object).setEpicenterCallback(new Transition.EpicenterCallback(){

            @Override
            public Rect onGetEpicenter(Transition transition) {
                return rect;
            }
        });
    }

    @Override
    public void setSharedElementTargets(Object list, View view, ArrayList<View> arrayList) {
        TransitionSet transitionSet = (TransitionSet)((Object)list);
        list = transitionSet.getTargets();
        list.clear();
        int n = arrayList.size();
        int n2 = 0;
        do {
            if (n2 >= n) {
                list.add(view);
                arrayList.add(view);
                this.addTargets(transitionSet, arrayList);
                return;
            }
            FragmentTransitionSupport.bfsAddViewChildren(list, arrayList.get(n2));
            ++n2;
        } while (true);
    }

    @Override
    public void swapSharedElementTargets(Object object, ArrayList<View> arrayList, ArrayList<View> arrayList2) {
        if ((object = (TransitionSet)object) == null) return;
        ((Transition)object).getTargets().clear();
        ((Transition)object).getTargets().addAll(arrayList2);
        this.replaceTargets(object, arrayList, arrayList2);
    }

    @Override
    public Object wrapTransitionInSet(Object object) {
        if (object == null) {
            return null;
        }
        TransitionSet transitionSet = new TransitionSet();
        transitionSet.addTransition((Transition)object);
        return transitionSet;
    }

}

