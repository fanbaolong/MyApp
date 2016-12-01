package example.myapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import example.myapp.view.BottomDialog;

/**
 *create by fanbaolong on 2016-12-01
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnPwd;
    private Button mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mBtnPwd = (Button) findViewById(R.id.btn_pwd);
        mBtnPwd.setOnClickListener(this);
        mView = (Button) findViewById(R.id.view);
        mView.setOnClickListener(this);
    }

    /**
     * 弹出密码验证界面
     */
    private void showVerifyDialog() {
        final BottomDialog mDialog = BottomDialog.create(getSupportFragmentManager());
        mDialog.setViewListener(new BottomDialog.ViewListener() {
            @Override
            public void bindView(View v) {

                FrameLayout frameLayout = (FrameLayout) v.findViewById(R.id.view_verify_framelayout);
                ManagerPwdView verifyView = new ManagerPwdView(MainActivity.this, 0);
                verifyView.setInputNumberCodeCallback(new ManagerPwdView.OnInputNumberCodeCallback() {

                    @Override
                    public void onSuccess() {
                        mDialog.dismiss();
                    }

                    @Override
                    public void onClose() {
                        mDialog.dismiss();
                    }
                });

                frameLayout.addView(verifyView.getView());
            }
        })
                .setLayoutRes(R.layout.buttom_fragment_layout)
                .setDimAmount(0.6f)
                .setTag("BottomDialog")
                .show();
    }


    /**
     * 自定义的弹出框
     */
    private void showButtomSelectDialog() {
        final BottomDialog mDialog = BottomDialog.create(getSupportFragmentManager());

        mDialog.setViewListener(new BottomDialog.ViewListener() {
            @Override
            public void bindView(View v) {
                TextView tv1 = (TextView) v.findViewById(R.id.pay_wechat_tv);
                TextView tv2 = (TextView) v.findViewById(R.id.pay_alipay_tv);

                tv1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "微信支付", Toast.LENGTH_SHORT).show();
                        mDialog.dismiss();
                    }
                });
                tv2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "支付宝支付", Toast.LENGTH_SHORT).show();
                        mDialog.dismiss();
                    }
                });
            }
        })
                .setLayoutRes(R.layout.buttom_select_dialog)
                .setDimAmount(0.6f)
                .setTag("BottomDialog")
                .show();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_pwd:
                showVerifyDialog();
                break;
            case R.id.view:
                showButtomSelectDialog();
                break;
            default:
                break;
        }
    }
}
