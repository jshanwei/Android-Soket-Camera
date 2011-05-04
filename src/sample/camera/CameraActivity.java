package sample.camera;

import java.io.IOException;

import sample.camera.device.SocketCamera;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.widget.Button;

/**
 * �J����Activity
 * 
 * @author k-daigo
 */
public class CameraActivity extends Activity {
	private SurfaceView cameraView;

	// private Camera camera;
	private SocketCamera camera;
	private Button buttonCameraMotion;

	/**
	 * Surface��Callback
	 */
	private SurfaceHolder.Callback surfaceHolderCallback = new SurfaceHolder.Callback() {

		/**
		 * Surface������
		 */
		public void surfaceCreated(SurfaceHolder holder) {
			// camera = Camera.open();
			camera = SocketCamera.getInstance();

			try {
				camera.setPreviewDisplay(holder);
			} catch (IOException e) {
				e.printStackTrace();
			}

			Canvas canvas = holder.lockCanvas();
			holder.unlockCanvasAndPost(canvas);
		}

		/**
		 * 
		 */
		public void surfaceChanged(SurfaceHolder holder, int format, int width, int heigth) {
			camera.startPreview();
		}

		/**
		 * Surface�I������
		 */
		public void surfaceDestroyed(SurfaceHolder holder) {
			camera.stopPreview();
			camera = null;
		}
	};

	/**
	 * Create
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.preview);
	}

	/**
	 * Resume
	 */
	@Override
	protected void onResume() {
		super.onResume();

		this.startCamera();
	}

	/**
	 * �J�����摜�\�����J�n
	 */
	private void startCamera() {
		// �e�Q�Ƃ��擾
		this.camera = SocketCamera.getInstance();
		this.cameraView = (SurfaceView) findViewById(R.id.cameraView);

		// �J�����̃R�[���o�b�N��ݒ�
		SurfaceHolder holder = cameraView.getHolder();
		holder.addCallback(surfaceHolderCallback);
		holder.setType(SurfaceHolder.SURFACE_TYPE_NORMAL);

		this.buttonCameraMotion = (Button) findViewById(R.id.buttonCameraMotion);
		this.buttonCameraMotion.setText("�L���v�`����~");
	}

	/**
	 * �L���v�`�� Button click
	 * 
	 * @param view
	 */
	public void onCapturButtonClick(View view) {
		Intent intent = new Intent();
		Log.d("aa", CapturConfirmActivity.class.getPackage().getName());
		Log.d("aa", CapturConfirmActivity.class.getCanonicalName());
		intent.setClassName(CapturConfirmActivity.class.getPackage().getName(), CapturConfirmActivity.class.getCanonicalName());

		// �L���v�`���摜�ۑ�
		intent.putExtra("captur", this.camera.getCaptur());

		startActivity(intent);
	}

	/**
	 * �v���r���[��~/�ĊJ Button click
	 * 
	 * @param view
	 */
	public void onCameraMotionButtonClick(View view) {
		if (this.camera.isPreviewing()) {
			this.camera.stopPreview();
			this.buttonCameraMotion.setText("�L���v�`���ĊJ");
			return;
		}

		this.camera.startPreview();
		this.buttonCameraMotion.setText("�L���v�`����~");

	}
}
