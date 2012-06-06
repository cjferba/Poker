package poker;
import java.io.*;
import java.util.*;
/**
 *
 * @author Carlosbasso
 */
public class JuegoPoker extends Juego{
     protected int rondas;
    
    /**
     *  Práctica Póker PDO
     * @brief Constructor por defecto
     * @author Carlos Jesus Fernandez Basso
     */
    JuegoPoker() {};
    
    /**
     *  Práctica Póker PDO
     * @brief Constructor con argumentos
     * @param numJug Numero inicial de jugadores.
     * @author Carlos Jesus Fernandez Basso
     */
    JuegoPoker(int r,int ganancias,LinkedList<String> jugador)  {
        this.inicializa(r,ganancias,jugador);
    };
    
    protected void inicializa (int r,int ganancias,LinkedList<String> jugador)  {
        rondas=r;
         int e=jugador.size();
        //Lectura de los nombres de los jugadores.
        for (int i=0; i<e; i++) {
            
            jugadores.add(new JugadorPoker(ganancias,jugador.get(i)));
        }
    };
    
    public void repartir(){
         baraja.barajar();
            for (int i=0; i<jugadores.size(); i++)
                for (int j=0; j<5; j++)
                    jugadores.get(i).robar(baraja.robar());
    }
   
      
    public String partida() {
        int indice;
        String SAL=new String();
        SAL="";
        //Rondas
       
        
        for (int p=0; p<rondas && jugadores.size()>1; p++) {
           repartir();
            
            SAL=SAL+this.info();
            SAL=SAL+"\n> Primeras apuestas. <\n"; 
            
            for (int i=0; i<jugadores.size(); i++) {
                //Si alguien ya ha apostado y la mano no es buena => seRetira()
                if (!plantados.isEmpty() && jugadores.get(i).seRetira()) {
                    perdedores.add(jugadores.get(i));
                    SAL=SAL+jugadores.get(i).nombre()+" se retira."+"\n";
                }//EndIf
                //Si no ha apostado nadie o la mano es buena => apostar()
                else {
                    SAL=SAL+jugadores.get(i).nombre()+" apuesta."+"\n";
                    apuestas+=jugadores.get(i).apostar();
                    plantados.add(jugadores.get(i));
                }//EndElse
            }//EndFor
            
            if (!plantados.isEmpty()) {
                String manos[]={"",""};
                JugadorPoker ganador = plantados.getFirst();
                
                if (plantados.size()>1) {
                    SAL=SAL+"======== DESCARTE ========"+"\n";

                    LinkedList<Carta> descartes = new LinkedList<Carta>();
                    boolean flag=false;

                    for (int i=0; i<plantados.size(); i++) {

                        if (plantados.get(i).perdedor())
                            flag=true;

                        //Descarte
                        if ((indice=jugadores.indexOf(plantados.get(i)))<0) {
                            System.exit(1);
                        }

                        descartes.addAll(jugadores.get(indice).descartar());

                        if (descartes.isEmpty())
                            SAL=SAL+jugadores.get(indice).nombre()+" no se descarta."+"\n";
                        else
                            SAL=SAL+jugadores.get(indice).nombre()+" se descarta de "+descartes.size()+" cartas."+"\n";

                        if (!descartes.isEmpty()) {
                            for (int j=0; j<descartes.size(); j++) 
                                //Reparto de cartas nuevas.
                                jugadores.get(indice).robar(baraja.robar());

                            plantados.remove(i);
                            plantados.add(i,jugadores.get(indice));
                        }

                        //Devolvemos el descarte a la baraja.
                        baraja.cartas.addAll(0,descartes);
                        while (!descartes.isEmpty()) 
                            descartes.remove();
                    }//EndFor
                    
                    if (!flag) {
                        SAL=SAL+"====> Segundas apuestas. <===="+"\n";
                        for (int i=0; i<plantados.size(); i++) {
                            if ((indice=jugadores.indexOf(plantados.get(i)))<0) {
                                System.exit(1);
                            }
                            //Si la mano no es buena => seRetira()
                            if (plantados.get(i).seRetira()) {
                                perdedores.add(jugadores.get(indice));
                                plantados.remove(jugadores.get(indice));
                                if ((indice=perdedores.indexOf(jugadores.get(indice)))<0) {
                                    System.exit(1);
                                }
                                SAL=SAL+perdedores.get(indice).nombre()+" se retira."+"\n";
                            }//EndIf
                            else {//Si la mano es buena => apostar()
                                SAL=SAL+plantados.get(i).nombre() +" apuesta."+"\n";
                                apuestas+=plantados.get(i).apostar();//Apuesta
                            }//EndElse
                        }//EndFor
                    }
                    else
                        SAL=SAL+"Un jugador no puede apostar más. Se resuelve la partida."+"\n";

                    if (plantados.size()==1)
                        ganador = plantados.getFirst();
                    else {
                        int pos=0;
                        for (int i=0; i<plantados.size(); i++) {
                            manos[0]=plantados.get(i).mano();

                            for (int j=i+1; j<plantados.size(); j++) {
                                manos[1]=plantados.get(j).mano();

                                //Si tienen la misma mano, comparamos los valores de cada mano
                                if (manos[0].equals(manos[1])) {
                                    if (manos[0].equals("Escalera de Color")) {
                                        if (cartaAltaEsc(plantados.get(i).carta,plantados.get(j).carta))  pos=i;
                                        else    pos=j;
                                    }
                                    else if (manos[0].equals("Poker")) {
                                        if (poker(plantados.get(i).carta,plantados.get(j).carta)) pos=i;
                                        else    pos=j;
                                    }
                                    else if (manos[0].equals("Full")) {
                                        if (trio(plantados.get(i).carta,plantados.get(j).carta))  pos=i;
                                        else    pos=j;
                                    }
                                    else if (manos[0].equals("Color")) {
                                        if (cartaAlta(plantados.get(i).carta,plantados.get(j).carta))  pos=i;
                                        else    pos=j;
                                    }
                                    else if (manos[0].equals("Escalera")) {
                                        if (cartaAltaEsc(plantados.get(i).carta,plantados.get(j).carta))  pos=i;
                                        else    pos=j;
                                    }
                                    else if (manos[0].equals("Trío")) {
                                        if (trio(plantados.get(i).carta,plantados.get(j).carta))  pos=i;
                                        else    pos=j;
                                    }
                                    else if (manos[0].equals("Dobles Parejas")) {
                                        if (par(plantados.get(i).carta,plantados.get(j).carta))  pos=i;
                                        else    pos=j;
                                    }
                                    else if (manos[0].equals("Pareja")) {
                                        if (par(plantados.get(i).carta,plantados.get(j).carta))  pos=i;
                                        else    pos=j;
                                    }
                                    else if (manos[0].equals("Carta Alta")) {
                                        if (cartaAlta(plantados.get(i).carta,plantados.get(j).carta))  pos=i;
                                        else    pos=j;
                                    }
                                    //Una vez obtenemos quién gana, lo guardamos
                                    if ((indice=jugadores.indexOf(plantados.get(pos)))<0) {
                                        System.err.println("ERROR: No existe ese jugador. (Check)");
                                        System.exit(1);
                                    }
                                    ganador = jugadores.get(indice);
                                }//EndIf (Si tienen la misma mano)
                                else{//Si las manos son distintas
                                    if (manos[0].equals("Escalera de Color"))
                                        pos=i;
                                    else if (manos[1].equals("Escalera de Color"))
                                        pos=j;
                                    else if (manos[0].equals("Poker") && (manos[1].equals("Full") || manos[1].equals("Color")
                                                || manos[1].equals("Escalera") || manos[1].equals("Trío") || manos[1].equals("Dobles Parejas")
                                                || manos[1].equals("Pareja") || manos[1].equals("Carta Alta")))
                                        pos=i;
                                    else if (manos[1].equals("Poker") && (manos[0].equals("Full") || manos[0].equals("Color")
                                                || manos[0].equals("Escalera") || manos[0].equals("Trío") || manos[0].equals("Dobles Parejas")
                                                || manos[0].equals("Pareja") || manos[0].equals("Carta Alta")))
                                        pos=j;
                                    else if (manos[0].equals("Full") && (manos[1].equals("Color") || manos[1].equals("Escalera")
                                                || manos[1].equals("Trío") || manos[1].equals("Dobles Parejas")
                                                || manos[1].equals("Pareja") || manos[1].equals("Carta Alta")))
                                        pos=i;
                                    else if (manos[1].equals("Full") && (manos[0].equals("Color") || manos[0].equals("Escalera")
                                                || manos[0].equals("Trío") || manos[0].equals("Dobles Parejas")
                                                || manos[0].equals("Pareja") || manos[0].equals("Carta Alta")))
                                        pos=j;
                                    else if (manos[0].equals("Color") && (manos[1].equals("Escalera")
                                                || manos[1].equals("Trío") || manos[1].equals("Dobles Parejas")
                                                || manos[1].equals("Pareja") || manos[1].equals("Carta Alta")))
                                        pos=i;
                                    else if (manos[1].equals("Color") && (manos[0].equals("Escalera")
                                                || manos[0].equals("Trío") || manos[0].equals("Dobles Parejas")
                                                || manos[0].equals("Pareja") || manos[0].equals("Carta Alta")))
                                        pos=j;
                                    else if (manos[0].equals("Escalera") && (manos[1].equals("Trío") || manos[1].equals("Dobles Parejas")
                                                || manos[1].equals("Pareja") || manos[1].equals("Carta Alta")))
                                        pos=i;
                                    else if (manos[1].equals("Escalera") && (manos[0].equals("Trío") || manos[0].equals("Dobles Parejas")
                                                || manos[0].equals("Pareja") || manos[0].equals("Carta Alta")))
                                        pos=j;
                                    else if (manos[0].equals("Trío") && (manos[1].equals("Dobles Parejas")
                                                || manos[1].equals("Pareja") || manos[1].equals("Carta Alta")))
                                        pos=i;
                                    else if (manos[1].equals("Trío") && (manos[0].equals("Dobles Parejas")
                                                || manos[0].equals("Pareja") || manos[0].equals("Carta Alta")))
                                        pos=j;
                                    else if (manos[0].equals("Dobles Parejas") && (manos[1].equals("Pareja")
                                                || manos[1].equals("Carta Alta")))
                                        pos=i;
                                    else if (manos[1].equals("Dobles Parejas") && (manos[0].equals("Pareja")
                                                || manos[0].equals("Carta Alta")))
                                        pos=j;
                                    else if (manos[0].equals("Pareja") && manos[1].equals("Carta Alta"))
                                        pos=i;
                                    else if (manos[1].equals("Pareja") && manos[0].equals("Carta Alta"))
                                        pos=j;

                                    if ((indice=jugadores.indexOf(plantados.get(pos)))<0) {
                                        System.err.println("ERROR: No existe ese jugador. (Check2)");
                                        System.exit(1);
                                    }
                                    ganador = jugadores.get(indice);
                                }//EndElse (Si las manos son distintas)
                            }
                        }
                    }
                }
                
                //Resultado de la Ronda
                jugadores.get(jugadores.indexOf(ganador)).ganancias(apuestas);
            SAL=SAL+this.info(ganador);
                
                apuestas=0;//Reinicio de apuestas
            }//EndIf hay plantados
            else {
                SAL=SAL+"Todos los jugadores se han retirado. Gana la banca."+"Bote = "+apuestas+"\n";
               }
            
            //Actualizamos las listas de jugadores, perdedores y plantados
            for (int i=0; i<jugadores.size(); i++) { 
                if (jugadores.get(i).perdedor()) {
                    SAL=SAL+jugadores.get(i).nombre()+" ha sido eliminado.\n";
                    jugadores.remove(i);
                }
            }
            while (!plantados.isEmpty())
                plantados.remove();
            while (!perdedores.isEmpty())
                perdedores.remove();
            
            //Quitamos las cartas de los jugadores y las devolvemos a la baraja.
            for (int i=0; i<jugadores.size(); i++)
                baraja.cartas.addAll(0,jugadores.get(i).vaciarCartas());
        }//EndFor Rondas
        
       return SAL;
    };
    protected String info() {
        String salida=new String("");
        String aux;
        salida=salida+"Información"+"\n\n";
        for (int i=0; i<jugadores.size(); i++) {
            aux = jugadores.get(i).nombre();
            salida=salida+"Jugador: "+aux+".\n\tGanancias: "+jugadores.get(i).ganancias()+"\n";
            salida=salida+"\t"+"Cartas:";
            salida=salida+"\n"+baraja.muestraCartas((jugadores.get(i).carta));
            salida=salida+"\t"+"La mano que tiene es: "+jugadores.get(i).mano()+"\n";
        }
        salida=salida+"BARAJA = "+baraja.cartas.size();
        salida=salida+"\t"+"Bote = "+apuestas;
        
        //salida=salida+this.info();
        return salida;
    };

    /**
     *  Práctica Póker PDO
     * @brief Muestra por la salida estándar la información final de la partida.
     * @param gan Ganador de la partida.
     * @author Carlos Jesus Fernandez Basso
     */
    protected String info(JugadorPoker gan) {
        int indice=0;
        String aux,sal;
        sal=new String("");
        sal=sal+"Información final de la ronda\n";
        //Perdedores
        if (!perdedores.isEmpty()) {
        sal=sal+"\t"+"Retirados: "+"\n";
            for (int i=0; i<perdedores.size(); i++) {
             
                if ((indice=jugadores.indexOf(perdedores.get(i)))<0) {
                    System.err.println("ERROR: No existe ese jugador. (Check)");
                    System.exit(1);
                }
                aux = jugadores.get(indice).nombre();
                sal=sal+"Jugador: "+aux+"."+"\n"+"\tGanancias: "+jugadores.get(indice).ganancias()+"\n\t"+"Cartas:\n";
                sal=sal + "\n"+baraja.muestraCartas((jugadores.get(indice).carta));
                sal=sal+"\tLa mano que tiene es: "+jugadores.get(indice).mano()+"\n"+"Jugadores plantados: "+"\n";
            }
        }
        for (int i=0; i<plantados.size(); i++) {
            
            if ((indice=jugadores.indexOf(plantados.get(i)))<0) {
                System.err.println("ERROR: No existe ese jugador. (Check)");
                System.exit(1);
            }
            aux = jugadores.get(indice).nombre();
            sal=sal+"Jugador: "+aux+".\tGanancias: "+jugadores.get(indice).ganancias()+"\n"+"\tCartas:";
            sal=sal+"\n"+baraja.muestraCartas((jugadores.get(indice).carta));
            sal=sal+"\tLa mano que tiene es: "+jugadores.get(indice).mano()+"\n";
        }
        if ((indice=jugadores.indexOf(gan))<0) {
                System.err.println("ERROR: No existe ese jugador. (Check)");
                System.exit(1);
        }
        sal=sal+"Ganador = "+jugadores.get(indice).nombre()+"\n"+"BARAJA = "+baraja.cartas.size()+"\n";
        sal=sal+"Bote = "+apuestas+"\n";
        return sal;
    };

  
    protected boolean par(LinkedList<Carta> m1, LinkedList<Carta> m2) {
        boolean salida=true;
        
        if (!(m1.isEmpty() && m2.isEmpty())) {
            int valor1=0, valor2=0, cont1=0, cont2=0;
            boolean sal[]={true,true};
            
            for (int i=0; i<(m1.size()-1) && (sal[0] || sal[1]); i++) {
                //Obtenemos el valor de la pareja más alta de m1
                if (m1.get(i).valor()==m1.get(i+1).valor())
                    cont1++;
                else if (cont1==1) {
                    if (m1.get(i).valor()>valor1)
                        if (m1.get(i).valor()==1)
                            valor1=14;
                        else
                            valor1=m1.get(i).valor();
                    cont1=0;
                }
                else
                    cont1=0;
                //Obtenemos el valor de la pareja más alta de m2
                if (m2.get(i).valor()==m2.get(i+1).valor())
                    cont2++;
                else if (cont2==1) {
                    if (m2.get(i).valor()>valor2)
                        if (m2.get(i).valor()==1)
                            valor2=14;
                        else
                            valor2=m2.get(i).valor();
                    cont2=0;
                }
                else
                    cont2=0;
            }
            
            //Resultado
            if (valor1>valor2)  salida=true;
            
            else if (valor1<valor2) salida=false;

        }
        
        return salida;
    };
    
    /**
     *  Práctica Póker PDO
     * @brief Compara 2 manos que contienen una trío y responde cuál es ganadora.
     * m1 y m2 deben estar ordenados.
     * @param m1 Lista 1 de cartas a comparar.
     * @param m2 Lista 2 de cartas a comparar
     * @return boolean True/False Gana m1/Gana m2.
     * @author Carlos Jesus Fernandez Basso
     */
    protected boolean trio(LinkedList<Carta> m1, LinkedList<Carta> m2) {
        boolean salida=true;
        
        if (!(m1.isEmpty() && m2.isEmpty())) {
            int valor1=0, valor2=0, cont1=0, cont2=0;
            boolean sal[]={true,true};
            
            for (int i=0; i<(m1.size()-1) && (sal[0] || sal[1]); i++) {
                //Obtenemos el valor del trío de m1
                if (m1.get(i).valor()==m1.get(i+1).valor())
                    cont1++;
                else if (cont1==2)
                    valor1=m1.get(i).valor();
                else
                    cont1=0;
                //Obtenemos el valor del trío de m2
                if (m2.get(i).valor()==m2.get(i+1).valor())
                    cont2++;
                else if (cont2==2)
                    valor2=m2.get(i).valor();
                else
                    cont2=0;
            }
            
            //Resultado
            if (valor1>valor2)  salida=true;
            
            else if (valor1<valor2) salida=false;

        }
        
        return salida;
    };
    
    /**
     * @brief Compara 2 manos que contienen poker y responde la ganadora.
     * @return boolean True/False Gana m1/Gana m2.
     * @author Carlos Jesus Fernandez Basso
     */
    protected boolean poker(LinkedList<Carta> m1, LinkedList<Carta> m2) {
        boolean salida=true;
        
        if (!(m1.isEmpty() && m2.isEmpty())) {
            int valor1=0, valor2=0;
            boolean sal[]={true,true};
            
            for (int i=0; i<(m1.size()-1) && (sal[0] || sal[1]); i++) {
                
                if (m1.get(i).valor()==m1.get(i+1).valor()) {
                    valor1=m1.get(i).valor();
                    sal[0]=false;
                }
                if (m2.get(i).valor()==m2.get(i+1).valor()) {
                    valor1=m2.get(i).valor();
                    sal[1]=false;
                }
            }
            
            //Resultado
            if (valor1>valor2) salida=true;
            
            else if (valor1<valor2) salida=false;
        }
        
        return salida;
    };

    /**
     * @brief Compara 2 manos que contienen Carta Alta y la ganadora.
     * @return boolean True/False Gana m1/Gana m2.
     * @author Carlos JEsus FErnandez Basso
     */
    protected boolean cartaAlta(LinkedList<Carta> m1, LinkedList<Carta> m2) {
        boolean salida=true;
        
        if (!(m1.isEmpty() && m2.isEmpty())) {
            int valor1=m1.getFirst().valor(), valor2=m2.getFirst().valor();
            boolean sal=true;
            
            for (int i=0; i<m1.size() && sal; i++) {
                if (m1.get(i).valor()==1) {
                    valor1=14;
                    sal=false;
                }
                else if (m1.get(i).valor()>valor1)
                    valor1 = m1.get(i).valor();
            }
            
            sal=true;
            for (int i=0; i<m2.size() && sal; i++) {
                if (m2.get(i).valor()==1) {
                    valor1=14;
                    sal=false;
                }
                else if (m2.get(i).valor()>valor1)
                    valor1 = m2.get(i).valor();
            }
            
            //Resultado
            if (valor1>=valor2) salida=true;
            
            else    salida=false;

        }
        
        return salida;
    };
    
    /**
     * @return boolean True/False Gana m1/Gana m2.
     * @author Carlos Jesus Fernandez Basso
     */
    protected boolean cartaAltaEsc(LinkedList<Carta> m1, LinkedList<Carta> m2) {
        if (!(m1.isEmpty() && m2.isEmpty())) {
            int valor1=m1.getFirst().valor(), valor2=m2.getFirst().valor();
            
            for (int i=0; i<m1.size(); i++)
                if (m1.get(i).valor()>valor1)
                    valor1 = m1.get(i).valor();
            
            for (int j=0; j<m2.size(); j++)
                if (m2.get(j).valor()>valor2)
                    valor2 = m2.get(j).valor();
            
            if (valor1>=valor2) return true;
            else return false;

        }
        else return false;
    };

}
