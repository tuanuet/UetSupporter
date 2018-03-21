package vnu.uet.tuan.uetsupporter.Adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
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
import vnu.uet.tuan.uetsupporter.Model.Response.Feedback;
import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.Utils.Utils;
import vnu.uet.tuan.uetsupporter.config.Config;

/**
 * Created by vmtuan on 3/12/2017.
 */

public class RecyclerAdapterFeedBack extends RecyclerView.Adapter {
    private final String TAG = this.getClass().getSimpleName();

    private List<Feedback> parentList;
    private List<Feedback> list;

    private Context context;
    private final int VIEW_TYPE_ITEM = 0;

    public RecyclerAdapterFeedBack(Context context, List<Feedback> list) {
        this.context = context;
        this.parentList = Stream.of(list)
                .filter(i->i.getSubFeedback()==null)
                .toList();
        this.list = list;
    }

    public void addAll(List<Feedback> list){
        this.list.addAll(list);
        this.parentList.addAll(Stream.of(list)
                .filter(i->i.getSubFeedback()==null)
                .toList());
        notifyItemRangeInserted(this.list.size() - list.size(),list.size());
    }
    public void addOne(Feedback feedback){
        this.list.add(feedback);
        if(feedback.getSubFeedback() == null){
            this.parentList.add(feedback);
            notifyItemInserted(this.list.size() - 1);
        } else {
            int parentOfSub = Stream.of(this.parentList)
                    .findIndexed((index, f) -> f.get_id().equals(feedback.getSubFeedback()))
                    .get()
                    .getFirst();
            notifyItemChanged(parentOfSub);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (viewType == VIEW_TYPE_ITEM) {
            View view = inflater.inflate(R.layout.item_comment_recycler, parent, false);
            RecyclerAdapterFeedBack.ItemViewHolder holder = new RecyclerAdapterFeedBack.ItemViewHolder(view);
            return holder;
        }
        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof RecyclerAdapterFeedBack.ItemViewHolder) {
            final RecyclerAdapterFeedBack.ItemViewHolder itemViewHolder = (ItemViewHolder) holder;

            Feedback fb = parentList.get(position);
            String avatar = Config.API_HOSTNAME + "/api/avatar/" + parentList.get(position).getSender().get_id();
            Glide.with(context)
                    .load(avatar)
                    .centerCrop()
                    .into(itemViewHolder.avatar);
            itemViewHolder.noiDung.setText(fb.getContent());
            itemViewHolder.nameUser.setText(fb.getSender().getName());
            itemViewHolder.time.setText(Utils.getThoiGian(context,fb.getCreatedAt()));

            //sub recyclerview
            LinearLayoutManager manager = new LinearLayoutManager(context);
            itemViewHolder.subView.setLayoutManager(manager);

            List<Feedback> subList = Stream.of(list)
                    .filter(i -> i.getSubFeedback()!= null)
                    .filter(i -> fb.get_id().equals(i.getSubFeedback()))
                    .toList();

            RecyclerAdapterSubFeedBack adapter = new RecyclerAdapterSubFeedBack(context,subList);
            adapter.setOnItemClickListener(new ClickListener() {
                @Override
                public void onItemClick(int position, View v) {

                }

                @Override
                public void onItemLongClick(int position, View v) {

                }

                @Override
                public void onReplyClick(Feedback feedback,int position, View v) {
                    clickListener.onReplyClick(feedback,position, v);
                }
            });
            itemViewHolder.subView.setNestedScrollingEnabled(false);
            itemViewHolder.subView.setAdapter(adapter);
        }
    }

    @Override
    public int getItemCount() {
        return parentList == null ? 0 : parentList.size();
    }

    public int getParentPosition(Feedback feedback) {
        return Stream.of(this.parentList)
                .findIndexed((index, f) -> f.get_id().equals(feedback.getSubFeedback()))
                .get()
                .getFirst();
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView nameUser, noiDung, time;
        ImageView avatar;
        RecyclerView subView;

        public ItemViewHolder(final View itemView) {
            super(itemView);

            nameUser = (TextView) itemView.findViewById(R.id.username);
            noiDung = (TextView) itemView.findViewById(R.id.noidung);
            time = (TextView) itemView.findViewById(R.id.time);
            avatar = (ImageView) itemView.findViewById(R.id.avatar);
            subView = (RecyclerView) itemView.findViewById(R.id.recycle_sub);

            itemView.findViewById(R.id.reply).setOnClickListener(v -> {
                Log.e(TAG,parentList.get(getAdapterPosition()).toString());
                clickListener.onReplyClick(parentList.get(getAdapterPosition()),getAdapterPosition(),v);
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

    //Listener
    public interface ClickListener {
        void onItemClick(int position, View v);

        void onItemLongClick(int position, View v);

        void onReplyClick(Feedback feedback,int position, View v);
    }

    public ClickListener clickListener;

    public void setOnItemClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }
}