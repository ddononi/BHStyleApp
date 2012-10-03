package kr.co.bh;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

/**
 * <h1>홈메뉴 엑티비티</h1>
 * 홈메뉴
 * @author 노병원
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

	//스타일 수량 체크 엑티비티로 이동한다
	public void StyleActivityGo(View v) {
		try {
			Intent intent = new Intent(this, StyleActivity.class);
			/*intent.putExtra("key","value");  해당 엑티비티에 넘길 데이커가 있을 경우 */
			startActivity(intent);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		
		case R.id.style_arrange_btn :	// 스타일 정리
			Intent intent = new Intent(this, StyleArrangeActivity.class);
			startActivity(intent);
			break;
		}
	}
    
}