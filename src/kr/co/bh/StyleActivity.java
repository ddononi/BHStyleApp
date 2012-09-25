package kr.co.bh;

import kr.co.bh.utils.CommonUtils;
import android.app.Activity;
import android.os.Bundle;

/**
 * <h1>인트로 엑티비티</h1>
 * 앱 첫 실행 엑티비티로 서버와 연결 상태 체크후
 *	로그인 엑티비티로 이동한다. 
 * @author 남주완
 *
 */
public class StyleActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.style_count_check_layout);
        
	    // 네트워크에 연결이 안되면 앱을 종료 합니다.
	    if(CommonUtils.checkNetWork(this) == false){
	    	//	Toast.makeText(this, "네트워크연결이 되지 않습니다.\n" + 	"네트워크 수신상태를 확인하세요.", Toast.LENGTH_SHORT).show();
	    	finish();
	    }        
    }
}