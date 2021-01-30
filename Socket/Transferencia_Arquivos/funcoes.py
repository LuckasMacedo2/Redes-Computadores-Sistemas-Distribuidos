import os.path
from time import sleep

# --------------------------- Validacoes ----------------------------------------
def validar_arquivo(arquivo):
    """
    [UDP e TCP]
    Verifica se o arquivo existe
    """
    return os.path.isfile(arquivo)

def tamanho_valido(nome_arquivo):
    """
    [UDP e TCP]
    Verifica se o tamanho do arquivo nao ultrapassa 5 Mbytes
    """
    if os.path.getsize(nome_arquivo) > 5045398:
        return False
    
    return True
# --------------------------- Validacoes ----------------------------------------

# ---------------------------- TCP ----------------------------------------------
def receber_arquivo(nome_arquivo, conexao):
    """
    [TCP]

    Recebe o arquivo
    """
    arquivo = open(nome_arquivo, 'wb')

    mensagem = ''

    # Recebe o arquivo
    while mensagem != b'':
        # Recebendo a mensagem do cliente
        mensagem = conexao.recv(1024)               # Recebe 1024 bytes
        #print(mensagem)
        sleep(0.0001)
        # Nenhuma mensagem
        if not mensagem:
            break

        arquivo.write(mensagem)

    arquivo.close()

def enviar_arquivo(nome_arquivo, conexao):
    """
    [TCP]

    Envia o arquivo
    """
    # Envia um arquivo
    arquivo = open(nome_arquivo, 'rb')

    for linha in arquivo.readlines():
        conexao.send(linha)
    arquivo.close()

def arquivo_valido(conexao):
    """
    [TCP]

    Envia a mensagem de erro de arquivo invalido
    """
    conexao.sendall('Arquivo Valido!'.encode('utf-8'))

def arquivo_invalido(conexao):
    """
    [TCP]

    Envia a mensagem de arquivo valido
    """
    conexao.sendall('Arquivo Invalido!'.encode('utf-8'))
# ---------------------------- TCP ----------------------------------------------

# ---------------------------- UDP ----------------------------------------------
def enviar_arquivo_UDP(socket_udp, destino, arquivo):
    """
    [UDP]

    Envia o arquivo
    """
    arquivo = open(arquivo, 'rb')
    print(destino)
    for linha in arquivo:
        socket_udp.sendto(linha, destino)
        sleep(0.0001)
        print('Enviado: {} -> Cliente: {}'.format(linha, destino[0]))
    
    socket_udp.sendto('END_FILE'.encode('utf-8'), destino)
    arquivo.close()

def receber_arquivo_UDP(socket_udp, arquivo):
    """
    [UDp]

    Recebe o arquivo
    """
    arquivo = open(arquivo, 'wb')

    msg =   ''
    #print('Recebendo ...')
    while msg != b'':
        msg, origem = socket_udp.recvfrom(1024)
        sleep(0.0001)
        if msg == b'END_FILE':  # Indica que chegou ao fim
            break

        if not msg:
            break
        
        arquivo.write(msg)

    arquivo.close()

def arquivo_valido_UDP(socket_udp, destino):
    """
    [UDP]

    Envia a mensagem arquivo valido
    """
    print('Valido: {} '.format(destino))
    socket_udp.sendto('Arquivo Valido!'.encode('utf-8'), destino)

def arquivo_invalido_UDP(socket_udp, destino):
    """
    [UDP]

    Envia a mensagem de erro de arquivo invalido
    """
    print('Valido: {} '.format(destino))
    socket_udp.sendto('Arquivo Invalido!'.encode('utf-8'), destino)
# ---------------------------- UDP ----------------------------------------------