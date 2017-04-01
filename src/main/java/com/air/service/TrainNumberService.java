package com.air.service;

import com.air.po.TrainNumber;

import java.util.List;

/**
 * Created by linux on 2017年03月28日.
 * Time 01:06
 */
public interface TrainNumberService {
    TrainNumber getTrainById(Long id);

    List<TrainNumber> listTrains(TrainNumber trainNumber);

    void updateTrainInfo(TrainNumber trainNumber);
}
