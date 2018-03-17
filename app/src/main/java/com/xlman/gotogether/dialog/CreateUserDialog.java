package com.xlman.gotogether.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xlman.gotogether.R;

/**
 * 修改信息的弹出框
 * 引用自http://blog.csdn.net/xiao190128/article/details/53282993
 * <p>
 * Created by xlman on 11:46 2018/3/18.
 */

public class CreateUserDialog extends Dialog {
    /**
     * 上下文对象 *
     */
    Activity context;

    private Button btn_save;

    private TextView text_desc;
    public EditText text_info;

    private View.OnClickListener mClickListener;

    public CreateUserDialog(Activity context) {
        super(context);
        this.context = context;
    }

    public CreateUserDialog(Activity context, int theme, View.OnClickListener clickListener) {
        super(context, theme);
        this.context = context;
        this.mClickListener = clickListener;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 指定布局
        this.setContentView(R.layout.lay_dialog);

        text_desc = findViewById(R.id.text_desc);
        text_info = findViewById(R.id.text_info);

        /*
         * 获取圣诞框的窗口对象及参数对象以修改对话框的布局设置, 可以直接调用getWindow(),表示获得这个Activity的Window
         * 对象,这样这可以以同样的方式改变这个Activity的属性.
         */
        Window dialogWindow = this.getWindow();

        WindowManager m = context.getWindowManager();
        // 获取屏幕宽、高用
        Display d = m.getDefaultDisplay();
        // 获取对话框当前的参数值
        WindowManager.LayoutParams p = dialogWindow.getAttributes();
        // p.height = (int) (d.getHeight() * 0.6); // 高度设置为屏幕的0.6
        // 宽度设置为屏幕的0.8
        p.width = (int) (d.getWidth() * 0.8);
        dialogWindow.setAttributes(p);

        // 根据id在布局中找到控件对象
        btn_save = findViewById(R.id.btn_save);

        // 为按钮绑定点击事件监听器
        btn_save.setOnClickListener(mClickListener);

        this.setCancelable(true);
    }
}
