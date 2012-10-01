package kr.co.bh;

import kr.co.bh.utils.WebImageView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * <h1>��Ÿ��  ��Ƽ��Ƽ</h1>
 * 
 * @author ���ֿ�
 * 
 */
public class StyleImageViewActivity extends BaseActivity {
	//private String imageUrl = "http://www.basichouse.co.kr/_data/attach/201209/19/85560d431e3117053440e9fabac7aacf.jpg";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.style_image_view_dialog);
		
		initLayout();

	}

	/**
	 * ���̾ƿ� �ʱ�ȭ
	 */
	private void initLayout() {
		// getting image from server 
		WebImageView styleImage = (WebImageView)findViewById(R.id.style_image);
		Intent intent = getIntent();
		String imageUrl = intent.getStringExtra("imageUrl");
		styleImage.setImasgeUrl(imageUrl);
	}
	
	public void mOnClick(View v){
		if(v.getId() == R.id.close_btn){
			finish();
		}
	}

}