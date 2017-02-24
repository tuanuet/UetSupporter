package vnu.uet.tuan.uetsupporter.Adapter;

/**
 * Created by Administrator on 13/01/2017.
 */

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import vnu.uet.tuan.uetsupporter.Animation.RecyclerAnim;

import vnu.uet.tuan.uetsupporter.Model.Response.TinTuc;
import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.Utils.Utils;

public class PatternRecyclerAdapterTinTuc extends RecyclerView.Adapter {
    private ArrayList<TinTuc> list;
    private Context context;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private int previousposition = -1;


    public PatternRecyclerAdapterTinTuc(Context context, ArrayList<TinTuc> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public int getItemViewType(int position) {
        return list.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (viewType == VIEW_TYPE_ITEM) {
            View view = inflater.inflate(R.layout.pattern_item_recycler_factory_tintuc, parent, false);
            ItemViewHolder holder = new ItemViewHolder(view);
            return holder;
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = inflater.inflate(R.layout.layout_loading_item, parent, false);
            return new LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ItemViewHolder) {
            final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;

            itemViewHolder.txt_title.setText(list.get(position).getTitle());
            itemViewHolder.txt_postat.setText(Utils.getThoiGian(list.get(position).getPostAt()));
            itemViewHolder.txt_loaitintuc.setText(list.get(position).getLoaiTinTuc().getKind());

            if (list.get(position).getImageLink() != null) {
                Glide.with(context)
                        .load(list.get(position).getImageLink())
                        .thumbnail(0.5f)
                        .crossFade()
                        .into(itemViewHolder.img_picture);
            } else {
                // make sure Glide doesn't load anything into this view until told otherwise
                Glide.clear(itemViewHolder.img_picture);
                // remove the placeholder (optional); read comments below
                itemViewHolder.img_picture.setImageDrawable(null);
            }
            //onClick tool
            //show popUp
            itemViewHolder.tool.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPopup(itemViewHolder.tool);
                }
            });



            //animation
            if (position >= previousposition) {
                RecyclerAnim.animate(itemViewHolder, true);
            } else RecyclerAnim.animate(itemViewHolder, false);

            previousposition = position;

        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }


    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


    static class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);
        }
    }

    private void showPopup(View v){
        PopupMenu popupMenu = new PopupMenu(context,v);
        popupMenu.inflate(R.menu.tintuc_popup_menu);
        popupMenu.show();
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView txt_title;
        TextView txt_postat;
        TextView txt_loaitintuc;
        CircleImageView img_picture;
        ImageView tool;



        public ItemViewHolder(final View itemView) {
            super(itemView);

            txt_title = (TextView) itemView.findViewById(R.id.recycle_item_title);
            txt_postat = (TextView) itemView.findViewById(R.id.recycle_item_postat);
            txt_loaitintuc = (TextView) itemView.findViewById(R.id.recycle_item_loaitintuc);
            img_picture = (CircleImageView) itemView.findViewById(R.id.recycle_item_img);
            tool = (ImageView) itemView.findViewById(R.id.recycle_item_tool);

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