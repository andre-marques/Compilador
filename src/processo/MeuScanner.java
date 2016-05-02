package processo;

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
	
	private String textoSaida = "";

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
	
	public String leitura() throws IOException{
		
		Character c = this.obterCaracter();

		String corrent = "";
		String correntAtri = "";
		
		while(c != null){
			
			//ignorar tabulação e pular linha
			if(!(c == '\n') &&  !(c == '\t') && !(c == '\r')){
				//System.out.println("pulou");
				corrent = corrent + c;
				//System.out.println("+"+corrent);
				
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
					
					//System.out.println("---->"+objetoFim);
					objInicio = false;
					objetoInicio = "";
					objetoFim = "";
					corrent = "";
					
					
					atriInicio = false;
					atributoInicio = "";
					atributoFim = "";
					correntAtri = "";
				}
				
				//até aqui foi o objeto
				
				
				//INICIO
				if(objInicio == true){
					correntAtri = correntAtri + c;
					
					//System.out.println("---"+atributoInicio);
					if(atriInicio == false || (atributoInicio.charAt(0) == '<' && atributoInicio.charAt(atributoInicio.length()-1) == '>')){
						
						if(!correntAtri.equals(">")){//GAMBIARRA, POIS NA INTERAÇÃO QUE VÁ ENCONTRAR > ELE LIMPA CORRENTATRI
								atributoInicio = atributoInicio + c;
						}
						
						//System.out.println(objetoInicio);
						//identifica um objeto
						if(c == '>' && !atributoInicio.isEmpty()){
							imprimeAtrib();
							atriInicio = true;
							atributoFim = "</"+ atributoInicio.substring(1);
							//corrent = "";
						}
						
					}
					
				}
				
				if(correntAtri.endsWith(atributoFim) && atriInicio){
					
					//adição de conteudo
					String conteudo;
					if(correntAtri.charAt(0) == '>'){
						conteudo = correntAtri.substring(atributoFim.length(), correntAtri.length()-(atributoFim.length()));
						//System.out.println("AQUI");
					}else{
						conteudo = correntAtri.substring(atributoFim.length()-1, correntAtri.length()-(atributoFim.length()));
					}
					//System.out.println("+++++"+conteudo);
					this.textoSaida = this.textoSaida + ":" + conteudo+"\n";
					
					//fim adição
					
					
					atriInicio = false;
					atributoInicio = "";
					atributoFim = "";
					correntAtri = "";
				}
				//FIM
				
				
						
			}
			
		
			c = this.obterCaracter();	
	
		}
		return this.textoSaida;
	}
	
	private void imprimeObj(){
		String objeto = "";
		for (int i = 1; i < objetoInicio.length()-1; i++) {
			objeto = objeto + objetoInicio.charAt(i);
		}
		//System.out.println("Objeto:" + objeto+"\n");
		
		this.textoSaida = "\n"+this.textoSaida + objeto+"\n";
	}
	
	private void imprimeAtrib(){
		String objeto = "";
		//System.out.println("--"+atributoInicio);
		for (int i = 1; i < atributoInicio.length()-1; i++) {
			objeto = objeto + atributoInicio.charAt(i);
		}
		//System.out.print("Atributo:" + objeto+"\n");
		
		this.textoSaida = this.textoSaida + "\t" + objeto;
	}
	
	public String getSaida(){
		return this.textoSaida;
	}
	
	
}
