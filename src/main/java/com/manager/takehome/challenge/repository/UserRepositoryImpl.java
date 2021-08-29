package com.manager.takehome.challenge.repository;

import com.manager.takehome.challenge.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

  @Autowired
  private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  @Override
  public List<User> findAllActiveUser() {

    MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
    mapSqlParameterSource.addValue("isActive", "%" + true + "%");

    return namedParameterJdbcTemplate.query("users.select manager_id, "
            + "users.first_name, users.last_name, user.email from users"
            + " where users.active = :isActive",
        mapSqlParameterSource,
        (rs, rowNum) ->
            new User.UserBuilder(
                rs.getLong("id"),
                rs.getString("firstName"),
                rs.getString("lastName")
            ).withEmailAddress(rs.getString("email"))
                .build()
    );
  }
}
