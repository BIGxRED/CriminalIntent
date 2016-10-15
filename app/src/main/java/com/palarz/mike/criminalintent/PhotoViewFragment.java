package com.palarz.mike.criminalintent;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import java.io.File;

/**
 * Created by QNP684 on 10/14/2016.
 */
public class PhotoViewFragment extends DialogFragment {
    private static final String ARG_PHOTO = "photo";
    private static final String TAG = "PhotoViewFragment";

    private ImageView mPhotoView;
    private File mPhotoFile;

    /** CHAPTER 16 CHALLENGE **/

    //This was originally written by following what had been done for the date dialog; this is
    //still an acceptable approach, but an alternative was also found via the Big Nerd Ranch forum
    public static PhotoViewFragment newInstance(File file){
        Bundle args = new Bundle();
        args.putParcelable(ARG_PHOTO, Uri.fromFile(file));
        PhotoViewFragment fragment = new PhotoViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    //This method also gets the job done
    public void getPhotoFile(File file){
        mPhotoFile = file;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
//        Uri uri = (Uri) getArguments().getParcelable(ARG_PHOTO);
//        File photoFile = new File(uri.getPath());

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_photo,null);

        mPhotoView = (ImageView) v.findViewById(R.id.dialog_photo_photo_view);

        //Almost an exact duplicate of the updatePhotoView() method in the CrimeFragment class
        if(mPhotoFile == null || !mPhotoFile.exists())
            mPhotoView.setImageDrawable(null);
        else {
            Bitmap bitmap = PictureUtils.getScaledBitmap(mPhotoFile.getPath(), getActivity());
            mPhotoView.setImageBitmap(bitmap);
        }

        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.photo_view_title)
                .setPositiveButton(R.string.ok, null)
                .setView(v)
                .create();
    }
}
