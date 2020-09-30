/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.Rect
 *  android.transition.Transition
 *  android.transition.Transition$EpicenterCallback
 *  android.transition.Transition$TransitionListener
 *  android.transition.TransitionManager
 *  android.transition.TransitionSet
 *  android.view.View
 *  android.view.ViewGroup
 */
package androidx.fragment.app;

import android.graphics.Rect;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.os.CancellationSignal;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransitionImpl;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class FragmentTransitionCompat21
extends FragmentTransitionImpl {
    FragmentTransitionCompat21() {
    }

    private static boolean hasSimpleTarget(Transition transition) {
        if (!FragmentTransitionCompat21.isNullOrEmpty(transition.getTargetIds())) return true;
        if (!FragmentTransitionCompat21.isNullOrEmpty(transition.getTargetNames())) return true;
        if (!FragmentTransitionCompat21.isNullOrEmpty(transition.getTargetTypes())) return true;
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
            n = object.getTransitionCount();
            while (n2 < n) {
                this.addTargets((Object)object.getTransitionAt(n2), arrayList);
                ++n2;
            }
            return;
        }
        if (FragmentTransitionCompat21.hasSimpleTarget((Transition)object)) return;
        if (!FragmentTransitionCompat21.isNullOrEmpty(object.getTargets())) return;
        int n3 = arrayList.size();
        n2 = n;
        while (n2 < n3) {
            object.addTarget(arrayList.get(n2));
            ++n2;
        }
    }

    @Override
    public void beginDelayedTransition(ViewGroup viewGroup, Object object) {
        TransitionManager.beginDelayedTransition((ViewGroup)viewGroup, (Transition)((Transition)object));
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
            object2.addTransition((Transition)object);
        }
        object2.addTransition((Transition)object3);
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
            n = object.getTransitionCount();
            while (n2 < n) {
                this.replaceTargets((Object)object.getTransitionAt(n2), arrayList, arrayList2);
                ++n2;
            }
            return;
        }
        if (FragmentTransitionCompat21.hasSimpleTarget((Transition)object)) return;
        List list = object.getTargets();
        if (list == null) return;
        if (list.size() != arrayList.size()) return;
        if (!list.containsAll(arrayList)) return;
        n2 = arrayList2 == null ? 0 : arrayList2.size();
        for (n = 0; n < n2; ++n) {
            object.addTarget(arrayList2.get(n));
        }
        n2 = arrayList.size() - 1;
        while (n2 >= 0) {
            object.removeTarget(arrayList.get(n2));
            --n2;
        }
    }

    @Override
    public void scheduleHideFragmentView(Object object, final View view, final ArrayList<View> arrayList) {
        ((Transition)object).addListener(new Transition.TransitionListener(){

            public void onTransitionCancel(Transition transition) {
            }

            public void onTransitionEnd(Transition transition) {
                transition.removeListener((Transition.TransitionListener)this);
                view.setVisibility(8);
                int n = arrayList.size();
                int n2 = 0;
                while (n2 < n) {
                    ((View)arrayList.get(n2)).setVisibility(0);
                    ++n2;
                }
            }

            public void onTransitionPause(Transition transition) {
            }

            public void onTransitionResume(Transition transition) {
            }

            public void onTransitionStart(Transition transition) {
                transition.removeListener((Transition.TransitionListener)this);
                transition.addListener((Transition.TransitionListener)this);
            }
        });
    }

    @Override
    public void scheduleRemoveTargets(Object object, final Object object2, final ArrayList<View> arrayList, final Object object3, final ArrayList<View> arrayList2, final Object object4, final ArrayList<View> arrayList3) {
        ((Transition)object).addListener(new Transition.TransitionListener(){

            public void onTransitionCancel(Transition transition) {
            }

            public void onTransitionEnd(Transition transition) {
                transition.removeListener((Transition.TransitionListener)this);
            }

            public void onTransitionPause(Transition transition) {
            }

            public void onTransitionResume(Transition transition) {
            }

            public void onTransitionStart(Transition object) {
                object = object2;
                if (object != null) {
                    FragmentTransitionCompat21.this.replaceTargets(object, arrayList, null);
                }
                if ((object = object3) != null) {
                    FragmentTransitionCompat21.this.replaceTargets(object, arrayList2, null);
                }
                if ((object = object4) == null) return;
                FragmentTransitionCompat21.this.replaceTargets(object, arrayList3, null);
            }
        });
    }

    @Override
    public void setEpicenter(Object object, final Rect rect) {
        if (object == null) return;
        ((Transition)object).setEpicenterCallback(new Transition.EpicenterCallback(){

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
        object.setEpicenterCallback(new Transition.EpicenterCallback(){

            public Rect onGetEpicenter(Transition transition) {
                return rect;
            }
        });
    }

    @Override
    public void setListenerForTransitionEnd(Fragment fragment, Object object, CancellationSignal cancellationSignal, final Runnable runnable2) {
        ((Transition)object).addListener(new Transition.TransitionListener(){

            public void onTransitionCancel(Transition transition) {
            }

            public void onTransitionEnd(Transition transition) {
                runnable2.run();
            }

            public void onTransitionPause(Transition transition) {
            }

            public void onTransitionResume(Transition transition) {
            }

            public void onTransitionStart(Transition transition) {
            }
        });
    }

    @Override
    public void setSharedElementTargets(Object object, View view, ArrayList<View> arrayList) {
        object = (TransitionSet)object;
        List list = object.getTargets();
        list.clear();
        int n = arrayList.size();
        int n2 = 0;
        do {
            if (n2 >= n) {
                list.add(view);
                arrayList.add(view);
                this.addTargets(object, arrayList);
                return;
            }
            FragmentTransitionCompat21.bfsAddViewChildren(list, arrayList.get(n2));
            ++n2;
        } while (true);
    }

    @Override
    public void swapSharedElementTargets(Object object, ArrayList<View> arrayList, ArrayList<View> arrayList2) {
        if ((object = (TransitionSet)object) == null) return;
        object.getTargets().clear();
        object.getTargets().addAll(arrayList2);
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

