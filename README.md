## PayIT
This is a sample application that is using a handful of technologies & patterns that is for learning purposes.

#### Technologies & Patterns Used in this Project
* [Play Framework 2.x](http://http://www.playframework.com/)
* [Scala](http://www.scala-lang.org/)
* [Slick](http://slick.typesafe.com/)
* [Cake Pattern for Dependency Injection](http://jonasboner.com/2008/10/06/real-world-scala-dependency-injection-di/)
* [MySQL](http://www.mysql.com/)

### Cake Pattern
Because this is is version 2 of play I am using the concept of overriding the GlobalSettings object in play.  Do do so
you just need to create an object in the root (no package) of the project that extends GlobalSettings, like this;

    object Global extends GlobalSettings {
        
        override def getControllerInstance[A](controllerClass: Class[A]): A = {
            // Add a way to create an instance of your controller class
        }
    
    }
    
__NOTE__ - because play usually just uses a static (object) as a controller class if you take this route you need to reference
your controller class in the "routes" file with "@". For more information on this approach see this [link](http://www.playframework.com/documentation/2.3.x/ScalaDependencyInjection)

#### Better Approach??
Although I really like this approach overall I'm still not sure what the best way is to handle cake injection to controller
classes, right now I use the following approach with is just adding controller implementation to my controller trait;

##### Approach 1

    trait MyController extends Controller {
        self: SomeServiceModule =>
    
        def index = {
            Ok("success")
        }
    
    }
    
Then in my Controllers class, because I inject a service into this controller I must conform to that injection:

    object Controllers
    {
        def apply[A](clazz: Class[A]) = instances(clazz).asInstanceOf[A]

        private val instances: Map[Class[_ <: Any], Any] = {
            def controller[A: ClassTag](instance: A) = classTag[A].runtimeClass -> instance

            Map(
                controller[MyController](new MyController with ServiceRegistry)
            )
        }
    }

##### Approach 2
The other approach I could take is to use the "module" cake pattern...which makes a controller part of a module just like
and other component I might want to inject.

    trait MyController extends Controller {
        def index: Action[AnyContent]    
    }
    
    trait MyControllerModule {
        self: SomeServiceModule =>
        
        class MyControllerImpl extends MyController {
            def index = {
                Ok("success")
            }
        }        
    }
    
    object Controllers with MyControllerModule with ServiceRegistry
    {
        def apply[A](clazz: Class[A]) = instances(clazz).asInstanceOf[A]

        private val instances: Map[Class[_ <: Any], Any] = {
            def controller[A: ClassTag](instance: A) = classTag[A].runtimeClass -> instance

            Map(
                controller[MyController](new MyControllerImpl)
            )
        }
    }   
     
This feels like any other cake pattern module however the different is that you aren't really injecting the controller
into any other component, plus you must expose the controller trait outside the module to be used to map the controller
implementation.  Which isn't horrible but the beauty of the cake pattern is that in reality you are doing module programming
which means you don't have access to internal of a module unless you are mixing in the module.

__Thoughts and/or Feedback is Welcome!__ - feel free to submit an issue to discuss or show better examples.

### Setup
To setup and run this application you need to have MySQL running..go to conf/application.config to configure your database.

    db.default.driver=com.mysql.jdbc.Driver
    db.default.url="jdbc:mysql://localhost:3306/payit?characterEncoding=UTF-8"
    db.default.user=root
    db.default.password="root"
    
### Running

##### Simple:

    play run
    
##### Run & Auto Compile when Changes Occur:

    play ~run