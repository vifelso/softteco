package by.softteco.hryharenka.testtask.database;

/**
 * Created by grigorenko on 27.03.2018.
 */

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import by.softteco.hryharenka.testtask.R;
import by.softteco.hryharenka.testtask.models.User;

/**
 * Database helper which creates and upgrades the database and provides the DAOs for the app.
 *
 *
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    /************************************************
     * Suggested Copy/Paste code. Everything from here to the done block.
     ************************************************/

    private static final String DATABASE_NAME = "users_info.db";
    private static final int DATABASE_VERSION = 1;

    private Dao<User, Integer> usersDao;
    private Dao<User.Address, Integer> addressDao;
    private Dao<User.Company, Integer> companyDao;
    private Dao<User.Geo, Integer> geoDao;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /************************************************
     * Suggested Copy/Paste Done
     ************************************************/

    @Override
    public void onCreate(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource) {
        try {

            // Create tables. This onCreate() method will be invoked only once of the application life time i.e. the first time when the application starts.
            TableUtils.createTable(connectionSource, User.class);
            TableUtils.createTable(connectionSource, User.Address.class);
            TableUtils.createTable(connectionSource, User.Company.class);
            TableUtils.createTable(connectionSource, User.Geo.class);



        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Unable to create databases", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource, int oldVer, int newVer) {
        try {

            // In case of change in database of next version of application, please increase the value of DATABASE_VERSION variable, then this method will be invoked
            //automatically. Developer needs to handle the upgrade logic here, i.e. create a new table or a new column to an existing table, take the backups of the
            // existing database etc.

            TableUtils.dropTable(connectionSource, User.class, true);
            TableUtils.dropTable(connectionSource, User.Address.class, true);
            TableUtils.dropTable(connectionSource, User.Company.class, true);
            TableUtils.dropTable(connectionSource, User.Geo.class, true);
            onCreate(sqliteDatabase, connectionSource);

        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Unable to upgrade database from version " + oldVer + " to new "
                    + newVer, e);
        }
    }

    // Create the getDao methods of all database tables to access those from android code.
    // Insert, delete, read, update everything will be happened through DAOs

    public Dao<User, Integer> getUsersDao() throws SQLException {
        if (usersDao == null) {
            usersDao = getDao(User.class);
        }
        return usersDao;
    }

    public Dao<User.Address, Integer> getAddressDao() throws SQLException {
        if (addressDao == null) {
            addressDao = getDao(User.Address.class);
        }
        return addressDao;
    }

    public Dao<User.Company, Integer> getCompanyDao() throws SQLException {
        if (companyDao == null) {
            companyDao = getDao(User.Company.class);
        }
        return companyDao;
    }

    public Dao<User.Geo, Integer> getGeoDao() throws SQLException {
        if (geoDao == null) {
            geoDao = getDao(User.Geo.class);
        }
        return geoDao;
    }
    @Override
    public void close() {
        super.close();
        usersDao = null;
    }
    @Override
    public SQLiteDatabase getWritableDatabase() {
        return super.getWritableDatabase();
    }
}