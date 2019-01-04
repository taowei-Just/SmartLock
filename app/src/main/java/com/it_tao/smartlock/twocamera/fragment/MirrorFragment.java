package com.it_tao.smartlock.twocamera.fragment;


import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import com.it_tao.smartlock.MainActivity;
import com.it_tao.smartlock.R;
import com.it_tao.smartlock.twocamera.DrawerActivity;

import java.io.IOException;

import huanyang.gloable.gloable.utils.FragmentTools;

/**
 * Created by Administrator on 2017-11-06.
 */

public class MirrorFragment extends Fragment implements SurfaceHolder.Callback {


    private DrawerActivity mActivity;
    private View mRootFragmentView;
    private SurfaceView svMirror;
    private SurfaceHolder mSvMirrorHolder;
    private Camera mCamera;
    private View btExcite;
    @Override
    public void onDestroy() {
        
        super.onDestroy();
        
        releseCarmera();
        
    }

    private void releseCarmera() {
        
        if (mCamera != null) {
                
            try {
                
                mCamera.setPreviewCallback(null);
                mCamera.setPreviewDisplay(null);
                
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                
            mCamera.stopPreview();
                
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            mCamera.release();
            mCamera = null;
            
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mCamera!=null){
            
            mCamera.startPreview();
            
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
        
        if (mCamera!=null)
        mCamera.stopPreview();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_layout_mirror, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initview();
        initdata();


    }

    private void initview() {
        mActivity = (DrawerActivity) getActivity();
        mRootFragmentView = getView();
        svMirror = (SurfaceView) mRootFragmentView.findViewById(R.id.sv_mirror);
        btExcite = mRootFragmentView.findViewById(R.id.bt_excit);
    }

    private void initdata() {

        mSvMirrorHolder = svMirror.getHolder();
        
        mSvMirrorHolder.addCallback(this);
      
        mSvMirrorHolder.setKeepScreenOn(true);

        mSvMirrorHolder.setFormat(PixelFormat.TRANSLUCENT);
 
        mSvMirrorHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        
        btExcite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                excite();
            }
            
        });

        mActivity.hideMoreFragment();

    }

    private void excite() {
        FragmentTools.removeFragment(mActivity,this);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
        int numberOfCameras = Camera.getNumberOfCameras();
        if (numberOfCameras>1){
            mCamera = Camera.open(numberOfCameras - 1);
            
        }else {
            mCamera = Camera.open(0);
        }
         
        mCamera.setDisplayOrientation(90);

            mCamera.setPreviewDisplay(mSvMirrorHolder);
            Camera.Parameters parameters = mCamera.getParameters();
//            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            mCamera.setParameters(parameters);

            mCamera.startPreview();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        
        releseCarmera();

    }
}
