package com.deepseat.server.DeepSeatServer.config

import org.apache.ibatis.session.SqlSessionFactory
import org.mybatis.spring.SqlSessionFactoryBean
import org.mybatis.spring.SqlSessionTemplate
import org.mybatis.spring.annotation.MapperScan
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource


@Configuration
@MapperScan(basePackages = ["com.deepseat.server.DeepSeatServer.dao"])
class MybatisConfig {
    @Value("\${spring.datasource.mapper-locations}")
    private lateinit var path: String

    @Bean
    @Throws(Exception::class)
    fun sqlSessionFactory(dataSource: DataSource?, context: ApplicationContext): SqlSessionFactory? {
        val sqlSessionFactory = SqlSessionFactoryBean()
        sqlSessionFactory.setDataSource(dataSource)
        sqlSessionFactory.setTypeAliasesPackage("com.deepseat.server.DeepSeatServer.vo")
        sqlSessionFactory.setMapperLocations(*context.getResources(path))
        return sqlSessionFactory.getObject()
    }

    @Bean
    fun sqlSession(sqlSessionFactory: SqlSessionFactory?): SqlSessionTemplate {
        return SqlSessionTemplate(sqlSessionFactory)
    }
}
