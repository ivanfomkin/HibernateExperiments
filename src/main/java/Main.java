import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //set global time_zone = '-3:00'; - пропишем это в mysql-консоли, чтобы не ругался на TimeZone

        //Сначала подключимся к базе через JDBC
        String url = "jdbc:mysql://localhost:3306/skillbox";
        String user = "root";
        String pass = "testtest";

        String query = "select student_name, course_name from purchaselist";

        //Будем хранить имена из purchaseList
        List<String> studentsNamesList = new ArrayList<>();
        List<String> coursesNamesList = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(url, user, pass);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String courseName = resultSet.getString("course_name");
                String studentName = resultSet.getString("student_name");
                studentsNamesList.add(studentName);
                coursesNamesList.add(courseName);
            }
            statement.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml")
                .build();
        Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        //Пробежимся по именам курсов и студентов и заполним LinkedPurchaseList
        for (int i = 0; i < coursesNamesList.size(); i++) {

            //Имя текущего студента
            String currentStudenName = studentsNamesList.get(i);

            //Имя текущего курса
            String currentCourseName = coursesNamesList.get(i);

            //Запрос на получение текущего курса
            Query courseQuery = session.createQuery("From " + Course.class.getSimpleName() +
                    " where name = ?1");
            courseQuery.setParameter(1, currentCourseName);

            //Запрос на получение текущего студента
            Query studentQuery = session.createQuery("From " + Student.class.getSimpleName() +
                    " where name = ?1");
            studentQuery.setParameter(1, currentStudenName);

            //Получаем студента и его ID
            Student student = (Student) studentQuery.uniqueResult();
            Integer studentId = student.getId();

            //Получаем курс и его ID
            Course course = (Course) courseQuery.uniqueResult();
            Integer courseId = course.getId();

            //Записываем в новую таблицу ключ
            LinkedPurchaseListPK key = new LinkedPurchaseListPK(studentId, courseId);
            LinkedPurchaseList linkedPurchaseList = new LinkedPurchaseList(key);
            session.save(linkedPurchaseList);

            session.save(linkedPurchaseList);
        }

        session.getTransaction().commit();
        session.close();
        System.out.println("Create was successful");
    }
}
