package com.example.asger.nepalspil.felter;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asger.nepalspil.R;
import com.example.asger.nepalspil.models.SpillePlade;

import java.io.IOException;

import static com.example.asger.nepalspil.activities.MainActivity.spiller;

/**
 * Created by Bruger on 03-01-2017.
 */

public class Vaerksted extends AppCompatActivity {

    ViewPager viewPager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vaerksted);


        viewPager = (ViewPager) findViewById(R.id.view_pager);
        ImagePagerAdapter adapter = new ImagePagerAdapter();
        viewPager.setAdapter(adapter);


        final TextView fieldinfo = (TextView) findViewById(R.id.fieldinfo);
        final TextView playerinfo = (TextView) findViewById(R.id.playerinfo);
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.cash);

        Button work = (Button) findViewById(R.id.workButton);
        Button buy = (Button) findViewById(R.id.buyBikeButton);
        Button back = (Button) findViewById(R.id.backButton);


        fieldinfo.setText("Velkommen til værkstedet! Her kan man arbejde eller købe en cykel.");
        playerinfo.setText("Navn: " + spiller.getNavn() + "\n mad: " + spiller.getHp() + "\n Penge: " + spiller.getPenge() + "\n Viden: " + spiller.getViden() + "\n Klassetrin: " + spiller.getKlassetrin() + "\n Tid: " + spiller.getTid());

        work.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (spiller.getTid() >= 2) {
                    work();

                    if (mp.isPlaying()) {
                        mp.stop();
                    }
                    try {
                        mp.reset();
                        AssetFileDescriptor afd;
                        afd = getAssets().openFd("cash.mp3");
                        mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                        mp.prepare();
                        mp.start();
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    playerinfo.setText(updateInfo());
                } else {

                }
            }
        });

        buy.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                buy();
                playerinfo.setText(updateInfo());
            }
        });

        back.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                finish();
            }
        });
    }


    private class ImagePagerAdapter extends PagerAdapter {
        private int[] mImages = new int[]{
                R.drawable.bike,
                R.drawable.books,
                R.drawable.books,
        };

        @Override
        public int getCount() {
            return mImages.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((ImageView) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Context context = Vaerksted.this;
            ImageView imageView = new ImageView(context);
            int padding = context.getResources().getDimensionPixelSize(
                    R.dimen.padding_medium);
            imageView.setPadding(padding, padding, padding, padding);
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            imageView.setImageResource(mImages[position]);
            ((ViewPager) container).addView(imageView, 0);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((ImageView) object);

        }
    }

    public void work() {
        spiller.setTid(spiller.getTid() - 2);
        spiller.setPenge(spiller.getPenge() + 10);
    }

    public void buy() {
        if (viewPager.getCurrentItem() == R.drawable.books && spiller.getPenge() >= 100) {
            spiller.setPenge(spiller.getPenge() - 100);

        } else if (spiller.getPenge() < 100) {
            Toast.makeText(Vaerksted.this, "Du har ikke råd til studiebøger", Toast.LENGTH_SHORT).show();
        }
        if (viewPager.getCurrentItem() == R.drawable.bike && spiller.getPenge() >= 200) {
            spiller.setPenge(spiller.getPenge() - 200);
            spiller.setBike(true);
        } else if (spiller.getPenge() < 200) {
            Toast.makeText(Vaerksted.this, "Du har ikke råd til en cykel", Toast.LENGTH_SHORT).show();
        }
    }

    public String updateInfo() {
        SpillePlade.updateInfobox();
        return "Navn: " + spiller.getNavn() + "\n mad: " + spiller.getHp() + "\n Penge: " + spiller.getPenge() + "\n Viden: " + spiller.getViden() + "\n Klassetrin: " + spiller.getKlassetrin() + "\n Tid: " + spiller.getTid();
    }


}

