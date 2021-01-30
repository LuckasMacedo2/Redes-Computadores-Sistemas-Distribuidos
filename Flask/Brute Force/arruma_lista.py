
import os


basedir = os.path.abspath(os.path.dirname(__file__))
arq = open(basedir + '\\pass_list\\list.txt', 'r',  errors='ignore')
arq2 = open(basedir + '\\pass_list\\list2.txt', 'w',  errors='ignore')

i = 0

for linha in arq:
    arq2.write(linha)

    i = i + 1

    if i == 500000:
        break


arq.close()
arq2.close()