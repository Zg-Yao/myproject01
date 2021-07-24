package com.yzg.team.service;
/**
 * @Description 表示员工的状态
 * @author YZG
 * @version 
 */

public class Status {
	
	private final String NAME;
	
	private Status(String name){
		this.NAME = name;
	}
	
	public static final Status FREE = new Status("FREE");
	public static final Status BUSY = new Status("BUSY");
	public static final Status VACATION = new Status("VACATION");

	public String getNAME() {
		return NAME;
	}
	
	@Override
    public String toString() {
        return NAME;
    }


}
