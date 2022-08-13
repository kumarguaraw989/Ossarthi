package com.cabossarthi.osaarthi.adapter;

import android.content.Context;
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
import com.cabossarthi.osaarthi.model.CabListModel;
import com.cabossarthi.osaarthi.model.VehicleCatogeryModal;
import com.cabossarthi.osaarthi.model.VehicleCatogeryResponseModal;
import com.cabossarthi.osaarthi.ui.user.UserDashboardActivity;
import com.skydoves.elasticviews.ElasticCardView;

import java.util.List;

public class CabListAdapter extends RecyclerView.Adapter<CabListAdapter.MyViewHolder> {
    List<VehicleCatogeryModal.VehicleCat> catogeryListList;
    Context context;
    private CabListAdapter.OnItemClickListeer listener;
    UserDashboardActivity userDashboardActivity;

    public interface OnItemClickListeer {
        void onItemClick(VehicleCatogeryModal.VehicleCat vehicleCatogeryModal, int pos,ElasticCardView cab, View view, String callFunction);
    }

    public CabListAdapter(Context context, List<VehicleCatogeryModal.VehicleCat> catogeryLists, UserDashboardActivity userDashboardActivity, CabListAdapter.OnItemClickListeer onItemClickListeer) {
        this.catogeryListList = catogeryLists;
        this.context = context;
        this.listener=onItemClickListeer;
        this.userDashboardActivity = userDashboardActivity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_cab_list, parent, false);
        return new CabListAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CabListAdapter.MyViewHolder holder, final int position) {
        holder.carCatName.setText(catogeryListList.get(holder.getAdapterPosition()).getVehicleCategoryName());
        holder.carClassName.setText(catogeryListList.get(holder.getAdapterPosition()).getVahicleClassName());
//        Glide.with(context)
//                .load(catogeryListList.get(holder.getAdapterPosition()).getCategoryImage())
//                .into(holder.carImage);
        if (catogeryListList.get(holder.getAdapterPosition()).getVehicleCategoryName().equals("Two Wheeler")) {
            Glide.with(context)
                    .load(ContextCompat.getDrawable(context, R.drawable.bycicle))
                    .into(holder.carImage);
        }
        if (catogeryListList.get(holder.getAdapterPosition()).getVehicleCategoryName().equals("Three Wheeler")) {
            Glide.with(context)
                    .load(ContextCompat.getDrawable(context, R.drawable.rickshaw))
                    .into(holder.carImage);
        }
        if (catogeryListList.get(holder.getAdapterPosition()).getVehicleCategoryName().equals("four Wheeler")) {
            Glide.with(context)
                    .load(ContextCompat.getDrawable(context, R.drawable.sport_car))
                    .into(holder.carImage);
        }

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
        TextView carClassName, carCatName;
        ImageView carImage;
        ElasticCardView selectVc;

        public MyViewHolder(View itemView) {
            super(itemView);
            carClassName = itemView.findViewById(R.id.tv_vehicle_class_name);
            carCatName = itemView.findViewById(R.id.tv_vehicle_cat_name);
            carImage = itemView.findViewById(R.id.iv_cab_image);
            selectVc = itemView.findViewById(R.id.select_vC);
            selectVc.setOnClickListener(v -> {
                listener.onItemClick(catogeryListList.get(getAdapterPosition()),getAdapterPosition(),selectVc,v, "Click");
            });
        }
    }
}