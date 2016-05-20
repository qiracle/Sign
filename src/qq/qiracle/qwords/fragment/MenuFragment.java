
//======================================================================
 //
 //        Copyright (C) 2016   
 //        All rights reserved
 //
 //        filename :MenuFragment
 //        
 //
 //        created by Qiangqiang Jinag in  2016.04
 //        https://github.com/qiracle
 //		   qiracle@foxmail.com
 //
 //======================================================================
package qq.qiracle.qwords.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import qq.qiracle.qwords.MainActivity;
import qq.qiracle.qwords.R;
import android.widget.ArrayAdapter;
import android.widget.ListView;

//import qq.qiracle.news.MainActivity;

public class MenuFragment extends Fragment implements OnItemClickListener {

	private View view;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);
		ListView list_view = (ListView) view.findViewById(R.id.list_view);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,
				android.R.id.text1, initData());

		list_view.setAdapter(adapter);

		list_view.setOnItemClickListener(this);

	}

	private List<String> initData() {
		List<String> list = new ArrayList<String>();
		list.add("µ«»Î");
		list.add("–ﬁ∏ƒ√‹¬Î");
		list.add("…Ë÷√");
		
		return list;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = LayoutInflater.from(getActivity()).inflate(R.layout.list_view, null);

		return view;

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Fragment f = null;
		switch (position) {
		
		case 0:
			f= new Fragment1();

			break;

		case 1:
			f= new Fragment2();
			break;

		case 2:
			f= new Fragment3();
			break;

		

		

		}
		
		
		switchFragment(f);

	}

	private void switchFragment(Fragment f) {
	if(f!=null){
		
		if(getActivity() instanceof MainActivity){
			
			((MainActivity)getActivity()).switchFragment(f);
		}
	}
		
	}

}
