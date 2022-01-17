import sys
from typing import *


# Entrada: El descriptor de fichero. Podemos leer una línea de texto con f.readline().
#          El formato de la entrada se especifica en el apartado 2.1.
# Salida: Una lista de booleanos con True si la moneda está cara arriba (‘o’) o
#         False si está cara abajo (‘x’).
def read_data(f) -> List[bool]:
    data = []
    cadena = f.readline()
    for elem in cadena:
        if elem == 'o':
            data.append(True)
        else:
            data.append(False)
    return data



# Entrada: Un parámetro, el mismo que devuelve la función read_data.
# Salida: Lista de enteros con las instrucciones para el robot.
def process(stack: List[bool]) -> List[int]:

    # creamos la lista donde guardaremos la solucion
    # si no nos dan ninguna moneda, devolvemos una lista vacia
    instrucciones: List[int] = []
    if len(stack) == 0:
        return []

    # separar las monedas en grupos
    grupos = []
    contador = 0
    actual = stack[0]
    for elem in stack:
        if actual == elem:  # si la moneda es igual al grupo que estamos mirando añadimos 1 al contador
            contador += 1
        else:
            grupos.append((contador, actual))  # si es una moneda diferente añadimos el grupo anterior y reiniciamos el contador
            actual = elem
            contador = 1
    grupos.append((contador, actual))  # añadimos el último grupo

    # si el ultimo grupo esta bien colocado actualizamos el contador y borramos ese grupo
    puntero = len(stack)
    if grupos[-1][1] == True:
        puntero -= grupos[-1][0]
        grupos.pop(-1)

    # mientras que no esten todas en posicion
    while puntero != 0:
        if grupos[0][1] == True:  # si el primer grupo esta boca arriba
            instrucciones.append(grupos[0][0])  # le damos la vuelta al primer grupo
            instrucciones.append(puntero)  # pasamos abajo de la pila el primer grupo y el segundo
            puntero -= (grupos[0][0] + grupos[1][0])  # actualizamos puntero y borramos los grupos
            grupos.pop(0)
            grupos.pop(0)
        else:
            instrucciones.append(puntero)  # si estan bien colocadas pasamos las monedas abajo y actualizamos puntero
            puntero -= grupos[0][0]
            grupos.pop(0)
        if len(grupos) > 0:  # si aun quedan grupos, mismo que antes, pero contando por el final
            if grupos[-1][1] == False:
                instrucciones.append(grupos[-1][0])
                instrucciones.append(puntero)
                puntero -= (grupos[-1][0] + grupos[-2][0])
                grupos.pop(-1)
                grupos.pop(-1)
            else:
                instrucciones.append(puntero)
                puntero -= grupos[-1][0]
                grupos.pop(-1)
    return instrucciones




# Entrada: Un parámetro, el mismo que devuelve la función process.
# Salida: No devuelve nada. Solo muestra líneas de texto por la salida estándar
#         siguiendo el formato que se detalla en el apartado 2.2.
def show_results(solution: List[int]):
    for elem in solution:
        print(elem)


if __name__ == '__main__':
    my_stack = read_data(sys.stdin)
    my_solution = process(my_stack)
    show_results(my_solution)
