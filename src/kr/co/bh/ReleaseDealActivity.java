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

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

/**
 * <h1>��� �ŷ� ����Ƽ��Ƽ</h1>
 * @author ���ֿ�
 * 
 */
public class ReleaseDealActivity extends BaseActivity {
	private String tempDummyURL = "http://wiseroh.vps.phps.kr/factoryinvoices_list.php";
	// ��ε� ĳ���� �׼ǰ�
	private static final String SEARCH_ACTION = "kr.co.bh.SEARCH";
	// ui
	private ProgressDialog progress;
	private ListView styleLv;
	private LinearLayout listViewHeader;
	private EditText searchEt;	
	//	��Ÿ�� ����Ʈ
	private ArrayList<ReleaseDealData> mArrayList; 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.style_count_check_layout);
		initLayout();
	}

	/**
	 * ���̾ƿ� �ʱ�ȭ
	 */
	private void initLayout() {
		styleLv = (ListView)findViewById(R.id.listview);
		// ����Ʈ�� ���
		LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		listViewHeader = (LinearLayout)inflater.inflate(R.layout.style_list_header_item, null );
        // ���ڵ��Է�
		searchEt = (EditText)findViewById(R.id.barcode_input);
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
				// check validation
				if(TextUtils.isDigitsOnly(searchEt.getText().toString()) == false){
					Toast.makeText(ReleaseDealActivity.this, "��Ȯ�� ���ڵ尪�� �Է��ϼ���", Toast.LENGTH_SHORT).show();
					searchEt.setText("");
					return;
				}
				//	���ڵ� �Է±��̸� ������û ó��
				if(searchEt.getText().toString().length() >= BARCODE_LENGTH){
					searchEt.setEnabled(false);
					doRequest();
				}
			}
		};
		
		searchEt.addTextChangedListener(watcher);		
	}

	private void doRequest() {
		try {
			//String url = tempDummyURL + ?
			String url  = "http://wiseroh.vps.phps.kr/factoryinvoices_list.php?" +
					"AS_MJCD=CC2003&AS_CORN=0&AN_SENO=68760&" +
					"AS_KIND=test&AS_ITEM=test&AS_STYL=test&AS_GUBN=3";
			HttpGet request = new HttpGet( new URI(url) );
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

	private ArrayList<ReleaseDealData> parseJson(String responseJson) {
		Log.i("bh",responseJson);
		StyleDAO  data;
		 ArrayList<ReleaseDealData> list = new ArrayList<StyleDAO>();
		try {
			// root ���
			JSONArray root = (new JSONObject(responseJson)).getJSONArray("list_data");
			for (int i = 0; i <  root.length(); i++) { // ��� �迭
				data = new StyleDAO();
				JSONObject obj = root.getJSONObject(i);

				/*
				[{"styl":"HLTS0101","stcd":"HLTS0101WH090","colr":"WH","size":"090","sobi":12900,"pric":12900,"" +
						"dcrt":0,"chqt":2,"siqt":0,"diqt":0,"cfyn":0,"chdt":20121012,"chsq":5343719,"chsr":1}				
				*/
				data.setStyl(obj.getString("styl")); 					 //  ��Ÿ�� �ڵ�
				data.setSobi(obj.getString("stcd")); 					
				data.setSbps_s(obj.getString("colr")); 			
				data.setDcrp_b(obj.getString("size")); 			
				data.setMjlq(obj.getString("sobi"));					
				data.setSjlq(obj.getString("pric")); 		
				
				data.setStyl(obj.getString("dcrt")); 					 //  ��Ÿ�� �ڵ�
				data.setSobi(obj.getString("chqt")); 					
				data.setSbps_s(obj.getString("siqt")); 			
				data.setDcrp_b(obj.getString("diqt")); 			
				data.setMjlq(obj.getString("cfyn"));					
				data.setSjlq(obj.getString("chdt")); 			
				data.setSjlq(obj.getString("chsq")); 		
				data.setSjlq(obj.getString("chsr"));
				
				Log.i("bh", data.getStyl() );

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
			// remove barcode number
			searchEt.setText("");
			searchEt.setEnabled(true);		
			
			String response = intent.getStringExtra(RestTask.HTTP_RESPONSE);
			mArrayList = parseJson(response);	// json �Ľ�ó��
			if(mArrayList == null || mArrayList.size() <= 0){
				Toast.makeText(ReleaseDealActivity.this, "�����͸� �������� ���߽��ϴ�.", Toast.LENGTH_SHORT).show();
				return;
			}
			// ���� ������ �����̸� ����� ����
			StyleListAdapter adapter = new StyleListAdapter(context, mArrayList);
			//  ��� ���̱�
			styleLv.removeHeaderView(listViewHeader);			
			styleLv.addHeaderView(listViewHeader);						
			styleLv.setAdapter(adapter);

		}
	};

}