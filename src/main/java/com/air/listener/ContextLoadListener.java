package com.air.listener;

import com.air.po.TrainNumber;
import com.air.service.TrainNumberService;
import com.air.service.impl.TrainNumberServiceImpl;
import com.air.utils.ConstantUtils;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import java.util.List;

/**
 * Created by linux on 2017年04月01日.
 * Time 16:19
 * 监听器,用于服务启动时将车次库存等信息放入队列中
 */
public class ContextLoadListener extends ContextLoaderListener {
    //获取spring注入的bean对象
    private WebApplicationContext springContext;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        springContext = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
        trainRepoInit();
    }

    void trainRepoInit() {
        TrainNumberService trainNumberService = springContext.getBean(TrainNumberServiceImpl.class);
        if (trainNumberService != null) {
            List<TrainNumber> trainNumbers = trainNumberService.listTrains(new TrainNumber());
            if (trainNumbers != null && trainNumbers.size() > 0) {
                for (TrainNumber trainNumber : trainNumbers) {
                    ConstantUtils.trainTicketRepoArrayQueue.put(trainNumber.getId(), trainNumber.getSeatsNumber());
                }
            }
        }
    }
}
