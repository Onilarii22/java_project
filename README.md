# Gestion des personnes et des chambres

Ce programme Java en ligne de commande permet de créer une liste de personnes, de
noter les relations entre elles, de saisir leurs préférences de chambres et de
produire une attribution simple. Il permet ensuite d'échanger les chambres de
deux personnes.

## Prérequis

- Un **JDK** installé (le JDK fournit `java` et `javac`).
- Un terminal ouvert à la racine du projet. Aucune bibliothèque externe ni outil
  de construction n'est nécessaire.

Vérifiez que Java est accessible depuis le terminal :

```sh
java -version
javac -version
```

## Compilation et exécution

Depuis la racine du projet, sous Linux ou macOS :

```sh
mkdir -p bin
javac -d bin src/*.java
java -cp bin App
```

Sous PowerShell :

```powershell
New-Item -ItemType Directory -Force bin
javac -d bin src/*.java
java -cp bin App
```

La compilation place les fichiers `.class` dans `bin/`. Il faut recompiler après
une modification des fichiers `.java`. Pour arrêter le programme à tout moment,
utilisez `Ctrl+C` dans le terminal.

### Lancer depuis Visual Studio Code

Ouvrez le dossier du projet dans VS Code, attendez que l'extension Java détecte
le projet, puis lancez `App.java` avec **Run Java** (ou le bouton **Run** au-dessus
de la méthode `main`). La configuration du projet indique que les sources sont
dans `src/`, que les fichiers compilés vont dans `bin/` et que les éventuels JAR
de dépendances peuvent être placés dans `lib/`.

## Utilisation

Le programme demande d'abord le nombre `n` de personnes, qui doit être compris entre
**1 et 26**. Elles sont nommées automatiquement de `A` à `Z` et les chambres
numérotée de `1` à `n` sont créées.

Un premier menu permet de :

1. **Ajouter une relation** entre deux personnes. La relation représente le fait
   qu'elles ne s'apprécient pas. Les relations sont enregistrées dans le graphe
   et affichées sous forme de liste d'adjacence avant l'attribution.
2. **Définir les préférences** d'une personne. Il faut saisir les numéros de
   toutes les chambres, dans l'ordre souhaité, sans répétition. Une préférence
   déjà définie peut être conservée ou remplacée.
3. **Terminer la saisie**. Le programme ne passe à l'attribution que lorsque
   chaque personne possède des préférences ; sinon, il indique celles qui en
   manquent et redemande une action.

L'attribution parcourt les personnes dans l'ordre alphabétique. Pour chacune,
elle lui donne la première chambre de sa liste de préférence qui n'est pas encore occupée.
Les attributions sont ensuite affichées.

Le second menu permet d'échanger les chambres de deux personnes différentes, de compter les
personnes jalouses, ou de quitter le programme.

### Exemple de saisie

Pour deux personnes, on peut saisir les préférences de `A` comme `1 2` et celles
de `B` comme `2 1`. Les menus et les invites s'affichent
entre les saisies : suivez leurs numéros pour passer d'une étape à l'autre.

## Organisation du code

Les sources Java actives sont dans `src/` :

- [`App.java`](src/App.java) contient le point d'entrée (`main`), les menus, la
  saisie, la validation des préférences, l'attribution et l'affichage.
- [`Person.java`](src/Person.java) représente une personne, son nom, sa chambre
  attribuée et son tableau de préférences.
- [`Room.java`](src/Room.java) représente une chambre avec son numéro et son état
  d'occupation.
- [`Graph.java`](src/Graph.java) représente les relations entre personnes par
  une matrice d'adjacence. Une relation est non orientée : elle apparaît des deux
  côtés de la matrice.

Les dossiers de projet sont configurés dans [`.vscode/settings.json`](.vscode/settings.json) :
`src/` pour les sources, `bin/` pour la sortie compilée.

## Fonctionnement et limites actuelles

- L'attribution est une stratégie gloutonne, effectuée dans l'ordre `A`, `B`, etc.
  Elle respecte les préférences et évite d'attribuer deux fois une chambre, mais
  ne cherche pas la meilleure solution globale.
- Les relations enregistrées sont affichées, mais elles ne sont pas prises en
  compte dans le calcul de l'attribution.
