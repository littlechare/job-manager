package com.littlehow.job.manager.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({JobDataSourceConfiguration.class})
public class DaoBaseApplicationConfiguration {

}
