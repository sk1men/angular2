package courses.dao;

import courses.model.Course;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

public class CoursesFileDaoTest {

    @Test
    public void shouldReturnSevenCourses() {
        CoursesFileDao coursesFileDao = new CoursesFileDao();

        List<Course> courseList = coursesFileDao.listCourses();

        assertEquals(7, courseList.size());
    }

    @Test
    public void shouldParseCoursesSuccessfully() {
        Course courses1 = new Course(1,
                "Angular 2 Basics",
                "Introduction to Angular 2",
                "video",
                "2019-01-01",
                9000,
                true);
        Course courses2 = new Course(2,
                "Angular Materials Basics",
                "Introduction to Angular Materials",
                "video",
                "2019-04-01",
                2700,
                false);
        Course courses3 = new Course(3,
                "TypeScript Basics",
                "Introduction to TypeScript",
                "video",
                "2019-03-01",
                5400,
                true);
        Course courses4 = new Course(4,
                "JavaScript Basics",
                "Introduction to JavaScript",
                "video",
                "2018-12-01",
                3600,
                false);
        Course courses5 = new Course(5,
                "Unrelated course",
                "Outdated description",
                "text",
                "2017-12-01",
                1800,
                false);
        Course courses6 = new Course(6,
                "RxJS In Practice",
                "Observables and Subjects",
                "video",
                "2019-01-01",
                14400,
                false);
        Course courses7 = new Course(7,
                "Ngrx Architecture",
                "Ngrx style application pros and cons",
                "video",
                "2019-06-01",
                7200,
                false);
        List<Course> expectedList = Arrays.asList(courses1, courses2, courses3, courses4, courses5, courses6, courses7);

        List<Course> actualList = new CoursesFileDao().listCourses();

        assertEquals(expectedList, actualList);
    }

    @Test
    public void shouldAddCoursesToFile() {
        Course newCourse = new Course(1,
                "Testing course",
                "Mockito testing",
                "video",
                "2019-01-01",
                9000,
                true);
        CoursesFileDao dao = new CoursesFileDao();

        Course savedCourse = dao.save(newCourse);

        assertEquals(8, dao.listCourses().size());
        assertEquals(savedCourse, dao.find(savedCourse.getId()));
        dao.delete(savedCourse.getId());
    }

    @Test
    public void shouldReturnCourseById() {
        Course expectedCourse = new Course(1,
                "Angular 2 Basics",
                "Introduction to Angular 2",
                "video",
                "2019-01-01",
                9000,
                true);
        CoursesFileDao dao = new CoursesFileDao();

        Course actualCourse = dao.find(1L);

        assertEquals(expectedCourse, actualCourse);
    }

    @Test
    public void shouldDeleteCourseById() {
        CoursesFileDao dao = new CoursesFileDao();
        Course courseToDelete = dao.save(new Course(0,
                "Testing course",
                "Mockito testing",
                "video",
                "2019-01-01",
                9000,
                true));
        int oldSize = dao.listCourses().size();
        long courseToDeleteId = courseToDelete.getId();

        Course deletedCourse = dao.delete(courseToDeleteId);

        assertEquals(oldSize - 1, dao.listCourses().size());
        assertEquals(deletedCourse, courseToDelete);
        assertNull(dao.find(courseToDeleteId));
    }

    @Test
    public void shouldReplaceCourseByIdAndValue() {
        long courseId = 2;
        CoursesFileDao dao = new CoursesFileDao();
        Course oldCourse = dao.find(courseId);
        Course expectedCourse = new Course(
                courseId,
                "name",
                "multiline\r\ndescription",
                "text",
                "2000-01-01",
                60000,
                !oldCourse.isTopRated());

        Course updated = dao.update(courseId, expectedCourse);

        assertEquals(expectedCourse, updated);
        assertEquals(expectedCourse, dao.find(courseId));
        assertNotEquals(expectedCourse, oldCourse);
        dao.update(courseId, oldCourse);
    }
}
