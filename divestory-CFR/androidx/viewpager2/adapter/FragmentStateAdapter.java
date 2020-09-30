/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Parcelable
 *  android.view.View
 *  android.view.View$OnLayoutChangeListener
 *  android.view.ViewGroup
 *  android.view.ViewParent
 *  android.widget.FrameLayout
 */
package androidx.viewpager2.adapter;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import androidx.collection.ArraySet;
import androidx.collection.LongSparseArray;
import androidx.core.util.Preconditions;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentViewHolder;
import androidx.viewpager2.adapter.StatefulAdapter;
import androidx.viewpager2.widget.ViewPager2;
import java.util.Iterator;
import java.util.Set;

public abstract class FragmentStateAdapter
extends RecyclerView.Adapter<FragmentViewHolder>
implements StatefulAdapter {
    private static final long GRACE_WINDOW_TIME_MS = 10000L;
    private static final String KEY_PREFIX_FRAGMENT = "f#";
    private static final String KEY_PREFIX_STATE = "s#";
    final FragmentManager mFragmentManager;
    private FragmentMaxLifecycleEnforcer mFragmentMaxLifecycleEnforcer;
    final LongSparseArray<Fragment> mFragments = new LongSparseArray();
    private boolean mHasStaleFragments = false;
    boolean mIsInGracePeriod = false;
    private final LongSparseArray<Integer> mItemIdToViewHolder = new LongSparseArray();
    final Lifecycle mLifecycle;
    private final LongSparseArray<Fragment.SavedState> mSavedStates = new LongSparseArray();

    public FragmentStateAdapter(Fragment fragment) {
        this(fragment.getChildFragmentManager(), fragment.getLifecycle());
    }

    public FragmentStateAdapter(FragmentActivity fragmentActivity) {
        this(fragmentActivity.getSupportFragmentManager(), fragmentActivity.getLifecycle());
    }

    public FragmentStateAdapter(FragmentManager fragmentManager, Lifecycle lifecycle) {
        this.mFragmentManager = fragmentManager;
        this.mLifecycle = lifecycle;
        super.setHasStableIds(true);
    }

    private static String createKey(String string2, long l) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append(l);
        return stringBuilder.toString();
    }

    private void ensureFragment(int n) {
        long l = this.getItemId(n);
        if (this.mFragments.containsKey(l)) return;
        Fragment fragment = this.createFragment(n);
        fragment.setInitialSavedState(this.mSavedStates.get(l));
        this.mFragments.put(l, fragment);
    }

    private boolean isFragmentViewBound(long l) {
        boolean bl = this.mItemIdToViewHolder.containsKey(l);
        boolean bl2 = true;
        if (bl) {
            return true;
        }
        Fragment fragment = this.mFragments.get(l);
        if (fragment == null) {
            return false;
        }
        if ((fragment = fragment.getView()) == null) {
            return false;
        }
        if (fragment.getParent() == null) return false;
        return bl2;
    }

    private static boolean isValidKey(String string2, String string3) {
        if (!string2.startsWith(string3)) return false;
        if (string2.length() <= string3.length()) return false;
        return true;
    }

    private Long itemForViewHolder(int n) {
        Long l = null;
        int n2 = 0;
        while (n2 < this.mItemIdToViewHolder.size()) {
            Long l2 = l;
            if (this.mItemIdToViewHolder.valueAt(n2) == n) {
                if (l != null) throw new IllegalStateException("Design assumption violated: a ViewHolder can only be bound to one item at a time.");
                l2 = this.mItemIdToViewHolder.keyAt(n2);
            }
            ++n2;
            l = l2;
        }
        return l;
    }

    private static long parseIdFromKey(String string2, String string3) {
        return Long.parseLong(string2.substring(string3.length()));
    }

    private void removeFragment(long l) {
        ViewParent viewParent;
        Fragment fragment = this.mFragments.get(l);
        if (fragment == null) {
            return;
        }
        if (fragment.getView() != null && (viewParent = fragment.getView().getParent()) != null) {
            ((FrameLayout)viewParent).removeAllViews();
        }
        if (!this.containsItem(l)) {
            this.mSavedStates.remove(l);
        }
        if (!fragment.isAdded()) {
            this.mFragments.remove(l);
            return;
        }
        if (this.shouldDelayFragmentTransactions()) {
            this.mHasStaleFragments = true;
            return;
        }
        if (fragment.isAdded() && this.containsItem(l)) {
            this.mSavedStates.put(l, this.mFragmentManager.saveFragmentInstanceState(fragment));
        }
        this.mFragmentManager.beginTransaction().remove(fragment).commitNow();
        this.mFragments.remove(l);
    }

    private void scheduleGracePeriodEnd() {
        final Handler handler = new Handler(Looper.getMainLooper());
        final Runnable runnable2 = new Runnable(){

            @Override
            public void run() {
                FragmentStateAdapter.this.mIsInGracePeriod = false;
                FragmentStateAdapter.this.gcFragments();
            }
        };
        this.mLifecycle.addObserver(new LifecycleEventObserver(){

            @Override
            public void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
                if (event != Lifecycle.Event.ON_DESTROY) return;
                handler.removeCallbacks(runnable2);
                lifecycleOwner.getLifecycle().removeObserver(this);
            }
        });
        handler.postDelayed(runnable2, 10000L);
    }

    private void scheduleViewAttach(final Fragment fragment, final FrameLayout frameLayout) {
        this.mFragmentManager.registerFragmentLifecycleCallbacks(new FragmentManager.FragmentLifecycleCallbacks(){

            @Override
            public void onFragmentViewCreated(FragmentManager fragmentManager, Fragment fragment2, View view, Bundle bundle) {
                if (fragment2 != fragment) return;
                fragmentManager.unregisterFragmentLifecycleCallbacks(this);
                FragmentStateAdapter.this.addViewToContainer(view, frameLayout);
            }
        }, false);
    }

    void addViewToContainer(View view, FrameLayout frameLayout) {
        if (frameLayout.getChildCount() > 1) throw new IllegalStateException("Design assumption violated.");
        if (view.getParent() == frameLayout) {
            return;
        }
        if (frameLayout.getChildCount() > 0) {
            frameLayout.removeAllViews();
        }
        if (view.getParent() != null) {
            ((ViewGroup)view.getParent()).removeView(view);
        }
        frameLayout.addView(view);
    }

    public boolean containsItem(long l) {
        if (l < 0L) return false;
        if (l >= (long)this.getItemCount()) return false;
        return true;
    }

    public abstract Fragment createFragment(int var1);

    void gcFragments() {
        long l;
        int n;
        if (!this.mHasStaleFragments) return;
        if (this.shouldDelayFragmentTransactions()) {
            return;
        }
        Object object = new ArraySet<E>();
        int n2 = 0;
        for (n = 0; n < this.mFragments.size(); ++n) {
            l = this.mFragments.keyAt(n);
            if (this.containsItem(l)) continue;
            object.add(l);
            this.mItemIdToViewHolder.remove(l);
        }
        if (!this.mIsInGracePeriod) {
            this.mHasStaleFragments = false;
            for (n = n2; n < this.mFragments.size(); ++n) {
                l = this.mFragments.keyAt(n);
                if (this.isFragmentViewBound(l)) continue;
                object.add(l);
            }
        }
        object = object.iterator();
        while (object.hasNext()) {
            this.removeFragment((Long)object.next());
        }
    }

    @Override
    public long getItemId(int n) {
        return n;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        FragmentMaxLifecycleEnforcer fragmentMaxLifecycleEnforcer;
        boolean bl = this.mFragmentMaxLifecycleEnforcer == null;
        Preconditions.checkArgument(bl);
        this.mFragmentMaxLifecycleEnforcer = fragmentMaxLifecycleEnforcer = new FragmentMaxLifecycleEnforcer();
        fragmentMaxLifecycleEnforcer.register(recyclerView);
    }

    @Override
    public final void onBindViewHolder(FragmentViewHolder fragmentViewHolder, int n) {
        long l = fragmentViewHolder.getItemId();
        int n2 = fragmentViewHolder.getContainer().getId();
        Long l2 = this.itemForViewHolder(n2);
        if (l2 != null && l2 != l) {
            this.removeFragment(l2);
            this.mItemIdToViewHolder.remove(l2);
        }
        this.mItemIdToViewHolder.put(l, n2);
        this.ensureFragment(n);
        l2 = fragmentViewHolder.getContainer();
        if (ViewCompat.isAttachedToWindow((View)l2)) {
            if (l2.getParent() != null) throw new IllegalStateException("Design assumption violated.");
            l2.addOnLayoutChangeListener(new View.OnLayoutChangeListener((FrameLayout)l2, fragmentViewHolder){
                final /* synthetic */ FrameLayout val$container;
                final /* synthetic */ FragmentViewHolder val$holder;
                {
                    this.val$container = frameLayout;
                    this.val$holder = fragmentViewHolder;
                }

                public void onLayoutChange(View view, int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
                    if (this.val$container.getParent() == null) return;
                    this.val$container.removeOnLayoutChangeListener((View.OnLayoutChangeListener)this);
                    FragmentStateAdapter.this.placeFragmentInViewHolder(this.val$holder);
                }
            });
        }
        this.gcFragments();
    }

    @Override
    public final FragmentViewHolder onCreateViewHolder(ViewGroup viewGroup, int n) {
        return FragmentViewHolder.create(viewGroup);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        this.mFragmentMaxLifecycleEnforcer.unregister(recyclerView);
        this.mFragmentMaxLifecycleEnforcer = null;
    }

    @Override
    public final boolean onFailedToRecycleView(FragmentViewHolder fragmentViewHolder) {
        return true;
    }

    @Override
    public final void onViewAttachedToWindow(FragmentViewHolder fragmentViewHolder) {
        this.placeFragmentInViewHolder(fragmentViewHolder);
        this.gcFragments();
    }

    @Override
    public final void onViewRecycled(FragmentViewHolder object) {
        if ((object = this.itemForViewHolder(((FragmentViewHolder)object).getContainer().getId())) == null) return;
        this.removeFragment((Long)object);
        this.mItemIdToViewHolder.remove((Long)object);
    }

    void placeFragmentInViewHolder(final FragmentViewHolder fragmentViewHolder) {
        Fragment fragment = this.mFragments.get(fragmentViewHolder.getItemId());
        if (fragment == null) throw new IllegalStateException("Design assumption violated.");
        Object object = fragmentViewHolder.getContainer();
        Object object2 = fragment.getView();
        if (!fragment.isAdded()) {
            if (object2 != null) throw new IllegalStateException("Design assumption violated.");
        }
        if (fragment.isAdded() && object2 == null) {
            this.scheduleViewAttach(fragment, (FrameLayout)object);
            return;
        }
        if (fragment.isAdded() && object2.getParent() != null) {
            if (object2.getParent() == object) return;
            this.addViewToContainer((View)object2, (FrameLayout)object);
            return;
        }
        if (fragment.isAdded()) {
            this.addViewToContainer((View)object2, (FrameLayout)object);
            return;
        }
        if (!this.shouldDelayFragmentTransactions()) {
            this.scheduleViewAttach(fragment, (FrameLayout)object);
            object2 = this.mFragmentManager.beginTransaction();
            object = new StringBuilder();
            ((StringBuilder)object).append("f");
            ((StringBuilder)object).append(fragmentViewHolder.getItemId());
            ((FragmentTransaction)object2).add(fragment, ((StringBuilder)object).toString()).setMaxLifecycle(fragment, Lifecycle.State.STARTED).commitNow();
            this.mFragmentMaxLifecycleEnforcer.updateFragmentMaxLifecycle(false);
            return;
        }
        if (this.mFragmentManager.isDestroyed()) {
            return;
        }
        this.mLifecycle.addObserver(new LifecycleEventObserver(){

            @Override
            public void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
                if (FragmentStateAdapter.this.shouldDelayFragmentTransactions()) {
                    return;
                }
                lifecycleOwner.getLifecycle().removeObserver(this);
                if (!ViewCompat.isAttachedToWindow((View)fragmentViewHolder.getContainer())) return;
                FragmentStateAdapter.this.placeFragmentInViewHolder(fragmentViewHolder);
            }
        });
    }

    @Override
    public final void restoreState(Parcelable object) {
        if (!this.mSavedStates.isEmpty()) throw new IllegalStateException("Expected the adapter to be 'fresh' while restoring state.");
        if (!this.mFragments.isEmpty()) throw new IllegalStateException("Expected the adapter to be 'fresh' while restoring state.");
        if ((object = (Bundle)object).getClassLoader() == null) {
            object.setClassLoader(this.getClass().getClassLoader());
        }
        Iterator<E> iterator2 = object.keySet().iterator();
        do {
            long l;
            if (!iterator2.hasNext()) {
                if (this.mFragments.isEmpty()) return;
                this.mHasStaleFragments = true;
                this.mIsInGracePeriod = true;
                this.gcFragments();
                this.scheduleGracePeriodEnd();
                return;
            }
            Object object2 = (String)iterator2.next();
            if (FragmentStateAdapter.isValidKey((String)object2, "f#")) {
                l = FragmentStateAdapter.parseIdFromKey((String)object2, "f#");
                object2 = this.mFragmentManager.getFragment((Bundle)object, (String)object2);
                this.mFragments.put(l, (Fragment)object2);
                continue;
            }
            if (!FragmentStateAdapter.isValidKey((String)object2, "s#")) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Unexpected key in savedState: ");
                ((StringBuilder)object).append((String)object2);
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            l = FragmentStateAdapter.parseIdFromKey((String)object2, "s#");
            object2 = (Fragment.SavedState)object.getParcelable((String)object2);
            if (!this.containsItem(l)) continue;
            this.mSavedStates.put(l, (Fragment.SavedState)object2);
        } while (true);
    }

    @Override
    public final Parcelable saveState() {
        long l;
        int n;
        Bundle bundle = new Bundle(this.mFragments.size() + this.mSavedStates.size());
        int n2 = 0;
        int n3 = 0;
        do {
            n = n2;
            if (n3 >= this.mFragments.size()) break;
            l = this.mFragments.keyAt(n3);
            Fragment fragment = this.mFragments.get(l);
            if (fragment != null && fragment.isAdded()) {
                String string2 = FragmentStateAdapter.createKey("f#", l);
                this.mFragmentManager.putFragment(bundle, string2, fragment);
            }
            ++n3;
        } while (true);
        while (n < this.mSavedStates.size()) {
            l = this.mSavedStates.keyAt(n);
            if (this.containsItem(l)) {
                bundle.putParcelable(FragmentStateAdapter.createKey("s#", l), (Parcelable)this.mSavedStates.get(l));
            }
            ++n;
        }
        return bundle;
    }

    @Override
    public final void setHasStableIds(boolean bl) {
        throw new UnsupportedOperationException("Stable Ids are required for the adapter to function properly, and the adapter takes care of setting the flag.");
    }

    boolean shouldDelayFragmentTransactions() {
        return this.mFragmentManager.isStateSaved();
    }

    private static abstract class DataSetChangeObserver
    extends RecyclerView.AdapterDataObserver {
        private DataSetChangeObserver() {
        }

        @Override
        public abstract void onChanged();

        @Override
        public final void onItemRangeChanged(int n, int n2) {
            this.onChanged();
        }

        @Override
        public final void onItemRangeChanged(int n, int n2, Object object) {
            this.onChanged();
        }

        @Override
        public final void onItemRangeInserted(int n, int n2) {
            this.onChanged();
        }

        @Override
        public final void onItemRangeMoved(int n, int n2, int n3) {
            this.onChanged();
        }

        @Override
        public final void onItemRangeRemoved(int n, int n2) {
            this.onChanged();
        }
    }

    class FragmentMaxLifecycleEnforcer {
        private RecyclerView.AdapterDataObserver mDataObserver;
        private LifecycleEventObserver mLifecycleObserver;
        private ViewPager2.OnPageChangeCallback mPageChangeCallback;
        private long mPrimaryItemId = -1L;
        private ViewPager2 mViewPager;

        FragmentMaxLifecycleEnforcer() {
        }

        private ViewPager2 inferViewPager(RecyclerView recyclerView) {
            if ((recyclerView = recyclerView.getParent()) instanceof ViewPager2) {
                return (ViewPager2)((Object)recyclerView);
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Expected ViewPager2 instance. Got: ");
            stringBuilder.append(recyclerView);
            throw new IllegalStateException(stringBuilder.toString());
        }

        void register(RecyclerView object) {
            this.mViewPager = this.inferViewPager((RecyclerView)object);
            this.mPageChangeCallback = object = new ViewPager2.OnPageChangeCallback(){

                @Override
                public void onPageScrollStateChanged(int n) {
                    FragmentMaxLifecycleEnforcer.this.updateFragmentMaxLifecycle(false);
                }

                @Override
                public void onPageSelected(int n) {
                    FragmentMaxLifecycleEnforcer.this.updateFragmentMaxLifecycle(false);
                }
            };
            this.mViewPager.registerOnPageChangeCallback((ViewPager2.OnPageChangeCallback)object);
            this.mDataObserver = object = new DataSetChangeObserver(){

                @Override
                public void onChanged() {
                    FragmentMaxLifecycleEnforcer.this.updateFragmentMaxLifecycle(true);
                }
            };
            FragmentStateAdapter.this.registerAdapterDataObserver((RecyclerView.AdapterDataObserver)object);
            this.mLifecycleObserver = new LifecycleEventObserver(){

                @Override
                public void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
                    FragmentMaxLifecycleEnforcer.this.updateFragmentMaxLifecycle(false);
                }
            };
            FragmentStateAdapter.this.mLifecycle.addObserver(this.mLifecycleObserver);
        }

        void unregister(RecyclerView recyclerView) {
            this.inferViewPager(recyclerView).unregisterOnPageChangeCallback(this.mPageChangeCallback);
            FragmentStateAdapter.this.unregisterAdapterDataObserver(this.mDataObserver);
            FragmentStateAdapter.this.mLifecycle.removeObserver(this.mLifecycleObserver);
            this.mViewPager = null;
        }

        void updateFragmentMaxLifecycle(boolean bl) {
            if (FragmentStateAdapter.this.shouldDelayFragmentTransactions()) {
                return;
            }
            if (this.mViewPager.getScrollState() != 0) {
                return;
            }
            if (FragmentStateAdapter.this.mFragments.isEmpty()) return;
            if (FragmentStateAdapter.this.getItemCount() == 0) {
                return;
            }
            int n = this.mViewPager.getCurrentItem();
            if (n >= FragmentStateAdapter.this.getItemCount()) {
                return;
            }
            long l = FragmentStateAdapter.this.getItemId(n);
            if (l == this.mPrimaryItemId && !bl) {
                return;
            }
            Fragment fragment = FragmentStateAdapter.this.mFragments.get(l);
            if (fragment == null) return;
            if (!fragment.isAdded()) {
                return;
            }
            this.mPrimaryItemId = l;
            FragmentTransaction fragmentTransaction = FragmentStateAdapter.this.mFragmentManager.beginTransaction();
            fragment = null;
            for (n = 0; n < FragmentStateAdapter.this.mFragments.size(); ++n) {
                l = FragmentStateAdapter.this.mFragments.keyAt(n);
                Fragment fragment2 = FragmentStateAdapter.this.mFragments.valueAt(n);
                if (!fragment2.isAdded()) continue;
                if (l != this.mPrimaryItemId) {
                    fragmentTransaction.setMaxLifecycle(fragment2, Lifecycle.State.STARTED);
                } else {
                    fragment = fragment2;
                }
                bl = l == this.mPrimaryItemId;
                fragment2.setMenuVisibility(bl);
            }
            if (fragment != null) {
                fragmentTransaction.setMaxLifecycle(fragment, Lifecycle.State.RESUMED);
            }
            if (fragmentTransaction.isEmpty()) return;
            fragmentTransaction.commitNow();
        }

    }

}

