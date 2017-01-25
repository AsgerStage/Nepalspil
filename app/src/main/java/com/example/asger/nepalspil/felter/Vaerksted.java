package com.example.asger.nepalspil.felter;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asger.nepalspil.R;
import com.example.asger.nepalspil.activities.SpillePlade;

import java.io.IOException;

import static com.example.asger.nepalspil.models.Spiller.instans;


public class Vaerksted extends AppCompatActivity {

    //Working
    final int MONEY_PER_CLICK = 7;
    final int TIME_PER_CLICK = 1;
    private Topbar topbar;

    @Override
    public void onBackPressed() {
        SpillePlade.updateEntireBoard();
        finish();
    }

    private Animation animation;
    AlertDialog.Builder dialog;
    ViewPager viewPager;
    TextView viewPagerText;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vaerksted);

        dialog = new AlertDialog.Builder(Vaerksted.this);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        ImagePagerAdapter adapter = new ImagePagerAdapter();
        viewPager.setAdapter(adapter);

        final MediaPlayer mp = MediaPlayer.create(this, R.raw.cash);
        ImageView helpField = (ImageView) findViewById(R.id.vaerkstedHelp);
        Button work = (Button) findViewById(R.id.workButton);
        Button buy = (Button) findViewById(R.id.buyBikeButton);
        ImageView hjemBack = (ImageView) findViewById(R.id.hjemBack);
        viewPagerText = (TextView) findViewById(R.id.viewpagerPris);
        ImageView menu = (ImageView) findViewById(R.id.menuknap);
        menu.setVisibility(View.INVISIBLE);

        topbar = new Topbar();
        topbar.init(this);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager, true);
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.plusknowledge);
        final TextView money = (TextView) findViewById(R.id.money);
        updateText();
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        viewPagerText.setText("Cykel: 200kr");
                        break;
                    case 1:
                        viewPagerText.setText("Pænt hurtig cykel: 500kr");
                        break;
                    case 2:
                        viewPagerText.setText("Racer cykel: 1000kr");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        helpField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(Vaerksted.this, R.anim.image_click));
                dialog.setMessage("Du bor ikke lige ved siden af skolen, så hvis du skal gå derhen tager det lang tid. Men en cykel koster mange penge, så du må først spare op.");
                dialog.show();
            }
        });

        work.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(Vaerksted.this, R.anim.image_click));
                if (instans.getTid() >= TIME_PER_CLICK && instans.getKlassetrin() >= 3) {
                    instans.work(TIME_PER_CLICK, MONEY_PER_CLICK);
                    money.setText("+" + MONEY_PER_CLICK + " kr");
                    money.startAnimation(animation);

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

                    updateText();
                } else if (instans.getTid() < 2) {

                    dialog.setTitle("Intet tid!");
                    dialog.setMessage("Du har ikke nok tid til at arbejde");
                    dialog.show();

                } else if (instans.getKlassetrin() < 3) {
                    dialog.setTitle("Du er ikke klog nok");
                    dialog.setMessage("Du skal gå i mindst 3. klasse for at arbejde her");
                    dialog.show();
                }
            }
        });

        buy.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(Vaerksted.this, R.anim.image_click));
                buy();
                updateText();
            }
        });

        hjemBack.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                SpillePlade.updateEntireBoard();
                v.startAnimation(AnimationUtils.loadAnimation(Vaerksted.this, R.anim.image_click));
                finish();
            }
        });

    }


    private class ImagePagerAdapter extends PagerAdapter {
        private int[] mImages = new int[]{
                R.drawable.badbike,
                R.drawable.bike,
                R.drawable.bike,
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


    public void buy() {

        switch (viewPager.getCurrentItem()) {
            case 0:
                if (instans.getmoveSpeed() < 2) {
                    if (instans.getPenge() >= 200) {
                        instans.setPenge(instans.getPenge() - 200);
                        instans.setmoveSpeed(2);
                        Toast.makeText(Vaerksted.this, "Du har købt en cykel", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Vaerksted.this, "Du har ikke råd til en cykel, den koster 200", Toast.LENGTH_SHORT).show();
                    }
                } else
                    Toast.makeText(Vaerksted.this, "Du har allerede en bedre cykel", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                if (instans.getmoveSpeed() < 3) {
                    if (instans.getPenge() >= 500) {
                        instans.setPenge(instans.getPenge() - 500);
                        instans.setmoveSpeed(3);
                        Toast.makeText(Vaerksted.this, "Du har købt en pænt hurtig cykel", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Vaerksted.this, "Du har ikke råd til den pænt hurtige cykel, den koster 500kr", Toast.LENGTH_SHORT).show();
                    }
                } else
                    Toast.makeText(Vaerksted.this, "Du har allerede en bedre cykel", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                if (instans.getmoveSpeed() < 4) {
                    if (instans.getPenge() >= 1000) {
                        instans.setPenge(instans.getPenge() - 1000);
                        instans.setmoveSpeed(4);
                        Toast.makeText(Vaerksted.this, "Du har købt en racer cykel", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Vaerksted.this, "Du har ikke råd til racer cyklen, den koster 1000kr", Toast.LENGTH_SHORT).show();
                    }
                } else
                    Toast.makeText(Vaerksted.this, "Du har allerede en bedre cykel", Toast.LENGTH_SHORT).show();
                break;

        }
    }


    public void updateText() {
        topbar.opdaterGui(instans);
        SpillePlade.updateEntireBoard();
    }

}

