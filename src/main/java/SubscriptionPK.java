import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Table(name = "subscriptions")
public class SubscriptionPK implements Serializable {
    @Column(name = "course_id")
    private int courseId;

    @Column(name = "student_id")
    private int studentId;

    public SubscriptionPK (){
    }

    public SubscriptionPK (int courseId, int studentId){
        this.courseId = courseId;
        this.studentId = studentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubscriptionPK that = (SubscriptionPK) o;
        return courseId == that.courseId &&
                studentId == that.studentId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, studentId);
    }
}