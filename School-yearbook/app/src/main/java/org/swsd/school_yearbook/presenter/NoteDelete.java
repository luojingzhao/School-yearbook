package org.swsd.school_yearbook.presenter;

import java.util.List;

/**
 * Created by Administrator on 2017/11/5.
 */

public class NoteDelete implements IPersenterDelete {

    List<String> phoneList;

    public NoteDelete(List<String> phoneList) {
        this.phoneList = phoneList;
    }


    //遍历List数组，实现删除
    @Override
    public void deleteNote(){

    }
}
