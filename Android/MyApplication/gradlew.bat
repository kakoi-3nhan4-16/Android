package com.example.cln.modelUers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.cln.R;

import java.util.ArrayList;
import java.util.List;

public class ListViewAdapter extends ArrayAdapter<String> {
    int VG_id;
    List<String> list_item;
    ArrayList<String> desc;
    Context context;
    public ListViewAdapter(Context context, int vb, int id, List<String> list_item){
        super(context,vb,id,list_item);
        VG_id = vb;
        this.list_item= list_item;
        this.context = context;
    }
    static  class ViewHodler{
        public TextView textView;
        public Button button;
    }
    @Override
    public View getView(int position,View convertView, ViewGroup parent) {
        View rowView = convertView;
        if (rowView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(VG_id, parent, false);
            ViewHodler viewHodler = new ViewHodler();
            viewHodler.textView = rowView.findViewById(R.id.lv_text);
            viewHodler.button = rowView.findViewById(R.id.lv_button);
            rowView.setTag(viewHodler);
        }
        //set text
        ViewHodler viewHodler =(ViewHodler) rowView.getTag();
        viewHodler.textView.setText(list_item[position]);
        viewHodler.button.setText("Call");
        return rowView;
    }
}
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      