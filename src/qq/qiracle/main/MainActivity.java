package qq.qiracle.main;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import qq.qiracle.fragment.Fragment1;
import qq.qiracle.fragment.Fragment11;
import qq.qiracle.fragment.MenuFragment;
import qq.qiracle.qwords.R;

public class MainActivity extends SlidingFragmentActivity implements OnClickListener{

	SlidingMenu sm;
	private ImageView topButton;
	private TextView topView;
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
		 // 设置渐入渐出效果的值  
        sm.setFadeDegree(0.35f); 
		MenuFragment menuFragment = new MenuFragment();

		getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame, menuFragment, "Menu").commit();

		/* 默认显示第一个Fragment */

		Intent intent = getIntent();
		int type = intent.getIntExtra("type", 0);
		if (type == 1) {

			Bundle bundle = new Bundle();
			bundle.putInt("flag", 1);
			menuFragment.setArguments(bundle);
			Fragment1 f1 = new Fragment1();
			getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, f1).commit();
		}

		if (type == 2) {
			Bundle bundle = new Bundle();
			bundle.putInt("flag", 2);
			menuFragment.setArguments(bundle);
			Fragment11 f11 = new Fragment11();
			getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, f11).commit();
		}

		topView = (TextView) findViewById(R.id.topTv);
		topButton = (ImageView) findViewById(R.id.topButton);
		topButton.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void switchFragment(Fragment f ,String title) {

		getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, f).commit();
		topView.setText(title);
		sm.toggle();// 自动切换

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.topButton:
			toggle();
			break;
		default:
			break;
		}
	}



	
	
}