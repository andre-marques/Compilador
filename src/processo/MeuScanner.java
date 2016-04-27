package processo;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PushbackReader;

public class MeuScanner {
	
	private FileInputStream fis;
	private InputStreamReader isr;
	private PushbackReader pbr;
	
	private boolean objInicio = false;
	private String objetoInicio = "";
	private String objetoFim = "";
	
	private boolean atriInicio = false;
	private String atributoInicio = "";
	private String atributoFim = "";

	public MeuScanner(String path) throws FileNotFoundException {
		
		this.fis = new FileInputStream(path);
		this.isr = new InputStreamReader(this.fis);
		this.pbr = new PushbackReader(this.isr);
		
	}
	
	private Character obterCaracter() throws IOException {

		Character c = null;
		int i = this.pbr.read();

		if (i != -1) {
			c = (char) i;
		}
		return c;
	}
	
	public void devolverCaracter(Character c) throws IOException {

		this.pbr.unread(c);
	}
	
	public void leitura() throws IOException{
		
		Character c = this.obterCaracter();

		String corrent = "";
		
		while(c != null){
		
			//ignorar tabulação e pular linha
			if(!(c == '\n') &&  !(c == '\t') && !(c == '\r')){
				//System.out.println("pulou");
				corrent = corrent + c;
				
				
				//define um objeto de inicio
				//LEMBRAR DE NÃO PODER ENTRAR AQUI SE NÃO TIVER PERCORRIDO TODOS OS ATRIBUTOS
				if((this.atriInicio == false) && (objInicio == false || (objetoInicio.charAt(0) == '<' && objetoInicio.charAt(objetoInicio.length()-1) == '>'))){

					objetoInicio = objetoInicio + c;
					//System.out.println(objetoInicio);
					//identifica um objeto
					if(c == '>'){
						imprimeObj();
						objInicio = true;
						objetoFim = "</"+ objetoInicio.substring(1);
						//corrent = "";
					}
					
				}
				
				if(corrent.endsWith(objetoFim) && objInicio){
					
					System.out.println("---->"+objetoFim);
					objInicio = false;
					objetoInicio = "";
					objetoFim = "";
					corrent = "";
				}
				
				//até aqui foi o objeto		
				
			}
			
			c = this.obterCaracter();	
	
		}
		
	}
	
	private void imprimeObj(){
		String objeto = "";
		for (int i = 1; i < objetoInicio.length()-1; i++) {
			objeto = objeto + objetoInicio.charAt(i);
		}
		System.out.println("Objeto:" + objeto+"\n");
	}
	
	private void imprimeAtrib(){
		String objeto = "";
		for (int i = 1; i < atributoInicio.length()-1; i++) {
			objeto = objeto + atributoInicio.charAt(i);
		}
		System.out.print("Objeto:" + objeto+"\n");
	}
	
	
}
