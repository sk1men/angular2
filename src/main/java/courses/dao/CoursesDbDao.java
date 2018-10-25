package courses.dao;

import com.google.common.flogger.FluentLogger;
import courses.model.Course;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides access to courses in HyperSQL db.
 */
@Component
public class CoursesDbDao implements CoursesDao {
    private static final FluentLogger logger = FluentLogger.forEnclosingClass();

    private final DataSource dataSource;

    CoursesDbDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Course save(Course course) {

        String getCoursesDbId = "SELECT * FROM Courses WHERE name = ?";
        String insertSql = "INSERT INTO Courses (name,description,type,date,durationInSeconds,topRated) VALUES (?,?,?,?,?,?)";
        try (Connection dbConn = dataSource.getConnection();
             PreparedStatement preparedStatement = dbConn.prepareStatement(insertSql);
             PreparedStatement getIdSqlPS = dbConn.prepareStatement(getCoursesDbId)) {

            preparedStatement.setString(1, course.getName());
            preparedStatement.setString(2, course.getDescription());
            preparedStatement.setString(3, course.getType());
            preparedStatement.setString(4, course.getDate());
            preparedStatement.setInt(5, course.getDurationInSeconds());
            preparedStatement.setBoolean(6, course.isTopRated());
            preparedStatement.executeUpdate();

            getIdSqlPS.setString(1, course.getName());

            ResultSet rs = getIdSqlPS.executeQuery();
            if (rs.next()) {
                if (rs.getString("name").equals(course.getName()) && rs.getString("date").equals(course.getDate())) {
                    logger.atInfo().log("Course %s was successfully saved.", course.getName());
                }
                return new Course(rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("type"),
                        rs.getString("date"),
                        rs.getInt("durationInSeconds"),
                        rs.getBoolean("topRated"));
            }
        } catch (SQLException ex) {
            throw new DaoException("Exception saving course", ex);
        }
        logger.atWarning().log("Course %s was not saved.", course.getName());
        throw new DaoException("Course %s was not saved");
    }

    @Override
    public List<Course> listCourses() {
        String sql = "SELECT * FROM Courses";
        List<Course> courseList = new ArrayList<>();
        try (Connection dbConn = dataSource.getConnection();
             Statement statement = dbConn.createStatement()) {
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                courseList.add(new Course(
                        rs.getInt("ID"),
                        rs.getString("NAME"),
                        rs.getString("DESCRIPTION"),
                        rs.getString("TYPE"),
                        rs.getString("DATE"),
                        rs.getInt("DURATIONINSECONDS"),
                        rs.getBoolean("TOPRATED")));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        logger.atInfo().log("Successfully read %d courses.", courseList.size());
        return courseList;
    }

    @Override
    public Course delete(Long id) {
        String deleteSql = "DELETE FROM COURSES WHERE id  = ?";
        try (Connection dbConn = dataSource.getConnection(); PreparedStatement deletePreparedStatement = dbConn.prepareStatement(deleteSql)) {
            Course deletedCourse = find(id);
            deletePreparedStatement.setLong(1, id);
            deletePreparedStatement.executeUpdate();
            logger.atInfo().log("Course with id#%d was deleted.", id);
            return deletedCourse;
        } catch (SQLException e) {
            throw new DaoException("Exception saving course.", e);
        }
    }

    @Override
    public Course find(Long id) {
        String selectCourse = "SELECT * FROM COURSES WHERE id  = ?";
        try (Connection dbConn = dataSource.getConnection();
             PreparedStatement preparedStatement = dbConn.prepareStatement(selectCourse)) {

            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                logger.atInfo().log("Course with id#%d was found.", id);
                return new Course(rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("type"),
                        rs.getString("date"),
                        rs.getInt("durationInSeconds"),
                        rs.getBoolean("topRated"));
            }
            return null;
        } catch (SQLException e) {
            logger.atWarning().log("Course with id#%d was not found.", id);
            throw new DaoException(e);
        }
    }

    @Override
    public Course update(Long id, Course modifiedCourse) {
        String updateCourse = " UPDATE COURSES SET name = ?, description = ?, " +
                "type = ?, date = ?, durationInSeconds = ?, topRated = ? WHERE id = ?";

        try (Connection dbConn = dataSource.getConnection();
             PreparedStatement preparedStatement = dbConn.prepareStatement(updateCourse)) {

            preparedStatement.setString(1, modifiedCourse.getName());
            preparedStatement.setString(2, modifiedCourse.getDescription());
            preparedStatement.setString(3, modifiedCourse.getType());
            preparedStatement.setString(4, modifiedCourse.getDate());
            preparedStatement.setInt(5, modifiedCourse.getDurationInSeconds());
            preparedStatement.setBoolean(6, modifiedCourse.isTopRated());
            preparedStatement.setLong(7, id);

            if (preparedStatement.executeUpdate() > 0) {
                logger.atInfo().log("Course with id#%d was updated.", id);
                return new Course(id,
                        modifiedCourse.getName(),
                        modifiedCourse.getDescription(),
                        modifiedCourse.getType(),
                        modifiedCourse.getDate(),
                        modifiedCourse.getDurationInSeconds(),
                        modifiedCourse.isTopRated());
            } else {
                logger.atWarning().log("Course with id#%d was not updated.", id);
                throw new DaoException(String.format("Course with id#%d does not exist.", id));
            }
        } catch (SQLException e) {
            logger.atWarning().log("Course with id#%d was not updated.", id);
            throw new DaoException(e);
        }
    }
}
