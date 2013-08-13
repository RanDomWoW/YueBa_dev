package com.qihoo.yueba.ui.activities;

import java.util.ArrayList;
import java.util.HashMap;

import com.ckt.vas.miles.R;
import com.qihoo.yueba.db.service.DBService;
import com.qihoo.yueba.dto.ActivityMessage;
import com.qihoo.yueba.ui.adapters.Desktop;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class FriendListActivity extends Activity {
	SlidingMenu mSlidingMenu;
	DBService dbService;
	private com.qihoo.yueba.ui.adapters.Desktop mDesktop;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friendlist);
        mDesktop = new Desktop(this);
		mSlidingMenu = new SlidingMenu(this);
		setContentView(mSlidingMenu);
		View menu = mDesktop.getView();
		View content = getLayoutInflater().inflate(R.layout.friendlist,
				null);
		mSlidingMenu.setMenu(menu);
		mSlidingMenu.setContent(content);
		dbService = new DBService(FriendListActivity.this);
        //缂佹垵鐣綥ayout闁插矂娼伴惃鍑﹊stView
        ListView list = (ListView) findViewById(R.id.ListView01);
        
        //閻㈢喐鍨氶崝銊︼拷閺佹壆绮嶉敍灞藉閸忋儲鏆熼幑锟�       
        ArrayList<HashMap<String, Object>> listItem 
        	= new ArrayList<HashMap<String, Object>>();
        for(int i=0;i<6;i++)
        {
        	HashMap<String, Object> map = new HashMap<String, Object>();
        	map.put("ItemImage", R.drawable.gauss0);//閸ユ儳鍎氱挧鍕爱閻ㄥ嚘D
        	map.put("ItemTitle", "Gauss"+(i+1));
        	map.put("ItemText", "唱歌 跳舞 读书 写字");
        	listItem.add(map);
        }
        
    	
        
        
        SimpleAdapter listItemAdapter = new SimpleAdapter(this,listItem,R.layout.list_items,
        		new String[] {"ItemImage","ItemTitle", "ItemText"}, 
        		new int[] {R.id.ItemImage,R.id.ItemTitle,R.id.ItemText}
        );
       
        //濞ｈ濮為獮鏈电瑬閺勫墽銇�       
        list.setAdapter(listItemAdapter);
        
        //濞ｈ濮為悙鐟板毊
        list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				//setTitle("閻愮懓鍤粭锟�arg2+"娑擃亪銆嶉惄锟�;
				Intent intent = new Intent();
				intent.setClass(FriendListActivity.this, TimeMergeActivity.class);
				// set the intent, then send it
				startActivity(intent);
			}
		});
/*        
      //濞ｈ濮為梹鎸庡瘻閻愮懓鍤�        list.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {
			
			@Override
			public void onCreateContextMenu(ContextMenu menu, View v,
											ContextMenuInfo menuInfo) {
				menu.setHeaderTitle("闂�寧瀵滈懣婊冨礋-ContextMenu");   
				menu.add(0, 0, 0, "瀵懓鍤梹鎸庡瘻閼挎粌宕�");
				menu.add(0, 1, 0, "瀵懓鍤梹鎸庡瘻閼挎粌宕�");   
			}
		}); */
		ImageView menu_bar = (ImageView) findViewById(R.id.qa_bar_menu);
		// Button add_thing = (Button)findViewById(R.id.qa_bar_friends);
		// mSlidingMenu.showMenu();
		// setContentView(R.layout.feed_activity2);

		menu_bar.setClickable(true);
		menu_bar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mSlidingMenu.showMenu();
			}
		});
    }
	
	//闂�寧瀵滈懣婊冨礋閸濆秴绨查崙鑺ユ殶
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		setTitle(""); 
		return super.onContextItemSelected(item);
	}
}