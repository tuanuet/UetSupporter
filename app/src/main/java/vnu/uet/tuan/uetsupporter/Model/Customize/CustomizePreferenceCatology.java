package vnu.uet.tuan.uetsupporter.Model.Customize;

import android.content.Context;
import android.graphics.Color;
import android.preference.PreferenceCategory;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * Created by vmtuan on 3/19/2017.
 */

public class CustomizePreferenceCatology extends PreferenceCategory {
    public CustomizePreferenceCatology(Context context) {
        super(context);
    }

    public CustomizePreferenceCatology(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomizePreferenceCatology(Context context, AttributeSet attrs,
                                       int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
        TextView titleView = (TextView) view.findViewById(android.R.id.title);
        titleView.setTextColor(Color.RED);
    }
}