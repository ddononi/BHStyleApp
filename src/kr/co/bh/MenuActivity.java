package kr.co.bh;

import kr.co.bh.utils.CommonUtils;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * <h1>홈메뉴 엑티비티</h1>
 * 홈메뉴
 * @author 노병원
 *
 */
public class MenuActivity extends BaseActivity implements OnClickListener{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);
        initLayout();
    
	    // 네트워크에 연결이 안되면 앱을 종료 합니다.
	    if(CommonUtils.checkNetWork(this) == false){
	    	finish();
	    }
	    
    }
        
    private void initLayout() {
    	ImageButton styleCheckBtn = (ImageButton)findViewById(R.id.style_amount_check_btn);
    	ImageButton shopReturnBtn = (ImageButton)findViewById(R.id.shop_return_btn);
    	ImageButton styleArrangeBtn = (ImageButton)findViewById(R.id.style_arrange_btn);
    	ImageButton factoryInvoiceBtn = (ImageButton)findViewById(R.id.factory_invoice_btn);
    	ImageButton inventoryCheck1Btn = (ImageButton)findViewById(R.id.inventory_check1);
    	ImageButton inventoryCheck2Btn = (ImageButton)findViewById(R.id.inventory_check2);
    	ImageButton inventoryCheck3Btn = (ImageButton)findViewById(R.id.inventory_check3);    	
    	
    	styleCheckBtn.setOnClickListener(this);
    	styleArrangeBtn.setOnClickListener(this);
    	shopReturnBtn.setOnClickListener(this);
    	factoryInvoiceBtn.setOnClickListener(this);
    	inventoryCheck1Btn.setOnClickListener(this);
    	inventoryCheck2Btn.setOnClickListener(this);
    	inventoryCheck3Btn.setOnClickListener(this);    	
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(this, WebClientActivity.class);
		switch(v.getId()){
		case R.id.style_amount_check_btn :	// 스타일 수량 체크
			intent.putExtra("filename", "styleCheck.html");			
			break;
		case R.id.factory_invoice_btn :	// 출고 거래
			intent.putExtra("filename", "factoryInvoices.html");			
			break;
		case R.id.shop_return_btn :			// 매장 반품
			intent.putExtra("filename", "shopReturn.html");			
			break;
		case R.id.style_arrange_btn :	// 스타일 정리 체크
			intent.putExtra("filename", "styleArrangement.html");			
			break;
			
		case R.id.inventory_check1 :	// 재고 실사1
			intent.putExtra("filename", "inventoryConduct1.html");			
			break;
			
		case R.id.inventory_check2 :	// 재고 실사2
			intent.putExtra("filename", "inventoryConduct2.html");			
			break;			
			
		case R.id.inventory_check3 :	// 재고 실사3
			intent.putExtra("filename", "inventoryConduct3.html");			
			break;		
			
		case R.id.help_btn :	// 도움말 버튼
			 intent = new Intent(this, SettingActivity.class);		
			break;				
		}
		startActivity(intent);
	}
	
	private boolean isTwoClickBack = false;

	@Override
	public boolean onKeyDown(final int keyCode, final KeyEvent event) {
		/*
		 * back 버튼이면 타이머(2초)를 이용하여 다시한번 뒤로 가기를 누르면 어플리케이션이 종료 되도록한다.
		 */
		if (event.getAction() == KeyEvent.ACTION_DOWN) {
			if (keyCode == KeyEvent.KEYCODE_BACK) {
				if (!isTwoClickBack) {
					Toast.makeText(this, "'뒤로' 버튼을 한번 더 누르면 종료됩니다.",
							Toast.LENGTH_SHORT).show();
					CntTimer timer = new CntTimer(2000, 1);
					timer.start();
				} else {
					moveTaskToBack(true);
					finish();
					return true;
				}

			}
		}
		return false;
	}

	// 뒤로가기 종료를 위한 타이머
	class CntTimer extends CountDownTimer {
		public CntTimer(final long millisInFuture, final long countDownInterval) {
			super(millisInFuture, countDownInterval);
			isTwoClickBack = true;
		}

		@Override
		public void onFinish() {
			// TODO Auto-generated method stub
			isTwoClickBack = false;
		}

		@Override
		public void onTick(final long millisUntilFinished) {
		}
	}

	/** 옵션 메뉴 만들기 */
	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, 1, 0, "종료하기");

		return true;
	}

	/** 옵션 메뉴 선택에 따라 해당 처리를 해줌 */
	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		switch (item.getItemId()) {
		case 1:
			finish();
		}
		return false;
	}	
    
}