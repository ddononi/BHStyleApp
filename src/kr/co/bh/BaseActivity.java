/**
 *
 */
package kr.co.bh;

import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 엑티비티 기본 설정 클래스로 상수 인터페이스 구현 , 폰트 설정, 종료 다이얼로그 메뉴 처리, 서비스 처리를 한다. 일반적으로 다른
 * 엑비비티는 BaseActivity를 상속 받는다.
 * 
 */
public class BaseActivity extends Activity implements iConstant {
	public static boolean mapLock = false; // 지도 사용여부체크
	public static Typeface typeFace = null; // 폰트
	public static Typeface typeFaceBold = null;
	public final static Calendar calendar = Calendar.getInstance();

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// 상단 타이틀 제거
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 화면 세로 고정
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		// 폰트 설정
		if (typeFace == null) {
			// 나눔고딕 글꼴
			typeFace = Typeface.createFromAsset(getAssets(),
					"fonts/NanumGothic.ttf");
			typeFaceBold = Typeface.createFromAsset(getAssets(),
					"fonts/NanumGothicBold.ttf");
		}
	}

	/**
	 * 폰트 설정(나눔 글꼴)
	 */
	@Override
	public void setContentView(final int viewId) {
		View view = LayoutInflater.from(this).inflate(viewId, null);
		ViewGroup group = (ViewGroup) view;
		recursiveViewSetTypeFace(group);
		super.setContentView(view);
	}

	/**
	 * 재귀적으로 뷰를 탐색하여 폰트를 적용한다. tag를 가지고 있으면 볼드폰트로 적용 탐색할 뷰 그룹
	 */
	public static void recursiveViewSetTypeFace(final ViewGroup group) {
		int childCnt = group.getChildCount();
		TextView tv;
		Button b;
		EditText et;
		for (int i = 0; i < childCnt; i++) {
			View v = group.getChildAt(i);
			if (v instanceof TextView) {
				tv = (TextView) v;
				tv.setTypeface((tv.getTag() != null) ? typeFaceBold : typeFace);
			} else if (v instanceof Button) {
				b = (Button) v;
				b.setTypeface((b.getTag() != null) ? typeFaceBold : typeFace);
			} else if (v instanceof EditText) {
				et = (EditText) v;
				et.setTypeface((et.getTag() != null) ? typeFaceBold : typeFace);
			} else if (v instanceof ViewGroup) {
				recursiveViewSetTypeFace((ViewGroup) v);
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, 1, 0, "환경설정");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		switch (item.getItemId()) {
		case 1:
			Intent intent = null;
			intent = new Intent(getBaseContext(), SettingActivity.class);
			startActivity(intent);
			return true;
		}

		return false;
	}

	/**
	 * 소프트 키보드 보이기
	 */
	protected void showSoftKeyboard(View v) {
		InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		mgr.showSoftInput(v, InputMethodManager.SHOW_FORCED);
		mgr.showSoftInputFromInputMethod(v.getApplicationWindowToken(),
				InputMethodManager.SHOW_FORCED);
		mgr.toggleSoftInputFromWindow(v.getApplicationWindowToken(),
				InputMethodManager.SHOW_FORCED, 0);

	}

	/**
	 * 소프트 키보드 숨기기
	 * 
	 * @param view
	 */
	protected void hideSoftKeyboard(View view) {
		InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		mgr.hideSoftInputFromWindow(view.getWindowToken(), 0);

	}

}
