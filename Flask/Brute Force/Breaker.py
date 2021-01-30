import hashlib

class Breaker:
   
    h = hashlib.sha256()

    def __init__(self, str_hash):
        self.h.update(str_hash)
    
    def verifica_hash(self, str_atual):
        """
        Verifica se a string passada apos ser convertida em hash e igual a hash requerida
        @params:
            str_hash -> A string  a ser convertida em hash e verificada se existe
            str_requerida -> A string ja convertida em hash que deseja-se encontrar
        
        @return
            True -> Caso as duas hashes sejam iguais
            False -> Caso contrario
        """
        h1 = hashlib.sha256()
        h1.update(str_atual.encode())
        return self.h.hexdigest() == h1.hexdigest()