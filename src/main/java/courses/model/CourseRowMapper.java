package courses.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseRowMapper implements RowMapper<Course> {
    @Override
    public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Course(
                rs.getInt("ID"),
                rs.getString("NAME"),
                rs.getString("DESCRIPTION"),
                rs.getString("TYPE"),
                rs.getString("DATE"),
                rs.getInt("DURATIONINSECONDS"),
                rs.getBoolean("TOPRATED"));
    }
}
