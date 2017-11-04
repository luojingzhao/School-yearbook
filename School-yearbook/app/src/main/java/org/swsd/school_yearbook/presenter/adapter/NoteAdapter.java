package org.swsd.school_yearbook.presenter.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import org.swsd.school_yearbook.R;
import org.swsd.school_yearbook.model.bean.SchoolyearbookBean;

import java.util.ArrayList;
import java.util.List;

/**
 * author     :  骆景钊
 * time       :  2017/11/04
 * description:  记录Recyclerview的每一个List的适配器
 * version:   :  1.0
 */

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder>
        implements  View.OnLongClickListener{

    private List<SchoolyearbookBean> mSchoolyearbookBeanList;

    @Override
    public boolean onLongClick(View view) {
        return false;
    }

    public interface OnItemOnClickListener{
        void onItemLongOnClick(View view ,int pos);
    }

    private OnItemOnClickListener mOnItemOnClickListener;
    public void setOnItemClickListener(OnItemOnClickListener listener){
        this.mOnItemOnClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final NoteAdapter.ViewHolder holder, final int position) {
        SchoolyearbookBean schoolyearbookBean = mSchoolyearbookBeanList.get(position);
        holder.noteName.setText(schoolyearbookBean.getName());
        if(mOnItemOnClickListener!=null){
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mOnItemOnClickListener.onItemLongOnClick(holder.itemView,position);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mSchoolyearbookBeanList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView noteName;
        public ViewHolder(View view){
            super(view);
            noteName = (TextView) view.findViewById(R.id.tv_note_name);
        }
    }

    public NoteAdapter(){
        List<SchoolyearbookBean> schoolyearbookBeanList = new ArrayList<>();
        mSchoolyearbookBeanList = schoolyearbookBeanList;
        for(int i = 0; i < 3; i++){
            SchoolyearbookBean schoolyearbookBean = new SchoolyearbookBean();
            schoolyearbookBean.setName("zhangsan");
            mSchoolyearbookBeanList.add(schoolyearbookBean);
        }
    }

    public List<SchoolyearbookBean> getmSchoolyearbookBeanList(){
        return mSchoolyearbookBeanList;
    }

}
