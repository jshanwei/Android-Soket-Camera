package sample.camera.utils;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;

/**
 * Bitmap���[�e�B���e�B�N���X
 * 
 * @author k-daigo
 */
public class BitmapUtil {

	/**
	 * Bitmap��byte�z��ɕϊ�����
	 * @param src �ϊ�����Bitmp
	 * @param format Bitmap.CompressFormat���w�肷��
	 * @param quality �掿
	 * @return �ϊ�����byte�z��
	 */
	public static byte[] bmp2data(Bitmap src, Bitmap.CompressFormat format, int quality) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		src.compress(format, quality, outputStream);
		return outputStream.toByteArray();
	}
}
