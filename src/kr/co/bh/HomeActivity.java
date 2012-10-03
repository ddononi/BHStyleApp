package kr.co.bh;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

/**
 * <h1>Ȩ�޴� ��Ƽ��Ƽ</h1>
 * Ȩ�޴�
 * @author �뺴��
 *
 */
public class HomeActivity extends BaseActivity implements OnClickListener{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);
    
        initLayout();
    
    }
        
    private void initLayout() {
    	ImageButton styleArrangeBtn = (ImageButton)findViewById(R.id.style_arrange_btn);
    	
    	
    	styleArrangeBtn.setOnClickListener(this);
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

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		
		case R.id.style_arrange_btn :	// ��Ÿ�� ����
			Intent intent = new Intent(this, StyleArrangeActivity.class);
			startActivity(intent);
			break;
		}
	}
    
}