package com.example.demo.repository;

import com.example.demo.model.Profile;
import com.example.demo.model.User;
import com.example.demo.model.type.Gender;
import com.example.demo.model.type.Goal;
import com.example.demo.model.type.Lifestyle;
import com.example.demo.model.type.Role;
import com.example.demo.repository.api.ProfileRepository;
import com.example.demo.repository.apiDao.ProfileDao;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

import static com.example.demo.utils.ProfileSQLQuery.*;

@Repository
public class DefaultProfileRepository implements ProfileRepository {

    private final ProfileDao profileDao;

    public DefaultProfileRepository(ProfileDao profileDao) {
        this.profileDao = profileDao;
    }

    @Override
    public void save(Profile profile) {
        try (Connection con = ConnectionJDBC.getInstance();) {
            try (PreparedStatement preparedStatement = con.prepareStatement(save, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setObject(1, profile.getBirthday());
                preparedStatement.setInt(4, profile.getGender().ordinal());
                preparedStatement.setInt(5, profile.getGoal().ordinal());
                preparedStatement.setDouble(7, profile.getHeight());
                preparedStatement.setInt(8, profile.getLifestyle().ordinal());
                preparedStatement.setLong(9, profile.getUser().getId());
                preparedStatement.setDouble(10, profile.getWeightActual());
                preparedStatement.executeUpdate();
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys();) {
                    generatedKeys.next();
                    profile.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Ошибка работы с базой данных " + e.getMessage());
        }
    }

    @Override
    public void delete(Long id, long dtUpdate) {
        try (Connection con = ConnectionJDBC.getInstance();) {
            try (PreparedStatement preparedStatement = con.prepareStatement(delete)) {
                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Ошибка работы с базой данных " + e.getMessage());
        }
    }

    @Override
    public Profile get(Long id) {
        try (Connection con = ConnectionJDBC.getInstance();) {
            try (PreparedStatement preparedStatement = con.prepareStatement(get)) {
                preparedStatement.setLong(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                resultSet.next();
                Profile profile = new Profile();
                profile.setId(resultSet.getLong(1));
                profile.setBirthday(resultSet.getTimestamp(2).toLocalDateTime().toLocalDate());
                profile.setGender(Gender.values()[converterEnum(resultSet, 5)]);
                profile.setGoal(Goal.values()[converterEnum(resultSet, 6)]);
                profile.setHeight(resultSet.getDouble(8));
                profile.setLifestyle(Lifestyle.values()[converterEnum(resultSet, 9)]);
                profile.setWeightActual(resultSet.getDouble(10));
                User user = new User();
                user.setId(resultSet.getLong(11));
//                user.setDtCreate(resultSet.getTimestamp(12).toLocalDateTime());
//                user.setDtUpdate(resultSet.getTimestamp(13).toLocalDateTime());
                user.setLogin(resultSet.getString(14));
                user.setPassword(resultSet.getString(15));
                user.setRole(Role.values()[converterEnum(resultSet, 16)]);
                profile.setUser(user);
                return profile;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Ошибка работа с базы данных");
        }
    }

    @Override
    public void update(Profile updateProfile, long dtUpdate) {
        try (Connection con = ConnectionJDBC.getInstance();) {
            try (PreparedStatement preparedStatement = con.prepareStatement(update)) {
                preparedStatement.setObject(1, updateProfile.getBirthday());
//                preparedStatement.setObject(2, updateProfile.getDtCreate());
//                preparedStatement.setObject(3, updateProfile.getDtUpdate());
                preparedStatement.setInt(4, updateProfile.getGender().ordinal());
                preparedStatement.setDouble(7, updateProfile.getHeight());
                preparedStatement.setInt(8, updateProfile.getLifestyle().ordinal());
                preparedStatement.setLong(9, updateProfile.getUser().getId());
                preparedStatement.setDouble(10, updateProfile.getWeightActual());
                preparedStatement.setLong(11, updateProfile.getId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("Ошибка работы с базой данных " + e.getMessage());
        }
    }

    @Override
    public List<Profile> getAll() {
        return profileDao.findAll();
    }

    private int converterEnum(ResultSet resultSet, int index) throws SQLException {
        return Integer.parseInt(resultSet.getString(index));
    }
}
