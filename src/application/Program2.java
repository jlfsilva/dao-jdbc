package application;

import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class Program2 {

	public static void main(String[] args) {
		
		Scanner in = new Scanner(System.in);

		DepartmentDao DepartmentDao = DaoFactory.createDepartmentDao();
		System.out.println("=== Teste n°1: Department findById ===");
		Department Department = DepartmentDao.findById(3);
		System.out.println(Department);

		System.out.println("\n=== Teste n°2: Department findAll ===");
		List<Department> list = DepartmentDao.findAll();

		for (Department obj : list) {
			System.out.println(obj);
		}
		
		System.out.println("\n=== Teste n°3: Department insert ===");
		Department newDepartment = new Department(null, "Software");
		DepartmentDao.insert(newDepartment);
		System.out.println("Inserted! New id = " + newDepartment.getId());
		
		System.out.println("\n=== Teste n°4: Department update ===");
		Department = DepartmentDao.findById(1);
		Department.setName("Utilities");
		DepartmentDao.update(Department);
		System.out.println("Update completed!");
		
		System.out.println("\n=== Teste n°5: Department delete ===");
		System.out.println("Enter id for delete Department: ");
		int id = in.nextInt();
		DepartmentDao.deleteById(id);
		System.out.println("Delete completed!");
		
		in.close();
		

	}

}
