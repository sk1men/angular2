package hello;

import courses.Course;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

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
}
