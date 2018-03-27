package by.softteco.hryharenka.testtask.views;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import by.softteco.hryharenka.testtask.R;
import by.softteco.hryharenka.testtask.TestApp;
import by.softteco.hryharenka.testtask.database.DatabaseHelper;
import by.softteco.hryharenka.testtask.models.User;

/**
 * Created by Andrei on 19.03.2018.
 */

public class SecondActivity extends AppCompatActivity {
    // Reference of DatabaseHelper class to access its DAOs and other components
    private DatabaseHelper databaseHelper = null;
    public static final String PREFIX_CONTACT = "contact #";
    @BindView(R.id.postIdInfo)
    TextView postIdInfo;
    @BindView(R.id.userName)
    TextView userName;
    @BindView (R.id.userNickname)
    TextView userNickname;
    @BindView (R.id.email)
    TextView email;
    @BindView (R.id.website)
    TextView website;
    @BindView (R.id.phone)
    TextView phone;
    @BindView (R.id.city)
    TextView city;

    User user;
    int postId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);
        ButterKnife.bind(this);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
          user  = (User) extras.get("user");
          postId = (int) extras.get("postId");
        }

        if(user != null){
            setTitle(PREFIX_CONTACT + String.valueOf(user.getId()));
            postIdInfo.setText(String.valueOf(postId));
            userName.setText(user.getName());
            userNickname.setText(user.getUsername());
            email.setText(user.getEmail());
            website.setText(user.getWebsite());
            phone.setText(user.getPhone());
            city.setText(user.getAddress().getCity());
        }

        databaseHelper = new DatabaseHelper(getApplication().getApplicationContext());
        databaseHelper.getWritableDatabase();
        System.out.println("second activity");
    }

    @OnClick(R.id.email)
    public void openEmail() {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", email.getText().toString(), null));
        startActivity(Intent.createChooser(intent, "Choose an Email client :"));
    }


    @OnClick(R.id.website)
    public void openWebsite() {
        String url = website.getText().toString();
        if (!url.startsWith("http://") && !url.startsWith("https://"))
            url = "http://" + url;
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }


    @OnClick(R.id.phone)
    public void openPhone(){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phone.getText().toString()));
        startActivity(intent);
    }


    @OnClick(R.id.city)
    public void openLocation(){
        String geoUri = "http://maps.google.com/maps?q=loc:" + user.getAddress().getGeo().getLat() + "," + user.getAddress().getGeo().getLng();
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,  Uri.parse(geoUri));
        startActivity(intent);
    }


    @OnClick(R.id.userSave)
    public void userSave(){

        try {
            getHelper().getUsersDao().create(user);
            getHelper().getCompanyDao().create(user.getCompany());
            getHelper().getAddressDao().create(user.getAddress());
            getHelper().getGeoDao().create(user.getAddress().getGeo());
            //checking
            /*getHelper().getUsersDao().queryForAll();
            getHelper().getCompanyDao().queryForAll();
            getHelper().getAddressDao().queryForAll();
            getHelper().getGeoDao().queryForAll();*/
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this,DatabaseHelper.class);
        }
        return databaseHelper;
    }
}
