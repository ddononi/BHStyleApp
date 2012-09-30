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
 *	<h1>�α��� ��Ƽ��Ƽ</h1>
 *	�ڵ��α��� üũ, �α��� ����ó��
 *
 */
public class LoginActivity extends BaseActivity {
	// context
	private Context mContext;
	/**
	 * 	�ڵ��α��� ������ �������� ���� ȯ�漳��
	 */
	private SharedPreferences mPref;

	private CheckBox checkBtn;	// �ڵ� �α��� üũ�ڽ�
	// �ڵ��α��� üũ �÷���
	private boolean checkFlag = false;

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_layout);
		mContext = this;
		mPref = PreferenceManager.getDefaultSharedPreferences(this);
		// �ڵ��α��� üũ
		// üũ���´� ȯ�漳���� �ٷ� ����
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
	 * �ڵ��α��� ����̸� ���� �α��� ������ ȯ�漳������ ��������
	 */
	private void getLoginInfo() {
		CharSequence userId = mPref.getString(LOGIN_ID, "");
		CharSequence userPwd = mPref.getString(LOGIN_PWD, "");
		if (!TextUtils.isEmpty(userId)) {
			((EditText) findViewById(R.id.user_id)).setText(userId);
			// setting �������� �ڵ��α��� ������ �����´�.
			if(mPref.getBoolean(AUTO_LOGIN, false)){	// �ڵ��α��ν� ��й�ȣ�� �־��ְ�
				//checkBtn.setBackgroundResource(R.drawable.checkboxall_s);
				((EditText) findViewById(R.id.user_pwd)).setText(userPwd);
				// �α��� Ʈ����
				((ImageButton)findViewById(R.id.login_submit)).performClick();
			}
		}
	}

	/**
	 * �α��� üũ�� id,password ���� ����
	 */
	private void checkLogin() {
		EditText userIdEt = (EditText) findViewById(R.id.user_id);
		EditText userPwdEt = (EditText) findViewById(R.id.user_pwd);

		CharSequence userId = userIdEt.getText();
		CharSequence userPwd = userPwdEt.getText();

		if (TextUtils.isEmpty(userId)) {
			Toast.makeText(this, "���̵� �Է��ϼ���", Toast.LENGTH_SHORT).show();
			return;
		} else if (TextUtils.isEmpty(userPwd)) {
			Toast.makeText(this, "��й�ȣ�� �Է��ϼ���", Toast.LENGTH_SHORT).show();
			return;
		}

		// ������ �α������� ó�� ����

	}


	/**
	 * submit button ó��
	 * @param v
	 */
	public void mOnclick(final View v) {
		checkLogin();
	}

}
