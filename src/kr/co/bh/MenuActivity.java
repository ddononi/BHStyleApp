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
 * <h1>Ȩ�޴� ��Ƽ��Ƽ</h1>
 * Ȩ�޴�
 * @author �뺴��
 *
 */
public class MenuActivity extends BaseActivity implements OnClickListener{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);
        initLayout();
    
	    // ��Ʈ��ũ�� ������ �ȵǸ� ���� ���� �մϴ�.
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
		case R.id.style_amount_check_btn :	// ��Ÿ�� ���� üũ
			intent.putExtra("filename", "styleCheck.html");			
			break;
		case R.id.factory_invoice_btn :	// ��� �ŷ�
			intent.putExtra("filename", "factoryInvoices.html");			
			break;
		case R.id.shop_return_btn :			// ���� ��ǰ
			intent.putExtra("filename", "shopReturn.html");			
			break;
		case R.id.style_arrange_btn :	// ��Ÿ�� ���� üũ
			intent.putExtra("filename", "styleArrangement.html");			
			break;
			
		case R.id.inventory_check1 :	// ��� �ǻ�1
			intent.putExtra("filename", "inventoryConduct1.html");			
			break;
			
		case R.id.inventory_check2 :	// ��� �ǻ�2
			intent.putExtra("filename", "inventoryConduct2.html");			
			break;			
			
		case R.id.inventory_check3 :	// ��� �ǻ�3
			intent.putExtra("filename", "inventoryConduct3.html");			
			break;		
			
		case R.id.help_btn :	// ���� ��ư
			 intent = new Intent(this, SettingActivity.class);		
			break;				
		}
		startActivity(intent);
	}
	
	private boolean isTwoClickBack = false;

	@Override
	public boolean onKeyDown(final int keyCode, final KeyEvent event) {
		/*
		 * back ��ư�̸� Ÿ�̸�(2��)�� �̿��Ͽ� �ٽ��ѹ� �ڷ� ���⸦ ������ ���ø����̼��� ���� �ǵ����Ѵ�.
		 */
		if (event.getAction() == KeyEvent.ACTION_DOWN) {
			if (keyCode == KeyEvent.KEYCODE_BACK) {
				if (!isTwoClickBack) {
					Toast.makeText(this, "'�ڷ�' ��ư�� �ѹ� �� ������ ����˴ϴ�.",
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

	// �ڷΰ��� ���Ḧ ���� Ÿ�̸�
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

	/** �ɼ� �޴� ����� */
	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, 1, 0, "�����ϱ�");

		return true;
	}

	/** �ɼ� �޴� ���ÿ� ���� �ش� ó���� ���� */
	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		switch (item.getItemId()) {
		case 1:
			finish();
		}
		return false;
	}	
    
}