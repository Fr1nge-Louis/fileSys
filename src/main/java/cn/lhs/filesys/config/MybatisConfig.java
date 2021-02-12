package cn.lhs.filesys.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("cn.lhs.filesys.dao")
@Slf4j
public class MybatisConfig {

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        log.debug("分页插件初始化完成");
        return new PaginationInterceptor();
    }
}
