package br.com.renanfretta.emprestimos_online.utils;

import java.util.Calendar;
import java.util.Date;

public class CalendarUtil {
	
	public static Date adicionarDiasNaDataInformada(Date date, Integer quantidadeDias) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, quantidadeDias);
		return calendar.getTime(); 
	}

}