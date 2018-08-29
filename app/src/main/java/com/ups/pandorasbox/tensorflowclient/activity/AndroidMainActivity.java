package com.ups.pandorasbox.tensorflowclient.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
//import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ups.pandorasbox.tensorflowclient.R;
import com.ups.pandorasbox.tensorflowclient.nonactivity.ServerRequest;
import com.ups.pandorasbox.tensorflowclient.nonactivity.Callback.VolleyCallback;

import java.util.Hashtable;

import static com.ups.pandorasbox.tensorflowclient.nonactivity.Utility.decodeImageFromBase64;
import static com.ups.pandorasbox.tensorflowclient.nonactivity.Utility.encodeImageToBase64;

public class AndroidMainActivity extends Activity {

    private TextView mTextMessage;
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_main);

        this.imageView = (ImageView)this.findViewById(R.id.imageView1);
        Button photoButton = (Button) this.findViewById(R.id.button1);
        photoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });
        //TestMethodActivity.testJsonSimple();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ImageView imageView1 = (ImageView)findViewById(R.id.imageView1);
        imageView1.setBackgroundColor(Color.rgb(220, 187, 39));
        TextView editText2 = (TextView)findViewById(R.id.editText2);
        editText2.setText("");
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);

            ServerRequest sr = new ServerRequest();

            //String imagePath = "C:\\Doge.jpeg";
            //String imagePath = "/storage/emulated/0/DCIM/rj52e14i5t3z.jpg";
            //String imagePath = Environment.getExternalStorageDirectory().getAbsolutePath() +
            //        "/DCIM/rj52e14i5t3z.jpg";
            //String imageStringFromPath = encodeImageToBase64(imagePath, this);
            //decodeImageFromBase64(imageStringFromPath, imagePath + ".jpg", this);
            //System.out.println("Image should have been saved to disk.");
            //System.out.println("IMAGE STRING FROM PATH: ");
            //System.out.println(imageStringFromPath);
            String imageString = encodeImageToBase64(photo, this);
            //System.out.println("IMAGE STRING: ");
            //System.out.println(imageString);

            sr.postImage(imageString, new VolleyCallback()
            {
                @Override
                public void onSuccess(String result)
                {
                    TextView editText2 = (TextView)findViewById(R.id.editText2);
                    ImageView imageView1 = (ImageView)findViewById(R.id.imageView1);
                    System.out.println("SERVER RESPONSE: " + result);
                    String[] answer = result.split(";");
                    String[] answer2 = answer[0].split(",");

                    if(answer2[0].equals("damaged box")){
                        imageView1.setBackgroundColor(Color.RED);
                            answer2[0]="Damaged Box";}
                    if(answer2[0].equals("good box")){
                        imageView1.setBackgroundColor(Color.GREEN);
                        answer2[0]="Good Box";}
                    if(answer2[0].equals("open box")){
                        imageView1.setBackgroundColor(Color.rgb(214, 117, 27));
                        answer2[0]="Open Box";}

                    float ratio = Float.valueOf(answer2[1]);
                    int nSigfig =4;
                    double percentage = Math.round(ratio * Math.pow(10.0, nSigfig)) * 100 / Math.pow(10.0, nSigfig);





                    editText2.setText(answer2[0]+ ": " + percentage + "%");
                    /*
                    Hashtable<String,String> ht = new Hashtable<String, String>();

                    for(String s : answer)
                    {
                        String[] kvp = s.split(",");
                        if(kvp[0].contains("damaged box")) {
                            ht.put("damaged", kvp[1]);
                        }
                        else if(kvp[0].contains("good box")) {
                            ht.put("good", kvp[1]);
                        }
                        else if(kvp[0].contains("open box")) {
                            ht.put("open", kvp[1]);
                        }
                    }

                    if(ht.containsKey("good"))
                    {
                        editText2.setText("good condition " + ht.get("good"));
                    }

                    else if(ht.containsKey("damaged"))
                    {
                        editText2.setText("damaged condition " +ht.get("damaged"));
                    }


                    else if(ht.containsKey("open"))
                    {
                        editText2.setText("open condition " +ht.get("open"));
                    }
                    */
                    // Kevin needs to do something with the above result variable
                }
            });

        }
    }

}
