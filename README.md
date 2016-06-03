# Banten 

[![Build Status](https://travis-ci.org/waabox/banten.svg?branch=master)](https://travis-ci.org/waabox/banten)

<h1>What is Banten?</h1>

Banten is a modular application Framework, based on Spring-Boot. http://projects.spring.io/spring-boot/

Banten focus in perform deep transversals cuts of funcionality called "Module".

<h1>Creating a new Application with Banten!</h1>

The only thing you need to do, is extend the BantenApplication class, providing the modules that you want to use.
This will create the Spring's Application Context.

```java
  public class SampleApplication extends BantenApplication {
    @Override
    protected Bootstrap bootstrap() {
      return new Bootstrap(
        ModuleA.class,
        ModuleB.class
      );
    }
    
    @Override
    public void init(final ModuleApiRegistry registry) {
      // here you should configure modules "application wide"
    }
    
  }
```
Your pom.xml

Yes, we are in maven central, take a look at banten-sample using the banten version 1.0!

