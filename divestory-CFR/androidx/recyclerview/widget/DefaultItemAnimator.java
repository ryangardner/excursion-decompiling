/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.animation.TimeInterpolator
 *  android.animation.ValueAnimator
 *  android.view.View
 *  android.view.ViewPropertyAnimator
 */
package androidx.recyclerview.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewPropertyAnimator;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class DefaultItemAnimator
extends SimpleItemAnimator {
    private static final boolean DEBUG = false;
    private static TimeInterpolator sDefaultInterpolator;
    ArrayList<RecyclerView.ViewHolder> mAddAnimations = new ArrayList();
    ArrayList<ArrayList<RecyclerView.ViewHolder>> mAdditionsList = new ArrayList();
    ArrayList<RecyclerView.ViewHolder> mChangeAnimations = new ArrayList();
    ArrayList<ArrayList<ChangeInfo>> mChangesList = new ArrayList();
    ArrayList<RecyclerView.ViewHolder> mMoveAnimations = new ArrayList();
    ArrayList<ArrayList<MoveInfo>> mMovesList = new ArrayList();
    private ArrayList<RecyclerView.ViewHolder> mPendingAdditions = new ArrayList();
    private ArrayList<ChangeInfo> mPendingChanges = new ArrayList();
    private ArrayList<MoveInfo> mPendingMoves = new ArrayList();
    private ArrayList<RecyclerView.ViewHolder> mPendingRemovals = new ArrayList();
    ArrayList<RecyclerView.ViewHolder> mRemoveAnimations = new ArrayList();

    private void animateRemoveImpl(final RecyclerView.ViewHolder viewHolder) {
        final View view = viewHolder.itemView;
        final ViewPropertyAnimator viewPropertyAnimator = view.animate();
        this.mRemoveAnimations.add(viewHolder);
        viewPropertyAnimator.setDuration(this.getRemoveDuration()).alpha(0.0f).setListener((Animator.AnimatorListener)new AnimatorListenerAdapter(){

            public void onAnimationEnd(Animator animator2) {
                viewPropertyAnimator.setListener(null);
                view.setAlpha(1.0f);
                DefaultItemAnimator.this.dispatchRemoveFinished(viewHolder);
                DefaultItemAnimator.this.mRemoveAnimations.remove(viewHolder);
                DefaultItemAnimator.this.dispatchFinishedWhenDone();
            }

            public void onAnimationStart(Animator animator2) {
                DefaultItemAnimator.this.dispatchRemoveStarting(viewHolder);
            }
        }).start();
    }

    private void endChangeAnimation(List<ChangeInfo> list, RecyclerView.ViewHolder viewHolder) {
        int n = list.size() - 1;
        while (n >= 0) {
            ChangeInfo changeInfo = list.get(n);
            if (this.endChangeAnimationIfNecessary(changeInfo, viewHolder) && changeInfo.oldHolder == null && changeInfo.newHolder == null) {
                list.remove(changeInfo);
            }
            --n;
        }
    }

    private void endChangeAnimationIfNecessary(ChangeInfo changeInfo) {
        if (changeInfo.oldHolder != null) {
            this.endChangeAnimationIfNecessary(changeInfo, changeInfo.oldHolder);
        }
        if (changeInfo.newHolder == null) return;
        this.endChangeAnimationIfNecessary(changeInfo, changeInfo.newHolder);
    }

    private boolean endChangeAnimationIfNecessary(ChangeInfo changeInfo, RecyclerView.ViewHolder viewHolder) {
        RecyclerView.ViewHolder viewHolder2 = changeInfo.newHolder;
        boolean bl = false;
        if (viewHolder2 == viewHolder) {
            changeInfo.newHolder = null;
        } else {
            if (changeInfo.oldHolder != viewHolder) return false;
            changeInfo.oldHolder = null;
            bl = true;
        }
        viewHolder.itemView.setAlpha(1.0f);
        viewHolder.itemView.setTranslationX(0.0f);
        viewHolder.itemView.setTranslationY(0.0f);
        this.dispatchChangeFinished(viewHolder, bl);
        return true;
    }

    private void resetAnimation(RecyclerView.ViewHolder viewHolder) {
        if (sDefaultInterpolator == null) {
            sDefaultInterpolator = new ValueAnimator().getInterpolator();
        }
        viewHolder.itemView.animate().setInterpolator(sDefaultInterpolator);
        this.endAnimation(viewHolder);
    }

    @Override
    public boolean animateAdd(RecyclerView.ViewHolder viewHolder) {
        this.resetAnimation(viewHolder);
        viewHolder.itemView.setAlpha(0.0f);
        this.mPendingAdditions.add(viewHolder);
        return true;
    }

    void animateAddImpl(final RecyclerView.ViewHolder viewHolder) {
        final View view = viewHolder.itemView;
        final ViewPropertyAnimator viewPropertyAnimator = view.animate();
        this.mAddAnimations.add(viewHolder);
        viewPropertyAnimator.alpha(1.0f).setDuration(this.getAddDuration()).setListener((Animator.AnimatorListener)new AnimatorListenerAdapter(){

            public void onAnimationCancel(Animator animator2) {
                view.setAlpha(1.0f);
            }

            public void onAnimationEnd(Animator animator2) {
                viewPropertyAnimator.setListener(null);
                DefaultItemAnimator.this.dispatchAddFinished(viewHolder);
                DefaultItemAnimator.this.mAddAnimations.remove(viewHolder);
                DefaultItemAnimator.this.dispatchFinishedWhenDone();
            }

            public void onAnimationStart(Animator animator2) {
                DefaultItemAnimator.this.dispatchAddStarting(viewHolder);
            }
        }).start();
    }

    @Override
    public boolean animateChange(RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2, int n, int n2, int n3, int n4) {
        if (viewHolder == viewHolder2) {
            return this.animateMove(viewHolder, n, n2, n3, n4);
        }
        float f = viewHolder.itemView.getTranslationX();
        float f2 = viewHolder.itemView.getTranslationY();
        float f3 = viewHolder.itemView.getAlpha();
        this.resetAnimation(viewHolder);
        int n5 = (int)((float)(n3 - n) - f);
        int n6 = (int)((float)(n4 - n2) - f2);
        viewHolder.itemView.setTranslationX(f);
        viewHolder.itemView.setTranslationY(f2);
        viewHolder.itemView.setAlpha(f3);
        if (viewHolder2 != null) {
            this.resetAnimation(viewHolder2);
            viewHolder2.itemView.setTranslationX((float)(-n5));
            viewHolder2.itemView.setTranslationY((float)(-n6));
            viewHolder2.itemView.setAlpha(0.0f);
        }
        this.mPendingChanges.add(new ChangeInfo(viewHolder, viewHolder2, n, n2, n3, n4));
        return true;
    }

    void animateChangeImpl(final ChangeInfo changeInfo) {
        RecyclerView.ViewHolder viewHolder = changeInfo.oldHolder;
        View view = null;
        viewHolder = viewHolder == null ? null : viewHolder.itemView;
        RecyclerView.ViewHolder viewHolder2 = changeInfo.newHolder;
        if (viewHolder2 != null) {
            view = viewHolder2.itemView;
        }
        if (viewHolder != null) {
            viewHolder2 = viewHolder.animate().setDuration(this.getChangeDuration());
            this.mChangeAnimations.add(changeInfo.oldHolder);
            viewHolder2.translationX((float)(changeInfo.toX - changeInfo.fromX));
            viewHolder2.translationY((float)(changeInfo.toY - changeInfo.fromY));
            viewHolder2.alpha(0.0f).setListener((Animator.AnimatorListener)new AnimatorListenerAdapter((ViewPropertyAnimator)viewHolder2, (View)viewHolder){
                final /* synthetic */ ViewPropertyAnimator val$oldViewAnim;
                final /* synthetic */ View val$view;
                {
                    this.val$oldViewAnim = viewPropertyAnimator;
                    this.val$view = view;
                }

                public void onAnimationEnd(Animator animator2) {
                    this.val$oldViewAnim.setListener(null);
                    this.val$view.setAlpha(1.0f);
                    this.val$view.setTranslationX(0.0f);
                    this.val$view.setTranslationY(0.0f);
                    DefaultItemAnimator.this.dispatchChangeFinished(changeInfo.oldHolder, true);
                    DefaultItemAnimator.this.mChangeAnimations.remove(changeInfo.oldHolder);
                    DefaultItemAnimator.this.dispatchFinishedWhenDone();
                }

                public void onAnimationStart(Animator animator2) {
                    DefaultItemAnimator.this.dispatchChangeStarting(changeInfo.oldHolder, true);
                }
            }).start();
        }
        if (view == null) return;
        viewHolder = view.animate();
        this.mChangeAnimations.add(changeInfo.newHolder);
        viewHolder.translationX(0.0f).translationY(0.0f).setDuration(this.getChangeDuration()).alpha(1.0f).setListener((Animator.AnimatorListener)new AnimatorListenerAdapter((ViewPropertyAnimator)viewHolder, view){
            final /* synthetic */ View val$newView;
            final /* synthetic */ ViewPropertyAnimator val$newViewAnimation;
            {
                this.val$newViewAnimation = viewPropertyAnimator;
                this.val$newView = view;
            }

            public void onAnimationEnd(Animator animator2) {
                this.val$newViewAnimation.setListener(null);
                this.val$newView.setAlpha(1.0f);
                this.val$newView.setTranslationX(0.0f);
                this.val$newView.setTranslationY(0.0f);
                DefaultItemAnimator.this.dispatchChangeFinished(changeInfo.newHolder, false);
                DefaultItemAnimator.this.mChangeAnimations.remove(changeInfo.newHolder);
                DefaultItemAnimator.this.dispatchFinishedWhenDone();
            }

            public void onAnimationStart(Animator animator2) {
                DefaultItemAnimator.this.dispatchChangeStarting(changeInfo.newHolder, false);
            }
        }).start();
    }

    @Override
    public boolean animateMove(RecyclerView.ViewHolder viewHolder, int n, int n2, int n3, int n4) {
        View view = viewHolder.itemView;
        this.resetAnimation(viewHolder);
        int n5 = n3 - (n += (int)viewHolder.itemView.getTranslationX());
        int n6 = n4 - (n2 += (int)viewHolder.itemView.getTranslationY());
        if (n5 == 0 && n6 == 0) {
            this.dispatchMoveFinished(viewHolder);
            return false;
        }
        if (n5 != 0) {
            view.setTranslationX((float)(-n5));
        }
        if (n6 != 0) {
            view.setTranslationY((float)(-n6));
        }
        this.mPendingMoves.add(new MoveInfo(viewHolder, n, n2, n3, n4));
        return true;
    }

    void animateMoveImpl(final RecyclerView.ViewHolder viewHolder, final int n, final int n2, int n3, int n4) {
        final View view = viewHolder.itemView;
        n = n3 - n;
        n2 = n4 - n2;
        if (n != 0) {
            view.animate().translationX(0.0f);
        }
        if (n2 != 0) {
            view.animate().translationY(0.0f);
        }
        final ViewPropertyAnimator viewPropertyAnimator = view.animate();
        this.mMoveAnimations.add(viewHolder);
        viewPropertyAnimator.setDuration(this.getMoveDuration()).setListener((Animator.AnimatorListener)new AnimatorListenerAdapter(){

            public void onAnimationCancel(Animator animator2) {
                if (n != 0) {
                    view.setTranslationX(0.0f);
                }
                if (n2 == 0) return;
                view.setTranslationY(0.0f);
            }

            public void onAnimationEnd(Animator animator2) {
                viewPropertyAnimator.setListener(null);
                DefaultItemAnimator.this.dispatchMoveFinished(viewHolder);
                DefaultItemAnimator.this.mMoveAnimations.remove(viewHolder);
                DefaultItemAnimator.this.dispatchFinishedWhenDone();
            }

            public void onAnimationStart(Animator animator2) {
                DefaultItemAnimator.this.dispatchMoveStarting(viewHolder);
            }
        }).start();
    }

    @Override
    public boolean animateRemove(RecyclerView.ViewHolder viewHolder) {
        this.resetAnimation(viewHolder);
        this.mPendingRemovals.add(viewHolder);
        return true;
    }

    @Override
    public boolean canReuseUpdatedViewHolder(RecyclerView.ViewHolder viewHolder, List<Object> list) {
        if (!list.isEmpty()) return true;
        if (super.canReuseUpdatedViewHolder(viewHolder, list)) return true;
        return false;
    }

    void cancelAll(List<RecyclerView.ViewHolder> list) {
        int n = list.size() - 1;
        while (n >= 0) {
            list.get((int)n).itemView.animate().cancel();
            --n;
        }
    }

    void dispatchFinishedWhenDone() {
        if (this.isRunning()) return;
        this.dispatchAnimationsFinished();
    }

    @Override
    public void endAnimation(RecyclerView.ViewHolder viewHolder) {
        ArrayList<Object> arrayList;
        int n;
        View view = viewHolder.itemView;
        view.animate().cancel();
        for (n = this.mPendingMoves.size() - 1; n >= 0; --n) {
            if (this.mPendingMoves.get((int)n).holder != viewHolder) continue;
            view.setTranslationY(0.0f);
            view.setTranslationX(0.0f);
            this.dispatchMoveFinished(viewHolder);
            this.mPendingMoves.remove(n);
        }
        this.endChangeAnimation(this.mPendingChanges, viewHolder);
        if (this.mPendingRemovals.remove(viewHolder)) {
            view.setAlpha(1.0f);
            this.dispatchRemoveFinished(viewHolder);
        }
        if (this.mPendingAdditions.remove(viewHolder)) {
            view.setAlpha(1.0f);
            this.dispatchAddFinished(viewHolder);
        }
        for (n = this.mChangesList.size() - 1; n >= 0; --n) {
            arrayList = this.mChangesList.get(n);
            this.endChangeAnimation(arrayList, viewHolder);
            if (!arrayList.isEmpty()) continue;
            this.mChangesList.remove(n);
        }
        block2 : for (n = this.mMovesList.size() - 1; n >= 0; --n) {
            arrayList = this.mMovesList.get(n);
            for (int i = arrayList.size() - 1; i >= 0; --i) {
                if (((MoveInfo)arrayList.get((int)i)).holder != viewHolder) continue;
                view.setTranslationY(0.0f);
                view.setTranslationX(0.0f);
                this.dispatchMoveFinished(viewHolder);
                arrayList.remove(i);
                if (!arrayList.isEmpty()) continue block2;
                this.mMovesList.remove(n);
                continue block2;
            }
        }
        n = this.mAdditionsList.size() - 1;
        do {
            if (n < 0) {
                this.mRemoveAnimations.remove(viewHolder);
                this.mAddAnimations.remove(viewHolder);
                this.mChangeAnimations.remove(viewHolder);
                this.mMoveAnimations.remove(viewHolder);
                this.dispatchFinishedWhenDone();
                return;
            }
            arrayList = this.mAdditionsList.get(n);
            if (arrayList.remove(viewHolder)) {
                view.setAlpha(1.0f);
                this.dispatchAddFinished(viewHolder);
                if (arrayList.isEmpty()) {
                    this.mAdditionsList.remove(n);
                }
            }
            --n;
        } while (true);
    }

    @Override
    public void endAnimations() {
        int n;
        int n2;
        Object object;
        Object object2;
        for (n2 = this.mPendingMoves.size() - 1; n2 >= 0; --n2) {
            object = this.mPendingMoves.get(n2);
            object2 = object.holder.itemView;
            object2.setTranslationY(0.0f);
            object2.setTranslationX(0.0f);
            this.dispatchMoveFinished(((MoveInfo)object).holder);
            this.mPendingMoves.remove(n2);
        }
        for (n2 = this.mPendingRemovals.size() - 1; n2 >= 0; --n2) {
            this.dispatchRemoveFinished(this.mPendingRemovals.get(n2));
            this.mPendingRemovals.remove(n2);
        }
        for (n2 = this.mPendingAdditions.size() - 1; n2 >= 0; --n2) {
            object2 = this.mPendingAdditions.get(n2);
            ((RecyclerView.ViewHolder)object2).itemView.setAlpha(1.0f);
            this.dispatchAddFinished((RecyclerView.ViewHolder)object2);
            this.mPendingAdditions.remove(n2);
        }
        for (n2 = this.mPendingChanges.size() - 1; n2 >= 0; --n2) {
            this.endChangeAnimationIfNecessary(this.mPendingChanges.get(n2));
        }
        this.mPendingChanges.clear();
        if (!this.isRunning()) {
            return;
        }
        for (n2 = this.mMovesList.size() - 1; n2 >= 0; --n2) {
            object = this.mMovesList.get(n2);
            for (n = object.size() - 1; n >= 0; --n) {
                MoveInfo moveInfo = (MoveInfo)((ArrayList)object).get(n);
                object2 = moveInfo.holder.itemView;
                object2.setTranslationY(0.0f);
                object2.setTranslationX(0.0f);
                this.dispatchMoveFinished(moveInfo.holder);
                ((ArrayList)object).remove(n);
                if (!((ArrayList)object).isEmpty()) continue;
                this.mMovesList.remove(object);
            }
        }
        for (n2 = this.mAdditionsList.size() - 1; n2 >= 0; --n2) {
            object2 = this.mAdditionsList.get(n2);
            for (n = object2.size() - 1; n >= 0; --n) {
                object = (RecyclerView.ViewHolder)((ArrayList)object2).get(n);
                ((RecyclerView.ViewHolder)object).itemView.setAlpha(1.0f);
                this.dispatchAddFinished((RecyclerView.ViewHolder)object);
                ((ArrayList)object2).remove(n);
                if (!((ArrayList)object2).isEmpty()) continue;
                this.mAdditionsList.remove(object2);
            }
        }
        n2 = this.mChangesList.size() - 1;
        do {
            if (n2 < 0) {
                this.cancelAll(this.mRemoveAnimations);
                this.cancelAll(this.mMoveAnimations);
                this.cancelAll(this.mAddAnimations);
                this.cancelAll(this.mChangeAnimations);
                this.dispatchAnimationsFinished();
                return;
            }
            object2 = this.mChangesList.get(n2);
            for (n = object2.size() - 1; n >= 0; --n) {
                this.endChangeAnimationIfNecessary((ChangeInfo)((ArrayList)object2).get(n));
                if (!((ArrayList)object2).isEmpty()) continue;
                this.mChangesList.remove(object2);
            }
            --n2;
        } while (true);
    }

    @Override
    public boolean isRunning() {
        if (!this.mPendingAdditions.isEmpty()) return true;
        if (!this.mPendingChanges.isEmpty()) return true;
        if (!this.mPendingMoves.isEmpty()) return true;
        if (!this.mPendingRemovals.isEmpty()) return true;
        if (!this.mMoveAnimations.isEmpty()) return true;
        if (!this.mRemoveAnimations.isEmpty()) return true;
        if (!this.mAddAnimations.isEmpty()) return true;
        if (!this.mChangeAnimations.isEmpty()) return true;
        if (!this.mMovesList.isEmpty()) return true;
        if (!this.mAdditionsList.isEmpty()) return true;
        if (!this.mChangesList.isEmpty()) return true;
        return false;
    }

    @Override
    public void runPendingAnimations() {
        Runnable runnable2;
        boolean bl = this.mPendingRemovals.isEmpty() ^ true;
        boolean bl2 = this.mPendingMoves.isEmpty() ^ true;
        boolean bl3 = this.mPendingChanges.isEmpty() ^ true;
        boolean bl4 = this.mPendingAdditions.isEmpty() ^ true;
        if (!(bl || bl2 || bl4 || bl3)) {
            return;
        }
        final ArrayList<MoveInfo> arrayList = this.mPendingRemovals.iterator();
        while (arrayList.hasNext()) {
            this.animateRemoveImpl(arrayList.next());
        }
        this.mPendingRemovals.clear();
        if (bl2) {
            arrayList = new ArrayList();
            arrayList.addAll(this.mPendingMoves);
            this.mMovesList.add(arrayList);
            this.mPendingMoves.clear();
            runnable2 = new Runnable(){

                @Override
                public void run() {
                    Iterator iterator2 = arrayList.iterator();
                    do {
                        if (!iterator2.hasNext()) {
                            arrayList.clear();
                            DefaultItemAnimator.this.mMovesList.remove(arrayList);
                            return;
                        }
                        MoveInfo moveInfo = (MoveInfo)iterator2.next();
                        DefaultItemAnimator.this.animateMoveImpl(moveInfo.holder, moveInfo.fromX, moveInfo.fromY, moveInfo.toX, moveInfo.toY);
                    } while (true);
                }
            };
            if (bl) {
                ViewCompat.postOnAnimationDelayed(((MoveInfo)arrayList.get((int)0)).holder.itemView, runnable2, this.getRemoveDuration());
            } else {
                runnable2.run();
            }
        }
        if (bl3) {
            arrayList = new ArrayList<MoveInfo>();
            arrayList.addAll(this.mPendingChanges);
            this.mChangesList.add(arrayList);
            this.mPendingChanges.clear();
            runnable2 = new Runnable(){

                @Override
                public void run() {
                    Iterator iterator2 = arrayList.iterator();
                    do {
                        if (!iterator2.hasNext()) {
                            arrayList.clear();
                            DefaultItemAnimator.this.mChangesList.remove(arrayList);
                            return;
                        }
                        ChangeInfo changeInfo = (ChangeInfo)iterator2.next();
                        DefaultItemAnimator.this.animateChangeImpl(changeInfo);
                    } while (true);
                }
            };
            if (bl) {
                ViewCompat.postOnAnimationDelayed(((ChangeInfo)arrayList.get((int)0)).oldHolder.itemView, runnable2, this.getRemoveDuration());
            } else {
                runnable2.run();
            }
        }
        if (!bl4) return;
        arrayList = new ArrayList();
        arrayList.addAll(this.mPendingAdditions);
        this.mAdditionsList.add(arrayList);
        this.mPendingAdditions.clear();
        runnable2 = new Runnable(){

            @Override
            public void run() {
                Iterator iterator2 = arrayList.iterator();
                do {
                    if (!iterator2.hasNext()) {
                        arrayList.clear();
                        DefaultItemAnimator.this.mAdditionsList.remove(arrayList);
                        return;
                    }
                    RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder)iterator2.next();
                    DefaultItemAnimator.this.animateAddImpl(viewHolder);
                } while (true);
            }
        };
        if (!(bl || bl2 || bl3)) {
            runnable2.run();
            return;
        }
        long l = 0L;
        long l2 = bl ? this.getRemoveDuration() : 0L;
        long l3 = bl2 ? this.getMoveDuration() : 0L;
        if (bl3) {
            l = this.getChangeDuration();
        }
        l3 = Math.max(l3, l);
        ViewCompat.postOnAnimationDelayed(((RecyclerView.ViewHolder)arrayList.get((int)0)).itemView, runnable2, l2 + l3);
    }

    private static class ChangeInfo {
        public int fromX;
        public int fromY;
        public RecyclerView.ViewHolder newHolder;
        public RecyclerView.ViewHolder oldHolder;
        public int toX;
        public int toY;

        private ChangeInfo(RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2) {
            this.oldHolder = viewHolder;
            this.newHolder = viewHolder2;
        }

        ChangeInfo(RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2, int n, int n2, int n3, int n4) {
            this(viewHolder, viewHolder2);
            this.fromX = n;
            this.fromY = n2;
            this.toX = n3;
            this.toY = n4;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ChangeInfo{oldHolder=");
            stringBuilder.append(this.oldHolder);
            stringBuilder.append(", newHolder=");
            stringBuilder.append(this.newHolder);
            stringBuilder.append(", fromX=");
            stringBuilder.append(this.fromX);
            stringBuilder.append(", fromY=");
            stringBuilder.append(this.fromY);
            stringBuilder.append(", toX=");
            stringBuilder.append(this.toX);
            stringBuilder.append(", toY=");
            stringBuilder.append(this.toY);
            stringBuilder.append('}');
            return stringBuilder.toString();
        }
    }

    private static class MoveInfo {
        public int fromX;
        public int fromY;
        public RecyclerView.ViewHolder holder;
        public int toX;
        public int toY;

        MoveInfo(RecyclerView.ViewHolder viewHolder, int n, int n2, int n3, int n4) {
            this.holder = viewHolder;
            this.fromX = n;
            this.fromY = n2;
            this.toX = n3;
            this.toY = n4;
        }
    }

}

