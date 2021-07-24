package com.yzg.team.service;
/**
 * 
 * 关于开发团队成员的管理：添加、删除等。
 */

import javax.print.attribute.standard.NumberOfDocuments;

import com.yzg.team.domain.Architect;
import com.yzg.team.domain.Designer;
import com.yzg.team.domain.Employee;
import com.yzg.team.domain.Programmer;

public class TeamService {

	private static int count = 1;// 用来为开发团队新增成员自动生成团队中的唯一ID，即memberId。（提示：应使用增1的方式）
	private final int MAX_MEMBER = 5;// 表示开发团队最大成员数
	private Programmer[] team = new Programmer[MAX_MEMBER];// 用来保存当前团队中的各成员对象
	int total = 0;// 记录团队成员的实际人数

	public TeamService() {
		super();
	}

	/**
	 * 返回当前团队的所有对象
	 * 
	 * @return 包含所有成员对象的数组，数组大小与成员人数一致
	 */
	public Programmer[] getTeam() {
		Programmer[] team = new Programmer[total];// 此时的team是局部变量
		for (int i = 0; i < team.length; i++) {
			team[i] = this.team[i];
		}
		return team;
	}

	/**
	 * 向团队中添加成员
	 * 
	 * @param e
	 *            待添加成员的对象
	 * @throws TeamException
	 */
	public void addMember(Employee e) throws TeamException {
		// 成员已满，无法添加
		if (total >= MAX_MEMBER) {
			throw new TeamException("成员已满，无法添加");
		}
		// 该成员不是开发人员，无法添加
		if (!(e instanceof Programmer)) {
			throw new TeamException("该成员不是开发人员，无法添加");
		}
		// 该员工已在本开发团队中
		if (isExist(e)) {
			throw new TeamException("该员工已在本开发团队中");
		}
		// 该员工已是某团队成员
		// 该员正在休假，无法添加
		Programmer p = (Programmer) e;// 强转一定不会出现ClassCastException异常
		if ("BUSY".equalsIgnoreCase(p.getStatus().getNAME())) {// if
																// (p.getStatus().getNAME().equals("BUSY"))
																// {
			throw new TeamException("该员工已是某团队成员");
		} else if ("VACATION".equalsIgnoreCase(p.getStatus().getNAME())) {
			throw new TeamException("该员正在休假，无法添加");
		}
		// 团队中至多只能有一名架构师
		// 团队中至多只能有两名设计师
		// 团队中至多只能有三名程序员+

		// 首先获取team中已有的架构师、设计师和程序员的人数
		int numOfArc = 0, numOfDes = 0, numOfPro = 0;
		for (int i = 0; i < total; i++) {
			if (team[i] instanceof Architect) {
				numOfArc++;
			} else if (team[i] instanceof Designer) {
				numOfDes++;
			} else if (team[i] instanceof Programmer) {
				numOfPro++;
			}
		}
		if (p instanceof Architect) {
			if (numOfArc >= 1) {
				throw new TeamException("团队中至多只能有一名架构师");
			}
		} else if (p instanceof Designer) {
			if (numOfDes >= 2) {
				throw new TeamException("团队中至多只能有两名设计师");
			}
		} else if (p instanceof Programmer) {
			if (numOfPro >= 3) {
				throw new TeamException("团队中至多只能有三名程序员");
			}
		}

		// 将p添加到现有的team中
		team[total] = p;
		total++;

		// P的属性的赋值
		p.setStatus(Status.BUSY);
		p.setMemberId(count++);
	}

	/**
	 * @Description 判断指定的员工是否存在现有的开发于团队中
	 * @param e
	 * @return
	 */
	private boolean isExist(Employee e) {
		for (int i = 0; i < total; i++) {
			if (team[i].getId() == e.getId()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 从团队中删除成员
	 * 
	 * @param 待删除成员的
	 *            memberId
	 * @throws TeamException
	 */
	public void removeMember(int memberId) throws TeamException {
		int i = 0;
		for (; i < total; i++) {
			if (team[i].getMemberId() == memberId) {// 找到memberId
				team[i].setStatus(Status.FREE);
				break;
			}
		}
		// 未找到指定memberId的情况
		if (i == total) {
			throw new TeamException("找不到指定memberId的员工，删除失败");
		}
		// 后一个元素覆盖前一个元素，实现删除操作
		for (int j = i; j < total - 1; j++) {
			team[j] = team[j + 1];
		}
		team[total - 1] = null;
		total--;
	}

}
