package com.droid.ashref.smartcontrol;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.navigation.NavigationView;
import com.mikepenz.crossfadedrawerlayout.view.CrossfadeDrawerLayout;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.MiniDrawer;
import com.mikepenz.materialdrawer.interfaces.ICrossfader;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.util.DrawerUIUtils;
import com.mikepenz.materialize.util.UIUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static RecyclerView recyclerView;
    private static RecyclerView recyclerViewMood;
    public static RecycelerViewAdapter recycelerViewAdapter;
    public static RecyclerView_Adapter_MOOD recycelerViewAdapterMOOD;
    private static ArrayList<DataList> lists;
    private static ArrayList<DataList_MOOD> listsMOOD;
    private static SwipeRefreshLayout swipeContainer;
    private TextView Internet;
    private static TextView TXT_AllDevices;
    private static TextView TXT_INDevices;
    private static TextView TXTOutDevices;
    private static TextView TXTModeName;
    private static Context context;
    private CircleImageView UserPhoto;
    private Bitmap bitmap;
    private final int IMG_REQUEST = 1;
    public static final String UPLOAD_URL = SaveSettings.Url+"user_Photo.php";
    static String AllDevice;
    static String INDevices;
    static String ModeName;
    //save our header or result
    private AccountHeader headerResult = null;
    private Drawer result = null;
    private CrossfadeDrawerLayout crossfadeDrawerLayout = null;

    ImageView UserImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SaveSettings saveSettings = new SaveSettings(getApplicationContext());
        saveSettings.LoadData();
        context = MainActivity.this;
        Internet = findViewById(R.id.TXT_Internet);
        TXT_AllDevices = findViewById(R.id.N_All_Decices);
        TXT_INDevices = findViewById(R.id.N_IN_Mode);
        TXTOutDevices = findViewById(R.id.N_OUT_Mode);
        TXTModeName = findViewById(R.id.Mood_Name);
        // UserImage = (ImageView) findViewById(R.id.User_IMG);
        swipeContainer = findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                if (Operations.isConnectingToInternet(getApplicationContext())) {
                    Internet.setVisibility(View.GONE);
                    jsonTask jsonTask = new jsonTask();
                    jsonTask.execute();
                } else {
                    Internet.setVisibility(View.VISIBLE);
                    swipeContainer.setRefreshing(false);
                }
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        recyclerViewMood = findViewById(R.id.recycler_MOOD);
        recyclerViewMood.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewMood.setLayoutManager(linearLayoutManager);
        listsMOOD = new ArrayList<DataList_MOOD>();
        recycelerViewAdapterMOOD = new RecyclerView_Adapter_MOOD(listsMOOD, context);
        recyclerViewMood.setAdapter(recycelerViewAdapterMOOD);


        recyclerView = findViewById(R.id.RecyclerViwe);
        recyclerView.setScrollContainer(false);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linear = new LinearLayoutManager(this);
        linear.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linear);
        lists = new ArrayList<DataList>();
        recycelerViewAdapter = new RecycelerViewAdapter(lists, context);
        recyclerView.setAdapter(recycelerViewAdapter);
        recycelerViewAdapter.notifyDataSetChanged();

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, ADD_Device.class);
//                intent.putExtra("Update", "Add");
//                startActivity(intent);
//
//            }
//        });


//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
//
//        NavigationView navigationView = findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
//        View headerview = navigationView.getHeaderView(0);
//        UserPhoto = headerview.findViewById(R.id.img);
//        TextView userName = headerview.findViewById(R.id.userName);
//        TextView userEmail = headerview.findViewById(R.id.userEmail);
//        userName.setText(SaveSettings.UserName);
//        userEmail.setText(SaveSettings.UserEmail);
//        UserPhoto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ChooesPhoto();
//            }
//        });
//        Picasso.get()
//                .load(SaveSettings.UserPhoto)
//                .placeholder(R.drawable.ic_menu_manage)
//                .error(R.drawable.ic_menu_share)
//                .into(UserPhoto);


        // Handle Toolbar
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        //set the back arrow in the toolbar
//        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        // getSupportActionBar().setTitle(R.string.drawer_item_crossfade_drawer_layout_drawer);

        // Create a few sample profile
        // NOTE you have to define the loader logic too. See the CustomApplication for more details


        final IProfile profile = new ProfileDrawerItem().withName(SaveSettings.UserName).withEmail(SaveSettings.UserEmail).withIcon(R.drawable.image);


        // Create the AccountHeader
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .addProfiles(
                        profile
                )
                .withTextColor(getResources().getColor(R.color.black))

                .withSavedInstance(savedInstanceState)
                .build();

        //Create the drawer
        result = new DrawerBuilder()
                .withActivity(this)
                // .withToolbar(toolbar)
                .withHasStableIds(true)
                .withInnerShadow(true)
                .withDrawerLayout(R.layout.crossfade_drawer)
                .withDrawerWidthDp(72)
                .withGenerateMiniDrawer(true)
                .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Room").withIcon(R.drawable.room).withIdentifier(1),
                        new PrimaryDrawerItem().withName("Add Device").withIcon(R.drawable.adddevice).withIdentifier(2).withSelectable(false),
                        new PrimaryDrawerItem().withName("Bill").withIcon(R.drawable.budgit).withIdentifier(3),
                        new PrimaryDrawerItem().withName("Running Appliances").withIcon(R.drawable.rinningdevice).withIdentifier(4),
                        new PrimaryDrawerItem().withName("Subtype").withIcon(R.drawable.profile).withIdentifier(5),

                        //   new PrimaryDrawerItem().withName("Contact"),
                        new PrimaryDrawerItem().withName("Share").withIcon(R.drawable.share).withIdentifier(6),
                        new PrimaryDrawerItem().withName("Exit").withIcon(R.drawable.exite).withTag("Bullhorn").withIdentifier(7)
                ) // add the items we want to use with our Drawer
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                        switch (position) {


                            case 1: {

                                Intent intent = new Intent(MainActivity.this, All_Rooms.class);
                                startActivity(intent);
                                break;
                            }
                            case 2: {

                                Intent intent = new Intent(MainActivity.this, ADD_Device.class);
                                intent.putExtra("Update", "Add");
                                startActivity(intent);
                                break;
                            }
                            case 3: {
                                Intent intent = new Intent(MainActivity.this, Chart.class);

                                startActivity(intent);
                                break;
                            }

                            case 4: {
                                Intent intent = new Intent(MainActivity.this, All_Devices.class);

                                startActivity(intent);
                                break;
                            }

                            case 5: {
                                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                                intent.putExtra("S_Type", "1");

                                startActivity(intent);
                                break;

                            }
                            case 6: {

//                                finish();
//                                System.exit(0);
                                break;
                            }
                            case 7: {
                                finish();
                                System.exit(0);
                                break;
                            }
                        }

                        //we do not consume the event and want the Drawer to continue with the event chain
                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .withShowDrawerOnFirstLaunch(true)
                .build();


        //get the CrossfadeDrawerLayout which will be used as alternative DrawerLayout for the Drawer
        //the CrossfadeDrawerLayout library can be found here: https://github.com/mikepenz/CrossfadeDrawerLayout
        crossfadeDrawerLayout = (CrossfadeDrawerLayout) result.getDrawerLayout();

        //define maxDrawerWidth
        crossfadeDrawerLayout.setMaxWidthPx(DrawerUIUtils.getOptimalDrawerWidth(this));
        //add second view (which is the miniDrawer)
        final MiniDrawer miniResult = result.getMiniDrawer();
        //build the view for the MiniDrawer
        View view = miniResult.build(this);
        //set the background of the MiniDrawer as this would be transparent
        view.setBackgroundColor(UIUtils.getThemeColorFromAttrOrRes(this, com.mikepenz.materialdrawer.R.attr.material_drawer_background, com.mikepenz.materialdrawer.R.color.material_drawer_background));
        //we do not have the MiniDrawer view during CrossfadeDrawerLayout creation so we will add it here
        crossfadeDrawerLayout.getSmallView().addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        //define the crossfader to be used with the miniDrawer. This is required to be able to automatically toggle open / close
        miniResult.withCrossFader(new ICrossfader() {
            @Override
            public void crossfade() {
                boolean isFaded = isCrossfaded();
                crossfadeDrawerLayout.crossfade(400);

                //only close the drawer if we were already faded and want to close it now
                if (isFaded) {
                    result.getDrawerLayout().closeDrawer(GravityCompat.START);
                }
            }

            @Override
            public boolean isCrossfaded() {
                return crossfadeDrawerLayout.isCrossfaded();
            }
        });


    }

    @Override
    public void onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

            Intent intent = new Intent(MainActivity.this, All_Rooms.class);
            startActivity(intent);
            // Handle the camera action
        } else if (id == R.id.nav_camera) {


        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void SeeALL(View view) {
        Intent intent = new Intent(this, All_Devices.class);
        startActivity(intent);
    }

    public void ViewDetals(View view) {
        Intent intent = new Intent(this, Chart.class);
        startActivity(intent);
    }

    public void OpenDrower(View view) {

        result.openDrawer();
    }

    public void AddNewMode(View view) {


        final AlertDialog dialogBuilder = new AlertDialog.Builder(context).create();
        //  LayoutInflater inflater = this.getLayoutInflater();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //LayoutInflater inflater = dialogBuilder.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.add_room_dialog, null);

        final EditText editText = dialogView.findViewById(R.id.txt_add);
        Button button1 = dialogView.findViewById(R.id.btn_Add);
        Button button2 = dialogView.findViewById(R.id.btn_cancel);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialogBuilder.dismiss();
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // DO SOMETHINGS
                RecyclerView_Adapter_MOOD.JsonTask_Add_Mood jsonTask_add_mood = new RecyclerView_Adapter_MOOD.JsonTask_Add_Mood();
                jsonTask_add_mood.execute(editText.getText().toString(), SaveSettings.UserID);
//                All_Rooms.JsonTask_Add_Room jsonTask_add_room = new All_Rooms.JsonTask_Add_Room();
//                jsonTask_add_room.execute(editText.getText().toString(), SaveSettings.UserID);
                dialogBuilder.dismiss();
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();


    }

    // Load Device
    public static class jsonTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            lists.clear();
         //   recycelerViewAdapter.clear();
        }

        URL url;
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream;
        BufferedReader bufferedReader;
        StringBuffer stringBuffer = new StringBuffer();

        @Override
        protected String doInBackground(String... strings) {
            try {
                url = new URL(SaveSettings.Url+"read_all.php?User_ID=" + SaveSettings.UserID);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();
                inputStream = httpURLConnection.getInputStream();

                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = " ";

                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line);

                }

                String fullfile = stringBuffer.toString();

                /*JSONObject jsonObject = new JSONObject(fullfile);
                JSONObject jsonObjectchild = jsonObject.getJSONObject("Device");*/

                JSONArray jsonArray = new JSONArray(fullfile);


                for (int i = 0; i <= jsonArray.length(); i++) {


                    JSONObject child = jsonArray.getJSONObject(i);
                    String ID = child.getString("ID");
                    String Name = child.getString("D_Name");
                    String Status = child.getString("D_status");
                    String Power = child.getString("D_Power");
                    String Port = child.getString("D_Port");
                    String Hours = child.getString("D_Hours");
                    String Photo = child.getString("D_Photo");

                    lists.add(new DataList(ID, Name, Status, Power, Port, Hours, Photo));

                }

                //   lists.add(new DataList("ashref","ashref","a23","11"));


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            //  RecycelerViewAdapter recycelerViewAdapter = new RecycelerViewAdapter(lists, context);
            recycelerViewAdapter.notifyDataSetChanged();
            //  recyclerView.setAdapter(recycelerViewAdapter);

            swipeContainer.setRefreshing(false);

        }
    }

    // Load Mood
    public static class jsonTask_Mood extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            listsMOOD.clear();
           // recycelerViewAdapterMOOD.clear();
        }

        URL url;
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream;
        BufferedReader bufferedReader;
        StringBuffer stringBuffer = new StringBuffer();

        @Override
        protected String doInBackground(String... strings) {
            try {
                url = new URL(SaveSettings.Url+"selectMood.php?User_ID=" + SaveSettings.UserID);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();
                inputStream = httpURLConnection.getInputStream();

                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = " ";

                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line);

                }

                String fullfile = stringBuffer.toString();

                /*JSONObject jsonObject = new JSONObject(fullfile);
                JSONObject jsonObjectchild = jsonObject.getJSONObject("Device");*/

                JSONArray jsonArray = new JSONArray(fullfile);


                for (int i = 0; i <= jsonArray.length(); i++) {


                    JSONObject child = jsonArray.getJSONObject(i);
                    String M_ID = child.getString("Mood_ID");
                    String M_Name = child.getString("M_Name");


                    listsMOOD.add(new DataList_MOOD(M_ID, M_Name));

                }

                //   lists.add(new DataList("ashref","ashref","a23","11"));


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

          //  RecyclerView_Adapter_MOOD recycelerViewAdapterMOOD = new RecyclerView_Adapter_MOOD(listsMOOD, context);
         //   recyclerViewMood.setAdapter(recycelerViewAdapterMOOD);
            recycelerViewAdapterMOOD.notifyDataSetChanged();
            swipeContainer.setRefreshing(false);

          //  listsMOOD.size();

        }
    }


    public static class jsonTask_Mood_Info extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            listsMOOD.clear();
            recycelerViewAdapterMOOD.clear();
        }

        URL url;
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream;
        BufferedReader bufferedReader;
        StringBuffer stringBuffer = new StringBuffer();

        @Override
        protected String doInBackground(String... strings) {
            try {
                url = new URL(SaveSettings.Url+"get_Mood_Info.php?User_ID=" + SaveSettings.UserID);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();
                inputStream = httpURLConnection.getInputStream();

                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = " ";

                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line);

                }

                String fullfile = stringBuffer.toString();

                /*JSONObject jsonObject = new JSONObject(fullfile);
                JSONObject jsonObjectchild = jsonObject.getJSONObject("Device");*/

                JSONArray jsonArray = new JSONArray(fullfile);


                for (int i = 0; i <= jsonArray.length(); i++) {


                    JSONObject child = jsonArray.getJSONObject(i);
                    AllDevice = child.getString("AllDevices");
                    INDevices = child.getString("MoodDevice");
                    ModeName = child.getString("ModeName");


                    //   listsMOOD.add(new DataList_MOOD(M_ID, M_Name));

                }

                //   lists.add(new DataList("ashref","ashref","a23","11"));


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
//            TXT_AllDevices.setText(AllDevice);
//            TXT_INDevices.setText(INDevices);
//            TXTModeName.setText(ModeName + " Mode ON ");

            TXT_AllDevices.setText("10");
            TXT_INDevices.setText("5");
            TXTModeName.setText( "Day Mode ON ");
//            int a = Integer.parseInt(TXT_AllDevices.getText().toString()) - Integer.parseInt(TXT_INDevices.getText().toString());
       //     TXTOutDevices.setText("" + a);


            SharedPreferences pref = context.getSharedPreferences("MyPref", 0); // 0 - for private mode
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("ModeName", ModeName);
            editor.apply();


        }
    }


    // Refresh data
    @Override
    protected void onResume() {
        super.onResume();
        if (Operations.isConnectingToInternet(getApplicationContext())) {
            Internet.setVisibility(View.GONE);

            jsonTask jsonTask = new jsonTask();
            jsonTask.execute();

            jsonTask_Mood jsonTask_Mood = new jsonTask_Mood();
            jsonTask_Mood.execute();


            jsonTask_Mood_Info jsonTaskMoodInfo = new jsonTask_Mood_Info();
            jsonTaskMoodInfo.execute();

        } else {
            swipeContainer.setRefreshing(false);
            Internet.setVisibility(View.VISIBLE);

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK
                && null != data) {
            Uri filePath = data.getData();
            try {

                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                UserPhoto.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }

            uploadImage();
        }
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = android.util.Base64.encodeToString(imageBytes, android.util.Base64.DEFAULT);
        return encodedImage;
    }

    //Upload User Image
    private void uploadImage() {
        class UploadImage extends AsyncTask<Bitmap, Void, String> {
            ProgressDialog loading;
            HttpRequest rh = new HttpRequest();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this, "Uploading Image", "Please wait...", true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Bitmap... params) {
                Bitmap bitmap = params[0];
                String uploadImage = getStringImage(bitmap);

                HashMap<String, String> data = new HashMap<>();
                data.put("User_ID", SaveSettings.UserID);
                data.put("Photo", uploadImage);
//                data.put(UPLOAD_KEY, uploadImage);
//                data.put("name",getFileName(filePath));

                String result = rh.postRequest(UPLOAD_URL, data);
                return result;
            }
        }

        UploadImage ui = new UploadImage();
        ui.execute(bitmap);
    }

    // Picture Gallery
    private void ChooesPhoto() {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, IMG_REQUEST);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //add the values which need to be saved from the drawer to the bundle
        outState = result.saveInstanceState(outState);
        //add the values which need to be saved from the accountHeader to the bundle
        outState = headerResult.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

//    @Override
//    public void onBackPressed() {
//        //handle the back press :D close the drawer first and if the drawer is closed close the activity
//        if (result != null && result.isDrawerOpen()) {
//            result.closeDrawer();
//        } else {
//            super.onBackPressed();
//        }
//    }


    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
