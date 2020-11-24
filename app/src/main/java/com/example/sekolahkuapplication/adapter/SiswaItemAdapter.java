package com.example.sekolahkuapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.sekolahkuapplication.R;
import com.example.sekolahkuapplication.model.Siswa;

import java.util.List;

public class SiswaItemAdapter extends ArrayAdapter<Siswa> {
    public SiswaItemAdapter(Context context, List<Siswa> objects) {
        super(context, R.layout.item_siswa, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;

        if (listItemView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            listItemView = inflater.inflate(R.layout.item_siswa, parent, false);
        }

        TextView etNamaDepan = listItemView.findViewById(R.id.etNamaDepan);
        TextView genderRb = listItemView.findViewById(R.id.genderRb);
        TextView educationList = listItemView.findViewById(R.id.listEducation);
        TextView etNohp = listItemView.findViewById(R.id.etNoHandphone);
        TextView etTgllahir = listItemView.findViewById(R.id.etTgllahir);

        Siswa siswa = getItem(position);
        etNamaDepan.setText(siswa.getNamaDepan() + " " + siswa.getNamaBelakang());
        genderRb.setText(siswa.getGender());
        etNohp.setText(siswa.getPhoneNumber());
        educationList.setText(siswa.getEducation());
        etTgllahir.setText(siswa.getTglLahir());

        return listItemView;
    }

}
