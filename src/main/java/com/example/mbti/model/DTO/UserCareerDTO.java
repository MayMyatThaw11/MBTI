package com.example.mbti.model.DTO;

import java.util.List;
import com.example.mbti.model.Career;
import com.example.mbti.model.CourseRecommendation;

public class UserCareerDTO {

    private Career career;
    private Double score;
    private List<CourseRecommendation> courses;

    // Getter and Setter for career
    public Career getCareer() {
        return career;
    }
    public void setCareer(Career career) {
        this.career = career;
    }

    // Getter and Setter for score
    public Double getScore() {
        return score;
    }
    public void setScore(Double score) {
        this.score = score;
    }

    // Getter and Setter for courses
    public List<CourseRecommendation> getCourses() {
        return courses;
    }
    public void setCourses(List<CourseRecommendation> courses) {
        this.courses = courses;
    }

    @Override
public String toString() {
    return "UserCareerDTO{" +
            "career=" + (career != null ? career.getTitle() : "null") +
            ", score=" + score +
            ", courses=" + (courses != null ? courses.stream()
                                    .map(CourseRecommendation::getCourseName)
                                    .toList() : "[]") +
            '}';
}

}
