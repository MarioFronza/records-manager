/*
 * To search this license header, choose License Headers in Project Properties.
 * To search this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.io.IOException;
import java.util.Scanner;
import model.Carro;

/**
 * Classe principal da aplicação, contendo o menu principal com as demais opções
 *
 * @author mario
 * @since 26/10/2018
 * @version 1.0
 */
public class Main {

    public static void main(String[] args) throws IOException {

        Persistencia persistencia = new Persistencia();
        Scanner sc = new Scanner(System.in);
        int input = 0;
        
//        Carro carro1 = new Carro("LWM4651", "Jipe Rocsta", 3, 54, "Branco", true, -1, -1);
//        Carro carro2 = new Carro("NCD8002", "Buggy Plus", 3, 54, "Vermelho", true, -1, -1);
//        Carro carro3 = new Carro("NEU2763", "fortwo coup", 3, 54, "azul", true, -1, -1);
//        Carro carro4 = new Carro("JHX2709", "Way", 3, 54, "Amarelo", true, -1, -1);
//        Carro carro5 = new Carro("EGV7407", "Fox Route", 3, 54, "Laranja", true, -1, -1);
//        Carro carro6 = new Carro("NER4417", "Rodeo", 3, 54, "preto", true, -1, -1);
//
//        persistencia.add(carro1);
//        persistencia.add(carro2);
//        persistencia.add(carro3);
//        persistencia.add(carro4);
//        persistencia.add(carro5);
//        persistencia.add(carro6);
        
        System.out.println("Bem-vindo");
        System.out.println("Trabalho Persistência de dados");
        System.out.println("Construção de um gerenciador de registros");
        System.out.println("=========================================");
        System.out.println("[1] Para adicionar um registro");
        System.out.println("[2] Para remover um registro");
        System.out.println("[3] Para alterar um registro");
        System.out.println("[4] Para consultar um registro");
        System.out.println("[5] Para exibir todos os registros");
        System.out.println("[6] Para sair do programa");

        while (input != 6) {
            System.out.print("Sua opção: ");
            input = sc.nextInt();
            if (input < 1 || input > 6) {
                System.out.println("Informe uma entrada válida");
            }
            switch (input) {
                case 1:
                    System.out.println("Informe os dados do carro: ");
                    System.out.print("chassi: ");
                    String carId = sc.next();
                    System.out.print("nome: ");
                    String carName = sc.next();
                    System.out.print("valor: ");
                    double carValue = sc.nextDouble();
                    System.out.print("quantidade: ");
                    int carQuantity = sc.nextInt();
                    System.out.print("cor: ");
                    String carColor = sc.next();

                    Carro carro = new Carro(carId, carName, carValue, carQuantity, carColor, true, -1, -1);
                    persistencia.add(carro);
                    break;
                case 2:
                    System.out.println("Informe o chassi: ");
                    System.out.print("chassi: ");
                    String removeId = sc.next();
                    persistencia.remove(removeId);
                    break;
                case 3:
                    System.out.println("Informe o chassi: ");
                    System.out.println("chassi: ");
                    String changeId = sc.next();
                    System.out.println("Informe os dados: ");
                    System.out.println("nome: ");
                    String changeName = sc.next();
                    System.out.print("valor: ");
                    double changeValue = sc.nextDouble();
                    System.out.print("quantidade: ");
                    int changeQuantity = sc.nextInt();
                    System.out.print("cor: ");
                    String changeColor = sc.next();

                    Carro changeCar = new Carro(changeId, changeName, changeValue, changeQuantity, changeColor, true, -1, -1);
                    persistencia.change(changeCar);
                    break;
                case 4:
                    System.out.println("Consultar por nome ou chassi?");
                    System.out.println("chassi: c");
                    System.out.println("nome: n");
                    System.out.print("Sua opção: ");
                    String inputSearch = sc.next();
                    if (inputSearch.equals("c")) {
                        System.out.println("Informe o chassi: ");
                        System.out.print("chassi: ");
                        String searchId = sc.next();
                        persistencia.searchByChassi(searchId);
                    } else if (inputSearch.equals("n")) {
                        System.out.println("Informe o nome: ");
                        System.out.print("nome: ");
                        String searchName = sc.next();
                        persistencia.searchByName(searchName);
                    }
                    break;
                case 5:
                    System.out.println("Exibir registros em árvore ou em tuplas?");
                    System.out.println("tuplas: t");
                    System.out.println("árvore: a");
                    System.out.print("Sua opção: ");
                    String decisao = sc.next();
                    if (decisao.equals("t")) {
                        System.out.println("");
                        persistencia.showTuple();
                        System.out.println("");
                    } else if (decisao.equals("a")) {
                        System.out.println("");
                        System.out.println(persistencia.show(0, 0));
                    } else {
                        System.out.println("entrada inválida");
                    }
                    break;
                case 6:
                    System.out.println("saindo");
                    break;
            }
        }
        sc.close();
    }
}
