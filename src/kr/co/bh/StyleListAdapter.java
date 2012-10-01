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
import android.widget.TextView;

/**
 */
public class StyleListAdapter extends BaseAdapter {
	private final Context context;
	private List<StyleDAO> list;
	private LayoutInflater inflater = null;

	public StyleListAdapter(final Context context, final List<StyleDAO> list) {
		this.list = list;
		this.context = context;
		// ���÷����� ���
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
		// �� Ȧ�� ����
		View view = null;
		ViewHolder wrapper = null;
		
		// ������ ���
		final StyleDAO data = (StyleDAO) getItem(pos);
		if (convertView == null) {
			view = inflater.inflate(R.layout.style_list_item, null);
			BaseActivity.recursiveViewSetTypeFace((ViewGroup)view);
			wrapper = new ViewHolder(view);
			view.setTag(wrapper);
		} else {
			view = convertView;
			wrapper = (ViewHolder) view.getTag();
		}

		wrapper.getNo().setText(String.valueOf(pos + 1));
		wrapper.getStly().setText(data.getStyl());
		wrapper.getSobi().setText(data.getSobi());
		wrapper.getSbps_s().setText(data.getSbps_s());
		wrapper.getDcrp_b().setText(data.getDcrp_b());
		wrapper.getMjlq().setText(data.getMjlq());		
		wrapper.getSjlq().setText(data.getSjlq());		
		// �̹��� ��ư�� ��Ŀ���� ������� ����Ʈ �����ۿ� ��Ŀ���� �ټ� �ִ�.
		wrapper.geStlyeViewBtn().setFocusable(false);
		//
		wrapper.geStlyeViewBtn().setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View v) {
				Intent intent = new Intent(context, StyleImageViewActivity.class);
				intent.putExtra("imageUrl", "http://www.basichouse.co.kr/_data/attach/201209/19/85560d431e3117053440e9fabac7aacf.jpg" );
				context.startActivity(intent);
			}
		});
		return wrapper.getBase();
	}


	/**
	 * ������Ʈ ��ŷ���ϸ� ���̱� ���� Ŭ����
	 */
	class ViewHolder {
		private final View base;
	
		private TextView noTv = null;				// ����
		private TextView stylTv = null;				// ��Ÿ�Ϲ�ȣ
		private TextView sobiTv = null;				// ���ʰ���
		private TextView  sbps_sTv = null;			// ���簡��
		private TextView dcrp_bTv = null;			// ������
		private TextView mjlqTv = null;				// ���
		private TextView sjlqTv = null;				// �����
		private Button stlyeViewBtn = null;		// ��Ÿ�Ϻ����ư
		
		ViewHolder(final View base) {
			this.base = base;
		}

		View getBase() {
			return base;
		}

		/**
		 * ����
		 * @return
		 */
		TextView getNo() {
			if (noTv == null) {
				noTv = (TextView) base.findViewById(R.id.no);
			}
			return noTv;
		}
		
		/**
		 * ��Ÿ�Ϲ�ȣ
		 * @return
		 */		
		TextView getStly(){
			if (stylTv == null) {
				stylTv = (TextView) base.findViewById(R.id.styl);
			}
			return stylTv;	
		}		

		/**
		 * ���� ����
		 * @return
		 */
		TextView getSobi() {
			if (sobiTv == null) {
				sobiTv = (TextView) base.findViewById(R.id.sobi);
			}
			return sobiTv;
		}
		
		/**
		 * ���� ����
		 * @return
		 */
		TextView getSbps_s() {
			if (sbps_sTv == null) {
				sbps_sTv = (TextView) base.findViewById(R.id.sbps_s);
			}
			return sbps_sTv;
		}			

		/**
		 * ���η�
		 * @return
		 */
		TextView getDcrp_b() {
			if (dcrp_bTv == null) {
				dcrp_bTv = (TextView) base.findViewById(R.id.dcrp_b);
			}
			return dcrp_bTv;
		}	
		
		/**
		 * ���
		 * @return
		 */
		TextView getMjlq() {
			if (mjlqTv == null) {
				mjlqTv = (TextView) base.findViewById(R.id.mjlq);
			}
			return mjlqTv;
		}		
		
		/**
		 * �����
		 * @return
		 */
		TextView getSjlq() {
			if (sjlqTv == null) {
				sjlqTv = (TextView) base.findViewById(R.id.sjlq);
			}
			return sjlqTv;
		}		
		
		/**
		 * ��Ÿ�� ���� ��ư
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