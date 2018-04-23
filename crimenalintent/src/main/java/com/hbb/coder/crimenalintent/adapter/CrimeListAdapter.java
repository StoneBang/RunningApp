package com.hbb.coder.crimenalintent.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.hbb.coder.crimenalintent.R;
import com.hbb.coder.crimenalintent.activity.CrimeActivity;
import com.hbb.coder.crimenalintent.activity.CrimeListActivity;
import com.hbb.coder.crimenalintent.activity.CrimePageActivity;
import com.hbb.coder.crimenalintent.model.Crime;

import java.util.List;

import static com.hbb.coder.crimenalintent.activity.CrimeActivity.CRIME_UUID;

/**
 * Created by Administrator on 2018/3/19.
 * 陋习列表的适配器 used
 */

public class CrimeListAdapter extends RecyclerView.Adapter {

    private Fragment mFragment;

    private List<Crime> mCrimeList;

    private Context mContext;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    private OnItemClickListener mOnItemClickListener;
    public interface OnItemClickListener{

        void  itemClick(Crime crime);

    }

    public CrimeListAdapter(Context context,Fragment fragment,List<Crime> crimeList) {
        mCrimeList = crimeList;
        mContext = context;
        mFragment=fragment;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).
                inflate(R.layout.item_crime_list, parent, false);

        CrimeListHolder crimeListHolder = new CrimeListHolder(inflate);

        return crimeListHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if(holder instanceof  CrimeListHolder){
                Crime crime = mCrimeList.get(position);
                ((CrimeListHolder) holder).bindCrime(crime);
            }

    }

    @Override
    public int getItemCount() {
        return mCrimeList==null?0:mCrimeList.size();
    }

    public void setCrimeList(List<Crime> crimeList) {
        mCrimeList = crimeList;
    }

    public class CrimeListHolder  extends RecyclerView.ViewHolder  implements  View.OnClickListener,
            CompoundButton.OnCheckedChangeListener
    {

        private Crime mCrime;

        private final TextView itemCrimeTitle;
        private final TextView itemCrimeDesc;
        private final CheckBox itemCrimeCheckbox;

        public CrimeListHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemCrimeTitle = itemView.findViewById(R.id.item_crime_title);
            itemCrimeDesc = itemView.findViewById(R.id.item_crime_desc);
            itemCrimeCheckbox = itemView.findViewById(R.id.item_crime_checkbox);
        }

        public void  bindCrime(Crime  crime){
            this.mCrime=crime;
          itemCrimeTitle.setText(crime.getTitle());
          itemCrimeDesc.setText(crime.getDate().toString());
          itemCrimeCheckbox.setChecked(crime.isSolve());
            itemCrimeCheckbox.setOnCheckedChangeListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnItemClickListener.itemClick(mCrime);

        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            mCrime.setSolve(isChecked);
        }
    }
}
