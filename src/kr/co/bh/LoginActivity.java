/**
 *
 */
package kr.co.bh;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 *	<h1>로그인 엑티비티</h1>
 *	자동로그인 체크, 로그인 인증처리
 *
 */
public class LoginActivity extends BaseActivity {
	// context
	private Context mContext;
	/**
	 * 	자동로그인 정보를 가져오기 위한 환경설정
	 */
	private SharedPreferences mPref;

	private CheckBox checkBtn;	// 자동 로그인 체크박스
	// 자동로그인 체크 플레그
	private boolean checkFlag = false;

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_layout);
		mContext = this;
		mPref = PreferenceManager.getDefaultSharedPreferences(this);
		// 자동로그인 체크
		// 체크상태는 환경설정에 바로 저장
		checkBtn = (CheckBox)findViewById(R.id.auto_login_btn);
		checkBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View v) {
				// TODO Auto-generated method stub
				 checkFlag = !checkFlag;
				 SharedPreferences.Editor editor = mPref.edit();
				 editor.putBoolean(AUTO_LOGIN, checkFlag);
				 editor.commit();
			}
		});
		
		getLoginInfo();
	}

	/**
	 * 자동로그인 허용이면 이전 로그인 정보를 환경설정에서 가져오기
	 */
	private void getLoginInfo() {
		CharSequence userId = mPref.getString(LOGIN_ID, "");
		CharSequence userPwd = mPref.getString(LOGIN_PWD, "");
		if (!TextUtils.isEmpty(userId)) {
			((EditText) findViewById(R.id.user_id)).setText(userId);
			// setting 정보에서 자동로그인 정보를 가져온다.
			if(mPref.getBoolean(AUTO_LOGIN, false)){	// 자동로그인시 비밀번호를 넣어주고
				//checkBtn.setBackgroundResource(R.drawable.checkboxall_s);
				((EditText) findViewById(R.id.user_pwd)).setText(userPwd);
				// 로그인 트리거
				((ImageButton)findViewById(R.id.login_submit)).performClick();
			}
		}
	}

	/**
	 * 로그인 체크후 id,password 정보 저장
	 */
	private void checkLogin() {
		EditText userIdEt = (EditText) findViewById(R.id.user_id);
		EditText userPwdEt = (EditText) findViewById(R.id.user_pwd);

		CharSequence userId = userIdEt.getText();
		CharSequence userPwd = userPwdEt.getText();

		if (TextUtils.isEmpty(userId)) {
			Toast.makeText(this, "아이디를 입력하세요", Toast.LENGTH_SHORT).show();
			return;
		} else if (TextUtils.isEmpty(userPwd)) {
			Toast.makeText(this, "비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();
			return;
		}

		// 서버에 로그인인증 처리 수행

	}


	/**
	 * submit button 처리
	 * @param v
	 */
	public void mOnclick(final View v) {
		checkLogin();
	}

}
