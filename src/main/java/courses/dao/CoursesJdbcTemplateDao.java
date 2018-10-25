package courses.dao;

import courses.model.Course;
import courses.model.CourseRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

//TODO: make tests pass
@Component
class CoursesJdbcTemplateDao implements CoursesDao {

    private final JdbcTemplate jdbcTemplate;

    CoursesJdbcTemplateDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Course> listCourses() {
        String sqlSelectAll = "SELECT * FROM Courses";
        return jdbcTemplate.query(sqlSelectAll, new CourseRowMapper());
    }

    @Override
    public Course find(Long id) {
        String selectSql = "SELECT * FROM Courses WHERE id = ?";
        return jdbcTemplate.queryForObject(selectSql,
                new Object[]{id},
                new CourseRowMapper());
    }

    @Override
    public Course save(Course course) {
        String getCoursesDbIdSql = "SELECT * FROM Courses WHERE name = ?";
        String insertSql = "INSERT INTO Courses (name,description,type,date,durationInSeconds,topRated) VALUES (?,?,?,?,?,?)";

        jdbcTemplate.update(insertSql, course.getId(),
                course.getName(),
                course.getDescription(),
                course.getType(),
                course.getDate(),
                course.getDurationInSeconds(),
                course.isTopRated());

        return jdbcTemplate.queryForObject(
                getCoursesDbIdSql,
                new Object[]{course.getName()},
                new CourseRowMapper());
    }

    @Override
    public Course update(Long id, Course modifiedCourse) {
        String updateCourse = " UPDATE COURSES SET name = ?, description = ?, type = ?, date = ?, durationInSeconds = ?, topRated = ? WHERE id = ?";

        jdbcTemplate.update(updateCourse, id,
                modifiedCourse.getName(),
                modifiedCourse.getDescription(),
                modifiedCourse.getType(),
                modifiedCourse.getDate(),
                modifiedCourse.getDurationInSeconds(),
                modifiedCourse.isTopRated());

        return find(id);
    }

    @Override
    public Course delete(Long id) {
        Course deleted = find(id);

        String deleteSql = "DELETE FROM Courses WHERE id = ?";
        jdbcTemplate.update(deleteSql, id);

        return deleted;
    }
}
