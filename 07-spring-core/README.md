# Spring Core

## IoC and DI

ioc stands for inversion of control which simply means that you are inverting the control. As a programmer you have multiple roles such as you make sure that you create the object, you control the flow of the application now the question is what's the wrong with that? see i am ok with the flow of the application but i am not ok with the object creation. we can create the object it is so easy but it is your responsibility to create the object, it's your responsiblity to maintain the object and also to destroy the object. Basically as a programmer you are controlling everything. and that's where the problem starts because you trying to maintain all these things you are not focusing on the main thing which is the actuall business logic. As a programmer your focus should be the business logic not object creation and management of it. so you want someone else to control this object creation. you are inverting the control. earlier you had the control now you're giving control to someone else. that is inversion of control.

now to achive this thing in spring we have something called as IoC container. so basically spring works with container.

so IoC is a principle which you follow to achive this.

But how exactly you make this work? that's where we have to use a design pattern called dependency injection where you have the objects there but someone has to inject them to the applicatio.
