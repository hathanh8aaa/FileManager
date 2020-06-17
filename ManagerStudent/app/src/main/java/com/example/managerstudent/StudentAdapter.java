package com.example.managerstudent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    int layout;
    ArrayList<Student> arrayList;

    public StudentAdapter(Context context, int layout, ArrayList<Student> arrayList) {
        this.context = context;
        this.layout = layout;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_row, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        StudentViewHolder viewHolder= (StudentViewHolder) holder;

        Student st= arrayList.get(position);
        viewHolder.txtMssv.setText(st.getMssv());
        viewHolder.txtName.setText(st.getName());
        viewHolder.txtBirth.setText(st.getBirthday());
        viewHolder.txtEmail.setText(st.getEmail());
        viewHolder.txtAddr.setText(st.getAddress());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder {
        TextView txtMssv;
        TextView txtName;
        TextView txtBirth;
        TextView txtEmail;
        TextView txtAddr;
        ImageView avatar;
        public StudentViewHolder(@NonNull final View itemView) {
            super(itemView);
            txtMssv= itemView.findViewById(R.id.mssv);
            txtName= itemView.findViewById(R.id.name);
            txtBirth= itemView.findViewById(R.id.birthday);
            txtEmail= itemView.findViewById(R.id.email);
            txtAddr= itemView.findViewById(R.id.address);
            avatar =itemView.findViewById(R.id.image);
            avatar.setImageResource(R.drawable.ic_person);
        }
    }
}
