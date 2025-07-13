Desde swagger añadimos los siguientes datos para probar el funcionamiento de la comunicación 
entre customer, transaction y product
# Testing micorservices scheme
This file contains the imputs shcmes for to test POST method on each microservices(customer, product, and transactions)

## 1- Products scheme

### scheme product 1
 ```json
 {
    "id": 1,
    "code": "01",
    "name": "Cuenta horros"
  }
```
## scheme product 1
 ```json
  {
    "id": 2,
    "code": "005",
    "name": "tarjeta credito"
  }
```
## scheme product 1
 ```json
  {
    "id": 3,
    "code": "00687l",
    "name": "Puntos banco"
  }
```
## 2- Customer schemes
### scheme customer 1
```json
{
  "id": 0,
  "code": "01",
  "name": "Carlos",
  "phone": "string",
  "iban": "000251487",
  "surname": "string",
  "address": "string",
  "products": [
    {
      "id": 0,
      "productId": 1,
      "productName": "string"
    },
 {
      "id": 0,
      "productId": 2,
      "productName": "string"
    }
  ],
  "transactions": [
    {}
  ]
}
```
## 3- Transaction Schema

### scheme Transaction 1
```json
{
  "reference": "6524ld",
  "ibanAccount": "000251487",
  "date": "2022-08-15T11:36:07.683Z",
  "amount": 450,
  "fee": 2,
  "descripcion": "Consignación",
  "status": "LIQUIDADA",
  "channel": "OFICINA"
}
```  a mi me lo ha creado bien sin incluir el id
### scheme Transaction 2
```json
{
  "reference": "hg52487",
  "ibanAccount": "000251487",
  "date": "2022-11-10T15:20:00.437Z",
  "amount": 100,
  "fee": 3,
  "description": "Retiro",
  "status": "RECHAZADA",
  "channel": "WEB"
}

```
### scheme Transaction 3
```json
{
  "id": 0,
  "reference": "53254jks",
  "ibanAccount": "000257849",
  "date": "2022-11-10T15:20:00.437Z",
  "amount": 100,
  "fee": 3,
  "description": "Retiro",
  "status": "LIQUIDADA",
  "channel": "WEB"
}
```

