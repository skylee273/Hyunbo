package btcore.co.kr.hyunbo.Adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import btcore.co.kr.hyunbo.Item.Month_Date_Listview_Item;
import btcore.co.kr.hyunbo.Item.Month_Listview_Item;
import btcore.co.kr.hyunbo.R;

/**
 * Created by leehaneul on 2017-12-01.
 */

public class HyunBo_Month_Date_Listview_Adapter extends BaseAdapter {

    private ArrayList<Month_Date_Listview_Item> MonthDateListViewItemlist = new ArrayList<Month_Date_Listview_Item>();


    public HyunBo_Month_Date_Listview_Adapter() {

    }

    @Override
    public int getCount() {
        return MonthDateListViewItemlist.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.month_date_listview_item, parent, false);
        }

        convertView.setFocusable(false);


        TextView textView_date = (TextView) convertView.findViewById(R.id.textView_date);


        Month_Date_Listview_Item monthlistviewItem = MonthDateListViewItemlist.get(position);


        textView_date.setText(monthlistviewItem.getDate());




        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return MonthDateListViewItemlist.get(position);
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(String date) {
        Month_Date_Listview_Item item = new Month_Date_Listview_Item();


        item.setDate(date);


        MonthDateListViewItemlist.add(item);
    }


}