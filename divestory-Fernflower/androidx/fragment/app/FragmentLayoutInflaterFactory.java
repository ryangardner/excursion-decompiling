package androidx.fragment.app;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.LayoutInflater.Factory2;
import androidx.fragment.R;

class FragmentLayoutInflaterFactory implements Factory2 {
   private static final String TAG = "FragmentManager";
   private final FragmentManager mFragmentManager;

   FragmentLayoutInflaterFactory(FragmentManager var1) {
      this.mFragmentManager = var1;
   }

   public View onCreateView(View var1, String var2, Context var3, AttributeSet var4) {
      if (FragmentContainerView.class.getName().equals(var2)) {
         return new FragmentContainerView(var3, var4, this.mFragmentManager);
      } else {
         boolean var5 = "fragment".equals(var2);
         var2 = null;
         if (!var5) {
            return null;
         } else {
            String var6 = var4.getAttributeValue((String)null, "class");
            TypedArray var7 = var3.obtainStyledAttributes(var4, R.styleable.Fragment);
            String var8 = var6;
            if (var6 == null) {
               var8 = var7.getString(R.styleable.Fragment_android_name);
            }

            int var9 = var7.getResourceId(R.styleable.Fragment_android_id, -1);
            var6 = var7.getString(R.styleable.Fragment_android_tag);
            var7.recycle();
            if (var8 != null && FragmentFactory.isFragmentClass(var3.getClassLoader(), var8)) {
               int var10;
               if (var1 != null) {
                  var10 = var1.getId();
               } else {
                  var10 = 0;
               }

               StringBuilder var15;
               if (var10 == -1 && var9 == -1 && var6 == null) {
                  var15 = new StringBuilder();
                  var15.append(var4.getPositionDescription());
                  var15.append(": Must specify unique android:id, android:tag, or have a parent with an id for ");
                  var15.append(var8);
                  throw new IllegalArgumentException(var15.toString());
               } else {
                  Fragment var12 = var2;
                  if (var9 != -1) {
                     var12 = this.mFragmentManager.findFragmentById(var9);
                  }

                  Fragment var13 = var12;
                  if (var12 == null) {
                     var13 = var12;
                     if (var6 != null) {
                        var13 = this.mFragmentManager.findFragmentByTag(var6);
                     }
                  }

                  var12 = var13;
                  if (var13 == null) {
                     var12 = var13;
                     if (var10 != -1) {
                        var12 = this.mFragmentManager.findFragmentById(var10);
                     }
                  }

                  if (FragmentManager.isLoggingEnabled(2)) {
                     StringBuilder var14 = new StringBuilder();
                     var14.append("onCreateView: id=0x");
                     var14.append(Integer.toHexString(var9));
                     var14.append(" fname=");
                     var14.append(var8);
                     var14.append(" existing=");
                     var14.append(var12);
                     Log.v("FragmentManager", var14.toString());
                  }

                  if (var12 == null) {
                     var12 = this.mFragmentManager.getFragmentFactory().instantiate(var3.getClassLoader(), var8);
                     var12.mFromLayout = true;
                     int var11;
                     if (var9 != 0) {
                        var11 = var9;
                     } else {
                        var11 = var10;
                     }

                     var12.mFragmentId = var11;
                     var12.mContainerId = var10;
                     var12.mTag = var6;
                     var12.mInLayout = true;
                     var12.mFragmentManager = this.mFragmentManager;
                     var12.mHost = this.mFragmentManager.mHost;
                     var12.onInflate(this.mFragmentManager.mHost.getContext(), var4, var12.mSavedFragmentState);
                     this.mFragmentManager.addFragment(var12);
                     this.mFragmentManager.moveToState(var12);
                  } else {
                     if (var12.mInLayout) {
                        var15 = new StringBuilder();
                        var15.append(var4.getPositionDescription());
                        var15.append(": Duplicate id 0x");
                        var15.append(Integer.toHexString(var9));
                        var15.append(", tag ");
                        var15.append(var6);
                        var15.append(", or parent id 0x");
                        var15.append(Integer.toHexString(var10));
                        var15.append(" with another fragment for ");
                        var15.append(var8);
                        throw new IllegalArgumentException(var15.toString());
                     }

                     var12.mInLayout = true;
                     var12.mHost = this.mFragmentManager.mHost;
                     var12.onInflate(this.mFragmentManager.mHost.getContext(), var4, var12.mSavedFragmentState);
                  }

                  if (this.mFragmentManager.mCurState < 1 && var12.mFromLayout) {
                     this.mFragmentManager.moveToState(var12, 1);
                  } else {
                     this.mFragmentManager.moveToState(var12);
                  }

                  if (var12.mView != null) {
                     if (var9 != 0) {
                        var12.mView.setId(var9);
                     }

                     if (var12.mView.getTag() == null) {
                        var12.mView.setTag(var6);
                     }

                     return var12.mView;
                  } else {
                     var15 = new StringBuilder();
                     var15.append("Fragment ");
                     var15.append(var8);
                     var15.append(" did not create a view.");
                     throw new IllegalStateException(var15.toString());
                  }
               }
            } else {
               return null;
            }
         }
      }
   }

   public View onCreateView(String var1, Context var2, AttributeSet var3) {
      return this.onCreateView((View)null, var1, var2, var3);
   }
}
