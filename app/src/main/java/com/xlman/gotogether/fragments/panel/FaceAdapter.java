package com.xlman.gotogether.fragments.panel;

import android.view.View;

import com.xlman.common.widget.recycler.RecyclerAdapter;
import com.xlman.face.Face;
import com.xlman.gotogether.R;

import java.util.List;

/**
 *
 * Created by xlman on 21:00 2018/3/5.
 */

public class FaceAdapter extends RecyclerAdapter<Face.Bean> {

    public FaceAdapter(List<Face.Bean> beans, AdapterListener<Face.Bean> listener) {
        super(beans, listener);
    }

    @Override
    protected int getItemViewType(int position, Face.Bean bean) {
        return R.layout.cell_face;
    }

    @Override
    protected ViewHolder<Face.Bean> onCreateViewHolder(View root, int viewType) {
        return new FaceHolder(root);
    }
}
