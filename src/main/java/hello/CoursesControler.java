package hello;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import courses.Course;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CoursesControler {
    private final AtomicLong counter = new AtomicLong();
    private List<Course> courses = new ArrayList<>();

    @RequestMapping("/courses")
    public List<Course> courses() {

        courses.add(new Course(
                counter.incrementAndGet(),
                "Angular 2 Basics",
                "Introduction to Angular 2",
                "video",
                "2019-01-01",
                9000,
                true));
        courses.add(new Course(
                counter.incrementAndGet(),
                "Angular Materials Basics",
                "Introduction to Angular Materials",
                "video",
                "2019-04-01",
                2700,
                false));

        return courses;

    }
}
