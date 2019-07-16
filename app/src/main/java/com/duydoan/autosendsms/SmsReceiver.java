package com.duydoan.autosendsms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

import java.util.List;

public class SmsReceiver extends BroadcastReceiver {

    private Bundle bundle;
    private SmsMessage currentSMS;
    private String message;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            bundle = intent.getExtras();
            if (bundle != null) {
                Object[] pdus = (Object[])bundle.get("pdus");
                final SmsMessage[] messages = new SmsMessage[pdus.length];
                for (int i = 0; i < pdus.length; i++) {
                    String format = bundle.getString("format");
                    messages[i] = SmsMessage.createFromPdu((byte[])pdus[i],format);
                    currentSMS = messages[i];
                }
                StringBuffer content = new StringBuffer();
                if (messages.length > 0) {
                    for (int i = 0; i < messages.length; i++) {
                        content.append(messages[i].getMessageBody());
                    }
                }
                message = content.toString();
                Database database = Database.getInstance(context);
                new SmsAsynctask(message,database).execute();
            }
        }
    }
    private class SmsAsynctask extends AsyncTask<Void,Void,Void>{

        private String code;
        private Database database;

        public SmsAsynctask(String code, Database database){
            this.code = code;
            this.database = database;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            List<ContentData> contentDataList = database.contentDataDao().findItem(code);
            if (contentDataList!=null){
                for (ContentData i: contentDataList){
                    SmsManager.getDefault().sendTextMessage(i.getNumber(),null,i.getText()
                    ,null,null);
                }
            }
            return null;
        }
    }
}
