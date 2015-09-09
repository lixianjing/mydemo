/*
 * Copyright (C) 2010~2014 FMSoft (Espier Studio)
 * 
 * This file is a part of Espier apps.
 * 
 * All rights reserved.
 */

package com.xian.myapp.swipe;

import java.util.HashSet;
import java.util.Set;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

public abstract class SwipeCusorAdapter extends CursorAdapter {

    public static enum Mode {
        Single, Multiple
    };

    private Mode mode = Mode.Single;

    public final int INVALID_POSITION = -1;
    private final Set<Integer> mOpenPositions = new HashSet<Integer>();
    private int mOpenPosition = INVALID_POSITION;
    private SwipeLayout mPrevious;

    /**
     * return the {@link com.xian.swipe.SwipeLayout} resource id, int the view item.
     *
     * @param position
     * @return
     */
    public abstract int getSwipeLayoutResourceId(int position);

    /**
     * generate a new view item, you don't need to fill any value to this view, you have a chance to
     * fill it in {@code fillValues} method.
     *
     * @param position
     * @param parent
     * @return
     */
    public abstract View generateView(int position, ViewGroup parent);

    /**
     * fill values to the view.
     *
     * @param position
     * @param convertView
     */
    public abstract void fillValues(Cursor cursor, View convertView);



    public SwipeCusorAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(final Context context, Cursor cursor, ViewGroup parent) {

        SwipeLayout swipeLayout;
        final int position = cursor.getPosition();
        int swipeResourceId = getSwipeLayoutResourceId(position);
        View view = generateView(position, parent);
        swipeLayout = (SwipeLayout) view.findViewById(swipeResourceId);
        if (swipeLayout != null) {
            OnLayoutListener onLayoutListener = new OnLayoutListener(position);
            SwipeMemory swipeMemory = new SwipeMemory(position);
            swipeLayout.addSwipeListener(swipeMemory);
            // swipeLayout.addOnLayoutListener(onLayoutListener);
            swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
            swipeLayout.setTag(swipeResourceId, new ValueBox(position, swipeMemory,
                    onLayoutListener));
        }


        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        SwipeLayout swipeLayout;
        int position = cursor.getPosition();
        int swipeResourceId = getSwipeLayoutResourceId(position);
        swipeLayout = (SwipeLayout) view.findViewById(swipeResourceId);
        if (swipeLayout != null) {
            ValueBox valueBox = (ValueBox) swipeLayout.getTag(swipeResourceId);
            valueBox.swipeMemory.setPosition(position);
            valueBox.onLayoutListener.setPosition(position);
            valueBox.position = position;
        }

        fillValues(cursor, view);
    }


    /**
     * set open mode
     *
     * @param mode
     */
    public void setMode(Mode mode) {
        if (mode == Mode.Multiple) {
            mOpenPositions.clear();
        } else {
            mOpenPosition = INVALID_POSITION;
        }
        this.mode = mode;
        notifyDataSetChanged();
    }

    public Mode getMode() {
        return mode;
    }

    /**
     * Open and item in the list
     *
     * @param position Position of the item
     */
    public void openItem(int position) {
        if (mode == Mode.Multiple) {
            if (!mOpenPositions.contains(position)) mOpenPositions.add(position);
        } else {
            mOpenPosition = position;
        }
        notifyDataSetChanged();
    }

    /**
     * Close an item in the list
     *
     * @param position Position of the item
     */
    public void closeItem(int position) {
        if (mode == Mode.Multiple) {
            mOpenPositions.remove(position);
        } else {
            if (mOpenPosition == position) mOpenPosition = INVALID_POSITION;
        }
        notifyDataSetChanged();
    }

    class ValueBox {
        OnLayoutListener onLayoutListener;
        SwipeMemory swipeMemory;
        int position;

        ValueBox(int position, SwipeMemory swipeMemory, OnLayoutListener onLayoutListener) {
            this.swipeMemory = swipeMemory;
            this.onLayoutListener = onLayoutListener;
            this.position = position;
        }
    }

    class OnLayoutListener implements SwipeLayout.OnLayout {

        private int position;

        OnLayoutListener(int position) {
            this.position = position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        @Override
        public void onLayout(SwipeLayout v) {
            if (mode == Mode.Multiple) {
                if (mOpenPositions.contains(position))
                    v.open(false);
                else {
                    v.close(false);
                }
            } else {
                if (mOpenPosition == position) {
                    v.open(false);
                } else {
                    v.close(false);
                }
            }
        }
    }

    class SwipeMemory implements SwipeLayout.SwipeListener {

        private int position;

        SwipeMemory(int position) {
            this.position = position;
        }

        @Override
        public void onClose(SwipeLayout layout) {
            if (mode == Mode.Multiple)
                mOpenPositions.remove(position);
            else {
                if (position == mOpenPosition) {
                    mOpenPosition = INVALID_POSITION;
                    mPrevious = null;
                }

            }
        }


        @Override
        public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {

        }

        @Override
        public void onOpen(SwipeLayout layout) {
            if (mode == Mode.Multiple)
                mOpenPositions.add(position);
            else {
                if (mOpenPosition != position) {
                    if (mPrevious != null) mPrevious.close();
                }
                mOpenPosition = position;
                mPrevious = layout;
            }
        }

        @Override
        public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {

        }

        public void setPosition(int position) {
            this.position = position;
        }
    }


}
