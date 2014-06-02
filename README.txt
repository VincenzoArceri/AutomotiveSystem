PROJECT TITLE: Assegnamento 2
PURPOSE OF PROJECT: Simulazione di un sistema di rilevamento del traffico e delle velocità di vetture su un tratto stradale da parte di una base centrale a cui le auto si connettono via wireless

VERSION or DATE: 4 Giugno 2014
HOW TO START THIS PROJECT: Da terminale, eseguire con il comando “java Simulator” dalla cartella "bin" all'interno del progetto.

AUTHORS: Vincenzo Arceri - Matteo Calabria - Pietro Musoni - Carlo Tacchella

STRUCTURE OF PROJECT: Per questo progetto abbiamo utilizzato quattro diversi design pattern. Facade per gestire i tre nodi principali, ovvero la base station, i channel e le cars. Abbiamo utilizzato il pattern Observer per gestire il passaggio di pacchetti dalla base station in broadcast verso tutte le auto e per ottenere la risposta da queste tramite il metodo update() descritto dal pattern.
Il modello Factory lo abbiamo utilizzato per la creazione dei canali (SimpleFactory) e per creare le macchine che a loro volta sfruttano il pattern Adapter per la specializzazione da Manual ad Automatic.

USER INSTRUCTIONS: Il programma presenta un'interfaccia grafica divisa in due finestre. Nella finestra di sinistra si possono vedere le informazioni sulle macchine registrate alla base centrale, tramite una tabella, al di sotto di questa c'è il pulsante "Refresh" tramite il quale l'utente può aggiornare i dati della tabella con le ultime informazioni. Sulla destra c'è la finestra che rappresenta lo stato dei canali e la percentuale di occupazione.
