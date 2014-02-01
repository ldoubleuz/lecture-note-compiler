package com.noteright;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

public class HomeActivity extends Activity {
	static final public String CAM_FILEPATH_KEY = "com.notewrite.CAM_FILEPATH";
	static final private int REQUEST_TAKE_PHOTO_CODE = 1;
	static private String mCurrentPhotoPath = null;

	private File createImageFile() throws IOException {
	    // Create an image file name
	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	    String imageFileName = "JPEG_" + timeStamp ;
	    File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

	    Log.e("cam13", "FileName: " +imageFileName + "\nStorageDir: " + storageDir);
	    Log.e("cam13", storageDir.mkdirs() + "\n");
	    
	    File image = File.createTempFile(
	        imageFileName,  /* prefix */
	        ".jpg",         /* suffix */
	        storageDir      /* directory */
	    );

	    // Save a file: path for use with ACTION_VIEW intents
	    this.mCurrentPhotoPath = image.getAbsolutePath();
	    return image;
	}
	
	private void dispatchTakePictureIntent(View view) {
	    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	    // Ensure that there's a camera activity to handle the intent
	    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
	        // Create the File where the photo should go
	        File photoFile = null;
	        try {
	            photoFile = createImageFile();
	        } catch (IOException ex) {
	            // Error occurred while creating the File
	        	return;
	        }
	        // Continue only if the File was successfully created
	        if (photoFile != null) {
	            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
	                    Uri.fromFile(photoFile));
	            startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO_CODE);
	        }
	    }
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == REQUEST_TAKE_PHOTO_CODE && resultCode == RESULT_OK) {
	    	Log.e("cam13", this.mCurrentPhotoPath);

	    	//DO STUFF HERE LIKE PROCESS IMAGE this.mCurrentPhotoPath
	    	
	    	// show preview
	    	Intent previewIntent = new Intent(this, ImagePreviewActivity.class);
	    	previewIntent.putExtra(CAM_FILEPATH_KEY, this.mCurrentPhotoPath);
	    	// move to image preview
	    	startActivity(previewIntent);
	    }
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
        OpenCVLoader.initDebug();
        //mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
    
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}
	
	public void toGalleryPage(View view){
		// TODO: wtf i dunno
	}
	
	public void takePicture(View view){
		// open camera prompt
		this.dispatchTakePictureIntent(view);
	}

}
