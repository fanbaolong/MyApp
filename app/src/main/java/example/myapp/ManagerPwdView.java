package example.myapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Stack;

/**
 * create by fanbaolong on 2016.12.01
 */
public class ManagerPwdView implements OnClickListener{

	private Context mContext;

	private static int NUMBER_COUNT = 6;
	private final static String PASSWORD_NUMBER_SYMBOL = "●";

	private OnInputNumberCodeCallback mCallback; // 返回结果的回调
	private Stack<Integer> mStack;

	private View view;
	private TextView tv_payment_pwd;
	private TextView tv0;
	private TextView tv1;
	private TextView tv2;
	private TextView tv3;
	private TextView tv4;
	private TextView tv5;
	private TextView tv6;
	private TextView tv7;
	private TextView tv8;
	private TextView tv9;
	private TextView tv_clean;
	private RelativeLayout relat_del;
	private TextView tv_dismiss;
	private TextView tv_title;
	private ProgressBar progressBar;
	private int mType = 0;


	/**
	 * @param mContext
	 * @param type 0为验证 1为修改
	 */
	public ManagerPwdView(Context mContext , int type) {
		this.mContext = mContext;
		this.mStack = new Stack<>();
		this.mType = type;
		initView();

	}


	@SuppressLint("InflateParams") 
	private void initView() {
		view = LayoutInflater.from(mContext).inflate(R.layout.view_payment_pwd, null);
		this.tv_payment_pwd = (TextView) view.findViewById(R.id.tv_payment_pwd);
		this.tv0 = (TextView) view.findViewById(R.id.tv0);
		this.tv1 = (TextView) view.findViewById(R.id.tv1);
		this.tv2 = (TextView) view.findViewById(R.id.tv2);
		this.tv3 = (TextView) view.findViewById(R.id.tv3);
		this.tv4 = (TextView) view.findViewById(R.id.tv4);
		this.tv5 = (TextView) view.findViewById(R.id.tv5);
		this.tv6 = (TextView) view.findViewById(R.id.tv6);
		this.tv7 = (TextView) view.findViewById(R.id.tv7);
		this.tv8 = (TextView) view.findViewById(R.id.tv8);
		this.tv9 = (TextView) view.findViewById(R.id.tv9);
		this.tv_clean = (TextView) view.findViewById(R.id.tv_clean);
		this.relat_del = (RelativeLayout) view.findViewById(R.id.relat_del);
		this.tv_dismiss = (TextView) view.findViewById(R.id.tv_dismiss);
		this.tv_title = (TextView) view.findViewById(R.id.tv_title);
		this.progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

		if( mType == 0 )
			this.tv_title.setText("验证主管密码");
		else 
			this.tv_title.setText("设置主管密码");

		this.tv0.setOnClickListener(this);
		this.tv1.setOnClickListener(this);
		this.tv2.setOnClickListener(this);
		this.tv3.setOnClickListener(this);
		this.tv4.setOnClickListener(this);
		this.tv5.setOnClickListener(this);
		this.tv6.setOnClickListener(this);
		this.tv7.setOnClickListener(this);
		this.tv8.setOnClickListener(this);
		this.tv9.setOnClickListener(this);
		this.tv_clean.setOnClickListener(this);
		this.relat_del.setOnClickListener(this);
		this.tv_dismiss.setOnClickListener(this);

	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv0:
			numberClick(0);
			break;
		case R.id.tv1:
			numberClick(1);
			break;
		case R.id.tv2:
			numberClick(2);
			break;
		case R.id.tv3:
			numberClick(3);
			break;
		case R.id.tv4:
			numberClick(4);
			break;
		case R.id.tv5:
			numberClick(5);
			break;
		case R.id.tv6:
			numberClick(6);
			break;
		case R.id.tv7:
			numberClick(7);
			break;
		case R.id.tv8:
			numberClick(8);
			break;
		case R.id.tv9:
			numberClick(9);
			break;
		case R.id.tv_clean:
			numberClick(10);
			break;
		case R.id.relat_del:
			numberClick(11);
			break;
		case R.id.tv_dismiss:
			mCallback.onClose();
			break;

		default:
			break;
		}
	}


	/**
	 * 将点击事件传来
	 * @param num
	 */
	public void numberClick(int num){

		if (num == 10) {
			clearnNumber();
			return;
		}

		if( num <= 9){
			mStack.push(num);
		}else if(num == 11){
			deleteNumber();
		}

		refreshNumberViews(mStack);
		//input 6 numbers complete
		if (mStack.size() == NUMBER_COUNT) {
			StringBuilder codeBuilder = new StringBuilder();
			for (int number : mStack) {
				codeBuilder.append(number);
			}
			validation(codeBuilder.toString());
		}

	}

	/**
	 * 验证字符串
	 * @param string
	 */
	private void validation(final String string) {
		progressBar.setVisibility(View.VISIBLE);


		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				clearnNumber();
				progressBar.setVisibility(View.GONE);
				if(string.equals("123456")) {
					Toast.makeText(mContext, R.string.success, Toast.LENGTH_SHORT).show();
					mCallback.onSuccess();
				}else {
					Toast.makeText(mContext, R.string.failure, Toast.LENGTH_SHORT).show();
				}
			}
		}, 1000);










	}


	/**
	 * 刷新输入框显示
	 * @param mNumberStack
	 */
	public void refreshNumberViews(Stack<Integer> mNumberStack) {
		StringBuffer numStr = new StringBuffer();

		for (int i = 0; i < mNumberStack.size(); i++) {
			numStr.append(PASSWORD_NUMBER_SYMBOL);
		}
		tv_payment_pwd.setText(numStr);
	}

	/**
	 * 清空mNumberStack的内容并刷新密码格
	 */
	public void clearnNumber() {
		mStack.clear();
		refreshNumberViews(mStack);
	}

	/**
	 * 删除密码位数
	 */
	public void deleteNumber() {
		if (mStack.empty() || mStack.size() > NUMBER_COUNT) {
			return;
		}
		mStack.pop();
	}

	/**
	 * 返回输出的结果
	 */
	public interface OnInputNumberCodeCallback {
		void onSuccess();
		void onClose();
	}

	public void setInputNumberCodeCallback(OnInputNumberCodeCallback callback) {
		this.mCallback = callback;
	}

	public View getView() {
		return view;
	}


}
