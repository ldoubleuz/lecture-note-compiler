package com.noteright;

import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

public class ImagePreviewActivity extends Activity {

	//opencv stuff
	private void process(Bitmap b){
		
    	Mat m = new Mat (b.getWidth(),b.getHeight(),CvType.CV_8UC1);

    	Utils.bitmapToMat(b, m);
    	
    	Imgproc.cvtColor(m, m, Imgproc.COLOR_RGB2GRAY);
		
		Utils.matToBitmap(m, b);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_preview);
		
		Intent intent = getIntent();
		String filepath = intent.getStringExtra(HomeActivity.CAM_FILEPATH_KEY);
		Log.e("butts", filepath);
		
		ImageView imgPreview = (ImageView) findViewById(R.id.proc_image_preview);
		Bitmap bitmapImg = BitmapFactory.decodeFile(filepath);
		
		process(bitmapImg);
		
		
		if(bitmapImg == null){
			imgPreview.setImageResource(R.drawable.blank_img);
		}
		else{
			imgPreview.setImageBitmap(bitmapImg);
		}

		// Show the Up button in the action bar.
		setupActionBar();
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_preview, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
