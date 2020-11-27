# Rapport de réalisation de TD de JEE.

## Informations sur le groupe :

Groupe :
 - Altan SORAN
 - Guillaume CAU

Exercice atteint :



## Instructions d'installation du projet BanqueWeb

### Installation de la base de données

Pour persister ses données, l'application BanqueWeb utilise une base de donnée SQL.
De ce fait, il est nécessaire d'installer une base compatible sur la machine sur laquelle l'installation est effectuée.

Il est recommandé d'utiliser une base MariaDB ou MySql pour faire fonctionner l'application. 
L'installation doit réspecter les contraintes suivantes :

| Contrainte    | Valeur        |
| ------------- |:-------------:|
| Port          | 3306          |
| Adresse       | localhost     |

Malheureusement, ces valeurs ne sont pour le moment pas modifiable étant donnée qu'elle sont renseignées *en dur* dans le code.
Bien entendu, la version suivante du programme corrigera le problème. 

### Installation du serveur Tomcat

Pour faire tourner l'application web Java, il faut installer un serveur Tomcat.
Pour se faire, il est recommandé de suivre la documentation :

 - Pour les bases Debian/Windows : [lien de la documentation](https://tomcat.apache.org/tomcat-9.0-doc/introduction.html)
 - Pour les bases Arch           : [lien de la documentation](https://wiki.archlinux.org/index.php/tomcat)

### Installation du bon driver Jdbc

Pour pouvoir faire fonctionner la base de données correctement, l'application nécessite l'accès à un Driver JDBC.
Le fichier du Driver doit prendre la forme d'un Jar qui devra ensuite être placé dans le dossier **lib** de l'installation Tomcat.
Sur Linux, se dossier se trouve très probablement à l'adresse suivante : */usr/share/tomcat/lib*.
Sinon, cherchez le dossier d'installation tomcat qui contient le dossier lib, et placer le jar dedans.

Vous trouverez ci-dessous le lien de deux Driver très probablement compatible avec votre base de données :

 - Mysql : Le jar se trouve dans le fichier zip à cette adresse : [lien de téléchargement](https://dev.mysql.com/get/Downloads/Connector-J/mysql-connector-java-8.0.22.zip)

 - MariaDB : [lien de téléchargement](https://mariadb.com/download-confirmation?group-name=Data%20Access&release-notes-uri=https%3A//mariadb.com/kb/en/mariadb-connector-j-271-release-notes/&documentation-uri=https%3A//mariadb.com/kb/en/library/mariadb-connector-j/&download-uri=https%3A//dlm.mariadb.com/1484707/Connectors/java/connector-java-2.7.1/mariadb-java-client-2.7.1.jar&product-name=Java%25208%2520connector&download-size=610.29%20KB)


### Installation du fichier war sur le serveur Tomcat

Une fois les drivers JDBC installés, il est possible d'insaller le fichier war.
Le fichier war vous a normallement été transféré par mail, en pièce jointe.

Il est également téléchargable à l'adresse suivante : [lien de téléchargement]

Une fois téléchargé, vous pouvez le déplacer dans le dossier **webapps** de tomcat.
Sur linux, ce dossier **webaps** se trouve généralement à l'adresse suivante : */var/lib/tomcat9/webapps*.
Une fois la copie effectuée, il sera possible d'accéder via un navigateur à l'appliction.
L'url à suivre est la suivante : http://localhost:8080/BanqueWeb/
Bien entendu, il est nécessaire de modifier l'adresse *localhost* ou le port *8080* si vous les avez modifiés lors de l'installation de Tomcat.

### Changer l'adresse du serveur tomcat

Pour se retrouver parmis les projets, ou pour faire des url plus simples pour les utilisateurs, il est parfois nécessaire de changer *l'adresse* d'accès de l'application Web.
En effet, on peut préférer **http://banque-web.localhost** à **http://localhost:8080/BanqueWeb-1.0/**.
Pour ce faire, il suffit de créer un nouvel host Tomcat, ainsi que de prévoir un dossier spécifique.
Le dossier spécifique doit être créé dans le dossier **wabapps** de Tomcat. Dans celui ci, il faudra placer le fichier *.war* renommé en tant que **ROOT.war**

Une fois le dossier créé et le .war renommé en ROOT et placé dedans, il faut créer le VirtualHost.
Pour créer le VirtualHost, il existe différentes méthodes :

 - Via l'interface Web de Tomcat : 

 - Via curl : curl -u root:root http://localhost:8080/host-manager/text/add?name=www.banque-web.localhost&aliases=banque-web.localhost&appBase=/usr/share/tomcat9/webapps/BanqueWeb&autoDeploy=on&deployOnStartup=on&deployXML=on&unpackWARs=on&manager=on (Vous pouvez remplacer les exemples par vos valeurs)