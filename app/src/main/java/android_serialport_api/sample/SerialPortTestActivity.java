package android_serialport_api.sample;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

/**
 * 描述：
 * 作者：dc on 2016/8/31 11:05
 * 邮箱：597210600@qq.com
 */
public class SerialPortTestActivity extends SerialPortActivity {
    private static final String TAG = SerialPortTestActivity.class.getSimpleName();

    private EditText getSerialPortContentEt = null;
    private EditText inputSerialPortContentEt = null;
    private Button sendSerialPortContentBtn = null;
    private Button cleanSerialPortContentBtn = null;
    private String result = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.serialport_test);

        getSerialPortContentEt = (EditText) findViewById(R.id.serial_port_test_get_content_et);
        inputSerialPortContentEt = (EditText) findViewById(R.id.serial_port_test_input_content_et);
        sendSerialPortContentBtn = (Button) findViewById(R.id.serial_port_test_input_content_send_btn);
        cleanSerialPortContentBtn = (Button) findViewById(R.id.serial_port_test_input_content_clean_btn);

        cleanSerialPortContentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSerialPortContentEt.setText("");
            }
        });

        sendSerialPortContentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputContentStr = inputSerialPortContentEt.getText().toString();
                if(inputContentStr.equals("")){
                    Toast.makeText(SerialPortTestActivity.this,"发送内容不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                int i;

                char[] text = new char[inputContentStr.length()];
                for (i=0; i<inputContentStr.length(); i++) {
                    text[i] = inputContentStr.charAt(i);
                }
                try {
                    mOutputStream.write(new String(text).getBytes());
                    mOutputStream.write('\n');
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onDataReceived(final byte[] buffer, final int size) {
        runOnUiThread(new Runnable() {
            public void run() {
                if (getSerialPortContentEt != null) {
                    result = new String(buffer, 0, size);
//                    result = result + result;
//                    Log.e(TAG, "result=" + result);
                    getSerialPortContentEt.append(result);//new String(buffer, 0, size));
                    Log.e(TAG,"result="+getSerialPortContentEt.getText().toString());
                }
            }
        });
    }
}
