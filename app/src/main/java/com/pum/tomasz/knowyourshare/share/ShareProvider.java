package com.pum.tomasz.knowyourshare.share;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;

import com.pum.tomasz.knowyourshare.R;
import com.pum.tomasz.knowyourshare.Utilities;
import com.pum.tomasz.knowyourshare.data.MeasureUnitTypeEnum;
import com.pum.tomasz.knowyourshare.data.Product;
import com.pum.tomasz.knowyourshare.preferences.Preferences;

import java.util.List;

/**
 * Created by tmaslon on 2015-08-10.
 */
public class ShareProvider {

    Context context;
    private ShareTypeEnum shareType;

    public ShareProvider(Context context) {
        this.context = context;
    }


    public void sendMessage(List<Product> selectedProducts) {
        String message = composeMessage(selectedProducts);

        SharedPreferences prefs = context.getApplicationContext()
                .getSharedPreferences(Preferences.PREFERENCES_NAME, Context.MODE_WORLD_READABLE);

        String phoneNumber = prefs.getString(Preferences.KEY_PHONE_NUMBER,"");

        Intent iSms = new Intent(Intent.ACTION_VIEW);
        iSms.putExtra("address", phoneNumber);
        iSms.putExtra("sms_body", message);
        iSms.setData(Uri.fromParts("sms", phoneNumber, null));
        //iSms.setData(Uri.parse("sms:"));
        //iSms.setType("vnd.android-dir/mms-sms");
        context.startActivity(iSms);

    }

    private String composeMessage(List<Product> selectedProducts) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(context.getResources().getString(R.string.text_message_begining).toString());
        for (Product p:selectedProducts){
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
}
