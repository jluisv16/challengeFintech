
<h1 align="center">Challenge Ontop para backend Java Senior</h1>

Se necesita desarrollar una API siguiendo el estándar Rest para cumplir con las funcionalidades de la siguientes
pantallas:
La funcionalidad consta de que el usuario pueda retirar su dinero de la wallet hacia su banco.
Para realizar eso contamos con la API (ver los mocks adjuntos) de un proveedor que va a ejecutar la transferencia
finalmente entre las cuentas bancarias.
Para tener la mejor experiencia de usuario queremos que el mismo ingrese sus datos bancarios solo una vez y luego
pueda realizar todas las transacciones que quiera siempre y cuando tenga el balance en su wallet para hacerlo.
Las transacciones tienen que tener:

● Un fee de 10% aplicado al momento de realizarse la transacción
● Un estado puesto que posterior a la transacción el proveedor podría informarnos asincrónicamente que se
canceló.
Para configurar la cuenta necesitamos que se ingresen los siguientes atributos:
● Nombre y apellido
● Número de identificación nacional
● Número de cuenta
● Nombre de banco

Las transacciones realizadas quieren poder consultarse ordenadas por fecha de creación descendente en una tabla
con paginado y con filtros de búsqueda por monto y fecha.
Servicios disponibles
Wallet -> Top Up, Withdraw, Balance
Proveedor de transferencias -> Payment, Status
Evaluación
Se va a evaluar el challenge haciendo énfasis en las buenas prácticas de coding y en el diseño de la solución.
Puntos a incluir:
● Tests unitarios
● Gestión de excepciones
● Readme para levantar el proyecto localmente (o subirlo a la nube)
Tecnologías a utilizar
● Java 8+
● Spring boot 2+
● Git
● Docker o docker compose (deseable)
● Cualquier base de datos sin restricción
● Cualquier framework de tests unitarios
Entregable
Para el entregable se debe enviar un mail con la siguiente información:
● La uri del repositorio público para poder revisar el código.
● Una colección de postman con todos los endpoints desarrollados y un ejemplo de ejecución para cada uno.

<h2>Solución:</h2>

Se crea la imagen de mysql, además se agrega la BD </br>
<code>docker-compose up </code></br>

Luego ingresar a la carpeta del proyecto y colocar el siguiente comando <br>
<code>./mvnw clean package</code>

Luego por comandos levantar el proyecto <br>
<code>docker build -t "spring-boot-docker" .</code> </br>

Luego correr el proyecto con el siguiente comando <br>
<code>docker run --name spring-boot-docker -p 8080:8080 spring-boot-docker:latest</code> </br>

Luego importar el proyecto POSTMAN que se encuentra en resources/files/Mocks for interviews_postman.json <br>
y ejecutar los 2 ultimos URLS.

