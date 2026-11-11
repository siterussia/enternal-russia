package com.eternalrussia.launcher;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    private static final String GAME_PACKAGE = "com.kranin";
    private TextView status;

    private GradientDrawable shape(int color, int radius) {
        GradientDrawable d = new GradientDrawable();
        d.setColor(color);
        d.setCornerRadius(radius);
        return d;
    }

    private TextView label(String value, int size, int color, boolean bold) {
        TextView t = new TextView(this);
        t.setText(value);
        t.setTextSize(size);
        t.setTextColor(color);
        if (bold) t.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        return t;
    }

    private Button button(String text, int color) {
        Button b = new Button(this);
        b.setText(text);
        b.setTextColor(Color.WHITE);
        b.setAllCaps(false);
        b.setTextSize(16);
        b.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        b.setBackground(shape(color, 28));
        return b;
    }

    private boolean isGameInstalled() {
        try {
            getPackageManager().getPackageInfo(GAME_PACKAGE, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    private void refreshStatus() {
        if (isGameInstalled()) {
            status.setText("Клиент обнаружен • com.kranin");
            status.setTextColor(Color.rgb(110, 231, 183));
        } else {
            status.setText("Клиент не найден • проверь установку");
            status.setTextColor(Color.rgb(252, 165, 165));
        }
    }

    private void launchGame() {
        Intent launch = getPackageManager().getLaunchIntentForPackage(GAME_PACKAGE);
        if (launch == null) {
            Toast.makeText(this, "Не удалось найти запускаемый клиент com.kranin", Toast.LENGTH_LONG).show();
            refreshStatus();
            return;
        }
        launch.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(launch);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.rgb(10, 14, 25));
        getWindow().setNavigationBarColor(Color.rgb(10, 14, 25));

        LinearLayout page = new LinearLayout(this);
        page.setOrientation(LinearLayout.VERTICAL);
        page.setPadding(24, 34, 24, 24);
        page.setGravity(Gravity.CENTER_HORIZONTAL);
        page.setBackground(new GradientDrawable(
            GradientDrawable.Orientation.TL_BR,
            new int[]{Color.rgb(10,14,25), Color.rgb(25,24,48), Color.rgb(10,14,25)}
        ));

        TextView tag = label("ANDROID CLIENT  •  PERSONAL EDITION", 11, Color.rgb(167,139,250), true);
        tag.setGravity(Gravity.CENTER);
        page.addView(tag, new LinearLayout.LayoutParams(-1, -2));

        TextView title = label("ENTERNAL\nRUSSIA", 34, Color.WHITE, true);
        title.setGravity(Gravity.CENTER);
        title.setLetterSpacing(0.08f);
        LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(-1, -2);
        titleParams.topMargin = 30;
        page.addView(title, titleParams);

        TextView subtitle = label("YOUR CITY. YOUR RULES.", 13, Color.rgb(196,181,253), true);
        subtitle.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams subParams = new LinearLayout.LayoutParams(-1, -2);
        subParams.topMargin = 8;
        page.addView(subtitle, subParams);

        LinearLayout panel = new LinearLayout(this);
        panel.setOrientation(LinearLayout.VERTICAL);
        panel.setPadding(18, 18, 18, 18);
        panel.setBackground(shape(Color.rgb(30,35,55), 24));
        LinearLayout.LayoutParams panelParams = new LinearLayout.LayoutParams(-1, -2);
        panelParams.topMargin = 42;
        page.addView(panel, panelParams);

        TextView cacheTitle = label("ИГРОВЫЕ ФАЙЛЫ", 12, Color.rgb(167,139,250), true);
        panel.addView(cacheTitle);
        TextView cacheSize = label("3,54 ГБ  •  локальный кеш", 20, Color.WHITE, true);
        LinearLayout.LayoutParams sizeParams = new LinearLayout.LayoutParams(-1, -2);
        sizeParams.topMargin = 8;
        panel.addView(cacheSize, sizeParams);
        TextView cacheNote = label("Папка: Android/data/com.kranin/files", 12, Color.LTGRAY, false);
        LinearLayout.LayoutParams noteParams = new LinearLayout.LayoutParams(-1, -2);
        noteParams.topMargin = 6;
        panel.addView(cacheNote, noteParams);

        status = label("", 13, Color.WHITE, true);
        status.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams statusParams = new LinearLayout.LayoutParams(-1, -2);
        statusParams.topMargin = 28;
        page.addView(status, statusParams);

        Button play = button("ИГРАТЬ", Color.rgb(124, 58, 237));
        LinearLayout.LayoutParams playParams = new LinearLayout.LayoutParams(-1, 58);
        playParams.topMargin = 18;
        page.addView(play, playParams);
        play.setOnClickListener(v -> launchGame());

        Button check = button("ПРОВЕРИТЬ КЛИЕНТ", Color.rgb(55, 65, 90));
        LinearLayout.LayoutParams checkParams = new LinearLayout.LayoutParams(-1, 54);
        checkParams.topMargin = 12;
        page.addView(check, checkParams);
        check.setOnClickListener(v -> refreshStatus());

        TextView footer = label("Версия лаунчера 1.0 • личная сборка", 11, Color.rgb(148,163,184), false);
        footer.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams footerParams = new LinearLayout.LayoutParams(-1, -2);
        footerParams.topMargin = 34;
        page.addView(footer, footerParams);

        setContentView(page);
        refreshStatus();
    }
}
