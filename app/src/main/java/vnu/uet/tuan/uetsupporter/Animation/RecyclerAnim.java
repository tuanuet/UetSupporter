package vnu.uet.tuan.uetsupporter.Animation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;

import vnu.uet.tuan.uetsupporter.Adapter.PatternRecyclerAdapter;

/**
 * Created by Administrator on 13/01/2017.
 */

public class RecyclerAnim {
    public static void animate(PatternRecyclerAdapter.ItemViewHolder holder, boolean isdown) {
        AnimatorSet animatorSet = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            animatorSet = new AnimatorSet();
            ObjectAnimator animatorY = ObjectAnimator.ofFloat(holder.itemView, "translationY", isdown == true ? 200 : -200, 0);
            animatorY.setDuration(600);
//            objectAnimator animatorX = ObjectAnimator.ofFloat(holder.itemView, "translationX",isdown==true ? 300 : -300,0);
//        animatorX.setDuration(600);
//
//        ObjectAnimator scaleY = ObjectAnimator.ofFloat(holder.itemView, "scaleY", 0, 1);
//        scaleY.setDuration(1000);
//
//        ObjectAnimator scaleX = ObjectAnimator.ofFloat(holder.itemView, "scaleX", 0, 1);
//        scaleX.setDuration(1000);
            animatorSet.playTogether(animatorY);
            animatorSet.start();
        }
    }
}
