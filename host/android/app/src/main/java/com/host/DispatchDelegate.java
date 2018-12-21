package com.host;

import android.app.Activity;

import com.facebook.react.ReactActivityDelegate;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.shell.MainReactPackage;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

public class DispatchDelegate extends ReactActivityDelegate {

    private Activity activity;
    private String bundleName;


    public DispatchDelegate(Activity activity, @Nullable String mainComponentName) {
        super(activity, mainComponentName);
        this.activity = activity;
        this.bundleName = mainComponentName;
    }

    @Override
    protected ReactNativeHost getReactNativeHost() {

        ReactNativeHost mReactNativeHost = new ReactNativeHost(activity.getApplication()) {
            @Override
            public boolean getUseDeveloperSupport() {
                return BuildConfig.DEBUG;
            }

            @Override
            protected List<ReactPackage> getPackages() {
                return Arrays.<ReactPackage>asList(
                        new MainReactPackage()
                );
            }

            @Nullable
            @Override
            protected String getJSBundleFile() {

                String file = activity.getFilesDir().getAbsolutePath() + "/" + bundleName + "/" + bundleName + ".bundle";
                return file;
            }

            @Nullable
            @Override
            protected String getBundleAssetName() {
                return bundleName + ".bundle";
            }

            @Override
            protected String getJSMainModuleName() {
                return "index";
            }
        };
        return mReactNativeHost;
    }
}
