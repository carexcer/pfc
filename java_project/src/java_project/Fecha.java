package java_project;

import java.util.Calendar;

public class Fecha {

	private Integer año;
	private Integer mes;
	private Integer dia;
	
	public Fecha(){
		
	}
	
	
	
	public static Calendar generarFechaAleatoriaCalendar(Integer añoMin, Integer añoMax){
		
		Calendar calendario = Calendar.getInstance();
		Integer año, mes;
		Integer dia=0;
		
		año = (int) ((añoMax - añoMin + 1) * Math.random()) + añoMin;
        mes = (int) (12 * Math.random());
        if (mes == 2) {//Mes de febrero
            if (año % 400 == 0 || año % 4 == 0) {//es bisiesto
                dia = (int) (29 * Math.random());
            } else {//No es año bisiesto
                dia = (int) (28 * Math.random());
            }
        } else {
            if (mes == 1 || mes == 3 || mes == 5 || mes == 7 || mes == 8 || mes == 10 || mes == 12) {
                //Mes de 31 días
                dia = (int) (31 * Math.random());
            } else {//Mes de 30 días
                dia = (int) Math.random();
            }
        }
        return calendario;
		
		
	}
	
}
