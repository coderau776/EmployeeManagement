package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.Query;
import java.util.List;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    static Session session;
    public static void main( String[] args )
    {
        session = Utility.getSession();
        A:while (true){
            System.out.println("***************EMPLOYEE DETAILS****************");
            System.out.println("1. Add\n2. Update\n3. Display all\n4. Display by Id\n5. Delete\n6. Sort based on id" +
                    "\n7. Get Data with range\n8. exit");

            switch (new Scanner(System.in).nextInt()){
                case 1:
                    insert();
                    break;
                case 2:
                    updateRecord();
                    break;
                case 3:
                    displayAll();
                    break;
                case 4:
                    display();
                    break;
                case 5:
                    deleteRecord();
                    break;
                case 6:
                    sortOnId();
                    break;
                case 7:
                    displayInRange();
                    break;
                case 8:
                    break A;
                default:
                    System.out.println("This option doesnt exist");

            }

            System.out.println("Do you want menu again ?");
            if(!new Scanner(System.in).next().equalsIgnoreCase("yes"))
                break;
        }
        session.close();

    }

    private static void displayInRange() {
        System.out.println("Enter the range starting and ending");
        displayInRangeRecords(new Scanner(System.in).nextInt(),new Scanner(System.in).nextInt());
    }
    private static void displayInRangeRecords(int start,int end){
        Query query = session.createQuery("FROM Employee e WHERE e.id BETWEEN :start AND :end")
                .setParameter("start",start)
                .setParameter("end",end);
        displayList(query.getResultList());
    }

    private static void sortOnId() {
        Query query = session.createQuery("FROM Employee e ORDER BY e.id");
        List<Employee> list = query.getResultList();
        displayList(list);
    }


    public static void insert(){

            Employee e = new Employee();

            System.out.println("Enter name");
            e.setName(new Scanner(System.in).next());

            System.out.println("Enter age");
            e.setAge(new Scanner(System.in).nextInt());

            add(e);

            System.out.println("Record Added Successfully....");
    }
    public static void add(Employee e){
        
        Transaction t = session.beginTransaction();
        session.save(e);
        t.commit();
        //session.close();
    }
    public static void updateRecord(){
        System.out.println("Enter record id to be updated");
        int id = new Scanner(System.in).nextInt();
        Employee e = get(id);
        System.out.println("Record you want to update is:- "+e);

        System.out.println("What do you want to update\n1. name     2. age    3. name & age");
        switch (new Scanner(System.in).nextInt()){
            case 1:
                System.out.println("Enter new name");
                e.setName(new Scanner(System.in).next());
                break;

            case 2:
                System.out.println("Enter new age");
                e.setAge(new Scanner(System.in).nextInt());
                break;

            case 3:
                System.out.println("Enter new name");
                e.setName(new Scanner(System.in).next());
                System.out.println("Enter new age");
                e.setAge(new Scanner(System.in).nextInt());
                break;

            default:
                System.out.println("This option doesnt exist");
        }
        update(e);
    }
    public static void update(Employee e){
        //Session session = Utility.getSession();
        Transaction t = session.beginTransaction();
        session.saveOrUpdate(e);
        t.commit();
        //session.close();
    }
    public static void displayAll(){
        Query query = session.createQuery("FROM Employee",Employee.class);
        List<Employee> list = query.getResultList();
        //list.forEach((e) -> System.out.println(e));
        displayList(list);
    }
    private static void displayList(List<Employee> list)
    {
        for(Employee e:list)
            System.out.println(e);
    }
    private static void display() {
        System.out.println("Enter id of record to be displayed");
        System.out.println(get(new Scanner(System.in).nextInt()));
    }
    public static Employee get(int id)
    {
        //Session session = Utility.getSession();
        Employee e = (Employee) session.get(Employee.class,id);
        //session.close();
        return e;
    }
    public static void deleteRecord(){
        System.out.println("Enter id of record to be deleted");
        Employee e = get(new Scanner(System.in).nextInt());
        System.out.println("Record you want to delete is:- "+e);
        delete(e);
        System.out.println("Record deleted successfully...");
    }
    public static void delete(Employee e)
    {
        //Session session = Utility.getSession();
        Transaction t = session.beginTransaction();
        session.delete(e);
        t.commit();
        //session.close();
    }
}
