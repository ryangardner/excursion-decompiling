/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Application
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentSender
 *  android.content.IntentSender$SendIntentException
 *  android.content.res.Configuration
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.view.LayoutInflater
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.Window
 *  android.view.WindowManager
 *  android.view.WindowManager$LayoutParams
 */
package androidx.fragment.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import androidx.activity.ComponentActivity;
import androidx.activity.OnBackPressedDispatcher;
import androidx.activity.OnBackPressedDispatcherOwner;
import androidx.collection.SparseArrayCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.app.SharedElementCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentController;
import androidx.fragment.app.FragmentHostCallback;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.loader.app.LoaderManager;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

public class FragmentActivity
extends ComponentActivity
implements ActivityCompat.OnRequestPermissionsResultCallback,
ActivityCompat.RequestPermissionsRequestCodeValidator {
    static final String ALLOCATED_REQUEST_INDICIES_TAG = "android:support:request_indicies";
    static final String FRAGMENTS_TAG = "android:support:fragments";
    static final int MAX_NUM_PENDING_FRAGMENT_ACTIVITY_RESULTS = 65534;
    static final String NEXT_CANDIDATE_REQUEST_INDEX_TAG = "android:support:next_request_index";
    static final String REQUEST_FRAGMENT_WHO_TAG = "android:support:request_fragment_who";
    private static final String TAG = "FragmentActivity";
    boolean mCreated;
    final LifecycleRegistry mFragmentLifecycleRegistry = new LifecycleRegistry(this);
    final FragmentController mFragments = FragmentController.createController(new HostCallbacks());
    int mNextCandidateRequestIndex;
    SparseArrayCompat<String> mPendingFragmentActivityResults;
    boolean mRequestedPermissionsFromFragment;
    boolean mResumed;
    boolean mStartedActivityFromFragment;
    boolean mStartedIntentSenderFromFragment;
    boolean mStopped = true;

    public FragmentActivity() {
    }

    public FragmentActivity(int n) {
        super(n);
    }

    private int allocateRequestIndex(Fragment fragment) {
        if (this.mPendingFragmentActivityResults.size() >= 65534) throw new IllegalStateException("Too many pending Fragment activity results.");
        do {
            if (this.mPendingFragmentActivityResults.indexOfKey(this.mNextCandidateRequestIndex) < 0) {
                int n = this.mNextCandidateRequestIndex;
                this.mPendingFragmentActivityResults.put(n, fragment.mWho);
                this.mNextCandidateRequestIndex = (this.mNextCandidateRequestIndex + 1) % 65534;
                return n;
            }
            this.mNextCandidateRequestIndex = (this.mNextCandidateRequestIndex + 1) % 65534;
        } while (true);
    }

    static void checkForValidRequestCode(int n) {
        if ((n & -65536) != 0) throw new IllegalArgumentException("Can only use lower 16 bits for requestCode");
    }

    private void markFragmentsCreated() {
        while (FragmentActivity.markState(this.getSupportFragmentManager(), Lifecycle.State.CREATED)) {
        }
    }

    private static boolean markState(FragmentManager object, Lifecycle.State state) {
        object = ((FragmentManager)object).getFragments().iterator();
        boolean bl = false;
        while (object.hasNext()) {
            Fragment fragment = (Fragment)object.next();
            if (fragment == null) continue;
            boolean bl2 = bl;
            if (fragment.getHost() != null) {
                bl2 = bl | FragmentActivity.markState(fragment.getChildFragmentManager(), state);
            }
            bl = bl2;
            if (!fragment.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) continue;
            fragment.mLifecycleRegistry.setCurrentState(state);
            bl = true;
        }
        return bl;
    }

    final View dispatchFragmentsOnCreateView(View view, String string2, Context context, AttributeSet attributeSet) {
        return this.mFragments.onCreateView(view, string2, context, attributeSet);
    }

    public void dump(String string2, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        super.dump(string2, fileDescriptor, printWriter, arrstring);
        printWriter.print(string2);
        printWriter.print("Local FragmentActivity ");
        printWriter.print(Integer.toHexString(System.identityHashCode(this)));
        printWriter.println(" State:");
        CharSequence charSequence = new StringBuilder();
        charSequence.append(string2);
        charSequence.append("  ");
        charSequence = charSequence.toString();
        printWriter.print((String)charSequence);
        printWriter.print("mCreated=");
        printWriter.print(this.mCreated);
        printWriter.print(" mResumed=");
        printWriter.print(this.mResumed);
        printWriter.print(" mStopped=");
        printWriter.print(this.mStopped);
        if (this.getApplication() != null) {
            LoaderManager.getInstance(this).dump((String)charSequence, fileDescriptor, printWriter, arrstring);
        }
        this.mFragments.getSupportFragmentManager().dump(string2, fileDescriptor, printWriter, arrstring);
    }

    public FragmentManager getSupportFragmentManager() {
        return this.mFragments.getSupportFragmentManager();
    }

    @Deprecated
    public LoaderManager getSupportLoaderManager() {
        return LoaderManager.getInstance(this);
    }

    protected void onActivityResult(int n, int n2, Intent object) {
        this.mFragments.noteStateNotSaved();
        int n3 = n >> 16;
        if (n3 != 0) {
            String string2 = this.mPendingFragmentActivityResults.get(--n3);
            this.mPendingFragmentActivityResults.remove(n3);
            if (string2 == null) {
                Log.w((String)TAG, (String)"Activity result delivered for unknown Fragment.");
                return;
            }
            Fragment fragment = this.mFragments.findFragmentByWho(string2);
            if (fragment == null) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Activity result no fragment exists for who: ");
                ((StringBuilder)object).append(string2);
                Log.w((String)TAG, (String)((StringBuilder)object).toString());
                return;
            }
            fragment.onActivityResult(n & 65535, n2, (Intent)object);
            return;
        }
        ActivityCompat.PermissionCompatDelegate permissionCompatDelegate = ActivityCompat.getPermissionCompatDelegate();
        if (permissionCompatDelegate != null && permissionCompatDelegate.onActivityResult(this, n, n2, (Intent)object)) {
            return;
        }
        super.onActivityResult(n, n2, (Intent)object);
    }

    public void onAttachFragment(Fragment fragment) {
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mFragments.noteStateNotSaved();
        this.mFragments.dispatchConfigurationChanged(configuration);
    }

    @Override
    protected void onCreate(Bundle bundle) {
        this.mFragments.attachHost(null);
        if (bundle != null) {
            String[] arrstring = bundle.getParcelable(FRAGMENTS_TAG);
            this.mFragments.restoreSaveState((Parcelable)arrstring);
            if (bundle.containsKey(NEXT_CANDIDATE_REQUEST_INDEX_TAG)) {
                this.mNextCandidateRequestIndex = bundle.getInt(NEXT_CANDIDATE_REQUEST_INDEX_TAG);
                int[] arrn = bundle.getIntArray(ALLOCATED_REQUEST_INDICIES_TAG);
                arrstring = bundle.getStringArray(REQUEST_FRAGMENT_WHO_TAG);
                if (arrn != null && arrstring != null && arrn.length == arrstring.length) {
                    this.mPendingFragmentActivityResults = new SparseArrayCompat(arrn.length);
                    for (int i = 0; i < arrn.length; ++i) {
                        this.mPendingFragmentActivityResults.put(arrn[i], arrstring[i]);
                    }
                } else {
                    Log.w((String)TAG, (String)"Invalid requestCode mapping in savedInstanceState.");
                }
            }
        }
        if (this.mPendingFragmentActivityResults == null) {
            this.mPendingFragmentActivityResults = new SparseArrayCompat();
            this.mNextCandidateRequestIndex = 0;
        }
        super.onCreate(bundle);
        this.mFragmentLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
        this.mFragments.dispatchCreate();
    }

    public boolean onCreatePanelMenu(int n, Menu menu2) {
        if (n != 0) return super.onCreatePanelMenu(n, menu2);
        return super.onCreatePanelMenu(n, menu2) | this.mFragments.dispatchCreateOptionsMenu(menu2, this.getMenuInflater());
    }

    public View onCreateView(View view, String string2, Context context, AttributeSet attributeSet) {
        View view2 = this.dispatchFragmentsOnCreateView(view, string2, context, attributeSet);
        if (view2 != null) return view2;
        return super.onCreateView(view, string2, context, attributeSet);
    }

    public View onCreateView(String string2, Context context, AttributeSet attributeSet) {
        View view = this.dispatchFragmentsOnCreateView(null, string2, context, attributeSet);
        if (view != null) return view;
        return super.onCreateView(string2, context, attributeSet);
    }

    protected void onDestroy() {
        super.onDestroy();
        this.mFragments.dispatchDestroy();
        this.mFragmentLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
    }

    public void onLowMemory() {
        super.onLowMemory();
        this.mFragments.dispatchLowMemory();
    }

    public boolean onMenuItemSelected(int n, MenuItem menuItem) {
        if (super.onMenuItemSelected(n, menuItem)) {
            return true;
        }
        if (n == 0) return this.mFragments.dispatchOptionsItemSelected(menuItem);
        if (n == 6) return this.mFragments.dispatchContextItemSelected(menuItem);
        return false;
    }

    public void onMultiWindowModeChanged(boolean bl) {
        this.mFragments.dispatchMultiWindowModeChanged(bl);
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        this.mFragments.noteStateNotSaved();
    }

    public void onPanelClosed(int n, Menu menu2) {
        if (n == 0) {
            this.mFragments.dispatchOptionsMenuClosed(menu2);
        }
        super.onPanelClosed(n, menu2);
    }

    protected void onPause() {
        super.onPause();
        this.mResumed = false;
        this.mFragments.dispatchPause();
        this.mFragmentLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE);
    }

    public void onPictureInPictureModeChanged(boolean bl) {
        this.mFragments.dispatchPictureInPictureModeChanged(bl);
    }

    protected void onPostResume() {
        super.onPostResume();
        this.onResumeFragments();
    }

    @Deprecated
    protected boolean onPrepareOptionsPanel(View view, Menu menu2) {
        return super.onPreparePanel(0, view, menu2);
    }

    public boolean onPreparePanel(int n, View view, Menu menu2) {
        if (n != 0) return super.onPreparePanel(n, view, menu2);
        return this.onPrepareOptionsPanel(view, menu2) | this.mFragments.dispatchPrepareOptionsMenu(menu2);
    }

    @Override
    public void onRequestPermissionsResult(int n, String[] object, int[] arrn) {
        this.mFragments.noteStateNotSaved();
        int n2 = n >> 16 & 65535;
        if (n2 == 0) return;
        String string2 = this.mPendingFragmentActivityResults.get(--n2);
        this.mPendingFragmentActivityResults.remove(n2);
        if (string2 == null) {
            Log.w((String)TAG, (String)"Activity result delivered for unknown Fragment.");
            return;
        }
        Fragment fragment = this.mFragments.findFragmentByWho(string2);
        if (fragment == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Activity result no fragment exists for who: ");
            ((StringBuilder)object).append(string2);
            Log.w((String)TAG, (String)((StringBuilder)object).toString());
            return;
        }
        fragment.onRequestPermissionsResult(n & 65535, (String[])object, arrn);
    }

    protected void onResume() {
        super.onResume();
        this.mResumed = true;
        this.mFragments.noteStateNotSaved();
        this.mFragments.execPendingActions();
    }

    protected void onResumeFragments() {
        this.mFragmentLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
        this.mFragments.dispatchResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        this.markFragmentsCreated();
        this.mFragmentLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
        String[] arrstring = this.mFragments.saveAllState();
        if (arrstring != null) {
            bundle.putParcelable(FRAGMENTS_TAG, (Parcelable)arrstring);
        }
        if (this.mPendingFragmentActivityResults.size() <= 0) return;
        bundle.putInt(NEXT_CANDIDATE_REQUEST_INDEX_TAG, this.mNextCandidateRequestIndex);
        int[] arrn = new int[this.mPendingFragmentActivityResults.size()];
        arrstring = new String[this.mPendingFragmentActivityResults.size()];
        int n = 0;
        do {
            if (n >= this.mPendingFragmentActivityResults.size()) {
                bundle.putIntArray(ALLOCATED_REQUEST_INDICIES_TAG, arrn);
                bundle.putStringArray(REQUEST_FRAGMENT_WHO_TAG, arrstring);
                return;
            }
            arrn[n] = this.mPendingFragmentActivityResults.keyAt(n);
            arrstring[n] = this.mPendingFragmentActivityResults.valueAt(n);
            ++n;
        } while (true);
    }

    protected void onStart() {
        super.onStart();
        this.mStopped = false;
        if (!this.mCreated) {
            this.mCreated = true;
            this.mFragments.dispatchActivityCreated();
        }
        this.mFragments.noteStateNotSaved();
        this.mFragments.execPendingActions();
        this.mFragmentLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START);
        this.mFragments.dispatchStart();
    }

    public void onStateNotSaved() {
        this.mFragments.noteStateNotSaved();
    }

    protected void onStop() {
        super.onStop();
        this.mStopped = true;
        this.markFragmentsCreated();
        this.mFragments.dispatchStop();
        this.mFragmentLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
    }

    void requestPermissionsFromFragment(Fragment fragment, String[] arrstring, int n) {
        if (n == -1) {
            ActivityCompat.requestPermissions(this, arrstring, n);
            return;
        }
        FragmentActivity.checkForValidRequestCode(n);
        try {
            this.mRequestedPermissionsFromFragment = true;
            ActivityCompat.requestPermissions(this, arrstring, (this.allocateRequestIndex(fragment) + 1 << 16) + (n & 65535));
            return;
        }
        finally {
            this.mRequestedPermissionsFromFragment = false;
        }
    }

    public void setEnterSharedElementCallback(SharedElementCallback sharedElementCallback) {
        ActivityCompat.setEnterSharedElementCallback(this, sharedElementCallback);
    }

    public void setExitSharedElementCallback(SharedElementCallback sharedElementCallback) {
        ActivityCompat.setExitSharedElementCallback(this, sharedElementCallback);
    }

    public void startActivityForResult(Intent intent, int n) {
        if (!this.mStartedActivityFromFragment && n != -1) {
            FragmentActivity.checkForValidRequestCode(n);
        }
        super.startActivityForResult(intent, n);
    }

    public void startActivityForResult(Intent intent, int n, Bundle bundle) {
        if (!this.mStartedActivityFromFragment && n != -1) {
            FragmentActivity.checkForValidRequestCode(n);
        }
        super.startActivityForResult(intent, n, bundle);
    }

    public void startActivityFromFragment(Fragment fragment, Intent intent, int n) {
        this.startActivityFromFragment(fragment, intent, n, null);
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    public void startActivityFromFragment(Fragment var1_1, Intent var2_3, int var3_4, Bundle var4_5) {
        this.mStartedActivityFromFragment = true;
        if (var3_4 != -1) ** GOTO lbl6
        try {
            ActivityCompat.startActivityForResult(this, var2_3, -1, var4_5);
            return;
lbl6: // 1 sources:
            FragmentActivity.checkForValidRequestCode(var3_4);
            ActivityCompat.startActivityForResult(this, var2_3, (this.allocateRequestIndex(var1_1) + 1 << 16) + (var3_4 & 65535), var4_5);
            this.mStartedActivityFromFragment = false;
            return;
        }
        finally {
            this.mStartedActivityFromFragment = false;
        }
    }

    public void startIntentSenderForResult(IntentSender intentSender, int n, Intent intent, int n2, int n3, int n4) throws IntentSender.SendIntentException {
        if (!this.mStartedIntentSenderFromFragment && n != -1) {
            FragmentActivity.checkForValidRequestCode(n);
        }
        super.startIntentSenderForResult(intentSender, n, intent, n2, n3, n4);
    }

    public void startIntentSenderForResult(IntentSender intentSender, int n, Intent intent, int n2, int n3, int n4, Bundle bundle) throws IntentSender.SendIntentException {
        if (!this.mStartedIntentSenderFromFragment && n != -1) {
            FragmentActivity.checkForValidRequestCode(n);
        }
        super.startIntentSenderForResult(intentSender, n, intent, n2, n3, n4, bundle);
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    public void startIntentSenderFromFragment(Fragment var1_1, IntentSender var2_3, int var3_4, Intent var4_5, int var5_6, int var6_7, int var7_8, Bundle var8_9) throws IntentSender.SendIntentException {
        this.mStartedIntentSenderFromFragment = true;
        if (var3_4 != -1) ** GOTO lbl6
        try {
            ActivityCompat.startIntentSenderForResult(this, var2_3, var3_4, var4_5, var5_6, var6_7, var7_8, var8_9);
            return;
lbl6: // 1 sources:
            FragmentActivity.checkForValidRequestCode(var3_4);
            ActivityCompat.startIntentSenderForResult(this, var2_3, (this.allocateRequestIndex(var1_1) + 1 << 16) + (var3_4 & 65535), var4_5, var5_6, var6_7, var7_8, var8_9);
            this.mStartedIntentSenderFromFragment = false;
            return;
        }
        finally {
            this.mStartedIntentSenderFromFragment = false;
        }
    }

    public void supportFinishAfterTransition() {
        ActivityCompat.finishAfterTransition(this);
    }

    @Deprecated
    public void supportInvalidateOptionsMenu() {
        this.invalidateOptionsMenu();
    }

    public void supportPostponeEnterTransition() {
        ActivityCompat.postponeEnterTransition(this);
    }

    public void supportStartPostponedEnterTransition() {
        ActivityCompat.startPostponedEnterTransition(this);
    }

    @Override
    public final void validateRequestPermissionsRequestCode(int n) {
        if (this.mRequestedPermissionsFromFragment) return;
        if (n == -1) return;
        FragmentActivity.checkForValidRequestCode(n);
    }

    class HostCallbacks
    extends FragmentHostCallback<FragmentActivity>
    implements ViewModelStoreOwner,
    OnBackPressedDispatcherOwner {
        public HostCallbacks() {
            super(FragmentActivity.this);
        }

        @Override
        public Lifecycle getLifecycle() {
            return FragmentActivity.this.mFragmentLifecycleRegistry;
        }

        @Override
        public OnBackPressedDispatcher getOnBackPressedDispatcher() {
            return FragmentActivity.this.getOnBackPressedDispatcher();
        }

        @Override
        public ViewModelStore getViewModelStore() {
            return FragmentActivity.this.getViewModelStore();
        }

        @Override
        public void onAttachFragment(Fragment fragment) {
            FragmentActivity.this.onAttachFragment(fragment);
        }

        @Override
        public void onDump(String string2, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
            FragmentActivity.this.dump(string2, fileDescriptor, printWriter, arrstring);
        }

        @Override
        public View onFindViewById(int n) {
            return FragmentActivity.this.findViewById(n);
        }

        @Override
        public FragmentActivity onGetHost() {
            return FragmentActivity.this;
        }

        @Override
        public LayoutInflater onGetLayoutInflater() {
            return FragmentActivity.this.getLayoutInflater().cloneInContext((Context)FragmentActivity.this);
        }

        @Override
        public int onGetWindowAnimations() {
            Window window = FragmentActivity.this.getWindow();
            if (window != null) return window.getAttributes().windowAnimations;
            return 0;
        }

        @Override
        public boolean onHasView() {
            Window window = FragmentActivity.this.getWindow();
            if (window == null) return false;
            if (window.peekDecorView() == null) return false;
            return true;
        }

        @Override
        public boolean onHasWindowAnimations() {
            if (FragmentActivity.this.getWindow() == null) return false;
            return true;
        }

        @Override
        public void onRequestPermissionsFromFragment(Fragment fragment, String[] arrstring, int n) {
            FragmentActivity.this.requestPermissionsFromFragment(fragment, arrstring, n);
        }

        @Override
        public boolean onShouldSaveFragmentState(Fragment fragment) {
            return FragmentActivity.this.isFinishing() ^ true;
        }

        @Override
        public boolean onShouldShowRequestPermissionRationale(String string2) {
            return ActivityCompat.shouldShowRequestPermissionRationale(FragmentActivity.this, string2);
        }

        @Override
        public void onStartActivityFromFragment(Fragment fragment, Intent intent, int n) {
            FragmentActivity.this.startActivityFromFragment(fragment, intent, n);
        }

        @Override
        public void onStartActivityFromFragment(Fragment fragment, Intent intent, int n, Bundle bundle) {
            FragmentActivity.this.startActivityFromFragment(fragment, intent, n, bundle);
        }

        @Override
        public void onStartIntentSenderFromFragment(Fragment fragment, IntentSender intentSender, int n, Intent intent, int n2, int n3, int n4, Bundle bundle) throws IntentSender.SendIntentException {
            FragmentActivity.this.startIntentSenderFromFragment(fragment, intentSender, n, intent, n2, n3, n4, bundle);
        }

        @Override
        public void onSupportInvalidateOptionsMenu() {
            FragmentActivity.this.supportInvalidateOptionsMenu();
        }
    }

}

