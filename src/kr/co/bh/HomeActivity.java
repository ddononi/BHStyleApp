package kr.co.bh;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * <h1>Ȩ�޴� ��Ƽ��Ƽ</h1>
 * Ȩ�޴�
 * @author �뺴��
 *
 */
public class HomeActivity extends BaseActivity{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);
    
    
    }
        
    //��Ÿ�� ���� üũ ��Ƽ��Ƽ�� �̵��Ѵ�
	public void StyleActivityGo(View v) {
		try {
			Intent intent = new Intent(this, StyleActivity.class);
			/*intent.putExtra("key","value");  �ش� ��Ƽ��Ƽ�� �ѱ� ����Ŀ�� ���� ��� */
			startActivity(intent);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
    
}