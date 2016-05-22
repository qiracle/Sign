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
	 * 1.得到滑动菜单 2.设置滑动菜单从左边还是从右边出来 3.设置滑动菜单出来之后内容页显示的剩余宽度
	 * 4.设置滑动菜单的阴影，开始特别暗，，慢慢变淡
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
		// 设置菜单阴影
		sm.setBehindWidthRes(R.dimen.slidingmenu_offset);

		// 设置阴影宽度
		sm.setShadowDrawable(R.drawable.shadow);
		// 设置滑动菜单范围
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
		sm.toggle();// 自动切换

	}
}