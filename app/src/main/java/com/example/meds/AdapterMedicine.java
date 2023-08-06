package com.example.meds;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterMedicine extends RecyclerView.Adapter<AdapterMedicine.ViewHolderMedicine> {
    private Context ctx;
    private ArrayList arrID, arrName, arrQuantity, arrPrice;

    public AdapterMedicine(Context ctx, ArrayList arrID, ArrayList arrName, ArrayList arrQuantity, ArrayList arrKlub) {
        this.ctx = ctx;
        this.arrID = arrID;
        this.arrName = arrName;
        this.arrQuantity = arrQuantity;
        this.arrPrice = arrKlub;
    }

    @NonNull
    @Override
    public ViewHolderMedicine onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.list_item_medicine, parent, false);
        return new ViewHolderMedicine(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderMedicine holder, int position) {
        holder.tvID.setText(arrID.get(position).toString());
        holder.tvName.setText("Name: "+arrName.get(position).toString());
        String ans = arrQuantity.get(position).toString();
        holder.tvQuantity.setText("Expiry Date: "+ans.substring(0,10));

        holder.tvPrice.setText("Price: "+arrPrice.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return arrName.size();
    }

    public class ViewHolderMedicine extends RecyclerView.ViewHolder {
        private TextView tvID, tvName, tvQuantity, tvPrice;

        public ViewHolderMedicine(@NonNull View itemView) {
            super(itemView);

            tvID = itemView.findViewById(R.id.tv_id);
            tvName = itemView.findViewById(R.id.tv_name);
            tvQuantity = itemView.findViewById(R.id.tv_quantity);
            tvPrice = itemView.findViewById(R.id.tv_price);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder pesan = new AlertDialog.Builder(ctx);
                    pesan.setTitle("Hello Admin");
                    pesan.setMessage("What do you want? ");
                    pesan.setCancelable(true);

                    pesan.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            MyDatabaseHelper myDB = new MyDatabaseHelper(ctx);
                            long status = myDB.deleteMedicine(tvID.getText().toString());

                            if (status == -1){
                                Toast.makeText(ctx, "Not Deleted", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(ctx, "Deleted Successfully!", Toast.LENGTH_SHORT).show();
                                dialogInterface.dismiss();
                                ((MainActivity) ctx).onResume();
                            }
                        }
                    });

                    pesan.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            Intent intent = new Intent(ctx, UpdateActivity.class);

                            intent.putExtra("vID", tvID.getText().toString());
                            intent.putExtra("vName", tvName.getText().toString());
                            intent.putExtra("vQuantity", tvQuantity.getText().toString());
                            intent.putExtra("vPrice", tvPrice.getText().toString());
                            ctx.startActivity(intent);
                        }
                    });

                    pesan.show();
                    return false;
                }
            });
        }
    }
}
