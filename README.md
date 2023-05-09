# MusiQuali

Afin de pouvoir importer le projet et de pouvoir l'utiliser, il faut se munir de l'application [SpringToolSuite4](https://spring.io/tools).

Une fois installé, suivez les étapes suivantes :

1. Ouvrir Spring Tool Suite 4
2. `File` → `Import` → `Git` → `Projects from Git (with smart import)` → `Clone URI`
3. Rentrez l'URI https du git : `https://github.com/dev-vinz/Spring-MusiQuali.git`
4. À la dernière étape d'importation, choisissez d'importer uniquement les dossiers contenenant `PLogger/`, `PMusicAdmin/` et `PMusicGame/`, autrement ils seront à double
5. Cliquez sur `Finish`

Une fois l'importation effectuée, faites :

- Un clic droit sur `PLogger` → `Run As` → `Maven build...` → `Goals: clean install` → `Run`
- Un clic droit sur `PMusicAdmin` → `Run As` → `Maven build...` → `Goals: clean install` → `Run`
- Un clic droit sur `PMusicGame` → `Run As` → `Maven build...` → `Goals: clean install` → `Run`

Lancez ensuite un conteneur Docker grâce à la commande suivante : `docker run -it -p 61616:61616 -p 8161:8161 -p 6572:6572 -e ACTIVEMQ_DISALLOW_WEBCONSOLE=false -e ACTIVEMQ_USERNAME=myactivemquser -e ACTIVEMQ_PASSWORD=myactivemquserpass -e ACTIVEMQ_WEBADMIN_USERNAME=root -e ACTIVEMQ_WEBADMIN_PASSWORD=pass symptoma/activemq:latest`

Cette commande va récupérer et démarrer le service ActiveMQ. Si vous possédez déjà le conteneur, lancez-le simplement manuellement.

Une fois ceci fait, vous pouvez lancer le projet en faisant :

- Un clic droit sur `PLogger` → `Run As` → `Spring Boot App`
- Un clic droit sur `PMusicAdmin` → `Run As` → `Spring Boot App`
- Un clic droit sur `PMusicGame` → `Run As` → `Spring Boot App`

L'application sera ensuite disponible dans votre navigateur aux adresses `localhost:8080/` et `localhost:8081/`.
