# Rapport de réalisation de TD de JEE.

## Informations sur le groupe :

Groupe :
 - Altan SORAN
 - Guillaume CAU

Exercice atteint :
Exercice 20 (terminé)


## Instructions d'installation du projet BanqueWeb

### Cas nominal

#### Installation de la version Java 15

L'application *BanqueWeb* nécessite l'installation de Java 15 pour pouvoir fonctionner.

#### Installation de la base de données

Pour persister ses données, l'application BanqueWeb utilise une base de données SQL.
De ce fait, il est nécessaire d'installer une base compatible sur la machine sur laquelle l'installation est effectuée.

Il est recommandé d'utiliser une base MySql pour faire fonctionner l'application. 
L'installation nominale doit respecter les contraintes suivantes :

| Contrainte |  Valeur   |
| ---------- | :-------: |
| Port       |   3306    |
| Adresse    | localhost |

##### Création de l'utilisateur

La base de données devra posséder un utilisateur nommé **root** et possédant le mot de passe **root**.

##### Exécution du SQL d'installation

Pour initialiser la base de données avec la bonne structure, nous avons prévu un script d'installation qui initialisera 
tout à votre place.
Le script [creation.sql](./creation.sql) créera la base de données, tandis que le script [insertion.sql](./insertion.sql) ajoutera des données de test.

#### Installation du serveur Tomcat

Pour faire tourner l'application web Java, il faut installer un serveur Tomcat.
Pour se faire, il est recommandé de suivre la documentation :

 - Pour les bases Debian/Windows : [lien de la documentation](https://tomcat.apache.org/tomcat-9.0-doc/introduction.html)
 - Pour les bases Arch           : [lien de la documentation](https://wiki.archlinux.org/index.php/tomcat)
 
##### Création des utilisateurs TomCat pour la gestion de la sécurité

Pour sécuriser les accès, l'application utilise la fonctionnalité *MemoryRealm* de tomcat.
Pour initialiser le *MemoryRealm*, il faut ajouter la ligne suivante dans le fichier **server.xml** à l'intérieur de la
balise **Engine** :
```xml
<Realm className="org.apache.catalina.realm.MemoryRealm" />
```

> Sur Linux, le fichier **server.xml** se trouve très probablement à l'adresse suivante : */etc/tomcat/server.xml*

##### Création des utilisateurs TomCat pour la gestion de la sécurité

Une fois le *MemoryRealm* activé, l'application nécessite la création de 2 utilisateurs.
Pour se faire, ajoutez les lignes suivantes dans le fichier **tomcat-users.xml** à l'intérieur de la balise **tomcat-users** :

```xml
<role rolename="lecture"/>
<role rolename="ecriture"/>
<user username="usr1" password="pwd1" roles="lecture"/>
<user username="usr2" password="pwd2" roles="ecriture"/>
```
> Sur Linux, le fichier **tomcat-users.xml** se trouve très probablement à l'adresse suivante : */etc/tomcat/tomcat-users.xml*

>Attention, n'oubliez pas de redémarrer le serveur Tomcat après cette étape.

#### Installation du bon driver Jdbc

Pour pouvoir fonctionner, l'application demande un driver JDBC. Dans notre cas, nous utilisons une base Mysql, il nous
faut donc le driver JDBC de mysql.
Il est téléchargeable ici :  [lien de téléchargement](https://dev.mysql.com/get/Downloads/Connector-J/mysql-connector-java-8.0.22.zip)
Il faudra par la suite désarchiver l'archive zip pour récupérer le Jar. 
Il faudra ensuite le placer dans le dossier **lib** de Tomcat.

> Sur Linux, le dossier **lib** se trouve très certainement à l'adresse suivante : */usr/share/tomcat/lib*

Cependant, si vous avez choisi d'utiliser un autre système de gestion de base de données, veuillez vous rendre 
à la section [ajout du bon Driver JDBC](#ajout-du-bon-driver-jdbc).

##### Configuration de l'accès à la base de données :

Pour que l'application *BanqueWeb* puisse accéder à la base de données, il est nécessaire d'ajouter une configuration
contenant les accès à la base de données.

Pour le cas nominal, vous devez ajouter les lignes suivantes dans le fichier **context.xml** à l'intérieur de la balise *Context* :

```xml
<Resource name="jdbc/BanqueWeb" auth="Container" type="javax.sql.DataSource"
              maxTotal="100" maxIdle="30" maxWaitMillis="10000"
              username="root" password="root" driverClassName="com.mysql.cj.jdbc.Driver"
              url="jdbc:mysql://localhost:3306/BanqueWeb"/>
```

> Sur Linux, le fichier **context.xml** se trouve très probablement à l'adresse suivante : */etc/tomcat/context.xml*

#### Installation du fichier war sur le serveur Tomcat

La dernière étape de la configuration du serveur est l'installation du fichier *war*.
Le fichier war vous a normalement été transféré par mail, en pièce jointe (dans l'archive).

Il est également téléchargeable à l'adresse suivante : https://github.com/asoran/banqueWeb-le-future-de-la-banque-en-ligne/releases/tag/1.0

Une fois téléchargé, vous pouvez le déplacer dans le dossier **webapps** de tomcat.

> Sur linux, ce dossier **webaps** se trouve généralement à l'adresse suivante : */var/lib/tomcat/webapps*.

Une fois la copie effectuée, il sera possible d'accéder via un navigateur à l'application.
L'url à suivre est la suivante : http://localhost:8080/BanqueWeb/
Bien entendu, il est nécessaire de modifier l'adresse *localhost* ou le port *8080* si vous les avez modifiés lors de l'installation de Tomcat.

___

### Personnalisation de l'installation

Vous êtes possiblement dans le cas où votre configuration diffère du cas nominal que nous avons prévu.
Pour vous aider, quelques modifications sont listées dans les chapitres ci-dessous.

#### Modification du serveur de base de données

##### Changement des informations d'accès à la base de données

Dans le cas où vous souhaiteriez utiliser un serveur de base de données qui n'utilise pas l'adresse et le port utilisé
par l'application par défaut ou si vous souhaitez changer es identifiants de connexion pour plus de sécurité,
vous pouvez les modifier en mettant à jour les informations que nous avons préalablement
ajoutés dans le fichier **context.xml** :

```xml
<Resource name="jdbc/BanqueWeb" auth="Container" type="javax.sql.DataSource"
              maxTotal="100" maxIdle="30" maxWaitMillis="10000"
              username="<sgbd_user>" password="<sgbd_password>" driverClassName="com.mysql.cj.jdbc.Driver"
              url="jdbc:mysql://<sgbd_address>:<sgbd_port>/BanqueWeb"/>
```

Remplacez :
 - **<sgbd_address>** par l'adresse du système de gestion de base de données
 - **<sgbd_port>** par le port du système de gestion de base de données
 - **<sgbd_user>** par le nom de l'utilisateur que vous souhaitez utiliser
 - **<sgbd_password>** par le mot de passe de l'utilisateur que vous souhaitez utiliser

##### Ajout du bon Driver JDBC

Pour pouvoir faire fonctionner la base de données correctement, l'application nécessite l'accès à un Driver JDBC.
Or, lorsque vous avez installé un autre serveur de gestion de base de données, le driver fournit par défaut ne marchera plus.
De ce fait, vous devrez procéder à son installation vous-même.
Le fichier du Driver doit prendre la forme d'un Jar qui devra ensuite être placé dans le dossier **lib** de l'installation Tomcat.
Sur Linux, se dossier se trouve très probablement à l'adresse suivante : */usr/share/tomcat/lib*.
Sinon, cherchez le dossier d'installation tomcat qui contient le dossier lib, et placer le jar dedans.

Exemples de Driver JDBC :

 - Mysql : Le jar se trouve dans le fichier zip à cette adresse : [lien de téléchargement](https://dev.mysql.com/get/Downloads/Connector-J/mysql-connector-java-8.0.22.zip)

 - MariaDB : [lien de téléchargement](https://mariadb.com/download-confirmation?group-name=Data%20Access&release-notes-uri=https%3A//mariadb.com/kb/en/mariadb-connector-j-271-release-notes/&documentation-uri=https%3A//mariadb.com/kb/en/library/mariadb-connector-j/&download-uri=https%3A//dlm.mariadb.com/1484707/Connectors/java/connector-java-2.7.1/mariadb-java-client-2.7.1.jar&product-name=Java%25208%2520connector&download-size=610.29%20KB)

##### Changement du type d'accès de l'url de la base de données

Si vous utilisez un système de gestion de base de données incompatible avec la norme mysql, il est nécessaire de changer
le type d'accès à la base de données.

Pour ce faire, il faut modifier les informations du **context.xml** :
```xml
<Resource name="jdbc/BanqueWeb" auth="Container" type="javax.sql.DataSource"
              maxTotal="100" maxIdle="30" maxWaitMillis="10000"
              username="root" password="root" driverClassName="com.mysql.cj.jdbc.Driver"
              url="jdbc:<sgbd_type>://localhost:3306/BanqueWeb"/>
```

Remplacez **<sgbd_type>** par la bonne valeur, exemple :
 - Serveur Mysql/Mariadb : **mysql**
 - Serveur Postgre Sql   : **postgresql**

### Changer l'adresse du serveur tomcat

Pour se retrouver parmi les projets, ou pour faire des url plus simples pour les utilisateurs, il est parfois nécessaire de changer *l'adresse* d'accès de l'application Web.
En effet, on peut préférer **http://banque-web.localhost** à **http://localhost:8080/BanqueWeb/**.
Pour ce faire, il suffit de créer un nouvel host Tomcat, ainsi que de prévoir un dossier spécifique.
Le dossier spécifique doit être créé dans le dossier **wabapps** de Tomcat. Dans celui ci, il faudra placer le fichier *.war* renommé en tant que **ROOT.war**

Une fois le dossier créé et le .war renommé en ROOT et placé dedans, il faut créer le VirtualHost.
Pour créer le VirtualHost, il existe différentes méthodes :

 - Via l'interface Web de Tomcat : 

 - Via curl : curl -u root:root http://localhost:8080/host-manager/text/add?name=www.banque-web.localhost&aliases=banque-web.localhost&appBase=/usr/share/tomcat/webapps/BanqueWeb&autoDeploy=on&deployOnStartup=on&deployXML=on&unpackWARs=on&manager=on
(Vous pouvez remplacer les exemples par vos valeurs)