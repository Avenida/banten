# Banten 

[![Build Status](https://travis-ci.org/waabox/banten.svg?branch=master)](https://travis-ci.org/waabox/banten)

<h1>What is Banten?</h1>

Banten is a modular application Framework, based on Spring-Boot. http://projects.spring.io/spring-boot/

Banten focus in perform deep transversals cuts of funcionality called "Module".

<h1>Creating a new Application with Banten!</h2>

The only thing you need to do, is extend the BantenApplication class, providing the modules that you want to use.
This will create the Spring's Application Context.

```java
  public class SampleApplication extends BantenApplication {
    public SampleApplication() {
      super(
        ModuleA.class,
        ModuleB.class
      )
    }
  }
```
