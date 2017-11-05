package org.swsd.school_yearbook.presenter;

import java.util.List;

/**
 * Created by YOOY on 2017/11/4.
 */

public class EmailPresenter implements IPresenterSendEmail {
    private List<String> mEmailList;

    public EmailPresenter(List<String> emailList){
        mEmailList = emailList;
    }
    @Override
    public void sendEmail() {
        
    }
}
