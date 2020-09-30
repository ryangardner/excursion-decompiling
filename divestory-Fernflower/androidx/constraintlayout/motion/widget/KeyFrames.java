package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.util.Log;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import org.xmlpull.v1.XmlPullParser;

public class KeyFrames {
   private static final String TAG = "KeyFrames";
   public static final int UNSET = -1;
   static HashMap<String, Constructor<? extends Key>> sKeyMakers;
   private HashMap<Integer, ArrayList<Key>> mFramesMap;

   static {
      HashMap var0 = new HashMap();
      sKeyMakers = var0;

      try {
         var0.put("KeyAttribute", KeyAttributes.class.getConstructor());
         sKeyMakers.put("KeyPosition", KeyPosition.class.getConstructor());
         sKeyMakers.put("KeyCycle", KeyCycle.class.getConstructor());
         sKeyMakers.put("KeyTimeCycle", KeyTimeCycle.class.getConstructor());
         sKeyMakers.put("KeyTrigger", KeyTrigger.class.getConstructor());
      } catch (NoSuchMethodException var1) {
         Log.e("KeyFrames", "unable to load", var1);
      }

   }

   public KeyFrames(Context param1, XmlPullParser param2) {
      // $FF: Couldn't be decompiled
   }

   private void addKey(Key var1) {
      if (!this.mFramesMap.containsKey(var1.mTargetId)) {
         this.mFramesMap.put(var1.mTargetId, new ArrayList());
      }

      ((ArrayList)this.mFramesMap.get(var1.mTargetId)).add(var1);
   }

   static String name(int var0, Context var1) {
      return var1.getResources().getResourceEntryName(var0);
   }

   public void addFrames(MotionController var1) {
      ArrayList var2 = (ArrayList)this.mFramesMap.get(var1.mId);
      if (var2 != null) {
         var1.addKeys(var2);
      }

      var2 = (ArrayList)this.mFramesMap.get(-1);
      if (var2 != null) {
         Iterator var3 = var2.iterator();

         while(var3.hasNext()) {
            Key var4 = (Key)var3.next();
            if (var4.matches(((ConstraintLayout.LayoutParams)var1.mView.getLayoutParams()).constraintTag)) {
               var1.addKey(var4);
            }
         }
      }

   }

   public ArrayList<Key> getKeyFramesForView(int var1) {
      return (ArrayList)this.mFramesMap.get(var1);
   }

   public Set<Integer> getKeys() {
      return this.mFramesMap.keySet();
   }
}
