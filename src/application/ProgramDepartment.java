package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;


public class ProgramDepartment {
	public static void main(String[] args) {
		
		DepartmentDao departmentDao = DaoFactory.createDepartmentDao();	
		Scanner sc = new Scanner(System.in);
		
		List<Department> list = new ArrayList<>();
	
	System.out.println("\n=== TESTE 1: Department Insertion =====");
	
	Department newDepartment = new Department(11, "Consulting");
	departmentDao.insert(newDepartment);
	System.out.println("Inserted! " + newDepartment);
	
	
	System.out.println("=== TESTE 2: Department findById =====");
	Department department = departmentDao.findById(7);
	System.out.println(department);
	
	
	System.out.println("\n=== TESTE 3: Department Update =====");
	department = departmentDao.findById(4);
	department.setName("Automation");
	departmentDao.update(department);
	System.out.println("Update completed!");
	
	
	System.out.println("\n=== TESTE 4: Department findAll =====");
	list = departmentDao.findAll();
	for(Department obj: list) {
		System.out.println(obj);
		}
	
	System.out.println("=== TESTE 5: Department Delete =====");
	System.out.println("Enter id for delete test: ");
	int id = sc.nextInt();
	departmentDao.deleteById(id);
	System.out.println("Delete completed");
	

	
	
	
	
	}
}