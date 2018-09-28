package hello;

import courses.Course;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CoursesControllerTest {

    @Test
    public void shouldFetchCourcesFromDao() {
        Course course = new Course(1,
                "Angular 2 Basics",
                "Introduction to Angular 2",
                "video",
                "2019-01-01",
                9000,
                true);
        CoursesDao coursesDaoMock = mock(CoursesDao.class);
        List<Course> expectedCourses = Collections.singletonList(course);
        when(coursesDaoMock.listCourses()).thenReturn(expectedCourses);
        CoursesController controller = new CoursesController(coursesDaoMock);

        List<Course> courses = controller.courses();

        assertEquals(expectedCourses, courses);
    }
}
