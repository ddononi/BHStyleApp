package kr.co.bh;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceActivity;
/**
 *	환경 설정 엑티비티
 */
public class SettingActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener {

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.setting);
	}

	@Override
	protected void onResume() {
		super.onResume();
		getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
	}


	@Override
	public void onSharedPreferenceChanged(final SharedPreferences sharedPreferences, final String key) {
        if (key.equals("tel")) {
        	// 전화 걸기
        	Uri uri = Uri.parse("tel:" + iConstant.BH_TEL);
        	Intent intent = new Intent(Intent.ACTION_DIAL, uri);  
        	startActivity(intent);      
        }else if(key.equals("homepage")) {
        	// 홈페이지 띄우기
        	Uri uri = Uri.parse(iConstant.HOME_PAGE);
        	Intent intent  = new Intent(Intent.ACTION_VIEW,uri);
        	startActivity(intent);        	
        }

	}
}