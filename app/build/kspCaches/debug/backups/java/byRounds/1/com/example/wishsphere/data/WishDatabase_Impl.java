package com.example.wishsphere.data;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class WishDatabase_Impl extends WishDatabase {
  private volatile WishDao _wishDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(5) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `wish_table` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `wish_title` TEXT NOT NULL, `wish_description` TEXT NOT NULL, `wish_is_completed` INTEGER NOT NULL, `wish_category` TEXT NOT NULL, `wish_priority` TEXT NOT NULL, `wish_image_uri` TEXT, `wish_notes` TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'be9a2558eb34c814ef01cecd02570600')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `wish_table`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsWishTable = new HashMap<String, TableInfo.Column>(8);
        _columnsWishTable.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWishTable.put("wish_title", new TableInfo.Column("wish_title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWishTable.put("wish_description", new TableInfo.Column("wish_description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWishTable.put("wish_is_completed", new TableInfo.Column("wish_is_completed", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWishTable.put("wish_category", new TableInfo.Column("wish_category", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWishTable.put("wish_priority", new TableInfo.Column("wish_priority", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWishTable.put("wish_image_uri", new TableInfo.Column("wish_image_uri", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWishTable.put("wish_notes", new TableInfo.Column("wish_notes", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysWishTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesWishTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoWishTable = new TableInfo("wish_table", _columnsWishTable, _foreignKeysWishTable, _indicesWishTable);
        final TableInfo _existingWishTable = TableInfo.read(db, "wish_table");
        if (!_infoWishTable.equals(_existingWishTable)) {
          return new RoomOpenHelper.ValidationResult(false, "wish_table(com.example.wishsphere.data.Wish).\n"
                  + " Expected:\n" + _infoWishTable + "\n"
                  + " Found:\n" + _existingWishTable);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "be9a2558eb34c814ef01cecd02570600", "d3b9ef8dbcd1d5fafce8490735b986fc");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "wish_table");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `wish_table`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(WishDao.class, WishDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public WishDao wishDao() {
    if (_wishDao != null) {
      return _wishDao;
    } else {
      synchronized(this) {
        if(_wishDao == null) {
          _wishDao = new WishDao_Impl(this);
        }
        return _wishDao;
      }
    }
  }
}
