package courses.dao;

import courses.model.Course;
import org.hsqldb.jdbc.JDBCDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

public class CoursesJdbcTemplateDaoTest {

    private final Course COURSE = new Course(0,
            "Test Name1",
            "Introduction to Angular 2",
            "video",
            "2019-01-01",
            9000,
            true);

    private final Course WRONG_COURSE = new Course(2,
            "Test Naaame1",
            "Introdauction to Angular 2",
            "viadeo",
            "2019-01-01",
            90030,
            true);

    CoursesJdbcTemplateDao coursesDbDao = null;

    @Before
    public void init() throws Exception {
        JDBCDataSource dataSource = new JDBCDataSource();
        dataSource.setUrl("jdbc:hsqldb:mem:mymemdb");
        dataSource.setUser("sa");
        dataSource.setPassword("");
        try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE COURSES (ID INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 0) NOT NULL PRIMARY KEY, " +
                    "name VARCHAR(50) NOT NULL," +
                    "DESCRIPTION VARCHAR(255) NOT NULL," +
                    "TYPE VARCHAR(255) NOT NULL," +
                    "DATE VARCHAR(10) NOT NULL," +
                    "DURATIONINSECONDS INTEGER NOT NULL," +
                    "TOPRATED BOOLEAN NOT NULL)");
        }
        coursesDbDao = new CoursesJdbcTemplateDao(new JdbcTemplate(dataSource));
    }

    @After
    public void shutdown() throws Exception {
        JDBCDataSource dataSource = new JDBCDataSource();
        dataSource.setUrl("jdbc:hsqldb:mem:mymemdb");
        dataSource.setUser("sa");
        dataSource.setPassword("");
        try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE COURSES");
        }
    }

    @Test
    public void save_shouldAddCourseToDb() {
        Course actualCourse = coursesDbDao.save(COURSE);
        assertEquals(COURSE, actualCourse);
    }

    @Test
    public void find_shouldFindCourseById() {
        coursesDbDao.save(COURSE);
        Course actualCourse = coursesDbDao.find(0L);
        assertEquals(COURSE, actualCourse);
    }

    @Test
    public void update_shouldDoesNotFindOldCourse() {
        coursesDbDao.save(WRONG_COURSE);
        coursesDbDao.update(0L, COURSE);
        assertNotEquals(WRONG_COURSE, coursesDbDao.find(0L));
    }

    @Test(expected = DaoException.class)
    public void update_shouldNotUpdateCourseById() {
        coursesDbDao.save(WRONG_COURSE);
        coursesDbDao.update(2L, COURSE);
    }

    @Test
    public void update_shouldUpdateCourseById() {
        coursesDbDao.save(WRONG_COURSE);
        Course actualCourse = coursesDbDao.update(0L, COURSE);
        assertEquals(coursesDbDao.find(0L), actualCourse);
    }

    @Test
    public void delete_shouldDeleteCourseById() {
        coursesDbDao.save(COURSE);
        Course actualCourse = coursesDbDao.delete(0L);
        assertEquals(COURSE, actualCourse);
    }

    @Test
    public void delete_shouldNotFindDeletedCourseById() {
        coursesDbDao.save(COURSE);
        coursesDbDao.delete(0L);

        assertNull(coursesDbDao.find(0L));
    }

    @Test
    public void listCourses_selectRecordsFromDBTest() throws Exception {
        int expSize = 1;
        Course expCourse = new Course(0,
                "Test Name1",
                "Introduction to Angular 2",
                "video",
                "2019-01-01",
                9000,
                true);
        coursesDbDao.save(expCourse);

        List<Course> courseList = coursesDbDao.listCourses();

        assertEquals(expSize, courseList.size());
        assertEquals(expCourse, courseList.get(0));
    }
}
