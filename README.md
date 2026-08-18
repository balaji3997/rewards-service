# Retail Rewards Service - Spring Boot Application

## Stack

- Java 17
- Spring Boot 3.3.0
- Spring Web
- Spring Data JPA
- H2 in-memory database
- Maven

## Database


Hibernate creates the schema from the JPA entities - `Customer` and `Transaction` then `data.sql` loads the test data.

The original MySQL-oriented schema has been mapped as:

```text
customers
    customer_id       PK
    customer_name
    email             

transactions
    transaction_id    PK
    customer_id       FK -> customers.customer_id
    transaction_date
    amount
    purchase_desc
```

### H2 in-memory Database details:

```text
User Name: sa
Password:
```


## Design implementation

The Reward Calculation API retrieves a rewards summary for a specific customerId provided as a path parameter. The summary can be fetched based on either a `startDate` - `endDate` range or the past `months` from the current date, specified as request parameters.

If no request parameter is provided, the `months` parameter defaults to `3`.

The transactions for the specified customer within the given date range are fetched from the database using SQL queries. Reward points are calculated for each month individually, along with the total rewards for the customer, in the `RewardsService` class.

### Reward rule

```text
amount <= 50
    0 points

50 < amount <= 100
    amount above 50 = 1 point per dollar

amount > 100
    50 points + 2 points for every dollar above 100
```

Reward points are calculated by considering the dollar amount and ignores cent values.

## API Details

- #### Explicit `startDate` - `endDate` range:

##### Endpoint
```text
GET /api/v1/customers/CUST00001/rewards?startDate=2026-07-01&endDate=2026-08-01
```
##### Request
```text
curl --location 'http://localhost:8080/api/v1/customers/CUST00001/rewards?startDate=2026-07-01&endDate=2026-08-01'
```


- #### Explicit past months from current date.

##### Endpoint
```text
GET /api/v1/customers/CUST00001/rewards?months=4
```
##### Request 
```text
curl --location 'http://localhost:8080/api/v1/customers/CUST00001/rewards?months=4'
```

- #### If `months` details were not provided like below, it will be defaulted to past 3 months

##### Endpoint
```text
GET /api/v1/customers/CUST00001/rewards
```
##### Request
```text
curl --location 'http://localhost:8080/api/v1/customers/CUST00002/rewards'
```


### API Sample Success Response
```text
{
    "customerInfo": {
        "customerId": "CUST00001",
        "customerName": "Amit",
        "email": "amit@service.com"
    },
    "startDate": "2026-07-01",
    "endDate": "2026-08-01",
    "totalRewardPoints": 150,
    "totalTransactionAmount": 190.00,
    "totalTransactionCount": 2,
    "monthlyRewards": [
        {
            "month": "2026-07",
            "rewardPoints": 150,
            "monthlyTransactionsCount": 2,
            "monthlyTransactionAmount": 190.00
        },
        {
            "month": "2026-08",
            "rewardPoints": 0,
            "monthlyTransactionsCount": 0,
            "monthlyTransactionAmount": 0
        }
    ]
}
```

### API Sample Error Response

```text
{
    "statusCode": 404,
    "error": "NOT_FOUND",
    "errorMessage": "cust003 Not found!",
    "timestamp": "2026-08-18T10:57:27.5779677"
}
```


