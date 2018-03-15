package com.xlman.factory.model.db;

import com.raizlabs.android.dbflow.structure.BaseModel;
import com.xlman.factory.utils.DiffUiDataCallback;

/**
 * App中的一个基础的BaseDBModel
 * 继承了数据库框架DbFlow中的基础类
 * 同时定义我们需要的方法
 *
 * Created by xlman on 2018/2/13.
 */

public abstract class BaseDbModel<Model> extends BaseModel
        implements DiffUiDataCallback.UiDataDiffer<Model>{
}
