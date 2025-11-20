package com.sonahlab.twitch_chat_hit_counter_course.config;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.kafka.core.KafkaTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Tag("Module3")
public class SqlConfigTest {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @Tag("Module3")
    /**
     * IMPORTANT: This bean gets Autoconfigured for us by Spring Kafka
     * {@link KafkaAutoConfiguration}
     *
     * At runtime Spring checks that we never created a {@link KafkaTemplate} Bean and will autoconfigure this bean for us.
     * @Bean
     * @ConditionalOnMissingBean(KafkaTemplate.class)
     * public KafkaTemplate<?, ?> kafkaTemplate(...) {}
     * */
    public void jdbcTemplateTest() throws SQLException {
        assertTrue(context.containsBean("jdbcTemplate"));

        DataSource dataSource = jdbcTemplate.getDataSource();
        assertNotNull(dataSource);

        HikariDataSource hikariDataSource = dataSource.unwrap(HikariDataSource.class);
        assertNotNull(hikariDataSource);

        assertEquals("jdbc:mysql://localhost:3306/dev_db", hikariDataSource.getJdbcUrl());
        assertEquals("root", hikariDataSource.getUsername());
        assertEquals(null, hikariDataSource.getPassword());
    }
}

