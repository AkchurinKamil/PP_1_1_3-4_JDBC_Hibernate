package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getConnection;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {
    }

    @Override
    public void createUsersTable() {
        String sql ="CREATE TABLE IF NOT EXISTS `testingdata`.`users`  (\n" +
                "  `id` int NOT NULL AUTO_INCREMENT,\n" +
                "  `name` varchar(45) DEFAULT NULL,\n" +
                "  `lastName` varchar(45) DEFAULT NULL,\n" +
                "  `age` int DEFAULT NULL,\n" +
                "  PRIMARY KEY (`id`)";

        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLSyntaxErrorException ex) {
            ex.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users ";
        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLSyntaxErrorException e) {
            e.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO users (name, lastName, age) VALUES(?, ?, ?)";
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        String sql = "delete FROM users where id=?";
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT id, name, lastName, age FROM users";
        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        String sql = "delete from users";
        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
