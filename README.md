# CQRS and DDD architectural patterns

## Abstract
This work presents a practical application of the CQRS (Command and Query Responsibility Segregation) and DDD (Domain-Driven Design) architectural patterns, it includes:  
*	Domain model and bounded context definition
*	Clean separation between the technical code and the business logic
*	Command side rest api
*	Query side rest api
* Saga Pattern

## Software requirements
*	Spring Boot 2.0.0.RELEASE
*	Axon Framework 3.3.2
*	Quartz 2.2.1
* RabbitMQ
* ActiveMQ
*	PostgreSQL
*	Angular

## Practical Example
A small sales application with the following user stories: 
* **As a** Billing manager **I want to** Update an existing invoice **So that** I can extend the payment date
* **As a** Billing manager **I want to** Get a list of all invoices **So that** I can navigate over all existing invoices 
* **As a** Billing manager **I want to** Searching for particular invoices **So that** I can find invoices to be updated 
* **As an** Order manager **I want to** Enter orders on behalf of customers **So that** I can fulfil customer needs
* **As an** Order manager **I want to** Cancel orders on behalf of customers **So that** I can fulfil customer needs
* **As an** Order manager **I want to** Get a list of all orders **So that** I can navigate over all existing orders 
* **As an** Inventory manager	**I want to** Add new products to inventory **So that** I can ensure that the product stock is adequate 
* **As an** Inventory manager	**I want to** Edit an existing product **So that** I can ensure that the product stock is adequate
* **As an** Inventory manager	**I want to** Delete an existing product **So that** I can ensure that the product stock is adequate
* **As an** Account Manager **I want to** Add new customer account **So that** I can fulfil customer needs
* **As an** Account Manager **I want to** Edit an existing customer account **So that** I can fulfil customer needs
* **As an** Account Manager **I want to** Delete an existing customer account **So that** I can fulfil customer needs
* **As a** Sales manager **I want to** Consult sales statistics **So that** I can have a clear view of the business performance
## Modules Packaging
![](https://github.com/AfifBouzidi/CQRS_DDD/blob/master/Modules%20Packaging%20.png)

| Component  | Responsibility    |
| ------------ | ------------ |
|  Sales_domain  | The domain layer is the core of the software, There is one package per aggregate, and to each aggregate belongs entities and value objects   |
| Sales_commands   | Contains domain commands   |
|  Sales_events |  Contains domain events  |
| Sales_application       |   Coordinates the domain layer objects to perform use cases|
|   Sales_command_api|  Handles create, update, and delete requests and emits events when data changes |
|   Sales_query_api|  Handles queries by executing them against materialized views. The views are kept up to date by subscribing to the stream of events emitted when data changes |
| Sales_infrastructure  |  Consists of everything that exists independently of the application: external libraries, database… |

## Detailed design
#### Domain model and bounded contexts 
![](https://github.com/AfifBouzidi/CQRS_DDD/blob/master/domain%20model.png)
#### Place Order Activity Diagram
![](https://github.com/AfifBouzidi/CQRS_DDD/blob/master/place%20order%20.png)
#### Receive payment Activity Diagram
![](https://github.com/AfifBouzidi/CQRS_DDD/blob/master/Receive%20payment.png)
#### Payment Scheduler job Activity Diagram
![](https://github.com/AfifBouzidi/CQRS_DDD/blob/master/Payment%20Scheduler%20job%20.png)

#### Order state diagram
![](https://github.com/AfifBouzidi/CQRS_DDD/blob/master/Order%20state%20diagram.png)
#### Invoice state diagram
![](https://github.com/AfifBouzidi/CQRS_DDD/blob/master/Invoice%20state%20diagram.png)
## Getting Started
