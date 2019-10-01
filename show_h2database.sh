mvn dependency:copy-dependencies
# run the below command from project root
# database is created in home dir of user in case of other dir "jdbc:h2:file:~/db/test"
java -cp "./target/dependency/h2-1.4.196.jar" org.h2.tools.Shell -url "jdbc:h2:file:./test" -user "test" -password "test"