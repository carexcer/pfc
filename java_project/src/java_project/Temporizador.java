package java_project;

public class Temporizador {

	static public long time_start;
	static public long time_end;
	
	public Temporizador(){
		
	}
	public static void iniciarTemporizador(){
		time_start = System.currentTimeMillis();
	}
	public static double pararTemporizador(String mensaje){
		
		time_end = System.currentTimeMillis();
		double tiempo = time_end - time_start;
		tiempo = tiempo / 1000;
		System.out.println("[Tiempo ejecución = " + tiempo + " segundos] - " + mensaje + "\n");
		return tiempo;
	}
}
