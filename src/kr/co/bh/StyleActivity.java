package kr.co.bh;

import java.net.URI;
import java.util.ArrayList;

import kr.co.bh.dao.StyleDAO;
import kr.co.bh.utils.CommonUtils;
import kr.co.bh.utils.RestTask;

import org.apache.http.client.methods.HttpGet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

/**
 * <h1>��Ÿ�� üũ ��Ƽ��Ƽ</h1>
 * 
 * @author ���ֿ�
 * 
 */
public class StyleActivity extends Activity {
	private String tempDummyURL = "http://wiseroh.dothome.co.kr/basic_list.php";
	// ��ε� ĳ���� �׼ǰ�
	private static final String SEARCH_ACTION = "kr.co.bh.SEARCH";

	private ProgressDialog progress;

	//	��Ÿ�� ����Ʈ
	private ArrayList<StyleDAO> styleArrayList; 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.style_count_check_layout);

		// ��Ʈ��ũ�� ������ �ȵǸ� ���� ���� �մϴ�.
		if (CommonUtils.checkNetWork(this) == false) {
			// Toast.makeText(this, "��Ʈ��ũ������ ���� �ʽ��ϴ�.\n" + "��Ʈ��ũ ���Ż��¸� Ȯ���ϼ���.",
			// Toast.LENGTH_SHORT).show();
			finish();
			return;
		}
		
		initLayout();

	}

	/**
	 * ���̾ƿ� �ʱ�ȭ
	 */
	private void initLayout() {

        // ���ڵ��Է�
        final EditText searchEt = (EditText)findViewById(R.id.barcode_input);
        //  ���� ����
        TextWatcher watcher = new TextWatcher() {
			@Override
			public void onTextChanged(final CharSequence s, final int start, final int before, final int count) {
			}
			@Override
			public void beforeTextChanged(final CharSequence s, final int start, final int count,
					final int after) {
			}
			@Override
			public void afterTextChanged(final Editable s) {
				//	���ڵ� �Է±��̸� ������û ó��
				if(searchEt.getText().toString().length() >= 13){
					doRequest();
				}
			}
		};
		
		searchEt.addTextChangedListener(watcher);		
	}

	private void doRequest() {
		try {
			HttpGet request = new HttpGet( new URI(tempDummyURL) );
			RestTask task = new RestTask(this, SEARCH_ACTION);
			task.execute(request);
			progress = ProgressDialog.show(this, "�˻���", "��ø� ��ٷ� �ּ���..", true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		// ���ù� ����
		unregisterReceiver(receiver);
	}

	@Override
	protected void onResume() {
		super.onResume();
		// ���ù� ���
		registerReceiver(receiver, new IntentFilter(SEARCH_ACTION));		
	}

	private ArrayList<StyleDAO> parseJson(String responseJson) {
		Log.i("bh",responseJson);
		StyleDAO  data;
		 ArrayList<StyleDAO> list = new ArrayList<StyleDAO>();
		try {
			// root ���
			JSONArray root = (new JSONObject(responseJson)).getJSONArray("list_data");
			for (int i = 0; i <  root.length(); i++) { // ��� �迭
				data = new StyleDAO();
				JSONObject obj = root.getJSONObject(i);
				//data.setobj.getString("no"); // y ��ǥ ���
				data.setStyleCode(obj.getString("style_code"));  //  ��Ÿ�� �ڵ�
				data.setOriginalPrice(obj.getString("first")); 		// 	������ ���
				data.setSalePrice(obj.getString("now")); 			//  ���簡�� ���
				data.setDiscount(obj.getString("discount")); 	//  ��ī��Ʈ ���
				data.setStock(obj.getString("stock"));				//  ��� ���				
				data.setRealStock(obj.getString("real_stock")); 	//  ����� ���		
				data.setImageUrl(obj.getString("img_url")); 	//  �̹��� ���						

				Log.i("bh", data.getRealStock());
				// ��ǥ�� ����
				list.add(data);
			}
			return list;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}
	
	/**
	 * http ���� ��� ��ε� ĳ���ø��ù� ����
	 */
	private BroadcastReceiver receiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			// ���� ���̾�α� ����
			if(progress != null && progress.isShowing()){
				progress.dismiss();
			}
			
			String response = intent.getStringExtra(RestTask.HTTP_RESPONSE);
			styleArrayList = parseJson(response);	// json �Ľ�ó��
			
			if(styleArrayList == null || styleArrayList.size() <= 0){
				Toast.makeText(StyleActivity.this, "�����͸� �������� ���߽��ϴ�.", Toast.LENGTH_SHORT).show();
				return;
			}
		}
	};

}