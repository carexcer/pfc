package java_project;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class Fecha {

	private Integer año;
	private Integer mes;
	private Integer dia;
	private ArrayList<Integer> probabilidades;
	
	public Fecha(){
		
		probabilidades = new ArrayList<Integer>(100);
		int pEnero = 11;
		int pFebrero = 7;
		int pMarzo = 7;
		int pAbril = 6;
		int pMayo = 7;
		int pJunio = 7;
		int pJulio = 8;
		int pAgosto = 10;
		int pSeptiembre = 7;
		int pOctubre = 8;
		int pNoviembre = 9;
		int pDiciembre = 13;
		for(int i=0; i<pEnero; i++){
			
		}
	}
	
	
	
	
	public Calendar generarFechaAleatoriaCalendar(Integer añoMin, Integer añoMax){
		
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
        calendario.set(Calendar.YEAR, año);
        calendario.set(Calendar.MONTH, mes);
        calendario.set(Calendar.DAY_OF_MONTH, dia);
        
        return calendario;
		
		
	}
	
}
