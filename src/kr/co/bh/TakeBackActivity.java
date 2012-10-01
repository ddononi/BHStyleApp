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
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

/**
 * <h1>�����ǰ ��Ƽ��Ƽ</h1>
 * @author ���ֿ�
 * 
 */
public class TakeBackActivity extends BaseActivity {
	private String tempDummyURL = "http://wiseroh.dothome.co.kr/basic_list.php";
	// ��ε� ĳ���� �׼ǰ�
	private static final String SEARCH_ACTION = "kr.co.bh.SEARCH";
	// ui
	private ProgressDialog progress;
	private ListView listView;
	private LinearLayout listViewHeader;
	//	��Ÿ�� ����Ʈ
	private ArrayList<StyleDAO> takebackList; 
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
		listView = (ListView)findViewById(R.id.listview);
		// ����Ʈ�� ���
		LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		listViewHeader = (LinearLayout)inflater.inflate(R.layout.style_list_header_item, null );
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
				if(searchEt.getText().toString().length() >= BARCODE_LENGTH){
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
				data.setStyl(obj.getString("styl"));  //  ��Ÿ�� �ڵ�
				data.setSobi(obj.getString("sobi")); 		// 	������ ���
				data.setSbps_s(obj.getString("sbps_s")); 			//  ���簡�� ���
				data.setDcrp_b(obj.getString("dcrp_b")); 	//  ��ī��Ʈ ���
				data.setMjlq(obj.getString("mjlq"));				//  ��� ���				
				data.setSjlq(obj.getString("sjlq")); 	//  ����� ���		
				data.setImageUrl(obj.getString("img_url")); 	//  �̹��� ���						

				Log.i("bh", data.getStyl() );
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
			takebackList = parseJson(response);	// json parsing
			
			if(takebackList == null || takebackList.size() <= 0){
				Toast.makeText(TakeBackActivity.this, "�����͸� �������� ���߽��ϴ�.", Toast.LENGTH_SHORT).show();
				return;
			}
			// ���� ������ �����̸� ����� ����
			StyleListAdapter adapter = new StyleListAdapter(context, takebackList);
			listView.removeHeaderView(listViewHeader);
			//  ��� ���̱�
			listView.addHeaderView(listViewHeader);				
			listView.setAdapter(adapter);
		}
	};

}