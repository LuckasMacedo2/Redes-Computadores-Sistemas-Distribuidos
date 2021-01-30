# Aluno: Lucas Macedo da Silva
# Disciplina: Redes de Computadores II
# Professor: Rafael Leal

# --- Cliente UDP ou TCP ---


import socket
from time import sleep
import os.path

import funcoes

#NOME_ARQUIVO = 'download.jpeg' #'teste.txt'
#ARQUIVO = './Cliente/Arquivos/'# + NOME_ARQUIVO
#DIRETORIO_DOWN = './Cliente/Downloads/'

def verifica_arquivo(arquivo):
    """
    Verifica se o arquivo a ser enviado existe e possui tamanho <= 5 Mbytes 
    
    Retorno:
    True -> se existe e tem menos de 5 Mbytes
    False -> caso contrario
    """

    # --- Valida arquivo Localmente ---
    ok = False
    ok = funcoes.validar_arquivo(arquivo)
    if ok:
        ok = funcoes.tamanho_valido(arquivo)
        if ok == False:
            print('Arquivo muito grande!')
    else:
        print('Arquivo invalido')
    # --- Valida arquivo Localmente ---
    return ok

def verifica_arquivo_server(arquivo, socket_cliente):
    """
    [TCP]
    Verifica se o arquivo a ser baixado, existe no servidor

    Retorno:
    'Arquivo Invalido!' -> se nao existe
    'Arquivo Valido!' -> se existe
    """
    # --- Valida arquivo ---
    mensagem = 'Arquivo Invalido!'                      

    print('Enviando nome do arquivo')
    socket_cliente.send(arquivo.encode('utf-8'))   # Envia o nome do arquivo
    #sleep(1)                                       # Espera
    print('Esperando resposta do servidor ...')
    mensagem = socket_cliente.recv(20).decode()
    print('Resposta: ' + mensagem)
    # --- Valida arquivo ---
    return mensagem

def verifica_arquivo_server_UDP(socket_cliente, arquivo, destino):
    """
    [UDP]
    Verifica se o arquivo a ser baixado, existe no servidor

    Retorno:
    'Arquivo Invalido!' -> se nao existe
    'Arquivo Valido!' -> se existe
    """
    # --- Valida arquivo ---
    mensagem = 'Arquivo Invalido!'                       
    print('Enviando nome do arquivo')
    socket_cliente.sendto(arquivo.encode('utf-8'),destino)   # Envia o nome do arquivo
    sleep(1)                                                 # Espera
    print('Esperando resposta do servidor ...')
    mensagem, origem = socket_cliente.recvfrom(20)
    mensagem = mensagem.decode()
    print('Resposta: ' + mensagem)
    # --- Valida arquivo ---
    return mensagem

def opcoes():
    """
    [UDP e TCP]

    Cliente escolhe as opões
    
    retorno
    1 -> Listar Arquivos
    2 -> Enviar Arquivo
    3 -> Baixar Arquivo
    """
    opc = 0
    while opc != '1' and opc != '2' and opc != '3':
        print('Informe a opção\n[1] Listar arquivos \n[2] Enviar arquivo\n[3] Baixar um arquivo\nOpcao: ', end = '')
        opc = str(input())
    return opc

def conectado_TCP(socket_cliente):
    """
    [TCP]
    O cliente escolheu a opcao TCP e um servidor valido
    
    A funcao:
    -> Lista os arquivos
    -> Faz o download de arquivos
    -> Envia arquivos para o servidor
    """

    nome_arquivo = ''
    try:
        
        opc = opcoes()
        socket_cliente.sendall(opc.encode('utf-8'))

        if (opc == '1'):                                        # Listar arquivo
            lista = socket_cliente.recv(1024)
            print(lista.decode())
        elif (opc == '2'):                                      # Enviar o arquivo para o servidor
            ok = False
            while ok == False:
                print('Arquivo a ser enviado: ', end = '')
                nome_arquivo = input()
                ok = verifica_arquivo(nome_arquivo)
            
            socket_cliente.send(nome_arquivo.split('/')[-1].encode('utf-8'))    # Envia o nome do arquivo
            sleep(1)                                             # Espera
            funcoes.enviar_arquivo(nome_arquivo, socket_cliente) # Envia o arquivo
            print('Arquivo enviado com sucesso')
        elif (opc == '3'):                                       # Receber aquivo do servidor            
            # --- Valida arquivo ---
            mensagem = 'Arquivo Invalido!'               
            while (mensagem == 'Arquivo Invalido!'):
                print('Informe um nome de arquivo válido: ', end = '')
                nome_arquivo = input()
                mensagem = verifica_arquivo_server(nome_arquivo, socket_cliente)
            # --- Valida arquivo ---
            dir = ''
            while not os.path.exists(dir):
                print('Informe um diretorio valido: ', end = '')
                dir = input()

            nome_arquivo = dir  + '/' + nome_arquivo

            sleep(1)                                            # Espera
            # Recebe o arquivo
            funcoes.receber_arquivo(nome_arquivo, socket_cliente)
            print('Arquivo recebido com sucesso')
            
    except Exception as ex:
        print (ex)
    
    # Finaliza o socket
    socket_cliente.close()
    print ('Socket finalizado')
 
def conectado_UDP(socket_cliente, destino):
    """
    [UDP]
    O cliente escolheu a opcao UDP e um servidor valido
    
    A funcao:
    -> Lista os arquivos
    -> Faz o download de arquivos
    -> Envia arquivos para o servidor
    """

    opc = opcoes()
    socket_cliente.sendto(opc.encode('utf-8'), destino)

    if opc == '1':                                  # Lista os arquivos
        lista = socket_cliente.recvfrom(1024)
        print(lista[0].decode())
    elif opc == '2':                                # Envia o arquivo ao servidor
        ok = False
        while ok == False:
            print('Arquivo a ser enviado: ', end = '')
            nome_arquivo = input()
            ok = verifica_arquivo(nome_arquivo)
            
        socket_cliente.sendto(nome_arquivo.split('/')[-1].encode('utf-8'), destino)# Envia o nome do arquivo
        sleep(1)                                    # Espera
        funcoes.enviar_arquivo_UDP(socket_cliente, destino, nome_arquivo)# Envia o arquivo
        print('Arquivo enviado com sucesso')
    elif opc == '3':                                # Faz o download de um arquivo
        # --- Valida arquivo ---
        mensagem = 'Arquivo Invalido!'                        
        while (mensagem == 'Arquivo Invalido!'):
            print('Informe um nome de arquivo válido: ', end = '')
            nome_arquivo = input()
            mensagem = verifica_arquivo_server_UDP(socket_cliente, nome_arquivo, destino)
        # --- Valida arquivo ---

        # --- Verifica se o diretorio existe ---
        dir = ''
        while not os.path.exists(dir):
            print('Informe um diretorio valido: ', end = '')
            dir = input()

        nome_arquivo = dir  + '/' + nome_arquivo

        socket_cliente.sendto('Manda'.encode('utf-8'), destino)
                                           # Espera
        # Recebe o arquivo
        funcoes.receber_arquivo_UDP(socket_cliente, nome_arquivo)
        print('Arquivo recebido com sucesso')

def main():

    opc_server = ''

    #ip = '127.0.0.1'    #input('IP para conexao: ')
    PORTA = 7700

    while opc_server != 'UDP' and opc_server != 'TCP':
        print('Informe o tipo de server\n[UDP] -> Servidor UDP\n[TCP] -> Servidor TCP\nOPCÃO: ', end = '')
        opc_server = input()

    print('Informe o ip do servidor: ', end = '')
    ip = input()
    
    continua = 'S'
    while continua.upper() == 'S':
        # --- TCP ---
        if opc_server == 'TCP':
            socket_cliente = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

            # --- Valida o servidor ---
            while True:
                try:
                    socket_cliente.connect((ip, PORTA))            # Faz a conexao com o servidor
                    break
                except Exception as ex: # Servidor nao encontrado
                    print('Erro: {}'.format(ex))
                    print('Informe o novo servidor\nServidor: ', end = '')
                    ip = input()

            conectado_TCP(socket_cliente)
        # --- UDP ---
        else:
            socket_udp = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)   # Cria o socket UDP
            socket_udp.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)

            destino = (ip, PORTA)

            conectado_UDP(socket_udp, destino)
        
        print('Continuar com a comunicação? \n[Qualquer tecla para encerrar\n[S] Para continuar\nOpcao: ', end = '')
        continua = input()

main()