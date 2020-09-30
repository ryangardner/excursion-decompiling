/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.lifecycle;

import androidx.arch.core.internal.FastSafeIterableMap;
import androidx.arch.core.internal.SafeIterableMap;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Lifecycling;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class LifecycleRegistry
extends Lifecycle {
    private int mAddingObserverCounter = 0;
    private boolean mHandlingEvent = false;
    private final WeakReference<LifecycleOwner> mLifecycleOwner;
    private boolean mNewEventOccurred = false;
    private FastSafeIterableMap<LifecycleObserver, ObserverWithState> mObserverMap = new FastSafeIterableMap();
    private ArrayList<Lifecycle.State> mParentStates = new ArrayList();
    private Lifecycle.State mState;

    public LifecycleRegistry(LifecycleOwner lifecycleOwner) {
        this.mLifecycleOwner = new WeakReference<LifecycleOwner>(lifecycleOwner);
        this.mState = Lifecycle.State.INITIALIZED;
    }

    private void backwardPass(LifecycleOwner lifecycleOwner) {
        Iterator iterator2 = this.mObserverMap.descendingIterator();
        block0 : while (iterator2.hasNext()) {
            if (this.mNewEventOccurred) return;
            Map.Entry entry = iterator2.next();
            ObserverWithState observerWithState = (ObserverWithState)entry.getValue();
            do {
                if (observerWithState.mState.compareTo(this.mState) <= 0 || this.mNewEventOccurred || !this.mObserverMap.contains((LifecycleObserver)entry.getKey())) continue block0;
                Lifecycle.Event event = LifecycleRegistry.downEvent(observerWithState.mState);
                this.pushParentState(LifecycleRegistry.getStateAfter(event));
                observerWithState.dispatchEvent(lifecycleOwner, event);
                this.popParentState();
            } while (true);
            break;
        }
        return;
    }

    private Lifecycle.State calculateTargetState(LifecycleObserver object) {
        object = this.mObserverMap.ceil((LifecycleObserver)object);
        Object object2 = null;
        object = object != null ? ((ObserverWithState)object.getValue()).mState : null;
        if (this.mParentStates.isEmpty()) return LifecycleRegistry.min(LifecycleRegistry.min(this.mState, (Lifecycle.State)((Object)((Object)object))), object2);
        object2 = this.mParentStates;
        object2 = object2.get(object2.size() - 1);
        return LifecycleRegistry.min(LifecycleRegistry.min(this.mState, (Lifecycle.State)((Object)((Object)object))), object2);
    }

    private static Lifecycle.Event downEvent(Lifecycle.State state) {
        int n = 1.$SwitchMap$androidx$lifecycle$Lifecycle$State[state.ordinal()];
        if (n == 1) throw new IllegalArgumentException();
        if (n == 2) return Lifecycle.Event.ON_DESTROY;
        if (n == 3) return Lifecycle.Event.ON_STOP;
        if (n == 4) return Lifecycle.Event.ON_PAUSE;
        if (n == 5) throw new IllegalArgumentException();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unexpected state value ");
        stringBuilder.append((Object)state);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private void forwardPass(LifecycleOwner lifecycleOwner) {
        SafeIterableMap.IteratorWithAdditions iteratorWithAdditions = this.mObserverMap.iteratorWithAdditions();
        block0 : while (iteratorWithAdditions.hasNext()) {
            if (this.mNewEventOccurred) return;
            Map.Entry entry = (Map.Entry)iteratorWithAdditions.next();
            ObserverWithState observerWithState = (ObserverWithState)entry.getValue();
            do {
                if (observerWithState.mState.compareTo(this.mState) >= 0 || this.mNewEventOccurred || !this.mObserverMap.contains((LifecycleObserver)entry.getKey())) continue block0;
                this.pushParentState(observerWithState.mState);
                observerWithState.dispatchEvent(lifecycleOwner, LifecycleRegistry.upEvent(observerWithState.mState));
                this.popParentState();
            } while (true);
            break;
        }
        return;
    }

    static Lifecycle.State getStateAfter(Lifecycle.Event event) {
        switch (1.$SwitchMap$androidx$lifecycle$Lifecycle$Event[event.ordinal()]) {
            default: {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unexpected event value ");
                stringBuilder.append((Object)event);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            case 6: {
                return Lifecycle.State.DESTROYED;
            }
            case 5: {
                return Lifecycle.State.RESUMED;
            }
            case 3: 
            case 4: {
                return Lifecycle.State.STARTED;
            }
            case 1: 
            case 2: 
        }
        return Lifecycle.State.CREATED;
    }

    private boolean isSynced() {
        int n = this.mObserverMap.size();
        boolean bl = true;
        if (n == 0) {
            return true;
        }
        Lifecycle.State state = ((ObserverWithState)this.mObserverMap.eldest().getValue()).mState;
        Lifecycle.State state2 = ((ObserverWithState)this.mObserverMap.newest().getValue()).mState;
        if (state != state2) return false;
        if (this.mState != state2) return false;
        return bl;
    }

    static Lifecycle.State min(Lifecycle.State state, Lifecycle.State state2) {
        Lifecycle.State state3 = state;
        if (state2 == null) return state3;
        state3 = state;
        if (state2.compareTo(state) >= 0) return state3;
        return state2;
    }

    private void moveToState(Lifecycle.State state) {
        if (this.mState == state) {
            return;
        }
        this.mState = state;
        if (!this.mHandlingEvent && this.mAddingObserverCounter == 0) {
            this.mHandlingEvent = true;
            this.sync();
            this.mHandlingEvent = false;
            return;
        }
        this.mNewEventOccurred = true;
    }

    private void popParentState() {
        ArrayList<Lifecycle.State> arrayList = this.mParentStates;
        arrayList.remove(arrayList.size() - 1);
    }

    private void pushParentState(Lifecycle.State state) {
        this.mParentStates.add(state);
    }

    private void sync() {
        LifecycleOwner lifecycleOwner = (LifecycleOwner)this.mLifecycleOwner.get();
        if (lifecycleOwner == null) throw new IllegalStateException("LifecycleOwner of this LifecycleRegistry is alreadygarbage collected. It is too late to change lifecycle state.");
        do {
            if (this.isSynced()) {
                this.mNewEventOccurred = false;
                return;
            }
            this.mNewEventOccurred = false;
            if (this.mState.compareTo(((ObserverWithState)this.mObserverMap.eldest().getValue()).mState) < 0) {
                this.backwardPass(lifecycleOwner);
            }
            Map.Entry entry = this.mObserverMap.newest();
            if (this.mNewEventOccurred || entry == null || this.mState.compareTo(((ObserverWithState)entry.getValue()).mState) <= 0) continue;
            this.forwardPass(lifecycleOwner);
        } while (true);
    }

    private static Lifecycle.Event upEvent(Lifecycle.State state) {
        int n = 1.$SwitchMap$androidx$lifecycle$Lifecycle$State[state.ordinal()];
        if (n == 1) return Lifecycle.Event.ON_CREATE;
        if (n == 2) return Lifecycle.Event.ON_START;
        if (n == 3) return Lifecycle.Event.ON_RESUME;
        if (n == 4) throw new IllegalArgumentException();
        if (n == 5) {
            return Lifecycle.Event.ON_CREATE;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unexpected state value ");
        stringBuilder.append((Object)state);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @Override
    public void addObserver(LifecycleObserver lifecycleObserver) {
        Lifecycle.State state = this.mState == Lifecycle.State.DESTROYED ? Lifecycle.State.DESTROYED : Lifecycle.State.INITIALIZED;
        ObserverWithState observerWithState = new ObserverWithState(lifecycleObserver, state);
        if (this.mObserverMap.putIfAbsent(lifecycleObserver, observerWithState) != null) {
            return;
        }
        LifecycleOwner lifecycleOwner = (LifecycleOwner)this.mLifecycleOwner.get();
        if (lifecycleOwner == null) {
            return;
        }
        boolean bl = this.mAddingObserverCounter != 0 || this.mHandlingEvent;
        state = this.calculateTargetState(lifecycleObserver);
        ++this.mAddingObserverCounter;
        while (observerWithState.mState.compareTo(state) < 0 && this.mObserverMap.contains(lifecycleObserver)) {
            this.pushParentState(observerWithState.mState);
            observerWithState.dispatchEvent(lifecycleOwner, LifecycleRegistry.upEvent(observerWithState.mState));
            this.popParentState();
            state = this.calculateTargetState(lifecycleObserver);
        }
        if (!bl) {
            this.sync();
        }
        --this.mAddingObserverCounter;
    }

    @Override
    public Lifecycle.State getCurrentState() {
        return this.mState;
    }

    public int getObserverCount() {
        return this.mObserverMap.size();
    }

    public void handleLifecycleEvent(Lifecycle.Event event) {
        this.moveToState(LifecycleRegistry.getStateAfter(event));
    }

    @Deprecated
    public void markState(Lifecycle.State state) {
        this.setCurrentState(state);
    }

    @Override
    public void removeObserver(LifecycleObserver lifecycleObserver) {
        this.mObserverMap.remove(lifecycleObserver);
    }

    public void setCurrentState(Lifecycle.State state) {
        this.moveToState(state);
    }

    static class ObserverWithState {
        LifecycleEventObserver mLifecycleObserver;
        Lifecycle.State mState;

        ObserverWithState(LifecycleObserver lifecycleObserver, Lifecycle.State state) {
            this.mLifecycleObserver = Lifecycling.lifecycleEventObserver(lifecycleObserver);
            this.mState = state;
        }

        void dispatchEvent(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
            Lifecycle.State state = LifecycleRegistry.getStateAfter(event);
            this.mState = LifecycleRegistry.min(this.mState, state);
            this.mLifecycleObserver.onStateChanged(lifecycleOwner, event);
            this.mState = state;
        }
    }

}

