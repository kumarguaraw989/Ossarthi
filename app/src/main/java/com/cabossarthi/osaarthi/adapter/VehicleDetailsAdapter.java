package com.cabossarthi.osaarthi.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cabossarthi.osaarthi.R;
import com.cabossarthi.osaarthi.model.VehicleCatogeryModal;
import com.cabossarthi.osaarthi.model.VehicleDetailsResponseModal;
import com.cabossarthi.osaarthi.ui.user.UserDashboardActivity;
import com.google.gson.Gson;
import com.skydoves.elasticviews.ElasticCardView;

import java.util.List;

public class VehicleDetailsAdapter extends RecyclerView.Adapter<VehicleDetailsAdapter.MyViewHolder> {
    List<VehicleDetailsResponseModal.VehiclePrices> catogeryListList;
    Context context;
    private VehicleDetailsAdapter.OnItemClickListeer listener;
    UserDashboardActivity userDashboardActivity;

    public interface OnItemClickListeer {
        void onItemClick(VehicleDetailsResponseModal.VehiclePrices vehiclePrices, int pos, LinearLayout cab, View view, String callFunction);
    }

    public VehicleDetailsAdapter(Context context, List<VehicleDetailsResponseModal.VehiclePrices> catogeryLists, UserDashboardActivity userDashboardActivity, VehicleDetailsAdapter.OnItemClickListeer onItemClickListeer) {
        this.catogeryListList = catogeryLists;
        this.context = context;
        this.listener=onItemClickListeer;
        this.userDashboardActivity = userDashboardActivity;
    }

    @NonNull
    @Override
    public VehicleDetailsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_vehicle_details, parent, false);
        return new VehicleDetailsAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final VehicleDetailsAdapter.MyViewHolder holder, final int position) {
        Log.e("TAG", "onBindViewHolder: "+new Gson().toJson(catogeryListList.get(holder.getAdapterPosition())));
        holder.vehicleName.setText(catogeryListList.get(holder.getAdapterPosition()).getClassName()+"/");
        holder.vehiclePrice.setText("₹ "+catogeryListList.get(holder.getAdapterPosition()).getTotalFare()+"/");

    }

    @Override
    public int getItemCount() {
        return catogeryListList.size();
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    // stores and recycles views as they are scrolled off screen
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView vehicleName, vehiclePrice;
        ImageView carImage;
        LinearLayout selectVc;

        public MyViewHolder(View itemView) {
            super(itemView);
            vehicleName = itemView.findViewById(R.id.tv_car_name);
            vehiclePrice = itemView.findViewById(R.id.tv_total_fare);
            carImage = itemView.findViewById(R.id.iv_cab_image);
            selectVc = itemView.findViewById(R.id.ll_selectVehicle);
            selectVc.setOnClickListener(v -> {
                listener.onItemClick(catogeryListList.get(getAdapterPosition()),getAdapterPosition(),selectVc,v, "Click");
            });
        }
    }
}