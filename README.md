

## Setup Requirements
Used H2 SQL in-memory Database to minimise setup requirements. <br>
Install OpenJDK: Use version 11.0.1

```sh
$ brew cask install java
```

## Running
### Command Line
```sh
$ ./gradlew clean
$ ./gradlew build
$ ./gradlew bootRun # run as spring boot application
```



##Requirements
The following are the most fundamental features of the app: <br>
    -Create a new expense <br>
        -The expense can be split between any number of users <br>
        -The expense can be paid for any number of users <br>
        -The expense can be split equally / by percentage / manually <br>
    -View the list of all expenses split with you, regardless of which user created the expense <br>
    -View outstanding balances with all the users you have split expenses with in the past <br>
    -View your overall outstanding balance <br>

Did it only for 1 payee for 1 expense but the code is extensible for such changes <br>

## Notes
Did it only for 1 payee for 1 expense but the code is extensible for such changes <br>
Error handling/validations can be done better but as discussed, i did necessary one's only <br>
Have Added postman collection on root level - Splitwise.postman_collection.json <br>
And for to make testing simple whenever user in expense doesn't exist in DB, added that in db. <br>
More function level documentation can be added.

## Entities
```sh
Users
    userName //PK
    email
    balance

UsersBalances
    paidBy //PK
    paidTo //PK
    amount

Transactions
    id //PK
    paidBy
    amount
    type

UsersTransactions
    transactionId //PK
    userId //PK
    amount
```    
 
sample apis-> <br>
1. Create expense <br>
POST-> localhost:8080/transaction <br>
splitType: { Equal,    Manual,    Percent} <br>
request->  <br>
```sh
{
    "paidBy": "user1",
    "amount": 100.0,
    "users": [
        {
            "userName": "user1",
            "shareAmount": 0.0,
            "sharePercentage": 10
        },
        {
            "userName": "user2",
            "shareAmount": 0.0,
            "sharePercentage": 30
        },
        {
            "userName": "user3",
            "shareAmount": 0.0,
            "sharePercentage": 40
        },
        {
            "userName": "user4",
            "shareAmount": 0.0,
            "sharePercentage": 20
        }
    ],
    "description": "",
    "splitType": "Equal"
}
```

2. add user <br>
POST-> localhost:8080/user <br>
request-> <br>
```sh
{
    "userName": "user1"
}
```

3. Get user's overall balance <br>
GET-> localhost:8080/user/{userName}/getOverallBalance <br>
Response-> <br>
```sh
100.0
```

4. Get Outstanding Balances with all users <br>
GET-> localhost:8080/user/{userName}/getOutstandingBalances <br>
Response-> <br>
```sh
[
    {
        "paidBy": "user1",
        "paidFor": "user2",
        "amount": 30.0
    },
    {
        "paidBy": "user1",
        "paidFor": "user3",
        "amount": 40.0
    },
    {
        "paidBy": "user1",
        "paidFor": "user4",
        "amount": 20.0
    }
]
```

5. get all expenses for a user <br>
GET -> localhost:8080/transaction/user1 <br>
Response-> <br>
```sh
{
    "expenses": [
        {
            "paidBy": "user1",
            "amount": 100.0,
            "users": [
                {
                    "userName": "user1",
                    "shareAmount": 10.0
                },
                {
                    "userName": "user2",
                    "shareAmount": -30.0
                },
                {
                    "userName": "user3",
                    "shareAmount": -40.0
                },
                {
                    "userName": "user4",
                    "shareAmount": -20.0
                }
            ],
            "description": null,
            "splitType": "Percent"
        }
    ]
}
```














