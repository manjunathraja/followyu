package com.followyu.utils;

import java.util.List;
import java.util.concurrent.RejectedExecutionException;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Loader;
import android.content.Loader.OnLoadCompleteListener;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Profile;
import android.util.Log;
import android.widget.Toast;

import com.followyu.Config;
import com.followyu.R;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

public class PhoneHelper {

    public static void loadPhoneContacts(Context context, final List<Bundle> phoneContacts, final OnPhoneContactsLoadedListener listener) {
        ContentResolver cr = context.getContentResolver();
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String id = cursor.getString(
                        cursor.getColumnIndex(ContactsContract.Contacts._ID));
                if (Integer.parseInt
                        (cursor.getString
                                (cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        if (null != pCur) {
                            Bundle contact = new Bundle();
                            contact.putInt("phoneid",
                                    cursor.getInt
                                            (cursor.getColumnIndex(ContactsContract.Data._ID)));
                            contact.putString("displayname",
                                    cursor.getString
                                            (cursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME)));
                            contact.putString("photouri",
                                    cursor.getString
                                            (cursor.getColumnIndex(ContactsContract.Data.PHOTO_URI)));
                            contact.putString("lookup",
                                    cursor.getString
                                            (cursor.getColumnIndex(ContactsContract.Data.LOOKUP_KEY)));

                            contact.putString("phonenumber", cursor.getString(
                                pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            phoneContacts.add(contact);
                        }
                    }
                    pCur.close();
                }
            }
        }

        if (listener != null) {
            listener.onPhoneContactsLoaded(phoneContacts);
        }
    }

	public static Uri getSefliUri(Context context) {
		String[] mProjection = new String[] { Profile._ID, Profile.PHOTO_URI };
		Cursor mProfileCursor = context.getContentResolver().query(
				Profile.CONTENT_URI, mProjection, null, null, null);

		if (mProfileCursor == null || mProfileCursor.getCount() == 0) {
			return null;
		} else {
			mProfileCursor.moveToFirst();
			String uri = mProfileCursor.getString(1);
			mProfileCursor.close();
			if (uri == null) {
				return null;
			} else {
				return Uri.parse(uri);
			}
		}
	}

	public static String getVersionName(Context context) {
		final String packageName = context == null ? null : context.getPackageName();
		if (packageName != null) {
			try {
				return context.getPackageManager().getPackageInfo(packageName, 0).versionName;
			} catch (final PackageManager.NameNotFoundException e) {
				return "unknown";
			}
		} else {
			return "unknown";
		}
	}
}
