package kr.co.bh;

import kr.co.bh.utils.CommonUtils;
import android.app.Activity;
import android.os.Bundle;

/**
 * <h1>��Ʈ�� ��Ƽ��Ƽ</h1>
 * �� ù ���� ��Ƽ��Ƽ�� ������ ���� ���� üũ��
 *	�α��� ��Ƽ��Ƽ�� �̵��Ѵ�. 
 * @author ���ֿ�
 *
 */
public class StyleActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.style_count_check_layout);
        
	    // ��Ʈ��ũ�� ������ �ȵǸ� ���� ���� �մϴ�.
	    if(CommonUtils.checkNetWork(this) == false){
	    	//	Toast.makeText(this, "��Ʈ��ũ������ ���� �ʽ��ϴ�.\n" + 	"��Ʈ��ũ ���Ż��¸� Ȯ���ϼ���.", Toast.LENGTH_SHORT).show();
	    	finish();
	    }        
    }
}