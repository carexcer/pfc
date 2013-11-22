package igu;


public class BarraProgreso {

	String texto;
	
	public BarraProgreso(){
	
	}
	
	public void setTextRunning(String texto){
		poblar_bd.progressBar.setStringPainted(true);
		if(texto!=null){
			poblar_bd.progressBar.setString(texto);
		}
		poblar_bd.progressBar.repaint();
	}
	/**Establece el valor maximo de la barra de progreso*/
	public void setMaximo(int valorMaximo){
		poblar_bd.progressBar.setStringPainted(true);
		poblar_bd.progressBar.setMaximum(valorMaximo);
		poblar_bd.progressBar.repaint();
	}
	/**Incrementa en valorProgreso el valor del progreso de la progressBar*/
	public void incrementar(int valorProgreso){
		poblar_bd.progressBar.setString(null);
		poblar_bd.progressBar.setStringPainted(true);
		poblar_bd.progressBar.setValue(poblar_bd.progressBar.getValue()+valorProgreso);
		poblar_bd.progressBar.repaint();
	}
	/**Incrementa en 1 el valor del progreso de la progressBar*/
	public void incrementar(){		
		poblar_bd.progressBar.setString(null);
		poblar_bd.progressBar.setStringPainted(true);
		poblar_bd.progressBar.setValue(poblar_bd.progressBar.getValue()+1);
		poblar_bd.progressBar.repaint();
	}
	public void setTextStopped(){
		poblar_bd.progressBar.setStringPainted(true);
		poblar_bd.progressBar.setValue(0);
		poblar_bd.progressBar.setString("Nada en ejecucion");
		poblar_bd.progressBar.repaint();
	}
	
}
