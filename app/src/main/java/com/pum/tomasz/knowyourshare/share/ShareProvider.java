package com.pum.tomasz.knowyourshare.share;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.View;

import com.pum.tomasz.knowyourshare.R;
import com.pum.tomasz.knowyourshare.Utilities;
import com.pum.tomasz.knowyourshare.data.MeasureUnitTypeEnum;
import com.pum.tomasz.knowyourshare.data.Product;
import com.pum.tomasz.knowyourshare.preferences.Preferences;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.pum.tomasz.knowyourshare.Utilities.convertDateToString;

/**
 * Created by tmaslon on 2015-08-10.
 */
public class ShareProvider {

    public static final int CONTACT_PICKER_RESULT = 1001;

    Context context;
    private ShareTypeEnum shareType = ShareTypeEnum.ANY;

    public ShareProvider(Context context) {
        this.context = context;
    }

    public static List<ResolveInfo> getSupportedApps(PackageManager pm){

//        email.putExtra(Intent.EXTRA_EMAIL, new String[]{"testomir@test.com"});
//        email.putExtra(Intent.EXTRA_SUBJECT, "Hi");
//        email.putExtra(Intent.EXTRA_TEXT, "Hi,This is Test");
//        email.setType("text/plain");


        List<ResolveInfo> launchables=pm.queryIntentActivities(createDefaultIntent(), 0);
        Collections.sort(launchables,
                new ResolveInfo.DisplayNameComparator(pm));

        return launchables;

    }

    public void sendMessage(List<Product> products) {
        String message = composeMessage(products);

        SharedPreferences prefs = context.getApplicationContext()
                .getSharedPreferences(Preferences.PREFERENCES_NAME, Context.MODE_WORLD_READABLE);

        switch (shareType){
            case SMS:
                String phoneNumber = prefs.getString(Preferences.KEY_PHONE_NUMBER,"");

                Intent iSms = new Intent(Intent.ACTION_VIEW);
                iSms.putExtra("address", phoneNumber);
                iSms.putExtra("sms_body", message);
                iSms.setData(Uri.fromParts("sms", phoneNumber, null));
                //iSms.setData(Uri.parse("sms:"));
                //iSms.setType("vnd.android-dir/mms-sms");
                context.startActivity(iSms);
                break;
            case ANY:
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,context.getResources().getString(R.string.message_title).toString() + " "+ convertDateToString(new Date()));
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, message);
                context.startActivity(Intent.createChooser(sharingIntent, "Share via"));
                break;

        }


    }

    private static Intent createDefaultIntent(){
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");

        return sharingIntent;
    }

    private String composeMessage(List<Product> products) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(context.getResources().getString(R.string.text_message_begining).toString());
        for (Product p:products){
            stringBuilder.append(p.getName()).append(" ");
            stringBuilder.append(Utilities.DOUBLE_CUT_ZERO_FMT.format(p.getAdjustedSize()));
            if(!p.getMeasureUnitTypeString().equals(MeasureUnitTypeEnum.QUANTITY.name())){
                stringBuilder.append(p.getMeasureUnitString());
            }
            stringBuilder.append(",");
        }
        stringBuilder.deleteCharAt(stringBuilder.length()-1).append(".");

        return stringBuilder.toString();
    }

    public void setShareType(ShareTypeEnum shareType) {
        this.shareType = shareType;
    }

    public void launchContactPicker() {
        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                ContactsContract.Contacts.CONTENT_URI);
        ((Activity)context).startActivityForResult(contactPickerIntent, CONTACT_PICKER_RESULT);
    }
}
