package org.swsd.school_yearbook.presenter.adapter;

/**
 * author     :  骆景钊
 * time       :  2017/11/04
 * description:  记录Recyclerview的每一个List的适配器
 * version:   :  1.0
 */
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.swsd.school_yearbook.R;
import org.swsd.school_yearbook.model.bean.SchoolyearbookBean;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder>{
    private List<SchoolyearbookBean> mSchoolyearbookBeanList;

    public NoteAdapter(List<SchoolyearbookBean> schoolyearbookBeanList){

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
        mSchoolyearbookBeanList = totallists;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(NoteAdapter.ViewHolder holder, int position) {
        SchoolyearbookBean schoolyearbookBean = mSchoolyearbookBeanList.get(position);
        holder.noteName.setText(schoolyearbookBean.getName());
        holder.noteEmail.setText(schoolyearbookBean.getEmail());
        holder.noteAddress.setText(schoolyearbookBean.getAddress());
        holder.notePhone.setText(schoolyearbookBean.getPhone());
        holder.noteWechat.setText(schoolyearbookBean.getWechat());
        holder.noteQq.setText(schoolyearbookBean.getQq());
        holder.noteSignature.setText(schoolyearbookBean.getSignature());
    }

    @Override
    public int getItemCount() {
        return mSchoolyearbookBeanList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView noteName;
        TextView noteEmail;
        TextView noteAddress;
        TextView notePhone;
        TextView noteWechat;
        TextView noteQq;
        TextView noteSignature;
        public ViewHolder(View view){
            super(view);
            noteName = (TextView) view.findViewById(R.id.tv_note_name);
            noteEmail=(TextView) view.findViewById(R.id.et_note_email);
            noteAddress=(TextView) view.findViewById(R.id.tv_note_address);
            notePhone=(TextView) view.findViewById(R.id.tv_note_phone);
            noteWechat=(TextView) view.findViewById(R.id.tv_note_wechat);
            noteQq=(TextView) view.findViewById(R.id.et_note_qq);
            noteSignature=(TextView) view.findViewById(R.id.et_note_signature);
        }
    }
}
