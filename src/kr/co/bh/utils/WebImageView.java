package kr.co.bh.utils;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import kr.co.bh.R;

import org.apache.http.util.ByteArrayBuffer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;


/**
 *	���̳� �ٸ� ���� �������� �̹����� �����޾� �����ش�.
 */
public class WebImageView extends ImageView {
	private Drawable mPlaceholder;
	private Drawable mImage;
	private AnimationDrawable aniDraw;	// �ε� �ִϸ��̼�

	private boolean isCompleted = false;

	public WebImageView(final Context context) {
		super(context);
		initImageView();		
	}
	public WebImageView(final Context context, final AttributeSet attrs) {
		super(context, attrs);
		initImageView();		
	}

	public WebImageView(final Context context, final AttributeSet attrs, final int defStyle) {
		super(context, attrs, defStyle);
		initImageView();
	}

	/**
	 * loading image start
	 */
	private  void initImageView(){
		this.setImageResource(R.anim.loading_ani);
		aniDraw = (AnimationDrawable) this.getDrawable();
		aniDraw.start();		
	}

	public void setUrl(final String url){
	}

	/**
	 * ����Ʈ �̹��� ����
	 * @param drawable
	 *  �̹��� drawable
	 */
	public void setPlaceholderImage(final Drawable drawable){
		mPlaceholder = drawable;
		if(mImage == null){
			setImageDrawable(mPlaceholder);
		}
	}

	/**
	 * ����Ʈ �̹��� ����
	 * @param resid
	 * ���ҽ� ���̵�
	 */
	public void setPlaceHolderImage(final int resid){
		mPlaceholder = getResources().getDrawable(resid);
		if(mPlaceholder == null){
			setImageDrawable(mPlaceholder);
		}
	}

	/**
	 * param ���κ��� �̹����� �����޾ƿ� ��Ʈ���� ������ �̹����� �����ش�.
	 * @param url
	 */
	public void setImasgeUrl(final String url){
		// �����̹����� ó�� �ε��̸�
		if(isCompleted == false){
			DownloadTask task = new DownloadTask();
			task.execute(url);
		}else{
			// �̹� �ε��� �Ϸᰡ �Ǿ����� BitmapDrawable�� �̹��� ����
			if(mImage != null){
				setImageDrawable(mImage);
				setScaleType(ScaleType.FIT_XY);
			}
		}
	}

	/**
	 * ������ ó���� �������κ��� �̹����� �޾ƿ� bitmap���� ��ȯ�� �̹����� �־��ش�.
	 */
	private class DownloadTask extends AsyncTask<String, Void, Bitmap>{

		@Override
		protected Bitmap doInBackground(final String... params) {
			String url = params[0];
			try{

				URLConnection connection = (new URL(url)).openConnection();
				InputStream is = connection.getInputStream();
				BufferedInputStream bis = new BufferedInputStream(is);

				ByteArrayBuffer baf = new ByteArrayBuffer(50);
				int current = 0;
				while((current = bis.read()) != -1){
					baf.append((byte)current);
				}
				byte[] imageData = baf.toByteArray();
				return BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
			}catch(Exception exc){
				return null;
			}
		}

		@Override
		protected void onPostExecute(final Bitmap bitmap) {
			if(aniDraw != null && aniDraw.isRunning()){
				aniDraw.stop();
			}
			mImage = new BitmapDrawable(bitmap);
			if(mImage != null){
				setImageDrawable(mImage);
				setScaleType(ScaleType.FIT_XY);	// Ʋ ũ�⿡ �°� ����
			}
			isCompleted = true;	// �����̹��� �ε� �Ϸ�
		}

	}

}
