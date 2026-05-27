public class HashAberto<T> {
    private static final int VAZIO = 0;
    private static final int REMOVIDO = 1;
    private static final int OCUPADO = 2;
    private static final double LOAD_FACTOR = 0.75;
    private HashEntry<T>[] tabela;
    private int tamanho;
    private int numElementos;
    private int comparacoes;

//------------------Classe interna para representar cada dado armazenado na tabela hash----------------------
    private static class Dado<T> {
        long key;
        T value;

        Dado(long key, T value) {
            this.key = key;
            this.value = value;
        }
    }
//------------------Classe interna para representar cada entrada da tabela hash----------------------
    private static class HashEntry<T> {
        Dado<T> dado;
        int estado;
        HashEntry() {
            this.estado = VAZIO;
        }
    }
//------------------Construtor------------------------------------ 
    public HashAberto(int tamanho) {
        this.tamanho = tamanho;
        tabela = new HashEntry[tamanho];
        for (int i = 0; i < tamanho; i++) {
            tabela[i] = new HashEntry<>();
        }
    }

    public HashAberto() {
        this.tamanho = 10; // Tamanho default
        tabela = new HashEntry[tamanho];
        for (int i = 0; i < tamanho; i++) {
            tabela[i] = new HashEntry<>();
        }
    }
//------------------------------------------------------
    private int funcaoHash(long chave, int i) {
        return (int) ((chave + i) % tamanho);
    }

//------------------------------------------------------
    public void put(long key, T value) {
        if ((double) numElementos / tamanho >= LOAD_FACTOR) {
            redimensiona();
        }
        
        Dado<T> dado = new Dado<>(key, value);
        int i = 0;
        int map;
        comparacoes = 0;
        do {
            map = funcaoHash(key, i);
            if (tabela[map].estado != OCUPADO) {
                tabela[map].dado = dado;
                tabela[map].estado = OCUPADO;
                numElementos++;
                return;
            }
            comparacoes++;
            i++;
        } while (i < tamanho);
    }// fim funcao put
//------------------------------------------------------
    public boolean containsKey(long key) {
        int i = 0;
        int map;
        do {
            map = funcaoHash(key, i);
            if (tabela[map].estado == VAZIO) {
                return false;
            }
            if (tabela[map].estado == OCUPADO && tabela[map].dado.key == key) {
                return true;
            }
            i++;
        } while (i < tamanho);
        return false;
    }

//------------------------------------------------------
    private int getIndex(long key) {
        int i = 0;
        int map;
        do {
            map = funcaoHash(key, i);
            if (tabela[map].estado == VAZIO) {
                return -1;
            }
            if (tabela[map].estado == OCUPADO && tabela[map].dado.key == key) {
                return map;
            }
            i++;
        } while (i < tamanho);
        return -1;
    }
//------------------------------------------------------
    public boolean deleteHash(long key) {
        // buscar com a função getIndex e marcar como removido
        return false;
    }

    public void printHash() {
        for (int i = 0; i < tamanho; i++) {
            if (tabela[i].estado == OCUPADO) {
                System.out.printf("[%d] - %d - %s |%n", i, tabela[i].dado.key, tabela[i].dado.value.toString());
            } else {
                System.out.printf("[%d] -___|%n", i);
            }
        }
    }

    private void redimensiona() {
        int novoTamanho = tamanho * 2;
        HashEntry<T>[] novaTabela = new HashEntry[novoTamanho];
        for (int i = 0; i < novoTamanho; i++) {
            novaTabela[i] = new HashEntry<>();
        }

        for (HashEntry<T> entry : tabela) {
            if (entry.estado == OCUPADO) {
                int i = 0;
                int map;
                do {
                    map = (int) ((entry.dado.key + i) % novoTamanho);
                    if (novaTabela[map].estado != OCUPADO) {
                        novaTabela[map].dado = entry.dado;
                        novaTabela[map].estado = OCUPADO;
                        break;
                    }
                    i++;
                } while (i < novoTamanho);
            }
        }

        tabela = novaTabela;
        tamanho = novoTamanho;
    }
    
}
