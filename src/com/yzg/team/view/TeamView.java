package com.yzg.team.view;

import com.yzg.team.domain.Employee;
import com.yzg.team.domain.Programmer;
import com.yzg.team.service.NameListService;
import com.yzg.team.service.TeamException;
import com.yzg.team.service.TeamService;

public class TeamView {
	
	//创建两属性，供类中的方法使用
	private NameListService listSvc = new NameListService();
	private TeamService teamSvc = new TeamService();
	
	/**
	 * 主界面显示及控制方法
	 * 
	 */
	public void enterMainMenu(){
		boolean loopFlag = true;
		char menu = 0;
		while(loopFlag){
			
			
			if (menu != '1') {
				
				listAllEmployees();
			}
			
//			System.out.println("-------------------------------------------------------------------------------");			
			System.out.print("1-团队列表  2-添加团队成员  3-删除团队成员 4-退出   请选择(1-4)：");
		
			menu = TSUtility.readMenuSelection();			
			switch (menu) {
			case '1':
				getTeam();
				break;
			case '2':
				addMember();
				break;
			case '3':
				deleteMember();
				break;
			case '4':
				
				System.out.print("\n确认是否退出(Y/N)：");
				char isExert = TSUtility.readConfirmSelection();
				if (isExert == 'Y') {
					loopFlag = false;
				}
			}
		}
		
	}
	
	/**
	 * 以表格形式列出公司所有成员
	 */
	private void listAllEmployees(){
		Employee[] allEmployees = listSvc.getAllEmployees();
		if (allEmployees.length == 0 || allEmployees == null) {
			System.out.println("公司中没有任何员工信息");
		}
		
		System.out.println("\n-------------------------------开发团队调度软件--------------------------------\n");
		System.out.println("ID\t姓名\t年龄\t工资\t职位\t状态\t奖金\t股票\t领用设备");
		for (int i = 0; i < allEmployees.length; i++) {
			
			System.out.println(allEmployees[i]);
			
		}
		System.out.println("-------------------------------------------------------------------------------");
	}
	
	/**
	 * 显示团队成员列表操作
	 */
	private void getTeam(){
//		System.out.println("团队成员列表");
		System.out.println("\n--------------------团队成员列表---------------------\n");
		Programmer[] team = teamSvc.getTeam();
		if (team.length == 0 || team == null) {
			System.out.println("开发团队目前没有成员！");
		}else{
			System.out.println("TID/ID\t姓名\t年龄\t工资\t职位\t奖金\t股票");
			for (int i = 0; i < team.length; i++) {				
				System.out.println(team[i].getMemmberOfInfPlus());
			}
		}
		System.out.println("-----------------------------------------------------");
	}
	
	/**
	 * 实现添加成员操作
	 */
	private void addMember(){
//		System.out.println("添加成员操作");
		System.out.println("\n---------------------添加成员---------------------");
		System.out.print("请输入要添加的员工ID：");
		int id = TSUtility.readInt();
		
		try {
			Employee emp = listSvc.getEmployee(id);
			teamSvc.addMember(emp);
			System.out.println("添加成功");
			TSUtility.readReturn();
		} catch (TeamException e) {
			System.out.print("添加失败，原因：" + e.getMessage());
		}
		
	}
	
	/**
	 * 实现删除成员操作
	 */
	private void deleteMember(){
//		System.out.println("删除成员操作");
		System.out.println("---------------------删除成员---------------------");
		getTeam();
		System.out.print("请输入要删除员工的TID：");
		int memberId = TSUtility.readInt();
		System.out.print("确认是否删除(Y/N)：");
		char isDelete = TSUtility.readConfirmSelection();
		if (isDelete == 'N') {
			return;
		}
		try {
			teamSvc.removeMember(memberId);
			System.out.println("删除成功");
			TSUtility.readReturn();
		} catch (TeamException e) {
			System.out.println("删除失败，原因：" + e.getMessage());
			TSUtility.readReturn();
		}
	}
	
	public static void main(String[] args) {
		TeamView view = new TeamView();
		view.enterMainMenu();
		
	}

}
