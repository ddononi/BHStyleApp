package kr.co.bh;

import java.util.List;

import kr.co.bh.dao.StyleDAO;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 스타일정리 리스트 어댑터
 * @author 남주완
 *
 */
public class StyleArrangeListAdapter extends BaseAdapter {
	private final Context context;
	private List<StyleDAO> list;
	private LayoutInflater inflater = null;

	public StyleArrangeListAdapter(final Context context, final List<StyleDAO> list) {
		this.list = list;
		this.context = context;
		// 인플레이터 얻기
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(final int pos) {
		return list.get(pos);
	}

	@Override
	public long getItemId(final int pos) {
		return 0;
	}

	@Override
	synchronized public View getView(final int pos, final View convertView,
			final ViewGroup parent) {
		// 뷰 홀더 설정
		View view = null;
		ViewHolder wrapper = null;
		
		// 데이터 얻기
		final StyleDAO data = (StyleDAO) getItem(pos);
		if (convertView == null) {
			view = inflater.inflate(R.layout.style_arrange_list_item, null);
			BaseActivity.recursiveViewSetTypeFace((ViewGroup)view);
			wrapper = new ViewHolder(view);
			view.setTag(wrapper);
		} else {
			view = convertView;
			wrapper = (ViewHolder) view.getTag();
		}

		wrapper.getStly().setText(data.getStyl());
		wrapper.getSobi().setText(data.getSobi());
		wrapper.getSbps_s().setText(data.getSbps_s());
		wrapper.getDcrp_b().setText(data.getDcrp_b());
		wrapper.getMjlq();	
		wrapper.getSjlq().setText(data.getSjlq());		

		return wrapper.getBase();
	}


	/**
	 * 엘리먼트 후킹부하를 줄이기 위한 클래스
	 */
	class ViewHolder {
		private final View base;
	
		private TextView noTv = null;				// 순번
		private TextView stylTv = null;				// 스타일번호
		private TextView sobiTv = null;				// 최초가격
		private TextView  sbps_sTv = null;			// 현재가격
		private TextView dcrp_bTv = null;			// 할인율
		private EditText mjlqTv = null;				// 재고
		private TextView sjlqTv = null;				// 실재고
		private Button stlyeViewBtn = null;		// 스타일보기버튼
		
		ViewHolder(final View base) {
			this.base = base;
		}

		View getBase() {
			return base;
		}

		
		/**
		 * 스타일번호
		 * @return
		 */		
		TextView getStly(){
			if (stylTv == null) {
				stylTv = (TextView) base.findViewById(R.id.styl);
			}
			return stylTv;	
		}		

		/**
		 * 최초 가격
		 * @return
		 */
		TextView getSobi() {
			if (sobiTv == null) {
				sobiTv = (TextView) base.findViewById(R.id.sobi);
			}
			return sobiTv;
		}
		
		/**
		 * 현재 가격
		 * @return
		 */
		TextView getSbps_s() {
			if (sbps_sTv == null) {
				sbps_sTv = (TextView) base.findViewById(R.id.sbps_s);
			}
			return sbps_sTv;
		}			

		/**
		 * 할인률
		 * @return
		 */
		TextView getDcrp_b() {
			if (dcrp_bTv == null) {
				dcrp_bTv = (TextView) base.findViewById(R.id.dcrp_b);
			}
			return dcrp_bTv;
		}	
		
		/**
		 * 재고
		 * @return
		 */
		EditText getMjlq() {
			if (mjlqTv == null) {
				mjlqTv = (EditText) base.findViewById(R.id.mjlq);
			}
			return mjlqTv;
		}		
		
		/**
		 * 실재고
		 * @return
		 */
		TextView getSjlq() {
			if (sjlqTv == null) {
				sjlqTv = (TextView) base.findViewById(R.id.sjlq);
			}
			return sjlqTv;
		}		
		
		/**
		 * 스타일 보기 버튼
		 * @return
		 */
		Button geStlyeViewBtn() {
			if (stlyeViewBtn == null) {
				stlyeViewBtn = (Button) base.findViewById(R.id.style_image_btn);
			}
		
			return stlyeViewBtn;
		}

	}
}
