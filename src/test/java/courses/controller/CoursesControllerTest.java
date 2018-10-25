package courses.controller;

import courses.model.Course;
import courses.dao.CoursesDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class CoursesControllerTest {

    private Course course = new Course(1,
            "Angular 2 Basics",
            "Introduction to Angular 2",
            "video",
            "2017-01-01",
            9000,
            true);

    private MockMvc mockMvc;
    private CoursesDao coursesDaoMock;

    @Before
    public void mockSpringMvc() {
        coursesDaoMock = mock(CoursesDao.class);
        CoursesController controller = new CoursesController(coursesDaoMock);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void getCourses_shouldFetchCoursesFromDao() throws Exception {
        when(coursesDaoMock.listCourses()).thenReturn(Collections
                .singletonList(course));

        this.mockMvc.perform(get("/courses"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("[{\"id\":1," +
                        "\"name\":\"Angular 2 Basics\"," +
                        "\"description\":\"Introduction to Angular 2\"," +
                        "\"type\":\"video\"," +
                        "\"date\":\"2017-01-01\",\"durationInSeconds\":9000," +
                        "\"topRated\":true}]")));

        verify(coursesDaoMock).listCourses();
    }

    @Test
    public void getCourse_shouldFetchOneCourseFromDao() throws Exception {
        when(coursesDaoMock.find(1L)).thenReturn(course);

        this.mockMvc.perform(
                get("/courses/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("{\"id\":1," +
                        "\"name\":\"Angular 2 Basics\"," +
                        "\"description\":\"Introduction to Angular 2\"," +
                        "\"type\":\"video\"," +
                        "\"date\":\"2017-01-01\",\"durationInSeconds\":9000," +
                        "\"topRated\":true}")));

        verify(coursesDaoMock).find(1L);
    }

    @Test
    public void createCourse_shouldSaveTheCourse() throws Exception {
        when(coursesDaoMock.save(course)).thenReturn(course);

        this.mockMvc.perform(
                post("/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1," +
                                "\"name\":\"Angular 2 Basics\"," +
                                "\"description\":\"Introduction to Angular 2\"," +
                                "\"type\":\"video\"," +
                                "\"date\":\"2017-01-01\"," +
                                "\"durationInSeconds\":9000," +
                                "\"topRated\":true}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("{\"id\":1," +
                        "\"name\":\"Angular 2 Basics\"," +
                        "\"description\":\"Introduction to Angular 2\"," +
                        "\"type\":\"video\"," +
                        "\"date\":\"2017-01-01\",\"durationInSeconds\":9000," +
                        "\"topRated\":true}")));

        verify(coursesDaoMock).save(
                new Course(1,
                        "Angular 2 Basics",
                        "Introduction to Angular 2",
                        "video",
                        "2017-01-01",
                        9000,
                        true));
    }

    @Test
    public void deleteCourse_shouldDeleteTheCourse() throws Exception {
        when(coursesDaoMock.delete(1L)).thenReturn(course);

        this.mockMvc.perform(
                delete("/courses/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("{\"id\":1," +
                        "\"name\":\"Angular 2 Basics\"," +
                        "\"description\":\"Introduction to Angular 2\"," +
                        "\"type\":\"video\"," +
                        "\"date\":\"2017-01-01\",\"durationInSeconds\":9000," +
                        "\"topRated\":true}")));

        verify(coursesDaoMock).delete(1L);
    }

    @Test
    public void updateCourse_shouldUpdateTheCourse() throws Exception {
        when(coursesDaoMock.update(anyLong(), any(Course.class))).thenReturn(course);

        this.mockMvc.perform(
                put("/courses/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1," +
                                "\"name\":\"Angular 2 Basics\"," +
                                "\"description\":\"Introduction to Angular 2\"," +
                                "\"type\":\"video\"," +
                                "\"date\":\"2017-01-01\"," +
                                "\"durationInSeconds\":9000," +
                                "\"topRated\":true}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("{\"id\":1," +
                        "\"name\":\"Angular 2 Basics\"," +
                        "\"description\":\"Introduction to Angular 2\"," +
                        "\"type\":\"video\"," +
                        "\"date\":\"2017-01-01\",\"durationInSeconds\":9000," +
                        "\"topRated\":true}")));

        verify(coursesDaoMock).update(1L, new Course(
                1,
                "Angular 2 Basics",
                "Introduction to Angular 2",
                "video",
                "2017-01-01",
                9000,
                true));
    }
}
