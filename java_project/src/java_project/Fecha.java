package java_project;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class Fecha {

	private Integer año;
	private Integer mes;
	private Integer dia;
	private ArrayList<Integer> probabilidadesMes;
	private ArrayList<Integer> probabilidadesDia;
	private Random randMes;
	private Random randDia;
	
	// Enero es el mes 1, y Diciembre es el mes 12
	public Fecha(){

		randMes = new Random();
		randDia = new Random();
		probabilidadesMes = new ArrayList<Integer>(100);
		probabilidadesDia = new ArrayList<Integer>(100);
		int pEnero = 11; //11% de probabilidades de Enero
		int pFebrero = 7; ///7% de probabilidades de Febrero
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
		for(int i=0; i<pEnero; i++)
			probabilidadesMes.add(1);
		for(int i=0; i<pFebrero; i++)
			probabilidadesMes.add(2);
		for(int i=0; i<pMarzo; i++)
			probabilidadesMes.add(3);
		for(int i=0; i<pAbril; i++)
			probabilidadesMes.add(4);
		for(int i=0; i<pMayo; i++)
			probabilidadesMes.add(5);
		for(int i=0; i<pJunio; i++)
			probabilidadesMes.add(6);
		for(int i=0; i<pJulio; i++)
			probabilidadesMes.add(7);
		for(int i=0; i<pAgosto; i++)
			probabilidadesMes.add(8);
		for(int i=0; i<pSeptiembre; i++)
			probabilidadesMes.add(9);
		for(int i=0; i<pOctubre; i++)
			probabilidadesMes.add(10);
		for(int i=0; i<pNoviembre; i++)
			probabilidadesMes.add(11);
		for(int i=0; i<pDiciembre; i++)
			probabilidadesMes.add(12);
		
	}
	
	/**@return Devuelve un entero entre 1 y 12 que representa el mes, siguiendo la distribución de probabilidad creada en el constructor.*/
	public int obtenerMesAleatorioPonderado(){
		
		if(probabilidadesMes==null)
			return (-1);
		
		return probabilidadesMes.get(randMes.nextInt(100));
		
	}
	/**@return Devuelve un entero entre 1 y maxMes (inclusive) que representa el mes, siguiendo la distribución de probabilidad creada en el constructor*/
	public int obtenerMesAleatorioPonderado(Integer maxMes){
		if(probabilidadesMes==null)
			return (-1);
		int ret;
		do{
			ret = probabilidadesMes.get(randMes.nextInt(100));
		}while(ret > maxMes);		
		return ret;
	}
	public int obtenerDiaAleatorio(Integer anyo){
		
		int año;
		int dia;
		int mes = randMes.nextInt(12);
		if(anyo==null)
			año=2012;
		else
			año = anyo;
		if (mes == 2) {//Mes de febrero
            if (año % 400 == 0 || año % 4 == 0) {//es bisiesto
                dia = randDia.nextInt(28)+1;
            } else {//No es año bisiesto
                dia = randDia.nextInt(27)+1;
            }
        } else {
            if (mes == 1 || mes == 3 || mes == 5 || mes == 7 || mes == 8 || mes == 10 || mes == 12) {
                //Mes de 31 días
                dia = randDia.nextInt(31)+1;
            } else {//Mes de 30 días
                dia = randDia.nextInt(30)+1;
            }
        }
		return dia;
		
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
	static Calendar stringToCalendar(String cadena){
		
		Calendar cal = Calendar.getInstance();
		String[] campos = cadena.split("-");
		cal.set(Calendar.DAY_OF_MONTH, Integer.valueOf(campos[0]));
		cal.set(Calendar.MONTH, Integer.valueOf(campos[1]));
		cal.set(Calendar.YEAR, Integer.valueOf(campos[2]));
		
		return cal;
	}
	
}
