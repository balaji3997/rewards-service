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
User Name: root
Password: root@123
```


## Design implementation


### Design and Business Logic
The Reward Calculation API retrieves a rewards summary for a specific `customerId` provided as a path parameter. The summary can be fetched based on a `startDate`–`endDate` range, a past dynamic `months` window, or a combination of both specified as request parameters.  

The transactions for the specified customer within the calculated date range are fetched from the database using SQL queries. Reward points are calculated for each month individually, along with the aggregated total rewards for the customer, inside the `RewardsService` class.

### Date Range Resolution Logic

The application dynamically resolves the active transaction date range based on the optional query parameters provided (`startDate`, `endDate`, and `months`):

1. **Both `startDate` & `endDate` Provided:**
    * Uses the explicit `[startDate, endDate]` range.
    * The `months` parameter is ignored.

2. **Only `startDate` Provided:**
    * Calculates `endDate = startDate + months`.
    * If `months` is omitted, it defaults to **3 months**.
    * *Note:* If the calculated `endDate` extends past the current date, it is capped at `LocalDate.now()`.

3. **Only `endDate` Provided:**
    * Calculates `startDate = endDate - months`.
    * If `months` is omitted, it defaults to **3 months**.

4. **Neither Date Provided:**
    * Calculates `startDate = currentDate - months` and `endDate = currentDate`.
    * If no parameters are provided at all, `months` defaults to **3** (i.e., past 3 months from today).

#### Parameter Resolution Matrix

| Provided Parameters | Calculated Start Date | Calculated End Date | Behavioral Rule |
| :--- | :--- | :--- | :--- |
| `startDate`, `endDate` | `startDate` | `endDate` | Explicit range used; `months` parameter is ignored. |
| `startDate` only | `startDate` | `startDate + months` | `months` defaults to 3 if omitted. Capped at `now()` if in the future. |
| `endDate` only | `endDate - months` | `endDate` | `months` defaults to 3 if omitted. |
| `months` only | `now() - months` | `now()` | Dynamic past lookback window. |
| *None* | `now() - 3 months` | `now()` | System default 3-month lookback. |
    
### Reward rule

```text
amount <= 50
    0 points

50 < amount <= 100
    amount above 50 = 1 point per dollar

amount > 100
    50 points + 2 points for every dollar above 100
```

The rewards calculation includes cent values, ensuring precise reward points based on the full transaction amount

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

- **Invalid Customer Id is passed**:
```text
    {
        "statusCode": 404,
        "error": "NOT_FOUND",
        "errorMessage": "cust003 Not found!",
        "timestamp": "2026-08-18T10:57:27.5779677"
    }
```

- **Invalid Date Range**:
```text
  {
      "statusCode": 400,
      "error": "BAD_REQUEST",
      "errorMessage": "Start date must be before end date.",
      "timestamp": "2026-08-18T10:57:27.5779677"
  }
```

