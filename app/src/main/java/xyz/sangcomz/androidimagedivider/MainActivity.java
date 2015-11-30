package xyz.sangcomz.androidimagedivider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    //    LinearLayout rootView;
    BitmapDivider bitmapDivider;
    ImageView imageView;
    String mLoaderPath;
    final static int TAKE_A_PICK = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bitmapDivider = new BitmapDivider();
        imageView = (ImageView) findViewById(R.id.image_view);
//        Crop.pickImage(this);
//        takePicture();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {
            beginCrop(result.getData());
        } else if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, result);
        } else if (requestCode == TAKE_A_PICK) {
            if (resultCode == RESULT_OK)
                beginCrop(Uri.fromFile(new File(mLoaderPath)));
            else
                new File(mLoaderPath).delete();
        }

    }


    public void takePicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//암시적 인텐트.

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile(); //이미 파일을 만들어버림. 실패할경우 파일 삭제.
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
//                Log.d("photoFile path ", String.valueOf(photoFile));
                startActivityForResult(takePictureIntent, TAKE_A_PICK);
            }
        }
    }

    private File createImageFile() throws IOException {   //파일 만들기
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "Puzzler_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".png",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        mLoaderPath = image.getAbsolutePath();
        return image;
    }


    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped" + System.currentTimeMillis()));
        Crop.of(source, destination).asSquare().start(this);
    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            Glide
                    .with(this)
                    .load(Crop.getOutput(result))
                    .asBitmap()
                    .centerCrop()
                    .override(800, 800)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            imageView.setImageBitmap(resource);
                            Bitmap[] bitmaps = bitmapDivider.bitmapDivider(resource, 3);
                            System.out.println("bitmap.length :::: " + bitmaps.length);

                        }
                    });
//            imageView.setImageURI(Crop.getOutput(result));
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}



