def imprimir_item(item):
    print()
    print(f"ID: {item['id']} \nNome: {item['nome']}\nQuantidade em estoque: {item['quantidade']}"
          f"\nPreço unitário R$: {item['valor']}\nDescrição: {item['descricao']}")
    print()

def imprimir_lista(lista, desc):
    print(f"\n--- Listagem dos {desc} ---", end='')
    for item in lista:
        imprimir_item(item)
    print("-- ---")
