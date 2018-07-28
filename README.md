# CQRS and DDD architectural patterns

## Abstract
This work presents a practical application of the CQRS (Command and Query Responsibility Segregation) and DDD (Domain-Driven Design) architectural patterns, it includes:  
*	Domain model and bounded context definition
*	Clean separation between the technical code and the business logic
*	Command side rest api
*	Query side rest api

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
##Modules Packaging
![modules packaging](CQRS_DDD/Modules%20Packaging%20.png)
