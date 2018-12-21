package com.host;

import android.content.Context;
import android.content.Intent;

import com.facebook.react.ReactActivity;
import com.facebook.react.ReactActivityDelegate;
import com.host.utils.DispatchUtils;

public class DispatchActivity extends ReactActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, DispatchActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected ReactActivityDelegate createReactActivityDelegate() {

        DispatchDelegate delegate = new DispatchDelegate(this, DispatchUtils.dispatchModel);

        return delegate;
    }
}
