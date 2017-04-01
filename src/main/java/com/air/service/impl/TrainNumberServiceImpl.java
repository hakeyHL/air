package com.air.service.impl;

import com.air.dao.IDataBaseManager;
import com.air.po.TrainNumber;
import com.air.service.TrainNumberService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by linux on 2017年03月28日.
 * Time 01:07
 */
@Service
public class TrainNumberServiceImpl implements TrainNumberService {
    private final String TRAIN_GET_BASE_SQL = "select t from TrainNumber  t where 1=1 ";

    @Resource
    IDataBaseManager<TrainNumber> dataBaseManager;

    public TrainNumber getTrainById(Long id) {
        return dataBaseManager.get(TrainNumber.class, id);
    }

    public List<TrainNumber> listTrains(TrainNumber trainNumber) {
        StringBuilder stringBuilder = new StringBuilder(TRAIN_GET_BASE_SQL);
        List params = new LinkedList();
        int i = 0;
        if (trainNumber != null) {
            if (StringUtils.isNotEmpty(trainNumber.getName())) {
                stringBuilder.append(" and t.name like ?").append(i);
                params.add("%" + trainNumber.getName() + "%");
                ++i;
            }

            if (StringUtils.isNotEmpty(trainNumber.getStartSite())) {
                stringBuilder.append(" and t.startSite like ?").append(i);
                params.add("%" + trainNumber.getStartSite() + "%");
                ++i;
            }

            if (StringUtils.isNotEmpty(trainNumber.getEndSite())) {
                stringBuilder.append(" and t.endSite like ?").append(i);
                params.add("%" + trainNumber.getEndSite() + "%");
                ++i;
            }

        }
        return dataBaseManager.query(stringBuilder.toString(), params);
    }

    /**
     * 更新车次信息
     *
     * @param trainNumber
     */
    @Transactional
    public void updateTrainInfo(TrainNumber trainNumber) {
        dataBaseManager.update(trainNumber);
    }
}
