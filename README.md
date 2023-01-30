# MusiQuali

Afin de pouvoir importer le projet et de pouvoir l'utiliser, il faut se munir de l'application [SpringToolSuite4](https://spring.io/tools).

Une fois installé, suivez les étapes suivantes :

1. Ouvrir Spring Tool Suite 4
2. `File` → `Import` → `Git` → `Projects from Git (with smart import)` → `Clone URI`
3. Rentrez l'URI https du git : `https://github.com/dev-vinz/Spring-MusiQuali.git`
4. À la dernière étape d'importation, choisissez d'importer uniquement le dossier contenenant `PMusic/`, autrement il sera à double
5. Cliquez sur `Finish`

Une fois l'importation effectuée, faites un clic droit sur `PMusic` → `Run As` → `Maven build...` → `Goals: clean install` → `Run`

Une fois ceci fait, vous pouvez lancer le projet en faisant un clic droit sur `PMusic` → `Run As` → `Spring Boot App`

L'application sera ensuite disponible dans votre navigateur à l'adresse `localhost:8080/`
