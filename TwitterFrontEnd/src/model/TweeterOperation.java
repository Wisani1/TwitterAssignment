package model;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name ="tweeterOperation")
@SessionScoped

public class TweeterOperation {
	
	private String textArea;  
	
    public String getTextArea() {
		return textArea;
	}
	public void setTextArea(String textArea) {
		this.textArea = textArea;
	}

	public  TwitterEntity getEntity() {
		
		TwitterEntity twitterEntity = new  TwitterEntity();
		twitterEntity.setTextArea(textArea);
		return twitterEntity;
		
	}
	
}
