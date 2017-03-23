package vnu.uet.tuan.uetsupporter.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.Image;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import vnu.uet.tuan.uetsupporter.Animation.RecyclerAnim;
import vnu.uet.tuan.uetsupporter.Model.Download.LoaiThongBao;
import vnu.uet.tuan.uetsupporter.Model.Download.MucDoThongBao;
import vnu.uet.tuan.uetsupporter.Model.PushNotification;
import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.Utils.Utils;
import vnu.uet.tuan.uetsupporter.config.Config;

/**
 * Created by Vu Minh Tuan on 2/16/2017.
 */

public class RecyclerAdapterHopThongBao extends RecyclerView.Adapter {
    private ArrayList<PushNotification> list;
    private Context context;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_ITEM_WARNING = 1;
    private final int VIEW_TYPE_ITEM_DANGER = 2;

    private int previousposition = -1;
    private ArrayList<MucDoThongBao> mucDoThongBaoList;
    private ArrayList<LoaiThongBao> loaiThongBaoList;
    private final String TAG = this.getClass().getSimpleName();

    public RecyclerAdapterHopThongBao(Context context, ArrayList<PushNotification> list) {
        this.context = context;
        this.list = list;
//        this.mucDoThongBaoList = Utils.getAllMucDoThongBao(context);
        this.loaiThongBaoList = Utils.getAllLoaiThongBao(context);
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
            itemViewHolder.img_hasFile.setVisibility(notification.getHasFile() ? View.VISIBLE : View.INVISIBLE);

            setupAvatarWithAuthor(itemViewHolder, notification);

            setupMucDo(itemViewHolder, notification);

            setupLoaiThongBao(itemViewHolder, notification);

            setupRead(itemViewHolder, notification);

            setupListenerIcon(itemViewHolder, position);

        }


    }

    private void setupRead(ItemViewHolder itemViewHolder, PushNotification notification) {
        itemViewHolder.img_read.setImageResource(notification.getRead() ?
                R.drawable.ic_icon_read : R.drawable.ic_action_unread);
    }

    /**
     * Cài đặt sự kiện cho các icon
     * * send feedback,show context menu
     *
     * @param itemViewHolder
     */
    private void setupListenerIcon(ItemViewHolder itemViewHolder, final int position) {
        itemViewHolder.img_tool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(v, position);
            }
        });

        itemViewHolder.sendFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //chuyen activity
                Toast.makeText(context, context.getResources().getString(R.string.title_sent), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * cài đặt avatar
     * * lên server để lấy ảnh về
     * * cache
     *
     * @param itemViewHolder
     * @param notification
     */
    private void setupAvatarWithAuthor(ItemViewHolder itemViewHolder, PushNotification notification) {

        String urlAvatar = Config.API_HOSTNAME + "/avatar/" + notification.getIdSender();
        Log.e(TAG, urlAvatar);
        Glide.with(context).load(urlAvatar)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .centerCrop()
                .into(itemViewHolder.avatar);

        itemViewHolder.txt_sender.setText(notification.getNameSender());

//        TextDrawable drawable = TextDrawable.builder()
//                .beginConfig()
//                .textColor(Color.WHITE)
//                .useFont(Typeface.DEFAULT)
//                .fontSize(30) /* size in px */
//                .bold()
//                .toUpperCase()
//                .endConfig()
//                .buildRoundRect(Utils.getFirstChar(notification.getTieuDe()), Utils.getRandomColor(notification.getTieuDe()), 15);
//        itemViewHolder.avatar.setImageDrawable(drawable);
    }

    /**
     * thêm tên laoij thông báo
     * * lấy tên thông báo trong csdl
     *
     * @param itemViewHolder
     * @param notification
     */
    private void setupLoaiThongBao(ItemViewHolder itemViewHolder, PushNotification notification) {
        for (int i = 0; i < loaiThongBaoList.size(); i++) {
            if (loaiThongBaoList.get(i).get_id() == notification.getIdLoaiThongBao()) {
                itemViewHolder.txt_loaithongbao.setText(loaiThongBaoList.get(i).getTenLoaiThongBao());
            }
        }
    }

    /**
     * phần này cùi quá vẫn chưa biết sửa sao cho ngon lành
     * có thể phải dùng quan hệ trong csdl
     *
     * @param itemViewHolder
     * @param notification
     */
    private void setupMucDo(ItemViewHolder itemViewHolder, PushNotification notification) {
        switch (notification.getIdMucDoThongBao()) {
            case 1:
                itemViewHolder.txt_tieuDe.setTextColor(context.getResources().getColor(R.color.dark_red));
                break;
            case 2:
                itemViewHolder.txt_tieuDe.setTextColor(context.getResources().getColor(R.color.dark_yellow));
                break;
            case 3:
                itemViewHolder.txt_tieuDe.setTextColor(context.getResources().getColor(R.color.black));
                break;
            default:

                break;
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    private void showPopup(View v, final int position) {
        PopupMenu popupMenu = new PopupMenu(context, v);
        popupMenu.inflate(R.menu.hopthongbao_popup_menu);
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_delete:
                        deleteitem(position);
                        break;
                    case R.id.action_share:
                        break;
                }
                return true;
            }
        });
    }

    private void deleteitem(int position) {
        this.list.remove(position);
        this.notifyItemRemoved(position);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {
        TextView txt_tieuDe;
        TextView txt_noiDung;
        TextView txt_thoiGian;
        ImageView img_hasFile;
        RelativeLayout img_tool;
        ImageView img_read;
        ImageView avatar;
        ImageView sendFeedback;
        TextView txt_loaithongbao;
        TextView txt_sender;

        @SuppressLint("WrongViewCast")
        public ItemViewHolder(final View itemView) {
            super(itemView);

            txt_tieuDe = (TextView) itemView.findViewById(R.id.recycle_item_tieude);
            txt_noiDung = (TextView) itemView.findViewById(R.id.recycle_item_noidung);
            txt_thoiGian = (TextView) itemView.findViewById(R.id.recycle_item_postat);
            img_hasFile = (ImageView) itemView.findViewById(R.id.recycle_item_hasfile);
            img_tool = (RelativeLayout) itemView.findViewById(R.id.recycle_item_tool);
            avatar = (ImageView) itemView.findViewById(R.id.avatar);
            sendFeedback = (ImageView) itemView.findViewById(R.id.recycle_item_sendfeedback);
            txt_loaithongbao = (TextView) itemView.findViewById(R.id.recycle_item_loaithongbao);
            txt_sender = (TextView) itemView.findViewById(R.id.recycle_item_sender);
            img_read = (ImageView) itemView.findViewById(R.id.recycle_item_isread);

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
