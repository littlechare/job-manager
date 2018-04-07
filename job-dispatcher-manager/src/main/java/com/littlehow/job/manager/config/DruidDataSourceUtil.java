package com.littlehow.job.manager.config;

import com.alibaba.druid.pool.DruidDataSource;

public abstract class DruidDataSourceUtil {

    /**
     * 创建druid数据源
     * @param driverClass
     * @param url
     * @param user
     * @param password
     * @return
     */
    public final static DruidDataSource getDruidDataSource(String driverClass, String url,
                                                           String user, String password) {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return dataSource;
    }
}
