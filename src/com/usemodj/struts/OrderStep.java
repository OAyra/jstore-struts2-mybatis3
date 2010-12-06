package com.usemodj.struts;

import java.util.Arrays;
import java.util.List;

public enum OrderStep {
	/*OD,CC,RC,SP,FS,RT*/
	
	OD("Order","주문"),
	CC("Cancel", "취소"),
	RC("Receipts","입금"),
	SP("Shipping","배송"),
	FS("Finish", "완료"),
	RT("Return","반송");
	
	private String fullName;
	private String koName;
	OrderStep(String fullName, String koName){
		this.fullName = fullName;
		this.koName = koName;
	}
	public String getFullName(){
		return this.fullName;
	}
	public String getKoName(){
		return this.koName;
	}
	public static List<OrderStep> getOrderStepList(){
		return Arrays.asList( OrderStep.values());
	}
}
