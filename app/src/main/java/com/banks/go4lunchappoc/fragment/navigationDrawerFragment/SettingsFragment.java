package com.banks.go4lunchappoc.fragment.navigationDrawerFragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;

import com.banks.go4lunchappoc.R;


public class SettingsFragment extends DialogFragment {



    @SuppressLint("UseCompatLoadingForDrawables")
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Inflate the view from the XML file
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_settings, null);

        // Récupération de l'image pour la croix
        ImageView closeButton = view.findViewById(R.id.close_image_view);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        // Create the AlertDialog with the inflated view
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);


        return builder.create();

    }

}