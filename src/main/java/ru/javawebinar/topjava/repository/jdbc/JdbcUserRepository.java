package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.sql.PreparedStatement;
import java.util.*;

@Repository
public class JdbcUserRepository implements UserRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    @Transactional
    public User save(User user) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);

        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
            addRoles(user);
        } else if (namedParameterJdbcTemplate.update("""
                   UPDATE users SET name=:name, email=:email, password=:password, 
                   registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id
                """, parameterSource) == 0) {
            return null;
        }
        deleteRoles(user);
        addRoles(user);
        return user;
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    @Transactional
    public User get(int id) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE id=?", ROW_MAPPER, id);
        return setRolesToUser(DataAccessUtils.singleResult(users));
    }

    @Override
    @Transactional
    public User getByEmail(String email) {
//        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        return setRolesToUser(DataAccessUtils.singleResult(users));
    }

    @Override
    @Transactional
    public List<User> getAll() {
        List<User> users = jdbcTemplate.query("SELECT * FROM users ORDER BY name, email", ROW_MAPPER);
        return getRolesToUsersList(users);
    }

    private User setRolesToUser(User user) {
        if (user != null) {
            List<Role> roles = jdbcTemplate.queryForList("SELECT role FROM user_roles WHERE user_id=?",
                    Role.class, user.getId());
            user.setRoles(roles);
        }
        return user;
    }

    private List<User> getRolesToUsersList(List<User> users) {
        Map<Integer, Set<Role>> roleMap = new HashMap<>();
        SqlRowSet row = jdbcTemplate.queryForRowSet("SELECT * FROM user_roles");
        for (User user : users) {
            Set<Role> roles = new HashSet<>();
            roleMap.put(user.getId(), roles);
        }
        while (row.next()) {
            int id = row.getInt("user_id");
            Set<Role> r = roleMap.get(id);
            r.add(Role.valueOf(row.getString("role")));
        }
        for(User user : users) {
            user.setRoles(roleMap.get(user.id()));
        }
        return users;
    }

    private void addRoles(User user) {
        Set<Role> roles = user.getRoles();
        if (!roles.isEmpty()) {
            jdbcTemplate.batchUpdate("INSERT INTO user_roles (user_id, role) VALUES (?, ?)",
                    roles, roles.size(), (statement, role) -> {
                        statement.setInt(1, user.id());
                        statement.setString(2, role.name());
                    });
        }
    }

    private void deleteRoles(User u) {
        jdbcTemplate.update("DELETE FROM user_roles WHERE user_id=?", u.getId());
    }

}
