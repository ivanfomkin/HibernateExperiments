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

        Subscription subscription = session.get(Subscription.class, new SubscriptionPK(1,2));
        System.out.println("Дата подписки студента " +
                session.get(Student.class, 2) +
                " на курс " +
                session.get(Course.class, 1) + ": ");
        System.out.println(subscription.getSubscriptionDate());

        Student student = session.get(Student.class, 6);
        System.out.println("\nСписок курсов студента " + student.getName() + ": ");
        student.getCourses().forEach(System.out::println);

        Course course = session.get(Course.class, 10);
        System.out.println("\nСписок студентов курса " + course.getName() + ": ");
        course.getStudents().forEach(System.out::println);

        session.close();
    }
}
