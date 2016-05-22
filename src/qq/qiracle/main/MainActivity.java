package qq.qiracle.main;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.Window;
import qq.qiracle.fragment.MenuFragment;
import qq.qiracle.qwords.R;



public class MainActivity extends SlidingFragmentActivity {

	SlidingMenu sm;

	/**
	 * 1.�õ������˵� 2.���û����˵�����߻��Ǵ��ұ߳��� 3.���û����˵�����֮������ҳ��ʾ��ʣ����
	 * 4.���û����˵�����Ӱ����ʼ�ر𰵣��������䵭
	 * 
	 * 
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setBehindContentView(R.layout.menu);
		setContentView(R.layout.content);

		
	
		sm = getSlidingMenu();

		sm.setMode(SlidingMenu.LEFT);
		// ���ò˵���Ӱ
		sm.setBehindWidthRes(R.dimen.slidingmenu_offset);

		// ������Ӱ���
		sm.setShadowDrawable(R.drawable.shadow);
		// ���û����˵���Χ
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

		MenuFragment menuFragment = new MenuFragment();
		
		getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame, menuFragment, "Menu").commit();
		
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void switchFragment(Fragment f) {

		getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, f).commit();
		sm.toggle();// �Զ��л�

	}
}