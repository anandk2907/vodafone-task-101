# vodafone-task-101

## Introduction - Assumption
File format is changed a bit to parse the data properly.

To save the file in csv format will delimiter  (",") 

I have changed all the integer values from (",") to (".") e.g. 32,56 to 32.56

The example file(events.csv) is attached with the source code 
in the src/main/resource/data.

## Notes
I tried to cover all the implementation and also cover as many tests as possible.

The code quality is not up to the production ready. It needs improvements.

so basically I have done more quantity than quality. So please excuse me for that, this is due 
to shortage of time.

## How to run the Application
##### On Localhost

> Build a jar file in the main dir.
>
>`mvn clean install
`
>
>Run locally as a jar file
>
>`java -jar target/<built-jar-file-name>.jar
`
>
>Or
>
>
> Run as a springboot app
>
>`mvn spring-boot:run`

Access API with Swagger - [Localhost-swagger-API](http://localhost:8088/swagger-ui.html)
