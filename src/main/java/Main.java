import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //set global time_zone = '-3:00'; - пропишем это в mysql-консоли, чтобы не ругался на TimeZone

        //Подключаемся к БД через Hibernate и создаём сессию
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml")
                .build();
        Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        //Будем хранить имена из purchaseList
        List<String> studentsNamesList = new ArrayList<>();
        List<String> coursesNamesList = new ArrayList<>();

        //Заполним листы имён SQL-запросом
        List<Object[]> request = session.createSQLQuery("select student_name, course_name from purchaselist")
                .list();
        for (Object[] rows: request) {
            studentsNamesList.add(rows[0].toString());
            coursesNamesList.add(rows[1].toString());
        }

        //Пробежимся по именам курсов и студентов и заполним LinkedPurchaseList
        for (int i = 0; i < coursesNamesList.size(); i++) {

            //Имя текущего студента
            String currentStudentName = studentsNamesList.get(i);

            //Имя текущего курса
            String currentCourseName = coursesNamesList.get(i);


            //Запрос на получение текущего курса
            Query courseQuery = session.createQuery("From " + Course.class.getSimpleName() +
                    " where name = ?1");
            courseQuery.setParameter(1, currentCourseName);

            //Запрос на получение текущего студента
            Query studentQuery = session.createQuery("From " + Student.class.getSimpleName() +
                    " where name = ?1");
            studentQuery.setParameter(1, currentStudentName);

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
        }

        session.getTransaction().commit();
        session.close();
        System.out.println("Create was successful");
    }
}
