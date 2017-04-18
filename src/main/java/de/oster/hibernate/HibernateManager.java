package de.oster.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

public class HibernateManager {

    private SessionFactory factory;
    private Session session;
    private Configuration configuration;
    public HibernateManager(){

        configuration = new Configuration().configure("hibernate.cfg.xml");

    }

    public void setAnnotatedClass(Class<?> ...cls){
        for (Class<?> cl : cls)
            configuration.addAnnotatedClass(cl);

        factory = configuration.buildSessionFactory();

        session = factory.getCurrentSession();
    }

    public boolean saveObject(Object obj){
       try {
            session.beginTransaction();

            System.out.println("Start saving ...");
            session.save(obj);

            session.getTransaction().commit();

            System.out.println("Savin is done.");
       }
       catch(Exception e){
            System.out.println(e.getMessage());

            return false;
        }

        return true;
    }

    public List getAllStudent(){

        session.beginTransaction();

        List<Student> studentList = session
                .createQuery("from Student").list();


        return studentList;
    }

    @Override
    public void finalize(){
        System.out.println("HibernateManager ist beendet.");
    }
}
