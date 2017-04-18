package org.test;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import de.oster.hibernate.HibernateManager;
import de.oster.hibernate.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * This UI is the application entry point. A UI may either represent a browser window
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {

    private HibernateManager manager = new HibernateManager();

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        manager.setAnnotatedClass(Student.class);
        final VerticalLayout layout = new VerticalLayout();
        setContent(layout);

        layout.addComponent(new Label("Add new Student:", ContentMode.HTML));
        TextField firstName = new TextField("First name");
        TextField lastName = new TextField("Last name");
        TextField email = new TextField("Email");


        Button btnSave = new Button("Save");

        btnSave.addClickListener(clickEvent ->
                {
                    boolean saved = manager.saveObject(new Student(
                            firstName.getValue(),
                            lastName.getValue(),
                            email.getValue()
                    ));
                    if(saved)
                        Notification.show("Info is saved.");
                    else
                        Notification.show("There is a problem in save method.");


                });

        //Grid with all Student in DB
        List<Student> personList = manager.getAllStudent();

        // Create a Grid and bind the Person objects to it
        Grid<Student> grid = new Grid<>(Student.class);
        grid.setItems(personList);

        // Define the columns to be displayed
        grid.setColumns("firstName", "lastName", "email");
        grid.addSelectionListener(event -> {
            List<Student> st =new ArrayList<>( event.getAllSelectedItems() );

            Notification.show(st.get(0).getFirstName() + ", " + st.get(0).getLastName());
        });
        grid.setSizeFull();

        layout.addComponent(firstName);
        layout.addComponent(lastName);
        layout.addComponent(email);
        layout.addComponent(btnSave);

        layout.addComponent(new Label("<hr />", ContentMode.HTML));

        layout.addComponent(grid);

    }
    @Override
    public void close() {

        System.out.println("It is be closed in time...");

        super.close();

    }


    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
