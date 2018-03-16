package vnu.uet.tuan.uetsupporter.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.Image;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;

import vnu.uet.tuan.uetsupporter.Model.Download.LoaiThongBao;
import vnu.uet.tuan.uetsupporter.Model.Download.MucDoThongBao;
import vnu.uet.tuan.uetsupporter.Model.AnnouncementNotification;
import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.Utils.Utils;
import vnu.uet.tuan.uetsupporter.config.Config;

/**
 * Created by Vu Minh Tuan on 2/16/2017.
 */

public class RecyclerAdapterHopThongBao extends RecyclerView.Adapter {
    private ArrayList<AnnouncementNotification> list;
    private Context context;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_ITEM_WARNING = 1;
    private final int VIEW_TYPE_ITEM_DANGER = 2;

    private int previousposition = -1;
    private ArrayList<MucDoThongBao> mucDoThongBaoList;
    private ArrayList<LoaiThongBao> loaiThongBaoList;
    private final String TAG = this.getClass().getSimpleName();

    public RecyclerAdapterHopThongBao(Context context, ArrayList<AnnouncementNotification> list) {
        this.context = context;
        this.list = list;
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
            AnnouncementNotification notification = list.get(position);

            itemViewHolder.txt_tieuDe.setText(notification.getTieuDe());
            itemViewHolder.txt_noiDung.setText(notification.getDescription());
            itemViewHolder.txt_thoiGian.setText(Utils.getThoiGian(context, notification.getThoiGianNhan()));
            itemViewHolder.img_hasFile.setVisibility(notification.getHasFile() ? View.VISIBLE : View.INVISIBLE);

            setupAvatarWithAuthor(itemViewHolder, notification);

            setupMucDo(itemViewHolder, notification);

            setupLoaiThongBao(itemViewHolder, notification);

            setupRead(itemViewHolder, notification);

            setupListenerIcon(itemViewHolder, position);

            setUpSampleImages(itemViewHolder,notification);
        }
    }

    private void setUpSampleImages(ItemViewHolder itemViewHolder, AnnouncementNotification notification) {
        final String[] sampleImages = notification.getDescriptionImages();
        if(sampleImages.length != 0) {
            itemViewHolder.carouselView.setPageCount(sampleImages.length);
            itemViewHolder.carouselView.setImageListener(new ImageListener() {
                @Override
                public void setImageForPosition(int position, ImageView imageView) {
                    Glide.with(context).load(Config.hostname+sampleImages[position])
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .centerCrop()
                            .into(imageView);
                }
            });
        } else {
            itemViewHolder.carouselView.setVisibility(View.GONE);
        }

    }

    private void setupRead(ItemViewHolder itemViewHolder, AnnouncementNotification notification) {
        itemViewHolder.cardView.setBackgroundColor(notification.getRead() ?
                context.getResources().getColor(R.color.card_view_read) :
                context.getResources().getColor(R.color.background_card)
        );
    }

    /**
     * Cài đặt sự kiện cho các icon
     * * send feedback,show context menu
     *
     * @param itemViewHolder
     */
    private void setupListenerIcon(ItemViewHolder itemViewHolder, final int position) {
        itemViewHolder.img_tool.setOnClickListener(v -> showPopup(v, position));
        itemViewHolder.angry.setOnClickListener(v -> reactionClick(v,position));
        itemViewHolder.cry.setOnClickListener(v -> reactionClick(v,position));
        itemViewHolder.love.setOnClickListener(v -> reactionClick(v,position));
        itemViewHolder.wow.setOnClickListener(v -> reactionClick(v,position));
        itemViewHolder.surprise.setOnClickListener(v -> reactionClick(v,position));

    }

    public void reactionClick(View v,int position) {
        String _id = list.get(position).get_id();
        int code = 0;
        switch (v.getId()){
            case R.id.ic_angry:
                code = Config.ReactionCode.ANGRY.ordinal();
                break;
            case R.id.ic_cry:
                code = Config.ReactionCode.CRY.ordinal();
                break;
            case R.id.ic_love:
                code = Config.ReactionCode.LOVE.ordinal();
                break;
            case R.id.ic_wow:
                code = Config.ReactionCode.WOW.ordinal();
                break;
            case R.id.ic_surprised:
                code = Config.ReactionCode.SURPRISE.ordinal();
                break;
        }
        clickListener.onReactionClick(_id,code);

    }

    /**
     * cài đặt avatar
     * * lên server để lấy ảnh về
     * * cache
     *
     * @param itemViewHolder
     * @param notification
     */
    private void setupAvatarWithAuthor(ItemViewHolder itemViewHolder, AnnouncementNotification notification) {

        String urlAvatar = Config.API_HOSTNAME + "/api/avatar/" + notification.getIdSender();
        Log.e(TAG, urlAvatar);
        Glide.with(context).load(urlAvatar)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .centerCrop()
                .into(itemViewHolder.avatar);

        itemViewHolder.txt_sender.setText(notification.getNameSender());
    }

    /**
     * thêm tên laoij thông báo
     * * lấy tên thông báo trong csdl
     *
     * @param itemViewHolder
     * @param notification
     */
    private void setupLoaiThongBao(ItemViewHolder itemViewHolder, AnnouncementNotification notification) {
        for (int i = 0; i < loaiThongBaoList.size(); i++) {
            if (loaiThongBaoList.get(i).get_id().equals(String.valueOf(notification.getIdLoaiThongBao()))) {
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
    private void setupMucDo(ItemViewHolder itemViewHolder, AnnouncementNotification notification) {
//        itemViewHolder.txt_tieuDe.setTextColor(context.getResources().getColor(R.color.dark_red));
        switch (notification.getCodeMucDoThongBao()) {
            case "khan_cap":
                itemViewHolder.txt_tieuDe.setTextColor(context.getResources().getColor(R.color.dark_red));
                break;
            case "canh_bao":
                itemViewHolder.txt_tieuDe.setTextColor(context.getResources().getColor(R.color.dark_yellow));
                break;
            case "binh_thuong":
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
        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_delete:
                    deleteitem(position);
                    break;
                case R.id.action_share:
                    break;
            }
            return true;
        });
    }

    private void deleteitem(int position) {
        this.list.remove(position);
        this.notifyItemRemoved(position);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView txt_tieuDe;
        TextView txt_noiDung;
        TextView txt_thoiGian;
        ImageView img_hasFile;
        RelativeLayout img_tool;
        LinearLayout img_read;
        ImageView avatar;
        TextView txt_loaithongbao;
        TextView txt_sender;
        CarouselView carouselView;
        ImageButton angry,cry,love,wow,surprise;


        public ItemViewHolder(final View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            txt_tieuDe = (TextView) itemView.findViewById(R.id.recycle_item_tieude);
            txt_noiDung = (TextView) itemView.findViewById(R.id.recycle_item_noidung);
            txt_thoiGian = (TextView) itemView.findViewById(R.id.recycle_item_postat);
            img_hasFile = (ImageView) itemView.findViewById(R.id.recycle_item_hasfile);
            img_tool = (RelativeLayout) itemView.findViewById(R.id.recycle_item_tool);
            avatar = (ImageView) itemView.findViewById(R.id.avatar);
            txt_loaithongbao = (TextView) itemView.findViewById(R.id.recycle_item_loaithongbao);
            txt_sender = (TextView) itemView.findViewById(R.id.recycle_item_sender);
            img_read = (LinearLayout) itemView.findViewById(R.id.recycle_item_isread);
            //image_header = (ImageView) itemView.findViewById(R.id.image_header);
            carouselView = (CarouselView) itemView.findViewById(R.id.carouselView);

            angry = (ImageButton) itemView.findViewById(R.id.ic_angry);
            cry = (ImageButton) itemView.findViewById(R.id.ic_cry);
            love = (ImageButton) itemView.findViewById(R.id.ic_love);
            wow = (ImageButton) itemView.findViewById(R.id.ic_wow);
            surprise = (ImageButton) itemView.findViewById(R.id.ic_surprised);

            itemView.setOnClickListener(v -> {
                clickListener.onItemClick(getAdapterPosition(), v);
            });
            itemView.setOnLongClickListener(v -> {
                clickListener.onItemLongClick(getAdapterPosition(), v);
                return false;
            });

        }
    }

    //Listener
    public interface ClickListener {
        void onItemClick(int position, View v);

        void onItemLongClick(int position, View v);

        void onReactionClick(String id,int code);
    }

    public ClickListener clickListener;

    public void setOnItemClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }
}
