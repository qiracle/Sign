package qq.qiracle.fragment;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.zxing.encoding.EncodingHandler;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import qq.qiracle.loginActivity.LoginActivity;
import qq.qiracle.main.MainActivity;
import qq.qiracle.qwords.R;
import qq.qiracle.systemservice.SystemService;
import qq.qiracle.systemservice.SystemServiceImpl;
import qq.qiracle.userservice.ServiceRulesException;

public class Fragment11 extends Fragment{
	View view;
	Button btnExitTeacher;
	TextView teacher_num;
	private ImageView qrImgImageView;
	private static final int IMAGE_HALFWIDTH = 20;
	int[] pixels = new int[2 * IMAGE_HALFWIDTH * 2 * IMAGE_HALFWIDTH];
	private Bitmap mBitmap;

	private String teacherNum;
	
	private SystemService systemService = new SystemServiceImpl();
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.teacher_main, null);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
				RelativeLayout.LayoutParams.MATCH_PARENT);
		view.setLayoutParams(lp);
		
		qrImgImageView = (ImageView) view.findViewById(R.id.iv_qr_image);
	    teacher_num = (TextView) view.findViewById(R.id.tv_teacher);
		 btnExitTeacher = (Button) view.findViewById(R.id.btn_exit_teacher);
		 btnExitTeacher.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
					
					dialogBuilder.setTitle("ע�⣡");
					dialogBuilder.setMessage("��ȷ��Ҫ�˳���");
				
					dialogBuilder.setPositiveButton("ȷ��", 
							new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							
						Intent intent	=new Intent(getActivity(),LoginActivity.class);
						
						startActivity(intent);
						getActivity().finish();
						}
					});
					dialogBuilder.setNegativeButton("ȡ��", 
							new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							
						}
					});
					
					
					
					dialogBuilder.show();
					
					
				}
			});
		
		 Intent intent = getActivity().getIntent();
			teacherNum = intent.getStringExtra("Username");
			teacher_num.setText("���ã����Ľ̹���Ϊ"+teacherNum);
			


			Button generateQRCodeButton = (Button) view
					.findViewById(R.id.btn_add_qrcode);
			generateQRCodeButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					try {
					
						 long currentTimeMillis = System.currentTimeMillis();
						 final String s = Long.toString(currentTimeMillis);
						final String contentString = s;
						
						new Thread(new Runnable() {
							
							@Override
							public void run() {
								boolean state = false;
								try {
									state = systemService.setStringQrcode(contentString);
								} catch(final ServiceRulesException e) {
									
									getActivity().runOnUiThread(new Runnable() {
										public void run() {
											Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
										}
									});
									
								}catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								if(!state){
									getActivity().runOnUiThread(new Runnable() {
										
										@Override
										public void run() {
											Toast.makeText(getActivity(), "����������", Toast.LENGTH_SHORT).show();;
											
										}
									});
									
								}
							}
						}){
							
						}.start();
					
						
						
						
						if (contentString != null
								&& contentString.trim().length() > 0) {
							// �����ַ������ɶ�ά��ͼƬ����ʾ�ڽ����ϣ��ڶ�������ΪͼƬ�Ĵ�С��1000*1000��
							Bitmap qrCodeBitmap = EncodingHandler.createQRCode(
									contentString, 1000);
							saveJpeg(qrCodeBitmap);
							qrImgImageView.setImageBitmap(qrCodeBitmap);
						} else {
							Toast.makeText(getActivity(),
									"�ı�����Ϊ��", Toast.LENGTH_SHORT)
									.show();
						}

					} catch (WriterException e) {
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});


		return view;
	}
	public Bitmap cretaeBitmap(String str) throws WriterException {

		Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		hints.put(EncodeHintType.MARGIN, 1);

		BitMatrix matrix = new MultiFormatWriter().encode(str,
				BarcodeFormat.QR_CODE, 300, 300, hints);
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		int halfW = width / 2;
		int halfH = height / 2;
		int[] pixels = new int[width * height];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (x > halfW - IMAGE_HALFWIDTH && x < halfW + IMAGE_HALFWIDTH
						&& y > halfH - IMAGE_HALFWIDTH
						&& y < halfH + IMAGE_HALFWIDTH) {
					pixels[y * width + x] = mBitmap.getPixel(x - halfW
							+ IMAGE_HALFWIDTH, y - halfH + IMAGE_HALFWIDTH);
				} else {
					if (matrix.get(x, y)) {
						pixels[y * width + x] = 0xff000000;
					} else {
						pixels[y * width + x] = 0xffffffff;
					}
				}
			}
		}
		Bitmap bitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		bitmap.setPixels(pixels, 0, width, 0, 0, width, height);

		return bitmap;
	}

	public String initSavePath() {
		File dateDir = Environment.getExternalStorageDirectory();
		String path = dateDir.getAbsolutePath() + "/RectPhoto/";
		File folder = new File(path);
		if (!folder.exists()) {
			folder.mkdir();
		}
		return path;
	}

	public void saveJpeg(Bitmap bm) {

		long dataTake = System.currentTimeMillis();
		String jpegName = initSavePath() + dataTake + ".jpg";


		try {
			FileOutputStream fout = new FileOutputStream(jpegName);
			BufferedOutputStream bos = new BufferedOutputStream(fout);


			bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
			bos.flush();
			bos.close();

		} catch (IOException e) {
			

			e.printStackTrace();
		}
	}
}
