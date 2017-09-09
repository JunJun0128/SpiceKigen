package com.lifeistech.android.schoolseventeen.junjun.spicekigen;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by junekelectric on 2017/09/08.
 */

public class app {extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
