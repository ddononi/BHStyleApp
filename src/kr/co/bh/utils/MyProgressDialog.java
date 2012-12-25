package kr.co.bh.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

/**
 * 프로
 */
public class MyProgressDialog extends ProgressDialog {
	public final static int MAX_LOADING_TIME = 6 * 1000;
	private Context context;

	public MyProgressDialog(Context context) {
		super(context);
		this.context = context;
	}

	@Override
	public void onStart() {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				if (MyProgressDialog.this.isShowing()) {
					MyProgressDialog.this.dismiss();
					Toast.makeText(context, "로딩 조회시간 초과", Toast.LENGTH_SHORT)
							.show();
				}
			}
		}, MAX_LOADING_TIME);
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

}
