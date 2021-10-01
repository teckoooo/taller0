//Nombres: Sergio Barraza - Gabriel Zuleta
import java.io.IOException;
import java.util.*;
import java.io.*;
import ucn.*;

public class taller0_4 {
	
	public static void main(String [] args) throws IOException {
		String [] nombres = new String[10000];
		String [] apellidos = new String[10000];
		String [] ruts = new String[10000];
		String [] contraseñas = new String[10000];
		double [] saldos = new double[10000];
		
		String [] rutStatus = new String[10000];
		String [] estado = new String[10000];
		
		String [] pelicula = new String[10000];
		String [] tipoPeli = new String [1000000];
		double [] recaudacion = new double[10000];
		String [] peliculas = new String [10000];
		String [][] funciones = new String [15][15];  
		String [][] asientosSala = new String[11][31];
		String [][] asientosSala2M = new String[11][31];
		String [][] asientosSala3M = new String[11][31];
		String [][] asientosSala1T = new String[11][31];
		String [][] asientosSala2T = new String[11][31];
		String [][] asientosSala3T = new String[11][31];
		String [][] asientos = new String[10000][1000];
		//asientos es por cliente
		double [] gastos= new double [100];
		int [] horarioSelect = new int[10000];
		int [] peliSelect = new int [10000];
		String [] rutIniciado = new String[10000];
		int contaa=0;
		int [] auxReca=new int [100];
		String [] filasA = {" ","A","B","C","D","E","F","G","H","I","J"};
		
		int cantidadClientes = leerClientes(nombres,apellidos,ruts,contraseñas,saldos);
		int cantidadStatus = leerStatus(rutStatus,estado);
		int cantidadPeliculas = leerPeliculas(pelicula, tipoPeli,recaudacion,funciones);
		int i= 0;
		int unicpelis=0;
		while (cantidadPeliculas>i) {
			int x=0;
		    while (cantidadPeliculas>x) {
		    	if (pelicula[i]!=peliculas[x]&& peliculas[x]==null) {
		    		peliculas[unicpelis]=pelicula[i];
		    		unicpelis++;
		    		break;
		    	}
		    	x++;
		    }
			i++;
		}
	    asientosLibres(asientosSala,filasA);
	    asientosLibres(asientosSala2M,filasA);
	    asientosLibres(asientosSala3M,filasA);
	    asientosLibres(asientosSala1T,filasA);
	    asientosLibres(asientosSala2T,filasA);
	    asientosLibres(asientosSala3T,filasA);
		
	    StdOut.println("-------- Bienvenido al Sistema de Cuevana --------");
	    StdOut.println("para salir del sistema, escriba exit en el rut");
	    String r="a";
	    while (!r.equals("exit")) {
	    	r= iniciarSesion(cantidadClientes,nombres,apellidos,ruts,contraseñas,saldos,estado,rutStatus,asientosSala,peliculas,funciones,cantidadStatus,filasA,pelicula,cantidadPeliculas,asientos,rutIniciado,horarioSelect,peliSelect,asientosSala2M,asientosSala3M,asientosSala1T,asientosSala2T,asientosSala3T,contaa,tipoPeli,gastos,auxReca);
	    	if (r.equals("agg")) {
	    		cantidadClientes++;
	    	}
	    }
	    cerrarSesion(nombres,apellidos,ruts,contraseñas,saldos,rutStatus,estado,pelicula,tipoPeli,recaudacion,funciones);
	    // AQUI VA CERRAR SESION
	}

	/**
	 * A function that reads the file "clientes.txt" and saves the data in the system through its methods
	 * @param sistema - system
	 * @return contadorClientes - number of clients entered
	 * @throws IOException - An exception
	 */
	public static int leerClientes(String [] nombres, String [] apellidos, String [] ruts, String [] contraseñas, double [] saldos) throws IOException {
		File obj = new File("clientes.txt");
		Scanner lector = new Scanner(obj);	
		int contadorClientes = 0;
		while(lector.hasNextLine() ) {
			String data = lector.nextLine();
			String[] partes = data.split(",");
			nombres [contadorClientes] = partes[0];
			apellidos[contadorClientes] = partes[1];
			String rut = partes[2];
			ruts[contadorClientes] = formatearRut(rut);
			contraseñas[contadorClientes] = partes[3];
			double saldo = Double.parseDouble(partes[4]);
			saldos [contadorClientes]= saldo;
		    contadorClientes ++;
		}
		lector.close();
		return contadorClientes;
	}
	
	/**
	 * A function that overwrites the file "clientes.txt" when the system is closed
	 * @param sistema - system
	 * @throws IOException - An exception
	 */
	public static void cerrarClientes(String[]nombres,String[]apellidos,String []ruts,String[]contraseñas,double[]saldos) throws IOException {
		ArchivoSalida archivo = new ArchivoSalida("clientes.txt");
		for (int i=0;i<nombres.length;i++) {
			if (ruts[i]==null) {
				break;
			}
			Registro registro = new Registro(5);
			int saldo =0; saldo= (int)saldos[i];
			registro.agregarCampo(nombres[i]);
			registro.agregarCampo(apellidos[i]);
			registro.agregarCampo(ruts[i]);
			registro.agregarCampo(contraseñas[i]);
			registro.agregarCampo(saldo);
			archivo.writeRegistro(registro);
		}
		archivo.close();
	}
	
	/**
	 * A function that reads the file "status.txt" and saves the data in the system through its methods
	 * @param sistema - system
	 * @return contadorStatus - number of status per client entered
	 * @throws IOException - An exception
	 */
	public static int leerStatus(String [] rutStatus,String [] estado) throws IOException {
		File obj = new File("status.txt");
		Scanner lector = new Scanner(obj);	
		int contador_Status = 0;
		while(lector.hasNextLine()) {
			String data = lector.nextLine();
			String[] partes = data.split(",");
			String rut = partes[0];
			String rutClienteStatusModificado = formatearRut(rut);
			rutStatus [contador_Status]= rutClienteStatusModificado;
		    estado [contador_Status] = partes[1];
		    contador_Status++;
		}
		lector.close();		
		return contador_Status;	
	}
	
	/**
	 * A function that overwrites the file "status.txt" when the system is closed
	 * @param sistema - system
	 * @throws IOException - An exception
	 */
	public static void cerrarStatus(String[]rutStatus,String[]estado) throws IOException {
		ArchivoSalida archivo = new ArchivoSalida("status.txt");
		for (int i=0;i<rutStatus.length;i++) {
			if (rutStatus[i]==null) {
				break;
			}
			Registro registro = new Registro(2);
			registro.agregarCampo(rutStatus[i]);
			registro.agregarCampo(estado[i]);
			archivo.writeRegistro(registro);
		}
		archivo.close();
	}
	
	/**
	 * A function that reads the file "peliculas.txt" and saves the data in the system through its methods
	 * @param sistema - system
	 * @return contadorPeliculas - number of movies entered
	 * @throws IOException - An exception
	 */
	public static int leerPeliculas(String [] pelicula, String [] tipoPeli,double [] recaudacion, String [][] funciones  )throws IOException {
		File obj = new File("peliculas.txt");
		Scanner lector = new Scanner(obj);	
        int contador_peliculas = 0;
        int cont=0;
        while( lector.hasNextLine()){
        	String data = lector.nextLine();
			String[] parte = data.split(",");
            pelicula[contador_peliculas] = parte[0];
            tipoPeli[contador_peliculas] = parte[1].toLowerCase();
            recaudacion[contador_peliculas] = Double.parseDouble(parte[2]);
            cont= parte.length - 3 ;
            int i=3;int y=0;
            while (cont>0) {
              funciones [contador_peliculas][y] = parte[i];
              cont--;i++;y++;
            }
            contador_peliculas++;
        }
        lector.close();
        return contador_peliculas;
    }
	
	/**
	 * A function that overwrites the file "peliculas.txt" when the system is closed
	 * @param sistema - system
	 * @throws IOException - An exception
	 */
	public static void cerrarPeliculas(String []pelicula, String[]tipoPeli,double[]recaudacion,String [][]funciones) throws IOException{
		ArchivoSalida archivo = new ArchivoSalida("peliculas.txt");
		for (int i=0;i<pelicula.length;i++) {
			if (pelicula[i]==null) {
				break;
			}
			String linea= "";int reca=0;
			reca= (int)recaudacion[i];
			Registro registro = new Registro(4);
			registro.agregarCampo(pelicula[i]);
			registro.agregarCampo(tipoPeli[i]);
			registro.agregarCampo(reca);
			for (int x=0;x<15;x++) {
				if (funciones[i][x]==null) {
					break;
				}
				linea= linea+funciones[i][x]+",";
			}
			linea = linea.replaceFirst(".$","");
			registro.agregarCampo(linea);
			archivo.writeRegistro(registro);
		}
		archivo.close();
	}
	
	/**
	 * With this function the user can buy a ticket for a certain movie at a certain function
	 * @param sistema - system
	 */
	public static void compraEntrada(String [][] asientosSala,String [] filasA,String [] pelicula, int cantidadPeliculas,String [][]funciones,String [][] asientos,String [] rutIniciado,int [] horarioSelec,int [] peliSelect, int cantidadClientes,String rut2,String [][] asientosSala2M,String [][] asientosSala3M,String [][] asientosSala1T,String [][] asientosSala2T,String [][] asientosSala3T,int [] auxReca, int contaa,String[]tipoPeli,String [] estado,double [] gastoCliente, double [] saldoCliente,String [] rutCliente){
		
		int conP = 0;
		StdOut.println("------Comprar entrada------");
		StdOut.println("Peliculas en Cartelera: ");
		for (int i = 0; i < cantidadPeliculas; i++) {
			StdOut.println((i + 1) + ")" + pelicula[i]);
		}
		StdOut.println("Seleccione la pelicula ");
		int peliSelec = StdIn.readInt();
		int peeeli = peliSelec-1;
		peliSelect[contaa] = peliSelec - 1;
		StdOut.println("Horarios de la pelicula ");
		StdOut.println("M = Mañana, T = Tarde");
		for (int c = 0; c < 15; c++) {
			if (funciones[peliSelec - 1][c] != null) {
				conP++;
			}
		}
		String[] coHor = new String[15];
		String[] coHora = new String[15];
		int co = 1;
		for (int c = 0; c < conP; c += 2) {
			if (funciones[peliSelec - 1][c] != null) {
				StdOut.println(co + ")" + " Sala " + funciones[peliSelec - 1][c] + " en la "
						+ funciones[peliSelec - 1][c + 1]);
				coHor[co-1] = funciones[peliSelec - 1][c];
				coHora[co-1] =funciones[peliSelec - 1][c + 1];
				co++;
			}
		}
		StdOut.println("Seleccione el Horario ");
		int horarioSelect = StdIn.readInt();
		horarioSelec[contaa] = horarioSelect - 1;
		int indiceSalaSelec = buscarIndiceInt(cantidadClientes, horarioSelec, horarioSelect -1);
		int indicePeliSelec = buscarIndiceInt(cantidadClientes, peliSelect, peliSelec-1 );
		String coord = (coHor[horarioSelect-1]+coHora[horarioSelect-1]);
		
		rutIniciado[contaa] = rut2;
		//StdOut.println(contaa+" a");
		StdOut.println("\n ----------");
		StdOut.println("¿Cuantas entradas desea comprar?");
		int cantidadAsientos = StdIn.readInt();
		auxReca[contaa]= cantidadAsientos;
		if(cantidadAsientos!=0)
		{	
				if (indiceSalaSelec == indicePeliSelec)
				{
					if (contaa == 0)
					{		
						selecCompra(coord,asientosSala,asientosSala2M,asientosSala3M,asientosSala1T,asientosSala2T,asientosSala3T,filasA,contaa,asientos,cantidadAsientos,auxReca,filasA,rutCliente);	
						montoCompra(contaa,peeeli,tipoPeli,estado,rutIniciado,auxReca,gastoCliente,saldoCliente,cantidadClientes,rutCliente);
					}
					else if (peliSelect[contaa - 1] != peliSelect[contaa])
					{
						for (int i = 0; i < contaa; i++)
						{
							if ((peliSelect[i] != indicePeliSelec) )
							{	
								selecCompra(coord,asientosSala,asientosSala2M,asientosSala3M,asientosSala1T,asientosSala2T,asientosSala3T,filasA,contaa,asientos,cantidadAsientos,auxReca,filasA,rutCliente);
							montoCompra(contaa,peeeli,tipoPeli,estado,rutIniciado,auxReca,gastoCliente,saldoCliente,cantidadClientes,rutCliente);
							}
							else
							{		
								selecCompra(coord,asientosSala,asientosSala2M,asientosSala3M,asientosSala1T,asientosSala2T,asientosSala3T,filasA,contaa,asientos,cantidadAsientos,auxReca,filasA,rutCliente);	
								montoCompra(contaa,peeeli,tipoPeli,estado,rutIniciado,auxReca,gastoCliente,saldoCliente,cantidadClientes,rutCliente);
							}	
						}	
					}		
					else if((peliSelect[contaa - 1] == peliSelect[contaa] )&& (horarioSelec[contaa-1] != horarioSelec[contaa]))
					{
						selecCompra(coord,asientosSala,asientosSala2M,asientosSala3M,asientosSala1T,asientosSala2T,asientosSala3T,filasA,contaa,asientos,cantidadAsientos,auxReca,filasA,rutCliente);
						montoCompra(contaa,peeeli,tipoPeli,estado,rutIniciado,auxReca,gastoCliente,saldoCliente,cantidadClientes,rutCliente);
					}
					else
					{
						selecCompra(coord,asientosSala,asientosSala2M,asientosSala3M,asientosSala1T,asientosSala2T,asientosSala3T,filasA,contaa,asientos,cantidadAsientos,auxReca,filasA,rutCliente);
						montoCompra(contaa,peeeli,tipoPeli,estado,rutIniciado,auxReca,gastoCliente,saldoCliente,cantidadClientes,rutCliente);
					}
					
				}	
				else
				{
					selecCompra(coord,asientosSala,asientosSala2M,asientosSala3M,asientosSala1T,asientosSala2T,asientosSala3T,filasA,contaa,asientos,cantidadAsientos,auxReca,filasA,rutCliente);
					montoCompra(contaa,peeeli,tipoPeli,estado,rutIniciado,auxReca,gastoCliente,saldoCliente,cantidadClientes,rutCliente);
				}
		}		
	}
	
	/**
	 * A function where the user can see his info, like his name and the money he has left
	 * @param sistema - system
	 */
	public static void infoUsuario(String ruts[],String[]nombres, String [] apellidos, double [] saldos, String []rutstatus, String [] estado, int indicerut) {
		StdOut.println("---Información del Usuario---");
		StdOut.println("rut: "+ruts[indicerut]);
		StdOut.println("Nombre completo: "+ nombres[indicerut]+ " "+ apellidos[indicerut]);
		StdOut.println("Saldo: "+ saldos[indicerut]);
		//asientos, entradas
	}
	
	/**
	 * A function where the Admin can see the clients info, like his name and the money he has left
	 * @param sistema - system
	 */
	public static void infoCliente(String[] ruts, int cantClientes,String[]nombres,String [] apellidos, double[] saldos,String[]rutIniciado,int[]peliSelect, int[]horarioSelect,String[]peliculas,String[][]asientos,String[][]funciones,String [] pelicula) {
		String rut;
		int conty = 0;
		StdOut.println("ingrese el rut de algun cliente");rut=StdIn.readString();
		String rutR= formatearRut(rut);
		StdOut.println(rutR);
		int index=buscarIndice(cantClientes, ruts, rutR);
		if (index==-1) {
			StdOut.println("este rut no estÃ¡ registrado");
		}
		else {
			int comprueba=0;int y=0; String []indices= new String[100];
			StdOut.println("Nombre del usuario:"+ nombres[index]+" "+apellidos[index]);
			StdOut.println("saldo: "+ (int)saldos[index]);
			for (int i=0;rutIniciado.length>i;i++) {
				if(rutIniciado[i]!=null) {
					if (rutIniciado[i].equals(rutR)) { 
						comprueba=1;
						indices[y]=String.valueOf(i);
						StdOut.println(rutIniciado[i]);
						y++;
					}
				}
			}
		//	imprimirLista(indices.length,indices);
			if (comprueba!=1) {
				StdOut.println("Este usuario no a comprado boletos");
			}
			else {
				StdOut.println("Este Usuario compro:");
				int indice=0; int conP = 0;
				for (int c = 0; c < 15; c++) {
					if (funciones[indice][c] != null) {
						conP++;
					}
				}
				for (int j = 0; indices.length > j; j++) {
					indice = Integer.parseInt(indices[j] + 1);
					for (int z = 0; peliculas.length > z; z++) {
						if (indice == z) {
							StdOut.println(peliculas[z]);
						}

						for (int ca = 0; ca < conP; ca += 2) {
							if ((funciones[indice - 1][ca] != null)) {

								StdOut.println(" Sala:" + funciones[indice ][horarioSelect[indice - 1] * 2]
										+ " en la " + funciones[indice - 1][(horarioSelect[indice - 1] * 2) + 1]);
								conty++;

							}
						}

					}

					if (Integer.parseInt(indices[j]) == 0) {
						break;
					}

				}
			}
		}
	}
	
	/**
	 * This function will display every movie available
	 * @param sistema - system
	 */
	public static void cartelera(String [][]funciones,String []peliculas) {
		StdOut.println("---Las funciones de hoy son---");
		for (int x=0;peliculas.length>x; x++) {
			if (peliculas[x]==null){
				break;
			}
			StdOut.println(x+1+")"+peliculas[x]);
			StdOut.println("con la funcion(es):");
			for (int y=0; y<15; y=y+2 ) {
				if (funciones[x][y]==null) {
					break;
				}
				StdOut.print("sala:"+funciones[x][y]);
				if (funciones[x][y+1] == "M") {
					StdOut.println(" Mañana");
				}
				else {
					StdOut.println(" Tarde");
				}
				
				
			}
		}
	}
	
	/**
	 * this function will make a refund for one ticket the client buyed
	 * @param sistema - system
	 */
	public static void devolucion(double[]saldo,int indiceRut,String[][]asientos) {
		StdOut.println("---De que boleto desea la devolución?---");
	}
	
	/**
	 * this function will display the amount of money every movie made
	 * @param sistema - system
	 */
	public static void infoTaquilla() {
	}
	
	/**
	 * this function generates the matrix and saves it on the system
	 * @param sistema - system
	 */
	public static void asientosLibres(String[][] asientosSala, String[] filasA) {
		for (int filas = 0; filas < 11; filas++) {
			for (int columnas = 0; columnas < 31; columnas++) {
				asientosSala[0][0] = " ";
				if ((0 < filas) && (filas <= 4) && (columnas <= 5) && (0 < columnas)) {
					asientosSala[filas][columnas] = "  ";
				} else if ((0 < filas) && (filas <= 4) && (columnas >= 26)) {
					asientosSala[filas][columnas] = "  ";
				} else if ((filas == 0) && (columnas < 10)) {
					asientosSala[filas][columnas] = String.valueOf(" " + columnas);
				} else if ((filas == 0) && (columnas >= 10)) {
					asientosSala[filas][columnas] = String.valueOf(columnas);
				} else if (columnas == 0) {
					{
						asientosSala[filas][0] = filasA[filas];
					}
				}
				else {
					asientosSala[filas][columnas] = " O";
				}
			}
		}
	}
	
	/**
	 * With this function will be able to buy his tickets and will save on the system some values
	 * @param sistema - system
	 */
	public static void compraEntradas(String [][] asientosSal, String [] filas,int contaa,String [][]asientos,int cantidadAsientos,int [] auxReca,String []filasA,String [] rutCliente){
		
		int y = 0;
		int j = 1;
		int cont = 0;
		int filaComprar;
		int asientoComprar;
		imprimirMatrizString(asientosSal, 11, 31);	
		for (int l = 0; l < cantidadAsientos; l++) 
		{
			while (j != 0) {
				StdOut.println("Seleccione la fila en la que desea comprar: ");
				String filaAComprar = StdIn.readString().toUpperCase();
				filaComprar = buscarIndice(10, filas, filaAComprar);
				StdOut.println("Seleccione el numero del asiento que desea comprar: ");
				asientoComprar = StdIn.readInt();
				
					if (asientosSal[filaComprar][asientoComprar] != " 1"
							&& asientosSal[filaComprar][asientoComprar] != " X"
							&& (asientosSal[filaComprar][asientoComprar] != "  "))
					{
						if (asientoComprar - 1 >= 0 && asientoComprar - 1 != 2) {
							asientosSal[filaComprar][asientoComprar] = " 1";
							if (asientos[contaa][y] == null) {
								asientos[contaa][y] = filaAComprar + String.valueOf(asientoComprar);
							}
							y++;
						}
						if (asientosSal[filaComprar][asientoComprar - 1] != " 1") {
							if ((asientosSal[filaComprar][asientoComprar - 1] == "  ")) {
								asientosSal[filaComprar][asientoComprar - 1] = "  ";
							} else  {
								if((asientosSal[filaComprar][asientoComprar - 1] != "A")&&(asientosSal[filaComprar][asientoComprar - 1] != "B")&&(asientosSal[filaComprar][asientoComprar - 1] != "C")&&(asientosSal[filaComprar][asientoComprar - 1] != "D")&&(asientosSal[filaComprar][asientoComprar - 1] != "E")&&(asientosSal[filaComprar][asientoComprar - 1] != "F")&&(asientosSal[filaComprar][asientoComprar - 1] != "G")&&(asientosSal[filaComprar][asientoComprar - 1] != "H")&&(asientosSal[filaComprar][asientoComprar - 1] != "I")&&(asientosSal[filaComprar][asientoComprar - 1] != "J"))
								{
								asientosSal[filaComprar][asientoComprar - 1] = " 2";
								}
							}
						}
						if (asientosSal[filaComprar][asientoComprar + 1] != " 1") {
							if (asientosSal[filaComprar][asientoComprar + 1] == "  ") {
								asientosSal[filaComprar][asientoComprar + 1] = "  ";
							}  
							else 
							{
								asientosSal[filaComprar][asientoComprar + 1] = " 2";
							}
						}
					} 
					else {
						StdOut.println("asiento no valido! reintente");
						j = 1;
						cont--;
					}
					cont++;
					if (cont == cantidadAsientos) {
						j = 0;
					}
				}
				contaa++;
			}
		
		for (int f = 1; f < 11; f++) {
			for (int c = 1; c < 31; c++) {
				if (asientosSal[f][c] != " X" && asientosSal[f][c] != "  " && asientosSal[f][c] != " O") {
					asientosSal[f][c] = " X";
				}
			}
		}
	}
	
	/**
	 * With this the system will save data if the incoming string coord is equal with one of the options
	 * @param sistema - system
	 */
	public static void selecCompra(String coord,String [][] asientosS1,String [][] asientosS2,String [][] asientosS3,String [][] asientosS4,String [][] asientosS5,String [][] asientosS6,String [] f,int contaa,String [][] asientos, int cantidadAss,int [] auxReca,String []filasA,String [] rutCliente){
		if (coord.equals("1M")) {
			StdOut.println("----Sala 1 en la Mañana----");
			StdOut.println("Asientos disponibles: ");
			compraEntradas(asientosS1, f, contaa, asientos, cantidadAss, auxReca,filasA,rutCliente);
			imprimirMatrizString(asientosS1, 11, 31);
			cantidadAss--;
		}
		if (coord.equals("2M")) {
			StdOut.println("----Sala 2 en la Mañana----");
			StdOut.println("Asientos disponibles: ");
			compraEntradas(asientosS2, f, contaa, asientos, cantidadAss, auxReca,filasA,rutCliente);
			imprimirMatrizString(asientosS2, 11, 31);
			cantidadAss--;
		}
		if (coord.equals("3M")) {
			StdOut.println("----Sala 3 en la Mañana----");
			StdOut.println("Asientos disponibles: ");
			compraEntradas(asientosS3, f, contaa, asientos, cantidadAss, auxReca,filasA,rutCliente);
			imprimirMatrizString(asientosS3, 11, 31);
			cantidadAss--;
		}
		if (coord.equals("1T")) {
			StdOut.println("----Sala 1 en la Tarde----");
			StdOut.println("Asientos disponibles: ");
			compraEntradas(asientosS4, f, contaa, asientos, cantidadAss, auxReca,filasA,rutCliente);
			imprimirMatrizString(asientosS4, 11, 31);
			cantidadAss--;
		}
		if (coord.equals("2T")) {
			StdOut.println("----Sala 2 en la Tarde----");
			StdOut.println("Asientos disponibles: ");
			compraEntradas(asientosS5, f, contaa, asientos, cantidadAss, auxReca,filasA,rutCliente);
			imprimirMatrizString(asientosS5, 11, 31);
			cantidadAss--;
		}
		if (coord.equals("3T")) {
			StdOut.println("----Sala 3 en la Tarde----");
			StdOut.println("Asientos disponibles: ");
			compraEntradas(asientosS6, f, contaa, asientos, cantidadAss, auxReca,filasA,rutCliente);
			imprimirMatrizString(asientosS6, 11, 31);
			cantidadAss--;
		}
	}	
	
	/**
	 * this function will calculate the amount of money the client is going to use
	 * @param sistema - system
	 */
	public static void montoCompra(int contaa,int peeeli,String [] tipoPeli,String[] estado,String []rutIniciado,int []auxReca,double []gastoCliente,double [] saldoCliente,int cantidadClientes, String [] rutCliente)
	{
		if(contaa==0)
		{	
				for (int i = 0; i < cantidadClientes; i++) 
				{
					if (rutIniciado[0].equals(rutCliente[i]))
					{	
						if (tipoPeli[peeeli].equals("estreno")) 
						{
							if (estado[0].equals("HABILITADO"))	
							{
								StdOut.println("El monto a pagar es de: " + ((auxReca[0] * 5500) - ((auxReca[0] * 5500) * 0.15)));
								gastoCliente[i] += ((auxReca[0] * 5500) - ((auxReca[0] * 5500) * 0.15));
								StdOut.println(gastoCliente[i]);
								StdOut.println(saldoCliente[i]);
								if (saldoCliente[i] >= gastoCliente[i]) {
									StdOut.println("Saldo suficiente para realizar la compra");
									StdOut.println("¿Desea efecturar la compra?");
									StdOut.println("1)Si \n2)No");
									int opc = StdIn.readInt();
									if (opc == 1) {
										saldoCliente[i] -= (gastoCliente[i]);
										StdOut.println("Compra realizada con Exito! \nSu nuevo saldo es de: "+ saldoCliente[i]);
									}
								}
								if(saldoCliente[i] < gastoCliente[i]) {
									recarga(saldoCliente,i);
								}
							}
							else
							{
								StdOut.println(
										"El monto a pagar es de: " + ((auxReca[0] * 5500) - ((auxReca[0] * 5500) * 0.15)));gastoCliente[i] += ((auxReca[0] * 5500));
								if (saldoCliente[i] >= gastoCliente[i]) 
								{
									StdOut.println("Saldo suficiente para realizar la compra");
									StdOut.println("¿Desea efecturar la compra?");
									StdOut.println("1)Si \n2)No");
									int opc = StdIn.readInt();
									if (opc == 1) {
										saldoCliente[i] -= (gastoCliente[i]);
										StdOut.println(
												"Compra realizada con Exito! \nSu nuevo saldo es de: " + saldoCliente[i]);
									}
								} 
								if(saldoCliente[i]< gastoCliente[i])
								{
									recarga(saldoCliente,i);
									// Preguntar si quiere recargar dinero
						  		}
							}
							
						}
						 else if (tipoPeli[peeeli].equals("liberada")) 
							{
							 if (estado[i].equals("HABILITADO"))	
								{
								StdOut.println(
										"El monto a pagar es de: " + ((auxReca[0] * 4000) - ((auxReca[0] * 4000) * 0.15)));
								gastoCliente[i] += ((auxReca[0] * 4000) - ((auxReca[0] * 4000) * 0.15));
								if (saldoCliente[i] >= gastoCliente[i]) {
									StdOut.println("Saldo suficiente para realizar la compra");
									StdOut.println("¿Desea efecturar la compra?");
									StdOut.println("1)Si \n2)No");
									int opc = StdIn.readInt();
									if (opc == 1) {
										saldoCliente[i] -= (gastoCliente[i]);
										StdOut.println(
												"Compra realizada con Exito! \nSu nuevo saldo es de: " + saldoCliente[i]);
									}
								} 
								else if (saldoCliente[i] < gastoCliente[0]) {
									recarga(saldoCliente,i);
									// Preguntar si quiere recargar dinero
						  		}
								}
								else if (saldoCliente[i] < gastoCliente[i])
								{
									StdOut.println(
											"El monto a pagar es de: " + ((auxReca[0] * 4000)));
									gastoCliente[i] += ((auxReca[0] * 4000));
									if (saldoCliente[i] >= gastoCliente[i]) {
										StdOut.println("Saldo suficiente para realizar la compra");
										StdOut.println("¿Desea efecturar la compra?");
										StdOut.println("1)Si \n2)No");
										int opc = StdIn.readInt();
										if (opc == 1) {
											saldoCliente[i] -= (gastoCliente[i]);
											StdOut.println(
													"Compra realizada con Exito! \nSu nuevo saldo es de: " + saldoCliente[i]);
										}
									} 
									else if (saldoCliente[i] < gastoCliente[0]){
										recarga(saldoCliente,i);
										// Preguntar si quiere recargar dinero
							  		}
								}
							}
						
					}
				}
				contaa++;
		}
		else {
		for(int i = 0;i<contaa;i++)
		{
			if (rutIniciado[contaa].equals(rutCliente[contaa]))
			{
			if (tipoPeli[peeeli].equals("estreno")) 
			{
				StdOut.println("estreno");
				if(estado[buscarIndice(contaa,rutIniciado,rutIniciado[i])].equals("HABILITADO"))
				{	StdOut.println(
						"El monto a pagar es de: " + ((auxReca[contaa] * 5500) - ((auxReca[contaa] * 5500) * 0.15)));
				gastoCliente[i] += ((auxReca[contaa] * 5500) - ((auxReca[contaa] * 5500) * 0.15));
				if (saldoCliente[buscarIndice(cantidadClientes, rutIniciado,
						rutIniciado[contaa])] >= gastoCliente[contaa]) {
					StdOut.println("Saldo suficiente para realizar la compra");
					StdOut.println("¿Desea efecturar la compra?");
					StdOut.println("1)Si \n2)No");
					int opc = StdIn.readInt();
					if (opc == 1) {
						saldoCliente[contaa] -= (gastoCliente[i]);
						StdOut.println(
								"Compra realizada con Exito! \nSu nuevo saldo es de: " + saldoCliente[contaa]);
					}
				} 
				else if (saldoCliente[buscarIndice(cantidadClientes, rutIniciado,
						rutIniciado[contaa])] < gastoCliente[contaa]) {
					recarga(saldoCliente,contaa);
					// Preguntar si quiere recargar dinero
		  		}
				}
				else
				{
					StdOut.println(
							"El monto a pagar es de: " + ((auxReca[contaa] * 4000)));
					gastoCliente[i] += ((auxReca[contaa] * 4000));
					if (saldoCliente[buscarIndice(cantidadClientes, rutIniciado,
							rutIniciado[contaa])] >= gastoCliente[contaa]) {
						StdOut.println("Saldo suficiente para realizar la compra");
						StdOut.println("¿Desea efecturar la compra?");
						StdOut.println("1)Si \n2)No");
						int opc = StdIn.readInt();
						if (opc == 1) {
							saldoCliente[contaa] -= (gastoCliente[i]);
							StdOut.println(
									"Compra realizada con Exito! \nSu nuevo saldo es de: " + saldoCliente[contaa]);
						}
					} 
					else if (saldoCliente[buscarIndice(cantidadClientes, rutIniciado,
							rutIniciado[contaa])] < gastoCliente[contaa]) {
						recarga(saldoCliente,contaa);
						// Preguntar si quiere recargar dinero
			  		}
					
				} 

			} else if (tipoPeli[peeeli].equals("liberada")) 
			{
				
				if(estado[buscarIndice(contaa,rutIniciado,rutIniciado[i])].equals("HABILITADO"))
				{
				StdOut.println(
						"El monto a pagar es de: " + ((auxReca[contaa] * 4000) - ((auxReca[contaa] * 4000) * 0.15)));
				gastoCliente[i] += ((auxReca[contaa] * 4000) - ((auxReca[contaa] * 4000) * 0.15));
				if (saldoCliente[buscarIndice(cantidadClientes, rutIniciado,
						rutIniciado[contaa])] >= gastoCliente[contaa]) {
					StdOut.println("Saldo suficiente para realizar la compra");
					StdOut.println("¿Desea efecturar la compra?");
					StdOut.println("1)Si \n2)No");
					int opc = StdIn.readInt();
					if (opc == 1) {
						saldoCliente[contaa] -= (gastoCliente[i]);
						StdOut.println(
								"Compra realizada con Exito! \nSu nuevo saldo es de: " + saldoCliente[contaa]);
					}
				} 
				else  if (saldoCliente[buscarIndice(cantidadClientes, rutIniciado,
						rutIniciado[contaa])] < gastoCliente[contaa]) {
					recarga(saldoCliente,contaa);
					// Preguntar si quiere recargar dinero
		  		}
				}
				else
				{
					StdOut.println(
							"El monto a pagar es de: " + ((auxReca[contaa] * 4000)));
					gastoCliente[i] += ((auxReca[contaa] * 4000));
					if (saldoCliente[buscarIndice(cantidadClientes, rutIniciado,
							rutIniciado[contaa])] >= gastoCliente[contaa])
					{
						StdOut.println("Saldo suficiente para realizar la compra");
						StdOut.println("¿Desea efecturar la compra?");
						StdOut.println("1)Si \n2)No");
						int opc = StdIn.readInt();
						if (opc == 1) {
							saldoCliente[contaa] -= (gastoCliente[i]);
							StdOut.println(
									"Compra realizada con Exito! \nSu nuevo saldo es de: " + saldoCliente[contaa]);
						}
					} 
					else if (saldoCliente[buscarIndice(cantidadClientes, rutIniciado,
							rutIniciado[contaa])] < gastoCliente[contaa]) {
						
						recarga(saldoCliente,contaa);
					 }
			  		}
				}

			} else {
				StdOut.println(tipoPeli[peeeli]);
			}
		}
		}
		
	
	
}
	
	/**
	 * if the client doesn't have enough money to buy a ticket this function will allow him to re charge his founds
	 * @param sistema - system
	 */
	public static void recarga(double [] saldoCliente, int cuentaa)
	{
		StdOut.println("Saldo insuficiente! \n¿Desea recargar dinero a su cuenta? \n1)Si \n2)No");
		int opci = StdIn.readInt();
		if (opci == 1) 
		{
			StdOut.println("Ingrese el monto que desea recargar: ");
			double cantidad = StdIn.readDouble();
			saldoCliente[cuentaa] += (cantidad);
			StdOut.println("Recarga realizada con exito! \nSu nuevo saldo es: "+saldoCliente[cuentaa]);
		}
	}
	
	/**
	 * this function will be saving the values of the matrix asientos in order to display wich sit's are used
	 * @param sistema - system
	 */
	public static void llenarAssientos (String [][] asientosSala,String [] filasA, String [][] asientos)
	{
		String faux;
		String caux;
		String coor;
		String [] x;
		for (int filas = 0; filas < 11; filas++) 
		{

			for (int columnas = 0; columnas < 31; columnas++) 
			{
				asientosSala[0][0] = " ";
				if(asientos[filas][columnas] != null)
				{
					coor = asientos[filas][columnas];
					
					faux =String.valueOf(coor.charAt(0));
					caux = String.valueOf(coor.charAt(1));
					int fa = buscarIndice(filasA.length,filasA,faux);
					int ca = Integer.parseInt(caux);
					asientosSala[fa+1][ca+1] = " X";
				}
				 
				if((0< filas) && (filas<=4) &&(columnas <= 5) && (0 < columnas))
				{
					asientosSala[filas][columnas] = "  ";
				}
				else if((0<filas) && (filas <= 4) && (columnas >= 26)  )
				{
					asientosSala[filas][columnas] = "  ";
				}
				else if ((filas == 0) && (columnas < 10))
				{
					asientosSala[filas][columnas] = String.valueOf(" "+columnas);
				}
				else if ((filas == 0) && (columnas >= 10))
				{
					asientosSala[filas][columnas] = String.valueOf(columnas);
				}
				else if(columnas == 0 )
				{
					{                                                                                  
			    		asientosSala[filas][0] = filasA[filas];                                                                       
					}
				}
				else
				{
					asientosSala[filas][columnas] = " O";
				}
			}

		}
	}
	
	/**
	 * this will search the index of the element in a vector
	 * @param sistema - system
	 * @return i - index needed
	 * @return -1 - not found in the vector
	 */
	public static int buscarIndice(int cantidad, String [] lista,String clave) {
		int i;
		for(i=0; i<cantidad; i++) {
			if(lista[i].equals(clave)) {
				break;
			}	
		}
		if(i==cantidad) {
			return -1;
		}
		else {
			return i;
		}
	}
	
	/**
	 * this will search the index of the element in the vector contraseña
	 * @param sistema - system
	 * @return i - index needed
	 * @return -1 - not found in the vector
	 */
	public static int buscarIndiceContra(int cantidad, String [] lista,String clave,int indiceRut, String[] ruts) {
		int i; int x=0;
		for(i=0; i<cantidad; i++) {
			if(indiceRut==-1) {
				indiceRut=0;
			}
			if(lista[i].equals(clave)&& i==indiceRut) {
				x=i;
				break;
			}
			if(lista[i].equals(clave)) {
				x=i;
			}
		}
		if(i==cantidad && x<=0) {
			return -1;
		}
		if(i==cantidad && x>0) {
			return x;
		}
		else {
			return x;
		}
	}

	/**
	 * this will search the index of the element in a int vector
	 * @param sistema - system
	 * @return i - index needed
	 * @return -1 - not found in the vector
	 */
	public static int buscarIndiceInt(int cantidad,int [] lista,int clave) {
		int i;
		for(i=0; i<cantidad; i++) {
			if(lista[i] == clave) {
				break;
			}	
		}
		if(i==cantidad) {
			return -1;
		}
		else {
			return i;
		}
	}
	
	/**
	 * this will change the format of the string rut in order to ve able to save it on the system
	 * @param sistema - system
	 * @return format - rut in the new format
	 */
	public static String formatearRut(String rut){
	    int cont=0;
	    String format = rut;
	    if(rut.length() == 0){
	        return "";
	    }else{
	        rut = rut.replace(".", "");
	        rut = rut.replace("-", "");
	        format = "-"+rut.substring(rut.length()-1);
	        for(int i = rut.length()-2;i>=0;i--){
	            format = rut.substring(i, i+1)+format;
	            cont++;
	            if(cont == 3 && i != 0){
	                format = "."+format;
	                cont = 0;
	            }
	        }
	        return format.toLowerCase();
	    }
     }
	
	/**
	 * this will print a String matrix
	 * @param sistema - system
	 */
	public static void imprimirMatrizString(String[][] m, int f, int c) {
		for (int i = 0; i < f; i++) 
		{
			for (int j = 0; j < c; j++)
			{
				StdOut.print(m[i][j] + " ");
			}
			StdOut.println();
		}
		StdOut.println();
	}
	
	/**
	 * this will print a Int matrix
	 * @param sistema - system
	 */
	public static void imprimirMatriz(int[][] m, int f, int c) {
		for (int i = 0; i < f; i++) 
		{
			for (int j = 0; j < c; j++)
			{
				StdOut.print(m[i][j] + " ");
			}
			StdOut.println();
		}
		StdOut.println();
	}
	
	/**
	 * this will print a String vector
	 * @param sistema - system
	 */
	public static void imprimirLista(int N, String [] lista) {
		for (int i = 0; i < N; i++) {
				StdOut.println(lista[i]);
			}
	}
	
	/**
	 * with this function the user will be able to interact with the system
	 * @param sistema - system
	 */
	public static void menuUsuario(String [][] asientosSala,String[] ruts,String[]nombres, String [] apellidos, double [] saldos, String []rutStatus, String [] estado, int indicerut,String[]peliculas, String[][] funciones,String[]filasA,String[]pelicula,int cantidadPeliculas,String[][]asientos,String[]rutIniciado,int []horarioSelec,int []peliSelect,int cantidadClientes,String rut2,String[][]asientosSala2M,String[][]asientosSala3M,String[][]asientosSala1T,String[][]asientosSala2T,String[][]asientosSala3T,int contaa,String[]tipoPeli,double[]gastos,int[]auxReca) {
		StdOut.println("");
		StdOut.println("Que accion desea realizar?");StdOut.println("1) Comprar Entrada");StdOut.println("2) Información cuenta");StdOut.println("3) Devolución de entrada");StdOut.println("4) Ver la cartelera");StdOut.println("5) Salir");
        String respuesta= StdIn.readString();
        while (!respuesta.equals("5")) {
            if (respuesta.equals("1"))  {
            	compraEntrada(asientosSala,filasA,pelicula,cantidadPeliculas,funciones,asientos,rutIniciado,horarioSelec,peliSelect,cantidadClientes,rut2,asientosSala2M,asientosSala3M,asientosSala1T,asientosSala2T,asientosSala3T,auxReca,contaa,tipoPeli,estado,gastos,saldos,ruts);
            	contaa++;
			}
		if (respuesta.equals("2")) {
			}
            if (respuesta.equals("2")) {
            	infoUsuario(ruts,nombres,apellidos,saldos,rutStatus,estado,indicerut);
            }
            if (respuesta.equals("3")) {
            	//devolucion 
            }
            if (respuesta.equals("4")) { 
            	cartelera(funciones,peliculas);
            }
            if (!respuesta.equals("1")&&!respuesta.equals("2")&&!respuesta.equals("3")&&!respuesta.equals("4")) { 
                StdOut.println("Numero no valido, intente nuevamente");
            }     
            StdOut.println("Que accion desea realizar?");StdOut.println("1) Comprar Entrada");StdOut.println("2) Información cuenta");StdOut.println("3) Devolución de entrada");StdOut.println("4) Ver la cartelera");StdOut.println("5) Salir"); respuesta= StdIn.readString();
        }
	}
	
	/**
	 * with this function the ADMIN will be able to interact with the system
	 * @param sistema - system
	 */
    public static void menuAdmin(String[] ruts, int cantidadClientes,String[]nombres,String [] apellidos, double[] saldos, String[]rutIniciado,int[]peliSelect, int[]horarioSelect,String[]peliculas,String[][]asientos,String[][]funciones,String[]pelicula) {
    	StdOut.println("");	
    	StdOut.println("Que accion desea realizar?");StdOut.println("1) Ver información de Taquilla ");StdOut.println("2) Informacion de clientes");StdOut.println("3) Salir");
    	String respuesta= StdIn.readString();
    	while (!respuesta.equals("3")) {
    		if (respuesta.equals("1")) {
    			
    		}
    		if (respuesta.equals("2")){
    			infoCliente(ruts,cantidadClientes,nombres,apellidos,saldos,rutIniciado,peliSelect,horarioSelect,peliculas,asientos,funciones,pelicula);
    		}
    		if (!respuesta.equals("1")&&!respuesta.equals("2")){ 
    			StdOut.println("Numero no valido, intente nuevamente");
    		}
        StdOut.println("Que accion desea realizar?");StdOut.println("1) Ver información de Taquilla ");StdOut.println("2) Informacion de clientes");StdOut.println("3) Salir"); respuesta= StdIn.readString();
    	}
    }
	
	/**
	 * with this function the user or ADMIN will be able to log in in order to interact with the system
	 * @param sistema - system
	 */
	public static String iniciarSesion(int cantidadClientes,String [] nombres, String [] apellidos, String [] ruts, String [] contraseñas, double [] saldos,String [] estado,String [] rutStatus,String [][] asientosSala,String[]peliculas, String[][]funciones,int cantidadStatus,String[]filasA,String[]pelicula,int cantidadPeliculas,String[][]asientos,String[]rutIniciado,int []horarioSelect,int []peliSelect,String[][]asientosSala2M,String[][]asientosSala3M,String[][]asientosSala1T,String[][]asientosSala2T,String[][]asientosSala3T,int contaa,String[]tipoPeli,double[]gastos,int[]auxReca){
		String r = "constante"; // string que retorna. debe ser "exit" o distinto a eso
		int respuesta = 1;
		while(respuesta!=-1) {
			StdOut.println("-  INICIO DE SESION  - ");
			StdOut.println("Ingrese rut: ");
			String rut = StdIn.readString();
			String rut2 = formatearRut(rut);
			if(rut.equals("exit")){ //si no es admin
				r = "exit";
				StdOut.println("-		Cerrando...		-");
				break;}
			StdOut.println("Ingrese contraseña: ");
			String contra = StdIn.readString();
			int indiceRut = buscarIndice(cantidadClientes, ruts, rut2);		
			int indiceContra = buscarIndiceContra(cantidadClientes, contraseñas, contra,indiceRut,ruts);
			if(rut.equals("ADMIN") && contra.equals("ADMIN")) {
				StdOut.println("-		Inicio menu admin		-");
				r = "ADMIN";
				 menuAdmin(ruts,cantidadClientes, nombres, apellidos,saldos,rutIniciado,peliSelect,horarioSelect,peliculas,asientos,funciones,pelicula);
				break;
			}
			else { 
					if(indiceContra==indiceRut) { // si los indices son iguales (misma posicion)
						if(indiceContra!=-1 && indiceRut!=-1) { // son iguales y si existen (ya está reg)
							StdOut.println("-		Inicio menu usuario		-");
							menuUsuario(asientosSala,ruts,nombres, apellidos,saldos,rutStatus,estado,indiceRut,peliculas,funciones,filasA,pelicula,cantidadPeliculas,asientos,rutIniciado,horarioSelect,peliSelect,cantidadClientes,rut2,asientosSala2M,asientosSala3M,asientosSala1T,asientosSala2T,asientosSala3T,contaa,tipoPeli,gastos,auxReca);							
							break;
						}else { //son iguales pero no existen (-1, no está reg)
							if(indiceContra==-1 && indiceRut == -1) {
								StdOut.println("Usuario no registrado. Si se desea registrar ingrese '1'. Cualquier otra tecla para reintentar.");
								String respuesta2 = StdIn.readString();
								if(respuesta2.equals("1") ) { //si es 1 registra nuevo user
									StdOut.println("Ingrese su nombre: ");
									String nombre = StdIn.readString();					
									StdOut.println("Ingrese su apellido: ");
									String apellido = StdIn.readString();
									StdOut.println("Ingrese su Contraseña: ");
									String contraNuevo = StdIn.readString();
									StdOut.println("Ingrese su Saldo: ");
									double saldoClienteNuevo = StdIn.readDouble();
									StdOut.println("Seleccione su estado del Pase de Movilidad: ");
									StdOut.println("1) Habilitado ");
									StdOut.println("2) No Habilitado ");
									int estadoPase = StdIn.readInt();
									String status= "";
									while (estadoPase!=1 && estadoPase!=2) {
										StdOut.println("Porfavor, ingrese un numero valido");
										estadoPase = StdIn.readInt();
									}
									if(estadoPase == 1)
									{
										status= "HABILITADO";
									}
									if(estadoPase ==2) {
										status= "NO HABILITADO";
									}
									
									ruts[cantidadClientes] = rut2;
									rutStatus[cantidadClientes] = rut2;
									contraseñas[cantidadClientes] = contraNuevo;
									nombres[cantidadClientes] = nombre;
									apellidos[cantidadClientes] = apellido;
									saldos[cantidadClientes] = saldoClienteNuevo;
									estado[cantidadClientes]=status.toUpperCase();
									cantidadClientes++;
									
									StdOut.println("Usuario registrado!");
									r = "agg"; 
									break;
								}else { 
									break;
								}
							}
						}
					}else { // si indice contra e indice rut son distintos, se equivocó en ingresar dato
						if(indiceContra!=indiceRut) {
							StdOut.println("Hubo un error. Ingrese datos nuevamente");
							break;
						}
					}
				}	
		}
		return r;
		}
	
	/**
	 * with this function the system will overwrite all the files it was using
	 * @param sistema - system
	 */
	public static void cerrarSesion(String[]nombres,String[]apellidos,String[]ruts,String[]contraseñas,double[]saldos,String[]rutStatus,String[]estado,String[]pelicula,String[]tipoPeli,double[]recaudacion,String[][]funciones)throws IOException  { 
	    cerrarClientes(nombres,apellidos,ruts,contraseñas,saldos);
	    cerrarStatus(rutStatus,estado);
	    cerrarPeliculas(pelicula,tipoPeli,recaudacion,funciones);
		
	}

}	