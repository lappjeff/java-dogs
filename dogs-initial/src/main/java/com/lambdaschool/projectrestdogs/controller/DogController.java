package com.lambdaschool.projectrestdogs.controller;

import com.lambdaschool.projectrestdogs.exception.ResourceNotFoundException;
import com.lambdaschool.projectrestdogs.models.Dog;
import com.lambdaschool.projectrestdogs.ProjectrestdogsApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

@RestController
@RequestMapping("/dogs")
public class DogController
{
    private static final Logger logger = LoggerFactory.getLogger(Dog.class);

//    @Autowired
//    MessageSender messageSender;

    // localhost:4200/dogs/dogs
    @GetMapping(value = "/dogs", produces = {"application/json"})
    public ResponseEntity<?> getAllDogs()
    {
        logger.info("/dogs/dogs accessed");
//        messageSender.sendMessage("/dogs accessed");
        return new ResponseEntity<>(ProjectrestdogsApplication.ourDogList.dogList, HttpStatus.OK);
    }

    // localhost:4200/dogs/{id}
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getDogDetail(@PathVariable long id)
    {
        Dog rtnDog;

        if ((ProjectrestdogsApplication.ourDogList.findDog(d -> (d.getId()) == id)) == null)
        {
            throw new ResourceNotFoundException("Dog with id " + id + " not found");
        } else {
            rtnDog = ProjectrestdogsApplication.ourDogList.findDog(d -> (d.getId() == id));
            logger.info("/dogs/" + rtnDog.getId() + " accessed");
        }
        return new ResponseEntity<>(rtnDog, HttpStatus.OK);
    }

    // localhost:4200/dogs/breeds/{breed}
    @GetMapping(value = "/breeds/{breed}", produces = {"application/json"})
    public ResponseEntity<?> getDogBreeds (@PathVariable String breed)
    {
        ArrayList<Dog> rtnDogs;

        if ((ProjectrestdogsApplication.ourDogList.findDog(d -> (d.getBreed()).equals(breed))) == null)
        {
            throw new ResourceNotFoundException("Dog with breed " + breed + " not found");
        } else {
            rtnDogs =
                    ProjectrestdogsApplication.ourDogList.findDogs(d -> d.getBreed().toUpperCase().equals(breed.toUpperCase()));
            logger.info("/dog/breed accessed");
        }

        return new ResponseEntity<>(rtnDogs, HttpStatus.OK);
    }

    //localhost:4200/dogs/dogtable
    @GetMapping(value = "/dogtable")
    public ModelAndView displayDogTable()
    {
        ArrayList<Dog> sortedDogs = ProjectrestdogsApplication.ourDogList.dogList;
        sortedDogs.sort((d1, d2) -> d1.getBreed().compareToIgnoreCase(d2.getBreed()));

        ModelAndView mav = new ModelAndView();
        mav.setViewName("dogs");
        mav.addObject("dogList", sortedDogs);

        return mav;
    }

    //localhost:4200/dogs/apartmentfriendly
    @GetMapping(value = "/apartmentfriendly")
    public ModelAndView displayApartmentFriendlyDogsTable()
    {
        ArrayList<Dog> friendlyDogs;
        friendlyDogs = ProjectrestdogsApplication.ourDogList.findDogs(d -> d.isApartmentSuitable());
        friendlyDogs.sort((d1, d2) -> d1.getBreed().compareToIgnoreCase(d2.getBreed()));
        logger.info(friendlyDogs.toString());
        ModelAndView mav = new ModelAndView();
        mav.setViewName("dogs");
        mav.addObject("dogList", friendlyDogs);

        return mav;
    }
}
