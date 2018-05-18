package org.hengsir.icma.timmer;

import org.hengsir.icma.dao.IdentifyDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author hengsir
 * @date 2018/5/17 下午2:57
 */
@Service
public class RecordToHistTimmer {

    Logger logger = LoggerFactory.getLogger(RecordToHistTimmer.class);

    @Autowired
    private IdentifyDao identifyDao;

    /**
     * 每天0点把识别记录存到历史表中
     */
    @Scheduled(cron = "59 59 23 * * ?")
    public void recordToHist(){
        logger.info("---------------开始转移今天记录到历史表---------------");
        boolean isToHist = identifyDao.toHist();
        if (isToHist){
            logger.info("---------------转移记录成功---------------");
            boolean isClear = identifyDao.clearRecord();
            logger.info(isClear ? "---------------清空当天记录成功---------------" : "---------------清空当天记录失败---------------");
        } else {
            logger.error("---------------转移记录失败---------------");
        }
    }
}
