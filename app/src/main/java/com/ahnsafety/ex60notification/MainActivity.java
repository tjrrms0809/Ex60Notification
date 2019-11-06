package com.ahnsafety.ex60notification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickBtn(View view) {

        //알림(Notification)을 관리하는 관리자객체를 운영체제(Context)로 부터 소환하기
        NotificationManager notificationManager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //Notification객체를 생성해주는 건축가객체 생성(AlertDialog와 비슷)
        NotificationCompat.Builder builder= null;

        //Oreo버전(API26버전)이상에서는 알림시에 NotificationChannel이라는 개념이 필수 구성요소가 됨
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            String channelID="channel_01";//알림채널 식별자
            String channelName="MyChanel01"; //알림채널의 이름(별명)

            //알림채널 객체 만들기
            NotificationChannel channel= new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);

            //알림매니저에게 채널객체의 생성을 요청
            notificationManager.createNotificationChannel(channel);

            //알림 건축가 객체 생성
            builder= new NotificationCompat.Builder(this, channelID);
        }else{
            //알림 건축가 객체 생성
            builder= new NotificationCompat.Builder(this, null);
        }

        //건축가에게 원하는 알림의 설정작업

        //상태표시줄에 보이는 아이콘
        builder.setSmallIcon(android.R.drawable.ic_dialog_email);

        //상태바를 드래그하여 아래로 내리면 보이는
        //알림창(확장 상태바)의 설정
        builder.setContentTitle("Title");//알림창 제목
        builder.setContentText("Messages....");//알림창 메세지..
        //알림창의 이미지
        Bitmap bm= BitmapFactory.decodeResource(getResources(), R.drawable.img01);
        builder.setLargeIcon(bm);

        //알림창을 클릭시에 실행할 작업(SecondActivity 실행) 설정
        Intent intent= new Intent(this, SecondActivity.class);
        //지금 실행하는 것이 아니라 잠시 보류시키는 Intent 객체 필요
        PendingIntent pendingIntent= PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        //알림창 클릭시에 자동으로 알림제거
        builder.setAutoCancel(true);

        //그밖의 설정들...

        //알림 사운드 설정
        Uri soundUri= RingtoneManager.getActualDefaultRingtoneUri(this, RingtoneManager.TYPE_NOTIFICATION);
        soundUri= Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.get_gem);
        builder.setSound(soundUri);

        //알림 진동 설정[진동은 반드시 퍼미션 추가 필요]
        builder.setVibrate(new long[]{0, 2000, 1000, 3000});//0초대기, 2초진동, 1초대기, 3초진동

        //잘 사용하지 않는 그밖으ㅔ 설정들..
        builder.addAction(android.R.drawable.ic_menu_more, "more", pendingIntent);
        builder.addAction(android.R.drawable.ic_menu_set_as, "setting", pendingIntent);

        builder.setColor(Color.MAGENTA);

        //요즘들어 종종 보이는 알림창 스타일
        //1. BigPictureStyle
        NotificationCompat.BigPictureStyle picStyle= new NotificationCompat.BigPictureStyle(builder);
        picStyle.bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.img01));

        //2. BitTextStyle : 많은 양의 글씨를 쓰고 싶을 때
        NotificationCompat.BigTextStyle bigTextStyle= new NotificationCompat.BigTextStyle(builder);
        bigTextStyle.bigText("Hello. world\nNice to meet you.\n\n Good boy.");//줄바꿈은 안되는 폰도 있음.

        //3. InboxStyle
        NotificationCompat.InboxStyle inboxStyle= new NotificationCompat.InboxStyle(builder);
        inboxStyle.addLine("first");
        inboxStyle.addLine("second");
        inboxStyle.addLine("hello~~");
        inboxStyle.addLine("안녕하세요.");

        //상태표시줄 표시
        builder.setProgress(100, 50, true);



        //건축가에게 알림객체 생성하도록
        Notification notification= builder.build();

        //알림매니져에게 알림(notify) 요청
        notificationManager.notify(1, notification);

        //알림요청시에 사용한 번호를 이용하여 알림제거 할 수 있음.
        //notificationManager.cancel(1);

    }
}
