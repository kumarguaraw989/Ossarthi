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
import androidx.recyclerview.widget.RecyclerView;

import com.cabossarthi.osaarthi.R;
import com.cabossarthi.osaarthi.model.CustomerBookingListModal;
import com.cabossarthi.osaarthi.ui.user.UserBookingListActivity;
import com.cabossarthi.osaarthi.ui.user.UserDashboardActivity;
import com.google.gson.Gson;

import java.util.List;

public class UserBookingListAdapter extends RecyclerView.Adapter<UserBookingListAdapter.MyViewHolder> {
    List<CustomerBookingListModal.CustomerRideList> customerBookingListModals;
    Context context;
    private UserBookingListAdapter.OnItemClickListeer listener;
    UserBookingListActivity userBookingListActivity;

    public interface OnItemClickListeer {
        void onItemClick(CustomerBookingListModal.CustomerRideList customerBookingListModal, int pos, LinearLayout cab, View view, String callFunction);
    }

    public UserBookingListAdapter(Context context, List<CustomerBookingListModal.CustomerRideList> customerBookingListModals, UserBookingListActivity userBookingListActivity, UserBookingListAdapter.OnItemClickListeer onItemClickListeer) {
        this.customerBookingListModals = customerBookingListModals;
        this.context = context;
        this.listener = onItemClickListeer;
        this.userBookingListActivity = userBookingListActivity;
    }

    @NonNull
    @Override
    public UserBookingListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_user_bookinglist, parent, false);
        return new UserBookingListAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final UserBookingListAdapter.MyViewHolder holder, final int position) {
        Log.e("TAG", "onBindViewHolder: " + new Gson().toJson(customerBookingListModals.get(holder.getAdapterPosition())));
        CustomerBookingListModal.CustomerRideList customerRideList = customerBookingListModals.get(holder.getAdapterPosition());
        holder.source.setText(customerRideList.getSource());
        holder.destination.setText(customerRideList.getDestination());
        holder.totalPrice.setText("₹ "+customerRideList.getTotalFare());
    }

    @Override
    public int getItemCount() {
        return customerBookingListModals.size();
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    // stores and recycles views as they are scrolled off screen
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView source,destination,totalPrice;
        ImageView carImage;
        LinearLayout selectBooking;

        public MyViewHolder(View itemView) {
            super(itemView);
            source = itemView.findViewById(R.id.tv_source_address);
            destination = itemView.findViewById(R.id.tv_drop_address);
            totalPrice=itemView.findViewById(R.id.tv_total_price);
            carImage = itemView.findViewById(R.id.iv_cab_image);
            selectBooking = itemView.findViewById(R.id.ll_selectBooking);
            selectBooking.setOnClickListener(v -> {
                listener.onItemClick(customerBookingListModals.get(getAdapterPosition()), getAdapterPosition(), selectBooking, v, "Click");
            });
        }
    }
}