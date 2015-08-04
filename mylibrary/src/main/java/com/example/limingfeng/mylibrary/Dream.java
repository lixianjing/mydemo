package com.example.limingfeng.mylibrary;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by limingfeng on 2015/7/9.
 */
public class Dream {
    public  List<String> getDreamList(){
        List<String> list=new ArrayList<String>();
        list.add("health");
        list.add("happiness");
        list.add("glory");
        return list;
    }

    public void achieveDreams(List<String> list){
        for(String str:list){
            System.out.println(str);
        }
    }
    public static void main(String args[]){
        Dream dream=new Dream();
        dream.achieveDreams(dream.getDreamList());
    }
}
