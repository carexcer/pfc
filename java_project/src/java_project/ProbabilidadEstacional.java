package java_project;

import java.util.ArrayList;
import java.util.Random;

public class ProbabilidadEstacional {

	private ArrayList<Integer> probabilidadesMes;
	private Random randMes;
	int pEnero = 10; //10% de probabilidades de Enero
	int pFebrero = 7; ///7% de probabilidades de Febrero
	int pMarzo = 7;
	int pAbril = 7;
	int pMayo = 7;
	int pJunio = 7;
	int pJulio = 10;
	int pAgosto = 8;
	int pSeptiembre = 7;
	int pOctubre = 8;
	int pNoviembre = 10;
	int pDiciembre = 12;
	int sumaProb = pEnero + pFebrero + pMarzo + pAbril + pMayo + pJunio + pJulio + pAgosto + pSeptiembre + pOctubre + pNoviembre + pDiciembre;
	
	
	/**Constructor de Probabilidad Estacional. Tiene una distribucion de probabilidad por defecto*/
	public ProbabilidadEstacional(){
		
		randMes = new Random();
		probabilidadesMes = new ArrayList<Integer>(100);
		int pEnero = 10; //10% de probabilidades de Enero
		int pFebrero = 7; ///7% de probabilidades de Febrero
		int pMarzo = 7;
		int pAbril = 7;
		int pMayo = 7;
		int pJunio = 7;
		int pJulio = 10;
		int pAgosto = 8;
		int pSeptiembre = 7;
		int pOctubre = 8;
		int pNoviembre = 10;
		int pDiciembre = 12;
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
	/**Crea un objeto de Probabilidad Estacional.
	 * @param Recibe la probabilidad de cada mes en porcentaje. La suma debe ser 100*/
	public ProbabilidadEstacional(int ene, int feb, int mar, int abr, int may, int jun, int jul, int ago, int sep, int oct, int nov, int dic){
		
		randMes = new Random();
		probabilidadesMes = new ArrayList<Integer>(100);
		pEnero = ene; 		// % de probabilidades de Enero
		pFebrero = feb; 		//% de probabilidades de Febrero
		pMarzo = mar;
		pAbril = abr;
		pMayo = may;
		pJunio = jun;
		pJulio = jul;
		pAgosto = ago;
		pSeptiembre = sep;
		pOctubre = oct;
		pNoviembre = nov;
		pDiciembre = dic;
		
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
	public int actualizar(){
		
		probabilidadesMes = new ArrayList<Integer>(100);
		
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
		
		this.sumaProb = pEnero + pFebrero + pMarzo + pAbril + pMayo + pJunio + pJulio + pAgosto + pSeptiembre + pOctubre + pNoviembre + pDiciembre;
		return this.sumaProb;
	}
	
	public ArrayList<Integer> getProbabilidadesMes() {
		return probabilidadesMes;
	}
	public void setProbabilidadesMes(ArrayList<Integer> probabilidadesMes) {
		this.probabilidadesMes = probabilidadesMes;
	}
	public int getpEnero() {
		return pEnero;
	}
	public void setpEnero(int pEnero) {
		this.pEnero = pEnero;
	}
	public int getpFebrero() {
		return pFebrero;
	}
	public void setpFebrero(int pFebrero) {
		this.pFebrero = pFebrero;
	}
	public int getpMarzo() {
		return pMarzo;
	}
	public void setpMarzo(int pMarzo) {
		this.pMarzo = pMarzo;
	}
	public int getpAbril() {
		return pAbril;
	}
	public void setpAbril(int pAbril) {
		this.pAbril = pAbril;
	}
	public int getpMayo() {
		return pMayo;
	}
	public void setpMayo(int pMayo) {
		this.pMayo = pMayo;
	}
	public int getpJunio() {
		return pJunio;
	}
	public void setpJunio(int pJunio) {
		this.pJunio = pJunio;
	}
	public int getpJulio() {
		return pJulio;
	}
	public void setpJulio(int pJulio) {
		this.pJulio = pJulio;
	}
	public int getpAgosto() {
		return pAgosto;
	}
	public void setpAgosto(int pAgosto) {
		this.pAgosto = pAgosto;
	}
	public int getpSeptiembre() {
		return pSeptiembre;
	}
	public void setpSeptiembre(int pSeptiembre) {
		this.pSeptiembre = pSeptiembre;
	}
	public int getpOctubre() {
		return pOctubre;
	}
	public void setpOctubre(int pOctubre) {
		this.pOctubre = pOctubre;
	}
	public int getpNoviembre() {
		return pNoviembre;
	}
	public void setpNoviembre(int pNoviembre) {
		this.pNoviembre = pNoviembre;
	}
	public int getpDiciembre() {
		return pDiciembre;
	}
	public void setpDiciembre(int pDiciembre) {
		this.pDiciembre = pDiciembre;
	}
	public int getSumaProb() {
		return sumaProb;
	}
	public void setSumaProb(int sumaProb) {
		this.sumaProb = sumaProb;
	}
}
