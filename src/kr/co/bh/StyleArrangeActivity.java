package kr.co.bh;

import java.net.URI;
import java.util.ArrayList;

import kr.co.bh.dao.StyleDAO;
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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

/**
 * <h1>��Ÿ�� ���� ��Ƽ��Ƽ</h1>
 * 
 * @author ���ֿ�
 * 
 */
public class StyleArrangeActivity extends BaseActivity {
	private String tempDummyURL = "http://wiseroh.dothome.co.kr/basic_list.php";
	// ��ε� ĳ���� �׼ǰ�
	private static final String SEARCH_ACTION = "kr.co.bh.SEARCH";
	// ui
	private ProgressDialog progress;
	private ListView styleLv;
	private LinearLayout listViewHeader;
	private EditText searchEt;	
	//	��Ÿ�� ����Ʈ
	private ArrayList<StyleDAO> styleArrayList; 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.style_arrange_layout);
		
		initLayout();

	}

	/**
	 * ���̾ƿ� �ʱ�ȭ
	 */
	private void initLayout() {
		styleLv = (ListView)findViewById(R.id.listview);
		// ����Ʈ�� ���
		LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		listViewHeader = (LinearLayout)inflater.inflate(R.layout.style_arrange_list_header_item, null );
		// �����ư
		Button saveBtn = (Button)findViewById(R.id.save_btn);
		saveBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// ������ ���ε� ó��
			}
		});
		
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
					Toast.makeText(StyleArrangeActivity.this, "��Ȯ�� ���ڵ尪�� �Է��ϼ���", Toast.LENGTH_SHORT).show();
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
				data.setStyl(obj.getString("styl"));  //  ��Ÿ�� �ڵ�
				data.setSobi(obj.getString("sobi")); 		// 	������ ���
				data.setSbps_s(obj.getString("sbps_s")); 			//  ���簡�� ���
				data.setDcrp_b(obj.getString("dcrp_b")); 	//  ��ī��Ʈ ���
				data.setMjlq(obj.getString("mjlq"));				//  ��� ���				
				data.setSjlq(obj.getString("sjlq")); 	//  ����� ���		

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
			styleArrayList = parseJson(response);	// json �Ľ�ó��
			if(styleArrayList == null || styleArrayList.size() <= 0){
				Toast.makeText(StyleArrangeActivity.this, "�����͸� �������� ���߽��ϴ�.", Toast.LENGTH_SHORT).show();
				return;
			}
			// ���� ������ �����̸� ����� ����
			StyleArrangeListAdapter adapter = new StyleArrangeListAdapter(context, styleArrayList);
			//  ��� ���̱�
			styleLv.removeHeaderView(listViewHeader);			
			styleLv.addHeaderView(listViewHeader);						
			styleLv.setAdapter(adapter);

		}
	};

}