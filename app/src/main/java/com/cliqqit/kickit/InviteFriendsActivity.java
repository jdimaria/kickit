package com.cliqqit.kickit;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;

import im.delight.android.ddp.Meteor;
import im.delight.android.ddp.MeteorCallback;

/**
 * Created by jdimaria on 2/21/15.
 */
public class InviteFriendsActivity extends ActionBarActivity implements MeteorCallback {
//    protected RelativeLayout recentPics;
//    protected RelativeLayout friendPics;
    protected Meteor thisMeteor;
    protected ImageView recentPic1;
    protected ImageView recentPic2;
    protected ImageView recentPic3;
    protected ImageView recentPic4;
    protected ImageView friendPic1;
    protected ImageView friendPic2;
    protected TextView friendName1;
    protected TextView friendName2;
    protected HashMap<Integer, Boolean> picHash = new HashMap<>();
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_friends);
        context = getApplicationContext();
//        recentPics = (RelativeLayout)findViewById(R.id.recent_list);
//        friendPics = (RelativeLayout)findViewById(R.id.friends_list);
        thisMeteor = MainActivity.mMeteor;

//        Calendar calendar = Calendar.getInstance();
//        int calendarDate = calendar.get(calendar.DATE);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(R.layout.tool_bar);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME, ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Invite Friends");
//        actionBar.setSubtitle(Integer.toString(calendarDate));

        recentPic1 = (ImageView)findViewById(R.id.recent_pic_1);
        recentPic2 = (ImageView)findViewById(R.id.recent_pic_2);
        recentPic3 = (ImageView)findViewById(R.id.recent_pic_3);
        recentPic4 = (ImageView)findViewById(R.id.recent_pic_4);
        friendPic1 = (ImageView)findViewById(R.id.friend_pic_1);
        friendPic2 = (ImageView)findViewById(R.id.friend_pic_2);

        Bitmap recent1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.profile_buff);
        Bitmap recent2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.profile_chris);
        Bitmap recent3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.profile_dylan);
        Bitmap recent4 = BitmapFactory.decodeResource(context.getResources(), R.drawable.profile_james);
        Bitmap friend1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.profile_mangold);
//        Bitmap friend2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.profile_chris);

        recentPic1.setBackground(getResizedDrawable(recent1, 250, 250));
        recentPic2.setBackground(getResizedDrawable(recent2, 250, 250));
        recentPic3.setBackground(getResizedDrawable(recent3, 250, 250));
        recentPic4.setBackground(getResizedDrawable(recent4, 250, 250));
        friendPic1.setBackground(getResizedDrawable(friend1, 250 ,250));
        friendPic2.setBackground(getResizedDrawable(recent2, 250, 250));

        ImageButton actionButton = (ImageButton) findViewById(R.id.action_button);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, SchedulerActivity.class);
                startActivity(intent);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public Drawable getResizedDrawable(Bitmap bm, int newHeight, int newWidth) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);
        // RECREATE THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height,
                matrix, false);

        Bitmap circleBitmap = Bitmap.createBitmap(resizedBitmap.getWidth(), resizedBitmap.getHeight(), Bitmap.Config.ARGB_8888);

        BitmapShader shader = new BitmapShader (resizedBitmap,  Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Paint paint = new Paint();
        paint.setShader(shader);
        paint.setAntiAlias(true);
        Canvas c = new Canvas(circleBitmap);
        c.drawCircle(resizedBitmap.getWidth() / 2, resizedBitmap.getHeight() / 2, resizedBitmap.getWidth() / 2, paint);

        Drawable resizedDrawable = new BitmapDrawable(getResources(), circleBitmap);
        return resizedDrawable;
    }

    public void imageSelectToggle(View v) {
        Log.d("InviteFriendsActivity", "view id: " + v.getId());
        Log.d("InviteFriendsActivity", "recentPic1 id: " + recentPic1.getId());
        Integer viewId = v.getId();
        if (picHash.containsKey(viewId)) {
            Log.d("InviteFriendsActivity", "Getting key!");
            Boolean picSelected = picHash.get(viewId);
            if(picSelected) {
                Log.d("InviteFriendsActivity", "Pic previously selected!");
                // Picture already selected, deselect and set false
                if(viewId == recentPic1.getId()) {
                    recentPic1.clearColorFilter();
                } else if (viewId == recentPic2.getId()) {
                    recentPic2.clearColorFilter();
                } else if (viewId == recentPic3.getId()) {
                    recentPic3.clearColorFilter();
                } else if (viewId == recentPic4.getId()) {
                    recentPic4.clearColorFilter();
                } else if (viewId == friendPic1.getId()) {
                    friendPic1.clearColorFilter();
                } else if (viewId == friendPic2.getId()) {
                    friendPic2.clearColorFilter();
                }
                picSelected = false;
                picHash.remove(viewId);
                picHash.put(viewId, picSelected);
            } else {
                Log.d("InviteFriendsActivity", "Pic previously unselected!");
                // Picture not selected, select and set true
                ColorFilter filter = new PorterDuffColorFilter(
                        0xff00ff00, PorterDuff.Mode.MULTIPLY);
                if(viewId == recentPic1.getId()) {
                    recentPic1.setColorFilter(filter);
                } else if (viewId == recentPic2.getId()) {
                    recentPic2.setColorFilter(filter);
                } else if (viewId == recentPic3.getId()) {
                    recentPic3.setColorFilter(filter);
                } else if (viewId == recentPic4.getId()) {
                    recentPic4.setColorFilter(filter);
                } else if (viewId == friendPic1.getId()) {
                    friendPic1.setColorFilter(filter);
                } else if (viewId == friendPic2.getId()) {
                    friendPic2.setColorFilter(filter);
                }
                picSelected = true;
                picHash.remove(viewId);
                picHash.put(viewId, picSelected);
            }
        } else {
            Log.d("InviteFriendsActivity", "No key!");
            // Picture selected for first time
            ColorFilter filter = new PorterDuffColorFilter(
                    0xff00ff00, PorterDuff.Mode.MULTIPLY);
            if(viewId == recentPic1.getId()) {
                recentPic1.setColorFilter(filter);
            } else if (viewId == recentPic2.getId()) {
                recentPic2.setColorFilter(filter);
            } else if (viewId == recentPic3.getId()) {
                recentPic3.setColorFilter(filter);
            } else if (viewId == recentPic4.getId()) {
                recentPic4.setColorFilter(filter);
            } else if (viewId == friendPic1.getId()) {
                friendPic1.setColorFilter(filter);
            } else if (viewId == friendPic2.getId()) {
                friendPic2.setColorFilter(filter);
            }
            Boolean picSelected = true;
            picHash.put(viewId, picSelected);
        }
    }

    @Override
    public void onConnect() {

    }

    @Override
    public void onDisconnect(int i, String s) {

    }

    @Override
    public void onDataAdded(String s, String s2, String s3) {

    }

    @Override
    public void onDataChanged(String s, String s2, String s3, String s4) {

    }

    @Override
    public void onDataRemoved(String s, String s2) {

    }

    @Override
    public void onException(Exception e) {

    }
}
