package sample.camera.device;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.util.Log;
import android.view.SurfaceHolder;

/**
 * �J�����T�[�o����摜���擾����N���X<br>
 * �i�V���O���g���j
 * 
 * @author k-daigo
 */
public class SocketCamera {
	private static final String LOG_TAG = "SocketCamera:";
	private static final int SOCKET_TIMEOUT = 1000;

	// �T�[�o�̃A�h���X
	private static final String SERVER_ADDRESS = "192.168.111.100";
	private static final int SERVER_PORT = 9889;

	// ��instance
	private static SocketCamera socketCamera;

	private CameraPreview cameraPreview;
	private Camera parametersCamera;
	private SurfaceHolder surfaceHolder;

	private final boolean preserveAspectRatio = true;
	private final Paint paint = new Paint();

	// �摜�T�C�Y
	private int width = 240;
	private int height = 200;
	private Rect bounds = new Rect(0, 0, width, height);

	private Bitmap currentBitmap = null;
	
	/**
	 * �R���X�g���N�^�͉B��
	 */
	private SocketCamera() {
	}

	/**
	 * ���N���X�̃C���X�^���X��Ԃ�
	 * 
	 * @return SocketCamera�̃C���X�^���X
	 */
	public static SocketCamera getInstance() {
		if (socketCamera == null) {
			socketCamera = new SocketCamera();
		}
		return socketCamera;
	}

	/**
	 * �v���r���[���J�n����
	 */
	public void startPreview() {
		cameraPreview = new CameraPreview();
		cameraPreview.start();
	}

	/**
	 * �v���r���[���~����
	 */
	public void stopPreview() {
		cameraPreview.stopPreview();
	}

	/**
	 * ���r���[�����Ԃ�
	 * @return	true	: �v���r���[��
	 * 			false	: �v���r���[���łȂ�
	 */
	public boolean isPreviewing()
	{
		return cameraPreview.isPreviewing();
	}

	/**
	 * ���O�̉摜��Ԃ�
	 * @return Bitmap�摜
	 */
	public Bitmap getCaptur()
	{
		return this.currentBitmap;
	}

	/**
	 * SurfaceHolder��ݒ肷��
	 * @param surfaceHolder
	 * @throws IOException
	 */
	public void setPreviewDisplay(SurfaceHolder surfaceHolder) throws IOException {
		this.surfaceHolder = surfaceHolder;
		this.surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_NORMAL);
	}

	/**
	 * �J�����p�����[�^��ݒ肷��
	 * @param parameters
	 */
	public void setParameters(Camera.Parameters parameters) {
		parametersCamera.setParameters(parameters);
		Size size = parameters.getPreviewSize();
		bounds = new Rect(0, 0, size.width, size.height);
	}

	/**
	 * �J�����p�����[�^��Ԃ�
	 * @return �J�����p�����[�^
	 */
	public Camera.Parameters getParameters() {
		Log.i(LOG_TAG, "Getting Socket Camera parameters");
		return parametersCamera.getParameters();
	}

	/**
	 * �J�����T�[�o����摜���擾����N���X
	 * 
	 * @author k-daigo
	 */
	private class CameraPreview extends Thread {
		private boolean previewing = false;

		/**
		 * �L���v�`�������Ԃ�
		 * @return
		 */
		public boolean isPreviewing()
		{
			return this.previewing;
		}
		
		/**
		 * �L���v�`�����J�n����
		 */
		public void startPreview() {
			this.previewing = true;
		}

		/**
		 * �L���v�`�����~����
		 */
		public void stopPreview() {
			this.previewing = false;
		}
		
		/**
		 * �L���v�`���X���b�h�J�n
		 */
		@Override
		public void run() {
			this.startPreview();

			while (previewing) {
				Canvas canvas = null;
				try {
					canvas = surfaceHolder.lockCanvas(null);
					synchronized (surfaceHolder) {
						Bitmap bitmap = this.getData();

						if (bounds.right == bitmap.getWidth() && bounds.bottom == bitmap.getHeight()) {
							canvas.drawBitmap(bitmap, 0, 0, null);
							
						} else {
							Rect dest;
							if (preserveAspectRatio) {
								dest = new Rect(bounds);
								dest.bottom = bitmap.getHeight() * bounds.right / bitmap.getWidth();
								dest.offset(0, (bounds.bottom - dest.bottom) / 2);
							} else {
								dest = bounds;
							}
							
							if (canvas != null) {
								currentBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
								canvas.drawBitmap(bitmap, null, dest, paint);
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (canvas != null) {
						surfaceHolder.unlockCanvasAndPost(canvas);
					}
				}
			}
		}

		/**
		 * �T�[�o����摜���擾����
		 * 
		 * @return Bitmap
		 * @throws IOException
		 */
		private Bitmap getData() throws IOException {
			Socket socket = null;
			Bitmap bitmap = null;
			try {
				socket = new Socket();
				socket.bind(null);
				socket.setSoTimeout(SOCKET_TIMEOUT);
				socket.connect(new InetSocketAddress(SERVER_ADDRESS, SERVER_PORT), SOCKET_TIMEOUT);

				InputStream in = socket.getInputStream();
				bitmap = BitmapFactory.decodeStream(in);
			} finally {
				if(socket != null){
					socket.close();
				}
			}

			return bitmap;
		}
	}
}
