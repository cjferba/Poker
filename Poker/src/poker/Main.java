package poker;
import java.util.*;
import java.io.*;

/**
 *
 * @author CArlos Jesus Fernandez Basso
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws java.io.IOException {
        //Para lectura de string
        DataInputStream strInput= new DataInputStream(System.in);
        //Para lectura de numeros
        StreamTokenizer numInput= new StreamTokenizer(System.in); 
        int op;
    
	JuegoPoker juego = new JuegoPoker();
        
        while (true) {
                System.out.println("MENÚ POKER");
                System.out.println("***** Elija opcion *****");
		System.out.println("1. Introducir Informacion de partida");
		System.out.println("2. Comenzar Partida");
		System.out.println("3. Salir");
                
                numInput.nextToken();
                op= (int) numInput.nval;

                if (op == 3) {break;}
                //End if
                LinkedList<String> nombre=new LinkedList();
                
                switch (op){
                case 1:
                    //numero de jugadores
                    System.out.println("Indique el numero de jugadores: ");
                    numInput.nextToken();
                    int aux=(int) numInput.nval;
                    //nombre de jugadores
                    for(int i=0;i<aux;i++){
                        System.out.println("Introduzca el nombre del jugador " + (i+1) + ": ");
                        nombre.add(strInput.readLine());
                    }
                    //Ganancias
                    int G=0,rondas;
                    System.out.println("Introduzca las ganancias iniciales: ");
                    do {
                        numInput.nextToken();
                        G = (int) numInput.nval;
                    } while (G<=0);
                    //rondas
                    System.out.println("Introduzca el número de rondas de la partida: ");

                    do {
                        numInput.nextToken();
                        rondas = (int) numInput.nval;
                    } while (rondas<=0);
                    //creamos nuestro juego
                    juego = new JuegoPoker(rondas,G,nombre);
                    break;
                case 2:
                    //Partida
                    String s=juego.partida();
                    System.out.println("Comienza Partida\n"+s);
                    break;
                }//End switch

        }//End while
	
    }

}





/* 
   para las cartas
 * //Añadimos las cartas de cada jugador, junto con su nombre y sus ganancias.
        cartas=new JLabel[juego.jugadores.size()][];
        
        for (int i=0; i<juego.jugadores.size(); i++){
            gbc.gridx=0;
            gbc.gridy=i+1;
            panelReparto.add(jugNombres.get(i),gbc);
            
            gbc.gridx=1;
            gbc.gridy=i+1;
            panelReparto.add(jugGanancias.get(i),gbc);
            
            cartas[i]=new JLabel[5];
            
            for (int j=0; j<5; j++) {
                cartas[i][j]=new JLabel();
                
                if (juego.jugadores.get(i).cartas.get(j).palo().equals("Corazones"))
                    cartas[i][j].setIcon(new ImageIcon(getClass().getResource("1_"+juego.jugadores.get(i).cartas.get(j).numero()+".JPG")));
                
                else if (juego.jugadores.get(i).cartas.get(j).palo().equals("Tréboles"))
                    cartas[i][j].setIcon(new ImageIcon(getClass().getResource("2_"+juego.jugadores.get(i).cartas.get(j).numero()+".JPG")));
                
                else if (juego.jugadores.get(i).cartas.get(j).palo().equals("Rombos"))
                    cartas[i][j].setIcon(new ImageIcon(getClass().getResource("3_"+juego.jugadores.get(i).cartas.get(j).numero()+".JPG")));
                
                else if (juego.jugadores.get(i).cartas.get(j).palo().equals("Picas"))
                    cartas[i][j].setIcon(new ImageIcon(getClass().getResource("4_"+juego.jugadores.get(i).cartas.get(j).numero()+".JPG")));
                
                gbc.gridx=3+j;
                gbc.gridy=i+1;
                panelReparto.add(cartas[i][j],gbc);
            }//EndFor
        }//EndFor*/