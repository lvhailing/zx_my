package com.my.zx.db.afinal.db.table;

public class OneToMany extends Property {

	private Class<?> oneClass;

	public Class<?> getOneClass() {
		return oneClass;
	}

	public void setOneClass(Class<?> oneClass) {
		this.oneClass = oneClass;
	}

}
