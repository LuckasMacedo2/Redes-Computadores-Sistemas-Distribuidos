# Aluno: Lucas Macedo da Silva
# Disciplina: Redes de Computadores II
# Professor: Rafael Leal

# --- Servidor de arquivos UDP e TCP com multiplas conexoes ---

import socket
from time import sleep
import os
import funcoes
import threading

# --- Inicia o socket ---
HOST = ''               # Aceita mensagem de todo mundo, 127.0.0.1 loopback
PORTA = 7700            # Porta que sera ouvida
endereco = (HOST, PORTA)

global DIRETORIO        # Arquivos a serem listados
DIRETORIO = ''

# --- Envia a lista de arquivos ----
def listar_arquivos(diretorio):
    """
    [UDP e TCP]

    Retorna a lista dos arquivos do servidor
    """
    lista_arquivos = os.listdir(diretorio)
    
    s = '--- ARQUIVOS ---\n'

    for (i, nome_arquivo) in enumerate(lista_arquivos):
        s = s +'[{}] -> {}\n'.format(str(i + 1), nome_arquivo)

    s = s + '----------------\n'

    return s
# --- Envia a lista de arquivos ----

# --- Cliente conectado com protocolo TCP ---
def cliente_conectado_TCP(conexao, ip, DIRETORIO):
    """
    [TCP]
    O cliente se conectou com o protocolo TCP

    Permite o cliente:
    -> Listar os arquivos
    -> Receber os arquivos 
    -> Fazer upload de arquivos
    """

    opcao = conexao.recv(1024).decode()
    print('[TCP] Escolhida a opcao: {}'.format(opcao))
    try:
        if opcao == '1':                            # Listar arquivo
            lista = listar_arquivos(DIRETORIO)
            conexao.sendall(lista.encode('utf-8'))
            print('[TCP] Lista enviada')
        elif opcao == '2':                          # Envia o arquivo para o cliente
            # Recebe o arquivo do cliente
            # 1 -> Recebe o nome do arquivo
            # 2 -> Recebe o arquivo em si
            nome_arquivo = conexao.recv(1024)       # Recebe o nome do arquivo
            # Recebe o arquivo
            funcoes.receber_arquivo(DIRETORIO + nome_arquivo.decode(), conexao)
            print ('[TCP] Recebido arquivo {} do cliente de ip: {}'.format(nome_arquivo.decode(), ip))
        elif opcao == '3':
            # --- Valida arquivo ---
            ok = False 
            while not ok:
                nome_arquivo = conexao.recv(1024)
                print('[TCP] Arquivo a ser baixado: {}'.format(nome_arquivo.decode()))
                sleep(1)                                # Espera
                if funcoes.validar_arquivo(DIRETORIO + nome_arquivo.decode()) and funcoes.tamanho_valido(DIRETORIO + nome_arquivo.decode()):
                    ok = True
                    funcoes.arquivo_valido(conexao)
                    print('[TCP] Arquivo Valido')
                else:
                    funcoes.arquivo_invalido(conexao)
                    print('[TCP] Arquivo invalido')
            # --- Valida arquivo ---

            sleep(1)
            print('[TCP] Enviando arquivo ...')
            funcoes.enviar_arquivo(DIRETORIO + nome_arquivo.decode(), conexao)
            print ('[TCP] Enviado o arquivo {} para o cliente {}'.format(nome_arquivo.decode(), ip))
    except Exception as ex:
        conexao.close()
        print('[TCP] Erro: {}'.format(ex))

    # Fecha a conexao
    conexao.close()
# --- Cliente conectado com protocolo TCP ---

def TCP_server(DIRETORIO):
    """
    [TCP]
    Cria e gerencia os datagramas do protocolo TCP
    """

    # --- Inicia o socket ---
    socket_server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)       # Socket TCP
    socket_server.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)     # Ao fechar a porta, para de escutar
    socket_server.bind(endereco)                                            # Endereco (ip do host e porta) do cliente que sera ouvida
    socket_server.listen(10)                                                # Define o limite de conexoes
    # --- Inicia o socket ---

    threads = []

    # --- Espera o cliente ---
    while True:
        print('[TCP] Servidor TCP')
        print('[TCP] Aguardando conexão ...')
        conexao, (ip, porta) = socket_server.accept()                           # Espera conexoes e as aceita
        print('[TCP] Cliente {} conectado pela porta {}'.format(ip, porta))
        print('[TCP] Esperando mensagem ...')

        # Inicia as threads
        t = threading.Thread(target = cliente_conectado_TCP, args = (conexao, ip, DIRETORIO))
        t.start()
        threads.append(t)

    
    while t in threads:
        t.join

    # Fecha o socket
    socket_server.close()
    conexao.close()    

def UDP_server2(DIRETORIO):
    """
    [UDP]
    Cria e gerencia os datagramas do protocolo UDP
    """

    socket_udp = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    socket_udp.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)

    socket_udp.bind(endereco)

    # v_ips[ip] -> (opcao, arquivo)
    v_ips = {}

    threads = []

    while True:
        print('[UDP] Servidor UDP')
        print('[UDP] Aguardando conexão...')
        
        msg, cliente = socket_udp.recvfrom(1024)
        ip = cliente[0]
        porta = cliente[1]
        print('[UDP] Cliente {} conectado na porta {}'.format(ip, porta))
        print('[UDP] Mandou a mensagem [{}]'.format(msg.decode()))


        if not ip in v_ips.keys():
            if (msg.decode() == '2'): # Receber arquivos
                v_ips[ip] = ['2', '']       # Cliente enviando arquivos pro servidor
            if (msg.decode() == '3'):
                v_ips[ip] = ['3', '']
        else:
            if ip in v_ips.keys():
                if (v_ips[ip][0] == '2'):
                    if (v_ips[ip][1] == ''):    # Campo do arquivo esta vazio
                        v_ips[ip][1] = msg.decode()
                        # Deleta o arquivo caso ele ja exista
                        if not funcoes.validar_arquivo(msg.decode()):
                            print('arq')
                            if os.name == 'nt' or os.name == 'posix':
                                os.system('rm ' + DIRETORIO + msg.decode())
                            else:
                                os.system('del ' + DIRETORIO + msg.decode())
                    else:                       # Campo do arquivo esta preenchido
                        if msg.decode() != 'END_FILE':
                            print(v_ips[ip][1])
                            arquivo = open(DIRETORIO + v_ips[ip][1], 'a')
                            arquivo.write(msg.decode())
                            arquivo.close()
                        else:
                            del v_ips[ip]
                elif (v_ips[ip][0] == '3' and v_ips[ip][1] == ''):
                    if funcoes.validar_arquivo(DIRETORIO + msg.decode()) and funcoes.tamanho_valido(DIRETORIO + msg.decode()):
                        v_ips[ip][1] = msg.decode()
                        funcoes.arquivo_valido_UDP(socket_udp, cliente)
                        print('[UDP] Arquivo Valido')
                        
                    else:
                        print('[UDP] Arquivo Invalido')
                        funcoes.arquivo_invalido_UDP(socket_udp, cliente)
                    #    print('[UDP] Arquivo invalido')
        
        if msg.decode() == '1':
            lista = listar_arquivos(DIRETORIO)
            socket_udp.sendto(lista.encode('utf-8'), cliente)

        if msg.decode() == 'Manda':
            t = threading.Thread(target = funcoes.enviar_arquivo_UDP, args=(socket_udp, cliente, DIRETORIO + v_ips[ip][1]))
            t.start()
            threads.append(t)
            del v_ips[ip]

        
    while t in threads:
        t.join


def main():
    print ('Informe onde os arquivos seram salvos: ', end = '')
    DIRETORIO = input()
    DIRETORIO = DIRETORIO + '/'
    t1 = threading.Thread(target=TCP_server, args=(DIRETORIO,))
    t1.start()
    t2 = threading.Thread(target=UDP_server2, args=(DIRETORIO,))
    t2.start()

main()
