package example.hs.haihai.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import example.hs.haihai.bean.Contacts;

public class ContactUtils {

    public static List<Contacts> getContactsBySort(Context context) {
        List<Contacts> list = getAllContact(context);
        //获取拼音
        for (Contacts contacts : list) {

            contacts.setPinyin(PinYinUtils.getPinyin(contacts.getName()));
            contacts.setLetter(contacts.getPinyin().substring(0,1));
        }

        //排序
        Collections.sort(list, new Comparator<Contacts>() {
            @Override
            public int compare(Contacts contacts, Contacts t1) {
                return contacts.getPinyin().compareTo(t1.getPinyin());
            }
        });

        //测试打印
        for (Contacts contacts : list) {
            Log.e("hao","----------------->"+contacts.getName());
        }
        return list;
    }

    public static List<Contacts> getAllContact(Context context) {
        //获取联系人信息的Uri
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        //获取ContentResolver
        ContentResolver contentResolver = context.getContentResolver();
        //查询数据，返回Cursor
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        //只封装了电话和姓名
        List<Contacts> contacts = new ArrayList<>();

        while (cursor.moveToNext()) {
            Map<String, Object> map = new HashMap<String, Object>();
            StringBuilder sb = new StringBuilder();
            //获取联系人的ID
            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            //获取联系人的姓名
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            //构造联系人信息
            sb.append("contactId=").append(contactId).append(",Name=").append(name);
            map.put("name", name);

            //查询电话号码、类型的数据操作
            Cursor phones = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
                    null, null);
            while (phones.moveToNext()) {
                String phoneNumber = phones.getString(phones.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.NUMBER));
                //添加Phone的信息
                sb.append(",Phone=").append(phoneNumber);
                map.put("mobile", phoneNumber);

                //一个姓名下有多个电话，分开展示
                contacts.add(new Contacts(contactId, name, phoneNumber));
            }
            phones.close();


            //查询Email类型的数据操作
            /**
             Cursor emails = contentResolver.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,
             null,
             ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = " + contactId,
             null, null);
             while (emails.moveToNext())
             {
             String emailAddress = emails.getString(emails.getColumnIndex(
             ContactsContract.CommonDataKinds.Email.DATA));
             //添加Email的信息
             sb.append(",Email=").append(emailAddress);
             Log.e("emailAddress", emailAddress);
             map.put("email", emailAddress);
             }
             emails.close();
             */

            //查询==地址==类型的数据操作.StructuredPostal.TYPE_WORK

            /**
             Cursor address = contentResolver.query(ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI,
             null,
             ContactsContract.CommonDataKinds.StructuredPostal.CONTACT_ID + " = " + contactId,
             null, null);
             while (address.moveToNext())
             {
             String workAddress = address.getString(address.getColumnIndex(
             ContactsContract.CommonDataKinds.StructuredPostal.DATA));


             //添加Email的信息
             sb.append(",address").append(workAddress);
             map.put("address", workAddress);
             }
             address.close();
             //Log.i("=========ddddddddddd=====", sb.toString());
             */
            //查询==公司名字==类型的数据操作.Organization.COMPANY  ContactsContract.Data.CONTENT_URI
            /**
             String orgWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
             String[] orgWhereParams = new String[]{contactId,
             ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE};
             Cursor orgCur = contentResolver.query(ContactsContract.Data.CONTENT_URI,
             null, orgWhere, orgWhereParams, null);
             if (orgCur.moveToFirst()) {
             //组织名 (公司名字)
             String company = orgCur.getString(orgCur.getColumnIndex(ContactsContract.CommonDataKinds.Organization.DATA));
             //职位
             String title = orgCur.getString(orgCur.getColumnIndex(ContactsContract.CommonDataKinds.Organization.TITLE));
             sb.append(",company").append(company);
             sb.append(",title").append(title);
             map.put("company", company);
             map.put("title", title);
             }
             orgCur.close();
             */
            list.add(map);
        }

        Log.i("=========list=====", list.toString());//
        cursor.close();

        return contacts;
    }
}
