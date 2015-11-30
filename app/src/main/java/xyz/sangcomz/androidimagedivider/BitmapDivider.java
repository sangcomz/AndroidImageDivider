package xyz.sangcomz.androidimagedivider;

import android.graphics.Bitmap;

/**
 * Created by sangc on 2015-11-27.
 */
public class BitmapDivider {

    /**
     * size = 3 or 4 3 == 3x3, 4 == 4x4
     * @param size
     */
    public Bitmap[] bitmapDivider(Bitmap oriBitmap, int size){
        Bitmap[] piecesBitmaps = new Bitmap[size*size];

        int height = oriBitmap.getHeight();
        int width = oriBitmap.getWidth();

        int picesHeight = height / size;
        int picesWidth = width / size;

        int count = 0;
        for (int j = 0; j < size; j++) {
            for (int i = 0; i < size; i++) {
                piecesBitmaps[count] = Bitmap.createBitmap(oriBitmap, i * picesWidth, j * picesHeight, picesWidth, picesHeight);
                count++;
            }
        }
        return piecesBitmaps;
    }
}
