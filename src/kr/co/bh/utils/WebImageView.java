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
 *	웹이나 다른 원격 서버에서 이미지를 내려받아 보여준다.
 */
public class WebImageView extends ImageView {
	private Drawable mPlaceholder;
	private Drawable mImage;
	private AnimationDrawable aniDraw;	// 로딩 애니메이션

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
	 * 디폴트 이미지 셋팅
	 * @param drawable
	 *  이미지 drawable
	 */
	public void setPlaceholderImage(final Drawable drawable){
		mPlaceholder = drawable;
		if(mImage == null){
			setImageDrawable(mPlaceholder);
		}
	}

	/**
	 * 디폴트 이미지 셋팅
	 * @param resid
	 * 리소스 아이디
	 */
	public void setPlaceHolderImage(final int resid){
		mPlaceholder = getResources().getDrawable(resid);
		if(mPlaceholder == null){
			setImageDrawable(mPlaceholder);
		}
	}

	/**
	 * param 으로부터 이미지를 내려받아와 비트맵을 생성후 이미지를 보여준다.
	 * @param url
	 */
	public void setImasgeUrl(final String url){
		// 원격이미지가 처음 로딩이면
		if(isCompleted == false){
			DownloadTask task = new DownloadTask();
			task.execute(url);
		}else{
			// 이미 로딩이 완료가 되었으면 BitmapDrawable로 이미지 설정
			if(mImage != null){
				setImageDrawable(mImage);
				setScaleType(ScaleType.FIT_XY);
			}
		}
	}

	/**
	 * 쓰레드 처리로 원격지로부터 이미지를 받아와 bitmap으로 변환후 이미지에 넣어준다.
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
				setScaleType(ScaleType.FIT_XY);	// 틀 크기에 맞게 조정
			}
			isCompleted = true;	// 원격이미지 로딩 완료
		}

	}

}
