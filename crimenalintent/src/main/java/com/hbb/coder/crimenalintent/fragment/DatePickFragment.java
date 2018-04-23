package com.hbb.coder.crimenalintent.fragment;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import com.hbb.coder.crimenalintent.R;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.zip.Inflater;

/**
 * Created by Administrator on 2018/3/21.
 */

public class DatePickFragment extends DialogFragment {


    public  static  final String DIALOG_DATE="dialog_date";
    private DatePicker mDatePick;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {



        View inflate = LayoutInflater.from(getActivity()).
                inflate(R.layout.alert_date_pick,null);

        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).
                setView(inflate).
                setTitle("选择时间").
                setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int year1 = mDatePick.getYear();
                        int month = mDatePick.getMonth();
                        int dayOfMonth1 = mDatePick.getDayOfMonth();
                        Date date=new GregorianCalendar(year1,month,dayOfMonth1).getTime();

                        setResult(Activity.RESULT_OK,date);
                    }
                }).
                create();


        Bundle arguments = getArguments();

        Date dialogDate = (Date) arguments.getSerializable(DIALOG_DATE);

        mDatePick = inflate.findViewById(R.id.alert_date_pick);

        Calendar calendar= Calendar.getInstance();
        calendar.setTime(dialogDate);

        mDatePick.init(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), null);

        return alertDialog;

    }


    public  static DatePickFragment newInstance(Date date){

        Bundle bundle=new Bundle();

        bundle.putSerializable(DIALOG_DATE,date);

        DatePickFragment datePickFragment = new DatePickFragment();

        datePickFragment.setArguments(bundle);

        return datePickFragment ;
    }


    private void  setResult(int  resultCode,Date  date){


        if(getTargetFragment()==null){

            return;
        }

        Intent intent = new Intent();
        intent.putExtra(DIALOG_DATE,date);
        getTargetFragment().onActivityResult(getTargetRequestCode(),resultCode,intent);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("DatePickFragment","DatePickFragment+onActivityResult"+this);
    }
}
