# customer_rewards
====Assignment and Instructions:=====
A retailer offers a rewards program to its customers, awarding points based on each recorded purchase.
A customer receives 2 points for every dollar spent over $100 in each transaction, plus 1 point for every
dollar spent over $50 in each transaction
(e.g. a $120 purchase = 2x$20 + 1x$50 = 90 points).
Given a record of every transaction during a three month period, calculate the reward points earned for
each customer per month and total.

	In memory H2 database used to persist data.
	Default customer added as below on Application startup
		Customer ID: 1, 2, 3	
	Default Orders added as below:
		ORDER_ID	CUSTOMER_ID	ORDER_DATE	ORDER_AMOUNT
		  10001			1		2023-04-12		120
		  10002  		1		2023-04-01		85
		  10003  		1		2023-03-04		160
		  10004  		1		2023-03-01		90
		  10005  		1		2023-02-04		120
		  10006  		1		2021-02-05		165
		  10007			2		2023-04-05		113
		  10008			2		2023-03-27		80
		  10009			2		2023-03-04		102
		  10010			2		2023-03-01		210
		  10011			2		2023-02-27		130
		  10012			2		2023-04-15		88
		  10013			3		2023-04-05		102
		  10014			3		2023-03-27		84
		  10015			3		2023-03-04		200
		  10016			3		2023-03-01		103
		  10017			3		2023-03-27		500
		  10018			3		2023-04-20		585
		  10019			3		2023-03-14		102	
	Endpoints exposed:
	Customer:
		Create Customer: POST - http://localhost:8080/retailer/v1/customer
		Request Body:
		{
		    "name": "abc",
		    "email": "am@gmail.com"
		}
		Update Customer: PATCH - http://localhost:8080/retailer/v1/customer/1
		Request Body:
		{
		   "name": "abc",
		    "email": "am@gmail.com"
		}	
		Get Customer: GET - http://localhost:8080/retailer/v1/customer/1
			
	Order:
		Create Order: POST - http://localhost:8080/retailer/v1/customer/1/order
		Request Body:
		{
		    "orderPrice": 120  
		}
			
	Reward:
		Get Reward of Customer for each month: GET - http://localhost:8080/retailer/v1/customer/reward/1
		Get Reward of Customer for particular month: GET - http://localhost:8080/retailer/v1/customer/reward/1/yearmonth/202304
			
			
