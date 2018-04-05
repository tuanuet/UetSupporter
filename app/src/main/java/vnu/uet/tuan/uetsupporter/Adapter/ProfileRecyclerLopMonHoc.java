package vnu.uet.tuan.uetsupporter.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import vnu.uet.tuan.uetsupporter.Model.Course;
import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.Utils.Utils;

/**
 * Created by Vu Minh Tuan on 2/11/2017.
 */

public class ProfileRecyclerLopMonHoc extends RecyclerView.Adapter {
    private List<Course> list;
    private Context context;
    private final int VIEW_TYPE_ITEM = 0;

    public ProfileRecyclerLopMonHoc(Context context, List<Course> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public int getItemViewType(int position) {

        return VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (viewType == VIEW_TYPE_ITEM) {
            View view = inflater.inflate(R.layout.item_profile_recycler_lopmonhoc, parent, false);
            ItemViewHolder holder = new ItemViewHolder(view);
            return holder;
        }
        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ItemViewHolder) {
            final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;

            Course lopMonHoc = list.get(position);

            if(lopMonHoc.getLectures().size() == 0 ){
                itemViewHolder.txt_giangvien.setText("Khoa công nghệ thông tin");
            } else {
                itemViewHolder.txt_giangvien.setText(Utils.getTenGiangVien(lopMonHoc.getLectures()));
            }

            itemViewHolder.txt_lopmonhoc.setText(Utils.getTenLopMonHoc(lopMonHoc));
            itemViewHolder.txt_siso.setText(String.valueOf((int) (Math.random() * 50) + 50));
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView txt_lopmonhoc;
        TextView txt_giangvien;
        TextView txt_kihoc;
        TextView txt_siso;

        public ItemViewHolder(final View itemView) {
            super(itemView);

            txt_giangvien = (TextView) itemView.findViewById(R.id.item_tengiangvien);
            txt_kihoc = (TextView) itemView.findViewById(R.id.item_kihoc);
            txt_lopmonhoc = (TextView) itemView.findViewById(R.id.item_lopmonhoc);
            txt_siso = (TextView) itemView.findViewById(R.id.item_siso);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }

        @Override
        public boolean onLongClick(View v) {
            clickListener.onItemLongClick(getAdapterPosition(), v);
            return false;
        }
    }

    //Listener
    public interface ClickListener {
        void onItemClick(int position, View v);

        void onItemLongClick(int position, View v);
    }

    public ClickListener clickListener;

    public void setOnItemClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }
}