

## Setup Requirements
Used H2 SQL in-memory Database to minimise setup requirements. 
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
The following are the most fundamental features of the app:
    -Create a new expense
        -The expense can be split between any number of users
        -The expense can be paid for any number of users
        -The expense can be split equally / by percentage / manually
    -View the list of all expenses split with you, regardless of which user created the expense
    -View outstanding balances with all the users you have split expenses with in the past
    -View your overall outstanding balance

Did it only for 1 payee for 1 expense but the code is extensible for such changes

## Notes
Did it only for 1 payee for 1 expense but the code is extensible for such changes
Error handling/validations can be done better but as discussed, i did necessary one's only
Have Added postman collection on root level - Splitwise.postman_collection.json
And for to make testing simple whenever user in expense doesn't exist in DB, added that in db.
More function level documentation can be added.


Users
    userName
    email
    balance

UsersBalanceMapping
    paidBy
    paidTo
    amount

Transaction
    id
    paidBy
    amount
    type

UsersTransactions
    transactionId
    userId
    amount
    
    
sample apis->
1. Create expense
POST-> localhost:8080/transaction
splitType: { Equal,    Manual,    Percent}
request-> 
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

2. add user
POST-> localhost:8080/user
request->
```sh
{
    "userName": "user1"
}
```

3. Get user's overall balance
GET-> localhost:8080/user/{userName}/getOverallBalance
Response->
```sh
100.0
```

4. Get Outstanding Balances with all users
GET-> localhost:8080/user/{userName}/getOutstandingBalances
Response->
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

5. get all expenses for a user
GET -> localhost:8080/transaction/user1
Response->
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














