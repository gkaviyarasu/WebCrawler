WebCrawler
==========
This is a sample project to read/download the MBOX files from "http://mail-archives.apache.org/mod_mbox/maven-users/" for the given year or for calendar year.

To execute this project, first build it and generate the jar using MAVEN.

Once it is built, the jar package will be created under the target folder with the name as "WebCrawler.0.1.jar".

To run this program, execute the following command,

java -cp "lib/slf4j-api-1.7.7.jar:lib/slf4j-api-1.7.7.jar:lib/log4j-1.2.17.jar" -jar WebCrawler.0.1.jar [year]

Here, year is considered as optional, because, if it is not provided, then it will take the calendar year and download the MBOX archive for that year.
Here, the lib folder in classpath is considered as jar libraries. Else, please set the path accordingly.