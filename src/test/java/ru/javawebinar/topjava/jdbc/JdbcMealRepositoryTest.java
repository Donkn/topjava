package ru.javawebinar.topjava.jdbc;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.jdbc.JdbcMealRepository;
import ru.javawebinar.topjava.web.SecurityUtil;
import org.junit.*;

import javax.sql.DataSource;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})

@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))

public class JdbcMealRepositoryTest{

     @Autowired
    JdbcMealRepository repository;

    @Test
    public void getAll() {
        Assert.assertNull(repository.getAll(SecurityUtil.authUserId()));
        Assert.assertEquals(2, repository.getAll(SecurityUtil.authUserId()).size());
    }
}
