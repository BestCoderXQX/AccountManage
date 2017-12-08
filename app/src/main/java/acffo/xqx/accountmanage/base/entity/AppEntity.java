package acffo.xqx.accountmanage.base.entity;


import android.graphics.drawable.Drawable;

/**
* @author xqx
* @email djlxqx@163.com
* blog:http://www.cnblogs.com/xqxacm/
* createAt 2017/11/23
* description: 应用信息
*/


public class AppEntity {
    private String name;
    private Drawable img ;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Drawable getImg() {
        return img;
    }

    public void setImg(Drawable img) {
        this.img = img;
    }
}
