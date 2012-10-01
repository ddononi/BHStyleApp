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
 * <h1>스타일 체크 엑티비티</h1>
 * 
 * @author 남주완
 * 
 */
public class StyleActivity extends BaseActivity {
	private String tempDummyURL = "http://wiseroh.dothome.co.kr/basic_list.php";
	// 브로드 캐스팅 액션값
	private static final String SEARCH_ACTION = "kr.co.bh.SEARCH";
	// ui
	private ProgressDialog progress;
	private ListView styleLv;
	private LinearLayout listViewHeader;
	private EditText searchEt;	
	//	스타일 리스트
	private ArrayList<StyleDAO> styleArrayList; 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.style_count_check_layout);

		// 네트워크에 연결이 안되면 앱을 종료 합니다.
		if (CommonUtils.checkNetWork(this) == false) {
			// Toast.makeText(this, "네트워크연결이 되지 않습니다.\n" + "네트워크 수신상태를 확인하세요.",
			// Toast.LENGTH_SHORT).show();
			finish();
			return;
		}
		
		initLayout();

	}

	/**
	 * 레이아웃 초기화
	 */
	private void initLayout() {
		styleLv = (ListView)findViewById(R.id.listview);
		// 리스트뷰 헤더
		LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		listViewHeader = (LinearLayout)inflater.inflate(R.layout.style_list_header_item, null );
        // 바코드입력
		searchEt = (EditText)findViewById(R.id.barcode_input);
        //  와쳐 설정
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
					Toast.makeText(StyleActivity.this, "정확한 바코드값을 입력하세요", Toast.LENGTH_SHORT).show();
					searchEt.setText("");
					return;
				}
				//	바코드 입력길이면 서버요청 처리
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
			progress = ProgressDialog.show(this, "검색중", "잠시만 기다려 주세요..", true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		// 리시버 해제
		unregisterReceiver(receiver);
	}

	@Override
	protected void onResume() {
		super.onResume();
		// 리시버 등록
		registerReceiver(receiver, new IntentFilter(SEARCH_ACTION));		
	}

	private ArrayList<StyleDAO> parseJson(String responseJson) {
		Log.i("bh",responseJson);
		StyleDAO  data;
		 ArrayList<StyleDAO> list = new ArrayList<StyleDAO>();
		try {
			// root 얻기
			JSONArray root = (new JSONObject(responseJson)).getJSONArray("list_data");
			for (int i = 0; i <  root.length(); i++) { // 경로 배열
				data = new StyleDAO();
				JSONObject obj = root.getJSONObject(i);
				//data.setobj.getString("no"); // y 좌표 얻기
				data.setStyl(obj.getString("styl"));  //  스타일 코드
				data.setSobi(obj.getString("sobi")); 		// 	원가격 얻기
				data.setSbps_s(obj.getString("sbps_s")); 			//  현재가격 얻기
				data.setDcrp_b(obj.getString("dcrp_b")); 	//  디스카운트 얻기
				data.setMjlq(obj.getString("mjlq"));				//  재고 얻기				
				data.setSjlq(obj.getString("sjlq")); 	//  실재고 얻기		
				data.setImageUrl(obj.getString("img_url")); 	//  이미지 얻기						

				Log.i("bh", data.getStyl() );
				// 좌표를 저장
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
	 * http 응답 결과 브로드 캐스팅리시버 설정
	 */
	private BroadcastReceiver receiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			// 진행 다이얼로그 제거
			if(progress != null && progress.isShowing()){
				progress.dismiss();
			}
			// remove barcode number
			searchEt.setText("");
			searchEt.setEnabled(true);		
			
			String response = intent.getStringExtra(RestTask.HTTP_RESPONSE);
			styleArrayList = parseJson(response);	// json 파싱처린
			if(styleArrayList == null || styleArrayList.size() <= 0){
				Toast.makeText(StyleActivity.this, "데이터를 가져오지 못했습니다.", Toast.LENGTH_SHORT).show();
				return;
			}
			// 정상 데이터 수신이면 어댑터 설정
			StyleListAdapter adapter = new StyleListAdapter(context, styleArrayList);
			//  헤더 붙이기
			styleLv.removeHeaderView(listViewHeader);			
			styleLv.addHeaderView(listViewHeader);						
			styleLv.setAdapter(adapter);

		}
	};

}