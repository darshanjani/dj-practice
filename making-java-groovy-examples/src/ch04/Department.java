package ch04;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Darshan on 7/17/16.
 */
public class Department {
    private int id;
    private String name;
    private Map<Integer, Employee> employees = new HashMap<Integer, Employee>();

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Collection<Employee> getEmployees() { return employees.values(); }
    public void hire(Employee e) { employees.put(e.getId(), e); }
    public void layoff(Employee e) { employees.remove(e.getId()); }

    public Department plus(Employee e) {
        hire(e);
        return this;
    }

    public Department minus(Employee e) {
        layoff(e);
        return this;
    }

    public Department leftShift(Employee e) {
        hire(e);
        return this;
    }
}
