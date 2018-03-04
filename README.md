
## FileBox Demo Server Application

FileBox is a demo application that simulates managing of customers and their any kind of uploaded files. This repository is the server side of the application. The client side project is available on [FileBox Demo Client Application](https://github.com/gulcuneyt/react-springboot-awss3-filebox.client.git) link.

It is a spring boot project that enables managing users and files on [Amazon S3 Cloud](https://aws.amazon.com/s3/). REST services for CRUD operations on users and files are implemented.

You need [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) or later version and [Maven 3](https://maven.apache.org/download.cgi) or later version

### Technologies

* Java 8 or later
* Maven 3 or later
* Spring Boot 1.5.10
* Amazon S3 Cloud Services
* Spring Restful Services
* Spring Boot Maven plugin

### Set up Amazon S3

**1. Sign up**

Go to <https://aws.amazon.com/s3/>, click "Get Started With Amazon S3" and follow the instructions

**2. Create Bucket**

Sign in the <https://console.aws.amazon.com/s3>, and create a bucket. It will be used to store objects for our application. Define the name of this bucket in application.properties file.

	bucketName=yourCreatedBucketName

**3. Create an IAM user**

Go to <https://console.aws.amazon.com/iam/>.

In the navigation pane, choose Users and then choose Add user.

Input User name, choose Programmatic access for Access type:

Press Next: Permissions button -> go to Set permissions for jsa-user screen.

Now, choose Attach existing policies directly -> filter policy type s3, then check AmazonS3FullAccess:, and then press "Create user"

**4. Configure Client Environment**

press Download .csv for {Access key ID, Secret access key}.

Copy file ~/.aws/credentials on Linux, macOS, or Unix, or at C:\Users\USERNAME \.aws\credentials on Windows. 
Click [here](https://docs.aws.amazon.com/cli/latest/userguide/cli-chap-getting-started.html) for manual.

### Run the Application

The Spring Boot Maven plugin includes a run goal to compile and run your application. 

To run the application, at command prompt, type

	$ mvn spring-boot:run

or if you are using an ide, Run As -> Maven Build and type 'spring-boot:run' to goal field, then press run.


To run unit tests, at command prompt, type

	$ mvn test

or if you are using an ide, Run As -> Maven Build and type 'test' to goal field, then press run.

