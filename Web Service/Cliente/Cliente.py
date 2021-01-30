import zeep
from funcoes import imprimir_item


class Cliente:

    def __init__(self):
        self.wsdl = "http://127.0.0.1:7777/ActionHouseClient?wsdl"
        self.client = zeep.Client(wsdl=self.wsdl)

    def listar_item(self, id):
        return self.client.service.listar_item(id)

    def listar_todos_itens(self):
        return self.client.service.listar_itens()

    def comprar(self, id, quantidade):
        return self.client.service.comprar(id, quantidade)

    def item_em_estoque(self, id, quantidade):
        return self.client.service.item_em_estoque(id, quantidade)


def interface_usuario():
    cliente = Cliente()

    while True:
        print("Informe\n[1] Para comprar um item \n[2] Para listar um item \n[3] Para listar todos os itens "
              "\n[0] Para sair \nOpcao: ", end='')

        opc = input()

        if opc == '1':
            print("Informe o id do produto a ser comprado: ", end='')
            id = int(input())

            print("Informe a quantidade de itens a serem comprados: ", end='')
            quantidade = int(input())

            possui = cliente.item_em_estoque(id, quantidade)

            if possui:
                cliente.comprar(id, quantidade)
                print("\nCompra realizada com sucesso!\n")
            else:
                print("\n*************************** ERRO ***************************")
                print("Não é possível realizar a compra. Item em falta no estoque."
                      "\nPeça uma quantidade menor ou contate o atendente mais proximo\n")

        elif opc == '2':
            print("Informe o id do item a ser consultado: ", end='')
            id = int(input())

            item = cliente.listar_item(id)
            if (item['id'] == -1):
                print("\n*************************** ERRO ***************************")
                print("Item não encontrado\n")
            else:
                imprimir_item(item)

        elif opc == '3':
            lista = cliente.listar_todos_itens()

            if lista == None:
                print("\n*************************** ERRO ***************************")
                print("Nenhum produto cadastrado\n")
            else:
                print("\n--- Listagem dos itens ---", end='')
                for item in lista:
                    imprimir_item(item)
                print("-- ---")

        elif opc == '0':
            exit(0)
            
interface_usuario()
