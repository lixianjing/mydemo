package com.xian.myapp.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.xian.myapp.R;
import com.xian.myapp.adapter.CustomELVAdapter;


public class ExpandableListviewActivity extends Activity {
    private ExpandableListView elv;


    /**
     * THIS DATA CAN BE FETCHED FROM DATABASES OR FROM WEB USING WEB API'S AND LOAD TO ADAPTER
     **/


    private static final String[] groupname = {"Bangalore", "Mysore", "Kodagu"};

    private static final String[][] data = {{"Vidhanasouda", "Cubbon park", "Lalbagh"},
            {"Palace", "Chamundi Hills", "Zoo"},
            {"Abbey Falls", "Talakaveri"}};

    private static final String[][] listinfo = {{"Dr Ambedkar rd,, Sampangi Ramnagar, Bangalore, Karnataka", "Kasturba Road, Bangalore, Karnataka", "Lal Bagh Road, Lalbagh, Mavalli, Bangalore, Karnataka"},
            {"Sayyaji Rao Rd, Mysore, Karnataka", "Mysore", "Ittige gudu, Mysore, Karnataka"},
            {"Kodagu,Karnataka", "Kodagu,Karnataka"}};


    private static final int[] ImgBckgrnd = {R.drawable.bangalore, R.drawable.mysore, R.drawable.coorg};


    private int position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable_listview);
        elv = (ExpandableListView) findViewById(R.id.lvExp1);
        elv.setFocusable(false);

        /**
         * THIS CAN BE USED IN ACTIVITY OR FRAGMENTS
         * **/

        elv.setAdapter(new CustomELVAdapter(this, ExpandableListviewActivity.this, groupname, ImgBckgrnd, listinfo, data));

        elv.setOnGroupClickListener(new OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                /**
                 * TODO:return true to enable group click
                 */

                // DO SOMETHING
                if (groupPosition == position) {
                    return false;
                }

                if (position != -1) {
                    parent.collapseGroup(position);
                }
                position = groupPosition;
                parent.expandGroup(groupPosition, true);
                return true;
            }
        });

    }

    public static String GroupName;
    public static String ChildName;
    public static String list_info;

    public static class ViewHolder {
        public ToggleButton ExpCol;
        public RelativeLayout toggleLayout;
        public RelativeLayout ChildLayout;
        public ImageView LayoutBackground;
        public TextView GroupHead;
        public TextView ListHead;
        public TextView ListDetail;
        public Button directions;
        public Button details;

        public ViewHolder(View v) {
            this.LayoutBackground = (ImageView) v.findViewById(R.id.listbackground);
            this.ChildLayout = (RelativeLayout) v.findViewById(R.id.list_Item_layout);
            this.directions = (Button) v.findViewById(R.id.directions);
            this.details = (Button) v.findViewById(R.id.details);
            this.GroupHead = (TextView) v.findViewById(R.id.lblListHeader);
            this.ListHead = (TextView) v.findViewById(R.id.lblListItem);
            this.ListDetail = (TextView) v.findViewById(R.id.listItemInfo);
            this.toggleLayout = (RelativeLayout) v.findViewById(R.id.toggle_layout);
            this.ExpCol = (ToggleButton) v.findViewById(R.id.expand_colapse);
        }


    }

}
