package org.swsd.school_yearbook.presenter;

import org.litepal.crud.DataSupport;
import org.swsd.school_yearbook.model.bean.SchoolyearbookBean;

import java.util.List;

/**
 * Created by Administrator on 2017/11/5.
 */

public class NoteDelete implements IPersenterDelete {

    List<SchoolyearbookBean> NoteList;

    public NoteDelete(List<SchoolyearbookBean> NoteList) {
        this.NoteList = NoteList;
        deleteNote();
    }


    //遍历List数组，实现删除
    @Override
    public void deleteNote(){
        for(int i = 0; i < NoteList.size(); i++){
            DataSupport.deleteAll(SchoolyearbookBean.class, "name = ?", NoteList.get(i).getName());
        }
    }
}
