package com.yzg.team.service;

import com.yzg.team.domain.Architect;
import com.yzg.team.domain.Designer;
/**
 * @Description 负责将Data中的数据封装到Employee[]数组中，同时提供相关操作Employee[]的方法。
 * @author YZG
 * @Version 1.0
 * 
 */
import com.yzg.team.domain.Employee;
import com.yzg.team.domain.Equipment;
import com.yzg.team.domain.NoteBook;
import com.yzg.team.domain.PC;
import com.yzg.team.domain.Printer;
import com.yzg.team.domain.Programmer;

import static com.yzg.team.service.Data.*;

public class NameListService {

	private Employee[] employees;// 保存公司所有员工对象

	/**
	 * 
	 * 给employees 及数组元素进行初始化
	 */

	public NameListService() {
		/**
		 * 
		 * 1. 根据项目提供的Data类构建相应大小的employees数组 2.
		 * 再根据Data类中的数据构建不同的对象，包括Employee、Programmer、Designer和Architect对象，以及相关联的Equipment子类的对象
		 * 3. 将对象存于数组中
		 * 
		 */

		employees = new Employee[EMPLOYEES.length];
		for (int i = 0; i < employees.length; i++) {
			int type = Integer.parseInt(EMPLOYEES[i][0]);
			// 获取Employee的四个基本信息

			int id = Integer.parseInt(EMPLOYEES[i][1]);
			String name = EMPLOYEES[i][2];
			int age = Integer.parseInt(EMPLOYEES[i][3]);
			double salary = Double.parseDouble(EMPLOYEES[i][4]);
			Equipment equipment;
			double bonus;

			switch (type) {
			case EMPLOYEE:
				employees[i] = new Employee(id, name, age, salary);
				break;

			case PROGRAMMER:
				equipment = creatEquipment(i);
				employees[i] = new Programmer(id, name, age, salary, equipment);
				break;
			case DESIGNER:
				equipment = creatEquipment(i);
				bonus = Double.parseDouble(EMPLOYEES[i][5]);
				employees[i] = new Designer(id, name, age, salary, equipment, bonus);

				break;
			case ARCHITECT:
				equipment = creatEquipment(i);
				bonus = Double.parseDouble(EMPLOYEES[i][5]);
				int stock = Integer.parseInt(EMPLOYEES[i][6]);
				employees[i] = new Architect(id, name, age, salary, equipment, bonus, stock);

				break;
			}
		}

	}

	/**
	 * @Description 获取指定index位置上的员工的设备
	 * @param index
	 * @date
	 * @return
	 */
	private Equipment creatEquipment(int index) {
		int key = Integer.parseInt(EQUIPMENTS[index][0]);
		String model = EQUIPMENTS[index][1];

		switch (key) {
		case PC:
			String display = EQUIPMENTS[index][2];
			return new PC(model, display);

		case NOTEBOOK:
			double price = Double.parseDouble(EQUIPMENTS[index][2]);
			return new NoteBook(model, price);

		case PRINTER:
			String name = EQUIPMENTS[index][1];
			String type = EQUIPMENTS[index][2];
			return new Printer(name, type);
		}
		return null;
	}

	/**
	 * @Description 获取当前所有员工。
	 * @return employees
	 */

	public Employee[] getAllEmployees() {

		return employees;
	}

	/**
	 * @Description 获取指定ID的员工对象。
	 * @param id
	 * @return 指定员工对象
	 * @throws TeamException
	 * @exception 找不到指定的员工
	 */
	public Employee getEmployee(int id) throws TeamException {
		for (int i = 0; i < employees.length; i++) {
			if (employees[i].getId() == id) {
				return employees[i];
			}
		}
		throw new TeamException("找不到指定的员工");

	}

}
