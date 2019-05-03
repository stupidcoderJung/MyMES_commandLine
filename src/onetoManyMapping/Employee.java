package onetoManyMapping;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;

@Entity
@Table(name = "Employee")
public class Employee { 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
  

	@Column(name = "name")
	private String name;

	
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
        name = "Employee_Project", 
        joinColumns = { @JoinColumn(name = "employee_id") }, 
        inverseJoinColumns = { @JoinColumn(name = "project_id") }
    )
    @MapKeyColumn(name = "Project_period")
    Map<Integer,Project> projects = new HashMap<Integer, Project>();


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


	public  Map<Integer,Project> getProjects() {
		return projects;
	}


	public void setProjects( Map<Integer,Project> projects) {
		this.projects = projects;
	}


	public Employee() {
		super();
	}


	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + "]";
	}



    
    // standard constructor/getters/setters
}