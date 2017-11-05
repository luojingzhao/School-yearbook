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
      List<SchoolyearbookBean>totallists=new ArrayList<>();
        for(int i = 0; i < 3; i++){
            SchoolyearbookBean schoolyearbookBean = new SchoolyearbookBean();
            schoolyearbookBean.setName("zhangsan");
            schoolyearbookBean.setAddress("zhangsan");
            schoolyearbookBean.setEmail("zhangsan");
            schoolyearbookBean.setPhone("zhangsan");
            schoolyearbookBean.setQq("zhangsan");
            schoolyearbookBean.setWechat("zhangsan");
            schoolyearbookBean.setSignature("zhangsan");
            totallists.add(schoolyearbookBean);
        }

       // totallists= DataSupport.findAll(SchoolyearbookBean.class);
        List<SchoolyearbookBean>totalList=new ArrayList<>();

        for(SchoolyearbookBean schoolyearbooks: totallists) {
            SchoolyearbookBean schoolyearbookBean = new SchoolyearbookBean();
            schoolyearbookBean.setName(schoolyearbooks.getName());
            schoolyearbookBean.setAddress(schoolyearbooks.getAddress());
            schoolyearbookBean.setEmail(schoolyearbooks.getEmail());
            schoolyearbookBean.setPhone(schoolyearbooks.getPhone());
            schoolyearbookBean.setQq(schoolyearbooks.getQq());
            schoolyearbookBean.setWechat(schoolyearbooks.getWechat());
            schoolyearbookBean.setSignature(schoolyearbooks.getSignature());
            totalList.add(schoolyearbookBean);
        }
        return totalList;
    }

    //选择函数
   public  List<SchoolyearbookBean> toSelect(String string){
        //获取数据库中的姓名
        List<SchoolyearbookBean>nameList= DataSupport.select("name").find(SchoolyearbookBean.class);
        List<SchoolyearbookBean>schoolyearbookBeanList=new ArrayList<>();

        for(SchoolyearbookBean schoolyearbook: nameList){
            if(schoolyearbook.getName().toString()==string){
                SchoolyearbookBean schoolyearbookBean = new SchoolyearbookBean();
                schoolyearbookBean.setName(string);
                schoolyearbookBean.setAddress(schoolyearbook.getAddress());
                schoolyearbookBean.setEmail(schoolyearbook.getEmail());
                schoolyearbookBean.setPhone(schoolyearbook.getPhone());
                schoolyearbookBean.setQq(schoolyearbook.getQq());
                schoolyearbookBean.setWechat(schoolyearbook.getWechat());
                schoolyearbookBean.setSignature(schoolyearbook.getSignature());
                schoolyearbookBeanList.add(schoolyearbookBean);
            }
        }
        return schoolyearbookBeanList;
    }
    @Override
    public void attachView(Object view, Context context) {

    }

    @Override
    public void detachView() {

    }
}


