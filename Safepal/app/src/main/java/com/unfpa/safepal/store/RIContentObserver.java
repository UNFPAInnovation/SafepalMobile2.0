package com.unfpa.safepal.store;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;

/**
 * Created by Kisa on 11/14/2016.
 */

public class RIContentObserver extends ContentObserver {

    Context context;
    Handler handler;

    public RIContentObserver(Context c, Handler handler) {
        super(handler);
        // TODO Auto-generated constructor stub
        this.context = c;
        this.handler = handler;
    }
    @Override
    public void onChange(boolean selfChange) {
        // TODO Auto-generated method stub
        super.onChange(selfChange);


        Cursor cursor =  context.getContentResolver().query(
                ReportIncidentContentProvider.CONTENT_URI,
                null,
                null,
                null,
                null);
        if(cursor != null) {
            StringBuilder builder = new StringBuilder();
            cursor.moveToLast();
                builder.append("Your SafePal Number is: " + cursor.getString(cursor.getColumnIndex(ReportIncidentTable.COLUMN_UNIQUE_IDENTIFIER)));

            cursor.close();
            String builder2 = builder.toString();
            handler.obtainMessage(1, builder2).sendToTarget();



        }




    }
}
