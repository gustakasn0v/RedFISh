RedFISh
=======
Universidad Simón Bolívar.
Implementación de archivos remotos mediante el uso de RMI.
Proyecto para el curso de Redes de Computadoras.
Trimestre Septiembre - Diciembre 2014.
Grupo 42: Balbás, Andrea. Carnet: 09-10076.
          El Khoury, Gustavo. Carnet: 10-10226.

Funcionamiento del programa
===========================
El servidor de archivos consiste en un sistema cliente-servidor basado en el
uso de invocación de métodos remotos (RMI, por sus siglas en inglés). 
Este sistema ofrece a sus usuarios la posibilidad de listar los archivos
que existan en el servidor, descargar alguno de éstos, subir un archivo nuevo,
y sólo puede borrar aquellos archivos que le pertenezcan, es decir, que hayan
sido subidos al servidor por él. De la misma manera, el cliente puede listar
los archivos presentes en la carpeta donde esté ubicado, y salir del sistema
en el momento que lo desee. Se provee también de una funcionalidad de ayuda al
usuario, en donde se describe los comandos disponibles y para qué funciona cada
uno de ellos. El servidor de archivos cuenta con una funcionalidad que permite
listar los últimos 20 comandos recibidos de clientes, junto con sus argumentos
(si aplica) y el nombre del usuario que lo ejecutó.

Sin embargo, no cualquier programa cliente puede gozar de las funciones
provistas por el servidor, sino que el usuario debe tener unas credenciales
que el servidor valida en cada solicitud enviada por el cliente, y que le 
permiten acceder a sus funciones. Para cumplir con el objetivo de que los
clientes se autentiquen para utilizar las funciones del servidor de archivos,
éste se apoya en el uso de un servidor de autenticación, que dado un archivo
con combinaciones de usuario/contraseña válidos, crea una lista con los
usuarios cuyo acceso al servidor está permitido.

El servidor de archivos utiliza un objeto remoto de autenticación para autenticar 
a los clientes. Este objeto es exportado por el servidor de autenticación, el cual analiza el archivo de texto provisto, y agrega cada entrada a una base de datos interna que será consultada por el servidor. Además, el servidor posee una lista circular que mantiene los 20 comandos más 
recientes que han ejecutado los clientes. Finalmente, mantiene una lista de archivos RMI, que consisten de un nombre de archivos y un dueño. Esta lista se actualiza con los comandos
del cliente, insertando y borrando archivos según se ejecuten los comandos. Esta lista se consulta para determinar si un usuario puede borrar el archivo que solicite, y se llena inicialmente
con los archivos del directorio donde se encuentra el servidor. Estos archivos son propiedad del servidor, y no pueden ser borrados por ningún usuario.
El cliente realiza la ejecución de los comandos exportados por el servidor. Al iniciar, consulta el archivo de texto para ver si el usuario está autenticado.  En caso de que hayan varias lineas,
el programa toma como usuario el último registro válido en el archivo.


Explicación de los archivos del proyecto
========================================
a_rmifs.java                            Programa que se encarga del servidor de autenticación.
AuthDatabaseImpl.java                   Implementación de la interfaz AuthDatabase, que se utiliza 
                                        para la autenticación de usuarios.
AuthDatabase.java                       Interfaz de la clase que se utiliza para la autenticación 
                                        de los usuarios.
AuthFileParser.java                     Implementación de la interfaz FileParser, que se utiliza para hacer
                                        el análisis del archivo que contiene los usuarios autorizados para 
                                        acceder al servidor de archivos.
CommandFileParser.java                  Implementación de la interfaz FileParser, que se utiliza para hacer
                                        el análisis del archivo que contiene los comandos que el cliente ejecuta.
c_rmifs.java                            Programa que se encarga de las funciones del cliente.
FileExistsException.java                Implementación de excepción utilizada en caso de que un usuario intente 
                                        subir un archivo que ya existe en el servidor de archivos.
FileParser.java                         Interfaz para hacer el análisis del archivo que contiene los usuarios
                                        autorizados para acceder al servidor de archivos y sus contraseñas.
FileServerCommand.java                  Implementación de la clase de los comandos del servidor.
FileServerImpl.java                     Implementación de las funciones del servidor de archivos.
FileServer.java                         Interfaz de las funciones del servidor de archivos.
Makefile                                Archivo de compilación del proyecto.
NotAuthenticatedException.java          Implementación de excepción utilizada en caso de que un usuario no 
                                        esté autenticado.
NotAuthorizedException.java             Implementación de excepción utilizada en caso de que un usuario 
                                        no esté autorizado para ejecutar una acción en el servidor de archivos.
org                                     Directorio con las librerías de apache que se usaron en el proyecto.
RMIFile.java                            Clase correspondiente a los archivos que serán enviados al servidor
                                        de archivos mediante el uso de RMI.
RMIInputStreamImpl.java                 Implementación de las funciones de los streams de bytes de entrada.
RMIInputStreamInterf.java               Interfaz de las funciones de los streams de bytes de entrada.
RMIInputStream.java                     Clase de los stream de bytes de entrada para RMI.
RMIOutputStreamImpl.java                Implementación de las funciones de los streams de bytes de salida.
RMIOutputStreamInterf.java              Interfaz de las funciones de los streams de bytes de salida.
RMIOutputStream.java                    Clase de los stream de bytes de salida para RMI.
s_rmifs.java                            Programa que se encarga del servidor de archivos.
User.java                               Implementación de la clase Usuario.


Requerimientos
==============
     Se requiere el JDK Java 1.7 para compilar y ejecutar el proyecto.

Librerías
=========
     Se hace uso de las librerías Apache Commons CLI y Apache Commons para reconocer opciones de consola y para implementar la lista de comandos que se usa en el 
comando log del servidor. Se provee una copia de la Licencia Apache v2.0. Las librerías se encuentran en la carpeta org

Ejecución
=========
     En los tres ejecutables se provee, en adición a los flags requeridos por el enunciado, un flag -help que muestra una descripción de las opciones por consola, 
así como los valores por defecto que se han tomado en cuantop a puertos y hosts de los servicios
Si se desea compilar/ejecutar el cliente de manera separada, se debe contar con los archivos siguientes, y compilarlos en el mismo directorio:

AuthFileParser.java: Permite analizar el archivo de usuarios y claves
CommandFileParser.java: Permite analizar el archivo de comandos
FileExistsException.java : Excepcion emitida por el servidor si un archivo a subir/bajar ya existe
FileParser.java: Interfaz para reconocedores de archivos
FileServerCommand.java: Comando para el FileServer, que se reconocen del archivo de comandos
FileServer.java: Interfaz del objeto remoto del servidor de archivos
NotAuthenticatedException.java: Excepcion emitida por el servidor si el cliente no está autenticado
NotAuthorizedException.java: Excepcion emitida por el servidor si el cliente no está autorizado para borrar un archivo
RMIInputStreamInterf.java, RMIInputStream.java, RMIOutputStreamInterf.java, RMIOutputStream.java:
Métodos para subir/bajar archivos a través de RMI:
User.java: Clase que almacena información de autenticación de un usuario
La carpeta org/apache/commons/cli: Librerias de reconocmiento de opciones por consola.


Compilacion
===========
     Se provee de un archivo Makefile capaz de realizar todas las labores de compilación. Debe descomprimirse el archivo y ejecutar make para compilar el proyecto. 
     

    Copyright (C) 2014  Gustavo El Khoury <gustavoelkhoury@gmail.com>

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License along
    with this program; if not, write to the Free Software Foundation, Inc.,
    51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.

    The full GPLv2 License can be fount at the root of the repo, in the 
    LICENSE file.
