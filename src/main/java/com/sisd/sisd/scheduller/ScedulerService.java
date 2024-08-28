package com.sisd.sisd.scheduller;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ScedulerService {
    @Scheduled(cron = "0 */1 * * * *")
    private void sample(){
        log.info("this is sample of scheduller");
    }
}
