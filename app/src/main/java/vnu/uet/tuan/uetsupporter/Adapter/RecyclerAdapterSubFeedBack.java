package vnu.uet.tuan.uetsupporter.Adapter;

/**
 * Created by FRAMGIA\vu.minh.tuan on 20/03/2018.
 */


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.bumptech.glide.Glide;

import java.util.List;

import vnu.uet.tuan.uetsupporter.Model.Comment;
import vnu.uet.tuan.uetsupporter.Model.Response.Feedback;
import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.Utils.Utils;
import vnu.uet.tuan.uetsupporter.config.Config;

/**
 * Created by vmtuan on 3/12/2017.
 */

public class RecyclerAdapterSubFeedBack extends RecyclerView.Adapter {
    private final String TAG = this.getClass().getSimpleName();

    private List<Feedback> list;
    private List<Feedback> subList;
    private Context context;
    private final int VIEW_TYPE_ITEM = 0;

    public RecyclerAdapterSubFeedBack(Context context, List<Feedback> list) {
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
            View view = inflater.inflate(R.layout.item_sub_comment_recycler, parent, false);
            RecyclerAdapterSubFeedBack.ItemViewHolder holder = new RecyclerAdapterSubFeedBack.ItemViewHolder(view);
            return holder;
        }
        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof RecyclerAdapterSubFeedBack.ItemViewHolder) {
            final RecyclerAdapterSubFeedBack.ItemViewHolder itemViewHolder = (ItemViewHolder) holder;

            Feedback comment = list.get(position);
            String avatar = Config.API_HOSTNAME + "/api/avatar/" + list.get(position).getSender().get_id();
            Glide.with(context)
                    .load(avatar)
                    .centerCrop()
                    .into(itemViewHolder.avatar);
            itemViewHolder.noiDung.setText(comment.getContent());
            itemViewHolder.nameUser.setText(comment.getSender().getName());
            itemViewHolder.time.setText(Utils.getThoiGian(context, comment.getCreatedAt()));
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView nameUser, noiDung, time;
        ImageView avatar;

        public ItemViewHolder(final View itemView) {
            super(itemView);

            nameUser = (TextView) itemView.findViewById(R.id.username);
            noiDung = (TextView) itemView.findViewById(R.id.noidung);
            time = (TextView) itemView.findViewById(R.id.time);
            avatar = (ImageView) itemView.findViewById(R.id.avatar);

            itemView.findViewById(R.id.reply).setOnClickListener(v -> {
                clickListener.onReplyClick(list.get(getAdapterPosition()),getAdapterPosition(),v);
            });

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


    public RecyclerAdapterFeedBack.ClickListener clickListener;

    public void setOnItemClickListener(RecyclerAdapterFeedBack.ClickListener clickListener) {
        this.clickListener = clickListener;
    }
}
