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
    Button button;
    ImageView imageView;
    ImageView imageView2;
    TextView temp2TextView;
    Button backwardButton;
    Button forwardButton;
    TextView forecastTextView;

    int month;
    int weekDay;
    int hours;
    int minutes;
    int counterTime;
    int counterForecast = 3;
    String[] weatherInfo = new String[4];//0-temp current; 1-image current; 2-temp forecast; 3-image forecast

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
        imageView = findViewById(R.id.imageView);
        imageView2 = findViewById(R.id.imageView2);
        temp2TextView = findViewById(R.id.temp2TextView);
        backwardButton = findViewById(R.id.backwardButton);
        forwardButton = findViewById(R.id.forwardButton);
        forecastTextView = findViewById(R.id.forecastTextView);
        button = findViewById(R.id.button);
        timeUpdate();
        updateWeather();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateWeather();
            }
        });

        backwardButton.setEnabled(false);
        forecastTextView.setText("Next " + counterForecast + " hours");


        backwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counterForecast -= 3;
                forecastTextView.setText("Next " + counterForecast + " hours");
                if (counterForecast <= 3){
                    backwardButton.setEnabled(false);
                } else {
                    backwardButton.setEnabled(true);
                }
                forwardButton.setEnabled(true);
            }
        });

        forwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counterForecast += 3;
                forecastTextView.setText("Next " + counterForecast + " hours");
                if (counterForecast >= 48){
                    forwardButton.setEnabled(false);
                } else {
                    forwardButton.setEnabled(true);
                }
                backwardButton.setEnabled(true);
            }
        });
    }

    private void updateWeather() {
        counterTime = 0;
        final Handler weatherHandler = new Handler();
        new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Parser parser = new Parser();
                            weatherInfo = parser.mainMethod();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        tempTextView.post(new Runnable() {
                            @Override
                            public void run() {
                                if (weatherInfo[0] != null && weatherInfo[1] != null && weatherInfo[2] != null && weatherInfo[3] != null) {
                                    tempTextView.setText(weatherInfo[0]);
                                    temp2TextView.setText(weatherInfo[2]);
                                    switch (weatherInfo[1]) {
                                        case "skc_n":
                                            imageView.setImageResource(R.drawable.skc_n);
                                            break;
                                        case "skc_d":
                                            imageView.setImageResource(R.drawable.skc_d);
                                            break;
                                        case "bkn_n":
                                            imageView.setImageResource(R.drawable.bkn_n);
                                            break;
                                        case "bkn_d":
                                            imageView.setImageResource(R.drawable.bkn_d);
                                            break;
                                        case "ovc_sn":
                                            imageView.setImageResource(R.drawable.ovc_sn);
                                            break;
                                        case "ovc_m_sn":
                                            imageView.setImageResource(R.drawable.ovc_m_sn);
                                            break;
                                        case "ovc_m_ra":
                                            imageView.setImageResource(R.drawable.ovc_m_ra);
                                            break;
                                        case "ovc":
                                            imageView.setImageResource(R.drawable.ovc);
                                            break;
                                        case "ovc_ra_sn":
                                            imageView.setImageResource(R.drawable.ovc_ra_sn);
                                            break;
                                        case "ovc_ra":
                                            imageView.setImageResource(R.drawable.ovc_ra);
                                            break;
                                        case "bkn_p_ra_d":
                                            imageView.setImageResource(R.drawable.bkn_p_ra_d);
                                            break;
                                        case "bkn_p_ra_n":
                                            imageView.setImageResource(R.drawable.bkn_p_ra_n);
                                            break;
                                        default:
                                            imageView.setImageResource(R.drawable.unknown);
                                    }
                                    switch (weatherInfo[3]) {
                                        case "skc_n":
                                            imageView2.setImageResource(R.drawable.skc_n);
                                            break;
                                        case "skc_d":
                                            imageView2.setImageResource(R.drawable.skc_d);
                                            break;
                                        case "bkn_n":
                                            imageView2.setImageResource(R.drawable.bkn_n);
                                            break;
                                        case "bkn_d":
                                            imageView2.setImageResource(R.drawable.bkn_d);
                                            break;
                                        case "ovc_sn":
                                            imageView2.setImageResource(R.drawable.ovc_sn);
                                            break;
                                        case "ovc_m_sn":
                                            imageView2.setImageResource(R.drawable.ovc_m_sn);
                                            break;
                                        case "ovc_m_ra":
                                            imageView2.setImageResource(R.drawable.ovc_m_ra);
                                            break;
                                        case "ovc":
                                            imageView2.setImageResource(R.drawable.ovc);
                                            break;
                                        case "ovc_ra_sn":
                                            imageView2.setImageResource(R.drawable.ovc_ra_sn);
                                            break;
                                        case "ovc_ra":
                                            imageView2.setImageResource(R.drawable.ovc_ra);
                                            break;
                                        case "bkn_p_ra_d":
                                            imageView2.setImageResource(R.drawable.bkn_p_ra_d);
                                            break;
                                        case "bkn_p_ra_n":
                                            imageView2.setImageResource(R.drawable.bkn_p_ra_n);
                                            break;
                                        default:
                                            imageView2.setImageResource(R.drawable.unknown);
                                    }
                                }else return;
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
                        counterTime++;
                        if (counterTime == 600){
                            updateWeather();
                        }
                        timeUpdate();
                    }
                });
            }
        }).start();
    }

}