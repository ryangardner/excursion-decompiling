/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.constraintlayout.motion.widget;

import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.motion.widget.MotionScene;
import androidx.constraintlayout.widget.ConstraintSet;
import java.util.ArrayList;

public class TransitionBuilder {
    private static final String TAG = "TransitionBuilder";

    public static MotionScene.Transition buildTransition(MotionScene motionScene, int n, int n2, ConstraintSet constraintSet, int n3, ConstraintSet constraintSet2) {
        MotionScene.Transition transition = new MotionScene.Transition(n, motionScene, n2, n3);
        TransitionBuilder.updateConstraintSetInMotionScene(motionScene, transition, constraintSet, constraintSet2);
        return transition;
    }

    private static void updateConstraintSetInMotionScene(MotionScene motionScene, MotionScene.Transition transition, ConstraintSet constraintSet, ConstraintSet constraintSet2) {
        int n = transition.getStartConstraintSetId();
        int n2 = transition.getEndConstraintSetId();
        motionScene.setConstraintSet(n, constraintSet);
        motionScene.setConstraintSet(n2, constraintSet2);
    }

    public static void validate(MotionLayout motionLayout) {
        if (motionLayout.mScene == null) throw new RuntimeException("Invalid motion layout. Layout missing Motion Scene.");
        MotionScene motionScene = motionLayout.mScene;
        if (!motionScene.validateLayout(motionLayout)) throw new RuntimeException("MotionLayout doesn't have the right motion scene.");
        if (motionScene.mCurrentTransition == null) throw new RuntimeException("Invalid motion layout. Motion Scene doesn't have any transition.");
        if (motionScene.getDefinedTransitions().isEmpty()) throw new RuntimeException("Invalid motion layout. Motion Scene doesn't have any transition.");
    }
}

