To start the app, run: 
mvnw spring-boot:run

This app is written using springboot on java 8.

The following endpoints are created

-----------
Customers
-----------
Get a list of customers
GET http://localhost:8080/api/customer

Create a customer
POST http://localhost:8080/api/customer
{
	"name": "Customer 1",
	"mobile": "90010009",
	"email": "cust1@somemail.com",
	"securitiesNum": "111111111"
}

Retrieve a customer
Get a list of customers
GET http://localhost:8080/api/customer/{id}
optional parameters 
	includePortfolio - include portfolios of customer
	includeRecommendations - include investment recommendations of each portfolio
	
Update a customer
PUT http://localhost:8080/api/customer/{id}
{
	"name": "Customer 1",
	"mobile": "90010009",
	"email": "cust1@somemail.com",
	"securitiesNum": "111111111"
}

Delete a customer
DELETE http://localhost:8080/api/customer/{id}

--------
Investment
--------
Get a list of investments
GET http://localhost:8080/api/investment

Create a investment
POST http://localhost:8080/api/investment
{
	"name": "1nvestment 1",
	"returnScore": 7,
	"riskScore": 6
}

Retrieve a investment
Get a list of investments
GET http://localhost:8080/api/investment/{id}
	
Update a investment
PUT http://localhost:8080/api/investment/{id}
{
	"name": "1nvestment 1",
	"returnScore": 7,
	"riskScore": 6
}
Delete a investment
DELETE http://localhost:8080/api/investment/{id}

-----------
Portfolio
-----------
Get a list of portfolios
GET http://localhost:8080/api/portfolio

Create a portfolio
POST http://localhost:8080/api/portfolio
{
	"name": "Portfolio 1",
	"riskTolerance": 6,
	"returnPreference": 7,
	"owner": 1
}

Retrieve a portfolio
Get a list of portfolios
GET http://localhost:8080/api/portfolio/{id}
optional parameters 
	includeRecommendations - include investment recommendations of each portfolio
	
Update a portfolio
PUT http://localhost:8080/api/portfolio/{id}
{
	"name": "Portfolio 1",
	"riskTolerance": 6,
	"returnPreference": 7,
	"owner": 1
}

Delete a portfolio
DELETE http://localhost:8080/api/portfolio/{id}

Get investment recommendations for portfolio
http://localhost:8080/api/portfolio/1/recommend