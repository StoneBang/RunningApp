package com.hbb.coder.crimenalintent.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.hbb.coder.crimenalintent.R;
import com.hbb.coder.crimenalintent.model.Crime;
import com.hbb.coder.crimenalintent.model.CrimeLabel;
import com.hbb.coder.crimenalintent.util.StatusUtils;
import com.hbb.coder.crimenalintent.util.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;
import static com.hbb.coder.crimenalintent.activity.CrimeActivity.CRIME_UUID;
import static com.hbb.coder.crimenalintent.fragment.DatePickFragment.DIALOG_DATE;


/**
 * Created by hongbang on 2018/3/18.
 */

public class CrimeFragmet extends Fragment {

    private static final int REQUSETCODE = 0;
    private static  final int REQUESTCONTACT=1;
    private TextView mTitle1;
    private EditText mEditText;
    private Button mDateButton;
    private CheckBox mCheckbox;
    private TextView mTitle2;
    private Crime mCrime;
    private static  final String DIALOG_TAG="dialog_tag";
    private TextView mChoosePeople;
    private TextView mSendMessage;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusUtils.setStatusBarLightMode(getActivity(), Color.WHITE);
        UUID crimeId = (UUID) getArguments().getSerializable(CRIME_UUID);
        mCrime = CrimeLabel.getCrimeLabel(getActivity()).getCrime(crimeId);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_crime, container, false);
        mTitle1 = inflate.findViewById(R.id.crime_title);
        mTitle2 = inflate.findViewById(R.id.crime_title2);
        mEditText = inflate.findViewById(R.id.crime_input);
        mDateButton = inflate.findViewById(R.id.crime_button);
        mCheckbox = inflate.findViewById(R.id.crime_checkbox);
        mChoosePeople = inflate.findViewById(R.id.choosePeople);
        mSendMessage = inflate.findViewById(R.id.send_message);
        mEditText.setText(mCrime.getTitle()==null?"":mCrime.getTitle());
        mCheckbox.setChecked(mCrime.isSolve());
        updateTime();
//        mDateButton.setEnabled(false);
        final Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);

        PackageManager packageManager = getActivity().getPackageManager();

        if(packageManager.resolveActivity(intent,PackageManager.MATCH_DEFAULT_ONLY)==null){




        }

        mChoosePeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivityForResult(intent,REQUESTCONTACT);

            }
        });
        mSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT,getCrimeReport());

                intent.putExtra(Intent.EXTRA_SUBJECT,getString(R.string.crime_is_suject));

                intent=Intent.createChooser(intent,"请选择用什么方式发送");

                startActivity(intent);
            }
        });

        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();

                DatePickFragment datePickFragment =  DatePickFragment.newInstance(mCrime.getDate());

                datePickFragment.setTargetFragment(CrimeFragmet.this,REQUSETCODE);

                datePickFragment.show(fragmentManager,DIALOG_TAG);
            }
        });


        mCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCrime.setSolve(isChecked);
            }
        });

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

                limitInNumber(s,"100");
            }
        });

        return inflate;
    }

    private void updateTime() {
        mDateButton.setText(mCrime.getDate().toString());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public static CrimeFragmet newInstantce(UUID uuid){

        Bundle  arg=new Bundle();

        arg.putSerializable(CRIME_UUID,uuid);

        CrimeFragmet crimeFragmet = new CrimeFragmet();

        crimeFragmet.setArguments(arg);

        return crimeFragmet;

    }


    @SuppressLint("StringFormatInvalid")
    private String  getCrimeReport(){

        String  solvedString=null;

        if(mCrime.isSolve()){

            solvedString=getString(R.string.crime_is_solved);

        }else{
            solvedString=getString(R.string.crime_is_unsolved);
        }

        String dateFormat="EEE MMM dd";


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        String dateString= simpleDateFormat.format(mCrime.getDate());

        String  suspect =mCrime.getSuspect();

        if(suspect==null){

            suspect=getString(R.string.crime_is_suspect);

        }else{
            suspect=getString(R.string.crime_is_suject,suspect);
        }

        String  report=getString(R.string.crime_is_report,mCrime.getTitle(),dateString,solvedString,suspect);

        return report;
    }


    /**
     * 限制输入一个亿,只能限制大于1的数字
     * @param s
     */
    private   void limitInNumber(Editable s,String number) {
        double value = StringUtils.toDouble(s.toString());

        if (value >= StringUtils.toDouble(number)) {
            if (s.toString().startsWith(number)) {
                if (new BigDecimal(s.toString()).toString().contains(".")) {
                    if(s.toString().indexOf(".")==s.length()-1){
                        s.delete(number.length(), s.length());
                    }else{
                        s.delete(number.length()-1, s.toString().indexOf("."));
                    }
                }else{
                    s.delete(number.length(), s.length());
                }
            }else{
                if (new BigDecimal(s.toString()).toString().contains(".")) {
                    s.delete(number.length()-1, s.toString().indexOf("."));
                }else{
                    s.delete(number.length()-1, s.length());
                }
            }
        }else{
            if(s.toString().startsWith("0")&&s.toString().indexOf(".")==2){
                s.delete(1,2);
            }
        }
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        CrimeLabel.getCrimeLabel(getActivity()).updateCrime(mCrime);
    }


    @Override
    public void onDetach() {
        super.onDetach();
        Log.e("CrimeFragment","onDetach=============="+this);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e("CrimeFragment","onDestroyView=============="+this);

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("CrimeFragment","onDestroy=============="+this);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("CrimeFragmet","CrimeFragmet+onActivityResult"+this);


        if(resultCode!=RESULT_OK){
            return;
        }

        if(requestCode==REQUSETCODE&&data!=null){

            Date date = (Date) data.getSerializableExtra(DIALOG_DATE);

            mCrime.setDate(date);

            updateTime();

            return;
        }

        if(requestCode==REQUESTCONTACT&&data!=null){

            Uri contactUri = data.getData();

            String[] queryField = {ContactsContract.Contacts.DISPLAY_NAME};


            Cursor query = getActivity().getContentResolver().query(contactUri, queryField,
                    null, null, null);

            if(query.getCount()==0){
                return;
            }

            try{
                query.moveToFirst();

                String suspect= query.getString(0);

                mCrime.setSuspect(suspect);

                mChoosePeople.setText(suspect);
            }catch (Exception exception){

            }finally {
                query.close();
            }

        }
    }
}
