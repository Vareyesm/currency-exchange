# Currency exchange
API con spring boot realizado como reto técnico

## Ejecutar aplicación
* Instalar postgres
* Crear la base de datos (`currency-exchange`)
* Instalar java SDK (11)
* Descargar maven y descomprimir
* Ir a la carpeta donde se clonó el proyecto spring boot y ejecutar el comando: 
`[ruta-base]\apache-maven-3.8.4\bin\mvn spring-boot:run`
* Crear usuario (desde postman con el endpoint `/api/auth/signup`)
* Generar token usando el endpoint `/api/auth/signin` y usarlo para consumir los demás web services

## Desplegar aplicación
### Despliegue manual
* Ir a la carpeta donde se clonó el proyecto spring boot y ejecutar el comando:
  `[ruta-base]\apache-maven-3.8.4\bin\mvn build`
* Detener la aplicación en el servidor
* Copiar el jar generado al servidor en el servidor
* Iniciar la aplicación

### Despliegue automático
En caso tener integrado con alguna herramienta de despliegue automático (por ejemplo jenkins) seguir el procedimiento