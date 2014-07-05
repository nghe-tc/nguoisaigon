package com.nguoisaigon.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore.Images;

import com.nguoisaigon.db.SettingDB;
import com.nguoisaigon.entity.EventsInfo;
import com.nguoisaigon.entity.MusicInfo;
import com.nguoisaigon.entity.NewsInfo;
import com.nguoisaigon.entity.ProductInfo;
import com.nguoisaigon.entity.SettingInfo;

public class Emailplugin {
	
	static public void SendEmailFromHomeView(Context context)
	{
		SettingDB settingDB = new SettingDB(context);
		SettingInfo info = settingDB.getSetting();
		
		String[] toRecipient = new String[]{"Ubeehoang@gmail.com"}; 
		String subject = "";
		String body = "\n---------\nNgười Sài Gòn App\n AppStore Link:" + info.getAppLink();
		
		Emailplugin.SendEmail(context, toRecipient, subject, body, null);
	}
	
	static public void SendEmailFromEventView(Context context, EventsInfo event)
	{
		SettingDB settingDB = new SettingDB(context);
		SettingInfo info = settingDB.getSetting();
		
		String[] toRecipient = null; 
		String subject = "Sự kiện chia sẻ từ Người Sài Gòn";
		String body = event.getTitle() + "\nThời gian: " + event.getEventDate().toString() + "\n" 
										+ event.getEventContent() + "\n\n---------\nNgười Sài Gòn App\n AppStore Link: "
										+ info.getAppLink();
		
		Emailplugin.SendEmail(context, toRecipient, subject, body, null);
	}
	
	static public void SendEmailFromMusicView(Context context, MusicInfo music)
	{
		SettingDB settingDB = new SettingDB(context);
		SettingInfo info = settingDB.getSetting();
		
		String[] toRecipient = null; 
		String subject = "Nhạc chia sẻ từ Người Sài Gòn";
		String body = music.getTitle() + "-" + music.getSinger() + "\nLink nhạc: " 
										+ music.getPlayUrl() + "\n\n---------\nNgười Sài Gòn App\n AppStore Link: "
										+ info.getAppLink();
		
		Emailplugin.SendEmail(context, toRecipient, subject, body, null);
	}
	
	static public void SendEmailFromNewsView(Context context, NewsInfo news)
	{
		SettingDB settingDB = new SettingDB(context);
		SettingInfo info = settingDB.getSetting();
		
		String[] toRecipient = null; 
		String subject = "Tin tức chia sẻ từ Người Sài Gòn";
		String body = news.getTitle() + "\nNgày đăng: " + news.getCreateDate().toString() + "\n" 
										+ news.getNewsContent() + "\n\n---------\nNgười Sài Gòn App\n AppStore Link: "
										+ info.getAppLink();
		
		Emailplugin.SendEmail(context, toRecipient, subject, body, null);
	}
	
	static public void SendEmailFromStoreView(Context context, ProductInfo product)
	{
		SettingDB settingDB = new SettingDB(context);
		SettingInfo info = settingDB.getSetting();
		
		String[] toRecipient = null; 
		String subject = "Thông tin sản phẩm chia sẻ từ Người Sài Gòn";
		String body = product.getName() + "\n" + product.getProductId() +  "\n\n---------\nNgười Sài Gòn App\n AppStore Link: "
										+ info.getAppLink();
		
		Emailplugin.SendEmail(context, toRecipient, subject, body, null);
	}
	
	static public Boolean SendEmail (Context context, String[] toRecipient, String subject, String body, Bitmap attatchment)
	{
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		if (toRecipient != null)
			i.putExtra(Intent.EXTRA_EMAIL  , toRecipient);
		i.putExtra(Intent.EXTRA_SUBJECT, subject);
		i.putExtra(Intent.EXTRA_TEXT   , body);
		
		try {
			if (attatchment != null)
			{
				i.setType("image/jpeg");
				// Store image in Devise database to send image to mail
			    String path = Images.Media.insertImage(context.getContentResolver(), attatchment,"tempImageForSendMail", null);
			    Uri attactmentUri = Uri.parse(path);
			    
			    i.putExtra(Intent.EXTRA_STREAM, attactmentUri);
			}
			
			context.startActivity(Intent.createChooser(i, "Send Mail Using :"));
		} catch (android.content.ActivityNotFoundException ex) {
			return false;
		}
		return true;
	}
}
