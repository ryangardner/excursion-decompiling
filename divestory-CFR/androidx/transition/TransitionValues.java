/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package androidx.transition;

import android.view.View;
import androidx.transition.Transition;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class TransitionValues {
    final ArrayList<Transition> mTargetedTransitions = new ArrayList();
    public final Map<String, Object> values = new HashMap<String, Object>();
    public View view;

    @Deprecated
    public TransitionValues() {
    }

    public TransitionValues(View view) {
        this.view = view;
    }

    public boolean equals(Object object) {
        if (!(object instanceof TransitionValues)) return false;
        View view = this.view;
        object = (TransitionValues)object;
        if (view != ((TransitionValues)object).view) return false;
        if (!this.values.equals(((TransitionValues)object).values)) return false;
        return true;
    }

    public int hashCode() {
        return this.view.hashCode() * 31 + this.values.hashCode();
    }

    public String toString() {
        CharSequence charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("TransitionValues@");
        ((StringBuilder)charSequence).append(Integer.toHexString(this.hashCode()));
        ((StringBuilder)charSequence).append(":\n");
        charSequence = ((StringBuilder)charSequence).toString();
        Object object = new StringBuilder();
        ((StringBuilder)object).append((String)charSequence);
        ((StringBuilder)object).append("    view = ");
        ((StringBuilder)object).append((Object)this.view);
        ((StringBuilder)object).append("\n");
        object = ((StringBuilder)object).toString();
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append((String)object);
        ((StringBuilder)charSequence).append("    values:");
        charSequence = ((StringBuilder)charSequence).toString();
        object = this.values.keySet().iterator();
        while (object.hasNext()) {
            String string2 = (String)object.next();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append((String)charSequence);
            stringBuilder.append("    ");
            stringBuilder.append(string2);
            stringBuilder.append(": ");
            stringBuilder.append(this.values.get(string2));
            stringBuilder.append("\n");
            charSequence = stringBuilder.toString();
        }
        return charSequence;
    }
}

