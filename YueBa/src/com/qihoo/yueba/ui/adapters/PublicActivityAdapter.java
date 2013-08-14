
package com.qihoo.yueba.ui.adapters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ckt.vas.miles.R;
import com.qihoo.yueba.dto.ActivityMessage;
import com.qihoo.yueba.ui.activities.PublicActivity;
import com.qihoo.yueba.ui.views.CircularImage;

public class PublicActivityAdapter<PiblicActivityAdapter> extends BaseAdapter {
    protected static final String TAG = "ChattingAdapter";
    protected static final String ACTION = "com.qihoo.psec.startactivity"; 

    private PublicActivity pa;
    private SimpleDateFormat formatter;
    private Context context;

    public static final String TEXT_FORMAT = "<font color='#1479ad'><b>%s</b></font>";

//    public static final String TEXT_ADDFRD_FORMAT_WITHFROM = "<font color='#1479ad'>%s</font> 涓�<font color='#1479ad'>%s</font> 鎴愪负浜嗗ソ鍙�;
//
//    public static final String TEXT_ADDFRD_NOFROM = "涓�<font color='#1479ad'><b>%s</b></font> 鎴愪负浜嗗ソ鍙�;

    private List<ActivityMessage> msgs;
    

    public PublicActivityAdapter(Context context, List<ActivityMessage> messages) {
        super();
        this.context = context;
        this.msgs = messages;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ActivityMessage message = msgs.get(position);
        System.out.println("position===========" + position);
        ViewHolder holder;
        if (convertView == null || (holder = (ViewHolder) convertView.getTag()).flag != position) {
            holder = new ViewHolder();

            if (position == 0) {
            	holder.flag = position;
                convertView = LayoutInflater.from(context).inflate(R.layout.mixed_feed_cover_row,
                        null);
                CircularImage avatar = (CircularImage) convertView
                        .findViewById(R.id.cover_user_photo);
                avatar.setImageResource(R.drawable.gauss0);

                // Set 3 photos
          /*      ImageView cover = (ImageView) convertView.findViewById(R.id.cover_image);
                cover.setImageResource(R.drawable.cover);
                CircularImage avatar = (CircularImage) convertView
                        .findViewById(R.id.cover_user_photo);
                avatar.setImageResource(R.drawable.gauss0);
                RoundedCornersImage friend = (RoundedCornersImage) convertView
                        .findViewById(R.id.cover_requests_photo);
                friend.setImageResource(R.drawable.andrew0);
*/
                // lay.setl
                // ((LinearLayout)convertView).setLayoutParams(new
                // LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, 200));
            }

            else {

                int type = message.getType();
                holder.flag = position;

                // Item layout
                convertView = LayoutInflater.from(context).inflate(
                        R.layout.mixed_feed_activity_item, null);

                // author text
                /*
                ImageView authorView = (ImageView) convertView
                        .findViewById(R.id.mixed_feed_author_photo);
                authorView.setImageResource(message.getAuthorAvatar());
			*/
                // author img
                TextView authorName = (TextView) convertView
                        .findViewById(R.id.mixed_feed_authorname);
        		formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");   
                authorName.setText(formatter.format(message.getStartTime()) + " -- " + formatter.format(message.getEndTime()));

                // big circle
                ImageView big = (ImageView) convertView.findViewById(R.id.moment_bigdot);

                // big smallcircle
                ImageView smal = (ImageView) convertView.findViewById(R.id.moment_smalldot);

                // image type
                ////ImageView imgType = (ImageView) convertView.findViewById(R.id.moment_people_photo);

                // feed type image
                ImageView feed_post_type = (ImageView) convertView.findViewById(R.id.feed_post_type);

                // content layout
                LinearLayout contentLayout = (LinearLayout) convertView
                        .findViewById(R.id.feed_post_body);
                // Text
                if (ActivityMessage.MESSAGE_TYPE_TEXT == type) {
                    big.setVisibility(View.GONE);
                    smal.setVisibility(View.VISIBLE);
                    View view = LayoutInflater.from(context).inflate(
                            R.layout.moment_thought_partial, null);

                    TextView thought_main = (TextView) view.findViewById(R.id.thought_main);
                    // thought_main.setText(message.getBody());
                   /* String txtstr = String.format(TEXT_FORMAT, message.getAuthorName(),
                            message.getStoreName());

                    Spanned spt = Html.fromHtml(txtstr);

                    thought_main.setText(spt);*/
                    thought_main.setText(message.getTitle());
                    thought_main.setClickable(true); //璁剧疆tv涓哄彲鐐瑰嚮銆�
                    thought_main.setOnClickListener(new OnClickListener(){   
                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            //"TextView is clicked"
                        	//tv.setBackgroundColor(R.drawable.m_bar);
                        	Intent intent = new Intent(ACTION);
                        	TextView tv = (TextView)v;
                        	String title = tv.getText().toString();
                        	intent.putExtra("title", title);
                        	PublicActivityAdapter.this.context.sendBroadcast(intent);
                        	//sendBroadcast(intent);
                        }
                        
                    }); 
                    
                    contentLayout.addView(view);

                }
                // Img
                /*
                else if (ActivityMessage.MESSAGE_TYPE_IMG == type) {
                    smal.setVisibility(View.GONE);
                    big.setVisibility(View.VISIBLE);

                    View view = LayoutInflater.from(context).inflate(R.layout.moment_photo_partial,
                            null);
                    feed_post_type.setImageResource(R.drawable.moment_icn_place);
                    // photo
                    ImageView photoView = (ImageView) view.findViewById(R.id.photo);
                    photoView.setImageResource(message.getStoreimg());

                    TextView comment = (TextView) view.findViewById(R.id.comment);
                    String txtstr = String.format(TEXT_FORMAT, message.getAuthorName(),
                            message.getStoreName());
                    Spanned spt = Html.fromHtml(txtstr);
                    comment.setText(spt);

                    contentLayout.addView(view);
                }
                */
/*
                // Friend
                else if (ActivityMessage.MESSAGE_TYPE_MKFRIENDS == type) {
                    smal.setVisibility(View.GONE);
                    big.setVisibility(View.VISIBLE);
                    View view = LayoutInflater.from(context).inflate(
                            R.layout.moment_people_partial, null);
                    imgType.setImageResource(R.drawable.m_san);

                    // mainView.setText("Gauss");
                    // main
                    TextView comment = (TextView) view.findViewById(R.id.people_body);
                    String txtstr = String.format(TEXT_ADDFRD_FORMAT_WITHFROM,
                            message.getAuthorName(), message.getBody());
                    Spanned spt = Html.fromHtml(txtstr);
                    comment.setText(spt);

                    // count of commment
                    TextView countcmt = (TextView) view.findViewById(R.id.comment_button_text);
                    countcmt.setText("5");

                    // friend photo
                    ImageView friendphoto = (ImageView) view.findViewById(R.id.friendphoto);
                    friendphoto.setImageResource(message.getStoreimg());
//
//                    RotateAnimation ra = new RotateAnimation(0f, 150f, Animation.RELATIVE_TO_SELF,
//                            0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//                    ra.setFillAfter(true);
//                    // 璁剧疆鍔ㄧ敾鐨勬墽琛屾椂闂�
//                    ra.setDuration(10000);
//                    
//                    friendphoto.setAnimation(ra);

                    contentLayout.addView(view);
                }
*/
                else {
                    smal.setVisibility(View.GONE);
                    big.setVisibility(View.VISIBLE);
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

    public void setActivity(PublicActivity para) {
    	pa = para;
    }
}
