package Core;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Emili on 2016-10-24.
 */

public class Student extends DomainObject{

    private static final AtomicInteger COUNTS = new AtomicInteger(1);
    //private int sid;
    //private String username;
    //private int username;
    private String name;
    private String password;

    public Student(long u, String n, String p){
        super(u);
        //super(COUNTS.incrementAndGet());
        //sid = COUNTS.incrementAndGet();
        //username = u;
        name = n;
        password = p;
    }

   /*public int getSid() {
        return sid;
    }*/

    /*public int getUsername() {
        return username;
    }*/

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    /*public void setSid(int sid) {
        this.sid = sid;
    }*/

    /*public void setUsername(int username) {
        this.username = username;
    }*/

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toString(){
        return ("Name: " + name +
                "\nStudent Id: " + super.getId());
    }
}
