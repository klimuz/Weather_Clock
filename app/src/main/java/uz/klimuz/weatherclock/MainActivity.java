package uz.klimuz.weatherclock;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    TextView dateTextVew;
    TextView weekDayTextVew;
    TextView timeTextVew;
    TextView tempTextView;
    TextView celsTextView;
    Button button;
    TextView spareTextView;
    ImageView imageView;

    int month;
    int weekDay;
    int hours;
    int minutes;
    int counter;
    String[] weatherInfo = new String[2];
    //String weatherKind = "";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        dateTextVew = findViewById(R.id.dateTextView);
        weekDayTextVew = findViewById(R.id.weekDayTextView);
        timeTextVew = findViewById(R.id.timeTextView);
        tempTextView = findViewById(R.id.tempTextView);
        celsTextView = findViewById(R.id.celsTextView);
        imageView = findViewById(R.id.imageView);

        spareTextView = findViewById(R.id.spareTextView);
        button = findViewById(R.id.button);
        timeUpdate();
        updateWeather();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateWeather();
            }
        });
    }

    private void updateWeather() {
        counter = 0;

        final Handler weatherHandler = new Handler();
        new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            Parser parser = new Parser();
                            weatherInfo = parser.mainMethod();
                            //weatherKind = weatherInfo[1];
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        tempTextView.post(new Runnable() {
                            @Override
                            public void run() {
                                tempTextView.setText(weatherInfo[0]);
                                switch (weatherInfo[1]) {
                                    case "skc-n":
                                        imageView.setImageResource(R.drawable.skc_n);
                                        break;
                                    case "skc-d":
                                        imageView.setImageResource(R.drawable.skc_d);
                                        break;
                                    case "bkn-n":
                                        imageView.setImageResource(R.drawable.bkn_n);
                                        break;
                                    case "bkn-d":
                                        imageView.setImageResource(R.drawable.bkn_d);
                                        break;
                                    case "ovc-sn":
                                        imageView.setImageResource(R.drawable.ovc_sn);
                                        break;
                                    case "ovc-m-sn":
                                        imageView.setImageResource(R.drawable.ovc_m_sn);
                                        break;
                                    case "ovc-m-ra":
                                        imageView.setImageResource(R.drawable.ovc_m_ra);
                                        break;
                                    case "ovc":
                                        imageView.setImageResource(R.drawable.ovc);
                                        break;
                                    case "ovc-r-sn":
                                        imageView.setImageResource(R.drawable.ovc_r_sn);
                                        break;
                                    case "ovc-r":
                                        imageView.setImageResource(R.drawable.ovc_r);
                                        break;
                                    case "bkn-p-ra-d":
                                        imageView.setImageResource(R.drawable.bkn_p_ra_d);
                                        break;
                                    case "bkn-p-ra-n":
                                        imageView.setImageResource(R.drawable.bkn_p_ra_n);
                                        break;
                                    default:
                                        imageView.setImageResource(R.drawable.uncknown);
                                }
                            }
                        });
                    }
                }).start();
        }
        private void timeUpdate() {
        final Handler timeHandler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }

                timeHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Calendar calendar = Calendar.getInstance();

                        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                        String dayOfMonthString = "";
                        if (dayOfMonth < 10){
                            dayOfMonthString = 0 + String.valueOf(dayOfMonth);
                        } else {
                            dayOfMonthString = String.valueOf(dayOfMonth);
                        }

                        month = calendar.get(Calendar.MONTH);
                        String monthString = "";
                        switch (month) {
                            case 0:
                                monthString = "Jan";
                                break;
                            case 1:
                                monthString = "Feb";
                                break;
                            case 2:
                                monthString = "Mar";
                                break;
                            case 3:
                                monthString = "Apr";
                                break;
                            case 4:
                                monthString = "May";
                                break;
                            case 5:
                                monthString = "Jun";
                                break;
                            case 6:
                                monthString = "Jul";
                                break;
                            case 7:
                                monthString = "Aug";
                                break;
                            case 8:
                                monthString = "Sep";
                                break;
                            case 9:
                                monthString = "Oct";
                                break;
                            case 10:
                                monthString = "Nov";
                                break;
                            case 11:
                                monthString = "Dec";
                                break;
                        }
                        dateTextVew.setText(dayOfMonthString + "-" + monthString
                                + "-" + calendar.get(Calendar.YEAR));

                        weekDay = calendar.get(Calendar.DAY_OF_WEEK);
                        String weekDayString = "";
                        switch (weekDay) {
                            case 1:
                                weekDayString = "Sunday";
                                break;
                            case 2:
                                weekDayString = "Monday";
                                break;
                            case 3:
                                weekDayString = "Tuesday";
                                break;
                            case 4:
                                weekDayString = "Wednesday";
                                break;
                            case 5:
                                weekDayString = "Thursday";
                                break;
                            case 6:
                                weekDayString = "Friday";
                                break;
                            case 7:
                                weekDayString = "Saturday";
                                break;
                        }
                        weekDayTextVew.setText(weekDayString);

                        hours = calendar.get(Calendar.HOUR_OF_DAY);
                        String hoursString = "";
                        if (hours < 10){
                            hoursString = 0 + String.valueOf(hours);
                        } else {
                            hoursString = String.valueOf(hours);
                        }
                        minutes = calendar.get(Calendar.MINUTE);
                        String minutesString = "";
                        if (minutes < 10){
                            minutesString = 0 + String.valueOf(minutes);
                        } else {
                            minutesString = String.valueOf(minutes);
                        }
                        timeTextVew.setText(hoursString + " : " + minutesString);
                        counter++;
                        if (counter == 600){
                            updateWeather();
                        }
                        timeUpdate();
                    }
                });
            }
        }).start();
    }

}