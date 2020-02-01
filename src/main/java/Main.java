import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class Main {
    public static void main(String[] args) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml")
                .build();
        Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
        Session session = sessionFactory.openSession();


        for (int i = 1; i < 11; i++){
            Course course = session.get(Course.class, i);
            System.out.println("Course name is " + course.getName());
            int teacherId = course.getTeacherId();
            Teacher teacher = session.get(Teacher.class, teacherId);
            System.out.println("Teacher of the course " + course.getName() + " is " + teacher.getName());
        }

        sessionFactory.close();
    }
}
