package com.android.bignerdranch.memo.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import android.widget.TextView;
import android.widget.Toast;


import com.android.bignerdranch.memo.DataStructure.Memo;
import com.android.bignerdranch.memo.Database.MemoDbManager;
import com.android.bignerdranch.memo.Activity.MemoListActivity;
import com.android.bignerdranch.memo.MemoSendManager;
import com.android.bignerdranch.memo.Activity.PaintActivity;
import com.android.bignerdranch.memo.PictureUtils;
import com.android.bignerdranch.memo.R;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import java.io.File;

import java.util.Date;
import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by KeanuZhao on 2017/4/25.
 */

public class MemoFragment extends Fragment {

    private Memo mNewMemo;

    @Bind(R.id.memo_title)
    EditText mTitle;
    
    @Bind(R.id.memo_content)
    EditText mContent;

    @Bind(R.id.memo_date)
    TextView mDateText;
    
    @Bind(R.id.memo_camera)
    TextView mCameraText;
    
    @Bind(R.id.memo_gallery)
    TextView mGalleryText;
    
    @Bind(R.id.memo_draw)
    TextView mPaintText;
    
    @Bind(R.id.memo_locate)
    TextView mLocateText;

    private MemoDbManager mdbManager;

    private File mPhotoFile = null;
    private File mPaintFile = null;

    private static final String DIALOG_DATE = "DialogDate"; //日期对话框
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_CAMERA= 1;
    private static final int REQUEST_PHOTO= 2;

    private LocationClient locationClient = null;
    private SpannableString mspanC = null;
    private SpannableString mspanP = null;

    private static final String ARG_MEMO_ID = "memo_id";

    public static MemoFragment newInstance(UUID mId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_MEMO_ID, mId);
        MemoFragment fragment = new MemoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        UUID meId = (UUID) getArguments().getSerializable(ARG_MEMO_ID);
        mdbManager = new MemoDbManager(getActivity());
        mNewMemo = MemoDbManager.get(getActivity()).getMemo(meId);
        //Toast.makeText(getActivity(),meId.toString(), Toast.LENGTH_LONG).show();//
        mNewMemo.setMemousId(meId.toString());
        mPhotoFile = MemoDbManager.get(getActivity()).getPhotoFile(mNewMemo);

        if(mNewMemo.getPhoto()!=null){
            mPhotoFile=new File(mNewMemo.getPhoto());
            Bitmap bitmapC = PictureUtils.getScaledBitmap(
                    mPhotoFile.getPath(), getActivity());
            mspanC=PictureUtils.bitmapConvert(bitmapC,1);

        }

        if(mNewMemo.getPaint()!=null){
            mPaintFile = new File(mNewMemo.getPaint());
            Bitmap bitmapP = PictureUtils.getScaledBitmap(
                    mPaintFile.getPath(), getActivity());
            mspanP=PictureUtils.bitmapConvert(bitmapP,2);

        }

        locationClient = new LocationClient(getActivity());
        locationClient.registerLocationListener(new BDLocationListener() {

            @Override
            public void onReceiveLocation(BDLocation location) {
                // TODO Auto-generated method stub
                if (location == null) {
                    return;
                }
                StringBuffer locateRecord = new StringBuffer(256);
                StringBuffer locateDB = new StringBuffer(256);
                locateRecord.append("Time : ");
                locateRecord.append(location.getTime());
                locateRecord.append("\nError code : ");
                locateRecord.append(location.getLocType());
                locateRecord.append("\nLatitude : ");
                locateRecord.append(location.getLatitude());
                locateRecord.append("\nLongitude : ");
                locateRecord.append(location.getLongitude());

                locateDB.append("\nLatitude : ");
                locateDB.append(location.getLatitude());
                locateDB.append("\nLongitude : ");
                locateDB.append(location.getLongitude());

                if (location.getLocType() == BDLocation.TypeGpsLocation){
                    locateRecord.append("\nAddress : ");
                    locateRecord.append(location.getAddrStr());
                    locateRecord.append("\nSatellite : ");
                    locateRecord.append(location.getSatelliteNumber());
                    locateRecord.append("\nType: GPS location  ");
                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation){
                    locateRecord.append("\nAddress : ");
                    locateRecord.append(location.getAddrStr());
                    locateRecord.append("\nType: Network location  ");
                }
                mNewMemo.setMemoLocation(locateDB.toString());
                Toast.makeText(getActivity(),locateRecord, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onConnectHotSpotMessage(String s, int i) {

            }

            public void onReceivePoi(BDLocation location) {
            }

        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_memo, container, false);
        ButterKnife.bind(this,v);

        mTitle.setText(mNewMemo.getMemoTitle());
        mTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mNewMemo.setMemoTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mContent.setText(mNewMemo.getMemoContent());
        mContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    mNewMemo.setMemoContent(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mDateText.setText(mNewMemo.getMemoDate().toString());

        return v;
    }

    @OnClick({R.id.memo_date,R.id.memo_gallery,R.id.memo_draw,R.id.memo_locate,R.id.memo_camera})
    public void onViewClicked(View v){
        switch(v.getId()){
            case R.id.memo_date:
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment
                        .newInstance(mNewMemo.getMemoDate());
                dialog.setTargetFragment(MemoFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
                break;

            case R.id.memo_gallery:
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, REQUEST_PHOTO);
                break;

            case R.id.memo_draw:
                String s = String.valueOf(mNewMemo.getMemoId());
                Intent intentdraw = PaintActivity.newIntent(getActivity(),s);
                startActivity(intentdraw);
                break;

            case R.id.memo_locate:
                InitLocation();
                if (locationClient == null) {
                    return;
                }
                if (locationClient.isStarted()) {
                    mLocateText.setText("Start");
                    locationClient.stop();
                }else {
                    mLocateText.setText("Stop");
                    locationClient.start();
                    locationClient.requestLocation();
                }
                break;

            case R.id.memo_camera:
                final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                Uri uri1 = Uri.fromFile(mPhotoFile);
                captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri1);
                startActivityForResult(captureImage, REQUEST_CAMERA);
                break;
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        MemoDbManager.get(getActivity())
                .updateMemoDB(mNewMemo);

        if (locationClient != null && locationClient.isStarted()) {
            locationClient.stop();
            locationClient = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data
                    .getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mNewMemo.setMemoDate(date);
            mDateText.setText(mNewMemo.getMemoDate().toString());

        } else if (requestCode == REQUEST_CAMERA) {
            Bitmap bitmapC = PictureUtils.getScaledBitmap(
                    mPhotoFile.getPath(), getActivity());
            SpannableString spannablespanC = PictureUtils.bitmapConvert(bitmapC,1);
            mContent.append(spannablespanC);
            mNewMemo.setPhoto(mPhotoFile.toString());
            Toast.makeText(getActivity(),mPhotoFile.toString(), Toast.LENGTH_LONG).show();
        }else if(requestCode == REQUEST_PHOTO){

            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContext().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            Bitmap bitmapP = BitmapFactory.decodeFile(picturePath);
            SpannableString spannablespanP = PictureUtils.bitmapConvert(bitmapP,2);
            mContent.append(spannablespanP);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) { //右上角选项栏
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.memo_create_list, menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_fin_memo:
                mNewMemo.setMemoTitle(mTitle.getText().toString());
                mNewMemo.setMemoContent(mContent.getText().toString());
                mdbManager.addMemoDB(mNewMemo) ;
                mdbManager.close();

                Intent intent = new Intent(getActivity(), MemoListActivity.class);
                startActivity(intent);
                return true;

            case R.id.menu_item_del_memo:
                mdbManager.deleteMemoDB(mNewMemo);
                MemoDbManager.get(getActivity()).deleteMemo(mNewMemo);
                Intent intentdel = new Intent(getActivity(), MemoListActivity.class);
                startActivity(intentdel);
                return true;


            case R.id.menu_item_share_picture_memo:
                mNewMemo.setMemoTitle(mTitle.getText().toString());
                mNewMemo.setMemoContent(mContent.getText().toString());
                Intent sendIntentpic = MemoSendManager.generateIntentPic(mNewMemo);
                startActivity(Intent.createChooser(sendIntentpic, "share"));
                return true;

            case R.id.menu_item_share_memo:
                mNewMemo.setMemoTitle(mTitle.getText().toString());
                mNewMemo.setMemoContent(mContent.getText().toString());
                Intent sendIntent = MemoSendManager.generateIntent(mNewMemo);
                startActivity(Intent.createChooser(sendIntent, "share"));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void InitLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);
        option.setPriority(LocationClientOption.NetWorkFirst);  //设置定位优先级
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//设置高精度定位定位模式
        option.setCoorType("bd09ll");//设置百度经纬度坐标系格式
        option.setScanSpan(8000);//设置发起定位请求的间隔时间为xxms
        option.setIsNeedAddress(true);//反编译获得具体位置，只有网络定位才可以
        locationClient.setLocOption(option);
        option.setIsNeedAddress(true);
    }

}






