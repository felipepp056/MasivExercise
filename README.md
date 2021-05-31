# MasivExercise
masiv API exercise 

el programa cuenta con todos los endpoints requeridos la versión final es version 1.2
Se creó usando las ruletas como objetos con los siguientes parámetros:

String id: id de la ruleta
int result: resultado de la apuesta con la ruleta (0 - 36) 
int status: (0 = estado cerrado) , (1 = estado activo) 
ArrayList<Player>: Lista de los jugadores activos para el juego de ruleta, los atributos del objeto Player son:

String id = id del jugador;
String bet =  apuesta del jugador (String 0-36) o (String: Rojo - Negro);
double profit = ganancia del jugador en la partida;
money = valor apostado para jugar en el juego de la ruleta (money < 10000); 



1. Endpoint de creación de ruleta se debe hacer con el asi:  "/create" -devuelve id de la ruleta creada
2. Endpoint de apertura de ruleta se hace asi: "/open/{id}" -devuelve un mensaje avisando el estado de la petición (estado abierto Existosa) o (estado abierto Denegado)
3. Endpoint de apuesta de ruleta se hace así: "/bet/{rouleteID}/{betType}/{betValue}" 
                                                                           donde: rouleteID = ID de la ruleta a jugar
                                                                                  betType = valor de la apuesta del jugador (0- 36) ó (Negro - Rojo)
                                                                                  betValue = valor del dinero apostado por el jugador < 10000
                                                                En los Headers: playerID = valor del id del jugador (String) (Se aseguró de no agregar un jugador con id ya usado)

  4. Endpoint de cierre de apuesta se hace así: "/close/{id}" - devuelve array con el valor del resultado de la apuesta y los resultados indiviuales de todos los jugadores activos
                                                           ejemplo: [
                                                                       "El valor obtenido en la ruleta fue: 10",
    [
                                                                              {
                                                                                 "id": "01",
                                                                                  "bet": "25",
                                                                                  "money": 0,
                                                                                  "profit": -10000
                                                                               },
                                                                              {
                                                                                   "id": "02",
                                                                                   "bet": "Rojo",
                                                                                    "money": 18000,
                                                                                    "profit": 8000
                                                                                },
                                                                               {
                                                                                     "id": "03",
                                                                                     "bet": "Negro",
                                                                                     "money": 0,
                                                                                     "profit": -10000
                                                                                 }
                                                                         ]
  5. EndPoint de listado de ruletas se hace así: "/all" - devuelve un listado de todas las ruletas con sus atributos.
