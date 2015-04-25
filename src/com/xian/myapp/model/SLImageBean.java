package com.xian.myapp.model;

import android.os.Parcel;
import android.os.Parcelable;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * @author 吴书永 14-2-28 :下午2:23
 */
public class SLImageBean implements Parcelable, Serializable, IParseFormJSON {

    private static final long serialVersionUID = 5979202924337286603L;
    public int id;
    public String url;
    public String thumb;
    public String name;
    public String desc;
    public String path;
    //=============================
    /**
     * 创建时间
     */
    public long timestamp;
    /**
     * 图片的状态,只有未上传或上传失败才可以编辑.
     */
    public int status;
    /**
     * 旋转度数,0,90,270,360
     */
    public int ori;

    public static final Creator<SLImageBean> CREATOR = new Creator<SLImageBean>() {
        @Override
        public SLImageBean createFromParcel(Parcel source) {
            return new SLImageBean(source);
        }

        @Override
        public SLImageBean[] newArray(int size) {
            return new SLImageBean[size];
        }
    };

    public SLImageBean() {
    }

    public SLImageBean(Parcel in) {
        readFromParcel(in);
    }

    protected void readFromParcel(Parcel in) {
        id = in.readInt();
        url = in.readString();
        thumb = in.readString();
        name = in.readString();
        desc = in.readString();
        path = in.readString();
        timestamp = in.readLong();
        status = in.readInt();
        ori = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(url);
        dest.writeString(thumb);
        dest.writeString(name);
        dest.writeString(desc);
        dest.writeString(path);
        dest.writeLong(timestamp);
        dest.writeInt(status);
        dest.writeInt(ori);
    }

    @Override
    public String toString() {
        return "SLImageBean{" +
                "id=" + id +
                ", timestamp='" + timestamp + '\'' +
                ", status='" + status + '\'' +
                ", ori='" + ori + '\'' +
                ", url='" + url + '\'' +
                ", thumb='" + thumb + '\'' +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", path='" + path + '\'' +
                '}';
    }

    @Override
    public boolean parseFromJSON(String result) {
        try {
            JSONObject jo = new JSONObject(result);
            url = jo.optString("imageUrl");
            thumb = jo.optString("thumbImageUrl");
            desc = jo.optString("description");
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
