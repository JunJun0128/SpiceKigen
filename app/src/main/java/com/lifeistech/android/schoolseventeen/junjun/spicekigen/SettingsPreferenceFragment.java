package com.lifeistech.android.schoolseventeen.junjun.spicekigen;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.view.View;

/**
 * Created by junekelectric on 2017/06/09.
 */

public class SettingsPreferenceFragment extends PreferenceFragment {
    //設定画面を表示するクラス
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.menu.optionsmenu);
    }


    public void notification (View v){
        Intent intent = new Intent(this, NotificationActivity.class);
        startActivity(intent);
        super.onResume();
    }


    public void design (View v){
        Intent intent = new Intent(this, DesignActivity.class);
        startActivity(intent);
        super.onResume();
    }
}
