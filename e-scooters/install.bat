mysql -h localhost -uroot -proot < db\sql_install.sql
call mvn clean package tomcat7:redeploy