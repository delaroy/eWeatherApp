package com.delaroystudios.weatherapp.repository;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;


import com.delaroystudios.weatherapp.AppExecutors;
import com.delaroystudios.weatherapp.BuildConfig;
import com.delaroystudios.weatherapp.api.ApiResponse;
import com.delaroystudios.weatherapp.api.WeatherService;
import com.delaroystudios.weatherapp.db.ForecastDao;
import com.delaroystudios.weatherapp.model.Resource;
import com.delaroystudios.weatherapp.model.SavedDailyForecast;
import com.delaroystudios.weatherapp.model.Uvi;
import com.delaroystudios.weatherapp.model.UviDb;
import com.delaroystudios.weatherapp.model.WeatherForecast;
import com.delaroystudios.weatherapp.util.Constants;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ForecastRepository {

    private final ForecastDao forecastDao;
    private final WeatherService weatherService;
    private final AppExecutors appExecutors;

    @Inject
    ForecastRepository(AppExecutors appExecutors, ForecastDao forecastDao, WeatherService weatherService) {
        this.forecastDao = forecastDao;
        this.weatherService = weatherService;
        this.appExecutors = appExecutors;
    }

    public LiveData<Resource<List<SavedDailyForecast>>> loadForecast(String city, String numDays) {
        return new NetworkBoundResource<List<SavedDailyForecast>, WeatherForecast>(appExecutors) {
            @Override
            protected void saveCallResult(@NonNull WeatherForecast item) {
                if (item != null && item.getDailyForecasts() != null) {
                    List<SavedDailyForecast> savedDailyForecasts = new ArrayList<SavedDailyForecast>();
                    for (int i = 0; i < item.getDailyForecasts().size(); i++) {
                        SavedDailyForecast savedDailyForecast = new SavedDailyForecast();
                        savedDailyForecast.setLat(item.getCity().getCoord().getLat());
                        savedDailyForecast.setLon(item.getCity().getCoord().getLon());
                        savedDailyForecast.setDate(item.getDailyForecasts().get(i).getDt());
                        savedDailyForecast.setMaxTemp(item.getDailyForecasts().get(i).getTemp().getMax());
                        savedDailyForecast.setMinTemp(item.getDailyForecasts().get(i).getTemp().getMin());
                        savedDailyForecast.setDayTemp(item.getDailyForecasts().get(i).getTemp().getDay());
                        savedDailyForecast.setEveningTemp(item.getDailyForecasts().get(i).getTemp().getEve());
                        savedDailyForecast.setMorningTemp(item.getDailyForecasts().get(i).getTemp().getMorn());
                        savedDailyForecast.setNightTemp(item.getDailyForecasts().get(i).getTemp().getNight());
                        savedDailyForecast.setFeelslikeDay(item.getDailyForecasts().get(i).getFeelsLike().getDay());
                        savedDailyForecast.setFeelslikeEve(item.getDailyForecasts().get(i).getFeelsLike().getEve());
                        savedDailyForecast.setFeelslikeMorning(item.getDailyForecasts().get(i).getFeelsLike().getMorn());
                        savedDailyForecast.setFeelslikeNight(item.getDailyForecasts().get(i).getFeelsLike().getNight());
                        savedDailyForecast.setHumidity(item.getDailyForecasts().get(i).getHumidity());
                        savedDailyForecast.setWind(item.getDailyForecasts().get(i).getSpeed());
                        savedDailyForecast.setDescription(item.getDailyForecasts().get(i).getWeather().get(0).getDescription());
                        savedDailyForecast.setWeatherid(item.getDailyForecasts().get(i).getWeather().get(0).getId());
                        savedDailyForecast.setImageUrl(item.getDailyForecasts().get(i).getWeather().get(0).getIcon());
                        savedDailyForecasts.add(savedDailyForecast);
                    }
                    forecastDao.insertForecastList(savedDailyForecasts);
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable List<SavedDailyForecast> data) {
                return data == null || data.isEmpty();
            }

            @NonNull
            @Override
            protected LiveData<List<SavedDailyForecast>> loadFromDb() {
                return forecastDao.loadForecast();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<WeatherForecast>> createCall() {
                return weatherService.getWeatherForecast(city, numDays, Constants.UNIT_SYSTEM, BuildConfig.WEATHER_API_KEY);
            }

            @Override
            protected void onFetchFailed() {

            }
        }.asLiveData();
    }

    public LiveData<Resource<List<SavedDailyForecast>>> fetchForecast(String city, String numDays) {
        return new NetworkBoundResource<List<SavedDailyForecast>, WeatherForecast>(appExecutors) {

            @Override
            protected void saveCallResult(@NonNull WeatherForecast item) {
                forecastDao.deleteNewsTable();
                if (item != null && item.getDailyForecasts() != null) {
                    List<SavedDailyForecast> savedDailyForecasts = new ArrayList<SavedDailyForecast>();
                    for (int i = 0; i < item.getDailyForecasts().size(); i++) {
                        SavedDailyForecast savedDailyForecast = new SavedDailyForecast();
                        savedDailyForecast.setLat(item.getCity().getCoord().getLat());
                        savedDailyForecast.setLon(item.getCity().getCoord().getLon());
                        savedDailyForecast.setDate(item.getDailyForecasts().get(i).getDt());
                        savedDailyForecast.setMaxTemp(item.getDailyForecasts().get(i).getTemp().getMax());
                        savedDailyForecast.setMinTemp(item.getDailyForecasts().get(i).getTemp().getMin());
                        savedDailyForecast.setDayTemp(item.getDailyForecasts().get(i).getTemp().getDay());
                        savedDailyForecast.setEveningTemp(item.getDailyForecasts().get(i).getTemp().getEve());
                        savedDailyForecast.setMorningTemp(item.getDailyForecasts().get(i).getTemp().getMorn());
                        savedDailyForecast.setNightTemp(item.getDailyForecasts().get(i).getTemp().getNight());
                        savedDailyForecast.setFeelslikeDay(item.getDailyForecasts().get(i).getFeelsLike().getDay());
                        savedDailyForecast.setFeelslikeEve(item.getDailyForecasts().get(i).getFeelsLike().getEve());
                        savedDailyForecast.setFeelslikeMorning(item.getDailyForecasts().get(i).getFeelsLike().getMorn());
                        savedDailyForecast.setFeelslikeNight(item.getDailyForecasts().get(i).getFeelsLike().getNight());
                        savedDailyForecast.setHumidity(item.getDailyForecasts().get(i).getHumidity());
                        savedDailyForecast.setWind(item.getDailyForecasts().get(i).getSpeed());
                        savedDailyForecast.setDescription(item.getDailyForecasts().get(i).getWeather().get(0).getDescription());
                        savedDailyForecast.setWeatherid(item.getDailyForecasts().get(i).getWeather().get(0).getId());
                        savedDailyForecast.setImageUrl(item.getDailyForecasts().get(i).getWeather().get(0).getIcon());
                        savedDailyForecasts.add(savedDailyForecast);
                    }
                    forecastDao.insertForecastList(savedDailyForecasts);
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable List<SavedDailyForecast> data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<SavedDailyForecast>> loadFromDb() {
                return forecastDao.loadForecast();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<WeatherForecast>> createCall() {
                return weatherService.getWeatherForecast(city, numDays, Constants.UNIT_SYSTEM, BuildConfig.WEATHER_API_KEY);
            }

            @Override
            protected void onFetchFailed() {

            }
        }.asLiveData();
    }

    public LiveData<Resource<UviDb>> fetchUvi(Double lat, Double lon) {
        return new NetworkBoundResource<UviDb, Uvi>(appExecutors) {

            @Override
            protected void saveCallResult(@NonNull Uvi item) {
                forecastDao.deleteUvi();
                if (item != null) {
                    UviDb uviDb = new UviDb();
                    uviDb.setLat(item.getLat());
                    uviDb.setLon(item.getLon());
                    uviDb.setValue(item.getValue());
                    forecastDao.insertUvi(uviDb);
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable UviDb data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<UviDb> loadFromDb() {
                return forecastDao.loadUvi();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<Uvi>> createCall() {
                return weatherService.getUvi(lat, lon, BuildConfig.WEATHER_API_KEY);
            }

            @Override
            protected void onFetchFailed() {

            }

        }.asLiveData();
    }
}
