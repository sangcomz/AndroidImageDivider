package xyz.sangcomz.androidimagedivider;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class MainActivity extends AppCompatActivity {

    LinearLayout rootView;
    ImageView[] imageViews = new ImageView[7];
    Bitmap[] bitmap = new Bitmap[6];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rootView = (LinearLayout) findViewById(R.id.root_view);

        Resources r = getResources();
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.mipmap.profile);

        imageViews[0] = new ImageView(this);
        imageViews[0].setImageBitmap(bm);
        int height = bm.getHeight();
        int width = bm.getWidth();

        int picesHeight = height / 2;
        int picesWidth = width / 3;

//        bitmap[1] = Bitmap.createBitmap(bm, 0, 0, 100, 100);
//        imageViews = new ImageView(this);
//        imageViews.setImageBitmap(bitmap[1]);
//        rootView.addView(imageViews[1]);


        int count = 0;
        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < 3; i++) {
                System.out.println("i * picesWidth :::: " + i * picesWidth);
                System.out.println("j * picesHeight :::: " + j * picesHeight);
                System.out.println("i * j :::: " + i * j + "\n");
                bitmap[count] = Bitmap.createBitmap(bm, i * picesWidth, j * picesHeight, picesWidth, picesHeight);
                imageViews[count + 1] = new ImageView(this);
                imageViews[count + 1].setImageBitmap(bitmap[count]);
                rootView.addView(imageViews[count + 1]);
                count++;
            }
        }


        //가로가 긴지 세로가 긴지...........가로 사진 위주!?

        System.out.println(height + " , " + width);

//        rootView.addView(imageViews);


    }
}
