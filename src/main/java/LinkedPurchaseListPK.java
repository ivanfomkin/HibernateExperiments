import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

//Класс составного ключа
@Embeddable
public class LinkedPurchaseListPK implements Serializable {
    @Column(name = "student_id", nullable = false)
    private Integer studentId;
    @Column(name = "course_id", nullable = false)
    private Integer courseId;

    public LinkedPurchaseListPK() {
    }

    public LinkedPurchaseListPK(Integer studentId, Integer courseId) {
        this.studentId = studentId;
        this.courseId = courseId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LinkedPurchaseListPK that = (LinkedPurchaseListPK) o;
        return Objects.equals(studentId, that.studentId) &&
                Objects.equals(courseId, that.courseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, courseId);
    }
}
