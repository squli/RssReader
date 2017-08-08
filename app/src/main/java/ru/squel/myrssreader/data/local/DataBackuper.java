package ru.squel.myrssreader.data.local;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by sq on 03.07.2017. Для сохранения базы
 */
public class DataBackuper {

    private static final String LOG_TAG = "DataBackuper";
    Context ctx;
    private File DataBaseBackUp = null;

    public DataBackuper(Context c) {
        ctx = c;
    }

    /* Checks if external storage is available for read and write */
    private boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    private boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    private boolean isExternalStorageHasFreeSpace(File f, long size){
        if (f.getFreeSpace() > size)
            return true;
        else
            return false;
    }

    private File getAlbumStorageDir(String backupName) {
        File folder = new File (Environment.getExternalStorageDirectory() + "/db_backup");
        folder.mkdirs();

        File file = new File(folder, backupName);

        Log.e(LOG_TAG, "Path to file: " + file.getPath());
        return file;
    }

    public boolean createDbCopyOnExternalStorage (String backupName) {
        DataBaseHelper dataBaseHelper = DataBaseHelper.getInstance(ctx);

        String path = "/data/data/ru.squel.myrssreader/databases/" + DataBaseHelper.getDBName();
        File dataBaseFile = new File(path);
        if (isExternalStorageWritable() == false)
            return false;

        DataBaseBackUp = getAlbumStorageDir(backupName);
        FileInputStream  fis = null;
        BufferedWriter fos = null;

        try
        {
            fis = new FileInputStream(dataBaseFile);
            fos = new BufferedWriter(new FileWriter(DataBaseBackUp));

            while(true)
            {
                int i = fis.read();
                if(i != -1)
                    fos.write(i);
                else
                    break;
            }
            fos.flush();
            Toast.makeText(ctx, "DB backup OK", Toast.LENGTH_LONG).show();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            Toast.makeText(ctx, "DB backup ERROR", Toast.LENGTH_LONG).show();
        }
        finally
        {
            try
            {
                fis.close();
                fos.close();
            }
            catch(IOException ioe)
            {
                Log.d(LOG_TAG, "createDbCopyOnExternalStorage()" + ioe.toString());
            }
        }
        return true;
    }
}
