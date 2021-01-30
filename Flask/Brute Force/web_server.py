# Esse é o código
# ------------------- Imports ------------------- 
from flask import Flask, render_template, url_for, request
import Breaker as br
import os
from random import randrange

# -------------------  Define as tarefas ------------------- 
passo = 100000 
i = -passo 

# ------------------- Senha encontrada ---------------------
senha = ""

# -------------------  Abre e le o arquivo ------------------- 
basedir = os.path.abspath(os.path.dirname(__file__))
arq = open(basedir + '\\pass_list\\list2.txt', 'r',  errors='ignore')
lista_arq = arq.readlines()
len_lista_arq = len(lista_arq)
print(len_lista_arq)

# -------------------  Escolha da string ------------------- 
# Aleatoriamente
#idx = randrange(0, len(lista_arq) - 1)
print('----------------------------------------------------')
print(f'String a ser encontrada -> giliarovsky')
print('----------------------------------------------------')
#breaker = br.Breaker(lista_arq[idx].encode())
#print(f'Senha a ser encontrada: {lista_arq[idx]} indice da senha {idx}')
# Ultima string da word list
#breaker = br.Breaker(lista_arq[len_lista_arq - 2].encode())
#breaker = br.Breaker(lista_arq[2*passo].encode())
breaker = br.Breaker(b'giliarovsky\n')
#breaker = br.Breaker(b'yling\n')

app = Flask(__name__)
# ------------------- Flask e routes ------------------- 
@app.route('/')
def index():
    print(f'{request.remote_addr} -> Está na raiz')
    global i
    print(f'Server -> Indice das tarefas esta em: {i}')
    global senha
    if senha != "":
        image_file = url_for('static', filename='achou.jpg')
        return render_template('senha_encontrada.html', senha = senha, img = image_file)
    return render_template('index.html')

# Quebra a senha
# ------------------- quebrar -------------------------- 
# Função que quebra  astring
@app.route('/quebrar', methods=['GET', 'POST'])
def quebrar_senha():
    global i
    print(f'{request.remote_addr} -> Quebrando a senha...')
    print(f'Server -> Indice das tarefas esta em: {i}')
    i = i + passo
    if (i == len_lista_arq):
        i = -passo
    return render_template('quebrar.html', arq = lista_arq[i:i + passo], breaker = breaker)

# Cliente acha a senha
# ------------------- nao_achou -------------------------- 
@app.route('/nao_achou', methods=['GET', 'POST'])
def nao_achou():
    global i
    print(f'{request.remote_addr} -> Nao encontrou a senha...')
    print(f'Indice das tarefas esta em: {i}')
    image_file = url_for('static', filename='fechado.png')
    return render_template('nao_achou.html', img = image_file)

# Cliente nao acha a senha
# ------------------- achou ------------------------------ 
@app.route('/achou', methods=['GET', 'POST'])
def achou():
    image_file = url_for('static', filename='aberto.png')
    global i
    global senha
    senha = request.form["senha"]
    print(f'{request.remote_addr} -> Encontrou a senha a senha era {senha}')
    print(f'{request.remote_addr} -> Senha encontrada: {senha}')
    print(f'Server -> Indice das tarefas esta em: {i}')
    return render_template('achou.html', senha = senha, img = image_file)

# ------------------- main -------------------------------
if __name__ == '__main__':
    print('Server -> Iniciando...')
    print(f'Server -> Indice das tarefas esta em: {i}')
    app.run(host = '127.0.0.1', port=5000, debug = False)