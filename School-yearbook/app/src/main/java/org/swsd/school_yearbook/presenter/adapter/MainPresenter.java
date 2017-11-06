/**
 * author  ： 胡俊钦
 * time    ： 2017/11/04
 * desc    ： Presenter of MainActivity
 * version ： 1.0
 */

package org.swsd.school_yearbook.presenter.adapter;

import android.content.Context;

import org.litepal.crud.DataSupport;
import org.swsd.school_yearbook.model.bean.SchoolyearbookBean;
import org.swsd.school_yearbook.presenter.IPresenter;

import java.util.ArrayList;
import java.util.List;


public class MainPresenter implements IPresenter{
    public  List<SchoolyearbookBean> getAllList(){
        List<SchoolyearbookBean>totalList=new ArrayList<>();
        totalList= DataSupport.findAll(SchoolyearbookBean.class);
        return totalList;
    }

    //选择函数
   public  List<SchoolyearbookBean> toSelect(String string){
        //获取数据库中的姓名
        List<SchoolyearbookBean>nameList= DataSupport.select("name").find(SchoolyearbookBean.class);
        List<SchoolyearbookBean>allList=new ArrayList<>();
        for(SchoolyearbookBean syb: nameList){
            int lenth=string.length();
            String nameString=syb.getName().toString();
            if(nameString.length()<lenth){
                lenth=nameString.length();
            }
            char[] nameArry=nameString.toCharArray();
            String str="";
            for(int i=0;i<lenth;i++){
                str+=nameArry[i];
            }
            if(str.equals(string.toString())){
                allList=DataSupport.select("id","name","address","phone",
                        "wechat","email","qq","signature")
                        .where("name=?",nameString).find(SchoolyearbookBean.class);
            }
        }
        return allList;
    }
    @Override
    public void attachView(Object view, Context context) {

    }

    @Override
    public void detachView() {

    }
}


