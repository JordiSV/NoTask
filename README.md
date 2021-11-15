# NoTask

## Important

### Llistes
Les llistes compten amb Id(String), Nom(String), Type(String), Tasques(pot ser null y afegir-ne després).
* Mostrar llistes | /todolists
* Consultar una llista | /todolists/{idLlista}
* Afegir una llista | /todolists
* Modificar una llista | "/todolists/{idLlista}
* Eliminar una llista | "/todolists/{idLlista}

### Tasques
Les tasques compten amb Id(String), Nom(String), Type(String), Feta(Boolean)
* Consultar totes les tasques /todoitems
* Consultar les tasques d'una llista | /todolists/{idLlista}/todoitems
* Consultar una tasca | /todolists{idLlista}/todoitems/{idTasca}
* Afegir una tasca a una llista | /todolists{idLlista}/todoitems
* Modificar una tasca | /todolists{idLlista}/todoitems
* Eliminar una llista | /todolists{idLlista}/todoitems/{idTasca}
