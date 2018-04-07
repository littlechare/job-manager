package com.littlehow.job.manager.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

import static com.littlehow.job.manager.config.DruidDataSourceUtil.getDruidDataSource;

/**
 * 扫描 Mapper 接口并容器管理
 * littlehow 2018/4/4
 */
@Configuration
@PropertySource(value = "classpath:database.properties")
@MapperScan(basePackages = "com.littlehow.job.manager.mapper.job", sqlSessionFactoryRef = "jobSqlSessionFactory")
public class JobDataSourceConfiguration {

  static final String MAPPER_LOCATION = "classpath:mybatis/sql-map/job/*.xml";

  @Value("${job.datasource.url:127.0.0.1}")
  private String url;

  @Value("${job.datasource.username:job}")
  private String user;

  @Value("${job.datasource.password:job123}")
  private String password;

  @Value("${datasource.driverClassName:com.mysql.jdbc.Driver}")
  private String driverClass;

  @Bean(name = "jobDataSource")
  @Primary
  public DataSource jobDataSource() {
    return getDruidDataSource(driverClass, url, user, password);
  }

  @Bean(name = "jobTransactionManager")
  @Primary
  public DataSourceTransactionManager jobTransactionManager() {
    return new DataSourceTransactionManager(jobDataSource());
  }

  @Bean(name = "jobSqlSessionFactory")
  @Primary
  public SqlSessionFactory jobSqlSessionFactory(
      @Qualifier("jobDataSource") DataSource dataSource)
      throws Exception {
    final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
    sessionFactory.setDataSource(dataSource);
    sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
        .getResources(MAPPER_LOCATION));
    return sessionFactory.getObject();
  }
}
