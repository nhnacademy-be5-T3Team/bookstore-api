package com.t3t.bookstoreapi.config;

import com.t3t.bookstoreapi.property.DatabaseProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;

/**
 * DataSource 설정 클래스
 * @author woody35545(구건모)
 */
@Configuration
public class DataSourceConfig {

    /**
     * DataSource 빈 등록
     * @param databaseProperties profile 이 적용된 DatabaseProperties
     * @return DataSource
     * @author woody35545(구건모)
     */
    @Bean
    public DataSource dataSource(DatabaseProperties databaseProperties){
        return DataSourceBuilder.create()
                .url(databaseProperties.getDatabaseUrl())
                .driverClassName(databaseProperties.getDriverClassName())
                .username(databaseProperties.getUsername())
                .password(databaseProperties.getPassword())
                .build();
    }
}