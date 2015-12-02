/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.revisionhistory;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.jhotdraw.draw.Drawing;

/**
 * This class represent a Revision, which is composed of an instance of 
 * respectively Drawing and Date.
 * 
 * @author Emil Frisk
 */
public class Revision {
	
	private final Drawing drawing;
	private final Date date;

	public Revision(Drawing drawing, Date date){
		this.drawing = drawing;
		this.date = date;
	}

	public Drawing getDrawing(){
		return drawing;
	}
	
	public Date getDate(){
		return date;
	}

	@Override
	public String toString() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		return dateFormat.format(date);
	}
}
