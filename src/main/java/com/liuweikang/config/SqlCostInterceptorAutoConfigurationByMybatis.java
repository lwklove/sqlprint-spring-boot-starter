package com.liuweikang.config;

import com.liuweikang.interceptor.SqlCostInterceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * @author liuweikang
 * @date 2021/03/16
 */
@Configuration
@EnableConfigurationProperties(SqlCostInterceptorProperties.class)
@ConditionalOnClass(name = {"org.apache.ibatis.session.SqlSessionFactory"})
@ConditionalOnMissingClass(value = {"com.github.pagehelper.autoconfigure.PageHelperAutoConfiguration"})
public class SqlCostInterceptorAutoConfigurationByMybatis {
    private static final Logger logger = LoggerFactory.getLogger(SqlCostInterceptorAutoConfigurationByMybatis.class);

    @Resource
    private SqlCostInterceptorProperties properties;

    @Resource
    private List<SqlSessionFactory> sqlSessionFactoryList;

    public SqlCostInterceptorAutoConfigurationByMybatis() {
        logger.info("SqlCostInterceptorAutoConfigurationByMybatis加载成功...");
    }

    @PostConstruct
    public void addMyInterceptor() {
        if (properties.isEnable()) {
            SqlCostInterceptor e = new SqlCostInterceptor();
            for (SqlSessionFactory sqlSessionFactory : sqlSessionFactoryList) {
                sqlSessionFactory.getConfiguration().addInterceptor(e);
            }
        }
    }
}
