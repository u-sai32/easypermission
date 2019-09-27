package com.gamermafia.Easypermissions;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gamerMafia.easypermission.EasyPermission;
import com.gamerMafia.easypermission.listener.PermissionStatusListener;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView statusView;
    private Button payButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize Components
        imageView = findViewById(R.id.imageView);
        statusView = findViewById(R.id.textView_status);
        payButton = findViewById(R.id.button_permission);

        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new EasyPermission.Builder()
                        .with(MainActivity.this)
                        .addPermissions(READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE, CAMERA)
                        .setPermissionCheckListener(new PermissionStatusListener() {
                            @Override
                            public void onPermissionSuccess() {
                                Toast.makeText(MainActivity.this, "Success permission granted", Toast.LENGTH_SHORT).show();
                                imageView.setImageResource(R.drawable.ic_success);
                            }

                            @Override
                            public void onPermissionCancel() {
                                Toast.makeText(MainActivity.this, "Cancelled permission canceled", Toast.LENGTH_SHORT).show();
                                imageView.setImageResource(R.drawable.ic_failed);
                            }
                        })
                        .startPermission()
                        .build();
            }
        });
    }
}
