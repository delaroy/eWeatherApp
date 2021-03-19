package com.delaroystudios.weatherapp.ui.today;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.delaroystudios.weatherapp.R;
import com.delaroystudios.weatherapp.di.Injectable;
import com.delaroystudios.weatherapp.model.SavedDailyForecast;
import com.delaroystudios.weatherapp.model.UviDb;
import com.delaroystudios.weatherapp.util.SharedPreferences;
import com.delaroystudios.weatherapp.util.Utility;
import com.delaroystudios.weatherapp.viewmodel.ForecastViewModel;
import com.delaroystudios.weatherapp.viewmodel.UviViewModel;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TodayFragment extends Fragment implements Injectable {
    private ForecastViewModel forecastViewModel;
    private UviViewModel uviViewModel;
    @BindView(R.id.city) TextView mcity;
    @BindView(R.id.date) TextView date;
    @BindView(R.id.condition) TextView condition;
    @BindView(R.id.weather_resource) ImageView weather_resource;
    @BindView(R.id.temp_condition) TextView temp_condition;
    @BindView(R.id.temperature) TextView temperature;
    @BindView(R.id.humidity_value) TextView humidity_value;
    @BindView(R.id.wind_value) TextView wind_value;
    @BindView(R.id.uv_value) TextView uv_value;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    public static TodayFragment create() {
        TodayFragment todayFragment = new TodayFragment();
        return todayFragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, root);
        getActivity().getWindow().setStatusBarColor(ContextCompat.getColor(getContext(),R.color.colorPrimaryToday));
        fetchData();

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void fetchData() {
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        String city = SharedPreferences.getInstance(getContext()).getCity();
        String numDays = SharedPreferences.getInstance(getContext()).getNumDays();

        forecastViewModel = new ViewModelProvider(this, viewModelFactory).get(ForecastViewModel.class);
        forecastViewModel.fetchResults(city, numDays).observe(getViewLifecycleOwner(), result -> {
            List<SavedDailyForecast> dailyForecasts = result.data;
            mcity.setText(Utility.toTitleCase(city));
            if (dailyForecasts != null && dailyForecasts.size() > 0) {

                fetchUvi(dailyForecasts.get(0).getLat(), dailyForecasts.get(0).getLon());

                weather_resource.setImageResource(Utility.getArtResourceForWeatherCondition(dailyForecasts.get(0).getWeatherid()));
                condition.setText(Utility.toTitleCase(dailyForecasts.get(0).getDescription()));
                date.setText(String.format("%s, %s", Utility.format(dailyForecasts.get(0).getDate()), Utility.formatDate(dailyForecasts.get(0).getDate())));
                humidity_value.setText(dailyForecasts.get(0).getHumidity() + "%");
                wind_value.setText(Utility.getFormattedWind(getContext(), Float.parseFloat(String.valueOf(dailyForecasts.get(0).getWind()))));

                SharedPreferences.getInstance(getContext()).putStringValue(SharedPreferences.DESC, dailyForecasts.get(0).getDescription());
                if(timeOfDay >= 0 && timeOfDay < 12){
                    temp_condition.setText(Utility.formatTemperature(getContext(), dailyForecasts.get(0).getMorningTemp()));
                    SharedPreferences.getInstance(getContext()).putStringValue(SharedPreferences.TEMP, Utility.formatTemperature(getContext(), dailyForecasts.get(0).getMorningTemp()));
                    temperature.setText(Utility.formatTemperature(getContext(), dailyForecasts.get(0).getFeelslikeMorning()));
                }else if(timeOfDay >= 12 && timeOfDay < 16){
                    temp_condition.setText(Utility.formatTemperature(getContext(), dailyForecasts.get(0).getDayTemp()));
                    temperature.setText(Utility.formatTemperature(getContext(), dailyForecasts.get(0).getFeelslikeDay()));
                    SharedPreferences.getInstance(getContext()).putStringValue(SharedPreferences.TEMP, Utility.formatTemperature(getContext(), dailyForecasts.get(0).getMorningTemp()));
                }else if(timeOfDay >= 16 && timeOfDay < 21){
                    temp_condition.setText(Utility.formatTemperature(getContext(), dailyForecasts.get(0).getEveningTemp()));
                    temperature.setText(Utility.formatTemperature(getContext(), dailyForecasts.get(0).getEveningTemp()));
                    SharedPreferences.getInstance(getContext()).putStringValue(SharedPreferences.TEMP, Utility.formatTemperature(getContext(), dailyForecasts.get(0).getMorningTemp()));
                }else if(timeOfDay >= 21 && timeOfDay < 24){
                    temp_condition.setText(Utility.formatTemperature(getContext(), dailyForecasts.get(0).getNightTemp()));
                    temperature.setText(Utility.formatTemperature(getContext(), dailyForecasts.get(0).getFeelslikeNight()));
                    SharedPreferences.getInstance(getContext()).putStringValue(SharedPreferences.TEMP, Utility.formatTemperature(getContext(), dailyForecasts.get(0).getMorningTemp()));
                }
            }
        });
    }

    private void fetchUvi(Double lat, Double lon) {
        forecastViewModel.fetchUvi(lat, lon).observe(getViewLifecycleOwner(), result -> {
            UviDb uviDb = result.data;
            if (uviDb !=null) {
                uv_value.setText(uviDb.getValue() + "");
            }
        });
    }

}
