package vnu.uet.tuan.uetsupporter.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import vnu.uet.tuan.uetsupporter.Animation.RecyclerAnim;
import vnu.uet.tuan.uetsupporter.Model.PushNotification;
import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.Utils.Utils;

/**
 * Created by Vu Minh Tuan on 2/16/2017.
 */

public class RecyclerAdapterHopThu extends RecyclerView.Adapter {
    private ArrayList<PushNotification> list;
    private Context context;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private int previousposition = -1;

    public RecyclerAdapterHopThu(Context context, ArrayList<PushNotification> list) {
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
            View view = inflater.inflate(R.layout.recycler_item_hopthu, parent, false);
            ItemViewHolder holder = new ItemViewHolder(view);
            return holder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            PushNotification notification = list.get(position);

            itemViewHolder.txt_tieuDe.setText(notification.getTieuDe());
            itemViewHolder.txt_noiDung.setText(notification.getNoiDung());
            itemViewHolder.txt_thoiGian.setText(Utils.getThoiGian(notification.getThoiGianNhan()));
            if (notification.getRead()) {
                itemViewHolder.img_read.setVisibility(View.VISIBLE);
            } else {
                itemViewHolder.img_read.setVisibility(View.GONE);
            }

            if (position >= previousposition) {
                RecyclerAnim.animate(itemViewHolder, true);
            } else RecyclerAnim.animate(itemViewHolder, false);

            previousposition = position;

        }


    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView txt_tieuDe;
        TextView txt_noiDung;
        TextView txt_thoiGian;
        CircleImageView img_icon;
        ImageView img_read;

        public ItemViewHolder(final View itemView) {
            super(itemView);

            txt_tieuDe = (TextView) itemView.findViewById(R.id.recycle_item_tieude);
            txt_noiDung = (TextView) itemView.findViewById(R.id.recycle_item_noidung);
            txt_thoiGian = (TextView) itemView.findViewById(R.id.recycle_item_postat);
            img_icon = (CircleImageView) itemView.findViewById(R.id.recycle_item_img);
            img_read = (ImageView) itemView.findViewById(R.id.recycle_item_read);

        }
    }
}
