# Currency exchange
API con spring boot realizado como reto técnico

## Correr API localmente
* Instalar docker
* Clonar repositorio e ir a la carpeta clonada
* Crear imágen docker: `docker build -t currency-exchange .`
* Ejecutar los contenedores: `docker-compose up -d`
* Verificar que ambos contenedores estén corriendo ('currency-exchange' y 'postgres:14-alpine3.15'): `docker ps`
* Ejecutar los seeders: `docker exec -it [CONTAINER ID] psql -U postgres -d currency-exchange-db -f /var/lib/postgresql/seeders/initial_data.sql`
* _(Opcional)_ Ingresar a la base de datos: `docker exec -it [CONTAINER ID] psql currency-exchange-db postgres`

_NOTA_ Reemplazar [CONTAINER ID] por el ID del contener

### Despliegue automático
En caso tener integrado con alguna herramienta de despliegue automático (por ejemplo jenkins) seguir el procedimiento
