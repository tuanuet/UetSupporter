package vnu.uet.tuan.uetsupporter.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.Image;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import vnu.uet.tuan.uetsupporter.Animation.RecyclerAnim;
import vnu.uet.tuan.uetsupporter.Model.Download.MucDoThongBao;
import vnu.uet.tuan.uetsupporter.Model.PushNotification;
import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.Utils.Utils;

/**
 * Created by Vu Minh Tuan on 2/16/2017.
 */

public class RecyclerAdapterHopThongBao extends RecyclerView.Adapter {
    private ArrayList<PushNotification> list;
    private Context context;
    private final int VIEW_TYPE_ITEM = 0;
    private int previousposition = -1;
    private ArrayList<MucDoThongBao> mucDoThongBaoList;
    private final String TAG = this.getClass().getSimpleName();

    public RecyclerAdapterHopThongBao(Context context, ArrayList<PushNotification> list) {
        this.context = context;
        this.list = list;
        this.mucDoThongBaoList = Utils.getAllMucDoThongBao(context);
    }


    @Override
    public int getItemViewType(int position) {
        return VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (viewType == VIEW_TYPE_ITEM) {
            View view = inflater.inflate(R.layout.recycler_item_hopthongbao, parent, false);
            ItemViewHolder holder = new ItemViewHolder(view);
            return holder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ItemViewHolder) {
            //===================INIT ITEM==============================

            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            PushNotification notification = list.get(position);

            itemViewHolder.txt_tieuDe.setText(notification.getTieuDe());
            itemViewHolder.txt_noiDung.setText(notification.getNoiDung());
            itemViewHolder.txt_thoiGian.setText(Utils.getThoiGian(notification.getThoiGianNhan()));

            itemViewHolder.img_read.setImageResource(notification.getRead() ? R.drawable.ic_read : R.drawable.ic_unread);
            Log.e(TAG, "isRead" + notification.getRead());
            itemViewHolder.img_hasFile.setVisibility(notification.getHasFile() ? View.VISIBLE : View.INVISIBLE);

            TextDrawable drawable = TextDrawable.builder()
                    .beginConfig()
                    .textColor(Color.WHITE)
                    .useFont(Typeface.DEFAULT)
                    .fontSize(30) /* size in px */
                    .bold()
                    .toUpperCase()
                    .endConfig()
                    .buildRoundRect(Utils.getFirstChar(notification.getTieuDe()), Utils.getRandomColor(notification.getTieuDe()), 15);
            itemViewHolder.avatar.setImageDrawable(drawable);

//            //mức độ
//
//            if (notification.getHasFile()) {
//                itemViewHolder.img_hasFile.setVisibility(View.VISIBLE);
//            } else {
//                itemViewHolder.img_read.setVisibility(View.INVISIBLE);
//            }


            itemViewHolder.img_tool.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPopup(v);
                }
            });

            //====================ANIMATION=========================
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

    private void showPopup(View v) {
        PopupMenu popupMenu = new PopupMenu(context, v);
        popupMenu.inflate(R.menu.hopthongbao_popup_menu);
        popupMenu.show();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {
        TextView txt_tieuDe;
        TextView txt_noiDung;
        TextView txt_thoiGian;
        ImageView mucDo;
        ImageView img_hasFile;
        ImageView img_read;
        ImageView img_tool;
        ImageView avatar;

        public ItemViewHolder(final View itemView) {
            super(itemView);

            txt_tieuDe = (TextView) itemView.findViewById(R.id.recycle_item_tieude);
            txt_noiDung = (TextView) itemView.findViewById(R.id.recycle_item_noidung);
            txt_thoiGian = (TextView) itemView.findViewById(R.id.recycle_item_postat);
            mucDo = (ImageView) itemView.findViewById(R.id.recycle_item_mucdo);
            img_read = (ImageView) itemView.findViewById(R.id.recycle_item_read);
            img_hasFile = (ImageView) itemView.findViewById(R.id.recycle_item_hasfile);
            img_tool = (ImageView) itemView.findViewById(R.id.recycle_item_tool);
            avatar = (ImageView) itemView.findViewById(R.id.avatar);

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
