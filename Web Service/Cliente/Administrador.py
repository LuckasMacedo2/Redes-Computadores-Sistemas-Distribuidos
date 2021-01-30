import zeep
from funcoes import imprimir_item, imprimir_lista

class Administrador:

    def __init__(self):
        self.wsdl = "http://127.0.0.1:9999/ActionHouseADM?wsdl"
        self.client = zeep.Client(wsdl=self.wsdl)

    def inserir_item(self, id, nome, quantidade, valor, descricao):
        return self.client.service.inserir(id, nome, quantidade, valor, descricao)


    def alterar_item(self, campo, condicao, novoValor):
        return self.client.service.alterar(campo, condicao, novoValor)

    def excluir_item(self, id):
        return self.client.service.excluir(id)

    def listar_item(self, id):
        return self.client.service.listar_item(id)

    def listar_todos_itens(self):
        return self.client.service.listar_itens()

    def compras_realizadas(self):
        return self.client.service.compras_realizadas()
        

    def deletar_banco(self):
        self.client.service.excluirBancoDeDados();

def interface_usuario():
    adm = Administrador()
    while True:
        print("Informe\n[1] Para inserir um item \n[2] Para alterar um item \n[3] Para excluir um item")
        print("[4] Para listar um item \n[5] Para listar todos os itens \n[6] Para listar as compras")
        print("[7] Para deletar o banco de dados\n[0] Para sair \nOpcao: ", end='')

        opc = input()

        if opc == '1':
            print("Informe o id: ", end='')
            id = int(input())
            print("Informe o nome do item: ", end='')
            nome = input()
            print("Informe a quantidade de itens: ", end='')
            quantidade = int(input())
            print("Informe o valor do item: ", end='')
            valor = float(input())
            print("Informe a descricao do item: ", end='')
            descricao = input()

            if(adm.inserir_item(id, nome, quantidade, valor, descricao)):
                print("\nInserido com sucesso\n")
            else:
                print("\n*************************** ERRO ***************************")
                print("Item já cadastrado, utilize a opcao [2] para alterar um item\n")


        elif opc == '2':
            print("------------------------------------------------------------------------------------------------------------------------------------------------------\n"+
                  "Nota:\nEsse opção é baseado no select do banco de dados\n Com isso, para utiliza-la corretamente: Primeiro informe o nome do campo, depois a condição"+
                  ", essa condição é uma expressão booleana e o novo valor que será inserido no lugar do campo.\n Por exemplo alterar o valor de um item.\n"
                  +">>>Informe um campo para ser alterado.\n>>>Os campos são:\tnome\tquantidade\tvalor\tdescricao\n>>>Campo: "
                  +"valor\n"+
                  ">>>Informe a condicao: id = 2\n" +
                  ">>>Informe o novo valor: 500.00\n\n" +
                  "Isso alterará o valor presente no campo valor do item de id 2 para 500.00"+
                  "\n------------------------------------------------------------------------------------------------------------------------------------------------------")
            print("Informe um campo para ser alterado.\nOs campos são:\tnome\tquantidade\tvalor\tdescricao\nCampo: ", end='')
            campo = input()
            print("Informe a condicao: ", end='')
            condicao = input()
            print("Informe o novo valor: ", end='')
            novoValor = input()

            if campo == 'nome' or campo == 'descricao':
                novoValor = "'" + novoValor + "'"

            if (adm.alterar_item(campo, condicao, novoValor)):
                print("\nAlterado com sucesso\n")
            else:
                print("\n*************************** ERRO ***************************")
                print("Ocorreu um erro durante a alteracao, não foi possivel alterar o valor.\nTente novamente mais tarde\n")

        elif opc == '3':
            print("Informe o id do item a ser deletado: ", end='')
            id = int(input())
            if adm.excluir_item(id):
                print("\nExcluido com sucesso\n")
            else:
                print("\n*************************** ERRO ***************************")
                print("Ocorreu um erro durante a exclusao, O id informado nao esta cadastrado\n")


        elif opc == '4':
            print("Informe o id do item a ser consultado: ", end='')
            id = int(input())

            item = adm.listar_item(id)
            if(item['id'] == -1):
                print("\n*************************** ERRO ***************************")
                print("Item não encontrado\n")
            else:
                imprimir_item(item)

        elif opc == '5':
            lista = adm.listar_todos_itens()

            if lista == None:
                print("\n*************************** ERRO ***************************")
                print("Nenhum produto cadastrado\n")
            else:
                imprimir_lista(lista, "Itens")

        elif opc == '6':
            lista = adm.compras_realizadas()

            if lista == None:
                print("\n*************************** ERRO ***************************")
                print("Nenhum compra realizada :/\n")
            else:
                print("\n--- Listagem das Compras ---")
                print(lista)
                print("-- ---\n")

        elif opc == '7':
            adm.deletar_banco()

        elif opc == '0':
            exit(1)

interface_usuario()
    #adm.inserir_item(1, 'PC', 2, 450, 'PC Gamer')
    #adm.inserir_item(2, 'Notebook', 2, 4500, 'Notebook Gamer')
