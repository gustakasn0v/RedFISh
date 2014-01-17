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
uno de ellos.

Sin embargo, no cualquier programa cliente puede gozar de las funciones
provistas por el servidor, sino que el usuario debe tener unas credenciales
que el servidor valida en cada solicitud enviada por el cliente, y que le 
permiten acceder a sus funciones. Para cumplir con el objetivo de que los
clientes se autentiquen para utilizar las funciones del servidor de archivos,
éste se apoya en el uso de un servidor de autenticación, que dado un archivo
con combinaciones de usuario/contraseña válidos, crea una lista con los
usuarios cuyo acceso al servidor está permitido.

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