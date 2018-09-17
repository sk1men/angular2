package hello;

import courses.Course;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CoursesControlerTest {

    @Test
    public void checkCoursesFields() {

        Course coursesControler1 = new Course(1,
                "Angular 2 Basics",
                "Introduction to Angular 2",
                "video",
                "2019-01-01",
                9000,
                true);
        Course coursesControler2 = new Course(2,
                "Angular Materials Basics",
                "Introduction to Angular Materials",
                "video",
                "2019-04-01",
                2700,
                false);
        List<Course> expectedList = new ArrayList<>();
        expectedList.add(coursesControler1);
        expectedList.add(coursesControler2);

        CoursesControler coursesControler = new CoursesControler();
        List<Course> courseList = coursesControler.courses();

        assertEquals(expectedList, courseList);
    }

}