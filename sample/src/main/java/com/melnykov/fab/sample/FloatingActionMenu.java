package com.melnykov.fab.sample;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.melnykov.fab.FloatingActionView;

/**
 * Created by reyn on 15/2/9.
 */
public class FloatingActionMenu extends FloatingActionView {
    private LinearLayout containerView;

    public FloatingActionMenu(Context context) {
        this(context, null);
    }

    public FloatingActionMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FloatingActionMenu(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setOrientation(VERTICAL);
        addBorderInTop();
    }

    private void addBorderInTop() {
        View line = new View(getContext());
        line.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
        line.setBackgroundColor(getResources().getColor(R.color.primary));
        addView(line);

        containerView = new LinearLayout(getContext());
        containerView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        containerView.setOrientation(HORIZONTAL);
        addView(containerView);
    }

    private View addItemView(int itemId, String title, int icon, String iconUri, boolean highlight) {
        RelativeLayout itemView = new RelativeLayout(getContext());
        itemView.setTag(R.id.floating_item_tag_id, itemId);
        itemView.setBackgroundColor(getResources().getColor(highlight?R.color.alice_blue_click:R.color.alice_blue));

        int itemHeight = getResources().getDimensionPixelOffset(R.dimen.floating_view_height);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                itemHeight, 1);
        itemView.setLayoutParams(params);
        itemView.setGravity(Gravity.CENTER);

        ImageView imageView = new ImageView(getContext());
        RelativeLayout.LayoutParams imageViewParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        imageViewParams.addRule(RelativeLayout.CENTER_VERTICAL);
        imageView.setLayoutParams(imageViewParams);
        if (icon > 0) {
            imageView.setImageResource(icon);
        } else {
            //from web
        }
        imageView.setId(R.id.floating_item_tag_image_id);
        itemView.addView(imageView);

        TextView textView = new TextView(getContext());
        RelativeLayout.LayoutParams textViewParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        textViewParams.addRule(RelativeLayout.CENTER_VERTICAL);
        textViewParams.addRule(RelativeLayout.RIGHT_OF, imageView.getId());
        textViewParams.leftMargin = (int)  dipToPix(getContext(), 3);
        textView.setLayoutParams(textViewParams);
        textView.setText(title);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        textView.setTextColor(getResources().getColor(R.color.primary));
        itemView.addView(textView);

        itemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //留下接口，在使用的地方实现，对每个不同的Item设立不同的监听事件
            }
        });

        if (itemView.getChildCount() > 0) {
            addDivider();
        }
        containerView.addView(itemView);
        return itemView;
    }

    private void addDivider() {
        View line = new View(getContext());
        line.setLayoutParams(new ViewGroup.LayoutParams(1, ViewGroup.LayoutParams.MATCH_PARENT));
        line.setBackgroundColor(getResources().getColor(R.color.primary));
        containerView.addView(line);
    }

    public void attachToListView(ListView listView) {
        mScrollListenerForListView = new AbsListViewScrollDetectorImpl();
        mScrollListenerForListView.setListView(listView);
        mScrollListenerForListView.setScrollThreshold(mScrollThreshold);
        listView.setOnScrollListener(mScrollListenerForListView);
    }

    public void addItem(int itemId, String title, int icon, String url, boolean highlight) {
        addItemView(itemId, title, icon, url, highlight);
    }
    private float dipToPix(Context context, float dip) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, metrics);
    }

    //public class AbsListViewScrollDetectorImpl extends FloatingActionView.AbsListViewScrollDetectorImp
}
