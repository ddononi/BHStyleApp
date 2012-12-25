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
 * ��Ƽ��Ƽ �⺻ ���� Ŭ������ ��� �������̽� ���� , ��Ʈ ����, ���� ���̾�α� �޴� ó��, ���� ó���� �Ѵ�. �Ϲ������� �ٸ�
 * �����Ƽ�� BaseActivity�� ��� �޴´�.
 * 
 */
public class BaseActivity extends Activity implements iConstant {
	public static boolean mapLock = false; // ���� ��뿩��üũ
	public static Typeface typeFace = null; // ��Ʈ
	public static Typeface typeFaceBold = null;
	public final static Calendar calendar = Calendar.getInstance();

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// ��� Ÿ��Ʋ ����
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// ȭ�� ���� ����
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		// ��Ʈ ����
		if (typeFace == null) {
			// ������� �۲�
			typeFace = Typeface.createFromAsset(getAssets(),
					"fonts/NanumGothic.ttf");
			typeFaceBold = Typeface.createFromAsset(getAssets(),
					"fonts/NanumGothicBold.ttf");
		}
	}

	/**
	 * ��Ʈ ����(���� �۲�)
	 */
	@Override
	public void setContentView(final int viewId) {
		View view = LayoutInflater.from(this).inflate(viewId, null);
		ViewGroup group = (ViewGroup) view;
		recursiveViewSetTypeFace(group);
		super.setContentView(view);
	}

	/**
	 * ��������� �並 Ž���Ͽ� ��Ʈ�� �����Ѵ�. tag�� ������ ������ ������Ʈ�� ���� Ž���� �� �׷�
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
		menu.add(0, 1, 0, "ȯ�漳��");
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
	 * ����Ʈ Ű���� ���̱�
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
	 * ����Ʈ Ű���� �����
	 * 
	 * @param view
	 */
	protected void hideSoftKeyboard(View view) {
		InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		mgr.hideSoftInputFromWindow(view.getWindowToken(), 0);

	}

}
