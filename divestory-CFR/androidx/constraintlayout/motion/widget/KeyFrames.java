/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.util.Xml
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 */
package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.motion.widget.Key;
import androidx.constraintlayout.motion.widget.KeyAttributes;
import androidx.constraintlayout.motion.widget.KeyCycle;
import androidx.constraintlayout.motion.widget.KeyPosition;
import androidx.constraintlayout.motion.widget.KeyTimeCycle;
import androidx.constraintlayout.motion.widget.KeyTrigger;
import androidx.constraintlayout.motion.widget.MotionController;
import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class KeyFrames {
    private static final String TAG = "KeyFrames";
    public static final int UNSET = -1;
    static HashMap<String, Constructor<? extends Key>> sKeyMakers;
    private HashMap<Integer, ArrayList<Key>> mFramesMap = new HashMap();

    static {
        HashMap hashMap = new HashMap();
        sKeyMakers = hashMap;
        try {
            hashMap.put("KeyAttribute", KeyAttributes.class.getConstructor(new Class[0]));
            sKeyMakers.put("KeyPosition", KeyPosition.class.getConstructor(new Class[0]));
            sKeyMakers.put("KeyCycle", KeyCycle.class.getConstructor(new Class[0]));
            sKeyMakers.put("KeyTimeCycle", KeyTimeCycle.class.getConstructor(new Class[0]));
            sKeyMakers.put("KeyTrigger", KeyTrigger.class.getConstructor(new Class[0]));
            return;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            Log.e((String)TAG, (String)"unable to load", (Throwable)noSuchMethodException);
        }
    }

    /*
     * Loose catch block
     * Enabled unnecessary exception pruning
     */
    public KeyFrames(Context context, XmlPullParser xmlPullParser) {
        Key key = null;
        try {
            int n = xmlPullParser.getEventType();
            while (n != 1) {
                Key key2;
                if (n != 2) {
                    if (n != 3) {
                        key2 = key;
                    } else {
                        key2 = key;
                        if ("KeyFrameSet".equals(xmlPullParser.getName())) {
                            return;
                        }
                    }
                } else {
                    String string2 = xmlPullParser.getName();
                    boolean bl = sKeyMakers.containsKey(string2);
                    if (bl) {
                        block18 : {
                            block19 : {
                                key2 = sKeyMakers.get(string2).newInstance(new Object[0]);
                                try {
                                    key2.load(context, Xml.asAttributeSet((XmlPullParser)xmlPullParser));
                                    this.addKey(key2);
                                    key = key2;
                                    break block18;
                                }
                                catch (Exception exception) {
                                    key = key2;
                                    break block19;
                                }
                                catch (Exception exception) {
                                    // empty catch block
                                }
                            }
                            Log.e((String)TAG, (String)"unable to create ", (Throwable)((Object)string2));
                        }
                        key2 = key;
                    } else {
                        key2 = key;
                        if (string2.equalsIgnoreCase("CustomAttribute")) {
                            key2 = key;
                            if (key != null) {
                                key2 = key;
                                if (key.mCustomConstraints != null) {
                                    ConstraintAttribute.parse(context, xmlPullParser, key.mCustomConstraints);
                                    key2 = key;
                                }
                            }
                        }
                    }
                }
                n = xmlPullParser.next();
                key = key2;
            }
            return;
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
            return;
        }
        catch (XmlPullParserException xmlPullParserException) {
            xmlPullParserException.printStackTrace();
        }
    }

    private void addKey(Key key) {
        if (!this.mFramesMap.containsKey(key.mTargetId)) {
            this.mFramesMap.put(key.mTargetId, new ArrayList());
        }
        this.mFramesMap.get(key.mTargetId).add(key);
    }

    static String name(int n, Context context) {
        return context.getResources().getResourceEntryName(n);
    }

    public void addFrames(MotionController motionController) {
        Object object = this.mFramesMap.get(motionController.mId);
        if (object != null) {
            motionController.addKeys((ArrayList<Key>)object);
        }
        if ((object = this.mFramesMap.get(-1)) == null) return;
        Iterator<Key> iterator2 = ((ArrayList)object).iterator();
        while (iterator2.hasNext()) {
            object = iterator2.next();
            if (!((Key)object).matches(((ConstraintLayout.LayoutParams)motionController.mView.getLayoutParams()).constraintTag)) continue;
            motionController.addKey((Key)object);
        }
    }

    public ArrayList<Key> getKeyFramesForView(int n) {
        return this.mFramesMap.get(n);
    }

    public Set<Integer> getKeys() {
        return this.mFramesMap.keySet();
    }
}

