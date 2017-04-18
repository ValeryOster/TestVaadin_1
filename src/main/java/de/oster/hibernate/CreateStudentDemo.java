package de.oster.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class CreateStudentDemo {
    public static void main(String[] args) {

        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Student.class)
                .buildSessionFactory();


        Session session = factory.getCurrentSession();

        try {
            System.out.println("Creating new student object ...");

            Student st = new Student("Paul", "Wall",
                    "pw@gmail.com");

            session.beginTransaction();

            System.out.println("Saving the student...");
            session.save(st);

            session.getTransaction().commit();

            System.out.println("Work is done.");
        }
        finally {
            factory.close();
        }
    }
}
