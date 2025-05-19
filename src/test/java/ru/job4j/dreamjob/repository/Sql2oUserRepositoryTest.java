package ru.job4j.dreamjob.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.sql2o.Sql2o;
import ru.job4j.dreamjob.configuration.DatasourceConfiguration;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.repository.user.Sql2oUserRepository;

import java.util.Properties;

import static org.assertj.core.api.Assertions.*;

class Sql2oUserRepositoryTest {
private static Sql2oUserRepository sql2oUserRepository;
private static Sql2o sql2o;

@BeforeAll
public static void init() throws Exception {
    var properties = new Properties();
    try (var inputStream = Sql2oUserRepositoryTest.class.getClassLoader()
            .getResourceAsStream("connection.properties")) {
        properties.load(inputStream);
    }
    var url = properties.getProperty("datasource.url");
    var username = properties.getProperty("datasource.username");
    var password = properties.getProperty("datasource.password");

    var configuration = new DatasourceConfiguration();
    var datasource = configuration.connectionPool(url, username, password);
        sql2o = configuration.databaseClient(datasource);

    sql2oUserRepository = new Sql2oUserRepository(sql2o);
}

@AfterEach
public void clearUsers() {
   try (var connection = sql2o.open()) {
       connection.createQuery("delete from users").executeUpdate();
   }
}

    @Test
    public void whenSaveThenGetSame() {
        var user = new User(0, "first@yandex.ru", "mrFirst", "first_1");
        var savedUser = sql2oUserRepository.save(user);
        user.setId(savedUser.get().getId());
        assertThat(savedUser.get()).usingRecursiveAssertion().isEqualTo(user);
    }

    @Test
    public void whenSaveSeveralThenGetByEmailAndPassword() {
        var user1 = sql2oUserRepository.save(new User(0, "first@yandex.ru", "mrFirst", "first_1"));
        var user2 = sql2oUserRepository.save(new User(0, "second@yandex.ru", "mrSecond", "second_2"));
        var user3 = sql2oUserRepository.save(new User(0, "third@yandex.ru", "mrThird", "third_3"));
        var savedUser1 = sql2oUserRepository.findByEmailAndPassword(user1.get().getEmail(), user1.get().getPassword()).get();
        var savedUser2 = sql2oUserRepository.findByEmailAndPassword(user2.get().getEmail(), user2.get().getPassword()).get();
        var savedUser3 = sql2oUserRepository.findByEmailAndPassword(user3.get().getEmail(), user3.get().getPassword()).get();
        assertThat(savedUser1).usingRecursiveAssertion().isEqualTo(user1.get());
        assertThat(savedUser2).usingRecursiveAssertion().isEqualTo(user2.get());
        assertThat(savedUser3).usingRecursiveAssertion().isEqualTo(user3.get());
    }

    @Test
    public void whenSaveSameUsersThenError() {
        sql2oUserRepository.save(new User(0, "first@yandex.ru", "mrFirst", "first_1"));
        assertThat(sql2oUserRepository.save(new User(0, "first@yandex.ru", "mrSecond", "second_2")).isEmpty());
    }
}
