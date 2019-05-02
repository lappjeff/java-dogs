package com.lambdaschool.projectrestdogs;

import com.lambdaschool.projectrestdogs.Services.MessageSender;
import com.lambdaschool.projectrestdogs.models.Dog;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
public class ProjectrestdogsApplication
{

    public static final String EXCHANGE_NAME = "LambdaServer";
    public static final String DOGS_QUEUE = "DOGS_QUEUE";
    public static final String NEW_DOGS_QUEUE = "NEW_DOGS_QUEUE";
    public static DogList ourDogList;
    public static void main(String[] args)
    {

        ourDogList = new DogList();
        ApplicationContext ctx = SpringApplication.run(ProjectrestdogsApplication.class, args);

        DispatcherServlet dispatcherServlet = (DispatcherServlet)ctx.getBean("dispatcherServlet");
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);

    }

    @Bean
    public TopicExchange appExchange()
    {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue dogsQueue()
    {
        return new Queue(DOGS_QUEUE);
    }

    @Bean
    public Binding declareBindingDogs()
    {
        return BindingBuilder.bind(dogsQueue()).to(appExchange()).with(DOGS_QUEUE);
    }

    @Bean
    public Queue newDogQueue()
    {
        return new Queue(NEW_DOGS_QUEUE);
    }

    @Bean
    public Binding declareNewDogBinding()
    {
        return BindingBuilder.bind(newDogQueue()).to(appExchange()).with(NEW_DOGS_QUEUE);
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2JsonMessageConverter()
    {
        return new Jackson2JsonMessageConverter();
    }
}

