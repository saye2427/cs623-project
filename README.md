# CS623-GroupProject
Course Name: CS623-Database Management Systems, Fall 2020
<br/>
Professor: Dr. Christelle Scharff
<br/>
Collaborators: Briana Figueroa & Sayema Islam
<br/>
Group # 4
<br/>
Language: Java
<br/>

## Entity-Relationship Diagram (ERD):
![alt text](https://github.com/saye2427/CS623-GroupProject/blob/main/Project_ERD.png?raw=true)
<br/>

### Relations
**Product to Stock**
<br/>
Rule 0:
<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Product(<ins>prodId</ins>, pname, price...)
<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Stock(<ins>prodId</ins>, <ins>depId</ins>, quantity...)
<br/>
Rule 2:
<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Product(<ins>prodID</ins>, pname, price)
<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Stock(<ins>prodId</ins>, <ins>depId</ins>, quantity, <em>Product</em>.prodId)
<br/>

**Stock to Depot:**
<br/>
Rule 0:
<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Stock(<ins>prodId</ins>, <ins>depId</ins>, quantity...)
<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Depot(<ins>depId</ins>, addr, volume...)
<br/>
Rule 2:
<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Stock(<ins>prodId</ins>, <ins>depId</ins>, quantity, <em>Depot</em>.depId)
<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Depot(<ins>depId</ins>, addr, volume)
<br/>
    
## Link to Project Presentation Video:
[Team 4 Presentation](https://drive.google.com/file/d/1Q-f99M6x1hR4f1Qd7MEVJXxbXhFKsc50/view?usp=sharing)
<br/>

## Summary of Work:
We started by first setting up the schema for our project's database in postgre as can be seen from the screenshots below:
<br/>
![alt text](https://github.com/saye2427/cs623-team4project/blob/main/PostgreSQLCode1.png?raw=true)
![alt text](https://github.com/saye2427/cs623-team4project/blob/main/PostgreSQLCode2.png?raw=true)
![alt text](https://github.com/saye2427/cs623-team4project/blob/main/PostgreSQLCode3.png?raw=true)
![alt text](https://github.com/saye2427/cs623-team4project/blob/main/PostgreSQLCode4.png?raw=true)
![alt text](https://github.com/saye2427/cs623-team4project/blob/main/PostgreSQLCode5.png?raw=true)
<br/>
<em>(N.B. All this code can also be found in our Team4Project.sql file in the repository.)</em>
<br/>
<br/>
We then created a java file/class to execute our assigned transaction--the renaming of one of the depots' IDs--by first connecting to the postgre database, and then implementing the ACID properties of atomicity and isolation (consistency is taken care of by our real-world execution of the transaction, and durability by the committing of these changes to the postgre database itself).
<br/>
<br/>
Because the Stock table has a foreign key that depends on the Depot table, neither of the tables could be updated without somehow manipulating the foreign keys. Thus we first considered implementing a transaction where the foreign key of Stock relating to Depot was dropped, and then reinstated using a CASCADE command before updating only <em>one</em> table to demonstrate that all these commands were necessary to execute the transaction (i.e. all or nothing) but given the nature of CASCADE, it was decided that this was not a good way to demonstrate atomicity.
<br/>
<br/>
So instead, we decided to execute the transaction by first dropping the foreign key of Stock, then updating BOTH the Depot and Stock tables to show the importance of both atomicity <em>and consistency</em>, as in the real world, renaming an ID in one table should also mean that the corresponding IDs in any other tables are updated/renamed as well (consistency), and both of these statements should be executed at once (atomicity). The foreign key between Stock and Depot was then, of course, reinstated. Thus in essence, we somewhat mimicked the way a CASCADE would work, but through commands to both tables instead of simply one, in order to highlight the transaction's ACID compliance.