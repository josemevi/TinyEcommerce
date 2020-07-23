# TinyEcommerce

To build this app i used the lastest version of eclipse IDE with the Web development kit and Apache tomcat 9 for the server, for the data base i used postgreSQL lastest version.

eclipse IDE: https://www.eclipse.org/downloads/
Apache tomcat 9: https://tomcat.apache.org/download-90.cgi
PostgreSQL: https://www.postgresql.org/download/ 


the class DBConnection manages the db connection here you can modifty the connection params to put others ports, passwords or db name

NOTE 
1. All the queries are inside the servlet code, this is wrong in a security point of view and i know that but since this app isn't for production purpuses i let it in this way

2. There's no validations for specific cases (f.e: correct email address) only validations for avoid crashes and exceptions from the server 

3. There's a lot to improve, i have a big chnage list for this project but i runned out of time

4. I made this all server side handler, i mean, everything is happening in the backend, a few validations are in the frontend 

5. categories and items can be added without frontend implementation, they're picked directed from the DB 