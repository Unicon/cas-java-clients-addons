# CAS Java Clients Addons

CAS Java Clients Addons is an open source collection of useful extensions to Java-based client libraries for JA-SIG CAS

==========================================================================

## Project Information

* [Changelog](https://github.com/Unicon/cas-java-clients-addons/blob/master/changelog.md) 
* [Wiki](https://github.com/Unicon/cas-java-clients-addons/wiki)

## Current version
`0.5`

## Build
You can build the project from source using the following Maven command:

```bash
$ mvn clean package
```

## Usage

Declare the project dependency in your local CAS client application's `pom.xml` file as:
```xml
<dependency>
    <groupId>net.unicon.cas</groupId>
    <artifactId>cas-java-clients-addons</artifactId>
    <version>0.5</version>
</dependency>
```

Some classes in this library like `Cas20ServiceTicketJsonValidator` depend on `cas-addons`. If there is no need to use features that require `cas-addons` dependency, one may exclude it so no unnecessary transitive dependecies of it are pulled in:

```xml
<dependency>
    <groupId>net.unicon.cas</groupId>
    <artifactId>cas-java-clients-addons</artifactId>
    <version>0.5</version>
    <exclusions>
        <exclusion>
          <groupId>net.unicon.cas</groupId>
          <artifactId>cas-addons</artifactId>
        </exclusion>
      </exclusions>
</dependency>
```
