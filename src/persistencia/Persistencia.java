/*
 * To search this license header, choose License Headers in Project Properties.
 * To search this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Carro;

/**
 * Classe responsável por persistir os dados no arquivo
 *
 * @author Mário Fronza
 * @version 1.0
 * @since 03/11/2018
 */
public class Persistencia {

    //Posição do chassi no arquivo
    private final int positionId = 0;
    //Posição do nome no arquivo 
    private final int positionName = 60;
    //Posição do valor no arquivo
    private final int positionValue = 150;
    //Posição da quantidade no arquivo 
    private final int positionQuantity = 158;
    //Posição da cor no arquivo 
    private final int positionColor = 162;
    //Posição do estado no arquivo 
    private final int positionState = 232;
    //Posição do endereço a esquerda no arquivo 
    private final int positionleft = 233;
    //Posição do endereço a direita no arquivo 
    private final int positionRight = 241;
    //Tamanho da tupla 
    private final int tupleSize = 249;

    //Nome do arquivo  
    String fileName;

    //Construtor da classe
    public Persistencia() {
        //Nomeia o arquivo 
        fileName = "persitencia.dat";
        //objeto para manipulacao fisica do arquivo
        File arq = new File(fileName);
        // apaga arquivo
        arq.delete();
    }

    /**
     * Este método faz a validação do carro a ser gravado, primeiramente ele
     * veririca se o arquivo está vazio, se sim ele grava na primeira posição,
     * se não, ele verifica se o arquivo é maior, menor ou igual a o objeto da
     * posição atual. Se for maior ou menor, ele vericia se a posição da
     * esquerda ou direita é -1, se sim ele pode gravar o arquivo na última
     * posição, e armazenar a referencia no objeto pai. Caso não seja -1, ele
     * armazena a referencia da esquerda ou direita na posição atual, e continua
     * vericiando até que exista uma posição de referência igual a -1.
     *
     * @param c carro a ser gravado no arquivo
     * @throws IOException
     */
    public void add(Carro c) throws IOException {
        String carId = c.getChassi().trim();
        //Campura o chassi do carro a ser gravado, e convertido para inteiro
        long newPosition = size(); //Posição que será gravado o arquivo
        long position = 0; //Posição atual do arquivo a ser comparado
        long correntLine = 0; //Tupla atual a ser comparada 

        if (isEmpty()) { //Verifica se o arquivo está vazio 
            writeLine(c, 0); //grava o arquivo na posição 0
            System.out.println("Arquivo adicionado com sucesso");
        } else {
            boolean notAdded = true; //variável para verificar se o objeto já foi gravado
            boolean isLeft = false; //variável para verificar se existe arquivo a esquerda

            while (notAdded) { //Loop até que o arquivo seja adicionado
                String correntId = readId(correntLine);
                if (carId.compareTo(correntId) == 0) {//Se for igual, maior ou menor 
                    if (readState(correntLine) == false) {
                        changeLine(c, correntLine);
                        System.out.println("Arquivo adicionado com sucesso");
                    } else {
                        System.out.println("Chassi já utilizado no arquivo");
                    }
                    break;
                } else if (carId.compareTo(correntId) < 0) {
                    position = readLeft(correntLine); //grava a referencia a esquerda da posição atual
                    isLeft = true;
                } else if (carId.compareTo(correntId) > 0) {
                    position = readRight(correntLine); //grava a referência a direita da posição atual 
                    isLeft = false;
                }

                if (position == -1) {
                    writeLine(c, newPosition); //Grava o arquivo na ultima posição
                    if (isLeft) {//Armazena a referencia do objeto no arquivo
                        writeLeft(correntLine, newPosition); //Grava a referência na esquerda
                        System.out.println("Arquivo adicionado com sucesso");
                    } else {
                        writeRigth(correntLine, newPosition); //Grava a referência na esquerda
                        System.out.println("Arquivo adicionado com sucesso");
                    }
                    notAdded = false;
                } else {
                    correntLine = position; //tupla atual recebe a posição caso não seja -1
                }
            }
        }
    }

    /**
     * Método responsável por retornar o chassi do carro, com base em uma posião
     * que é informada
     *
     * @param position posição do arquivo a ser lido o chassi
     * @return Retorna uma string do Id da posião da tupla
     * @throws IOException
     */
    public String readId(long position) throws IOException {
        RandomAccessFile arquivo;
        String id = "";
        try {
            arquivo = new RandomAccessFile(fileName, "r");
            arquivo.seek(position);
            for (int j = 0; j < 30; j++) {
                id += arquivo.readChar();
            }

            arquivo.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Persistencia.class.getName()).log(Level.SEVERE, null, ex);
        }

        return id.trim();
    }

    /**
     * Método responsável por retornar o a referência a esquerda do carro, com
     * base em uma posião que é informada
     *
     * @param position posição do arquivo
     * @return Retorna uma string do Id da posião da tupla
     * @throws IOException
     */
    public long readLeft(long position) throws IOException {
        RandomAccessFile arquivo;
        long left = 0;
        try {
            arquivo = new RandomAccessFile(fileName, "r");
            arquivo.seek(position + positionleft);
            left = arquivo.readLong();
            arquivo.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Persistencia.class.getName()).log(Level.SEVERE, null, ex);
        }

        return left;
    }

    /**
     * Método responsável por retornar o a referência a direita do carro, com
     * base em uma posião que é informada
     *
     * @param position posição do arquivo
     * @return Retorna uma string do Id da posião da tupla
     * @throws IOException
     */
    public long readRight(long position) throws IOException {
        RandomAccessFile arquivo;
        long right = 0;
        try {
            arquivo = new RandomAccessFile(fileName, "r");
            arquivo.seek(position + positionRight);
            right = arquivo.readLong();
            arquivo.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Persistencia.class.getName()).log(Level.SEVERE, null, ex);
        }

        return right;
    }

    public boolean readState(long position) {
        RandomAccessFile arquivo;
        boolean state = true;
        try {
            arquivo = new RandomAccessFile(fileName, "r");
            arquivo.seek(position + positionState);
            state = arquivo.readBoolean();

            arquivo.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Persistencia.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Persistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
        return state;
    }

    public String readName(long position) {
        RandomAccessFile arquivo;
        String name = "";
        try {
            arquivo = new RandomAccessFile(fileName, "r");
            arquivo.seek(position + positionName);
            for (int j = 0; j < 45; j++) {
                name += arquivo.readChar();
            }

            arquivo.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Persistencia.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Persistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
        return name.trim();
    }

    /**
     * Método responsável por escrever o a referência a esquerda do carro, com
     * base em uma posião que é informada
     *
     * @param position posição do arquivo
     * @throws IOException
     */
    public void writeLeft(long position, long left) throws IOException {
        RandomAccessFile arquivo;
        try {
            arquivo = new RandomAccessFile(fileName, "rw");
            arquivo.seek(position + positionleft);
            arquivo.writeLong(left);
            arquivo.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Persistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Método responsável por escrever o a referência a direita do carro, com
     * base em uma posião que é informada
     *
     * @param position posição do arquivo
     * @throws IOException
     */
    public void writeRigth(long position, long right) throws IOException {
        RandomAccessFile arquivo;
        try {
            arquivo = new RandomAccessFile(fileName, "rw");
            arquivo.seek(position + positionRight);
            arquivo.writeLong(right);
            arquivo.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Persistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void writeState(long position) {
        RandomAccessFile arquivo;
        try {
            arquivo = new RandomAccessFile(fileName, "rw");
            arquivo.seek(position + positionState);
            arquivo.writeBoolean(false);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Persistencia.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Persistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Método responsável por escrever uma tupla no arquivo
     *
     * @param c carro a ser gravado e posição
     * @param position
     */
    public void writeLine(Carro c, long position) {
        RandomAccessFile arquivo;
        try {
            //Abre arquivo para leitura e gravacao
            arquivo = new RandomAccessFile(fileName, "rw");
            arquivo.seek(position);
            arquivo.writeChars(String.format("%1$30s", c.getChassi()));
            arquivo.writeChars(String.format("%1$45s", c.getNome()));
            arquivo.writeDouble(c.getValor());
            arquivo.writeInt(c.getQuantidade());
            arquivo.writeChars(String.format("%1$35s", c.getCor()));
            arquivo.writeBoolean(true);
            arquivo.writeLong(-1);
            arquivo.writeLong(-1);

            arquivo.close();
        } catch (IOException e) {
            System.exit(1);
        }
    }

    public void changeLine(Carro c, long position) {
        RandomAccessFile arquivo;
        try {
            arquivo = new RandomAccessFile(fileName, "rw");
            arquivo.seek(position);
            arquivo.writeChars(String.format("%1$30s", c.getChassi()));
            arquivo.writeChars(String.format("%1$45s", c.getNome()));
            arquivo.writeDouble(c.getValor());
            arquivo.writeInt(c.getQuantidade());
            arquivo.writeChars(String.format("%1$35s", c.getCor()));
            arquivo.writeBoolean(true);

            arquivo.close();
        } catch (IOException e) {
            System.exit(1);
        }
    }

    public String readLine(long position) {
        RandomAccessFile arquivo;
        String line = "";
        try {
            arquivo = new RandomAccessFile(fileName, "r");
            long tamArq = arquivo.length();
            String chassi = "";
            String nome = "";
            double valor;
            int quantidade;
            String cor = "";
            boolean estado;
            long left;
            long right;
            arquivo.seek(position);

            for (int j = 0; j < 30; j++) {
                chassi += arquivo.readChar();
            }

            for (int j = 0; j < 45; j++) {
                nome += arquivo.readChar();
            }

            valor = arquivo.readDouble();
            quantidade = arquivo.readInt();

            for (int j = 0; j < 35; j++) {
                cor += arquivo.readChar();
            }

            estado = arquivo.readBoolean();
            left = arquivo.readLong();
            right = arquivo.readLong();

            line = chassi.trim() + ", " + nome.trim() + ", " + Double.toString(valor) + ", " + Integer.toString(quantidade) + ", " + cor.trim() + ", " + Boolean.toString(estado) + ", " + Long.toString(left) + ", " + Long.toString(right);

            arquivo.close();
        } catch (IOException e) {
            System.exit(1);
        }
        return line;
    }

    public void remove(String chassi) throws IOException {
        long removeLine = searchByChassi(chassi);
        if (removeLine != -1) {
            if (readState(removeLine) == false) {
                System.out.println("Arquivo já foi excluído");
            } else {
                writeState(removeLine);
                System.out.println("Arquivo excluído com sucesso");
            }
        }
    }

    public long searchByChassi(String chassi) throws IOException {
        String carId = chassi;
        //Posição atual do arquivo a ser comparado
        long position = 0;
        //Tupla atual a ser comparada 
        long correntLine = 0;
        boolean naoEncontrado = true;

        if (isEmpty()) {
            System.out.println("Arquivo vazio");
        } else {
            while (naoEncontrado) {
                String correntId = readId(correntLine);

                if (carId.compareTo(correntId) == 0) {
                    if (readState(position) == false) {
                        position = -1;
                    }
                    naoEncontrado = false;
                } else if (carId.compareTo(correntId) < 0) {
                    position = readLeft(correntLine);
                    correntLine = position;
                } else if (carId.compareTo(correntId) > 0) {
                    position = readRight(correntLine);
                    correntLine = position;
                }

                if (position == -1) {
                    System.out.println("Registro não existe");
                    return -1;
                }
            }
            System.out.println(readLine(correntLine));
        }
        return correntLine;
    }

    public void searchByName(String name) throws IOException {
        String carName = name;
        //Posição atual do arquivo a ser comparado
        long position = 0;
        boolean naoEncontrado = true;

        if (isEmpty()) {
            System.out.println("Arquivo vazio");
        } else {
            while (naoEncontrado) {
                if (position == size()) {
                    System.out.println("Registro não existe");
                    break;
                } else {
                    String correntCarName = readName(position);
                    if (carName.equals(correntCarName)) {
                        if (readState(position) == false) {
                            System.out.println("Registro não existe");
                            break;
                        } else {
                            System.out.println(readLine(position));
                            naoEncontrado = false;
                            break;
                        }
                    } else {
                        position = position + tupleSize;
                    }
                }
            }
        }
    }

    public void change(Carro c) throws IOException {
        long position = searchByChassi(c.getChassi());
        if (position != -1) {
            changeLine(c, position);
            System.out.println("Arquivo alterado com sucesso");
        }
    }

    public String show(long position, long k) throws IOException {
        String tree = "";

        if (position != -1) {
            if (isEmpty()) {
                System.out.println("Arquivo vazio");
            } else {
                if (size() == tupleSize) {
                    tree += readLine(position);
                    return tree;
                }

                for (int i = 0; i < k; i++) {
                    tree += "|   ";
                }

                k++;

                tree += "-> " + readLine(position) + "\n";

                tree += show(readRight(position), k);
                tree += show(readLeft(position), k);
            }
        } else {
            for (int i = 0; i < k; i++) {
                tree += "|   ";
            }
            k++;
            tree += "null\n";
        }

        return tree;
    }

    public void showTuple() {
        RandomAccessFile arquivo;
        try {
            arquivo = new RandomAccessFile(fileName, "r");
            long tamArq = arquivo.length();//recupera o tamanho do arquivo
            for (int i = 0; i < arquivo.length() / 249; i++) {
                String chassi = ""; // variavel auxiliar para contruir o nome
                String nome = ""; // variavel auxiliar para contruir o nome
                double valor = 0; // variavel auxiliar para contruir o nome
                int quantidade = 0; // variavel auxiliar para contruir o nome
                String cor = ""; // variavel auxiliar para contruir o nome
                boolean estado = true; // variavel auxiliar para contruir o nome
                long left = 0; // variavel auxiliar para contruir o nome
                long right = 0; // variavel auxiliar para contruir o nome
                arquivo.seek(i * tupleSize);

                for (int j = 0; j < 30; j++) {
                    chassi += arquivo.readChar(); //le 2 bytes do arquivo e converte para char
                }
                for (int j = 0; j < 45; j++) {
                    nome += arquivo.readChar();
                }

                valor = arquivo.readDouble();
                quantidade = arquivo.readInt();

                for (int j = 0; j < 35; j++) {
                    cor += arquivo.readChar();
                }

                estado = arquivo.readBoolean();
                left = arquivo.readLong();
                right = arquivo.readLong();

                System.out.println(chassi.replace(" ", "") + ", " + nome.replace(" ", "") + ", " + valor + ", " + quantidade + ", " + cor.replace(" ", "") + ", " + estado + ", " + left + ", " + right); // imprime tupla
            }
            arquivo.close();
        } catch (IOException e) {
            System.exit(1);
        }
    }

    /**
     * Método responsável por retornar o tamanho do arquivo
     *
     * @return tamanho do arquivo
     * @throws IOException
     */
    public long size() throws IOException {
        RandomAccessFile arquivo = new RandomAccessFile(fileName, "rw");
        return arquivo.length();
    }

    /**
     * Método que retorna true se o arquivo está vazio
     *
     * @return true se o arquivo é vazio
     * @throws IOException
     */
    public boolean isEmpty() throws IOException {
        RandomAccessFile arquivo = new RandomAccessFile(fileName, "rw");
        return arquivo.length() == 0;
    }

}
