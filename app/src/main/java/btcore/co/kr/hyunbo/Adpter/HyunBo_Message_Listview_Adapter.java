package btcore.co.kr.hyunbo.Adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import btcore.co.kr.hyunbo.Item.Message_Listview_Item;
import btcore.co.kr.hyunbo.Item.Notice_Listview_Item;
import btcore.co.kr.hyunbo.R;

/**
 * Created by leehaneul on 2018-01-11.
 */

public class HyunBo_Message_Listview_Adapter extends BaseAdapter {

    private ArrayList<Message_Listview_Item> MessageListViewItemlist = new ArrayList<Message_Listview_Item>();


    public HyunBo_Message_Listview_Adapter() {

    }

    @Override
    public int getCount() {
        return MessageListViewItemlist.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.message_listview_item, parent, false);
        }

        convertView.setFocusable(false);


        TextView textView_date = (TextView) convertView.findViewById(R.id.textView_date);
        TextView textView_title = (TextView) convertView.findViewById(R.id.textView_title);

        Message_Listview_Item messagelistviewitem = MessageListViewItemlist.get(position);


        textView_date.setText(messagelistviewitem.getDate());
        textView_title.setText(messagelistviewitem.getTitle());




        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return MessageListViewItemlist.get(position);
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(String date, String title) {
        Message_Listview_Item item = new Message_Listview_Item();


        item.setDate(date);
        item.setTitle(title);


        MessageListViewItemlist.add(item);
    }


}