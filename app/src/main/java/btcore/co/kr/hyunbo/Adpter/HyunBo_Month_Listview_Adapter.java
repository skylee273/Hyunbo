package btcore.co.kr.hyunbo.Adpter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

import btcore.co.kr.hyunbo.Item.Month_Listview_Item;
import btcore.co.kr.hyunbo.R;

/**
 * Created by leehaneul on 2017-09-27.
 */

public class HyunBo_Month_Listview_Adapter extends BaseAdapter {

    private ArrayList<Month_Listview_Item> MonthListViewItemlist = new ArrayList<Month_Listview_Item>();


    public HyunBo_Month_Listview_Adapter() {

    }

    @Override
    public int getCount() {
        return MonthListViewItemlist.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.month_listview_item, parent, false);
        }

        convertView.setFocusable(false);


        TextView textView_work = (TextView) convertView.findViewById(R.id.textView_work);
        TextView textView_out = (TextView) convertView.findViewById(R.id.textView_out);
        TextView textView_default = (TextView) convertView.findViewById(R.id.textView_default);
        TextView textView_over = (TextView) convertView.findViewById(R.id.textView_over);
        TextView textView_night = (TextView) convertView.findViewById(R.id.textView_night);
        TextView textView_special = (TextView) convertView.findViewById(R.id.textView_special);
        TextView textView_specialover = (TextView) convertView.findViewById(R.id.textView_specialover);
        TextView textView_state = (TextView) convertView.findViewById(R.id.textView_state);



        Month_Listview_Item monthlistviewItem = MonthListViewItemlist.get(position);


        textView_work.setText(monthlistviewItem.getWork_time());
        textView_out.setText(monthlistviewItem.getOut_time());
        textView_default.setText(monthlistviewItem.getDefault_time());
        textView_over.setText(monthlistviewItem.getOver_time());
        textView_night.setText(monthlistviewItem.getNight());
        textView_special.setText(monthlistviewItem.getSpecial());
        textView_specialover.setText(monthlistviewItem.getSpecial_over());
        textView_state.setText(monthlistviewItem.getState());



        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return MonthListViewItemlist.get(position);
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(String work, String out, String default_time, String over,String night, String special, String special_over, String state) {
        Month_Listview_Item item = new Month_Listview_Item();


        item.setWork_time(work);
        item.setOut_time(out);
        item.setDefault_time(default_time);
        item.setOver_time(over);
        item.setNight(night);
        item.setSpecial(special);
        item.setSpecial_over(special_over);
        item.setState(state);

        MonthListViewItemlist.add(item);
    }


}
