
package com.qihoo.yueba.ui.adapters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ckt.vas.miles.R;
import com.qihoo.yueba.db.service.DBService;
import com.qihoo.yueba.dto.ActivityMessage;
import com.qihoo.yueba.ui.activities.TimeMergeActivity;

public class TimeMergeActivityAdapter extends BaseAdapter {
    protected static final String TAG = "ChattingAdapter";

    private Context context;
    
    private DBService dbService;

    public static final String TEXT_FORMAT = "<font color='#1479ad'><b>%s</b></font>";

    public static final String TEXT_ADDFRD_FORMAT_WITHFROM = "<font color='#1479ad'>%s</font> 与 <font color='#1479ad'>%s</font> 成为了好友";

    public static final String TEXT_ADDFRD_NOFROM = "与 <font color='#1479ad'><b>%s</b></font> 成为了好友";

    private static List<ActivityMessage> msgs;


    public TimeMergeActivityAdapter(Context context, List<ActivityMessage> messages) {
        super();
        this.context = context;
        this.msgs = messages;
        dbService = new DBService(context);
    }

    public TimeMergeActivityAdapter() {
		// TODO Auto-generated constructor stub

	}

	@Override
    public int getCount() {
        return msgs.size();
    }

    @Override
    public Object getItem(int position) {
        return msgs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

	public String[] getEmptyTime(String[] empty, String[] full) {
		if (cmpTime(empty[0], full[1]) < 2 || cmpTime(empty[1], full[0]) == 2) {
			return empty;
		} else {
			if (cmpTime(empty[0], full[1]) == 2) {
				String[] result = { empty[0], full[0] };
				return result;
			} else {
				String[] result = { full[1], empty[1] };
				return result;
			}

		}

	}
    
    
    /**
     * 
     * @param t1
     * @param t2
     * @return 0相等  1 大于   2 小于
     */
    public int cmpTime(String t1, String t2){
    	String t1s[] = t1.split(":");
    	String t2s[] = t1.split(":");
    	if(Integer.parseInt(t1s[0]) > Integer.parseInt(t2s[0])){
    		return 1;
    	}
    	else if(Integer.parseInt(t1s[0]) < Integer.parseInt(t2s[0])){
    		return 2;
    	}
    	else if(Integer.parseInt(t1s[1]) > Integer.parseInt(t2s[1])){
    		return 1;
    	}
    	else if(Integer.parseInt(t1s[1]) < Integer.parseInt(t2s[1])){
    		return 2;
    	}
    	return 0;
    }
    
    
    public static void main(String[] args){
    	TimeMergeActivityAdapter taa = new TimeMergeActivityAdapter();
    	System.out.println(taa.cmpTime("5:40", "6:20"));
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ActivityMessage message = msgs.get(position);
        System.out.println("position===========" + position);
        ViewHolder holder;
        if (convertView == null || (holder = (ViewHolder) convertView.getTag()).flag != position) {
            holder = new ViewHolder();

            if (position == 0) {

            	holder.flag = position;
                convertView = LayoutInflater.from(context).inflate(R.layout.timemerge_cover_row, null);
                final RelativeLayout rl = (RelativeLayout) convertView.findViewById(R.id.timemerge);
                List<ImageView> livs = new ArrayList<ImageView>();
                
            /*    //第一个好友
                final ImageView iv = new ImageView(convertView.getContext());
				iv.setImageResource(R.drawable.gauss0);
				iv.setPadding(5, 5, 5, 5);
				iv.setLayoutParams(new LayoutParams(200, 200));
				iv.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						msgs.clear();
						msgs.add(new ActivityMessage());
						msgs.add(new ActivityMessage( R.drawable.gauss0, "Gauss", "8:00至9:00", "空闲时间： 8:00至9:00",
				                "8:00", "9:00"));
						msgs.add(new ActivityMessage( R.drawable.gauss0, "Gauss", "8:00至9:00", "空闲时间： 8:00至9:00",
				                "8:00", "9:00"));
						msgs.add(new ActivityMessage( R.drawable.gauss0, "Gauss", "8:00至9:00", "空闲时间： 8:00至9:00",
				                "8:00", "9:00"));
						View vp = (View) v.getRootView();
						RelativeLayout rl = (RelativeLayout) vp.findViewById(R.id.timemerge);
						
						TextView tv = new TextView(vp.getContext());
						tv.setText("");
						tv.setHeight(1);
						tv.setGravity(0x11);
						rl.addView(tv);
						rl.invalidate();
					}
				});
				livs.add(iv);*/
                
                //设置好友空闲时间
                
                /*String[][] emptyTimes = {
                		{"9:00-10:00", "12:00-13:20", "15:00-19:15"},
                		{"9:30-10:00", "12:05-13:20", "15:30-19:15"},
                		{"7:30-10:00", "13:05-13:20", "16:30-19:15"}
                };
                
                
                List<List> allTimeList = new ArrayList<List>();
                List<HashMap> timeList = new ArrayList<HashMap>();
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("startTime", "8:00");
                map.put("endTime", "9:00");
                map = new HashMap<String, String>();
                map.put("startTime", "9:50");
                map.put("endTime", "10:00");
                timeList.add(map);
                allTimeList.add(timeList);
                
                List l = dbService.getPersons(0, (int)dbService.getTotalCounts());
                String[] myfulltime = new String[20] ;
                for(int i=0;i<l.size();i++){
                	myfulltime[i] = ((List)l.get(i)).get(5).toString() + "-" +((List)l.get(i)).get(6).toString(); 
                }
                */
                
                
				/*class MyClickListener implements View.OnClickListener {
					private View myv = null;
					private ActivityMessage[] msg;

					public MyClickListener(View v, ActivityMessage[] msg) {
						this.myv = v;
						this.msg = msg;
					}

					public void onClick(View v) {

						msgs.clear();
						msgs.add(new ActivityMessage());
						for (int i = 0; i < msg.length; i++) {
							msgs.add(msg[i]);
						}
						View vp = (View) v.getRootView();
						RelativeLayout rl = (RelativeLayout) vp
								.findViewById(R.id.timemerge);
						TextView tv = new TextView(vp.getContext());
						tv.setText("");
						tv.setHeight(1);
						tv.setGravity(0x11);
						rl.addView(tv);
						rl.invalidate();
					}
				}
				
				
				//张飞
				final ImageView iv = new ImageView(convertView.getContext());
				iv.setImageResource(R.drawable.zhangfei);
				iv.setPadding(5, 5, 5, 5);
				iv.setLayoutParams(new LayoutParams(200, 200));
				ActivityMessage[] msg = {
						new ActivityMessage(R.drawable.zhangfei, "张飞", null, "空闲时间： 8:00至9:00", null, null	),
						new ActivityMessage(R.drawable.zhangfei, "张飞", null, "空闲时间： 8:00至9:00", null, null	)
				};
				iv.setOnClickListener(new MyClickListener(convertView, msg));
				*/
				
				
                
                for(int i=0; i<6;i++){
					final ImageView iv = new ImageView(convertView.getContext());
					iv.setImageResource(R.drawable.gauss0);
					iv.setPadding(5, 5, 5, 5);
					iv.setLayoutParams(new LayoutParams(200, 200));
					class MyClickListener implements View.OnClickListener{
                        private View myv = null;
                        private int j;
						public MyClickListener(View v, int j){
							this.myv = v;
							this.j = j;
						}
						public void onClick(View v) {

							msgs.clear();
							msgs.add(new ActivityMessage());
							msgs.add(new ActivityMessage(0, "Bian", "空间时间 8:00 至 10：00", "", "8:00", "10:00" ));
							
							View vp = (View) v.getRootView();
							RelativeLayout rl = (RelativeLayout) vp.findViewById(R.id.timemerge);
							
							TextView tv = new TextView(vp.getContext());
							tv.setText("");
							tv.setHeight(1);
							tv.setGravity(0x11);
							rl.addView(tv);
							rl.invalidate();

							
						}
						
					}
					iv.setOnClickListener(new MyClickListener(convertView, i+1));
					livs.add(iv);
                }
                LinearLayout ll = (LinearLayout) convertView.findViewById(R.id.toolbar_items);
                for (int i = 0; i < 6; i++) {
					ll.addView(livs.get(i));
				}
            }

            else {

                int type = message.getType();
                holder.flag = position;

                // Item layout
                convertView = LayoutInflater.from(context).inflate(
                        R.layout.timemerge_activity_item, null);

                // author text
                ImageView authorView = (ImageView) convertView
                        .findViewById(R.id.mixed_feed_author_photo);
                authorView.setImageResource(R.drawable.gauss0);
			
                // author img
                TextView authorName = (TextView) convertView
                        .findViewById(R.id.mixed_feed_authorname);
                authorName.setText(message.getName());

                // big circle
                ImageView big = (ImageView) convertView.findViewById(R.id.moment_bigdot);

                // big smallcircle
                ImageView smal = (ImageView) convertView.findViewById(R.id.moment_smalldot);

                // image type
               // ImageView imgType = (ImageView) convertView.findViewById(R.id.moment_people_photo);

                // feed type image
                ImageView feed_post_type = (ImageView) convertView
                        .findViewById(R.id.feed_post_type);

                // content layout
                LinearLayout contentLayout = (LinearLayout) convertView
                        .findViewById(R.id.feed_post_body);
                // Text
                if (ActivityMessage.MESSAGE_TYPE_TEXT == 1) {
                    big.setVisibility(View.GONE);
                    smal.setVisibility(View.VISIBLE);
                    View view = LayoutInflater.from(context).inflate(
                            R.layout.timemerge_moment_thought_partial, null);

                    TextView thought_main = (TextView) view.findViewById(R.id.thought_main);
                    // thought_main.setText(message.getBody());
                   /* String txtstr = String.format(TEXT_FORMAT, message.getAuthorName(),
                            message.getStoreName());

                    Spanned spt = Html.fromHtml(txtstr);

                    thought_main.setText(spt);*/
                   // thought_main.setText(message.getStoreName());
                    thought_main.setText(message.getTitle());
                    
                    TextView textView = (TextView) view.findViewById(R.id.comment_body);
                    textView.setText("有空一起喝杯茶吗？？？？？");
                    
                    ImageView imageButton = (ImageView) view.findViewById(R.id.comment_button_image);
                    imageButton.setOnClickListener(new OnClickListener() {
						
						public void onClick(View v) {
							// TODO Auto-generated method stub
							 Toast toast = Toast.makeText(v.getContext(),
								     "功能还在开发中。。。。", Toast.LENGTH_LONG);
								   toast.setGravity(Gravity.CENTER, 0, 0);
								   toast.show();
							
						}
                    });

                    contentLayout.addView(view);

                }

            }
            convertView.setTag(holder);

        }

        return convertView;
    }

    static class ViewHolder {
        TextView text;

        TextView time;

        TextView status;

        int flag = -1;
    }

}
